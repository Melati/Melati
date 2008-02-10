package org.melati.poem.test.throwing;

import java.sql.ResultSet;

import org.melati.poem.Database;
import org.melati.poem.PoemDatabaseFactory;
import org.melati.poem.SQLSeriousPoemException;
import org.melati.poem.dbms.test.sql.ThrowingResultSet;


/**
 * @author timp
 * @since 3 February 2007
 *
 */
public class CachedSelectionTest extends
        org.melati.poem.test.CachedSelectionTest {

  /**
   * Constructor.
   */
  public CachedSelectionTest() {
  }

  /**
   * Constructor.
   * 
   * @param name test name
   */
  public CachedSelectionTest(String name) {
    super(name);
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

  
  /** 
   * {@inheritDoc}
   * @see org.melati.poem.test.PoemTestCase#getDatabase(java.lang.String)
   */
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

  /** 
   * @see org.melati.poem.test.CachedSelectionTest#testFirstObject()
   */
  public void testFirstObject() {
    // Hit next in compute()
    ThrowingResultSet.startThrowing(ResultSet.class, "next");
    // Hit finally in compute()
    ThrowingResultSet.startThrowing(ResultSet.class, "close");
    try { 
      super.testFirstObject();
      fail("Should have bombed");
    } catch (SQLSeriousPoemException e) { 
      assertEquals("ResultSet bombed", e.innermostException().getMessage());      
    }
    ThrowingResultSet.stopThrowing(ResultSet.class, "next");
    ThrowingResultSet.stopThrowing(ResultSet.class, "close");
  }

  /** 
   * {@inheritDoc}
   * @see org.melati.poem.test.CachedSelectionTest#testMultiTableSelection()
   */
  public void testMultiTableSelection() {
  }

  /** 
   * {@inheritDoc}
   * @see org.melati.poem.test.CachedSelectionTest#testNth()
   */
  public void testNth() {
  }

  /** 
   * {@inheritDoc}
   * @see org.melati.poem.test.CachedSelectionTest#testToString()
   */
  public void testToString() {
  }

}
