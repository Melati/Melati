/**
 * 
 */
package org.melati.poem.dbms.test;

import org.melati.poem.dbms.Dbms;

import junit.framework.TestCase;

/**
 * Abstract test for a class implementing the Dbms interface.
 * 
 * @author timp
 * @since 23 Jan 2007
 *
 */
public abstract class DbmsSpec extends TestCase {

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
   * Test method for {@link org.melati.poem.dbms.Dbms#
   * getConnection(java.lang.String, java.lang.String, java.lang.String)}.
   */
  public void testGetConnection() {
    
  }

  /**
   * Test method for {@link org.melati.poem.dbms.Dbms#getSchema()}.
   */
  public void testGetSchema() {
    
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
   * getJdbcMetadataName(java.lang.String)}.
   */
  public void testGetJdbcMetadataName() {
    
  }

  /**
   * Test method for {@link org.melati.poem.dbms.Dbms#
   * preparedStatementPlaceholder(org.melati.poem.PoemType)}.
   */
  public void testPreparedStatementPlaceholder() {
    
  }

  /**
   * Test method for {@link org.melati.poem.dbms.Dbms#createTableSql()}.
   */
  public void testCreateTableSql() {
    assertEquals("CREATE TABLE ", it.createTableSql());
  }

  /**
   * Test method for {@link org.melati.poem.dbms.Dbms#
   * getSqlDefinition(java.lang.String)}.
   */
  public void testGetSqlDefinition() {
    
  }

  /**
   * Test method for {@link org.melati.poem.dbms.Dbms#getStringSqlDefinition(int)}.
   */
  public void testGetStringSqlDefinition() throws Exception {
    assertEquals("VARCHAR(0)", it.getStringSqlDefinition(0));    
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
  }

  /**
   * Test method for {@link org.melati.poem.dbms.Dbms#
   * getBinarySqlDefinition(int)}.
   */
  public void testGetBinarySqlDefinition() throws Exception {
    assertEquals("LONGVARBINARY(0)", it.getBinarySqlDefinition(0));        
  }

  /**
   * Test method for {@link org.melati.poem.dbms.Dbms#
   * getFixedPtSqlDefinition(int, int)}.
   */
  public void testGetFixedPtSqlDefinition() {
    
  }

  /**
   * Test method for {@link org.melati.poem.dbms.Dbms#
   * canRepresent(org.melati.poem.PoemType, org.melati.poem.PoemType)}.
   */
  public void testCanRepresent() {
    
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
    assertFalse(it.canDropColumns());
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
  }

  /**
   * Test method for {@link org.melati.poem.dbms.Dbms#
   * getIndexLength(org.melati.poem.Column)}.
   */
  public void testGetIndexLength() {
    
  }

  /**
   * Test method for {@link org.melati.poem.dbms.Dbms#
   * canBeIndexed(org.melati.poem.Column)}.
   */
  public void testCanBeIndexed() {
    
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
    String expected = "a REGEXP b";
    String actual = it.caseInsensitiveRegExpSQL("a", "b");
    assertEquals(expected, actual);
  }

  public void testCaseInsensitiveRegExpSQLQuoted() {
    String expected = "a REGEXP \"b\"";
    String actual = it.caseInsensitiveRegExpSQL("a", "\"b\"");
    assertEquals(expected, actual);
  }

  public void testCaseInsensitiveRegExpSQLBlank() {
    String expected = " REGEXP ";
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
   * getForeignKeyDefinition(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)}.
   */
  public void testGetForeignKeyDefinition() {
    assertEquals(" ADD FOREIGN KEY (\"user\") REFERENCES \"user\"(\"id\") ON DELETE CASCADE",it.getForeignKeyDefinition("test", "user", "user", "id", "delete"));
  }

  /**
   * Test method for {@link org.melati.poem.dbms.Dbms#
   * getPrimaryKeyDefinition(java.lang.String)}.
   */
  public void testGetPrimaryKeyDefinition() {
    
  }

}
