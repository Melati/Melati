package org.melati.template.test;

import org.melati.template.JavaMarkupLanguage;

/**
 * @author timp
 * @since 2017-01-22
 *
 */
public abstract class JavaMarkupLanguageSpec extends MarkupLanguageSpec {

  public JavaMarkupLanguageSpec(String arg0) {
    super(arg0);
  }

  public JavaMarkupLanguageSpec() {
    super();
  }

  protected void setUp() throws Exception {
    super.setUp();
    ml = new JavaMarkupLanguage(m);
    m.setMarkupLanguage(ml);
  }

}