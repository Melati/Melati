/**
 * 
 */
package org.melati.poem.util.test;

import java.util.NoSuchElementException;
import java.util.Vector;

import org.melati.poem.util.FilteredEnumeration;
import org.melati.poem.util.EnumUtils;

import junit.framework.TestCase;

/**
 * @author timp
 * @since 7 June 2007
 *
 */
public class FilteredEnumerationTest extends TestCase {

  FilteredEnumeration<String> it;
  
  /**
   * @param name
   */
  public FilteredEnumerationTest(String name) {
    super(name);
  }

  /** 
   * {@inheritDoc}
   * @see junit.framework.TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
    Vector<String> v = new Vector<String>();
    v.add("a");
    v.add("b");
    v.add("c");
    v.add("d");
    v.add("e");
    it = new FilteredEnumeration<String>(v.elements()) {
      public boolean isIncluded(String o) {
        return o.compareTo("c") <= 0;
      }
    };
  }

  /** 
   * {@inheritDoc}
   * @see junit.framework.TestCase#tearDown()
   */
  protected void tearDown() throws Exception {
    super.tearDown();
  }

  /**
   * Test method for {@link org.melati.poem.util.FilteredEnumeration#FilteredEnumeration(java.util.Enumeration)}.
   */
  public void testFilteredEnumeration() {
    assertEquals(3, EnumUtils.vectorOf(it).size());
  }

  /**
   * Test method for {@link org.melati.poem.util.FilteredEnumeration#hasMoreElements()}.
   */
  public void testHasMoreElements() {
    
  }

  /**
   * Test method for {@link org.melati.poem.util.FilteredEnumeration#nextElement()}.
   */
  public void testNextElement() {
    
  }

  /**
   * Test method for {@link org.melati.poem.util.FilteredEnumeration#skip()}.
   */
  public void testSkip() {
    it.skip();
    it.skip();
    it.skip();
    try { 
      it.skip();
      fail("Should have blown up");
    } catch (NoSuchElementException e) { 
      e = null;
    }
    try { 
      it.nextElement();    
    } catch (NoSuchElementException e) { 
      e = null;
    }
  }

}
