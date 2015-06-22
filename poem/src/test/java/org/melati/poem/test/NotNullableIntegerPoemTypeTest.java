package org.melati.poem.test;

import java.sql.Types;
import java.util.Enumeration;

import org.melati.poem.DisplayLevelPoemType;
import org.melati.poem.IntegerPoemType;
import org.melati.poem.ParsingPoemException;
import org.melati.poem.SQLPoemType;
import org.melati.poem.TypeMismatchPoemException;

/**
 * @author timp
 * @since 21 Dec 2006
 *
 */
public class NotNullableIntegerPoemTypeTest extends SQLPoemTypeSpec<Integer> {

  public NotNullableIntegerPoemTypeTest() {
  }

  public NotNullableIntegerPoemTypeTest(String name) {
    super(name);
  }

  void setObjectUnderTest() {
    it = new IntegerPoemType(false);
  }

  /**
   * Test method for {@link org.melati.poem.PoemType#canRepresent(org.melati.poem.PoemType)}.
   */
  public void testCanRepresent() {
    assertTrue(it.canRepresent(new DisplayLevelPoemType()) instanceof IntegerPoemType);
  }

  /**
   * Only way to get doubleChecked to throw. 
   * {@inheritDoc}
   * @see org.melati.poem.test.SQLPoemTypeSpec#testRawOfCooked()
   */
  public void testRawOfCooked() {
    super.testRawOfCooked();
    try { 
      it.rawOfCooked(new Long(0));
      fail("should have blown up");
    } catch (TypeMismatchPoemException e) { 
      e = null;
    }
  }

  /**
   * Test method for {@link org.melati.poem.SQLType#quotedRaw(java.lang.Object)}.
   */
  public void testQuotedRaw() {
    assertEquals(((SQLPoemType<Integer>)it).sqlDefaultValue(getDb().getDbms()) , 
        ((SQLPoemType<Integer>)it).quotedRaw(((SQLPoemType<Integer>)it).rawOfString(
                ((SQLPoemType<Integer>)it).sqlDefaultValue(getDb().getDbms()))));

  }

  public void testPossibleRaws() {
    super.testPossibleRaws();
    Enumeration<Integer> them = ((IntegerPoemType)it).possibleRaws();
    if (!it.getNullable())
      assertNull(them);
    ((IntegerPoemType)it).setRawRange(new Integer(Integer.MAX_VALUE -5), (Integer)null);
    them = it.possibleRaws();
    int counter = 0;
    while(them.hasMoreElements()) {
      them.nextElement();
      counter++;
    }
    if (it.getNullable())
      assertEquals(6,counter);
    else
      assertEquals(5,counter);
    ((IntegerPoemType)it).setRawRange(new Integer(2), new Integer(5));
    them = it.possibleRaws();
    counter = 0;
    while(them.hasMoreElements()) {
      them.nextElement();
      counter++;
    }
    if (it.getNullable())
      assertEquals(4,counter);
    else
      assertEquals(3,counter);
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
   * Test full constructor.
   */
  public void testFullConstructor() {
    IntegerPoemType it2 = new MyIntegerPoemType(it.getNullable());
    assertEquals(it.getNullable(),it2.getNullable());
  }
  class MyIntegerPoemType extends IntegerPoemType {

    public MyIntegerPoemType(boolean nullable) {
      super(Types.INTEGER, "INT", nullable);
    }
    
  }
}
