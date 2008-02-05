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

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.util.Properties;


/**
 * A {@link Driver} decorated with a {@link Thrower}.
 * @author timp
 * @since 22 Feb 2007
 *
 */
public class ThrowingDriver implements Driver {

  final static String className = ThrowingDriver.class.getName() + ".";
  
  /**
   * @param methodName the name of the method to set to start to throw
   */
  public static void startThrowing(String methodName) {
    Thrower.startThrowing(className  +  methodName);
  }
  
  /**
   * @param methodName the name of the method to set to start to throw
   * @param goes the number of invocations before throwing should be happen
   */
  public static void startThrowingAfter(String methodName, int goes) {
    Thrower.startThrowingAfter(className  +  methodName, goes);
  }
  /**
   * @param methodName the name of the method to set to no longer throw
   */
  public static void stopThrowing(String methodName) {
    Thrower.stopThrowing(className  +  methodName);
  }
  
  /**
   * @param methodName the name of the method to check
   * @return whether the named method should throw an exception
   */
  public static boolean shouldThrow(String methodName) { 
    return Thrower.shouldThrow(className  +  methodName);
  }

  private Driver it = null;
  
  /**
   * Constructor.
   * @param d the driver to decorate
   */
  public ThrowingDriver(Driver d) {
    it = d;
  }

  /**
   * {@inheritDoc}
   * @see java.sql.Driver#acceptsURL(java.lang.String)
   */
  public boolean acceptsURL(String url) throws SQLException {
    if (shouldThrow("acceptsURL"))
      throw new SQLException("Driver bombed");
    return it.acceptsURL(url);
  }

  /**
   * Return the decorated Connection.
   * {@inheritDoc}
   * @see java.sql.Driver#connect(java.lang.String, java.util.Properties)
   */
  public Connection connect(String url, Properties info) throws SQLException {
    if (shouldThrow("connect"))
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
   *  JDBC 4.0
   */

  /** 
   * {@inheritDoc}
   * @see java.sql.Driver#getPropertyInfo(java.lang.String, java.util.Properties)
   */

  public DriverPropertyInfo[] getPropertyInfo(String url, Properties info)
          throws SQLException {
    if (shouldThrow("getPropertyInfo"))
      throw new SQLException("Driver bombed");
    return it.getPropertyInfo(url, info);
  }

  
}
