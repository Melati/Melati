package org.melati.poem.test;


import org.melati.poem.Database;

import junit.framework.Test;

/**
 * A TestCase that runs in a Database session.
 * 
 * @author timp
 * @since 19-May-2006
 */
public abstract class EverythingTestCase extends PoemTestCase implements Test {

  /** Default db name */
  public static final String databaseName = "everything"; 

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
   * @return Returns the db.
   */
  public Database getDb() {
    return getDb(databaseName);
  }
  /**
   * @return the db name
   */
  public String getDatabaseName() {
    return databaseName;
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
    if (getDb().getDbms().canDropColumns()) {
      assertEquals("ColumnInfo changed", 156, getDb().getColumnInfoTable().count());
      assertEquals("TableInfo changed", 24, getDb().getTableInfoTable().count());
      checkTables(24);
    }
  }
  

}