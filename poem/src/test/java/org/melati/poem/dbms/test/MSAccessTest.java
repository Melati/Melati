/**
 * 
 */
package org.melati.poem.dbms.test;

import java.sql.SQLException;

import org.melati.poem.BigDecimalPoemType;
import org.melati.poem.BooleanPoemType;
import org.melati.poem.DatePoemType;
import org.melati.poem.DoublePoemType;
import org.melati.poem.IntegerPoemType;
import org.melati.poem.LongPoemType;
import org.melati.poem.StringPoemType;
import org.melati.poem.TimestampPoemType;
import org.melati.poem.dbms.DbmsFactory;
import org.melati.poem.dbms.MSAccess;

/**
 * @author timp
 * @since 23 Jan 2007
 *
 */
public class MSAccessTest extends DbmsSpec {

  /**
   * Constructor.
   * @param name
   */
  public MSAccessTest(String name) {
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
    it = DbmsFactory.getDbms("org.melati.poem.dbms.MSAccess");
  }

  /** 
   * {@inheritDoc}
   * @see org.melati.poem.dbms.test.DbmsSpec#testCanDropColumns()
   */
  public void testCanDropColumns() throws Exception {
    assertFalse(it.canDropColumns());
  }

  /**
   * Test method for {@link org.melati.poem.dbms.Dbms#
   * getStringSqlDefinition(java.lang.String)}.
   */
  public void testGetStringSqlDefinition() throws Exception {
    assertEquals("VARCHAR(250)",  it.getStringSqlDefinition(-1));
    assertEquals("VARCHAR(0)",  it.getStringSqlDefinition(0));
  }

  /**
   * Test method for {@link org.melati.poem.dbms.Dbms#
   * getSqlDefinition(java.lang.String)}.
   * @throws Exception 
   */
  public void testGetSqlDefinition() throws Exception {
    assertEquals("BIT", it.getSqlDefinition("BOOLEAN"));
    assertEquals("DOUBLE", it.getSqlDefinition("DOUBLE PRECISION"));
    assertEquals("INTEGER", it.getSqlDefinition("INT8"));
    assertEquals("NUMERIC", it.getSqlDefinition("Big Decimal"));
    assertEquals("INTEGER", it.getSqlDefinition("INTEGER"));
  }

  
  /**
   * Test method for {@link org.melati.poem.dbms.Dbms#
   * getLongSqlDefinition()}.
   */
  public void testGetLongSqlDefinition() {
    assertEquals("INTEGER", it.getLongSqlDefinition());    
  }

  /**
   * Test method for {@link org.melati.poem.dbms.Dbms#
   * getBinarySqlDefinition(int)}.
   */
  public void testGetBinarySqlDefinition() throws Exception {
    assertEquals("BINARY(0)", it.getBinarySqlDefinition(0));
    assertEquals("BINARY", it.getBinarySqlDefinition(-1));
  }
  /**
   * Test method for {@link org.melati.poem.dbms.Dbms#melatiName(java.lang.String)}.
   */
  public void testMelatiName() {
    assertEquals("name", it.melatiName("name"));
    assertNull(it.melatiName(null));
    assertNull(it.melatiName("~MSAccess special"));
  }

  /**
   * Test method for {@link org.melati.poem.dbms.Dbms#
   * getFixedPtSqlDefinition(int, int)}.
   * @throws Exception 
   */
  public void testGetFixedPtSqlDefinition() throws Exception {
    assertEquals("NUMERIC", it.getFixedPtSqlDefinition(22, 2));
    try { 
      it.getFixedPtSqlDefinition(-1, 2);
      fail("Should have blown up");
    } catch (SQLException e) { 
      e = null;
    }
    try { 
      it.getFixedPtSqlDefinition(22, -1);
      fail("Should have blown up");
    } catch (SQLException e) { 
      e = null;
    }
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
            new StringPoemType(true, MSAccess.msAccessTextHack), new StringPoemType(true, -1)) 
            instanceof StringPoemType);
    assertTrue(it.canRepresent(
            new StringPoemType(true, MSAccess.msAccessMemoSize), new StringPoemType(true, -1)) 
            instanceof StringPoemType);
    assertTrue(it.canRepresent(
            new StringPoemType(true, -1), new StringPoemType(true, -1)) 
            instanceof StringPoemType);

    assertTrue(it.canRepresent(
            new TimestampPoemType(true), new DatePoemType(true)) 
            instanceof DatePoemType);

    assertTrue(it.canRepresent(
            new BooleanPoemType(true), new BooleanPoemType(false)) 
            instanceof BooleanPoemType);

    assertNull(it.canRepresent(new DoublePoemType(false), new BigDecimalPoemType(true)));

    assertTrue(it.canRepresent(
            new DoublePoemType(true), new BigDecimalPoemType(false)) 
            instanceof BigDecimalPoemType);

    assertNull(it.canRepresent(new IntegerPoemType(false), new LongPoemType(true)));

    assertTrue(it.canRepresent(
            new IntegerPoemType(true), new LongPoemType(false)) 
            instanceof LongPoemType);

    
    
  }


  /**
   * Test method for {@link org.melati.poem.dbms.Dbms#
   * caseInsensitiveRegExpSQL(java.lang.String, java.lang.String)}.
   */
  public void testCaseInsensitiveRegExpSQL() {
    String expected = "a LIKE '%b%'";
    String actual = it.caseInsensitiveRegExpSQL("a", "b");
    assertEquals(expected, actual);
  }

  public void testCaseInsensitiveRegExpSQLQuoted() {
    String expected = "a LIKE '%b%'";
    String actual = it.caseInsensitiveRegExpSQL("a", "\"b\"");
    assertEquals(expected, actual);
  }

  public void testCaseInsensitiveRegExpSQLBlank() {
    String expected = " LIKE '%%'";
    String actual = it.caseInsensitiveRegExpSQL("", "");
    assertEquals(expected, actual);
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
   *    selectLimit(java.lang.String, int)}.
   */
  public void testSelectLimit() {
    assertEquals("SELECT TOP 1 * FROM \"USER\"", it.selectLimit("* FROM \"USER\"", 1));
  }

  
  /**
   * Test method for {@link org.melati.poem.dbms.Dbms#
   * getForeignKeyDefinition(java.lang.String, java.lang.String, 
   *                         java.lang.String, java.lang.String, 
   *                         java.lang.String)}.
   */
  public void testGetForeignKeyDefinition() {
    assertEquals(" ADD FOREIGN KEY (\"MELATI_USER\") REFERENCES \"MELATI_USER\"(\"id\") ON DELETE RESTRICT",
            it.getForeignKeyDefinition("test", "user", "user", "id", "prevent"));
    assertEquals(" ADD FOREIGN KEY (\"MELATI_USER\") REFERENCES \"MELATI_USER\"(\"id\") ON DELETE SET NULL",
            it.getForeignKeyDefinition("test", "user", "user", "id", "clear"));
    assertEquals(" ADD FOREIGN KEY (\"MELATI_USER\") REFERENCES \"MELATI_USER\"(\"id\") ON DELETE CASCADE",
            it.getForeignKeyDefinition("test", "user", "user", "id", "delete"));
  }


  
}
