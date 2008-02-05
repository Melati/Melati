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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

/**
 * @author timp
 * @since 5 Feb 2008
 *
 */
public class ThrowingArray extends Thrower implements Array {

  final static String className = ThrowingArray.class.getName() + ".";
  
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

  Array it = null;
  

  /**
   * Constructor.
   */
  public ThrowingArray(Array a) {
    it = a;
  }

  /** 
   * {@inheritDoc}
   * @see java.sql.Array#free()
   */
  public void free() throws SQLException {
    if (shouldThrow("free"))
      throw new SQLException("Array bombed");
    it.free();
  }

  /** 
   * {@inheritDoc}
   * @see java.sql.Array#getArray()
   */
  public Object getArray() throws SQLException {
    if (shouldThrow("getArray"))
      throw new SQLException("Array bombed");
    return it.getArray();
  }

  /** 
   * {@inheritDoc}
   * @see java.sql.Array#getArray(java.util.Map)
   */
  public Object getArray(Map<String, Class<?>> map) throws SQLException {
    if (shouldThrow("getArray"))
      throw new SQLException("Array bombed");
    return it.getArray();
  }

  /** 
   * {@inheritDoc}
   * @see java.sql.Array#getArray(long, int)
   */
  public Object getArray(long index, int count) throws SQLException {
    if (shouldThrow("getArray"))
      throw new SQLException("Array bombed");
    return it.getArray();
  }

  /** 
   * {@inheritDoc}
   * @see java.sql.Array#getArray(long, int, java.util.Map)
   */
  public Object getArray(long index, int count, Map<String, Class<?>> map)
          throws SQLException {
    if (shouldThrow("getArray"))
      throw new SQLException("Array bombed");
    return it.getArray();
  }

  /** 
   * {@inheritDoc}
   * @see java.sql.Array#getBaseType()
   */
  public int getBaseType() throws SQLException {
    if (shouldThrow("getBaseType"))
      throw new SQLException("Array bombed");
    return it.getBaseType();
  }

  /** 
   * {@inheritDoc}
   * @see java.sql.Array#getBaseTypeName()
   */
  public String getBaseTypeName() throws SQLException {
    if (shouldThrow("getBaseTypeName"))
      throw new SQLException("Array bombed");
    return it.getBaseTypeName();
  }

  /** 
   * {@inheritDoc}
   * @see java.sql.Array#getResultSet()
   */
  public ResultSet getResultSet() throws SQLException {
    if (shouldThrow("getResultSet"))
      throw new SQLException("Array bombed");
    return new ThrowingResultSet<Object>(it.getResultSet());
  }

  /** 
   * {@inheritDoc}
   * @see java.sql.Array#getResultSet(java.util.Map)
   */
  public ResultSet getResultSet(Map<String, Class<?>> map) throws SQLException {
    if (shouldThrow("getResultSet"))
      throw new SQLException("Array bombed");
    return  new ThrowingResultSet<Object>(it.getResultSet());
  }

  /** 
   * {@inheritDoc}
   * @see java.sql.Array#getResultSet(long, int)
   */
  public ResultSet getResultSet(long index, int count) throws SQLException {
    if (shouldThrow("getResultSet"))
      throw new SQLException("Array bombed");
    return  new ThrowingResultSet<Object>(it.getResultSet());
  }

  /** 
   * {@inheritDoc}
   * @see java.sql.Array#getResultSet(long, int, java.util.Map)
   */
  public ResultSet getResultSet(long index, int count, Map<String, Class<?>> map)
          throws SQLException {
    if (shouldThrow("getResultSet"))
      throw new SQLException("Array bombed");
    return  new ThrowingResultSet<Object>(it.getResultSet());
  }

}
