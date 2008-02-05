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

import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.NClob;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLXML;
import java.sql.Struct;
import java.util.Properties;

/**
 * @author timp
 * @since  5 Feb 2008
 *
 */
public abstract class ThrowingConnectionJdbc4 
    extends ThrowingConnectionJdbc3 
    implements Connection {
  /**
   *  JDBC 4.0
   */
  
  
  /** 
   * {@inheritDoc}
   * @see java.sql.Connection#createArrayOf(java.lang.String, java.lang.Object[])
   */

  public Array createArrayOf(String typeName, Object[] elements)
          throws SQLException {
    if (shouldThrow("createArrayOf"))
      throw new SQLException("Connection bombed");
    return new ThrowingArray(it.createArrayOf(typeName, elements));
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.Connection#createBlob()
   */

  public Blob createBlob() throws SQLException {
    if (shouldThrow("createBlob"))
      throw new SQLException("Connection bombed");
    return new ThrowingBlob(it.createBlob());
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.Connection#createClob()
   */

  public Clob createClob() throws SQLException {
    if (shouldThrow("createClob"))
      throw new SQLException("Connection bombed");
    return new ThrowingClob(it.createClob());
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.Connection#createNClob()
   */

  public NClob createNClob() throws SQLException {
    if (shouldThrow("createNClob"))
      throw new SQLException("Connection bombed");
    return  new ThrowingNClob(it.createNClob());
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.Connection#createSQLXML()
   */

  public SQLXML createSQLXML() throws SQLException {
    if (shouldThrow("createSQLXML"))
      throw new SQLException("Connection bombed");
    return new ThrowingSQLXML(it.createSQLXML());
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.Connection#createStruct(java.lang.String, java.lang.Object[])
   */

  public Struct createStruct(String typeName, Object[] attributes)
          throws SQLException {
    if (shouldThrow("createStruct"))
      throw new SQLException("Connection bombed");
    return new ThrowingStruct(it.createStruct(typeName, attributes));
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.Connection#getClientInfo()
   */

  public Properties getClientInfo() throws SQLException {
    if (shouldThrow("getClientInfo"))
      throw new SQLException("Connection bombed");
    return it.getClientInfo();
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.Connection#getClientInfo(java.lang.String)
   */

  public String getClientInfo(String name) throws SQLException {
    if (shouldThrow("getClientInfo"))
      throw new SQLException("Connection bombed");
    return it.getClientInfo(name);
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.Connection#isValid(int)
   */

  public boolean isValid(int timeout) throws SQLException {
    if (shouldThrow("isValid"))
      throw new SQLException("Connection bombed");
    return it.isValid(timeout);
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.Connection#setClientInfo(java.util.Properties)
   */

  public void setClientInfo(Properties properties)
          throws SQLClientInfoException {
    if (shouldThrow("setClientInfo"))
      throw new SQLClientInfoException();
    it.setClientInfo(properties);
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.Connection#setClientInfo(java.lang.String, java.lang.String)
   */

  public void setClientInfo(String name, String value)
          throws SQLClientInfoException {
    if (shouldThrow("setClientInfo"))
      throw new SQLClientInfoException();
    it.setClientInfo(name, value);
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.Wrapper#isWrapperFor(java.lang.Class)
   */

  public boolean isWrapperFor(Class<?> iface) throws SQLException {
    if (shouldThrow("isWrapperFor"))
      throw new SQLException("Connection bombed");
    return it.isWrapperFor(iface);
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.Wrapper#unwrap(java.lang.Class)
   */

  public <T> T unwrap(Class<T> iface) throws SQLException {
    if (shouldThrow("unwrap"))
      throw new SQLException("Connection bombed");
    return it.unwrap(iface);
  }
  


}
