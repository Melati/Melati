package org.melati.poem.test;

import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.DateFormat;

import org.melati.poem.ParsingPoemException;
import org.melati.poem.PoemLocale;
import org.melati.poem.SQLPoemType;
import org.melati.poem.TimePoemType;

/**
 * @author timp
 * @since 2011/06/11
 *
 */
public class NotNullableTimePoemTypeTest extends SQLPoemTypeSpec {

  public NotNullableTimePoemTypeTest() {
  }

  public NotNullableTimePoemTypeTest(String name) {
    super(name);
  }

  /**
   * {@inheritDoc}
   * @see org.melati.poem.test.SQLPoemTypeSpec#setObjectUnderTest()
   */
  void setObjectUnderTest() {
    it = new TimePoemType(false);
  }

  public void testStringOfCooked() {
    super.testStringOfCooked();
    long now = System.currentTimeMillis();
    Time nowT = new Time(now);
    assertEquals(20, it.stringOfCooked(nowT, PoemLocale.HERE, DateFormat.MEDIUM).length());
  }

  /**
   * Test method for {@link org.melati.poem.SQLType#quotedRaw(java.lang.Object)}.
   */
  public void testQuotedRaw() {
    long now = System.currentTimeMillis();
    Time nowT = new Time(now);
    assertEquals("'" + TimePoemType.format.format(nowT) + "'", 
        ((SQLPoemType)it).quotedRaw(nowT));

  }

  public void testAssertValidCooked() {
    super.testAssertValidCooked();
  }

  public void testPossibleRaws() {
    super.testPossibleRaws();
    
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

  public void testRawOfCooked() {
    super.testRawOfCooked();
  }

  /**
   * Test the full constructor. 
   */
  public void testFullConstructor() {
    TimePoemType it2 = new MyTimePoemType(it.getNullable());
    assertEquals(it.getNullable(),it2.getNullable());
  }
  class MyTimePoemType extends TimePoemType {

    /**
     * @param sqlTypeCode
     * @param sqlTypeName
     * @param nullable
     */
    public MyTimePoemType(boolean nullable) {
      super(Types.TIME, "TIME", nullable);
    }
    
  }
}
