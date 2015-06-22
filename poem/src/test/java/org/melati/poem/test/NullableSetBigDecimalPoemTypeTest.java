package org.melati.poem.test;

import org.melati.poem.BigDecimalPoemType;
import org.melati.poem.DoublePoemType;

/**
 * @author timp
 * @since 21 Dec 2006
 *
 */
public class NullableSetBigDecimalPoemTypeTest extends NotNullableSetBigDecimalPoemTypeTest {

  public NullableSetBigDecimalPoemTypeTest() {
  }

  public NullableSetBigDecimalPoemTypeTest(String name) {
    super(name);
  }

  void setObjectUnderTest() {
    it = new BigDecimalPoemType(true, 22, 2);
  }

  /**
   * BigDecimals can represent Doubles.
   */
  public void testCanRepresentDoubles() { 
    assertNotNull(it.canRepresent(DoublePoemType.it));
    assertNotNull(it.canRepresent(new DoublePoemType(false)));
  }

}
