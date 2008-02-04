package org.melati.poem.dbms.test.sql;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;


/**
 * A {@link ResultSetMetaData} decorated to throw an {@SQLException} on command.
 * 
 * @author timp
 * @since 12 Feb 2007
 *
 */
public class ThrowingResultSetMetaData extends Thrower implements ResultSetMetaData {
  final static String className = ThrowingResultSetMetaData.class.getName() + ".";
  public static void startThrowing(String methodName) {
    Thrower.startThrowing(className  +  methodName);
  }
  public static void startThrowingAfter(String methodName, int goes) {
    Thrower.startThrowingAfter(className  +  methodName, goes);
  }
  public static void stopThrowing(String methodName) {
    Thrower.stopThrowing(className  +  methodName);
  }
  public static boolean shouldThrow(String methodName) { 
    return Thrower.shouldThrow(className  +  methodName);
  }

  ResultSetMetaData it = null;

  /**
   * Constructor.
   * @param r to decorate
   */
  public ThrowingResultSetMetaData(ResultSetMetaData r) {
    this.it = r;
  }

  public String getCatalogName(int column) throws SQLException {
    if (shouldThrow("getCatalogName"))
      throw new SQLException("ResultSetMetaData bombed");
    return it.getCatalogName(column);
  }

  public String getColumnClassName(int column) throws SQLException {
    if (shouldThrow("getColumnClassName"))
      throw new SQLException("ResultSetMetaData bombed");
    return it.getColumnClassName(column);
  }

  public int getColumnCount() throws SQLException {
    if (shouldThrow("getColumnCount"))
      throw new SQLException("ResultSetMetaData bombed");
    return it.getColumnCount();
  }

  public int getColumnDisplaySize(int column) throws SQLException {
    if (shouldThrow("getColumnDisplaySize"))
      throw new SQLException("ResultSetMetaData bombed");
    return it.getColumnDisplaySize(column);
  }

  public String getColumnLabel(int column) throws SQLException {
    if (shouldThrow("getColumnLabel"))
      throw new SQLException("ResultSetMetaData bombed");
    return it.getColumnLabel(column);
  }

  public String getColumnName(int column) throws SQLException {
    if (shouldThrow("getColumnName"))
      throw new SQLException("ResultSetMetaData bombed");
    return it.getColumnName(column);
  }

  public int getColumnType(int column) throws SQLException {
    if (shouldThrow("getColumnType"))
      throw new SQLException("ResultSetMetaData bombed");
    return it.getColumnType(column);
  }

  public String getColumnTypeName(int column) throws SQLException {
    if (shouldThrow("getColumnTypeName"))
      throw new SQLException("ResultSetMetaData bombed");
    return it.getColumnTypeName(column);
 }

  public int getPrecision(int column) throws SQLException {
    if (shouldThrow("getPrecision"))
      throw new SQLException("ResultSetMetaData bombed");
    return it.getPrecision(column);
  }

  public int getScale(int column) throws SQLException {
    if (shouldThrow("getScale"))
      throw new SQLException("ResultSetMetaData bombed");
    return it.getScale(column);
  }

  public String getSchemaName(int column) throws SQLException {
    if (shouldThrow("getSchemaName"))
      throw new SQLException("ResultSetMetaData bombed");
    return it.getSchemaName(column);
  }

  public String getTableName(int column) throws SQLException {
    if (shouldThrow("getTableName"))
      throw new SQLException("ResultSetMetaData bombed");
    return it.getTableName(column);
  }

  public boolean isAutoIncrement(int column) throws SQLException {
    if (shouldThrow("isAutoIncrement"))
      throw new SQLException("ResultSetMetaData bombed");
    return it.isAutoIncrement(column);
  }

  public boolean isCaseSensitive(int column) throws SQLException {
    if (shouldThrow("isCaseSensitive"))
      throw new SQLException("ResultSetMetaData bombed");
    return it.isCaseSensitive(column);
  }

  public boolean isCurrency(int column) throws SQLException {
    if (shouldThrow("isCurrency"))
      throw new SQLException("ResultSetMetaData bombed");
    return it.isCurrency(column);
  }

  public boolean isDefinitelyWritable(int column) throws SQLException {
    if (shouldThrow("isDefinitelyWritable"))
      throw new SQLException("ResultSetMetaData bombed");
    return it.isDefinitelyWritable(column);
  }

  public int isNullable(int column) throws SQLException {
    if (shouldThrow("isNullable"))
      throw new SQLException("ResultSetMetaData bombed");
    return it.isNullable(column);
  }

  public boolean isReadOnly(int column) throws SQLException {
    if (shouldThrow("isReadOnly"))
      throw new SQLException("ResultSetMetaData bombed");
    return it.isReadOnly(column);
  }

  public boolean isSearchable(int column) throws SQLException {
    if (shouldThrow("isSearchable"))
      throw new SQLException("ResultSetMetaData bombed");
    return it.isSearchable(column);
  }

  public boolean isSigned(int column) throws SQLException {
    if (shouldThrow("isSigned"))
      throw new SQLException("ResultSetMetaData bombed");
    return it.isSigned(column);
  }

  public boolean isWritable(int column) throws SQLException {
    if (shouldThrow("isWritable"))
      throw new SQLException("ResultSetMetaData bombed");
    return it.isWritable(column);
  }
  /**
   * JDK 1.6
   * @param c
   */
  public boolean isWrapperFor(Class c) {
    Class c2 = c;
    c = c2;
    return false;
  }
  /**
   * JDK 1.6
   * @param c
   */
  public Object unwrap(Class c) {
    Class c2 = c;
    c = c2;
    return null;
  }

}
