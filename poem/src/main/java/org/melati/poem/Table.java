package org.melati.poem;

import org.melati.util.*;
import java.sql.*;
import java.util.*;

/**
 * A table in a POEM database.  This is the minimal set of methods available;
 * if the table is defined in the data structure definition under the name
 * <TT><I>foo</I></TT>, there will be an application-specialised
 * <TT>Table</TT> subclass, called <TT><I>Foo</I>Table</TT> (and available as
 * <TT>get<I>Foo</I>Table</TT> from the application-specialised
 * <TT>Database</TT> subclass) which has extra, typed methods for retrieving
 * the application-specialised objects in the table, and methods for accessing
 * the table's predefined <TT>Column</TT>s.
 */

public class Table {

  private Database database;
  private String name;
  private DefinitionSource definitionSource;

  private TableInfo info = null;

  private Vector columns = new Vector();
  private Vector normalColumns = null;
  private Hashtable columnsByName = new Hashtable();

  private Column troidColumn = null;
  private Column deletedColumn = null;
  private Column canReadColumn = null;
  private Column canWriteColumn = null;
  private Column displayColumn = null;

  private PoemFloatingVersionedObject allTroids = null;

  public Table(Database database, String name,
               DefinitionSource definitionSource) {
    this.database = database;
    this.name = name;
    this.definitionSource = definitionSource;
  }

  // 
  // ===========
  //  Accessors
  // ===========
  // 

  /**
   * The database to which the table is attached.
   */

  public final Database getDatabase() {
    return database;
  }

  /**
   * The table's programmatic name.  Identical with its name in the DSD (if the
   * table was defined there), in its <TT>tableinfo</TT> entry, and in the
   * RDBMS itself.
   */

  public final String getName() {
    return name;
  }

  /**
   * The descriptive name of the table.  POEM itself doesn't use this, but it's
   * available to applications and Melati's generic admin system as a default
   * label for the table and caption for its records.
   */

  public final String getDisplayName() {
    return info.getDisplayname();
  }

  /**
   * The table's column with a given name.  If the table is defined in the DSD
   * under the name <TT><I>foo</I></TT>, there will be an
   * application-specialised <TT>Table</TT> subclass, called
   * <TT><I>Foo</I>Table</TT> (and available as <TT>get<I>Foo</I>Table</TT>
   * from the application-specialised <TT>Database</TT> subclass) which has
   * extra named methods for accessing the table's predefined <TT>Column</TT>s.
   *
   * @exception NoSuchColumnPoemException if there is no column with that name
   */

  public final Column getColumn(String name) throws NoSuchColumnPoemException {
    Column column = (Column)columnsByName.get(name);
    if (column == null)
      throw new NoSuchColumnPoemException(this, name);
    else
      return column;
  }

  /**
   * All the table's `normal' columns.  Its troid column and any deleted-flag
   * column are omitted.
   *
   * @return an <TT>Enumeration</TT> of <TT>Column</TT>s
   * @see Column
   */

  public final Enumeration columns() {
    return normalColumns.elements();
  }

  final int getColumnsCount() {
    return columns.size();
  }

  /**
   * The table's troid column.  Every table in a POEM database must have a
   * troid (table row ID, or table-unique non-nullable integer primary key),
   * often but not necessarily called <TT>id</TT>, so that it can be
   * conveniently `named'.
   *
   * @see #getObject(java.lang.Integer)
   */

  public final Column troidColumn() {
    return troidColumn;
  }

  /**
   * The table's deleted-flag column, if any.  FIXME.
   */

  public final Column deletedColumn() {
    return deletedColumn;
  }

  /**
   * The table's primary display column, if any.  This is the column used to
   * represent records from the table concisely in reports or whatever.  It is
   * determined at initialisation time by examining the <TT>Column</TT>s
   * <TT>isPrimaryDisplay()</TT> flags.
   *
   * @return the table's display column, or <TT>null</TT> if it hasn't got one
   *
   * @see Column#isPrimaryDisplay()
   * @see ReferencePoemType#_stringOfValue(Object)
   */

  public final Column displayColumn() {
    return displayColumn == null ? troidColumn : displayColumn;
  }

  /**
   * The troid (<TT>id</TT>) of the table's entry in the <TT>tableinfo</TT>
   * table.  It will always have one (except during initialisation, which the
   * application programmer will never see).
   */

