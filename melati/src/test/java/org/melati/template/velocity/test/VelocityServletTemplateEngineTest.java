/**
 * 
 */
package org.melati.template.velocity.test;

import org.melati.template.test.ServletTemplateEngineSpec;
import org.melati.template.velocity.VelocityServletTemplateEngine;

/**
 * @author timp
 * @since 22 Aug 2007
 *
 */
public class VelocityServletTemplateEngineTest extends
        ServletTemplateEngineSpec {

  /**
   * @param name
   */
  public VelocityServletTemplateEngineTest(String name) {
    super(name);
  }
  
  /**
   * @throws java.lang.Exception
   */
  protected void setUp() throws Exception {
    super.setUp();
    setServletTemplateEngine();
  }

  /**
   * @throws java.lang.Exception
   */
  protected void tearDown() throws Exception {
    super.tearDown();
  }

  
  /** 
   * {@inheritDoc}
   * @see org.melati.template.test.TemplateEngineSpec#setTemplateEngine()
   */
  protected void setTemplateEngine() {
    templateEngine = new VelocityServletTemplateEngine();
  }
  /** 
   * {@inheritDoc}
   * @see org.melati.template.test.ServletTemplateEngineSpec#setServletTemplateEngine()
   */
  protected void setServletTemplateEngine() {
    servletTemplateEngine = new VelocityServletTemplateEngine();
  }


  /** 
   * {@inheritDoc}
   * @see org.melati.template.test.TemplateEngineSpec#testGetName()
   */
  public void testGetName() {
    assertEquals("velocity",templateEngine.getName());    
  }

  /** 
   * {@inheritDoc}
   * @see org.melati.template.test.TemplateEngineSpec#testTemplateExtension()
   */
  public void testTemplateExtension() {
    assertEquals(".vm",templateEngine.templateExtension());    
  }


  /**
   * Test method for {@link org.melati.template.ServletTemplateEngine#getServletWriter(javax.servlet.http.HttpServletResponse, boolean)}.
   */
  public void testGetServletWriter() throws Exception {
    // TODO setup servlet to initialise
  }  

}
