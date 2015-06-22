package org.melati.poem.test;

import org.melati.poem.DatePoemType;

/**
 * @author timp
 * @since 21 Dec 2006
 *
 */
public class NullableDatePoemTypeTest extends NotNullableDatePoemTypeTest {

  public NullableDatePoemTypeTest() {
  }

  public NullableDatePoemTypeTest(String name) {
    super(name);
  }

  void setObjectUnderTest() {
    it = new DatePoemType(true);
  }

}
