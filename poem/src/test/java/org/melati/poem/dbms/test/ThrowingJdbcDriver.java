/**
 * 
 */
package org.melati.poem.dbms.test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.hsqldb.jdbcDriver;

/**
 * A wrapper around a Connection.
 * @author timp
 * @since 10 Feb 2007
 *
 */
public class ThrowingJdbcDriver extends jdbcDriver {

  /**
   * Constructor.
   */
  public ThrowingJdbcDriver() {
  }

  /**
   * {@inheritDoc}
   * @see org.hsqldb.jdbcDriver#connect(java.lang.String, java.util.Properties)
   */
  public Connection connect(String arg0, Properties arg1) throws SQLException {
    Connection c = super.connect(arg0, arg1);
    return new ThrowingConnection(c);
  }

}
