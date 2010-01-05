/**
 * 
 */
package org.melati.test;

import java.util.Properties;
import java.util.Vector;

import org.melati.LogicalDatabase;
import org.melati.poem.Database;
import org.melati.poem.DatabaseInitialisationPoemException;
import org.melati.poem.test.PoemTestCase;
import org.melati.util.DatabaseInitException;
import org.melati.util.NoSuchPropertyException;

/**
 * @author timp
 * @since 15 Jan 2007
 *
 */
public class LogicalDatabaseTest extends PoemTestCase {

  /**
   * Constructor.
   * @param name
   */
  public LogicalDatabaseTest(String name) {
    super(name);
  }

  /**
   * {@inheritDoc}
   * @see junit.framework.TestCase#setUp()
   */
  @Override
  protected void setUp() throws Exception {
    super.setUp();
  }

  /**
   * {@inheritDoc}
   * @see junit.framework.TestCase#tearDown()
   */
  @Override
  protected void tearDown() throws Exception {
    super.tearDown();
  }

  /**
   * When run in eclipse only one is know, when run through a suite 
   * then all dbs are found.
   * 
   * Test method for {@link org.melati.LogicalDatabase#initialisedDatabases()}.
   */
  public void testInitialisedDatabases() {
    Vector<Database> them = LogicalDatabase.initialisedDatabases();
    assertTrue(them.size()> 0);
  }

  /**
   * This fails under crap4j, so this is a difference between crap4j/eclipse and maven.
   * 
   * Test method for {@link org.melati.LogicalDatabase#getInitialisedDatabaseNames()}.
   */
  public void testGetInitialisedDatabaseNames() {
    Vector<String> them = LogicalDatabase.getInitialisedDatabaseNames();
    assertTrue(them.size() > 0);
    boolean found = false;
    for (int i = 0; i < them.size(); i++)
      if ((them.get(i)).equals("melatijunit"))
              found = true;
    assertTrue(found);
    
  }

  /**
   * Test method for {@link org.melati.LogicalDatabase#getDatabase(java.lang.String)}.
   * @throws Exception 
   */
  public void testGetDatabase() throws Exception {
    try { 
      LogicalDatabase.getDatabase(null);
      fail("Should have blown up");
    } catch (NullPointerException e) {
      e = null;
    }
    try { 
      LogicalDatabase.getDatabase("bad");
      fail("Should have blown up");
    } catch (DatabaseInitialisationPoemException e) {
      e = null;
    }
    try { 
      LogicalDatabase.getDatabase("unknown");
      fail("Should have blown up");
    } catch (DatabaseInitException e) {
      e = null;
    }
  }

  /**
   * Test method for {@link org.melati.LogicalDatabase#setDatabaseDefs(java.util.Properties)}.
   */
  public void testPropertiesFileNotFound() throws Exception {
   LogicalDatabase.setDatabaseDefs(null);
   try { 
     LogicalDatabase.getDatabase("unknown");
     fail("Should have blown up");
   } catch (DatabaseInitException e) {
     //e.printStackTrace();
     e = null;
   }
   LogicalDatabase.setDatabaseDefs(null);
  }

  /**
   * Test method for {@link org.melati.LogicalDatabase#setDatabaseDefs(java.util.Properties)}.
   */
  public void testSetDatabaseDefs() {
   Properties empty = new Properties(); 
   LogicalDatabase.setDatabaseDefs(empty);
   try { 
     Database ld = LogicalDatabase.getDatabase("unknown");
     fail("Should have blown up but LD = " + ld);
   } catch (DatabaseInitException e) {
     assertTrue(e.subException instanceof NoSuchPropertyException);
     e = null;
   }
   LogicalDatabase.setDatabaseDefs(null);
  }

  
  /**
   * Test method for {@link org.melati.LogicalDatabase#getPropertiesName()}.
   */
  public void testGetDefaultPropertiesName() {
    assertEquals("org.melati.LogicalDatabase.properties", LogicalDatabase.getPropertiesName());
  }

}
