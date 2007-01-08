/*
 * $Source$
 * $Revision$
 *
 * Copyright (C) 2003 Tim Joyce
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
 *     Tim Joyce <timj At paneris.org>
 *     http://paneris.org/~timj
 */

package org.melati.poem;

 /**
  * An object which can be persisted, ie has a Troid.
  *
  */ 
public interface Persistable {

  /**
   * The object's troid, returned after access check.
   *
   * @return Every record (object) in a POEM database must have a
   *         troid (table row ID, or table-unique non-nullable integer primary
   *         key), often but not necessarily called <TT>id</TT>, so that it can
   *         be conveniently `named' for retrieval.
   *
   * @exception AccessPoemException
   *                if <TT>assertCanRead</TT> fails
   *
   * @see Table#getObject(java.lang.Integer)
   * @see Persistent#getTroid()
   */
  Integer getTroid() throws AccessPoemException;
  
  /**
   * Get the troid without access checking.
   * @return the troid
   */
  Integer troid();
}
