package org.melati.poem;

import org.apache.java.lang.Lock;
import java.sql.*;
import java.util.*;
import org.melati.util.*;

abstract public class Database {

  private final int sessionsMax = 1;

  private Vector sessions = null; // FIXME make this more explicit
  private Vector freeSessions = null;

  private Connection committedConnection;
  private final Lock structuralModificationLock = new Lock();

  private Vector tables = new Vector();
  private Hashtable tablesByName = new Hashtable();

  // 
  // ==========
  //  Cacheing
  // ==========
  // 

  public void dumpCacheAnalysis() {
    for (Enumeration t = tables.elements(); t.hasMoreElements();)
      ((Table)t.nextElement()).dumpCacheAnalysis();
  }

  public void uncacheContents() {
    for (Enumeration t = tables.elements(); t.hasMoreElements();)
      ((Table)t.nextElement()).uncacheContents();
  }

  public void trimCache(int maxSize) {
    for (Enumeration t = tables.elements(); t.hasMoreElements();)
      ((Table)t.nextElement()).trimCache(maxSize);
  }

  protected synchronized void defineTable(Table table)
      throws DuplicateTableNamePoemException {
    if (table.getDatabase() != this)
      throw new TableInUsePoemException(this, table);

    if (tablesByName.get(table.getName()) != null)
      throw new DuplicateTableNamePoemException(this, table.getName());

    tables.addElement(table);
    tablesByName.put(table.getName(), table);
  }

  public void connect(String url, String username, String password)
      throws PoemException {

    if (committedConnection != null)
      throw new ReconnectionPoemException();

    try {
      committedConnection =
          DriverManager.getConnection(url, username, password);
      sessions = new Vector();
      for (int s = sessionsMax - 1; s >= 0; --s)
        sessions.addElement(
            new Session(this,
                        DriverManager.getConnection(url, username, password),
                        s));
      freeSessions = (Vector)sessions.clone();
    }
    catch (SQLException e) {
      throw new ConnectionFailurePoemException(e);
    }

    try {
      // Bootstrap: set up the tableinfo and fieldinfo tables

      DatabaseMetaData m = committedConnection.getMetaData();
      getTableInfoTable().unifyWithDB(
          m.getColumns(null, null, getTableInfoTable().getName(), null));
      getColumnInfoTable().unifyWithDB(
          m.getColumns(null, null, getColumnInfoTable().getName(), null));
      getCapabilityTable().unifyWithDB(
          m.getColumns(null, null, getCapabilityTable().getName(), null));
    }
    catch (SQLException e) {
      throw new DatabaseMetaDataFailurePoemException(e);
    }

    final Database _this = this;
    inSession(AccessToken.root,
              new Runnable() {
                public void run() {
                  try {
                    _this.unifyWithDB();
                  }
                  catch (Exception e) {
                    throw new UnificationPoemException(e);
                  }
                }
              });
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

    for (Enumeration t = tables.elements(); t.hasMoreElements();) {
      Table table = (Table)t.nextElement();
      // bit yukky using getColumns ...
      ResultSet colDescs = columnsMetadata(m, table.getName());
      if (!colDescs.next())
        table.unifyWithDB(colDescs);
    }
  }

  public int sessionsMax() {
    return sessionsMax;
  }

  public void dump() {
    for (int t = 0; t < tables.size(); ++t) {
      System.out.println();
      ((Table)tables.elementAt(t)).dump();
    }

    System.err.println("there are " + sessions.size() + " sessions " +
                       "of which " + freeSessions.size() + " are free");
  }

  protected ResultSet columnsMetadata(DatabaseMetaData m, String tableName)
      throws SQLException {
    return m.getColumns(null, null, tableName, null);
  }

  public final Table getTable(String name) throws NoSuchTablePoemException {
    Table table = (Table)tablesByName.get(name);
    if (table == null) throw new NoSuchTablePoemException(this, name);
    return table;
  }

  public final Enumeration tables() {
    return tables.elements();
  }

  final Table tableWithTableInfoID(int tableInfoID) {
    synchronized (tables) {
      for (Enumeration t = tables.elements(); t.hasMoreElements();) {
        Table table = (Table)t.nextElement();
        Integer id = table.tableInfoID();
        if (id != null && id.intValue() == tableInfoID)
          return table;
      }

      return null;
    }
  }

  Session openSession() {
    synchronized (freeSessions) {
      if (freeSessions.size() == 0)
        throw new NoMoreSessionsException();
      Session session = (Session)freeSessions.lastElement();
      freeSessions.setSize(freeSessions.size() - 1);
      return session;
    }
  }

  void notifyClosed(Session session) {
    freeSessions.addElement(session);
  }

  Session session(int index) {
    return (Session)sessions.elementAt(index);
  }

