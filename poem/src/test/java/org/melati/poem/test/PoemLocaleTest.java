/**
 * 
 */
package org.melati.poem.test;

import java.text.DateFormat;
import java.util.Locale;

import org.melati.poem.PoemLocale;

import junit.framework.TestCase;

/**
 * @author timp
 * @since 5 Feb 2007
 *
 */
public class PoemLocaleTest extends TestCase {

  public PoemLocaleTest(String name) {
    super(name);
  }

  protected void setUp() throws Exception {
    super.setUp();
  }

  protected void tearDown() throws Exception {
    super.tearDown();
  }

  /**
   * Test method for {@link org.melati.poem.PoemLocale#hashCode()}.
   */
  public void testHashCode() {
    PoemLocale l1 = PoemLocale.fromLanguageTag("en-gb");
    assertEquals(PoemLocale.HERE, l1);
    assertEquals(PoemLocale.HERE.hashCode(),l1.hashCode());
  }

  /**
   * Test method for {@link org.melati.poem.PoemLocale#fromLanguageTag(java.lang.String)}.
   */
  public void testFromLanguageTag() {
    assertEquals(PoemLocale.HERE, PoemLocale.fromLanguageTag("en-gb"));
    assertEquals("en_AU", PoemLocale.fromLanguageTag("en-au").toString());
    assertEquals("en", PoemLocale.fromLanguageTag("en-").toString());
    assertEquals("en", PoemLocale.fromLanguageTag("en-a").toString());
    assertEquals("en", PoemLocale.fromLanguageTag("en-abc").toString());
    assertEquals("en", PoemLocale.fromLanguageTag("en-abc-fr").toString());
    assertEquals("en_AU", PoemLocale.fromLanguageTag("en-au-fr").toString());

    assertNull(PoemLocale.fromLanguageTag(""));
    
  }

  /**
   * Test method for {@link org.melati.poem.PoemLocale#PoemLocale(java.util.Locale)}.
   */
  public void testPoemLocale() {
    try {
      new PoemLocale(null);
      fail("Should have blown up.");
    } catch (NullPointerException e) { 
      e = null;
    }
  }

  /**
   * Test method for {@link org.melati.poem.PoemLocale#locale()}.
   */
  public void testLocale() {
    assertEquals("en_GB",PoemLocale.HERE.locale().toString());
  }

  /**
   * Test method for {@link org.melati.poem.PoemLocale#monthName(int)}.
   */
  public void testMonthName() {
    assertEquals("January", PoemLocale.HERE.monthName(1));
  }

  /**
   * Test method for {@link org.melati.poem.PoemLocale#shortMonthName(int)}.
   */
  public void testShortMonthName() {
    assertEquals("Jan", PoemLocale.HERE.shortMonthName(1));
  }

  /**
   * Test method for {@link org.melati.poem.PoemLocale#dateFormat(int)}.
   */
  public void testDateFormat() {
    assertEquals( DateFormat.getDateInstance(DateFormat.SHORT, 
                                             PoemLocale.HERE.locale()).hashCode(), 
    PoemLocale.HERE.dateFormat(3).hashCode());    
  }

  /**
   * Test method for {@link org.melati.poem.PoemLocale#timestampFormat(int)}.
   */
  public void testTimestampFormat() {
    
  }

  /**
   * Test method for {@link org.melati.poem.PoemLocale#equals(java.lang.Object)}.
   */
  public void testEqualsObject() {
    assertEquals(PoemLocale.HERE,PoemLocale.HERE);
    assertEquals(PoemLocale.HERE,new PoemLocale(new Locale("en","GB")));
    assertTrue(PoemLocale.HERE.equals(new PoemLocale(new Locale("en","GB"))));
    assertFalse(PoemLocale.HERE.equals(new Exception()));
  }

  /**
   * Test method for {@link org.melati.poem.PoemLocale#toString()}.
   */
  public void testToString() {
    assertEquals("en_GB",PoemLocale.HERE.toString());    
  }

}
