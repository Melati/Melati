/**
 * 
 */
package org.melati.test;

import org.melati.PoemContext;

import junit.framework.TestCase;

/**
 * @author timp
 * @since 15-Dec-2006
 *
 */
public class PoemContextTest extends TestCase {

  /**
   * Constructor for PoemContextTest.
   * @param name
   */
  public PoemContextTest(String name) {
    super(name);
  }

  /**
   * @see TestCase#setUp()
   */
  protected void setUp()
      throws Exception {
    super.setUp();
  }

  /**
   * @see TestCase#tearDown()
   */
  protected void tearDown()
      throws Exception {
    super.tearDown();
  }

  /**
   * @see org.melati.PoemContext#PoemContext()
   */
  public void testPoemContext() {

  }

  /**
   * Appears not to be used in anger.
   * @see org.melati.PoemContext#PoemContext(String, String, Integer, String)
   */
  public void testPoemContextStringStringIntegerString() {
    PoemContext pc = new PoemContext("ldb", "table",new Integer(0),"method");
    assertEquals("ldb", pc.getLogicalDatabase());
    assertEquals("table", pc.getTable());
    assertEquals(new Integer(0), pc.getTroid());
    assertEquals("method", pc.getMethod());

  }

  /**
   * @see org.melati.PoemContext#toString()
   */
  public void testToString() {

  }

  /**
   * @see org.melati.PoemContext#getLogicalDatabase()
   */
  public void testGetLogicalDatabase() {

  }

  /**
   * @see org.melati.PoemContext#getTable()
   */
  public void testGetTable() {

  }

  /**
   * @see org.melati.PoemContext#getTroid()
   */
  public void testGetTroid() {

  }

  /**
   * @see org.melati.PoemContext#getMethod()
   */
  public void testGetMethod() {

  }

  /**
   * @see org.melati.PoemContext#setLogicalDatabase(String)
   */
  public void testSetLogicalDatabase() {

  }

  /**
   * @see org.melati.PoemContext#setMethod(String)
   */
  public void testSetMethod() {

  }

  /**
   * @see org.melati.PoemContext#setTable(String)
   */
  public void testSetTable() {

  }

  /**
   * @see org.melati.PoemContext#setTroid(Integer)
   */
  public void testSetTroid() {

  }

}
