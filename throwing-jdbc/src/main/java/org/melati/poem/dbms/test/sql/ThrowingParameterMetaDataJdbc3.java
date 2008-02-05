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

import java.sql.ParameterMetaData;
import java.sql.SQLException;

/**
 * @author timp
 * @since  5 Feb 2008
 *
 */
public abstract class ThrowingParameterMetaDataJdbc3 extends Thrower
    implements ParameterMetaData {

  ParameterMetaData it = null;


  public String getParameterClassName(int param) throws SQLException {
    if (shouldThrow("getParameterClassName"))
      throw new SQLException("ParameterMetaData bombed");
    return it.getParameterClassName(param);
  }

  public int getParameterCount() throws SQLException {
    if (shouldThrow("getParameterCount"))
      throw new SQLException("ParameterMetaData bombed");
    return it.getParameterCount();
  }

  public int getParameterMode(int param) throws SQLException {
    if (shouldThrow("getParameterMode"))
      throw new SQLException("ParameterMetaData bombed");
    return it.getParameterMode(param);
  }

  public int getParameterType(int param) throws SQLException {
    if (shouldThrow("getParameterType"))
      throw new SQLException("ParameterMetaData bombed");
    return it.getParameterType(param);
  }

  public String getParameterTypeName(int param) throws SQLException {
    if (shouldThrow("getParameterTypeName"))
      throw new SQLException("ParameterMetaData bombed");
    return it.getParameterTypeName(param);
  }

  public int getPrecision(int param) throws SQLException {
    if (shouldThrow("getPrecision"))
      throw new SQLException("ParameterMetaData bombed");
    return it.getPrecision(param);
  }

  public int getScale(int param) throws SQLException {
    if (shouldThrow("getScale"))
      throw new SQLException("ParameterMetaData bombed");
    return it.getScale(param);
  }

  public int isNullable(int param) throws SQLException {
    if (shouldThrow("isNullable"))
      throw new SQLException("ParameterMetaData bombed");
    return it.isNullable(param);
  }

  public boolean isSigned(int param) throws SQLException {
    if (shouldThrow("isSigned"))
      throw new SQLException("ParameterMetaData bombed");
    return it.isSigned(param);
  }
    
}
