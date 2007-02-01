/**
 * 
 */
package org.melati.poem.dbms.test;

import org.melati.poem.BigDecimalPoemType;
import org.melati.poem.BinaryPoemType;
import org.melati.poem.BooleanPoemType;
import org.melati.poem.DatePoemType;
import org.melati.poem.DoublePoemType;
import org.melati.poem.IntegerPoemType;
import org.melati.poem.LongPoemType;
import org.melati.poem.StringPoemType;
import org.melati.poem.TimestampPoemType;
import org.melati.poem.dbms.DbmsFactory;
import org.melati.poem.dbms.Mimer;

/**
 * @author timp
 * @since 23 Jan 2007
 *
 */
public class MimerTest extends DbmsSpec {

  /**
   * Constructor.
   * @param name
   */
  public MimerTest(String name) {
    super(name);
  }

  /**
   * {@inheritDoc}
   * @see org.melati.poem.dbms.test.DbmsSpec#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
  }

  /**
   * {@inheritDoc}
   * @see org.melati.poem.dbms.test.DbmsSpec#tearDown()
   */
  protected void tearDown() throws Exception {
    super.tearDown();
  }

  protected void setObjectUnderTest() {
    it = DbmsFactory.getDbms("org.melati.poem.dbms.Mimer");
  }

  /**
   * Test method for {@link org.melati.poem.dbms.Dbms#
   * getStringSqlDefinition(java.lang.String)}.
   */
  public void testGetStringSqlDefinition() throws Exception {
    assertEquals("VARCHAR(2500)",  it.getStringSqlDefinition(-1));
    assertEquals("VARCHAR(0)",  it.getStringSqlDefinition(0));
  }

  /**
   * Test method for {@link org.melati.poem.dbms.Dbms#
   * getSqlDefinition(java.lang.String)}.
   * @throws Exception 
   */
  public void testGetSqlDefinition() throws Exception {
    assertEquals("INT", it.getSqlDefinition("BOOLEAN"));
    assertEquals("DOUBLE PRECISION", it.getSqlDefinition("DOUBLE PRECISION"));
    assertEquals("INT8", it.getSqlDefinition("INT8"));
    assertEquals("Big Decimal", it.getSqlDefinition("Big Decimal"));
  }

  /**
   * Test method for {@link org.melati.poem.dbms.Dbms#
   * canRepresent(org.melati.poem.PoemType, org.melati.poem.PoemType)}.
   */
  public void testCanRepresent() {
    assertNull(it.canRepresent(StringPoemType.nullableInstance, IntegerPoemType.nullableInstance));
    assertNull(it.canRepresent(IntegerPoemType.nullableInstance,StringPoemType.nullableInstance));

    assertNull(it.canRepresent(new BigDecimalPoemType(false),new BigDecimalPoemType(true)));
    assertTrue(it.canRepresent(new BigDecimalPoemType(true),new BigDecimalPoemType(false))
               instanceof BigDecimalPoemType);

    assertNull(it.canRepresent(new StringPoemType(true, 255), new StringPoemType(true, -1)));

    assertTrue(it.canRepresent(
            new StringPoemType(true, Mimer.mimerTextHack), new StringPoemType(true, -1)) 
            instanceof StringPoemType);
    assertTrue(it.canRepresent(
            new StringPoemType(true, -1), new StringPoemType(true, -1)) 
            instanceof StringPoemType);

    assertNull(it.canRepresent(new TimestampPoemType(true), new DatePoemType(true))); 

    assertTrue(it.canRepresent(
            new BooleanPoemType(true), new BooleanPoemType(false)) 
            instanceof BooleanPoemType);

    assertNull(it.canRepresent(new DoublePoemType(false), new BigDecimalPoemType(true)));

    assertNull(it.canRepresent(new DoublePoemType(true), new BigDecimalPoemType(false))); 

    assertNull(it.canRepresent(new IntegerPoemType(false), new LongPoemType(true)));

    assertNull(it.canRepresent(new IntegerPoemType(true), new LongPoemType(false))); 

    
    assertNull(it.canRepresent(new BinaryPoemType(false,10), new BinaryPoemType(true,10))); 
    assertNull(it.canRepresent(new BinaryPoemType(true,10), new BinaryPoemType(true,11))); 
    assertTrue(it.canRepresent(
            new BinaryPoemType(true,-1), 
            new BinaryPoemType(true,-1)) instanceof BinaryPoemType); 
    assertTrue(it.canRepresent(
            new BinaryPoemType(true,2500), 
            new BinaryPoemType(true,10)) instanceof BinaryPoemType); 
 
    assertTrue(it.canRepresent(
            new IntegerPoemType(true), 
            new BooleanPoemType(true)) instanceof BooleanPoemType); 
     

  
  }


  
}
