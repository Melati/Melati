/**
 * 
 */
package org.melati.poem.test;

import java.util.Enumeration;

import org.melati.poem.BooleanPoemType;
import org.melati.poem.ParsingPoemException;
import org.melati.poem.SQLPoemType;

/**
 * @author timp
 * @since 21 Dec 2006
 *
 */
public class NotNullableBooleanPoemTypeTest extends SQLPoemTypeTest {

  /**
   * 
   */
  public NotNullableBooleanPoemTypeTest() {
  }

  /**
   * @param name
   */
  public NotNullableBooleanPoemTypeTest(String name) {
    super(name);
  }

  /**
   * {@inheritDoc}
   * @see org.melati.poem.test.SQLPoemTypeTest#setObjectUnderTest()
   */
  void setObjectUnderTest() {
    it = new BooleanPoemType(false);
  }

  /**
   * Test method for
   * {@link org.melati.poem.PoemType#rawOfString(java.lang.String)}.
   */
  public void testRawOfString() {
    super.testRawOfString();
    assertEquals(Boolean.TRUE,it.rawOfString("t")); 
    assertEquals(Boolean.TRUE,it.rawOfString("T")); 
    assertEquals(Boolean.TRUE,it.rawOfString("y")); 
    assertEquals(Boolean.TRUE,it.rawOfString("Y")); 
    assertEquals(Boolean.TRUE,it.rawOfString("1")); 
    assertEquals(Boolean.TRUE,it.rawOfString("true")); 
    assertEquals(Boolean.TRUE,it.rawOfString("yes")); 
    assertEquals(Boolean.FALSE,it.rawOfString("f")); 
    assertEquals(Boolean.FALSE,it.rawOfString("F")); 
    assertEquals(Boolean.FALSE,it.rawOfString("n")); 
    assertEquals(Boolean.FALSE,it.rawOfString("N")); 
    assertEquals(Boolean.FALSE,it.rawOfString("0")); 
    assertEquals(Boolean.FALSE,it.rawOfString("false")); 
    assertEquals(Boolean.FALSE,it.rawOfString("no")); 
    try{
      it.rawOfString("9");
      fail("Should have blown up");
    } catch (ParsingPoemException e) {
      e = null;
    }
    try{
      it.rawOfString("frue");
      fail("Should have blown up");
    } catch (ParsingPoemException e) {
      e = null;
    }
  }

  /**
   * Test method for {@link org.melati.poem.SQLType#quotedRaw(java.lang.Object)}.
   */
  public void testQuotedRaw() {
    assertEquals(((SQLPoemType)it).sqlDefaultValue(), 
        ((SQLPoemType)it).quotedRaw(((SQLPoemType)it).rawOfString(((SQLPoemType)it).sqlDefaultValue())));

  }

  public void testPossibleRaws() {
    super.testPossibleRaws();
    Enumeration them = it.possibleRaws();
    int counter = 0;
    while(them.hasMoreElements()) {
      them.nextElement();
      counter++;
    }
    if (it.getNullable())
      assertEquals(3,counter);
    else
      assertEquals(2,counter);
  }


}
