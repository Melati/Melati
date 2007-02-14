/**
 * 
 */
package org.melati.poem.test;



import org.melati.poem.AccessToken;
import org.melati.poem.Capability;
import org.melati.poem.Database;
import org.melati.poem.PoemDatabase;
import org.melati.poem.PoemThread;
import org.melati.poem.ReconnectionPoemException;
import org.melati.poem.SQLPoemException;
import org.melati.poem.SQLSeriousPoemException;
import org.melati.poem.UnificationPoemException;
import org.melati.poem.User;
import org.melati.poem.dbms.test.ThrowingConnection;
import org.melati.poem.dbms.test.ThrowingDatabaseMetaData;
import org.melati.poem.dbms.test.ThrowingResultSet;

import junit.framework.TestCase;

/**
 * Test db outside of PoemTestCase so that we do not run into 
 * session closing issues.
 * 
 * @author timp
 * @since 25 Jan 2007
 *
 */
public class DatabaseTest extends TestCase {

  /**
   * @param name
   */
  public DatabaseTest(String name) {
    super(name);
  }

  /**
   * {@inheritDoc}
   * @see junit.framework.TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
  }

  /**
   * {@inheritDoc}
   * @see junit.framework.TestCase#tearDown()
   */
  protected void tearDown() throws Exception {
    super.tearDown();
  }

  private static PoemDatabase db;
  /**
   * @return the db
   */
  private static Database getDb() {
    if (db != null)
      return db;
    else {
      db = new PoemDatabase();
      db.connect("org.melati.poem.dbms.Hsqldb", "jdbc:hsqldb:mem:m2",
        "sa", "", 8);
      assertEquals(8, db.getFreeTransactionsCount());
      assertTrue(db.getClass().getName() == "org.melati.poem.PoemDatabase");
      return db;
    }
  }
  private static Database getThrowingDb() { 
    if (db != null) db = null;
    db = new PoemDatabase();
    db.connect("org.melati.poem.dbms.test.HsqldbThrower", "jdbc:hsqldb:mem:m2",
      "sa", "", 8);
    assertEquals(8, db.getFreeTransactionsCount());
    assertTrue(db.getClass().getName() == "org.melati.poem.PoemDatabase");
    return db;

  }

  /**
   * Test method for {@link org.melati.poem.Database#Database()}.
   */
  public void testDatabase() {
    
  }

  /**
   * Test method for {@link org.melati.poem.Database#connect(java.lang.String, java.lang.String, java.lang.String, java.lang.String, int)}.
   */
  public void testConnect() { 
    try { 
      getDb().connect("org.melati.poem.dbms.Hsqldb", "jdbc:hsqldb:mem:m2",
          "sa", "", 8);
      fail("Should have blown up");
    } catch (ReconnectionPoemException e) {
      e = null;
    }
    assertTrue(db.getClass().getName() == "org.melati.poem.PoemDatabase");
    assertEquals(8, db.getFreeTransactionsCount());
    db.shutdown();
    db.disconnect();
    assertEquals(0, db.getFreeTransactionsCount());
    db = null;
  }
  /**
   * Test method for {@link org.melati.poem.Database#connect(java.lang.String, java.lang.String, java.lang.String, java.lang.String, int)}.
   */
  public void testConnectThrowing() { 
    ThrowingResultSet.startThrowing("next");     
    try { 
      getThrowingDb();
      fail("Should have blown up");
    } catch (UnificationPoemException e) {
      assertEquals("ResultSet bombed", e.innermostException().getMessage());
    }
    assertTrue(db.getClass().getName() == "org.melati.poem.PoemDatabase");
    assertEquals(8, db.getFreeTransactionsCount());
    db.shutdown();
    db.disconnect();
    assertEquals(0, db.getFreeTransactionsCount());
    db = null;
    ThrowingResultSet.stopThrowing("next");     
  }

