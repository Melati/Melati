/**
 * 
 */
package org.melati.poem.test;

import org.melati.poem.SQLPoemType;

/**
 * @author timp
 * @since 21 Dec 2006
 *
 */
public class SqlExceptionPoemTypeTest extends SQLPoemTypeTest {

  /**
   * 
   */
  public SqlExceptionPoemTypeTest() {
  }

  /**
   * @param name
   */
  public SqlExceptionPoemTypeTest(String name) {
    super(name);
  }

  /**
   * {@inheritDoc}
   * @see org.melati.poem.test.SQLPoemTypeTest#setObjectUnderTest()
   */
  void setObjectUnderTest() {
    it = new SqlExceptionPoemType(false);
  }
  
  /**
   * Test method for {@link org.melati.poem.PoemType#toDsdType()}.
   */
  public void testToDsdType() {
    assertNull(it.toDsdType()); 

  }
  /**
   * Test method for {@link org.melati.poem.SQLType#quotedRaw(java.lang.Object)}.
   */
  public void testQuotedRaw() {
    assertEquals(((SQLPoemType)it).sqlDefaultValue(getDb().getDbms()) , 
        ((SQLPoemType)it).quotedRaw(((SQLPoemType)it).rawOfString(((SQLPoemType)it).sqlDefaultValue(getDb().getDbms()))));

  }

}
