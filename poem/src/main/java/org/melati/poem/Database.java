package org.melati.poem;

import org.apache.java.lang.Lock;
import java.sql.*;
import java.util.*;
import org.melati.util.*;

/**
 * An RDBMS database.  Don't instantiate (or subclass) this class, but rather
 * <TT>PoemDatabase</TT>, which includes the boilerplate code for the standard
 * tables such as <TT>user</TT> and <TT>columninfo</TT> which all POEM
 * databases must contain.  If the database is predefined by a Data Structure
 * Definition <TT><I>Bar</I>.dsd</TT>, there will be an application-specialised
 * subclass of <TT>PoemDatabase</TT> called <TT><I>Bar</I>Database</TT> which
 * provides named methods for accessing application-specialised objects
 * representing the predefined tables.
 *
 * @see PoemDatabase 
 */

abstract public class Database {

  final Database _this = this;

  private Vector sessions = null; // FIXME make this more explicit
  private Vector freeSessions = null;

  private Connection committedConnection;
  private final Lock lock = new Lock();

  private Vector tables = new Vector();
  private Hashtable tablesByName = new Hashtable();
  private Table[] displayTables = null;

  // 
  // ================
  //  Initialisation
  // ================
  // 

  /**
   * Don't subclass this, subclass <TT>PoemDatabase</TT>.
   * @see PoemDatabase
   */

  Database() {
  }

  /**
   * Connect to an RDBMS database.  This should be called once when the
   * application starts up; it will
   *
   * <UL>
   *   <LI> Open <TT>this.sessionsMax()</TT> JDBC <TT>Connection</TT>s to the
   *        database for subsequent `pooling'
   *
   *   <LI> Unify (reconcile) the structural information about the database
   *        given in
   *
   *        <OL>
   *          <LI> the Database Structure Definition (<I>i.e.</I> embodied in
   *               the boilerplate code generated from it), including the
   *               POEM-standard tables defined in <TT>Poem.dsd</TT>;
   *          <LI> the metadata tables <TT>tableinfo</TT> and
   *               <TT>columninfo</TT>;
   *          <LI> the actual JDBC metadata from the RDBMS.
   *        </OL>
   *
   *        Any tables or columns defined in the DSD or the metadata tables,
   *        but not present in the actual database, will be created.  FIXME
   *        this doesn't work with Postgres 6.4.2 because <TT>ALTER TABLE ADD
   *        COLUMN</TT> does not respect the <TT>NOT NULL</TT> attribute.
   *        Conversely, entries will be created in the metadata tables for
   *        tables and columns that don't have them.  If an inconsistency is
   *        detected between any of the three information sources (such as a
   *        fundamental type incompatibility, or a string field which is
   *        narrower in the database than it was declared to be), an exception
   *        will be thrown.  In that case the database will in theory be left
   *        untouched, except that in Postgres (at least) all structural
   *        updates happen immediately and irrevocably even if made from a
   *        transaction subsequently rolled back.
   * </UL>
   *
   * @param url         The JDBC URL for the database; for instance
   *                    <TT>jdbc:postgresql:williamc</TT>.  It is the
   *                    programmer's responsibility to make sure that an
   *                    appropriate driver has been loaded.  
   *
   * @param username    The username under which to establish JDBC connections
   *                    to the database.  This has nothing to do with the
   *                    user/group/capability authentication performed by
   *                    Melati.
   *
   * @param password    The password to go with the username.
   *
   * @see java.sql.DriverManager
   * @see #sessionsMax()
   */

