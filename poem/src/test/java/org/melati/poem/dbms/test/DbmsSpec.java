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
  }

  /**
   * {@inheritDoc}
   * @see junit.framework.TestCase#tearDown()
   */
  protected void tearDown() throws Exception {
    super.tearDown();
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
  public void testGetStringSqlDefinition() {
    
  }

  /**
   * Test method for {@link org.melati.poem.dbms.Dbms#
   * getLongSqlDefinition()}.
   */
  public void testGetLongSqlDefinition() {
    
  }

  /**
   * Test method for {@link org.melati.poem.dbms.Dbms#
   * sqlBooleanValueOfRaw(java.lang.Object)}.
   */
  public void testSqlBooleanValueOfRaw() {
    
  }

  /**
   * Test method for {@link org.melati.poem.dbms.Dbms#
   * getBinarySqlDefinition(int)}.
   */
  public void testGetBinarySqlDefinition() {
    
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
  public void testCanDropColumns() {
    
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
    
  }

  /**
   * Test method for {@link org.melati.poem.dbms.Dbms#melatiName(java.lang.String)}.
   */
  public void testMelatiName() {
    
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
    
  }

  /**
   * Test method for {@link org.melati.poem.dbms.Dbms#
   * caseInsensitiveRegExpSQL(java.lang.String, java.lang.String)}.
   */
  public void testCaseInsensitiveRegExpSQL() {
    
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
    
  }

  /**
   * Test method for {@link org.melati.poem.dbms.Dbms#
   * getPrimaryKeyDefinition(java.lang.String)}.
   */
  public void testGetPrimaryKeyDefinition() {
    
  }

}
