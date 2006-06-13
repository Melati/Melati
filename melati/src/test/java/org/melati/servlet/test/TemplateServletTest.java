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
import javax.servlet.http.HttpSession;

import org.melati.util.HttpServletRequestParameters;

import com.mockobjects.dynamic.Mock;
import com.mockobjects.dynamic.OrderedMock;

/**
 * @author timp
 *
 */
public class TemplateServletTest extends PoemServletTest {

  /**
   * Constructor for TemplateServletTest.
   * @param name
   */
  public TemplateServletTest(String name) {
    super(name);
  }

  /*
   * @see PoemServletTest#setUp()
   */
  protected void setUp()
      throws Exception {
    super.setUp();
  }

  /*
   * @see PoemServletTest#tearDown()
   */
  protected void tearDown()
      throws Exception {
    super.tearDown();
  }

  /*
   * Test method for 'org.melati.servlet.TemplateServlet.error(Melati, Exception)'
   */
  public void testError() {

  }

  /*
   * Test method for 'org.melati.servlet.PoemServlet.getSysAdminName()'
   */
  public void testGetSysAdminName() {

  }

  /*
   * Test method for 'org.melati.servlet.PoemServlet.getSysAdminEmail()'
   */
  public void testGetSysAdminEmail() {

  }

  /*
   * Test method for 'org.melati.servlet.ConfigServlet.doGet(HttpServletRequest, HttpServletResponse)'
   */
  public void testDoGetHttpServletRequestHttpServletResponse() {
    Mock mockHttpServletRequest = new Mock(HttpServletRequest.class); 
    Mock mockHttpServletResponse = new OrderedMock(HttpServletResponse.class, "Response with non-default name"); 
                   
    mockHttpServletRequest.expectAndReturn( "getCharacterEncoding", "ISO-8859-1"); 
    mockHttpServletRequest.expectAndReturn( "getPathInfo", "/melatitest/user/1"); 
    mockHttpServletRequest.expectAndReturn( "getPathInfo", "/melatitest/user/1"); 
    mockHttpServletRequest.expectAndReturn( "getHeader", "Accept-Charset", "ISO-8859-1"); 
    
    final StringWriter output = new StringWriter(); 
    final PrintWriter contentWriter = new PrintWriter(output); 
           
    mockHttpServletResponse.expect( "setContentType", "text/html"); 
    mockHttpServletResponse.expectAndReturn( "getWriter", contentWriter ); 

    Mock mockServletConfig = new Mock(ServletConfig.class);
    Mock mockServletContext = new Mock(ServletContext.class);
    mockServletConfig.expectAndReturn("getServletContext", (ServletContext)mockServletContext.proxy()); 
    mockServletConfig.expectAndReturn("getServletContext", (ServletContext)mockServletContext.proxy()); 
    mockServletContext.expectAndReturn("getMajorVersion", 2); 
    mockServletContext.expectAndReturn("getMinorVersion", 3); 
    mockServletConfig.expectAndReturn("getServletContext", (ServletContext)mockServletContext.proxy()); 
    mockServletConfig.expectAndReturn("getServletName", "MelatiConfigTest");
    mockServletContext.expectAndReturn("hashCode", 17); 
    mockServletContext.expectAndReturn("toString", "mockServletContext"); 
    mockServletContext.expectAndReturn("log", "MelatiConfigTest: init", null);
    mockServletContext.expectAndReturn("getResource", "/WEB-INF/WebMacro.properties", null); 
    mockServletContext.expectAndReturn("getResource", "/WebMacro.properties", null); 
    mockServletContext.expectAndReturn("getInitParameterNames", null); 
    mockServletContext.expectAndReturn("hashCode", 17); 
    mockServletContext.expectAndReturn("toString", "mockServletContext"); 
    mockServletContext.expectAndReturn("log", "WebMacro:LogFile\tNOTICE\t--- Log Started ---", null); 
    mockServletContext.expectAndReturn("hashCode", 17); 
    mockServletContext.expectAndReturn("log", "WebMacro:broker\tNOTICE\tLoaded settings from WebMacro.defaults, WebMacro.properties, (WAR file), (System Properties)", null);
    mockServletContext.expectAndReturn("hashCode", 17); 
    mockServletContext.expectAndReturn("log", "WebMacro:wm\tNOTICE\tnew WebMacro(mockServletContext) v2.0b1", null);

    Mock mockSession = new Mock(HttpSession.class);
    mockHttpServletRequest.expectAndReturn("getSession", Boolean.TRUE, mockSession.proxy());

    
    //mockHttpServletRequest.expectAndReturn( "getParameterNames", null); 
    //mockSession.expectAndReturn("getAttribute", "org.melati.login.HttpSessionAccessHandler.overlayParameters", 
    //                            new HttpServletRequestParameters((HttpServletRequest)mockHttpServletRequest.proxy())); 
        
    
    org.melati.test.TemplateServletTest aServlet = 
          new org.melati.test.TemplateServletTest();
    try {
      aServlet.init((ServletConfig)mockServletConfig.proxy());
     // aServlet.doPost((HttpServletRequest) mockHttpServletRequest.proxy(),  
     //                (HttpServletResponse) mockHttpServletResponse.proxy());
      
    } catch (Exception e) {
      e.printStackTrace();
      fail();
    } 
                   
    //mockHttpServletRequest.verify(); 
    //mockHttpServletResponse.verify(); 
    mockServletConfig.verify(); 
    mockServletContext.verify(); 
    //assertTrue(output.toString().indexOf("<h2>TemplateServlet Test</h2>") != -1); 


  }

  /*
   * Test method for 'org.melati.servlet.ConfigServlet.doPost(HttpServletRequest, HttpServletResponse)'
   */
  public void testDoPostHttpServletRequestHttpServletResponse() {

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