  public void connect(String url, String username, String password)
      throws PoemException {

    if (committedConnection != null)
      throw new ReconnectionPoemException();

    try {
      committedConnection =
          DriverManager.getConnection(url, username, password);
      sessions = new Vector();
      for (int s = 0; s < sessionsMax(); ++s)
        sessions.addElement(
            new PoemSession(
                this,
                DriverManager.getConnection(url, username, password),
                s));
      freeSessions = (Vector)sessions.clone();
    }
    catch (SQLException e) {
      throw new ConnectionFailurePoemException(e);
    }

    try {
      // Bootstrap: set up the tableinfo and columninfo tables

      DatabaseMetaData m = committedConnection.getMetaData();
      getTableInfoTable().unifyWithDB(
          m.getColumns(null, null, getTableInfoTable().getName(), null));
      getColumnInfoTable().unifyWithDB(
          m.getColumns(null, null, getColumnInfoTable().getName(), null));

      inSession(AccessToken.root,
                new PoemTask() {
                  public void run() throws PoemException {
                    try {
                      _this.unifyWithDB();
                    }
                    catch (SQLException e) {
                      throw new SQLPoemException(e);
                    }
                  }
                });

      TableListener capabilityCacheInvalidator =
          new TableListener() {
            public void notifyTouched(PoemSession session, Table table,
                                      Integer troid, Data data) {
              userCapabilities.invalidateVersion(session);
            }
          };

      getGroupCapabilityTable().addListener(capabilityCacheInvalidator);
      getGroupMembershipTable().addListener(capabilityCacheInvalidator);
    }
    catch (SQLException e) {
      throw new UnificationPoemException(e);
    }
  }

  /**
   * Don't call this.  Tables should be defined either in the DSD (in which
   * case the boilerplate code generated by the preprocessor will call this
   * method) or directly in the RDBMS (in which case the initialisation code
   * will).
   */

  protected synchronized void defineTable(Table table)
      throws DuplicateTableNamePoemException {
    if (table.getDatabase() != this)
      throw new TableInUsePoemException(this, table);

    if (tablesByName.get(table.getName()) != null)
      throw new DuplicateTableNamePoemException(this, table.getName());

    tables.addElement(table);
    tablesByName.put(table.getName(), table);
  }

  private ResultSet columnsMetadata(DatabaseMetaData m, String tableName)
      throws SQLException {
    return m.getColumns(null, null, tableName, null);
  }

  public Table addTableAndCommit(TableInfo info, String troidName)
      throws PoemException {
    Table table = new Table(this, info.getName(),
                            DefinitionSource.infoTables);
    table.setTableInfo(info);
    table.defineColumn(new ExtraColumn(table, troidName, TroidPoemType.it,
                                       DefinitionSource.infoTables,
                                       table.extrasIndex++));
    table.unifyWithColumnInfo();
    try {
      table.unifyWithDB(null);
    }
    catch (SQLException e) {
      throw new SQLPoemException(e);
    }

    PoemThread.commit();
    defineTable(table);

    return table;
  }

  private synchronized void unifyWithDB() throws PoemException, SQLException {

    // Check all tables against tableInfo, silently defining the ones
    // that don't exist

    for (Enumeration ti = getTableInfoTable().selection();
         ti.hasMoreElements();) {
      TableInfo tableInfo = (TableInfo)ti.nextElement();
      Table table = (Table)tablesByName.get(tableInfo.getName());
      if (table == null)
        defineTable(table = new Table(this, tableInfo.getName(),
                                      DefinitionSource.infoTables));
      table.setTableInfo(tableInfo);
    }

    // Conversely, add tableInfo for the tables that aren't there

    for (Enumeration t = tables.elements(); t.hasMoreElements();)
      ((Table)t.nextElement()).createTableInfo();

    // Check all tables against columnInfo

    for (Enumeration t = tables.elements(); t.hasMoreElements();)
      ((Table)t.nextElement()).unifyWithColumnInfo();

    String[] normalTables = { "TABLE" };

    // Finally, check tables against the actual JDBC metadata

    DatabaseMetaData m = committedConnection.getMetaData();
    ResultSet tableDescs = m.getTables(null, null, null, normalTables);
    while (tableDescs.next()) {
      String tableName = tableDescs.getString("TABLE_NAME");
      Table table = (Table)tablesByName.get(tableName);

      if (table == null) {
        try {
          defineTable(table = new Table(this, tableName,
                                        DefinitionSource.sqlMetaData));
        }
        catch (DuplicateTableNamePoemException e) {
          throw new UnexpectedExceptionPoemException(e);
        }
        table.createTableInfo();
      }

      table.unifyWithDB(columnsMetadata(m, tableName));
    }

    // ... and create any that simply don't exist

    for (Enumeration t = tables.elements(); t.hasMoreElements();) {
      Table table = (Table)t.nextElement();
      // bit yukky using getColumns ...
      ResultSet colDescs = columnsMetadata(m, table.getName());
      if (!colDescs.next())
        table.unifyWithDB(null);
    }

    for (Enumeration t = tables.elements(); t.hasMoreElements();)
      ((Table)t.nextElement()).postInitialise();
  }

