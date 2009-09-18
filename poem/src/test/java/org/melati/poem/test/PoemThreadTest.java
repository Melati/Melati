/**
 * 
 */
package org.melati.poem.test;

import java.util.Enumeration;

import org.melati.poem.AccessPoemException;
import org.melati.poem.AlreadyInSessionPoemException;
import org.melati.poem.Capability;
import org.melati.poem.Column;
import org.melati.poem.PoemThread;
import org.melati.poem.transaction.ToTidyList;

/**
 * @author timp
 * @since 24 Jan 2007
 *
 */
public class PoemThreadTest extends PoemTestCase {

  /**
   * Constructor.
   * @param name
   */
  public PoemThreadTest(String name) {
    super(name);
  }

  /**
   * {@inheritDoc}
   * @see org.melati.poem.test.PoemTestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
  }

  /**
   * {@inheritDoc}
   * @see org.melati.poem.test.PoemTestCase#tearDown()
   */
  protected void tearDown() throws Exception {
    super.tearDown();
  }

  /**
   * Check that exception it thrown.
   */
  public void testAlreadyInSession() {
    try {
      getDb().beginSession(PoemThread.accessToken());
      fail("Should have blown up");
    } catch (AlreadyInSessionPoemException e) { 
      e = null;
    }
  }
  /**
   * Called in SessionAnalysisServlet.
   * Test method for {@link org.melati.poem.PoemThread#openSessions()}.
   */
  public void testOpenSessions() {
    assertEquals(1, PoemThread.openSessions().size());
  }

  /**
   * Test method for {@link org.melati.poem.PoemThread#toTidy()}.
   */
  public void testToTidy() {
    ToTidyList list = PoemThread.toTidy();
    Enumeration<Object> en = list.elements();
    int count = 0;
    while (en.hasMoreElements()) { 
      count++;
      en.nextElement();
    }
    assertEquals(0, count);
  }

  /**
   * Test method for {@link org.melati.poem.PoemThread#transaction()}.
   */
  public void testTransaction() {
    
  }

  /**
   * Test method for {@link org.melati.poem.PoemThread#inSession()}.
   */
  public void testInSession() {
    
  }

  /**
   * Test method for {@link org.melati.poem.PoemThread#accessToken()}.
   */
  public void testAccessToken() {
    
  }

  /**
   * Test method for {@link org.melati.poem.PoemThread#
   * setAccessToken(org.melati.poem.AccessToken)}.
   */
  public void testSetAccessToken() {
    
  }

  /**
   * Test method for {@link org.melati.poem.PoemThread#
   * withAccessToken(org.melati.poem.AccessToken, org.melati.poem.PoemTask)}.
   */
  public void testWithAccessToken() {
    
  }

  /**
   * Not used in Melati.
   * Test method for {@link org.melati.poem.PoemThread#
   * assertHasCapability(org.melati.poem.Capability)}.
   */
  public void testAssertHasCapability() {
    Column c = getDb().getCapabilityTable().getNameColumn();
    Capability canWrite = (Capability)c.firstWhereEq("canRead");
    assertNotNull(canWrite);
    PoemThread.assertHasCapability(canWrite);
    PoemThread.assertHasCapability(null);
    PoemThread.setAccessToken(getDb().guestAccessToken());
    try { 
      PoemThread.assertHasCapability(canWrite);
      fail("Should have blown up");
    } catch (AccessPoemException e) { 
      e = null;
    }
  }

  /**
   * Test method for {@link org.melati.poem.PoemThread#database()}.
   */
  public void testDatabase() {
    
  }

  /**
   * Test method for {@link org.melati.poem.PoemThread#writeDown()}.
   */
  public void testWriteDown() {
    
  }

  /**
   * Test method for {@link org.melati.poem.PoemThread#commit()}.
   */
  public void testCommit() {
    
  }

  /**
   * Test method for {@link org.melati.poem.PoemThread#rollback()}.
   */
  public void testRollback() {
    
  }

}
