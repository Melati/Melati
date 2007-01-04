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
public class NullableDatePoemTypeTest extends NotNullableDatePoemTypeTest {

  /**
   * 
   */
  public NullableDatePoemTypeTest() {
  }

  /**
   * @param name
   */
  public NullableDatePoemTypeTest(String name) {
    super(name);
  }

  /**
   * {@inheritDoc}
   * @see org.melati.poem.test.SQLPoemTypeTest#setObjectUnderTest()
   */
  void setObjectUnderTest() {
    it = new DatePoemType(true);
  }

}
