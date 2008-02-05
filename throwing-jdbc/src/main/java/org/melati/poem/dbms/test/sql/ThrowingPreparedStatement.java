/*
 * $Source$
 * $Revision$
 *
 * Copyright (C) 2007 Tim Pizey
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
 *     Tim Pizey <timp At paneris.org>
 *     http://paneris.org/~timp
 */
package org.melati.poem.dbms.test.sql;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.NClob;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;


/**
 *  A PreparedStatement which can throw SQLException on command.
 *  
 * @author timp
 * @since 10 Feb 2007
 * 
 */
public class ThrowingPreparedStatement extends Thrower implements PreparedStatement {
  
  final static String className = ThrowingPreparedStatement.class.getName() + ".";
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

  PreparedStatement it = null;

  /**
   * @param statement
   */
  public ThrowingPreparedStatement(PreparedStatement statement) {
    this.it = statement;
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.sql.PreparedStatement#addBatch()
   */
  public void addBatch() throws SQLException {
    if (shouldThrow("addBatch"))
      throw new SQLException("PreparedStatement bombed");
    it.addBatch();
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.sql.PreparedStatement#clearParameters()
   */
  public void clearParameters() throws SQLException {
    if (shouldThrow("clearParameters"))
      throw new SQLException("PreparedStatement bombed");
    it.clearParameters();
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.sql.PreparedStatement#execute()
   */
  public boolean execute() throws SQLException {
    if (shouldThrow("execute"))
      throw new SQLException("PreparedStatement bombed");

    return it.execute();
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.sql.PreparedStatement#executeQuery()
   */
  public ResultSet executeQuery() throws SQLException {
    if (shouldThrow("executeQuery"))
      throw new SQLException("PreparedStatement bombed");

    return new ThrowingResultSet<Object>(it.executeQuery());
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.sql.PreparedStatement#executeUpdate()
   */
  public int executeUpdate() throws SQLException {
    if (shouldThrow("executeUpdate"))
      throw new SQLException("PreparedStatement bombed");

    return it.executeUpdate();
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.sql.PreparedStatement#getMetaData()
   */
  public ResultSetMetaData getMetaData() throws SQLException {
    if (shouldThrow("getMetaData"))
      throw new SQLException("PreparedStatement bombed");

    return new ThrowingResultSetMetaData(it.getMetaData());
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.sql.PreparedStatement#getParameterMetaData()
   */
  public ParameterMetaData getParameterMetaData() throws SQLException {
    if (shouldThrow("getParameterMetaData"))
      throw new SQLException("PreparedStatement bombed");

    return new ThrowingParameterMetaData(it.getParameterMetaData());
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.sql.PreparedStatement#setArray(int, java.sql.Array)
   */
  public void setArray(int i, Array x) throws SQLException {
    if (shouldThrow("setArray"))
      throw new SQLException("PreparedStatement bombed");
    it.setArray(i, x);
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
    it.setAsciiStream(parameterIndex, x, length);
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
    it.setBigDecimal(parameterIndex, x);
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
    it.setBinaryStream(parameterIndex, x, length);
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.sql.PreparedStatement#setBlob(int, java.sql.Blob)
   */
  public void setBlob(int i, Blob x) throws SQLException {
    if (shouldThrow("setBlob"))
      throw new SQLException("PreparedStatement bombed");
    it.setBlob(i, x);
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.sql.PreparedStatement#setBoolean(int, boolean)
   */
  public void setBoolean(int parameterIndex, boolean x) throws SQLException {
    if (shouldThrow("setBoolean"))
      throw new SQLException("PreparedStatement bombed");
    it.setBoolean(parameterIndex, x);
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.sql.PreparedStatement#setByte(int, byte)
   */
  public void setByte(int parameterIndex, byte x) throws SQLException {
    if (shouldThrow("setByte"))
      throw new SQLException("PreparedStatement bombed");
    it.setByte(parameterIndex, x);
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.sql.PreparedStatement#setBytes(int, byte[])
   */
  public void setBytes(int parameterIndex, byte[] x) throws SQLException {
    if (shouldThrow("setBytes"))
      throw new SQLException("PreparedStatement bombed");
    it.setBytes(parameterIndex, x);
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
    it.setCharacterStream(parameterIndex, reader, length);
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.sql.PreparedStatement#setClob(int, java.sql.Clob)
   */
  public void setClob(int i, Clob x) throws SQLException {
    if (shouldThrow("setClob"))
      throw new SQLException("PreparedStatement bombed");
    it.setClob(i, x);
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.sql.PreparedStatement#setDate(int, java.sql.Date)
   */
  public void setDate(int parameterIndex, Date x) throws SQLException {
    if (shouldThrow("setDate"))
      throw new SQLException("PreparedStatement bombed");
    it.setDate(parameterIndex, x);
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
    it.setDate(parameterIndex, x, cal);
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.sql.PreparedStatement#setDouble(int, double)
   */
  public void setDouble(int parameterIndex, double x) throws SQLException {
    if (shouldThrow("setDouble"))
      throw new SQLException("PreparedStatement bombed");
    it.setDouble(parameterIndex, x);
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.sql.PreparedStatement#setFloat(int, float)
   */
  public void setFloat(int parameterIndex, float x) throws SQLException {
    if (shouldThrow("setFloat"))
      throw new SQLException("PreparedStatement bombed");
    it.setFloat(parameterIndex, x);
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.sql.PreparedStatement#setInt(int, int)
   */
  public void setInt(int parameterIndex, int x) throws SQLException {
    if (shouldThrow("setInt"))
      throw new SQLException("PreparedStatement bombed");
    it.setInt(parameterIndex, x);
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.sql.PreparedStatement#setLong(int, long)
   */
  public void setLong(int parameterIndex, long x) throws SQLException {
    if (shouldThrow("setLong"))
      throw new SQLException("PreparedStatement bombed");
    it.setLong(parameterIndex, x);
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.sql.PreparedStatement#setNull(int, int)
   */
  public void setNull(int parameterIndex, int sqlType) throws SQLException {
    if (shouldThrow("setNull"))
      throw new SQLException("PreparedStatement bombed");
    it.setNull(parameterIndex, sqlType);
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
    it.setNull(paramIndex, sqlType, typeName);
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.sql.PreparedStatement#setObject(int, java.lang.Object)
   */
  public void setObject(int parameterIndex, Object x) throws SQLException {
    if (shouldThrow("setObject"))
      throw new SQLException("PreparedStatement bombed");
    it.setObject(parameterIndex, x);
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
    it.setObject(parameterIndex, x, targetSqlType);
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
    it.setObject(parameterIndex, x, targetSqlType, scale);
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.sql.PreparedStatement#setRef(int, java.sql.Ref)
   */
  public void setRef(int i, Ref x) throws SQLException {
    if (shouldThrow("setRef"))
      throw new SQLException("PreparedStatement bombed");
    it.setRef(i, x);
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.sql.PreparedStatement#setShort(int, short)
   */
  public void setShort(int parameterIndex, short x) throws SQLException {
    if (shouldThrow("setShort"))
      throw new SQLException("PreparedStatement bombed");
    it.setShort(parameterIndex, x);
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.sql.PreparedStatement#setString(int, java.lang.String)
   */
  public void setString(int parameterIndex, String x) throws SQLException {
    if (shouldThrow("setString"))
      throw new SQLException("PreparedStatement bombed");
    it.setString(parameterIndex, x);
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.sql.PreparedStatement#setTime(int, java.sql.Time)
   */
  public void setTime(int parameterIndex, Time x) throws SQLException {
    if (shouldThrow("setTime"))
      throw new SQLException("PreparedStatement bombed");
    it.setTime(parameterIndex, x);
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
    it.setTime(parameterIndex, x, cal);
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.sql.PreparedStatement#setTimestamp(int, java.sql.Timestamp)
   */
  public void setTimestamp(int parameterIndex, Timestamp x) throws SQLException {
    if (shouldThrow("setTimestamp"))
      throw new SQLException("PreparedStatement bombed");
    it.setTimestamp(parameterIndex, x);
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
    it.setTimestamp(parameterIndex, x, cal);
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.sql.PreparedStatement#setURL(int, java.net.URL)
   */
  public void setURL(int parameterIndex, URL x) throws SQLException {
    if (shouldThrow("setURL"))
      throw new SQLException("PreparedStatement bombed");
    it.setURL(parameterIndex, x);
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
    it.addBatch();
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.sql.Statement#cancel()
   */
  public void cancel() throws SQLException {
    if (shouldThrow("cancel"))
      throw new SQLException("PreparedStatement bombed");
    it.cancel();
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.sql.Statement#clearBatch()
   */
  public void clearBatch() throws SQLException {
    if (shouldThrow("clearBatch"))
      throw new SQLException("PreparedStatement bombed");
    it.clearBatch();
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.sql.Statement#clearWarnings()
   */
  public void clearWarnings() throws SQLException {
    if (shouldThrow("clearWarnings"))
      throw new SQLException("PreparedStatement bombed");
    it.clearWarnings();
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.sql.Statement#close()
   */
  public void close() throws SQLException {
    if (shouldThrow("close"))
      throw new SQLException("PreparedStatement bombed");
    it.close();
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.sql.Statement#execute(java.lang.String)
   */
  public boolean execute(String sql) throws SQLException {
    if (shouldThrow("execute"))
      throw new SQLException("PreparedStatement bombed");

    return it.execute(sql);
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.sql.Statement#execute(java.lang.String, int)
   */
  public boolean execute(String sql, int autoGeneratedKeys) throws SQLException {
    if (shouldThrow("execute"))
      throw new SQLException("PreparedStatement bombed");

    return it.execute(sql, autoGeneratedKeys);
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.sql.Statement#execute(java.lang.String, int[])
   */
  public boolean execute(String sql, int[] columnIndexes) throws SQLException {
    if (shouldThrow("execute"))
      throw new SQLException("PreparedStatement bombed");

    return it.execute(sql, columnIndexes);
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.sql.Statement#execute(java.lang.String, java.lang.String[])
   */
  public boolean execute(String sql, String[] columnNames) throws SQLException {
    if (shouldThrow("execute"))
      throw new SQLException("PreparedStatement bombed");

    return it.execute(sql, columnNames);
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.sql.Statement#executeBatch()
   */
  public int[] executeBatch() throws SQLException {
    if (shouldThrow("executeBatch"))
      throw new SQLException("PreparedStatement bombed");

    return it.executeBatch();
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.sql.Statement#executeQuery(java.lang.String)
   */
  public ResultSet executeQuery(String sql) throws SQLException {
    if (shouldThrow("executeQuery"))
      throw new SQLException("PreparedStatement bombed");

    return new ThrowingResultSet<Object>(it.executeQuery(sql));
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.sql.Statement#executeUpdate(java.lang.String)
   */
  public int executeUpdate(String sql) throws SQLException {
    if (shouldThrow("executeUpdate"))
      throw new SQLException("PreparedStatement bombed");

    return it.executeUpdate(sql);
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

    return it.executeUpdate(sql, autoGeneratedKeys);
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.sql.Statement#executeUpdate(java.lang.String, int[])
   */
  public int executeUpdate(String sql, int[] columnIndexes) throws SQLException {
    if (shouldThrow("executeUpdate"))
      throw new SQLException("PreparedStatement bombed");

    return it.executeUpdate(sql, columnIndexes);
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

    return it.executeUpdate(sql, columnNames);
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.sql.Statement#getConnection()
   */
  public Connection getConnection() throws SQLException {
    if (shouldThrow("getConnection"))
      throw new SQLException("PreparedStatement bombed");

    return new ThrowingConnection(it.getConnection());
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.sql.Statement#getFetchDirection()
   */
  public int getFetchDirection() throws SQLException {
    if (shouldThrow("getFetchDirection"))
      throw new SQLException("PreparedStatement bombed");

    return it.getFetchDirection();
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.sql.Statement#getFetchSize()
   */
  public int getFetchSize() throws SQLException {
    if (shouldThrow("getFetchSize"))
      throw new SQLException("PreparedStatement bombed");

    return it.getFetchSize();
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.sql.Statement#getGeneratedKeys()
   */
  public ResultSet getGeneratedKeys() throws SQLException {
    if (shouldThrow("getGeneratedKeys"))
      throw new SQLException("PreparedStatement bombed");

    return new ThrowingResultSet<Object>(it.getGeneratedKeys());
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.sql.Statement#getMaxFieldSize()
   */
  public int getMaxFieldSize() throws SQLException {
    if (shouldThrow("getMaxFieldSize"))
      throw new SQLException("PreparedStatement bombed");

    return it.getMaxFieldSize();
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.sql.Statement#getMaxRows()
   */
  public int getMaxRows() throws SQLException {
    if (shouldThrow("getMaxRows"))
      throw new SQLException("PreparedStatement bombed");

    return it.getMaxRows();
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.sql.Statement#getMoreResults()
   */
  public boolean getMoreResults() throws SQLException {
    if (shouldThrow("getMoreResults"))
      throw new SQLException("PreparedStatement bombed");

    return it.getMoreResults();
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.sql.Statement#getMoreResults(int)
   */
  public boolean getMoreResults(int current) throws SQLException {
    if (shouldThrow("getMoreResults"))
      throw new SQLException("PreparedStatement bombed");

    return it.getMoreResults(current);
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.sql.Statement#getQueryTimeout()
   */
  public int getQueryTimeout() throws SQLException {
    if (shouldThrow("getQueryTimeout"))
      throw new SQLException("PreparedStatement bombed");

    return it.getQueryTimeout();
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.sql.Statement#getResultSet()
   */
  public ResultSet getResultSet() throws SQLException {
    if (shouldThrow("getResultSet"))
      throw new SQLException("PreparedStatement bombed");

    return new ThrowingResultSet<Object>(it.getResultSet());
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.sql.Statement#getResultSetConcurrency()
   */
  public int getResultSetConcurrency() throws SQLException {
    if (shouldThrow("getResultSetConcurrency"))
      throw new SQLException("PreparedStatement bombed");

    return it.getResultSetConcurrency();
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.sql.Statement#getResultSetHoldability()
   */
  public int getResultSetHoldability() throws SQLException {
    if (shouldThrow("getResultSetHoldability"))
      throw new SQLException("PreparedStatement bombed");

    return it.getResultSetHoldability();
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.sql.Statement#getResultSetType()
   */
  public int getResultSetType() throws SQLException {
    if (shouldThrow("getResultSetType"))
      throw new SQLException("PreparedStatement bombed");

    return it.getResultSetType();
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.sql.Statement#getUpdateCount()
   */
  public int getUpdateCount() throws SQLException {
    if (shouldThrow("getUpdateCount"))
      throw new SQLException("PreparedStatement bombed");

    return it.getUpdateCount();
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.sql.Statement#getWarnings()
   */
  public SQLWarning getWarnings() throws SQLException {
    if (shouldThrow("getWarnings"))
      throw new SQLException("PreparedStatement bombed");

    return it.getWarnings();
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.sql.Statement#setCursorName(java.lang.String)
   */
  public void setCursorName(String name) throws SQLException {
    if (shouldThrow("setCursorName"))
      throw new SQLException("PreparedStatement bombed");
    it.setCursorName(name);
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.sql.Statement#setEscapeProcessing(boolean)
   */
  public void setEscapeProcessing(boolean enable) throws SQLException {
    if (shouldThrow("setEscapeProcessing"))
      throw new SQLException("PreparedStatement bombed");
    it.setEscapeProcessing(enable);
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.sql.Statement#setFetchDirection(int)
   */
  public void setFetchDirection(int direction) throws SQLException {
    if (shouldThrow("setFetchDirection"))
      throw new SQLException("PreparedStatement bombed");
    it.setFetchDirection(direction);
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.sql.Statement#setFetchSize(int)
   */
  public void setFetchSize(int rows) throws SQLException {
    if (shouldThrow("setFetchSize"))
      throw new SQLException("PreparedStatement bombed");
    it.setFetchSize(rows);
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.sql.Statement#setMaxFieldSize(int)
   */
  public void setMaxFieldSize(int max) throws SQLException {
    if (shouldThrow("setMaxFieldSize"))
      throw new SQLException("PreparedStatement bombed");
    it.setMaxFieldSize(max);
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.sql.Statement#setMaxRows(int)
   */
  public void setMaxRows(int max) throws SQLException {
    if (shouldThrow("setMaxRows"))
      throw new SQLException("PreparedStatement bombed");
    it.setMaxRows(max);
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.sql.Statement#setQueryTimeout(int)
   */
  public void setQueryTimeout(int seconds) throws SQLException {
    if (shouldThrow("setQueryTimeout"))
      throw new SQLException("PreparedStatement bombed");
    it.setQueryTimeout(seconds);
  }
  
  /**
   *  JDBC 4.0
   */
  
  /** 
   * {@inheritDoc}
   * @see java.sql.PreparedStatement#setAsciiStream(int, java.io.InputStream, long)
   */

  public void setAsciiStream(int parameterIndex, InputStream x, long length)
          throws SQLException {
    if (shouldThrow("setAsciiStream"))
      throw new SQLException("PreparedStatement bombed");
    it.setAsciiStream(parameterIndex, x, length);
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.PreparedStatement#setAsciiStream(int, java.io.InputStream)
   */

  public void setAsciiStream(int parameterIndex, InputStream x)
          throws SQLException {
    if (shouldThrow("setAsciiStream"))
      throw new SQLException("PreparedStatement bombed");
    it.setAsciiStream(parameterIndex, x);
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.PreparedStatement#setBinaryStream(int, java.io.InputStream, long)
   */

  public void setBinaryStream(int parameterIndex, InputStream x, long length)
          throws SQLException {
    if (shouldThrow("setBinaryStream"))
      throw new SQLException("PreparedStatement bombed");
    it.setBinaryStream(parameterIndex, x, length);
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.PreparedStatement#setBinaryStream(int, java.io.InputStream)
   */

  public void setBinaryStream(int parameterIndex, InputStream x)
          throws SQLException {
    if (shouldThrow("setBinaryStream"))
      throw new SQLException("PreparedStatement bombed");
    it.setBinaryStream(parameterIndex, x);
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.PreparedStatement#setBlob(int, java.io.InputStream, long)
   */

  public void setBlob(int parameterIndex, InputStream inputStream, long length)
          throws SQLException {
    if (shouldThrow("setBlob"))
      throw new SQLException("PreparedStatement bombed");
    it.setBlob(parameterIndex, inputStream, length);
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.PreparedStatement#setBlob(int, java.io.InputStream)
   */

  public void setBlob(int parameterIndex, InputStream inputStream)
          throws SQLException {
    if (shouldThrow("setBlob"))
      throw new SQLException("PreparedStatement bombed");
    it.setBlob(parameterIndex, inputStream);
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.PreparedStatement#setCharacterStream(int, java.io.Reader, long)
   */

  public void setCharacterStream(int parameterIndex, Reader reader, long length)
          throws SQLException {
    if (shouldThrow("setCharacterStream"))
      throw new SQLException("PreparedStatement bombed");
    it.setCharacterStream(parameterIndex, reader, length);
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.PreparedStatement#setCharacterStream(int, java.io.Reader)
   */

  public void setCharacterStream(int parameterIndex, Reader reader)
          throws SQLException {
    if (shouldThrow("setCharacterStream"))
      throw new SQLException("PreparedStatement bombed");
    it.setCharacterStream(parameterIndex, reader);
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.PreparedStatement#setClob(int, java.io.Reader, long)
   */

  public void setClob(int parameterIndex, Reader reader, long length)
          throws SQLException {
    if (shouldThrow("setClob"))
      throw new SQLException("PreparedStatement bombed");
    it.setClob(parameterIndex, reader, length);
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.PreparedStatement#setClob(int, java.io.Reader)
   */

  public void setClob(int parameterIndex, Reader reader) throws SQLException {
    if (shouldThrow("setClob"))
      throw new SQLException("PreparedStatement bombed");
    it.setClob(parameterIndex, reader);
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.PreparedStatement#setNCharacterStream(int, java.io.Reader, long)
   */

  public void setNCharacterStream(int parameterIndex, Reader value, long length)
          throws SQLException {
    if (shouldThrow("setNCharacterStream"))
      throw new SQLException("PreparedStatement bombed");
    it.setNCharacterStream(parameterIndex, value, length);
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.PreparedStatement#setNCharacterStream(int, java.io.Reader)
   */

  public void setNCharacterStream(int parameterIndex, Reader value)
          throws SQLException {
    if (shouldThrow("setNCharacterStream"))
      throw new SQLException("PreparedStatement bombed");
    it.setNCharacterStream(parameterIndex, value);
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.PreparedStatement#setNClob(int, java.sql.NClob)
   */

  public void setNClob(int parameterIndex, NClob value) throws SQLException {
    if (shouldThrow("setNClob"))
      throw new SQLException("PreparedStatement bombed");
    it.setNClob(parameterIndex, value);
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.PreparedStatement#setNClob(int, java.io.Reader, long)
   */

  public void setNClob(int parameterIndex, Reader reader, long length)
          throws SQLException {
    if (shouldThrow("setNClob"))
      throw new SQLException("PreparedStatement bombed");
    it.setNClob(parameterIndex, reader, length);
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.PreparedStatement#setNClob(int, java.io.Reader)
   */

  public void setNClob(int parameterIndex, Reader reader) throws SQLException {
    if (shouldThrow("setNClob"))
      throw new SQLException("PreparedStatement bombed");
    it.setNClob(parameterIndex, reader);
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.PreparedStatement#setNString(int, java.lang.String)
   */

  public void setNString(int parameterIndex, String value) throws SQLException {
    if (shouldThrow("setNString"))
      throw new SQLException("PreparedStatement bombed");
    it.setNString(parameterIndex, value);
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.PreparedStatement#setRowId(int, java.sql.RowId)
   */

  public void setRowId(int parameterIndex, RowId x) throws SQLException {
    if (shouldThrow("setRowId"))
      throw new SQLException("PreparedStatement bombed");
    it.setRowId(parameterIndex, x);
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.PreparedStatement#setSQLXML(int, java.sql.SQLXML)
   */

  public void setSQLXML(int parameterIndex, SQLXML xmlObject)
          throws SQLException {
    if (shouldThrow("setSQLXML"))
      throw new SQLException("PreparedStatement bombed");
    it.setSQLXML(parameterIndex, xmlObject);
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.Statement#isClosed()
   */

  public boolean isClosed() throws SQLException {
    if (shouldThrow("isClosed"))
      throw new SQLException("PreparedStatement bombed");
    return it.isClosed();
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.Statement#isPoolable()
   */

  public boolean isPoolable() throws SQLException {
    if (shouldThrow("isPoolable"))
      throw new SQLException("PreparedStatement bombed");
    return it.isPoolable();
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.Statement#setPoolable(boolean)
   */

  public void setPoolable(boolean poolable) throws SQLException {
    if (shouldThrow("setPoolable"))
      throw new SQLException("PreparedStatement bombed");
    it.setPoolable(poolable);
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.Wrapper#isWrapperFor(java.lang.Class)
   */

  public boolean isWrapperFor(Class<?> iface) throws SQLException {
    if (shouldThrow("isWrapperFor"))
      throw new SQLException("PreparedStatement bombed");
    return it.isWrapperFor(iface);
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.Wrapper#unwrap(java.lang.Class)
   */

  public <T> T unwrap(Class<T> iface) throws SQLException {
    if (shouldThrow("unwrap"))
      throw new SQLException("PreparedStatement bombed");
    return it.unwrap(iface);
  }
  

  
  

}
