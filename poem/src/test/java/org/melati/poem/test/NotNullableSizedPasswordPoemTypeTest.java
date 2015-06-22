package org.melati.poem.test;

import org.melati.poem.PasswordPoemType;

/**
 * @author timp
 * @since 7 Jan 2007
 *
 */
public class NotNullableSizedPasswordPoemTypeTest extends SizedAtomPoemTypeSpec<String> {

  /**
   * 
   */
  public NotNullableSizedPasswordPoemTypeTest() {
  }

  public NotNullableSizedPasswordPoemTypeTest(String name) {
    super(name);
  }

  void setObjectUnderTest() {
    it = new PasswordPoemType(false, 20);
  }

}
