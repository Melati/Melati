package org.melati;

import java.util.*;
import java.io.*;
import org.melati.util.*;
import org.melati.poem.*;
import org.webmacro.servlet.*;

public class Melati {

  private WebContext webContext;

  public Melati(WebContext webContext) {
    this.webContext = webContext;
  }

  public HTMLMarkupLanguage getHTMLMarkupLanguage() {
    return new HTMLMarkupLanguage(webContext);
  }
}
