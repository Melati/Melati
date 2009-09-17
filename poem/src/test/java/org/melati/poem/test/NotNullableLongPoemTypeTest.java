/**
 * 
 */
package org.melati.poem.test;

import java.sql.Types;
import java.util.Enumeration;

import org.melati.poem.DisplayLevelPoemType;
import org.melati.poem.LongPoemType;
import org.melati.poem.TroidPoemType;
import org.melati.poem.ParsingPoemException;
import org.melati.poem.SQLPoemType;

/**
 * @author timp
 * @since 21 Dec 2006
 *
 */
public class NotNullableLongPoemTypeTest extends SQLPoemTypeSpec {

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
   * @see org.melati.poem.test.SQLPoemTypeSpec#setObjectUnderTest()
   */
  void setObjectUnderTest() {
    it = new LongPoemType(false);
  }
  
  /**
   * Test method for {@link org.melati.poem.SQLType#quotedRaw(java.lang.Object)}.
   */
  public void testQuotedRaw() {
    assertEquals(((SQLPoemType)it).sqlDefaultValue(getDb().getDbms()), 
        ((SQLPoemType)it).quotedRaw(((SQLPoemType)it).rawOfString(
                ((SQLPoemType)it).sqlDefaultValue(getDb().getDbms()))));

  }

  @SuppressWarnings("unchecked")
  public void testPossibleRaws() {
    super.testPossibleRaws();
    Enumeration<Object> them = it.possibleRaws();
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
  
  /**
   * Test method for
   * {@link org.melati.poem.PoemType#canRepresent(org.melati.poem.PoemType)}.
   */
  public void testCanRepresent() {
    DisplayLevelPoemType dl = new DisplayLevelPoemType();
    assertNotNull(it.canRepresent(dl)); // We can represent an Integer
    assertNull(dl.canRepresent(it));

  }

  
  /**
   * Longs can represent Integers and troids.
   */
  public void testCanRepresentTroid() { 
    assertNotNull(it.canRepresent(TroidPoemType.it));
  }
  /**
   * Test full constructor.
   */
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
