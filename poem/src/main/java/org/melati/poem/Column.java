/*
 * $Source$
 * $Revision$
 *
 * Copyright (C) 2000 William Chesters
 *
 * Part of Melati (http://melati.org), a framework for the rapid
 * development of clean, maintainable web applications.
 *
 * Melati is free software; Permission is granted to copy, distribute
 * and/or modify this software under the terms either:
 *
 * a) the GNU General Public License as published by the Free Software
 *    Foundation; either version 2 of the License, or (at your option)
 *    any later version,
 *
 *    or
 *
 * b) any version of the Melati Software License, as published
 *    at http://melati.org
 *
 * You should have received a copy of the GNU General Public License and
 * the Melati Software License along with this program;
 * if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA to obtain the
 * GNU General Public License and visit http://melati.org to obtain the
 * Melati Software License.
 *
 * Feel free to contact the Developers of Melati (http://melati.org),
 * if you would like to work out a different arrangement than the options
 * outlined here.  It is our intention to allow Melati to be used by as
 * wide an audience as possible.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * Contact details for copyright holder:
 *
 *     William Chesters <williamc At paneris.org>
 *     http://paneris.org/~williamc
 *     Obrechtstraat 114, 2517VX Den Haag, The Netherlands
 */

package org.melati.poem;

import java.io.PrintStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Enumeration;

import org.melati.poem.dbms.Dbms;
import org.melati.poem.util.EmptyEnumeration;
import org.melati.poem.util.StringUtils;

/**
 * Abstract {@link Table} column which is extended by the generated classes.
 *
 * @author WilliamC At paneris.org
 * @param <T> The type of the Column
 * 
 */
public abstract class Column<T> implements FieldAttributes<T> {
  @SuppressWarnings("rawtypes")
  private Table table = null;
  private String name;
  private String quotedName;
  private SQLPoemType<T> type;
  private DefinitionSource definitionSource;
  private ColumnInfo info = null;

  /**
   * Constructor.
   * @param table this column belongs to
   * @param name of this Column
   * @param type datatype
   * @param definitionSource where it is being defined from
   */
  public Column(
    Table<?> table,
    String name,
    SQLPoemType<T> type,
    DefinitionSource definitionSource) {
    this.table = table;
    this.name = name;
    this.quotedName = table.getDatabase().quotedName(name);
    this.type = type;
    this.definitionSource = definitionSource;
  }

  // 
  // ================
  //  Initialisation
  // ================
  // 

  /**
   * @return the underlying Dbms
   */
  Dbms dbms() {
    return getDatabase().getDbms();
  }

  <O> void unifyType(SQLPoemType<O> storeType, DefinitionSource source) {
    PoemType<T> unified = dbms().canRepresent(storeType, type);
    if (unified == null || !(unified instanceof SQLPoemType))
      throw new TypeDefinitionMismatchException(this, storeType, source);

    type = (SQLPoemType<T>) unified;
  }

  void assertMatches(ResultSet colDesc)
      throws SQLException, TypeDefinitionMismatchException {
    PoemType<?> dbType = getDatabase().defaultPoemTypeOfColumnMetaData(colDesc);

    if (dbms().canRepresent(dbType, type) == null)
      throw new TypeDefinitionMismatchException(
        this,
        dbType,
        DefinitionSource.sqlMetaData);
  }

  @SuppressWarnings("unchecked")
  void setColumnInfo(ColumnInfo columnInfo) {
    try {
      unifyType(columnInfo.getType(), DefinitionSource.infoTables);
      columnInfo.setColumn(this);
      if (columnInfo.getDisplaylevel() == DisplayLevel.primary)
        table.setDisplayColumn(this);
      if (columnInfo.getSearchability() == Searchability.primary)
        table.setSearchColumn(this);
      info = columnInfo;
      table.notifyColumnInfo(info);
    } catch (Exception e) {
      throw new UnexpectedExceptionPoemException(
        e,
        "Setting column info for " + name + " to " + columnInfo);
    }
  }

  protected DisplayLevel defaultDisplayLevel() {
    return DisplayLevel.summary;
  }

  protected Searchability defaultSearchability() {
    return Searchability.yes;
  }

  protected Integer defaultDisplayOrderPriority() {
    return null;
  }

