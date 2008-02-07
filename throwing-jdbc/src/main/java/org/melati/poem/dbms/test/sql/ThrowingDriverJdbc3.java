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

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author timp
 * @since  5 Feb 2008
 *
 */
public abstract class ThrowingDriverJdbc3 
    extends Thrower 
    implements Driver {

  Driver it = null;


  /**
   * {@inheritDoc}
   * @see java.sql.Driver#acceptsURL(java.lang.String)
   */
  public boolean acceptsURL(String url) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "acceptsURL"))
      throw new SQLException("Driver bombed");
    return it.acceptsURL(url);
  }

  /**
   * Return the decorated Connection.
   * {@inheritDoc}
   * @see java.sql.Driver#connect(java.lang.String, java.util.Properties)
   */
  public Connection connect(String url, Properties info) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "connect"))
      throw new SQLException("Driver bombed");
    return new ThrowingConnection(it.connect(url, info));
  }

  /** 
   * {@inheritDoc}
   * @see java.sql.Driver#getMajorVersion()
   */

  public int getMajorVersion() {
    return it.getMajorVersion();
  }

  /** 
   * {@inheritDoc}
   * @see java.sql.Driver#getMinorVersion()
   */

  public int getMinorVersion() {
    return it.getMinorVersion();
  }

  /** 
   * {@inheritDoc}
   * @see java.sql.Driver#jdbcCompliant()
   */

  public boolean jdbcCompliant() {
    return it.jdbcCompliant();
  }

  /** 
   * {@inheritDoc}
   * @see java.sql.Driver#getPropertyInfo(java.lang.String, java.util.Properties)
   */

  public DriverPropertyInfo[] getPropertyInfo(String url, Properties info)
          throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getPropertyInfo"))
      throw new SQLException("Driver bombed");
    return it.getPropertyInfo(url, info);
  }

  
  
}
