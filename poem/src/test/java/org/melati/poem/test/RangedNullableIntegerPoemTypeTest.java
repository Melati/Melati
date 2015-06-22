package org.melati.poem.test;

import org.melati.poem.IntegerPoemType;

/**
 * @author timp
 * @since 21 Dec 2006
 *
 */
public class RangedNullableIntegerPoemTypeTest extends NotNullableIntegerPoemTypeTest {

  public RangedNullableIntegerPoemTypeTest() {
  }

  public RangedNullableIntegerPoemTypeTest(String name) {
    super(name);
  }

  void setObjectUnderTest() {
    it = new IntegerPoemType(true);
  }

}
