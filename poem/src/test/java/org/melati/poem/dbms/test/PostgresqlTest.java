/**
 * 
 */
package org.melati.poem.dbms.test;

import org.melati.poem.DoublePoemType;
import org.melati.poem.IntegerPoemType;
import org.melati.poem.LongPoemType;
import org.melati.poem.StringPoemType;
import org.melati.poem.dbms.DbmsFactory;

/**
 * @author timp
 * @since 23 Jan 2007
 *
 */
public class PostgresqlTest extends DbmsSpec {

  /**
   * Constructor.
   * @param name
   */
  public PostgresqlTest(String name) {
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
    it = DbmsFactory.getDbms("org.melati.poem.dbms.Postgresql");
  }

  /**
   * Test method for {@link org.melati.poem.dbms.Postgresql#
   * preparedStatementPlaceholder(org.melati.poem.PoemType)}.
   */
  public void testPreparedStatementPlaceholder() {
    assertEquals("CAST(? AS INT4)", it.preparedStatementPlaceholder(new IntegerPoemType(true)));
    assertEquals("CAST(? AS INT8)", it.preparedStatementPlaceholder(new LongPoemType(true)));
    assertEquals("CAST(? AS FLOAT8)", it.preparedStatementPlaceholder(new DoublePoemType(true)));
    assertEquals("?", it.preparedStatementPlaceholder(new StringPoemType(true, -1)));
  }
  
  /**
   * Test method for {@link org.melati.poem.dbms.Dbms#
   * getStringSqlDefinition(java.lang.String)}.
   */
  public void testGetStringSqlDefinition() throws Exception {
    assertEquals("VARCHAR(0)", it.getStringSqlDefinition(0));    
    assertEquals("TEXT",  it.getStringSqlDefinition(-1));
  }
  
  /**
   * Test method for {@link org.melati.poem.dbms.Dbms#
   * getBinarySqlDefinition(int)}.
   */
  public void testGetBinarySqlDefinition() throws Exception {
    assertEquals("BYTEA", it.getBinarySqlDefinition(0));        
  }

  /**
   * Test method for {@link org.melati.poem.dbms.Dbms#createTableSql()}.
   */
  public void testCreateTableSql() {
    if (getDb().getDbms() == it)
      assertEquals("CREATE TABLE \"user\" (\"id\" INT NOT NULL, \"name\" VARCHAR(60) NOT NULL, \"login\" VARCHAR(255) NOT NULL, \"password\" VARCHAR(20) NOT NULL)", it.createTableSql(getDb().getUserTable()));
  }
  
  
}
