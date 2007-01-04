/**
 * 
 */
package org.melati.poem.test;

import org.melati.poem.StringPoemType;

/**
 * @author timp
 * @since 21 Dec 2006
 *
 */
public class NullableSizedStringPoemTypeTest extends SizedAtomPoemTypeTest {

  /**
   * 
   */
  public NullableSizedStringPoemTypeTest() {
  }

  /**
   * @param name
   */
  public NullableSizedStringPoemTypeTest(String name) {
    super(name);
  }

  /**
   * {@inheritDoc}
   * @see org.melati.poem.test.SQLPoemTypeTest#setObjectUnderTest()
   */
  void setObjectUnderTest() {
    it = new StringPoemType(true, -1);
  }

}
