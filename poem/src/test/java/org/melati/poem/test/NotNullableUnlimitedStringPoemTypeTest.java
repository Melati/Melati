package org.melati.poem.test;

import org.melati.poem.StringPoemType;

/**
 * @author timp
 * @since 21 Dec 2006
 *
 */
public class NotNullableUnlimitedStringPoemTypeTest extends SizedAtomPoemTypeSpec<String> {

  /**
   * 
   */
  public NotNullableUnlimitedStringPoemTypeTest() {
  }

  /**
   * @param name
   */
  public NotNullableUnlimitedStringPoemTypeTest(String name) {
    super(name);
  }

  /**
   * {@inheritDoc}
   * @see org.melati.poem.test.SQLPoemTypeSpec#setObjectUnderTest()
   */
  void setObjectUnderTest() {
    it = new StringPoemType(false, -1);
  }

}
