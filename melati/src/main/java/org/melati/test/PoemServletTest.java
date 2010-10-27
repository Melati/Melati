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
 *     Tim Joyce <timj At paneris.org>
 *     http://paneris.org/
 *     68 Sandbanks Rd, Poole, Dorset. BH14 8BY. UK
 */

package org.melati.test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Hashtable;

import javax.servlet.ServletException;

import org.melati.Melati;
import org.melati.MelatiConfig;
import org.melati.poem.Database;
import org.melati.poem.PoemTask;
import org.melati.poem.Table;
import org.melati.poem.Capability;
import org.melati.poem.AccessToken;
import org.melati.poem.AccessPoemException;
import org.melati.poem.PoemThread;
import org.melati.servlet.MultipartFormDataDecoder;
import org.melati.servlet.MultipartFormField;
import org.melati.servlet.PoemServlet;
import org.melati.util.MelatiBugMelatiException;
import org.melati.util.MelatiWriter;

/**
 * Test a Melati configuration which accesses a POEM database 
 * without using a Template Engine.
 */
public class PoemServletTest extends PoemServlet {

  private static final long serialVersionUID = -2216872878288661630L;

  /**
   * Constructor.
   */
   public PoemServletTest() {
     super();
   }
   

  /**
   * Normally one would ensure that these settings are present in 
   * the database, but they are ensured here so that everything 
   * is in one place.
   * {@inheritDoc}
   * @see org.melati.servlet.PoemServlet#prePoemSession(org.melati.Melati)
   */
  protected void prePoemSession(Melati melati) throws Exception {
    final Database db = melati.getDatabase();
    final MelatiConfig mc = melati.getConfig();
    db.inSession(AccessToken.root, new PoemTask() {
      public void run() {
        db.getSettingTable().
        ensure("UploadDir", 
               mc.getStaticURL(), 
               "Upload Directory",
               "Directory to upload to");
        db.getSettingTable().
        ensure("UploadURL",
               mc.getStaticURL(), 
               "Uploaded URL",
               "URL of uploaded files, defaults to Melati Static ");
      }
    });
    
  }


