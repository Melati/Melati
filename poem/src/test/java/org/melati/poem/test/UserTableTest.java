package org.melati.poem.test;

import org.melati.poem.AccessToken;
import org.melati.poem.PoemDatabase;
import org.melati.poem.PoemTask;
import org.melati.poem.UnexpectedExceptionPoemException;
import org.melati.poem.User;
import org.melati.poem.UserTable;

import junit.framework.TestCase;

/**
 * Test the User Table.
 * 
 * This test is duplicated in PoemDatabaseTest, 
 * but is left in here as it is a useful template for 
 * a simple, single dbms test.
 * 
 * @author timp
 * @see PoemDatabaseTest
 */
public class UserTableTest extends TestCase {

  private PoemDatabase db = null;
  private UserTable ut;

  //protected static String dbUrl = "jdbc:hsqldb:/dist/melati/db/melatijunit"; 
  protected static String dbUrl = "jdbc:hsqldb:mem:melatijunit"; 
  /*
   * @see TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
    init();
  }

  private void init() {
    db = new PoemDatabase();
    db.connect("poem","org.melati.poem.dbms.Hsqldb", 
               dbUrl, "sa",
               "", 4);
    ut = db.getUserTable();
    System.err.println("in UserTableTest");
  }

  /*
   * @see TestCase#tearDown()
   */
  protected void tearDown() throws Exception {
    db.inSession(AccessToken.root, // HACK
        new PoemTask() {
          public void run() {
            try {
              if (db.getDbms().toString().endsWith("Hsqldb"))
                db.sqlQuery("SHUTDOWN");
            } catch (Exception e) {
              throw new UnexpectedExceptionPoemException(e);
            }
          }
        });
    //db.disconnect();
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
    db.inSession(AccessToken.root, // HACK
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


}
