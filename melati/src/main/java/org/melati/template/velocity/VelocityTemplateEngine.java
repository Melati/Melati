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
 *     Tim Joyce <timj@paneris.org>
 */

package org.melati.template.velocity;

import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.Properties;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServlet;

import org.melati.Melati;
import org.melati.MelatiConfig;
import org.melati.poem.AccessPoemException;
import org.melati.template.TemplateEngine;
import org.melati.template.TemplateContext;
import org.melati.template.ServletTemplateContext;
import org.melati.template.TemplateEngineException;
import org.melati.template.NotFoundException;
import org.melati.util.MelatiStringWriter;
import org.melati.util.MelatiWriter;
import org.melati.util.StringUtils;

import org.apache.velocity.runtime.Runtime;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ResourceNotFoundException;

/**
 * Wrapper for the Velocity Template Engine for use with Melati.
 */
public class VelocityTemplateEngine implements TemplateEngine {

  /**
   * This is the string that is looked for when getInitParameter is
   * called.
   */
//  private static final String INIT_PROPS_KEY = "velocity.properties";

  /**
   * Construct a new Engine.
   *
   * @param melatiConfig a {@link MelatiConfig}
   * @throws TemplateEngineException if any problem occurs with the engine
   */
  public void init(MelatiConfig melatiConfig) throws TemplateEngineException {
    try {
      Properties props = loadConfiguration(melatiConfig);
      Velocity.init(props);
    } catch (Exception e) {
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
    init(melatiConfig);
  }

  protected Properties loadConfiguration(MelatiConfig melatiConfig) 
                               throws IOException, FileNotFoundException {
        
//    String propsFile = config.getInitParameter(INIT_PROPS_KEY);
    /*
     * This will attempt to find the location of the properties
     * file from the relative path to the WAR archive (ie:
     * docroot). Since JServ returns null for getRealPath()
     * because it was never implemented correctly, then we know we
     * will not have an issue with using it this way. I don't know
     * if this will break other servlet engines, but it probably
     * shouldn't since WAR files are the future anyways.
     */
                                     /*
    if (propsFile != null) {
      String realPath = config.getServletContext().getRealPath(propsFile);
      if ( realPath != null ) propsFile = realPath;
      p.load( new FileInputStream(propsFile) );
    }
    // FIXME work out how to set this some other way
 */
    Properties p = new Properties();
    p.setProperty("file.resource.loader.path", 
                  melatiConfig.getTemplatePath());
    p.setProperty("file.resource.loader.class", 
                  "org.melati.template.velocity.WebMacroFileResourceLoader");
    return p;
  }

  /** A special variable that Velocity is expecting, I think.*/
  public static final String FORM = "Form";

  /**
   * Get the template context for Velocity.
   *
   * @param melati the {@link Melati}
   * @return a {@link TemplateContext}
   */
  public TemplateContext getTemplateContext(Melati melati) {
    VelocityContext context = new VelocityContext();
    org.melati.template.velocity.HttpServletRequestWrap req = 
      new org.melati.template.velocity.HttpServletRequestWrap(
            melati.getRequest());
    context.put(VelocityTemplateContext.REQUEST, req);
    context.put(FORM, req);
    context.put(VelocityTemplateContext.RESPONSE, 
                             melati.getResponse());
    return (ServletTemplateContext)new VelocityTemplateContext(context);
  }
  
  /**
   * The name of the template engine (used to find the templets).
   *
   * Note that we have yet to write Velocity specific templates, 
   * so we dynamically convert the WebMacro ones. 
   * Hence this returns 'webmacro'.
   *
   * @return the name of the current configured template engine
   */
  public String getName() {
    return "webmacro";
  }

  /**
   * @return the extension of the templates used by this template engine
   */
  public String templateExtension() {
    return ".vm";
  }

  /** 
   * Get a template given it's name.
   * 
   * @param templateName the name of the template to find
   * @throws NotFoundException if the template is not found by the engine
   * @return a template
   */
  public org.melati.template.Template template(String templateName)
                             throws NotFoundException {
      try {                                  
        return new VelocityTemplate(Runtime.getTemplate(templateName));
      } catch (ResourceNotFoundException e) {
        if (templateName.endsWith(templateExtension())) {
          // have a go at loading the webmacro template, and converting it!
          templateName = templateName.substring(0,templateName.lastIndexOf
                                                (templateExtension())) + ".wm";
          try {                                  
            return new VelocityTemplate(Runtime.getTemplate(templateName));
          } catch (Exception f) {
            throw new NotFoundException(f);
          }
        } else {
          throw new NotFoundException(e);
        }
      } catch (Exception e) {
        throw new NotFoundException(e);
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
      throws NotFoundException {

    // Note File.separator will not find templates in jars
    // so we use forward slash
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
   * @param templateContext the {@link TemplateContext} to expand 
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
      throw new TemplateEngineException(e);
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
      if (underlying instanceof AccessPoemException) {
        throw (AccessPoemException)underlying;
      }
      if (underlying instanceof MethodInvocationException) {
        Throwable caught = ((MethodInvocationException)underlying).
                                getWrappedThrowable();
        if (caught instanceof AccessPoemException) {
          throw (AccessPoemException)caught;
        }
      }
      throw problem;
    }
  }

  /** 
   * Not Implemented. Expand the Template against the context.
   *
   * @param template        the {@link org.melati.template.Template} to expand
   * @param templateContext the {@link ServletTemplateContext} to expand 
   *                        the template against
   * @throws TemplateEngineException if any problem occurs with the engine
   */
  public String expandedTemplate(org.melati.template.Template template, 
                                 TemplateContext templateContext)
              throws TemplateEngineException {
    throw new TemplateEngineException("Not Implemented.");
  }

  /** 
   * Get a variable exception handler for use if there is 
   * a problem accessing a variable.
   *
   * @return a <code>PassbackVariableExceptionHandler</code> 
   *         appropriate for this engine.
   */
  public Object getPassbackVariableExceptionHandler() {
    return new PassbackMethodExceptionEventHandler();
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
      return new MelatiBufferedVelocityWriter(response);
    } else {
      return new MelatiVelocityWriter(response);
    }
  }

  /** 
   * @deprecated Use {@link #getStringWriter()}.
   * @todo Delete this method. Suggest 2004.
   */
  public MelatiWriter getStringWriter(String encoding) {
    return getStringWriter();
  }

  /** 
   * Return a {@link MelatiStringWriter}.
   *
   * @see Melati#getStringWriter() 
   */
  public MelatiStringWriter getStringWriter() {
    return new MelatiStringWriter();
  }

  /** 
   * Get the underlying engine.
   *
   * @return null - for velocity there is none.
   */
  public Object getEngine() {
    return null;
  }

}