  // 
  // ==========
  //  Sessions
  // ==========
  // 

  /**
   * The number of sessions available for concurrent use on the database.  This
   * is the number of JDBC <TT>Connection</TT>s opened when the database was
   * <TT>connect</TT>ed, currently simply fixed at five.
   */

  // ¡AbstractVersionedObject depends on this staying constant!, because overflows
  // of its `versions' array are not trapped

  public final int sessionsMax() {
    return 5;
  }

  // 
  // -----------------------
  //  Keeping track of them
  // -----------------------
  // 

  /**
   * Get a session for exclusive use.  It's simply taken off the freelist, to
   * be put back later.
   */

  private PoemSession openSession() {
    synchronized (freeSessions) {
      if (freeSessions.size() == 0)
        throw new NoMoreSessionsException();
      PoemSession session = (PoemSession)freeSessions.lastElement();
      freeSessions.setSize(freeSessions.size() - 1);
      return session;
    }
  }

  /**
   * Finish using a session.  It's put back on the freelist.
   */

  void notifyClosed(PoemSession session) {
    freeSessions.addElement(session);
  }

  /**
   * Find a session by its index.
   * session(i).index() == i
   */

  PoemSession session(int index) {
    return (PoemSession)sessions.elementAt(index);
  }

  void beginExclusiveLock() {
    // FIXME yuk

    if (PoemThread.inSession())
      lock.readUnlock();

    try {
      lock.writeLock();
    }
    catch (InterruptedException e) {
      throw new InterruptedPoemException(e);
    }
  }

  void endExclusiveLock() {
    lock.writeUnlock();

    // FIXME yuk, see above

    if (PoemThread.inSession())
      try {
        lock.readLock();
      }
      catch (InterruptedException e) {
        throw new InterruptedPoemException(e);
      }
  }

  // 
  // ---------------
  //  Starting them
  // ---------------
  // 

  private void perform(AccessToken accessToken, final PoemTask task,
                       boolean committedSession) throws PoemException {
    try {
      lock.readLock();
    }
    catch (InterruptedException e) {
      throw new InterruptedPoemException(e);
    }

    try {
      final PoemSession session = committedSession ? null : openSession();
      PoemThread.inSession(new PoemTask() {
                             public void run() throws PoemException {
                               try {
                                 task.run();
                                 if (session != null)
                                   session.close(true);
                               }
                               catch (PoemException e) {
                                 if (session != null)
                                   session.close(false);
                                 throw e;
                               }
                               catch (RuntimeException e) {
                                 if (session != null)
                                   session.close(false);
                                 throw e;
                               }
                             }
                           },
                           accessToken,
                           session);
    }
    finally {
      lock.readUnlock();
    }
  }

