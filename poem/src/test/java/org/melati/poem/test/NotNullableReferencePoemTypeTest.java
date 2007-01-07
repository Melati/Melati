/**
 * 
 */
package org.melati.poem.test;


import java.util.Enumeration;

import org.melati.poem.Group;
import org.melati.poem.ReferencePoemType;
import org.melati.poem.SQLPoemType;
import org.melati.poem.ValidationPoemException;

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
  
  public void testAssertValidCooked() {
    // TODO Auto-generated method stub
    super.testAssertValidCooked();
    Group g = (Group)getDb().getGroupTable().firstSelection(null);
    try {
      it.assertValidCooked(g);
      fail("Should have blown up");
    } catch (ValidationPoemException e) { 
      e = null;
    }
  }

  public void testPossibleRaws() {
    super.testPossibleRaws();
    Enumeration them = it.possibleRaws();
    them = it.possibleRaws();
    int count = 0;
    while(them.hasMoreElements()) {
      them.nextElement();
      count++;
    }
    assertEquals(2,count);
  }

  public void testRawOfCooked() {
    super.testRawOfCooked();
    assertEquals(new Integer(1), it.rawOfCooked(getDb().administratorUser()));
    
  }

  /**
   * Test method for {@link org.melati.poem.PoemType#toDsdType()}.
   */
  public void testToDsdType() {
    assertEquals("User", it.toDsdType()); 

  }

  public void testBadConstructor() {
    try {
      new ReferencePoemType(null, false);
      fail("Should have blown up");
    } catch (NullPointerException e) {
      e = null;
    }
  }
}
