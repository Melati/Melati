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

import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;

import java.util.Hashtable;

import javax.servlet.ServletException;

import org.melati.servlet.ConfigServlet;
import org.melati.servlet.MemoryFormDataAdaptorFactory;
import org.melati.servlet.MultipartFormField;
import org.melati.servlet.MultipartFormDataDecoder;
import org.melati.Melati;
import org.melati.MelatiConfig;
import org.melati.util.MelatiBugMelatiException;
import org.melati.util.MelatiWriter;

/**
 * Test a Melati configuration without using a Template Engine.
 */
public class ConfigServletTest extends ConfigServlet {

  private static final long serialVersionUID = 5538437218064525327L;

  protected void doConfiguredRequest(Melati melati)
      throws ServletException, IOException {

    String method = melati.getMethod();
    if (method != null && method.equals("Upload")) {
      Hashtable fields = null;
      InputStream in = melati.getRequest().getInputStream();
      MultipartFormDataDecoder decoder=
        new MultipartFormDataDecoder(melati,
            in,
            melati.getRequest().getContentType(),
            melati.getConfig().getFormDataAdaptorFactory());
      fields = decoder.parseData();
      MultipartFormField field = (MultipartFormField)fields.get("file");
      byte[] data = field.getData();
      if (data.length == 0) {
        melati.getWriter().write("No file was uploaded");
        return;
      }
      melati.getResponse().setContentType(field.getContentType());
      OutputStream output = melati.getResponse().getOutputStream();
      output.write(data);
      output.close();
      return;
    }

    MelatiConfig config = melati.getConfig();
    melati.setResponseContentType("text/html");
    MelatiWriter output = melati.getWriter();

    output.write(
    "<html><head><title>ConfigServlet Test</title></head>\n");
    output.write(
    "<body><h2>ConfigServlet Test</h2>\n");
    output.write(
    "<p>This servlet tests your basic melati " +
    "configuration. <br>\n" +
    "If you can read this message, it means that you have " +
    "successfully created a Melati using the configuration " +
    "given in org.melati.MelatiConfig.properties.<br>\n"+
    "Please note that this " +
    "servlet does not construct a POEM session or initialise a template " +
    "engine.</p>\n");
    output.write(
    "<h4>Your Melati is configured with the following properties: " +
    "</h4>\n<table>");
    output.write(
    "<tr><td>AccessHandler</td><td>" +
    config.getAccessHandler().getClass().getName() +
    "</td></tr>\n");
    output.write(
    "<tr><td>ServletTemplateEngine</td><td>" +
    config.getTemplateEngine().getClass().getName() +
    "</td></tr>\n");
    output.write(
    "<tr><td>StaticURL</td><td>" +
    config.getStaticURL() + "</td></tr>\n");
    output.write(
    "<tr><td>JavascriptLibraryURL</td><td>" +
    config.getJavascriptLibraryURL() +
    "</td></tr>\n");
    output.write(
    "<tr><td>FormDataAdaptorFactory</td><td>" +
    config.getFormDataAdaptorFactory().getClass().getName() +
    "</td></tr>\n");
    output.write(
    "<tr><td>Locale</td><td>" +
    MelatiConfig.getPoemLocale().getClass().getName() +
    "</td></tr>\n");
    output.write(
    "<tr><td>TempletLoader</td><td>" +
    config.getTempletLoader().getClass().getName() +
    "</td></tr>\n");
    output.write(
    "</table>\n" +

    "<h4>This servlet was called with the following Method (taken from " +
    "melati.getMethod()): " + 
    melati.getMethod() + 
    "</h4>\n");
    output.write(
    "<h4>Further Testing:</h4>\n" + 
    "You can test melati Exception handling by " +
    "clicking <a href=" + 
    melati.getSameURL() + 
    "/Exception>Exception</a><br>\n");
    output.write(
    "You can test melati Redirect " +
    "handling by clicking <a href=" +
    melati.getSameURL() + 
    "/Redirect>Redirect</a><br>\n");
    output.write(
    "You can test your " +
    "POEM setup (connecting to logical database <tt>melatitest</tt>) by " +
    "clicking <a href=" + 
    melati.getZoneURL() + 
    "/org.melati.test.PoemServletTest/melatitest/>" +
    "org.melati.test.PoemServletTest/melatitest/</a><br>\n");
    output.write(
    "<form method=\"post\" action=\"" + 
    melati.getSameURL() + 
    "/Upload\" enctype=\"multipart/form-data\" target='Upload'>" +
    "You can upload a file here:<br>\n" +
    "<input type=hidden name='upload' value='yes'>" +
    "<input type=\"file\" name=\"file\" enctype=\"multipart/form-data\">" +
    "<input type=\"submit\" name=\"Submit\" value=\"Upload file\"><br>" +
    getUploadMessage(melati) +
    "</form>\n");
    if (method != null) {
      if (method.equals("Exception")) 
        throw new MelatiBugMelatiException("It got caught!");
      if (method.equals("Redirect")) {
        melati.getResponse().sendRedirect("http://www.melati.org");
        return;
      }
    }
  }

 /**
  * Demonstrates how to use a different melati configuration.
  */
  protected MelatiConfig melatiConfig() {
    MelatiConfig config = super.melatiConfig();
    config.setFormDataAdaptorFactory(new MemoryFormDataAdaptorFactory());
    return config;
  }

  protected String getUploadMessage(Melati melati) {
    return "This will save your file in memory. Try saving a file in your " +
           "/tmp directory <a href='" + melati.getZoneURL() +
           "/org.melati.test.ConfigServletTestOverride/'>here</a>.";
  }

}






