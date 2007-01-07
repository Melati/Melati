/**
 * 
 */
package org.melati.poem.test;

import java.sql.Types;
import java.util.Enumeration;

import org.melati.poem.LongPoemType;
import org.melati.poem.ParsingPoemException;
import org.melati.poem.SQLPoemType;

/**
 * @author timp
 * @since 21 Dec 2006
 *
 */
public class NotNullableLongPoemTypeTest extends SQLPoemTypeTest {

  /**
   * 
   */
  public NotNullableLongPoemTypeTest() {
  }

  /**
   * @param name
   */
  public NotNullableLongPoemTypeTest(String name) {
    super(name);
  }

  /**
   * {@inheritDoc}
   * @see org.melati.poem.test.SQLPoemTypeTest#setObjectUnderTest()
   */
  void setObjectUnderTest() {
    it = new LongPoemType(false);
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
    assertNull(them);
    ((LongPoemType)it).setRawRange(new Long(Long.MAX_VALUE -5), (Long)null);
    them = it.possibleRaws();
    int count = 0;
    while(them.hasMoreElements()) {
      them.nextElement();
      count++;
    }
    if (it.getNullable())
      assertEquals(6,count);
    else 
      assertEquals(5,count);
    ((LongPoemType)it).setRawRange(new Long(2L), new Long(5L));
    them = it.possibleRaws();
    count = 0;
    while(them.hasMoreElements()) {
      them.nextElement();
      count++;
    }
    if (it.getNullable())
      assertEquals(4,count);
    else
      assertEquals(3,count);

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
  public void testFullConstructor() {
    LongPoemType it2 = new MyLongPoemType(it.getNullable());
    assertEquals(it.getNullable(),it2.getNullable());
  }
  class MyLongPoemType extends LongPoemType {

    /**
     * @param sqlTypeCode
     * @param sqlTypeName
     * @param nullable
     */
    public MyLongPoemType(boolean nullable) {
      super(Types.BIGINT, "INT8", nullable);
    }
    
  }

}
