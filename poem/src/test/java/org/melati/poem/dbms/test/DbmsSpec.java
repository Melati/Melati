/**
 * 
 */
package org.melati.poem.dbms.test;

import java.sql.Connection;
import java.sql.SQLException;

import org.melati.poem.BigDecimalPoemType;
import org.melati.poem.DoublePoemType;
import org.melati.poem.IntegerPoemType;
import org.melati.poem.LongPoemType;
import org.melati.poem.PoemThread;
import org.melati.poem.StringPoemType;
import org.melati.poem.dbms.Dbms;
import org.melati.poem.test.PoemTestCase;

/**
 * Abstract test for a class implementing the Dbms interface.
 * 
 * @author timp
 * @since 23 Jan 2007
 *
 */
public abstract class DbmsSpec extends PoemTestCase {

  protected Dbms it = null;

  /**
   * Constructor.
   * @param name
   */
  public DbmsSpec(String name) {
    super(name);
  }

  /**
   * {@inheritDoc}
   * @see junit.framework.TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
    setObjectUnderTest();
  }

  /**
   * {@inheritDoc}
   * @see junit.framework.TestCase#tearDown()
   */
  protected void tearDown() throws Exception {
    super.tearDown();
    it.unloadDriver();
  }

  protected abstract void setObjectUnderTest();

  /**
   * FIXME Skipped for Postgres
   * Test method for {@link org.melati.poem.dbms.Dbms#
   * getConnection(java.lang.String, java.lang.String, java.lang.String)}.
   * @throws Exception 
   */
  public void testGetConnection() throws Exception {
    Connection c = PoemThread.transaction().getDatabase().getCommittedConnection();
    
    if (c.getClass().getName().indexOf("postgresql") == -1) {
      //System.err.println(c.getTransactionIsolation() + ">=" + Connection.TRANSACTION_READ_COMMITTED);
      assertTrue(c.getTransactionIsolation() + " is not >= " + Connection.TRANSACTION_READ_COMMITTED + 
              " for database " + PoemThread.transaction().getDatabase() + 
              " using " + PoemThread.transaction().getDatabase().getDbms() + 
              " for connection " + c.getClass().getName(),
              c.getTransactionIsolation() >= Connection.TRANSACTION_READ_COMMITTED);
    }
  }

  /**
   * Test method for {@link org.melati.poem.dbms.Dbms#getSchema()}.
   */
  public void testGetSchema() {
    assertNull(it.getSchema());
  }

  /**
   * Test method for {@link org.melati.poem.dbms.Dbms#
   * shutdown(java.sql.Connection)}.
   */
  public void testShutdown() {
    
  }

  /**
   * Test method for {@link org.melati.poem.dbms.Dbms#
   * getQuotedName(java.lang.String)}.
   */
  public void testGetQuotedName() {
    
  }

  /**
   * Test method for {@link org.melati.poem.dbms.Dbms#
   * getQuotedValue(org.melati.poem.SQLType, String)} .
   */
  public void testGetQuotedValue() {
    
  }

  /**
   * Test method for {@link org.melati.poem.dbms.Dbms#
   * getJdbcMetadataName(java.lang.String)}.
   */
  public void testGetJdbcMetadataName() {
    assertEquals("name",it.getJdbcMetadataName("name"));
  }

  /**
   * Test method for {@link org.melati.poem.dbms.Dbms#
   * preparedStatementPlaceholder(org.melati.poem.PoemType)}.
   */
  public void testPreparedStatementPlaceholder() {
    assertEquals("?", it.preparedStatementPlaceholder(new IntegerPoemType(true)));
    assertEquals("?", it.preparedStatementPlaceholder(new LongPoemType(true)));
    assertEquals("?", it.preparedStatementPlaceholder(new DoublePoemType(true)));
    assertEquals("?", it.preparedStatementPlaceholder(new StringPoemType(true, -1)));
  }

  /**
   * Test method for {@link org.melati.poem.dbms.Dbms#createTableSql()}.
   */
  public void testCreateTableSql() {
    assertEquals("CREATE TABLE ", it.createTableSql());
  }

  /**
   * Test method for {@link org.melati.poem.dbms.Dbms#createTableOptionsSql()}.
   */
  public void testCreateTableOptionsSql() {
    assertEquals("", it.createTableOptionsSql());
  }

