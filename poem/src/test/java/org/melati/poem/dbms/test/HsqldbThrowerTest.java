/**
 * 
 */
package org.melati.poem.dbms.test;

import org.melati.poem.dbms.DbmsFactory;

/**
 * @author timp
 * @since 10 Feb 2007
 *
 */
public class HsqldbThrowerTest extends HsqldbTest {

  /**
   * @param name
   */
  public HsqldbThrowerTest(String name) {
    super(name);
  }
  protected void setObjectUnderTest() {
    it = DbmsFactory.getDbms("org.melati.poem.dbms.test.HsqldbThrower");
  }

}
