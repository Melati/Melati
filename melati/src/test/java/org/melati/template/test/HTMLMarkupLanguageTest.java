package org.melati.template.test;

import org.melati.template.ClassNameTempletLoader;
import org.melati.template.HTMLAttributeMarkupLanguage;
import org.melati.template.HTMLMarkupLanguage;
import org.melati.util.MelatiLocale;


/**
 * Test the HTMLMarkupLanguage and its AttributeMarkupLanguage.
 * 
 * @author timp
 * @since 18-May-2006
 */
public class HTMLMarkupLanguageTest extends MarkupLanguageTestAbstract {
  
  /**
   * @see MarkupLanguageAbstract#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
    ml = new HTMLMarkupLanguage(
            m, 
            new ClassNameTempletLoader(), 
            MelatiLocale.HERE);
    aml = new HTMLAttributeMarkupLanguage((HTMLMarkupLanguage)ml);
    m.setMarkupLanguage(ml);
    assertEquals(ml, m.getMarkupLanguage());    
  }
  
  /**
   * Test method for getName.
   * 
   * @see org.melati.template.MarkupLanguage#getName()
   */
  public void testGetName() {
    assertEquals("html", ml.getName());
    assertEquals("html_attr", aml.getName());
  }

  
}
