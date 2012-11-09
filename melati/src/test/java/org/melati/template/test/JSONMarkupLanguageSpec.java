package org.melati.template.test;

import org.melati.poem.PoemLocale;
import org.melati.template.AttributeMarkupLanguage;
import org.melati.template.ClassNameTempletLoader;
import org.melati.template.HTMLAttributeMarkupLanguage;
import org.melati.template.HTMLMarkupLanguage;
import org.melati.template.JSONMarkupLanguage;

/**
 * @author timp
 * @since 2 Jul 2007
 *
 */
public abstract class JSONMarkupLanguageSpec extends MarkupLanguageSpec {

  /**
   * @param arg0
   */
  public JSONMarkupLanguageSpec(String arg0) {
    super(arg0);
  }

  /**
   * 
   */
  public JSONMarkupLanguageSpec() {
    super();
  }

  /** 
   * {@inheritDoc}
   * @see org.melati.template.test.MarkupLanguageSpec#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
    ml = new JSONMarkupLanguage(
            m, 
            ClassNameTempletLoader.getInstance(), 
            PoemLocale.HERE);
    m.setMarkupLanguage(ml);
  }

}