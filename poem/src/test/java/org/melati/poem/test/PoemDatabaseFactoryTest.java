/**
 * 
 */
package org.melati.poem.test;

import java.util.Vector;

import junit.framework.TestCase;

import org.melati.poem.Database;
import org.melati.poem.DatabaseInitialisationPoemException;
import org.melati.poem.PoemDatabaseFactory;


/**
 * @author timp
 * @since 2 Feb 2007
 *
 */
public class PoemDatabaseFactoryTest extends TestCase {

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
   * Test method for {@link org.melati.poem.PoemDatabaseFactory#getDatabase(String)}.
   * @throws Exception 
   */
  public void testGetDatabase() throws Exception {
    try { 
      PoemDatabaseFactory.getDatabase(null);
      fail("Should have blown up");
    } catch (NullPointerException e) {
      e = null;
    }
    assertNull(PoemDatabaseFactory.getDatabase("unknown"));
  }

  /**
   * Test method for {@link org.melati.poem.PoemDatabaseFactory
   * #getDatabase(String, String, String, String, String, String, boolean, boolean, boolean, int)}.
   * @throws Exception 
   */
  public void testGetDatabaseStringStringStringStringStringBooleanBooleanBooleanInt() throws Exception {
    try { 
      PoemDatabaseFactory.getDatabase(null);
      fail("Should have blown up");
    } catch (NullPointerException e) {
      e = null;
    }
    
    assertNull(PoemDatabaseFactory.getDatabase("unknown"));
    try { 
      PoemDatabaseFactory.getDatabase("badclassname",
            "jdbc:hsqldb:mem:" + PoemTestCase.databaseName,
            "sa",
            "","org.melati.poem.PoemDatabaseNOT",
            "org.melati.poem.dbms.Hsqldb",false,false,false,4);
    } catch (DatabaseInitialisationPoemException e) { 
      assertTrue(e.innermostException() instanceof ClassNotFoundException);
    }
    try { 
      PoemDatabaseFactory.getDatabase("badclassname",
            "jdbc:hsqldb:mem:" + PoemTestCase.databaseName,
            "sa",
            "","java.lang.Exception",
            "org.melati.poem.dbms.Hsqldb",false,false,false,4);
    } catch (DatabaseInitialisationPoemException e) { 
      assertTrue(e.innermostException() instanceof ClassCastException);
    }
  }


  
  /**
   * Test method for {@link org.melati.poem.PoemDatabaseFactory#initialisedDatabases()}.
   */
  public void testInitialisedDatabases() {
    getPoemDatabase();
    getEverythingDatabase();
    Vector them = PoemDatabaseFactory.initialisedDatabases();
    assertEquals(2,them.size());
  }

  /**
   * Test method for {@link org.melati.poem.PoemDatabaseFactory#getInitialisedDatabaseNames()}.
   */
  public void testGetInitialisedDatabaseNames() {
    Vector them = PoemDatabaseFactory.getInitialisedDatabaseNames();
    assertEquals(2,them.size()); 
    String name = (String)them.get(1);
    assertEquals(EverythingTestCase.databaseName, name);
    
  }

  /**
   * Test method for {@link org.melati.poem.PoemDatabaseFactory#removeDatabase(String)}.
   */
  public void testRemoveDatabaseString() {
    
  }
  public Database getPoemDatabase() { 
    return PoemDatabaseFactory.getDatabase(PoemTestCase.databaseName,
            "jdbc:hsqldb:mem:" + PoemTestCase.databaseName,
            "sa",
            "","org.melati.poem.PoemDatabase",
            "org.melati.poem.dbms.Hsqldb",true,true,false,4);
  }
  public static Database getEverythingDatabase() { 
    return PoemDatabaseFactory.getDatabase(EverythingTestCase.databaseName,
            "jdbc:hsqldb:mem:" + EverythingTestCase.databaseName,
            "sa",
            "","org.melati.poem.test.EverythingDatabase",
            "org.melati.poem.dbms.Hsqldb",false,false,false,4);
  }

}
