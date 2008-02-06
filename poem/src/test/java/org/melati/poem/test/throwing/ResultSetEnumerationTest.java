/**
 * 
 */
package org.melati.poem.test.throwing;

import java.sql.ResultSet;

import org.melati.poem.Database;
import org.melati.poem.PoemDatabaseFactory;
import org.melati.poem.SQLSeriousPoemException;
import org.melati.poem.dbms.test.sql.ThrowingResultSet;

/**
 * @author timp
 * @since 11 Feb 2007
 *
 */
public class ResultSetEnumerationTest extends
    org.melati.poem.test.ResultSetEnumerationTest {

  /**
   * @param name
   */
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

  public void testHasMoreElements() {
    ThrowingResultSet.startThrowing(ResultSet.class, "close");
    try { 
      super.testHasMoreElements();
      fail("Should have bombed");
    } catch (SQLSeriousPoemException e) { 
      assertEquals("ResultSet bombed", e.innermostException().getMessage());
    }
    ThrowingResultSet.stopThrowing(ResultSet.class, "close");
    ThrowingResultSet.startThrowing(ResultSet.class, "next");
    try { 
      super.testHasMoreElements();
      fail("Should have bombed");
    } catch (SQLSeriousPoemException e) { 
      assertEquals("ResultSet bombed", e.innermostException().getMessage());
    }
    ThrowingResultSet.stopThrowing(ResultSet.class, "next");
  }
  public void testNextElement() {
    ThrowingResultSet.startThrowing(ResultSet.class, "close");
    try { 
      super.testNextElement();
      fail("Should have bombed");
    } catch (SQLSeriousPoemException e) { 
      assertEquals("ResultSet bombed", e.innermostException().getMessage());
    }
    ThrowingResultSet.stopThrowing(ResultSet.class, "close");
    ThrowingResultSet.startThrowing(ResultSet.class, "next");
    try { 
      super.testNextElement();
      fail("Should have bombed");
    } catch (SQLSeriousPoemException e) { 
      assertEquals("ResultSet bombed", e.innermostException().getMessage());
    }
    ThrowingResultSet.stopThrowing(ResultSet.class, "next");
  }
  public void testResultSetEnumeration() {
    super.testResultSetEnumeration();
  }
  public void testSkip() {
    ThrowingResultSet.startThrowing(ResultSet.class, "next");
    try { 
      super.testSkip();
      fail("Should have bombed");
    } catch (SQLSeriousPoemException e) { 
      assertEquals("ResultSet bombed", e.innermostException().getMessage());
    }
    ThrowingResultSet.stopThrowing(ResultSet.class, "next");
  }

}
