/**
 *
 */
package org.melati.login.test;


import org.melati.login.HttpBasicAuthenticationAccessHandler;
import org.melati.servlet.test.MockHttpServletRequest;
import org.melati.servlet.test.MockHttpServletResponse;


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
    MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
    MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();
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
