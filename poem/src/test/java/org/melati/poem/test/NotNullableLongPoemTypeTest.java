/**
 * 
 */
package org.melati.poem.test;

import org.melati.poem.LongPoemType;
import org.melati.poem.SQLPoemType;

/**
 * @author timp
 * @since 21 Dec 2006
 *
 */
public class NotNullableLongPoemTypeTest extends SQLPoemTypeTest {

  /**
   * 
   */
  public NotNullableLongPoemTypeTest() {
  }

  /**
   * @param name
   */
  public NotNullableLongPoemTypeTest(String name) {
    super(name);
  }

  /**
   * {@inheritDoc}
   * @see org.melati.poem.test.SQLPoemTypeTest#setObjectUnderTest()
   */
  void setObjectUnderTest() {
    it = new LongPoemType(false);
  }
  /**
   * Test method for {@link org.melati.poem.SQLType#quotedRaw(java.lang.Object)}.
   */
  public void testQuotedRaw() {
    assertEquals(((SQLPoemType)it).sqlDefaultValue(), 
        ((SQLPoemType)it).quotedRaw(((SQLPoemType)it).rawOfString(((SQLPoemType)it).sqlDefaultValue())));

  }

}
