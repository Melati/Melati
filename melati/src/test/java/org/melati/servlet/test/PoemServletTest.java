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

import org.melati.poem.test.PoemTestCase;

import com.mockobjects.dynamic.Mock;
import com.mockobjects.dynamic.OrderedMock;

/**
 * @author timp
 *
 */
public class PoemServletTest extends PoemTestCase {

  /**
   * Constructor for PoemServletTest.
   * @param name
   */
  public PoemServletTest(String name) {
    super(name);
  }

  /*
   * @see PoemTestCase#setUp()
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
   * Test method for 'org.melati.servlet.PoemServlet.getSysAdminName()'
   */
  public void testGetSysAdminName() {
    Mock mockHttpServletRequest = new Mock(HttpServletRequest.class); 
    Mock mockHttpServletResponse = new OrderedMock(HttpServletResponse.class, "Response with non-default name"); 
                   
    Mock mockServletConfig = new Mock(ServletConfig.class);
    Mock mockServletContext = new Mock(ServletContext.class);
    mockServletConfig.expectAndReturn("getServletContext", (ServletContext)mockServletContext.proxy()); 
    mockServletConfig.expectAndReturn("getServletName", "MelatiConfigTest");
    mockServletContext.expectAndReturn("log","MelatiConfigTest: init", null);
    org.melati.test.PoemServletTest aServlet = 
          new org.melati.test.PoemServletTest();
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
   * Test method for 'org.melati.servlet.PoemServlet.getSysAdminEmail()'
   */
  public void testGetSysAdminEmail() {

  }

  /*
   * Test method for 'org.melati.servlet.PoemServlet.poemContext(Melati)'
   */
  public void testPoemContext() {

  }

  /*
   * Test method for 'org.melati.servlet.PoemServlet.doConfiguredRequest(Melati)'
   */
  public void testDoConfiguredRequest() {

  }

  /*
   * Test method for 'org.melati.servlet.PoemServlet.prePoemSession(Melati)'
   */
  public void testPrePoemSession() {

  }

  /*
   * Test method for 'org.melati.servlet.ConfigServlet.doGet(HttpServletRequest, HttpServletResponse)'
   */
  public void testDoGetHttpServletRequestHttpServletResponse() {
    Mock mockHttpServletRequest = new Mock(HttpServletRequest.class); 
    Mock mockHttpServletResponse = new OrderedMock(HttpServletResponse.class, "Response with non-default name"); 
                   
    mockHttpServletRequest.expectAndReturn( "getCharacterEncoding", "ISO-8859-1"); 
    //poemContext - melati.getPathInfoParts
    mockHttpServletRequest.expectAndReturn( "getPathInfo", "/melatitest/user/1");
    
    mockHttpServletRequest.expectAndReturn( "getPathInfo", "/melatitest/user/1"); 
    // HttpUtil.appendZoneURL
    mockHttpServletRequest.expectAndReturn( "getScheme", "mockScheme"); 
    // HttpUtil.appendRelativeZoneURL
    mockHttpServletRequest.expectAndReturn( "getContextPath", "mockContextPath"); 

    mockHttpServletRequest.expectAndReturn( "getServletPath", "mockServletPath/"); 
    mockHttpServletRequest.expectAndReturn( "getHeader", "Authorization", null); 
    
    mockHttpServletRequest.expectAndReturn( "getServerName", "mockServer.net"); 
    mockHttpServletRequest.expectAndReturn( "getScheme", "mockScheme"); 
    mockHttpServletRequest.expectAndReturn( "getServerName", "mockServer.net"); 
    mockHttpServletRequest.expectAndReturn( "getServletPath", "mockServletPath/"); 
    mockHttpServletRequest.expectAndReturn( "getScheme", "mockScheme"); 
    mockHttpServletRequest.expectAndReturn( "getServerName", "mockServer.net"); 
    mockHttpServletRequest.expectAndReturn( "getContextPath", "mockContextPath"); 
    // melati.establishCharSets
    mockHttpServletRequest.expectAndReturn( "getHeader", "Accept-Charset", "ISO-8859-1"); 
    mockHttpServletRequest.expectAndReturn( "getServletPath", "mockServletPath/"); 
    mockHttpServletRequest.expectAndReturn( "getScheme", "mockScheme"); 
    mockHttpServletRequest.expectAndReturn( "getServerName", "mockServer.net"); 
    mockHttpServletRequest.expectAndReturn( "getContextPath", "mockContextPath"); 
    mockHttpServletRequest.expectAndReturn( "getContextPath", "mockContextPath"); 
    mockHttpServletRequest.expectAndReturn( "getServletPath", "mockServletPath/"); 
    
    final StringWriter output = new StringWriter(); 
    final PrintWriter contentWriter = new PrintWriter(output); 
           
    mockHttpServletResponse.expect( "setContentType", "text/html"); 
    mockHttpServletResponse.expectAndReturn( "getWriter", contentWriter ); 

    Mock mockServletConfig = new Mock(ServletConfig.class);
    Mock mockServletContext = new Mock(ServletContext.class);
    mockServletConfig.expectAndReturn("getInitParameter", "pathInfo", null);
    mockServletConfig.expectAndReturn("getServletContext", (ServletContext)mockServletContext.proxy()); 
    mockServletConfig.expectAndReturn("getServletName", "MelatiConfigTest");
    mockServletContext.expectAndReturn("log", "MelatiConfigTest: init", null);
    org.melati.test.PoemServletTest aServlet = 
          new org.melati.test.PoemServletTest();
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
    assertTrue("Unexpected output (check org.melati.LogicalDatabase properties): " + output.toString(), output.toString().indexOf("<h2>PoemServlet Test</h2>") != -1); 

  }

  /*
   * Test method for 'org.melati.servlet.ConfigServlet.doPost(HttpServletRequest, HttpServletResponse)'
   */
  public void testDoPostHttpServletRequestHttpServletResponse() {
    Mock mockHttpServletRequest = new Mock(HttpServletRequest.class); 
    Mock mockHttpServletResponse = new OrderedMock(HttpServletResponse.class, "Response with non-default name"); 
                   
    mockHttpServletRequest.expectAndReturn( "getCharacterEncoding", "ISO-8859-1"); 
    mockHttpServletRequest.expectAndReturn( "getPathInfo", "/melatitest/user/1"); 
    mockHttpServletRequest.expectAndReturn( "getPathInfo", "/melatitest/user/1"); 
    mockHttpServletRequest.expectAndReturn( "getScheme", "mockScheme"); 
    mockHttpServletRequest.expectAndReturn( "getContextPath", "mockContextPath"); 
    mockHttpServletRequest.expectAndReturn( "getServletPath", "mockServletPath/"); 
    mockHttpServletRequest.expectAndReturn( "getHeader", "Authorization", null); 
    mockHttpServletRequest.expectAndReturn( "getServerName", "mockServer.net"); 
    mockHttpServletRequest.expectAndReturn( "getScheme", "mockScheme"); 
    mockHttpServletRequest.expectAndReturn( "getServerName", "mockServer.net"); 
    mockHttpServletRequest.expectAndReturn( "getServletPath", "mockServletPath/"); 
    mockHttpServletRequest.expectAndReturn( "getScheme", "mockScheme"); 
    mockHttpServletRequest.expectAndReturn( "getServerName", "mockServer.net"); 
    mockHttpServletRequest.expectAndReturn( "getContextPath", "mockContextPath"); 
    mockHttpServletRequest.expectAndReturn( "getHeader", "Accept-Charset", "ISO-8859-1"); 
    mockHttpServletRequest.expectAndReturn( "getServletPath", "mockServletPath/"); 
    mockHttpServletRequest.expectAndReturn( "getScheme", "mockScheme"); 
    mockHttpServletRequest.expectAndReturn( "getServerName", "mockServer.net"); 
    mockHttpServletRequest.expectAndReturn( "getContextPath", "mockContextPath"); 
    mockHttpServletRequest.expectAndReturn( "getContextPath", "mockContextPath"); 
    mockHttpServletRequest.expectAndReturn( "getServletPath", "mockServletPath/"); 
    
    final StringWriter output = new StringWriter(); 
    final PrintWriter contentWriter = new PrintWriter(output); 
           
    mockHttpServletResponse.expect( "setContentType", "text/html"); 
    mockHttpServletResponse.expectAndReturn( "getWriter", contentWriter ); 

    Mock mockServletConfig = new Mock(ServletConfig.class);
    Mock mockServletContext = new Mock(ServletContext.class);
    mockServletConfig.expectAndReturn("getServletContext", (ServletContext)mockServletContext.proxy()); 
    mockServletConfig.expectAndReturn("getInitParameter", "pathInfo", null);
    mockServletConfig.expectAndReturn("getServletName", "MelatiConfigTest");
    mockServletContext.expectAndReturn("log", "MelatiConfigTest: init", null);
    org.melati.test.PoemServletTest aServlet = 
          new org.melati.test.PoemServletTest();
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
    assertTrue("Unexpected output (check org.melati.LogicalDatabase properties): " + output.toString(), output.toString().indexOf("<h2>PoemServlet Test</h2>") != -1); 

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


}
