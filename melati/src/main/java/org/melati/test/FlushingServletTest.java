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

package org.melati.test;

/**
 * Base class to use Melati with Servlets.
 * Simply extend this class, override the doRequest method
 *
 * @author Tim Joyce
 * $Revision$
 */

import org.melati.template.webmacro.WebmacroMelatiServlet;
import org.melati.servlet.MelatiContext;
import org.melati.Melati;
import org.melati.util.Waiter;
import org.melati.servlet.PathInfoException;
import org.webmacro.WebMacroException;
import org.webmacro.servlet.WebContext;

public class FlushingServletTest extends WebmacroMelatiServlet {

  public String handle( Melati melati, WebContext context ) 
  throws Exception {
    melati.setBufferingOff();
    if (!melati.getMethod().equals("unflushed")) melati.setFlushingOn();
    context.put("waiter", new Waiter());
    return "test/FlushingServletTest.wm";
  }

/*
 * set up the melati context so we don't have to specify the 
 * logicaldatabase on the pathinfo.  this is a very good idea when
 * writing your appications where you are typically only accessing
 * a single database
 */
  protected MelatiContext melatiContext(Melati melati)
  throws PathInfoException {
    return melatiContextWithLDB(melati,"melatitest");
  }

}

