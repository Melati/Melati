package org.melati.poem;

public class UnsupportedTypePoemException extends SeriousPoemException {
  public String tableName;
  public String columnName;
  public short sqlTypeCode;
  public String sqlTypeName;
  public String dbTypeName;

  public UnsupportedTypePoemException(String tableName, String columnName,
                                      short sqlTypeCode, String sqlTypeName,
                                      String dbTypeName) {
    this.tableName = tableName;;
    this.columnName = columnName;
    this.sqlTypeCode = sqlTypeCode;
    this.sqlTypeName = sqlTypeName;
    this.dbTypeName = dbTypeName;
  }

  public UnsupportedTypePoemException(String sqlTypeName) {
    this(null, null, (short)0, sqlTypeName, null);
  }

  public String getMessage() {
    return
        "Column `" + columnName + "' of table `" + tableName + "'" +
        " has the unsupported type " + sqlTypeName +
        " (SQL code " + sqlTypeCode +
        ", db-specific name `" + dbTypeName + "'";
  }
}
