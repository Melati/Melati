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
import java.sql.Blob;
import java.sql.Clob;
import java.sql.NClob;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLXML;

/**
 * @author timp
 * @since 5 Feb 2008
 * From src/jdbc4
 */
public abstract class ThrowingCallableStatementJdbc4 
    extends ThrowingCallableStatementJdbc3 {

  
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
      throw new SQLException("CallableStatement bombed");
    it.setAsciiStream(parameterIndex, x, length);
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.PreparedStatement#setAsciiStream(int, java.io.InputStream)
   */

  public void setAsciiStream(int parameterIndex, InputStream x)
          throws SQLException {
    if (shouldThrow("setAsciiStream"))
      throw new SQLException("CallableStatement bombed");
    it.setAsciiStream(parameterIndex, x);
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.PreparedStatement#setBinaryStream(int, java.io.InputStream, long)
   */

  public void setBinaryStream(int parameterIndex, InputStream x, long length)
          throws SQLException {
    if (shouldThrow("setBinaryStream"))
      throw new SQLException("CallableStatement bombed");
    it.setBinaryStream(parameterIndex, x, length);
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.PreparedStatement#setBinaryStream(int, java.io.InputStream)
   */

  public void setBinaryStream(int parameterIndex, InputStream x)
          throws SQLException {
    if (shouldThrow("setBinaryStream"))
      throw new SQLException("CallableStatement bombed");
    it.setBinaryStream(parameterIndex, x);
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.PreparedStatement#setBlob(int, java.io.InputStream, long)
   */

  public void setBlob(int parameterIndex, InputStream inputStream, long length)
          throws SQLException {
    if (shouldThrow("setBlob"))
      throw new SQLException("CallableStatement bombed");
    it.setBlob(parameterIndex, inputStream, length);
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.PreparedStatement#setBlob(int, java.io.InputStream)
   */

  public void setBlob(int parameterIndex, InputStream inputStream)
          throws SQLException {
    if (shouldThrow("setBlob"))
      throw new SQLException("CallableStatement bombed");
    it.setBlob(parameterIndex, inputStream);
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.PreparedStatement#setCharacterStream(int, java.io.Reader, long)
   */

  public void setCharacterStream(int parameterIndex, Reader reader, long length)
          throws SQLException {
    if (shouldThrow("setCharacterStream"))
      throw new SQLException("CallableStatement bombed");
    it.setCharacterStream(parameterIndex, reader, length);
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.PreparedStatement#setCharacterStream(int, java.io.Reader)
   */

  public void setCharacterStream(int parameterIndex, Reader reader)
          throws SQLException {
    if (shouldThrow("setCharacterStream"))
      throw new SQLException("CallableStatement bombed");
    it.setCharacterStream(parameterIndex, reader);
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.PreparedStatement#setClob(int, java.io.Reader, long)
   */

  public void setClob(int parameterIndex, Reader reader, long length)
          throws SQLException {
    if (shouldThrow("setClob"))
      throw new SQLException("CallableStatement bombed");
    it.setClob(parameterIndex, reader, length);
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.PreparedStatement#setClob(int, java.io.Reader)
   */

  public void setClob(int parameterIndex, Reader reader) throws SQLException {
    if (shouldThrow("setClob"))
      throw new SQLException("CallableStatement bombed");
    it.setClob(parameterIndex, reader);
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.PreparedStatement#setNCharacterStream(int, java.io.Reader, long)
   */

  public void setNCharacterStream(int parameterIndex, Reader value, long length)
          throws SQLException {
    if (shouldThrow("setNCharacterStream"))
      throw new SQLException("CallableStatement bombed");
    it.setNCharacterStream(parameterIndex, value, length);
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.PreparedStatement#setNCharacterStream(int, java.io.Reader)
   */

  public void setNCharacterStream(int parameterIndex, Reader value)
          throws SQLException {
    if (shouldThrow("setNCharacterStream"))
      throw new SQLException("CallableStatement bombed");
    it.setNCharacterStream(parameterIndex, value);
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.PreparedStatement#setNClob(int, java.sql.NClob)
   */

  public void setNClob(int parameterIndex, NClob value) throws SQLException {
    if (shouldThrow("setNClob"))
      throw new SQLException("CallableStatement bombed");
    it.setNClob(parameterIndex, value);
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.PreparedStatement#setNClob(int, java.io.Reader, long)
   */

  public void setNClob(int parameterIndex, Reader reader, long length)
          throws SQLException {
    if (shouldThrow("setNClob"))
      throw new SQLException("CallableStatement bombed");
    it.setNClob(parameterIndex, reader, length);
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.PreparedStatement#setNClob(int, java.io.Reader)
   */

  public void setNClob(int parameterIndex, Reader reader) throws SQLException {
    if (shouldThrow("setNClob"))
      throw new SQLException("CallableStatement bombed");
    it.setNClob(parameterIndex, reader);
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.PreparedStatement#setNString(int, java.lang.String)
   */

  public void setNString(int parameterIndex, String value) throws SQLException {
    if (shouldThrow("setNString"))
      throw new SQLException("CallableStatement bombed");
    it.setNString(parameterIndex, value);
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.PreparedStatement#setRowId(int, java.sql.RowId)
   */

  public void setRowId(int parameterIndex, RowId x) throws SQLException {
    if (shouldThrow("setRowId"))
      throw new SQLException("CallableStatement bombed");
    it.setRowId(parameterIndex, x);
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.PreparedStatement#setSQLXML(int, java.sql.SQLXML)
   */

  public void setSQLXML(int parameterIndex, SQLXML xmlObject)
          throws SQLException {
    if (shouldThrow("setSQLXML"))
      throw new SQLException("CallableStatement bombed");
    it.setSQLXML(parameterIndex, xmlObject);
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.Statement#isClosed()
   */

  public boolean isClosed() throws SQLException {
    if (shouldThrow("isClosed"))
      throw new SQLException("CallableStatement bombed");
    return it.isClosed();
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.Statement#isPoolable()
   */

  public boolean isPoolable() throws SQLException {
    if (shouldThrow("isPoolable"))
      throw new SQLException("CallableStatement bombed");
    return it.isPoolable();
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.Statement#setPoolable(boolean)
   */

  public void setPoolable(boolean poolable) throws SQLException {
    if (shouldThrow("setPoolable"))
      throw new SQLException("CallableStatement bombed");
    it.setPoolable(poolable);
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.Wrapper#isWrapperFor(java.lang.Class)
   */

  public boolean isWrapperFor(Class<?> iface) throws SQLException {
    if (shouldThrow("isWrapperFor"))
      throw new SQLException("CallableStatement bombed");
    return it.isWrapperFor(iface);
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.Wrapper#unwrap(java.lang.Class)
   */

  public <T> T unwrap(Class<T> iface) throws SQLException {
    if (shouldThrow("unwrap"))
      throw new SQLException("CallableStatement bombed");
    return it.unwrap(iface);
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.CallableStatement#getCharacterStream(int)
   */

  public Reader getCharacterStream(int parameterIndex) throws SQLException {
    if (shouldThrow("getCharacterStream"))
      throw new SQLException("CallableStatement bombed");
    return it.getCharacterStream(parameterIndex);
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.CallableStatement#getCharacterStream(java.lang.String)
   */

  public Reader getCharacterStream(String parameterName) throws SQLException {
    if (shouldThrow("getCharacterStream"))
      throw new SQLException("CallableStatement bombed");
    return it.getCharacterStream(parameterName);
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.CallableStatement#getNCharacterStream(int)
   */

  public Reader getNCharacterStream(int parameterIndex) throws SQLException {
    if (shouldThrow("getNCharacterStream"))
      throw new SQLException("CallableStatement bombed");
    return it.getNCharacterStream(parameterIndex);
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.CallableStatement#getNCharacterStream(java.lang.String)
   */

  public Reader getNCharacterStream(String parameterName) throws SQLException {
    if (shouldThrow("getNCharacterStream"))
      throw new SQLException("CallableStatement bombed");
    return it.getNCharacterStream(parameterName);
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.CallableStatement#getNClob(int)
   */

  public NClob getNClob(int parameterIndex) throws SQLException {
    if (shouldThrow("getNClob"))
      throw new SQLException("CallableStatement bombed");
    return new ThrowingNClob(it.getNClob(parameterIndex));
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.CallableStatement#getNClob(java.lang.String)
   */

  public NClob getNClob(String parameterName) throws SQLException {
    if (shouldThrow("getNClob"))
      throw new SQLException("CallableStatement bombed");
    return new ThrowingNClob(it.getNClob(parameterName));
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.CallableStatement#getNString(int)
   */

  public String getNString(int parameterIndex) throws SQLException {
    if (shouldThrow("getNString"))
      throw new SQLException("CallableStatement bombed");
    return it.getNString(parameterIndex);
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.CallableStatement#getNString(java.lang.String)
   */

  public String getNString(String parameterName) throws SQLException {
    if (shouldThrow("getNString"))
      throw new SQLException("CallableStatement bombed");
    return it.getNString(parameterName);
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.CallableStatement#getRowId(int)
   */

  public RowId getRowId(int parameterIndex) throws SQLException {
    if (shouldThrow("getRowId"))
      throw new SQLException("CallableStatement bombed");
    return new ThrowingRowId(it.getRowId(parameterIndex));
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.CallableStatement#getRowId(java.lang.String)
   */

  public RowId getRowId(String parameterName) throws SQLException {
    if (shouldThrow("getRowId"))
      throw new SQLException("CallableStatement bombed");
    return new ThrowingRowId(it.getRowId(parameterName));
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.CallableStatement#getSQLXML(int)
   */

  public SQLXML getSQLXML(int parameterIndex) throws SQLException {
    if (shouldThrow("getSQLXML"))
      throw new SQLException("CallableStatement bombed");
    return new ThrowingSQLXML(it.getSQLXML(parameterIndex));
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.CallableStatement#getSQLXML(java.lang.String)
   */

  public SQLXML getSQLXML(String parameterName) throws SQLException {
    if (shouldThrow("getSQLXML"))
      throw new SQLException("CallableStatement bombed");
    return  new ThrowingSQLXML(it.getSQLXML(parameterName));
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.CallableStatement#setAsciiStream(java.lang.String, java.io.InputStream, long)
   */

  public void setAsciiStream(String parameterName, InputStream x, long length)
          throws SQLException {
    if (shouldThrow("setAsciiStream"))
      throw new SQLException("CallableStatement bombed");
    it.setAsciiStream(parameterName, x, length);
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.CallableStatement#setAsciiStream(java.lang.String, java.io.InputStream)
   */

  public void setAsciiStream(String parameterName, InputStream x)
          throws SQLException {
    if (shouldThrow("setAsciiStream"))
      throw new SQLException("CallableStatement bombed");
    it.setAsciiStream(parameterName, x);
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.CallableStatement#setBinaryStream(java.lang.String, java.io.InputStream, long)
   */

  public void setBinaryStream(String parameterName, InputStream x, long length)
          throws SQLException {
    if (shouldThrow("setBinaryStream"))
      throw new SQLException("CallableStatement bombed");
    it.setBinaryStream(parameterName, x, length);
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.CallableStatement#setBinaryStream(java.lang.String, java.io.InputStream)
   */

  public void setBinaryStream(String parameterName, InputStream x)
          throws SQLException {
    if (shouldThrow("setBinaryStream"))
      throw new SQLException("CallableStatement bombed");
    it.setBinaryStream(parameterName, x);
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.CallableStatement#setBlob(java.lang.String, java.sql.Blob)
   */

  public void setBlob(String parameterName, Blob x) throws SQLException {
    if (shouldThrow("setBlob"))
      throw new SQLException("CallableStatement bombed");
    it.setBlob(parameterName, x);
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.CallableStatement#setBlob(java.lang.String, java.io.InputStream, long)
   */

  public void setBlob(String parameterName, InputStream inputStream, long length)
          throws SQLException {
    if (shouldThrow("setBlob"))
      throw new SQLException("CallableStatement bombed");
    it.setBlob(parameterName, inputStream, length);
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.CallableStatement#setBlob(java.lang.String, java.io.InputStream)
   */

  public void setBlob(String parameterName, InputStream inputStream)
          throws SQLException {
    if (shouldThrow("setBlob"))
      throw new SQLException("CallableStatement bombed");
    it.setBlob(parameterName, inputStream);
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.CallableStatement#setCharacterStream(java.lang.String, java.io.Reader, long)
   */

  public void setCharacterStream(String parameterName, Reader reader,
          long length) throws SQLException {
    if (shouldThrow("setCharacterStream"))
      throw new SQLException("CallableStatement bombed");
    it.setCharacterStream(parameterName, reader);
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.CallableStatement#setCharacterStream(java.lang.String, java.io.Reader)
   */

  public void setCharacterStream(String parameterName, Reader reader)
          throws SQLException {
    if (shouldThrow("setCharacterStream"))
      throw new SQLException("CallableStatement bombed");
    it.setCharacterStream(parameterName, reader);
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.CallableStatement#setClob(java.lang.String, java.sql.Clob)
   */

  public void setClob(String parameterName, Clob x) throws SQLException {
    if (shouldThrow("setClob"))
      throw new SQLException("CallableStatement bombed");
    it.setClob(parameterName, x);
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.CallableStatement#setClob(java.lang.String, java.io.Reader, long)
   */

  public void setClob(String parameterName, Reader reader, long length)
          throws SQLException {
    if (shouldThrow("setClob"))
      throw new SQLException("CallableStatement bombed");
    it.setClob(parameterName, reader, length);
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.CallableStatement#setClob(java.lang.String, java.io.Reader)
   */

  public void setClob(String parameterName, Reader reader) throws SQLException {
    if (shouldThrow("setClob"))
      throw new SQLException("CallableStatement bombed");
    it.setClob(parameterName, reader);
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.CallableStatement#setNCharacterStream(java.lang.String, java.io.Reader, long)
   */

  public void setNCharacterStream(String parameterName, Reader value,
          long length) throws SQLException {
    if (shouldThrow("setNCharacterStream"))
      throw new SQLException("CallableStatement bombed");
    it.setNCharacterStream(parameterName, value);
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.CallableStatement#setNCharacterStream(java.lang.String, java.io.Reader)
   */

  public void setNCharacterStream(String parameterName, Reader value)
          throws SQLException {
    if (shouldThrow("setNCharacterStream"))
      throw new SQLException("CallableStatement bombed");
    it.setNCharacterStream(parameterName, value);
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.CallableStatement#setNClob(java.lang.String, java.sql.NClob)
   */

  public void setNClob(String parameterName, NClob value) throws SQLException {
    if (shouldThrow("setNClob"))
      throw new SQLException("CallableStatement bombed");
    it.setNClob(parameterName, value);
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.CallableStatement#setNClob(java.lang.String, java.io.Reader, long)
   */

  public void setNClob(String parameterName, Reader reader, long length)
          throws SQLException {
    if (shouldThrow("setNClob"))
      throw new SQLException("CallableStatement bombed");
    it.setNClob(parameterName, reader, length);
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.CallableStatement#setNClob(java.lang.String, java.io.Reader)
   */

  public void setNClob(String parameterName, Reader reader) throws SQLException {
    if (shouldThrow("setNClob"))
      throw new SQLException("CallableStatement bombed");
    it.setNClob(parameterName, reader);
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.CallableStatement#setNString(java.lang.String, java.lang.String)
   */

  public void setNString(String parameterName, String value)
          throws SQLException {
    if (shouldThrow("setNString"))
      throw new SQLException("CallableStatement bombed");
    it.setNString(parameterName, value);
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.CallableStatement#setRowId(java.lang.String, java.sql.RowId)
   */

  public void setRowId(String parameterName, RowId x) throws SQLException {
    if (shouldThrow("setRowId"))
      throw new SQLException("CallableStatement bombed");
    it.setRowId(parameterName, x);
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.CallableStatement#setSQLXML(java.lang.String, java.sql.SQLXML)
   */

  public void setSQLXML(String parameterName, SQLXML xmlObject)
          throws SQLException {
    if (shouldThrow("setSQLXML"))
      throw new SQLException("CallableStatement bombed");
    it.setSQLXML(parameterName, xmlObject);
  }
  

}
