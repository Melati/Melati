/**
 * 
 */
package org.melati.poem.test.throwing;

import org.melati.poem.AccessToken;
import org.melati.poem.Capability;
import org.melati.poem.Database;
import org.melati.poem.PoemDatabase;
import org.melati.poem.PoemDatabaseFactory;
import org.melati.poem.PoemThread;
import org.melati.poem.ReconnectionPoemException;
import org.melati.poem.SQLPoemException;
import org.melati.poem.SQLSeriousPoemException;
import org.melati.poem.TableInfo;
import org.melati.poem.UnexpectedExceptionPoemException;
import org.melati.poem.UnificationPoemException;
import org.melati.poem.User;
import org.melati.poem.dbms.test.sql.ThrowingConnection;
import org.melati.poem.dbms.test.sql.ThrowingResultSet;
import org.melati.poem.test.PoemTestCase;

/**
 * @author timp
 * @since 25 Feb 2007
 *
 */
public class DatabaseTest extends org.melati.poem.test.DatabaseTest {

  /**
   * Constructor. 
   * @param name test name
   */
  public DatabaseTest(String name) {
    super(name);
  }

  protected void setUp() throws Exception {
    super.setUp();
  }

  protected void tearDown() throws Exception {
    super.tearDown();
    db = null;
    PoemDatabaseFactory.removeDatabase(PoemTestCase.databaseName);
  }

  private static Database db;
  /**
   * @return the db
   */
  private static Database getDb() {
    int maxTrans = 4;
    /* 
    db = PoemDatabaseFactory.getDatabase(PoemTestCase.databaseName, 
            "jdbc:hsqldb:mem:" + PoemTestCase.databaseName,
            "sa", 
            "",
            "org.melati.poem.PoemDatabase",
            "org.melati.poem.dbms.test.HsqldbThrower", 
            false, 
            false, 
            false, maxTrans);
    */
    db = new PoemDatabase();
    db.connect("org.melati.poem.dbms.test.HsqldbThrower", 
            "jdbc:hsqldb:mem:" + PoemTestCase.databaseName, 
            "sa", 
            "",
            maxTrans);
    assertTrue(db.getClass().getName() == "org.melati.poem.PoemDatabase");
    assertEquals(4, db.getFreeTransactionsCount());
    return db;
  }

  
  public void testAddConstraints() {
   // super.testAddConstraints();
  }

  public void testAddTableAndCommit() {
    //super.testAddTableAndCommit();
  }

  /**
   * Test method for {@link org.melati.poem.Database
   * #addTableAndCommit(org.melati.poem.TableInfo, java.lang.String)}.
   */
  public void testAddTableAndCommitThrowing() {
    Database db = getDb();
    db.beginSession(AccessToken.root);
    TableInfo info = (TableInfo)db.getTableInfoTable().newPersistent();
    info.setName("addedtable");
    info.setDisplayname("Junit created table");
    info.setDisplayorder(13);
    info.setSeqcached(new Boolean(false));
    info.setCategory_unsafe(new Integer(1));
    info.setCachelimit(0);
    info.makePersistent();
    PoemThread.commit();
    ThrowingConnection.startThrowing("getMetaData");
    try { 
      db.addTableAndCommit(info, "id");
      fail("Should have blown up");
    } catch (SQLPoemException e) {
      assertEquals("Connection bombed", e.innermostException().getMessage());      
    }
    ThrowingConnection.stopThrowing("getMetaData");
    db.sqlUpdate("DROP TABLE " + db.getDbms().getQuotedName("addedtable"));
    db.sqlUpdate("DROP TABLE " + db.getDbms().getQuotedName("columninfo"));
    db.sqlUpdate("DROP TABLE " + db.getDbms().getQuotedName("tableinfo"));
    PoemThread.commit();
    db.endSession();
  }

  public void testAdministerCapability() {
    //super.testAdministerCapability();
  }

  public void testAdministratorUser() {
    //super.testAdministratorUser();
  }

  public void testBeginExclusiveLock() {
    //super.testBeginExclusiveLock();
  }

  public void testBeginSession() {
    //super.testBeginSession();
  }

  public void testColumns() {
    //super.testColumns();
  }

  public void testConnect() {
    try { 
      getDb().connect("org.melati.poem.dbms.test.HsqldbThrower", 
              "jdbc:hsqldb:mem:" + PoemTestCase.databaseName,
              "sa", "", 8);
      fail("Should have blown up");
    } catch (ReconnectionPoemException e) {
      e = null;
    }
    PoemDatabaseFactory.removeDatabase(PoemTestCase.databaseName);
    ThrowingResultSet.startThrowing("close");     
    try { 
      getDb();
      fail("Should have blown up");
    } catch (SQLSeriousPoemException e) {
      assertEquals("ResultSet bombed", e.innermostException().getMessage());
    }
    ThrowingResultSet.stopThrowing("close");
  }

  /**
   * Provoke an SQLException within unifyWithDB().
   */
  public void testConnect2() {
    Database db = new PoemDatabase();
    ThrowingConnection.startThrowingAfter("getMetaData",1);     
    try { 
      db.connect("org.melati.poem.dbms.test.HsqldbThrower", 
              "jdbc:hsqldb:mem:" + PoemTestCase.databaseName, 
              "sa", 
              "",
              4);
      fail("Should have blown up");
    } catch (SQLPoemException e) {
      assertEquals("Connection bombed", e.innermostException().getMessage());
    }
    db.disconnect();
    assertEquals(0, db.getFreeTransactionsCount());
    db = null;
    ThrowingConnection.stopThrowing("getMetaData");
  }

