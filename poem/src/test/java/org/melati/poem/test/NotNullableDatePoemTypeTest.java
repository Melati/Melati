/**
 * 
 */
package org.melati.poem.test;

import org.melati.poem.DatePoemType;

/**
 * @author timp
 * @since 21 Dec 2006
 *
 */
public class NotNullableDatePoemTypeTest extends SQLPoemTypeTest {

  /**
   * 
   */
  public NotNullableDatePoemTypeTest() {
  }

  /**
   * @param name
   */
  public NotNullableDatePoemTypeTest(String name) {
    super(name);
  }

  /**
   * {@inheritDoc}
   * @see org.melati.poem.test.SQLPoemTypeTest#setObjectUnderTest()
   */
  void setObjectUnderTest() {
    it = new DatePoemType(false);
  }

}
