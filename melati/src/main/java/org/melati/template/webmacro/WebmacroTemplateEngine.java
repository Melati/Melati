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

package org.melati.template.webmacro;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.melati.Melati;
import org.melati.MelatiConfig;
import org.melati.poem.AccessPoemException;
import org.melati.template.TemplateEngine;
import org.melati.template.TemplateContext;
import org.melati.template.TemplateEngineException;
import org.melati.template.NotFoundException;
import org.melati.util.MelatiWriter;

import org.webmacro.WM;
import org.webmacro.InitException;
import org.webmacro.servlet.WebContext;
import org.webmacro.PropertyException;


/**
 * Template engine for use of WebMacro with Melati.
 */
public class WebmacroTemplateEngine implements TemplateEngine {


//  public static final Object check =
//  org.webmacro.engine.Variable.
//       youNeedToBeUsingAVersionOfVariableHackedForMelati;

  // the webmacro
  public WM wm;
  private WebContext _webContext;

  /**
   * Inititialise the Engine
   */
  public void init(MelatiConfig melatiConfig) throws TemplateEngineException {
    try {
      wm = new WM ();
      _webContext = new WebContext(wm.getBroker());
    } catch (InitException e) {
      throw new TemplateEngineException(e);
    }
  }

  /**
   * get the generic parameters for webmacro
   */
  public TemplateContext getTemplateContext(Melati melati) {
    _webContext.clear();
    WebContext wc = _webContext.newInstance(melati.getRequest(),
                                            melati.getResponse());
    return new WebmacroTemplateContext(wc);
  }
  
  public Object getPassbackVariableExceptionHandler() {
    return new PassbackEvaluationExceptionHandler();
  }

  /**
   * the name of the template engine (used to find the templets)
   */
  public String getName () {
    return "webmacro";
  }

  /**
  * the extension of the templates used by this template engine)
  */
  public String templateExtension() {
    return ".wm";
  }

  /**
   * the underlying engine
   */
  public Object getEngine() {
    return wm;
  }

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

  public MelatiWriter getStringWriter(String encoding) 
          throws IOException {
//    return new MelatiBufferedFastWriter(encoding);
    return new MelatiBufferedFastWriter(wm.getBroker(),encoding);
  }

  /**
   * get a template given it's name
   */
  public org.melati.template.Template template(String templateName)
                                      throws NotFoundException {
      try {                                  
        org.webmacro.Template template = wm.getTemplate (templateName);
        return new WebmacroTemplate (template);
      } catch (Exception e) {
        throw new NotFoundException(e);
      }
  }

  /**
   * Expand the Template against the context.
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
   */
  public void expandTemplate(MelatiWriter out,
                             org.melati.template.Template template, 
                             TemplateContext templateContext)
              throws TemplateEngineException {
    try {
      template.write (out, templateContext, this);
    } catch (TemplateEngineException problem) {
      Exception underlying = problem.subException;
      if (underlying instanceof PropertyException) {
        Throwable caught = ((PropertyException)underlying).getCaught();
        if (caught instanceof AccessPoemException) {
          throw (AccessPoemException)caught;
        }
      }
      throw problem;
    }
/*    } catch (VariableException problem) {
      Exception underlying = problem.innermostException();
      if (underlying instanceof AccessPoemException) {
        throw (AccessPoemException)underlying;
      } else {
        throw new TemplateEngineException(underlying);
      }
    }
*/
  }
}
