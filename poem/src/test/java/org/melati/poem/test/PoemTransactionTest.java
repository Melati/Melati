package org.melati.poem.test;

import org.melati.poem.PoemThread;
import org.melati.poem.PoemTransaction;
import org.melati.poem.User;

/**
 * @author timp
 * @since 11 Feb 2007
 *
 */
public class PoemTransactionTest extends PoemTestCase {

  public PoemTransactionTest(String name) {
    super(name);
  }

  protected void setUp() throws Exception {
    super.setUp();
  }

  protected void tearDown() throws Exception {
    super.tearDown();
  }

  /**
   * Test method for {@link org.melati.poem.PoemTransaction#
   * PoemTransaction(org.melati.poem.Database, java.sql.Connection, int)}.
   */
  public void testPoemTransaction() {
    new PoemTransaction(getDb(),getDb().getCommittedConnection(),1);
  }

  /**
   * Test method for {@link org.melati.poem.PoemTransaction#getDatabase()}.
   */
  public void testGetDatabase() {
    
  }

  /**
   * Test method for {@link org.melati.poem.PoemTransaction#close(boolean)}.
   */
  public void testClose() {
    
  }

  /**
   * Test method for {@link org.melati.poem.transaction.Transaction#writeDown()}.
   */
  public void testWriteDown() {
    
  }

  /**
   * Test method for {@link org.melati.poem.transaction.Transaction#commit()}.
   */
  public void testCommit() {
    User u = new User("tester","tester","tester");
    getDb().getUserTable().create(u); 
    assertEquals("tester",u.getName());
    u.setName("tester2");
    // get the logSQL line covered
    PoemThread.commit();
    u.delete();    
  }

  /**
   * Test method for {@link org.melati.poem.transaction.Transaction#rollback()}.
   */
  public void testRollback() {
    User u = new User("tester","tester","tester");
    getDb().getUserTable().create(u); 
    assertEquals("tester",u.getName());
    u.setName("tester2");
    // get the logSQL line covered
    PoemThread.rollback();
    u.delete();    
    
  }

  /**
   * Test method for {@link org.melati.poem.transaction.Transaction#getBlockedOn()}.
   */
  public void testGetBlockedOn() {
    
  }

  /**
   * Test method for {@link org.melati.poem.transaction.Transaction#toString()}.
   */
  public void testToString() {
    
  }

}
