/**
 * 
 */
package org.melati.poem.util.test;

import java.util.Vector;

import org.melati.poem.util.DumbPagedEnumeration;
import org.melati.poem.util.PagedEnumeration;

/**
 * @author timp
 * @since 13 Jun 2007
 *
 */
public class DumbPagedEnumerationTest extends PagedEnumerationSpec {

  /**
   * @param name
   */
  public DumbPagedEnumerationTest(String name) {
    super(name);
  }

  /** 
   * {@inheritDoc}
   * @see org.melati.poem.util.test.PagedEnumerationSpec#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
  }

  /** 
   * {@inheritDoc}
   * @see org.melati.poem.util.test.PagedEnumerationSpec#tearDown()
   */
  protected void tearDown() throws Exception {
    super.tearDown();
  }

  protected PagedEnumeration getObjectUnderTest() {
    Vector them = new Vector();
    for (int i = 0; i < 30; i++)
      them.add(new Integer(i));
    return new DumbPagedEnumeration(them.elements(), 3, 10, 25);
  }
  
  /**
   * Test method for {@link org.melati.poem.util.DumbPagedEnumeration#DumbPagedEnumeration(java.util.Enumeration, int, int, int)}.
   */
  public void testDumbPagedEnumerationEnumerationIntIntInt() {
  }

  /**
   * Test method for {@link org.melati.poem.util.DumbPagedEnumeration#DumbPagedEnumeration(org.melati.poem.util.SkipEnumeration, int, int, int)}.
   */
  public void testDumbPagedEnumerationSkipEnumerationIntIntInt() {
  }

  /**
   * Test method for {@link org.melati.poem.util.DumbPagedEnumeration#getTotalCountIsMinimum()}.
   */
  public void testGetTotalCountIsMinimum() {
    assertTrue(((DumbPagedEnumeration)it).getTotalCountIsMinimum());
  }

}