  /**
   * Perform a task with the database.  Every access to a POEM database must be
   * made in the context of a `session' established using this method (note
   * that Melati programmers don't have to worry about this, because the
   * <TT>MelatiServlet</TT> will have done this by the time they get control).
   *
   * @param accessToken         A token determining the <TT>Capability</TT>s
   *                            available to the task, which in turn determine
   *                            what data it can attempt to read and write
   *                            without triggering an
   *                            <TT>AccessPoemException</TT>.  Note that a
   *                            <TT>User</TT> can be an <TT>AccessToken</TT>.
   *
   * @param task                What to do: its <TT>run()</TT> is invoked, in
   *                            the current Java thread; until <TT>run()</TT>
   *                            returns, all POEM accesses made by the thread
   *                            are taken to be performed with the capabilities
   *                            given by <TT>accessToken</TT>, and in a private
   *                            transaction.  No changes made to the database
   *                            by other sessions will be visible to it (in the
   *                            sense that once it has seen a particular
   *                            version of a record, it will always
   *                            subsequently see the same one), and its own
   *                            changes will not be made permanent until it
   *                            completes successfully or performs an explicit
   *                            <TT>PoemThread.commit()</TT>.  If it terminates
   *                            with an exception or issues a
   *                            <TT>PoemThread.rollback()</TT> its changes will
   *                            be lost.  (The task is allowed to continue
   *                            after either a <TT>commit()</TT> or a
   *                            <TT>rollback()</TT>.)
   *
   * @see PoemThread
   * @see PoemThread#commit
   * @see PoemThread#rollback
   * @see User
   */

  public void inSession(AccessToken accessToken, final PoemTask task) {
    perform(accessToken, task, false);
  }

  /**
   * Perform a task with the database, but not in an insulated session.  The
   * effect is the same as <TT>inSession</TT>, except that the task will see
   * changes to the database made by other sessions as they are committed, and
   * it is not allowed to make any changes of its own.  (If it tries, it will
   * currently trigger a <TT>NullPointerException</TT>---FIXME!)  Not
   * recommended.
   *
   * @see #inSession
   */

  public void inCommittedSession(AccessToken accessToken, final PoemTask task) {
    perform(accessToken, task, true);
  }

  // 
  // ==================
  //  Accessing tables
  // ==================
  // 

  /**
   * The table with a given name.
   *
   * @param name        The name of the table to return, as in the RDBMS
   *                    database.  It's case-sensitive, and some RDBMSs such as
   *                    Postgres 6.4.2 (and perhaps other versions) treat upper
   *                    case letters in identifiers inconsistently, so this is
   *                    likely to be a simple lower-case name.
   *
   * @exception NoSuchTablePoemException
   *             if no table with the given name exists in the RDBMS
   */

  public final Table getTable(String name) throws NoSuchTablePoemException {
    Table table = (Table)tablesByName.get(name);
    if (table == null) throw new NoSuchTablePoemException(this, name);
    return table;
  }

  /**
   * All the tables in the database.
   *
   * @return an <TT>Enumeration</TT> of <TT>Table</TT>s, in no particular
   *         order.  FIXME it ought to be display order.
   */

  public final Enumeration tables() {
    return tables.elements();
  }

  public final Enumeration getDisplayTables() {
    if (displayTables == null) {
      Enumeration tableIDs = getTableInfoTable().troidSelection(
          null /* "displayable" */, "displayorder, name", false, null);

      Vector them = new Vector();
      while (tableIDs.hasMoreElements()) {
        Table table =
            tableWithTableInfoID(((Integer)tableIDs.nextElement()).intValue());
        if (table != null)
          them.addElement(table);
      }

      displayTables = new Table[them.size()];
      them.copyInto(displayTables);
      this.displayTables = displayTables;
    }

    return new ArrayEnumeration(displayTables);
  }

  /**
   * The table with a given ID in the <TT>tableinfo</TT> table, or
   * <TT>null</TT>.
   *
   * @see #getTableInfoTable
   */

  final Table tableWithTableInfoID(int tableInfoID) {
    for (Enumeration t = tables.elements(); t.hasMoreElements();) {
      Table table = (Table)t.nextElement();
      Integer id = table.tableInfoID();
      if (id != null && id.intValue() == tableInfoID)
        return table;
    }

    return null;
  }

  final Column columnWithColumnInfoID(int columnInfoID) {
    for (Enumeration t = tables.elements(); t.hasMoreElements();) {
      Column column =
          ((Table)t.nextElement()).columnWithColumnInfoID(columnInfoID);
      if (column != null)
        return column;
    }

    return null;
  }

