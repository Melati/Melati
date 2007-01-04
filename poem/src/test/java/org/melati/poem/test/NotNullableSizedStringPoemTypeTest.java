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
public class NotNullableSizedStringPoemTypeTest extends SizedAtomPoemTypeTest {

  /**
   * 
   */
  public NotNullableSizedStringPoemTypeTest() {
  }

  /**
   * @param name
   */
  public NotNullableSizedStringPoemTypeTest(String name) {
    super(name);
  }

  /**
   * {@inheritDoc}
   * @see org.melati.poem.test.SQLPoemTypeTest#setObjectUnderTest()
   */
  void setObjectUnderTest() {
    it = new StringPoemType(false, 20);
  }

}
