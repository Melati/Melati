/**
 * 
 */
package org.melati.poem.test;


import java.math.BigDecimal;

import org.melati.poem.BigDecimalPoemType;
import org.melati.poem.ParsingPoemException;
import org.melati.poem.SQLPoemType;
import org.melati.poem.SQLSeriousPoemException;

/**
 * @author timp
 * @since 21 Dec 2006
 *
 */
public class NotNullableDefaultBigDecimalPoemTypeTest extends SQLPoemTypeSpec<BigDecimal> {

  /**
   * 
   */
  public NotNullableDefaultBigDecimalPoemTypeTest() {
  }

  /**
   * @param name
   */
  public NotNullableDefaultBigDecimalPoemTypeTest(String name) {
    super(name);
  }

  /**
   * {@inheritDoc}
   * @see org.melati.poem.test.SQLPoemTypeSpec#setObjectUnderTest()
   */
  void setObjectUnderTest() {
    it = new BigDecimalPoemType(false);
  }

  public void testRawOfString() {
    super.testRawOfString();
    try {
      it.rawOfString("ggg");
      fail("Should have blown up");
    } catch (ParsingPoemException e) {
      e = null;
    }
   
  }

  /**
   * Test method for
   * {@link org.melati.poem.SQLType#sqlTypeDefinition(org.melati.poem.dbms.Dbms)}.
   */
  public void testSqlTypeDefinition() {
    super.testSqlDefinition();
    BigDecimalPoemType it2 = new BigDecimalPoemType(true);
    it2.setPrecision(-999);
    it2.setScale(-999);
    try {
      it2.sqlTypeDefinition(getDb().getDbms());
      fail("Should have blown up");
    } catch (SQLSeriousPoemException e) {
      e = null;
    }
  }
 
  /**
   * Test method for {@link org.melati.poem.SQLType#quotedRaw(java.lang.Object)}.
   */
  public void testQuotedRaw() {
    assertEquals(((SQLPoemType<?>)it).sqlDefaultValue(getDb().getDbms()), 
        ((SQLPoemType<?>)it).quotedRaw(((SQLPoemType<?>)it).rawOfString(
                ((SQLPoemType<?>)it).sqlDefaultValue(getDb().getDbms()))));

  }

}
