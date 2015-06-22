package org.melati.poem.test;

import org.melati.poem.BinaryPoemType;

/**
 * @author timp
 * @since 21 Dec 2006
 *
 */
public class NullableBinaryPoemTypeTest extends NotNullableBinaryPoemTypeTest {

  public NullableBinaryPoemTypeTest() {
  }

  public NullableBinaryPoemTypeTest(String name) {
    super(name);
  }

  void setObjectUnderTest() {
    it = new BinaryPoemType(true, 20);
  }

  public void testToString() {
    assertEquals("nullable binary(20)",it.toString());
  }

}
