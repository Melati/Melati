package org.melati.poem;

import org.melati.util.*;
import java.sql.*;
import java.util.*;

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

  public final Database getDatabase() {
    return database;
  }

  public final String getName() {
    return name;
  }

  public final String getDisplayName() {
    return info.getDisplayname();
  }

  public final Enumeration columns() {
    return normalColumns.elements();
  }

  final int getColumnsCount() {
    return columns.size();
  }

  public final Column getColumn(String name) {
    return (Column)columnsByName.get(name);
  }

  public final Column troidColumn() {
    return troidColumn;
  }

  public final Column deletedColumn() {
    return deletedColumn;
  }

  public final Column displayColumn() {
    return displayColumn == null ? troidColumn : displayColumn;
  }

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

  Data dbData(Session session, Integer troid) {
    return dbData(((SessionStuff)sessionStuffs.get(session.index())).get,
                  troid);
  }

  Data dbCommittedData(Integer troid) {
    return dbData(getCommittedSessionStuff().get, troid);
  }

  private void modify(Session session, Integer troid, Data data) {
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

  private void insert(Session session, Integer troid, Data data) {
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

  public Enumeration referencesTo(final Persistent object) {
    return new FlattenedEnumeration(
        new MappedEnumeration(columns.elements()) {
          public Object mapped(Object column) {
            return ((Column)column).referencesTo(object);
          }
        });
  }

  void delete(Integer troid, Session session) {
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

  void writeDown(Session session, Integer troid, Data data) {
    troidColumn.setIdent(data, troid);

    // FIXME race, I suppose
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

  public void dumpCacheAnalysis() {
    System.err.println("\n-------- Analysis of " + name + "'s cache\n");
    cache.dumpAnalysis();
  }

  void uncacheContents() {
    cache.uncacheContents();
  }

  void trimCache(int maxSize) {
    cache.trim(maxSize);
  }

  void cacheIterate(final Session session, final Function f) {
    cache.iterate(new Function() {
                    public Object apply(Object is) {
                      Data data = ((CacheInterSession)is).cachedData(session);
                      if (data != null) f.apply(data);
                      return null;
                    }
                  });
  }

  CacheInterSession interSession(Integer troid) {
    synchronized (cache) {
      CacheInterSession is = (CacheInterSession)cache.get(troid);
      if (is == null) {
        is = new CacheInterSession(this, troid);
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

  public Persistent getObject(Integer troid)  {
    Persistent persistent = newPersistent();
    persistent.init(interSession(troid));

    try {
      persistent.dataUnchecked(PoemThread.session());
    }
    catch (RowDisappearedPoemException e) {
      throw new NoSuchRowPoemException(this, troid);
    }

    return persistent;
  }

  public Persistent getObject(int troid) throws NoSuchRowPoemException {
    return getObject(new Integer(troid));
  }

  private ResultSet selectionResultSet(String whereClause,
                                       boolean includeDeleted)
      throws SQLPoemException {
    if (deletedColumn != null && !includeDeleted)
      whereClause = (whereClause == null ? "" : whereClause + " AND ") +
                    "NOT " + deletedColumn.getName();
      
    String sql =
        "SELECT " + _quotedName(troidColumn.getName()) +
        " FROM " + _quotedName(name) +
        (whereClause == null ? "" : " WHERE " + whereClause);

    try {
      Session session = PoemThread.session();
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

  public Enumeration selection(String whereClause, boolean includeDeleted)
      throws SQLPoemException {
    return new ResultSetEnumeration(
        this, selectionResultSet(whereClause, includeDeleted), true);
  }

  Enumeration troidSelection(String whereClause, boolean includeDeleted)
      throws SQLPoemException {
    return new ResultSetEnumeration(
        this, selectionResultSet(whereClause, includeDeleted), false);
  }

  public final Enumeration selection(String whereClause)
      throws SQLPoemException {
    return selection(whereClause, false);
  }

  public final Enumeration selection(boolean includeDeleted)
      throws SQLPoemException {
    return selection(null, includeDeleted);
  }

  public final Enumeration selection() throws SQLPoemException {
    return selection(null, false);
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

    SessionToken sessionToken = PoemThread.sessionToken();
    ConstructionInterSession dummyInterSession =
        new ConstructionInterSession(this, troid, data, sessionToken.session);
    Persistent object = newPersistent();
    object.initForConstruct(dummyInterSession, sessionToken.accessToken);

    if (initOrData instanceof Initialiser)
      // Let the user do their worst.
      ((Initialiser)initOrData).init(object);

    // Are the values they have put in legal, and is the result
    // something they could have created by writing into a record?
    // FIXME does this do the right thing?

    try {
      validate(data);
      object.assertCanWrite(data, sessionToken.accessToken);
    }
    catch (Exception e) {
      throw new InitialisationPoemException(this, e);
    }

    // Plug it into the cache for later writedown

    CacheInterSession interSession = interSession(troid);
    interSession.setData(sessionToken.session, data);
    object.init(interSession);
    return object;
  }

  public Persistent create(Data data) 
      throws AccessPoemException, ValidationPoemException,
             InitialisationPoemException {
    return create((Object)data);
  }

  public Persistent create(Initialiser initialiser) 
      throws AccessPoemException, ValidationPoemException,
             InitialisationPoemException {
    return create((Object)initialiser);
  }

  protected Data _newData() {
    return new Data();
  }

  final Data newData() {
    Data them = _newData();
    them.extras = new Object[getExtrasCount()];
    return them;
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

  public Capability getDefaultCanRead() {
    return info.getDefaultcanread();
  }

  public Capability getDefaultCanWrite() {
    return info.getDefaultcanwrite();
  }

  final Column canReadColumn() {
    return canReadColumn;
  }

  final Column canWriteColumn() {
    return canWriteColumn;
  }

  protected void notifyUpdate(Session session, Data data) {
  }
}
