package org.melati.template.test;

import org.melati.MelatiConfig;
import org.melati.template.TemplateEngineException;
import org.melati.template.velocity.VelocityTemplateEngine;
import org.melati.util.MelatiBugMelatiException;
import org.melati.util.MelatiException;


/**
 * Run all the tests against velocity.
 * 
 * @author timp
 * @since 21-May-2006
 *
 */
public class WMLMarkupLanguageVelocityTest extends WMLMarkupLanguageSpec {

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

  /**
   * Test that a syntax error in a WM templet is handled by Velocity.
   */
  public void testSyntaxErrorInWMTemplet() throws Exception { 
    Object templated = new TemplatedWithWMSyntaxError();
    try { 
      ml.rendered(templated);
      fail("Should have bombed");
    } catch (MelatiBugMelatiException e) { 
      assertTrue(e.getCause().getMessage().startsWith("Encountered"));
      e = null;
    } catch (TemplateEngineException e) { // If a bad .vm file is found instead,
                                          // due to auto creation of .vm from .wm files
      assertTrue(e.getCause().getMessage().startsWith("Encountered"));
      e = null;
    }
  }
  
}
