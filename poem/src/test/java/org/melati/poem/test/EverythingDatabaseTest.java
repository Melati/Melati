package org.melati.poem.test;

import org.melati.poem.Table;
import org.melati.poem.UserTable;

/**
 * A test of the example database which exercises every datatype and table type. 
 * 
 * @author timp
 */
public class EverythingDatabaseTest extends EverythingTestCase {

  
  /**
   * Constructor for PoemTest.
   * @param arg0
   */
  public EverythingDatabaseTest(String arg0) {
    super(arg0);
  }

  /*
   * @see TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
  }
  /**
   * Test method for 'org.melati.poem.test.generated.EverythingDatabaseBase.getUserTable()'
   * Test method for 'org.melati.poem.Database.getTable(String)'
   */
  public void testGetUserTable() {
      UserTable ut1 = getDb().getUserTable();
      UserTable ut2 = (UserTable)getDb().getTable("user");
      assertEquals(ut1, ut2);
  }

  /**
   * Test method for 'org.melati.poem.Database.getDisplayTables()'
   */
  public void testGetDisplayTables() {
    final String expected = 
    "binaryField (from the data structure definition)" +
    "stringField (from the data structure definition)" + 
    "passwordField (from the data structure definition)" + 
    "booleanField (from the data structure definition)" + 
    "dateField (from the data structure definition)" + 
    "doubleField (from the data structure definition)" + 
    "integerField (from the data structure definition)" + 
    "longField (from the data structure definition)" + 
    "bigdecimalField (from the data structure definition)" + 
    "timestampField (from the data structure definition)" + 
    "everythingNormal (from the data structure definition)" + 
    "eNExtended (from the data structure definition)" + 
    "eAExtended (from the data structure definition)" + 
    "protected (from the data structure definition)" +
    "dynamic (from the data structure definition)" +
    "user (from the data structure definition)" + 
    "group (from the data structure definition)" + 
    "capability (from the data structure definition)" + 
    "groupMembership (from the data structure definition)" + 
    "groupCapability (from the data structure definition)" + 
    "tableInfo (from the data structure definition)" + 
    "columnInfo (from the data structure definition)" + 
    "tableCategory (from the data structure definition)" +
    "setting (from the data structure definition)";

    String outcome = "";
    for (Table t : getDb().getDisplayTables()) { 
      outcome += t.toString();      
    }
    //System.err.println(expected);
    //System.err.println(result);
    assertEquals(expected, outcome);
  }


}
