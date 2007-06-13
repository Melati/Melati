/**
 * 
 */
package org.melati.poem.util.test;

import java.util.Vector;

import org.melati.poem.util.CountedDumbPagedEnumeration;
import org.melati.poem.util.PagedEnumeration;

/**
 * @author timp
 * @since 13 Jun 2007
 *
 */
public class CountedDumbPagedEnumerationTest extends PagedEnumerationSpec {

  /**
   * @param name
   */
  public CountedDumbPagedEnumerationTest(String name) {
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
    return new CountedDumbPagedEnumeration(them.elements(), 3, 10, 25);
  }

  /**
   * Test method for {@link org.melati.poem.util.CountedDumbPagedEnumeration#CountedDumbPagedEnumeration(java.util.Enumeration, int, int, int)}.
   */
  public void testCountedDumbPagedEnumeration() {

  }


}
