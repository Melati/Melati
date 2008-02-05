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
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ParameterMetaData;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Map;

/**
 * @author timp
 * @since 3 May 2007
 *
 */
public class ThrowingCallableStatement extends ThrowingCallableStatementVariant implements
        CallableStatement {

  final static String className = ThrowingCallableStatement.class.getName() + ".";
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

  /**
   * Constructor. 
   * @param callableStatement
   */
  public ThrowingCallableStatement(CallableStatement callableStatement) {
    this.it = callableStatement;
  }
  
  public Array getArray(int i) throws SQLException {
    if (shouldThrow("getArray"))
      throw new SQLException("CallableStatement bombed");
    return new ThrowingArray(it.getArray(i));
  }

  public Array getArray(String parameterName) throws SQLException {
    if (shouldThrow("getArray"))
      throw new SQLException("CallableStatement bombed");
    return new ThrowingArray(it.getArray(parameterName));
  }

  public BigDecimal getBigDecimal(int parameterIndex) throws SQLException {
    if (shouldThrow("getBigDecimal"))
      throw new SQLException("CallableStatement bombed");
    return it.getBigDecimal(parameterIndex);
  }

  public BigDecimal getBigDecimal(String parameterName) throws SQLException {
    if (shouldThrow("getBigDecimal"))
      throw new SQLException("CallableStatement bombed");
    return it.getBigDecimal(parameterName);
  }

  public BigDecimal getBigDecimal(int parameterIndex, int scale)
          throws SQLException {
    if (shouldThrow("getBigDecimal"))
      throw new SQLException("CallableStatement bombed");
    return it.getBigDecimal(parameterIndex);
  }

  public Blob getBlob(int i) throws SQLException {
    if (shouldThrow("getBlob"))
      throw new SQLException("CallableStatement bombed");
    return new ThrowingBlob(it.getBlob(i));
  }

  public Blob getBlob(String parameterName) throws SQLException {
    if (shouldThrow("getBlob"))
      throw new SQLException("CallableStatement bombed");
    return new ThrowingBlob(it.getBlob(parameterName));
  }

  public boolean getBoolean(int parameterIndex) throws SQLException {
    if (shouldThrow("getBoolean"))
      throw new SQLException("CallableStatement bombed");
    return it.getBoolean(parameterIndex);
  }

  public boolean getBoolean(String parameterName) throws SQLException {
    if (shouldThrow("getBoolean"))
      throw new SQLException("CallableStatement bombed");
    return it.getBoolean(parameterName);
  }

  public byte getByte(int parameterIndex) throws SQLException {
    if (shouldThrow("getByte"))
      throw new SQLException("CallableStatement bombed");
    return it.getByte(parameterIndex);
  }

  public byte getByte(String parameterName) throws SQLException {
    if (shouldThrow("getByte"))
      throw new SQLException("CallableStatement bombed");
    return it.getByte(parameterName);
  }

  public byte[] getBytes(int parameterIndex) throws SQLException {
    if (shouldThrow("getBytes"))
      throw new SQLException("CallableStatement bombed");
    return it.getBytes(parameterIndex);
  }

  public byte[] getBytes(String parameterName) throws SQLException {
    if (shouldThrow("getBytes"))
      throw new SQLException("CallableStatement bombed");
    return it.getBytes(parameterName);
  }

  public Clob getClob(int i) throws SQLException {
    if (shouldThrow("getClob"))
      throw new SQLException("CallableStatement bombed");
    return new ThrowingClob(it.getClob(i));
  }

  public Clob getClob(String parameterName) throws SQLException {
    if (shouldThrow("getClob"))
      throw new SQLException("CallableStatement bombed");
    return new ThrowingClob(it.getClob(parameterName));
  }

  public Date getDate(int parameterIndex) throws SQLException {
    if (shouldThrow("getDate"))
      throw new SQLException("CallableStatement bombed");
    return it.getDate(parameterIndex);
  }

  public Date getDate(String parameterName) throws SQLException {
    if (shouldThrow("getDate"))
      throw new SQLException("CallableStatement bombed");
    return it.getDate(parameterName);
  }

  public Date getDate(int parameterIndex, Calendar cal) throws SQLException {
    if (shouldThrow("getDate"))
      throw new SQLException("CallableStatement bombed");
    return it.getDate(parameterIndex, cal);
  }

  public Date getDate(String parameterName, Calendar cal) throws SQLException {
    if (shouldThrow("getDate"))
      throw new SQLException("CallableStatement bombed");
    return it.getDate(parameterName, cal);
  }

  public double getDouble(int parameterIndex) throws SQLException {
    if (shouldThrow("getDouble"))
      throw new SQLException("CallableStatement bombed");
    return it.getDouble(parameterIndex);
  }

  public double getDouble(String parameterName) throws SQLException {
    if (shouldThrow("getDouble"))
      throw new SQLException("CallableStatement bombed");
    return it.getDouble(parameterName);
  }

  public float getFloat(int parameterIndex) throws SQLException {
    if (shouldThrow("getFloat"))
      throw new SQLException("CallableStatement bombed");
    return it.getFloat(parameterIndex);
  }

  public float getFloat(String parameterName) throws SQLException {
    if (shouldThrow("getFloat"))
      throw new SQLException("CallableStatement bombed");
    return it.getFloat(parameterName);
  }

  public int getInt(int parameterIndex) throws SQLException {
    if (shouldThrow("getInt"))
      throw new SQLException("CallableStatement bombed");
    return it.getInt(parameterIndex);
  }

  public int getInt(String parameterName) throws SQLException {
    if (shouldThrow("getInt"))
      throw new SQLException("CallableStatement bombed");
    return it.getInt(parameterName);
  }

  public long getLong(int parameterIndex) throws SQLException {
    if (shouldThrow("getLong"))
      throw new SQLException("CallableStatement bombed");
    return it.getLong(parameterIndex);
  }

  public long getLong(String parameterName) throws SQLException {
    if (shouldThrow("getLong"))
      throw new SQLException("CallableStatement bombed");
    return it.getLong(parameterName);
  }

  public Object getObject(int parameterIndex) throws SQLException {
    if (shouldThrow("getObject"))
      throw new SQLException("CallableStatement bombed");
    return it.getObject(parameterIndex);
  }

  public Object getObject(String parameterName) throws SQLException {
    if (shouldThrow("getObject"))
      throw new SQLException("CallableStatement bombed");
    return it.getObject(parameterName);
  }

  public Object getObject(int arg0, Map arg1) throws SQLException {
    if (shouldThrow("getObject"))
      throw new SQLException("CallableStatement bombed");
    return it.getObject(arg0, arg1);
  }

  public Object getObject(String arg0, Map arg1) throws SQLException {
    if (shouldThrow("getObject"))
      throw new SQLException("CallableStatement bombed");
    return it.getObject(arg0, arg1);
  }

  public Ref getRef(int i) throws SQLException {
    if (shouldThrow("getRef"))
      throw new SQLException("CallableStatement bombed");
    return new ThrowingRef(it.getRef(i));
  }

  public Ref getRef(String parameterName) throws SQLException {
    if (shouldThrow("getRef"))
      throw new SQLException("CallableStatement bombed");
    return new ThrowingRef(it.getRef(parameterName));
  }

  public short getShort(int parameterIndex) throws SQLException {
    if (shouldThrow("getShort"))
      throw new SQLException("CallableStatement bombed");
    return it.getShort(parameterIndex);
  }

  public short getShort(String parameterName) throws SQLException {
    if (shouldThrow("getShort"))
      throw new SQLException("CallableStatement bombed");
    return it.getShort(parameterName);
  }

  public String getString(int parameterIndex) throws SQLException {
    if (shouldThrow("getString"))
      throw new SQLException("CallableStatement bombed");
    return it.getString(parameterIndex);
  }

  public String getString(String parameterName) throws SQLException {
    if (shouldThrow("getString"))
      throw new SQLException("CallableStatement bombed");
    return it.getString(parameterName);
  }

  public Time getTime(int parameterIndex) throws SQLException {
    if (shouldThrow("getTime"))
      throw new SQLException("CallableStatement bombed");
    return it.getTime(parameterIndex);
  }

  public Time getTime(String parameterName) throws SQLException {
    if (shouldThrow("getTime"))
      throw new SQLException("CallableStatement bombed");
    return it.getTime(parameterName);
  }

  public Time getTime(int parameterIndex, Calendar cal) throws SQLException {
    if (shouldThrow("getTime"))
      throw new SQLException("CallableStatement bombed");
    return it.getTime(parameterIndex, cal);
  }

  public Time getTime(String parameterName, Calendar cal) throws SQLException {
    if (shouldThrow("getTime"))
      throw new SQLException("CallableStatement bombed");
    return it.getTime(parameterName, cal);
  }

  public Timestamp getTimestamp(int parameterIndex) throws SQLException {
    if (shouldThrow("getTimestamp"))
      throw new SQLException("CallableStatement bombed");
    return it.getTimestamp(parameterIndex);
  }

  public Timestamp getTimestamp(String parameterName) throws SQLException {
    if (shouldThrow("getTimestamp"))
      throw new SQLException("CallableStatement bombed");
    return it.getTimestamp(parameterName);
  }

  public Timestamp getTimestamp(int parameterIndex, Calendar cal)
          throws SQLException {
    if (shouldThrow("getTimestamp"))
      throw new SQLException("CallableStatement bombed");
    return it.getTimestamp(parameterIndex, cal);
  }

  public Timestamp getTimestamp(String parameterName, Calendar cal)
          throws SQLException {
    if (shouldThrow("getTimestamp"))
      throw new SQLException("CallableStatement bombed");
    return it.getTimestamp(parameterName, cal);
  }

  public URL getURL(int parameterIndex) throws SQLException {
    if (shouldThrow("getURL"))
      throw new SQLException("CallableStatement bombed");
    return it.getURL(parameterIndex);
    
  }
  public URL getURL(String parameterName) throws SQLException {
    if (shouldThrow("getURL"))
      throw new SQLException("CallableStatement bombed");
    return it.getURL(parameterName);    
  }
  public void registerOutParameter(int parameterIndex, int sqlType, int scale) throws SQLException {
    if (shouldThrow("registerOutParameter"))
      throw new SQLException("CallableStatement bombed");
    it.registerOutParameter(parameterIndex, sqlType, scale);
    
  }
  public void registerOutParameter(int paramIndex, int sqlType, String typeName) throws SQLException {
    if (shouldThrow("registerOutParameter"))
      throw new SQLException("CallableStatement bombed");
    it.registerOutParameter(paramIndex, sqlType, typeName);
    
  }
  public void registerOutParameter(int parameterIndex, int sqlType) throws SQLException {
    if (shouldThrow("registerOutParameter"))
      throw new SQLException("CallableStatement bombed");
    it.registerOutParameter(parameterIndex, sqlType);
    
  }
  public void registerOutParameter(String parameterName, int sqlType, int scale) throws SQLException {
    if (shouldThrow("registerOutParameter"))
      throw new SQLException("CallableStatement bombed");
    it.registerOutParameter(parameterName, sqlType, scale);
    
  }
  public void registerOutParameter(String parameterName, int sqlType, String typeName) throws SQLException {
    if (shouldThrow("registerOutParameter"))
      throw new SQLException("CallableStatement bombed");
    it.registerOutParameter(parameterName, sqlType, typeName);
    
  }
  public void registerOutParameter(String parameterName, int sqlType) throws SQLException {
    if (shouldThrow("registerOutParameter"))
      throw new SQLException("CallableStatement bombed");
    it.registerOutParameter(parameterName, sqlType);
    
  }
  public void setAsciiStream(String parameterName, InputStream x, int length) throws SQLException {
    if (shouldThrow("setAsciiStream"))
      throw new SQLException("CallableStatement bombed");
    it.setAsciiStream(parameterName, x, length);
    
  }
  public void setBigDecimal(String parameterName, BigDecimal x) throws SQLException {
    if (shouldThrow("setBigDecimal"))
      throw new SQLException("CallableStatement bombed");
    it.setBigDecimal(parameterName, x);
    
  }
  public void setBinaryStream(String parameterName, InputStream x, int length) throws SQLException {
    if (shouldThrow("setBinaryStream"))
      throw new SQLException("CallableStatement bombed");
    it.setBinaryStream(parameterName, x, length);
  }
  public void setBoolean(String parameterName, boolean x) throws SQLException {
    if (shouldThrow("setBoolean"))
      throw new SQLException("CallableStatement bombed");
    it.setBoolean(parameterName, x);
    
  }
  public void setByte(String parameterName, byte x) throws SQLException {
    if (shouldThrow("setByte"))
      throw new SQLException("CallableStatement bombed");
    it.setByte(parameterName, x);
  }
  public void setBytes(String parameterName, byte[] x) throws SQLException {
    if (shouldThrow("setBytes"))
      throw new SQLException("CallableStatement bombed");
    it.setBytes(parameterName, x);
  }
  public void setCharacterStream(String parameterName, Reader reader, int length) throws SQLException {
    if (shouldThrow("setCharacterStream"))
      throw new SQLException("CallableStatement bombed");
    it.setCharacterStream(parameterName, reader, length);
  }
  public void setDate(String parameterName, Date x, Calendar cal) throws SQLException {
    if (shouldThrow("setDate"))
      throw new SQLException("CallableStatement bombed");
    it.setDate(parameterName, x, cal);
  }
  public void setDate(String parameterName, Date x) throws SQLException {
    if (shouldThrow("setDate"))
      throw new SQLException("CallableStatement bombed");
    it.setDate(parameterName, x);
    
  }
  public void setDouble(String parameterName, double x) throws SQLException {
    if (shouldThrow("setDouble"))
      throw new SQLException("CallableStatement bombed");
    it.setDouble(parameterName, x);
  }
  public void setFloat(String parameterName, float x) throws SQLException {
    if (shouldThrow("setFloat"))
      throw new SQLException("CallableStatement bombed");
    it.setFloat(parameterName, x);
  }
  public void setInt(String parameterName, int x) throws SQLException {
    if (shouldThrow("setInt"))
      throw new SQLException("CallableStatement bombed");
    it.setInt(parameterName, x);
  }
  public void setLong(String parameterName, long x) throws SQLException {
    if (shouldThrow("setLong"))
      throw new SQLException("CallableStatement bombed");
    it.setLong(parameterName, x);
  }
  public void setNull(String parameterName, int sqlType, String typeName) throws SQLException {
    if (shouldThrow("setNull"))
      throw new SQLException("CallableStatement bombed");
    it.setNull(parameterName, sqlType, typeName);
  }
  public void setNull(String parameterName, int sqlType) throws SQLException {
    if (shouldThrow("setNull"))
      throw new SQLException("CallableStatement bombed");
    it.setNull(parameterName, sqlType);
  }
  public void setObject(String parameterName, Object x, int targetSqlType, int scale) throws SQLException {
    if (shouldThrow("setObject"))
      throw new SQLException("CallableStatement bombed");
    it.setObject(parameterName, x, targetSqlType, scale);
  }
  public void setObject(String parameterName, Object x, int targetSqlType) throws SQLException {
    if (shouldThrow("setObject"))
      throw new SQLException("CallableStatement bombed");
    it.setObject(parameterName, x, targetSqlType);
  }
  public void setObject(String parameterName, Object x) throws SQLException {
    if (shouldThrow("setObject"))
      throw new SQLException("CallableStatement bombed");
    it.setObject(parameterName, x);
  }
  public void setShort(String parameterName, short x) throws SQLException {
    if (shouldThrow("setShort"))
      throw new SQLException("CallableStatement bombed");
    it.setShort(parameterName, x);
  }
  public void setString(String parameterName, String x) throws SQLException {
    if (shouldThrow("setString"))
      throw new SQLException("CallableStatement bombed");
    it.setString(parameterName, x);
  }
  public void setTime(String parameterName, Time x, Calendar cal) throws SQLException {
    if (shouldThrow("setTime"))
      throw new SQLException("CallableStatement bombed");
    it.setTime(parameterName, x, cal);
    
  }
  public void setTime(String parameterName, Time x) throws SQLException {
    if (shouldThrow("setTime"))
      throw new SQLException("CallableStatement bombed");
    it.setTime(parameterName, x);
  }
  public void setTimestamp(String parameterName, Timestamp x, Calendar cal) throws SQLException {
    if (shouldThrow("setTimestamp"))
      throw new SQLException("CallableStatement bombed");
    it.setTimestamp(parameterName, x, cal);
    
  }
  public void setTimestamp(String parameterName, Timestamp x) throws SQLException {
    if (shouldThrow("setTimestamp"))
      throw new SQLException("CallableStatement bombed");
    it.setTimestamp(parameterName, x);
  }
  public void setURL(String parameterName, URL val) throws SQLException {
    if (shouldThrow("setURL"))
      throw new SQLException("CallableStatement bombed");
    it.setURL(parameterName, val);
  }
  public boolean wasNull() throws SQLException {
    if (shouldThrow("wasNull"))
      throw new SQLException("CallableStatement bombed");
    return it.wasNull();
  }
  public void addBatch() throws SQLException {
    if (shouldThrow("addBatch"))
      throw new SQLException("CallableStatement bombed");
    it.addBatch();
  }
  public void clearParameters() throws SQLException {
    if (shouldThrow("clearParameters"))
      throw new SQLException("CallableStatement bombed");
    it.clearParameters();
  }
  public boolean execute() throws SQLException {
    if (shouldThrow("execute"))
      throw new SQLException("CallableStatement bombed");
    return it.execute();
  }
  public ResultSet executeQuery() throws SQLException {
    if (shouldThrow("executeQuery"))
      throw new SQLException("CallableStatement bombed");
    return it.executeQuery();
  }
  public int executeUpdate() throws SQLException {
    if (shouldThrow("executeUpdate"))
      throw new SQLException("CallableStatement bombed");
    return it.executeUpdate();
  }
  public ResultSetMetaData getMetaData() throws SQLException {
    if (shouldThrow("getMetaData"))
      throw new SQLException("CallableStatement bombed");
    return it.getMetaData();
  }
  public ParameterMetaData getParameterMetaData() throws SQLException {
    if (shouldThrow("getParameterMetaData"))
      throw new SQLException("CallableStatement bombed");
    return new ThrowingParameterMetaData(it.getParameterMetaData());
  }
  public void setArray(int i, Array x) throws SQLException {
    if (shouldThrow("setArray"))
      throw new SQLException("CallableStatement bombed");
    it.setArray(i, x);
  }
  public void setAsciiStream(int parameterIndex, InputStream x, int length) throws SQLException {
    if (shouldThrow("setAsciiStream"))
      throw new SQLException("CallableStatement bombed");
    it.setAsciiStream(parameterIndex, x, length);
  }
  public void setBigDecimal(int parameterIndex, BigDecimal x) throws SQLException {
    if (shouldThrow("setBigDecimal"))
      throw new SQLException("CallableStatement bombed");
    it.setBigDecimal(parameterIndex, x);
  }
  public void setBinaryStream(int parameterIndex, InputStream x, int length) throws SQLException {
    if (shouldThrow("setBinaryStream"))
      throw new SQLException("CallableStatement bombed");
    it.setBinaryStream(parameterIndex, x, length);
  }
  public void setBlob(int i, Blob x) throws SQLException {
    if (shouldThrow("setBlob"))
      throw new SQLException("CallableStatement bombed");
    it.setBlob(i, x);
  }
  public void setBoolean(int parameterIndex, boolean x) throws SQLException {
    if (shouldThrow("setBoolean"))
      throw new SQLException("CallableStatement bombed");
    it.setBoolean(parameterIndex, x);
  }
  public void setByte(int parameterIndex, byte x) throws SQLException {
    if (shouldThrow("setByte"))
      throw new SQLException("CallableStatement bombed");
    it.setByte(parameterIndex, x);
  }
  public void setBytes(int parameterIndex, byte[] x) throws SQLException {
    if (shouldThrow("setBytes"))
      throw new SQLException("CallableStatement bombed");
    it.setBytes(parameterIndex, x);
  }
  public void setCharacterStream(int parameterIndex, Reader reader, int length) throws SQLException {
    if (shouldThrow("setCharacterStream"))
      throw new SQLException("CallableStatement bombed");
    it.setCharacterStream(parameterIndex, reader, length);
  }
  public void setClob(int i, Clob x) throws SQLException {
    if (shouldThrow("setClob"))
      throw new SQLException("CallableStatement bombed");
    it.setClob(i, x);
  }
  public void setDate(int parameterIndex, Date x, Calendar cal) throws SQLException {
    if (shouldThrow("setDate"))
      throw new SQLException("CallableStatement bombed");
    it.setDate(parameterIndex, x, cal);
    
  }
  public void setDate(int parameterIndex, Date x) throws SQLException {
    if (shouldThrow("setDate"))
      throw new SQLException("CallableStatement bombed");
    it.setDate(parameterIndex, x);
  }
  public void setDouble(int parameterIndex, double x) throws SQLException {
    if (shouldThrow("setDouble"))
      throw new SQLException("CallableStatement bombed");
    it.setDouble(parameterIndex, x);
  }
  public void setFloat(int parameterIndex, float x) throws SQLException {
    if (shouldThrow("setFloat"))
      throw new SQLException("CallableStatement bombed");
    it.setFloat(parameterIndex, x);
  }
  public void setInt(int parameterIndex, int x) throws SQLException {
    if (shouldThrow("setInt"))
      throw new SQLException("CallableStatement bombed");
    it.setInt(parameterIndex, x);
  }
  public void setLong(int parameterIndex, long x) throws SQLException {
    if (shouldThrow("setLong"))
      throw new SQLException("CallableStatement bombed");
    it.setLong(parameterIndex, x);
  }
  public void setNull(int paramIndex, int sqlType, String typeName) throws SQLException {
    if (shouldThrow("setNull"))
      throw new SQLException("CallableStatement bombed");
    it.setNull(paramIndex, sqlType, typeName);
  }
  public void setNull(int parameterIndex, int sqlType) throws SQLException {
    if (shouldThrow("setNull"))
      throw new SQLException("CallableStatement bombed");
    it.setNull(parameterIndex, sqlType);
  }
  public void setObject(int parameterIndex, Object x, int targetSqlType, int scale) throws SQLException {
    if (shouldThrow("setObject"))
      throw new SQLException("CallableStatement bombed");
    it.setObject(parameterIndex, x, targetSqlType, scale);
  }
  public void setObject(int parameterIndex, Object x, int targetSqlType) throws SQLException {
    if (shouldThrow("setObject"))
      throw new SQLException("CallableStatement bombed");
    it.setObject(parameterIndex, x, targetSqlType);
  }
  public void setObject(int parameterIndex, Object x) throws SQLException {
    if (shouldThrow("setObject"))
      throw new SQLException("CallableStatement bombed");
    it.setObject(parameterIndex, x);
    
  }
  public void setRef(int i, Ref x) throws SQLException {
    if (shouldThrow("setRef"))
      throw new SQLException("CallableStatement bombed");
    it.setRef(i, x);
  }
  public void setShort(int parameterIndex, short x) throws SQLException {
    if (shouldThrow("setShort"))
      throw new SQLException("CallableStatement bombed");
    it.setShort(parameterIndex, x);
  }
  public void setString(int parameterIndex, String x) throws SQLException {
    if (shouldThrow("setString"))
      throw new SQLException("CallableStatement bombed");
    it.setString(parameterIndex, x);
  }
  public void setTime(int parameterIndex, Time x, Calendar cal) throws SQLException {
    if (shouldThrow("setTime"))
      throw new SQLException("CallableStatement bombed");
    it.setTime(parameterIndex, x, cal);
  }
  public void setTime(int parameterIndex, Time x) throws SQLException {
    if (shouldThrow("setTime"))
      throw new SQLException("CallableStatement bombed");
    it.setTime(parameterIndex, x);
  }
  public void setTimestamp(int parameterIndex, Timestamp x, Calendar cal) throws SQLException {
    if (shouldThrow("setTimestamp"))
      throw new SQLException("CallableStatement bombed");
    it.setTimestamp(parameterIndex, x, cal);
  }
  public void setTimestamp(int parameterIndex, Timestamp x) throws SQLException {
    if (shouldThrow("setTimestamp"))
      throw new SQLException("CallableStatement bombed");
    it.setTimestamp(parameterIndex, x);
  }
  public void setUnicodeStream(int parameterIndex, InputStream x, int length) throws SQLException {
    if (shouldThrow("setUnicodeStream"))
      throw new SQLException("CallableStatement bombed");
    //s.setUnicodeStream(parameterIndex, x, length);
  }
  public void setURL(int parameterIndex, URL x) throws SQLException {
    if (shouldThrow("setURL"))
      throw new SQLException("CallableStatement bombed");
    it.setURL(parameterIndex, x);
  }
  public void addBatch(String sql) throws SQLException {
    if (shouldThrow("addBatch"))
      throw new SQLException("CallableStatement bombed");
    it.addBatch(sql);
  }
  public void cancel() throws SQLException {
    if (shouldThrow("cancel"))
      throw new SQLException("CallableStatement bombed");
    it.cancel();
  }
  public void clearBatch() throws SQLException {
    if (shouldThrow("clearBatch"))
      throw new SQLException("CallableStatement bombed");
    it.clearBatch();
  }
  public void clearWarnings() throws SQLException {
    if (shouldThrow("clearWarnings"))
      throw new SQLException("CallableStatement bombed");
    it.clearWarnings();
  }
  public void close() throws SQLException {
    if (shouldThrow("close"))
      throw new SQLException("CallableStatement bombed");
    it.close();
  }
  public boolean execute(String sql, int autoGeneratedKeys) throws SQLException {
    if (shouldThrow("execute"))
      throw new SQLException("CallableStatement bombed");
    return it.execute(sql, autoGeneratedKeys);
  }
  public boolean execute(String sql, int[] columnIndexes) throws SQLException {
    if (shouldThrow("execute"))
      throw new SQLException("CallableStatement bombed");
    return it.execute(sql, columnIndexes);
  }
  public boolean execute(String sql, String[] columnNames) throws SQLException {
    if (shouldThrow("execute"))
      throw new SQLException("CallableStatement bombed");
    return it.execute(sql, columnNames);
  }
  public boolean execute(String sql) throws SQLException {
    if (shouldThrow("execute"))
      throw new SQLException("CallableStatement bombed");
    return it.execute(sql);
  }
  public int[] executeBatch() throws SQLException {
    if (shouldThrow("executeBatch"))
      throw new SQLException("CallableStatement bombed");
    return it.executeBatch();
  }
  public ResultSet executeQuery(String sql) throws SQLException {
    if (shouldThrow("executeQuery"))
      throw new SQLException("CallableStatement bombed");
    return new ThrowingResultSet(it.executeQuery(sql));
  }
  public int executeUpdate(String sql, int autoGeneratedKeys) throws SQLException {
    if (shouldThrow("executeUpdate"))
      throw new SQLException("CallableStatement bombed");
    return it.executeUpdate(sql, autoGeneratedKeys);
  }
  public int executeUpdate(String sql, int[] columnIndexes) throws SQLException {
    if (shouldThrow("executeUpdate"))
      throw new SQLException("CallableStatement bombed");
    return it.executeUpdate(sql, columnIndexes);
  }
  public int executeUpdate(String sql, String[] columnNames) throws SQLException {
    if (shouldThrow("executeUpdate"))
      throw new SQLException("CallableStatement bombed");
    return it.executeUpdate(sql, columnNames);
  }
  public int executeUpdate(String sql) throws SQLException {
    if (shouldThrow("executeUpdate"))
      throw new SQLException("CallableStatement bombed");
    return it.executeUpdate(sql);
  }

  public Connection getConnection() throws SQLException {
    if (shouldThrow("getConnection"))
      throw new SQLException("CallableStatement bombed");
    return new ThrowingConnection(it.getConnection());
  }
  public int getFetchDirection() throws SQLException {
    if (shouldThrow("getFetchDirection"))
      throw new SQLException("CallableStatement bombed");
    return it.getFetchDirection();
  }
  public int getFetchSize() throws SQLException {
    if (shouldThrow("getFetchSize"))
      throw new SQLException("CallableStatement bombed");
    return it.getFetchSize();
  }
  public ResultSet getGeneratedKeys() throws SQLException {
    if (shouldThrow("getGeneratedKeys"))
      throw new SQLException("CallableStatement bombed");
    return new ThrowingResultSet(it.getGeneratedKeys());
  }
  public int getMaxFieldSize() throws SQLException {
    if (shouldThrow("getMaxFieldSize"))
      throw new SQLException("CallableStatement bombed");
    return it.getMaxFieldSize();
  }
  public int getMaxRows() throws SQLException {
    if (shouldThrow("getMaxRows"))
      throw new SQLException("CallableStatement bombed");
    return it.getMaxRows();
  }
  public boolean getMoreResults() throws SQLException {
    if (shouldThrow("getMoreResults"))
      throw new SQLException("CallableStatement bombed");
    return it.getMoreResults();
  }
  public boolean getMoreResults(int current) throws SQLException {
    if (shouldThrow("getMoreResults"))
      throw new SQLException("CallableStatement bombed");
    return it.getMoreResults(current);
  }
  public int getQueryTimeout() throws SQLException {
    if (shouldThrow("getQueryTimeout"))
      throw new SQLException("CallableStatement bombed");
    return it.getQueryTimeout();
  }
  public ResultSet getResultSet() throws SQLException {
    if (shouldThrow("getResultSet"))
      throw new SQLException("CallableStatement bombed");
    return new ThrowingResultSet(it.getResultSet());
  }
  public int getResultSetConcurrency() throws SQLException {
    if (shouldThrow("getResultSetConcurrency"))
      throw new SQLException("CallableStatement bombed");
    return it.getResultSetConcurrency();
  }
  public int getResultSetHoldability() throws SQLException {
    if (shouldThrow("getResultSetHoldability"))
      throw new SQLException("CallableStatement bombed");
    return it.getResultSetHoldability();
  }
  public int getResultSetType() throws SQLException {
    if (shouldThrow("getResultSetType"))
      throw new SQLException("CallableStatement bombed");
    return it.getResultSetType();
  }
  public int getUpdateCount() throws SQLException {
    if (shouldThrow("getUpdateCount"))
      throw new SQLException("CallableStatement bombed");
    return it.getUpdateCount();
  }
  public SQLWarning getWarnings() throws SQLException {
    if (shouldThrow("getWarnings"))
      throw new SQLException("CallableStatement bombed");
    return it.getWarnings();
  }
  public void setCursorName(String name) throws SQLException {
    if (shouldThrow("setCursorName"))
      throw new SQLException("CallableStatement bombed");
    it.setCursorName(name);
  }
  public void setEscapeProcessing(boolean enable) throws SQLException {
    if (shouldThrow("setEscapeProcessing"))
      throw new SQLException("CallableStatement bombed");
    it.setEscapeProcessing(enable);
  }
  public void setFetchDirection(int direction) throws SQLException {
    if (shouldThrow("setFetchDirection"))
      throw new SQLException("CallableStatement bombed");
    it.setFetchDirection(direction);
    
  }
  public void setFetchSize(int rows) throws SQLException {
    if (shouldThrow("setFetchSize"))
      throw new SQLException("CallableStatement bombed");
    it.setFetchSize(rows);
  }
  public void setMaxFieldSize(int max) throws SQLException {
    if (shouldThrow("setMaxFieldSize"))
      throw new SQLException("CallableStatement bombed");
    it.setMaxFieldSize(max);
  }
  public void setMaxRows(int max) throws SQLException {
    if (shouldThrow("setMaxRows"))
      throw new SQLException("CallableStatement bombed");
    it.setMaxRows(max);
  }
  public void setQueryTimeout(int seconds) throws SQLException {
    if (shouldThrow("setQueryTimeout"))
      throw new SQLException("CallableStatement bombed");
    it.setQueryTimeout(seconds);
  }
}
