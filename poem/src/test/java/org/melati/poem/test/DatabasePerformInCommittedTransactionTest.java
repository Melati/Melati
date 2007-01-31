package org.melati.poem.test;

import java.util.Enumeration;

import org.melati.LogicalDatabase;
import org.melati.poem.AccessToken;
import org.melati.poem.Group;
import org.melati.poem.Persistent;
import org.melati.poem.PoemDatabase;
import org.melati.poem.PoemTask;
import org.melati.poem.Table;
import org.melati.poem.User;
import org.melati.util.DatabaseInitException;
import org.melati.poem.transaction.WriteCommittedException;

import junit.framework.Test;
import junit.framework.TestCase;

/**
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
    assertEquals(0, getDb().getSettingTable().count());
    assertEquals(1, getDb().getGroupTable().count());
    assertEquals(1, getDb().getGroupMembershipTable().count());
    assertEquals(5, getDb().getCapabilityTable().count());
    assertEquals(1, getDb().getGroupCapabilityTable().count());
    assertEquals(2, getDb().getTableCategoryTable().count());
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
    Enumeration them = t.selection();
    while (them.hasMoreElements()) {
      Persistent it = (Persistent)them.nextElement();
      System.err.println(it.troid() + " " + it.getCooked("name") + " " +
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
    PoemTask read = new PoemTask() {
      public void run() {
        assertEquals("Melati guest user",
                ((User)getDb().getUserTable().getObject(new Integer(0))).getName());
      }
    };

    getDb().inCommittedTransaction(AccessToken.root, read);
    getDb().uncacheContents();
    getDb().inCommittedTransaction(AccessToken.root, read);
    
  }
  
  /**
   * @see org.melati.poem.Database#inCommittedTransaction(AccessToken, PoemTask)
   */
  public void testUpdateInCommittedTransaction() {
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
   * FIXME Relies upon session
   */
  public void testTableTroidSelection() {
    //Enumeration en = getDb().getUserTable().troidSelection(
    //        getDb().getUserTable().troidColumn().fullQuotedName() + "=0",null, false);
    //assertEquals(2, EnumUtils.vectorOf(en).size());
  }
  
  /**
   * Test that committed transaction is used.
   */
  public void testTableCount() {
    getDb().setLogSQL(true);
    assertEquals(1, getDb().getGroupTable().count());
    getDb().setLogSQL(false);
    assertEquals(1, getDb().getGroupTable().count(null, true));
    
  }
}