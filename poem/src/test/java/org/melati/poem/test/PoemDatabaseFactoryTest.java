/**
 * 
 */
package org.melati.poem.test;

import java.util.Vector;

import org.melati.poem.PoemDatabaseFactory;


/**
 * @author timp
 * @since 2 Feb 2007
 *
 */
public class PoemDatabaseFactoryTest extends PoemTestCase {

  /**
   * Constructor.
   * @param name
   */
  public PoemDatabaseFactoryTest(String name) {
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
   * Test method for {@link org.melati.LogicalDatabase#getDatabase(java.lang.String)}.
   * @throws Exception 
   */
  public void testGetDatabase() throws Exception {
    try { 
      PoemDatabaseFactory.getDatabase(null);
      fail("Should have blown up");
    } catch (NullPointerException e) {
      e = null;
    }
  }


  
  /**
   * Test method for {@link org.melati.LogicalDatabase#initialisedDatabases()}.
   */
  public void testInitialisedDatabases() {
    Vector them = PoemDatabaseFactory.initialisedDatabases();
    assertEquals(1,them.size());
  }

  /**
   * Test method for {@link org.melati.LogicalDatabase#getInitialisedDatabaseNames()}.
   */
  public void testGetInitialisedDatabaseNames() {
    Vector them = PoemDatabaseFactory.getInitialisedDatabaseNames();
    assertEquals(1,them.size()); 
    String name = (String)them.get(0);
    assertEquals("melatijunit", name);
    
  }

  /**
   * Test method for {@link org.melati.LogicalDatabase#setDatabaseDefs(java.util.Properties)}.
   */
  public void testSetDatabaseDefs() {
    
  }

}
