/*
 * $Source$
 * $Revision$
 *
 * Copyright (C) 2000 William Chesters
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
 *     William Chesters <williamc At paneris.org>
 *     http://paneris.org/~williamc
 *     Obrechtstraat 114, 2517VX Den Haag, The Netherlands
 */

package org.melati.poem;

import java.sql.ResultSet;
import java.sql.PreparedStatement;
import org.melati.poem.dbms.Dbms;

/**
 * A native SQL {@link Field} datatype.
 *
 * @author WilliamC At paneris.org
 *
 */
public interface SQLType<T> {
  
  /**
   * @return The SQL92 ANSI code for this type.
   */
  int sqlTypeCode();
  
  
  /**
   * SQL type definition with nullability.
   * eg:
   * <code>
   * STRING NOT NULL
   * </code>
   * @param dbms the DBMS 
   * @return the DBMS specific SQL snippet
   */
  String sqlDefinition(Dbms dbms);

  /**
   * Used to set a not null value when 
   * creating a non nullable column.
   * @param dbms the dbms the value is suitable for 
   * @return a String suitable for substitution in UPDATE table SET field = ?
   */
  String sqlDefaultValue(Dbms dbms);
  
  /**
   * SQL type definition without nullability.
   * eg:
   * <code>
   * STRING
   * </code>
   * @param dbms the DBMS 
   * @return the DBMS specific SQL snippet
   */
  String sqlTypeDefinition(Dbms dbms);  
  /**
   * Quoting a raw value, if appropriate, for the Dbms.
   * <p>
   * numbers and booleans are not quoted for most dbms'.
   * @param raw sql value
   * @return the raw value with quotes if appropriate for the dbms
   */
  String quotedRaw(Object raw);

  /**
   * Return an object as delivered by the database.
   * 
   * @param rs the Resultset containing the value
   * @param col the column withing the ResultSet to read
   * @return the value as an Object
   */
  Object getRaw(ResultSet rs, int col)
      throws TypeMismatchPoemException, ValidationPoemException,
             ParsingPoemException;

  /**
   * Set a column of a PreparedStatement to the passed in value.
   * 
   * @param ps the Prepared Statement to modify
   * @param col the column within the PreparedStatement
   * @param cooked the value to set
   */
  void setRaw(PreparedStatement ps, int col, Object cooked)
      throws TypeMismatchPoemException;
}
