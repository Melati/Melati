/*
 * $Source$
 * $Revision$
 *
 * Copyright (C) 2007 Tim Pizey
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
 */
package org.melati.template.freemarker;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;

import org.melati.Melati;
import org.melati.MelatiConfig;
import org.melati.template.ServletTemplateContext;
import org.melati.template.ServletTemplateEngine;
import org.melati.template.TemplateEngineException;
import org.melati.template.TemplateIOException;
import org.melati.template.velocity.MelatiBufferedVelocityWriter;
import org.melati.template.velocity.MelatiVelocityWriter;
import org.melati.util.MelatiWriter;

/**
 * @author timp
 * @since 7 Mar 2012
 *
 */
public class FreemarkerServletTemplateEngine extends FreemarkerTemplateEngine 
        implements ServletTemplateEngine {
  /**    
   * The HTTP request object context key.    
   */    
   public static final String REQUEST = "Request";    
     
  /**    
   * The HTTP response object context key.    
   */    
   public static final String RESPONSE = "Response";    
     
   /** Mimicking the $Form behaviour of Webmacro. */
   public static final String FORM = "Form";

  /**
   * Constructor.
   */
  public FreemarkerServletTemplateEngine() {
    super();
  }

  /**
   * Construct a new Engine for use in a servlet environment. For Velocity this
   * is no different to initialisation outside of a servlets environment.
   * 
   * @param melatiConfig
   *          a {@link MelatiConfig}
   * @param servlet
   *          the servlet we are within
   * @throws TemplateEngineException
   *           if any problem occurs with the engine
   * @see org.melati.template.ServletTemplateEngine#init(org.melati.MelatiConfig, javax.servlet.http.HttpServlet)
   */
  @Override
  public void init(MelatiConfig melatiConfig, HttpServlet servlet)
          throws TemplateEngineException {
    init(melatiConfig);
  }

  /**
   * @param response
   *          the <code>HttpServletResponse</code> that this writer will be
   *          part of
   * @param buffered
   *          whether the writer should be buffered
   * @return a {@link MelatiWriter} appropriate for this engine.
   * @see org.melati.template.ServletTemplateEngine#getServletWriter(javax.servlet.http.HttpServletResponse, boolean)
   */
  @Override
  public MelatiWriter getServletWriter(HttpServletResponse response, 
          boolean buffered) {
    if (buffered) {
      try {
        return new MelatiBufferedVelocityWriter(response);
      } catch (IOException e) {
        throw new TemplateIOException(e);
      }
    } else {
      try {
        return new MelatiVelocityWriter(response);
      } catch (IOException e) {
        throw new TemplateIOException(e);
      }
    }
  }

  /**
   * Get the template context for Velocity, with servlet specific objects added.
   * 
   * @param melati
   *        the {@link Melati}
   * @return a {@link ServletTemplateContext}
   * @see org.melati.template.ServletTemplateEngine#getServletTemplateContext(org.melati.Melati)
   */
  @Override
  public ServletTemplateContext getServletTemplateContext(Melati melati) throws TemplateEngineException {
    Map<String, Object> context = new HashMap<String, Object>();
    
    context.put(REQUEST, melati.getRequest());
    context.put(RESPONSE, melati.getResponse());
    return new FreemarkerServletTemplateContext(context);
  }

}
