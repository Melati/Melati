package org.melati;

import org.melati.poem.*;

public class AttributeHTMLMarkupLanguage extends HTMLMarkupLanguage {

  public AttributeHTMLMarkupLanguage(HTMLMarkupLanguage html) {
    super("html_attr", html);
  }

  public String rendered(AccessPoemException e) {
    return "[Access denied to " + rendered(e.token) + "]";
  }
}
