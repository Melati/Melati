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
import java.util.Enumeration;
import javax.servlet.ServletException;
import org.melati.Melati;
import org.melati.MelatiConfig;
import org.melati.login.HttpBasicAuthenticationAccessHandler;
import org.melati.poem.Table;
import org.melati.poem.Capability;
import org.melati.poem.AccessToken;
import org.melati.poem.AccessPoemException;
import org.melati.poem.PoemThread;
import org.melati.servlet.PoemServlet;
import org.melati.util.MelatiBugMelatiException;
import org.melati.util.MelatiWriter;
import org.melati.util.MelatiException;

public class PoemServletTest extends PoemServlet {

  protected void doPoemRequest(Melati melati)
  throws ServletException, IOException {

    melati.getResponse().setContentType("text/html");
    MelatiWriter output = melati.getWriter();

    output.write(
    "<html><head><title>PoemServlet Test</title></head><body><h2>PoemServlet " +
    "Test</h2><p>This servlet tests your basic melati/poem configuration. " +
    "</p><p>If you can read this message, it means that you have " +
    "successfully created a  POEM session using the configurations given in " +
    "org.melati.LogicalDatabase.properties. </p><p>Please note that this " +
    "servlet does not initialise a template engine.</p><p>Your " +
    "<b>MelatiContext</b> is set up as: " +
    melati.getContext() +
    "<br>, try playing with the PathInfo to see the results (or click " +
    "<a href=" +
    melati.getZoneURL() +
    "/org.melati.test.PoemServletTest/melatitest/user/1/View>user/1/View" +
    "</a>).</p><h4>Your Database has the following tables:</h4><table>");

    for (Enumeration e = melati.getDatabase().getDisplayTables(); 
    e.hasMoreElements(); ) {
      output.write(new StringBuffer("<br>").
      append(((Table)e.nextElement()).getDisplayName()).toString());
    }

    output.write("<h4>Further Testing:</h4>You can test melati Exception " +
    "handling by clicking <a href=Exception>Exception</a><br>You can test " +
    "melati Access Poem Exception handling (requiring you to log-in as an " +
    "administrator) by clicking <a href=AccessPoemException>Access Poem " +
    "Exception</a><br><h4>Template Engine Testing:</h4>You are currently " +
    "using: <b>" + 
    melati.getConfig().getTemplateEngine().getClass().getName() + 
    "</b>.<br>You can test your WebMacro installaction by clicking <a href=" + 
    melati.getZoneURL() + 
    "/HelloWorld/>HelloWorld</a>, or <a href=" + 
    melati.getZoneURL() + 
    "/GuestBook/>GuestBook</a>, or <a href=" + 
    melati.getZoneURL() + 
    "/org.melati.test.WebmacroStandalone/>WebmacroStandalone</a>" + 
    "<br>You can test your Template Engine working with " +
    "Melati by clicking <a href=" + 
    melati.getZoneURL() + 
    "/org.melati.test.TemplateServletTest/>" + 
    "org.melati.test.TemplateServletTest/</a>");

    String method = melati.getMethod();
    if (method != null) {
      output.write("<h4>" + method + "</h4>");
      Capability admin = PoemThread.database().getCanAdminister();
      AccessToken token = PoemThread.accessToken();
      if (method.equals("AccessPoemException")) 
      throw new AccessPoemException(token, admin);
      if (method.equals("Exception")) 
      throw new MelatiBugMelatiException("It got caught!");
      if (method.equals("Redirect")) 
      melati.getResponse().sendRedirect("http://www.melati.org");
    }
  }
  
/**
 *
 * this simply demonstrates how to use a different melati configuration
 *
 **/
  protected MelatiConfig melatiConfig() throws MelatiException {
    MelatiConfig config = super.melatiConfig();
    config.setAccessHandler(new HttpBasicAuthenticationAccessHandler());
    return config;
  }

}






