package org.melati.poem;

import org.melati.util.*;
import java.sql.*;
import java.util.*;

public class Table {

  private Database database;
  private String name;
  private DefinitionSource definitionSource;
  private Integer tableInfoID = null;

  private Vector columns = new Vector();
  private Hashtable columnsByName = new Hashtable();

  private Column troidColumn = null;

  public Table(Database database, String name,
               DefinitionSource definitionSource) {
    this.database = database;
    this.name = name;
    this.definitionSource = definitionSource;
  }

  // 
  // ================
  //  Initialization
  // ================
  // 

  protected void init() throws PoemException {
  }

  protected synchronized void defineColumn(Column column)
      throws DuplicateColumnNamePoemException,
             DuplicateTroidColumnPoemException {
    if (column.getTable() != this)
      throw new ColumnInUsePoemException(this, column);

    if (columnsByName.get(column.getName()) != null)
      throw new DuplicateColumnNamePoemException(this, column);

    if (column.isTroidColumn()) {
      if (troidColumn != null)
        throw new DuplicateTroidColumnPoemException(this, column);
      troidColumn = column;
    }

    column.setTable(this);
    column.setDBIndex(-1);
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

  synchronized void createTableInfo() throws PoemException {
    final Table _this = this;
    if (getTableInfoID() == null) {
      System.err.println("*** creating t i for " + getName());
      setTableInfoID(
          getDatabase().getTableInfoTable().create(
              new Initialiser() {
                public void init(Persistent g) throws AccessPoemException {
                  ((TableInfo)g).setName(_this.getName());
                }
              }).getTroid());
    }
  }

  synchronized void unifyWithColumnInfo() throws PoemException {

    // Match columnInfo with our columns

    if (tableInfoID == null)
      throw new PoemBugPoemException("Get the initialisation order right ...");

    for (Enumeration ci =
             database.getColumnInfoTable().getTableinfoColumn().
                 selectionWhereEq(tableInfoID);
         ci.hasMoreElements();) {
      ColumnInfo columnInfo = (ColumnInfo)ci.nextElement();
      System.err.println("*** looking at columninfo for " + name + "." + columnInfo.getName());
      Column column = (Column)columnsByName.get(columnInfo.getName());
      if (column == null) {
        System.err.println("*** (creating)");
        column = ExtraColumn.from(this, columnInfo, extrasIndex++);
        _defineColumn(column);
      }
      else {
        System.err.println("*** (found)");
        column.readColumnInfo(columnInfo);
      }

      column.setColumnInfoID(columnInfo.getId());
    }

    // Conversely, make columnInfo for any columns which don't have it

    System.err.println("*** making c i");
    for (Enumeration c = columns.elements(); c.hasMoreElements();)
      ((Column)c.nextElement()).createColumnInfo();
  }

  synchronized void unifyWithDB(ResultSet colDescs) throws SQLException {
    for (int c = 0; c < columns.size(); ++c)
      ((Column)columns.elementAt(c)).setDBIndex(-1);

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

        column = new ExtraColumn(this, colDescs.getString("COLUMN_NAME"),
                                 colType, DefinitionSource.sqlMetaData,
                                 extrasIndex++);

        _defineColumn(column);

        // FIXME hack: this happens when *InfoTable are unified with
        // the database---obviously they haven't been initialised yet
        // but it gets fixed in the next round when all tables
        // (including them, again) are unified

        if (tableInfoID != null)
          column.createColumnInfo();
      }
      else
        column.assertMatches(colDescs);

      column.setDBIndex(dbIndex);
    }

    if (dbIndex == 0)
      // OK, we simply don't exist ...
      dbCreateTable();
    else {
      // silently create any missing columns
      for (int c = 0; c < columns.size(); ++c) {
        Column column = (Column)columns.elementAt(c);
        if (column.getDBIndex() == -1)
          dbAddColumn(column);
        column.setDBIndex(dbIndex++);
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
      if (maxTroid.next())
        nextTroid = maxTroid.getInt(1) + 1;
      else
        nextTroid = 0;
    }
    catch (SQLException e) {
      throw new SQLSeriousPoemException(e);
    }
  }

