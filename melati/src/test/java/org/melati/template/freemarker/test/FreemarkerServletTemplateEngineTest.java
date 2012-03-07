/**
 * 
 */
package org.melati.template.freemarker.test;

import org.melati.template.freemarker.FreemarkerServletTemplateEngine;
import org.melati.template.test.ServletTemplateEngineSpec;

/**
 * @author timp
 * @since 08 03 2012
 *
 */
public class FreemarkerServletTemplateEngineTest extends
        ServletTemplateEngineSpec {

  /**
   * @param name
   */
  public FreemarkerServletTemplateEngineTest(String name) {
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
    templateEngine = new FreemarkerServletTemplateEngine();
  }
  /** 
   * {@inheritDoc}
   * @see org.melati.template.test.ServletTemplateEngineSpec#setServletTemplateEngine()
   */
  protected void setServletTemplateEngine() {
    servletTemplateEngine = new FreemarkerServletTemplateEngine();
  }


  /** 
   * {@inheritDoc}
   * @see org.melati.template.test.TemplateEngineSpec#testGetName()
   */
  public void testGetName() {
    assertEquals("freemarker",templateEngine.getName());    
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
