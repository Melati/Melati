/**
 * 
 */
package org.melati.poem.test;

import org.melati.poem.Column;
import org.melati.poem.Group;
import org.melati.poem.GroupMembership;
import org.melati.poem.PoemThread;
import org.melati.poem.ReadPasswordAccessPoemException;
import org.melati.poem.RowDisappearedPoemException;
import org.melati.poem.User;
import org.melati.poem.WriteFieldAccessPoemException;
import org.melati.poem.WritePersistentAccessPoemException;

/**
 * @author timp
 * @since 9 Jan 2007
 *
 */
public class UserTest extends PoemTestCase {

  /**
   * @param name
   */
  public UserTest(String name) {
    super(name);
  }

  /**
   * {@inheritDoc}
   * @see org.melati.poem.test.PoemTestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
  }

  /**
   * {@inheritDoc}
   * @see org.melati.poem.test.PoemTestCase#tearDown()
   */
  protected void tearDown() throws Exception {
    super.tearDown();
    
  }

  protected void databaseUnchanged() { 
    Column<String> c = getDb().getUserTable().getNameColumn();
    User tester = (User)c.firstWhereEq("tester");
    if (tester != null)
      tester.delete();
    super.databaseUnchanged();

  }
  
  /**
   * Test method for {@link org.melati.poem.User#toString()}.
   */
  public void testToString() {
    
  }

  /**
   * The guest user should not be able to read 
   * another users password.
   * Test method for {@link org.melati.poem.User#getPassword()}.
   */
  public void testGetPassword() {
    // Hsqldb memory database is the only one which finnesses the tidy up issue  
    if (getDb().getDbms().toString().equals("org.melati.poem.dbms.Hsqldb")) { 
      assertEquals("guest",getDb().guestUser().getPassword());
      User tester = (User)getDb().getUserTable().newPersistent();
      tester.setName("tester");
      tester.setLogin("tester");
      tester.setPassword("pwd");
      tester.makePersistent();
      assertEquals("pwd",tester.getPassword());
    
      PoemThread.setAccessToken(getDb().guestUser());
      assertEquals("guest",getDb().guestUser().getPassword());
      try {
        assertEquals("pwd",tester.getPassword());
        fail("should have blown up");
      } catch (ReadPasswordAccessPoemException e) {
        e = null;
      }
      assertFalse(tester.isGuest());
      assertFalse(tester.isAdministrator());
    }
  }

  /**
   * A user may read her own password but not set it. 
   * Test method for {@link org.melati.poem.User#setPassword(java.lang.String)}.
   */
  public void testSetPassword() {
    // Hsqldb memory database is the only one which finnesses the tidy up issue  
    if (getDb().getDbms().toString().equals("org.melati.poem.dbms.Hsqldb")) { 
      assertEquals("guest",getDb().guestUser().getPassword());
      getDb().guestUser().setPassword("fred");
      assertEquals("fred",getDb().guestUser().getPassword());
      getDb().guestUser().setPassword("guest");
      assertEquals("guest",getDb().guestUser().getPassword());
      User tester = (User)getDb().getUserTable().newPersistent();
      tester.setName("tester");
      tester.setLogin("tester");
      tester.setPassword("pwd");
      tester.makePersistent();
      assertEquals("pwd",tester.getPassword());

      PoemThread.setAccessToken(tester);
      assertEquals("pwd",tester.getPassword());
      try {
        tester.setPassword("new");
        fail("should have blown up");
      } catch (WritePersistentAccessPoemException e) {
        e = null;
      }
    }
  }
  /**
   * Need another go as root.
   * Test method for {@link org.melati.poem.User#setPassword(java.lang.String)}.
   */
  public void testSetPassword2() {
    // Hsqldb memory database is the only one which finnesses the tidy up issue  
    if (getDb().getDbms().toString().equals("org.melati.poem.dbms.Hsqldb")) { 
      User tester = (User)getDb().getUserTable().newPersistent();
      tester.setName("tester");
      tester.setLogin("tester");
      tester.setPassword("pwd");
      tester.makePersistent();
      assertEquals("pwd",tester.getPassword());

      PoemThread.setAccessToken(getDb().guestAccessToken());
      try {
        tester.setPassword("new");
        fail("should have blown up");
      } catch (WriteFieldAccessPoemException e) {
        e = null;
      }
    }
  }

  /**
   * Test method for {@link org.melati.poem.User#User()}.
   */
  public void testUser() {
    
  }

  /**
   * Test method for {@link org.melati.poem.User#
   * User(java.lang.String, java.lang.String, java.lang.String)}.
   */
  public void testUserStringStringString() {
    User u = new User("tester","tester","tester");
    getDb().getUserTable().create(u); 
    assertEquals("tester",u.getName());
    u.setName("tester2");
    // get the logSQL line covered
    getDb().setLogSQL(true);
    PoemThread.commit();
    u.delete();
    getDb().setLogSQL(false);
  }

  /**
   * Test method for {@link org.melati.poem.User#givesCapability(org.melati.poem.Capability)}.
   */
  public void testGivesCapability() {
    
  }

  /**
   * Test method for {@link org.melati.poem.User#isGuest()}.
   */
  public void testIsGuest() {
    assertTrue(getDb().guestUser().isGuest());
    assertFalse(getDb().administratorUser().isGuest());
  }

  /**
   * Test method for {@link org.melati.poem.User#isAdministrator()}.
   */
  public void testIsAdministrator() {
    assertFalse(getDb().guestUser().isAdministrator());
    assertTrue(getDb().administratorUser().isAdministrator());    
  }

  /**
   * Test cascading delete works. 
   */
  public void testIntegrityFixDelete() {
    User u = new User("tester","tester","tester");
    getDb().getUserTable().create(u); 
    assertEquals("tester",u.getName());
    Group g = getDb().getGroupTable().administratorsGroup();
    GroupMembership gm = (GroupMembership)getDb().getGroupMembershipTable().newPersistent();
    gm.setGroup(g);
    gm.setUser(u);
    gm.makePersistent();
    u.delete();
    try {
      gm.delete();
      fail("Should have blown up.");
    } catch(RowDisappearedPoemException e) { 
      e = null;
    }
  }

}
