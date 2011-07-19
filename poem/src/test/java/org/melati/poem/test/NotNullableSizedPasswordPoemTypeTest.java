/**
 * 
 */
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

  /**
   * @param name
   */
  public NotNullableSizedPasswordPoemTypeTest(String name) {
    super(name);
  }

  /**
   * {@inheritDoc}
   * @see org.melati.poem.test.SQLPoemTypeSpec#setObjectUnderTest()
   */
  void setObjectUnderTest() {
    it = new PasswordPoemType(false, 20);
  }

}
