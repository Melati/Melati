/**
 * 
 */
package org.melati.template.test;

import org.melati.template.ServletTemplateEngine;


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
  public void testGetServletWriter() {
    
  }

  /**
   * Test method for {@link org.melati.template.ServletTemplateEngine#getServletTemplateContext(org.melati.Melati)}.
   */
  public void testGetServletTemplateContext() {
    
  }

}
