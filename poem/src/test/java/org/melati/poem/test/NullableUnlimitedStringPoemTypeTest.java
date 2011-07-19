/**
 * 
 */
package org.melati.poem.test;

import org.melati.poem.StringPoemType;

/**
 * @author timp
 * @since 21 Dec 2006
 *
 */
public class NullableUnlimitedStringPoemTypeTest extends SizedAtomPoemTypeSpec<String> {

  /**
   * 
   */
  public NullableUnlimitedStringPoemTypeTest() {
  }

  /**
   * @param name
   */
  public NullableUnlimitedStringPoemTypeTest(String name) {
    super(name);
  }

  /**
   * {@inheritDoc}
   * @see org.melati.poem.test.SQLPoemTypeSpec#setObjectUnderTest()
   */
  void setObjectUnderTest() {
    it = new StringPoemType(true, -1);
  }

}
