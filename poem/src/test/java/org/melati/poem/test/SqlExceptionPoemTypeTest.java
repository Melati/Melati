package org.melati.poem.test;

import org.melati.poem.SQLPoemType;

/**
 * @author timp
 * @since 21 Dec 2006
 *
 */
public class SqlExceptionPoemTypeTest extends SQLPoemTypeSpec<Integer> {

  public SqlExceptionPoemTypeTest() {
  }

  public SqlExceptionPoemTypeTest(String name) {
    super(name);
  }

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
    assertEquals(((SQLPoemType<Integer>)it).sqlDefaultValue(getDb().getDbms()) , 
        ((SQLPoemType<Integer>)it).quotedRaw(((SQLPoemType<Integer>)it).rawOfString(((SQLPoemType<Integer>)it).sqlDefaultValue(getDb().getDbms()))));

  }

}
