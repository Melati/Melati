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
import java.sql.NClob;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.RowId;
import java.sql.SQLXML;
import java.sql.SQLException;

/**
 * The JDBC4 methods of a {@link Ref}, decorated to throw an SQLException on command.
 * 
 * @author timp
 * @since  5 Feb 2008
 *
 */
public abstract class ThrowingResultSetJdbc4 
    extends ThrowingResultSetJdbc3 
    implements ResultSet {
  
  /**
   *  JDBC 4.0
   */
  

  /** 
   * {@inheritDoc}
   * @see java.sql.ResultSet#getHoldability()
   */

  public int getHoldability() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getHoldability"))
      throw new SQLException("ResultSet bombed");
    return it.getHoldability();
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.ResultSet#getNCharacterStream(int)
   */

  public Reader getNCharacterStream(int columnIndex) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getNCharacterStream"))
      throw new SQLException("ResultSet bombed");
    return it.getNCharacterStream(columnIndex);
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.ResultSet#getNCharacterStream(java.lang.String)
   */

  public Reader getNCharacterStream(String columnLabel) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getNCharacterStream"))
      throw new SQLException("ResultSet bombed");
    return it.getNCharacterStream(columnLabel);
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.ResultSet#getNClob(int)
   */

  public NClob getNClob(int columnIndex) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getNClob"))
      throw new SQLException("ResultSet bombed");
    return new ThrowingNClob(it.getNClob(columnIndex));
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.ResultSet#getNClob(java.lang.String)
   */

  public NClob getNClob(String columnLabel) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getNClob"))
      throw new SQLException("ResultSet bombed");
    return new ThrowingNClob(it.getNClob(columnLabel));
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.ResultSet#getNString(int)
   */

  public String getNString(int columnIndex) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getNString"))
      throw new SQLException("ResultSet bombed");
    return it.getNString(columnIndex);
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.ResultSet#getNString(java.lang.String)
   */

  public String getNString(String columnLabel) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getNString"))
      throw new SQLException("ResultSet bombed");
    return it.getNString(columnLabel);
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.ResultSet#getRowId(int)
   */

  public RowId getRowId(int columnIndex) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getRowId"))
      throw new SQLException("ResultSet bombed");
    return new ThrowingRowId(it.getRowId(columnIndex));
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.ResultSet#getRowId(java.lang.String)
   */

  public RowId getRowId(String columnLabel) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getRowId"))
      throw new SQLException("ResultSet bombed");
    return new ThrowingRowId(it.getRowId(columnLabel));
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.ResultSet#getSQLXML(int)
   */

  public SQLXML getSQLXML(int columnIndex) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getSQLXML"))
      throw new SQLException("ResultSet bombed");
    return new ThrowingSQLXML(it.getSQLXML(columnIndex));
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.ResultSet#getSQLXML(java.lang.String)
   */

  public SQLXML getSQLXML(String columnLabel) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getSQLXML"))
      throw new SQLException("ResultSet bombed");
    return new ThrowingSQLXML(it.getSQLXML(columnLabel));
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.ResultSet#isClosed()
   */

  public boolean isClosed() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "isClosed"))
      throw new SQLException("ResultSet bombed");
    return it.isClosed();
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.ResultSet#updateAsciiStream(int, java.io.InputStream, long)
   */

  public void updateAsciiStream(int columnIndex, InputStream x, long length)
          throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "updateAsciiStream"))
      throw new SQLException("ResultSet bombed");
    it.updateAsciiStream(columnIndex, x, length);
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.ResultSet#updateAsciiStream(int, java.io.InputStream)
   */

  public void updateAsciiStream(int columnIndex, InputStream x)
          throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "updateAsciiStream"))
      throw new SQLException("ResultSet bombed");
    it.updateAsciiStream(columnIndex, x);
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.ResultSet#updateAsciiStream(java.lang.String, java.io.InputStream, long)
   */

  public void updateAsciiStream(String columnLabel, InputStream x, long length)
          throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "updateAsciiStream"))
      throw new SQLException("ResultSet bombed");
    it.updateAsciiStream(columnLabel, x, length);
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.ResultSet#updateAsciiStream(java.lang.String, java.io.InputStream)
   */

  public void updateAsciiStream(String columnLabel, InputStream x)
          throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "updateAsciiStream"))
      throw new SQLException("ResultSet bombed");
    it.updateAsciiStream(columnLabel, x);
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.ResultSet#updateBinaryStream(int, java.io.InputStream, long)
   */

  public void updateBinaryStream(int columnIndex, InputStream x, long length)
          throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "updateBinaryStream"))
      throw new SQLException("ResultSet bombed");
    it.updateBinaryStream(columnIndex, x, length);
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.ResultSet#updateBinaryStream(int, java.io.InputStream)
   */

  public void updateBinaryStream(int columnIndex, InputStream x)
          throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "updateBinaryStream"))
      throw new SQLException("ResultSet bombed");
    it.updateBinaryStream(columnIndex, x);
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.ResultSet#updateBinaryStream(java.lang.String, java.io.InputStream, long)
   */

  public void updateBinaryStream(String columnLabel, InputStream x, long length)
          throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "updateBinaryStream"))
      throw new SQLException("ResultSet bombed");
    it.updateBinaryStream(columnLabel, x, length);
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.ResultSet#updateBinaryStream(java.lang.String, java.io.InputStream)
   */

  public void updateBinaryStream(String columnLabel, InputStream x)
          throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "updateBinaryStream"))
      throw new SQLException("ResultSet bombed");
    it.updateBinaryStream(columnLabel, x);
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.ResultSet#updateBlob(int, java.io.InputStream, long)
   */

  public void updateBlob(int columnIndex, InputStream inputStream, long length)
          throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "updateBlob"))
      throw new SQLException("ResultSet bombed");
    it.updateBlob(columnIndex, inputStream, length);
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.ResultSet#updateBlob(int, java.io.InputStream)
   */

  public void updateBlob(int columnIndex, InputStream inputStream)
          throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "updateBlob"))
      throw new SQLException("ResultSet bombed");
    it.updateBlob(columnIndex, inputStream);
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.ResultSet#updateBlob(java.lang.String, java.io.InputStream, long)
   */

  public void updateBlob(String columnLabel, InputStream inputStream,
          long length) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "updateBlob"))
      throw new SQLException("ResultSet bombed");
    it.updateBlob(columnLabel, inputStream);
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.ResultSet#updateBlob(java.lang.String, java.io.InputStream)
   */

  public void updateBlob(String columnLabel, InputStream inputStream)
          throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "updateBlob"))
      throw new SQLException("ResultSet bombed");
    it.updateBlob(columnLabel, inputStream);
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.ResultSet#updateCharacterStream(int, java.io.Reader, long)
   */

  public void updateCharacterStream(int columnIndex, Reader x, long length)
          throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "updateCharacterStream"))
      throw new SQLException("ResultSet bombed");
    it.updateCharacterStream(columnIndex, x, length);
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.ResultSet#updateCharacterStream(int, java.io.Reader)
   */

  public void updateCharacterStream(int columnIndex, Reader x)
          throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "updateCharacterStream"))
      throw new SQLException("ResultSet bombed");
    it.updateCharacterStream(columnIndex, x);
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.ResultSet#updateCharacterStream(java.lang.String, java.io.Reader, long)
   */

  public void updateCharacterStream(String columnLabel, Reader reader,
          long length) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "updateCharacterStream"))
      throw new SQLException("ResultSet bombed");
    it.updateCharacterStream(columnLabel, reader);
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.ResultSet#updateCharacterStream(java.lang.String, java.io.Reader)
   */

  public void updateCharacterStream(String columnLabel, Reader reader)
          throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "updateCharacterStream"))
      throw new SQLException("ResultSet bombed");
    it.updateCharacterStream(columnLabel, reader);
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.ResultSet#updateClob(int, java.io.Reader, long)
   */

  public void updateClob(int columnIndex, Reader reader, long length)
          throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "updateClob"))
      throw new SQLException("ResultSet bombed");
    it.updateClob(columnIndex, reader, length);
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.ResultSet#updateClob(int, java.io.Reader)
   */

  public void updateClob(int columnIndex, Reader reader) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "updateClob"))
      throw new SQLException("ResultSet bombed");
    it.updateClob(columnIndex, reader);
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.ResultSet#updateClob(java.lang.String, java.io.Reader, long)
   */

  public void updateClob(String columnLabel, Reader reader, long length)
          throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "updateClob"))
      throw new SQLException("ResultSet bombed");
    it.updateClob(columnLabel, reader, length);
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.ResultSet#updateClob(java.lang.String, java.io.Reader)
   */

  public void updateClob(String columnLabel, Reader reader) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "updateClob"))
      throw new SQLException("ResultSet bombed");
    it.updateClob(columnLabel, reader);
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.ResultSet#updateNCharacterStream(int, java.io.Reader, long)
   */

  public void updateNCharacterStream(int columnIndex, Reader x, long length)
          throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "updateNCharacterStream"))
      throw new SQLException("ResultSet bombed");
    it.updateNCharacterStream(columnIndex, x, length);
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.ResultSet#updateNCharacterStream(int, java.io.Reader)
   */

  public void updateNCharacterStream(int columnIndex, Reader x)
          throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "updateNCharacterStream"))
      throw new SQLException("ResultSet bombed");
    it.updateNCharacterStream(columnIndex, x);
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.ResultSet#updateNCharacterStream(java.lang.String, java.io.Reader, long)
   */

  public void updateNCharacterStream(String columnLabel, Reader reader,
          long length) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "updateNCharacterStream"))
      throw new SQLException("ResultSet bombed");
    it.updateNCharacterStream(columnLabel, reader);
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.ResultSet#updateNCharacterStream(java.lang.String, java.io.Reader)
   */

  public void updateNCharacterStream(String columnLabel, Reader reader)
          throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "updateNCharacterStream"))
      throw new SQLException("ResultSet bombed");
    it.updateNCharacterStream(columnLabel, reader);
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.ResultSet#updateNClob(int, java.sql.NClob)
   */

  public void updateNClob(int columnIndex, NClob clob) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "updateNClob"))
      throw new SQLException("ResultSet bombed");
    it.updateNClob(columnIndex, clob);
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.ResultSet#updateNClob(int, java.io.Reader, long)
   */

  public void updateNClob(int columnIndex, Reader reader, long length)
          throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "updateNClob"))
      throw new SQLException("ResultSet bombed");
    it.updateNClob(columnIndex, reader, length);
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.ResultSet#updateNClob(int, java.io.Reader)
   */

  public void updateNClob(int columnIndex, Reader reader) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "updateNClob"))
      throw new SQLException("ResultSet bombed");
    it.updateNClob(columnIndex, reader);
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.ResultSet#updateNClob(java.lang.String, java.sql.NClob)
   */

  public void updateNClob(String columnLabel, NClob clob) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "updateNClob"))
      throw new SQLException("ResultSet bombed");
    it.updateNClob(columnLabel, clob);
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.ResultSet#updateNClob(java.lang.String, java.io.Reader, long)
   */

  public void updateNClob(String columnLabel, Reader reader, long length)
          throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "updateNClob"))
      throw new SQLException("ResultSet bombed");
    it.updateNClob(columnLabel, reader, length);
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.ResultSet#updateNClob(java.lang.String, java.io.Reader)
   */

  public void updateNClob(String columnLabel, Reader reader)
          throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "updateNClob"))
      throw new SQLException("ResultSet bombed");
    it.updateNClob(columnLabel, reader);
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.ResultSet#updateNString(int, java.lang.String)
   */

  public void updateNString(int columnIndex, String string) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "updateNString"))
      throw new SQLException("ResultSet bombed");
    it.updateNString(columnIndex, string);
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.ResultSet#updateNString(java.lang.String, java.lang.String)
   */

  public void updateNString(String columnLabel, String string)
          throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "updateNString"))
      throw new SQLException("ResultSet bombed");
    it.updateNString(columnLabel, string);
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.ResultSet#updateRowId(int, java.sql.RowId)
   */

  public void updateRowId(int columnIndex, RowId x) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "updateRowId"))
      throw new SQLException("ResultSet bombed");
    it.updateRowId(columnIndex, x);
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.ResultSet#updateRowId(java.lang.String, java.sql.RowId)
   */

  public void updateRowId(String columnLabel, RowId x) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "updateRowId"))
      throw new SQLException("ResultSet bombed");
    it.updateRowId(columnLabel, x);
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.ResultSet#updateSQLXML(int, java.sql.SQLXML)
   */

  public void updateSQLXML(int columnIndex, SQLXML xmlObject)
          throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "updateSQLXML"))
      throw new SQLException("ResultSet bombed");
    it.updateSQLXML(columnIndex, xmlObject);
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.ResultSet#updateSQLXML(java.lang.String, java.sql.SQLXML)
   */

  public void updateSQLXML(String columnLabel, SQLXML xmlObject)
          throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "updateSQLXML"))
      throw new SQLException("ResultSet bombed");
    it.updateSQLXML(columnLabel, xmlObject);
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.Wrapper#isWrapperFor(java.lang.Class)
   */

  public boolean isWrapperFor(Class<?> iface) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "isWrapperFor"))
      throw new SQLException("ResultSet bombed");
    return it.isWrapperFor(iface);
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.Wrapper#unwrap(java.lang.Class)
   */

  public <T1> T1 unwrap(Class<T1> iface) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "unwrap"))
      throw new SQLException("ResultSet bombed");
    return it.unwrap(iface);
  }


}
