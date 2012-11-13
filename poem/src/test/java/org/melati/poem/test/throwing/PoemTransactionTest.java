/**
 * 
 */
package org.melati.poem.test.throwing;

import java.sql.Connection;
import java.sql.PreparedStatement;

import org.melati.poem.Database;
import org.melati.poem.PoemDatabaseFactory;
import org.melati.poem.PoemThread;
import org.melati.poem.SQLSeriousPoemException;
import org.melati.poem.User;
import org.melati.poem.dbms.test.sql.Thrower;

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

  
  public Database getDatabase(String name) {
    maxTrans = 8;
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

  public void testClose() {
    Thrower.startThrowing(PreparedStatement.class, "executeQuery");
    super.testClose();
    Thrower.stopThrowing(PreparedStatement.class, "executeQuery");
  }

  public void testCommit() {
    Thrower.startThrowing(Connection.class, "commit");
    try { 
      super.testCommit();
      fail("Should have bombed");
    } catch (SQLSeriousPoemException e) { 
      assertEquals("Connection bombed", e.innermostException().getMessage());      
    }
    Thrower.stopThrowing(Connection.class, "commit");
  }

  public void testGetBlockedOn() {
    //ThrowingPreparedStatement.startThrowing("executeQuery");
    //super.testGetBlockedOn();
    //ThrowingPreparedStatement.stopThrowing("executeQuery");
  }

  public void testGetDatabase() {
    //ThrowingPreparedStatement.startThrowing("executeQuery");
    //super.testGetDatabase();
    //ThrowingPreparedStatement.stopThrowing("executeQuery");
  }

  public void testPoemTransaction() {
    Thrower.startThrowing(Connection.class, "setAutoCommit");
    try { 
      super.testPoemTransaction();
      fail("Should have bombed");
    } catch (SQLSeriousPoemException e) { 
      assertEquals("Connection bombed", e.innermostException().getMessage());      
    }
    Thrower.stopThrowing(Connection.class, "setAutoCommit");
  }

  public void testRollback() {
    Thrower.startThrowing(Connection.class, "rollback");
    User u = new User("tester","tester","tester");
    try { 
      getDb().getUserTable().create(u); 
      assertEquals("tester",u.getName());
      u.setName("tester2");
      // get the logSQL line covered
      PoemThread.rollback();
      fail("Should have bombed");
    } catch (SQLSeriousPoemException e) { 
      assertEquals("Connection bombed", e.innermostException().getMessage());      
    }
    Thrower.stopThrowing(Connection.class, "rollback");
    u.delete();    
  }

  public void testToString() {
    //ThrowingPreparedStatement.startThrowing("executeQuery");
    //super.testToString();
    //ThrowingPreparedStatement.stopThrowing("executeQuery");
  }

  public void testWriteDown() {
    //ThrowingPreparedStatement.startThrowing("executeQuery");
    //super.testWriteDown();
    //ThrowingPreparedStatement.stopThrowing("executeQuery");
  }

}
