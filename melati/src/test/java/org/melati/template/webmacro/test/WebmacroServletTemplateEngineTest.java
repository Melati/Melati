/**
 * 
 */
package org.melati.template.webmacro.test;

import org.melati.template.test.ServletTemplateEngineSpec;
import org.melati.template.webmacro.WebmacroServletTemplateEngine;
import org.melati.template.webmacro.WebmacroTemplateEngine;

/**
 * @author timp
 * @since 22 Aug 2007
 *
 */
public class WebmacroServletTemplateEngineTest extends
        ServletTemplateEngineSpec {

  /**
   * @param name
   */
  public WebmacroServletTemplateEngineTest(String name) {
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
   * @see org.melati.template.test.ServletTemplateEngineSpec#setServletTemplateEngine()
   */
  protected void setServletTemplateEngine() {
    servletTemplateEngine = new WebmacroServletTemplateEngine();
  }

  /** 
   * {@inheritDoc}
   * @see org.melati.template.test.TemplateEngineSpec#setTemplateEngine()
   */
  protected void setTemplateEngine() {
    templateEngine = new WebmacroTemplateEngine();
  }

  /** 
   * {@inheritDoc}
   * @see org.melati.template.test.TemplateEngineSpec#testGetName()
   */
  public void testGetName() {
    assertEquals("webmacro",templateEngine.getName());    
  }

  /** 
   * {@inheritDoc}
   * @see org.melati.template.test.TemplateEngineSpec#testTemplateExtension()
   */
  public void testTemplateExtension() {
    assertEquals(".wm",templateEngine.templateExtension());    
  }
  /**
   * Test method for {@link org.melati.template.ServletTemplateEngine#getServletWriter(javax.servlet.http.HttpServletResponse, boolean)}.
   */
  public void testGetServletWriter() throws Exception {
    // TODO setup servlet to initialise
  }  

}
