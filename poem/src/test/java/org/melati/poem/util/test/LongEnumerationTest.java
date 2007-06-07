/**
 * 
 */
package org.melati.poem.util.test;

import java.util.NoSuchElementException;

import org.melati.poem.util.LongEnumeration;

import junit.framework.TestCase;

/**
 * @author timp
 * @since 7 Jun 2007
 *
 */
public class LongEnumerationTest extends TestCase {

  /**
   * @param name
   */
  public LongEnumerationTest(String name) {
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
   * Test method for {@link org.melati.poem.util.LongEnumeration#LongEnumeration(long, long)}.
   */
  public void testLongEnumeration() {
    
  }

  /**
   * Test method for {@link org.melati.poem.util.LongEnumeration#hasMoreElements()}.
   */
  public void testHasMoreElements() {
    
  }

  /**
   * Test method for {@link org.melati.poem.util.LongEnumeration#nextElement()}.
   */
  public void testNextElement() {
    LongEnumeration l = new LongEnumeration(2,5);
    assertEquals(new Long(2),l.nextElement());
    assertEquals(new Long(3),l.nextElement());
    assertEquals(new Long(4),l.nextElement());
    try { 
      assertEquals(new Long(5),l.nextElement());
      fail("Should have blown up");
    } catch (NoSuchElementException e) { 
      e = null;
    }

  }

}
