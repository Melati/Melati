/**
 * 
 */
package org.melati.poem.test;

import org.melati.poem.IntegerPoemType;
import org.melati.poem.ValidationPoemException;
import org.melati.poem.dbms.Dbms;

/**
 * @author timp
 * @since 21 Dec 2006
 *
 */
public class NullableIntegerPoemTypeTest extends NotNullableIntegerPoemTypeTest {

  /**
   * 
   */
  public NullableIntegerPoemTypeTest() {
  }

  /**
   * @param name
   */
  public NullableIntegerPoemTypeTest(String name) {
    super(name);
  }

  /**
   * {@inheritDoc}
   * @see org.melati.poem.test.SQLPoemTypeSpec#setObjectUnderTest()
   */
  void setObjectUnderTest() {
    it = new RangedIntegerPoemType(true, new Integer(2), new Integer(13));
  }
 
  
  public void testToDsdType() {
  }


  public void testAssertValidRaw() {
    super.testAssertValidRaw();
    try {
      it.assertValidRaw(new Integer(0));
      fail("Should have blown up");
    } catch (ValidationPoemException e) {
      e = null;
    }
    try {
      it.assertValidRaw(new Integer(23));
      fail("Should have blown up");
    } catch (ValidationPoemException e) {
      e = null;
    }
  }



  class RangedIntegerPoemType extends IntegerPoemType {
    /**
     * @param nullableP
     * @param low
     * @param limit
     */
    RangedIntegerPoemType(boolean nullableP, Integer low, Integer limit) {
      super(nullableP);
      setRawRange(low, limit);
    }

    /**
     * {@inheritDoc}
     * @see org.melati.poem.IntegerPoemType#sqlDefaultValue()
     */
    public String sqlDefaultValue(Dbms Any) {
      return "2";
    }
    
  }
}
