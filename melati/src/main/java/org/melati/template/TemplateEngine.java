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
 *     Tim Pizey <timp@paneris.org>
 *     http://paneris.org/~timp
 */
package org.melati.template;

import java.io.IOException;

import org.melati.Melati;
import org.melati.MelatiConfig;
import org.melati.util.MelatiStringWriter;
import org.melati.util.MelatiWriter;

/**
 * @author tim.pizey
 *
 */
public interface TemplateEngine {
  /**
   * Construct a new Engine.
   *
   * @param melatiConfig a {@link MelatiConfig}
   * @throws TemplateEngineException if any problem occurs with the engine
   */
  void init(MelatiConfig melatiConfig) throws TemplateEngineException;

  /**
   * Get the generic parameters for this engine.
   *
   * @param melati the {@link Melati}
   * @throws TemplateEngineException if any problem occurs with the engine
   * @return a {@link ServletTemplateContext}
   */
  TemplateContext getTemplateContext(Melati melati)
      throws TemplateEngineException;

  /**
   * The name of the template engine (used to find the templets).
   * @return the name of the current configured template engine
   */
  String getName();

  /**
   * @return the extension of the templates used by this template engine
   */
  String templateExtension();

  /** 
   * Get a template given it's name.
   * 
   * @param templateName the name of the template to find
   * @throws NotFoundException if the template is not found by the engine
   * @return a template
   */
  Template template(String templateName) throws TemplateEngineException;

  /** 
   * Get a template for a given class.
   *
   * @param clazz the class name to translate into a template name 
   * @throws NotFoundException if the template is not found by the engine
   * @return a template
   */
  Template template(Class clazz) throws TemplateEngineException;

  /** 
   * Expand the Template against the context.
   *
   * @param out             a {@link MelatiWriter} to output on
   * @param templateName    the name of the template to expand
   * @param templateContext the {@link ServletTemplateContext} to expand 
   *                        the template against
   * @throws TemplateEngineException if any problem occurs with the engine
   */
  void expandTemplate(MelatiWriter out, String templateName,
      TemplateContext templateContext) throws TemplateEngineException;

  /** 
   * Expand the Template against the context.
   *
   * @param out             a {@link MelatiWriter} to output on
   * @param template        the {@link Template} to expand
   * @param templateContext the {@link ServletTemplateContext} to expand 
   *                        the template against
   * @throws TemplateEngineException if any problem occurs with the engine
   */
  void expandTemplate(MelatiWriter out, Template template,
      TemplateContext templateContext) throws TemplateEngineException;

  /** 
   * Expand the Template against the context and return the expansion as a string.
   *
   * @param template        the {@link Template} to expand
   * @param templateContext the {@link ServletTemplateContext} to expand 
   *                        the template against
   * @throws TemplateEngineException if any problem occurs with the engine
   */
  String expandedTemplate(Template template, TemplateContext templateContext)
      throws TemplateEngineException;

  /** 
   * Get a variable exception handler for use if there is 
   * a problem accessing a variable.
   *
   * @return a <code>PassbackVariableExceptionHandler</code> 
   *         appropriate for this engine.
   */
  Object getPassbackVariableExceptionHandler();

  /** 
   * @param encoding Ignored.
   * @return a {@link MelatiStringWriter} for this engine.
   * @deprecated Use {@link #getStringWriter()}.
   * @todo Delete this method. Suggest 2004.
   */
  MelatiWriter getStringWriter(String encoding) throws IOException;

  /** 
   * Return a {@link MelatiStringWriter}.
   *
   * @see Melati#getStringWriter() 
   */
  MelatiStringWriter getStringWriter();

  /** 
   * Get the underlying engine.
   *
   * @return the configured template engine
   */
  Object getEngine();

}

