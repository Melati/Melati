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
 *     http://paneris.org/
 *     68 Sandbanks Rd, Poole, Dorset. BH14 8BY. UK
 */

package org.melati.template;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServlet;

import org.melati.Melati;
import org.melati.MelatiConfig;
import org.melati.util.MelatiWriter;

/**
 * Interface for a Template engine for use with Melati and Servlets.
 */
public interface ServletTemplateEngine extends TemplateEngine {

  /**
   * Construct a new Engine for use in a servlet environment.
   *
   * @see org.melati.servlet.TemplateServlet
   * @param melatiConfig a {@link MelatiConfig}
   * @param servlet the servlet we are within
   * @throws TemplateEngineException if any problem occurs with the engine
   */
  void init(MelatiConfig melatiConfig, HttpServlet servlet) 
      throws TemplateEngineException;

  /**
   * Get a Writer which can write to Servlet output.
   *  
   * @param response the <code>HttpServletResponse</code> that this 
   *                 writer will be part of
   * @param buffered whether the writer should be buffered
   * @return a {@link MelatiWriter} 
   *         appropriate for this engine.
   */
  MelatiWriter getServletWriter(HttpServletResponse response, 
                                boolean buffered);

  /**
   * Get the generic parameters for this engine.
   *
   * @param melati the {@link Melati}
   * @throws TemplateEngineException if any problem occurs with the engine
   * @return a {@link ServletTemplateContext}
   */
  ServletTemplateContext getServletTemplateContext(Melati melati)
      throws TemplateEngineException;
  
}
