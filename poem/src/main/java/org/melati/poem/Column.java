package org.melati.poem;

import java.sql.*;
import java.util.*;
import org.melati.util.*;

public abstract class Column {
  private Table table = null;
  private String name;
  private PoemType type;
  private DefinitionSource definitionSource;
  private ColumnInfo info = null;

  public Column(Table table, String name, PoemType type,
                DefinitionSource definitionSource) {
    this.table = table;
    this.name = name;
    this.type = type;
    this.definitionSource = definitionSource;
  }

  // 
  // ================
  //  Initialisation
  // ================
  // 

  void refineType(PoemType refined, DefinitionSource source) {
    if (type.canBe(refined))
      type = refined;
    else
      throw new TypeDefinitionMismatchException(this, refined, source);
  }

  void assertMatches(ResultSet colDesc)
      throws SQLException, TypeDefinitionMismatchException {
    PoemType dbType = getDatabase().defaultPoemTypeOfColumnMetaData(colDesc);
    if (!dbType.canBe(type))
      throw new TypeDefinitionMismatchException(this, dbType,
                                                DefinitionSource.sqlMetaData);
  }

  void setColumnInfo(ColumnInfo columnInfo) {
    refineType(BasePoemType.ofColumnInfo(getDatabase(),
                                         columnInfo.dataSnapshot()),
               DefinitionSource.infoTables);
    columnInfo.setColumn(this);
    if (columnInfo.getPrimarydisplay().booleanValue())
      table.setDisplayColumn(this);
    info = columnInfo;
  }

  protected boolean defaultPrimaryDisplay() {
    return false;
  }

