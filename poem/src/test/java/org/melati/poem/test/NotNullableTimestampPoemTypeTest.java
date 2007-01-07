/**
 * 
 */
package org.melati.poem.test;

import java.sql.Timestamp;
import java.text.DateFormat;

import org.melati.poem.SQLPoemType;
import org.melati.poem.TimestampPoemType;
import org.melati.util.MelatiLocale;

/**
 * @author timp
 * @since 21 Dec 2006
 *
 */
public class NotNullableTimestampPoemTypeTest extends SQLPoemTypeTest {

  /**
   * 
   */
  public NotNullableTimestampPoemTypeTest() {
  }

  /**
   * @param name
   */
  public NotNullableTimestampPoemTypeTest(String name) {
    super(name);
  }

  /**
   * {@inheritDoc}
   * @see org.melati.poem.test.SQLPoemTypeTest#setObjectUnderTest()
   */
  void setObjectUnderTest() {
    it = new TimestampPoemType(false);
  }

  public void testStringOfCooked() {
    super.testStringOfCooked();
    long now = System.currentTimeMillis();
    Timestamp nowStamp = new Timestamp(now);
    // Hmm not a real test
    assertTrue(it.stringOfCooked(nowStamp, MelatiLocale.HERE, DateFormat.MEDIUM) instanceof String);
  }

  /**
   * Test method for {@link org.melati.poem.SQLType#quotedRaw(java.lang.Object)}.
   */
  public void testQuotedRaw() {
    long now = System.currentTimeMillis();
    Timestamp nowStamp = new Timestamp(now);
    assertEquals("'" + TimestampPoemType.format.format(nowStamp) + "'", 
        ((SQLPoemType)it).quotedRaw(nowStamp));

  }

  public void testAssertValidCooked() {
    // TODO Auto-generated method stub
    super.testAssertValidCooked();
  }

  public void testPossibleRaws() {
    super.testPossibleRaws();
    
  }

  public void testRawOfCooked() {
    // TODO Auto-generated method stub
    super.testRawOfCooked();
  }

}
