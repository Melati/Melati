/**
 * 
 */
package org.melati.poem.test;

import java.util.Enumeration;

import org.melati.poem.ColumnTypePoemType;
import org.melati.poem.PoemTypeFactory;
import org.melati.poem.SQLPoemType;
import org.melati.poem.PoemLocale;

/**
 * @author timp
 * @since 21 Dec 2006
 *
 */
public class ColumnTypePoemTypeTest extends SQLPoemTypeTest {

  /**
   * 
   */
  public ColumnTypePoemTypeTest() {
  }

  /**
   * @param name
   */
  public ColumnTypePoemTypeTest(String name) {
    super(name);
  }

  /**
   * {@inheritDoc}
   * @see org.melati.poem.test.SQLPoemTypeTest#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
  }

  /**
   * {@inheritDoc}
   * @see org.melati.poem.test.SQLPoemTypeTest#tearDown()
   */
  protected void tearDown() throws Exception {
    super.tearDown();
  }

  /**
   * {@inheritDoc}
   * @see org.melati.poem.test.SQLPoemTypeTest#setObjectUnderTest()
   */
  void setObjectUnderTest() {
    it = new ColumnTypePoemType(getDb());
  }

  public void testPossibleRaws() {
    super.testPossibleRaws();
    Enumeration them = it.possibleRaws();
    int count = 0;
    Integer dummy = null;
    while(them.hasMoreElements()) {
      Integer type = (Integer)them.nextElement();
      dummy = type;
      type = dummy;
      count++;
      //System.err.println(type.intValue() + " : " + 
      //    PoemTypeFactory.forCode(getDb(),type.intValue()).getDisplayName());
    }
    assertEquals(25,count);
  }

  public void testRawOfCooked() {
    super.testRawOfCooked();
    assertEquals(new Integer(-1),it.rawOfCooked(PoemTypeFactory.TROID));
  }

  public void testCookedOfRaw() {
    super.testCookedOfRaw();
    assertEquals(PoemTypeFactory.TROID,it.cookedOfRaw(new Integer(-1)));
  }

  public void testStringOfCooked() {
    super.testStringOfCooked();
    assertEquals("BOOLEAN", it.stringOfCooked(PoemTypeFactory.BOOLEAN, PoemLocale.HERE, 0));
  }
  
  /**
   * Test method for {@link org.melati.poem.SQLType#quotedRaw(java.lang.Object)}.
   */
  public void testQuotedRaw() {
    assertEquals(((SQLPoemType)it).sqlDefaultValue(getDb().getDbms()), 
        ((SQLPoemType)it).quotedRaw(((SQLPoemType)it).rawOfString(
                ((SQLPoemType)it).sqlDefaultValue(getDb().getDbms()))));

  }

}
