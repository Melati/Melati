/**
 * 
 */
package org.melati.poem.test;

import org.melati.poem.TroidPoemType;

/**
 * @author timp
 * @since 9 Jan 2007
 */
public class TroidPoemTypeTest extends NotNullableIntegerPoemTypeTest {

  /**
   * 
   */
  public TroidPoemTypeTest() {
  }

  /**
   * @param name
   */
  public TroidPoemTypeTest(String name) {
    super(name);
  }

  /**
   * {@inheritDoc}
   * @see org.melati.poem.test.SQLPoemTypeSpec#setObjectUnderTest()
   */
  void setObjectUnderTest() {
    it = new TroidPoemType();
  }

  public void testToDsdType() {
    assertEquals("Integer", it.toDsdType()); 
  }

}
