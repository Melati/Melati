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

import java.sql.SQLException;
import java.sql.Savepoint;

/**
 * @author timp
 * @since 10 Feb 2007
 *
 */
public class ThrowingSavepoint extends Thrower implements Savepoint {
  final static String className = ThrowingSavepoint.class.getName() + ".";
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
  
  Savepoint it = null;
  
  /**
   * Constructor.
   * @param savepoint to decorate
   */
  public ThrowingSavepoint(Savepoint savepoint) {
    this.it = savepoint;
  }

  /**
   * {@inheritDoc}
   * @see java.sql.Savepoint#getSavepointId()
   */
  public int getSavepointId() throws SQLException {
    if (shouldThrow("getSavepointId"))
      throw new SQLException("Savepoint bombed");
    return it.getSavepointId();
  }

  /**
   * {@inheritDoc}
   * @see java.sql.Savepoint#getSavepointName()
   */
  public String getSavepointName() throws SQLException {
    if (shouldThrow("getSavepointName"))
      throw new SQLException("Savepoint bombed");
    return it.getSavepointName();
  }

}
