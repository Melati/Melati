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

  public NullableDefaultBigDecimalPoemTypeTest(String name) {
    super(name);
  }

  void setObjectUnderTest() {
    it = new BigDecimalPoemType(true);
  }

}
