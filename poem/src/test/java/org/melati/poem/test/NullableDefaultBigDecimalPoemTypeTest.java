package org.melati.poem.test;

import org.melati.poem.BigDecimalPoemType;

/**
 * @author timp
 * @since 21 Dec 2006
 *
 */
public class NullableDefaultBigDecimalPoemTypeTest extends NotNullableDefaultBigDecimalPoemTypeTest {

  public NullableDefaultBigDecimalPoemTypeTest() {
  }

  /**
   * @param name
   */
  public NullableDefaultBigDecimalPoemTypeTest(String name) {
    super(name);
  }

  /**
   * {@inheritDoc}
   * @see org.melati.poem.test.SQLPoemTypeSpec#setObjectUnderTest()
   */
  void setObjectUnderTest() {
    it = new BigDecimalPoemType(true);
  }

}
