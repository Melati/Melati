/**
 * 
 */
package org.melati.poem.test;

import org.melati.poem.BigDecimalPoemType;
import org.melati.poem.DoublePoemType;
import org.melati.poem.FixedPointAtomPoemType;
import org.melati.poem.SQLPoemType;

/**
 * @author timp
 * @since 21 Dec 2006
 *
 */
public class NotNullableSetBigDecimalPoemTypeTest extends SQLPoemTypeSpec {

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
    assertEquals(24, ((FixedPointAtomPoemType)it).getTotalSize());
  }
  
  /**
   * Test full constructor. 
   */
  public void testWithPrecisionAndScale() {
    FixedPointAtomPoemType itAlso = (FixedPointAtomPoemType)it;
    assertEquals(it, itAlso.withPrecisionAndScale(itAlso.getPrecision(),itAlso.getScale()));
    FixedPointAtomPoemType itClone = itAlso.withPrecisionAndScale(10,itAlso.getScale());
    assertEquals(10, itClone.getPrecision());
  }
  /**
   * Test method for {@link org.melati.poem.SQLType#quotedRaw(java.lang.Object)}.
   */
  public void testQuotedRaw() {
    assertEquals(((SQLPoemType)it).sqlDefaultValue(getDb().getDbms()) , 
        ((SQLPoemType)it).quotedRaw(((SQLPoemType)it).rawOfString(
                ((SQLPoemType)it).sqlDefaultValue(getDb().getDbms()))));

  }

  /**
   * BigDecimals can represent Doubles.
   */
  public void testCanRepresentDoubles() { 
    assertNull(it.canRepresent(DoublePoemType.it));
    assertNotNull(it.canRepresent(new DoublePoemType(false)));
  }
  
}
