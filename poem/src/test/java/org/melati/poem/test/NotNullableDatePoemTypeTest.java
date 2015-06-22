/**
 * 
 */
package org.melati.poem.test;

import java.sql.Date;
import java.sql.Types;
import java.text.DateFormat;

import org.melati.poem.DatePoemType;
import org.melati.poem.SQLPoemType;
import org.melati.poem.PoemLocale;

/**
 * @author timp
 * @since 21 Dec 2006
 *
 */
public class NotNullableDatePoemTypeTest extends SQLPoemTypeSpec<Date> {

  /**
   * 
   */
  public NotNullableDatePoemTypeTest() {
  }

  public NotNullableDatePoemTypeTest(String name) {
    super(name);
  }

  void setObjectUnderTest() {
    it = new DatePoemType(false);
  }

  /**
   * Test full constructor.
   */
  public void testFullConstructor() {
    DatePoemType it2 = new DatePoemType(Types.DATE, "DATETIME", it.getNullable());
    assertNotNull(it.canRepresent(it2));
    assertNotNull(it2.canRepresent(it2));
  }
  
  /**
   * Test method for {@link org.melati.poem.SQLType#quotedRaw(java.lang.Object)}.
   */
  public void testQuotedRaw() {
    assertEquals(((SQLPoemType<Date>)it).sqlDefaultValue(getDb().getDbms()), 
        ((SQLPoemType<Date>)it).quotedRaw(((SQLPoemType<Date>)it).rawOfString(
                ((SQLPoemType<Date>)it).sqlDefaultValue(getDb().getDbms()))));

  }

  public void testStringOfCooked() {
    super.testStringOfCooked();
    assertEquals("01-Jan-1970", it.stringOfCooked(new Date(1L), 
        PoemLocale.HERE, DateFormat.MEDIUM));
  }
  public void testToDsdType() {

    DatePoemType t = new DatePoemType(true);

    assertTrue(t.toDsdType().equals("Date"));

  }

  public void testRawOfString() {

    DatePoemType t = new DatePoemType(true);
    Date d1 = (Date) t.rawOfString("12122001");
    Date d2 = (Date) t.rawOfString("2001-12-12");
    assertTrue(d1.equals(d2));
    assertTrue(!d1.equals(null));
    assertTrue(!d2.equals(null));
    Date d3 = (Date) t.rawOfString(null);
    assertTrue(d3 == null);

    try {
      t.rawOfString("");
      fail("Should throw IllegalArgumentException for empty string");
    } catch (IllegalArgumentException success) {
      success = null; // shut PMD up
    }

    try {

      t.rawOfString("1999-2-2");
      fail("Should throw IllegalArgumentException for malformed date 1999-2-2");
    } catch (IllegalArgumentException success) {
      success = null; // shut PMD up
    }

  }

  /**
   * Test toString.
   */
  public void testToString() {

    DatePoemType t = new DatePoemType(true);
    assertTrue(t.toString().equals("nullable DATE (org.melati.poem.DatePoemType)"));
    DatePoemType t2 = new DatePoemType(false);
    assertTrue(t2.toString().equals("DATE (org.melati.poem.DatePoemType)"));

  }

  /**
   * Test equals.
   */
  public void testEquals() {

    DatePoemType t = new DatePoemType(true);
    assertTrue(t.rawOfString("2001-12-12").equals(t.rawOfString("2001-12-12")));
    assertTrue(t.rawOfString("12122001").equals(t.rawOfString("2001-12-12")));
    assertTrue(t.rawOfString("12122001").equals(t.rawOfString("2001-12-12")));
    assertTrue(t.rawOfString("02022001").equals(t.rawOfString("2001-02-02")));
    assertTrue(t.rawOfString("02022001").equals(t.rawOfString("2001-02-02")));

  }

}
