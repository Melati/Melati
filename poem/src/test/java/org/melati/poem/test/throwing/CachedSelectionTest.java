package org.melati.poem.test.throwing;

import org.melati.poem.Database;
import org.melati.poem.PoemDatabaseFactory;
import org.melati.poem.SQLSeriousPoemException;
import org.melati.poem.dbms.test.sql.ThrowingResultSet;

public class CachedSelectionTest extends
        org.melati.poem.test.CachedSelectionTest {

  public CachedSelectionTest() {
  }

  public CachedSelectionTest(String name) {
    super(name);
  }
  protected void setUp() throws Exception {
    PoemDatabaseFactory.removeDatabase(databaseName);
    super.setUp();
    assertEquals("org.melati.poem.dbms.test.HsqldbThrower",getDb().getDbms().getClass().getName());
  }
  protected void tearDown() throws Exception {
    super.tearDown();
    PoemDatabaseFactory.removeDatabase(databaseName);
  }

  
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

  public void testFirstObject() {
    ThrowingResultSet.startThrowing("close");
    ThrowingResultSet.startThrowing("next");
    try { 
      super.testFirstObject();
      fail("Should have bombed");
    } catch (SQLSeriousPoemException e) { 
      assertEquals("ResultSet bombed", e.innermostException().getMessage());      
    }
    ThrowingResultSet.stopThrowing("close");
    ThrowingResultSet.stopThrowing("next");

  }

  public void testMultiTableSelection() {
  }

  public void testNth() {
  }

  public void testToString() {
  }

}