  final Integer tableInfoID() {
    return info == null ? null : info.troid();
  }

  // 
  // =========================
  //  Low-level DB operations
  // =========================
  // 

  // 
  // -----------
  //  Structure
  // -----------
  // 

  private void dbCreateTable() {
    StringBuffer sqb = new StringBuffer();
    sqb.append("CREATE TABLE " + _quotedName(name) + " (");
    for (int c = 0; c < columns.size(); ++c) {
      Column column = (Column)columns.elementAt(c);
      if (c != 0) sqb.append(", ");
      sqb.append(_quotedName(column.getName()) + " " +
                 column.getType().sqlDefinition());
    }

    sqb.append(")");
    String sql = sqb.toString();

    try {
      database.getCommittedConnection().createStatement().executeUpdate(sql);
      database.log(new StructuralModificationLogEvent(sql));
    }
    catch (SQLException e) {
      throw new StructuralModificationFailedPoemException(sql, e);
    }
  }

  private void dbAddColumn(Column column) {
    String sql = "ALTER TABLE " + _quotedName(name) +
                 " ADD COLUMN " + _quotedName(column.getName()) +
                 " " + column.getType().sqlDefinition();

    try {
      database.getCommittedConnection().createStatement().executeUpdate(sql);
      database.log(new StructuralModificationLogEvent(sql));
    }
    catch (SQLException e) {
      throw new StructuralModificationFailedPoemException(sql, e);
    }
  }

  // 
  // -------------------------------
  //  Standard `PreparedStatement's
  // -------------------------------
  // 

  private PreparedStatement simpleInsert(Connection connection) {
    StringBuffer sql = new StringBuffer();
    sql.append("INSERT INTO " + _quotedName(name) + " (");
    for (int c = 0; c < columns.size(); ++c) {
      if (c > 0) sql.append(", ");
      sql.append(_quotedName(((Column)columns.elementAt(c)).getName()));
    }
    sql.append(") VALUES (");
    for (int c = 0; c < columns.size(); ++c) {
      if (c > 0) sql.append(", ");
      sql.append("?");
    }

    sql.append(")");

    try {
      return connection.prepareStatement(sql.toString());
    }
    catch (SQLException e) {
      throw new SimplePrepareFailedPoemException(sql.toString(), e);
    }
  }

  private PreparedStatement simpleGet(Connection connection) {
    StringBuffer sql = new StringBuffer();
    sql.append("SELECT ");
    for (int c = 0; c < columns.size(); ++c) {
      if (c > 0) sql.append(", ");
      sql.append(_quotedName(((Column)columns.elementAt(c)).getName()));
    }
    sql.append(" FROM " + _quotedName(name) +
               " WHERE " + _quotedName(troidColumn.getName()) + " = ?");

    try {
      return connection.prepareStatement(sql.toString());
    }
    catch (SQLException e) {
      throw new SimplePrepareFailedPoemException(sql.toString(), e);
    }
  }

  private PreparedStatement simpleModify(Connection connection) {
    // FIXME synchronize this too
    StringBuffer sql = new StringBuffer();
    sql.append("UPDATE " + _quotedName(name) + " SET ");
    for (int c = 0; c < columns.size(); ++c) {
      if (c > 0) sql.append(", ");
      sql.append(_quotedName(((Column)columns.elementAt(c)).getName()));
      sql.append(" = ?");
    }
    sql.append(" WHERE " + _quotedName(troidColumn.getName()) + " = ?");

    try {
      return connection.prepareStatement(sql.toString());
    }
    catch (SQLException e) {
      throw new SimplePrepareFailedPoemException(sql.toString(), e);
    }
  }

  // 
  // -------------------------
  //  Session-specific things
  // -------------------------
  // 

  private final Table _this = this;

  private class SessionStuff {
    PreparedStatement insert, modify, get;
    SessionStuff(Connection connection) {
      insert = _this.simpleInsert(connection);
      modify = _this.simpleModify(connection);
      get = _this.simpleGet(connection);
    }
  }

  private CachedIndexFactory sessionStuffs = new CachedIndexFactory() {
    public Object reallyGet(int index) {
      return new SessionStuff(database.session(index).getConnection());
    }
  };

