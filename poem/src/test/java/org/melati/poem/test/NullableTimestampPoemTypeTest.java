/**
 * 
 */
package org.melati.poem.test;

import org.melati.poem.TimestampPoemType;

/**
 * @author timp
 * @since 21 Dec 2006
 *
 */
public class NullableTimestampPoemTypeTest extends SQLPoemTypeTest {

  /**
   * 
   */
  public NullableTimestampPoemTypeTest() {
  }

  /**
   * @param name
   */
  public NullableTimestampPoemTypeTest(String name) {
    super(name);
  }

  /**
   * {@inheritDoc}
   * @see org.melati.poem.test.SQLPoemTypeTest#setObjectUnderTest()
   */
  void setObjectUnderTest() {
    it = new TimestampPoemType(true);
  }

}
