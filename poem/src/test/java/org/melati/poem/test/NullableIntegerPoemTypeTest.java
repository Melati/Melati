/**
 * 
 */
package org.melati.poem.test;

import org.melati.poem.DisplayLevelPoemType;
import org.melati.poem.IntegerPoemType;

/**
 * @author timp
 * @since 21 Dec 2006
 *
 */
public class NullableIntegerPoemTypeTest extends SQLPoemTypeTest {

  /**
   * 
   */
  public NullableIntegerPoemTypeTest() {
  }

  /**
   * @param name
   */
  public NullableIntegerPoemTypeTest(String name) {
    super(name);
  }

  /**
   * {@inheritDoc}
   * @see org.melati.poem.test.SQLPoemTypeTest#setObjectUnderTest()
   */
  void setObjectUnderTest() {
    it = new IntegerPoemType(true);
  }

  /**
   * Test method for
   * {@link org.melati.poem.PoemType#canRepresent(org.melati.poem.PoemType)}.
   */
  public void testCanRepresent() {
    assertTrue(it.canRepresent(new DisplayLevelPoemType()) instanceof IntegerPoemType);
  }
}