  /**
   * The metadata table with information about all tables in the database.
   */

  public abstract TableInfoTable getTableInfoTable();

  /**
   * The metadata table with information about all columns in all tables in the
   * database.
   */

  public abstract ColumnInfoTable getColumnInfoTable();

  /**
   * The table of capabilities (required for reading and/or writing records)
   * defined for the database.  Users acquire capabilities in virtue of being
   * members of groups.
   *
   * @see Persistent#getCanRead
   * @see Persistent#getCanWrite
   * @see Table#getDefaultCanRead
   * @see Table#getDefaultCanWrite
   * @see User
   * @see #getUserTable
   * @see Group
   * @see #getGroupTable
   */

  public abstract CapabilityTable getCapabilityTable();

  /**
   * The table of known users of the database.
   */

  public abstract UserTable getUserTable();

  /**
   * The table of defined user groups for the database.
   */

  public abstract GroupTable getGroupTable();

  /**
   * The table containing group-membership records.  A user is a member of a
   * group iff there is a record in this table to say so.
   */

  public abstract GroupMembershipTable getGroupMembershipTable();

  /**
   * The table containing group-capability records.  A group has a certain
   * capability iff there is a record in this table to say so.
   */

  public abstract GroupCapabilityTable getGroupCapabilityTable();

  // 
  // ========================
  //  Running arbitrary SQL 
  // ========================
  // 

  /**
   * Run an arbitrary SQL query against the database.  This is a low-level
   * <TT>java.sql.Statement.executeQuery</TT>, intended for fiddly queries for
   * which the higher-level methods are too clunky or inflexible.  <B>Note</B>
   * that it bypasses the access control mechanism!
   *
   * @see Table#selection()
   * @see Table#selection(java.lang.String)
   * @see Column#selectionWhereEq(java.lang.Object)
   */

  public ResultSet sqlQuery(String sql) throws SQLPoemException {
    PoemSession session = PoemThread.session();
    session.writeDown();
    try {
      ResultSet rs =
          session.getConnection().createStatement().executeQuery(sql);
      if (logSQL)
        log(new SQLLogEvent(sql));
      return rs;
    }
    catch (SQLException e) {
      throw new ExecutingSQLPoemException(sql, e);
    }
  }

  /**
   * Run an arbitrary SQL update against the database.  This is a low-level
   * <TT>java.sql.Statement.executeUpdate</TT>, intended for fiddly updates for
   * which the higher-level methods are too clunky or inflexible.  <B>Note</B>
   * that it bypasses the access control mechanism.  Furthermore, the cache
   * will be left in out of sync with the database and must be cleared out
   * (explicitly, manually) after the currently session has been committed or
   * completed.
   *
   * @see Table#selection()
   * @see Table#selection(java.lang.String)
   * @see Column#selectionWhereEq(java.lang.Object)
   * @see #uncacheContents
   */

  public int sqlUpdate(String sql) throws SQLPoemException {
    // FIXME this relies on the one-thread-per-session thing, else needs more
    // syncing

    PoemSession session = PoemThread.session();
    session.writeDown();

    try {
      int n = session.getConnection().createStatement().executeUpdate(sql);
      if (logSQL)
        log(new SQLLogEvent(sql));
      return n;
    }
    catch (SQLException e) {
      throw new ExecutingSQLPoemException(sql, e);
    }
  }

  // 
  // =======
  //  Users
  // =======
  // 

  private final PoemFloatingVersionedObject userCapabilities =
      new PoemFloatingVersionedObject(_this) {
        protected Version backingVersion(Session session) {
          return new VersionHashtable();
        }
      };

