/**
 * 
 */
package org.melati.poem.test;

import org.melati.poem.StringKeyReferencePoemType;

/**
 * @author timp
 * @since 2012-06-15
 *
 */
public class NullableStingKeyReferencePoemTypeTest extends NotNullableStringKeyReferencePoemTypeTest {

  /**
   * 
   */
  public NullableStingKeyReferencePoemTypeTest() {
  }

  /**
   * @param name
   */
  public NullableStingKeyReferencePoemTypeTest(String name) {
    super(name);
  }

  /**
   * {@inheritDoc}
   * @see org.melati.poem.test.SQLPoemTypeSpec#setObjectUnderTest()
   */
  void setObjectUnderTest() {
    it = new StringKeyReferencePoemType(
        getDb().getCapabilityTable(), "name", 
        true, 
        60);
  }

}