  protected boolean defaultSortDescending() {
    return false;
  }

  protected String defaultDisplayName() {
    return StringUtils.capitalised(getName());
  }

  protected int defaultDisplayOrder() {
    return 100;
  }

  protected String defaultDescription() {
    return null;
  }

  protected boolean defaultUserEditable() {
    return true;
  }

  protected boolean defaultUserCreateable() {
    return true;
  }

  protected boolean defaultIndexed() {
    return isTroidColumn();
  }

  protected boolean defaultUnique() {
    return isTroidColumn();
  }

  /**
   * @return the StandardIntegrityFix prevent
   */
  protected StandardIntegrityFix defaultIntegrityFix() {
    return StandardIntegrityFix.prevent;
  }

  protected int defaultWidth() {
    return 20;
  }

  protected int defaultHeight() {
    return 1;
  }

  protected int defaultPrecision() {
    return 22;
  }

  protected int defaultScale() {
    return 2;
  }

  protected String defaultRenderinfo() {
    return null;
  }

  @SuppressWarnings("unchecked")
  void createColumnInfo() throws PoemException {
    if (info == null) {
      info = (ColumnInfo)getDatabase().
          getColumnInfoTable().create(new Initialiser() {
        public void init(Persistent g) throws AccessPoemException {
          ColumnInfo i = (ColumnInfo)g;
          i.setName(getName());
          i.setDisplayname(defaultDisplayName());
          i.setDisplayorder(defaultDisplayOrder());
          i.setDescription(defaultDescription());
          i.setDisplaylevel(defaultDisplayLevel());
          i.setSearchability(defaultSearchability());
          i.setSortdescending(defaultSortDescending());
          i.setDisplayorderpriority(defaultDisplayOrderPriority());
          i.setTableinfoTroid(table.tableInfoID());
          i.setUsereditable(defaultUserEditable());
          i.setUsercreateable(defaultUserCreateable());
          i.setIndexed(defaultIndexed());
          i.setUnique(defaultUnique());
          i.setWidth(defaultWidth());
          i.setHeight(defaultHeight());
          i.setRenderinfo(defaultRenderinfo());
          i.setIntegrityfix(defaultIntegrityFix());
          i.setPrecision(defaultPrecision());
          i.setScale(defaultScale());
          getType().saveColumnInfo(i);
        }
      });

      // FIXME Repeating this in setColumnInfo(ColumnInfo) is a bad sign

      if (defaultDisplayLevel() == DisplayLevel.primary)
        table.setDisplayColumn(this);
      if (defaultSearchability() == Searchability.primary)
        table.setSearchColumn(this);
    }
  }

  void unifyWithIndex(String indexName, ResultSet index) throws SQLException, 
                              IndexUniquenessPoemException {
    boolean indexUnique = !index.getBoolean("NON_UNIQUE");
    if (indexUnique != getUnique()) 
        throw new IndexUniquenessPoemException(
          this,
          indexName,
          getUnique());
  }

  // 
  // ===========
  //  Accessors
  // ===========
  // 

  /**
   * @return the Database our table is in
   */
  public final Database getDatabase() {
    return getTable().getDatabase();
  }

  /**
   * @return our Table
   */
  @SuppressWarnings("unchecked")
  public final Table<Persistent> getTable() {
    return table;
  }

  final void setTable(Table<?> table) {
    this.table = table;
  }

  /**
   * {@inheritDoc}
   * @see org.melati.poem.FieldAttributes#getName()
   */
  public final String getName() {
    return name;
  }

  /**
   * @return the name quoted appropriately for the DBMS
   */
  public final String quotedName() {
    return quotedName;
  }

  /**
   * @return the name in table.column notation
   */
  public final String fullQuotedName() {
    return table.quotedName() + "." + quotedName;
  }

  /**
   * Return a human readable name from the metadata.
   * 
   * {@inheritDoc}
   * @see org.melati.poem.FieldAttributes#getDisplayName()
   */
  public final String getDisplayName() {
    return info.getDisplayname();
  }

  /**
   * {@inheritDoc}
   * @see org.melati.poem.FieldAttributes#getDescription()
   */
  public final String getDescription() {
    return info.getDescription();
  }

