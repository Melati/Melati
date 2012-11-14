package org.melati.template.test;

import org.melati.poem.PoemLocale;
import org.melati.template.ClassNameTempletLoader;
import org.melati.template.JSONMarkupLanguage;

/**
 * @author timp
 * @since 2007-11-07
 *
 */
public abstract class JSONMarkupLanguageSpec extends MarkupLanguageSpec {

  public JSONMarkupLanguageSpec(String arg0) {
    super(arg0);
  }

  public JSONMarkupLanguageSpec() {
    super();
  }

  protected void setUp() throws Exception {
    super.setUp();
    ml = new JSONMarkupLanguage(
            m, 
            ClassNameTempletLoader.getInstance(), 
            PoemLocale.HERE);
    m.setMarkupLanguage(ml);
  }

}