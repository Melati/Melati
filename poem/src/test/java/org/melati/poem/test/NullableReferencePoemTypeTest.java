package org.melati.poem.test;

import org.melati.poem.ReferencePoemType;

/**
 * @author timp
 * @since 21 Dec 2006
 *
 */
public class NullableReferencePoemTypeTest extends NotNullableReferencePoemTypeTest {

  public NullableReferencePoemTypeTest() {
  }

  public NullableReferencePoemTypeTest(String name) {
    super(name);
  }

  void setObjectUnderTest() {
    it = new ReferencePoemType(getDb().getCapabilityTable(), true);
  }

}
