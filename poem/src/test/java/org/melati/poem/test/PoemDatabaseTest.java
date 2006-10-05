package org.melati.poem.test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Enumeration;

import org.melati.poem.Capability;
import org.melati.poem.ColumnInfo;
import org.melati.poem.PoemDatabase;
import org.melati.poem.PoemThread;
import org.melati.poem.TableInfo;
import org.melati.poem.User;
import org.melati.poem.UserTable;
import org.melati.poem.dbms.Dbms;
import org.melati.poem.Database;

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

  /**
   * @see TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
  }

  /**
   * @see org.melati.poem.generated.PoemDatabaseBase#getUserTable()
   * @see org.melati.poem.Database#getTable(String)
   */
  public void testGetUserTable() {
    UserTable ut1 = getDb().getUserTable();
    UserTable ut2 = (UserTable)getDb().getTable("user");
    assertEquals(ut1, ut2);
  }

  /**
   * @see org.melati.poem.Database#transactionsMax()
   */
  public void testTransactionsMax() {
    assertEquals(4, getDb().transactionsMax());
  }

  /**
   * @see org.melati.poem.Database#getDisplayTables()
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

    Enumeration en = getDb().getDisplayTables();
    String result = "";
    while (en.hasMoreElements()) {
      result += en.nextElement().toString();
    }
    System.err.println(expected);
    System.err.println(result);
    assertEquals(expected, result);
  }

  /**
   * @see org.melati.poem.Database#sqlQuery(String)
   */
  public void testSqlQuery() {
    String query = "select * from " + getDb().getUserTable().quotedName();
    ResultSet rs = getDb().sqlQuery(query);
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
   * @see org.melati.poem.Database#hasCapability(User, Capability)
   */
  public void testHasCapability() {
    assertTrue(getDb().hasCapability(getDb().getUserTable().administratorUser(), 
        getDb().getCanAdminister()));
  }

  /**
   * @see org.melati.poem.Database#administerCapability()
   */
  public void testAdministerCapability() {
    Capability c = getDb().administerCapability();
    assertEquals("_administer_", c.toString());
  }

  /**
   * @see org.melati.poem.Database#getCanAdminister()
   */
  public void testGetCanAdminister() {
    assertNull(getDb().getCanAdminister());
  }

  /**
   * @see org.melati.poem.UserTable#guestUser()
   */
  public final void testGuestUser() {
    User u = getDb().getUserTable().guestUser();
    assertEquals(u.getLogin(), "_guest_");
  }

  /**
   * @see org.melati.poem.UserTable#administratorUser()
   */
  public final void testAdministratorUser() {
    User u = getDb().getUserTable().administratorUser();
    assertEquals(u.getPassword(), "FIXME");
  }

  /**
   * @see org.melati.poem.Database#referencesTo(Table)
   */
  public void testReferencesToTable() {
    String expected = "groupmembership.user: reference to user "
            + "(INT (org.melati.poem.ReferencePoemType)) "
            + "(from the data structure definition)";
    String result = "";
    Enumeration en = getDb().referencesTo(getDb().getUserTable());
    while (en.hasMoreElements())
      result += en.nextElement();
    assertEquals(expected, result);
  }

  /**
   * @see org.melati.poem.Database#getDbms()
   */
  public void testGetDbms() {
    Dbms dbms = getDb().getDbms();
    System.err.println(dbms);
  }

  /**
   * @see org.melati.poem.Database#toString()
   */
  public void testToString() {
    String name = getDb().toString();
    System.err.println(name);
    assertTrue(name.endsWith(getDbName()));
  }

  /**
   * @see org.melati.poem.Database#logSQL()
   */
  public void testLogSQL() {
    assertFalse(getDb().logSQL());
  }

  /**
   * @see org.melati.poem.Database#setLogSQL(boolean)
   */
  public void testSetLogSQL() {
    assertFalse(getDb().logSQL());
    getDb().setLogSQL(true);
    assertTrue(getDb().logSQL());
  }
  
  /**
   * @see Database#connect(String, String, String, String, int)
   */
  public void testConnect() {
    
    PoemDatabase db = new PoemDatabase();
    db.connect("org.melati.poem.dbms.Hsqldb",
                            "jdbc:hsqldb:/dist/melati/db/m2", "sa", "", 3); 
    assertTrue(db.getClass().getName() == "org.melati.poem.PoemDatabase"); 
    db.shutdown();
    db.disconnect();
  }

  /**
   * @see org.melati.poem.Database#disconnect()
   */
  public void testDisconnect() {
    // see above
  }

  /**
   * @see org.melati.poem.Database#shutdown()
   */
  public void testShutdown() {
    // see above
  }

  /**
   * @see org.melati.poem.Database#addTableAndCommit(TableInfo, String)
   */
  public void testAddTableAndCommit() {
    TableInfo info = (TableInfo)getDb().getTableInfoTable().newPersistent();
    info.setName("junit");
    info.setDisplayname("Junit created table");
    info.setDisplayorder(13);
    info.setSeqcached(new Boolean(false));
    info.setCategory_unsafe(new Integer(1));
    info.setCachelimit(0);
    info.makePersistent();
    getDb().addTableAndCommit(info, "id");
    Enumeration cols = getDb().getColumnInfoTable().
                           getTableinfoColumn().selectionWhereEq(info.troid());
     while (cols.hasMoreElements()) {
       ColumnInfo c = (ColumnInfo)cols.nextElement();
       c.delete();
     }
    info.delete();
    getDb().sqlUpdate("DROP TABLE JUNIT");
    PoemThread.commit();
    getDb().shutdown();
    getDb().disconnect();

  }

  /**
   * @see org.melati.poem.Database#addConstraints()
   */
  public void testAddConstraints() {

  }

  /**
   * @see org.melati.poem.Database#setTransactionsMax(int)
   */
  public void testSetTransactionsMax() {

  }

  /**
   * @see org.melati.poem.Database#getTransactionsCount()
   */
  public void testGetTransactionsCount() {

  }

  /**
   * @see org.melati.poem.Database#getFreeTransactionsCount()
   */
  public void testGetFreeTransactionsCount() {

  }

  /**
   * @see org.melati.poem.Database#poemTransaction(int)
   */
  public void testPoemTransaction() {

  }

  /**
   * @see org.melati.poem.Database#transaction(int)
   */
  public void testTransaction() {

  }

  /**
   * @see org.melati.poem.Database#isFree(PoemTransaction)
   */
  public void testIsFree() {

  }

  /**
   * @see org.melati.poem.Database#beginExclusiveLock()
   */
  public void testBeginExclusiveLock() {

  }

  /**
   * @see org.melati.poem.Database#endExclusiveLock()
   */
  public void testEndExclusiveLock() {

  }

  /** 
   * @see org.melati.poem.Database#inSession(AccessToken, PoemTask)
   */
  public void testInSession() {

  }

  /**
   * @see org.melati.poem.Database#beginSession(AccessToken)
   */
  public void testBeginSession() {

  }

  /** 
   * @see org.melati.poem.Database#endSession()
   */
  public void testEndSession() {

  }

  /**
   * @see org.melati.poem.Database#inCommittedTransaction(AccessToken, PoemTask)
   */
  public void testInCommittedTransaction() {

  }

  /**
   * @see org.melati.poem.Database#getTable(String)
   */
  public void testGetTable() {

  }

  /**
   * @see org.melati.poem.Database#tables()
   */
  public void testTables() {

  }

  /**
   * @see org.melati.poem.Database#columns()
   */
  public void testColumns() {

  }

  /**
   * @see org.melati.poem.Database#getTableInfoTable()
   */
  public void testGetTableInfoTable() {

  }

  /**
   * @see org.melati.poem.Database#getTableCategoryTable()
   */
  public void testGetTableCategoryTable() {

  }

  /**
   * @see org.melati.poem.Database#getColumnInfoTable()
   */
  public void testGetColumnInfoTable() {

  }

  /**
   * @see org.melati.poem.Database#getCapabilityTable()
   */
  public void testGetCapabilityTable() {

  }

  /**
   * @see org.melati.poem.Database#getGroupTable()
   */
  public void testGetGroupTable() {

  }

  /**
   * @see org.melati.poem.Database#getGroupMembershipTable()
   */
  public void testGetGroupMembershipTable() {

  }

  /**
   * @see org.melati.poem.Database#getGroupCapabilityTable()
   */
  public void testGetGroupCapabilityTable() {

  }

  /**
   * @see org.melati.poem.Database#getSettingTable()
   */
  public void testGetSettingTable() {

  }

  /**
   * @see org.melati.poem.Database#sqlUpdate(String)
   */
  public void testSqlUpdate() {

  }

  /**
   * @see org.melati.poem.Database#givesCapabilitySQL(User, Capability)
   */
  public void testGivesCapabilitySQL() {

  }

  /**
   * @see org.melati.poem.Database#guestAccessToken()
   */
  public void testGuestAccessToken() {

  }

  /**
   * @see org.melati.poem.Database#setCanAdminister()
   */
  public void testSetCanAdminister() {

  }

  /**
   * @see org.melati.poem.Database#setCanAdminister(String)
   */
  public void testSetCanAdministerString() {

  }

  /**
   * @see org.melati.poem.Database#trimCache(int)
   */
  public void testTrimCache() {

  }

  /**
   * @see org.melati.poem.Database#uncacheContents()
   */
  public void testUncacheContents() {

  }

  /**
   * @see org.melati.poem.Database.referencesTo(Persistent)
   */
  public void testReferencesToPersistent() {

  }

  /**
   * @see org.melati.poem.Database.dumpCacheAnalysis()
   */
  public void testDumpCacheAnalysis() {

  }

  /**
   * @see org.melati.poem.Database.dump()
   */
  public void testDump() {

  }

  /**
   * @see org.melati.poem.Database.quotedName(String)
   */
  public void testQuotedName() {

  }

  /**
   * @see org.melati.poem.Database#getCommittedConnection()
   */
  public void testGetCommittedConnection() {

  }

  /**
   * @see org.melati.poem.Database#logCommits()
   */
  public void testLogCommits() {

  }

  /**
   * @see org.melati.poem.Database#setLogCommits(boolean)
   */
  public void testSetLogCommits() {

  }


}
