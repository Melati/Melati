/*
 * $Source$
 * $Revision$
 *
 * Copyright (C) 2000 Tim Joyce
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
 *     Tim Joyce <timj At paneris.org>
 *     http://paneris.org/
 *     68 Sandbanks Rd, Poole, Dorset. BH14 8BY. UK
 */

package org.melati.template;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServlet;

import org.melati.Melati;
import org.melati.MelatiConfig;
import org.melati.util.MelatiIOException;
import org.melati.util.MelatiWriter;
import org.melati.util.MelatiSimpleWriter;
import org.melati.util.MelatiStringWriter;
import org.melati.util.MelatiBufferedWriter;


/**
 * The <code>null</code> {@link ServletTemplateEngine}.
 *
 * @author Tim Joyce
 */
public class NoTemplateEngine extends AbstractTemplateEngine implements ServletTemplateEngine {

  private String message =
      "No Template engine is Configured, please specify an engine in " +
      "org.melati.MelatiConfig.properties";

  /**
   * Constructor.
   */
  public NoTemplateEngine() {
    super();
  }

  /**
   * Construct a null Engine.
   *
   * @param config a {@link MelatiConfig}
   */
  public void init(MelatiConfig config) {
    // we don't throw an exception here as it gets hidden away, rather
    // it is better to wait until expandTemplate or getParameters, as
    // that produces a nice
    // exception to the browser
  }

  /**
   * Construct a new Engine for use in a servlet environment.
   *
   * @see org.melati.servlet.TemplateServlet
   * @param melatiConfig a {@link MelatiConfig}
   * @param servlet the servlet we are within
   * @throws TemplateEngineException if any problem occurs with the engine
   */
  public void init(MelatiConfig melatiConfig, HttpServlet servlet) 
      throws TemplateEngineException {
    init(melatiConfig);
  }

  /**
   * Throw exception.
   *
   * @param melati the {@link Melati}
   * @throws TemplateEngineException if any problem occurs with the engine
   * @return a {@link TemplateContext}
   */
  public TemplateContext getTemplateContext(Melati melati)
      throws TemplateEngineException {
    return melati.getTemplateContext();
  }

  /**
   * Throw exception.
   *
   * @param melati the {@link Melati}
   * @throws TemplateEngineException always
   * @return an exception
   */
  public ServletTemplateContext getServletTemplateContext(Melati melati)
      throws TemplateEngineException {
    throw new TemplateEngineException(message);
  }

  /**
   * The name of the template engine (used to find the templets).
   * @return the name of the current configured template engine
   */
  public String getName() {
    return "none";
  }

  /**
   * @return the extension of the templates used by this template engine
   */
  public String templateExtension() {
    return ".none";
  }

  /** 
   * Get a template given its name.
   * 
   * @param templateName the name of the template to find
   * @return a template
   */
  public Template template(String templateName) {
    throw new TemplateEngineException(
        "The template " +
        templateName + " could not be found because you have not configured a template engine.");
  }

  /** 
   * Expand the Template against the context.
   *
   * @param out             a {@link MelatiWriter} to output on
   * @param templateName    the name of the template to expand
   * @param templateContext the {@link ServletTemplateContext} to expand 
   *                        the template against
   * @throws TemplateEngineException if invoked
   */
  public void expandTemplate(MelatiWriter out, String templateName,
                             TemplateContext templateContext)
      throws TemplateEngineException {
    throw new TemplateEngineException(message);
  }


  /** 
   * Expand the Template against the context.
   *
   * @param out             a {@link MelatiWriter} to output on
   * @param melatiTemplate  the {@link Template} to expand
   * @param templateContext the {@link ServletTemplateContext} to expand 
   *                        the template against
   * @throws TemplateEngineException if invoked
   */
  public void expandTemplate(MelatiWriter out, Template melatiTemplate,
                             TemplateContext templateContext)
      throws TemplateEngineException {
    throw new TemplateEngineException(message);
  }

  /** 
   * Expand the Template against the context.
   *
   * @param melatiTemplate  the {@link Template} to expand
   * @param templateContext the {@link ServletTemplateContext} to expand 
   *                        the template against
   * @throws TemplateEngineException if invoked
   * {@inheritDoc}
   * @see org.melati.template.TemplateEngine#expandedTemplate
   * (org.melati.template.Template, org.melati.template.TemplateContext)
   */
  public String expandedTemplate(Template melatiTemplate,
                                 TemplateContext templateContext)
      throws TemplateEngineException {
    throw new TemplateEngineException(message);
  }

  /** 
   * @param response the <code>HttpServletResponse</code> that this 
   *                 writer will be part of
   * @param buffered whether the writer should be buffered
   * @return a {@link MelatiWriter} 
   *         appropriate for this engine.
   */
  public MelatiWriter getServletWriter(HttpServletResponse response, 
                                       boolean buffered) {
    PrintWriter printWriter = null;
    try { 
      printWriter = response.getWriter(); 
    } catch (IOException e) { 
      throw new MelatiIOException(e);
    }
    if (buffered)
      return new MelatiBufferedWriter(printWriter);
    else
      return new MelatiSimpleWriter(printWriter);
  }

  /** 
   * Return a {@link MelatiStringWriter}.
   * {@inheritDoc}
   * @see org.melati.template.TemplateEngine#getStringWriter()
   * @see Melati#getStringWriter() 
   */
  public MelatiStringWriter getStringWriter() {
    return new MelatiStringWriter();
  }

  /** 
   * Get the underlying engine.
   *
   * @return 'none'
   */
  public Object getEngine() {
    return "none";
  }

}
