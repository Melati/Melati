/*
 * $Source$
 * $Revision$
 *
 * Copyright (C) 2001 Tim Joyce
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
 *     Tim Joyce <timj@paneris.org>
 */

package org.melati.template.webmacro;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;

import org.melati.Melati;
import org.melati.MelatiConfig;
import org.melati.poem.AccessPoemException;
import org.melati.template.NotFoundException;
import org.melati.template.TemplateContext;
import org.melati.template.ServletTemplateContext;
import org.melati.template.TemplateEngine;
import org.melati.template.TemplateEngineException;
import org.melati.util.MelatiStringWriter;
import org.melati.util.MelatiWriter;
import org.melati.util.StringUtils;
import org.webmacro.InitException;
import org.webmacro.PropertyException;
import org.webmacro.WM;
import org.webmacro.servlet.WebContext;


/**
 * Wrapper for the WebMacro Template Engine for use with Melati.
 */
public class WebmacroTemplateEngine implements TemplateEngine {


//  public static final Object check =
//  org.webmacro.engine.Variable.
//       youNeedToBeUsingAVersionOfVariableHackedForMelati;

  /** The WebMacro. */
  public WM wm;
  //private WebContext _webContext;

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
   * Construct a new Engine for use in a servlet environment.
   *
   * @see org.melati.servlet.TemplateServlet
   * @param melatiConfig a {@link MelatiConfig}
   * @param servlet the servlet we are within
   * @throws TemplateEngineException if any problem occurs with the engine
   */
  public void init(MelatiConfig melatiConfig, HttpServlet servlet) 
      throws TemplateEngineException {
    try {
      wm = new WM (servlet);
    } catch (InitException e) {
      throw new TemplateEngineException(e);
    }
  }

  /**
   * Get the generic parameters for WebMacro.
   *
   * @param melati the {@link Melati}
   * @return a {@link ServletTemplateContext}
   */
  public TemplateContext getTemplateContext(Melati melati) {
    WebContext webContext = new WebContext(wm.getBroker(),
                                melati.getRequest(),
                                melati.getResponse());
    return new WebmacroTemplateContext(webContext);
  }
  
  /**
   * The name of the template engine (used to find the templets).
   * @return the name of the current configured template engine
   */
  public String getName () {
    return "webmacro";
  }

  /**
   * @return the extension of the templates used by this template engine
   */
  public String templateExtension() {
    return ".wm";
  }

  /** 
   * Get a template given it's name.
   * 
   * @param templateName the name of the template to find
   * @throws NotFoundException if the template is not found by the engine
   * @return a template
   */
  public org.melati.template.Template template(String templateName)
      throws TemplateEngineException {
    try {                                  
      org.webmacro.Template template = wm.getTemplate (templateName);
      return new WebmacroTemplate (template);
    } catch (org.webmacro.NotFoundException e) {
      throw new NotFoundException("Could not find template " + templateName);
    } catch (Exception e) {
      throw new TemplateEngineException(e);
    }
  }

  /** 
   * Get a template for a given class.
   *
   * @param clazz the class name to translate into a template name 
   * @throws NotFoundException if the template is not found by the engine
   * @return a template
   */
  public org.melati.template.Template template(Class clazz)
      throws TemplateEngineException {

    // Note we don't use File.separator but hardcode "/" 
    // as templates canmnot be found within jar files otherwise
    String templateName = StringUtils.tr(clazz.getName(),
                                         ".", "/")
                          + templateExtension();
    return template(templateName);
  }

  /** 
   * Expand the Template against the context.
   *
   * @param out             a {@link MelatiWriter} to output on
   * @param templateName    the name of the template to expand
   * @param templateContext the {@link ServletTemplateContext} to expand 
   *                        the template against
   * @throws TemplateEngineException if any problem occurs with the engine
   */
  public void expandTemplate(MelatiWriter out, 
                             String templateName, 
                             TemplateContext templateContext)
      throws TemplateEngineException {
    try {
      expandTemplate (out, template (templateName), templateContext);
    } catch (NotFoundException e) {
      throw e;
    } catch (Exception e2) {
      throw new TemplateEngineException(e2);
    }
  }

  /**
   * Expand the Template against the context.
   *
   * @param out             a {@link MelatiWriter} to output on
   * @param template        the {@link org.melati.template.Template} to expand
   * @param templateContext the {@link ServletTemplateContext} to expand 
   *                        the template against
   * @throws TemplateEngineException if any problem occurs with the engine
   */
  public void expandTemplate(MelatiWriter out,
                             org.melati.template.Template template, 
                             TemplateContext templateContext)
              throws TemplateEngineException {
    try {
      template.write(out, templateContext, this);
    } catch (TemplateEngineException problem) {
      Exception underlying = problem.subException;
      if (underlying instanceof PropertyException) {
        Throwable caught = ((PropertyException)underlying).getCause();
        if (caught instanceof AccessPoemException) {
          throw (AccessPoemException)caught;
        }
      }
      throw problem;
    }
  }

  /**
   * Expand the Template against the context and return it as a string.
   *
   * @param template        the {@link org.melati.template.Template} to expand
   * @param templateContext the {@link ServletTemplateContext} to expand 
   *                        the template against
   * @throws TemplateEngineException if any problem occurs with the engine
   */
  public String expandedTemplate(org.melati.template.Template template,  
                                 TemplateContext templateContext)
      throws TemplateEngineException {
    try {
      MelatiStringWriter s = new MelatiWebmacroStringWriter();
      template.write (s, templateContext, this);
      return s.toString();
    } catch (TemplateEngineException problem) {
      Exception underlying = problem.subException;
      if (underlying instanceof PropertyException) {
        Throwable caught = ((PropertyException)underlying).getCause();
        if (caught instanceof AccessPoemException) {
          throw (AccessPoemException)caught;
        }
      }
      throw problem;
    }
  }

  /** 
   * Get a variable exception handler for use if there is 
   * a problem accessing a variable.
   *
   * @return a <code>PassbackVariableExceptionHandler</code> 
   *         appropriate for this engine.
   */
  public Object getPassbackVariableExceptionHandler() {
    return new PassbackEvaluationExceptionHandler();
  }

  /** 
   * @param response the <code>HttpServletResponse</code> that this 
   *                 writer will be part of
   * @param buffered whether the writer should be buffered
   * @throws IOException if there is a problem with the filesystem.
   * @return a {@link MelatiWriter} 
   *         appropriate for this engine.
   */
  public MelatiWriter getServletWriter(HttpServletResponse response, 
                                       boolean buffered) 
      throws IOException {
    if (buffered) {
//      return new MelatiBufferedFastWriter(response);
      return new MelatiBufferedFastWriter(wm.getBroker(),response);
    } else {
//      return new MelatiFastWriter(response);
      return new MelatiFastWriter(wm.getBroker(),response);
    }
  }

  /** 
   * @param encoding ignored
   * @return a {@link MelatiWriter} for this engine.
   * @deprecated Use {@link #getStringWriter()}.
   * @todo Delete this method. Suggest 2004.
   */
  public final MelatiWriter getStringWriter(String encoding) {
    return getStringWriter();
  }

  /** 
   * Return a {@link MelatiWebmacroStringWriter}.
   *
   * @see Melati#getStringWriter() 
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
