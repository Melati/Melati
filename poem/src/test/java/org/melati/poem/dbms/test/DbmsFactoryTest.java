/**
 * 
 */
package org.melati.poem.dbms.test;

import org.melati.poem.AppBugPoemException;
import org.melati.poem.dbms.AnsiStandard;
import org.melati.poem.dbms.Dbms;
import org.melati.poem.dbms.DbmsFactory;

import junit.framework.TestCase;

/**
 * @author timp
 * @since 23 Jan 2007
 *
 */
public class DbmsFactoryTest extends TestCase {

  /**
   * Constructor.
   * @param name
   */
  public DbmsFactoryTest(String name) {
    super(name);
  }

  /**
   * {@inheritDoc}
   * @see junit.framework.TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
  }

  /**
   * {@inheritDoc}
   * @see junit.framework.TestCase#tearDown()
   */
  protected void tearDown() throws Exception {
    super.tearDown();
  }

  /**
   * Test method for {@link org.melati.poem.dbms.DbmsFactory#
   *     getDbms(java.lang.String)}.
   */
  public void testGetDbms() {
    Dbms ansiStandard = DbmsFactory.getDbms("org.melati.poem.dbms.AnsiStandard");
    assertTrue(ansiStandard instanceof AnsiStandard);
    try { 
      DbmsFactory.getDbms("java.lang.Exception");
      fail("Should have blown up");
    } catch (AppBugPoemException e) { 
      e = null;
    }
    try { 
      DbmsFactory.getDbms("gsqldb");
      fail("Should have blown up");
    } catch (AppBugPoemException e) { 
      e = null;
    }
  }

}
