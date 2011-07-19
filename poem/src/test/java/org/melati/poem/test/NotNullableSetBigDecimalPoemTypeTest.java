/**
 * 
 */
package org.melati.poem.test;

import java.math.BigDecimal;

import org.melati.poem.BigDecimalPoemType;
import org.melati.poem.DoublePoemType;
import org.melati.poem.SQLPoemType;

/**
 * @author timp
 * @since 21 Dec 2006
 *
 */
public class NotNullableSetBigDecimalPoemTypeTest extends SQLPoemTypeSpec<BigDecimal> {

  /**
   * 
   */
  public NotNullableSetBigDecimalPoemTypeTest() {
  }

  /**
   * @param name
   */
  public NotNullableSetBigDecimalPoemTypeTest(String name) {
    super(name);
  }

  /**
   * {@inheritDoc}
   * @see org.melati.poem.test.SQLPoemTypeSpec#setObjectUnderTest()
   */
  void setObjectUnderTest() {
    it = new BigDecimalPoemType(false, 22, 2);
  }

  /**
   * Test get total size. 
   */
  public void testGetTotalSize() {
    assertEquals(24, ((BigDecimalPoemType)it).getTotalSize());
  }
  
  /**
   * Test full constructor. 
   */
  public void testWithPrecisionAndScale() {
    BigDecimalPoemType itAlso = (BigDecimalPoemType)it;
    assertEquals(it, itAlso.withPrecisionAndScale(itAlso.getPrecision(),itAlso.getScale()));
    BigDecimalPoemType itClone = (BigDecimalPoemType) itAlso.withPrecisionAndScale(10,itAlso.getScale());
    assertEquals(10, itClone.getPrecision());
  }
  /**
   * Test method for {@link org.melati.poem.SQLType#quotedRaw(java.lang.Object)}.
   */
  public void testQuotedRaw() {
    assertEquals(((SQLPoemType<BigDecimal>)it).sqlDefaultValue(getDb().getDbms()) , 
        ((SQLPoemType<BigDecimal>)it).quotedRaw(((SQLPoemType<BigDecimal>)it).rawOfString(
                ((SQLPoemType<BigDecimal>)it).sqlDefaultValue(getDb().getDbms()))));

  }

  /**
   * BigDecimals can represent Doubles.
   */
  public void testCanRepresentDoubles() { 
    assertNull(it.canRepresent(DoublePoemType.it));
    assertNotNull(it.canRepresent(new DoublePoemType(false)));
  }
  
}
