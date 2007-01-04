/**
 * 
 */
package org.melati.poem.test;

import org.melati.poem.DoublePoemType;

/**
 * @author timp
 * @since 21 Dec 2006
 *
 */
public class NullableDoublePoemTypeTest extends NotNullableDoublePoemTypeTest {

  /**
   * 
   */
  public NullableDoublePoemTypeTest() {
  }

  /**
   * @param name
   */
  public NullableDoublePoemTypeTest(String name) {
    super(name);
  }

  /**
   * {@inheritDoc}
   * @see org.melati.poem.test.SQLPoemTypeTest#setObjectUnderTest()
   */
  void setObjectUnderTest() {
    it = new DoublePoemType(true);
  }

}
