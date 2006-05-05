package org.melati.poem.test;

import java.util.Enumeration;

import junit.framework.TestCase;
import org.melati.LogicalDatabase;
import org.melati.poem.AccessToken;
import org.melati.poem.PoemTask;
import org.melati.poem.UnexpectedExceptionPoemException;
import org.melati.poem.UserTable;

/**
 * A test of the example database which excercises every datatype and table type. 
 * 
 * @author timp
 */
public class TestDatabaseTest extends TestCase {
  private TestDatabase db;
  private static final String dbName = "poemtest";
  
  /**
   * Constructor for PoemTest.
   * @param arg0
   */
  public TestDatabaseTest(String arg0) {
    super(arg0);
  }

  /*
   * @see TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
    db = (TestDatabase)LogicalDatabase.getDatabase(dbName); 
  }

  /*
   * @see TestCase#tearDown()
   */
  protected void tearDown() throws Exception {
    db.inSession(AccessToken.root, // FIXME
            new PoemTask() {
              public void run() {
/*
                try {
                  if (db.getDbms().toString().endsWith("Hsqldb"))
                    db.sqlQuery("SHUTDOWN");
                } catch (Exception e) {
                  throw new UnexpectedExceptionPoemException(e);
                }
*/                
              }
            });
        //db.disconnect();
        super.tearDown();
  }

  /**
   * Test method for 'org.melati.poem.test.generated.TestDatabaseBase.getUserTable()'
   * Test method for 'org.melati.poem.Database.getTable(String)'
   */
  public void testGetUserTable() {
      UserTable ut1 = db.getUserTable();
      UserTable ut2 = (UserTable)db.getTable("user");
      assertEquals(ut1, ut2);
  }

  /**
   * Test method for 'org.melati.poem.Database.getDisplayTables()'
   */
  public void testGetDisplayTables() {
    final String expected = 
    "binaryfield (from the data structure definition)" +
    "stringfield (from the data structure definition)" + 
    "passwordfield (from the data structure definition)" + 
    "booleanfield (from the data structure definition)" + 
    "datefield (from the data structure definition)" + 
    "doublefield (from the data structure definition)" + 
    "integerfield (from the data structure definition)" + 
    "longfield (from the data structure definition)" + 
    "bigdecimalfield (from the data structure definition)" + 
    "timestampfield (from the data structure definition)" + 
    "everythingnormal (from the data structure definition)" + 
    "enextended (from the data structure definition)" + 
    "eaextended (from the data structure definition)" + 
    "user (from the data structure definition)" + 
    "group (from the data structure definition)" + 
    "capability (from the data structure definition)" + 
    "groupmembership (from the data structure definition)" + 
    "groupcapability (from the data structure definition)" + 
    "tableinfo (from the data structure definition)" + 
    "columninfo (from the data structure definition)" + 
    "tablecategory (from the data structure definition)" +
    "setting (from the data structure definition)";
    db.inSession(AccessToken.root, // FIXME
            new PoemTask() {
              public void run() {
                try {
    Enumeration en = db.getDisplayTables();
    String result = "";
    while (en.hasMoreElements()) {
      result += en.nextElement().toString();
    }
    System.err.println(expected);
    System.err.println(result);
    assertEquals(expected, result);
                } catch (Exception e) {
                  throw new UnexpectedExceptionPoemException(e);
                }
              }
            });
  }


}
