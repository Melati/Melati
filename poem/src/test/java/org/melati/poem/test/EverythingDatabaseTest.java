package org.melati.poem.test;

import java.util.Enumeration;

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
    "protected (from the data structure definition)" +
    "dynamic (from the data structure definition)" +
    "user (from the data structure definition)" + 
    "group (from the data structure definition)" + 
    "capability (from the data structure definition)" + 
    "groupmembership (from the data structure definition)" + 
    "groupcapability (from the data structure definition)" + 
    "tableinfo (from the data structure definition)" + 
    "columninfo (from the data structure definition)" + 
    "tablecategory (from the data structure definition)" +
    "setting (from the data structure definition)";

    Enumeration en = getDb().getDisplayTables();
    String outcome = "";
    while (en.hasMoreElements()) {
      outcome += en.nextElement().toString();
    }
    //System.err.println(expected);
    //System.err.println(result);
    assertEquals(expected, outcome);
  }


}
