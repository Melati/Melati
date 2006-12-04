/**
 * 
 */
package org.melati.servlet.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.melati.util.MelatiStringWriter;

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
    final MelatiStringWriter output = new MelatiStringWriter(); 
    //final PrintWriter contentWriter = new PrintWriter(output); 
           
    //Mock mockHttpServletRequest = new OrderedMock(HttpServletRequest.class);
    MockServletRequest mockHttpServletRequest = new MockServletRequest();
    MockServletResponse mockHttpServletResponse = new MockServletResponse(); 
    Mock mockSession = new Mock(HttpSession.class);

    mockSession.expectAndReturn("getId", "1");
    mockSession.expectAndReturn("getAttribute", "org.melati.login.HttpSessionAccessHandler.overlayParameters", 
       null); 
    mockSession.expectAndReturn("getAttribute", "org.melati.login.HttpSessionAccessHandler.user", null);
    mockHttpServletRequest.setSession(mockSession.proxy());
    /*
    
    mockHttpServletRequest.expectAndReturn("getHeader", "Accept-Charset", "ISO-8859-1"); 
    mockHttpServletRequest.expectAndReturn("getCharacterEncoding", "ISO-8859-1"); 
    mockHttpServletRequest.expectAndReturn("getPathInfo", "/melatitest/user/1"); 
    mockHttpServletRequest.expectAndReturn("getPathInfo", "/melatitest/user/1"); 
    mockHttpServletRequest.expectAndReturn("getSession", Boolean.TRUE, mockSession.proxy());
    mockHttpServletRequest.expectAndReturn("getHeader", "content-type", "text/html"); 

    mockHttpServletRequest.expectAndReturn("getSession", Boolean.TRUE, mockSession.proxy());
    mockHttpServletRequest.expectAndReturn("getCookies", null);
    mockHttpServletRequest.expectAndReturn("getHeader", "Accept-Language", "en-gb"); 
    
    mockHttpServletRequest.expectAndReturn("getParameterNames", new EmptyEnumeration()); 
    mockHttpServletRequest.expectAndReturn("getContextPath", "mockContextPath"); 
    mockHttpServletRequest.expectAndReturn("getServletPath", "mockServletPath/"); 
    mockHttpServletRequest.expectAndReturn("getServletPath", "mockServletPath/"); 
    mockHttpServletRequest.expectAndReturn("getPathInfo", "/melatitest/user/1"); 
    mockHttpServletRequest.expectAndReturn("getPathInfo", "/melatitest/user/1"); 
    mockHttpServletRequest.expectAndReturn("getQueryString", null); 
    mockHttpServletRequest.expectAndReturn("getMethod", null); 
    mockHttpServletRequest.expectAndReturn("getSession", Boolean.TRUE, mockSession.proxy());
    mockHttpServletRequest.expectAndReturn("getHeader", "Accept-Charset", "ISO-8859-1"); 

    mockHttpServletRequest.expectAndReturn("getCharacterEncoding", "ISO-8859-1"); 
    mockHttpServletRequest.expectAndReturn("getPathInfo", "/melatitest/user/1"); 
    mockHttpServletRequest.expectAndReturn("getPathInfo", "/melatitest/user/1"); 
    mockHttpServletRequest.expectAndReturn("getSession", Boolean.TRUE, mockSession.proxy());
    mockHttpServletResponse.expectAndReturn( "getOutputStream", output ); 
    mockHttpServletResponse.expectAndReturn( "getOutputStream", output ); 
    mockHttpServletResponse.expectAndReturn( "setContentType", "text/html; charset=ISO-8859-1", null); 
    mockHttpServletResponse.expectAndReturn( "getOutputStream", output ); 
    mockHttpServletResponse.expectAndReturn( "getOutputStream", output ); 
    mockHttpServletResponse.expectAndReturn( "setContentType", "text/html; charset=ISO-8859-1", null); 
    mockHttpServletResponse.expectAndReturn( "getOutputStream", output ); 
*/

    Mock mockServletConfig = new Mock(ServletConfig.class);
    Mock mockServletContext = new Mock(ServletContext.class);
    mockServletConfig.expectAndReturn("getServletContext", (ServletContext)mockServletContext.proxy()); 
    mockServletConfig.expectAndReturn("getServletContext", (ServletContext)mockServletContext.proxy()); 
    mockServletContext.expectAndReturn("getMajorVersion", 2); 
    mockServletContext.expectAndReturn("getMinorVersion", 3); 
    mockServletConfig.expectAndReturn("getServletContext", (ServletContext)mockServletContext.proxy()); 
    mockServletConfig.expectAndReturn("getServletName", "MelatiConfigTest");
    mockServletConfig.expectAndReturn("getInitParameter","pathInfo", null); 
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
    mockServletContext.expectAndReturn("getResource", "/org/melati/test/TemplateServletTest.wm", null); 
    mockServletContext.expect("log", "MelatiConfigTest: destroy");


    
        
    
    org.melati.test.TemplateServletTest aServlet = 
          new org.melati.test.TemplateServletTest();
    try {
      aServlet.init((ServletConfig)mockServletConfig.proxy());
      aServlet.doPost((HttpServletRequest) mockHttpServletRequest,  
                     (HttpServletResponse) mockHttpServletResponse);
      aServlet.destroy();
      
    } catch (Exception e) {
      e.printStackTrace();
      fail();
    } 
                   
   // mockHttpServletRequest.verify(); 
    //mockHttpServletResponse.verify(); 
    mockServletConfig.verify(); 
    mockServletContext.verify(); 
    try {
      System.err.println(mockHttpServletResponse.getOutputStream().toString());
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    //assertTrue(output.toString().indexOf("<h2>TemplateServlet Test</h2>") != -1); 


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
  
  
  private class MockServletResponse implements HttpServletResponse {

    public void addCookie(Cookie arg0) {
    }

    public boolean containsHeader(String arg0) {
        return false;
    }

    public String encodeURL(String arg0) {
        return null;
    }

    public String encodeRedirectURL(String arg0) {
        return null;
    }

    public String encodeUrl(String arg0) {
        return null;
    }

    public String encodeRedirectUrl(String arg0) {
        return null;
    }

    public void sendError(int arg0, String arg1) throws IOException {
    }

    public void sendError(int arg0) throws IOException {
    }

    public void sendRedirect(String arg0) throws IOException {
    }

    public void setDateHeader(String arg0, long arg1) {
    }

    public void addDateHeader(String arg0, long arg1) {
    }

    public void setHeader(String arg0, String arg1) {
    }

    public void addHeader(String arg0, String arg1) {
    }

    public void setIntHeader(String arg0, int arg1) {
    }

    public void addIntHeader(String arg0, int arg1) {
    }

    public void setStatus(int arg0) {
    }

    public void setStatus(int arg0, String arg1) {
    }

    public String getCharacterEncoding() {
      return "ISO-8859-1";
    }

    public ServletOutputStream getOutputStream() throws IOException {
        return new ServletOutputStream() {
        
            public void println(String arg0) throws IOException {
                super.println(arg0);
            }

            public void write(int b) throws IOException {
            }
        };
    }

    public PrintWriter getWriter() throws IOException {
        return null;
    }

    public void setContentLength(int arg0) {
    }

    public void setContentType(String arg0) {
    }

    public void setBufferSize(int arg0) {
    }

    public int getBufferSize() {
        return 0;
    }

    public void flushBuffer() throws IOException {
    }

    public void resetBuffer() {
    }

    public boolean isCommitted() {
        return false;
    }

    public void reset() {
    }

    public void setLocale(Locale arg0) {
    }

    public Locale getLocale() {
        return null;
    }
    
}

  private class MockServletRequest implements HttpServletRequest {

    Map parameters = new HashMap();
    
    public void setParameters(Map map) {
        parameters = map;
    }
    
    public String getAuthType() {
        return null;
    }

    public Cookie[] getCookies() {
        return null;
    }

    public long getDateHeader(String arg0) {
        return 0;
    }

    public String getHeader(String arg0) {
        return null;
    }

    public Enumeration getHeaders(String arg0) {
        return null;
    }

    public Enumeration getHeaderNames() {
        return null;
    }

    public int getIntHeader(String arg0) {
        return 0;
    }

    public String getMethod() {
        return null;
    }

    public String getPathInfo() {
        return null;
    }

    public String getPathTranslated() {
        return null;
    }

    public String getContextPath() {
        return null;
    }

    public String getQueryString() {
        return null;
    }

    public String getRemoteUser() {
        return null;
    }

    public boolean isUserInRole(String arg0) {
        return false;
    }

    public Principal getUserPrincipal() {
        return null;
    }

    public String getRequestedSessionId() {
        return null;
    }

    public String getRequestURI() {
        return null;
    }

    public StringBuffer getRequestURL() {
        return null;
    }

    public String getServletPath() {
        return null;
    }
    Object session;
    public void setSession(Object s){
      session = s;
    }
    public HttpSession getSession(boolean arg0) {
      return (HttpSession)session;
    }

    public HttpSession getSession() {
      return (HttpSession)session;
    }

    public boolean isRequestedSessionIdValid() {
        return false;
    }

    public boolean isRequestedSessionIdFromCookie() {
        return false;
    }

    public boolean isRequestedSessionIdFromURL() {
        return false;
    }

    public boolean isRequestedSessionIdFromUrl() {
        return false;
    }

    public Object getAttribute(String arg0) {
        return null;
    }

    public Enumeration getAttributeNames() {
        return null;
    }

    public String getCharacterEncoding() {
      return "ISO-8859-1";
    }

    public void setCharacterEncoding(String arg0) throws UnsupportedEncodingException {
    }

    public int getContentLength() {
        return 0;
    }

    public String getContentType() {
        return null;
    }

    public ServletInputStream getInputStream() throws IOException {
        return null;
    }

    public String getParameter(String arg0) {
        return (String) parameters.get(arg0);
    }

    public Enumeration getParameterNames() {
        return null;
    }

    public String[] getParameterValues(String arg0) {
        return null;
    }

    public Map getParameterMap() {
        return null;
    }

    public String getProtocol() {
        return null;
    }

    String scheme = "http";
    public void setScheme(String s) {
      scheme = s;
    }
    public String getScheme() {
        return scheme;
    }

    public String getServerName() {
        return null;
    }

    public int getServerPort() {
        return 0;
    }

    public BufferedReader getReader() throws IOException {
        return null;
    }

    public String getRemoteAddr() {
        return null;
    }

    public String getRemoteHost() {
        return null;
    }

    public void setAttribute(String arg0, Object arg1) {
    }

    public void removeAttribute(String arg0) {
    }

    public Locale getLocale() {
        return null;
    }

    public Enumeration getLocales() {
        return null;
    }

    public boolean isSecure() {
        return false;
    }

    public RequestDispatcher getRequestDispatcher(String arg0) {
        return new RequestDispatcher() {
        
            public void include(ServletRequest arg0, ServletResponse arg1)
                    throws ServletException, IOException {
            }
        
            public void forward(ServletRequest arg0, ServletResponse arg1)
                    throws ServletException, IOException {
            }
        };
    }

    public String getRealPath(String arg0) {
        return null;
    }
    
}

}
