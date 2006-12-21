/**
 * 
 */
package org.melati.poem.test;

import org.melati.poem.BinaryPoemType;

/**
 * @author timp
 * @since 21 Dec 2006
 *
 */
public class NotNullableBinaryPoemTypeTest extends SQLPoemTypeTest {

  /**
   * 
   */
  public NotNullableBinaryPoemTypeTest() {
  }

  /**
   * @param name
   */
  public NotNullableBinaryPoemTypeTest(String name) {
    super(name);
  }

  /**
   * {@inheritDoc}
   * @see org.melati.poem.test.SQLPoemTypeTest#setObjectUnderTest()
   */
  void setObjectUnderTest() {
    it = new BinaryPoemType(false, 20);
  }

}
