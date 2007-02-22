/**
 * 
 */
package org.melati.poem.dbms.test;

import java.sql.Driver;

import org.hsqldb.jdbcDriver;

/**
 * A decorated Hsqldb {@link jdbcDriver}.
 * @author timp
 * @since 10 Feb 2007
 *
 */
public class HsqldbThrowingJdbcDriver extends DriverThrowingDecorated implements Driver {

  /**
   * Constructor.
   */
  public HsqldbThrowingJdbcDriver() {
    super(new jdbcDriver());
  }

}
