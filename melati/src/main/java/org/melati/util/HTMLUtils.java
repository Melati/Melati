package org.melati.util;

import java.io.*;
import java.util.*;
import javax.swing.text.*;
import javax.swing.text.html.*;
import javax.swing.text.html.parser.*;

public class HTMLUtils {
  private HTMLUtils() {}

  public static String elementFor(char c) {
    switch (c) {
      case '<': return "&lt;";
      case '>': return "&gt;";
      case '&': return "&amp;";
      case '"': return "&quot;";
      default: return null;
    }
  } 

  public static String entitied(String s) {
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

  public static void write(Writer w, HTML.Tag tag, AttributeSet attributes)
      throws IOException {
    w.write("<" + tag);
    for (Enumeration a = attributes.getAttributeNames();
	 a.hasMoreElements();) {
      Object n = a.nextElement();
      if (attributes.isDefined(n))
	w.write(
	    " " + n + "=\"" +
	    entitied(attributes.getAttribute(n).toString()) + '"');
    }
    w.write(">");
  }

  public static class TagInstance {
    public final HTML.Tag tag;
    public final AttributeSet attributes;

    public TagInstance(HTML.Tag tag, AttributeSet attributes) {
      this.tag = tag;
      this.attributes = attributes;
    }

    public void write(Writer w) throws IOException {
      HTMLUtils.write(w, tag, attributes);
    }
  }
}
