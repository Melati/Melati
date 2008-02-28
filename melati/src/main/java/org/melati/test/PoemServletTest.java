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
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.servlet.ServletException;

import org.melati.Melati;
import org.melati.MelatiConfig;
import org.melati.login.HttpBasicAuthenticationAccessHandler;
import org.melati.poem.Table;
import org.melati.poem.Capability;
import org.melati.poem.AccessToken;
import org.melati.poem.AccessPoemException;
import org.melati.poem.PoemThread;
import org.melati.PoemContext;
import org.melati.servlet.MultipartDataDecoder;
import org.melati.servlet.MultipartFormField;
import org.melati.servlet.PoemServlet;
import org.melati.servlet.PathInfoException;
import org.melati.servlet.TemporaryFileDataAdaptorFactory;
import org.melati.util.ExceptionUtils;
import org.melati.util.MelatiBugMelatiException;
import org.melati.util.MelatiWriter;

/**
 * Test a Melati configuration which accesses a POEM database 
 * without using a Template Engine.
 */
public class PoemServletTest extends PoemServlet {
  private static final long serialVersionUID = 1L;

  /**
   * Constructor.
   */
   public PoemServletTest() {
     super();
   }

   protected void doPoemRequest(Melati melati)
     throws ServletException, IOException {
     String method = melati.getMethod();
     if (method != null && method.equals("Upload")) {
       Hashtable fields = null;
       try {
         InputStream in = melati.getRequest().getInputStream();
         MultipartDataDecoder decoder=
           new MultipartDataDecoder(melati,
                 in,
                 melati.getRequest().getContentType(),
                 melati.getConfig().getFormDataAdaptorFactory());
         fields = decoder.parseData();
       }
       catch (IOException e) {
         melati.getWriter().write(
           "There was some error uploading your file:" +
             ExceptionUtils.stackTrace(e));
         return;
       }
       MultipartFormField field = (MultipartFormField)fields.get("file");
       if (field == null) {
         melati.getWriter().write("No file was uploaded");
         return;
       }
       byte[] data = field.getData();
       melati.getResponse().setContentType(field.getContentType());
       OutputStream output = melati.getResponse().getOutputStream();
       output.write(data);
       output.close();
       return;
     }

     melati.getResponse().setContentType("text/html");
     MelatiWriter output = melati.getWriter();

     output.write(
     "<html><head><title>PoemServlet Test</title></head>\n");
     output.write("<body>\n");
     output.write("<h2>PoemServlet " +
     "Test</h2>\n");
     output.write("<p>This servlet tests your melati/poem configuration. " +
     "</p>\n");
     output.write("<p>If you can read this message, it means that you have " +
     "successfully created a  POEM session using the configurations given in " +
     "org.melati.LogicalDatabase.properties. </p>\n");
     output.write("<p><b>Note</b> that this " +
     "servlet does not initialise a template engine.</p>\n");
     output.write("<p>Your " +
     "<b>MelatiContext</b> is set up as: " +
     melati.getPoemContext() +
     "<br>, \n");
     output.write("try playing with the PathInfo to see the results (or click " +
     "<a href=" +
     melati.getZoneURL() +
     "/org.melati.test.PoemServletTest/" +
     melati.getPoemContext().getLogicalDatabase() +
     "/user/1/View>user/1/View" +
     "</a>).</p>\n");
     output.write("<h4>Your Database has the following tables:</h4>\n");
     output.write("<table>");

     for (Enumeration e = melati.getDatabase().getDisplayTables(); 
         e.hasMoreElements();) {
       output.write(new StringBuffer("<br>").
       append(((Table)e.nextElement()).getDisplayName()).toString());
     }

     output.write("<h3>Further Testing:</h3>\n");
     output.write("<h4>Template Engine Testing:</h4>\n");
     output.write("You are currently using: <b>" + 
     melati.getConfig().getTemplateEngine().getClass().getName() + 
     "</b>.<br>\n");
     output.write("You can test your WebMacro installation by clicking <a href=" + 
     melati.getZoneURL() + 
     "/org.melati.test.WebmacroStandalone/>WebmacroStandalone</a>" + 
     "<br>\n");
     output.write("You can test your Template Engine working with " +
     "Melati by clicking <a href=" + 
     melati.getZoneURL() + 
     "/org.melati.test.TemplateServletTest/" + 
     melati.getPoemContext().getLogicalDatabase() + 
     ">" + 
     "org.melati.test.TemplateServletTest/" + 
     melati.getPoemContext().getLogicalDatabase() + "</a><br/>\n");

     output.write("<p>\n");
     output.write("Make sure the <a href='"+ 
       melati.getZoneURL() + 
       "/org.melati.admin.Admin/" + 
       melati.getPoemContext().getLogicalDatabase() + 
       "/Main'>Admin System</a> is working." + 
       "\n");
     output.write("</p>\n");  

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
      output.write("Test melati Exception handling" +
      "handling by clicking <a href=Exception>Exception</a><br>\n");
      output.write("Test " +
      "melati Access Poem Exception handling (requiring you to log-in as an " +
      "administrator) by clicking <a href=AccessPoemException>Access Poem " +
      "Exception</a><br>\n");
      output.write("Current method:" + method + "<br/>\n");
      
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
 * How to use a different melati configuration.
 */
  protected MelatiConfig melatiConfig() {
    MelatiConfig config = super.melatiConfig();
    config.setAccessHandler(new HttpBasicAuthenticationAccessHandler());
    TemporaryFileDataAdaptorFactory factory = 
      new TemporaryFileDataAdaptorFactory();
    config.setFormDataAdaptorFactory(factory);

    return config;
  }

/**
 * Set up the melati context so we don't have to specify the 
 * logical database on the pathinfo.  
 * <p>
 * This is a very good idea when
 * writing your applications where you are typically only accessing
 * a single database.
 */
  protected PoemContext poemContext(Melati melati)
  throws PathInfoException {
    String[] parts = melati.getPathInfoParts();
    if (parts.length == 0) 
      return poemContextWithLDB(melati,"melatitest");
    else 
      return super.poemContext(melati);
  }

  protected String getUploadMessage(Melati melati) {
    return "This will save your file in a tempoarary file in the servers memory, " + 
    "which will remain until the JVM exits or the memory is required.<br>\n" + 
    "  Try saving a file in your " +
           "/tmp directory <a href='" + melati.getZoneURL() +
           "/org.melati.test.ConfigServletTestOverride/'>here</a>.";
  }
  
  

}






