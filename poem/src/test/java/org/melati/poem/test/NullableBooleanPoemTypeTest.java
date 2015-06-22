package org.melati.poem.test;

import org.melati.poem.BooleanPoemType;

/**
 * @author timp
 * @since 21 Dec 2006
 *
 */
public class NullableBooleanPoemTypeTest extends NotNullableBooleanPoemTypeTest {

  public NullableBooleanPoemTypeTest() {
  }

  public NullableBooleanPoemTypeTest(String name) {
    super(name);
  }

  void setObjectUnderTest() {
    it = new BooleanPoemType(true);
  }

}
