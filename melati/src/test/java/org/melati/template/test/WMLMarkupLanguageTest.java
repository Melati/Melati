package org.melati.template.test;

import org.melati.MelatiConfig;
import org.melati.template.ClassNameTempletLoader;
import org.melati.template.WMLAttributeMarkupLanguage;
import org.melati.template.WMLMarkupLanguage;
import org.melati.template.webmacro.WebmacroTemplateEngine;
import org.melati.util.MelatiException;
import org.melati.poem.PoemLocale;


/**
 * Test the HTMLMarkupLanguage and its AttributeMarkupLanguage.
 * 
 * @author timp
 * @since 18-May-2006
 */
public class WMLMarkupLanguageTest extends MarkupLanguageTestAbstract {

  /**
   * Constructor for PoemTest.
   * @param arg0
   */
  public WMLMarkupLanguageTest(String arg0) {
    super(arg0);
  }
  /**
   * Constructor.
   */
  public WMLMarkupLanguageTest() {
    super();
  }
  

  /**
   * @see MarkupLanguageAbstract#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
    ml = new WMLMarkupLanguage(
            m, 
            ClassNameTempletLoader.getInstance(), 
            PoemLocale.HERE);
    aml = new WMLAttributeMarkupLanguage((WMLMarkupLanguage)ml);
    m.setMarkupLanguage(ml);
    assertEquals(ml, m.getMarkupLanguage());    
    assertEquals(aml.getClass(), m.getMarkupLanguage().getAttr().getClass());    
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


}
