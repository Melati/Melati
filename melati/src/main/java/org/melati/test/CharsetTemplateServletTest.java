/*
 * $Source$
 * $Revision$
 *
 * Copyright (C) 2003 Jim Wright
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
 *     Jim Wright <jimw@paneris.org>
 *     Bohemian Enterprise
 *     Predmerice nad Jizerou 77
 *     294 74
 *     Mlada Boleslav
 *     Czech Republic
 */

package org.melati.test;

import org.melati.Melati;
import org.melati.PoemContext;
import org.melati.servlet.PathInfoException;
import org.melati.servlet.TemplateServlet;
import org.melati.template.TemplateContext;

/**
 * Test display of various characters using a Template Engine.
 */
public class CharsetTemplateServletTest extends TemplateServlet {

  protected String doTemplateRequest(Melati melati,
                                     TemplateContext context)
      throws Exception {

    context.put("servlet", this);
    context.put("items", CharData.getItems());
    melati.setResponseContentType("text/html");
    return "org/melati/test/CharsetTemplateServletTest";
  }

  public String getServletName() {
      return "org.melati.test.CharsetTemplateServletTest";
  }

/**
 * Set up the melati context so we don't have to specify the 
 * logicaldatabase on the pathinfo.  
 *
 * Useful when writing appications where you are typically only accessing
 * a single database.
 */
  protected PoemContext poemContext(Melati melati)
      throws PathInfoException {
    PoemContext pc = super.poemContext(melati);
    if (pc.getLogicalDatabase().equals("")) 
      pc = poemContextWithLDB(melati,"melatitest");
    return pc;
  }
}
