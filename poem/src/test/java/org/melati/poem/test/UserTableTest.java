//
// (C) 2005, CSW Health.  A CSW Group Company.
//
package org.melati.poem.test;

import org.melati.poem.AccessToken;
import org.melati.poem.PoemDatabase;
import org.melati.poem.PoemTask;
import org.melati.poem.UnexpectedExceptionPoemException;
import org.melati.poem.User;
import org.melati.poem.UserTable;

import junit.framework.TestCase;

/**
 * @author tim.pizey
 */
public class UserTableTest extends TestCase {

  private PoemDatabase db = null;
  private UserTable ut;

  /*
   * @see TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
    db = new PoemDatabase();
    db.connect("org.melati.poem.dbms.Hsqldb", "jdbc:hsqldb:melatijunit", "sa",
        "", 4);
    ut = db.getUserTable();

  }

  /*
   * @see TestCase#tearDown()
   */
  protected void tearDown() throws Exception {
    db.inSession(AccessToken.root, // FIXME
        new PoemTask() {
          public void run() {
            try {
              db.sqlQuery("SHUTDOWN");
            } catch (Exception e) {
              throw new UnexpectedExceptionPoemException(e);
            }
          }
        });
    db.disconnect();
    super.tearDown();
  }

  /**
   * Constructor for UserTableTest.
   * @param name
   */
  public UserTableTest(String name) {
    super(name);
  }

  /**
   * @see org.melati.poem.UserTable#guestUser()
   */
  public final void testGuestUser() {
    db.inSession(AccessToken.root, // FIXME
        new PoemTask() {
          public void run() {
            try {
              User u = ut.guestUser();
              assertEquals(u.getLogin(), "_guest_");
            } catch (Exception e) {
              throw new UnexpectedExceptionPoemException(e);
            }
          }
        });

  }

  public final void testAdministratorUser() {
    //TODO Implement administratorUser().
  }

}
