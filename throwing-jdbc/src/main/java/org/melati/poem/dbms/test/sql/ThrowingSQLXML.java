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
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.sql.SQLException;
import java.sql.SQLXML;

import javax.xml.transform.Result;
import javax.xml.transform.Source;

/**
 * @author timp
 * @since 5 Feb 2008
 *
 */
public class ThrowingSQLXML extends Thrower implements SQLXML {
  
  final static String className = ThrowingSQLXML.class.getName() + ".";
  
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

  SQLXML it = null;
  

  /**
   * Constructor.
   */
  public ThrowingSQLXML(SQLXML sx) {
    it = sx;
  }

  /** 
   * {@inheritDoc}
   * @see java.sql.SQLXML#free()
   */

  public void free() throws SQLException {
    if (shouldThrow("free"))
      throw new SQLException("SQLXML bombed");
    it.free();
  }

  /** 
   * {@inheritDoc}
   * @see java.sql.SQLXML#getBinaryStream()
   */

  public InputStream getBinaryStream() throws SQLException {
    if (shouldThrow("getBinaryStream"))
      throw new SQLException("SQLXML bombed");
    return it.getBinaryStream();
  }

  /** 
   * {@inheritDoc}
   * @see java.sql.SQLXML#getCharacterStream()
   */

  public Reader getCharacterStream() throws SQLException {
    if (shouldThrow("getCharacterStream"))
      throw new SQLException("SQLXML bombed");
    return it.getCharacterStream();
  }

  /** 
   * {@inheritDoc}
   * @see java.sql.SQLXML#getSource(java.lang.Class)
   */

  public <T extends Source> T getSource(Class<T> sourceClass)
          throws SQLException {
    if (shouldThrow("getSource"))
      throw new SQLException("SQLXML bombed");
    return it.getSource(sourceClass);
  }

  /** 
   * {@inheritDoc}
   * @see java.sql.SQLXML#getString()
   */

  public String getString() throws SQLException {
    if (shouldThrow("getString"))
      throw new SQLException("SQLXML bombed");
    return it.getString();
  }

  /** 
   * {@inheritDoc}
   * @see java.sql.SQLXML#setBinaryStream()
   */

  public OutputStream setBinaryStream() throws SQLException {
    if (shouldThrow("setBinaryStream"))
      throw new SQLException("SQLXML bombed");
    return it.setBinaryStream();
  }

  /** 
   * {@inheritDoc}
   * @see java.sql.SQLXML#setCharacterStream()
   */

  public Writer setCharacterStream() throws SQLException {
    if (shouldThrow("setCharacterStream"))
      throw new SQLException("SQLXML bombed");
    return it.setCharacterStream();
  }

  /** 
   * {@inheritDoc}
   * @see java.sql.SQLXML#setResult(java.lang.Class)
   */

  public <T extends Result> T setResult(Class<T> resultClass)
          throws SQLException {
    if (shouldThrow("setResult"))
      throw new SQLException("SQLXML bombed");
    return it.setResult(resultClass);
  }

  /** 
   * {@inheritDoc}
   * @see java.sql.SQLXML#setString(java.lang.String)
   */

  public void setString(String value) throws SQLException {
    if (shouldThrow("setString"))
      throw new SQLException("SQLXML bombed");
    it.setString(value);
  }

}
