/**
 *
 */
package org.melati.login.test;


import org.melati.login.OpenAccessHandler;

/**
 * @author timp
 *
 */
public class OpenAccessHandlerTest extends AccessHandlerTestAbstract {

  /**
   * @param name
   */
  public OpenAccessHandlerTest(String name) {
    super(name);
  }

  public void setUp() throws Exception {
    super.setUp();
  }

  /**
   * Create the AccessHandler and set its input stream.
   *
   * @see org.melati.login.test.AccessHandlerTestAbstract#setAccessHandler()
   */
  public void setAccessHandler() {
    OpenAccessHandler ah = new OpenAccessHandler();
    it = ah;
  }
  
  /**
   * Test method for {@link org.melati.login.AccessHandler#establishUser(Melati)}.
   */
  public void testEstablishUser() {
    it.establishUser(m);
    assertEquals("Melati guest user",m.getUser().displayString());
  }

}