  private boolean dbGivesCapability(User user, Capability capability) {
    String sql = 
        "SELECT count(*) FROM groupmembership " +
        "WHERE \"user\" = " + user.troid() + " AND " +
        "EXISTS (" +
          "SELECT \"group\", capability FROM groupcapability " +
          "WHERE groupcapability.\"group\" = groupmembership.\"group\" AND " +
                "capability = " + capability.troid() + ")";

    try {
      ResultSet rs = sqlQuery(sql);
      rs.next();
      return rs.getInt(1) > 0;
    }
    catch (SQLPoemException e) {
      throw new UnexpectedExceptionPoemException(e);
    }
    catch (SQLException e) {
      throw new SQLSeriousPoemException(e, sql);
    }
  }

  boolean hasCapability(User user, Capability capability) {

    // `versionForReading' is right here rather than `versionForWriting',
    // because as soon as there is any question of things being different in
    // this session, `invalidateVersion' is called (below)

    Hashtable caps =
        (Hashtable)userCapabilities.versionForReading(PoemThread.session());

    Long pair = new Long(
        (user.troid().longValue() << 32) | (capability.troid().longValue()));
    Boolean known = (Boolean)caps.get(pair);

    if (known != null)
      return known.booleanValue();
    else {
      boolean does = dbGivesCapability(user, capability);
      caps.put(pair, does ? Boolean.TRUE : Boolean.FALSE);
      return does;
    }
  }

  public AccessToken guestAccessToken() {
    return getUserTable().guestUser();
  }

  public Capability administerCapability() {
    return getCapabilityTable().administer();
  }

  // 
  // ==========
  //  Cacheing
  // ==========
  // 

  /**
   * Trim POEM's cache to a given size.
   *
   * @param maxSize     The data for all but this many records per table will
   *                    be dropped from POEM's cache, on a least-recently-used
   *                    basis, with the exception of private copies made by
   *                    currently running sessions of records which they or
   *                    another session have changed will remain in memory.
   */

  public void trimCache(int maxSize) {
    for (Enumeration t = tables.elements(); t.hasMoreElements();)
      ((Table)t.nextElement()).trimCache(maxSize);
  }

  /**
   * Dump all the contents of the cache.  Actually only the `committed'
   * versions of the records stored in the cache are dropped, which means that
   * some private copies made by currently running sessions of records which
   * they or another session have changed may remain in memory.
   */

  public void uncacheContents() {
    for (int t = 0; t < tables.size(); ++t)
      ((Table)tables.elementAt(t)).uncacheContents();
  }

  // 
  // ===========
  //  Utilities
  // ===========
  // 

  public Enumeration referencesTo(final Persistent object) {
    return new FlattenedEnumeration(
        new MappedEnumeration(tables.elements()) {
          public Object mapped(Object table) {
            return ((Table)table).referencesTo(object);
          }
        });
  }

  /**
   * Print some diagnostic information about the contents and consistency of
   * POEM's cache to stderr.
   */

  public void dumpCacheAnalysis() {
    for (Enumeration t = tables.elements(); t.hasMoreElements();)
      ((Table)t.nextElement()).dumpCacheAnalysis();
  }

  /**
   * Print information about the structure of the database to stdout.
   */

  public void dump() {
    for (int t = 0; t < tables.size(); ++t) {
      System.out.println();
      ((Table)tables.elementAt(t)).dump();
    }

    System.err.println("there are " + sessions.size() + " sessions " +
                       "of which " + freeSessions.size() + " are free");
  }

  // 
  // =========================
  //  Database-specific stuff
  // =========================
  // 

  /**
   * Quote a name for use as an identifier in an SQL statement.  FIXME this is
   * DBMS-specific and we need a <TT>DatabasePecularities</TT> class to
   * which this is delegated.
   *
   * @exception InvalidNamePoemException
   *             if the name simply cannot be used with the DBMS (e.g. FIXME
   *             Postgres will have to throw this if there are any upper case
   *             letters)
   */

  public void appendQuotedName(StringBuffer buffer, String name)
      throws InvalidNamePoemException {
    StringUtils.appendQuoted(buffer, name/*.toLowerCase()*/, '"');
  }

