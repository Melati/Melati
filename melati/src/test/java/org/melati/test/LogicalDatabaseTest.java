/**
 * 
 */
package org.melati.test;

import java.util.Properties;
import java.util.Vector;

import org.melati.LogicalDatabase;
import org.melati.poem.DatabaseInitialisationPoemException;
import org.melati.poem.test.PoemTestCase;
import org.melati.util.DatabaseInitException;

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
  protected void setUp() throws Exception {
    super.setUp();
  }

  /**
   * {@inheritDoc}
   * @see junit.framework.TestCase#tearDown()
   */
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
    Vector them = LogicalDatabase.initialisedDatabases();
    assertTrue(them.size()> 0);
  }

  /**
   * This fails under crap4j, so this is a difference between crap4j/eclipse and maven.
   * 
   * Test method for {@link org.melati.LogicalDatabase#getInitialisedDatabaseNames()}.
   */
  public void testGetInitialisedDatabaseNames() {
    Vector them = LogicalDatabase.getInitialisedDatabaseNames();
    assertTrue(them.size() > 0);
    String name = (String)them.get(0);
    assertEquals("melatijunit", name);
    
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
     LogicalDatabase.getDatabase("empty");
     fail("Should have blown up");
   } catch (DatabaseInitException e) {
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
