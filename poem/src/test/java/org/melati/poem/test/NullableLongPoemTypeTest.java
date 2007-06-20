/**
 * 
 */
package org.melati.poem.test;

import org.melati.poem.LongPoemType;

/**
 * @author timp
 * @since 21 Dec 2006
 *
 */
public class NullableLongPoemTypeTest extends NotNullableLongPoemTypeTest {

  /**
   * 
   */
  public NullableLongPoemTypeTest() {
  }

  /**
   * @param name
   */
  public NullableLongPoemTypeTest(String name) {
    super(name);
  }

  /**
   * {@inheritDoc}
   * @see org.melati.poem.test.SQLPoemTypeSpec#setObjectUnderTest()
   */
  void setObjectUnderTest() {
    it = new LongPoemType(true);
  }

}