  /**
   * Test method for {@link org.melati.poem.dbms.Dbms#
   * getSqlDefinition(java.lang.String)}.
   * @throws Exception 
   */
  public void testGetSqlDefinition() throws Exception {
    assertEquals("BOOLEAN", it.getSqlDefinition("BOOLEAN"));
    assertEquals("DOUBLE PRECISION", it.getSqlDefinition("DOUBLE PRECISION"));
    assertEquals("INT8", it.getSqlDefinition("INT8"));
    assertEquals("INT", it.getSqlDefinition("INT"));
    assertEquals("Big Decimal", it.getSqlDefinition("Big Decimal"));
    assertEquals("STRING", it.getSqlDefinition("STRING"));
  }

  /**
   * Test method for {@link org.melati.poem.dbms.Dbms#getStringSqlDefinition(int)}.
   */
  public void testGetStringSqlDefinition() throws Exception {
    assertEquals("VARCHAR(0)", it.getStringSqlDefinition(0));    
    try {
      it.getStringSqlDefinition(-1);
      fail("Should have blown up");
    } catch (SQLException e) {
      e = null;
    }
  }

  /**
   * Test method for {@link org.melati.poem.dbms.Dbms#
   * getLongSqlDefinition()}.
   */
  public void testGetLongSqlDefinition() {
    assertEquals("INT8", it.getLongSqlDefinition());    
  }

  /**
   * Test method for {@link org.melati.poem.dbms.Dbms#
   * sqlBooleanValueOfRaw(java.lang.Object)}.
   */
  public void testSqlBooleanValueOfRaw() {
    assertEquals("false", it.sqlBooleanValueOfRaw(Boolean.FALSE));        
    assertEquals("true", it.sqlBooleanValueOfRaw(Boolean.TRUE));        
  }

  /**
   * Test method for {@link org.melati.poem.dbms.Dbms#
   * getBinarySqlDefinition(int)}.
   */
  public void testGetBinarySqlDefinition() throws Exception {
    assertEquals("LONGVARBINARY(0)", it.getBinarySqlDefinition(0));        
    try {
      it.getBinarySqlDefinition(-1);
      fail("Should have blown up");
    } catch (SQLException e) {
      e = null;
    }
  }

  /**
   * Test method for {@link org.melati.poem.dbms.Dbms#
   * getFixedPtSqlDefinition(int, int)}.
   * @throws Exception 
   */
  public void testGetFixedPtSqlDefinition() throws Exception {
    assertEquals("DECIMAL(2,22)", it.getFixedPtSqlDefinition(22, 2));
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

    assertNull(it.canRepresent(new StringPoemType(true, 250), new StringPoemType(true, -1)));
    
  }

  /**
   * Test method for {@link org.melati.poem.dbms.Dbms#
   * defaultPoemTypeOfColumnMetaData(java.sql.ResultSet)}.
   */
  public void testDefaultPoemTypeOfColumnMetaData() {
    
  }

  /**
   * Test method for {@link org.melati.poem.dbms.Dbms#
   * canDropColumns(java.sql.Connection)}.
   */
  public void testCanDropColumns() throws Exception {
    assertTrue(it.canDropColumns());
  }

  /**
   * Test method for {@link org.melati.poem.dbms.Dbms#
   * exceptionForUpdate(org.melati.poem.Table, java.lang.String, boolean, java.sql.SQLException)}.
   */
  public void testExceptionForUpdateTableStringBooleanSQLException() {
    
  }

  /**
   * Test method for {@link org.melati.poem.dbms.Dbms#
   * exceptionForUpdate(org.melati.poem.Table, java.sql.PreparedStatement, boolean, java.sql.SQLException)}.
   */
  public void testExceptionForUpdateTablePreparedStatementBooleanSQLException() {
    
  }

  /**
   * Test method for {@link org.melati.poem.dbms.Dbms#
   * unreservedName(java.lang.String)}.
   */
  public void testUnreservedName() {
    assertEquals("name", it.unreservedName("name"));    
  }

  /**
   * Test method for {@link org.melati.poem.dbms.Dbms#melatiName(java.lang.String)}.
   */
  public void testMelatiName() {
    assertEquals("name", it.melatiName("name"));
    assertEquals(null, it.melatiName(null));
    assertEquals("~Special", it.melatiName("~Special"));
  }

  /**
   * Test method for {@link org.melati.poem.dbms.Dbms#
   * getIndexLength(org.melati.poem.Column)}.
   * @throws Exception 
   */
  public void testGetIndexLength() throws Exception {
    assertEquals("", it.getIndexLength(null));
  }

  /**
   * Test method for {@link org.melati.poem.dbms.Dbms#
   * canBeIndexed(org.melati.poem.Column)}.
   * @throws Exception 
   */
  public void testCanBeIndexed() throws Exception {
    
  }

