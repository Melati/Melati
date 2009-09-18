/**
 * 
 */
package org.melati.poem.test.throwing;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import org.melati.poem.Database;
import org.melati.poem.PoemDatabaseFactory;
import org.melati.poem.SQLPoemException;
import org.melati.poem.SQLSeriousPoemException;
import org.melati.poem.UnexpectedExceptionPoemException;
import org.melati.poem.dbms.test.sql.Thrower;

/**
 * @author timp
 * @since 10 Feb 2007
 * 
 */
public class PoemDatabaseTest extends org.melati.poem.test.PoemDatabaseTest {

  /**
   * @param arg0
   */
  public PoemDatabaseTest(String arg0) {
    super(arg0);
  }

  protected void setUp() throws Exception {
    PoemDatabaseFactory.removeDatabase(getDatabaseName());
    super.setUp();
    assertEquals("org.melati.poem.dbms.test.HsqldbThrower",getDb().getDbms().getClass().getName());
  }

  protected void tearDown() throws Exception {
    try { 
      super.tearDown();
    } finally { 
      PoemDatabaseFactory.removeDatabase(getDatabaseName());
    }
  }

  public Database getDatabase(String name) {
    maxTrans = 4;
    Database db = PoemDatabaseFactory.getDatabase(name, 
        "jdbc:hsqldb:mem:" + name,
        "sa", 
        "",
        "org.melati.poem.PoemDatabase",
        "org.melati.poem.dbms.test.HsqldbThrower", 
        false, 
        false, 
        false, maxTrans);
    return db;
  }

  public void testAddConstraints() {
    // ThrowingConnection.startThrowing("prepareStatement");

    // super.testAddConstraints();
    // ThrowingConnection.stopThrowing("prepareStatement");
  }

  public void testAdministerCapability() {
    // ThrowingConnection.startThrowing("prepareStatement");

    // super.testAdministerCapability();
    // ThrowingConnection.stopThrowing("prepareStatement");
  }

  public void testBeginExclusiveLock() {
    // ThrowingConnection.startThrowing("prepareStatement");

    // super.testBeginExclusiveLock();
    // ThrowingConnection.stopThrowing("prepareStatement");
  }

  public void testBeginSession() {
    // ThrowingConnection.startThrowing("prepareStatement");

    // super.testBeginSession();
    // ThrowingConnection.stopThrowing("prepareStatement");
  }

  public void testColumns() {
    // ThrowingConnection.startThrowing("prepareStatement");

    // super.testColumns();
    // ThrowingConnection.stopThrowing("prepareStatement");
  }

  public void testConnect() {
    // ThrowingConnection.startThrowing("prepareStatement");

    // super.testConnect();
    // ThrowingConnection.stopThrowing("prepareStatement");
  }

  public void testDisconnect() {
    // ThrowingConnection.startThrowing("prepareStatement");

    // super.testDisconnect();
    // ThrowingConnection.stopThrowing("prepareStatement");
  }

  public void testDump() {
    // ThrowingConnection.startThrowing("prepareStatement");

    // super.testDump();
    // ThrowingConnection.stopThrowing("prepareStatement");
  }

  public void testDumpCacheAnalysis() {
    // ThrowingConnection.startThrowing("prepareStatement");

    // super.testDumpCacheAnalysis();
    // ThrowingConnection.stopThrowing("prepareStatement");
  }

  public void testEndExclusiveLock() {
    // ThrowingConnection.startThrowing("prepareStatement");

    // super.testEndExclusiveLock();
    // ThrowingConnection.stopThrowing("prepareStatement");
  }

  public void testEndSession() {
    // ThrowingConnection.startThrowing("prepareStatement");

    // super.testEndSession();
    // ThrowingConnection.stopThrowing("prepareStatement");
  }

  public void testGetCanAdminister() {
    // ThrowingConnection.startThrowing("prepareStatement");

    // super.testGetCanAdminister();
    // ThrowingConnection.stopThrowing("prepareStatement");
  }

  public void testGetCapabilityTable() {
    // ThrowingConnection.startThrowing("prepareStatement");

    // super.testGetCapabilityTable();
    // ThrowingConnection.stopThrowing("prepareStatement");
  }

  public void testGetColumnInfoTable() {
    // ThrowingConnection.startThrowing("prepareStatement");

    // super.testGetColumnInfoTable();
    // ThrowingConnection.stopThrowing("prepareStatement");
  }

  public void testGetCommittedConnection() {
    // ThrowingConnection.startThrowing("prepareStatement");

    // super.testGetCommittedConnection();
    // ThrowingConnection.stopThrowing("prepareStatement");
  }

