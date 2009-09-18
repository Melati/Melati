/**
 * 
 */
package org.melati.poem.util.test;

import java.util.NoSuchElementException;

import org.melati.poem.util.ArrayEnumeration;

import junit.framework.TestCase;

/**
 * @author timp
 * @since 29 May 2007
 *
 */
public class ArrayEnumerationTest extends TestCase {

  /**
   * @param name
   */
  public ArrayEnumerationTest(String name) {
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
   * Test method for {@link org.melati.poem.util.ArrayEnumeration#ArrayEnumeration(java.lang.Object[])}.
   */
  public void testArrayEnumeration() {
    
  }

  /**
   * Test method for {@link org.melati.poem.util.ArrayEnumeration#hasMoreElements()}.
   */
  public void testHasMoreElements() {
    
  }

  /**
   * Test method for {@link org.melati.poem.util.ArrayEnumeration#nextElement()}.
   */
  public void testNextElement() {
    String[] them = new String[] {"one", "two", "three"};
    ArrayEnumeration<String> it = new ArrayEnumeration<String>(them);
    while (it.hasMoreElements()) 
      it.nextElement();
    try { 
      it.nextElement();
      fail("should have bombed");
    } catch (NoSuchElementException e) {
      e = null;      
    }
  }

}