  /**
   * Test method for {@link org.melati.poem.Database#connect(java.lang.String, java.lang.String, java.lang.String, java.lang.String, int)}.
   */
  public void testConnectThrowing2() { 
    ThrowingResultSet.startThrowing("close");     
    try { 
      getThrowingDb();
      fail("Should have blown up");
    } catch (SQLSeriousPoemException e) {
      assertEquals("ResultSet bombed", e.innermostException().getMessage());
    }
    assertTrue(db.getClass().getName() == "org.melati.poem.PoemDatabase");
    assertEquals(8, db.getFreeTransactionsCount());
    db.shutdown();
    db.disconnect();
    assertEquals(0, db.getFreeTransactionsCount());
    db = null;
    ThrowingResultSet.stopThrowing("close");     
  }
  /**
   * Test method for {@link org.melati.poem.Database#connect(java.lang.String, java.lang.String, java.lang.String, java.lang.String, int)}.
   */
  public void testConnectThrowing3() { 
    ThrowingConnection.startThrowing("getMetaData");     
    try { 
      getThrowingDb();
      fail("Should have blown up");
    } catch (UnificationPoemException e) {
      assertEquals("Connection bombed", e.innermostException().getMessage());
    }
    db.shutdown();
    db.disconnect();
    assertEquals(0, db.getFreeTransactionsCount());
    db = null;
    ThrowingConnection.stopThrowing("getMetaData");     
  }

  /**
   * Test method for {@link org.melati.poem.Database#connect(java.lang.String, java.lang.String, java.lang.String, java.lang.String, int)}.
   */
  public void testConnectThrowing4() { 
    ThrowingDatabaseMetaData.startThrowing("getTables");     
    try { 
      getThrowingDb();
      fail("Should have blown up");
    } catch (SQLPoemException e) {
      assertEquals("DatabaseMetaData bombed", e.innermostException().getMessage());
    }
    db.shutdown();
    db.disconnect();
    assertEquals(0, db.getFreeTransactionsCount());
    db = null;
    ThrowingDatabaseMetaData.stopThrowing("getTables");     
  }

  /**
   * Test method for {@link org.melati.poem.Database#disconnect()}.
   */
  public void testDisconnect() {
    
  }

  /**
   * Test method for {@link org.melati.poem.Database#disconnect()}.
   */
  public void testDisconnectThrowing() {
    getThrowingDb();
    db.shutdown();
    ThrowingConnection.startThrowing("close");     
    try { 
      db.disconnect();
      fail("Should have blown up");
    } catch (SQLPoemException e) {
      assertEquals("Connection bombed", e.innermostException().getMessage());
    }
    ThrowingConnection.stopThrowing("close");     
    db.disconnect();
    assertEquals(0, db.getFreeTransactionsCount());
    db = null;
    
  }

  /**
   * Test method for {@link org.melati.poem.Database#shutdown()}.
   */
  public void testShutdown() {
    
  }
  /**
   * Test method for {@link org.melati.poem.Database#shutdown()}.
   */
  public void testShutdownThrowing() {
    getThrowingDb();
    ThrowingConnection.startThrowing("isClosed");     
    try { 
      db.shutdown();
      fail("Should have blown up");
    } catch (SQLPoemException e) {
      assertEquals("Connection bombed", e.innermostException().getMessage());
    }
    ThrowingConnection.stopThrowing("isClosed");     
    db.disconnect();
    assertEquals(0, db.getFreeTransactionsCount());
    db = null;
    
  }

  /**
   * Test method for {@link org.melati.poem.Database#addTableAndCommit(org.melati.poem.TableInfo, java.lang.String)}.
   */
  public void testAddTableAndCommit() {
    
  }

  /**
   * Test method for {@link org.melati.poem.Database#addConstraints()}.
   */
  public void testAddConstraints() {
    getDb().addConstraints();    
  }

  /**
   * Test method for {@link org.melati.poem.Database#transactionsMax()}.
   */
  public void testTransactionsMax() {
    
  }

  /**
   * Test method for {@link org.melati.poem.Database#setTransactionsMax(int)}.
   */
  public void testSetTransactionsMax() {
    
  }

  /**
   * Test method for {@link org.melati.poem.Database#getTransactionsCount()}.
   */
  public void testGetTransactionsCount() {
    
  }

  /**
   * Test method for {@link org.melati.poem.Database#getFreeTransactionsCount()}.
   */
  public void testGetFreeTransactionsCount() {
    
  }

  /**
   * Test method for {@link org.melati.poem.Database#poemTransaction(int)}.
   */
  public void testPoemTransaction() {
    
  }

