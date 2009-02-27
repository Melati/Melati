package org.melati.servlet.test;

import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import junit.framework.TestCase;

/**
 * @author timp
 */
public class ConfigServletTest extends TestCase {

  /**
   * Constructor for ConfigServletTest.
   * @param name
   */
  public ConfigServletTest(String name) {
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
   * @see org.melati.servlet.ConfigServlet#init(ServletConfig)
   */
  public void testInitServletConfig() {

  }

  /**
   * @see org.melati.servlet.ConfigServlet#doGet(HttpServletRequest, HttpServletResponse)
   */
  public void testDoGetHttpServletRequestHttpServletResponse() {
    MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest(); 
    MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse(); 
                   
    mockHttpServletRequest.setPathInfo("melatitest/user/1"); 

    MockServletConfig mockServletConfig = new MockServletConfig();
    org.melati.test.ConfigServletTest aServlet = 
          new org.melati.test.ConfigServletTest();
    try {
      aServlet.init(mockServletConfig);
      aServlet.doGet(mockHttpServletRequest,  
                     mockHttpServletResponse);
    } catch (ServletException e) {
      e.printStackTrace();
      fail(e.toString());
    } 

    aServlet.destroy();

    String output = mockHttpServletResponse.getWritten();
    assertTrue(output.toString().indexOf("<h2>ConfigServlet Test</h2>") != -1); 
  }

  /**
   * @see org.melati.servlet.ConfigServlet#doPost(HttpServletRequest, HttpServletResponse)
   */
  public void testDoPostHttpServletRequestHttpServletResponse() {
    MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest(); 
    MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse(); 
                   
    mockHttpServletRequest.setPathInfo("melatitest/user/1"); 
    
    MockServletConfig mockServletConfig = new MockServletConfig();
    org.melati.test.ConfigServletTest aServlet = 
          new org.melati.test.ConfigServletTest();
    try {
      aServlet.init(mockServletConfig);
      aServlet.doPost(mockHttpServletRequest,  
                     mockHttpServletResponse);
    } catch (Exception e) {
      e.printStackTrace();
      fail();
    } 
                   
    aServlet.destroy();

    String output = mockHttpServletResponse.getWritten();
    assertTrue(output.toString().indexOf("<h2>ConfigServlet Test</h2>") != -1); 

  }

  /**
   * @see org.melati.servlet.ConfigServlet#error(Melati, Exception)
   */
  public void testError() {
    MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest(); 
    MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse(); 
                   
    mockHttpServletRequest.setPathInfo("melatitest/user/1"); 
    
    MockServletConfig mockServletConfig = new MockServletConfig();
    ErrorConfigServlet aServlet = 
          new ErrorConfigServlet();
    try {
      aServlet.init(mockServletConfig);
      aServlet.doPost(mockHttpServletRequest,  
                     mockHttpServletResponse);
    } catch (Exception e) {
      e.printStackTrace();
      fail();
    } 
                   
    aServlet.destroy();

    String output = mockHttpServletResponse.getWritten();
    assertTrue(output.toString().indexOf("You need the capability") != -1); 


  }

  /**
   * @see org.melati.servlet.ConfigServlet#error(Melati, Exception)
   */
  public void testConnectionPendingError() throws Exception {
    MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest(); 
    MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse(); 
                   
    mockHttpServletRequest.setPathInfo("melatitest/user/1"); 
    
    MockServletConfig mockServletConfig = new MockServletConfig();
    DbPendingErrorConfigServlet aServlet = 
          new DbPendingErrorConfigServlet();
    aServlet.init(mockServletConfig);
    aServlet.doPost(mockHttpServletRequest,  
                   mockHttpServletResponse);
    aServlet.destroy();
                   
    String output = mockHttpServletResponse.getWritten();
    assertTrue(output.toString().indexOf("The database `testdb' is in the process of being initialized") != -1); 

  }
  
  /**
   * Test expection thrown during initialisation.
   */
  public void testExceptionDuringInit() throws Exception {
    MockHttpServletResponse response = new MockHttpServletResponse();
    MockHttpServletRequest request = new MockHttpServletRequest();
    MockServletConfig mockServletConfig = new MockServletConfig();
    MelatiConfigExceptionConfigServlet aServlet = 
      new MelatiConfigExceptionConfigServlet();
    try {
      aServlet.init(mockServletConfig);
      aServlet.doPost(request,  
                     response);
      fail("Should have blown up");
    } catch (ServletException e) {
      assertEquals("org.melati.util.ConfigException: Pretend bug", e.getMessage());
    }
    aServlet.destroy();
  }
  

  /**
   * @see org.melati.servlet.ConfigServlet#writeError(PrintWriter, Exception)
   */
  public void testWriteError() {

  }

  /**
   * @see org.melati.servlet.ConfigServlet#writeConnectionPendingException(PrintWriter, Exception)
   */
  public void testWriteConnectionPendingException() {

  }

  /**
   * @see org.melati.servlet.ConfigServlet#getSysAdminName()
   */
  public void testGetSysAdminName() {
    MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest(); 
    MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse(); 
                   
    mockHttpServletRequest.setPathInfo("melatitest/user/1"); 
           
    MockServletConfig mockServletConfig = new MockServletConfig();

    org.melati.test.ConfigServletTest aServlet = 
          new org.melati.test.ConfigServletTest();
    try {
      aServlet.init(mockServletConfig);
      assertEquals("nobody", aServlet.getSysAdminName());
      aServlet.doPost(mockHttpServletRequest,  
          mockHttpServletResponse);
      aServlet.destroy();               
    } catch (Exception e) {
      e.printStackTrace();
      fail();
    } 
  }

  /**
   * @see org.melati.servlet.ConfigServlet#getSysAdminEmail()
   */
  public void testGetSysAdminEmail() {
    MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest(); 
    MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse(); 

    mockHttpServletRequest.setPathInfo("melatitest/user/1"); 

    MockServletConfig mockServletConfig = new MockServletConfig();

    org.melati.test.ConfigServletTest aServlet = 
          new org.melati.test.ConfigServletTest();
    try {
      aServlet.init(mockServletConfig);
      assertEquals("nobody@nobody.com", aServlet.getSysAdminEmail());
      aServlet.doPost(mockHttpServletRequest,  
          mockHttpServletResponse);
      aServlet.destroy();               
    } catch (Exception e) {
      e.printStackTrace();
      fail();
    } 
  }

}