  /**
   * Quote a name for use as an identifier in an SQL statement.  FIXME this is
   * DBMS-specific and we need a <TT>DatabasePecularities</TT> class to
   * which this is delegated.
   *
   * @exception InvalidNamePoemException
   *             if the name simply cannot be used with the DBMS (e.g. FIXME
   *             Postgres will have to throw this if there are any upper case
   *             letters)
   */

  public final String quotedName(String name) throws InvalidNamePoemException {
    StringBuffer b = new StringBuffer();
    appendQuotedName(b, name);
    return b.toString();
  }

  private PoemType unsupported(String sqlTypeName, ResultSet md)
      throws UnsupportedTypePoemException {
    UnsupportedTypePoemException e;
    try {
      e = new UnsupportedTypePoemException(
          md.getString("TABLE_NAME"), md.getString("COLUMN_NAME"),
          md.getShort("DATA_TYPE"), sqlTypeName,
          md.getString("TYPE_NAME"));
    }
    catch (SQLException ee) {
      throw new UnsupportedTypePoemException(sqlTypeName);
    }

    throw e;
  }

  /**
   * The simplest POEM type corresponding to a JDBC description from the
   * database.  FIXME this is meant to be customised per-database, and needs to
   * be delegated to a <TT>DatabasePecularities</TT> class.
   */

  public PoemType defaultPoemTypeOfColumnMetaData(ResultSet md)
      throws SQLException {
    int typeCode = md.getShort("DATA_TYPE");
    boolean nullable =
        md.getInt("NULLABLE") == DatabaseMetaData.columnNullable;
    int width = md.getInt("COLUMN_SIZE");
    switch (typeCode) {
      case Types.BIT            : return new BooleanPoemType(nullable);
      case Types.TINYINT        : return unsupported("TINYINT", md);
      case Types.SMALLINT       : return unsupported("SMALLINT", md);
      case Types.INTEGER        : return new IntegerPoemType(nullable);
      case Types.BIGINT         : return unsupported("BIGINT", md);

      case Types.FLOAT          : return unsupported("FLOAT", md);
      case Types.REAL           : return new DoublePoemType(nullable);
      case Types.DOUBLE         : return new DoublePoemType(nullable);

      case Types.NUMERIC        : return unsupported("NUMERIC", md);
      case Types.DECIMAL        : return unsupported("DECIMAL", md);

      case Types.CHAR           : return unsupported("CHAR", md);
      case Types.VARCHAR        : return new StringPoemType(nullable, width);
      case Types.LONGVARCHAR    : return new StringPoemType(nullable, width);

      case Types.DATE           : return unsupported("DATE", md);
      case Types.TIME           : return unsupported("TIME", md);
      case Types.TIMESTAMP      : return unsupported("TIMESTAMP", md);

      case Types.BINARY         : return unsupported("BINARY", md);
      case Types.VARBINARY      : return unsupported("VARBINARY", md);
      case Types.LONGVARBINARY  : return unsupported("LONGVARBINARY", md);

      case Types.NULL           : return unsupported("NULL", md);

      case Types.OTHER          : return unsupported("OTHER", md);

      default: return unsupported("<code not in Types.java!>", md);
    }
  }

  // 
  // =====================
  //  Technical utilities
  // =====================
  // 

  /**
   * <TT>quotedName</TT>, but don't expect anything to go wrong (cheesy or
   * what).
   *
   * @see #quotedName
   */

  String _quotedName(String name) {
    try {
      return quotedName(name);
    }
    catch (InvalidNamePoemException e) {
      throw new UnexpectedExceptionPoemException(e);
    }
  }

  Connection getCommittedConnection() {
    return committedConnection;
  }

  public boolean logSQL = false;

  void log(PoemLogEvent e) {
    System.err.println("---\n" + e.toString());
  }

  void beginStructuralModification() {
    beginExclusiveLock();
  }

  void endStructuralModification() {
    for (int t = 0; t < tables.size(); ++t)
      ((Table)tables.elementAt(t)).uncacheContents();
    endExclusiveLock();
  }
}
