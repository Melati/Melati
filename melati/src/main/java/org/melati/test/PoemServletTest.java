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

import java.io.IOException;
import java.io.Writer;
import java.util.Enumeration;
import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import org.melati.Melati;
import org.melati.MelatiContext;
import org.melati.servlet.PoemServlet;
import org.melati.util.MelatiBugMelatiException;
import org.melati.poem.Table;
import org.melati.poem.Capability;
import org.melati.poem.AccessToken;
import org.melati.poem.AccessPoemException;
import org.melati.poem.PoemThread;

public class PoemServletTest extends PoemServlet {

  protected void doPoemRequest(MelatiContext melatiContext)
  throws ServletException, IOException {

    Melati melati = melatiContext.getMelati();
    melatiContext.getResponse().setContentType("text/html");
    Writer output = melatiContext.getWriter();

    output.write("<html><head><title>PoemServlet Test</title></head><body><h2>PoemServlet Test</h2>");
    output.write("<p>This servlet tests your basic melati/poem configuration. ");
    output.write("If you can read this message, it means that you have successfully ");
    output.write("created a  POEM session using the configurations given in ");
    output.write("org.melati.LogicalDatabase.properties. ");

    output.write("Please note that this servlet does not initialise a template engine.</p>");
    output.write(melatiContext.getMethod() + "</h4>");
    output.write("<h4>Your Database has the following tables:</h4><table>");

    for (Enumeration e = melatiContext.getDatabase().getDisplayTables(); e.hasMoreElements(); ) {
      output.write(new StringBuffer("<br>").append(((Table)e.nextElement()).getDisplayName()).toString());
    }

    output.write("<h4>Further Testing:</h4>");
    output.write("You can test melati Exception handling by clicking <a href=Exception>Exception</a><br>");
    output.write("You can test melati Access Poem Exception handling (requiring you to log-in as an administrator) by clicking <a href=AccessPoemException>Access Poem Exception</a><br>");
    output.write("<h4>Template Engine Testing:</h4>");
    output.write("You are currently using: <b>" + melati.getTemplateEngine().getClass().getName() + "</b>.<br>");
    output.write("You can test your WebMacro installaction by clicking <a href=" + melatiContext.getZoneURL() + "/HelloWorld/>HelloWorld</a>, or <a href=" + melatiContext.getZoneURL() + "/GuestBook/>GuestBook</a><br>");
    output.write("You can test your WebMacro working with Melati by clicking <a href=" + melatiContext.getZoneURL() + "/org.melati.test.TemplateServletTestWM/>org.melati.test.TemplateServletTestWM/</a><br>");
    output.write("Melati does not work with JTemplater at present, this should be fixed soon.");

    String method = melatiContext.getMethod();
    if (method != null) {
      Capability admin = PoemThread.database().getCanAdminister();
      AccessToken token = PoemThread.accessToken();
      if (method.equals("AccessPoemException")) throw new AccessPoemException(token, admin);
      if (method.equals("Exception")) throw new MelatiBugMelatiException("It got caught!");
      if (method.equals("Redirect")) melatiContext.getResponse().sendRedirect("http://www.melati.org");
    }
  }

  public String getLogicalDatabase(MelatiContext melatiContextIn, String logicalDatabase)
  {
    return "melatitest";
  }

}

