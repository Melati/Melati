/*
 * $Source$
 * $Revision$
 *
 * Copyright (C) 2006 Tim Pizey
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
 *     Tim Pizey <timp At paneris.org>
 *     http://paneris.org/~timp
 */

package org.melati.template;

import java.io.IOException;
import java.text.DateFormat;

import org.melati.Melati;
import org.melati.poem.Field;
import org.melati.poem.PoemLocale;
import org.melati.util.MelatiStringWriter;
import org.melati.util.MelatiWriter;

/**
 * MarkupLanguage provides a variety of methods for rendering objects in a
 * template.  
 *
 * Each object to be rendered has 3 methods:
 * 1 - String rendered(Object o) - this will render the object to a String
 * 2 - void render(Object o) - renders the object to melati.getWriter()
 * 3 - void render(Object o, MelatiWriter w) - render the object to w.
 *
 * When this class was written it was thought that for maximum 
 * efficiency one should render the object direct to the output stream using
 * method (2) above.  
 * However now all but (1) is deprecated. 
 */

public abstract class AbstractMarkupLanguage implements MarkupLanguage {

  protected TempletLoader templetLoader = null;
  protected Melati melati = null;
  protected PoemLocale locale = null;

  /** The maximum number of field possibilites to render.  */
  public static final int FIELD_POSSIBILITIES_LIMIT = 10000;

  private String name;

  /**
   * Construct a Markup Language object.
   *
   * @param name - the name associated with this markup language.
   *    This is used to determine where to load
   *    templates from ie 'html' templates are
   *    found in the 'html' directory.
   * @param melati - the melati currently in use
   * @param templetLoader - the template loader in use
   *       (taken from org.melati.MelatiConfig.properties)
   * @param locale - the locale in use
   *    (taken from org.melati.MelatiConfig.properties)
   */
  public AbstractMarkupLanguage(String name,
                        Melati melati,
                        TempletLoader templetLoader,
                        PoemLocale locale) {
    this.name = name;
    this.melati = melati;
    this.templetLoader = templetLoader;
    this.locale = locale;
  }

  /**
   * Construct a new MarkupLanguage given a new name and an
   * existing MarkupLanguage.
   *
   * @param name - the name of the new MarkupLanguage
   * @param other - the Markup Language to base this one upon
   */
  protected AbstractMarkupLanguage(String name, AbstractMarkupLanguage other) {
    this(name, other.melati, other.templetLoader, other.locale);
  }

  /**
   * {@inheritDoc}
   * @see org.melati.template.MarkupLanguage#getName()
   */
  public String getName() {
    return name;
  }

  /**
   * Name and locale.
   * {@inheritDoc}
   * @see java.lang.Object#toString()
   */
  public String toString() {
    return getName() + "/" + locale.toString();
  }
  
  private MelatiStringWriter getStringWriter() {
    return (MelatiStringWriter)melati.getStringWriter();
  }

  /**
   * {@inheritDoc}
   * @see org.melati.template.MarkupLanguage#rendered(java.lang.String, int)
   */
  public String rendered(String s, int limit) throws IOException {
    MelatiStringWriter sw = getStringWriter();
    render(s,limit,sw);
    return sw.toString();
  }

  /**
   * {@inheritDoc}
   * @see org.melati.template.MarkupLanguage#rendered(org.melati.poem.Field, int, int)
   */
  public String rendered(Field field, int style, int limit)
      throws TemplateEngineException, IOException {
    MelatiStringWriter sw = getStringWriter();
    render(field, style, limit, sw);
    return sw.toString();
  }

  /**
   * {@inheritDoc}
   * @see org.melati.template.MarkupLanguage#rendered(org.melati.poem.Field, int)
   */
  public String rendered(Field field, int style)
      throws TemplateEngineException, IOException {
    MelatiStringWriter sw = getStringWriter();
    render(field, style, FIELD_POSSIBILITIES_LIMIT, sw);
    return sw.toString();
  }

  /**
   * {@inheritDoc}
   * @see org.melati.template.MarkupLanguage#rendered(java.lang.Object)
   */
  public String rendered(Object o)
    throws IOException {
    MelatiStringWriter sw = getStringWriter();
    if (o instanceof String)
      render((String)o, sw);
    else if (o instanceof Field) 
      render((Field)o, sw);
    else
      render(o, sw);
    return sw.toString();
  }

  /**
   * {@inheritDoc}
   * @see org.melati.template.MarkupLanguage#renderedMarkup(java.lang.String)
   */
  public String renderedMarkup(String s)  
      throws IOException {
    MelatiStringWriter sw = getStringWriter();
    renderMarkup(s, sw);
    return sw.toString();    
  }

  /**
   * Render a String in a MarkupLanguage specific way, limiting it's length.
   * Render to a supplied MelatiWriter.
   *
   * @param s - the string to be rendered
   * @param writer - the MelatiWriter to render this String to
   * @param limit - the lenght to trim the string to
   * @throws IOException - if there is a problem during rendering
   */
  protected void render(String s, int limit, MelatiWriter writer)
      throws IOException {
    render(s.length() < limit + 3 ? s : s.substring(0, limit) + "...", writer);
  }

  /**
   * Render a String in a MarkupLanguage specific way
   * to a supplied MelatiWriter.
   *
   * @param s - the string to be rendered
   * @param writer - the MelatiWriter to render this String to
   * @throws IOException - if there is a problem during rendering
   */
  protected abstract void render(String s, MelatiWriter writer) throws IOException;
  
