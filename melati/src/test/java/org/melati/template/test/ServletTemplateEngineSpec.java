/**
 * 
 */
package org.melati.template.test;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletResponse;

import org.melati.template.ServletTemplateEngine;

import com.mockobjects.dynamic.Mock;
import com.mockobjects.dynamic.OrderedMock;


/**
 * @author timp
 * @since 2007/08/21
 *
 */
public abstract class ServletTemplateEngineSpec extends TemplateEngineSpec {

  protected ServletTemplateEngine servletTemplateEngine = null;
  
  /**
   * @param name test name
   */
  public ServletTemplateEngineSpec(String name) {
    super(name);
  }

  abstract protected void setServletTemplateEngine();
  
  /**
   * @throws java.lang.Exception
   */
  protected void setUp() throws Exception {
    super.setUp();
    setTemplateEngine();
  }

  /**
   * @throws java.lang.Exception
   */
  protected void tearDown() throws Exception {
    super.tearDown();
  }

  /**
   * Test method for {@link org.melati.template.ServletTemplateEngine#init(org.melati.MelatiConfig, javax.servlet.http.HttpServlet)}.
   * @throws Exception 
   */
  public void testInitMelatiConfigHttpServlet() throws Exception {
    
  }

  /**
   * Test method for {@link org.melati.template.ServletTemplateEngine#getServletWriter(javax.servlet.http.HttpServletResponse, boolean)}.
   */
  public void testGetServletWriter() throws Exception {
    Mock mockHttpServletResponse = new OrderedMock(HttpServletResponse.class, "Response with non-default name"); 
    final StringWriter output = new StringWriter(); 
    final PrintWriter contentWriter = new PrintWriter(output); 
           
    mockHttpServletResponse.expectAndReturn( "getWriter", contentWriter ); 
    servletTemplateEngine.getServletWriter((HttpServletResponse)mockHttpServletResponse.proxy(), true);
    mockHttpServletResponse.expectAndReturn( "getWriter", contentWriter ); 
    servletTemplateEngine.getServletWriter((HttpServletResponse)mockHttpServletResponse.proxy(), false);
  }


  /**
   * Test method for {@link org.melati.template.ServletTemplateEngine#getServletTemplateContext(org.melati.Melati)}.
   * @throws Exception 
   */
  public void testGetServletTemplateContext() throws Exception {
    
  }

}
