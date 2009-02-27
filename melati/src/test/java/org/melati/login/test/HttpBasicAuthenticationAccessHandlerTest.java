/**
 *
 */
package org.melati.login.test;


import org.melati.login.HttpBasicAuthenticationAccessHandler;
import org.melati.servlet.test.MockServletRequest;
import org.melati.servlet.test.MockServletResponse;


/**
 * @author timp
 *
 */
public class HttpBasicAuthenticationAccessHandlerTest extends AccessHandlerTestAbstract {

  /**
   * @param name
   */
  public HttpBasicAuthenticationAccessHandlerTest(String name) {
    super(name);
  }

  public void setUp() throws Exception {
    super.setUp();
    MockServletRequest mockHttpServletRequest = new MockServletRequest();
    MockServletResponse mockHttpServletResponse = new MockServletResponse();
    m.setRequest(mockHttpServletRequest);
    m.setResponse(mockHttpServletResponse);
  }

  /**
   * Create the AccessHandler and set its input stream.
   *
   * @see org.melati.login.test.AccessHandlerTestAbstract#setAccessHandler()
   */
  public void setAccessHandler() {
    HttpBasicAuthenticationAccessHandler ah = new HttpBasicAuthenticationAccessHandler();
    it = ah;
  }

}
