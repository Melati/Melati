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

import java.sql.Ref;
import java.sql.SQLException;
import java.util.Map;

/**
 * The JDBC3 methods of a {@link Ref}, decorated to throw an SQLException on command.
 * 
 * @author timp
 * @since  5 Feb 2008
 *
 */
public class ThrowingRefJdbc3 
    extends Thrower 
    implements Ref {

  Ref it = null;

  /** 
   * {@inheritDoc}
   * @see java.sql.Ref#getBaseTypeName()
   */

  public String getBaseTypeName() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getBaseTypeName"))
      throw new SQLException("Ref bombed");
    return it.getBaseTypeName();
  }

  /** 
   * {@inheritDoc}
   * @see java.sql.Ref#getObject()
   */

  public Object getObject() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getObject"))
      throw new SQLException("Ref bombed");
    return it.getObject();
  }

  /** 
   * {@inheritDoc}
   * @see java.sql.Ref#getObject(java.util.Map)
   */

  public Object getObject(Map<String, Class<?>> map) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getObject"))
      throw new SQLException("Ref bombed");
    return it.getObject();
  }

  /** 
   * {@inheritDoc}
   * @see java.sql.Ref#setObject(java.lang.Object)
   */

  public void setObject(Object value) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "setObject"))
      throw new SQLException("Ref bombed");
    it.setObject(value);
  }


}
