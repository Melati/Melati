/**
 * 
 */
package org.melati.poem.test;

import java.text.DateFormat;
import java.util.Enumeration;

import org.melati.poem.DisplayLevelPoemType;
import org.melati.poem.Searchability;
import org.melati.poem.SearchabilityPoemType;
import org.melati.poem.Searchability.NameUnrecognisedException;
import org.melati.poem.PoemLocale;

/**
 * @author timp
 * @since 7 Jan 2007
 *
 */
public class SearchabilityPoemTypeTest extends NotNullableIntegerPoemTypeTest {

  /**
   * 
   */
  public SearchabilityPoemTypeTest() {
  }

  /**
   * @param name
   */
  public SearchabilityPoemTypeTest(String name) {
    super(name);
  }

  /**
   * {@inheritDoc}
   * @see org.melati.poem.test.SQLPoemTypeSpec#setObjectUnderTest()
   */
  void setObjectUnderTest() {
    it = new SearchabilityPoemType();
  }

  /**
   * Test {@link org.melati.poem.Searchability#toString()}.
   */
  public void testToString() {
    assertEquals("searchability (INT (org.melati.poem.SearchabilityPoemType))", it.toString());
  }

  public void testRawOfCooked() {
    super.testRawOfCooked();
    assertEquals(Searchability.primary.getIndex(), 
        it.rawOfCooked(Searchability.primary));
  }

  public void testPossibleRaws() {
    Enumeration<Object> them = it.possibleRaws();
    int count = 0;
    while(them.hasMoreElements()) {
      them.nextElement();
      count++;
    }
    assertEquals(3,count);
  }
  
  public void testStringOfCooked() {
    super.testStringOfCooked();
    assertEquals("primary", 
        it.stringOfCooked(Searchability.primary, PoemLocale.HERE, DateFormat.MEDIUM));
  }

  public void testCanRepresent() {
    assertNull(it.canRepresent(new DisplayLevelPoemType()));
  }
  
  /**
   * Test {@link org.melati.poem.Searchability#named(String)}.
   */
  public void testNamed() {
    assertEquals(Searchability.primary, Searchability.named("primary"));
    try {
      Searchability.named("kk");
      fail("should have blown up");
    } catch (NameUnrecognisedException e) {
      e = null;
    }
  }
}
