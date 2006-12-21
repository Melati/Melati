/**
 * 
 */
package org.melati.poem.test;

import org.melati.poem.LongPoemType;

/**
 * @author timp
 * @since 21 Dec 2006
 *
 */
public class NotNullableLongPoemTypeTest extends SQLPoemTypeTest {

  /**
   * 
   */
  public NotNullableLongPoemTypeTest() {
  }

  /**
   * @param name
   */
  public NotNullableLongPoemTypeTest(String name) {
    super(name);
  }

  /**
   * {@inheritDoc}
   * @see org.melati.poem.test.SQLPoemTypeTest#setObjectUnderTest()
   */
  void setObjectUnderTest() {
    it = new LongPoemType(false);
  }

}
