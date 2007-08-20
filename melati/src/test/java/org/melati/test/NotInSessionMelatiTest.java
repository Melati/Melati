/**
 * 
 */
package org.melati.test;

import org.melati.Melati;
import org.melati.util.MelatiStringWriter;

import junit.framework.TestCase;

/**
 * @author timp
 * @since 20 Aug 2007
 *
 */
public class NotInSessionMelatiTest extends TestCase {

  /**
   * @param name
   */
  public NotInSessionMelatiTest(String name) {
    super(name);
  }

  /**
   * @see TestCase#setUp()
   */
  protected void setUp()
      throws Exception {
    super.setUp();
  }

  /**
   * @see TestCase#tearDown()
   */
  protected void tearDown()
      throws Exception {
    super.tearDown();
  }

  /**
   * We have no access token, so will get a null User.
   * @see org.melati.Melati#getUser()
   */
  public void testGetUser() throws Exception {
    Melati m = new Melati(null, new MelatiStringWriter());
    assertNull(m.getUser());
  }
  
}
