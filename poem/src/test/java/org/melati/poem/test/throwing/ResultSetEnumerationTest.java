package org.melati.poem.test.throwing;

import java.sql.ResultSet;
import java.util.NoSuchElementException;

import org.melati.poem.Database;
import org.melati.poem.PoemDatabaseFactory;
import org.melati.poem.SQLSeriousPoemException;
import org.melati.poem.dbms.test.sql.Thrower;

/**
 * @author timp
 * @since 11 Feb 2007
 *
 */
public class ResultSetEnumerationTest extends
    org.melati.poem.test.ResultSetEnumerationTest {

  public ResultSetEnumerationTest(String name) {
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

  public void testHasMoreElements() {
    Thrower.startThrowing(ResultSet.class, "close");
    try { 
      super.testHasMoreElements();
      fail("Should have bombed");
    } catch (SQLSeriousPoemException e) { 
      assertEquals("ResultSet bombed", e.innermostException().getMessage());
    }
    Thrower.stopThrowing(ResultSet.class, "close");
    Thrower.startThrowing(ResultSet.class, "getInt");
    try { 
      super.testHasMoreElements();
      fail("Should have bombed");
    } catch (SQLSeriousPoemException e) { 
      assertEquals("ResultSet bombed", e.innermostException().getMessage());
    }
    Thrower.stopThrowing(ResultSet.class, "getInt");
  }
  public void testNextElement() {
    Thrower.startThrowing(ResultSet.class, "close");
    try { 
      super.testNextElement();
      fail("Should have bombed");
    } catch (SQLSeriousPoemException e) { 
      assertEquals("ResultSet bombed", e.innermostException().getMessage());
    }
    Thrower.stopThrowing(ResultSet.class, "close");
    Thrower.startThrowing(ResultSet.class, "getInt");
    try { 
      super.testNextElement();
      fail("Should have bombed");
    } catch (SQLSeriousPoemException e) { 
      assertEquals("ResultSet bombed", e.innermostException().getMessage());
    }
    Thrower.stopThrowing(ResultSet.class, "getInt");
  }
  public void testResultSetEnumeration() {
    super.testResultSetEnumeration();
  }
  public void testSkip() {
    Thrower.startThrowing(ResultSet.class, "next");
    try { 
      super.testSkip();
      fail("Should have bombed");
    } catch (NoSuchElementException e) {
      //assertEquals("ResultSet bombed", e.innermostException().getMessage());
    }
    Thrower.stopThrowing(ResultSet.class, "next");
  }

}
