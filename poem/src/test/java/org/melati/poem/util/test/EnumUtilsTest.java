/**
 * 
 */
package org.melati.poem.util.test;

import java.util.Enumeration;
import java.util.Vector;

import org.melati.poem.util.EmptyEnumeration;
import org.melati.poem.util.EnumUtils;
import org.melati.poem.util.LimitedEnumeration;
import org.melati.poem.util.SkipEnumeration;

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
    Vector<String> v = new Vector<String>();
    v.add("a");
    v.add("b");
    v.add("c");
    Enumeration<String> e = v.elements();
    EnumUtils.skip(e, 2);
    assertEquals("c",(String)e.nextElement());
    
    SkipEnumeration<String> se = new LimitedEnumeration<String>(v.elements(), 2); 
    EnumUtils.skip(se, 1);
    assertEquals("b",(String)se.nextElement());
    
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
    Vector<String> v1 = new Vector<String>();
    v1.add("a");
    v1.add("b");
    v1.add("c");
    Enumeration<String> e1 = v1.elements();
    Vector<String> v2 = new Vector<String>();
    v2.add("1");
    v2.add("2");
    v2.add("3");
    Enumeration<String> e2 = v2.elements();
    Enumeration<String> joined = EnumUtils.join(e1, e2);
    assertEquals(6, EnumUtils.vectorOf(joined).size());
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
    String result = EnumUtils.concatenated(",",new EmptyEnumeration<String>());
    assertEquals("", result);
  }

  /**
   * Test method for {@link org.melati.poem.util.EnumUtils#contains(java.util.Enumeration, java.lang.Object)}.
   */
  public void testContains() {
    Vector<String> v = new Vector<String>();
    v.add("a");
    v.add("b");
    v.add("c");
    Enumeration<String> e = v.elements();
    assertTrue(EnumUtils.contains(e, "b"));
    // c has yet to be reached
    assertTrue(EnumUtils.contains(e, "c")); 
    assertFalse(EnumUtils.contains(e, "b"));
    
    e = v.elements();
    assertFalse(EnumUtils.contains(e, "z"));
    // Whole enumeration has been searched
    assertFalse(EnumUtils.contains(e, "b"));
  }

}