  /**
   * The troid (<TT>id</TT>) of the column's entry in the <TT>columninfo</TT>
   * table.  It will always have one (except during initialisation, which the
   * application programmer will never see).
   * @return the troid of our record in the columnInfo table
   */
  final Integer columnInfoID() {
    return info == null ? null : info.troid();
  }

  /**
   * @return the metadata record for this Column
   */
  public final ColumnInfo getColumnInfo() {
    return info;
  }

  /**
   * @return the defined or default DsiplayLevel
   */
  public DisplayLevel getDisplayLevel() {
    return info == null ? defaultDisplayLevel() : info.getDisplaylevel();
  }

  /**
   * @param level the DisplayLevel to set
   */
  public void setDisplayLevel(DisplayLevel level) {
    if (info != null)
      info.setDisplaylevel(level);
  }

  /**
   * @return our defined or default Searchabillity
   */
  public Searchability getSearchability() {
    return info == null ? defaultSearchability() : info.getSearchability();
  }

  /**
   * @param searchability the Searchability to set
   */
  public void setSearchability(Searchability searchability) {
    if (info != null)
      info.setSearchability(searchability);
  }

  /**
   * {@inheritDoc}
   * @see org.melati.poem.FieldAttributes#getUserEditable()
   */
  public final boolean getUserEditable() {
    return !isTroidColumn()
      && (info == null || info.getUsereditable().booleanValue());
  }

  /**
   * {@inheritDoc}
   * @see org.melati.poem.FieldAttributes#getUserCreateable()
   */
  public final boolean getUserCreateable() {
    return !isTroidColumn()
      && (info == null || info.getUsercreateable().booleanValue());
  }

  /**
   * @return the SQLPoemType of this Column
   */
  public final SQLPoemType<T> getSQLType() {
    return type;
  }

  /**
   * {@inheritDoc}
   * @see org.melati.poem.FieldAttributes#getType()
   */
  public final PoemType<T> getType() {
    return type;
  }

  /**
   * @return whether this is a Troid Column
   */
  public final boolean isTroidColumn() {
    return getType() instanceof TroidPoemType;
  }

  /**
   * A Deleted Column is a Column which signal whether 
   * the record has been soft-deleted.
   * @return whether this is a Deleted Column
   */
  public final boolean isDeletedColumn() {
    return getType() instanceof DeletedPoemType;
  }

  /**
   * {@inheritDoc}
   * @see org.melati.poem.FieldAttributes#getIndexed()
   */
  public final boolean getIndexed() {
    return getUnique() || info.getIndexed().booleanValue();
  }

  /**
   * @return whether this Column's values are unique in this table
   */
  public final boolean getUnique() {
    return isTroidColumn() || info.getUnique().booleanValue();
  }

  /**
   * @return the specified or default IntegrityFix
   */
  public IntegrityFix getIntegrityFix() {
    IntegrityFix it = info.getIntegrityfix();
    return it == null ? defaultIntegrityFix() : it;
  }

  /**
   * @param fix the IntegrityFix to set
   */
  public void setIntegrityFix(StandardIntegrityFix fix) {
    info.setIntegrityfix(fix);
  }

  /**
   * {@inheritDoc}
   * @see org.melati.poem.FieldAttributes#getRenderInfo()
   */
  public final String getRenderInfo() {
    return info.getRenderinfo();
  }

  /**
   * {@inheritDoc}
   * @see org.melati.poem.FieldAttributes#getWidth()
   */
  public final int getWidth() {
    return info.getWidth().intValue();
  }

  /**
   * {@inheritDoc}
   * @see org.melati.poem.FieldAttributes#getHeight()
   */
  public final int getHeight() {
    return info.getHeight().intValue();
  }

  /**
   * @return the set or default DisplayOrderPriority
   */
  public final Integer getDisplayOrderPriority() {
    return info == null ? null : info.getDisplayorderpriority();
  }

  /**
   * Defaults to false.
   * @return whether this Column should be sorted in descending order 
   */
  public final boolean getSortDescending() {
    return info.getSortdescending() == null
      ? false
      : info.getSortdescending().booleanValue();
  }

  // 
  // ===========
  //  Utilities
  // ===========
  // 

  /**
   * {@inheritDoc}
   * @see java.lang.Object#toString()
   */
  public String toString() {
    return table.getName()
      + "."
      + name
      + ": "
      + getType().toString()
      + " (from "
      + definitionSource
      + ")";
  }

