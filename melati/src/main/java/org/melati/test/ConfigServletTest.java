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

import java.io.Writer;
import java.io.IOException;

import javax.servlet.ServletException;

import org.melati.servlet.ConfigServlet;
import org.melati.MelatiContext;
import org.melati.Melati;
import org.melati.util.MelatiBugMelatiException;

public class ConfigServletTest extends ConfigServlet {

    protected void doConfiguredRequest(MelatiContext melatiContext) 
      throws ServletException, IOException {

        Melati melati = melatiContext.getMelati();
        melatiContext.getResponse().setContentType("text/html");
        Writer output = melatiContext.getWriter();
        output.write("<html><head><title>ConfigServlet Test</title></head><body><h2>ConfigServlet Test</h2>");
        output.write("<p>This servlet tests your basic melati configuration. ");
        output.write("If you can read this message, it means that you have successfully ");
        output.write("created a Melati and a MelatiContext, using the configuration given in ");
        output.write("org.melati.MelatiServlet.properties.  Please note that this servlet does not ");
        output.write("construct a POEM session or initialise a template engine.</p>");
        
        output.write("<h4>Your Melati is configured with the following parameters:</h4><table>");
        output.write("<tr><td>TemplateEngine</td><td>" + melati.getTemplateEngine().getClass().getName() + "</td></tr>");
        output.write("<tr><td>AccessHandler</td><td>" + melati.getAccessHandler().getClass().getName() + "</td></tr>");
        output.write("<tr><td>Locale</td><td>" + melati.getLocale().getClass().getName() + "</td></tr>");
        output.write("<tr><td>TempletLoader</td><td>" + melati.getTempletLoader().getClass().getName() + "</td></tr>");
        output.write("<tr><td>JavascriptLibraryURL</td><td>" + melati.getJavascriptLibraryURL() + "</td></tr>");
        output.write("<tr><td>StaticURL</td><td>" + melati.getStaticURL() + "</td></tr></table>");

        output.write("<h4>This servlet was called with the follwing Method (taken from melatiContext.getMethod()): " + melatiContext.getMethod() + "</h4>");
        
        output.write("<h4>Further Testing:</h4>");
        output.write("You can test melati Exception handling by clicking <a href=Exception>Exception</a><br>");
        output.write("You can test melati Redirect handling by clicking <a href=Redirect>Redirect</a><br>");
        output.write("You can test your POEM setup (connecting to logical database <tt>melatitest</tt>) by clicking <a href=" + melatiContext.getZoneURL() + "/org.melati.test.PoemServletTest/>org.melati.test.PoemServletTest/</a><br>");
        
        String method = melatiContext.getMethod();
        if (method != null) {
          if (method.equals("Exception")) throw new MelatiBugMelatiException("It got caught!");
          if (method.equals("Redirect")) melatiContext.getResponse().sendRedirect("http://www.melati.org");
        }
        
    }


}






