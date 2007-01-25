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
   * 
   * @param name
   */
  public MckoiTest(String name) {
    super(name);
  }

  /**
   * {@inheritDoc}
   * 
   * @see org.melati.poem.dbms.test.DbmsSpec#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
  }

  /**
   * {@inheritDoc}
   * 
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
   * getForeignKeyDefinition(java.lang.String, java.lang.String,
   * java.lang.String, java.lang.String, java.lang.String)}.
   */
  public void testGetForeignKeyDefinition() {
    assertEquals(
            " ADD FOREIGN KEY (user) REFERENCES user(id) ON DELETE CASCADE", it
                    .getForeignKeyDefinition("test", "user", "user", "id",
                            "delete"));
  }

  /**
   * Test method for {@link org.melati.poem.dbms.Dbms#
   * givesCapabilitySQL(java.lang.Integer, java.lang.String)}.
   */
  public void testGivesCapabilitySQL() {
    String actual = it.givesCapabilitySQL(new Integer(42), "hello");
    String expected = "SELECT " + it.getQuotedName("groupmembership") + ".* "
            + "FROM " + it.getQuotedName("groupmembership") + " LEFT JOIN "
            + it.getQuotedName("groupcapability") + " ON "
            + it.getQuotedName("groupmembership") + "."
            + it.getQuotedName("group") + " =  "
            + it.getQuotedName("groupcapability") + "."
            + it.getQuotedName("group") + " WHERE " + it.getQuotedName("user")
            + " = 42" + " " + "AND " + it.getQuotedName("groupcapability")
            + "." + it.getQuotedName("group") + " IS NOT NULL " + "AND "
            + it.getQuotedName("capability") + " = hello";

    assertEquals(expected, actual);

  }

}
