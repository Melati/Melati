/**
 * 
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
   * @see java.sql.Driver#getPropertyInfo(java.lang.String, java.util.Properties)
   */
  public DriverPropertyInfo[] getPropertyInfo(String url, Properties info)
      throws SQLException {
    if (shouldThrow("getPropertyInfo"))
      throw new SQLException("Driver bombed");
    return it.getPropertyInfo(url, info);
  }

  /**
   * {@inheritDoc}
   * @see java.sql.Driver#jdbcCompliant()
   */
  public boolean jdbcCompliant() {
    return it.jdbcCompliant();
  }

}
