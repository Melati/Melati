/*
 * $Source$
 * $Revision$
 *
 * Copyright (C) 2000 William Chesters
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
 *     William Chesters <williamc@paneris.org>
 *     http://paneris.org/~williamc
 *     Obrechtstraat 114, 2517VX Den Haag, The Netherlands
 */

package org.melati.template;

import java.text.DateFormat;

import java.io.Writer;
import java.io.StringWriter;
import java.io.IOException;

import org.melati.Melati;
import org.melati.util.MelatiLocale;
import org.melati.util.JSDynamicTree;
import org.melati.poem.Persistent;
import org.melati.poem.Field;
import org.melati.poem.AccessPoemException;

/**
 * MarkupLanguage provise a variety of menthods for rendering objects in a 
 * template.  Each object to be rendered has 3 methods:
 *
 * 1 - String rendered(Object o) - this will render the object to a string
 * 2 - void render(Object o) - renders the object to melati.getWriter()
 * 3 - void render(Object o, Writer w) - render the object to w.
 *
 * For maximum effieiency, render the object direct to the output stream using
 * method (2) above.  However, WebMacro throws errors on clals to void methods,
 * so we user (1) when writing Webmacro tempaltes (for the time being)
 */

public abstract class MarkupLanguage {

  private String name;
/**
 * The melati currently being used
 */  
  protected Melati melati;
  private TemplateContext templateContext;
  private TempletLoader templetLoader;
  private MelatiLocale locale;

/**
 * Construct a Markup Language object
 *
 * @param name - the name associated with this markup language.
 *    This is used to determin where to load
 *    templates from ie 'html' templates are
 *    found in the 'html' directory.
 * @param melati - the melati currently in use
 * @param templetLoader - the template loader in use
 *       (taken from org.melati.MelaitServlet.properties)
 * @param locale - the locale in use
 *    (taken from org.melati.MelaitServlet.properties)
 */  
  public MarkupLanguage(String name, 
                        Melati melati,
                        TempletLoader templetLoader, 
                        MelatiLocale locale) {
    this.name = name;
    this.melati = melati;
    this.templateContext = melati.getTemplateContext();
    this.templetLoader = templetLoader;
    this.locale = locale;
  }

/**
 * construct a new MarkupLanguage given a new name and an
 * existing MarkupLanguage
 *
 * @param name - the name of the new MarkupLanguage
 * @param other - the Markup Language to base this one on
 */  
  protected MarkupLanguage(String name, MarkupLanguage other) {
    this(name, other.melati, other.templetLoader, other.locale);
  }

/**
 * get the name of this Markup Language
 *
 * @return name - the name associated with this markup language.
 *                This is used to determin where to load
 *                templates from ie 'html' templates are
 *                found in the 'html' directory.
 */  
  public String getName() {
    return name;
  }

/**
 * Render a String in a MarkupLanguage specific way.  Return a string.
 *
 * @param s - the string to be rendered
 * @throws IOException - if there is a problem during rendering
 * @return - the String having been rendered in a MarkupLanguage specific way.
 */  
  public String rendered(String s) throws IOException {
    StringWriter sw = new StringWriter();
    render(s,sw);
    return sw.toString();
  }

/**
 * Render a String in a MarkupLanguage specific way (to the Melati.getWriter())
 *
 * @param s - the string to be rendered
 * @throws IOException - if there is a problem during rendering
 */  
  public void render(String s) throws IOException {
    render(s,melati.getWriter());
  }

/**
 * Render a String in a MarkupLanguage specific way (to a supplied Writer)
 *
 * @param s - the string to be rendered
 * @param writer - the Writer to render this String to
 * @throws IOException - if there is a problem during rendering
 */  
  public abstract void render(String s, Writer writer) throws IOException;

/**
 * Render a String in a MarkupLanguage specific way, limiting it's length.
 * Return a String
 *
 * @param s - the string to be rendered
 * @param limit - the lenght to trim the string to
 * @throws IOException - if there is a problem during rendering
 * @return - the String having been rendered in a MarkupLanguage specific way.
 */  
  public String rendered(String s, int limit) throws IOException {
    StringWriter sw = new StringWriter();
    render(s,limit,sw);
    return sw.toString();
  }

/**
 * Render a String in a MarkupLanguage specific way, limiting it's length.
 * Rendering to melati.getWriter()
 *
 * @param s - the string to be rendered
 * @param limit - the lenght to trim the string to
 * @throws IOException - if there is a problem during rendering
 */  
  public void render(String s, int limit) throws IOException {
    render(s,limit,melati.getWriter());
  }

/**
 * Render a String in a MarkupLanguage specific way, limiting it's length.
 * Render to a supplied Writer
 *
 * @param s - the string to be rendered
 * @param writer - the Writer to render this String to
 * @param limit - the lenght to trim the string to
 * @throws IOException - if there is a problem during rendering
 */  
  public void render(String s, int limit, Writer writer) throws IOException {
    render(s.length() < limit + 3 ? s : s.substring(0, limit) + "...", writer);
  }

/**
 * Render an Object in a MarkupLanguage specific way, returning a String
 *
 * @return - the object rendered as a String in a MarkupLanguage specific way.
 * @param o - the Object to be rendered
 * @throws IOException - if there is a problem during rendering
 * @throws TemplateEngineException - if there is a problem with the 
 *                                   TemplateEngine
 */  
  public String rendered(Object o)
                throws TemplateEngineException, IOException {
    StringWriter sw = new StringWriter();
    render(o,sw);
    return sw.toString();
  }

/**
 * Render an Object in a MarkupLanguage specific way, rendering to 
 * melati.getWriter()
 *
 * @param o - the Object to be rendered
 * @throws IOException - if there is a problem during rendering
 * @throws TemplateEngineException - if there is a problem with the 
 *                                   TemplateEngine
 */  
  public void render(Object o) throws TemplateEngineException, IOException {
    render(o,melati.getWriter());
  }

/**
 * Render an Object in a MarkupLanguage specific way, rendering to a supplied 
 * Writer
 *
 * @param o - the Object to be rendered
 * @param writer - the Writer to render this Object to
 * @throws IOException - if there is a problem during rendering
 * @throws TemplateEngineException - if there is a problem with the 
 *                                   TemplateEngine
 */  
  public void render(Object o, Writer writer)
              throws TemplateEngineException, IOException {
    if (o instanceof JSDynamicTree)
      render((JSDynamicTree)o, writer);
    if (o instanceof Persistent)
      render(((Persistent)o).displayString(locale, DateFormat.MEDIUM), writer);
    if (o instanceof Exception)
      render((Exception)o, writer);
    render(o.toString(), writer);
  }

/**
 * Render an Tree Object in a MarkupLanguage specific way, returning a String
 *
 * @return - the Tree rendered as a String in a MarkupLanguage specific way.
 * @param tree - the Tree to be rendered
 * @throws IOException - if there is a problem during rendering
 * @throws TemplateEngineException - if there is a problem with the 
 *                                   TemplateEngine
 */  
  public String rendered(JSDynamicTree tree)
                throws TemplateEngineException, IOException {
    StringWriter sw = new StringWriter();
    render(tree,sw);
    return sw.toString();
  }

/**
 * Render an Tree Object in a MarkupLanguage specific way, rendering to 
 * melati.getWriter()
 *
 * @param tree - the Tree to be rendered
 * @throws IOException - if there is a problem during rendering
 * @throws TemplateEngineException - if there is a problem with the 
 *                                   TemplateEngine
 */  
  public void render(JSDynamicTree tree) 
              throws TemplateEngineException, IOException {
    render(tree, melati.getWriter());
  }


/**
 * Render an Tree Object in a MarkupLanguage specific way, rendering to 
 * a suplier Wrtier
 *
 * @param tree - the Tree to be rendered
 * @param writer - the Writer to render this Object to
 * @throws IOException - if there is a problem during rendering
 * @throws TemplateEngineException - if there is a problem with the 
 *                                   TemplateEngine
 */  
  public void render(JSDynamicTree tree, Writer writer)
              throws TemplateEngineException, IOException {
    TemplateContext vars = melati.getTemplateEngine().getTemplateContext(melati);
    vars.put("tree",tree);
    vars.put("melati", melati);
    String templetName = "org.melati.util.JSDynamicTree";
    try {
      expandedTemplet(
            templetLoader.templet(melati.getTemplateEngine(), this, templetName)
            ,vars , writer);
    } catch (NotFoundException e) {
      throw new TemplateEngineException(e);
    }
  }

/**
 * Render a Field Object in a MarkupLanguage specific way, returning a String
 *
 * @return - the Field rendered as a String in a MarkupLanguage specific way.
 * @param field - the Field to be rendered
 * @param style - a style to format this Field.  
 * @see org.melati.poem.DatePoemType._stringOfCooked()
 * @param limit - the lenght to trim the rendered string to
 * @throws IOException - if there is a problem during rendering
 * @throws TemplateEngineException - if there is a problem with the 
 *                                   TemplateEngine
 */  
  public String rendered(Field field, int style, int limit)
                throws TemplateEngineException, IOException {
    StringWriter sw = new StringWriter();
    render(field, style, limit, sw);
    return sw.toString();
  }

/**
 * Render a Field Object in a MarkupLanguage specific way, rendering to 
 * melati.getWriter()
 *
 * @param field - the Field to be rendered
 * @param style - a style to format this Field.  
 * @see org.melati.poem.DatePoemType._stringOfCooked()
 * @param limit - the lenght to trim the rendered string to
 * @throws IOException - if there is a problem during rendering
 * @throws TemplateEngineException - if there is a problem with the 
 *                                   TemplateEngine
 */  
  public void render(Field field, int style, int limit)
              throws TemplateEngineException, IOException {
    render(field, style, limit, melati.getWriter());
  }

/**
 * Render a Field Object in a MarkupLanguage specific way, rendering a supplied
 * Writer
 *
 * @param field - the Field to be rendered
 * @param style - a style to format this Field.  
 * @see org.melati.poem.DatePoemType._stringOfCooked()
 * @param limit - the lenght to trim the rendered string to
 * @param writer - the Writer to render this Object to
 * @throws IOException - if there is a problem during rendering
 * @throws TemplateEngineException - if there is a problem with the 
 *                                   TemplateEngine
 */  
  public void render(Field field, int style, int limit, Writer writer)
              throws TemplateEngineException, IOException {
    render(field.getCookedString(locale, style), limit, writer);
  }