  private SessionStuff committedSessionStuff = null;

  private synchronized SessionStuff getCommittedSessionStuff() {
    if (committedSessionStuff == null)
      committedSessionStuff =
          new SessionStuff(database.getCommittedConnection());
    return committedSessionStuff;
  }

  // 
  // --------------------
  //  Loading and saving
  // --------------------
  // 

  private Data dbData(PreparedStatement select, Integer troid) {
    try {
      synchronized (select) {
        select.setInt(1, troid.intValue());
        ResultSet rs = select.executeQuery();
        if (database.logSQL)
          database.log(new SQLLogEvent(select.toString()));

        if (!rs.next())
          return null;

        Data data = newData();
        for (int c = 0; c < columns.size(); ++c)
          ((Column)columns.elementAt(c)).load(rs, c + 1, data);

        if (rs.next())
          throw new DuplicateTroidPoemException(this, troid);

        return data;
      }
    }
    catch (SQLException e) {
      throw new SimpleRetrievalFailedPoemException(e);
    }
    catch (ParsingPoemException e) {
      throw new UnexpectedParsingPoemException(e);
    }
    catch (ValidationPoemException e) {
      throw new UnexpectedValidationPoemException(e);
    }
  }

  Data dbData(PoemSession session, Integer troid) {
    return dbData(session == null ?
                      getCommittedSessionStuff().get :
                      ((SessionStuff)sessionStuffs.get(session.index())).get,
                  troid);
  }

  private void modify(PoemSession session, Integer troid, Data data) {
    PreparedStatement modify =
        ((SessionStuff)sessionStuffs.get(session.index())).modify;
    synchronized (modify) {
      for (int c = 0; c < columns.size(); ++c)
        ((Column)columns.elementAt(c)).save(data, modify, c + 1);
      try {
        modify.setInt(columns.size() + 1, troid.intValue());
        modify.executeUpdate();
      }
      catch (SQLException e) {
        throw new SQLSeriousPoemException(e);
      }
      if (database.logSQL)
        database.log(new SQLLogEvent(modify.toString()));
    }
  }

  private void insert(PoemSession session, Integer troid, Data data) {
    PreparedStatement insert =
        ((SessionStuff)sessionStuffs.get(session.index())).insert;
    synchronized (insert) {
      for (int c = 0; c < columns.size(); ++c)
        ((Column)columns.elementAt(c)).save(data, insert, c + 1);
      try {
        insert.executeUpdate();
      }
      catch (SQLException e) {
        throw new PreparedSQLSeriousPoemException(insert, e);
      }
      if (database.logSQL)
        database.log(new SQLLogEvent(insert.toString()));
    }
  }

  void delete(Integer troid, PoemSession session) {
    String sql =
        "DELETE FROM " + _quotedName(name) +
        " WHERE " + _quotedName(troidColumn.getName()) + " = " +
        troid.toString();

    try {
      Connection connection;
      if (session == null)
        connection = getDatabase().getCommittedConnection();
      else {
        session.writeDown();
        connection = session.getConnection();
      }

      connection.createStatement().executeUpdate(sql);
      if (database.logSQL)
        database.log(new SQLLogEvent(sql));
    }
    catch (SQLException e) {
      throw new ExecutingSQLPoemException(sql, e);
    }
  }

  void writeDown(PoemSession session, Integer troid, Data data) {
    troidColumn.setIdent(data, troid);

    // no race, provided that the one-thread-per-session parity is maintained

    if (data.exists)
      modify(session, troid, data);
    else {
      insert(session, troid, data);
      data.exists = true;
    }

    data.dirty = false;
  }

  // 
  // ============
  //  Operations
  // ============
  // 

  // 
  // ----------
  //  Cacheing
  // ----------
  // 

  private Cache cache = new Cache();

  void uncacheContents() {
    cache.uncacheContents();
  }

  void trimCache(int maxSize) {
    cache.trim(maxSize);
  }

  CachedVersionedRow versionedRow(Integer troid) {
    synchronized (cache) {
      CachedVersionedRow is = (CachedVersionedRow)cache.get(troid);
      if (is == null) {
        is = new CachedVersionedRow(this, troid);
        cache.put(is);
      }
      return is;
    }
  }

  // 
  // ----------
  //  Fetching
  // ----------
  // 

