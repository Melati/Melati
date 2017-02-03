/**
 * 
 */
package org.melati.servlet.test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
    //doGetPost(); 
  }
  /**
   * @see org.melati.servlet.ConfigServlet#doPost(HttpServletRequest, HttpServletResponse)
   */
  public void testDoPostHttpServletRequestHttpServletResponse() throws Exception {
    doGetPost(); 

  }
  
  /**
   * 
   */
  public void doGetPost() throws Exception {
    MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
    MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse(); 
    MockServletConfig mockServletConfig = new MockServletConfig();

    org.melati.test.TemplateServletTest aServlet = 
          new org.melati.test.TemplateServletTest();
    aServlet.init(mockServletConfig);
    aServlet.doPost(mockHttpServletRequest,  
                    mockHttpServletResponse);
    aServlet.destroy();
      
    assertTrue(mockHttpServletResponse.getWritten().indexOf("TemplateServlet Test") > 1);


  }


  /**
   * @see org.melati.servlet.TemplateServlet#error(org.melati.Melati, Exception)
   */
  public void testError() throws Exception {
    MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
    MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse(); 
                   
    
    MockHttpSession mockSession = new MockHttpSession();
           
    MockServletConfig mockServletConfig = new MockServletConfig();
    mockServletConfig.setInitParameter("pathInfo", "melatitest/user/1");
    mockServletConfig.setServletName("MelatiConfigTest");

    mockHttpServletRequest.setSession(mockSession);
    
    ExceptionTemplateServlet aServlet = 
          new ExceptionTemplateServlet();
    aServlet.init(mockServletConfig);
    aServlet.doPost( mockHttpServletRequest,  
                     mockHttpServletResponse);
    assertTrue(mockHttpServletResponse.getWritten().indexOf("Melati Error Template") > 0);
    assertTrue(mockHttpServletResponse.getWritten().indexOf("java.lang.Exception: A problem") > 0);
    aServlet.destroy();
  }
  

  /**
   * Test that an error templet from the classpath is used.
   * @see org.melati.servlet.TemplateServlet#error(org.melati.Melati, Exception)
   */
  public void testErrorUsesClasspathTemplet() throws Exception {
    MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
    MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();

    MockHttpSession mockSession = new MockHttpSession();
           
    MockServletConfig mockServletConfig = new MockServletConfig();
    mockServletConfig.setInitParameter("pathInfo", "melatitest/user/1");
    mockServletConfig.setServletName("MelatiConfigTest");

    mockHttpServletRequest.setSession(mockSession);
    ClasspathRenderedExceptionTemplateServlet aServlet = 
          new ClasspathRenderedExceptionTemplateServlet();
    aServlet.init(mockServletConfig);
    aServlet.doPost(mockHttpServletRequest,  
                    mockHttpServletResponse);
    assertTrue(mockHttpServletResponse.getWritten().indexOf("org.melati.servlet.test.ClasspathRenderedException: A problem") > 0);
    assertTrue(mockHttpServletResponse.getWritten().indexOf("Rendered using template from classpath") > 0);
    aServlet.destroy();
  }

  /**
   * Test passback AccessPoemException handling.
   */
  public void testPassbackAccessPoemExceptionHandling() throws Exception {
    MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
    MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse(); 
    MockServletConfig mockServletConfig = new MockServletConfig();
    MockServletContext mockServletContext = new MockServletContext();

    
    mockServletContext.expectAndReturn("getResource", 
            "/org/melati/template/webmacro/templets/html/error/org.melati.template.TemplateEngineException.wm"); 
    mockServletContext.expectAndReturn("log","WebMacro:resource WARNING BrokerTemplateProvider: Template not found: " + 
            "org/melati/template/webmacro/templets/html/error/org.melati.template.TemplateEngineException.wm");
    
    
    mockHttpServletRequest.setParameter("passback", "true");
    org.melati.test.TemplateServletTest aServlet = 
          new org.melati.test.TemplateServletTest();
    aServlet.init(mockServletConfig);
    aServlet.doPost(mockHttpServletRequest,  
                    mockHttpServletResponse);
    aServlet.destroy();
      
    assertTrue(mockHttpServletResponse.getWritten().indexOf("[Access denied to Melati guest user]") != -1);
    
  }
  /**
   * Test propagation of AccessPoemException handling.
   */
  public void testPropagateAccessPoemExceptionHandling() throws Exception {
    MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
    MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse(); 
    MockServletConfig mockServletConfig = new MockServletConfig();
    MockServletContext mockServletContext = new MockServletContext();

    
    mockServletContext.expectAndReturn("getResource", 
            "/org/melati/template/webmacro/templets/html/error/org.melati.template.TemplateEngineException.wm"); 
    mockServletContext.expectAndReturn("log","WebMacro:resource WARNING BrokerTemplateProvider: Template not found: " + 
            "org/melati/template/webmacro/templets/html/error/org.melati.template.TemplateEngineException.wm");
    
    
    mockHttpServletRequest.setParameter("propagate", "true");
    org.melati.test.TemplateServletTest aServlet = 
          new org.melati.test.TemplateServletTest();
    aServlet.init(mockServletConfig);
    aServlet.doPost(mockHttpServletRequest,  
                    mockHttpServletResponse);
    aServlet.destroy();
      
    assertEquals(mockHttpServletResponse.getWritten(), "");
    
  }
}
