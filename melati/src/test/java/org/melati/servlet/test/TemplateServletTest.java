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

import org.melati.util.EmptyEnumeration;
import org.melati.util.HttpServletRequestParameters;

//import org.melati.util.HttpServletRequestParameters;

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

  /**
   * @see PoemServletTest#setUp()
   */
  protected void setUp()
      throws Exception {
    super.setUp();
  }

  /**
   * @see PoemServletTest#tearDown()
   */
  protected void tearDown()
      throws Exception {
    super.tearDown();
  }

  /**
   * @see org.melati.servlet.TemplateServlet.error(Melati, Exception)
   */
  public void testError() {
    super.testError();
  }

  /**
   * @see org.melati.servlet.PoemServlet.getSysAdminName()
   */
  public void testGetSysAdminName() {
    super.testGetSysAdminName();
  }

  /**
   * @see org.melati.servlet.PoemServlet.getSysAdminEmail()
   */
  public void testGetSysAdminEmail() {
    super.testGetSysAdminEmail();
  }

  /**
   * @see org.melati.servlet.ConfigServlet.doGet(HttpServletRequest, HttpServletResponse)
   */
  public void testDoGetHttpServletRequestHttpServletResponse() {
    doGetPost(); 
  }
  /**
   * @see org.melati.servlet.ConfigServlet.doPost(HttpServletRequest, HttpServletResponse)
   */
  public void testDoPostHttpServletRequestHttpServletResponse() {
    //doGetPost(); 

  }
  
  /**
   * 
   */
  public void doGetPost() {
    Mock mockHttpServletRequest = new OrderedMock(HttpServletRequest.class); 
    Mock mockHttpServletResponse = new OrderedMock(HttpServletResponse.class, "Response with non-default name"); 
                   
    mockHttpServletRequest.expectAndReturn( "getParameterNames", new EmptyEnumeration()); 
    mockHttpServletRequest.expectAndReturn( "getContextPath", "mockContextPath"); 
    mockHttpServletRequest.expectAndReturn( "getServletPath", "mockServletPath/"); 
    mockHttpServletRequest.expectAndReturn( "getServletPath", "mockServletPath/"); 
    mockHttpServletRequest.expectAndReturn( "getPathInfo", "/melatitest/user/1"); 
    mockHttpServletRequest.expectAndReturn( "getPathInfo", "/melatitest/user/1"); 
    mockHttpServletRequest.expectAndReturn( "getQueryString", null); 
    mockHttpServletRequest.expectAndReturn( "getMethod", null); 

    Mock mockSession = new Mock(HttpSession.class);
    mockSession.expectAndReturn("getId", "1");
    mockSession.expectAndReturn("getId", "1");

    mockHttpServletRequest.expectAndReturn("getSession", Boolean.TRUE, mockSession.proxy());

    
    mockHttpServletRequest.expectAndReturn( "getHeader", "Accept-Charset", "ISO-8859-1"); 

    mockHttpServletRequest.expectAndReturn( "getCharacterEncoding", "ISO-8859-1"); 
    mockHttpServletRequest.expectAndReturn("getPathInfo", "melatitest/user/1"); 
    
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

    mockHttpServletRequest.expectAndReturn("getSession", Boolean.TRUE, mockSession.proxy());

    
    mockSession.expectAndReturn("getAttribute", "org.melati.login.HttpSessionAccessHandler.overlayParameters", 
                                new HttpServletRequestParameters((HttpServletRequest)mockHttpServletRequest.proxy())); 
        
    
    org.melati.test.TemplateServletTest aServlet = 
          new org.melati.test.TemplateServletTest();
    try {
      aServlet.init((ServletConfig)mockServletConfig.proxy());
      aServlet.doPost((HttpServletRequest) mockHttpServletRequest.proxy(),  
                     (HttpServletResponse) mockHttpServletResponse.proxy());
      aServlet.destroy();
      
    } catch (Exception e) {
      e.printStackTrace();
      fail();
    } 
                   
    mockHttpServletRequest.verify(); 
    mockHttpServletResponse.verify(); 
    mockServletConfig.verify(); 
    mockServletContext.verify(); 
    assertTrue(output.toString().indexOf("<h2>TemplateServlet Test</h2>") != -1); 


  }


  /**
   * @see org.melati.servlet.ConfigServlet.writeError(PrintWriter, Exception)
   */
  public void testWriteError() {

  }

  /**
   * @see org.melati.servlet.ConfigServlet.writeConnectionPendingException(PrintWriter, Exception)
   */
  public void testWriteConnectionPendingException() {

  }

}