  /**
   * The object from the table with a given troid.
   *
   * @param troid       Every record (object) in a POEM database must have a
   *                    troid (table row ID, or table-unique non-nullable
   *                    integer primary key), often but not necessarily called
   *                    <TT>id</TT>, so that it can be conveniently `named' for
   *                    retrieval by this method.
   *
   * @return A <TT>Persistent</TT> representing the record with the given troid;
   *         or, if the table was defined in the DSD under the name
   *         <TT><I>foo</I></TT>, an application-specialised subclass
   *         <TT><I>Foo</I></TT> of <TT>Persistent</TT>.  In that case, there
   *         will also be an application-specialised <TT>Table</TT> subclass,
   *         called <TT><I>Foo</I>Table</TT> (and available as
   *         <TT>get<I>Foo</I>Table</TT> from the application-specialised
   *         <TT>Database</TT> subclass), which has a matching method
   *         <TT>get<I>Foo</I>Object</TT> for obtaining the specialised object
   *         under its own type.  Note that no access checks are done at this
   *         stage: you may not be able to do anything with the object handle
   *         returned from this method without provoking a
   *         <TT>PoemAccessException</TT>.
   *
   * @exception NoSuchRowPoemException
   *                if there is no row in the table with the given troid
   *
   * @see Persistent#getTroid()
   */

  public Persistent getObject(Integer troid) throws NoSuchRowPoemException {
    Persistent persistent = newPersistent();
    persistent.init(versionedRow(troid));

    try {
      persistent.dataUnchecked(PoemThread.session());
    }
    catch (RowDisappearedPoemException e) {
      throw new NoSuchRowPoemException(this, troid);
    }

    return persistent;
  }

  /**
   * The object from the table with a given troid.  See previous.
   *
   * @see #getObject(java.lang.Integer)
   */

  public Persistent getObject(int troid) throws NoSuchRowPoemException {
    return getObject(new Integer(troid));
  }

  // 
  // -----------
  //  Searching
  // -----------
  // 

  private ResultSet selectionResultSet(
      String whereClause, boolean includeDeleted, PoemSession session)
          throws SQLPoemException {
    if (deletedColumn != null && !includeDeleted)
      whereClause = (whereClause == null ? "" : whereClause + " AND ") +
                    "NOT " + deletedColumn.getName();
      
    String sql =
        "SELECT " + _quotedName(troidColumn.getName()) +
        " FROM " + _quotedName(name) +
        (whereClause == null ? "" : " WHERE " + whereClause);

    try {
      session.writeDown();
      ResultSet rs =
          session.getConnection().createStatement().executeQuery(sql);
      if (database.logSQL)
        database.log(new SQLLogEvent(sql));
      return rs;
    }
    catch (SQLException e) {
      throw new ExecutingSQLPoemException(sql, e);
    }
  }

  private Enumeration troidSelection(
      String whereClause, boolean includeDeleted, PoemSession session) {
    ResultSet them = selectionResultSet(whereClause, includeDeleted, session);
    return
        new ResultSetEnumeration(them) {
          public Object mapped(ResultSet rs) throws SQLException {
            return new Integer(rs.getInt(1));
          }
        };
  }

  protected void rememberAllTroids() {
    allTroids = new PoemFloatingVersionedObject(getDatabase()) {
      protected Version backingVersion(Session session) {
        VersionVector store = new VersionVector();
        for (Enumeration them =
                 troidSelection(null, false, (PoemSession)session);
             them.hasMoreElements();)
          store.addElement(them.nextElement());
        return store;
      }
    };
  }

  /**
   * A <TT>SELECT</TT>ion of troids of objects from the table meeting given
   * criteria.
   *
   * @return an <TT>Enumeration</TT> of <TT>Integer</TT>s, which can be mapped
   *         onto <TT>Persistent</TT> objects using <TT>getObject</TT>;
   *         or you can just use <TT>selection</TT>
   *
   * @see #select(java.lang.String, boolean)
   * @see #getObject(java.lang.Integer)
   * @see #selection(java.lang.String, boolean)
   */

