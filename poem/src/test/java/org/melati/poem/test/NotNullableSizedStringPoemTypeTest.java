package org.melati.poem.test;

import org.melati.poem.StringLengthValidationPoemException;
import org.melati.poem.StringPoemType;

/**
 * @author timp
 * @since 21 Dec 2006
 *
 */
public class NotNullableSizedStringPoemTypeTest extends SizedAtomPoemTypeSpec<String> {

  public NotNullableSizedStringPoemTypeTest() {
  }

  public NotNullableSizedStringPoemTypeTest(String name) {
    super(name);
  }

  void setObjectUnderTest() {
    it = new StringPoemType(false, 20);
  }

  public void testAssertValidRaw() {
    super.testAssertValidRaw();
    try {
      it.assertValidRaw("123456789012345678901");
      fail("Should have blown up");
    } catch (StringLengthValidationPoemException e) { 
      e = null;
    }
  }

}
