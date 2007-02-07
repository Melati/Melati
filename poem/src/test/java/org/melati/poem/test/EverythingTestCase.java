package org.melati.poem.test;


import org.melati.poem.AccessToken;
import org.melati.poem.Database;
import org.melati.poem.PoemTask;

import junit.framework.Test;
import junit.framework.TestCase;

/**
 * A TestCase that runs in a Database session.
 * 
 * @author timp
 * @since 19-May-2006
 */
public abstract class EverythingTestCase extends PoemTestCase implements Test {

  /** Default db name */
  public static final String databaseName = "poemtest"; // change to Everything

  /**
   * Constructor.
   */
  public EverythingTestCase() {
    super();
  }

  /**
   * Constructor.
   * 
   * @param name
   */
  public EverythingTestCase(String name) {
    super(name);
  }
  /**
   * @see TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
  }

  /**
   * @see TestCase#tearDown()
   */
  protected void tearDown() throws Exception {
    if (!problem) {
      checkDbUnchanged();
      assertEquals("Not all transactions free", 4, getDb().getFreeTransactionsCount());
    }
  }
  
  /**
   * @return Returns the db.
   */
  public Database getDb() {
    Database db = getDb(databaseName);
    System.err.println(databaseName + ":" +db.toString());
    return db;
  }
  
  protected void checkDbUnchanged() {
    getDb().inSession(AccessToken.root, // HACK
        new PoemTask() {
          public void run() {
            databaseUnchanged();
          }
        });

  }
  protected void databaseUnchanged() { 
    assertEquals("Setting changed",0, getDb().getSettingTable().count());
    assertEquals("Group changed", 1, getDb().getGroupTable().count());
    assertEquals("GroupMembership changed", 1, getDb().getGroupMembershipTable().count());
    assertEquals("Capability changed", 5, getDb().getCapabilityTable().count());
    assertEquals("GroupCapability changed", 1, getDb().getGroupCapabilityTable().count());
    assertEquals("TableCategory changed", 4, getDb().getTableCategoryTable().count());
    assertEquals("User changed", 2, getDb().getUserTable().count());
    //dumpTable(getDb().getColumnInfoTable());
    // Until table.dropColumnAndCommit() arrives...
    //assertEquals("ColumnInfo changed", 156, getDb().getColumnInfoTable().count());
    assertEquals("TableInfo changed", 24, getDb().getTableInfoTable().count());
    checkTables(24);
  }
  

}