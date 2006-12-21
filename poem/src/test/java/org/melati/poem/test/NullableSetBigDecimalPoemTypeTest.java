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
public class NullableSetBigDecimalPoemTypeTest extends SQLPoemTypeTest {

  /**
   * 
   */
  public NullableSetBigDecimalPoemTypeTest() {
  }

  /**
   * @param name
   */
  public NullableSetBigDecimalPoemTypeTest(String name) {
    super(name);
  }

  /**
   * {@inheritDoc}
   * @see org.melati.poem.test.SQLPoemTypeTest#setObjectUnderTest()
   */
  void setObjectUnderTest() {
    it = new BigDecimalPoemType(true, 22, 2);
  }

}
