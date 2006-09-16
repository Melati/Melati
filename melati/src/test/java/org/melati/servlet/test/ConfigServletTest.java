/**
 * 
 */
package org.melati.servlet.test;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import junit.framework.TestCase;
import com.mockobjects.dynamic.Mock;
import com.mockobjects.dynamic.OrderedMock;

/**
 * @author timp
 *
 */
public class ConfigServletTest extends TestCase {

  /**
   * Constructor for ConfigServletTest.
   * @param name
   */
  public ConfigServletTest(String name) {
    super(name);
    
  }

  /*
   * @see TestCase#setUp()
   */
  protected void setUp()
      throws Exception {
    super.setUp();
  }

  /*
   * @see TestCase#tearDown()
   */
  protected void tearDown()
      throws Exception {
    super.tearDown();
  }

  /*
   * Test method for 'org.melati.servlet.ConfigServlet.init(ServletConfig)'
   */
  public void testInitServletConfig() {

  }

  /*
   * Test method for 'org.melati.servlet.ConfigServlet.doGet(HttpServletRequest, HttpServletResponse)'
   */
  public void testDoGetHttpServletRequestHttpServletResponse() {
    Mock mockHttpServletRequest = new Mock(HttpServletRequest.class); 
    Mock mockHttpServletResponse = new OrderedMock(HttpServletResponse.class, "Response with non-default name"); 
                   
    mockHttpServletRequest.expectAndReturn( "getHeader", "Accept-Charset", "ISO-8859-1"); 
    mockHttpServletRequest.expectAndReturn( "getCharacterEncoding", "ISO-8859-1"); 
    mockHttpServletRequest.expectAndReturn( "getPathInfo", "melatitest/user/1"); 
    mockHttpServletRequest.expectAndReturn( "getRequestURI", null); 
    mockHttpServletRequest.expectAndReturn( "getRequestURI", null); 
    mockHttpServletRequest.expectAndReturn( "getQueryString", null); 
    mockHttpServletRequest.expectAndReturn( "getQueryString", null); 
    mockHttpServletRequest.expectAndReturn( "getServerName", "mockServer"); 
    mockHttpServletRequest.expectAndReturn( "getScheme", "mockScheme"); 
    mockHttpServletRequest.expectAndReturn( "getContextPath", "mockContextPath"); 
    mockHttpServletRequest.expectAndReturn( "getServletPath", "mockContextPath"); 
    
    final StringWriter output = new StringWriter(); 
    final PrintWriter contentWriter = new PrintWriter(output); 
           
    mockHttpServletResponse.expect( "setContentType", "text/html; charset=ISO-8859-1"); 
    mockHttpServletResponse.expectAndReturn( "getWriter", contentWriter ); 
    mockHttpServletResponse.expect( "setContentType", "text/html"); 

    Mock mockServletConfig = new Mock(ServletConfig.class);
    Mock mockServletContext = new Mock(ServletContext.class);
    mockServletConfig.expectAndReturn("getServletContext", (ServletContext)mockServletContext.proxy()); 
    mockServletConfig.expectAndReturn("getServletName", "MelatiConfigTest");
    mockServletContext.expectAndReturn("log","MelatiConfigTest: init", null);
    org.melati.test.ConfigServletTest aServlet = 
          new org.melati.test.ConfigServletTest();
    try {
      aServlet.init((ServletConfig)mockServletConfig.proxy());
      aServlet.doGet((HttpServletRequest) mockHttpServletRequest.proxy(),  
                     (HttpServletResponse) mockHttpServletResponse.proxy());
    } catch (Exception e) {
      e.printStackTrace();
      fail();
    } 
                   
    mockHttpServletRequest.verify(); 
    mockHttpServletResponse.verify(); 
    mockServletConfig.verify(); 
    mockServletContext.verify(); 
    assertTrue(output.toString().indexOf("<h2>ConfigServlet Test</h2>") != -1); 
  }

