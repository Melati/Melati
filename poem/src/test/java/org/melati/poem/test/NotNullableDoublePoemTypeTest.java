/**
 * 
 */
package org.melati.poem.test;

import org.melati.poem.DoublePoemType;
import org.melati.poem.ParsingPoemException;
import org.melati.poem.SQLPoemType;

/**
 * @author timp
 * @since 21 Dec 2006
 *
 */
public class NotNullableDoublePoemTypeTest extends SQLPoemTypeSpec<Double> {

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
   * @see org.melati.poem.test.SQLPoemTypeSpec#setObjectUnderTest()
   */
  void setObjectUnderTest() {
    it = new DoublePoemType(false);
  }

  /**
   * Test method for {@link org.melati.poem.SQLType#quotedRaw(java.lang.Object)}.
   */
  public void testQuotedRaw() {
    assertEquals(((SQLPoemType<Double>)it).sqlDefaultValue(getDb().getDbms()) , 
        ((SQLPoemType<Double>)it).quotedRaw(((SQLPoemType<Double>)it).rawOfString(((SQLPoemType<Double>)it).sqlDefaultValue(getDb().getDbms()))));

  }

  public void testRawOfString() {
    super.testRawOfString();
    try{
      it.rawOfString("kk");
      fail("Should have blown up");
    } catch (ParsingPoemException e) {
      e = null;
    }
    
  }
  
}
