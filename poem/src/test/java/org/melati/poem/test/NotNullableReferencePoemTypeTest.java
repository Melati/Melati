/**
 * 
 */
package org.melati.poem.test;

import org.melati.poem.ReferencePoemType;
import org.melati.poem.SQLPoemType;

/**
 * @author timp
 * @since 21 Dec 2006
 *
 */
public class NotNullableReferencePoemTypeTest extends SQLPoemTypeTest {

  /**
   * 
   */
  public NotNullableReferencePoemTypeTest() {
  }

  /**
   * @param name
   */
  public NotNullableReferencePoemTypeTest(String name) {
    super(name);
  }

  /**
   * {@inheritDoc}
   * @see org.melati.poem.test.SQLPoemTypeTest#setObjectUnderTest()
   */
  void setObjectUnderTest() {
    it = new ReferencePoemType(getDb().getUserTable(), false);
  }
  /**
   * Test method for {@link org.melati.poem.SQLType#quotedRaw(java.lang.Object)}.
   */
  public void testQuotedRaw() {
    assertEquals(((SQLPoemType)it).sqlDefaultValue(), 
        ((SQLPoemType)it).quotedRaw(((SQLPoemType)it).rawOfString(((SQLPoemType)it).sqlDefaultValue())));

  }
  /**
   * Test method for {@link org.melati.poem.PoemType#toDsdType()}.
   */
  public void testToDsdType() {
    assertEquals("User", it.toDsdType()); 

  }

}
