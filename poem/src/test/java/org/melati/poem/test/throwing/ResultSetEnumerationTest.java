/**
 * 
 */
package org.melati.poem.test.throwing;

import java.util.Properties;

import org.melati.poem.Database;
import org.melati.poem.PoemDatabaseFactory;
import org.melati.poem.SQLSeriousPoemException;
import org.melati.poem.dbms.test.ThrowingResultSet;

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