  /**
   * Print information about the structure of the Column to stdout.
   */
  public void dump() {
    dump(System.out);
  }

  /**
   * Print information to PrintStream. 
   * 
   * @param ps PrintStream to dump to
   */
  public void dump(PrintStream ps) {
    ps.println(toString());
  }

  /**
   * 
   * @param raw An object with an equivalent SQL type
   * @return the SQL euals clause that would capture equality with the raw
   */
  public String eqClause(Object raw) {
    return fullQuotedName()
      + (raw == null ? " IS NULL" : " = " + type.quotedRaw(raw));
  }

  private PreparedStatementFactory selectionWhereEq = null;

  private PreparedStatementFactory statementWhereEq() {
    if (selectionWhereEq == null)
      selectionWhereEq =
        new PreparedStatementFactory(
          getDatabase(),
          getTable().selectionSQL(
            getTable().quotedName(),
            fullQuotedName()
              + " = "
              + dbms().preparedStatementPlaceholder(getType()),
            null,
            false,
            true));

    return selectionWhereEq;
  }

  ResultSet resultSetWhereEq(Object raw) {
    SessionToken token = PoemThread.sessionToken();
    PreparedStatement ps =
      statementWhereEq().preparedStatement(token.transaction);
    type.setRaw(ps, 1, raw);
    return statementWhereEq().resultSet(token, ps);
  }

  /**
   * Not used in Melati or PanEris tree.
   * 
   * @param raw value 
   * @return an Enumeration of Integers 
   */
  /*
  Enumeration troidSelectionWhereEq(Object raw) {
    return new ResultSetEnumeration(resultSetWhereEq(raw)) {
      public Object mapped(ResultSet rs) throws SQLException {
        return new Integer(rs.getInt(1));
      }
    };
  }
  */
  
  /**
   * Get rows where column equal to value.
   * 
   * @param raw a raw value such as a String
   * @return an enumeration of Persistents
   */
  public Enumeration<Persistent> selectionWhereEq(Object raw) {
    return new ResultSetEnumeration<Persistent>(resultSetWhereEq(raw)) {
      public Object mapped(ResultSet rs) throws SQLException {
        return getTable().getObject(rs.getInt(1));
      }
    };
  }

  /**
   * Return the first one found or null if not found. 
   * 
   * @param raw Object of correct type for this Column
   * @return the first one found based upon default ordering
   */
  public Persistent firstWhereEq(Object raw) {
    Enumeration<Persistent> them = selectionWhereEq(raw);
    return them.hasMoreElements() ? (Persistent)them.nextElement() : null;
  }

  /**
   * Create a new CachedSelection of objects equal to this raw parameter. 
   * 
   * @param raw Object of correct type for this Column
   * @return a new CachedSelection of objects equal to raw.
   */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  public CachedSelection cachedSelectionWhereEq(Object raw) {
    return new CachedSelection(getTable(), eqClause(raw), null);
  }

  // 
  // =======================================
  //  Reading/setting the column in records
  // =======================================
  // 

 /**
  * Retrieves the field value, with locking,
  * for this {@link Column}.
  * 
  * @param g  the {@link Persistent} to read
  * @return the Object itself
  * @throws AccessPoemException 
  *         if the current <code>AccessToken</code> 
  *         does not confer read access rights
  */
  public abstract Object getRaw(Persistent g) throws AccessPoemException;

 /**
  * Retrieves the field value, without locking,
  * for this <code>Column</code>.
  * 
  * @param g  the {@link Persistent} to read
  * @return the Object without checks
  */
  public abstract Object getRaw_unsafe(Persistent g);

 /**
  * Sets the field value, with locking,
  * for this <code>Column</code>.
  * 
  * @param g  the {@link Persistent} to modify
  * @param raw the value to set the field to 
  * @throws AccessPoemException 
  *         if the current <code>AccessToken</code> 
  *         does not confer write access rights
  * @throws ValidationPoemException 
  *         if the raw value is not valid
  */
  public abstract void setRaw(Persistent g, Object raw)
    throws AccessPoemException, ValidationPoemException;

