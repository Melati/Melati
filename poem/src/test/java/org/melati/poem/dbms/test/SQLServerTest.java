/**
 * 
 */
package org.melati.poem.dbms.test;

import org.melati.LogicalDatabase;
import org.melati.poem.dbms.DbmsFactory;
import org.melati.poem.test.TestDatabase;

/**
 * @author timp
 * @since 23 Jan 2007
 *
 */
public class SQLServerTest extends DbmsSpec {

  /**
   * Constructor.
   * @param name
   */
  public SQLServerTest(String name) {
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
    it = DbmsFactory.getDbms("org.melati.poem.dbms.SQLServer");
  }

  /**
   * Test method for {@link org.melati.poem.dbms.Dbms#
   * getStringSqlDefinition(java.lang.String)}.
   */
  public void testGetStringSqlDefinition() throws Exception {
    assertEquals("VARCHAR(0)", it.getStringSqlDefinition(0));    
    assertEquals("VARCHAR(2333)",  it.getStringSqlDefinition(-1));
  }
  
  /**
   * Test method for {@link org.melati.poem.dbms.Dbms#
   * getSqlDefinition(java.lang.String)}.
   * @throws Exception 
   */
  public void testGetSqlDefinition() throws Exception {
    assertEquals("DOUBLE PRECISION", it.getSqlDefinition("DOUBLE PRECISION"));
    assertEquals("INT8", it.getSqlDefinition("INT8"));
    assertEquals("Big Decimal", it.getSqlDefinition("Big Decimal"));

    assertEquals("BIT", it.getSqlDefinition("BOOLEAN"));
    assertEquals("DATETIME", it.getSqlDefinition("DATE"));
    assertEquals("DATETIME", it.getSqlDefinition("TIMESTAMP"));
  }

  /**
   * Test method for {@link org.melati.poem.dbms.Dbms#melatiName(java.lang.String)}.
   */
  public void testMelatiName() {
    assertEquals("name", it.melatiName("name"));
    assertEquals(null, it.melatiName(null));
    assertNull(it.melatiName("dtproperties"));
  }

  
  /**
   * Test method for {@link org.melati.poem.dbms.Dbms#
   * canBeIndexed(org.melati.poem.Column)}.
   * @throws Exception 
   */
  public void testCanBeIndexed() throws Exception {
    TestDatabase db = (TestDatabase)LogicalDatabase.getDatabase("poemtest");
    assertFalse(it.canBeIndexed(db.getTableInfoTable().getDescriptionColumn()));
    assertTrue(it.canBeIndexed(db.getUserTable().getNameColumn()));
  }

  
  /**
   * Test method for {@link org.melati.poem.dbms.Dbms#
   * getForeignKeyDefinition(java.lang.String, java.lang.String, 
   *                         java.lang.String, java.lang.String, 
   *                         java.lang.String)}.
   */
  public void testGetForeignKeyDefinition() {
    assertEquals(" ADD FOREIGN KEY (\"user\") REFERENCES \"user\"(\"id\") ON DELETE NO ACTION",
            it.getForeignKeyDefinition("test", "user", "user", "id", "prevent"));
    assertEquals(" ADD FOREIGN KEY (\"user\") REFERENCES \"user\"(\"id\") ON DELETE SET NULL",
            it.getForeignKeyDefinition("test", "user", "user", "id", "clear"));
    assertEquals(" ADD FOREIGN KEY (\"user\") REFERENCES \"user\"(\"id\") ON DELETE CASCADE",
            it.getForeignKeyDefinition("test", "user", "user", "id", "delete"));
  }


}
