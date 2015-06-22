package org.melati.poem.test;

import org.melati.poem.StringKeyReferencePoemType;

/**
 * @author timp
 * @since 2012-06-15
 *
 */
public class NullableStingKeyReferencePoemTypeTest extends NotNullableStringKeyReferencePoemTypeTest {

  public NullableStingKeyReferencePoemTypeTest() {
  }

  public NullableStingKeyReferencePoemTypeTest(String name) {
    super(name);
  }

  void setObjectUnderTest() {
    it = new StringKeyReferencePoemType(
        getDb().getCapabilityTable(), "name", 
        true, 
        60);
  }

}
