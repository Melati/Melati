package org.melati.poem;

import java.sql.*;
import java.util.*;
import org.melati.util.*;

public abstract class Column implements FieldAttributes {
  private Table table = null;
  private String name;
  private String quotedName;
  private PoemType type;
  private DefinitionSource definitionSource;
  private ColumnInfo info = null;

  public Column(Table table, String name, PoemType type,
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
    refineType(BasePoemType.ofColumnInfo(getDatabase(), columnInfo),
               DefinitionSource.infoTables);
    columnInfo.setColumn(this);
    if (columnInfo.getPrimarydisplay().booleanValue())
      table.setDisplayColumn(this);
    info = columnInfo;
    table.notifyColumnInfo(info);
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

  protected boolean defaultRecordDisplay() {
    return true;
  }

  protected boolean defaultSummaryDisplay() {
    return !isTroidColumn();
  }

  protected boolean defaultSearchCriterion() {
    return !isTroidColumn();
  }

  protected boolean defaultIndexed() {
    return isTroidColumn();
  }

  protected boolean defaultUnique() {
    return isTroidColumn();
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
                  i.setRecorddisplay(defaultRecordDisplay());
                  i.setSummarydisplay(defaultSummaryDisplay());
                  i.setSearchcriterion(defaultSearchCriterion());
                  i.setIndexed(defaultIndexed());
                  i.setUnique(defaultUnique());
                  getType().saveColumnInfo(i);
                }
              });

      // FIXME repeating this in several places is a bad sign

      if (defaultPrimaryDisplay())
	table.setDisplayColumn(this);
    }
  }

  void unifyWithIndex(ResultSet index)
      throws SQLException, IndexUniquenessPoemException {
    boolean indexUnique = !index.getBoolean("NON_UNIQUE");
    if (indexUnique != getUnique())
      throw new IndexUniquenessPoemException(
          this, index.getString("INDEX_NAME"), getUnique());
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

  public final String quotedName() {
    return quotedName;
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

  public final ColumnInfo getColumnInfo() {
    return info;
  }

  public final boolean getPrimaryDisplay() {
    return info == null ? false : info.getPrimarydisplay().booleanValue();
  }

  public final void setPrimaryDisplay(boolean flag) {
    if (info != null)
      info.setPrimarydisplay(flag);
  }

  public final boolean getUserEditable() {
    return !isTroidColumn() &&
           (info == null ? true : info.getUsereditable().booleanValue());
  }

  public final boolean getUserCreateable() {
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

  public final boolean getIndexed() {
    return getUnique() || info.getIndexed().booleanValue();
  }

  public final boolean getUnique() {
    return isTroidColumn() || info.getUnique().booleanValue();
  }

  public final String getRenderInfo() {
    return info.getRenderinfo();
  }

  public final Integer getDisplayOrderPriority() {
    return info == null ? null : info.getDisplayorderpriority();
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

  Enumeration selectionWhereEq(Object ident, boolean resolved) {
    try {
      String clause = quotedName + " = " + type.quotedIdent(ident);
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

  public abstract Object getIdent(Persistent g)
      throws AccessPoemException;
  public abstract Object getIdent_unsafe(Persistent g);
  public abstract void setIdent(Persistent g, Object ident)
      throws AccessPoemException, ValidationPoemException;
  public abstract void setIdent_unsafe(Persistent g, Object ident);
  public abstract Object getValue(Persistent g)
      throws AccessPoemException, PoemException;
  public abstract void setValue(Persistent g, Object value)
      throws AccessPoemException, ValidationPoemException;

  public void load_unsafe(ResultSet rs, int rsCol, Persistent g)
      throws ParsingPoemException, ValidationPoemException {
    // FIXME double validation
    setIdent_unsafe(g, type.getIdent(rs, rsCol));
  }

  public void save_unsafe(Persistent g, PreparedStatement ps, int psCol) {
    // FIXME double validation
    type.setIdent(ps, psCol, getIdent_unsafe(g));
  }

  // 
  // ============
  //  Operations
  // ============
  // 

  public Field asField(Persistent g) {
    return Field.of(g, this);
  }

  public Field asEmptyField() {
    return new Field((Object)null, this);
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

  Persistent ensure(Persistent orCreate) {
    Persistent there = firstWhereEq(getIdent_unsafe(orCreate));
    if (there == null) {
      getTable().create(orCreate);
      return orCreate;
    }
    else
      return there;
  }
}
