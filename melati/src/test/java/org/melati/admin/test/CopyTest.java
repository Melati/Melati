package org.melati.admin.test;

import java.util.Properties;

import junit.framework.TestCase;

import org.melati.admin.AnticipatedException;
import org.melati.admin.Copy;
import org.melati.poem.Database;
import org.melati.poem.PoemDatabaseFactory;
import org.melati.poem.PoemTask;
import org.melati.poem.test.Dynamic;
import org.melati.poem.test.EverythingDatabase;
import org.melati.poem.test.PoemTestCase;

/**
 *
 * Test Copy
 */
public class CopyTest extends TestCase {
  /**
   * Constructor for CopyTest.
   * @param name
   */
  public CopyTest(String name) {
    super(name);
  }

  /**
   * @see junit.framework.TestCase#setUp()
   */
  protected void setUp() throws Exception {
  }

  /**
   * @see junit.framework.TestCase#tearDown()
   */
  protected void tearDown() throws Exception {
  }

  public void testCopyDissimilarDbs() { 
    try { 
      Copy.copy("everything", "melatitest");
      fail("Should have bombed");
    } catch (AnticipatedException e) { 
      e = null;
    }
  }
  
  public void testRecordsActuallyCopied() {
    final EverythingDatabase edb = (EverythingDatabase)getDatabase("everything");
    final EverythingDatabase edb2= (EverythingDatabase)getDatabase("everything2");
    System.err.println("From " + edb);
    System.err.println("To " + edb2);
    edb.inSessionAsRoot( 
        new PoemTask() {
          public void run() {
            Dynamic p = (Dynamic)(edb).getDynamicTable().newPersistent();
            p.setName("1");
            p.makePersistent();
            assertEquals(3, edb.getDynamicTable().count());
          }
        });
    Copy.copy(edb, edb2);
    edb2.inSessionAsRoot( 
        new PoemTask() {
          public void run() {
            assertEquals(3, edb2.getDynamicTable().count());
          }
        });
  }
    
  /**
   * @param name the name of the logical db
   * @return a Database
   */
  public static Database getDatabase(String name){ 
    Properties defs = PoemTestCase.getProperties();
    String pref = "org.melati.poem.test.PoemTestCase." + name + ".";
    String url = PoemTestCase.getOrDie(defs, pref + "url");
    return PoemDatabaseFactory.getDatabase(name,
            url, 
            PoemTestCase.getOrDie(defs, pref + "user"),
            PoemTestCase.getOrDie(defs, pref + "password"),
            PoemTestCase.getOrDie(defs, pref + "class"),
            PoemTestCase.getOrDie(defs, pref + "dbmsclass"),
            new Boolean(PoemTestCase.getOrDie(defs, pref + "addconstraints")).booleanValue(),
            false,
            false,
            4);
  }
}
