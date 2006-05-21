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
public class HTMLMarkupLanguageVelocityTest extends HTMLMarkupLanguageTest {

  /**
   * 
   */
  public HTMLMarkupLanguageVelocityTest() {
    super();
  }
  protected void melatiConfig() throws MelatiException {
    mc = new MelatiConfig();
    mc.setTemplateEngine(new VelocityTemplateEngine());
  }
  
}
