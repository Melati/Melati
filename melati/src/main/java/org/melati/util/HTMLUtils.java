package org.melati.util;

import java.io.*;
import java.util.*;
import javax.swing.text.html.parser.*;
import javax.swing.text.html.*;
import javax.swing.text.*;
import javax.swing.text.html.parser.Element;

public class HTMLUtils {
  private HTMLUtils() {}

  public static final String dtdNameForHTMLParser = "html32.bdtd";

  private static DTD dtdForHTMLParser = null;

  public static void add(ContentModel cm, Element existing, Element alt) {
    if (cm.content == existing) {
      ContentModel twig =
          new ContentModel(0, existing, new ContentModel(0, alt, null));
      if (cm.type == 0) {
        cm.type = '|';
        cm.content = twig;
      }
      else
        cm.content = new ContentModel('|', twig);
    }
    else if (cm.content instanceof ContentModel)
      add((ContentModel)cm.content, existing, alt);

    if (cm.next != null)
      add(cm.next, existing, alt);
  }

  public static void addToContentModels(DTD dtd,
                                        Element existing, Element alt) {
    for (Enumeration els = dtd.elementHash.elements();
         els.hasMoreElements();) {
      ContentModel c = ((Element)els.nextElement()).content;
      if (c != null)
        add(c, existing, alt);
    }
  }

  public static DTD dtdForHTMLParser() {
    // not clear HTF this putDTDHash/getDTD API is meant to be useful ...

    if (dtdForHTMLParser == null)
      try {
	dtdForHTMLParser = DTD.getDTD(dtdNameForHTMLParser);
	InputStream res = dtdForHTMLParser.getClass().
                              getResourceAsStream(dtdNameForHTMLParser);
	if (res == null)
	  throw new FileNotFoundException(
	      "Resource " + dtdNameForHTMLParser + " not found: " +
	      "but it ought to be in rt.jar?!");
	dtdForHTMLParser.read(new DataInputStream(res));

        // add <SPAN CLASS=...> with the same content model as <DIV>
        // [which is a hack for bibliomania!!]
        // usable in the same places as <I>

        Element div = (Element)dtdForHTMLParser.elementHash.get("div");
        Element i = (Element)dtdForHTMLParser.elementHash.get("i");

        dtdForHTMLParser.defineElement(
           "span", DTDConstants.STARTTAG, false, false, div.content, null, null,
           new AttributeList("class", DTDConstants.CDATA,
                             0, null, null, null));

        Element span = (Element)dtdForHTMLParser.elementHash.get("span");

        addToContentModels(dtdForHTMLParser, i, span);
      }
      catch (Exception e) {
	throw new UnexpectedExceptionException(
	    "making the DTD for Sun's HTML parser", e);
      }

    return dtdForHTMLParser;
  }

  public static FictionalNotifyingDocumentParser newDocumentParser() {
    return new FictionalNotifyingDocumentParser(dtdForHTMLParser());
  }

  public static String entityFor(char c) {
    switch (c) {
      case '\n': return "<BR>\n";
      case '<': return "&lt;";
      case '>': return "&gt;";
      case '&': return "&amp;";
      case '"': return "&quot;";
      case '\'': return "&#39;";
      default: return null;
    }
  } 

  public static String entitied(String s) {
    int l = s.length();
    int i = 0;
    String entity = null;
    for (i = 0; i < l && (entity = entityFor(s.charAt(i))) == null; ++i);

    if (entity == null) return s;

    StringBuffer b = new StringBuffer(l * 2);
    for (int j = 0; j < i; ++j)
      b.append(s.charAt(j));

    b.append(entity);

    char c;
    for (++i; i < l; ++i)
      if ((entity = entityFor(c = s.charAt(i))) != null)
        b.append(entity);
      else
        b.append(c);

    return b.toString();
  }

  public static String entityForWithoutBRSubstitution(char c) {
    switch (c) {
      case '<': return "&lt;";
      case '>': return "&gt;";
      case '&': return "&amp;";
      case '"': return "&quot;";
      case '\'': return "&#39;";
      default: return null;
    }
  } 

  public static String entitiedWithoutBRSubstitution(String s) {
    int l = s.length();
    int i = 0;
    String entity = null;
    for (i = 0; i < l && (entity = entityForWithoutBRSubstitution(s.charAt(i))) == null; ++i);

    if (entity == null) return s;

    StringBuffer b = new StringBuffer(l * 2);
    for (int j = 0; j < i; ++j)
      b.append(s.charAt(j));

    b.append(entity);

    char c;
    for (++i; i < l; ++i)
      if ((entity = entityForWithoutBRSubstitution(c = s.charAt(i))) != null)
        b.append(entity);
      else
        b.append(c);

    return b.toString();
  }

  public static String jsEscapeFor(char c) {
    switch (c) {
      case '\n': return "\\012";
      case '"': return "\\042";
      case '\'': return "\\047";
      default: return null;
    }
  } 

  public static String jsEscaped(String s) {
    int l = s.length();
    int i = 0;
    String escape = null;
    for (i = 0; i < l && (escape = jsEscapeFor(s.charAt(i))) == null; ++i);

    if (escape == null) return s;

    StringBuffer b = new StringBuffer(l * 2);
    for (int j = 0; j < i; ++j)
      b.append(s.charAt(j));

    b.append(escape);

    char c;
    for (++i; i < l; ++i)
      if ((escape = jsEscapeFor(c = s.charAt(i))) != null)
        b.append(escape);
      else
        b.append(c);

    return b.toString();
  }

  public static void write(Writer w, HTML.Tag tag, AttributeSet attributes)
      throws IOException {
    w.write('<');
    w.write(tag.toString());
    for (Enumeration a = attributes.getAttributeNames();
	 a.hasMoreElements();) {
      Object n = a.nextElement();
      if (attributes.isDefined(n)) {
        w.write(' ');
        w.write(n.toString());
        w.write("=\"");
        w.write(entitied(attributes.getAttribute(n).toString()));
        w.write('"');
      }
    }
    w.write('>');
  }

  public static String stringOf(HTML.Tag tag, AttributeSet attributes) {
    StringWriter w = new StringWriter();

    try {
      write(w, tag, attributes);
    }
    catch (IOException e) {
      throw new UnexpectedExceptionException(e);
    }

    return w.toString();
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

    public String toString() {
      return HTMLUtils.stringOf(tag, attributes);
    }
  }
}
