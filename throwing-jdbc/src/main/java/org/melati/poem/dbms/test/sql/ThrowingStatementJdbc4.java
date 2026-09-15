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

import java.sql.SQLException;
import java.sql.Statement;

/**
 * The JDBC4 members of a {@link Statement}, decorated to throw SQLException on command.
 *  
 * @author timp
 * @since  5 Feb 2008
 *
 */
public abstract class ThrowingStatementJdbc4 extends ThrowingStatementJdbc3 {
  /**
   *  JDBC 4.0
   */
  
    
  /** 
   * {@inheritDoc}
   * @see java.sql.Statement#isClosed()
   */

  public boolean isClosed() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "isClosed"))
      throw new SQLException("Statement bombed");
    return it.isClosed();
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.Statement#isPoolable()
   */

  public boolean isPoolable() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "isPoolable"))
      throw new SQLException("Statement bombed");
    return it.isPoolable();
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.Statement#setPoolable(boolean)
   */

  public void setPoolable(boolean poolable) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "setPoolable"))
      throw new SQLException("Statement bombed");
    it.setPoolable(poolable);
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.Wrapper#isWrapperFor(java.lang.Class)
   */

  public boolean isWrapperFor(Class<?> iface) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "isWrapperFor"))
      throw new SQLException("Statement bombed");
    return it.isWrapperFor(iface);
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.Wrapper#unwrap(java.lang.Class)
   */

  public <T> T unwrap(Class<T> iface) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "unwrap"))
      throw new SQLException("Statement bombed");
    return it.unwrap(iface);
  }


}
