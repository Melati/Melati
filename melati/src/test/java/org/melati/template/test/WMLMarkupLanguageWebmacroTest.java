package org.melati.template.test;

import org.melati.MelatiConfig;
import org.melati.template.TemplateContext;
import org.melati.template.webmacro.WebmacroTemplateEngine;
import org.melati.util.MelatiException;
import org.melati.poem.AccessPoemException;
import org.melati.poem.Capability;


/**
 * Test the HTMLMarkupLanguage and its AttributeMarkupLanguage.
 * 
 * @author timp
 * @since 18-May-2006
 */
public class WMLMarkupLanguageWebmacroTest extends WMLMarkupLanguageSpec {

  /**
   * Constructor for PoemTest.
   * @param arg0
   */
  public WMLMarkupLanguageWebmacroTest(String arg0) {
    super(arg0);
  }
  /**
   * Constructor.
   */
  public WMLMarkupLanguageWebmacroTest() {
    super();
  }
  

  /** 
   * {@inheritDoc}
   * @see org.melati.template.test.MarkupLanguageSpec#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
    TemplateContext templateContext =
        templateEngine.getTemplateContext();
      m.setTemplateContext(templateContext);
  }
  protected void melatiConfig() throws MelatiException {
    mc = new MelatiConfig();
    if(mc.getTemplateEngine().getName() != "webmacro") {
      mc.setTemplateEngine(new WebmacroTemplateEngine());
    }
  }
  
  /**
   * Test method for getName.
   * 
   * @see org.melati.template.MarkupLanguage#getName()
   */
  public void testGetName() {
    assertEquals("wml", ml.getName());
    assertEquals("wml_attr", aml.getName());
  }

  /**
   * Test method for rendered(Exception).
   * @throws Exception 
   * 
   * @see org.melati.template.HTMLAttributeMarkupLanguage#
   *      rendered(AccessPoemException)
   */
  public void testRenderedAccessPoemException() throws Exception {
    
    assertEquals("java.lang.Exception",aml.rendered(new Exception()));

    AccessPoemException ape = new AccessPoemException(
          getDb().getUserTable().guestUser(), new Capability("Cool"));
    // System.err.println(ml.rendered(ape));
    assertTrue(ml.rendered(ape).indexOf(
          "org.melati.poem.AccessPoemException: " + 
          "You need the capability Cool but " + 
          "your access token _guest_ doesn&#39;t confer it") != -1);
    //assertTrue(ml.rendered(ape).indexOf("[Access denied to Melati guest user]") != -1);
    ape = new AccessPoemException();
    assertEquals("", aml.rendered(ape));
    //System.err.println(m.getWriter().toString());
    assertTrue(m.getWriter().toString().indexOf("[Access denied to [UNRENDERABLE EXCEPTION!]") != -1);
    ape = new AccessPoemException(
          getDb().getUserTable().guestUser(), new Capability("Cool"));
    assertEquals("", aml.rendered(ape));
      // NB Not at all sure how this value changed 
      //System.err.println(m.getWriter().toString());
      //assertTrue(m.getWriter().toString().indexOf("[Access denied to Melati guest user]") != -1);
    assertTrue(m.getWriter().toString().indexOf("[Access denied to _guest_]") != -1);

  }


}
