/**
 * 
 */
package org.melati.poem.dbms.test;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Hashtable;

/**
 *  A PreparedStatement which can throw SQLException on command.
 *  
 * @author timp
 * @since 10 Feb 2007
 * 
 */
public class ThrowingPreparedStatement extends Thrower implements PreparedStatement {
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

  PreparedStatement p = null;

  /**
   * @param statement
   */
  public ThrowingPreparedStatement(PreparedStatement statement) {
    this.p = statement;
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.sql.PreparedStatement#addBatch()
   */
  public void addBatch() throws SQLException {
    if (shouldThrow("addBatch"))
      throw new SQLException("PreparedStatement bombed");
    p.addBatch();
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.sql.PreparedStatement#clearParameters()
   */
  public void clearParameters() throws SQLException {
    if (shouldThrow("clearParameters"))
      throw new SQLException("PreparedStatement bombed");
    p.clearParameters();
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.sql.PreparedStatement#execute()
   */
  public boolean execute() throws SQLException {
    if (shouldThrow("execute"))
      throw new SQLException("PreparedStatement bombed");

    return p.execute();
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.sql.PreparedStatement#executeQuery()
   */
  public ResultSet executeQuery() throws SQLException {
    if (shouldThrow("executeQuery"))
      throw new SQLException("PreparedStatement bombed");

    return new ThrowingResultSet(p.executeQuery());
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.sql.PreparedStatement#executeUpdate()
   */
  public int executeUpdate() throws SQLException {
    if (shouldThrow("executeUpdate"))
      throw new SQLException("PreparedStatement bombed");

    return p.executeUpdate();
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.sql.PreparedStatement#getMetaData()
   */
  public ResultSetMetaData getMetaData() throws SQLException {
    if (shouldThrow("getMetaData"))
      throw new SQLException("PreparedStatement bombed");

    return new ThrowingResultSetMetaData(p.getMetaData());
  }

  /**
   * @todo 
   * {@inheritDoc}
   * 
   * @see java.sql.PreparedStatement#getParameterMetaData()
   */
  public ParameterMetaData getParameterMetaData() throws SQLException {
    if (shouldThrow("getParameterMetaData"))
      throw new SQLException("PreparedStatement bombed");

    return p.getParameterMetaData();
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.sql.PreparedStatement#setArray(int, java.sql.Array)
   */
  public void setArray(int i, Array x) throws SQLException {
    if (shouldThrow("setArray"))
      throw new SQLException("PreparedStatement bombed");
    p.setArray(i, x);

  }

  /**
   * {@inheritDoc}
   * 
   * @see java.sql.PreparedStatement#setAsciiStream(int, java.io.InputStream,
   *      int)
   */
  public void setAsciiStream(int parameterIndex, InputStream x, int length)
      throws SQLException {
    if (shouldThrow("setAsciiStream"))
      throw new SQLException("PreparedStatement bombed");
    p.setAsciiStream(parameterIndex, x, length);
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.sql.PreparedStatement#setBigDecimal(int, java.math.BigDecimal)
   */
  public void setBigDecimal(int parameterIndex, BigDecimal x)
      throws SQLException {
    if (shouldThrow("setBigDecimal"))
      throw new SQLException("PreparedStatement bombed");
    p.setBigDecimal(parameterIndex, x);
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.sql.PreparedStatement#setBinaryStream(int, java.io.InputStream,
   *      int)
   */
  public void setBinaryStream(int parameterIndex, InputStream x, int length)
      throws SQLException {
    if (shouldThrow("setBinaryStream"))
      throw new SQLException("PreparedStatement bombed");
    p.setBinaryStream(parameterIndex, x, length);
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.sql.PreparedStatement#setBlob(int, java.sql.Blob)
   */
  public void setBlob(int i, Blob x) throws SQLException {
    if (shouldThrow("setBlob"))
      throw new SQLException("PreparedStatement bombed");
    p.setBlob(i, x);
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.sql.PreparedStatement#setBoolean(int, boolean)
   */
  public void setBoolean(int parameterIndex, boolean x) throws SQLException {
    if (shouldThrow("setBoolean"))
      throw new SQLException("PreparedStatement bombed");
    p.setBoolean(parameterIndex, x);
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.sql.PreparedStatement#setByte(int, byte)
   */
  public void setByte(int parameterIndex, byte x) throws SQLException {
    if (shouldThrow("setByte"))
      throw new SQLException("PreparedStatement bombed");
    p.setByte(parameterIndex, x);
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.sql.PreparedStatement#setBytes(int, byte[])
   */
  public void setBytes(int parameterIndex, byte[] x) throws SQLException {
    if (shouldThrow("setBytes"))
      throw new SQLException("PreparedStatement bombed");
    p.setBytes(parameterIndex, x);
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.sql.PreparedStatement#setCharacterStream(int, java.io.Reader,
   *      int)
   */
  public void setCharacterStream(int parameterIndex, Reader reader, int length)
      throws SQLException {
    if (shouldThrow("setCharacterStream"))
      throw new SQLException("PreparedStatement bombed");
    p.setCharacterStream(parameterIndex, reader, length);
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.sql.PreparedStatement#setClob(int, java.sql.Clob)
   */
  public void setClob(int i, Clob x) throws SQLException {
    if (shouldThrow("setClob"))
      throw new SQLException("PreparedStatement bombed");
    p.setClob(i, x);
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.sql.PreparedStatement#setDate(int, java.sql.Date)
   */
  public void setDate(int parameterIndex, Date x) throws SQLException {
    if (shouldThrow("setDate"))
      throw new SQLException("PreparedStatement bombed");
    p.setDate(parameterIndex, x);
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.sql.PreparedStatement#setDate(int, java.sql.Date,
   *      java.util.Calendar)
   */
  public void setDate(int parameterIndex, Date x, Calendar cal)
      throws SQLException {
    if (shouldThrow("setDate"))
      throw new SQLException("PreparedStatement bombed");
    p.setDate(parameterIndex, x, cal);
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.sql.PreparedStatement#setDouble(int, double)
   */
  public void setDouble(int parameterIndex, double x) throws SQLException {
    if (shouldThrow("setDouble"))
      throw new SQLException("PreparedStatement bombed");
    p.setDouble(parameterIndex, x);
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.sql.PreparedStatement#setFloat(int, float)
   */
  public void setFloat(int parameterIndex, float x) throws SQLException {
    if (shouldThrow("setFloat"))
      throw new SQLException("PreparedStatement bombed");
    p.setFloat(parameterIndex, x);
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.sql.PreparedStatement#setInt(int, int)
   */
  public void setInt(int parameterIndex, int x) throws SQLException {
    if (shouldThrow("setInt"))
      throw new SQLException("PreparedStatement bombed");
    p.setInt(parameterIndex, x);
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.sql.PreparedStatement#setLong(int, long)
   */
  public void setLong(int parameterIndex, long x) throws SQLException {
    if (shouldThrow("setLong"))
      throw new SQLException("PreparedStatement bombed");
    p.setLong(parameterIndex, x);
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.sql.PreparedStatement#setNull(int, int)
   */
  public void setNull(int parameterIndex, int sqlType) throws SQLException {
    if (shouldThrow("setNull"))
      throw new SQLException("PreparedStatement bombed");
    p.setNull(parameterIndex, sqlType);
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.sql.PreparedStatement#setNull(int, int, java.lang.String)
   */
  public void setNull(int paramIndex, int sqlType, String typeName)
      throws SQLException {
    if (shouldThrow("setNull"))
      throw new SQLException("PreparedStatement bombed");
    p.setNull(paramIndex, sqlType, typeName);
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.sql.PreparedStatement#setObject(int, java.lang.Object)
   */
  public void setObject(int parameterIndex, Object x) throws SQLException {
    if (shouldThrow("setObject"))
      throw new SQLException("PreparedStatement bombed");
    p.setObject(parameterIndex, x);
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.sql.PreparedStatement#setObject(int, java.lang.Object, int)
   */
  public void setObject(int parameterIndex, Object x, int targetSqlType)
      throws SQLException {
    if (shouldThrow("setObject"))
      throw new SQLException("PreparedStatement bombed");
    p.setObject(parameterIndex, x, targetSqlType);
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.sql.PreparedStatement#setObject(int, java.lang.Object, int, int)
   */
  public void setObject(int parameterIndex, Object x, int targetSqlType,
      int scale) throws SQLException {
    if (shouldThrow("setObject"))
      throw new SQLException("PreparedStatement bombed");
    p.setObject(parameterIndex, x, targetSqlType, scale);
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.sql.PreparedStatement#setRef(int, java.sql.Ref)
   */
  public void setRef(int i, Ref x) throws SQLException {
    if (shouldThrow("setRef"))
      throw new SQLException("PreparedStatement bombed");
    p.setRef(i, x);
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.sql.PreparedStatement#setShort(int, short)
   */
  public void setShort(int parameterIndex, short x) throws SQLException {
    if (shouldThrow("setShort"))
      throw new SQLException("PreparedStatement bombed");
    p.setShort(parameterIndex, x);
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.sql.PreparedStatement#setString(int, java.lang.String)
   */
  public void setString(int parameterIndex, String x) throws SQLException {
    if (shouldThrow("setString"))
      throw new SQLException("PreparedStatement bombed");
    p.setString(parameterIndex, x);
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.sql.PreparedStatement#setTime(int, java.sql.Time)
   */
  public void setTime(int parameterIndex, Time x) throws SQLException {
    if (shouldThrow("setTime"))
      throw new SQLException("PreparedStatement bombed");
    p.setTime(parameterIndex, x);
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.sql.PreparedStatement#setTime(int, java.sql.Time,
   *      java.util.Calendar)
   */
  public void setTime(int parameterIndex, Time x, Calendar cal)
      throws SQLException {
    if (shouldThrow("setTime"))
      throw new SQLException("PreparedStatement bombed");
    p.setTime(parameterIndex, x, cal);
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.sql.PreparedStatement#setTimestamp(int, java.sql.Timestamp)
   */
  public void setTimestamp(int parameterIndex, Timestamp x) throws SQLException {
    if (shouldThrow("setTimestamp"))
      throw new SQLException("PreparedStatement bombed");
    p.setTimestamp(parameterIndex, x);
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.sql.PreparedStatement#setTimestamp(int, java.sql.Timestamp,
   *      java.util.Calendar)
   */
  public void setTimestamp(int parameterIndex, Timestamp x, Calendar cal)
      throws SQLException {
    if (shouldThrow("setTimestamp"))
      throw new SQLException("PreparedStatement bombed");
    p.setTimestamp(parameterIndex, x, cal);
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.sql.PreparedStatement#setURL(int, java.net.URL)
   */
  public void setURL(int parameterIndex, URL x) throws SQLException {
    if (shouldThrow("setURL"))
      throw new SQLException("PreparedStatement bombed");
    p.setURL(parameterIndex, x);
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.sql.PreparedStatement#setUnicodeStream(int, java.io.InputStream,
   *      int)
   */
  public void setUnicodeStream(int parameterIndex, InputStream x, int length)
      throws SQLException {
    if (shouldThrow("setUnicodeStream"))
      throw new SQLException("PreparedStatement bombed");
   // p.setUnicodeStream(parameterIndex, x, length);
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.sql.Statement#addBatch(java.lang.String)
   */
  public void addBatch(String sql) throws SQLException {
    if (shouldThrow("addBatch"))
      throw new SQLException("PreparedStatement bombed");
    p.addBatch();
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.sql.Statement#cancel()
   */
  public void cancel() throws SQLException {
    if (shouldThrow("cancel"))
      throw new SQLException("PreparedStatement bombed");
    p.cancel();
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.sql.Statement#clearBatch()
   */
  public void clearBatch() throws SQLException {
    if (shouldThrow("clearBatch"))
      throw new SQLException("PreparedStatement bombed");
    p.clearBatch();
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.sql.Statement#clearWarnings()
   */
  public void clearWarnings() throws SQLException {
    if (shouldThrow("clearWarnings"))
      throw new SQLException("PreparedStatement bombed");
    p.clearWarnings();
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.sql.Statement#close()
   */
  public void close() throws SQLException {
    if (shouldThrow("close"))
      throw new SQLException("PreparedStatement bombed");
    p.close();
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.sql.Statement#execute(java.lang.String)
   */
  public boolean execute(String sql) throws SQLException {
    if (shouldThrow("execute"))
      throw new SQLException("PreparedStatement bombed");

    return p.execute(sql);
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.sql.Statement#execute(java.lang.String, int)
   */
  public boolean execute(String sql, int autoGeneratedKeys) throws SQLException {
    if (shouldThrow("execute"))
      throw new SQLException("PreparedStatement bombed");

    return p.execute(sql, autoGeneratedKeys);
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.sql.Statement#execute(java.lang.String, int[])
   */
  public boolean execute(String sql, int[] columnIndexes) throws SQLException {
    if (shouldThrow("execute"))
      throw new SQLException("PreparedStatement bombed");

    return p.execute(sql, columnIndexes);
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.sql.Statement#execute(java.lang.String, java.lang.String[])
   */
  public boolean execute(String sql, String[] columnNames) throws SQLException {
    if (shouldThrow("execute"))
      throw new SQLException("PreparedStatement bombed");

    return p.execute(sql, columnNames);
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.sql.Statement#executeBatch()
   */
  public int[] executeBatch() throws SQLException {
    if (shouldThrow("executeBatch"))
      throw new SQLException("PreparedStatement bombed");

    return p.executeBatch();
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.sql.Statement#executeQuery(java.lang.String)
   */
  public ResultSet executeQuery(String sql) throws SQLException {
    if (shouldThrow("executeQuery"))
      throw new SQLException("PreparedStatement bombed");

    return new ThrowingResultSet(p.executeQuery(sql));
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.sql.Statement#executeUpdate(java.lang.String)
   */
  public int executeUpdate(String sql) throws SQLException {
    if (shouldThrow("executeUpdate"))
      throw new SQLException("PreparedStatement bombed");

    return p.executeUpdate(sql);
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.sql.Statement#executeUpdate(java.lang.String, int)
   */
  public int executeUpdate(String sql, int autoGeneratedKeys)
      throws SQLException {
    if (shouldThrow("executeUpdate"))
      throw new SQLException("PreparedStatement bombed");

    return p.executeUpdate(sql, autoGeneratedKeys);
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.sql.Statement#executeUpdate(java.lang.String, int[])
   */
  public int executeUpdate(String sql, int[] columnIndexes) throws SQLException {
    if (shouldThrow("executeUpdate"))
      throw new SQLException("PreparedStatement bombed");

    return p.executeUpdate(sql, columnIndexes);
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.sql.Statement#executeUpdate(java.lang.String, java.lang.String[])
   */
  public int executeUpdate(String sql, String[] columnNames)
      throws SQLException {
    if (shouldThrow("executeUpdate"))
      throw new SQLException("PreparedStatement bombed");

    return p.executeUpdate(sql, columnNames);
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.sql.Statement#getConnection()
   */
  public Connection getConnection() throws SQLException {
    if (shouldThrow("getConnection"))
      throw new SQLException("PreparedStatement bombed");

    return new ThrowingConnection(p.getConnection());
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.sql.Statement#getFetchDirection()
   */
  public int getFetchDirection() throws SQLException {
    if (shouldThrow("getFetchDirection"))
      throw new SQLException("PreparedStatement bombed");

    return p.getFetchDirection();
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.sql.Statement#getFetchSize()
   */
  public int getFetchSize() throws SQLException {
    if (shouldThrow("getFetchSize"))
      throw new SQLException("PreparedStatement bombed");

    return p.getFetchSize();
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.sql.Statement#getGeneratedKeys()
   */
  public ResultSet getGeneratedKeys() throws SQLException {
    if (shouldThrow("getGeneratedKeys"))
      throw new SQLException("PreparedStatement bombed");

    return new ThrowingResultSet(p.getGeneratedKeys());
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.sql.Statement#getMaxFieldSize()
   */
  public int getMaxFieldSize() throws SQLException {
    if (shouldThrow("getMaxFieldSize"))
      throw new SQLException("PreparedStatement bombed");

    return p.getMaxFieldSize();
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.sql.Statement#getMaxRows()
   */
  public int getMaxRows() throws SQLException {
    if (shouldThrow("getMaxRows"))
      throw new SQLException("PreparedStatement bombed");

    return p.getMaxRows();
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.sql.Statement#getMoreResults()
   */
  public boolean getMoreResults() throws SQLException {
    if (shouldThrow("getMoreResults"))
      throw new SQLException("PreparedStatement bombed");

    return p.getMoreResults();
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.sql.Statement#getMoreResults(int)
   */
  public boolean getMoreResults(int current) throws SQLException {
    if (shouldThrow("getMoreResults"))
      throw new SQLException("PreparedStatement bombed");

    return p.getMoreResults(current);
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.sql.Statement#getQueryTimeout()
   */
  public int getQueryTimeout() throws SQLException {
    if (shouldThrow("getQueryTimeout"))
      throw new SQLException("PreparedStatement bombed");

    return p.getQueryTimeout();
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.sql.Statement#getResultSet()
   */
  public ResultSet getResultSet() throws SQLException {
    if (shouldThrow("getResultSet"))
      throw new SQLException("PreparedStatement bombed");

    return new ThrowingResultSet(p.getResultSet());
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.sql.Statement#getResultSetConcurrency()
   */
  public int getResultSetConcurrency() throws SQLException {
    if (shouldThrow("getResultSetConcurrency"))
      throw new SQLException("PreparedStatement bombed");

    return p.getResultSetConcurrency();
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.sql.Statement#getResultSetHoldability()
   */
  public int getResultSetHoldability() throws SQLException {
    if (shouldThrow("getResultSetHoldability"))
      throw new SQLException("PreparedStatement bombed");

    return p.getResultSetHoldability();
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.sql.Statement#getResultSetType()
   */
  public int getResultSetType() throws SQLException {
    if (shouldThrow("getResultSetType"))
      throw new SQLException("PreparedStatement bombed");

    return p.getResultSetType();
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.sql.Statement#getUpdateCount()
   */
  public int getUpdateCount() throws SQLException {
    if (shouldThrow("getUpdateCount"))
      throw new SQLException("PreparedStatement bombed");

    return p.getUpdateCount();
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.sql.Statement#getWarnings()
   */
  public SQLWarning getWarnings() throws SQLException {
    if (shouldThrow("getWarnings"))
      throw new SQLException("PreparedStatement bombed");

    return p.getWarnings();
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.sql.Statement#setCursorName(java.lang.String)
   */
  public void setCursorName(String name) throws SQLException {
    if (shouldThrow("setCursorName"))
      throw new SQLException("PreparedStatement bombed");
    p.setCursorName(name);
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.sql.Statement#setEscapeProcessing(boolean)
   */
  public void setEscapeProcessing(boolean enable) throws SQLException {
    if (shouldThrow("setEscapeProcessing"))
      throw new SQLException("PreparedStatement bombed");
    p.setEscapeProcessing(enable);
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.sql.Statement#setFetchDirection(int)
   */
  public void setFetchDirection(int direction) throws SQLException {
    if (shouldThrow("setFetchDirection"))
      throw new SQLException("PreparedStatement bombed");
    p.setFetchDirection(direction);
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.sql.Statement#setFetchSize(int)
   */
  public void setFetchSize(int rows) throws SQLException {
    if (shouldThrow("setFetchSize"))
      throw new SQLException("PreparedStatement bombed");
    p.setFetchSize(rows);
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.sql.Statement#setMaxFieldSize(int)
   */
  public void setMaxFieldSize(int max) throws SQLException {
    if (shouldThrow("setMaxFieldSize"))
      throw new SQLException("PreparedStatement bombed");
    p.setMaxFieldSize(max);
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.sql.Statement#setMaxRows(int)
   */
  public void setMaxRows(int max) throws SQLException {
    if (shouldThrow("setMaxRows"))
      throw new SQLException("PreparedStatement bombed");
    p.setMaxRows(max);
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.sql.Statement#setQueryTimeout(int)
   */
  public void setQueryTimeout(int seconds) throws SQLException {
    if (shouldThrow("setQueryTimeout"))
      throw new SQLException("PreparedStatement bombed");
    p.setQueryTimeout(seconds);
  }

}