  /**
   * Test method for {@link org.melati.poem.Database#transaction(int)}.
   */
  public void testTransaction() {
    
  }

  /**
   * Test method for {@link org.melati.poem.Database#isFree(org.melati.poem.PoemTransaction)}.
   */
  public void testIsFree() {
    
  }

  /**
   * Test method for {@link org.melati.poem.Database#beginExclusiveLock()}.
   */
  public void testBeginExclusiveLock() {
    
  }

  /**
   * Test method for {@link org.melati.poem.Database#endExclusiveLock()}.
   */
  public void testEndExclusiveLock() {
    
  }

  /**
   * Test method for {@link org.melati.poem.Database#inSession(org.melati.poem.AccessToken, org.melati.poem.PoemTask)}.
   */
  public void testInSession() {
    
  }

  /**
   * Test method for {@link org.melati.poem.Database#beginSession(org.melati.poem.AccessToken)}.
   */
  public void testBeginSession() {
    
  }

  /**
   * Test method for {@link org.melati.poem.Database#endSession()}.
   */
  public void testEndSession() {
    getDb().beginSession(AccessToken.root);
    assertEquals(
            PoemThread.database().getTransactionsCount(), 
            PoemThread.database().getFreeTransactionsCount() + 1);
    getDb().endSession();
  }

  /**
   * Test method for {@link org.melati.poem.Database#inCommittedTransaction(org.melati.poem.AccessToken, org.melati.poem.PoemTask)}.
   */
  public void testInCommittedTransaction() {
    
  }

  /**
   * Test method for {@link org.melati.poem.Database#getTable(java.lang.String)}.
   */
  public void testGetTable() {
    
  }

  /**
   * Test method for {@link org.melati.poem.Database#tables()}.
   */
  public void testTables() {
    
  }

  /**
   * Test method for {@link org.melati.poem.Database#getDisplayTables()}.
   */
  public void testGetDisplayTables() {
    
  }

  /**
   * Test method for {@link org.melati.poem.Database#columns()}.
   */
  public void testColumns() {
    
  }

  /**
   * Test method for {@link org.melati.poem.Database#getTableInfoTable()}.
   */
  public void testGetTableInfoTable() {
    
  }

  /**
   * Test method for {@link org.melati.poem.Database#getTableCategoryTable()}.
   */
  public void testGetTableCategoryTable() {
    
  }

  /**
   * Test method for {@link org.melati.poem.Database#getColumnInfoTable()}.
   */
  public void testGetColumnInfoTable() {
    
  }

  /**
   * Test method for {@link org.melati.poem.Database#getCapabilityTable()}.
   */
  public void testGetCapabilityTable() {
    
  }

  /**
   * Test method for {@link org.melati.poem.Database#getUserTable()}.
   */
  public void testGetUserTable() {
    
  }

  /**
   * Test method for {@link org.melati.poem.Database#getGroupTable()}.
   */
  public void testGetGroupTable() {
    
  }

  /**
   * Test method for {@link org.melati.poem.Database#getGroupMembershipTable()}.
   */
  public void testGetGroupMembershipTable() {
    
  }

  /**
   * Test method for {@link org.melati.poem.Database#getGroupCapabilityTable()}.
   */
  public void testGetGroupCapabilityTable() {
    
  }

  /**
   * Test method for {@link org.melati.poem.Database#getSettingTable()}.
   */
  public void testGetSettingTable() {
    
  }

  /**
   * Test method for {@link org.melati.poem.Database#sqlQuery(java.lang.String)}.
   */
  public void testSqlQuery() {
    
  }

  /**
   * Test method for {@link org.melati.poem.Database#sqlUpdate(java.lang.String)}.
   */
  public void testSqlUpdate() {
    
  }

  /**
   * Test method for {@link org.melati.poem.Database#guestUser()}.
   */
  public void testGuestUser() {
    
  }

  /**
   * Test method for {@link org.melati.poem.Database#administratorUser()}.
   */
  public void testAdministratorUser() {
    
  }

  /**
   * Test method for {@link org.melati.poem.Database#
   * givesCapabilitySQL(org.melati.poem.User, org.melati.poem.Capability)}.
   */
  public void testGivesCapabilitySQL() {
  }