  public void testGetDbms() {
    // ThrowingConnection.startThrowing("prepareStatement");

    // super.testGetDbms();
    // ThrowingConnection.stopThrowing("prepareStatement");
  }

  public void testGetDisplayTables() {
    // ThrowingConnection.startThrowing("prepareStatement");

    // super.testGetDisplayTables();
    // ThrowingConnection.stopThrowing("prepareStatement");
  }

  public void testGetFreeTransactionsCount() {
    // ThrowingConnection.startThrowing("prepareStatement");

    // super.testGetFreeTransactionsCount();
    // ThrowingConnection.stopThrowing("prepareStatement");
  }

  public void testGetGroupCapabilityTable() {
    // ThrowingConnection.startThrowing("prepareStatement");

    // super.testGetGroupCapabilityTable();
    // ThrowingConnection.stopThrowing("prepareStatement");
  }

  public void testGetGroupMembershipTable() {
    // ThrowingConnection.startThrowing("prepareStatement");

    // super.testGetGroupMembershipTable();
    // ThrowingConnection.stopThrowing("prepareStatement");
  }

  public void testGetGroupTable() {
    // ThrowingConnection.startThrowing("prepareStatement");

    // super.testGetGroupTable();
    // ThrowingConnection.stopThrowing("prepareStatement");
  }

  public void testGetSettingTable() {
    // ThrowingConnection.startThrowing("prepareStatement");

    // super.testGetSettingTable();
    // ThrowingConnection.stopThrowing("prepareStatement");
  }

  public void testGetTable() {
    // ThrowingConnection.startThrowing("prepareStatement");

    // super.testGetTable();
    // ThrowingConnection.stopThrowing("prepareStatement");
  }

  public void testGetTableCategoryTable() {
    // ThrowingConnection.startThrowing("prepareStatement");

    // super.testGetTableCategoryTable();
    // ThrowingConnection.stopThrowing("prepareStatement");
  }

  public void testGetTableInfoTable() {
    // ThrowingConnection.startThrowing("prepareStatement");

    // super.testGetTableInfoTable();
    // ThrowingConnection.stopThrowing("prepareStatement");
  }

  public void testGetTransactionsCount() {
    // ThrowingConnection.startThrowing("prepareStatement");

    // super.testGetTransactionsCount();
    // ThrowingConnection.stopThrowing("prepareStatement");
  }

  public void testGetUserTable() {
    // ThrowingConnection.startThrowing("prepareStatement");

    // super.testGetUserTable();
    // ThrowingConnection.stopThrowing("prepareStatement");
  }

  public void testGivesCapabilitySQL() {
    //ThrowingResultSet.startThrowing("next");
    //super.testGivesCapabilitySQL();
    //ThrowingResultSet.stopThrowing("next");
  }

  public void testGuestAccessToken() {
    // ThrowingConnection.startThrowing("prepareStatement");

    // super.testGuestAccessToken();
    // ThrowingConnection.stopThrowing("prepareStatement");
  }

  public void testHasCapability() {
    Thrower.startThrowing(Statement.class, "executeQuery");
    try { 
      super.testHasCapability();
    } catch (UnexpectedExceptionPoemException e) { 
      assertEquals("Statement bombed", e.innermostException().getMessage());      
      Thrower.stopThrowing(Statement.class, "executeQuery");
    }
    Thrower.startThrowing(ResultSet.class, "next");
    Thrower.startThrowing(ResultSet.class, "close");
    try { 
      super.testHasCapability();
    } catch (SQLSeriousPoemException e) { 
      assertEquals("ResultSet bombed", e.innermostException().getMessage());      
      Thrower.stopThrowing(ResultSet.class, "next");
      Thrower.stopThrowing(ResultSet.class, "close");
    }
  }

  public void testInSession() {
    // ThrowingConnection.startThrowing("prepareStatement");

    // super.testInSession();
    // ThrowingConnection.stopThrowing("prepareStatement");
  }

  public void testIsFree() {
    // ThrowingConnection.startThrowing("prepareStatement");

    // super.testIsFree();
    // ThrowingConnection.stopThrowing("prepareStatement");
  }

  public void testLogCommits() {
    // ThrowingConnection.startThrowing("prepareStatement");

    // super.testLogCommits();
    // ThrowingConnection.stopThrowing("prepareStatement");
  }

  public void testLogSQL() {
    // ThrowingConnection.startThrowing("prepareStatement");

    // super.testLogSQL();
    // ThrowingConnection.stopThrowing("prepareStatement");
  }

