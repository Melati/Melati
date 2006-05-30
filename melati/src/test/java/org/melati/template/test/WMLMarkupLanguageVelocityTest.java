package org.melati.template.test;

import org.melati.MelatiConfig;
import org.melati.template.velocity.VelocityTemplateEngine;
import org.melati.util.MelatiException;


/**
 * Run all the tests against velocity.
 * 
 * @author timp
 * @since 21-May-2006
 *
 */
public class WMLMarkupLanguageVelocityTest extends WMLMarkupLanguageTest {

  /**
   * Constructor.
   */
  public WMLMarkupLanguageVelocityTest() {
    super();
  }
  
  protected void melatiConfig() throws MelatiException {
    mc = new MelatiConfig();
    mc.setTemplateEngine(new VelocityTemplateEngine());
  }
  
}
