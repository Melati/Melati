package org.melati.poem.test;

import org.melati.poem.PoemTask;
import org.melati.poem.PoemThread;
import org.melati.poem.PoemTransaction;
import org.melati.poem.SessionToken;

/**
 * @author timp
 * @since 29 Jan 2007
 *
 */
public class SessionTokenTest extends PoemTestCase {

  public SessionTokenTest(String name) {
    super(name);
  }

  protected void setUp() throws Exception {
    super.setUp();
  }

  protected void tearDown() throws Exception {
    super.tearDown();
  }

  /**
   * Test method for {@link org.melati.poem.SessionToken#toTidy()}.
   */
  public void testToTidy() {
    
  }

  /**
   * Test method for {@link org.melati.poem.SessionToken#getStarted()}.
   */
  public void testGetStarted() {
    SessionToken st = PoemThread.sessionToken();
    long started = st.getStarted();
    //System.err.println(started +"<"+System.currentTimeMillis());
    assertTrue(started <= System.currentTimeMillis());    
  }

  /**
   * Test method for {@link org.melati.poem.SessionToken#getTask()}.
   */
  public void testGetTask() {
    SessionToken st = PoemThread.sessionToken();
    PoemTask task = st.getTask();
    assertEquals("PoemTestCase:testGetTask",task.toString());
  }

  /**
   * Test method for {@link org.melati.poem.SessionToken#getThread()}.
   */
  public void testGetThread() {
    SessionToken st = PoemThread.sessionToken();
    Thread thread = st.getThread();
    assertEquals(Thread.currentThread(),thread);
  }

  /**
   * Test method for {@link org.melati.poem.SessionToken#getTransaction()}.
   */
  public void testGetTransaction() {
    SessionToken st = PoemThread.sessionToken();
    PoemTransaction transaction = st.getTransaction();
    assertEquals(getDb(),transaction.getDatabase());
  }

}
