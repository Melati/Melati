/*
 * $Source$
 * $Revision$
 *
 * Copyright (C) 2008 Tim Pizey
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

package org.melati.template.webmacro.test;

import org.melati.Melati;
import org.melati.MelatiConfig;
import org.melati.PoemContext;
import org.melati.servlet.PathInfoException;
import org.melati.template.velocity.VelocityServletTemplateEngine;
import org.webmacro.servlet.WebContext;

/**
 * @author timp
 * @since 26 Feb 2009
 * 
 */
public class MisconfiguredWebmacroMelatiServlet extends
    org.melati.template.webmacro.WebmacroMelatiServlet {

  private static final long serialVersionUID = 561338804739539428L;

  /**
   * Constructor.
   */
  public MisconfiguredWebmacroMelatiServlet() {
    super();
  }

  /**
   * {@inheritDoc}
   * 
   * 
   * @see org.melati.template.webmacro.WebmacroMelatiServlet#handle(org.melati.Melati,
   *      org.webmacro.servlet.WebContext)
   */
  protected String handle(Melati m, WebContext c) throws Exception {
    throw new RuntimeException("Should not have got this far");
  }

  /**
   * {@inheritDoc}
   * 
   * @see org.melati.servlet.ConfigServlet#melatiConfig()
   */
  protected MelatiConfig melatiConfig() {
    MelatiConfig m = new MelatiConfig();
    m.setTemplateEngine(new VelocityServletTemplateEngine());
    return m;
  }

  protected PoemContext poemContext(Melati melati) throws PathInfoException {
    return poemContextWithLDB(melati, "melatitest");
  }

}
