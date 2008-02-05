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
   * JDBC 4.0
   */
  
  /** 
   * {@inheritDoc}
   * @see java.sql.Wrapper#isWrapperFor(java.lang.Class)
   */
  @Override
  public boolean isWrapperFor(Class<?> iface) throws SQLException {
    if (shouldThrow("isWrapperFor"))
      throw new SQLException("ResultSetMetaData bombed");
    return it.isWrapperFor(iface);
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.Wrapper#unwrap(java.lang.Class)
   */
  @Override
  public <T> T unwrap(Class<T> iface) throws SQLException {
    if (shouldThrow("unwrap"))
      throw new SQLException("ResultSetMetaData bombed");
    return it.unwrap(iface);
  }

}
