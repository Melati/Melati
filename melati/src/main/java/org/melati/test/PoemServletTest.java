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
import org.melati.servlet.PoemFileDataAdaptorFactory;
import org.melati.servlet.PoemServlet;
import org.melati.servlet.PathInfoException;
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
       melati.getDatabase().getSettingTable().ensure("UploadDir","/tmp","","");
       melati.getDatabase().getSettingTable().ensure("UploadURL","tmp","","");

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
       System.err.println("File:"+ field + ":");
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
     output.write("<h4>The PoemContext</h4>\n");
     output.write("<h4>The PoemContext enables access to a database, a table, a record and a method.</h4>\n");
     output.write("<p>Your " +
     "<b>PoemContext</b> is set up as: " +
     melati.getPoemContext() +
     ".</p> \n");
     output.write("<p>Method:" + method + "</p>\n");
     
     output.write("<p>\nThe PoemContext can be setup using the servlet's PathInfo.</p>\n");
     output.write("<ul>\n");
     output.write("<li>\n");
     output.write("<a href=" +
         melati.getZoneURL() +
         "/org.melati.test.PoemServletTest/" +
         melati.getPoemContext().getLogicalDatabase() +
         "/tableinfo/0/View>tableinfo/0/View" +
         "</a>)\n");
     output.write("</li>\n");
     output.write("<li>\n");
     output.write("<a href=" +
         melati.getZoneURL() +
         "/org.melati.test.PoemServletTest/" +
         melati.getPoemContext().getLogicalDatabase() +
         "/columninfo/0/View>columninfo/0/View" +
         "</a>)\n");
     output.write("</li>\n");
     output.write("<li>\n");
     output.write("<a href=" +
         melati.getZoneURL() +
         "/org.melati.test.PoemServletTest/" +
         melati.getPoemContext().getLogicalDatabase() +
         "/user/1/View>user/1/View" +
         "</a>)\n");
     output.write("</li>\n");
     output.write("</ul>\n");
     output.write("");
     output.write("<table>");
     output.write("<tr><th colspan=2>Tables in the Database " + melati.getDatabaseName() + "</th></tr>\n");

     for (Enumeration e = melati.getDatabase().getDisplayTables(); 
         e.hasMoreElements();) {
       Table t = (Table)e.nextElement();
       output.write("<tr><td>");
       output.write(t.getDisplayName());
       output.write("</td><td>\n");
       output.write(t.getDescription());
       output.write("</td></tr>\n");
     }
     output.write("</table>\n");


     output.write("<h4>File upload</h4>\n");
     output.write("<p>\n");
     output.write("A <b>PoemFileDataAdaptor</b> obtains the name of the file upload directory from a setting in the settings table.\n");
     output.write("</p>\n");
     
     output.write(
         "<form method=\"post\" action=\"Upload" + 
         "\" enctype=\"multipart/form-data\" target='Upload'>" +
         "You can upload a file here:<br>\n" +
         "<input type=hidden name='upload' value='yes'>" +
         "<input type=\"file\" name=\"file\" enctype=\"multipart/form-data\">" +
         "<input type=\"submit\" name=\"Submit\" value=\"Upload file\"><br>" +
         getUploadMessage(melati) +
         "</form>\n");
     
     output.write("<h4>Melati Exception handling</h4>");
     output.write("An exception is rendered to the output.\n");     
     output.write("An access violation provokes a login challenge.\n");     
     output.write("You curently have the access token " + melati.getUser() + ".\n");     
     output.write("<ul>\n");
     output.write("<li>\n");
     output.write("<a href='Exception'>Exception</a>\n");
     output.write("</li>\n");
     output.write("<li>\n");
     output.write("<a href='AccessPoemException'>Access Poem " +
     "Exception</a> (requiring you to log-in as an administrator)\n");
     output.write("</li>\n");
     output.write("</ul>\n");
     
    if (method != null) {
      if (method.equals("Exception")) 
        throw new MelatiBugMelatiException("It got caught!");
      if (method.equals("AccessPoemException")) {
        Capability admin = PoemThread.database().administerCapability();
        AccessToken token = PoemThread.accessToken();
        if (!token.givesCapability(admin))
          throw new AccessPoemException(token, admin);
      }
    }

    output.write("<h3>Further Testing</h3>\n");
    output.write("<h4>Template Engine Testing</h4>\n");
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


   
   }
  
/**
 * How to use a different melati configuration.
 */
  protected MelatiConfig melatiConfig() {
    MelatiConfig config = super.melatiConfig();
    config.setAccessHandler(new HttpBasicAuthenticationAccessHandler());
    PoemFileDataAdaptorFactory factory = 
      new PoemFileDataAdaptorFactory();
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
    return "This will save your file in a file at a path specified in the database's Settings table.<br>\n" + 
    "  Try saving a file in your " +
           "/tmp directory <a href='" + melati.getZoneURL() +
           "/org.melati.test.ConfigServletTestOverride/'>here</a>.";
  }
  
  

}






