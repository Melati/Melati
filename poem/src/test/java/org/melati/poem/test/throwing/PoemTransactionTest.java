/**
 * 
 */
package org.melati.poem.test.throwing;

import java.util.Properties;

import org.melati.poem.Database;
import org.melati.poem.PoemDatabaseFactory;

/**
 * @author timp
 * @since 11 Feb 2007
 *
 */
public class PoemTransactionTest extends
    org.melati.poem.test.PoemTransactionTest {

  /**
   * @param name
   */
  public PoemTransactionTest(String name) {
    super(name);
  }


  protected void setUp() throws Exception {
    PoemDatabaseFactory.removeDatabase(databaseName);
    super.setUp();
    assertEquals("org.melati.poem.dbms.test.HsqldbThrower",getDb().getDbms().getClass().getName());
  }
  public Database getDatabase(String name) {
    Properties defs = databaseDefs();
    String pref = "org.melati.poem.test.PoemTestCase." + name + ".";
    maxTrans = new Integer(getOrDie(defs, pref + "maxtransactions")).intValue();
    return PoemDatabaseFactory.getDatabase(name, 
        getOrDie(defs, pref + "url"),
        getOrDie(defs, pref + "user"), getOrDie(defs, pref + "password"),
        getOrDie(defs, pref + "class"),
        "org.melati.poem.dbms.test.HsqldbThrower", 
        new Boolean(getOrDie(defs, pref + "addconstraints")).booleanValue(), 
        new Boolean(getOrDie(defs, pref + "logsql")).booleanValue(), 
        new Boolean(getOrDie(defs, pref + "logcommits")).booleanValue(), maxTrans);
  }

  public void testClose() {
    // TODO Auto-generated method stub
    super.testClose();
  }

  public void testCommit() {
    // TODO Auto-generated method stub
    super.testCommit();
  }

  public void testGetBlockedOn() {
    // TODO Auto-generated method stub
    super.testGetBlockedOn();
  }

  public void testGetDatabase() {
    // TODO Auto-generated method stub
    super.testGetDatabase();
  }

  public void testPoemTransaction() {
    // TODO Auto-generated method stub
    super.testPoemTransaction();
  }

  public void testRollback() {
    // TODO Auto-generated method stub
    super.testRollback();
  }

  public void testToString() {
    // TODO Auto-generated method stub
    super.testToString();
  }

  public void testWriteDown() {
    // TODO Auto-generated method stub
    super.testWriteDown();
  }

}