  /**
   * Render a markup fragment in a MarkupLanguage specific way
   * to a supplied MelatiWriter.
   *
   * @param s - the fragment to be rendered
   * @param writer - the MelatiWriter to render this String to
   * @throws IOException - if there is a problem during rendering
   */
  protected abstract void renderMarkup(String s, MelatiWriter writer) throws IOException;

  /**
   * Render a Field Object in a MarkupLanguage specific way, 
   * rendering to supplied MelatiWriter.
   *
   * @param field - the Field to be rendered
   * @param writer - the MelatiWriter to render this Object to
   * @throws IOException - if there is a problem during rendering
   */
  protected void render(Field field, MelatiWriter writer) throws IOException {
    render(field, DateFormat.MEDIUM, FIELD_POSSIBILITIES_LIMIT, writer);
  }

  /**
   * Render a Field Object in a MarkupLanguage specific way, 
   * rendering to supplied MelatiWriter.
   *
   * @param field - the Field to be rendered
   * @param style - a style to format this Field.
   * @see org.melati.poem.DatePoemType#stringOfCooked
   *              (java.lang.Object,org.melati.poem.PoemLocale, int)
   * @param limit - the length to trim the rendered string to
   * @param writer - the MelatiWriter to render this Object to
   * @throws IOException - if there is a problem during rendering
   */
  protected void render(Field field, int style, int limit, MelatiWriter writer)
      throws IOException {
    render(field.getCookedString(locale, style), limit, writer);
  }


  /**
   * {@inheritDoc}
   * @see org.melati.template.MarkupLanguage#renderedStart(org.melati.poem.Field)
   */
  public String renderedStart(Field field)
      throws IOException {
    MelatiStringWriter sw = getStringWriter();
    renderStart(field, sw);
    return sw.toString();
  }


  protected void renderStart(Field field, MelatiWriter writer)
      throws IOException {
    render(field, DateFormat.MEDIUM, 50, writer);
  }

  /**
   * Render an Object in a MarkupLanguage specific way, rendering to
   * the <code>MelatiWriter</code> supplied by <code>melati.getWriter()</code>.
   *
   * @param o - the Object to be rendered
   * @throws IOException - if there is a problem during rendering
   * @throws TemplateEngineException - if there is a problem with the
   *                                   ServletTemplateEngine
   */
  protected void render(Object o) throws IOException {
    MelatiWriter writer = melati.getWriter();
    render(o, writer);
  }

  /**
   * Render an Object in a MarkupLanguage specific way, rendering to
   * a supplied Writer.
   *
   * NOTE The context always contains objects with the names melati, object and  ml  
   *
   * @param o - the Object to be rendered
   * @param writer - the MelatiWriter to render this Object to
   */
  protected void render(Object o, MelatiWriter writer) throws IOException {
    if (o == null)
      throw new NullPointerException();
    else {
        TemplateContext vars =
          melati.getTemplateEngine().getTemplateContext(melati);
        Template templet =
          templetLoader.templet(melati.getTemplateEngine(), this, o.getClass());
        vars.put("object", o);
        // Not happy but 
        if (o instanceof Field) vars.put("field", o);
        vars.put("melati", melati);
        vars.put("ml", melati.getMarkupLanguage());
        expandTemplet(templet, vars, writer);
    }
  }


  //
  // =========
  //  Widgets
  // =========
  //
  
  /**
   * {@inheritDoc}
   * @see org.melati.template.MarkupLanguage#input(org.melati.poem.Field)
   */
  public String input(Field field)
      throws TemplateEngineException,
             IOException, NotFoundException {
    return input(field, null, "", false);
  }

  /**
   * {@inheritDoc}
   * @see org.melati.template.MarkupLanguage#inputAs(org.melati.poem.Field, java.lang.String)
   */
  public String inputAs(Field field, String templetName)
      throws TemplateEngineException,
             IOException, NotFoundException {
    return input(field, templetName, "", false);
  }

  /**
   * {@inheritDoc}
   * @see org.melati.template.MarkupLanguage#searchInput(org.melati.poem.Field, java.lang.String)
   */
  public String searchInput(Field field, String nullValue)
      throws TemplateEngineException,
             IOException, NotFoundException{
    return input(field, null, nullValue, true);
  }

  protected String input(Field field,
                         String templetName,
                         String nullValue,
                         boolean overrideNullable)
       throws IOException, NotFoundException {

    Template templet;
    templet =
      templetName == null ?
        templetLoader.templet(melati.getTemplateEngine(), this, field) :
        templetLoader.templet(melati.getTemplateEngine(), this, templetName);

    TemplateContext vars =
        melati.getTemplateEngine().getTemplateContext(melati);

    if (overrideNullable) {
      field = field.withNullable(true);
      vars.put("nullValue", nullValue);
    }

    vars.put("melati", melati);
    vars.put("ml", melati.getMarkupLanguage());
    vars.put("object", field);
    vars.put("field", field);
    MelatiStringWriter sw = getStringWriter();
    melati.getTemplateEngine().expandTemplate(sw, templet,vars);
    
    return sw.toString(); 
  }

  
  /**
   * Interpolate a templet and write it out.
   * 
   * @param templet {@link Template} to interpolate
   * @param tc {@link TemplateContext} against which to instantiate variables
   * @param out {@link MelatiWriter} to write results to 
   * @throws TemplateEngineException if something unexpected happens
   */
  protected void expandTemplet(Template templet, TemplateContext tc,
                               MelatiWriter out) throws IOException {
    melati.getTemplateEngine().expandTemplate(out, templet, tc);
  }
}


