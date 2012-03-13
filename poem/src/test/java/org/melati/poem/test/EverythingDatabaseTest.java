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
  @SuppressWarnings("unchecked")
  public void testGetUserTable() {
      UserTable<org.melati.poem.User> ut1 = getDb().getUserTable();
      UserTable<User> ut2 = (UserTable<User>)getDb().getTable("user");
      assertEquals(ut1, ut2);
  }

  /**
   * Test method for 'org.melati.poem.Database.getDisplayTables()'
   */
  public void testGetDisplayTables() {
    final String expected = 
    "BinaryField (from the data structure definition)" +
    "StringField (from the data structure definition)" + 
    "PasswordField (from the data structure definition)" + 
    "BooleanField (from the data structure definition)" + 
    "DateField (from the data structure definition)" + 
    "DoubleField (from the data structure definition)" + 
    "IntegerField (from the data structure definition)" + 
    "LongField (from the data structure definition)" + 
    "BigdecimalField (from the data structure definition)" + 
    "TimestampField (from the data structure definition)" + 
    "EverythingNormal (from the data structure definition)" + 
    "ENExtended (from the data structure definition)" + 
    "EAExtended (from the data structure definition)" + 
    "Protected (from the data structure definition)" +
    "Dynamic (from the data structure definition)" +
    "User (from the data structure definition)" + 
    "group (from the data structure definition)" + 
    "capability (from the data structure definition)" + 
    "groupMembership (from the data structure definition)" + 
    "groupCapability (from the data structure definition)" + 
    "tableInfo (from the data structure definition)" + 
    "columnInfo (from the data structure definition)" + 
    "tableCategory (from the data structure definition)" +
    "setting (from the data structure definition)";

    String outcome = "";
    for (Table<?> t : getDb().getDisplayTables()) { 
      outcome += t.toString();      
    }
    //System.err.println(expected);
    //System.err.println(result);
    assertEquals(expected, outcome);
  }


}
