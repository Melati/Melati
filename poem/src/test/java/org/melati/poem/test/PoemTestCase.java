package org.melati.poem.test;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Enumeration;

import org.melati.LogicalDatabase;
import org.melati.poem.AccessToken;
import org.melati.poem.Column;
import org.melati.poem.Persistent;
import org.melati.poem.PoemDatabase;
import org.melati.poem.PoemTask;
import org.melati.poem.Table;
import org.melati.util.DatabaseInitException;

import junit.framework.Test;
import junit.framework.TestCase;

/**
 * A TestCase that runs in a Database session.
 * 
 * @author timp
 * @since 19-May-2006
 */
public abstract class PoemTestCase extends TestCase implements Test {

  /**
   * The name of the test case
   */
  private String fName;

  private static PoemDatabase db = null;

  /** Default db name */
  public static final String defaultDbName = "melatijunit";
  private String dbName = defaultDbName;
  
  private AccessToken userToRunAs;

  /**
   * Constructor.
   */
  public PoemTestCase() {
    super();
    fName = null;
  }

  /**
   * Constructor.
   * 
   * @param name
   */
  public PoemTestCase(String name) {
    super(name);
    fName = name;
  }
  boolean problem = false;
  String dbUrl = null;
  /**
   * @see TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
    problem = false;
    setDbName(getDbName());
    setDb(getDbName());
    assertEquals(4, getDb().getFreeTransactionsCount());
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
   * Run the test in a session.
   * 
   * @see junit.framework.TestCase#runTest()
   */
  protected void runTest() throws Throwable {
    assertNotNull(fName);
    try {
      // use getMethod to get all public inherited
      // methods. getDeclaredMethods returns all
      // methods of this class but excludes the
      // inherited ones.
      final Method runMethod = getClass().getMethod(fName, null);
      if (!Modifier.isPublic(runMethod.getModifiers())) {
        fail("Method \"" + fName + "\" should be public");
      }
      // Ensures that we are invoking on
      // the object that method belongs to.
      final Object _this = this;
      getDb().inSession(AccessToken.root, // HACK
          new PoemTask() {
            public void run() {
              try {
                runMethod.invoke(_this, new Class[0]);
              } catch (Throwable e) {
                problem = true;
                e.fillInStackTrace();
                throw new RuntimeException(e);
              }
            }
            public String toString() { 
              return "PoemTestCase:"+ fName;
            }
          });
    } catch (NoSuchMethodException e) {
      fail("Method \"" + fName + "\" not found");
    }
  }

  protected void checkDbUnchanged() {
    getDb().inSession(AccessToken.root, // HACK
        new PoemTask() {
          public void run() {
            if (dbName.equals("poemtest")) {
              poemtestUnchanged();
            } 
            if (dbName.equals("melatijunit")) {
              melatijunitUnchanged();
            }
          }
        });

  }
  protected void melatijunitUnchanged() { 
    assertEquals("Setting changed", 0, getDb().getSettingTable().count());
    assertEquals("Group changed", 1, getDb().getGroupTable().count());
    assertEquals("GroupMembership changed", 1, getDb().getGroupMembershipTable().count());
    assertEquals("Capability changed", 5, getDb().getCapabilityTable().count());
    assertEquals("GroupCapability changed", 1, getDb().getGroupCapabilityTable().count());
    assertEquals("TableCategory changed", 2, getDb().getTableCategoryTable().count());
    assertEquals("User changed", 2, getDb().getUserTable().count());
    assertEquals("ColumnInfo changed", 69, getDb().getColumnInfoTable().count());
    assertEquals("TableInfo changed", 9, getDb().getTableInfoTable().count());
    checkTablesAndColumns(9,69);
  }
  protected void poemtestUnchanged() { 
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
    assertEquals("TableInfo changed", 25, getDb().getTableInfoTable().count());
    checkTables(25);
  }
  

  protected void checkTablesAndColumns(int tableCount, int columnCount) {
    checkTables(tableCount);
    checkColumns(columnCount);
  }
  protected void checkTables(int tableCount) {
    Enumeration e = getDb().tables();
    int count = 0;
    while (e.hasMoreElements()) {
      Table t = (Table)e.nextElement();
      if (t.getTableInfo().statusExistent()) count++;
    }
    if (count != tableCount) {
      System.out.println(fName + " Additional tables - expected:" + 
              tableCount + " found:" + count);
      e = getDb().tables();
      while (e.hasMoreElements()) {
        Table t = (Table)e.nextElement();
        System.out.println(t.getTableInfo().troid() + " " +
                t.getTableInfo().statusExistent() + " " +
                t);
      }      
    }
    assertEquals(tableCount, count);
  }
  protected void checkColumns(int columnCount) {
    Enumeration e = getDb().columns();
    int count = 0;
    while (e.hasMoreElements()) {
      Column c = (Column)e.nextElement();
      if (c.getColumnInfo().statusExistent())
        count++;
    }
    if (count != columnCount) {
      System.out.println(fName + " Additional columns - expected:" + 
              columnCount + " found:" + count);
      e = getDb().columns();
      while (e.hasMoreElements()) {
        System.out.println((Column)e.nextElement());
      }      
    }
    assertEquals(columnCount, count);
  }
  
  protected void dumpTable(Table t) {
    Enumeration them = t.selection();
    while (them.hasMoreElements()) {
      Persistent it = (Persistent)them.nextElement();
      System.err.println(it.troid() + " " + it.getCooked("name") + " " +
          it.getTable().getName());
    }
    
  }
  /**
   * Gets the name of a TestCase.
   * 
   * @return returns a String
   */
  public String getName() {
    return fName;
  }

  /**
   * Sets the name of a TestCase.
   * 
   * @param name
   *          The name to set
   */
  public void setName(String name) {
    fName = name;
  }

  /**
   * @return Returns the dbName.
   */
  protected String getDbName() {
    return this.dbName;
  }

  /**
   * @param dbName
   *          The dbName to set.
   */
  protected void setDbName(String dbName) {
    this.dbName = dbName;
  }

  /**
   * @return Returns the db.
   */
  public PoemDatabase getDb() {
    return db;
  }

  public void setDb(String dbName) {
    if (dbName == null)
      throw new NullPointerException();
    try {
      db = (PoemDatabase)LogicalDatabase.getDatabase(dbName);
    } catch (DatabaseInitException e) {
      e.printStackTrace();
      fail(e.getMessage());
    }
  }

  public AccessToken getUserToRunAs() {
    if (userToRunAs == null) return AccessToken.root;
    return userToRunAs;
  }

  public void setUserToRunAs(AccessToken userToRunAs) {
    if (userToRunAs == null) 
      this.userToRunAs = AccessToken.root;
    else
      this.userToRunAs = userToRunAs;
  }

}