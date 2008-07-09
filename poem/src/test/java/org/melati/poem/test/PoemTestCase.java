package org.melati.poem.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Enumeration;
import java.util.Properties;

import org.melati.poem.ColumnInfo;
import org.melati.poem.Database;
import org.melati.poem.NoSuchTablePoemException;
import org.melati.poem.PoemDatabaseFactory;
import org.melati.poem.AccessToken;
import org.melati.poem.Column;
import org.melati.poem.Persistent;
import org.melati.poem.PoemTask;
import org.melati.poem.Table;

import junit.framework.Assert;
import junit.framework.AssertionFailedError;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestResult;

/**
 * A TestCase that runs in a Database session.
 * 
 * @author timp
 * @since 19-May-2006
 */
public class PoemTestCase extends TestCase implements Test {

  /**
   * The name of the test case
   */
  private String fName;

  protected int maxTrans = 0;
  
  /** Default db name */
  private String databaseName = "melatijunit";  // change to poemtest
  
  private AccessToken userToRunAs;

  boolean problem = false;
  String dbUrl = null;

  private String propertiesFileName = "org.melati.poem.test.PoemTestCase.properties";
  
  protected static TestResult result;
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
  /**
   * @see TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
    problem = false;
    int freeTrans = getDb().getFreeTransactionsCount();
    assertEquals("Not all transactions free", maxTrans, freeTrans);
  }

  /**
   * @see TestCase#tearDown()
   */
  protected void tearDown() throws Exception {
    checkDbUnchanged();
    if (!problem) {
      assertEquals("Not all transactions free", maxTrans, getDb().getFreeTransactionsCount());
    }
  }
  
  /**
   * Runs the test case and collects the results in TestResult.
   */
  public void run(TestResult result) {
    PoemTestCase.result = result;
    super.run(result);
  }
  