  /**
   * Test method for {@link org.melati.poem.Database#connect(java.lang.String, java.lang.String, java.lang.String, java.lang.String, int)}.
   */
  public void testConnectThrowing3() {
    Database db = getDb();
    db.disconnect();
    ThrowingConnection.startThrowing("getMetaData");     
    try { 
      db.connect("org.melati.poem.dbms.test.HsqldbThrower", 
              "jdbc:hsqldb:mem:" + PoemTestCase.databaseName, 
              "sa", 
              "",
              4);
      fail("Should have blown up");
    } catch (UnificationPoemException e) {
      assertEquals("Connection bombed", e.innermostException().getMessage());
    }
    db.disconnect();
    assertEquals(0, db.getFreeTransactionsCount());
    db = null;
    PoemDatabaseFactory.removeDatabase(PoemTestCase.databaseName);
    ThrowingConnection.stopThrowing("getMetaData");
  }

  public void testDatabase() {
   // super.testDatabase();
  }

  public void testDisconnect() {
    Database db = getDb();
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
    PoemDatabaseFactory.removeDatabase(PoemTestCase.databaseName);
  }


  public void testDump() {
    //super.testDump();
  }

  public void testDumpCacheAnalysis() {
    //super.testDumpCacheAnalysis();
  }

  public void testEndExclusiveLock() {
    //super.testEndExclusiveLock();
  }

  public void testEndSession() {
    //super.testEndSession();
  }

  public void testGetCanAdminister() {
    // super.testGetCanAdminister();
  }

  public void testGetCapabilityTable() {
    // super.testGetCapabilityTable();
  }

  public void testGetColumnInfoTable() {
    // super.testGetColumnInfoTable();
  }

  public void testGetCommittedConnection() {
    //super.testGetCommittedConnection();
  }

  public void testGetDbms() {
    //super.testGetDbms();
  }

  public void testGetDisplayTables() {
    //super.testGetDisplayTables();
  }

  public void testGetFreeTransactionsCount() {
    //super.testGetFreeTransactionsCount();
  }

  public void testGetGroupCapabilityTable() {
    //super.testGetGroupCapabilityTable();
  }

  public void testGetGroupMembershipTable() {
    //super.testGetGroupMembershipTable();
  }

  public void testGetGroupTable() {
    //super.testGetGroupTable();
  }

  public void testGetQueryCount() {
    //super.testGetQueryCount();
  }

  public void testGetSettingTable() {
    //super.testGetSettingTable();
  }

  public void testGetTable() {
   // super.testGetTable();
  }

  public void testGetTableCategoryTable() {
    //super.testGetTableCategoryTable();
  }

  public void testGetTableInfoTable() {
    //super.testGetTableInfoTable();
  }

  public void testGetTransactionsCount() {
    //super.testGetTransactionsCount();
  }

  public void testGetUserTable() {
    //super.testGetUserTable();
  }

  public void testGivesCapabilitySQL() {
    //super.testGivesCapabilitySQL();
  }

  public void testGuestAccessToken() {
    //super.testGuestAccessToken();
  }

  public void testGuestUser() {
    //super.testGuestUser();
  }

  public void testHasCapability() {
    //super.testHasCapability();
  }

  /**
   * @see org.melati.poem.Database#hasCapability(User, Capability)
   */
  public void testHasCapabilityThrowing() {
    Database db = getDb();
    db.beginSession(AccessToken.root);
    ThrowingResultSet.startThrowing("next");
    try { 
      assertFalse(db.hasCapability(
          db.guestUser(), db.administerCapability()));
      fail("Should have bombed");
    } catch (SQLSeriousPoemException e) { 
      assertEquals("ResultSet bombed", e.innermostException().getMessage());
    }
    ThrowingResultSet.stopThrowing("next");

    ThrowingConnection.startThrowing("createStatement");
    try { 
      assertFalse(db.hasCapability(db.guestUser(), 
          db.administerCapability()));
      fail("Should have bombed");
    } catch (UnexpectedExceptionPoemException e) { 
      assertEquals("Connection bombed", e.innermostException().getMessage());
    }
    ThrowingConnection.stopThrowing("createStatement");
    db.endSession();
  }


  public void testInCommittedTransaction() {
    //super.testInCommittedTransaction();
  }

  public void testIncrementQueryCount() {
    //super.testIncrementQueryCount();
  }

  public void testInSession() {
    //super.testInSession();
  }

  public void testIsFree() {
    //super.testIsFree();
  }

  public void testLogCommits() {
    //super.testLogCommits();
  }

  public void testLogSQL() {
    //super.testLogSQL();
  }

  public void testPoemTransaction() {
    //super.testPoemTransaction();
  }

  public void testQuotedName() {
    //super.testQuotedName();
  }

  public void testReferencesToPersistent() {
    //super.testReferencesToPersistent();
  }

  public void testReferencesToTable() {
    //super.testReferencesToTable();
  }

  public void testSetCanAdminister() {
    super.testSetCanAdminister();
  }

  public void testSetCanAdministerString() {
    //super.testSetCanAdministerString();
  }

  public void testSetLogCommits() {
    //super.testSetLogCommits();
  }

  public void testSetLogSQL() {
    //super.testSetLogSQL();
  }

  public void testSetTransactionsMax() {
    //super.testSetTransactionsMax();
  }

  public void testSqlQuery() {
    //super.testSqlQuery();
  }

  public void testSqlUpdate() {
    //super.testSqlUpdate();
  }

  public void testTables() {
    //super.testTables();
  }

  public void testToString() {
    //super.testToString();
  }

  public void testTransaction() {
    //super.testTransaction();
  }

  public void testTransactionsMax() {
    //super.testTransactionsMax();
  }

  public void testTrimCache() {
    //super.testTrimCache();
  }

  public void testUncacheContents() {
    //super.testUncacheContents();
  }

}
