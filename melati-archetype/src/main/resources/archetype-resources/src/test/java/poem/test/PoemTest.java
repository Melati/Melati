package ${groupId}.${artifactId}.model.test;

import org.melati.poem.test.PoemDatabaseTest;

/**
 * @author timp
 * @since 10 May 2007
 *
 */
public class PoemTest extends PoemDatabaseTest {
  /**
   * Constructor for PoemTest.
   * 
   * @param arg0
   */
  public PoemTest(String arg0) {
    super(arg0);
  }

  /**
   * @see TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
  }

  /**
   * @see TestCase#tearDown()
   */
  protected void tearDown() throws Exception {
    super.tearDown();
    getDb().setLogCommits(false);
    getDb().setLogSQL(false);
  }
  
  //protected void databaseUnchanged() { 
  //}
  /** 
   * We have different tables. 
   */
  public void testGetDisplayTables() {
  }
}
