/**
 * 
 */
package org.melati.login.test;

import org.melati.Melati;
import org.melati.MelatiConfig;
import org.melati.PoemContext;
import org.melati.login.AccessHandler;
import org.melati.poem.AccessPoemException;
import org.melati.poem.Capability;
import org.melati.poem.test.PoemTestCase;
import org.melati.template.AttributeMarkupLanguage;
import org.melati.template.MarkupLanguage;
import org.melati.template.TemplateContext;
import org.melati.template.TemplateEngine;
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
      templateEngine.getTemplateContext();
    m.setTemplateContext(templateContext);
    PoemContext poemContext = new PoemContext();
    poemContext.setLogicalDatabase("melatijunit");
    m.setPoemContext(poemContext);
   
  }

  protected void tearDown() throws Exception {
    super.tearDown();
  }
  
  /**
   * Set the particular AccessHandler to test.
   */
  public abstract void setAccessHandler();
  
  protected void melatiConfig() {
    mc = new MelatiConfig();
  }
  
  /**
   * Test method for handleAccessException(Melati, AccessPoemException).
   * 
   * @see org.melati.login.AccessHandler#handleAccessException(Melati, AccessPoemException)
   */
  public void testHandleAccessException() throws Exception {
    AccessPoemException ape = new AccessPoemException(
        getDb().getUserTable().guestUser(), new Capability("Cool"));
    it.handleAccessException(m, ape);
  }

  /**
   * Test method for buildRequest(Melati).
   * 
   * @see org.melati.login.AccessHandler#buildRequest(Melati)
   */
  public void testBuildRequest() throws Exception {
    it.buildRequest(m);
  }

  /**
   * Test method for {@link org.melati.login.AccessHandler#establishUser(Melati)}.
   */
  public void testEstablishUser() {
    it.establishUser(m);
    assertEquals("Melati database administrator",m.getUser().displayString());
  }

  
}