  final Enumeration troidSelection(String whereClause, boolean includeDeleted)
      throws SQLPoemException {
    PoemSession session = PoemThread.session();
    PoemFloatingVersionedObject allTroids = this.allTroids;
    if (allTroids != null && whereClause == null && !includeDeleted)
      return ((Vector)allTroids.versionForReading(session)).elements();
    else
      return troidSelection(whereClause, includeDeleted, session);
  }

  /**
   * All the objects in the table.
   *
   * @return An <TT>Enumeration</TT> of <TT>Persistent</TT>s, or, if the table
   *         was defined in the DSD under the name <TT><I>foo</I></TT>, of
   *         application-specialised subclasses <TT><I>Foo</I></TT>.  Note
   *         that no access checks are done at this stage: you may not be able
   *         to do anything with some of the object handles in the enumeration
   *         without provoking a <TT>PoemAccessException</TT>.  If the table
   *         has a <TT>deleted</TT> column, the objects flagged as deleted will
   *         be passed over.
   */

  public final Enumeration selection() throws SQLPoemException {
    return selection(null, false);
  }

  /**
   * A <TT>SELECT</TT>ion of objects from the table meeting given criteria.
   * This is one way to run a search against the database and return the
   * results as a series of typed POEM objects.
   *
   * @param whereClause         SQL <TT>SELECT</TT>ion criteria for the search:
   *                            the part that should appear after the
   *                            <TT>WHERE</TT> keyword
   *
   * @return An <TT>Enumeration</TT> of <TT>Persistent</TT>s, or, if the table
   *         was defined in the DSD under the name <TT><I>foo</I></TT>, of
   *         application-specialised subclasses <TT><I>Foo</I></TT>.  Note
   *         that no access checks are done at this stage: you may not be able
   *         to do anything with some of the object handles in the enumeration
   *         without provoking a <TT>PoemAccessException</TT>.  If the table
   *         has a <TT>deleted</TT> column, the objects flagged as deleted will
   *         be passed over.
   *
   * @see Column#selectionWhereEq(java.lang.Object)
   */

  public final Enumeration selection(String whereClause)
      throws SQLPoemException {
    return selection(whereClause, false);
  }

  /**
   * A <TT>SELECT</TT>ion of objects from the table meeting given criteria,
   * possibly including those flagged as deleted.
   *
   * @param includeDeleted      whether to return objects flagged as deleted
   *                            (ignored if the table doesn't have a
   *                            <TT>deleted</TT> column)
   *
   * @see #selection(java.lang.String)
   */

  public Enumeration selection(String whereClause, boolean includeDeleted)
      throws SQLPoemException {
    return
        new MappedEnumeration(troidSelection(whereClause, includeDeleted)) {
          public Object mapped(Object troid) {
            return getObject((Integer)troid);
          }
        };
  }

  /**
   * All the objects in the table which refer to a given object.  If none of
   * the table's columns are reference columns, the <TT>Enumeration</TT>
   * returned will obviously be empty.  This is used by
   * <TT>Persistent.delete()</TT> to determine whether deleting an object would
   * destroy the integrity of any references.  It is not guaranteed to be
   * particularly quick to execute!
   *
   * @return an <TT>Enumeration</TT> of <TT>Persistent</TT>s
   */

  public Enumeration referencesTo(final Persistent object) {
    return new FlattenedEnumeration(
        new MappedEnumeration(columns.elements()) {
          public Object mapped(Object column) {
            return ((Column)column).referencesTo(object);
          }
        });
  }

  // 
  // ----------
  //  Creation
  // ----------
  // 

  private void validate(Data data) throws FieldContentsPoemException {
    for (int c = 0; c < columns.size(); ++c) {
      Column column = (Column)columns.elementAt(c);
      try {
        column.getType().assertValidIdent(column.getIdent(data));
      }
      catch (Exception e) {
        throw new FieldContentsPoemException(column, e);
      }
    }
  }

  private int nextTroid = -1;

  synchronized private Integer nextTroid() {
    if (nextTroid == -1)
      throw new PoemBugPoemException();
    return new Integer(nextTroid++);
  }

  final int troidBound() {
    if (nextTroid == -1)
      throw new PoemBugPoemException();
    return nextTroid;
  }

  // FIXME don't use the Data version 'cos it messes up triggers ...

