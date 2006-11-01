/**
 * 
 */
package org.melati.login.test;

import org.melati.Melati;
import org.melati.MelatiConfig;
import org.melati.PoemContext;
import org.melati.login.AccessHandler;
import org.melati.poem.AccessPoemException;
import org.melati.poem.AccessToken;
import org.melati.poem.Capability;
import org.melati.poem.test.PoemTestCase;
import org.melati.template.AttributeMarkupLanguage;
import org.melati.template.MarkupLanguage;
import org.melati.template.TemplateContext;
import org.melati.template.TemplateEngine;
//import org.melati.template.webmacro.WebmacroTemplateEngine;
import org.melati.util.MelatiException;
import org.melati.util.MelatiStringWriter;

/**
 * @author timp
 *
 */
public abstract class AccessHandlerTestAbstract extends PoemTestCase {

  protected AccessHandler it = null;
 
  protected static MelatiConfig mc = null;
  protected static TemplateEngine templateEngine = null;
  protected static MarkupLanguage ml = null;
  protected static AttributeMarkupLanguage aml = null;
  protected static Melati m = null;
  
  /**
   * Constructor for AccessHandlerTestAbstract.
   * @param name
   */
  public AccessHandlerTestAbstract(String name) {
    super(name);
  }

  /**
   * Set the particular AccessHandler to test.
   */
  public abstract void setAccessHandler();
  
  /*
   * @see TestCase#setUp()
   */
  protected void setUp()
      throws Exception {
    super.setUp();
    setAccessHandler();
    melatiConfig();
    templateEngine = mc.getTemplateEngine();
    if (templateEngine != null)
      templateEngine.init(mc);
    else fail();
    m = new Melati(mc, new MelatiStringWriter());
    m.setTemplateEngine(templateEngine);
    assertNotNull(m.getTemplateEngine());
    TemplateContext templateContext =
      templateEngine.getTemplateContext(m);
    m.setTemplateContext(templateContext);
    PoemContext poemContext = new PoemContext();
    poemContext.setLogicalDatabase("poemtest");
    m.setPoemContext(poemContext);
   
  }

  protected void melatiConfig() throws MelatiException {
    mc = new MelatiConfig();
    /* 
     * Not sure what this was about
    if(mc.getTemplateEngine().getName() != "webmacro") {
      mc.setTemplateEngine(new WebmacroTemplateEngine());
    }
    */
  }
  
  /**
   * Test method for handleAccessException(Melati, AccessPoemException).
   * 
   * @see org.melati.login.AccessHandler#handleAccessException(Melati, AccessPoemException)
   */
  public void testHandleAccessException() {
    try {
      AccessPoemException ape = new AccessPoemException(
          (AccessToken)getDb().getUserTable().guestUser(), new Capability("Cool"));
      System.err.println(m);
      it.handleAccessException(m, ape);
    } catch (Exception e) {
      e.printStackTrace();
      fail();
    }
  }

  /**
   * Test method for buildRequest(Melati).
   * 
   * @see org.melati.login.AccessHandler.buildRequest(Melati)
   */
  public void testBuildRequest() {
    try {
      it.buildRequest(m);
    } catch (Exception e) {
      e.printStackTrace();
      fail();
    }
  }

  /*
   * Test method for 'org.melati.login.AccessHandler.establishUser(Melati)'
   */
  public void testEstablishUser() {
    System.err.println("MA:"+ m.getArguments());
    it.establishUser(m);
  }

  
}
