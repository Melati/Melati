package org.melati.poem.dbms.test;

import org.melati.poem.dbms.Dbms;
import org.melati.poem.dbms.Hsqldb;

/**
 * A DBMS which can throw an SQLException when told to.
 * 
 * @author timp
 * @since 10 Feb 2007
 */
public class HsqldbThrower extends Hsqldb implements Dbms {

  /**
   * Constructor. 
   */
  public HsqldbThrower() {
    setDriverClassName("org.melati.poem.dbms.test.HsqldbThrowingJdbcDriver");
  }

}
