package org.melati;

import java.net.URLEncoder;
import org.webmacro.engine.*;
import org.webmacro.servlet.*;
import org.melati.util.*;
import org.melati.poem.*;
import org.melati.templets.*;

public class HTMLMarkupLanguage extends MarkupLanguage {

  private AttributeHTMLMarkupLanguage attributeML = null;

  public HTMLMarkupLanguage(WebContext webContext,
			    TempletLoader templetLoader, MelatiLocale locale) {
    super("html", webContext, templetLoader, locale);
  }

  protected HTMLMarkupLanguage(String name, HTMLMarkupLanguage other) {
    super(name, other);
  }

  public AttributeHTMLMarkupLanguage getAttr() {
    if (attributeML == null)
      attributeML = new AttributeHTMLMarkupLanguage(this);
    return attributeML;
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

  public String rendered(AccessPoemException e) {
    return "<TABLE><TR><TD BGCOLOR=red>" + 
             "[Access denied to " + rendered(e.token) + "]" +
           "</TD></TR></TABLE>";
  }

  public String rendered(Exception e) {
    return "[" + rendered(e.toString()) + "]";
  }

  public String encoded(String s) {
    return URLEncoder.encode(s);
  }
}
