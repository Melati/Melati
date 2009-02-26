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
package org.melati.template;

import java.io.IOException;
import java.util.Enumeration;

import org.melati.Melati;
import org.melati.MelatiConfig;
import org.melati.util.MelatiStringWriter;
import org.melati.util.MelatiWriter;

/**
 * A TemplateEngine typically evaluates a template containing variables 
 * against a context containing values for those variables.
 * 
 * The canonical java Template Engines are WebMacro and Velocity.
 * 
 * @author timp At paneris.org
 */
public interface TemplateEngine {
  
  /**
   * Initialise the Engine.
   *
   * @param melatiConfig a {@link MelatiConfig}
   * @throws TemplateEngineException if any problem occurs with the engine
   */
  void init(MelatiConfig melatiConfig) throws TemplateEngineException;

  /**
   * Create a new Context for this engine.
   *
   * @param melati the {@link Melati}
   * @throws TemplateEngineException if any problem occurs with the engine
   * @return a {@link TemplateContext}
   */
  TemplateContext getTemplateContext(Melati melati)
      throws TemplateEngineException;

  /**
   * The name of the template engine (used to find the templets).
   * @return the name of the current configured template engine
   */
  String getName();

  /**
   * @return the extension of the templates used by this template engine,
   * including the dot.
   */
  String templateExtension();

  /**
   * A root should not end in a slash.
   * 
   * @return an Enumeration of string roots, always at least the empty string
   */
  Enumeration getRoots();
  
  /**
   * Add a template root directory.
   * NOTE A root should not start or end in a slash.
   * 
   * @param root the root to add
   */
  void addRoot(String root);
  
  /** 
   * Get a template given it's full name.
   * 
   * @param templateName the name of the template to find
   * @throws IOException if TemplateEngine does
   * @throws NotFoundException if template not found
   * @return a template
   */
  Template template(String templateName) 
    throws IOException, NotFoundException;

  /**
   * The name of a template which exists.
   * 
   * @param key short name, without path or extension
   * @param classifier a purpose or database name or similar qualifier 
   * @return the name of a template, null if none found
   */
  String getTemplateName(String key, String classifier);
   
  /** 
   * Expand the Template against the context.
   *
   * @param out             a {@link MelatiWriter} to output on
   * @param templateName    the name of the template to expand
   * @param templateContext the {@link ServletTemplateContext} to expand 
   *                        the template against
   * @throws IOException if TemplateEngine does
   * @throws NotFoundException if template not found
   */
  void expandTemplate(MelatiWriter out, String templateName,
      TemplateContext templateContext) throws IOException, NotFoundException;

  /** 
   * Expand the Template against the context, unwrapping any Access Exceptions.
   *
   * @param out             a {@link MelatiWriter} to output on
   * @param template        the {@link Template} to expand
   * @param templateContext the {@link ServletTemplateContext} to expand 
   *                        the template against
   * @throws IOException if TemplateEngine does
   */
  void expandTemplate(MelatiWriter out, Template template,
      TemplateContext templateContext) throws IOException;

  /** 
   * Expand the Template against the context and return the expansion as a string.
   *
   * @param template        the {@link Template} to expand
   * @param templateContext the {@link ServletTemplateContext} to expand 
   *                        the template against
   * @return the interpolated template as a String
   * @throws IOException if TemplateEngine does
   */
  String expandedTemplate(Template template, TemplateContext templateContext)
      throws IOException;

  /** 
   * @return a {@link MelatiStringWriter}.
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

