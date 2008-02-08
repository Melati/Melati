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

import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 * The JDBC3 methods of a {@link ResultSetMetaData}, decorated to throw an {@SQLException} on command.
 * 
 * @author timp
 * @since  5 Feb 2008
 *
 */
public abstract class ThrowingResultSetMetaDataJdbc3 extends Thrower implements ResultSetMetaData {

  ResultSetMetaData it = null;

  public String getCatalogName(int column) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getCatalogName"))
      throw new SQLException("ResultSetMetaData bombed");
    return it.getCatalogName(column);
  }

  public String getColumnClassName(int column) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getColumnClassName"))
      throw new SQLException("ResultSetMetaData bombed");
    return it.getColumnClassName(column);
  }

  public int getColumnCount() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getColumnCount"))
      throw new SQLException("ResultSetMetaData bombed");
    return it.getColumnCount();
  }

  public int getColumnDisplaySize(int column) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getColumnDisplaySize"))
      throw new SQLException("ResultSetMetaData bombed");
    return it.getColumnDisplaySize(column);
  }

  public String getColumnLabel(int column) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getColumnLabel"))
      throw new SQLException("ResultSetMetaData bombed");
    return it.getColumnLabel(column);
  }

  public String getColumnName(int column) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getColumnName"))
      throw new SQLException("ResultSetMetaData bombed");
    return it.getColumnName(column);
  }

  public int getColumnType(int column) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getColumnType"))
      throw new SQLException("ResultSetMetaData bombed");
    return it.getColumnType(column);
  }

  public String getColumnTypeName(int column) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getColumnTypeName"))
      throw new SQLException("ResultSetMetaData bombed");
    return it.getColumnTypeName(column);
 }

  public int getPrecision(int column) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getPrecision"))
      throw new SQLException("ResultSetMetaData bombed");
    return it.getPrecision(column);
  }

  public int getScale(int column) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getScale"))
      throw new SQLException("ResultSetMetaData bombed");
    return it.getScale(column);
  }

  public String getSchemaName(int column) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getSchemaName"))
      throw new SQLException("ResultSetMetaData bombed");
    return it.getSchemaName(column);
  }

  public String getTableName(int column) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getTableName"))
      throw new SQLException("ResultSetMetaData bombed");
    return it.getTableName(column);
  }

  public boolean isAutoIncrement(int column) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "isAutoIncrement"))
      throw new SQLException("ResultSetMetaData bombed");
    return it.isAutoIncrement(column);
  }

  public boolean isCaseSensitive(int column) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "isCaseSensitive"))
      throw new SQLException("ResultSetMetaData bombed");
    return it.isCaseSensitive(column);
  }

  public boolean isCurrency(int column) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "isCurrency"))
      throw new SQLException("ResultSetMetaData bombed");
    return it.isCurrency(column);
  }

  public boolean isDefinitelyWritable(int column) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "isDefinitelyWritable"))
      throw new SQLException("ResultSetMetaData bombed");
    return it.isDefinitelyWritable(column);
  }

  public int isNullable(int column) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "isNullable"))
      throw new SQLException("ResultSetMetaData bombed");
    return it.isNullable(column);
  }

  public boolean isReadOnly(int column) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "isReadOnly"))
      throw new SQLException("ResultSetMetaData bombed");
    return it.isReadOnly(column);
  }

  public boolean isSearchable(int column) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "isSearchable"))
      throw new SQLException("ResultSetMetaData bombed");
    return it.isSearchable(column);
  }

  public boolean isSigned(int column) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "isSigned"))
      throw new SQLException("ResultSetMetaData bombed");
    return it.isSigned(column);
  }

  public boolean isWritable(int column) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "isWritable"))
      throw new SQLException("ResultSetMetaData bombed");
    return it.isWritable(column);
  }
}
