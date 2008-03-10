/**
 * 
 */
package org.melati.servlet.test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.melati.Melati;
import org.melati.util.HttpServletRequestParameters;

import junit.framework.TestCase;

import com.mockobjects.constraint.Constraint;
import com.mockobjects.constraint.IsEqual;
import com.mockobjects.dynamic.Mock;

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
    MockServletRequest mockHttpServletRequest = new MockServletRequest();
    MockServletResponse mockHttpServletResponse = new MockServletResponse(); 
                   
    mockHttpServletRequest.setPathInfo("/melatitest/user/1");
    
    mockHttpServletRequest.setHeader("Accept-Charset", "ISO-8859-1"); 
    

    MockServletConfig mockServletConfig = new MockServletConfig();

    org.melati.test.PoemServletTest aServlet = 
          new org.melati.test.PoemServletTest();
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
    MockServletRequest mockHttpServletRequest = new MockServletRequest();
    MockServletResponse mockHttpServletResponse = new MockServletResponse(); 
                   
    mockHttpServletRequest.setCharacterEncoding("ISO-8859-1"); 
    mockHttpServletRequest.setPathInfo("/melatitest/user/1"); 
    mockHttpServletRequest.setHeader("Accept-Charset", "ISO-8859-1"); 
    
           
    MockServletConfig mockServletConfig = new MockServletConfig();

    org.melati.test.PoemServletTest aServlet = 
          new org.melati.test.PoemServletTest();
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
    
    MockServletConfig mockServletConfig = new MockServletConfig();
    mockServletConfig.setInitParameter("pathInfo", "melatitest/user/1");

    mockSession.expectAndReturn("getId", "1");
    mockSession.expectAndReturn("getId", "1");

    mockHttpServletRequest.setSession(mockSession.proxy());
    
    mockSession.expectAndReturn("getAttribute", "org.melati.login.HttpSessionAccessHandler.overlayParameters",
        new HttpServletRequestParameters(mockHttpServletRequest));
        

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
  public void BorkedTestLDB() throws Exception {
    MockServletResponse response = new MockServletResponse();
    MockServletRequest request = new MockServletRequest();
    MockServletConfig mockServletConfig = new MockServletConfig();
    LDBPoemServlet aServlet = 
      new LDBPoemServlet();
    aServlet.init(mockServletConfig);
    aServlet.doPost(request, response);
    System.out.println(response.getWritten().toString());
    aServlet.destroy();
    assertTrue(response.getWritten().toString().indexOf("logicalDatabase = melatijunit") != -1);
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
          //System.err.println("Argument to Mock:" + arg);
          return _class.isInstance( arg );
      }
      
      public String toString() {
          return "an instance of <" + _class.getName() + ">";
      }
  }

}