  /**
   * Test method for {@link org.melati.poem.dbms.Dbms#
   * givesCapabilitySQL(java.lang.Integer, java.lang.String)}.
   */
  public void testGivesCapabilitySQL() {
    String actual = it.givesCapabilitySQL(new Integer(42),"hello");
    String expected = "SELECT * FROM " + it.getQuotedName("groupmembership") + 
                      " WHERE " + it.getQuotedName("user") + " = 42 AND " + 
                      "EXISTS ( SELECT " + it.getQuotedName("groupcapability") + 
                      "." + it.getQuotedName("group") +  
                      " FROM " + it.getQuotedName("groupcapability") + 
                      " WHERE " + 
                      it.getQuotedName("groupcapability") + "." + it.getQuotedName("group") + 
                      " = " + 
                      it.getQuotedName("groupmembership") + "." + 
                      it.getQuotedName("group") + " AND " + 
                      it.getQuotedName("capability") + " = hello)";
    assertEquals(expected,actual);
    
  }

  /**
   * Test method for {@link org.melati.poem.dbms.Dbms#
   * caseInsensitiveRegExpSQL(java.lang.String, java.lang.String)}.
   */
  public void testCaseInsensitiveRegExpSQL() {
    String expected = "a ILIKE '%b%'";
    String actual = it.caseInsensitiveRegExpSQL("a", "b");
    assertEquals(expected, actual);
  }

  /**
   * Test method for {@link org.melati.poem.dbms.Dbms#
   * caseInsensitiveRegExpSQL(java.lang.String, java.lang.String)}.
   */
  public void testCaseInsensitiveRegExpSQLQuoted() {
    String expected = "a ILIKE '%b%'";
    String actual = it.caseInsensitiveRegExpSQL("a", "\"b\"");
    assertEquals(expected, actual);
  }

  /**
   * Test method for {@link org.melati.poem.dbms.Dbms#
   * caseInsensitiveRegExpSQL(java.lang.String, java.lang.String)}.
   */
  public void testCaseInsensitiveRegExpSQLBlank() {
    String expected = " ILIKE '%%'";
    String actual = it.caseInsensitiveRegExpSQL("", "");
    assertEquals(expected, actual);
  }

  
  /**
   * Test method for {@link org.melati.poem.dbms.Dbms#toString()}.
   */
  public void testToString() {
    
  }

  /**
   * Test method for {@link org.melati.poem.dbms.Dbms#
   * getForeignKeyDefinition(java.lang.String, java.lang.String, 
   *                         java.lang.String, java.lang.String, 
   *                         java.lang.String)}.
   */
  public void testGetForeignKeyDefinition() {
    assertEquals(" ADD FOREIGN KEY (\"user\") REFERENCES \"user\"(\"id\") ON DELETE RESTRICT",
            it.getForeignKeyDefinition("test", "user", "user", "id", "prevent"));
    assertEquals(" ADD FOREIGN KEY (\"user\") REFERENCES \"user\"(\"id\") ON DELETE SET NULL",
            it.getForeignKeyDefinition("test", "user", "user", "id", "clear"));
    assertEquals(" ADD FOREIGN KEY (\"user\") REFERENCES \"user\"(\"id\") ON DELETE CASCADE",
            it.getForeignKeyDefinition("test", "user", "user", "id", "delete"));
  }

  /**
   * Test method for {@link org.melati.poem.dbms.Dbms#
   * getPrimaryKeyDefinition(java.lang.String)}.
   */
  public void testGetPrimaryKeyDefinition() {
    assertEquals(" ADD PRIMARY KEY (\"name\")", it.getPrimaryKeyDefinition("name"));
  }

  /**
   * Test method for {@link org.melati.poem.dbms.Dbms#
   *    alterColumnNotNullableSQL(java.lang.String, org.melati.poem.Column)}.
   */
  public void testAlterColumnNotNullableSQL() { 
    
  }

  /**
   * Test method for {@link org.melati.poem.dbms.Dbms#
   *    selectLimit(java.lang.String, int)}.
   */
  public void testSelectLimit() {
    assertEquals("SELECT * FROM \"USER\" LIMIT 1", it.selectLimit("* FROM \"USER\"", 1));
  }

  /**
   * Test method for {@link org.melati.poem.dbms.Dbms#
   *    booleanTrueExpression(org.melati.poem.Column)}.
   */
  public void  testBooleanTrueExtression() { 
    
  }
  
}