  protected void doPoemRequest(Melati melati)
     throws ServletException, IOException {
     String method = melati.getMethod();
     if (method != null && method.equals("Upload")) {
       doUpload(melati);
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
                  "successfully created a  POEM session, \n");
     output.write("using the configurations given in " +
                  "org.melati.LogicalDatabase.properties. </p>\n");
     output.write("<p><b>Note</b> that this " +
                  "servlet does not initialise a template engine.</p>\n");
     output.write("<h4>The PoemContext</h4>\n");
     output.write("<h4>The PoemContext enables access to a database, a table, a record and a method.</h4>\n");
     output.write("<p>Your <b>PoemContext</b> is set up as: " +
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
         "/tableinfo/0/View>" + 
         "tableinfo/0/View" +
         "</a>)\n");
     output.write("</li>\n");
     output.write("<li>\n");
     output.write("<a href=" +
         melati.getZoneURL() +
         "/org.melati.test.PoemServletTest/" +
         melati.getPoemContext().getLogicalDatabase() +
         "/columninfo/0/View>" + 
         "columninfo/0/View" +
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

     for (Table t : melati.getDatabase().getDisplayTables()) { 
       output.write("<tr>\n <td>");
       output.write(t.getDisplayName());
       output.write("</td>\n <td>");
       output.write(t.getDescription());
       output.write("</td>\n</tr>\n");
     }
     output.write("</table>\n");


     output.write("<h4>File upload</h4>\n");
     output.write("<p>\n");
     output.write("A <b>PoemFileDataAdaptor</b> ");
     output.write("obtains the name of the file upload directory from ");
     output.write("a setting in the settings table.\n");
     output.write("</p>\n");
     
     output.write(
         "<form method=\"post\" action=\"Upload" + 
         "\" enctype=\"multipart/form-data\" target='Upload'>" +
         p("You can upload a file here:") +
         "<input type=hidden name='upload' value='yes'>\n" +
         "<input type=\"file\" name=\"file\" enctype=\"multipart/form-data\">\n" +
         "<input type=\"submit\" name=\"Submit\" value=\"Upload file\">\n" +
         getUploadMessage(melati) +
         "</form>\n");
     
     output.write("<h4>Melati Exception handling</h4\n>");
     output.write("<p>An exception is rendered to the output.</p>\n");     
     output.write("<p>An access violation provokes a login challenge.</p>\n");     
     output.write("<p>You curently have the access token " + melati.getUser() + ".</p>\n<");     
     output.write("<ul>\n");
     output.write("<li>\n");
     output.write("<a href='" + 
             melati.getSameURL() +
             "/Exception'>Exception</a>\n");
     output.write("</li>\n");
     output.write("<li>\n");
     output.write("<a href='" +
             melati.getSameURL() +
             "/AccessPoemException'>Access Poem " +
     "Exception</a> (requiring you to log-in as an administrator)\n");
     output.write("</li>\n");
     output.write("</ul>\n");
     
    if (method != null) {
      if (method.equals("Exception")) 
        throw new MelatiBugMelatiException("It got caught!");
      if (method.equals("AccessPoemException")) {
        Capability admin = PoemThread.database().administerCapability();
        AccessToken token = PoemThread.accessToken();
        output.write("<p>You are logged in as "+token+" and have " + admin + " capability.</p>\n");
        if (!token.givesCapability(admin))
          throw new AccessPoemException(token, admin);
      }
    }

    output.write("<h3>Further Testing</h3>\n");
    output.write("<h4>Template Engine Testing</h4>\n");
    output.write(p("You are currently using: <b>" + 
    melati.getConfig().getTemplateEngine().getClass().getName() + 
    "</b>."));
    output.write(p("You can test your WebMacro installation by clicking <a href=" + 
    melati.getZoneURL() + 
    "/org.melati.test.WebmacroStandalone/>WebmacroStandalone</a>"));
    output.write(p("You can test your Template Engine working with " +
    "Melati by clicking <a href=" + 
    melati.getZoneURL() + 
    "/org.melati.test.TemplateServletTest/" + 
    melati.getPoemContext().getLogicalDatabase() + 
    ">" + 
    "org.melati.test.TemplateServletTest/" + 
    melati.getPoemContext().getLogicalDatabase() + "</a>"));

    output.write(p("Make sure the <a href='"+ 
      melati.getZoneURL() + 
      "/org.melati.admin.Admin/" + 
      melati.getPoemContext().getLogicalDatabase() + 
      "/Main'>Admin System</a> is working." + 
      "\n"));
    
    output.write("</body></html>");

   
   }

  private void doUpload(Melati melati) throws IOException {

    Hashtable<String, MultipartFormField> fields = null;
    InputStream in = melati.getRequest().getInputStream();
    MultipartFormDataDecoder decoder =
        new MultipartFormDataDecoder(melati,
            in,
            melati.getRequest().getContentType(),
            melati.getConfig().getFormDataAdaptorFactory());
    fields = decoder.parseData();
    MultipartFormField field = (MultipartFormField)fields.get("file");
    byte[] data = field.getData();
    if (data.length == 0) {
      melati.getWriter().write(p("No file was uploaded"));
      return;
    }
    melati.getResponse().setContentType(field.getContentType());
    OutputStream output = melati.getResponse().getOutputStream();
    output.write(data);
    output.close();
    return;
  }
  

  protected String getUploadMessage(Melati melati) {
    return p("This will save your file in a file at a path specified in the database's Settings table.") + 
    p("  Try saving a file in your " +
           "/tmp directory <a href='" + melati.getZoneURL() +
           "/org.melati.test.ConfigServletTestOverride/'>here</a>.");
  }
  
  private String p(String sentence) { 
    return "<p>" + sentence + "</p>\n";
  }

}






