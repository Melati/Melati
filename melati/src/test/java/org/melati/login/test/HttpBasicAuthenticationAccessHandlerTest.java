/**
 *
 */
package org.melati.login.test;


import org.melati.Melati;
import org.melati.login.HttpAuthorizationMelatiException;
import org.melati.login.HttpBasicAuthenticationAccessHandler;
import org.melati.servlet.test.MockHttpServletRequest;
import org.melati.servlet.test.MockHttpServletResponse;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;


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

  /**
   * Test method for {@link org.melati.login.AccessHandler#establishUser(Melati)}.
   */
  public void testEstablishUserFromRequest() {
    ((MockHttpServletRequest)m.getRequest()).setHeader("Authorization", 
        "Basic " + Base64.encode("_administrator_:FIXME".getBytes()));
    it.establishUser(m);
    assertEquals("Melati database administrator",m.getUser().displayString());
  }
  /**
   * Test method for {@link org.melati.login.AccessHandler#establishUser(Melati)}.
   */
  public void testEstablishUserFromRequestWrongMethod() {
    ((MockHttpServletRequest)m.getRequest()).setHeader("Authorization", 
        "basic " + Base64.encode("_administrator_:FIXME".getBytes()));
    try { 
      it.establishUser(m);
      fail("Should have bombed");
    } catch (HttpAuthorizationMelatiException e) { 
      e = null;
    }
    ((MockHttpServletRequest)m.getRequest()).setHeader("Authorization", 
        "Basic " + Base64.encode("_administrator_/FIXME".getBytes()));
    try { 
      it.establishUser(m);
      fail("Should have bombed");
    } catch (HttpAuthorizationMelatiException e) { 
      e = null;
    }
    ((MockHttpServletRequest)m.getRequest()).setHeader("Authorization", 
        "Basic" + Base64.encode("_administrator_:FIXME".getBytes()));
    try { 
      it.establishUser(m);
      fail("Should have bombed");
    } catch (HttpAuthorizationMelatiException e) { 
      e = null;
    }
  }
}
