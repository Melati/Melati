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

package org.melati;

import java.text.DateFormat;

import java.io.IOException;

// fixme - these should not be here! - need to use a newer WebMacro
import org.webmacro.engine.VariableExceptionHandler;
import org.webmacro.engine.Variable;

import org.melati.template.TemplateContext;
import org.melati.template.TemplateEngineException;
import org.melati.template.NotFoundException;
import org.melati.template.TempletLoader;
import org.melati.util.MelatiLocale;
import org.melati.util.JSDynamicTree;
import org.melati.poem.Persistent;
import org.melati.poem.Field;
import org.melati.poem.AccessPoemException;

public abstract class MarkupLanguage {

  private String name;
  protected MelatiContext melatiContext;
  private TemplateContext templateContext;
  private TempletLoader templetLoader;
  private MelatiLocale locale;
  private Melati melati;

  public MarkupLanguage(String name, MelatiContext melatiContext,
  TempletLoader templetLoader, MelatiLocale locale) {
    this.name = name;
    this.melatiContext = melatiContext;
    this.templateContext = melatiContext.getTemplateContext();
    this.templetLoader = templetLoader;
    this.locale = locale;
    melati = melatiContext.getMelati();
  }

  protected MarkupLanguage(String name, MarkupLanguage other) {
    this(name, other.melatiContext, other.templetLoader, other.locale);
  }

  public String getName() {
    return name;
  }

  public abstract String rendered(String s) throws IOException;

  public String rendered(String s, int limit) throws IOException {
    return rendered(s.length() < limit + 3 ? s : s.substring(0, limit) + "...");
  }

  public String rendered(Object o)
  throws TemplateEngineException, IOException {
    if (o instanceof JSDynamicTree)
    return rendered((JSDynamicTree)o);
    if (o instanceof Persistent)
    return rendered(((Persistent)o).displayString(locale, DateFormat.MEDIUM));
    if (o instanceof Exception)
    return rendered((Exception)o);
    return rendered(o.toString());
  }

  public String rendered(JSDynamicTree tree)
  throws TemplateEngineException, IOException {
    TemplateContext vars = melatiContext.getTemplateEngine().getTemplateContext(melatiContext);;
    vars.put("tree",tree);
    return expandedTemplet("org.melati.util.JSDynamicTree",vars);
  }

  public String rendered(Field field, int style, int limit)
  throws TemplateEngineException, IOException {

    try {
      return rendered(field.getCookedString(locale, style), limit);
    }
    catch (AccessPoemException e) {
      VariableExceptionHandler handler =
      (VariableExceptionHandler)templateContext.get(Variable.EXCEPTION_HANDLER);
      if (handler != null)
      return rendered(handler.handle(null, templateContext, e));
      else
      throw e;
    }
  }

  public String rendered(Field field, int style)
  throws TemplateEngineException, IOException {
    return rendered(field, style, 10000000);
  }

  public String renderedShort(Field field)
  throws TemplateEngineException, IOException {
    return rendered(field, DateFormat.SHORT);
  }

  public String renderedMedium(Field field)
  throws TemplateEngineException, IOException {
    return rendered(field, DateFormat.MEDIUM);
  }

  public String renderedLong(Field field)
  throws TemplateEngineException, IOException {
    return rendered(field, DateFormat.LONG);
  }

  public String renderedFull(Field field)
  throws TemplateEngineException, IOException {
    return rendered(field, DateFormat.FULL);
  }

  public String rendered(Field field)
  throws TemplateEngineException, IOException {
    return renderedMedium(field);
  }

  public String renderedStart(Field field)
  throws TemplateEngineException, IOException {
    return rendered(field, DateFormat.MEDIUM, 50);
  }

  /**
   * This is just <TT>rendered</TT> for now, but it is guaranteed
   * always to evaluate to a plain old string suitable for
   * (<I>e.g.</I>) putting in a <TT>&lt;TEXTAREA&gt;</TT>, which
   * <TT>rendered</TT> might not.
   */

  public String renderedString(Field field)  
  throws TemplateEngineException, IOException {
    return rendered(field);
  }

  /*
  public Template templet(String templetName) {
  return templetLoader.templet(templateContext.getBroker(), this, templetName);
  }

  public String templetExpansion(Template template)  {
  return (String)template.evaluate(melaitContext);
  }
   */

  //
  // =========
  //  Widgets
  // =========
  //

  public String input(Field field)
  throws TemplateEngineException, IOException, UnsupportedTypeException {
    return input(field, null, "", false);
  }

  public String inputAs(Field field, String templetName)
  throws TemplateEngineException, IOException, UnsupportedTypeException {
    return input(field, templetName, "", false);
  }

  public String searchInput(Field field, String nullValue)
  throws TemplateEngineException, IOException, UnsupportedTypeException {
    return input(field, null, nullValue, true);
  }

  public Object templet(String templetName) throws NotFoundException {
    return templetLoader.templet(melatiContext.getTemplateEngine(), this, templetName);
  }

  protected String expandedTemplet(Object templet, TemplateContext tc)
  throws TemplateEngineException, IOException {
    melatiContext.getTemplateEngine().expandTemplate(melatiContext.getWriter(),templet,tc);
    return "";
  }

  protected String input(Field field, String templetName,
  String nullValue, boolean overrideNullable)
  throws UnsupportedTypeException, TemplateEngineException, IOException {

    try {
      field.getRaw();
    }
    catch (AccessPoemException e) {
      VariableExceptionHandler handler =
      (VariableExceptionHandler)templateContext.get(Variable.EXCEPTION_HANDLER);
      if (handler != null)
        rendered(handler.handle(null, melatiContext, e));
      else
      throw e;
    }

    Object templet;
    try {
      templet = 
      templetName == null ?
        templetLoader.templet(melatiContext.getTemplateEngine(), this, field) :
        templetLoader.templet(melatiContext.getTemplateEngine(), this, templetName);
    } catch (NotFoundException e) {
      throw new TemplateEngineException("I couldn't find the templet: " + templetName + " because: " +e.toString());
    }


    TemplateContext vars = melatiContext.getTemplateEngine().getTemplateContext(melatiContext);

    if (overrideNullable) {
      field = field.withNullable(true);
      vars.put("nullValue", nullValue);
    }

    vars.put("field", field);
    vars.put("ml", this);
    vars.put("melati", melatiContext);

    return expandedTemplet(templet, vars);
  }

  public final String rendered(Exception e) throws IOException {
    try {
      TemplateContext vars = melatiContext.getTemplateEngine().getTemplateContext(melatiContext);
      Object templet = templetLoader.templet(melatiContext.getTemplateEngine(), this, e.getClass());
      vars.put("exception", e);
      return expandedTemplet(templet,templateContext);
    }
    catch (Exception f) {
      try {
        System.err.println("MarkupLanguage failed to render an exception:");
        f.printStackTrace();
        melatiContext.getWriter().write("[");
        rendered(e.toString());
        melatiContext.getWriter().write("]");
        return "";
      }
      catch (Exception g) {
        melatiContext.getWriter().write("[UNRENDERABLE EXCEPTION!]");
        return "";
      }
    }
  }
}
