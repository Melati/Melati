/**
 * 
 */
package org.melati.poem;

import java.util.NoSuchElementException;

import junit.framework.TestCase;

/**
 * @author timp
 *
 */
public class BooleanPossibleRawEnumerationTest extends TestCase {

  /*
   * Test method for 'org.melati.poem.BooleanPossibleRawEnumeration.hasMoreElements()'
   */
  public void testHasMoreElements() {
    // see below
  }

  /*
   * Test method for 'org.melati.poem.BooleanPossibleRawEnumeration.nextElement()'
   */
  public void testNextElement() {
    BooleanPossibleRawEnumeration en = new BooleanPossibleRawEnumeration();
    assertTrue(en.hasMoreElements());
    Object o = en.nextElement();
    assertEquals(Boolean.FALSE,o);
    assertTrue(en.hasMoreElements());
    o = en.nextElement();
    assertEquals(Boolean.TRUE,o);
    assertFalse(en.hasMoreElements());
    try {
      o = en.nextElement();
      fail("Should have thrown NoSuchElementException");      
    } catch (Exception e) {
      assertTrue( e instanceof NoSuchElementException);
    }

  }

}
