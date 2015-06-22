package org.melati.poem.test;

import org.melati.poem.StringPoemType;

/**
 * @author timp
 * @since 21 Dec 2006
 *
 */
public class NullableUnlimitedStringPoemTypeTest extends SizedAtomPoemTypeSpec<String> {

  public NullableUnlimitedStringPoemTypeTest() {
  }

  public NullableUnlimitedStringPoemTypeTest(String name) {
    super(name);
  }

  void setObjectUnderTest() {
    it = new StringPoemType(true, -1);
  }

}
