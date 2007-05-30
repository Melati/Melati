/**
 * 
 */
package org.melati.poem.util.test;

import java.util.Vector;

import org.melati.poem.util.ConsEnumeration;

import junit.framework.TestCase;

/**
 * @author timp
 * @since 30 May 2007
 *
 */
public class ConsEnumerationTest extends TestCase {

  /**
   * @param name
   */
  public ConsEnumerationTest(String name) {
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
   * Test method for {@link org.melati.poem.util.ConsEnumeration#ConsEnumeration(java.lang.Object, java.util.Enumeration)}.
   */
  public void testConsEnumeration() {
    
  }

  /**
   * Test method for {@link org.melati.poem.util.ConsEnumeration#hasMoreElements()}.
   */
  public void testHasMoreElements() {
    
  }

  /**
   * Test method for {@link org.melati.poem.util.ConsEnumeration#nextElement()}.
   */
  public void testNextElement() {
    
  }

  /**
   * Test method for {@link org.melati.poem.util.ConsEnumeration#skip()}.
   */
  public void testSkip() {
    ConsEnumeration c = new ConsEnumeration("head", new Vector(1).elements());
    assertTrue(c.hasMoreElements());
    c.skip();
    assertFalse(c.hasMoreElements());
  }

}
