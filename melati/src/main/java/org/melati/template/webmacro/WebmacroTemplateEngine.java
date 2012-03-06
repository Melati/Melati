/*
 * $Source$
 * $Revision$
 *
 * Copyright (C) 2005 Tim Pizey
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

package org.melati.template.webmacro;


import org.melati.Melati;
import org.melati.MelatiConfig;
import org.melati.poem.AccessPoemException;
import org.melati.template.AbstractTemplateEngine;
import org.melati.template.NotFoundException;
import org.melati.template.TemplateContext;
import org.melati.template.TemplateEngine;
import org.melati.template.TemplateEngineException;
import org.melati.util.MelatiStringWriter;
import org.melati.util.MelatiWriter;
import org.webmacro.InitException;
import org.webmacro.PropertyException;
import org.webmacro.WM;
import org.webmacro.Context;


/**
 * Wrapper for the WebMacro Template Engine for use with Melati.
 */
public class WebmacroTemplateEngine extends AbstractTemplateEngine implements TemplateEngine {

  /** The name of the engine. */
  public static final String NAME = "webmacro";

  /** The WebMacro. */
  public WM wm;
  //private WebContext _webContext;

  /**
   * Constructor.
   */
  public WebmacroTemplateEngine() {
    super();
  }
  
  /**
   * Construct a new Engine.
   *
   * @param melatiConfig a {@link MelatiConfig}
   * @throws TemplateEngineException if any problem occurs with the engine
   */
  public void init(MelatiConfig melatiConfig) 
      throws TemplateEngineException {
    try {
      wm = new WM ();
    } catch (InitException e) {
      throw new TemplateEngineException(e);
    }
  }

  /**
   * Create a new, empty, Context for WebMacro.
   *
   * @param melati the {@link Melati}
   * @return a {@link TemplateContext}
   */
  public TemplateContext getTemplateContext(Melati melati) {
    Context context = new Context(wm.getBroker());
    return new WebmacroTemplateContext(context);
  }
  
  /**
   * The name of the template engine (used to find the templets).
   * @return the name of the current configured template engine
   */
  public String getName () {
    return NAME;
  }

  /**
   * @return the extension of the templates used by 
   * WebMacro, including the dot. 
   * 
   */
  public String templateExtension() {
    return ".wm";
  }

  /** 
   * Get a template given it's name.
   * 
   * @param templateName the name of the template to find
   * @return a template
   * @throws NotFoundException if template not found
   */
  public org.melati.template.Template template(String templateName)
      throws NotFoundException {
    try {                                  
      org.webmacro.Template template = wm.getTemplate(templateName);
      return new WebmacroTemplate(template);
    } catch (org.webmacro.NotFoundException e) {
      throw new NotFoundException("Could not find template " + templateName);
    } catch (Exception e) {
      throw new TemplateEngineException(e);
    }
  }

  /** 
   * Expand the Template against the context.
   *
   * @param out             a {@link MelatiWriter} to output on
   * @param templateName    the name of the template to expand
   * @param templateContext the {@link TemplateContext} to expand 
   *                        the template against
   * @throws NotFoundException if template not found
   */
  public void expandTemplate(MelatiWriter out, 
                             String templateName, 
                             TemplateContext templateContext)
      throws NotFoundException {
    expandTemplate (out, template(templateName), templateContext);
  }

  /**
   * Expand the Template against the context.
   *
   * @param out             a {@link MelatiWriter} to output on
   * @param template        the {@link org.melati.template.Template} to expand
   * @param templateContext the {@link TemplateContext} to expand 
   *                        the template against
   */
  public void expandTemplate(MelatiWriter out,
                             org.melati.template.Template template, 
                             TemplateContext templateContext) {
    try {
      template.write(out, templateContext);
    } catch (TemplateEngineException problem) {
      // underlying will always be a PropertyException
      PropertyException underlying = (PropertyException)problem.subException;
      Throwable caught = underlying.getCause();
      if (caught instanceof AccessPoemException) {
        throw (AccessPoemException)caught;
      }
      throw problem;
    }
  }

  /**
   * {@inheritDoc}
   * @see org.melati.template.AbstractTemplateEngine#expandedTemplate
   */
  public String expandedTemplate(org.melati.template.Template template,  
                                 TemplateContext templateContext) {
      MelatiStringWriter s = new MelatiWebmacroStringWriter();
      expandTemplate(s, template, templateContext);
      return s.toString();
  }

  /** 
   * Return a {@link MelatiWebmacroStringWriter}.
   *
   * {@inheritDoc}
   * @see org.melati.Melati#getStringWriter() 
   * @see org.melati.template.AbstractTemplateEngine#getStringWriter()
   */
  public MelatiStringWriter getStringWriter() {
    return new MelatiWebmacroStringWriter();
  }

  /**
   * Get the underlying engine.
   *
   * @return the configured template engine
   */
  public Object getEngine() {
    return wm;
  }

}
