/**
 * 
 */
package org.melati.template.test;

import org.melati.poem.Database;
import org.melati.poem.PoemDatabaseFactory;
import org.melati.poem.test.PoemTestCase;

/**
 * @author timp
 * @since 10 Feb 2007
 *
 */
public abstract class TreeTestCase extends PoemTestCase {
  /** Default db name */
  public static final String databaseName = "tree";

  /**
   * 
   */
  public TreeTestCase() {
  }

  /**
   * @param name
   */
  public TreeTestCase(String name) {
    super(name);
  }
  
  /**
   * @return Returns the db.
   */
  public Database getDb() {
    return getTreeDatabase();
  }

  /**
   * @return the tree db
   */
  public Database getTreeDatabase() { 
    maxTrans = 4;
    return PoemDatabaseFactory.getDatabase("tree",
            "jdbc:hsqldb:mem:" + "tree",
            "sa",
            "","org.melati.util.test.TreeDatabase",
            "org.melati.poem.dbms.Hsqldb",false,false,false,4);
  }
  

  protected void databaseUnchanged() { 
    assertEquals("Setting changed", 0, getDb().getSettingTable().count());
    assertEquals("Group changed", 1, getDb().getGroupTable().count());
    assertEquals("GroupMembership changed", 1, getDb().getGroupMembershipTable().count());
    assertEquals("Capability changed", 5, getDb().getCapabilityTable().count());
    assertEquals("GroupCapability changed", 1, getDb().getGroupCapabilityTable().count());
    assertEquals("TableCategory changed", 4, getDb().getTableCategoryTable().count());
    assertEquals("User changed", 2, getDb().getUserTable().count());
    //ColumnInfo newOne = null; 
    //try{ 
    //  newOne = (ColumnInfo)getDb().getColumnInfoTable().getObject(69);
    //} catch (Exception e) {}
    //if (newOne != null) { 
    //  System.err.println(newOne.getName() + " " + newOne.getTableinfo().getName());
    //}
    assertEquals("ColumnInfo changed", 72, getDb().getColumnInfoTable().count());
    assertEquals("TableInfo changed", 10, getDb().getTableInfoTable().count());
    checkTablesAndColumns(10,72);
  }

}
