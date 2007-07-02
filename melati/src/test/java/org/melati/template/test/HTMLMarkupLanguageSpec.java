package org.melati.template.test;

import org.melati.poem.PoemLocale;
import org.melati.template.ClassNameTempletLoader;
import org.melati.template.HTMLAttributeMarkupLanguage;
import org.melati.template.HTMLMarkupLanguage;

/**
 * @author timp
 * @since 2 Jul 2007
 *
 */
public abstract class HTMLMarkupLanguageSpec extends MarkupLanguageSpec {

  /**
   * @param arg0
   */
  public HTMLMarkupLanguageSpec(String arg0) {
    super(arg0);
  }

  /**
   * 
   */
  public HTMLMarkupLanguageSpec() {
    super();
  }

  /** 
   * {@inheritDoc}
   * @see org.melati.template.test.MarkupLanguageSpec#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
    ml = new HTMLMarkupLanguage(
            m, 
            ClassNameTempletLoader.getInstance(), 
            PoemLocale.HERE);
    aml = new HTMLAttributeMarkupLanguage((HTMLMarkupLanguage)ml);
    m.setMarkupLanguage(ml);
  }

}