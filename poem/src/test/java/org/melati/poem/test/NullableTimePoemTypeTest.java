package org.melati.poem.test;

import org.melati.poem.TimePoemType;

/**
 * @author timp
 * @since 2011/06/11
 *
 */
public class NullableTimePoemTypeTest extends NotNullableTimePoemTypeTest {

  public NullableTimePoemTypeTest() {
  }

  public NullableTimePoemTypeTest(String name) {
    super(name);
  }

  /**
   * {@inheritDoc}
   * @see org.melati.poem.test.SQLPoemTypeSpec#setObjectUnderTest()
   */
  void setObjectUnderTest() {
    it = new TimePoemType(true);
  }

}
