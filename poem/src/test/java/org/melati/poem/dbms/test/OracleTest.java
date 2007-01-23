/**
 * 
 */
package org.melati.poem.dbms.test;

import org.melati.poem.dbms.DbmsFactory;

/**
 * @author timp
 * @since 23 Jan 2007
 *
 */
public class OracleTest extends DbmsSpec {

  /**
   * Constructor.
   * @param name
   */
  public OracleTest(String name) {
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
    it = DbmsFactory.getDbms("org.melati.poem.dbms.Oracle");
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
   * Test method for {@link org.melati.poem.dbms.Dbms#
   * unreservedName(java.lang.String)}.
   */
  public void testUnreservedName() {
    assertEquals("NAME", it.unreservedName("name"));    
  }

  /**
   * Test method for {@link org.melati.poem.dbms.Dbms#
   * getForeignKeyDefinition(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)}.
   */
  public void testGetForeignKeyDefinition() {
    assertEquals(" ADD (CONSTRAINT FK_test_user) FOREIGN KEY (\"MELATI_USER\") REFERENCES " + 
                 "\"MELATI_USER\"(\"ID\") ON DELETE CASCADE",
            it.getForeignKeyDefinition("test", "user", "user", "id", "delete"));
  }


  
}
