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

  void readColumnInfo(ColumnInfo info) {
    refineType(BasePoemType.ofColumnInfo(getDatabase(),
                                         info.dataSnapshot()),
               DefinitionSource.infoTables);
  }

  void setColumnInfo(ColumnInfo columnInfo) {
    info = columnInfo;
  }

  void writeColumnInfo(ColumnInfo info) {
    info.setName(getName());
    info.setDisplayname(getName());
    info.setPrimarydisplay(false);
    info.setTableinfoTroid(table.tableInfoID());
    getType().saveColumnInfo(info);
  }

  void createColumnInfo() throws PoemException {
    if (info == null) {
      final Column _this = this; 
      info =
          (ColumnInfo)getDatabase().getColumnInfoTable().create(
              new Initialiser() {
                public void init(Persistent g) throws AccessPoemException {
                  _this.writeColumnInfo((ColumnInfo)g);
                }
              });
    }
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

  public final boolean isPrimaryDisplay() {
    return info == null ? false : info.getPrimarydisplay().booleanValue();
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

  public final boolean isNormal() {
    return !isTroidColumn() && !isDeletedColumn();
  }

  public final String getRenderInfo() {
    return info.getRenderinfo();
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
                        getTable().troidSelection(clause, false);
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

  public Field asField(Persistent g) {
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

/*
  public void rename(String newName)
      throws AccessPoemException, CannotBeInSessionPoemException,
             DuplicateColumnNamePoemException, AlterDSDAttemptPoemException {

    if (isDefinedInDSD)
      throw new AlterDSDAttemptPoemException();

    PoemThread.accessToken().assertHasCapability(attributes.getCanAlter());

    table.rename(this, newName);
    try {
      attributes.setName(newName);
    }
    catch (Exception e) {
      throw new UnexpectedExceptionPoemException(e);
    }
    name = newName;
  }
*/
}