  static public void assertEquals(String message, int expected, int actual) {
    try { 
      Assert.assertEquals(message, expected, actual);
    } catch (Error e) { 
      result.stop();
      throw e;
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
      final Method runMethod = getClass().getMethod(fName, (Class[])null);
      if (!Modifier.isPublic(runMethod.getModifiers())) {
        fail("Method \"" + fName + "\" should be public");
      }
      // Ensures that we are invoking on
      // the object that method belongs to.
      final Object _this = this;
      getDb().inSession(getUserToRunAs(), 
          new PoemTask() {
            public void run() {
              try {
                runMethod.invoke(_this, new Object[0]);
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
    getDb().inSession(AccessToken.root,
        new PoemTask() {
          public void run() {
            databaseUnchanged();
          }
        });

  }
  protected void databaseUnchanged() { 
    //assertEquals("Setting changed", 0, getDb().getSettingTable().count());
    assertEquals("Group changed", 1, getDb().getGroupTable().count());
    assertEquals("GroupMembership changed", 1, getDb().getGroupMembershipTable().count());
    assertEquals("Capability changed", 5, getDb().getCapabilityTable().count());
    assertEquals("GroupCapability changed", 1, getDb().getGroupCapabilityTable().count());
    assertEquals("TableCategory changed", 3, getDb().getTableCategoryTable().count());
    assertEquals("User changed", 2, getDb().getUserTable().count());
    ColumnInfo newOne = null; 
    try{ 
      newOne = (ColumnInfo)getDb().getColumnInfoTable().getObject(69);
    } catch (Exception e) {}
    if (newOne != null) { 
      System.err.println(getDb() + " " + newOne.getName() + " " + newOne.getTableinfo().getName());
    }
    assertEquals("ColumnInfo changed", 69, getDb().getColumnInfoTable().count());
    assertEquals("TableInfo changed", 9, getDb().getTableInfoTable().count());
    checkTablesAndColumns(9,69);
  }
  
  protected void dropTable(String tableName) { 
    Connection c = getDb().getCommittedConnection();
    Table table = null;
    try { 
      table = getDb().getTable(tableName);
      Statement s = c.createStatement();
      if (table != null && table.getTableInfo().statusExistent()) {
        s.executeUpdate("DROP TABLE " + getDb().getDbms().getQuotedName(tableName));
      }
      s.close();
      c.commit();
    } catch (NoSuchTablePoemException e) { 
      e = null;
    } catch (Exception e) { 
      e.printStackTrace();
      fail("Something bombed");
    }
    
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
        System.out.println(t.getTableInfo().getTroid() + " " +
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
        System.out.println(e.nextElement());
      }      
    }
    assertEquals(columnCount, count);
  }
  
  protected void dumpTable(Table t) {
    Enumeration them = t.selection();
    while (them.hasMoreElements()) {
      Persistent it = (Persistent)them.nextElement();
      System.err.println(it.getTroid() + " " + it.getCooked("name") + " " +
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
   * @return Returns the db.
   */
  public Database getDb() {
    return getDb(getDatabaseName());
  }

  /**
   * @param dbNameP the name of the logical db
   * @return a Database
   */
  public Database getDb(String dbNameP) {
    if (dbNameP == null)
      throw new NullPointerException();
    return getDatabase(dbNameP);
  }

  /**
   * @param name the name of the logical db
   * @return a Database
   */
  public Database getDatabase(String name){ 
    Properties defs = getProperties();
    String pref = "org.melati.poem.test.PoemTestCase." + name + ".";
    maxTrans = new Integer(getOrDie(defs, pref + "maxtransactions")).intValue();
    String url = getOrDie(defs, pref + "url");
    return PoemDatabaseFactory.getDatabase(name,
            url, 
            getOrDie(defs, pref + "user"),
            getOrDie(defs, pref + "password"),
            getOrDie(defs, pref + "class"),
            getOrDie(defs, pref + "dbmsclass"),
            new Boolean(getOrDie(defs, pref + "addconstraints")).booleanValue(),
            new Boolean(getOrDie(defs, pref + "logsql")).booleanValue(),
            new Boolean(getOrDie(defs, pref + "logcommits")).booleanValue(),
            maxTrans);
  }
  /**
   * @return the user
   */
  public AccessToken getUserToRunAs() {
    if (userToRunAs == null) return AccessToken.root;
    return userToRunAs;
  }

  /**
   * @param userToRunAs the user
   */
  public void setUserToRunAs(AccessToken userToRunAs) {
    if (userToRunAs == null) 
      this.userToRunAs = AccessToken.root;
    else
      this.userToRunAs = userToRunAs;
  }

  /**
   * @return a Properties
   */
  public Properties getProperties() {
    InputStream is = PoemTestCase.class.getResourceAsStream(getPropertiesFileName());

    if (is == null)
      throw new RuntimeException(
              new FileNotFoundException(getPropertiesFileName() + ": is it in CLASSPATH?"));

    Properties them = new Properties();
    try {
      them.load(is);
      is.close();
    } catch (IOException e) {
      throw new RuntimeException(
              new IOException("Corrupt properties file `" + getPropertiesFileName() + "': " +
      e.getMessage()));
    }
    return them;
  }
  
  /**
   * Return a property.
   * 
   * @param properties the {@link Properties} object to look in 
   * @param propertyName the property to get 
   * @return the property value
   * @throws RuntimeException if the property is not set
   */
  public static String getOrDie(Properties properties, String propertyName) {
    String value = properties.getProperty(propertyName);
    if (value == null)
      throw new RuntimeException("Property " + propertyName + " not found in " + properties);
    return value;
  }

  /**
   * @return the properties name
   */
  public String getPropertiesFileName() {
    return propertiesFileName;
  }

  /**
   * @param propertiesFileName the name to set
   */
  public void setPropertiesFileName(String propertiesFileName) {
    this.propertiesFileName = propertiesFileName;
  }

  /**
   * @return the db name
   */
  public String getDatabaseName() {
    return databaseName;
  }

  /**
   * @param databaseName the db name
   */
  public void setDatabaseName(String databaseName) {
    this.databaseName = databaseName;
  }

  /**
   * Taken from junit-addons.sourceforge.net.
   * Asserts that two files are equal. Throws an
   * <tt>AssertionFailedError</tt> if they are not.<p>
   *
   * <b>Note</b>: This assertion method rely on the standard
   * <tt>junit.framework.Assert(String expected, String actual)</tt> method
   * to compare the lines of the files.  JUnit > 3.8 provides a nicer way to
   * display differences between two strings but since only lines are
   * compared (and not entire paragraphs) you can still use JUnit 3.7.
   */
  public static void assertEquals(String message,
                                  File expected,
                                  File actual) {
      Assert.assertNotNull(expected);
      Assert.assertNotNull(actual);

      Assert.assertTrue("File does not exist [" + expected.getAbsolutePath() + "]", expected.exists());
      Assert.assertTrue("File does not exist [" + actual.getAbsolutePath() + "]", actual.exists());

      Assert.assertTrue("Expected file not readable", expected.canRead());
      Assert.assertTrue("Actual file not readable", actual.canRead());

      FileInputStream eis = null;
      FileInputStream ais = null;

      try {
          try {
              eis = new FileInputStream(expected);
              ais = new FileInputStream(actual);
  
              BufferedReader expData = new BufferedReader(new InputStreamReader(eis));
              BufferedReader actData = new BufferedReader(new InputStreamReader(ais));
  
              Assert.assertNotNull(message, expData);
              Assert.assertNotNull(message, actData);
  
              assertEquals(message, expData, actData);
          } finally {
              if (eis != null) eis.close();
              if (ais != null) ais.close();
          }
      } catch (IOException e) {
          throw new AssertionFailedError(e.toString());
      }
  }

  /**
   * Asserts that two files are equal. Throws an
   * <tt>AssertionFailedError</tt> if they are not.
   */
  public static void assertEquals(File expected,
                                  File actual) {
      assertEquals(null, expected, actual);
  }

  /**
   * Soem test runners seem to think there should be a test in this file.
   */
  public void testNothing() { 
    
  }
}
