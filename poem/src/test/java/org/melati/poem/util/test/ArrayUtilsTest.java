/**
 * 
 */
package org.melati.poem.util.test;

import java.util.Vector;

import org.melati.poem.util.ArrayUtils;

import junit.framework.TestCase;

/**
 * @author timp
 * @since 2006-05-03
 */
public class ArrayUtilsTest extends TestCase {

  /**
   * Constructor for ArrayUtilsTest.
   * @param arg0
   */
  public ArrayUtilsTest(String arg0) {
    super(arg0);
  }

  /**
   * @see org.melati.poem.util.ArrayUtils#arrayOf(Vector)
   */
  public void testArrayOfVector() {
    assertEquals(new Object[]{}.length, ArrayUtils.arrayOf(new Vector<Object>()).length);
    
  }

  /**
   * @see org.melati.poem.util.ArrayUtils#arrayOf(java.util.Enumeration)
   */
  public void testArrayOfEnumeration() {
    assertEquals(new String[]{}.length, 
                 ArrayUtils.arrayOf(new Vector<Object>().elements()).length);
    
  }

  /**
   * @see org.melati.poem.util.ArrayUtils#added(Object[], Object)
   */
  public void testAdded() {
    String[] them = {"one", "two", "three"};
    assertEquals(them.length + 1, ArrayUtils.added(them, "four").length); 
  }

  /**
   * @see org.melati.poem.util.ArrayUtils#removed(Object[], Object)
   */
  public void testRemoved() {
    String[] them = {"one", "two", "three", "four", "five"};
    Object[] without = ArrayUtils.removed(them, "four");
    assertEquals(them.length - 1, without.length);
    assertTrue(without[2] instanceof String);
  }

  /**
   * @see org.melati.poem.util.ArrayUtils#concatenated(Object[], Object[])
   */
  public void testConcatenated() {
    String[] x = {"one", "two", "three"};
    String[] y = {"four", "five", "six"};
    assertEquals(x.length + y.length, ArrayUtils.concatenated(x,y).length);
  }

  /**
   * @see org.melati.poem.util.ArrayUtils#section(Object[], int, int)
   */
  public void testSection() {
    String[] in = {"one", "two", "three", "four"};
    assertEquals("three", ArrayUtils.section(in, 2, 3)[0]);
  }

  /**
   * @see org.melati.poem.util.ArrayUtils#indexOf(Object[], Object)
   */
  public void testIndexOf() {
    String[] in = {"one", "two", "three", "four"};
    assertEquals(1, ArrayUtils.indexOf(in, "two"));
  }

  /**
   * @see org.melati.poem.util.ArrayUtils#contains(Object[], Object)
   */
  public void testContains() {
    String[] in = {"one", "two", "three", "four"};
    assertTrue(ArrayUtils.contains(in, "two"));
    assertFalse(ArrayUtils.contains(in, "five"));
  }

}
