/**
 * 
 */
package org.melati.poem.test;

/**
 * @author timp
 * @since 21 Dec 2006
 *
 */
public class NonSQLPoemTypeTest extends SQLPoemTypeSpec<Object> {

  public NonSQLPoemTypeTest() {
  }

  public NonSQLPoemTypeTest(String name) {
    super(name);
  }

  void setObjectUnderTest() {
    it = new NonSQLPoemType();
  }

  /**
   * Test method for {@link org.melati.poem.SQLType#quotedRaw(java.lang.Object)}.
   */
  public void testQuotedRaw() {
  }
  
  /**
   * Test method for {@link org.melati.poem.PoemType#toDsdType()}.
   */
  public void testToDsdType() {
    assertNull(it.toDsdType()); 

  }

}
