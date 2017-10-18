package org.melati.example.contacts;

import org.melati.example.contacts.Contact.DescendantParentException;
import org.melati.poem.*;
import org.melati.poem.test.PoemTestCase;

/**
 * @author timp
 */
public class ContactTest extends PoemTestCase {

  private static String databaseName = "contacts";
  
  private Contact rootContact = null;
  private Contact a = null;
  private Contact b = null;
  private Contact c = null;
  private Contact r = null;
  private Contact s = null;
  private Contact x = null;
  private Contact y = null;
  private Contact z = null;
  
  /**
   * Constructor.
   */
  public ContactTest(String arg0) {
    super(arg0);
  }

  protected void setUp() throws Exception {
    super.setUp();
    User guestUser = getDb().getUserTable().guestUser();
    setUserToRunAs(guestUser);
    getDb().inSession(AccessToken.root, 
        new PoemTask() {
          public void run() {
            try {
              rootContact = (Contact) ((ContactsDatabase) getDb()).getContactTable().newPersistent();
              rootContact.setAddress("Oxford");
              rootContact.setName("rootContact");
              rootContact = (Contact) ((ContactsDatabase) getDb()).getContactTable().getNameColumn().ensure(rootContact);

              a = ((ContactsDatabase) getDb()).getContactTable().ensure("a", rootContact, "Oxford");
              b = ((ContactsDatabase) getDb()).getContactTable().ensure("b", rootContact, "Oxford");
              c = ((ContactsDatabase) getDb()).getContactTable().ensure("c", rootContact, "Oxford");

              r = ((ContactsDatabase)getDb()).getContactTable().ensure("r", a, "Oxford");
              s = ((ContactsDatabase)getDb()).getContactTable().ensure("s", a, "Oxford");

              x = ((ContactsDatabase)getDb()).getContactTable().ensure("x", s, "Oxford");
              y = ((ContactsDatabase)getDb()).getContactTable().ensure("y", x, "Oxford");
              z = ((ContactsDatabase)getDb()).getContactTable().ensure("z", y, "Oxford");
              PoemThread.commit();
            } catch (Throwable e) {
              //e.fillInStackTrace();
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
  public  Database getPoemDatabase() { 
    maxTrans = 4;
    return PoemDatabaseFactory.getDatabase(databaseName,
            "jdbc:hsqldb:mem:" + databaseName,
            "sa",
            "","org.melati.example.contacts.ContactsDatabase",
            "org.melati.poem.dbms.Hsqldb",false,false,false,4);
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
    checkTablesAndColumns(12,81);
  }
  
  /**
   * Test setOwner.
   */
  public void testSetOwner() {
    try {
      rootContact.setOwner(z);
      fail("Should have bombed");
    } catch (DescendantParentException e) {
      e = null;
    }
  }

  /**
   * Test isIn.
   */
  public void testIsIn() {
    //fail("Not yet implemented");
  }

  /**
   * Test getChildren.
   */
  public void testGetChildren() {
    assertTrue(rootContact.getChildren().length == 3);
  }

  /**
   * @see org.melati.example.contacts.Contact#getAncestors
   */
  public void testGetAncestors() {
    assertTrue(z.getAncestors().toArray().length == 5);
    assertTrue(b.getAncestors().toArray().length == 1);
    assertTrue(c.getAncestors().toArray().length == 1);
    assertTrue(r.getAncestors().toArray().length == 2);
    assertTrue(s.getAncestors().toArray().length == 2);
}

  /**
   * Test arrayOf.
   */
  public void testArrayOfVector() {
   // fail("Not yet implemented");
  }

  /**
   * Test arrayOf.
   */
  public void testArrayOfEnumeration() {
    // fail("Not yet implemented");
  }

}
