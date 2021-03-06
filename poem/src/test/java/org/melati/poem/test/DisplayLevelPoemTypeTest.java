/**
 * 
 */
package org.melati.poem.test;

import java.text.DateFormat;
import java.util.Enumeration;

import org.melati.poem.DisplayLevel;
import org.melati.poem.DisplayLevelPoemType;
import org.melati.poem.PoemLocale;

/**
 * @author timp
 * @since 6 Jan 2007
 */
public class DisplayLevelPoemTypeTest extends NotNullableIntegerPoemTypeTest {

  /**
   * 
   */
  public DisplayLevelPoemTypeTest() {
  }

  public DisplayLevelPoemTypeTest(String name) {
    super(name);
  }

  void setObjectUnderTest() {
    it = new DisplayLevelPoemType();
  }

  /**
   * Test toString.
   */
  public void testToString() {
    assertEquals("display level (INT (org.melati.poem.DisplayLevelPoemType))", it.toString());
  }

  public void testRawOfCooked() {
    super.testRawOfCooked();
    assertEquals(DisplayLevel.primary.getIndex(), 
        it.rawOfCooked(DisplayLevel.primary));
  }

  public void testPossibleRaws() {
    Enumeration<Integer> them = it.possibleRaws();
    int count = 0;
    while(them.hasMoreElements()) {
      them.nextElement();
      count++;
    }
    assertEquals(5,count);      
  }
  
  public void testStringOfCooked() {
    super.testStringOfCooked();
    assertEquals("primary", 
        it.stringOfCooked(DisplayLevel.primary, PoemLocale.HERE, DateFormat.MEDIUM));
  }
}
