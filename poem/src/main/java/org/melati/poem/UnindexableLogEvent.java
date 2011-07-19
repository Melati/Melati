/*
 * $Source$
 * $Revision$
 *
 * Copyright (C) 2004 Tim Pizey
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

package org.melati.poem;


/**
 * A warning about an unindexable indexed Column.
 * 
 * MSSQL has an unindexable column type: text, but melati does not use it. 
 */
public class UnindexableLogEvent extends PoemLogEvent {

  /** The Column we are dealing with. */ 
  public Column<?> column;

  /**
   * Constructor.
   * @param column the column which cannot be indexed
   */
  public UnindexableLogEvent(Column<?> column) {
    this.column = column;
  }

  /** @return The detail message. */
  public String toString() {
    return "Column " + column.getName() + " of table " + 
           column.getTable().getName() + 
           " is specified as Indexed, " + column.getType() + 
           " which is not implemented in dbms " + 
           column.dbms() + ".";
  }
}
