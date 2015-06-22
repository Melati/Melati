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
public class HsqldbTest extends DbmsSpec {

  public HsqldbTest(String name) {
    super(name);
  }

  protected void setUp() throws Exception {
    super.setUp();
  }

  protected void tearDown() throws Exception {
    super.tearDown();
  }

  protected void setObjectUnderTest() {
    it = DbmsFactory.getDbms("org.melati.poem.dbms.Hsqldb");
  }

  /**
   * Test method for {@link org.melati.poem.dbms.Dbms#createTableSql}.
   */
  public void testCreateTableSql() {
    if (getDb().getDbms() == it)
      assertEquals("CREATE CACHED TABLE " + 
            "\"USER\" (\"ID\" INT NOT NULL, " + 
            "\"NAME\" VARCHAR(60) NOT NULL, " + 
            "\"LOGIN\" VARCHAR(255) NOT NULL, " + 
            "\"PASSWORD\" VARCHAR(20) NOT NULL)", 
            it.createTableSql(getDb().getUserTable()));
  }

  /**
   * Test method for {@link org.melati.poem.dbms.Dbms#
   * getStringSqlDefinition(java.lang.String)}.
   */
  public void testGetStringSqlDefinition() throws Exception {
    //assertEquals("VARCHAR(266)",  it.getStringSqlDefinition(-1));
    assertEquals("LONGVARCHAR",  it.getStringSqlDefinition(-1));
  }

  /**
   * Test method for {@link org.melati.poem.dbms.Dbms#
   * getLongSqlDefinition()}.
   */
  public void testGetLongSqlDefinition() {
    assertEquals("BIGINT", it.getLongSqlDefinition());    
  }

  /**
   * Test method for {@link org.melati.poem.dbms.Dbms#
   * getBinarySqlDefinition(int)}.
   */
  public void testGetBinarySqlDefinition() throws Exception {
    assertEquals("LONGVARBINARY", it.getBinarySqlDefinition(0));        
  }

  /**
   * Test method for {@link org.melati.poem.dbms.Dbms#melatiName(java.lang.String)}.
   */
  public void testMelatiName() {
    assertEquals("name", it.melatiName("name"));
    assertEquals(null, it.melatiName(null));
    assertEquals("~special", it.melatiName("~Special"));
    assertEquals("unique", it.melatiName("MELATI_UNIQUE"));
    assertEquals("constraint", it.melatiName("MELATI_CONSTRAINT"));
    assertEquals("users", it.melatiName("users"));
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
   * getJdbcMetadataName(java.lang.String)}.
   */
  public void testGetJdbcMetadataName() {
    assertEquals("NAME",it.getJdbcMetadataName("name"));
  }


  /**
   * Test method for {@link org.melati.poem.dbms.Dbms#
   * getForeignKeyDefinition(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)}.
   */
  public void testGetForeignKeyDefinition() {
    
    assertEquals(" ADD FOREIGN KEY (\"USER\") REFERENCES \"USER\"(\"ID\")",
            it.getForeignKeyDefinition("test", "user", "user", "id", "prevent"));
    assertEquals(" ADD FOREIGN KEY (\"USER\") REFERENCES \"USER\"(\"ID\") ON DELETE SET NULL",
            it.getForeignKeyDefinition("test", "user", "user", "id", "clear"));
    assertEquals(" ADD FOREIGN KEY (\"USER\") REFERENCES \"USER\"(\"ID\") ON DELETE CASCADE",
            it.getForeignKeyDefinition("test", "user", "user", "id", "delete"));

  
  }

  /**
   * Test method for {@link org.melati.poem.dbms.Dbms#
   * getPrimaryKeyDefinition(java.lang.String)}.
   */
  public void testGetPrimaryKeyDefinition() {
    assertEquals(" ADD PRIMARY KEY (\"NAME\")", it.getPrimaryKeyDefinition("name"));
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
    String expected = "a LIKE \'%b%\'";
    String actual = it.caseInsensitiveRegExpSQL("a", "\"b\"");
    assertEquals(expected, actual);
  }

  public void testCaseInsensitiveRegExpSQLBlank() {
    String expected = " LIKE '%%'";
    String actual = it.caseInsensitiveRegExpSQL("", "");
    assertEquals(expected, actual);
  }


}
