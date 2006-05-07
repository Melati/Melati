/*
 * $Source$
 * $Revision$
 *
 * Copyright (C) 2001 Myles Chippendale
 *
 * Part of Melati (http://melati.org), a framework for the rapid
 * development of clean, maintainable web applications.
 *
 * Melati is free software; Permission is granted to copy, distribute
 * and/or modify this software under the terms either:
 *
 * a) the GNU General Public License as published by the Free Software
 *    Foundation; either version 2 of the License, or (at your option)
 *    any later version,
 *
 *    or
 *
 * b) any version of the Melati Software License, as published
 *    at http://melati.org
 *
 * You should have received a copy of the GNU General Public License and
 * the Melati Software License along with this program;
 * if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA to obtain the
 * GNU General Public License and visit http://melati.org to obtain the
 * Melati Software License.
 *
 * Feel free to contact the Developers of Melati (http://melati.org),
 * if you would like to work out a different arrangement than the options
 * outlined here.  It is our intention to allow Melati to be used by as
 * wide an audience as possible.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * Contact details for copyright holder:
 *
 *     Myles Chippendale <mylesc@paneris.org>
 */
package org.melati.util;

import java.io.DataInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.Enumeration;

import javax.swing.text.AttributeSet;
import javax.swing.text.html.HTML;
import javax.swing.text.html.parser.AttributeList;
import javax.swing.text.html.parser.ContentModel;
import javax.swing.text.html.parser.DTD;
import javax.swing.text.html.parser.DTDConstants;
import javax.swing.text.html.parser.Element;

/**
 * An assortment of useful things to do with HTML.
 */
public final class HTMLUtils {

  private HTMLUtils() {}

  /** The DTD name */
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

  /**
   * If the given character has special meaning in HTML or will not
   * necessarily encode in the character set, then return an escape string.
   * <p>
   * The name of this method implies the character is escaped as a
   * character entity but if the second argument is true then newlines
   * are encoded as &lt;BR&gt;.
   * This is not required for attribute values.
   * <p>
   * Which characters will necessarily encode depends on the charset.
   * For backward compatibility if a charset is not passed we assume the
   * character will encode.
   * If a charset is passed and a character does not encode then we
   * replace it with a numeric character reference (not an entity
   * either but pretty similar).
   */
  public static String entityFor(char c, boolean mapBR, CharsetEncoder ce) {
    switch (c) {
      case '\n': return mapBR ? "<BR>\n" : null;
      case '<': return "&lt;";
      case '>': return "&gt;";
      case '&': return "&amp;";
      case '"': return "&quot;";
      case '\'': return "&#39;";
      default:
        if (ce == null || ce.canEncode(c)) {
          // System.err.println("Tested");
          return null;  
        } else {
          String result = "&#x" + Integer.toHexString(c) + ";";
          System.err.println("Cannot encode: " + c + " so encoded as: " + result);
          return result;
        }
    }
  }

 /**
  * Convenience method for backward compatibility.
  * 
  *@deprecated Specify encoding
  *@return A string with all special characters as entities 
  */
  public static String entitied(String s, boolean mapBR) {
    return entitied(s, mapBR, null);
  }
  public static String entitied(String s, boolean mapBR, String encoding) {
    int l = s.length();
    int i;
    String entity = null;

    CharsetEncoder ce = null;
    if (encoding != null) {
      ce = Charset.forName(encoding).newEncoder();
    }

    for (i = 0;
         i < l && (entity = entityFor(s.charAt(i), mapBR, ce)) == null;
         ++i);

    if (entity == null) return s;

    StringBuffer b = new StringBuffer(l * 2);
    for (int j = 0; j < i; ++j)
      b.append(s.charAt(j));

    b.append(entity);

    char c;
    for (++i; i < l; ++i) {
      c = s.charAt(i);
      entity = entityFor(c, mapBR, ce);
      if (entity != null)
        b.append(entity);
      else
        b.append(c);
    }
    return b.toString();
  }

  /**
   * Escape the given string as PCDATA without regard for any characters that
   * cannot be encoded in some required character set.
   * <p>
   * This is for backward compatibility mainly because it is used below and
   * I cannot be bothered to unravel the call sequence to find out what for.
   *
   * @see #entitied(String, boolean, String)
   */    
  public static String entitied(String s) {
    return entitied(s, true, null);
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
    for (++i; i < l; ++i) {
      c = s.charAt(i);
      escape = jsEscapeFor(c);
      if (escape != null)
        b.append(escape);
      else
        b.append(c);
    }
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

/**
 * An Instance of a tag.
 */
  public static class TagInstance {
    public final HTML.Tag tag;
    public final AttributeSet attributes;

    private TagInstance() {
      this.tag = null;
      this.attributes = null;      
    }
    /** Constructor. */
    public TagInstance(HTML.Tag tag, AttributeSet attributes) {
      this.tag = tag;
      this.attributes = attributes;
    }

    /**
     * Write tag to specified Writer. 
     * @param w The Writer to write to.
     */
    public void write(Writer w) throws IOException {
      HTMLUtils.write(w, tag, attributes);
    }

    /** A String representation. */
    public String toString() {
      return HTMLUtils.stringOf(tag, attributes);
    }
  }
}
