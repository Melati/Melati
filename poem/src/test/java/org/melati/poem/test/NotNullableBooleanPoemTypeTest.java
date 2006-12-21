/**
 * 
 */
package org.melati.poem.test;

import org.melati.poem.BooleanPoemType;

/**
 * @author timp
 * @since 21 Dec 2006
 *
 */
public class NotNullableBooleanPoemTypeTest extends SQLPoemTypeTest {

  /**
   * 
   */
  public NotNullableBooleanPoemTypeTest() {
  }

  /**
   * @param name
   */
  public NotNullableBooleanPoemTypeTest(String name) {
    super(name);
  }

  /**
   * {@inheritDoc}
   * @see org.melati.poem.test.SQLPoemTypeTest#setObjectUnderTest()
   */
  void setObjectUnderTest() {
    it = new BooleanPoemType(false);
  }

}
