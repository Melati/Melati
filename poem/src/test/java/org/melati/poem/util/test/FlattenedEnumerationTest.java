/**
 * 
 */
package org.melati.poem.util.test;

import java.util.NoSuchElementException;

import org.melati.poem.util.EmptyEnumeration;
import org.melati.poem.util.FlattenedEnumeration;

import junit.framework.TestCase;

/**
 * @author timp
 * @since 29 May 2007
 *
 */
public class FlattenedEnumerationTest extends TestCase {

  /**
   * @param name
   */
  public FlattenedEnumerationTest(String name) {
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
   * Test method for {@link org.melati.poem.util.FlattenedEnumeration#FlattenedEnumeration(java.util.Enumeration)}.
   */
  public void testFlattenedEnumerationEnumeration() {
    
  }

  /**
   * Test method for {@link org.melati.poem.util.FlattenedEnumeration#FlattenedEnumeration(java.util.Enumeration, java.util.Enumeration)}.
   */
  public void testFlattenedEnumerationEnumerationEnumeration() {
    
  }

  /**
   * Test method for {@link org.melati.poem.util.FlattenedEnumeration#hasMoreElements()}.
   */
  public void testHasMoreElements() {
    
  }

  /**
   * Test method for {@link org.melati.poem.util.FlattenedEnumeration#nextElement()}.
   */
  public void testNextElement() {
    FlattenedEnumeration fen = new FlattenedEnumeration(EmptyEnumeration.it);
    try { 
      fen.nextElement();
      fail("should have bombed");
    } catch (NoSuchElementException e) {
      e = null;      
    }
    
  }

}
