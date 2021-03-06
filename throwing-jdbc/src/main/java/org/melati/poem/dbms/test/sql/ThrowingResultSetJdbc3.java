/*
 * $Source$
 * $Revision$
 *
 * Copyright (C) 2008 Tim Pizey
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
import java.sql.Date;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Map;

/**
 * The JDBC3 methods of a {@link ResultSet}, decorated to throw an SQLException on command.
 * 
 * @author timp
 * @since  5 Feb 2008
 *
 */
public abstract class ThrowingResultSetJdbc3 
    extends Thrower 
    implements ResultSet {

  ResultSet it = null;


  /**
   * {@inheritDoc}
   * @see java.sql.ResultSet#absolute(int)
   */
  public boolean absolute(int row) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "absolute"))
      throw new SQLException("ResultSet bombed");
     return it.absolute(row);
  }

  /**
   * {@inheritDoc}
   * @see java.sql.ResultSet#afterLast()
   */
  public void afterLast() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "afterLast"))
      throw new SQLException("ResultSet bombed");
    it.afterLast();
  }

  /**
   * {@inheritDoc}
   * @see java.sql.ResultSet#beforeFirst()
   */
  public void beforeFirst() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "beforeFirst"))
      throw new SQLException("ResultSet bombed");
    it.beforeFirst();
  }

  /**
   * {@inheritDoc}
   * @see java.sql.ResultSet#cancelRowUpdates()
   */
  public void cancelRowUpdates() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "cancelRowUpdates"))
      throw new SQLException("ResultSet bombed");
    it.cancelRowUpdates();
  }

  /**
   * {@inheritDoc}
   * @see java.sql.ResultSet#clearWarnings()
   */
  public void clearWarnings() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "clearWarnings"))
      throw new SQLException("ResultSet bombed");
    it.clearWarnings();
  }

  /**
   * {@inheritDoc}
   * @see java.sql.ResultSet#close()
   */
  public void close() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "close"))
      throw new SQLException("ResultSet bombed");
    it.close();
  }

  /**
   * {@inheritDoc}
   * @see java.sql.ResultSet#deleteRow()
   */
  public void deleteRow() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "deleteRow"))
      throw new SQLException("ResultSet bombed");
    it.deleteRow();
  }

  /**
   * {@inheritDoc}
   * @see java.sql.ResultSet#findColumn(java.lang.String)
   */
  public int findColumn(String columnName) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "findColumn"))
      throw new SQLException("ResultSet bombed");
    return it.findColumn(columnName);
  }

  /**
   * {@inheritDoc}
   * @see java.sql.ResultSet#first()
   */
  public boolean first() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "first"))
      throw new SQLException("ResultSet bombed");
    return it.first();
  }

  /**
   * {@inheritDoc}
   * @see java.sql.ResultSet#getArray(int)
   */
  public Array getArray(int i) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getArray"))
      throw new SQLException("ResultSet bombed");
    return new ThrowingArray(it.getArray(i));
  }

  /**
   * {@inheritDoc}
   * @see java.sql.ResultSet#getArray(java.lang.String)
   */
  public Array getArray(String colName) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getArray"))
      throw new SQLException("ResultSet bombed");
    return new ThrowingArray(it.getArray(colName));
  }

  /**
   * {@inheritDoc}
   * @see java.sql.ResultSet#getAsciiStream(int)
   */
  public InputStream getAsciiStream(int columnIndex) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getAsciiStream"))
      throw new SQLException("ResultSet bombed");
    return it.getAsciiStream(columnIndex);
  }

  /**
   * {@inheritDoc}
   * @see java.sql.ResultSet#getAsciiStream(java.lang.String)
   */
  public InputStream getAsciiStream(String columnName) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getAsciiStream"))
      throw new SQLException("ResultSet bombed");
    return it.getAsciiStream(columnName);
  }

  /**
   * {@inheritDoc}
   * @see java.sql.ResultSet#getBigDecimal(int)
   */
  public BigDecimal getBigDecimal(int columnIndex) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getBigDecimal"))
      throw new SQLException("ResultSet bombed");
    return it.getBigDecimal(columnIndex);
  }

  /**
   * {@inheritDoc}
   * @see java.sql.ResultSet#getBigDecimal(java.lang.String)
   */
  public BigDecimal getBigDecimal(String columnName) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getBigDecimal"))
      throw new SQLException("ResultSet bombed");
    return it.getBigDecimal(columnName);
  }

  /**
   * {@inheritDoc}
   * @see java.sql.ResultSet#getBigDecimal(int, int)
   */
  @SuppressWarnings("deprecation")
  public BigDecimal getBigDecimal(int columnIndex, int scale)
      throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getBigDecimal"))
      throw new SQLException("ResultSet bombed");
    return it.getBigDecimal(columnIndex, scale);
  }

  /**
   * {@inheritDoc}
   * @see java.sql.ResultSet#getBigDecimal(java.lang.String, int)
   */
  @SuppressWarnings("deprecation")
  public BigDecimal getBigDecimal(String columnName, int scale)
      throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getBigDecimal"))
      throw new SQLException("ResultSet bombed");
    return it.getBigDecimal(columnName, scale);
  }

  /**
   * {@inheritDoc}
   * @see java.sql.ResultSet#getBinaryStream(int)
   */
  public InputStream getBinaryStream(int columnIndex) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getBinaryStream"))
      throw new SQLException("ResultSet bombed");
    return it.getBinaryStream(columnIndex);
  }

  /**
   * {@inheritDoc}
   * @see java.sql.ResultSet#getBinaryStream(java.lang.String)
   */
  public InputStream getBinaryStream(String columnName) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getBinaryStream"))
      throw new SQLException("ResultSet bombed");
    return it.getBinaryStream(columnName);
  }

  /**
   * {@inheritDoc}
   * @see java.sql.ResultSet#getBlob(int)
   */
  public Blob getBlob(int i) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getBlob"))
      throw new SQLException("ResultSet bombed");
    return new ThrowingBlob(it.getBlob(i));
  }

  /**
   * {@inheritDoc}
   * @see java.sql.ResultSet#getBlob(java.lang.String)
   */
  public Blob getBlob(String colName) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getBlob"))
      throw new SQLException("ResultSet bombed");
    return new ThrowingBlob(it.getBlob(colName));
  }

  /**
   * {@inheritDoc}
   * @see java.sql.ResultSet#getBoolean(int)
   */
  public boolean getBoolean(int columnIndex) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getBoolean"))
      throw new SQLException("ResultSet bombed");
    return it.getBoolean(columnIndex);
  }

  /**
   * {@inheritDoc}
   * @see java.sql.ResultSet#getBoolean(java.lang.String)
   */
  public boolean getBoolean(String columnName) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getBoolean"))
      throw new SQLException("ResultSet bombed");
    return it.getBoolean(columnName);
  }

  /**
   * {@inheritDoc}
   * @see java.sql.ResultSet#getByte(int)
   */
  public byte getByte(int columnIndex) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getByte"))
      throw new SQLException("ResultSet bombed");
    return it.getByte(columnIndex);
  }

  /**
   * {@inheritDoc}
   * @see java.sql.ResultSet#getByte(java.lang.String)
   */
  public byte getByte(String columnName) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getByte"))
      throw new SQLException("ResultSet bombed");
    return it.getByte(columnName);
  }

  /**
   * {@inheritDoc}
   * @see java.sql.ResultSet#getBytes(int)
   */
  public byte[] getBytes(int columnIndex) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getBytes"))
      throw new SQLException("ResultSet bombed");
    return it.getBytes(columnIndex);
  }

  /**
   * {@inheritDoc}
   * @see java.sql.ResultSet#getBytes(java.lang.String)
   */
  public byte[] getBytes(String columnName) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getBytes"))
      throw new SQLException("ResultSet bombed");
    return it.getBytes(columnName);
  }

  /**
   * {@inheritDoc}
   * @see java.sql.ResultSet#getCharacterStream(int)
   */
  public Reader getCharacterStream(int columnIndex) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getCharacterStream"))
      throw new SQLException("ResultSet bombed");
    return it.getCharacterStream(columnIndex);
  }

  /**
   * {@inheritDoc}
   * @see java.sql.ResultSet#getCharacterStream(java.lang.String)
   */
  public Reader getCharacterStream(String columnName) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getCharacterStream"))
      throw new SQLException("ResultSet bombed");
    return it.getCharacterStream(columnName);
  }

  /**
   * {@inheritDoc}
   * @see java.sql.ResultSet#getClob(int)
   */
  public Clob getClob(int i) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getClob"))
      throw new SQLException("ResultSet bombed");
    return new ThrowingClob(it.getClob(i));
  }

  /**
   * {@inheritDoc}
   * @see java.sql.ResultSet#getClob(java.lang.String)
   */
  public Clob getClob(String colName) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getClob"))
      throw new SQLException("ResultSet bombed");
    return new ThrowingClob(it.getClob(colName));
  }

  /**
   * {@inheritDoc}
   * @see java.sql.ResultSet#getConcurrency()
   */
  public int getConcurrency() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getConcurrency"))
      throw new SQLException("ResultSet bombed");
    return it.getConcurrency();
  }

  /**
   * {@inheritDoc}
   * @see java.sql.ResultSet#getCursorName()
   */
  public String getCursorName() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getCursorName"))
      throw new SQLException("ResultSet bombed");
    return it.getCursorName();
  }

  /**
   * {@inheritDoc}
   * @see java.sql.ResultSet#getDate(int)
   */
  public Date getDate(int columnIndex) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getDate"))
      throw new SQLException("ResultSet bombed");
    return it.getDate(columnIndex);
  }

  /**
   * {@inheritDoc}
   * @see java.sql.ResultSet#getDate(java.lang.String)
   */
  public Date getDate(String columnName) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getDate"))
      throw new SQLException("ResultSet bombed");
    return it.getDate(columnName);
  }

  /**
   * {@inheritDoc}
   * @see java.sql.ResultSet#getDate(int, java.util.Calendar)
   */
  public Date getDate(int columnIndex, Calendar cal) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getDate"))
      throw new SQLException("ResultSet bombed");
    return it.getDate(columnIndex, cal);
  }

  /**
   * {@inheritDoc}
   * @see java.sql.ResultSet#getDate(java.lang.String, java.util.Calendar)
   */
  public Date getDate(String columnName, Calendar cal) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getDate"))
      throw new SQLException("ResultSet bombed");
    return it.getDate(columnName, cal);
  }

  /**
   * {@inheritDoc}
   * @see java.sql.ResultSet#getDouble(int)
   */
  public double getDouble(int columnIndex) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getDouble"))
      throw new SQLException("ResultSet bombed");
    return it.getDouble(columnIndex);
  }

  /**
   * {@inheritDoc}
   * @see java.sql.ResultSet#getDouble(java.lang.String)
   */
  public double getDouble(String columnName) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getDouble"))
      throw new SQLException("ResultSet bombed");
    return it.getDouble(columnName);
  }

  /**
   * {@inheritDoc}
   * @see java.sql.ResultSet#getFetchDirection()
   */
  public int getFetchDirection() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getFetchDirection"))
      throw new SQLException("ResultSet bombed");
    return it.getFetchDirection();
  }

  /**
   * {@inheritDoc}
   * @see java.sql.ResultSet#getFetchSize()
   */
  public int getFetchSize() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getFetchSize"))
      throw new SQLException("ResultSet bombed");
    return it.getFetchSize();
  }

  /**
   * {@inheritDoc}
   * @see java.sql.ResultSet#getFloat(int)
   */
  public float getFloat(int columnIndex) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getFloat"))
      throw new SQLException("ResultSet bombed");
    return it.getFloat(columnIndex);
  }

  /**
   * {@inheritDoc}
   * @see java.sql.ResultSet#getFloat(java.lang.String)
   */
  public float getFloat(String columnName) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getFloat"))
      throw new SQLException("ResultSet bombed");
    return it.getFloat(columnName);
  }

  /**
   * {@inheritDoc}
   * @see java.sql.ResultSet#getInt(int)
   */
  public int getInt(int columnIndex) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getInt"))
      throw new SQLException("ResultSet bombed");
    return it.getInt(columnIndex);
  }

  /**
   * {@inheritDoc}
   * @see java.sql.ResultSet#getInt(java.lang.String)
   */
  public int getInt(String columnName) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getInt"))
      throw new SQLException("ResultSet bombed");
    return it.getInt(columnName);
  }

  /**
   * {@inheritDoc}
   * @see java.sql.ResultSet#getLong(int)
   */
  public long getLong(int columnIndex) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getLong"))
      throw new SQLException("ResultSet bombed");
    return it.getLong(columnIndex);
  }

  /**
   * {@inheritDoc}
   * @see java.sql.ResultSet#getLong(java.lang.String)
   */
  public long getLong(String columnName) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getLong"))
      throw new SQLException("ResultSet bombed");
    return it.getLong(columnName);
  }

  /**
   * {@inheritDoc}
   * @see java.sql.ResultSet#getMetaData()
   */
  public ResultSetMetaData getMetaData() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getMetaData"))
      throw new SQLException("ResultSet bombed");
    return new ThrowingResultSetMetaData(it.getMetaData());
  }

  /**
   * {@inheritDoc}
   * @see java.sql.ResultSet#getObject(int)
   */
  public Object getObject(int columnIndex) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getObject"))
      throw new SQLException("ResultSet bombed");
    return it.getObject(columnIndex);
  }

  /**
   * {@inheritDoc}
   * @see java.sql.ResultSet#getObject(java.lang.String)
   */
  public Object getObject(String columnName) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getObject"))
      throw new SQLException("ResultSet bombed");
    return it.getObject(columnName);
  }

  /**
   * {@inheritDoc}
   * @see java.sql.ResultSet#getObject(int, java.util.Map)
   */
  public Object getObject(int i, Map<String, Class<?>>map) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getObject"))
      throw new SQLException("ResultSet bombed");

    return it.getObject(i, map);
  }

  /**
   * {@inheritDoc}
   * @see java.sql.ResultSet#getObject(java.lang.String, java.util.Map)
   */
  public Object getObject(String colName, Map<String, Class<?>> map) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getObject"))
      throw new SQLException("ResultSet bombed");
    return it.getObject(colName, map);
  }

  /**
   * {@inheritDoc}
   * @see java.sql.ResultSet#getRef(int)
   */
  public Ref getRef(int i) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getRef"))
      throw new SQLException("ResultSet bombed");
    return new ThrowingRef(it.getRef(i));
  }

  /**
   * {@inheritDoc}
   * @see java.sql.ResultSet#getRef(java.lang.String)
   */
  public Ref getRef(String colName) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getRef"))
      throw new SQLException("ResultSet bombed");
    return new ThrowingRef(it.getRef(colName));
  }

  /**
   * {@inheritDoc}
   * @see java.sql.ResultSet#getRow()
   */
  public int getRow() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getRow"))
      throw new SQLException("ResultSet bombed");
    return it.getRow();
  }

  /**
   * {@inheritDoc}
   * @see java.sql.ResultSet#getShort(int)
   */
  public short getShort(int columnIndex) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getShort"))
      throw new SQLException("ResultSet bombed");
    return it.getShort(columnIndex);
  }

  /**
   * {@inheritDoc}
   * @see java.sql.ResultSet#getShort(java.lang.String)
   */
  public short getShort(String columnName) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getShort"))
      throw new SQLException("ResultSet bombed");
    return it.getShort(columnName);
  }

  /**
   * {@inheritDoc}
   * @see java.sql.ResultSet#getStatement()
   */
  public Statement getStatement() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getStatement"))
      throw new SQLException("ResultSet bombed");
    return new ThrowingStatement(it.getStatement());
  }

  /**
   * {@inheritDoc}
   * @see java.sql.ResultSet#getString(int)
   */
  public String getString(int columnIndex) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getString"))
      throw new SQLException("ResultSet bombed");
    return it.getString(columnIndex);
  }

  /**
   * {@inheritDoc}
   * @see java.sql.ResultSet#getString(java.lang.String)
   */
  public String getString(String columnName) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getString"))
      throw new SQLException("ResultSet bombed");
    return it.getString(columnName);
  }

  /**
   * {@inheritDoc}
   * @see java.sql.ResultSet#getTime(int)
   */
  public Time getTime(int columnIndex) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getTime"))
      throw new SQLException("ResultSet bombed");
    return it.getTime(columnIndex);
  }

  /**
   * {@inheritDoc}
   * @see java.sql.ResultSet#getTime(java.lang.String)
   */
  public Time getTime(String columnName) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getTime"))
      throw new SQLException("ResultSet bombed");
    return it.getTime(columnName);
  }

  /**
   * {@inheritDoc}
   * @see java.sql.ResultSet#getTime(int, java.util.Calendar)
   */
  public Time getTime(int columnIndex, Calendar cal) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getTime"))
      throw new SQLException("ResultSet bombed");
    return it.getTime(columnIndex, cal);
  }

  /**
   * {@inheritDoc}
   * @see java.sql.ResultSet#getTime(java.lang.String, java.util.Calendar)
   */
  public Time getTime(String columnName, Calendar cal) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getTime"))
      throw new SQLException("ResultSet bombed");
    return it.getTime(columnName, cal);
  }

  /**
   * {@inheritDoc}
   * @see java.sql.ResultSet#getTimestamp(int)
   */
  public Timestamp getTimestamp(int columnIndex) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getTimestamp"))
      throw new SQLException("ResultSet bombed");
    return it.getTimestamp(columnIndex);
  }

  /**
   * {@inheritDoc}
   * @see java.sql.ResultSet#getTimestamp(java.lang.String)
   */
  public Timestamp getTimestamp(String columnName) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getTimestamp"))
      throw new SQLException("ResultSet bombed");
    return it.getTimestamp(columnName);
  }

  /**
   * {@inheritDoc}
   * @see java.sql.ResultSet#getTimestamp(int, java.util.Calendar)
   */
  public Timestamp getTimestamp(int columnIndex, Calendar cal)
      throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getTimestamp"))
      throw new SQLException("ResultSet bombed");
    return it.getTimestamp(columnIndex, cal);
  }

  /**
   * {@inheritDoc}
   * @see java.sql.ResultSet#getTimestamp(java.lang.String, java.util.Calendar)
   */
  public Timestamp getTimestamp(String columnName, Calendar cal)
      throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getTimestamp"))
      throw new SQLException("ResultSet bombed");
    return it.getTimestamp(columnName, cal);
  }

  /**
   * {@inheritDoc}
   * @see java.sql.ResultSet#getType()
   */
  public int getType() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getType"))
      throw new SQLException("ResultSet bombed");
    return it.getType();
  }

  /**
   * {@inheritDoc}
   * @see java.sql.ResultSet#getURL(int)
   */
  public URL getURL(int columnIndex) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getURL"))
      throw new SQLException("ResultSet bombed");
    return it.getURL(columnIndex);
  }

  /**
   * {@inheritDoc}
   * @see java.sql.ResultSet#getURL(java.lang.String)
   */
  public URL getURL(String columnName) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getURL"))
      throw new SQLException("ResultSet bombed");

    return it.getURL(columnName);
  }

  /**
   * {@inheritDoc}
   * @see java.sql.ResultSet#getUnicodeStream(int)
   */
  @SuppressWarnings("deprecation")
  public InputStream getUnicodeStream(int columnIndex) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getUnicodeStream"))
      throw new SQLException("ResultSet bombed");
    return it.getUnicodeStream(columnIndex);
  }

  /**
   * {@inheritDoc}
   * @see java.sql.ResultSet#getUnicodeStream(java.lang.String)
   */
  @SuppressWarnings("deprecation")
  public InputStream getUnicodeStream(String columnName) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getUnicodeStream"))
      throw new SQLException("ResultSet bombed");
    return it.getUnicodeStream(columnName);
  }

  /**
   * {@inheritDoc}
   * @see java.sql.ResultSet#getWarnings()
   */
  public SQLWarning getWarnings() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getWarnings"))
      throw new SQLException("ResultSet bombed");
    return it.getWarnings();
  }

  /**
   * {@inheritDoc}
   * @see java.sql.ResultSet#insertRow()
   */
  public void insertRow() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "insertRow"))
      throw new SQLException("ResultSet bombed");
    it.insertRow();
  }

  /**
   * {@inheritDoc}
   * @see java.sql.ResultSet#isAfterLast()
   */
  public boolean isAfterLast() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "isAfterLast"))
      throw new SQLException("ResultSet bombed");
    return it.isAfterLast();
  }

  /**
   * {@inheritDoc}
   * @see java.sql.ResultSet#isBeforeFirst()
   */
  public boolean isBeforeFirst() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "isBeforeFirst"))
      throw new SQLException("ResultSet bombed");
    return it.isBeforeFirst();
  }

  /**
   * {@inheritDoc}
   * @see java.sql.ResultSet#isFirst()
   */
  public boolean isFirst() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "isFirst"))
      throw new SQLException("ResultSet bombed");
    return it.isFirst();
  }

  /**
   * {@inheritDoc}
   * @see java.sql.ResultSet#isLast()
   */
  public boolean isLast() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "isLast"))
      throw new SQLException("ResultSet bombed");
    return it.isLast();
  }

  /**
   * {@inheritDoc}
   * @see java.sql.ResultSet#last()
   */
  public boolean last() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "last"))
      throw new SQLException("ResultSet bombed");
    return it.last();
  }

  /**
   * {@inheritDoc}
   * @see java.sql.ResultSet#moveToCurrentRow()
   */
  public void moveToCurrentRow() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "moveToCurrentRow"))
      throw new SQLException("ResultSet bombed");
    it.moveToCurrentRow();
  }

  /**
   * {@inheritDoc}
   * @see java.sql.ResultSet#moveToInsertRow()
   */
  public void moveToInsertRow() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "moveToInsertRow"))
      throw new SQLException("ResultSet bombed");
    it.moveToInsertRow();
  }

  /**
   * {@inheritDoc}
   * @see java.sql.ResultSet#next()
   */
  public boolean next() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "next"))
      throw new SQLException("ResultSet bombed");

    return it.next();
  }

  /**
   * {@inheritDoc}
   * @see java.sql.ResultSet#previous()
   */
  public boolean previous() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "previous"))
      throw new SQLException("ResultSet bombed");
    return it.previous();
  }

  /**
   * {@inheritDoc}
   * @see java.sql.ResultSet#refreshRow()
   */
  public void refreshRow() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "refreshRow"))
      throw new SQLException("ResultSet bombed");
    it.refreshRow();
  }

  /**
   * {@inheritDoc}
   * @see java.sql.ResultSet#relative(int)
   */
  public boolean relative(int rows) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "relative"))
      throw new SQLException("ResultSet bombed");
    return it.relative(rows);
  }

  /**
   * {@inheritDoc}
   * @see java.sql.ResultSet#rowDeleted()
   */
  public boolean rowDeleted() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "rowDeleted"))
      throw new SQLException("ResultSet bombed");
    return it.rowDeleted();
  }

  /**
   * {@inheritDoc}
   * @see java.sql.ResultSet#rowInserted()
   */
  public boolean rowInserted() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "rowInserted"))
      throw new SQLException("ResultSet bombed");
    return it.rowInserted();
  }

  /**
   * {@inheritDoc}
   * @see java.sql.ResultSet#rowUpdated()
   */
  public boolean rowUpdated() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "rowUpdated"))
      throw new SQLException("ResultSet bombed");
    return it.rowUpdated();
  }

  /**
   * {@inheritDoc}
   * @see java.sql.ResultSet#setFetchDirection(int)
   */
  public void setFetchDirection(int direction) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "setFetchDirection"))
      throw new SQLException("ResultSet bombed");
    it.setFetchDirection(direction);
  }

  /**
   * {@inheritDoc}
   * @see java.sql.ResultSet#setFetchSize(int)
   */
  public void setFetchSize(int rows) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "setFetchSize"))
      throw new SQLException("ResultSet bombed");
    it.setFetchSize(rows);
  }

  /**
   * {@inheritDoc}
   * @see java.sql.ResultSet#updateArray(int, java.sql.Array)
   */
  public void updateArray(int columnIndex, Array x) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "updateArray"))
      throw new SQLException("ResultSet bombed");
    it.updateArray(columnIndex, x);
  }

  /**
   * {@inheritDoc}
   * @see java.sql.ResultSet#updateArray(java.lang.String, java.sql.Array)
   */
  public void updateArray(String columnName, Array x) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "updateArray"))
      throw new SQLException("ResultSet bombed");
    it.updateArray(columnName, x);
  }

  /**
   * {@inheritDoc}
   * @see java.sql.ResultSet#updateAsciiStream(int, java.io.InputStream, int)
   */
  public void updateAsciiStream(int columnIndex, InputStream x, int length)
      throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "updateAsciiStream"))
      throw new SQLException("ResultSet bombed");
    it.updateAsciiStream(columnIndex, x, length);
  }

  /**
   * {@inheritDoc}
   * @see java.sql.ResultSet#updateAsciiStream(java.lang.String, java.io.InputStream, int)
   */
  public void updateAsciiStream(String columnName, InputStream x, int length)
      throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "updateAsciiStream"))
      throw new SQLException("ResultSet bombed");
    it.updateAsciiStream(columnName, x, length);
  }

  /**
   * {@inheritDoc}
   * @see java.sql.ResultSet#updateBigDecimal(int, java.math.BigDecimal)
   */
  public void updateBigDecimal(int columnIndex, BigDecimal x)
      throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "updateBigDecimal"))
      throw new SQLException("ResultSet bombed");
    it.updateBigDecimal(columnIndex, x);
  }

  /**
   * {@inheritDoc}
   * @see java.sql.ResultSet#updateBigDecimal(java.lang.String, java.math.BigDecimal)
   */
  public void updateBigDecimal(String columnName, BigDecimal x)
      throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "updateBigDecimal"))
      throw new SQLException("ResultSet bombed");
    it.updateBigDecimal(columnName, x);
  }

  /**
   * {@inheritDoc}
   * @see java.sql.ResultSet#updateBinaryStream(int, java.io.InputStream, int)
   */
  public void updateBinaryStream(int columnIndex, InputStream x, int length)
      throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "updateBinaryStream"))
      throw new SQLException("ResultSet bombed");
    it.updateBinaryStream(columnIndex, x, length);
  }

  /**
   * {@inheritDoc}
   * @see java.sql.ResultSet#updateBinaryStream(java.lang.String, java.io.InputStream, int)
   */
  public void updateBinaryStream(String columnName, InputStream x, int length)
      throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "updateBinaryStream"))
      throw new SQLException("ResultSet bombed");
    it.updateBinaryStream(columnName, x, length);
  }

  /**
   * {@inheritDoc}
   * @see java.sql.ResultSet#updateBlob(int, java.sql.Blob)
   */
  public void updateBlob(int columnIndex, Blob x) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "updateBlob"))
      throw new SQLException("ResultSet bombed");
    it.updateBlob(columnIndex, x);
  }

  /**
   * {@inheritDoc}
   * @see java.sql.ResultSet#updateBlob(java.lang.String, java.sql.Blob)
   */
  public void updateBlob(String columnName, Blob x) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "updateBlob"))
      throw new SQLException("ResultSet bombed");
    it.updateBlob(columnName, x);
  }

  /**
   * {@inheritDoc}
   * @see java.sql.ResultSet#updateBoolean(int, boolean)
   */
  public void updateBoolean(int columnIndex, boolean x) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "updateBoolean"))
      throw new SQLException("ResultSet bombed");
    it.updateBoolean(columnIndex, x);
  }

  /**
   * {@inheritDoc}
   * @see java.sql.ResultSet#updateBoolean(java.lang.String, boolean)
   */
  public void updateBoolean(String columnName, boolean x) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "updateBoolean"))
      throw new SQLException("ResultSet bombed");
    it.updateBoolean(columnName, x);
  }

  /**
   * {@inheritDoc}
   * @see java.sql.ResultSet#updateByte(int, byte)
   */
  public void updateByte(int columnIndex, byte x) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "updateByte"))
      throw new SQLException("ResultSet bombed");
    it.updateByte(columnIndex, x);
  }

  /**
   * {@inheritDoc}
   * @see java.sql.ResultSet#updateByte(java.lang.String, byte)
   */
  public void updateByte(String columnName, byte x) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "updateByte"))
      throw new SQLException("ResultSet bombed");
    it.updateByte(columnName, x);
  }

  /**
   * {@inheritDoc}
   * @see java.sql.ResultSet#updateBytes(int, byte[])
   */
  public void updateBytes(int columnIndex, byte[] x) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "updateBytes"))
      throw new SQLException("ResultSet bombed");
    it.updateBytes(columnIndex, x);
  }

  /**
   * {@inheritDoc}
   * @see java.sql.ResultSet#updateBytes(java.lang.String, byte[])
   */
  public void updateBytes(String columnName, byte[] x) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "updateBytes"))
      throw new SQLException("ResultSet bombed");
    it.updateBytes(columnName, x);
  }

  /**
   * {@inheritDoc}
   * @see java.sql.ResultSet#updateCharacterStream(int, java.io.Reader, int)
   */
  public void updateCharacterStream(int columnIndex, Reader x, int length)
      throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "updateCharacterStream"))
      throw new SQLException("ResultSet bombed");
    it.updateCharacterStream(columnIndex, x, length);
  }

  /**
   * {@inheritDoc}
   * @see java.sql.ResultSet#updateCharacterStream(java.lang.String, java.io.Reader, int)
   */
  public void updateCharacterStream(String columnName, Reader reader, int length)
      throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "updateCharacterStream"))
      throw new SQLException("ResultSet bombed");
    it.updateCharacterStream(columnName, reader, length);
  }

  /**
   * {@inheritDoc}
   * @see java.sql.ResultSet#updateClob(int, java.sql.Clob)
   */
  public void updateClob(int columnIndex, Clob x) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "updateClob"))
      throw new SQLException("ResultSet bombed");
    it.updateClob(columnIndex, x);
  }

  /**
   * {@inheritDoc}
   * @see java.sql.ResultSet#updateClob(java.lang.String, java.sql.Clob)
   */
  public void updateClob(String columnName, Clob x) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "updateClob"))
      throw new SQLException("ResultSet bombed");
    it.updateClob(columnName, x);
  }

  /**
   * {@inheritDoc}
   * @see java.sql.ResultSet#updateDate(int, java.sql.Date)
   */
  public void updateDate(int columnIndex, Date x) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "updateDate"))
      throw new SQLException("ResultSet bombed");
    it.updateDate(columnIndex, x);
  }

  /**
   * {@inheritDoc}
   * @see java.sql.ResultSet#updateDate(java.lang.String, java.sql.Date)
   */
  public void updateDate(String columnName, Date x) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "updateDate"))
      throw new SQLException("ResultSet bombed");
    it.updateDate(columnName, x);
  }

  /**
   * {@inheritDoc}
   * @see java.sql.ResultSet#updateDouble(int, double)
   */
  public void updateDouble(int columnIndex, double x) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "updateDouble"))
      throw new SQLException("ResultSet bombed");
    it.updateDouble(columnIndex, x);
  }

  /**
   * {@inheritDoc}
   * @see java.sql.ResultSet#updateDouble(java.lang.String, double)
   */
  public void updateDouble(String columnName, double x) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "updateDouble"))
      throw new SQLException("ResultSet bombed");
    it.updateDouble(columnName, x);
  }

  /**
   * {@inheritDoc}
   * @see java.sql.ResultSet#updateFloat(int, float)
   */
  public void updateFloat(int columnIndex, float x) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "updateFloat"))
      throw new SQLException("ResultSet bombed");
    it.updateFloat(columnIndex, x);
  }

  /**
   * {@inheritDoc}
   * @see java.sql.ResultSet#updateFloat(java.lang.String, float)
   */
  public void updateFloat(String columnName, float x) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "updateFloat"))
      throw new SQLException("ResultSet bombed");
    it.updateFloat(columnName, x);
  }

  /**
   * {@inheritDoc}
   * @see java.sql.ResultSet#updateInt(int, int)
   */
  public void updateInt(int columnIndex, int x) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "updateInt"))
      throw new SQLException("ResultSet bombed");
    it.updateInt(columnIndex, x);
  }

  /**
   * {@inheritDoc}
   * @see java.sql.ResultSet#updateInt(java.lang.String, int)
   */
  public void updateInt(String columnName, int x) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "updateInt"))
      throw new SQLException("ResultSet bombed");
    it.updateInt(columnName, x);
  }

  /**
   * {@inheritDoc}
   * @see java.sql.ResultSet#updateLong(int, long)
   */
  public void updateLong(int columnIndex, long x) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "updateLong"))
      throw new SQLException("ResultSet bombed");
    it.updateLong(columnIndex, x);
  }

  /**
   * {@inheritDoc}
   * @see java.sql.ResultSet#updateLong(java.lang.String, long)
   */
  public void updateLong(String columnName, long x) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "updateLong"))
      throw new SQLException("ResultSet bombed");
    it.updateLong(columnName, x);
  }

  /**
   * {@inheritDoc}
   * @see java.sql.ResultSet#updateNull(int)
   */
  public void updateNull(int columnIndex) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "updateNull"))
      throw new SQLException("ResultSet bombed");
    it.updateNull(columnIndex);
  }

  /**
   * {@inheritDoc}
   * @see java.sql.ResultSet#updateNull(java.lang.String)
   */
  public void updateNull(String columnName) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "updateNull"))
      throw new SQLException("ResultSet bombed");
    it.updateNull(columnName);
  }

  /**
   * {@inheritDoc}
   * @see java.sql.ResultSet#updateObject(int, java.lang.Object)
   */
  public void updateObject(int columnIndex, Object x) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "updateObject"))
      throw new SQLException("ResultSet bombed");
    it.updateObject(columnIndex, x);
  }

  /**
   * {@inheritDoc}
   * @see java.sql.ResultSet#updateObject(java.lang.String, java.lang.Object)
   */
  public void updateObject(String columnName, Object x) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "updateObject"))
      throw new SQLException("ResultSet bombed");
    it.updateObject(columnName, x);
  }

  /**
   * {@inheritDoc}
   * @see java.sql.ResultSet#updateObject(int, java.lang.Object, int)
   */
  public void updateObject(int columnIndex, Object x, int scale)
      throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "updateObject"))
      throw new SQLException("ResultSet bombed");
    it.updateObject(columnIndex, x, scale);
  }

  /**
   * {@inheritDoc}
   * @see java.sql.ResultSet#updateObject(java.lang.String, java.lang.Object, int)
   */
  public void updateObject(String columnName, Object x, int scale)
      throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "updateObject"))
      throw new SQLException("ResultSet bombed");
    it.updateObject(columnName, x, scale);
  }

  /**
   * {@inheritDoc}
   * @see java.sql.ResultSet#updateRef(int, java.sql.Ref)
   */
  public void updateRef(int columnIndex, Ref x) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "updateRef"))
      throw new SQLException("ResultSet bombed");
    it.updateRef(columnIndex, x);
  }

  /**
   * {@inheritDoc}
   * @see java.sql.ResultSet#updateRef(java.lang.String, java.sql.Ref)
   */
  public void updateRef(String columnName, Ref x) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "updateRef"))
      throw new SQLException("ResultSet bombed");
    it.updateRef(columnName, x);
  }

  /**
   * {@inheritDoc}
   * @see java.sql.ResultSet#updateRow()
   */
  public void updateRow() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "updateRow"))
      throw new SQLException("ResultSet bombed");
    it.updateRow();
  }

  /**
   * {@inheritDoc}
   * @see java.sql.ResultSet#updateShort(int, short)
   */
  public void updateShort(int columnIndex, short x) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "updateShort"))
      throw new SQLException("ResultSet bombed");
    it.updateShort(columnIndex, x);
  }

  /**
   * {@inheritDoc}
   * @see java.sql.ResultSet#updateShort(java.lang.String, short)
   */
  public void updateShort(String columnName, short x) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "updateShort"))
      throw new SQLException("ResultSet bombed");
    it.updateShort(columnName, x);
  }

  /**
   * {@inheritDoc}
   * @see java.sql.ResultSet#updateString(int, java.lang.String)
   */
  public void updateString(int columnIndex, String x) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "updateString"))
      throw new SQLException("ResultSet bombed");
    it.updateString(columnIndex, x);
  }

  /**
   * {@inheritDoc}
   * @see java.sql.ResultSet#updateString(java.lang.String, java.lang.String)
   */
  public void updateString(String columnName, String x) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "updateString"))
      throw new SQLException("ResultSet bombed");
    it.updateString(columnName, x);
  }

  /**
   * {@inheritDoc}
   * @see java.sql.ResultSet#updateTime(int, java.sql.Time)
   */
  public void updateTime(int columnIndex, Time x) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "updateTime"))
      throw new SQLException("ResultSet bombed");
    it.updateTime(columnIndex, x);
  }

  /**
   * {@inheritDoc}
   * @see java.sql.ResultSet#updateTime(java.lang.String, java.sql.Time)
   */
  public void updateTime(String columnName, Time x) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "updateTime"))
      throw new SQLException("ResultSet bombed");
    it.updateTime(columnName, x);
  }

  /**
   * {@inheritDoc}
   * @see java.sql.ResultSet#updateTimestamp(int, java.sql.Timestamp)
   */
  public void updateTimestamp(int columnIndex, Timestamp x) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "updateTimestamp"))
      throw new SQLException("ResultSet bombed");
    it.updateTimestamp(columnIndex, x);
  }

  /**
   * {@inheritDoc}
   * @see java.sql.ResultSet#updateTimestamp(java.lang.String, java.sql.Timestamp)
   */
  public void updateTimestamp(String columnName, Timestamp x)
      throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "updateTimestamp"))
      throw new SQLException("ResultSet bombed");
    it.updateTimestamp(columnName, x);
  }

  /**
   * {@inheritDoc}
   * @see java.sql.ResultSet#wasNull()
   */
  public boolean wasNull() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "wasNull"))
      throw new SQLException("ResultSet bombed");
    return it.wasNull();
  }


  
}