  public void testPoemTransaction() {
    // ThrowingConnection.startThrowing("prepareStatement");

    // super.testPoemTransaction();
    // ThrowingConnection.stopThrowing("prepareStatement");
  }

  public void testQuotedName() {
    // ThrowingConnection.startThrowing("prepareStatement");

    // super.testQuotedName();
    // ThrowingConnection.stopThrowing("prepareStatement");
  }

  public void testReferencesToPersistent() {
    Thrower.startThrowing(Connection.class, "prepareStatement");
    try {
      super.testReferencesToPersistent();
      fail("Should have bombed");
    } catch (SQLPoemException e) {
      assertEquals("Connection bombed", e.innermostException().getMessage());
      Thrower.stopThrowing(Connection.class, "prepareStatement");
    }
  }

  public void testReferencesToTable() {
    // ThrowingConnection.startThrowing("prepareStatement");

    // super.testReferencesToTable();
    // ThrowingConnection.stopThrowing("prepareStatement");
  }

  public void testSetCanAdminister() {
    Thrower.startThrowing(Connection.class, "prepareStatement");
    try {
      getDb().setCanAdminister();
      assertEquals(getDb().getCapabilityTable().get("_administer_"), getDb()
              .getCanAdminister());
      getDb().setCanAdminister("testing");
      assertEquals(getDb().getCapabilityTable().get("testing"), getDb()
              .getCanAdminister());
      getDb().setCanAdminister();
      getDb().getCapabilityTable().getNameColumn().firstWhereEq("testing")
      .delete();
      fail("Should have bombed");
    } catch (SQLPoemException e) {
      assertEquals("Connection bombed", e.innermostException().getMessage());
      Thrower.stopThrowing(Connection.class, "prepareStatement");
    }
    getDb().getCapabilityTable().getNameColumn().firstWhereEq("testing")
    .delete();
  }

  public void testSetLogCommits() {
    // ThrowingConnection.startThrowing("prepareStatement");

    // super.testSetLogCommits();
    // ThrowingConnection.stopThrowing("prepareStatement");
  }

  public void testSetLogSQL() {
    // ThrowingConnection.startThrowing("prepareStatement");

    // super.testSetLogSQL();
    // ThrowingConnection.stopThrowing("prepareStatement");
  }

  public void testSetTransactionsMax() {
    // ThrowingConnection.startThrowing("prepareStatement");

    // super.testSetTransactionsMax();
    // ThrowingConnection.stopThrowing("prepareStatement");
  }

  public void testShutdown() {
    // ThrowingConnection.startThrowing("prepareStatement");

    // super.testShutdown();
    // ThrowingConnection.stopThrowing("prepareStatement");
  }

  public void testSqlQuery() {
    // ThrowingConnection.startThrowing("prepareStatement");

    // super.testSqlQuery();
    // ThrowingConnection.stopThrowing("prepareStatement");
  }

  public void testSqlUpdate() {
    // ThrowingConnection.startThrowing("prepareStatement");

    // super.testSqlUpdate();
    // ThrowingConnection.stopThrowing("prepareStatement");
  }

  public void testSqlUpdate1() {
    // ThrowingConnection.startThrowing("prepareStatement");

    // super.testSqlUpdate1();
    // ThrowingConnection.stopThrowing("prepareStatement");
  }

  public void testSqlUpdate2() {
    // ThrowingConnection.startThrowing("prepareStatement");

    // super.testSqlUpdate2();
    // ThrowingConnection.stopThrowing("prepareStatement");
  }

  public void testTables() {
    // ThrowingConnection.startThrowing("prepareStatement");

    // super.testTables();
    // ThrowingConnection.stopThrowing("prepareStatement");
  }

  public void testToString() {
    // ThrowingConnection.startThrowing("prepareStatement");

    // super.testToString();
    // ThrowingConnection.stopThrowing("prepareStatement");
  }

  public void testTransaction() {
    // ThrowingConnection.startThrowing("prepareStatement");

    // super.testTransaction();
    // ThrowingConnection.stopThrowing("prepareStatement");
  }

  public void testTransactionsMax() {
    // ThrowingConnection.startThrowing("prepareStatement");

    // super.testTransactionsMax();
    // ThrowingConnection.stopThrowing("prepareStatement");
  }

  public void testTrimCache() {
    // ThrowingConnection.startThrowing("prepareStatement");

    // super.testTrimCache();
    // ThrowingConnection.stopThrowing("prepareStatement");
  }

  public void testUncacheContents() {
    // ThrowingConnection.startThrowing("prepareStatement");

    // super.testUncacheContents();
    // ThrowingConnection.stopThrowing("prepareStatement");
  }

}