  public String rendered(Field field, int style)
                throws TemplateEngineException, IOException {
    StringWriter sw = new StringWriter();
    render(field, style, sw);
    return sw.toString();
  }

  public void render(Field field, int style)
              throws TemplateEngineException, IOException {
    render(field, style, melati.getWriter());
  }

  public void render(Field field, int style, Writer writer)
              throws TemplateEngineException, IOException {
    render(field, style, 10000000, writer);
  }

  public String renderedShort(Field field)
                throws TemplateEngineException, IOException {
    StringWriter sw = new StringWriter();
    renderShort(field, sw);
    return sw.toString();
  }

  public void renderShort(Field field)
              throws TemplateEngineException, IOException {
    renderShort(field, melati.getWriter());
  }

  public void renderShort(Field field, Writer writer)
                throws TemplateEngineException, IOException {
    render(field, DateFormat.SHORT, writer);
  }


  public String renderedMedium(Field field)
                throws TemplateEngineException, IOException {
    StringWriter sw = new StringWriter();
    renderMedium(field, sw);
    return sw.toString();
  }

  public void renderMedium(Field field)
                throws TemplateEngineException, IOException {
    renderMedium(field, melati.getWriter());
  }

  public void renderMedium(Field field, Writer writer)
                throws TemplateEngineException, IOException {
    render(field, DateFormat.MEDIUM, writer);
  }

