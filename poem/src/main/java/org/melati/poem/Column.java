package org.melati.poem;

import java.sql.*;
import java.util.*;

public abstract class Column {
  private Table table = null;
  private String name;
  private int dbIndex = -1;
  private PoemType type;
  private DefinitionSource definitionSource;
  private Integer columnInfoID = null;

  public Column(Table table, String name, PoemType type,
                DefinitionSource definitionSource) {
    this.table = table;
    this.name = name;
    this.type = type;
    this.definitionSource = definitionSource;
  }

  public final Table getTable() {
    return table;
  }

  final void setTable(Table table) {
    this.table = table;
  }

  public final Database getDatabase() {
    return getTable().getDatabase();
  }

  public final String getName() {
    return name;
  }

  public final boolean isTroidColumn() {
    return getType() instanceof TroidPoemType;
  }

  public final int getDBIndex() {
    return dbIndex;
  }

  void setDBIndex(int dbIndex) {
    this.dbIndex = dbIndex;
  }

  public final PoemType getType() {
    return type;
  }

  protected abstract Object getIdent(Fields fields);
  protected abstract void setIdent(Fields fields, Object ident);

  public Object getValue(Fields fields) throws PoemException {
    return type.valueOfIdent(getIdent(fields));
  }

  public abstract Object getIdent(Persistent g)
      throws AccessPoemException;
  public abstract void setIdent(Persistent g, Object ident)
      throws AccessPoemException, ValidationPoemException;
  public abstract Object getValue(Persistent g)
      throws AccessPoemException, PoemException;
  public abstract void setValue(Persistent g, Object value)
      throws AccessPoemException, ValidationPoemException;

  public void load(ResultSet rs, int rsCol, Fields fields)
      throws ParsingPoemException, ValidationPoemException {
    // FIXME double validation
    setIdent(fields, type.getIdent(rs, rsCol));
  }

  public void save(Fields fields, PreparedStatement ps, int psCol) {
    // FIXME double validation
    type.setIdent(ps, psCol, getIdent(fields));
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

  public void dump() {
    System.out.println(toString());
  }

  void refineType(PoemType refined, DefinitionSource source) {
    if (type.canBe(refined))
      type = refined;
    else
      throw new TypeDefinitionMismatchException(this, refined, source);
  }

  public void assertMatches(ResultSet colDesc)
      throws SQLException, TypeDefinitionMismatchException {
    PoemType dbType = getDatabase().defaultPoemTypeOfColumnMetaData(colDesc);
    if (!dbType.canBe(type))
      throw new TypeDefinitionMismatchException(this, dbType,
                                                DefinitionSource.sqlMetaData);
  }

  void readColumnInfo(ColumnInfo info) {
    refineType(BasePoemType.ofColumnInfo(getDatabase(),
                                         info.fieldsSnapshot()),
               DefinitionSource.infoTables);
  }

  void writeColumnInfo(ColumnInfo info) {
    info.setName(getName());
    info.setTableinfoTroid(getTable().getTableInfoID());
    getType().saveColumnInfo(info);
  }

  Integer getColumnInfoID() {
    return columnInfoID;
  }

  void setColumnInfoID(Integer columnInfoID) {
    this.columnInfoID = columnInfoID;
  }

  void createColumnInfo() throws PoemException {
    if (columnInfoID == null) {
      System.err.println("*** making c i for " + name);
      final Column _this = this; 
      setColumnInfoID(
          getDatabase().getColumnInfoTable().create(
              new Initialiser() {
                public void init(Persistent g) throws AccessPoemException {
                  _this.writeColumnInfo((ColumnInfo)g);
                }
              }).getTroid());
    }
  }

  private String _quotedName(String name) {
    return getDatabase()._quotedName(name);
  }

  public Enumeration selectionWhereEq(Object ident) throws SQLPoemException {
    return getTable().selection(_quotedName(name) + " = " +
                                type.quotedIdent(ident));
  }

  public String toString() {
    return
        table.getName() + "." + name + ": " + getType().toString() +
        " (from " + definitionSource + ")";
  }
}
