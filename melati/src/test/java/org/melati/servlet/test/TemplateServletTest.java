/**
 * 
 */
package org.melati.servlet.test;

import java.net.URL;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.melati.util.HttpServletRequestParameters;

import com.mockobjects.constraint.Constraint;
import com.mockobjects.constraint.IsEqual;
import com.mockobjects.dynamic.Mock;

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
   * @throws ServletException 
   * @see org.melati.servlet.PoemServlet#getSysAdminName()
   */
  public void testGetSysAdminName() throws ServletException {
    super.testGetSysAdminName();
  }

  /**
   * @throws ServletException 
   * @see org.melati.servlet.PoemServlet#getSysAdminEmail()
   */
  public void testGetSysAdminEmail() throws ServletException {
    super.testGetSysAdminEmail();
  }

  /**
   * @see org.melati.servlet.ConfigServlet#doGet(HttpServletRequest, HttpServletResponse)
   */
  public void testDoGetHttpServletRequestHttpServletResponse() throws Exception {
    doGetPost(); 
  }
  /**
   * @see org.melati.servlet.ConfigServlet#doPost(HttpServletRequest, HttpServletResponse)
   */
  public void testDoPostHttpServletRequestHttpServletResponse() {
    //doGetPost(); 

  }
  
  /**
   * 
   */
  public void doGetPost() throws Exception {
    MockServletRequest mockHttpServletRequest = new MockServletRequest();
    MockServletResponse mockHttpServletResponse = new MockServletResponse(); 
    Mock mockSession = new Mock(HttpSession.class);
    Mock mockServletConfig = new Mock(ServletConfig.class);
    MockServletContext mockServletContext = new MockServletContext();

    setupMocks(mockHttpServletRequest, mockHttpServletResponse, mockSession, 
            mockServletConfig, mockServletContext);
    
    
    org.melati.test.TemplateServletTest aServlet = 
          new org.melati.test.TemplateServletTest();
    aServlet.init((ServletConfig)mockServletConfig.proxy());
    aServlet.doPost(mockHttpServletRequest,  
                    mockHttpServletResponse);
    aServlet.destroy();
      
    mockServletConfig.verify(); 
    //mockServletContext.verify(); 
    System.err.println(mockHttpServletResponse.getOutputStream().toString());


  }


  private void setupMocks(MockServletRequest mockHttpServletRequest, 
          MockServletResponse mockHttpServletResponse,
          Mock mockSession,
          Mock mockServletConfig, 
          MockServletContext mockServletContext) {

    mockSession.expectAndReturn("getId", "1");
    mockSession.expectAndReturn("getAttribute", "org.melati.login.HttpSessionAccessHandler.overlayParameters", 
       null); 
    mockSession.expectAndReturn("getAttribute", "org.melati.login.HttpSessionAccessHandler.user", null);
    mockHttpServletRequest.setSession(mockSession.proxy());

    mockServletConfig.expectAndReturn("getServletContext", mockServletContext); 
    mockServletConfig.expectAndReturn("getServletContext", mockServletContext); 
    mockServletContext.expectAndReturn("getMajorVersion", "2"); 
    mockServletContext.expectAndReturn("getMinorVersion", "3"); 
    mockServletConfig.expectAndReturn("getServletContext", mockServletContext); 
    mockServletConfig.expectAndReturn("getServletName", "MelatiConfigTest");
    mockServletConfig.expectAndReturn("getInitParameter","pathInfo", null); 
    mockServletConfig.expectAndReturn("getServletContext", mockServletContext); 
    mockServletConfig.expectAndReturn("getServletName", "MelatiConfigTest");

    mockServletContext.expectAndReturn("hashCode", "17"); 
    mockServletContext.expectAndReturn("toString", "mockServletContext"); 
    mockServletContext.expectAndReturn("log", "MelatiConfigTest: init");
    mockServletContext.expectAndReturn("getResource", "/WEB-INF/WebMacro.properties"); 
    mockServletContext.expectAndReturn("getResource", "/WebMacro.properties"); 
    mockServletContext.expectAndReturn("getInitParameterNames", ""); 
    mockServletContext.expectAndReturn("hashCode", "17"); 
    mockServletContext.expectAndReturn("toString", "mockServletContext"); 
    mockServletContext.expectAndReturn("log", "WebMacro:LogFile\tNOTICE\t--- Log Started ---"); 
    mockServletContext.expectAndReturn("hashCode", "17"); 
    mockServletContext.expectAndReturn("log", "WebMacro:broker\tNOTICE\tLoaded settings from WebMacro.defaults, WebMacro.properties, (WAR file), (System Properties)");
    mockServletContext.expectAndReturn("hashCode", "17"); 
    mockServletContext.expectAndReturn("log", "WebMacro:wm\tNOTICE\tnew WebMacro(mockServletContext) v2.0b1");
    mockServletContext.expectAndReturn("getResource", "/org/melati/test/TemplateServletTest.wm"); 
    mockServletContext.expectAndReturn("log", "MelatiConfigTest: destroy");
  }

  /**
   * @see org.melati.servlet.TemplateServlet#error(org.melati.Melati, Exception)
   */
  public void testError() throws Exception {
    MockServletRequest mockHttpServletRequest = new MockServletRequest();
    MockServletResponse mockHttpServletResponse = new MockServletResponse(); 
                   
    
    Mock mockSession = new Mock(HttpSession.class);
    mockSession.expectAndReturn("getId", "1");
    mockSession.expectAndReturn("getId", "1");


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
    
           
    Mock mockServletConfig = new Mock(ServletConfig.class);
    Mock mockServletContext = new Mock(ServletContext.class);
    mockServletConfig.expectAndReturn("getServletContext", mockServletContext.proxy()); 
    mockServletConfig.expectAndReturn("getServletContext", mockServletContext.proxy()); 
    mockServletConfig.expectAndReturn("getInitParameter", "pathInfo", "melatitest/user/1");
    mockServletConfig.expectAndReturn("getServletName", "MelatiConfigTest");
    mockServletContext.expectAndReturn("log","MelatiConfigTest: init", null);
    mockServletContext.expectAndReturn("getMajorVersion", 2); 
    mockServletContext.expectAndReturn("getMinorVersion", 3); 
    mockServletContext.expectAndReturn("hashCode", 17); 
    mockServletContext.expectAndReturn("toString", "mockServletContext"); 
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
    mockServletContext.expectAndReturn("getResource", "/java/lang/Exception.wm", null); 
    mockServletContext.expectAndReturn("log", new IsInstanceOf(String.class), null);
//        "WebMacro:resource\tWARNING\tBrokerTemplateProvider:\tTemplate not found:\tjava/lang/Exception.wm", null);
    mockServletContext.expectAndReturn("getResource", 
        "/org/melati/template/webmacro/templets/html/error/java.lang.Exception.wm", 
        null); 

    mockHttpServletRequest.setSession(mockSession.proxy());
    mockSession.expectAndReturn("getId", "1");
    mockSession.expectAndReturn("getId", "1");

    mockSession.expectAndReturn("getAttribute", "org.melati.login.HttpSessionAccessHandler.overlayParameters",
        new HttpServletRequestParameters(mockHttpServletRequest));
        

    mockServletConfig.expectAndReturn("getServletContext", mockServletContext.proxy()); 
    mockServletConfig.expectAndReturn("getServletName", "MelatiConfigTest");
    mockServletContext.expectAndReturn("log","MelatiConfigTest: destroy", null);
    mockServletConfig.expectAndReturn("getServletContext", mockServletContext.proxy()); 
    
    ExceptionTemplateServlet aServlet = 
          new ExceptionTemplateServlet();
    aServlet.init((ServletConfig)mockServletConfig.proxy());
    aServlet.doPost( mockHttpServletRequest,  
                     mockHttpServletResponse);
    assertTrue(mockHttpServletResponse.getWritten().indexOf("Melati Error Template") > 0);
    assertTrue(mockHttpServletResponse.getWritten().indexOf("java.lang.Exception: A problem") > 0);
    aServlet.destroy();
  }
  

  /**
   * Test that a templet on the classpath is used.
   * @see org.melati.servlet.TemplateServlet#error(org.melati.Melati, Exception)
   */
  public void testErrorUsesClasspathTemplet() throws Exception {
    MockServletRequest mockHttpServletRequest = new MockServletRequest();
    MockServletResponse mockHttpServletResponse = new MockServletResponse(); 
                   
    
    Mock mockSession = new Mock(HttpSession.class);
    mockSession.expectAndReturn("getId", "1");
    mockSession.expectAndReturn("getId", "1");


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
    
           
    Mock mockServletConfig = new Mock(ServletConfig.class);
    Mock mockServletContext = new Mock(ServletContext.class);
    mockServletConfig.expectAndReturn("getServletContext", mockServletContext.proxy()); 
    mockServletConfig.expectAndReturn("getServletContext", mockServletContext.proxy()); 
    mockServletConfig.expectAndReturn("getInitParameter", "pathInfo", "melatitest/user/1");
    mockServletConfig.expectAndReturn("getServletName", "MelatiConfigTest");
    mockServletContext.expectAndReturn("log","MelatiConfigTest: init", null);
    mockServletContext.expectAndReturn("getMajorVersion", 2); 
    mockServletContext.expectAndReturn("getMinorVersion", 3); 
    mockServletContext.expectAndReturn("hashCode", 17); 
    mockServletContext.expectAndReturn("toString", "mockServletContext"); 
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
    mockServletContext.expectAndReturn("getResource", 
        "/org/melati/servlet/test/ClasspathRenderedException.wm", 
        new URL("file://org/melati/servlet/test/ClasspathRenderedException.wm")); 
    mockServletContext.expectAndReturn("getResource", 
        "/org/melati/servlet/test/ClasspathRenderedException.wm", 
        new URL("file://org/melati/template/webmacro/templets/html/error/ClasspathRenderedException.wm")); 
    mockServletContext.expectAndReturn("log", new IsInstanceOf(String.class), null);
//        "WebMacro:resource\tWARNING\tBrokerTemplateProvider:\tTemplate not found:\tjava/lang/Exception.wm", null);
    mockServletContext.expectAndReturn("getResource", "/org/melati/template/webmacro/templets/html/error/org.melati.servlet.test.ClasspathRenderedException.wm", null); 
    mockServletContext.expectAndReturn("getResource", "/org/melati/template/webmacro/templets/html/error/java.lang.Exception.wm", null); 

    mockHttpServletRequest.setSession(mockSession.proxy());
    mockSession.expectAndReturn("getId", "1");
    mockSession.expectAndReturn("getId", "1");

    mockSession.expectAndReturn("getAttribute", "org.melati.login.HttpSessionAccessHandler.overlayParameters",
        new HttpServletRequestParameters(mockHttpServletRequest));
        

    mockServletConfig.expectAndReturn("getServletContext", mockServletContext.proxy()); 
    mockServletConfig.expectAndReturn("getServletName", "MelatiConfigTest");
    mockServletContext.expectAndReturn("log","MelatiConfigTest: destroy", null);
    mockServletConfig.expectAndReturn("getServletContext", mockServletContext.proxy()); 
    
    ClasspathRenderedExceptionTemplateServlet aServlet = 
          new ClasspathRenderedExceptionTemplateServlet();
    aServlet.init((ServletConfig)mockServletConfig.proxy());
    aServlet.doPost(mockHttpServletRequest,  
                    mockHttpServletResponse);
    System.err.println(mockHttpServletResponse.getWritten());
    assertTrue(mockHttpServletResponse.getWritten().indexOf("org.melati.servlet.test.ClasspathRenderedException: A problem") > 0);
    assertTrue(mockHttpServletResponse.getWritten().indexOf("Rendered using template from classpath") > 0);
    aServlet.destroy();
  }

  /**
   * @see org.melati.servlet.ConfigServlet#writeConnectionPendingException(PrintWriter, Exception)
   */
  public void testWriteConnectionPendingException() {

  }

  /**
   * Test passback AccessPoemException handling.
   */
  public void testPassbackAccessPoemExceptionHandling() throws Exception {
    MockServletRequest mockHttpServletRequest = new MockServletRequest();
    MockServletResponse mockHttpServletResponse = new MockServletResponse(); 
    Mock mockSession = new Mock(HttpSession.class);
    Mock mockServletConfig = new Mock(ServletConfig.class);
    MockServletContext mockServletContext = new MockServletContext();

    setupMocks(mockHttpServletRequest, mockHttpServletResponse, mockSession, 
            mockServletConfig, mockServletContext);

    
    mockServletContext.expectAndReturn("getResource", 
            "/org/melati/template/webmacro/templets/html/error/org.melati.template.TemplateEngineException.wm"); 
    mockServletContext.expectAndReturn("log","WebMacro:resource WARNING BrokerTemplateProvider: Template not found: " + 
            "org/melati/template/webmacro/templets/html/error/org.melati.template.TemplateEngineException.wm");
    
    
    mockHttpServletRequest.setParameter("passback", "true");
    org.melati.test.TemplateServletTest aServlet = 
          new org.melati.test.TemplateServletTest();
    aServlet.init((ServletConfig)mockServletConfig.proxy());
    aServlet.doPost(mockHttpServletRequest,  
                    mockHttpServletResponse);
    aServlet.destroy();
      
    mockServletConfig.verify(); 
    //mockServletContext.verify(); 
    System.err.println(mockHttpServletResponse.getWritten());
    
  }
  /**
   * Test propagation of AccessPoemException handling.
   */
  public void testPropagateAccessPoemExceptionHandling() throws Exception {
    MockServletRequest mockHttpServletRequest = new MockServletRequest();
    MockServletResponse mockHttpServletResponse = new MockServletResponse(); 
    Mock mockSession = new Mock(HttpSession.class);
    Mock mockServletConfig = new Mock(ServletConfig.class);
    MockServletContext mockServletContext = new MockServletContext();

    setupMocks(mockHttpServletRequest, mockHttpServletResponse, mockSession, 
            mockServletConfig, mockServletContext);

    
    mockServletContext.expectAndReturn("getResource", 
            "/org/melati/template/webmacro/templets/html/error/org.melati.template.TemplateEngineException.wm"); 
    mockServletContext.expectAndReturn("log","WebMacro:resource WARNING BrokerTemplateProvider: Template not found: " + 
            "org/melati/template/webmacro/templets/html/error/org.melati.template.TemplateEngineException.wm");
    
    mockSession.expect("setAttribute", new Constraint []  {new IsEqual("org.melati.login.Login.triggeringRequestParameters"),
            new IsInstanceOf(HttpServletRequestParameters.class)});

     mockSession.expect("setAttribute", new Constraint []  {new IsEqual("org.melati.login.Login.triggeringException"),
            new IsInstanceOf(org.melati.poem.AccessPoemException.class)});
        

    
    mockHttpServletRequest.setParameter("propagate", "true");
    org.melati.test.TemplateServletTest aServlet = 
          new org.melati.test.TemplateServletTest();
    aServlet.init((ServletConfig)mockServletConfig.proxy());
    aServlet.doPost(mockHttpServletRequest,  
                    mockHttpServletResponse);
    aServlet.destroy();
      
    mockServletConfig.verify(); 
    System.err.println(mockHttpServletResponse.getWritten());
    
  }
}