  private Persistent create(Object initOrData)
      throws AccessPoemException, ValidationPoemException,
             InitialisationPoemException {

    SessionToken sessionToken = PoemThread.sessionToken();

    Capability canCreate = getCanCreate();
    if (canCreate != null &&
        !sessionToken.accessToken.givesCapability(canCreate))
      throw new CreationAccessPoemException(this, sessionToken.accessToken,
                                            canCreate);

    Integer troid = nextTroid();

    Data data;
    if (initOrData instanceof Data)
      data = (Data)initOrData;
    else
      data = newData();

    troidColumn.setIdent(data, troid);
    if (deletedColumn != null)
      deletedColumn.setIdent(data, Boolean.FALSE);
    data.exists = false;

    ConstructionVersionedRow dummyVersionedRow =
        new ConstructionVersionedRow(this, troid, data, sessionToken.session);
    Persistent object = newPersistent();
    object.initForConstruct(dummyVersionedRow, sessionToken.accessToken);

    if (initOrData instanceof Initialiser)
      // Let the user do their worst.
      ((Initialiser)initOrData).init(object);

    // Are the values they have put in legal, and is the result
    // something they could have created by writing into a record?

    try {
      validate(data);
      object.assertCanWrite(data, sessionToken.accessToken);
    }
    catch (Exception e) {
      throw new InitialisationPoemException(this, e);
    }

    // Plug it into the cache for later writedown

    CachedVersionedRow versionedRow = versionedRow(troid);
    versionedRow.setVersion(sessionToken.session, data);
    object.init(versionedRow);
    return object;
  }

  /**
   * Create a new object (record) in the table.  FIXME don't use this because
   * it bypasses any extra logic the programmer may have put on `set' methods.
   */

  Persistent create(Data data) 
      throws AccessPoemException, ValidationPoemException,
             InitialisationPoemException {
    return create((Object)data);
  }

  /**
   * Create a new object (record) in the table.
   *
   * @param initialiser         A piece of code for setting the new object's
   *                            initial values.  You'll probably want to define
   *                            it as an anonymous class.
   *
   * @return A <TT>Persistent</TT> representing the new object, or, if the
   *         table was defined in the DSD under the name <TT><I>foo</I></TT>,
   *         an application-specialised subclass <TT><I>Foo</I></TT> of
   *         <TT>Persistent</TT>.
   *
   * @exception CreationAccessPoemException
   *                if the calling thread's <TT>AccessToken</TT> doesn't allow
   *                you to create records in the table
   * @exception AccessPoemException
   *                if <TT>initialiser</TT> provokes one during its work (which
   *                is unlikely, since POEM's standard checks are disabled
   *                while it runs)
   * @exception ValidationPoemException
   *                if <TT>initialiser</TT> provokes one during its work
   * @exception InitialisationPoemException
   *                if the object is left by <TT>initialiser</TT> in a state in
   *                which not all of its fields have legal values, or in which
   *                the calling thread would not be allowed write access to the
   *                object under its <TT>AccessToken</TT>---<I>i.e.</I> you
   *                can't create objects you wouldn't be allowed to write to.
   *
   * @see Initialiser#init(org.melati.poem.Persistent)
   * @see PoemThread#accessToken()
   * @see #getCanCreate()
   */

  public Persistent create(Initialiser initialiser) 
      throws CreationAccessPoemException,
             AccessPoemException, ValidationPoemException,
             InitialisationPoemException {
    return create((Object)initialiser);
  }

  /**
   * A freshly minted <TT>Data</TT> object for the table.  These represent the
   * actual underlying field values of the objects in the table, but you don't
   * in general have to worry about them.  This method is overridden in
   * application-specialised <TT>Table</TT> subclasses derived from the Data
   * Structure Definition.
   *
   * @see User#_newData()
   */

  protected Data _newData() {
    return new Data();
  }

  final Data newData() {
    Data them = _newData();
    them.extras = new Object[getExtrasCount()];
    return them;
  }

  /**
   * A freshly minted <TT>Persistent</TT> object for the table.  You don't ever
   * have to call this and there is no point in doing so.  This method is
   * overridden in application-specialised <TT>Table</TT> subclasses derived
   * from the Data Structure Definition.
   */

  protected Persistent newPersistent() {
    return new Persistent();
  }

  /**
   * The number of `extra' (non-DSD-defined) columns in the table.
   */

  int getExtrasCount() {
    return extrasIndex;
  }

