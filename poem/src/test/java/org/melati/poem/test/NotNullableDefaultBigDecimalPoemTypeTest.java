/**
 * 
 */
package org.melati.poem.test;

import org.melati.poem.BigDecimalPoemType;

/**
 * @author timp
 * @since 21 Dec 2006
 *
 */
public class NotNullableDefaultBigDecimalPoemTypeTest extends SQLPoemTypeTest {

  /**
   * 
   */
  public NotNullableDefaultBigDecimalPoemTypeTest() {
  }

  /**
   * @param name
   */
  public NotNullableDefaultBigDecimalPoemTypeTest(String name) {
    super(name);
  }

  /**
   * {@inheritDoc}
   * @see org.melati.poem.test.SQLPoemTypeTest#setObjectUnderTest()
   */
  void setObjectUnderTest() {
    it = new BigDecimalPoemType(false);
  }

}
