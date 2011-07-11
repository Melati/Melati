/**
 * 
 */
package org.melati.util.test;

import org.melati.util.Page;
import org.melati.util.PagedEnumeration;

import junit.framework.TestCase;

/**
 * @author timp
 * @since 13 Jun 2007
 *
 */
public abstract class PagedEnumerationSpec extends TestCase {

   PagedEnumeration<?> it = null;
  /**
   * @param name
   */
  public PagedEnumerationSpec(String name) {
    super(name);
  }

  /** 
   * {@inheritDoc}
   * @see junit.framework.TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
    it = getObjectUnderTest();
  }

  /** 
   * {@inheritDoc}
   * @see junit.framework.TestCase#tearDown()
   */
  protected void tearDown() throws Exception {
    super.tearDown();
  }

  protected abstract PagedEnumeration<?> getObjectUnderTest(); 
  
  /**
   * Test method for {@link org.melati.poem.util.PagedEnumeration#getPageStart()}.
   */
  public void testGetPageStart() {
    assertEquals(3, it.getPageStart());
  }

  /**
   * Test method for {@link org.melati.poem.util.PagedEnumeration#getPageEnd()}.
   */
  public void testGetPageEnd() {
    assertEquals(12, it.getPageEnd());
  }

  /**
   * Test method for {@link org.melati.poem.util.PagedEnumeration#getTotalCount()}.
   */
  public void testGetTotalCount() {
    assertEquals(25, it.getTotalCount());
  }

  /**
   * Test method for {@link org.melati.poem.util.PagedEnumeration#getPrevPageStart()}.
   */
  public void testGetPrevPageStart() {
    assertNull(it.getPrevPageStart());
    
  }

  /**
   * Test method for {@link org.melati.poem.util.PagedEnumeration#getNextPageStart()}.
   */
  public void testGetNextPageStart() {
    assertEquals(new Integer(13),it.getNextPageStart());
  }

  /**
   * Test method for {@link org.melati.poem.util.PagedEnumeration#getCurrentPosition()}.
   */
  public void testGetCurrentPosition() {
    assertEquals(2,it.getCurrentPosition());    
  }

  /**
   * Test method for {@link org.melati.poem.util.PagedEnumeration#getNextPosition()}.
   */
  public void testGetNextPosition() {
    assertEquals(3,it.getNextPosition());        
  }

  /**
   * Test method for {@link org.melati.poem.util.PagedEnumeration#nextElementOnThisPage()}.
   */
  public void testNextElementOnThisPage() {
    assertTrue(it.nextElementOnThisPage());            
  }

  /**
   * Test method for {@link org.melati.poem.util.PagedEnumeration#getPageSize()}.
   */
  public void testGetPageSize() {
    assertEquals(10, it.getPageSize());
  }

  /**
   * Test method for {@link org.melati.poem.util.PagedEnumeration#getPages()}.
   */
  public void testGetPages() {
    assertEquals(3, it.getPages().size());
    Page one = (Page)it.getPages().get(0);
    assertEquals(1, one.getNumber());
    assertEquals(1, one.getStart());
  }
  
  /**
   * Test method for {@link java.util.Enumeration#hasMoreElements()}.
   */
  public void testHasMoreElements() {
   assertTrue(it.hasMoreElements()); 
  }

  /**
   * Test method for {@link java.util.Enumeration#nextElement()}.
   */
  public void testNextElement() {
    assertEquals(new Integer(2), it.nextElement());
  }

}
