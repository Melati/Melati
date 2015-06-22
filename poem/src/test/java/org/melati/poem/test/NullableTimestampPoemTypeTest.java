package org.melati.poem.test;

import org.melati.poem.TimestampPoemType;

/**
 * @author timp
 * @since 21 Dec 2006
 *
 */
public class NullableTimestampPoemTypeTest extends NotNullableTimestampPoemTypeTest {

  public NullableTimestampPoemTypeTest() {
  }

  public NullableTimestampPoemTypeTest(String name) {
    super(name);
  }

  void setObjectUnderTest() {
    it = new TimestampPoemType(true);
  }

}
