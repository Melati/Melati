package org.melati.poem.dbms.test;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Hashtable;

/**
 * A {@link ResultSetMetaData} decorated to throw an {@SQLException} on command.
 * 
 * @author timp
 * @since 12 Feb 2007
 *
 */
public class ThrowingResultSetMetaData implements ResultSetMetaData {
  static Hashtable throwers = new Hashtable();
  public static void startThrowing(String methodName) {
    throwers.put(methodName, Boolean.TRUE);
  }
  public static void stopThrowing(String methodName) {
    throwers.put(methodName, Boolean.FALSE);
  }
  public static boolean shouldThrow(String methodName) { 
    if (throwers.get(methodName) == null || throwers.get(methodName) == Boolean.FALSE)
      return false;
    return true;
  }

  ResultSetMetaData resultSetMetaData = null;

  public ThrowingResultSetMetaData(ResultSetMetaData r) {
    this.resultSetMetaData = r;
  }

  public String getCatalogName(int column) throws SQLException {
    if (shouldThrow("getCatalogName"))
      throw new SQLException("ResultSetMetaData bombed");
    return resultSetMetaData.getCatalogName(column);
  }

  public String getColumnClassName(int column) throws SQLException {
    if (shouldThrow("getColumnClassName"))
      throw new SQLException("ResultSetMetaData bombed");
    return resultSetMetaData.getColumnClassName(column);
  }

  public int getColumnCount() throws SQLException {
    if (shouldThrow("getColumnCount"))
      throw new SQLException("ResultSetMetaData bombed");
    return resultSetMetaData.getColumnCount();
  }

  public int getColumnDisplaySize(int column) throws SQLException {
    if (shouldThrow("getColumnDisplaySize"))
      throw new SQLException("ResultSetMetaData bombed");
    return resultSetMetaData.getColumnDisplaySize(column);
  }

  public String getColumnLabel(int column) throws SQLException {
    if (shouldThrow("getColumnLabel"))
      throw new SQLException("ResultSetMetaData bombed");
    return resultSetMetaData.getColumnLabel(column);
  }

  public String getColumnName(int column) throws SQLException {
    if (shouldThrow("getColumnName"))
      throw new SQLException("ResultSetMetaData bombed");
    return resultSetMetaData.getColumnName(column);
  }

  public int getColumnType(int column) throws SQLException {
    if (shouldThrow("getColumnType"))
      throw new SQLException("ResultSetMetaData bombed");
    return resultSetMetaData.getColumnType(column);
  }

  public String getColumnTypeName(int column) throws SQLException {
    if (shouldThrow("getColumnTypeName"))
      throw new SQLException("ResultSetMetaData bombed");
    return resultSetMetaData.getColumnTypeName(column);
 }

  public int getPrecision(int column) throws SQLException {
    if (shouldThrow("getPrecision"))
      throw new SQLException("ResultSetMetaData bombed");
    return resultSetMetaData.getPrecision(column);
  }

  public int getScale(int column) throws SQLException {
    if (shouldThrow("getScale"))
      throw new SQLException("ResultSetMetaData bombed");
    return resultSetMetaData.getScale(column);
  }

  public String getSchemaName(int column) throws SQLException {
    if (shouldThrow("getSchemaName"))
      throw new SQLException("ResultSetMetaData bombed");
    return resultSetMetaData.getSchemaName(column);
  }

  public String getTableName(int column) throws SQLException {
    if (shouldThrow("getTableName"))
      throw new SQLException("ResultSetMetaData bombed");
    return resultSetMetaData.getTableName(column);
  }

  public boolean isAutoIncrement(int column) throws SQLException {
    if (shouldThrow("isAutoIncrement"))
      throw new SQLException("ResultSetMetaData bombed");
    return resultSetMetaData.isAutoIncrement(column);
  }

  public boolean isCaseSensitive(int column) throws SQLException {
    if (shouldThrow("isCaseSensitive"))
      throw new SQLException("ResultSetMetaData bombed");
    return resultSetMetaData.isCaseSensitive(column);
  }

  public boolean isCurrency(int column) throws SQLException {
    if (shouldThrow("isCurrency"))
      throw new SQLException("ResultSetMetaData bombed");
    return resultSetMetaData.isCurrency(column);
  }

  public boolean isDefinitelyWritable(int column) throws SQLException {
    if (shouldThrow("isDefinitelyWritable"))
      throw new SQLException("ResultSetMetaData bombed");
    return resultSetMetaData.isDefinitelyWritable(column);
  }

  public int isNullable(int column) throws SQLException {
    if (shouldThrow("isNullable"))
      throw new SQLException("ResultSetMetaData bombed");
    return resultSetMetaData.isNullable(column);
  }

  public boolean isReadOnly(int column) throws SQLException {
    if (shouldThrow("isReadOnly"))
      throw new SQLException("ResultSetMetaData bombed");
    return resultSetMetaData.isReadOnly(column);
  }

  public boolean isSearchable(int column) throws SQLException {
    if (shouldThrow("isSearchable"))
      throw new SQLException("ResultSetMetaData bombed");
    return resultSetMetaData.isSearchable(column);
  }

  public boolean isSigned(int column) throws SQLException {
    if (shouldThrow("isSigned"))
      throw new SQLException("ResultSetMetaData bombed");
    return resultSetMetaData.isSigned(column);
  }

  public boolean isWritable(int column) throws SQLException {
    if (shouldThrow("isWritable"))
      throw new SQLException("ResultSetMetaData bombed");
    return resultSetMetaData.isWritable(column);
  }

}
