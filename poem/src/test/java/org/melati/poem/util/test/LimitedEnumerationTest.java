/**
 * 
 */
package org.melati.poem.util.test;

import java.util.NoSuchElementException;
import java.util.Vector;

import org.melati.poem.util.LimitedEnumeration;

import junit.framework.TestCase;

/**
 * @author timp
 * @since 12 Jun 2007
 *
 */
public class LimitedEnumerationTest extends TestCase {

  /**
   * @param name
   */
  public LimitedEnumerationTest(String name) {
    super(name);
  }
  LimitedEnumeration<String> it;
  LimitedEnumeration<String> it2;
  /** 
   * {@inheritDoc}
   * @see junit.framework.TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
    Vector<String> them = new Vector<String>();
    them.add("a");
    them.add("b");
    them.add("c");
    them.add("d");
    it = new LimitedEnumeration<String>(them.elements(),2);
    it2 = new LimitedEnumeration<String>(new LimitedEnumeration<String>(them.elements(),2),2);
  }

  /** 
   * {@inheritDoc}
   * @see junit.framework.TestCase#tearDown()
   */
  protected void tearDown() throws Exception {
    super.tearDown();
  }

  /**
   * Test method for {@link org.melati.poem.util.LimitedEnumeration#LimitedEnumeration(java.util.Enumeration, int)}.
   */
  public void testLimitedEnumeration() {
  }

  /**
   * Test method for {@link org.melati.poem.util.LimitedEnumeration#hasMoreElements()}.
   */
  public void testHasMoreElements() {
    assertTrue(it.hasMoreElements());
  }

  /**
   * Test method for {@link org.melati.poem.util.LimitedEnumeration#nextElement()}.
   */
  public void testNextElement() {
    assertEquals("a", it.nextElement());
    assertEquals("b", it.nextElement());
    try { 
      assertEquals("c", it.nextElement());
      fail("Should have bombed");
    } catch (NoSuchElementException e) { 
      e = null;
    }
  }

  /**
   * Test method for {@link org.melati.poem.util.LimitedEnumeration#skip()}.
   */
  public void testSkip() {
    it.skip();
    assertEquals("b", it.nextElement());
    try { 
      it.nextElement();
      fail("Should have bombed");
    } catch (NoSuchElementException e) { 
      e = null;
    }
    try { 
      it.skip();
      fail("Should have bombed");
    } catch (NoSuchElementException e) { 
      e = null;
    }
    it2.skip();
    assertEquals("b", it2.nextElement());
    try { 
      it2.skip();
    } catch (NoSuchElementException e) { 
      e = null;
    }
    
  }

}
