package org.melati.template.test;

import org.melati.MelatiConfig;
import org.melati.template.webmacro.WebmacroTemplateEngine;
import org.melati.util.MelatiException;


/**
 * Test the HTMLMarkupLanguage and its AttributeMarkupLanguage.
 * 
 * @author timp
 * @since 18-May-2006
 */
public class HTMLMarkupLanguageWebmacroTest extends HTMLMarkupLanguageSpec {
  
  protected void melatiConfig() throws MelatiException {
    mc = new MelatiConfig();
    mc.setTemplateEngine(new WebmacroTemplateEngine());
  }
 
}