  public String renderedLong(Field field)
                throws TemplateEngineException, IOException {
    StringWriter sw = new StringWriter();
    renderLong(field, sw);
    return sw.toString();
  }

  public void renderLong(Field field)
              throws TemplateEngineException, IOException {
    renderLong(field, melati.getWriter());
  }

  public void renderLong(Field field, Writer writer)
              throws TemplateEngineException, IOException {
    render(field, DateFormat.LONG, writer);
  }



  public String renderedFull(Field field)
                throws TemplateEngineException, IOException {
    StringWriter sw = new StringWriter();
    renderFull(field, sw);
    return sw.toString();
  }

  public void renderFull(Field field)
              throws TemplateEngineException, IOException {
    renderFull(field, melati.getWriter());
  }

  public void renderFull(Field field, Writer writer)
              throws TemplateEngineException, IOException {
    render(field, DateFormat.FULL, writer);
  }



  public String rendered(Field field)
                throws TemplateEngineException, IOException {
    StringWriter sw = new StringWriter();
    render(field, sw);
    return sw.toString();
  }

  public void render(Field field)
                throws TemplateEngineException, IOException {
    render(field, melati.getWriter());
  }

  public void render(Field field, Writer writer)
                throws TemplateEngineException, IOException {
    renderMedium(field, writer);
  }

