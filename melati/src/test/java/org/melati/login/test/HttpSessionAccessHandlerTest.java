/**
 *
 */
package org.melati.login.test;



import org.melati.login.HttpSessionAccessHandler;
import org.melati.poem.AccessPoemException;
import org.melati.servlet.test.MockHttpServletRequest;
import org.melati.servlet.test.MockHttpServletResponse;
import org.melati.servlet.test.MockHttpSession;
import org.melati.util.HttpServletRequestParameters;


/**
 * @author timp
 *
 */
public class HttpSessionAccessHandlerTest extends AccessHandlerTestAbstract {

  /**
   * @param name
   */
  public HttpSessionAccessHandlerTest(String name) {
    super(name);
  }

  public void setUp() throws Exception {
    super.setUp();
    MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest(); 
    
    MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse(); 
    MockHttpSession mockSession = new MockHttpSession();
    mockSession.setAttribute("org.melati.login.Login.triggeringRequestParameters", 
        new HttpServletRequestParameters(mockHttpServletRequest));
    mockSession.setAttribute("org.melati.login.Login.triggeringException", new AccessPoemException());
    //mockSession.setAttribute("org.melati.login.Login.HttpSessionAccessHandler.overlayParameters", null);
    mockSession.setAttribute("org.melati.login.HttpSessionAccessHandler.user", m.getDatabase().guestAccessToken());
    mockHttpServletRequest.setSession(mockSession);
    m.setRequest(mockHttpServletRequest);
    m.setResponse(mockHttpServletResponse);
  }

  /**
   * Create the AccessHandler and set its input stream.
   *
   * @see org.melati.login.test.AccessHandlerTestAbstract#setAccessHandler()
   */
  public void setAccessHandler() {
    HttpSessionAccessHandler ah = new HttpSessionAccessHandler();
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
