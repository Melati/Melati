package org.melati.poem.dbms.test;

import org.melati.poem.*;
import org.melati.poem.dbms.DbmsFactory;

/**
 * @author timp
 * @since 23 Jan 2007
 *
 */
public class OracleTest extends DbmsSpec {

  public OracleTest(String name) {
    super(name);
  }

  protected void setUp() throws Exception {
    super.setUp();
  }

  protected void tearDown() throws Exception {
    super.tearDown();
  }

  protected void setObjectUnderTest() {
    it = DbmsFactory.getDbms("org.melati.poem.dbms.Oracle");
  }

  /**
   * Test method for {@link org.melati.poem.dbms.Dbms#
   * getStringSqlDefinition(java.lang.String)}.
   */
  public void testGetStringSqlDefinition() throws Exception {
    assertEquals("VARCHAR(0)", it.getStringSqlDefinition(0));
    assertEquals("CLOB", it.getStringSqlDefinition(-1));
  }
  
  /**
   * Test method for {@link org.melati.poem.dbms.Dbms#
   * getSqlDefinition(java.lang.String)}.
   * @throws Exception 
   */
  public void testGetSqlDefinition() throws Exception {
    assertEquals("CHAR(1)", it.getSqlDefinition("BOOLEAN"));
    assertEquals("DOUBLE PRECISION", it.getSqlDefinition("DOUBLE PRECISION"));
    assertEquals("INT8", it.getSqlDefinition("INT8"));
    assertEquals("Big Decimal", it.getSqlDefinition("Big Decimal"));
  }

  /**
   * Test method for {@link org.melati.poem.dbms.Dbms#
   * getLongSqlDefinition()}.
   */
  public void testGetLongSqlDefinition() {
    assertEquals("NUMBER", it.getLongSqlDefinition());    
  }

  /**
   * Test method for {@link org.melati.poem.dbms.Dbms#
   * sqlBooleanValueOfRaw(java.lang.Object)}.
   */
  public void testSqlBooleanValueOfRaw() {
    assertEquals("0", it.sqlBooleanValueOfRaw(Boolean.FALSE));        
    assertEquals("1", it.sqlBooleanValueOfRaw(Boolean.TRUE));        
  }

  /**
   * Test method for {@link org.melati.poem.dbms.Dbms#
   * getBinarySqlDefinition(int)}.
   */
  public void testGetBinarySqlDefinition() throws Exception {
    assertEquals("BLOB", it.getBinarySqlDefinition(0));        
  }

  /**
   * Test method for {@link org.melati.poem.dbms.Dbms#
   * canDropColumns(java.sql.Connection)}.
   */
  public void testCanDropColumns() throws Exception {
    assertTrue(it.canDropColumns());
  }
  
  /**
   * Test method for {@link org.melati.poem.dbms.Dbms#melatiName(java.lang.String)}.
   */
  public void testMelatiName() {
    assertEquals("name", it.melatiName("name"));
    assertEquals(null, it.melatiName(null));
    assertEquals("~special", it.melatiName("~Special"));
  }

  /**
   * Test method for {@link org.melati.poem.dbms.Dbms#
   * unreservedName(java.lang.String)}.
   */
  public void testUnreservedName() {
    assertEquals("NAME", it.unreservedName("name"));    
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
            new StringPoemType(true, -1), new StringPoemType(true, -1)) 
            instanceof StringPoemType);

    assertNull(it.canRepresent(new TimestampPoemType(true), new DatePoemType(true))); 

    assertTrue(it.canRepresent(
            new BooleanPoemType(true), new BooleanPoemType(false)) 
            instanceof BooleanPoemType);

    assertNull(it.canRepresent(new DoublePoemType(false), new BigDecimalPoemType(true)));

    assertNull(it.canRepresent(new DoublePoemType(true), new BigDecimalPoemType(false))); 

    assertNull(it.canRepresent(new IntegerPoemType(false), new LongPoemType(true)));

    assertTrue(it.canRepresent(new IntegerPoemType(true), new LongPoemType(false))
            instanceof LongPoemType);
    assertTrue(it.canRepresent(new IntegerPoemType(true), new BigDecimalPoemType(false))
            instanceof BigDecimalPoemType);

    
    assertNull(it.canRepresent(new BinaryPoemType(false,10), new BinaryPoemType(true,10))); 
    assertNull(it.canRepresent(new BinaryPoemType(true,10), new BinaryPoemType(true,11))); 
    assertTrue(it.canRepresent(
            new BinaryPoemType(true,-1), 
            new BinaryPoemType(true,-1)) instanceof BinaryPoemType); 
    assertTrue(it.canRepresent(
            new BinaryPoemType(true,2500), 
            new BinaryPoemType(true,10)) instanceof BinaryPoemType); 
 
  
  }

  
  /**
   * Test method for {@link org.melati.poem.dbms.Dbms#
   * getForeignKeyDefinition(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)}.
   */
  public void testGetForeignKeyDefinition() {
    assertEquals(" ADD (CONSTRAINT FK_test_user) FOREIGN KEY (\"MELATI_USER\") REFERENCES " + 
            "\"MELATI_USER\"(\"ID\") ON DELETE CASCADE",
       it.getForeignKeyDefinition("test", "user", "user", "id", "delete"));
    assertEquals(" ADD (CONSTRAINT FK_test_user) FOREIGN KEY (\"MELATI_USER\") REFERENCES " + 
            "\"MELATI_USER\"(\"ID\") ON DELETE SET NULL",
       it.getForeignKeyDefinition("test", "user", "user", "id", "clear"));
  }

  /**
   * Test method for {@link org.melati.poem.dbms.Dbms#
   * getPrimaryKeyDefinition(java.lang.String)}.
   */
  public void testGetPrimaryKeyDefinition() {
    assertEquals(" ADD (CONSTRAINT PK_name PRIMARY KEY(\"NAME\"))", it.getPrimaryKeyDefinition("name"));
  }

}