  // 
  // ----------------
  //  Access control
  // ----------------
  // 

  /**
   * The capability required for reading records from the table, unless
   * overridden in the record itself.  This simply comes from the table's
   * record in the <TT>tableinfo</TT> table.
   *
   * @see Persistent#getCanRead(org.melati.poem.Data)
   */

  public final Capability getDefaultCanRead() {
    return info.getDefaultcanread();
  }

  /**
   * The capability required for updating records in the table, unless
   * overridden in the record itself.  This simply comes from the table's
   * record in the <TT>tableinfo</TT> table.
   *
   * @see Persistent#getCanWrite(org.melati.poem.Data)
   */

  public final Capability getDefaultCanWrite() {
    return info.getDefaultcanwrite();
  }

  /**
   * The capability required for creating records in the table.  This simply
   * comes from the table's record in the <TT>tableinfo</TT> table.
   *
   * @see #create(org.melati.poem.Initialiser)
   */

  public final Capability getCanCreate() {
    return info == null ? null : info.getCancreate();
  }

  final Column canReadColumn() {
    return canReadColumn;
  }

  final Column canWriteColumn() {
    return canWriteColumn;
  }

  /**
   * Notify the table that one if its records is about to be changed in a
   * session.  You can (with care) use this to support cacheing of
   * frequently-used facts about the table's records.  For instance,
   * <TT>GroupMembershipTable</TT> and <TT>GroupCapabilityTable</TT> override
   * this to inform <TT>UserTable</TT> that its cache of users' capabilities
   * has become invalid.
   *
   * @param session     the session in which the change will be made
   * @param troid       the troid of the record which has been changed
   * @param data        the new values of the record's fields
   *
   * @see GroupMembershipTable#notifyTouched
   */

  protected void notifyTouched(PoemSession session, Integer troid) {
    PoemFloatingVersionedObject allTroids = this.allTroids;
    if (allTroids != null)
      allTroids.invalidateVersion(session);
  }

  // 
  // ===========
  //  Utilities
  // ===========
  // 

  private String _quotedName(String name) {
    return database._quotedName(name);
  }

  /**
   * A concise string to stand in for the table.  The table's name and a
   * description of where it was defined (the DSD, the metadata tables or the
   * JDBC metadata).
   */

  public String toString() {
    return getName() + " (from " + definitionSource + ")";
  }

  /**
   * Print some diagnostic information about the contents and consistency of
   * POEM's cache for this table to stderr.
   */

  public void dumpCacheAnalysis() {
    System.err.println("\n-------- Analysis of " + name + "'s cache\n");
    cache.dumpAnalysis();
  }

  /**
   * Print information about the structure of the database to stdout.
   */

  public void dump() {
    System.out.println("=== table " + name);
    for (int c = 0; c < columns.size(); ++c)
      ((Column)columns.elementAt(c)).dump();
  }

  // 
  // ================
  //  Initialization
  // ================
  // 

  /**
   * Don't call this.  Columns should be defined either in the DSD (in which
   * case the boilerplate code generated by the preprocessor will call this
   * method) or directly in the RDBMS (in which case the initialisation code
   * will).
   */

  protected synchronized void defineColumn(Column column)
      throws DuplicateColumnNamePoemException,
             DuplicateTroidColumnPoemException,
             DuplicateDeletedColumnPoemException {
    if (column.getTable() != this)
      throw new ColumnInUsePoemException(this, column);

    if (columnsByName.get(column.getName()) != null)
      throw new DuplicateColumnNamePoemException(this, column);

    if (column.isTroidColumn()) {
      if (troidColumn != null)
        throw new DuplicateTroidColumnPoemException(this, column);
      troidColumn = column;
    }
    else if (column.isDeletedColumn()) {
      if (deletedColumn != null)
        throw new DuplicateDeletedColumnPoemException(this, column);
      deletedColumn = column;
    }
    else {
      PoemType type = column.getType();
      if (type instanceof ReferencePoemType &&
          ((ReferencePoemType)type).targetTable() ==
               database.getCapabilityTable()) {
        if (column.getName().equals("canread"))
          canReadColumn = column;
        else if (column.getName().equals("canwrite"))
          canWriteColumn = column;
      }
    }

    if (column.isPrimaryDisplay())
      displayColumn = column;

    column.setTable(this);
    columns.addElement(column);
    columnsByName.put(column.getName(), column);
  }

