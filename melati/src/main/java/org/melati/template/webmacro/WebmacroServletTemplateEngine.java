/*
 * $Source$
 * $Revision$
 *
 * Copyright (C) 2004 Tim Pizey
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
 */

package org.melati.template.webmacro;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;

import org.melati.Melati;
import org.melati.MelatiConfig;
import org.melati.template.ServletTemplateContext;
import org.melati.template.ServletTemplateEngine;
import org.melati.template.TemplateEngineException;
import org.melati.template.TemplateIOException;
import org.melati.util.MelatiWriter;
import org.webmacro.InitException;
import org.webmacro.WM;
import org.webmacro.servlet.WebContext;


/**
 * Wrapper for the WebMacro Template Engine for use in a Servlet context.
 */
public class WebmacroServletTemplateEngine 
    extends WebmacroTemplateEngine implements ServletTemplateEngine {

  /**
   * Constructor.
   */
  public WebmacroServletTemplateEngine() {
    super();
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
      wm = new WM(servlet);
    } catch (InitException e) {
      throw new TemplateEngineException(e);
    }
  }

  /**
   * Get the generic parameters for WebMacro, 
   * with servlet objects in the Context.
   *
   * @param melati the {@link Melati}
   * @return a {@link ServletTemplateContext}
   */
  public ServletTemplateContext getServletTemplateContext(Melati melati) {
    WebContext webContext = new WebContext(wm.getBroker(),
                                           melati.getRequest(),
                                           melati.getResponse());
    return new WebmacroServletTemplateContext(webContext);
  }
  
  /** 
   * @param response the <code>HttpServletResponse</code> that this 
   *                 writer will be part of
   * @param buffered whether the writer should be buffered
   * @return a {@link MelatiWriter}
   *         appropriate for this engine.
   */
  public MelatiWriter getServletWriter(HttpServletResponse response, 
                                       boolean buffered)  {
    if (buffered) {
      try {
        return new MelatiBufferedFastWriter(wm.getBroker(),response);
      } catch (IOException e) {
        throw new TemplateIOException(e);
      }
    } else {
      try {
        return new MelatiFastWriter(wm.getBroker(),response);
      } catch (IOException e) {
        throw new TemplateIOException(e);
      }
    }
  }



}