 /**
  * Sets the field value, without locking,
  * for this <code>Column</code>.
  * 
  * @param g  the {@link Persistent} to modify
  * @param raw the value to set the field to 
  */
  public abstract void setRaw_unsafe(Persistent g, Object raw);

 /**
  * Retrieves the field value, with locking and  access control 
  * for this <code>Column</code>.
  * 
  * @param g  the {@link Persistent} to modify
  * @return either the value or what is represented by the value
  * @throws AccessPoemException 
  *         if the current <code>AccessToken</code> 
  *         does not confer read access rights
  * @throws PoemException 
  *         if any problem occurs
  */
  public abstract Object getCooked(Persistent g)
    throws AccessPoemException, PoemException;

 /**
  * Sets the field value, with locking, access control 
  * and validation for this <code>Column</code>.
  * 
  * @param g  the {@link Persistent} to modify
  * @param cooked  the value to set
  * @throws AccessPoemException 
  *         if the current <code>AccessToken</code> 
  *         does not confer read access rights
  * @throws ValidationPoemException 
  *         if the value is not valid
  */
  public abstract void setCooked(Persistent g, Object cooked)
    throws AccessPoemException, ValidationPoemException;

  /**
   * Thrown when any unforeseen problem arises loading a {@link Column}.
   */
  public static class LoadException extends UnexpectedExceptionPoemException {

    private Column<?> column;

    /**
     * Constructor.
     * @param column Column relevant to
     * @param problem the Exception
     */
    public LoadException(Column<?> column, Exception problem) {
      super(problem);
      this.column = column;
    }

    /** @return Returns the message */
    public String getMessage() {
      return "An unexpected problem arose loading "
        + column
        + " from the "
        + "database:\n"
        + subException;
    }

  
    /**
     * @return Returns the column.
     */
     @SuppressWarnings("rawtypes")
    protected Column getColumn() {
      return column;
    }
  }

  /**
   * Load a Persistent field from a column of a ResultSet.
   * 
   * TODO Double validation
   * @param rs A <code>ResultSet</code>containing the value(s) to load
   * @param rsCol The index in the <tt>ResultSet</tt> of this {link column}
   * @param g The {@link Persistent} to load db values into
   * @throws LoadException
   */
  void load_unsafe(ResultSet rs, int rsCol, Persistent g)
    throws LoadException {
    try {
      setRaw_unsafe(g, type.getRaw(rs, rsCol));
    } catch (Exception e) {
      throw new LoadException(this, e);
    }
  }

  /**
   * Set value in a PreparedStatement which is to be used to save to database.
   *
   * TODO Double validation
   * @param g The {@link Persistent} containing unsaved values
   * @param ps <tt>PreparedStatement</tt> to save this column
   * @param psCol index of this Column in the PreparedStatement
   */
  void save_unsafe(Persistent g, PreparedStatement ps, int psCol) {
    try {
      type.setRaw(ps, psCol, getRaw_unsafe(g));
    } catch (Exception e) {
      throw new FieldContentsPoemException(this, e); 
    }
  }

  // 
  // ============
  //  Operations
  // ============
  // 

  /**
   * Return a Field of the same type as this Column from the Persistent.
   * @param g the Persistent
   * @return a Field
   */
  public abstract Field<T> asField(Persistent g);

  /**
   * Return a Field of the same type as this Column with default attributes.
   * @return the empty Field
   */
  public Field<T> asEmptyField() {
    return new Field<T>((T) null, this);
  }

  /**
   * Thrown when any unforseen problem arises setting the value 
   * of a {@link Column}.
   */
  public static class SettingException extends NormalPoemException {
    /** The Persistent to which this Column belongs. */
    public Persistent persistent;
    /** The Column setting which caused the problem. */
    public Column<?> column;
    /** The description of the Column. */
    public String columnDesc;

    /**
     * Constructor.
     * @param persistent the Persistent with the problem
     * @param column he Column with the problem
     * @param trouble the problem
     */
    public SettingException(Persistent persistent, Column<?> column, Exception trouble) {
      super(trouble);
      this.persistent = persistent;
      this.column = column;
      columnDesc =
        "field `"
          + column.getDisplayName()
          + "' in object `"
          + persistent.displayString()
          + "' of type `"
          + column.getTable().getDisplayName()
          + "'";
    }

