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
 *     Jim Wright <jimw At paneris.org>
 *     Bohemian Enterprise
 *     Predmerice nad Jizerou 77
 *     294 74
 *     Mlada Boleslav
 *     Czech Republic
 */

package org.melati.test;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;

import javax.servlet.ServletException;

import org.melati.servlet.ConfigServlet;
import org.melati.Melati;

/**
 * Test display of various characters without using a Template Engine.
 */
public class CharsetServletTest extends ConfigServlet {
  private static final long serialVersionUID = 1L;

  protected void doConfiguredRequest(Melati melati)
      throws ServletException, IOException {

    melati.setResponseContentType("text/html");
    PrintWriter w = melati.getWriter().getPrintWriter();
    String charset = melati.getResponse().getCharacterEncoding();

    w.println("<?xml version=\"1.0\" encoding=\"" + charset + "\"?>");
    w.println("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\"");
    w.println("\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">");
    w.println("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
    w.println("<head>");
    w.println("<title>" + getServletName() + "</title>");
    w.println("</head>");
    w.println("<body>");
    w.println("<h1>Characters Displayed in a Servlet</h1>");
    w.println("<p>Based on available configuration information Melati" +
              " has suggested the response encoding " + charset +
              " and we are using it for this test.</p>");
    w.println("<p>The test data originally comes from the Unicode Database.");
    w.println("If you are viewing it online then for copyright information ");
    w.println("and UCD Terms click here: ");
    w.println("<a href=\"http://www.unicode.org/unicode/copyright.html\">");
    w.println("http://www.unicode.org/unicode/copyright.html</a>.");
    w.println("Offline please refer to the Javadocs.</p>");
    w.println("<table border=\"1\">");
    w.println("<thead><tr><th>Description</th><th>Character Reference</th>" +
              "<th>Character</th><th>Entitied</th><th>Encoding Test</th></tr></thead>");
    w.println("<tbody>");
    for (Iterator i = CharData.getItems(); i.hasNext();) {
      CharData.Item cd = (CharData.Item)i.next();
      w.println("<tr>");
      w.println("<td>" + cd.getDescription() + "</td>");
      w.println("<td>" + cd.getReference() + "</td>");
      w.println("<td>" + cd.getChar() + "</td>");
      w.println("<td>" + melati.getMarkupLanguage().rendered(
                  cd.getChar()) + "</td>");
      w.println("<td>" + cd.encodingTest(melati) + "</td>");
      w.println("</tr>");
    }
    w.println("</tbody>");
    w.println("</table>");
    w.println("</body>");
    w.println("</html>");
  }

  
}
