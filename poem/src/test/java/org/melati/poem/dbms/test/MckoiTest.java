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
public class MckoiTest extends DbmsSpec {

  /**
   * Constructor.
   * @param name
   */
  public MckoiTest(String name) {
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
    it = DbmsFactory.getDbms("org.melati.poem.dbms.Mckoi");
  }

  /**
   * Test method for {@link org.melati.poem.dbms.Dbms#
   * getBinarySqlDefinition(int)}.
   */
  public void testGetBinarySqlDefinition() throws Exception {
    assertEquals("LONGVARBINARY", it.getBinarySqlDefinition(0));        
  }

  /**
   * Test method for {@link org.melati.poem.dbms.Dbms#
   * getForeignKeyDefinition(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)}.
   */
  public void testGetForeignKeyDefinition() {
    assertEquals(" ADD FOREIGN KEY (user) REFERENCES user(id) ON DELETE CASCADE",it.getForeignKeyDefinition("test", "user", "user", "id", "delete"));
  }

  
}