    /** @return the message */
    public String getMessage() {
      return "Unable to set " + columnDesc + "\n" + subException;
    }
  }

  /**
   * Set the value from its String representation, if possible.
   * 
   * Throws SettingException if the String value cannot be 
   * converted to the appropriate type 
   * @param g the Persistent to alter
   * @param rawString the String representation of the value to set
   */
  public void setRawString(Persistent g, String rawString) {
    Object raw;
    try {
      raw = getType().rawOfString(rawString);
    } catch (Exception e) {
      throw new SettingException(g, this, e);
    }
    setRaw(g, raw);
  }

  /**
   * Return an Enumeration of {@link Persistent}s from the 
   * Table this column refers to, if this is a reference column, 
   * otherwise the Empty Enumeration.
   * @param object A persistent of the type referred to by this column
   * @return an Enumeration {@link Persistent}s referencing this Column of the Persistent
   */
  public Enumeration<Persistent> referencesTo(Persistent object) {
    if (getType() instanceof PersistentReferencePoemType)
      if(((PersistentReferencePoemType) getType()).targetTable() == object.getTable()) { 
        if (getType() instanceof ReferencePoemType)
          return selectionWhereEq(object.troid());
        else if (getType() instanceof StringKeyReferencePoemType)
          return selectionWhereEq(
              object.getField(
                  ((StringKeyReferencePoemType)getType()).targetKeyName()).getRawString());
        else throw new PoemBugPoemException("Unanticipated type " + getType());
      }
    return new EmptyEnumeration<Persistent>();
  }

  /**
   * Ensures a row exists for which this column matches when compared with
   * the given {@link Persistent}.
   * 
   * The given object is used to create a new row if
   * necessary, in which case it will be assigned the next troid and
   * cached.
   * @param orCreate the Persistent to use as criteria and ensure
   * @return the existing or newly created Persistent
   */
  public Persistent ensure(Persistent orCreate) {
    Persistent there = firstWhereEq(getRaw_unsafe(orCreate));
    if (there == null) {
      getTable().create(orCreate);
      return orCreate;
    } else
      return there;
  }

  
  /**
   * Find the next free value in an Integer column.
   * 
   * This is not used in Melati, but is used in Bibliomania. 
   * Throws AppBugPoemException if this Column is not an Integer column.
   * 
   * @param whereClause
   * @return the incremented value 
   * @since 04/05/2000
   */
  public int firstFree(String whereClause) {
    if (! (getType() instanceof IntegerPoemType)) 
      throw new AppBugPoemException("firstFree called on a non Integer column");
    getTable().readLock();
    String querySelection = 
        quotedName
      + " + 1 "
      + "FROM "
      + getTable().quotedName()
      + " AS t1 "
      + "WHERE "
      + (whereClause == null ? "" : "(t1." + whereClause + ") AND ")
      + "NOT EXISTS ("
      + "SELECT * FROM "
      + getTable().quotedName()
      + " AS t2 "
      + "WHERE "
      + (whereClause == null ? "" : "(t2." + whereClause + ") AND ")
      + "t2."
      + quotedName
      + " = t1."
      + quotedName
      + " + 1) ";

    String query = getDatabase().getDbms().selectLimit(querySelection, 1); 
    ResultSet results = getDatabase().sqlQuery(query);
    try {
      if (results.next())
        return results.getInt(1);
      else
        return 0;
    } catch (SQLException e) {
      throw new SQLSeriousPoemException(e);
    }
  }

  /**
   * @param colDescs from DatabaseMetaData.getColumns, with cursor at current row
   * @throws SQLException 
   */
  public void unifyWithMetadata(ResultSet colDescs) throws SQLException {
    if (info == null)
      return;
    String remarks = colDescs.getString("REMARKS");
    if (getDescription() == null) {
      if (remarks != null && !remarks.trim().equals("")) {
        info.setDescription(remarks);
        getDatabase().log("Adding comment to column " + table + "." + name + 
            " from SQL metadata:" + remarks);
      }
    } else {
      if (!this.getDescription().equals(remarks)) {
        String sql = this.dbms().alterColumnAddCommentSQL(this, null); 
        if (sql != null)
          this.getDatabase().modifyStructure(sql);          
      }
    }
      
    
  }
  
}
