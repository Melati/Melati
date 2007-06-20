/**
 * 
 */
package org.melati.poem.test;

import org.melati.poem.IntegerPoemType;

/**
 * @author timp
 * @since 21 Dec 2006
 *
 */
public class RangedNullableIntegerPoemTypeTest extends NotNullableIntegerPoemTypeTest {

  /**
   * 
   */
  public RangedNullableIntegerPoemTypeTest() {
  }

  /**
   * @param name
   */
  public RangedNullableIntegerPoemTypeTest(String name) {
    super(name);
  }

  /**
   * {@inheritDoc}
   * @see org.melati.poem.test.SQLPoemTypeSpec#setObjectUnderTest()
   */
  void setObjectUnderTest() {
    it = new IntegerPoemType(true);
  }

}
