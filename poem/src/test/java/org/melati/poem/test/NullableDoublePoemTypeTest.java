package org.melati.poem.test;

import org.melati.poem.DoublePoemType;

/**
 * @author timp
 * @since 21 Dec 2006
 *
 */
public class NullableDoublePoemTypeTest extends NotNullableDoublePoemTypeTest {

  public NullableDoublePoemTypeTest() {
  }

  public NullableDoublePoemTypeTest(String name) {
    super(name);
  }

  void setObjectUnderTest() {
    it = new DoublePoemType(true);
  }

}
