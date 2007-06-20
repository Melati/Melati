/**
 * 
 */
package org.melati.poem.test;

import org.melati.poem.ReferencePoemType;

/**
 * @author timp
 * @since 21 Dec 2006
 *
 */
public class NullableReferencePoemTypeTest extends NotNullableReferencePoemTypeTest {

  /**
   * 
   */
  public NullableReferencePoemTypeTest() {
  }

  /**
   * @param name
   */
  public NullableReferencePoemTypeTest(String name) {
    super(name);
  }

  /**
   * {@inheritDoc}
   * @see org.melati.poem.test.SQLPoemTypeSpec#setObjectUnderTest()
   */
  void setObjectUnderTest() {
    it = new ReferencePoemType(getDb().getCapabilityTable(), true);
  }

}