  public String renderedStart(Field field)
                throws TemplateEngineException, IOException {
    StringWriter sw = new StringWriter();
    renderStart(field, sw);
    return sw.toString();
  }

  public void renderStart(Field field)
                throws TemplateEngineException, IOException {
    renderStart(field, melati.getWriter());
  }

  public void renderStart(Field field, Writer writer)
                throws TemplateEngineException, IOException {
    render(field, DateFormat.MEDIUM, 50, writer);
  }

  public String rendered(Exception e) throws IOException {
    StringWriter sw = new StringWriter();
    render(e, sw);
    return sw.toString();
  }

  public void render(Exception e) throws IOException {
    render(e, melati.getWriter());
  }

  public void render(Exception e, Writer writer)
                throws IOException {
    try {
      TemplateContext vars =
      melati.getTemplateEngine().getTemplateContext(melati);
      Template templet =
      templetLoader.templet(melati.getTemplateEngine(), this, e.getClass());
      vars.put("exception", e);
      vars.put("melati", melati);
      vars.put("ml", this);
      expandedTemplet(templet, vars, writer);
    }
    catch (Exception f) {
      try {
        System.err.println("MarkupLanguage failed to render an exception:");
        f.printStackTrace();
        writer.write("[");
        render(e.toString(),writer);
        writer.write("]");
      }
      catch (Exception g) {
        writer.write("[UNRENDERABLE EXCEPTION!]");
      }
    }
  }



  //
  // =========
  //  Widgets
  // =========
  //
   public String input(Field field)
                throws TemplateEngineException, 
                       IOException, 
                       UnsupportedTypeException {
    return input(field, null, "", false);
  }

  public String inputAs(Field field, String templetName)
                throws TemplateEngineException, 
                       IOException, 
                       UnsupportedTypeException {
    return input(field, templetName, "", false);
  }

  public String searchInput(Field field, String nullValue)
                throws TemplateEngineException, 
                       IOException, 
                       UnsupportedTypeException {
    return input(field, null, nullValue, true);
  }

  protected String input(Field field, 
                         String templetName,
                         String nullValue, 
                         boolean overrideNullable)
                   throws UnsupportedTypeException, 
                          TemplateEngineException, 
                          IOException {
    Template templet;
    try {
      templet =
      templetName == null ?
      templetLoader.templet(melati.getTemplateEngine(), this, field) :
      templetLoader.templet(melati.getTemplateEngine(), this, templetName);
    } catch (NotFoundException e) {
      throw new TemplateEngineException(e);
    }

    TemplateContext vars =
    melati.getTemplateEngine().getTemplateContext(melati);

    if (overrideNullable) {
      field = field.withNullable(true);
      vars.put("nullValue", nullValue);
    }

    vars.put("field", field);
    vars.put("ml", this);
    vars.put("melati", melati);
    expandedTemplet(templet, vars, melati.getWriter());
    return "";
  }

  public Template templet(String templetName) throws NotFoundException {
    return templetLoader.templet(melati.getTemplateEngine(), this, templetName);
  }

  protected void expandedTemplet(Template templet, TemplateContext tc, Writer writer)
                   throws TemplateEngineException, IOException {
    melati.getTemplateEngine().expandTemplate(writer,templet,tc);
  }
}
