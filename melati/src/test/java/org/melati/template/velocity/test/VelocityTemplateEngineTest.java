/**
 * 
 */
package org.melati.template.velocity.test;

import org.melati.template.test.TemplateEngineSpec;
import org.melati.template.velocity.VelocityTemplateEngine;

/**
 * @author timp
 * @since 22 Aug 2007
 *
 */
public class VelocityTemplateEngineTest extends TemplateEngineSpec {

  /**
   * @param name
   */
  public VelocityTemplateEngineTest(String name) {
    super(name);
  }

  /** 
   * {@inheritDoc}
   * @see org.melati.template.test.TemplateEngineSpec#setTemplateEngine()
   */
  protected void setTemplateEngine() {
    templateEngine = new VelocityTemplateEngine();
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

}
