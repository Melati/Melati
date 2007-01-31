/**
 * 
 */
package org.melati.util.test;

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

  /*
   * Test method for 'org.melati.util.ArrayUtils.arrayOf(Vector)'
   */
  public void testArrayOfVector() {
    assertEquals(new Object[]{}.length, ArrayUtils.arrayOf(new Vector()).length);
    
  }

  /*
   * Test method for 'org.melati.util.ArrayUtils.arrayOf(Enumeration)'
   */
  public void testArrayOfEnumeration() {
    assertEquals(new String[]{}.length, 
                 ArrayUtils.arrayOf(new Vector().elements()).length);
    
  }

  /*
   * Test method for 'org.melati.util.ArrayUtils.added(Object[], Object)'
   */
  public void testAdded() {
    String[] them = {"one", "two", "three"};
    assertEquals(them.length + 1, ArrayUtils.added(them, "four").length); 
  }

  /*
   * Test method for 'org.melati.util.ArrayUtils.concatenated(Object[], Object[])'
   */
  public void testConcatenated() {
    String[] x = {"one", "two", "three"};
    String[] y = {"four", "five", "six"};
    assertEquals(x.length + y.length, ArrayUtils.concatenated(x,y).length);
  }

  /*
   * Test method for 'org.melati.util.ArrayUtils.section(Object[], int, int)'
   */
  public void testSection() {
    String[] in = {"one", "two", "three", "four"};
    assertEquals("three", ArrayUtils.section(in, 2, 3)[0]);
  }

  /*
   * Test method for 'org.melati.util.ArrayUtils.indexOf(Object[], Object)'
   */
  public void testIndexOf() {
    String[] in = {"one", "two", "three", "four"};
    assertEquals(1, ArrayUtils.indexOf(in, "two"));
  }

  /*
   * Test method for 'org.melati.util.ArrayUtils.contains(Object[], Object)'
   */
  public void testContains() {
    String[] in = {"one", "two", "three", "four"};
    assertTrue(ArrayUtils.contains(in, "two"));
    assertFalse(ArrayUtils.contains(in, "five"));
  }

}
