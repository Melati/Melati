/**
 * 
 */
package org.melati.test;

import java.util.Vector;

import org.melati.LogicalDatabase;
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
   * Test method for {@link org.melati.LogicalDatabase#initialisedDatabases()}.
   */
  public void testInitialisedDatabases() {
    Vector them = LogicalDatabase.initialisedDatabases();
    assertEquals(1,them.size());
  }

  /**
   * Test method for {@link org.melati.LogicalDatabase#getInitialisedDatabaseNames()}.
   */
  public void testGetInitialisedDatabaseNames() {
    Vector them = LogicalDatabase.getInitialisedDatabaseNames();
    assertEquals(1,them.size()); 
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
  }

  /**
   * Test method for {@link org.melati.LogicalDatabase#setDatabaseDefs(java.util.Properties)}.
   */
  public void testSetDatabaseDefs() {
    
  }

  /**
   * Test method for {@link org.melati.LogicalDatabase#getDefaultPropertiesName()}.
   */
  public void testGetDefaultPropertiesName() {
    assertEquals("org.melati.LogicalDatabse.properties", LogicalDatabase.getDefaultPropertiesName());
  }

}
