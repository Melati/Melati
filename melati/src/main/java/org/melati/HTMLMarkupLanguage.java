package org.melati;

import org.webmacro.servlet.*;
import org.melati.util.*;

public class HTMLMarkupLanguage extends MarkupLanguage {

  public HTMLMarkupLanguage(WebContext webContext) {
    super("html", webContext);
  }

  public final String elementFor(char c) {
    switch (c) {
      case '<': return "&lt;";
      case '>': return "&gt;";
      case '&': return "&amp;";
      case '"': return "&quot;";
      default: return null;
    }
  } 

  public String rendered(String s) {
    int l = s.length();
    int i = 0;
    String element = null;
    for (i = 0; i < l && (element = elementFor(s.charAt(i))) == null; ++i);

    if (element == null) return s;

    StringBuffer b = new StringBuffer(l * 2);
    for (int j = 0; j < i; ++j)
      b.append(s.charAt(j));

    b.append(element);

    char c;
    for (++i; i < l; ++i)
      if ((element = elementFor(c = s.charAt(i))) != null)
        b.append(element);
      else
        b.append(c);

    return b.toString();
  }

  public String escaped(String s) {
    return StringUtils.escaped(s, '"');
  }

  public String rendered(Integer i) {
    return i == null ? "" : i.toString();
  }
}
