/**
 * 
 */
package org.melati.poem.test;

import java.text.DateFormat;
import java.util.Enumeration;

import org.melati.poem.IntegrityFixPoemType;
import org.melati.poem.SQLPoemType;
import org.melati.poem.StandardIntegrityFix;
import org.melati.poem.PoemLocale;

/**
 * @author timp
 * @since 21 Dec 2006
 *
 */
public class NotNullablelntegrityFixPoemTypeTest extends SQLPoemTypeSpec {

  /**
   * 
   */
  public NotNullablelntegrityFixPoemTypeTest() {
  }

  /**
   * @param name
   */
  public NotNullablelntegrityFixPoemTypeTest(String name) {
    super(name);
  }

  /**
   * {@inheritDoc}
   * @see org.melati.poem.test.SQLPoemTypeSpec#setObjectUnderTest()
   */
  void setObjectUnderTest() {
    it = new IntegrityFixPoemType(false);
  }


  public void testPossibleRaws() {
    super.testPossibleRaws();
    Enumeration<Object> them = it.possibleRaws();
    int count = 0;
    while(them.hasMoreElements()) {
      them.nextElement();
      count++;
    }
    assertEquals(3,count);
  }

  public void testRawOfCooked() {
    super.testRawOfCooked();
    assertEquals(StandardIntegrityFix.prevent.getIndex(), 
        it.rawOfCooked(StandardIntegrityFix.prevent));
  }

  public void testStringOfCooked() {
    super.testStringOfCooked();
    assertEquals("prevent", 
        it.stringOfCooked(StandardIntegrityFix.prevent, PoemLocale.HERE, DateFormat.MEDIUM));
  }

  /**
   * Test method for {@link org.melati.poem.SQLType#quotedRaw(java.lang.Object)}.
   */
  public void testQuotedRaw() {
    assertEquals(((SQLPoemType)it).sqlDefaultValue(getDb().getDbms()) , 
        ((SQLPoemType)it).quotedRaw(((SQLPoemType)it).rawOfString(
                ((SQLPoemType)it).sqlDefaultValue(getDb().getDbms()))));

  }
  
}
