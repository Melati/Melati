/*
 * $Source$
 * $Revision$
 *
 * Copyright (C) 2001 William Chesters
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
 *     William Chesters <williamc@paneris.org>
 *     http://paneris.org/~williamc
 *     Obrechtstraat 114, 2517VX Den Haag, The Netherlands
 */

package org.melati.poem.dbms;

import java.sql.SQLException;

import org.melati.poem.Column;
import org.melati.poem.ExecutingSQLPoemException;
import org.melati.poem.Table;

 /**
  * Thrown when an attempt to insert a value which is already present 
  * in a column with a unique index is made.
  *
  * @author WilliamC@paneris.org
  */
public class DuplicateKeySQLPoemException extends ExecutingSQLPoemException {
  private static final long serialVersionUID = 1L;

  /** The Table we are dealing with. */
  public String table;
  /** The Column we are dealing with. */
  public String column;
  /** Whether this was an insert operation or not.*/
  public boolean insert;

  /** Constructor. */
  public DuplicateKeySQLPoemException(Table<?> table, String sql,
                                      boolean insert, SQLException e) {
    super(sql, e);
    if (table != null)
      try {
        this.table = table.getDisplayName();
      }
      catch (Exception f) {
        this.table = table.getName();
      }

    column = null;
    this.insert = insert;
  }

  /** Constructor. */
  public DuplicateKeySQLPoemException(Column<?> column, String sql,
                                      boolean insert, SQLException e) {
    super(sql, e);
    if (column != null) {
      try {
        table = column.getTable().getDisplayName();
      }
      catch (Exception f) {
        table = column.getTable().getName();
      }

      try {
        this.column = column.getDisplayName();
      }
      catch (Exception f) {
        this.column = column.getName();
      }
    }

    this.insert = insert;
  }

  /**
   * @return The table name.
   */
  public final String getTable() {
    return table;
  }

  /**
   * @return The column name.
   */
  public final String getColumn() {
    return column;
  }

  /**
   * @return whether operation was an insert
   */
  public final boolean getWasInsert() {
    return insert;
  }

  /** @return The detail message. */
  public String getMessage() {
    String quant = insert ? "an existing" : "another";
    if (column == null) {
      String desc = table == null ? "record" : table;
      return "Unable to create " + desc + ": one or more fields clashed " +
             "with " + quant + " " + desc;
    }
    else {
      return "The " + table + "'s " + column + " clashes with that of " +
             quant + " " + table;
    }
  }
}
