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
public abstract class SizedAtomPoemTypeSpec<T> extends SQLPoemTypeSpec<T> {

  /**
   * @param name
   */
  public SizedAtomPoemTypeSpec() {
    super();
  }

  /**
   * @param name
   */
  public SizedAtomPoemTypeSpec(String name) {
    super(name);
  }

  /**
   * {@inheritDoc}
   * @see org.melati.poem.test.SQLPoemTypeSpec#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
  }

  /**
   * {@inheritDoc}
   * @see org.melati.poem.test.SQLPoemTypeSpec#tearDown()
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
    ((SizedAtomPoemType<T>)it).getSize();
  }

  /**
   * Test method for {@link org.melati.poem.SizedAtomPoemType#withSize(int)}.
   */
  public void testWithSize() {
    int currentSize = ((SizedAtomPoemType<T>)it).getSize();
    assertEquals(it, ((SizedAtomPoemType<T>)it).withSize(currentSize));
  }

  /**
   * Test method for {@link org.melati.poem.SizedAtomPoemType#
   * sizeGreaterEqual(int, int)}.
   */
  public void testSizeGreaterEqual() {
    int currentSize = ((SizedAtomPoemType<T>)it).getSize();
    assertTrue(SizedAtomPoemType.sizeGreaterEqual(currentSize, currentSize));
  }

}
