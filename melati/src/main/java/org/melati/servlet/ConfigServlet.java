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
 
 /*
 * Config Servlet is the simplest way to use Melati.  
 *
 * All a ComboServlet does is to configure a melati and combine the 
 * doGet and doPost methods.  Importantly it does not establish a poem session
 * leaving you to do this for yourself.
 *
 * if you want a poem session established, please extend PoemServlet
 */
 
package org.melati.servlet;

import java.io.PrintWriter;
import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.melati.Melati;
import org.melati.MelatiContext;
import org.melati.util.MelatiException;

public abstract class ConfigServlet extends HttpServlet
{

  // the melati
  public Melati melati;
  
  /** 
   * Inititialise Melati
   * @param ServletConfig
   */
  public void init( ServletConfig config ) throws ServletException
  {
     super.init( config );
     try {
       melati = new Melati();
     } catch (MelatiException e) {
       // log it to system.err as ServletExceptions go to the 
       // servlet runner log (eg jserv.log), and don't have a stack trace!
       e.printStackTrace(System.err);
       throw new ServletException(e.toString());
     }
  }
    
  /**
   * Handles GET
   */
  public void doGet( HttpServletRequest request, HttpServletResponse response )
    throws ServletException, IOException
  {
    doGetPostRequest(request, response);
  }

  /**
   * Handle a POST
   */
  public void doPost( HttpServletRequest request, HttpServletResponse response )
    throws ServletException, IOException
  {
    doGetPostRequest(request, response);
  }

  /**
   * Process the request.
   */
  private void doGetPostRequest
   (final HttpServletRequest request, final HttpServletResponse response)
    throws IOException {

    try {
      // the method (taken from pathinfo)
      String method = null;
      MelatiContext melatiContext = melati.getContext(request, response); 
      // set the method called
      String[] parts = melatiContext.getPathInfoParts();
      if (parts.length > 0) method = getMethod(melatiContext, nulled(parts[parts.length - 1]));
      melatiContext.setMethod(method);
    
      doConfiguredRequest(melatiContext);
      // send the output to the client
      melatiContext.write();
    } catch (Exception e) {
      error(response,e);
    }
  }                                                                                
    
  /**
   * Send an error message
   */
  protected void error( HttpServletResponse response, Exception e )
    throws IOException {
       // has it been trapped already, if so, we don't need to relog it here
      if (!(e instanceof TrappedException)) {
        // log it
        e.printStackTrace(System.err);
        // and put it on the page
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><head><title>Melati Error</title></head><body><h2>Melati Error</h2>");
        out.println("<p>An error has occured in the application that runs this website, please ");
        out.println("contact <a href='mailto:" + getSysAdminEmail() + "'>" + getSysAdminName() + "</a>");
        out.println(", with the information given below.</p>");
        out.println("<h4><font color=red><pre>" );
        e.printStackTrace(out);
        out.println("</pre></font></h4></body></html>");
    }
  }

  /* 
  * please override these settings
  */
  public String getSysAdminName() {
    return "nobody";
  }
  public String getSysAdminEmail() {
    return "nobody@nobody.com";
  }

  // override this to set your method to something other than is provided in pathinfo
  public String getMethod(MelatiContext melatiContextIn, String methodIn) {
    return methodIn;
  }

  // null a string
  private static String nulled(String s) {
    if (s.equals("")) return null;
    return s;
  }

  /**
   * Override the method to build up your output
   * @param melatiContext 
   */
  protected abstract void doConfiguredRequest(MelatiContext melatiContext) 
    throws Exception;

}






