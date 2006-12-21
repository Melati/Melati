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
public class NotNullableTimestampPoemTypeTest extends SQLPoemTypeTest {

  /**
   * 
   */
  public NotNullableTimestampPoemTypeTest() {
  }

  /**
   * @param name
   */
  public NotNullableTimestampPoemTypeTest(String name) {
    super(name);
  }

  /**
   * {@inheritDoc}
   * @see org.melati.poem.test.SQLPoemTypeTest#setObjectUnderTest()
   */
  void setObjectUnderTest() {
    it = new TimestampPoemType(false);
  }

}
