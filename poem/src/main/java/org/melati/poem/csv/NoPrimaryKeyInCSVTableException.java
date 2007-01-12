/*
 * $Source$
 * $Revision$
 *
 * Part of Melati (http://melati.org), a framework for the rapid
 * development of clean, maintainable web applications.
 *
 *  Copyright (C) 2001 Myles Chippendale
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
 *     Myles Chippendale <mylesc At paneris.org>
 *
 *
 * ------
 *  Note
 * ------
 *
 * I will assign copyright to PanEris (http://paneris.org) as soon as
 * we have sorted out what sort of legal existence we need to have for
 * that to make sense. 
 * In the meantime, if you want to use Melati on non-GPL terms,
 * contact me!
 */

package org.melati.poem.csv;

/**
 * Thrown when no primary key is set.
 */
public class NoPrimaryKeyInCSVTableException extends RuntimeException {

  private static final long serialVersionUID = 1L;
  String tableName = null;
  String cvsValue = null;

  /**
   * @param tableName
   *          name of table missing primary key
   * @param cvsValue
   *          value of field
   */
  public NoPrimaryKeyInCSVTableException(String tableName, String cvsValue) {
    this.tableName = tableName;
    this.cvsValue = cvsValue;
  }

  /**
   * {@inheritDoc}
   * @see java.lang.Throwable#getMessage()
   */
  public String getMessage() {
    return "A foreign key points to the value " + cvsValue + " in this table ("
            + tableName + ") but no record with that " + "value was found";
  }

}
