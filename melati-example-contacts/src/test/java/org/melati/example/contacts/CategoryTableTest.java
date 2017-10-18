package org.melati.example.contacts;

import org.melati.poem.*;
import org.melati.poem.test.PoemTestCase;

/**
 * @author timp
 * @since 2017-10-18
 */
public class CategoryTableTest extends PoemTestCase {
  private static String databaseName = "contacts";


  protected void setUp() throws Exception {
    super.setUp();
    User guestUser = getDb().getUserTable().guestUser();
    setUserToRunAs(guestUser);
    getDb().inSession(AccessToken.root,
        new PoemTask() {
          public void run() {
            try {
              PoemThread.commit();
            } catch (Throwable e) {
              throw new RuntimeException(e);
            }
          }
        });

  }

  protected void tearDown() throws Exception {
    super.tearDown();
  }

  /**
   * @return Returns the dbName.
   */
  protected String getDbName() {
    return databaseName;
  }

  public Database getDb(String dbNameP) {
    if (dbNameP == null)
      throw new NullPointerException();
    return getPoemDatabase();
  }

  /**
   * @return a db
   */
  public Database getPoemDatabase() {
    maxTrans = 4;
    return PoemDatabaseFactory.getDatabase(getDbName(),
        "jdbc:hsqldb:mem:" + getDbName(),
        "sa",
        "", "org.melati.example.contacts.ContactsDatabase",
        "org.melati.poem.dbms.Hsqldb", false, false, false, 4);
  }

  protected void databaseUnchanged() {
    assertEquals("Setting changed", 0, getDb().getSettingTable().count());
    assertEquals("Group changed", 1, getDb().getGroupTable().count());
    assertEquals("GroupMembership changed", 1, getDb().getGroupMembershipTable().count());
    assertEquals("Capability changed", 5, getDb().getCapabilityTable().count());
    assertEquals("GroupCapability changed", 1, getDb().getGroupCapabilityTable().count());
    assertEquals("TableCategory changed", 5, getDb().getTableCategoryTable().count());
    assertEquals("User changed", 2, getDb().getUserTable().count());
    assertEquals("ColumnInfo changed", 81, getDb().getColumnInfoTable().count());
    assertEquals("TableInfo changed", 12, getDb().getTableInfoTable().count());
    checkTablesAndColumns(12, 81);
  }

  /**
   * Test setOwner.
   */
  public void testEnsure() {
    Category c = (Category) ((ContactsDatabase) getDb()).getCategoryTable().newPersistent();
    c.setName("Cat");
    c.makePersistent();
    Category c2 = ((ContactsDatabase) getDb()).getCategoryTable()
        .ensure("Cat");
    assertEquals(c, c2);
    Category d = ((ContactsDatabase) getDb()).getCategoryTable().ensure("Dog");
    assertNotNull(d);
    c.delete();
    d.delete();
  }

}
