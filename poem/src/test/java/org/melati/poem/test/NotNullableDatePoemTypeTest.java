/**
 * 
 */
package org.melati.poem.test;

import org.melati.poem.DatePoemType;
import org.melati.poem.SQLPoemType;

/**
 * @author timp
 * @since 21 Dec 2006
 *
 */
public class NotNullableDatePoemTypeTest extends SQLPoemTypeTest {

  /**
   * 
   */
  public NotNullableDatePoemTypeTest() {
  }

  /**
   * @param name
   */
  public NotNullableDatePoemTypeTest(String name) {
    super(name);
  }

  /**
   * {@inheritDoc}
   * @see org.melati.poem.test.SQLPoemTypeTest#setObjectUnderTest()
   */
  void setObjectUnderTest() {
    it = new DatePoemType(false);
  }

  /**
   * Test method for {@link org.melati.poem.SQLType#quotedRaw(java.lang.Object)}.
   */
  public void testQuotedRaw() {
    assertEquals(((SQLPoemType)it).sqlDefaultValue(), 
        ((SQLPoemType)it).quotedRaw(((SQLPoemType)it).rawOfString(((SQLPoemType)it).sqlDefaultValue())));

  }

}
