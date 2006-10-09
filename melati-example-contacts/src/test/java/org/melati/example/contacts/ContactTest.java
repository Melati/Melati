package org.melati.example.contacts;

import org.melati.example.contacts.Contact.DescendantParentException;
import org.melati.poem.AccessToken;
import org.melati.poem.PoemTask;
import org.melati.poem.PoemThread;
import org.melati.poem.User;
import org.melati.poem.test.PoemTestCase;

public class ContactTest extends PoemTestCase {

  private String dbName = "contacts";

  private Contact root = null;
  private Contact a = null;
  private Contact b = null;
  private Contact c = null;
  private Contact r = null;
  private Contact s = null;
  private Contact t = null;
  private Contact x = null;
  private Contact y = null;
  private Contact z = null;
  
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
              root = (Contact)((ContactsDatabase)getDb()).getContactTable().newPersistent();
              root.setAddress("Oxford");
              root.setName("root");
              root = (Contact)((ContactsDatabase)getDb()).getContactTable().getNameColumn().ensure(root);

              a = (Contact)((ContactsDatabase)getDb()).getContactTable().ensure("a", root, "Oxford");
              b = (Contact)((ContactsDatabase)getDb()).getContactTable().ensure("b", root, "Oxford");
              c = (Contact)((ContactsDatabase)getDb()).getContactTable().ensure("c", root, "Oxford");

              r = (Contact)((ContactsDatabase)getDb()).getContactTable().ensure("r", a, "Oxford");
              s = (Contact)((ContactsDatabase)getDb()).getContactTable().ensure("s", a, "Oxford");
              t = (Contact)((ContactsDatabase)getDb()).getContactTable().ensure("t", a, "Oxford");

              x = (Contact)((ContactsDatabase)getDb()).getContactTable().ensure("x", s, "Oxford");
              y = (Contact)((ContactsDatabase)getDb()).getContactTable().ensure("y", x, "Oxford");
              z = (Contact)((ContactsDatabase)getDb()).getContactTable().ensure("z", y, "Oxford");
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
    return this.dbName;
  }
  public void testSetOwner() {
    try { 
      root.setOwner(z);
      fail("Should have bombed");
    } catch (DescendantParentException e) {
      e = null;
    }
  }

  public void testIsIn() {
    //fail("Not yet implemented");
  }

  public void testGetChildren() {
    assertTrue(root.getChildren().length == 3);
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

  public void testArrayOfVector() {
   // fail("Not yet implemented");
  }

  public void testArrayOfEnumeration() {
    // fail("Not yet implemented");
  }

}
