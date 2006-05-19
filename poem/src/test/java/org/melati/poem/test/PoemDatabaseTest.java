package org.melati.poem.test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Enumeration;

import org.melati.poem.Capability;
import org.melati.poem.User;
import org.melati.poem.UserTable;
import org.melati.poem.dbms.Dbms;

/**
 * Test the features of all Poem databases.
 * 
 * @author timp
 */
public class PoemDatabaseTest extends PoemTestCase {

  /**
   * Constructor for PoemTest.
   * 
   * @param arg0
   */
  public PoemDatabaseTest(String arg0) {
    super(arg0);
  }

  /*
   * @see TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
  }

  /**
   * Test method for 'org.melati.poem.generated.PoemDatabaseBase.getUserTable()'
   * Test method for 'org.melati.poem.Database.getTable(String)'
   */
  public void testGetUserTable() {
    UserTable ut1 = db.getUserTable();
    UserTable ut2 = (UserTable)db.getTable("user");
    assertEquals(ut1, ut2);
  }

  /**
   * Test method for 'org.melati.poem.Database.transactionsMax()'
   */
  public void testTransactionsMax() {
    assertEquals(4, db.transactionsMax());
  }

  /**
   * Test method for 'org.melati.poem.Database.getDisplayTables()'
   */
  public void testGetDisplayTables() {
    final String expected = "user (from the data structure definition)"
            + "group (from the data structure definition)"
            + "capability (from the data structure definition)"
            + "groupmembership (from the data structure definition)"
            + "groupcapability (from the data structure definition)"
            + "tableinfo (from the data structure definition)"
            + "columninfo (from the data structure definition)"
            + "tablecategory (from the data structure definition)"
            + "setting (from the data structure definition)";

    Enumeration en = db.getDisplayTables();
    String result = "";
    while (en.hasMoreElements()) {
      result += en.nextElement().toString();
    }
    System.err.println(expected);
    System.err.println(result);
    assertEquals(expected, result);
  }

  /**
   * Test method for 'org.melati.poem.Database.sqlQuery(String)'
   */
  public void testSqlQuery() {
    String query = "select * from " + db.getUserTable().quotedName();
    ResultSet rs = db.sqlQuery(query);
    int count = 0;
    try {
      while (rs.next())
        count++;
    } catch (SQLException e) {
      e.printStackTrace();
      fail();
    }
    assertEquals(2, count);

  }

  /**
   * Test method for 'org.melati.poem.Database.hasCapability(User, Capability)'
   */
  public void testHasCapability() {
    assertTrue(db.hasCapability(db.getUserTable().administratorUser(), db
            .getCanAdminister()));
  }

  /**
   * Test method for 'org.melati.poem.Database.administerCapability()'
   */
  public void testAdministerCapability() {
    Capability c = db.administerCapability();
    assertEquals("_administer_", c.toString());
  }

  /**
   * Test method for 'org.melati.poem.Database.getCanAdminister()'
   */
  public void testGetCanAdminister() {
    assertNull(db.getCanAdminister());
  }

  /**
   * @see org.melati.poem.UserTable#guestUser()
   */
  public final void testGuestUser() {
    User u = db.getUserTable().guestUser();
    assertEquals(u.getLogin(), "_guest_");
  }

  public final void testAdministratorUser() {
    User u = db.getUserTable().administratorUser();
    assertEquals(u.getPassword(), "FIXME");
    // TODO Implement administratorUser().
  }

  /**
   * Test method for 'org.melati.poem.Database.referencesTo(Table)'
   */
  public void testReferencesToTable() {
    String expected = "groupmembership.user: reference to user "
            + "(INT (org.melati.poem.ReferencePoemType)) "
            + "(from the data structure definition)";
    String result = "";
    Enumeration en = db.referencesTo(db.getUserTable());
    while (en.hasMoreElements())
      result += en.nextElement();
    assertEquals(expected, result);
  }

  /**
   * Test method for 'org.melati.poem.Database.getDbms()'
   */
  public void testGetDbms() {
    Dbms dbms = db.getDbms();
    System.err.println(dbms);
  }

  /**
   * Test method for 'org.melati.poem.Database.toString()'
   */
  public void testToString() {
    String name = db.toString();
    System.err.println(name);
    assertTrue(name.endsWith(getDbName()));
  }

  /**
   * Test method for 'org.melati.poem.Database.logSQL()'
   */
  public void testLogSQL() {
    assertFalse(db.logSQL());
  }

  /**
   * Test method for 'org.melati.poem.Database.setLogSQL(boolean)'
   */
  public void testSetLogSQL() {
    assertFalse(db.logSQL());
    db.setLogSQL(true);
    assertTrue(db.logSQL());
  }

}
