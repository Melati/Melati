/**
 * 
 */
package org.melati.poem.test;

import org.melati.poem.SizedAtomPoemType;

/**
 * @author timp
 * @since 30 Dec 2006
 *
 */
public abstract class SizedAtomPoemTypeTest extends SQLPoemTypeTest {

  /**
   * @param name
   */
  public SizedAtomPoemTypeTest() {
    super();
  }

  /**
   * @param name
   */
  public SizedAtomPoemTypeTest(String name) {
    super(name);
  }

  /**
   * {@inheritDoc}
   * @see org.melati.poem.test.SQLPoemTypeTest#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
  }

  /**
   * {@inheritDoc}
   * @see org.melati.poem.test.SQLPoemTypeTest#tearDown()
   */
  protected void tearDown() throws Exception {
    super.tearDown();
  }

  /**
   * Test method for {@link org.melati.poem.SizedAtomPoemType#
   * SizedAtomPoemType(int, java.lang.String, boolean, int)}.
   */
  public void testSizedAtomPoemType() {
  }

  /**
   * Test method for {@link org.melati.poem.SizedAtomPoemType#getSize()}.
   */
  public void testGetSize() {
    ((SizedAtomPoemType)it).getSize();
  }

  /**
   * Test method for {@link org.melati.poem.SizedAtomPoemType#withSize(int)}.
   */
  public void testWithSize() {
    int currentSize = ((SizedAtomPoemType)it).getSize();
    assertEquals(it, ((SizedAtomPoemType)it).withSize(currentSize));
  }

  /**
   * Test method for {@link org.melati.poem.SizedAtomPoemType#
   * sizeGreaterEqual(int, int)}.
   */
  public void testSizeGreaterEqual() {
    int currentSize = ((SizedAtomPoemType)it).getSize();
    assertTrue(SizedAtomPoemType.sizeGreaterEqual(currentSize, currentSize));
  }

}
