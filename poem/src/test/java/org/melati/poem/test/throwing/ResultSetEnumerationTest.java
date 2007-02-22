/**
 * 
 */
package org.melati.poem.test.throwing;

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

  public void testHasMoreElements() {
    ThrowingResultSet.startThrowing("close");
    System.err.println(ThrowingResultSet.shouldThrow("close"));
    try { 
      super.testHasMoreElements();
      fail("Should have bombed");
    } catch (SQLSeriousPoemException e) { 
      assertEquals("ResultSet bombed", e.innermostException().getMessage());
    }
    ThrowingResultSet.stopThrowing("close");
    ThrowingResultSet.startThrowing("next");
    try { 
      super.testHasMoreElements();
      fail("Should have bombed");
    } catch (SQLSeriousPoemException e) { 
      assertEquals("ResultSet bombed", e.innermostException().getMessage());
    }
    ThrowingResultSet.stopThrowing("next");
  }
  public void testNextElement() {
    ThrowingResultSet.startThrowing("close");
    try { 
      super.testNextElement();
      fail("Should have bombed");
    } catch (SQLSeriousPoemException e) { 
      assertEquals("ResultSet bombed", e.innermostException().getMessage());
    }
    ThrowingResultSet.stopThrowing("close");
    ThrowingResultSet.startThrowing("next");
    try { 
      super.testNextElement();
      fail("Should have bombed");
    } catch (SQLSeriousPoemException e) { 
      assertEquals("ResultSet bombed", e.innermostException().getMessage());
    }
    ThrowingResultSet.stopThrowing("next");
  }
  public void testResultSetEnumeration() {
    super.testResultSetEnumeration();
  }
  public void testSkip() {
    ThrowingResultSet.startThrowing("next");
    try { 
      super.testSkip();
      fail("Should have bombed");
    } catch (SQLSeriousPoemException e) { 
      assertEquals("ResultSet bombed", e.innermostException().getMessage());
    }
    ThrowingResultSet.stopThrowing("next");
  }

}
