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

  public NullableIntegerPoemTypeTest() {
  }

  public NullableIntegerPoemTypeTest(String name) {
    super(name);
  }

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
    RangedIntegerPoemType(boolean nullableP, Integer low, Integer limit) {
      super(nullableP);
      setRawRange(low, limit);
    }

    public String sqlDefaultValue(Dbms Any) {
      return "2";
    }
    
  }
}
