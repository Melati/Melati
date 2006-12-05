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

import org.melati.Melati;
import org.melati.util.EmptyEnumeration;
import org.melati.util.HttpServletRequestParameters;

import junit.framework.TestCase;

import com.mockobjects.constraint.Constraint;
import com.mockobjects.constraint.IsEqual;
import com.mockobjects.dynamic.Mock;
import com.mockobjects.dynamic.OrderedMock;

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
   * @see org.melati.servlet.PoemServlet#getSysAdminName()
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
    mockServletConfig.expectAndReturn("getServletContext", (ServletContext)mockServletContext.proxy()); 
    mockServletConfig.expectAndReturn("getServletName", "MelatiConfigTest");
    mockServletContext.expectAndReturn("log","MelatiConfigTest: destroy", null);
    aServlet.destroy();
                   
    mockHttpServletRequest.verify(); 
    mockHttpServletResponse.verify(); 
    mockServletConfig.verify(); 
    mockServletContext.verify(); 

  }

  /**
   * @see org.melati.servlet.PoemServlet#getSysAdminEmail()
   */
  public void testGetSysAdminEmail() {
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
      assertEquals("nobody@nobody.com", aServlet.getSysAdminEmail());
    } catch (Exception e) {
      e.printStackTrace();
      fail();
    } 
                   
    mockServletConfig.expectAndReturn("getServletContext", (ServletContext)mockServletContext.proxy()); 
    mockServletConfig.expectAndReturn("getServletName", "MelatiConfigTest");
    mockServletContext.expectAndReturn("log","MelatiConfigTest: destroy", null);
    aServlet.destroy();

    mockHttpServletRequest.verify(); 
    mockHttpServletResponse.verify(); 
    mockServletConfig.verify(); 
    mockServletContext.verify(); 
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
    mockHttpServletRequest.expectAndReturn( "getServerName", "mockServer.net"); 
    mockHttpServletRequest.expectAndReturn( "getServletPath", "mockServletPath/"); 
    mockHttpServletRequest.expectAndReturn( "getScheme", "mockScheme"); 
    mockHttpServletRequest.expectAndReturn( "getServerName", "mockServer.net"); 
    mockHttpServletRequest.expectAndReturn( "getContextPath", "mockContextPath"); 
    // melati.establishCharSets
    mockHttpServletRequest.expectAndReturn( "getHeader", "Accept-Charset", "ISO-8859-1"); 
    mockHttpServletRequest.expectAndReturn( "getServletPath", "mockServletPath/"); 
    mockHttpServletRequest.expectAndReturn( "getScheme", "mockScheme"); 
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
    aServlet.init((ServletConfig)mockServletConfig.proxy());
    aServlet.doGet((HttpServletRequest) mockHttpServletRequest.proxy(),  
                   (HttpServletResponse) mockHttpServletResponse.proxy());
    assertTrue("Unexpected output (check org.melati.LogicalDatabase properties): " + output.toString(), output.toString().indexOf("<h2>PoemServlet Test</h2>") != -1); 

    mockServletConfig.expectAndReturn("getServletContext", (ServletContext)mockServletContext.proxy()); 
    mockServletConfig.expectAndReturn("getServletName", "MelatiConfigTest");
    mockServletContext.expectAndReturn("log","MelatiConfigTest: destroy", null);
    aServlet.destroy();
    mockHttpServletRequest.verify(); 
    mockHttpServletResponse.verify(); 
    mockServletConfig.verify(); 
    mockServletContext.verify(); 
  }

  /**
   * @see org.melati.servlet.ConfigServlet#doPost(HttpServletRequest, HttpServletResponse)
   */
  public void testDoPostHttpServletRequestHttpServletResponse() throws Exception {
    Mock mockHttpServletResponse = new OrderedMock(HttpServletResponse.class, "Response with non-default name"); 
    Mock mockHttpServletRequest = new Mock(HttpServletRequest.class); 
                   
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
    mockServletConfig.expectAndReturn("getServletContext", (ServletContext)mockServletContext.proxy()); 
    mockServletConfig.expectAndReturn("getServletName", "MelatiConfigTest");
    mockServletContext.expectAndReturn("log","MelatiConfigTest: destroy", null);
    mockServletContext.expectAndReturn("log", "MelatiConfigTest: init", null);

    org.melati.test.PoemServletTest aServlet = 
          new org.melati.test.PoemServletTest();
    aServlet.init((ServletConfig)mockServletConfig.proxy());
    aServlet.doPost((HttpServletRequest) mockHttpServletRequest.proxy(),  
                   (HttpServletResponse) mockHttpServletResponse.proxy());
    assertTrue("Unexpected output (check org.melati.LogicalDatabase properties): " + output.toString(), output.toString().indexOf("<h2>PoemServlet Test</h2>") != -1); 
    aServlet.destroy();
    mockHttpServletRequest.verify(); 
    mockHttpServletResponse.verify(); 
    mockServletConfig.verify(); 
    mockServletContext.verify(); 
  }

  /**
   * @throws Exception 
   * @see org.melati.servlet.PoemServlet#error(Melati, Exception)
   */
  public void testError() throws Exception {
    Mock mockHttpServletRequest = new OrderedMock(HttpServletRequest.class); 
    Mock mockHttpServletResponse = new OrderedMock(HttpServletResponse.class, "Response with non-default name"); 
                   
    mockHttpServletRequest.expectAndReturn( "getParameterNames", new EmptyEnumeration()); 
    mockHttpServletRequest.expectAndReturn( "getContextPath", "mockContextPath"); 
    mockHttpServletRequest.expectAndReturn( "getServletPath", "mockServletPath/"); 
    mockHttpServletRequest.expectAndReturn( "getServletPath", "mockServletPath/"); 
    mockHttpServletRequest.expectAndReturn( "getPathInfo", "melatitest/user/1"); 
    mockHttpServletRequest.expectAndReturn( "getPathInfo", "melatitest/user/1"); 
    mockHttpServletRequest.expectAndReturn( "getQueryString", null); 
    mockHttpServletRequest.expectAndReturn( "getMethod", null); 
    
    Mock mockSession = new Mock(HttpSession.class);
    mockSession.expectAndReturn("getId", "1");
    mockSession.expectAndReturn("getId", "1");

    mockHttpServletRequest.expectAndReturn("getSession", Boolean.TRUE, mockSession.proxy());
    mockHttpServletRequest.expectAndReturn("getHeader", "Accept-Charset", "ISO-8859-1"); 
    mockHttpServletRequest.expectAndReturn("getCharacterEncoding", "ISO-8859-1"); 
    mockHttpServletRequest.expectAndReturn("getSession", Boolean.TRUE, mockSession.proxy());
    mockHttpServletRequest.expectAndReturn("getSession", Boolean.FALSE, mockSession.proxy());
    mockHttpServletRequest.expectAndReturn("getContextPath", "mockContextPath"); 
    mockHttpServletRequest.expectAndReturn("getServletPath", "mockServletPath/"); 
    mockHttpServletRequest.expectAndReturn("getServletPath", "mockServletPath/"); 
    mockHttpServletRequest.expectAndReturn("getPathInfo", "melatitest/user/1"); 
    mockHttpServletRequest.expectAndReturn("getPathInfo", "melatitest/user/1"); 
    mockHttpServletRequest.expectAndReturn("getQueryString", null); 
    mockHttpServletRequest.expectAndReturn("getSession", Boolean.TRUE, mockSession.proxy());
    mockHttpServletRequest.expectAndReturn("getCookies", null);
    mockHttpServletRequest.expectAndReturn("getSession", Boolean.TRUE, mockSession.proxy());
    mockHttpServletRequest.expectAndReturn("getContextPath", "mockContextPath"); 
    mockHttpServletRequest.expectAndReturn("getServletPath", "mockServletPath/"); 
    mockHttpServletRequest.expectAndReturn("getServletPath", "mockServletPath/"); 
    mockHttpServletRequest.expectAndReturn("getPathInfo", "melatitest/user/1");
    mockHttpServletRequest.expectAndReturn("getPathInfo", "melatitest/user/1");
    mockHttpServletRequest.expectAndReturn("getQueryString", null); 
    mockHttpServletRequest.expectAndReturn("getSession", Boolean.TRUE, mockSession.proxy());
    mockHttpServletRequest.expectAndReturn("getContextPath", "mockContextPath");     
    mockHttpServletRequest.expectAndReturn("getServletPath", "mockServletPath/"); 

    mockSession.expect("removeAttribute", "org.melati.login.HttpSessionAccessHandler.overlayParameters"); 
    mockSession.expectAndReturn("getId", "1");
    mockSession.expectAndReturn("getId", "1");
    mockSession.expectAndReturn("getAttribute", "org.melati.login.HttpSessionAccessHandler.user", null); 
    mockSession.expectAndReturn("getId", "1");
    
    mockSession.expect("setAttribute", new Constraint []  {new IsEqual("org.melati.login.Login.triggeringRequestParameters"),
        new IsInstanceOf(HttpServletRequestParameters.class)});

    mockSession.expect("setAttribute", new Constraint []  {new IsEqual("org.melati.login.Login.triggeringException"),
        new IsInstanceOf(org.melati.poem.AccessPoemException.class)});
    

    
    mockSession.expectAndReturn("getId", "1");
    
    final StringWriter output = new StringWriter(); 
    final PrintWriter contentWriter = new PrintWriter(output); 
           
    mockHttpServletResponse.expectAndReturn( "getWriter", contentWriter ); 
    mockHttpServletResponse.expect("sendRedirect",new IsInstanceOf(String.class));


    Mock mockServletConfig = new Mock(ServletConfig.class);
    Mock mockServletContext = new Mock(ServletContext.class);
    mockServletConfig.expectAndReturn("getServletContext", (ServletContext)mockServletContext.proxy()); 
    mockServletConfig.expectAndReturn("getInitParameter", "pathInfo", "melatitest/user/1");
    mockServletConfig.expectAndReturn("getServletName", "MelatiConfigTest");
    mockServletContext.expectAndReturn("log","MelatiConfigTest: init", null);

    mockHttpServletRequest.expectAndReturn("getSession", Boolean.TRUE, mockSession.proxy());

    
    mockHttpServletRequest.expectAndReturn("getSession", Boolean.FALSE, mockSession.proxy());
    mockSession.expectAndReturn("getId", "1");
    mockSession.expectAndReturn("getId", "1");

    mockSession.expectAndReturn("getAttribute", "org.melati.login.HttpSessionAccessHandler.overlayParameters",
        new HttpServletRequestParameters((HttpServletRequest)mockHttpServletRequest.proxy()));
        

    mockServletConfig.expectAndReturn("getServletContext", (ServletContext)mockServletContext.proxy()); 
    mockServletConfig.expectAndReturn("getServletName", "MelatiConfigTest");
    mockServletContext.expectAndReturn("log","MelatiConfigTest: destroy", null);
    
    ExceptionPoemServlet aServlet = 
          new ExceptionPoemServlet();
    aServlet.init((ServletConfig)mockServletConfig.proxy());
    aServlet.doPost((HttpServletRequest) mockHttpServletRequest.proxy(),  
                     (HttpServletResponse) mockHttpServletResponse.proxy());
    aServlet.destroy();
                   

   // mockHttpServletRequest.verify(); 
      mockHttpServletResponse.verify(); 
   // mockServletConfig.verify(); 
    //mockServletContext.verify(); 
    //assertTrue(output.toString().indexOf("You need the capability") != -1); 
    System.err.println(output);

  }

  public void testLDB() throws Exception {
    MockServletResponse response = new MockServletResponse();
    MockServletRequest request = new MockServletRequest();
    Mock mockServletConfig = new Mock(ServletConfig.class);
    Mock mockServletContext = new Mock(ServletContext.class);
   // mockServletConfig.expectAndReturn("getServletContext", (ServletContext)mockServletContext.proxy()); 
    mockServletConfig.expectAndReturn("getInitParameter", "pathInfo", null);
   // mockServletConfig.expectAndReturn("getServletName", "MelatiConfigTest");
    mockServletConfig.expectAndReturn("getServletContext", (ServletContext)mockServletContext.proxy()); 
    mockServletConfig.expectAndReturn("getServletName", "MelatiConfigTest");
    mockServletConfig.expectAndReturn("getServletContext", (ServletContext)mockServletContext.proxy()); 
    mockServletConfig.expectAndReturn("getServletName", "MelatiConfigTest");
    mockServletContext.expectAndReturn("log", "MelatiConfigTest: init", null);
    mockServletContext.expectAndReturn("log","MelatiConfigTest: destroy", null);
    LDBPoemServlet aServlet = 
      new LDBPoemServlet();
    aServlet.init((ServletConfig)mockServletConfig.proxy());
    aServlet.doPost((HttpServletRequest) request,  
                    (HttpServletResponse) response);
    System.out.println(response.getWritten().toString());
    aServlet.destroy();

    assertTrue(response.getWritten().toString().indexOf("logicalDatabase = poemtest") != -1);
    mockServletConfig.verify(); 
    mockServletContext.verify(); 
    
  }

  /** Tests whether the value is an instance of a class.
   */
  public class IsInstanceOf implements Constraint
  {
      private Class _class;
      
      /** Creates a new instance of IsInstanceOf
       *  
       *  @param theclass
       *      The predicate evaluates to true for instances of this class
       *      or one of its subclasses.
       */
      public IsInstanceOf( Class theclass ) {
          _class = theclass;
      }
      
      public boolean eval( Object arg ) {
          System.err.println(arg);
          return _class.isInstance( arg );
      }
      
      public String toString() {
          return "an instance of <" + _class.getName() + ">";
      }
  }

}