  private void _defineColumn(Column column) {
    try {
      defineColumn(column);
    }
    catch (DuplicateColumnNamePoemException e) {
      throw new UnexpectedExceptionPoemException(e);
    }
    catch (DuplicateTroidColumnPoemException e) {
      throw new UnexpectedExceptionPoemException(e);
    }
  }

  private int extrasIndex = 0;

  void setTableInfo(TableInfo tableInfo) {
    info = tableInfo;
  }

  void createTableInfo() throws PoemException {
    if (info == null) {
      TableInfoData tid = new TableInfoData(getName());
      info = (TableInfo)getDatabase().getTableInfoTable().create(tid);
    }
  }

  synchronized void unifyWithColumnInfo() throws PoemException {

    // Match columnInfo with our columns

    if (info == null)
      throw new PoemBugPoemException("Get the initialisation order right ...");

    for (Enumeration ci =
             database.getColumnInfoTable().getTableinfoColumn().
                 selectionWhereEq(info.troid());
         ci.hasMoreElements();) {
      ColumnInfo columnInfo = (ColumnInfo)ci.nextElement();
      Column column = (Column)columnsByName.get(columnInfo.getName());
      if (column == null) {
        column = ExtraColumn.from(this, columnInfo, extrasIndex++);
        _defineColumn(column);
      }

      column.setColumnInfo(columnInfo);
    }

    // Conversely, make columnInfo for any columns which don't have it

    for (Enumeration c = columns.elements(); c.hasMoreElements();)
      ((Column)c.nextElement()).createColumnInfo();
  }

  synchronized void unifyWithDB(ResultSet colDescs)
      throws SQLException, PoemException {

    Hashtable dbColumns = new Hashtable();

    int dbIndex;
    for (dbIndex = 0; colDescs.next(); ++dbIndex) {
      String colName = colDescs.getString("COLUMN_NAME");
      Column column = (Column)columnsByName.get(colName);

      if (column == null) {
        PoemType colType =
            database.defaultPoemTypeOfColumnMetaData(colDescs);

        if (troidColumn == null && colName.equals("id") &&
            colType.canBe(TroidPoemType.it))
          colType = TroidPoemType.it;

        if (deletedColumn == null && colName.equals("deleted") &&
            colType.canBe(DeletedPoemType.it))
          colType = DeletedPoemType.it;

        column = new ExtraColumn(this, colDescs.getString("COLUMN_NAME"),
                                 colType, DefinitionSource.sqlMetaData,
                                 extrasIndex++);

        _defineColumn(column);

        // FIXME hack: this happens when *InfoTable are unified with
        // the database---obviously they haven't been initialised yet
        // but it gets fixed in the next round when all tables
        // (including them, again) are unified

        if (info != null)
          column.createColumnInfo();
      }
      else
        column.assertMatches(colDescs);

      dbColumns.put(column, Boolean.TRUE);
    }

    if (dbIndex == 0)
      // OK, we simply don't exist ...
      dbCreateTable();
    else {
      // silently create any missing columns
      for (int c = 0; c < columns.size(); ++c) {
        Column column = (Column)columns.elementAt(c);
        if (dbColumns.get(column) == null)
          dbAddColumn(column);
      }
    }

    if (troidColumn == null)
      throw new NoTroidColumnException(this);

    // Where should we start numbering new records?

    String sql = 
        "SELECT " + _quotedName(troidColumn.getName()) +
        " FROM " + _quotedName(name) +
        " ORDER BY " + _quotedName(troidColumn.getName()) + " DESC";
    try {
      ResultSet maxTroid =
          getDatabase().getCommittedConnection().createStatement().
              executeQuery(sql);
      if (database.logSQL)
        database.log(new SQLLogEvent(sql));
      if (maxTroid.next())
        nextTroid = maxTroid.getInt(1) + 1;
      else
        nextTroid = 0;
    }
    catch (SQLException e) {
      throw new SQLSeriousPoemException(e);
    }

    normalColumns = new Vector();
    for (int c = 0; c < columns.size(); ++c) {
      Column column = (Column)columns.elementAt(c);
      if (column.isNormal())
        normalColumns.addElement(column);
    }
  }
}
