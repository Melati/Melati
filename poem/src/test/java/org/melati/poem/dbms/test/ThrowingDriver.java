/**
 * 
 */
package org.melati.poem.dbms.test;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.util.Properties;

import org.melati.poem.dbms.test.sql.ThrowingConnection;

/**
 * A {@link Driver} decorated with a {@link Thrower}.
 * @author timp
 * @since 22 Feb 2007
 *
 */
public class ThrowingDriver implements Driver {

  private Driver it = null;
  /**
   * 
   */
  public ThrowingDriver(Driver d) {
    it = d;
  }

  /**
   * {@inheritDoc}
   * @see java.sql.Driver#acceptsURL(java.lang.String)
   */
  public boolean acceptsURL(String url) throws SQLException {
    return it.acceptsURL(url);
  }

  /**
   * {@inheritDoc}
   * @see java.sql.Driver#connect(java.lang.String, java.util.Properties)
   */
  public Connection connect(String url, Properties info) throws SQLException {
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
