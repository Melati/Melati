package org.melati.poem.test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;

import org.melati.poem.AccessToken;
import org.melati.poem.Database;
import org.melati.poem.DatabaseInitialisationPoemException;
import org.melati.poem.Group;
import org.melati.poem.Persistent;
import org.melati.poem.PoemDatabase;
import org.melati.poem.PoemDatabaseFactory;
import org.melati.poem.PoemTask;
import org.melati.poem.Table;
import org.melati.poem.User;
import org.melati.poem.transaction.WriteCommittedException;
import org.melati.poem.util.EnumUtils;

import junit.framework.Test;
import junit.framework.TestCase;

/**
 * FIXME Postgresql seems to think that the committed transaction is in a funny state. 
 * 
 * @author timp
 * @since 19-January-2007
 */
public class DatabasePerformInCommittedTransactionTest 
   extends TestCase implements Test {

  /**
   * The name of the test case
   */

  private PoemDatabase db = null;

  private String dbName = "melatijunit";
  
  private AccessToken userToRunAs;
  
  boolean skipTest = false;

  /**
   * Constructor.
   */
  public DatabasePerformInCommittedTransactionTest() {
    super();
  }

  /**
   * Constructor.
   * 
   * @param name
   */
  public DatabasePerformInCommittedTransactionTest(String name) {
    super(name);
  }

  String dbUrl = null;
  /**
   * @see TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
    setDbName(getDbName());
    setDb(getDbName());
  }

  /**
   * @see TestCase#tearDown()
   */
  protected void tearDown() throws Exception {
    checkDbUnchanged();
  }

  protected void checkDbUnchanged() {
    getDb().inSession(AccessToken.root,
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
    if (!getDb().getDbms().canDropColumns()) {
      return;
    }
    assertEquals(0, getDb().getSettingTable().count());
    assertEquals(1, getDb().getGroupTable().count());
    assertEquals(1, getDb().getGroupMembershipTable().count());
    assertEquals(5, getDb().getCapabilityTable().count());
    assertEquals(1, getDb().getGroupCapabilityTable().count());
    assertEquals(3, getDb().getTableCategoryTable().count());
    assertEquals(2, getDb().getUserTable().count());
    assertEquals(69, getDb().getColumnInfoTable().count());
    assertEquals(9, getDb().getTableInfoTable().count());
  }
  protected void poemtestUnchanged() { 
    assertEquals(0, getDb().getSettingTable().count());
    assertEquals(1, getDb().getGroupTable().count());
    assertEquals(1, getDb().getGroupMembershipTable().count());
    assertEquals(5, getDb().getCapabilityTable().count());
    assertEquals(1, getDb().getGroupCapabilityTable().count());
    assertEquals(4, getDb().getTableCategoryTable().count());
    assertEquals(2, getDb().getUserTable().count());
    //dumpTable(getDb().getColumnInfoTable());
    // Until table.dropColumnAndCommit() arrives...
    assertEquals(147, getDb().getColumnInfoTable().count());
    assertEquals(23, getDb().getTableInfoTable().count());

  }
  protected void dumpTable(Table t) {
    Enumeration<Persistent> them = t.selection();
    while (them.hasMoreElements()) {
      Persistent it = (Persistent)them.nextElement();
      System.err.println(it.getTroid() + " " + it.getCooked("name") + " " +
          it.getTable().getName());
    }
    
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

  /**
   * @param dbName the name of the db to set
   */
  public void setDb(String dbName) {
    if (dbName == null)
      throw new NullPointerException();
    try {
      db = (PoemDatabase)getDatabase(dbName);
    } catch (DatabaseInitialisationPoemException e) {
      e.printStackTrace();
      fail(e.getMessage());
    }
  }

  /**
   * @param name the name of the logical database
   * @return a database with that name 
   */
  public Database getDatabase(String name){ 
    Properties defs = databaseDefs();
    String pref = "org.melati.poem.test.PoemTestCase." + name + ".";
    if (PoemTestCase.getOrDie(defs, pref + "dbmsclass").indexOf("Postgres") > 0) skipTest = true;

    return PoemDatabaseFactory.getDatabase(name,
            PoemTestCase.getOrDie(defs, pref + "url"), 
            PoemTestCase.getOrDie(defs, pref + "user"),
            PoemTestCase.getOrDie(defs, pref + "password"),
            PoemTestCase.getOrDie(defs, pref + "class"),
            PoemTestCase.getOrDie(defs, pref + "dbmsclass"),
            new Boolean(PoemTestCase.getOrDie(defs, pref + "addconstraints")).booleanValue(),
            new Boolean(PoemTestCase.getOrDie(defs, pref + "logsql")).booleanValue(),
            new Boolean(PoemTestCase.getOrDie(defs, pref + "logcommits")).booleanValue(),
            new Integer(PoemTestCase.getOrDie(defs, pref + "maxtransactions")).intValue());
  }

  /** Properties, named for this class. */
  public static Properties databaseDefs = null;
  /**
   * @returnthe databse defs
   */
  public  Properties databaseDefs() {
    if (databaseDefs == null)
      databaseDefs = getProperties();
    return databaseDefs;
  }
  /**
   * @return a properties object
   */
  public Properties getProperties() {
    String className = "org.melati.poem.test.PoemTestCase";
    String name = className + ".properties";
    InputStream is = this.getClass().getResourceAsStream(name);

    if (is == null)
      throw new RuntimeException(new FileNotFoundException(name + ": is it in CLASSPATH?"));

    Properties them = new Properties();
    try {
      them.load(is);
    } catch (IOException e) {
      throw new RuntimeException(new IOException("Corrupt properties file `" + name + "': " +
      e.getMessage()));
    }
    return them;
  }

  
  /**
   * @return the user
   */
  public AccessToken getUserToRunAs() {
    if (userToRunAs == null) return AccessToken.root;
    return userToRunAs;
  }

  /**
   * @param userToRunAs the user to set
   */
  public void setUserToRunAs(AccessToken userToRunAs) {
    if (userToRunAs == null) 
      this.userToRunAs = AccessToken.root;
    else
      this.userToRunAs = userToRunAs;
  }

  /**
   * @see org.melati.poem.Database#inCommittedTransaction(AccessToken, PoemTask)
   */
  public void testInCommittedTransaction() {
    PoemTask doNothing = new PoemTask() {
      public void run() {
      }
    };
    getDb().inCommittedTransaction(AccessToken.root, doNothing);
    
  }

  /**
   * @see org.melati.poem.Database#inCommittedTransaction(AccessToken, PoemTask)
   */
  public void testReadInCommittedTransaction() {
    if (skipTest) return;
    PoemTask read = new PoemTask() {
      public void run() {
        assertEquals("Melati guest user",
                ((User)getDb().getUserTable().getObject(new Integer(0))).getName());
      }
    };

    getDb().inCommittedTransaction(AccessToken.root, read);
    getDb().uncache();
    getDb().inCommittedTransaction(AccessToken.root, read);
    
  }
  
  /**
   * @see org.melati.poem.Database#inCommittedTransaction(AccessToken, PoemTask)
   */
  public void testUpdateInCommittedTransaction() {
    if (skipTest) return;
    PoemTask modify = new PoemTask() {
      public void run() {
        try { 
          getDb().guestUser().setName(getDb().guestUser().getName());
          fail("Should have blown up");
        } catch (WriteCommittedException e ) { 
          e = null;
        }
      }
    };
    getDb().inCommittedTransaction(AccessToken.root, modify);
    
  }
  
  /**
   * @see org.melati.poem.Database#inCommittedTransaction(AccessToken, PoemTask)
   */
  public void testDeleteInCommittedTransaction() {
    if (skipTest) return;
    PoemTask modify = new PoemTask() {
      public void run() {
        try { 
          getDb().guestUser().delete_unsafe();
          fail("Should have blown up");
        } catch (WriteCommittedException e ) { 
          e = null;
        }
      }
    };
    getDb().inCommittedTransaction(AccessToken.root, modify);
    
  }
  
  /**
   * It might be a good idea to handle this more elegantly than by throwing NPE. 
   * @see org.melati.poem.Database#inCommittedTransaction(AccessToken, PoemTask)
   */
  public void testCreateInCommittedTransaction() {
    if (skipTest) return;
    PoemTask create = new PoemTask() {
      public void run() {
        try { 
          Group g = (Group)getDb().getGroupTable().newPersistent();
          g.setName("failure");
          g.makePersistent();
          fail("Should have blown up");
        } catch (NullPointerException e ) { 
          e = null;
        }
      }
    };
    getDb().inCommittedTransaction(AccessToken.root, create);
    create = new PoemTask() {
      public void run() {
        try { 
          getDb().getGroupTable().ensure("failure");
          fail("Should have blown up");
        } catch (NullPointerException e ) { 
          e = null;
        }
      }
    };
  }
  
  /**
   * @see org.melati.poem.Database#toString()
   */
  public void testToString() {
    PoemDatabase d = new PoemDatabase();
    assertEquals("unconnected database", d.toString());
  }
  
  /**
   * Test troidSelection uses committed transaction.
   */
  public void testTableTroidSelection() {
    getDb().inSession(AccessToken.root,
            new PoemTask() {
              public void run() {
                Enumeration<Integer> en = getDb().getUserTable().troidSelection("id=0",null, false);
                assertEquals(1, EnumUtils.vectorOf(en).size());
              }
            });
  }
  
  /**
   * Test that committed transaction is used.
   */
  public void testTableCount() {
    if (skipTest) return;
    getDb().setLogSQL(true);
    assertEquals(1, getDb().getGroupTable().count());
    getDb().setLogSQL(false);
    assertEquals(1, getDb().getGroupTable().count(null, true));
    
  }
}