/**
 * 
 */
package org.melati.poem.test;

import org.melati.poem.DoublePoemType;
import org.melati.poem.SQLPoemType;

/**
 * @author timp
 * @since 21 Dec 2006
 *
 */
public class NotNullableDoublePoemTypeTest extends SQLPoemTypeTest {

  /**
   * 
   */
  public NotNullableDoublePoemTypeTest() {
  }

  /**
   * @param name
   */
  public NotNullableDoublePoemTypeTest(String name) {
    super(name);
  }

  /**
   * {@inheritDoc}
   * @see org.melati.poem.test.SQLPoemTypeTest#setObjectUnderTest()
   */
  void setObjectUnderTest() {
    it = new DoublePoemType(false);
  }

  /**
   * Test method for {@link org.melati.poem.SQLType#quotedRaw(java.lang.Object)}.
   */
  public void testQuotedRaw() {
    assertEquals(((SQLPoemType)it).sqlDefaultValue() , 
        ((SQLPoemType)it).quotedRaw(((SQLPoemType)it).rawOfString(((SQLPoemType)it).sqlDefaultValue())));

  }
}
