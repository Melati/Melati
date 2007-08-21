/**
 * 
 */
package org.melati.test;

import org.melati.Melati;
import org.melati.poem.test.PoemTestCase;
import org.melati.util.MelatiStringWriter;

/**
 * @author timp
 * @since 21 Aug 2007
 *
 */
public class InSessionMelatiTest extends PoemTestCase {

  /**
   * 
   */
  public InSessionMelatiTest() {
  }

  /**
   * @param name
   */
  public InSessionMelatiTest(String name) {
    super(name);
  }

  /**
   * We have a RootAccessToken, so will get a null User.
   * @see org.melati.Melati#getUser()
   */
  public void testGetUser() throws Exception {
    Melati m = new Melati(null, new MelatiStringWriter());
    assertNull(m.getUser());
  }
}