  public void inSession(AccessToken accessToken, final Runnable task,
                        boolean committedSession) {
    try {
      structuralModificationLock.readLock();
    }
    catch (InterruptedException e) {
      throw new InterruptedPoemException(e);
    }

    try {
      final Session session = committedSession ? null : openSession();
      PoemThread.inSession(new Runnable() {
                             public void run() {
                               try {
                                 task.run();
                                 if (session != null)
                                   session.commit();
                               }
                               catch (RuntimeException e) {
                                 if (session != null)
                                   session.close(false);
                                 throw e;
                               }
                               finally {
                                 if (session != null)
                                   session.close(true);
                               }
                             }
                           },
                           accessToken,
                           session);
    }
    finally {
      structuralModificationLock.readUnlock();
    }
  }

  public void inSession(AccessToken accessToken, final Runnable task) {
    inSession(accessToken, task, false);
  }

  public void inCommittedSession(AccessToken accessToken, final Runnable task) {
    inSession(accessToken, task, true);
  }

  Connection getCommittedConnection() {
    return committedConnection;
  }

  public ResultSet sqlQuery(String sql) throws SQLPoemException {
    Session session = PoemThread.session();
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

  public int sqlUpdate(String sql) throws SQLPoemException {
    Session session = PoemThread.session();
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

  public Enumeration referencesTo(final Persistent object) {
    return new FlattenedEnumeration(
        new MappedEnumeration(tables.elements()) {
          public Object mapped(Object table) {
            return ((Table)table).referencesTo(object);
          }
        });
  }

  public void appendQuotedName(StringBuffer buffer, String name)
      throws InvalidNamePoemException {
    StringUtils.appendQuoted(buffer, name/*.toLowerCase()*/, '"');
  }

  public final String quotedName(String name) throws InvalidNamePoemException {
    StringBuffer b = new StringBuffer();
    appendQuotedName(b, name);
    return b.toString();
  }

  String _quotedName(String name) {
    try {
      return quotedName(name);
    }
    catch (InvalidNamePoemException e) {
      throw new UnexpectedExceptionPoemException(e);
    }
  }

  public boolean logSQL = false;

  void log(PoemLogEvent e) {
    System.err.println("---\n" + e.toString());
  }

  void beginExclusiveLock() {
    if (PoemThread.inSession())
      structuralModificationLock.readUnlock();

    // FIXME yuk, see above

    try {
      structuralModificationLock.writeLock();
    }
    catch (InterruptedException e) {
      throw new InterruptedPoemException(e);
    }
  }

  void endExclusiveLock() {
    structuralModificationLock.writeUnlock();

    // FIXME yuk, see above

    if (PoemThread.inSession())
      try {
        structuralModificationLock.readLock();
      }
      catch (InterruptedException e) {
        throw new InterruptedPoemException(e);
      }
  }

  void beginStructuralModification() {
    beginExclusiveLock();
  }

  void endStructuralModification() {
    for (int t = 0; t < tables.size(); ++t)
      ((Table)tables.elementAt(t)).uncacheContents();
    endExclusiveLock();
  }

/*
  synchronized void addTable(Table table, final String tableName)
      throws InvalidNamePoemException, DuplicateTableNamePoemException {

    if (tablesByName.get(tableName) != null)
      throw new DuplicateTableNamePoemException(this, tableName);

    table.isNonexistent = true;
    grab(table, tableName);
    if (tableInfoTable != null) {
      tableInfoTable.create(
        new Initialiser() {
          public void init(Persistent p) {
            TableInfo i = (TableInfo)p;
            i.setName(tableName);
          } 
        });
  }
*/

  /**
   * Don't call this!  Call <TT>Table.rename</TT> instead.
   */

/*
  void rename(Table table, String newName)
      throws CannotBeInSessionPoemException, DuplicateTableNamePoemException,
             InvalidNamePoemException {

    DatabaseMixingPoemException.check(this, table.getDatabase());

    String sql = "ALTER TABLE " + table.getQuotedName() +
                 " RENAME TO " + quotedName(newName);

    beginStructuralModification();
    try {
      if (tablesByName.get(newName) != null)
        throw new DuplicateTableNamePoemException(this, newName);

      try {
        committedSession().getConnection().createStatement().
            executeUpdate(sql);
      }
      catch (SQLException e) {
        throw new StructuralModificationFailedPoemException(sql, e);
      }

      synchronized (tablesByName) {
        tablesByName.remove(table.getName());
        tablesByName.put(newName, table);
      }
    }
    finally {
      endStructuralModification();
    }
  }
*/

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

  public abstract TableInfoTable getTableInfoTable();
  public abstract ColumnInfoTable getColumnInfoTable();
  public abstract CapabilityTable getCapabilityTable();
  public abstract UserTable getUserTable();
  public abstract GroupTable getGroupTable();
  public abstract GroupMembershipTable getGroupMembershipTable();

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
}