  /**
   * Test method for 'org.melati.servlet.ConfigServlet.doPost(HttpServletRequest, HttpServletResponse)'
   */
  public void testDoPostHttpServletRequestHttpServletResponse() {
    Mock mockHttpServletRequest = new Mock(HttpServletRequest.class); 
    Mock mockHttpServletResponse = new OrderedMock(HttpServletResponse.class, "Response with non-default name"); 
                   
    mockHttpServletRequest.expectAndReturn( "getHeader", "Accept-Charset", "ISO-8859-1"); 
    mockHttpServletRequest.expectAndReturn( "getCharacterEncoding", "ISO-8859-1"); 
    mockHttpServletRequest.expectAndReturn( "getPathInfo", "melatitest/user/1"); 
    mockHttpServletRequest.expectAndReturn( "getRequestURI", null); 
    mockHttpServletRequest.expectAndReturn( "getRequestURI", null); 
    mockHttpServletRequest.expectAndReturn( "getQueryString", null); 
    mockHttpServletRequest.expectAndReturn( "getQueryString", null); 
    mockHttpServletRequest.expectAndReturn( "getServerName", "mockServer"); 
    mockHttpServletRequest.expectAndReturn( "getScheme", "mockScheme"); 
    mockHttpServletRequest.expectAndReturn( "getContextPath", "mockContextPath"); 
    mockHttpServletRequest.expectAndReturn( "getServletPath", "mockContextPath"); 
    
    final StringWriter output = new StringWriter(); 
    final PrintWriter contentWriter = new PrintWriter(output); 
           
    mockHttpServletResponse.expect( "setContentType", "text/html; charset=ISO-8859-1"); 
    mockHttpServletResponse.expectAndReturn( "getWriter", contentWriter ); 
    mockHttpServletResponse.expect( "setContentType", "text/html"); 

    Mock mockServletConfig = new Mock(ServletConfig.class);
    Mock mockServletContext = new Mock(ServletContext.class);
    mockServletConfig.expectAndReturn("getServletContext", (ServletContext)mockServletContext.proxy()); 
    mockServletConfig.expectAndReturn("getServletName", "MelatiConfigTest");
    mockServletContext.expectAndReturn("log","MelatiConfigTest: init", null);
    org.melati.test.ConfigServletTest aServlet = 
          new org.melati.test.ConfigServletTest();
    try {
      aServlet.init((ServletConfig)mockServletConfig.proxy());
      aServlet.doPost((HttpServletRequest) mockHttpServletRequest.proxy(),  
                     (HttpServletResponse) mockHttpServletResponse.proxy());
    } catch (Exception e) {
      e.printStackTrace();
      fail();
    } 
                   
    mockHttpServletRequest.verify(); 
    mockHttpServletResponse.verify(); 
    mockServletConfig.verify(); 
    mockServletContext.verify(); 
    assertTrue(output.toString().indexOf("<h2>ConfigServlet Test</h2>") != -1); 

  }

  /*
   * Test method for 'org.melati.servlet.ConfigServlet.error(Melati, Exception)'
   */
  public void testError() {

  }

  /*
   * Test method for 'org.melati.servlet.ConfigServlet.writeError(PrintWriter, Exception)'
   */
  public void testWriteError() {

  }

  /*
   * Test method for 'org.melati.servlet.ConfigServlet.writeConnectionPendingException(PrintWriter, Exception)'
   */
  public void testWriteConnectionPendingException() {

  }

  /*
   * Test method for 'org.melati.servlet.ConfigServlet.getSysAdminName()'
   */
  public void testGetSysAdminName() {
    Mock mockHttpServletRequest = new Mock(HttpServletRequest.class); 
    Mock mockHttpServletResponse = new OrderedMock(HttpServletResponse.class, "Response with non-default name"); 
                   
    Mock mockServletConfig = new Mock(ServletConfig.class);
    Mock mockServletContext = new Mock(ServletContext.class);
    mockServletConfig.expectAndReturn("getServletContext", (ServletContext)mockServletContext.proxy()); 
    mockServletConfig.expectAndReturn("getServletName", "MelatiConfigTest");
    mockServletContext.expectAndReturn("log","MelatiConfigTest: init", null);
    org.melati.test.ConfigServletTest aServlet = 
          new org.melati.test.ConfigServletTest();
    try {
      aServlet.init((ServletConfig)mockServletConfig.proxy());
      assertEquals("nobody", aServlet.getSysAdminName());
    } catch (Exception e) {
      e.printStackTrace();
      fail();
    } 
                   
    mockHttpServletRequest.verify(); 
    mockHttpServletResponse.verify(); 
    mockServletConfig.verify(); 
    mockServletContext.verify(); 

  }

  /*
   * Test method for 'org.melati.servlet.ConfigServlet.getSysAdminEmail()'
   */
  public void testGetSysAdminEmail() {
    Mock mockHttpServletRequest = new Mock(HttpServletRequest.class); 
    Mock mockHttpServletResponse = new OrderedMock(HttpServletResponse.class, "Response with non-default name"); 
                   
    Mock mockServletConfig = new Mock(ServletConfig.class);
    Mock mockServletContext = new Mock(ServletContext.class);
    mockServletConfig.expectAndReturn("getServletContext", (ServletContext)mockServletContext.proxy()); 
    mockServletConfig.expectAndReturn("getServletName", "MelatiConfigTest");
    mockServletContext.expectAndReturn("log","MelatiConfigTest: init", null);
    org.melati.test.ConfigServletTest aServlet = 
          new org.melati.test.ConfigServletTest();
    try {
      aServlet.init((ServletConfig)mockServletConfig.proxy());
      assertEquals("nobody@nobody.com", aServlet.getSysAdminEmail());
    } catch (Exception e) {
      e.printStackTrace();
      fail();
    } 
                   
    mockHttpServletRequest.verify(); 
    mockHttpServletResponse.verify(); 
    mockServletConfig.verify(); 
    mockServletContext.verify(); 


  }

}
