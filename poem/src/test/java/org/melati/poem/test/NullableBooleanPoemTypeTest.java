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
public class NullableBooleanPoemTypeTest extends SQLPoemTypeTest {

  /**
   * 
   */
  public NullableBooleanPoemTypeTest() {
  }

  /**
   * @param name
   */
  public NullableBooleanPoemTypeTest(String name) {
    super(name);
  }

  /**
   * {@inheritDoc}
   * @see org.melati.poem.test.SQLPoemTypeTest#setObjectUnderTest()
   */
  void setObjectUnderTest() {
    it = new BooleanPoemType(true);
  }

}
