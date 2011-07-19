/**
 * 
 */
package org.melati.poem.util.test;

import java.util.NoSuchElementException;

import org.melati.poem.util.EmptyEnumeration;

import junit.framework.TestCase;

/**
 * @author timp
 * @since 29 May 2007
 *
 */
public class EmptyEnumerationTest extends TestCase {

  /**
   * @param name
   */
  public EmptyEnumerationTest(String name) {
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
   * Test method for {@link org.melati.poem.util.EmptyEnumeration#hasMoreElements()}.
   */
  public void testHasMoreElements() {
    assertFalse(new EmptyEnumeration<String>().hasMoreElements());
  }

  /**
   * Test method for {@link org.melati.poem.util.EmptyEnumeration#nextElement()}.
   */
  public void testNextElement() {
    try { 
      new EmptyEnumeration<String>().nextElement();
      fail("should have bombed");
    } catch (NoSuchElementException e) {
      e = null;      
    }
  }

}
