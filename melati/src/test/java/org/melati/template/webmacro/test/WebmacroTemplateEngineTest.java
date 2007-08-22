/**
 * 
 */
package org.melati.template.webmacro.test;

import org.melati.template.test.TemplateEngineSpec;
import org.melati.template.webmacro.WebmacroTemplateEngine;

/**
 * @author timp
 * @since 22 Aug 2007
 *
 */
public class WebmacroTemplateEngineTest extends TemplateEngineSpec {

  /**
   * @param name
   */
  public WebmacroTemplateEngineTest(String name) {
    super(name);
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

}
