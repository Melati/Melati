/**
 * 
 */
package org.melati.servlet.test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.melati.Melati;

import junit.framework.TestCase;


/**
 * @author timp
 *
 */
public class PoemServletTest extends TestCase {

  /**
   * Constructor for PoemServletTest.
   * @param name
   */
  public PoemServletTest(String name) {
    super(name);
  }

  /**
   * @see PoemTestCase#setUp()
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
   * @throws ServletException 
   * @see org.melati.servlet.PoemServlet#getSysAdminName()
   */
  public void testGetSysAdminName() throws ServletException {
                   
    MockServletConfig mockServletConfig = new MockServletConfig();
    
    org.melati.test.PoemServletTest aServlet = 
          new org.melati.test.PoemServletTest();
    aServlet.init(mockServletConfig);
    assertEquals("nobody", aServlet.getSysAdminName());

    aServlet.destroy();
  }

  /**
   * @throws ServletException 
   * @see org.melati.servlet.PoemServlet#getSysAdminEmail()
   */
  public void testGetSysAdminEmail() throws ServletException {
    MockServletConfig mockServletConfig = new MockServletConfig();
    org.melati.test.PoemServletTest aServlet = 
          new org.melati.test.PoemServletTest();
    aServlet.init(mockServletConfig);
    assertEquals("nobody@nobody.com", aServlet.getSysAdminEmail());
                   
    aServlet.destroy();

  }


  /**
   * @see org.melati.servlet.PoemServlet#prePoemSession(Melati)
   */
  public void testPrePoemSession() {

  }

  /**
   * @throws Exception 
   * @see org.melati.servlet.ConfigServlet#doGet(HttpServletRequest, HttpServletResponse)
   */
  public void testDoGetHttpServletRequestHttpServletResponse() throws Exception {
    MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
    MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse(); 
                   
    mockHttpServletRequest.setPathInfo("/melatitest/user/1");
    
    mockHttpServletRequest.setHeader("Accept-Charset", "ISO-8859-1"); 
    

    MockServletConfig mockServletConfig = new MockServletConfig();

    org.melati.test.HttpAuthenticationPoemServletTest aServlet = 
          new org.melati.test.HttpAuthenticationPoemServletTest();
    aServlet.init(mockServletConfig);
    aServlet.doGet(mockHttpServletRequest,  
                   mockHttpServletResponse);
    String output = mockHttpServletResponse.getWritten();
    assertTrue("Unexpected output (check org.melati.LogicalDatabase properties): " + output.toString(), 
                output.toString().indexOf("<h2>PoemServlet Test</h2>") != -1); 

    aServlet.destroy();
  }

  /**
   * @see org.melati.servlet.ConfigServlet#doPost(HttpServletRequest, HttpServletResponse)
   */
  public void testDoPostHttpServletRequestHttpServletResponse() throws Exception {
    MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
    MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse(); 
                   
    mockHttpServletRequest.setCharacterEncoding("ISO-8859-1"); 
    mockHttpServletRequest.setPathInfo("/melatitest/user/1"); 
    mockHttpServletRequest.setHeader("Accept-Charset", "ISO-8859-1"); 
    
           
    MockServletConfig mockServletConfig = new MockServletConfig();

    org.melati.test.HttpAuthenticationPoemServletTest aServlet = 
      new org.melati.test.HttpAuthenticationPoemServletTest();
    aServlet.init(mockServletConfig);
    aServlet.doPost(mockHttpServletRequest,  
                    mockHttpServletResponse);
    String output = mockHttpServletResponse.getWritten();
    assertTrue("Unexpected output (check org.melati.LogicalDatabase properties): " + 
            output.toString(), output.toString().indexOf("<h2>PoemServlet Test</h2>") != -1); 
    aServlet.destroy();

  }

  /**
   * @throws Exception 
   * @see org.melati.servlet.PoemServlet#error(Melati, Exception)
   */
  public void testError() throws Exception {
    MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
    MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse(); 
                   
    MockHttpSession mockSession = new MockHttpSession();

    MockServletConfig mockServletConfig = new MockServletConfig();
    mockServletConfig.setInitParameter("pathInfo", "melatitest/user/1");

    mockHttpServletRequest.setSession(mockSession);
    

    ExceptionPoemServlet aServlet = 
          new ExceptionPoemServlet();
    aServlet.init(mockServletConfig);
    aServlet.doPost(mockHttpServletRequest,  
                    mockHttpServletResponse);
    aServlet.destroy();
                   
    String output = mockHttpServletResponse.getWritten();
    // Request gets redirected to login
    assertTrue(output.toString().equals("")); 

  }

  /**
   * Test logical database.
   * 
   */
  public void testLDB() throws Exception {
    MockHttpServletResponse response = new MockHttpServletResponse();
    MockHttpServletRequest request = new MockHttpServletRequest();
    MockServletConfig mockServletConfig = new MockServletConfig();
    LDBPoemServlet aServlet = 
      new LDBPoemServlet();
    aServlet.init(mockServletConfig);
    aServlet.doPost(request, response);
    System.out.println(response.getWritten().toString());
    aServlet.destroy();
    assertTrue(response.getWritten().toString().indexOf("logicalDatabase = melatijunit") != -1);
  }

}