  protected Integer defaultDisplayOrderPriority() {
    return null;
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

  protected boolean defaultDisplayable() {
    return true;
  }

  protected boolean defaultIndexed() {
    return isTroidColumn();
  }

  protected boolean defaultUnique() {
    return false;
  }

  void createColumnInfo() throws PoemException {
    if (info == null) {
      info =
          (ColumnInfo)getDatabase().getColumnInfoTable().create(
              new Initialiser() {
                public void init(Persistent g) throws AccessPoemException {
                  ColumnInfo i = (ColumnInfo)g;
                  i.setName(getName());
                  i.setDisplayname(defaultDisplayName());
                  i.setDisplayorder(defaultDisplayOrder());
                  i.setDescription(defaultDescription());
                  i.setPrimarydisplay(defaultPrimaryDisplay());
                  i.setDisplayorderpriority(defaultDisplayOrderPriority());
                  i.setTableinfoTroid(table.tableInfoID());
                  i.setUsereditable(defaultUserEditable());
                  i.setDisplayable(defaultDisplayable());
                  i.setIndexed(defaultIndexed());
                  i.setUnique(defaultUnique());
                  getType().saveColumnInfo(i);
                }
              });
    }
  }

  void unifyWithIndex(ResultSet index)
      throws SQLException, IndexUniquenessPoemException {
    boolean indexUnique = !index.getBoolean("NON_UNIQUE");
    if (indexUnique != isUnique())
      throw new IndexUniquenessPoemException(
          this, index.getString("INDEX_NAME"), isUnique());
  }

  // 
  // ===========
  //  Accessors
  // ===========
  // 

  public final Database getDatabase() {
    return getTable().getDatabase();
  }

  public final Table getTable() {
    return table;
  }

  final void setTable(Table table) {
    this.table = table;
  }

  public final String getName() {
    return name;
  }

  public final String getDisplayName() {
    return info.getDisplayname();
  }

  public final String getDescription() {
    return info.getDescription();
  }

  /**
   * The troid (<TT>id</TT>) of the column's entry in the <TT>columninfo</TT>
   * table.  It will always have one (except during initialisation, which the
   * application programmer will never see).
   */

  final Integer columnInfoID() {
    return info == null ? null : info.troid();
  }

  public final boolean isPrimaryDisplay() {
    return info == null ? false : info.getPrimarydisplay().booleanValue();
  }

  public final void setPrimaryDisplay(boolean flag) {
    if (info != null)
      info.setPrimarydisplay(flag);
  }

  public final boolean isUserEditable() {
    return !isTroidColumn() &&
           (info == null ? true : info.getUsereditable().booleanValue());
  }

  public final boolean isUserCreateable() {
    return !isTroidColumn();
  }

  public final PoemType getType() {
    return type;
  }

  public final boolean isTroidColumn() {
    return getType() instanceof TroidPoemType;
  }

  public final boolean isDeletedColumn() {
    return getType() instanceof DeletedPoemType;
  }

  public final boolean isIndexed() {
    return isUnique() || info.getIndexed().booleanValue();
  }

  public final boolean isUnique() {
    return isTroidColumn() || info.getUnique().booleanValue();
  }

  public final String getRenderInfo() {
    return info.getRenderinfo();
  }

  public final Integer getDisplayOrderPriority() {
    return info.getDisplayorderpriority();
  }

  // 
  // ===========
  //  Utilities
  // ===========
  // 

  public String toString() {
    return
        table.getName() + "." + name + ": " + getType().toString() +
        " (from " + definitionSource + ")";
  }

  public void dump() {
    System.out.println(toString());
  }

  private String _quotedName(String name) {
    return getDatabase()._quotedName(name);
  }

  Enumeration selectionWhereEq(Object ident, boolean resolved) {
    try {
      String clause = _quotedName(name) + " = " + type.quotedIdent(ident);
      return resolved ? getTable().selection(clause) :
                        getTable().troidSelection(clause, null, false);
    }
    catch (SQLPoemException e) {
      throw new UnexpectedExceptionPoemException(e);
    }
  }

  public Enumeration selectionWhereEq(Object ident) {
    return selectionWhereEq(ident, true);
  }

  Enumeration troidSelectionWhereEq(Object ident) {
    return selectionWhereEq(ident, false);
  }

  public Persistent firstWhereEq(Object ident) {
    Enumeration them = selectionWhereEq(ident);
    return them.hasMoreElements() ? (Persistent)them.nextElement() : null;
  }

  // 
  // =======================================
  //  Reading/setting the column in records
  // =======================================
  // 

  protected abstract Object getIdent(Data data);
  protected abstract void setIdent(Data data, Object ident);

  public Object getValue(Data data) throws PoemException {
    return type.valueOfIdent(getIdent(data));
  }

  public abstract Object getIdent(Persistent g)
      throws AccessPoemException;
  public abstract void setIdent(Persistent g, Object ident)
      throws AccessPoemException, ValidationPoemException;
  public abstract Object getValue(Persistent g)
      throws AccessPoemException, PoemException;
  public abstract void setValue(Persistent g, Object value)
      throws AccessPoemException, ValidationPoemException;

  public void load(ResultSet rs, int rsCol, Data data)
      throws ParsingPoemException, ValidationPoemException {
    // FIXME double validation
    setIdent(data, type.getIdent(rs, rsCol));
  }

  public void save(Data data, PreparedStatement ps, int psCol) {
    // FIXME double validation
    type.setIdent(ps, psCol, getIdent(data));
  }

  // 
  // ============
  //  Operations
  // ============
  // 

  public Field asField(Persistent g) throws AccessPoemException {
    return new Field(getIdent(g), this);
  }

  public void setIdentString(Persistent g, String identString)
      throws ParsingPoemException, ValidationPoemException,
             AccessPoemException {
    setIdent(g, getType().identOfString(identString));
  }

  public Enumeration referencesTo(Persistent object) {
    return
        getType() instanceof ReferencePoemType &&
            ((ReferencePoemType)getType()).targetTable() == object.getTable() ?
          selectionWhereEq(object.troid()) :
          EmptyEnumeration.it;
  }
}