  /**
   * Test method for {@link org.melati.poem.Database#hasCapability(org.melati.poem.User, org.melati.poem.Capability)}.
   */
  public void testHasCapability() {
    
  }
  /**
   * @see org.melati.poem.Database#hasCapability(User, Capability)
   */
  public void testHasCapabilityThrowing() {
    Database db = getThrowingDb();
    db.beginSession(AccessToken.root);
    ThrowingResultSet.startThrowing("next");
    try { 
      assertFalse(db.hasCapability(
          db.guestUser(), db.administerCapability()));
      fail("Should have bombed");
    } catch (SQLSeriousPoemException e) { 
      assertEquals("ResultSet bombed", e.innermostException().getMessage());
    }
    ThrowingResultSet.startThrowing("close");
    try { 
      assertFalse(db.hasCapability(db.guestUser(), 
          db.administerCapability()));
      fail("Should have bombed");
    } catch (SQLSeriousPoemException e) { 
      assertEquals("ResultSet bombed", e.innermostException().getMessage());
    }
    ThrowingResultSet.stopThrowing("next");
    ThrowingResultSet.stopThrowing("close");
    db.endSession();
  }

  /**
   * Test method for {@link org.melati.poem.Database#guestAccessToken()}.
   */
  public void testGuestAccessToken() {
    
  }

  /**
   * If we are not in a  session then an unsafe read is performed.
   * Test method for {@link org.melati.poem.Database#administerCapability()}.
   */
  public void testAdministerCapability() {
    Capability c = getDb().administerCapability();
    assertEquals("_administer_", c.toString());    
  }

  /**
   * Test method for {@link org.melati.poem.Database#getCanAdminister()}.
   */
  public void testGetCanAdminister() {
    
  }

  /**
   * Test method for {@link org.melati.poem.Database#setCanAdminister()}.
   */
  public void testSetCanAdminister() {
    
  }

  /**
   * Test method for {@link org.melati.poem.Database#setCanAdminister(java.lang.String)}.
   */
  public void testSetCanAdministerString() {
    
  }

  /**
   * Test method for {@link org.melati.poem.Database#trimCache(int)}.
   */
  public void testTrimCache() {
    
  }

  /**
   * Test method for {@link org.melati.poem.Database#uncacheContents()}.
   */
  public void testUncacheContents() {
    
  }

  /**
   * Test method for {@link org.melati.poem.Database#referencesTo(org.melati.poem.Persistent)}.
   */
  public void testReferencesToPersistent() {
    
  }

  /**
   * Test method for {@link org.melati.poem.Database#referencesTo(org.melati.poem.Table)}.
   */
  public void testReferencesToTable() {
    
  }

  /**
   * Test method for {@link org.melati.poem.Database#dumpCacheAnalysis()}.
   */
  public void testDumpCacheAnalysis() {
    
  }

  /**
   * Test method for {@link org.melati.poem.Database#dump()}.
   */
  public void testDump() {
    
  }

  /**
   * Test method for {@link org.melati.poem.Database#getDbms()}.
   */
  public void testGetDbms() {
    
  }

  /**
   * Test method for {@link org.melati.poem.Database#quotedName(java.lang.String)}.
   */
  public void testQuotedName() {
    
  }

  /**
   * Test method for {@link org.melati.poem.Database#toString()}.
   */
  public void testToString() {
    
  }

  /**
   * Test method for {@link org.melati.poem.Database#getCommittedConnection()}.
   */
  public void testGetCommittedConnection() {
    
  }

  /**
   * Test method for {@link org.melati.poem.Database#logSQL()}.
   */
  public void testLogSQL() {
    
  }

  /**
   * Test method for {@link org.melati.poem.Database#setLogSQL(boolean)}.
   */
  public void testSetLogSQL() {
    
  }

  /**
   * Test method for {@link org.melati.poem.Database#logCommits()}.
   */
  public void testLogCommits() {
    
  }

  /**
   * Test method for {@link org.melati.poem.Database#setLogCommits(boolean)}.
   */
  public void testSetLogCommits() {
    
  }

  /**
   * Test method for {@link org.melati.poem.Database#getQueryCount()}.
   */
  public void testGetQueryCount() {
    
  }

  /**
   * Test method for {@link org.melati.poem.Database#incrementQueryCount()}.
   */
  public void testIncrementQueryCount() {
    
  }

}