  // 
  // ===========
  //  Utilities
  // ===========
  // 

  private String _quotedName(String name) {
    return database._quotedName(name);
  }

  public String toString() {
    return getName() + " (from " + definitionSource + ")";
  }

  public void dump() {
    System.out.println("=== table " + name);
    for (int c = 0; c < columns.size(); ++c)
      ((Column)columns.elementAt(c)).dump();
  }

  // 
  // ===========
  //  Accessors
  // ===========
  // 

  public final String getName() {
    return name;
  }

  public final Database getDatabase() {
    return database;
  }

  final Integer getTableInfoID() {
    return tableInfoID;
  }

  final void setTableInfoID(Integer tableInfoID) {
    this.tableInfoID = tableInfoID;
  }

  final int getColumnsCount() {
    return columns.size();
  }

  public final Column getColumn(String name) {
    return (Column)columnsByName.get(name);
  }

  public final Column getTroidColumn() {
    return troidColumn;
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
      database.log(new StructuralModificationLogEvent(sql));
      database.getCommittedConnection().createStatement().executeUpdate(sql);
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
      database.log(new StructuralModificationLogEvent(sql));
      database.getCommittedConnection().createStatement().executeUpdate(sql);
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

  private boolean load(PreparedStatement select, Integer troid,
                       Fields fields) {
    try {
      synchronized (select) {
        select.setInt(1, troid.intValue());
        ResultSet rs = select.executeQuery();

        if (!rs.next())
          return false;

        for (int c = 0; c < columns.size(); ++c)
          ((Column)columns.elementAt(c)).load(rs, c + 1, fields);

        if (rs.next())
          throw new DuplicateTroidPoemException(this, troid);

        return true;
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

  boolean load(Session session, Integer troid, Fields fields) {
    return load(((SessionStuff)sessionStuffs.get(session.getIndex())).get,
                troid, fields);
  }

  boolean loadCommitted(Integer troid, Fields fields) {
    return load(getCommittedSessionStuff().get, troid, fields);
  }

  private void modify(Session session, Fields fields) {
    PreparedStatement modify =
        ((SessionStuff)sessionStuffs.get(session.getIndex())).modify;
    synchronized (modify) {
      for (int c = 0; c < columns.size(); ++c)
        ((Column)columns.elementAt(c)).save(fields, modify, c + 1);
      troidColumn.save(fields, modify, columns.size() + 1);
      try {
        modify.executeUpdate();
      }
      catch (SQLException e) {
        throw new SQLSeriousPoemException(e);
      }
    }
  }

  private void insert(Session session, Fields fields) {
    PreparedStatement insert =
        ((SessionStuff)sessionStuffs.get(session.getIndex())).insert;
    synchronized (insert) {
      for (int c = 0; c < columns.size(); ++c)
        ((Column)columns.elementAt(c)).save(fields, insert, c + 1);
      try {
        insert.executeUpdate();
      }
      catch (SQLException e) {
        throw new PreparedSQLSeriousPoemException(insert, e);
      }
    }
  }

  void writeDown(Session session, Fields fields) {
    // FIXME race, I suppose
    if (fields.exists)
      modify(session, fields);
    else {
      fields.exists = true;
      insert(session, fields);
    }
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

  private int maxCacheSize = 1000;
  private Cache cache = new Cache(maxCacheSize);

  void uncacheContents() {
    cache.uncacheContents();
  }

  private InterSession getInterSession(Integer troid) {
    synchronized (cache) {
      InterSession is = (InterSession)cache.get(troid);
      if (is == null) {
        is = new InterSession(troid);
        cache.put(is);
      }
      return is;
    }
  }

  private Persistent _get(Integer troid) {
    SessionToken sessionToken = PoemThread.sessionToken();
    int s = sessionToken.session.getIndex();

    InterSession is = getInterSession(troid);
    synchronized (is) {
      if (is.versions == null) {
        is.versions = new Persistent[database.getSessionsMax()];
        return is.versions[s] = newPersistent(troid, sessionToken, is);
      }
      else {
        Persistent g = is.versions[s];
        return g != null ? g :
            (is.versions[s] = newPersistent(troid, sessionToken, is));
      }
    }
  }

  // 
  // ----------
  //  Fetching
  // ----------
  // 

  public Persistent getObject(Integer troid) throws NoSuchRowPoemException {
    Persistent g = _get(troid);
    try {
      g.fieldsUnchecked();
    }
    catch (RowDisappearedPoemException e) {
      throw new NoSuchRowPoemException(this, troid);
    }
    return g;
  }

  public Persistent getObject(int troid) throws NoSuchRowPoemException {
    return getObject(new Integer(troid));
  }

  public Enumeration selection(String whereClause)
      throws SQLPoemException {
    String sql =
        "SELECT " + _quotedName(troidColumn.getName()) +
        " FROM " + _quotedName(name) +
        (whereClause == null ? "" : " WHERE " + whereClause);

    try {
      Session session = PoemThread.session();
      session.writeDown();
      return new ResultSetEnumeration(
          this,
          session.getConnection().createStatement().executeQuery(sql));
    }
    catch (SQLException e) {
      throw new ExecutingSQLPoemException(sql, e);
    }
  }

  public Enumeration selection() throws SQLPoemException {
    return selection(null);
  }

  // 
  // ----------
  //  Creation
  // ----------
  // 

  private void validate(Fields fields) throws FieldContentsPoemException {
    for (int c = 0; c < columns.size(); ++c) {
      Column column = (Column)columns.elementAt(c);
      try {
        column.getType().assertValidIdent(column.getIdent(fields));
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

  public Persistent create(Initialiser initialiser)
      throws AccessPoemException, ValidationPoemException {

    // Set it up without an InterSession but with some blank fields

    SessionToken sessionToken = PoemThread.sessionToken();
    Integer troid = nextTroid();
    Persistent g = newPersistentForInit(troid, sessionToken);
    g.getFields().exists = false;
    troidColumn.setIdent(g.getFields(), troid);

    // Let the user do their worst.

    initialiser.init(g);

    // Are the values they have put in legal, and is the result
    // something they could have created by writing into a record?
    // FIXME does this do the right thing?

    try {
      validate(g.getFields());
      g.assertCanWrite(g.getFields(), g.getSessionToken().accessToken);
    }
    catch (Exception e) {
      throw new InitialisationPoemException(this, e);
    }

    // Plug it into the cache ...

    InterSession is = getInterSession(troid);
    synchronized (is) {
      if (is.versions != null)
        // FIXME this check is definitely NOT sufficient!
        throw new DuplicateTroidPoemException(this, troid);

      is.versions = new Persistent[database.getSessionsMax()];
      is.versions[sessionToken.session.getIndex()] = g;
      g.setInterSession(is);
    }

    // ... for later writedown

    g.markTouched();

    return g;
  }

  private Persistent newPersistent(
      Integer troid, SessionToken sessionToken, InterSession interSession) {
    Persistent g = newPersistent();
    g.init(this, troid, sessionToken, interSession);
    return g;
  }

  private Persistent newPersistentForInit(Integer troid, SessionToken sessionToken) {
    Persistent g = newPersistent();
    g.initForInit(this, troid, sessionToken);
    return g;
  }

  protected Persistent newPersistent() {
    return new Persistent();
  }

  int getExtrasCount() {
    return extrasIndex;
  }

  // 
  // ----------------
  //  Access control
  // ----------------
  // 

  final AccessToken readOverride = new BaseAccessToken();

  public Column getCanReadColumn() {
    return null;
  }

  public Capability getCanRead() {
    return null;
  }

  public Column getCanWriteColumn() {
    return null;
  }

  public Capability getCanWrite() {
    return null;
  }
}
