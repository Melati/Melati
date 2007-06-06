/**
 * 
 */
package org.melati.poem.util.test;

import org.melati.poem.util.EmptyEnumeration;
import org.melati.poem.util.EnumUtils;

import junit.framework.TestCase;

/**
 * @author timp
 * @since 1 Jun 2007
 *
 */
public class EnumUtilsTest extends TestCase {

  /**
   * @param name
   */
  public EnumUtilsTest(String name) {
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
   * Test method for {@link org.melati.poem.util.EnumUtils#skip(java.util.Enumeration, int)}.
   */
  public void testSkip() {
    
  }

  /**
   * Test method for {@link org.melati.poem.util.EnumUtils#initial(java.util.Enumeration, int)}.
   */
  public void testInitial() {
    
  }

  /**
   * Test method for {@link org.melati.poem.util.EnumUtils#join(java.util.Enumeration, java.util.Enumeration)}.
   */
  public void testJoin() {
    
  }

  /**
   * Test method for {@link org.melati.poem.util.EnumUtils#vectorOf(java.util.Enumeration, int)}.
   */
  public void testVectorOfEnumerationInt() {
    
  }

  /**
   * Test method for {@link org.melati.poem.util.EnumUtils#vectorOf(java.util.Enumeration)}.
   */
  public void testVectorOfEnumeration() {
    
  }

  /**
   * Test method for {@link org.melati.poem.util.EnumUtils#concatenated(java.lang.String, java.util.Enumeration)}.
   */
  public void testConcatenated() {
    String result = EnumUtils.concatenated(",",EmptyEnumeration.it);
    assertEquals("", result);
  }

  /**
   * Test method for {@link org.melati.poem.util.EnumUtils#contains(java.util.Enumeration, java.lang.Object)}.
   */
  public void testContains() {
    
  }

}
