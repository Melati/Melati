/*
 * $Source$
 * $Revision$
 *
 * Copyright (C) 2003 Tim Pizey
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
 *     Tim Pizey <timp@paneris.org>
 */
package org.melati.servlet;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.HttpJspPage;

import org.melati.Melati;
import org.melati.MelatiConfig;
import org.melati.poem.AccessToken;
import org.melati.poem.Database;
import org.melati.poem.PoemTask;
import org.melati.util.DatabaseInitException;
import org.melati.util.MelatiException;
import org.melati.util.StringUtils;

/**
 * This is a TOY and does not represent the proper way to use Melati as Melati
 * was designed with a template engine in mind.
 * 
 * To use extend this class to
 * 
 * @author timp@paneris.org
 *  
 */
public abstract class JspServlet extends HttpServlet implements HttpJspPage {

  static MelatiConfig melatiConfig;

  /**
   * Initialise Melati.
   * 
   * @param config a <code>ServletConfig</code>
   * @throws ServletException is anything goes wrong
   */
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
    try {
      melatiConfig = getMelatiConfig();
    } catch (MelatiException e) {
      // log it to system.err as ServletExceptions go to the
      // servlet runner log (eg jserv.log), and don't have a stack trace!
      e.printStackTrace(System.err);
      throw new ServletException(e.toString());
    }
    jspInit();
    _jspInit();
  }

  /**
   * Override this to tailor your configuration.
   * 
   * @return a configured MelatiConfig
   */
  protected MelatiConfig getMelatiConfig() throws MelatiException {
    MelatiConfig m = new MelatiConfig();
    return m;
  }

  /*
   * @see javax.servlet.jsp.JspPage#jspInit()
   */
  public void jspInit() {
  }

  public void _jspInit() {
  }

  /*
   * @see javax.servlet.Servlet#getServletInfo()
   */
  public String getServletInfo() {
    return "org.wafer.weblog.melati.jsp.JspServlet - " + "timp@paneris.org - "
        + "21/10/2003";
  }

  /*
   * @see javax.servlet.Servlet#destroy()
   */
  public final void destroy() {
    jspDestroy();
    _jspDestroy();
  }

  /*
   * @see javax.servlet.jsp.JspPage#jspDestroy()
   */
  public void jspDestroy() {
  }

  protected void _jspDestroy() {
  }

  /**
   * This method is overridden by the code generated from the .jsp file.
   * 
   * @see javax.servlet.jsp.HttpJspPage#_jspService(javax.servlet.http.HttpServletRequest,
   *      javax.servlet.http.HttpServletResponse)
   */
  public abstract void _jspService(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException;

  /**
   * Run the generated code within a database session. Poem requires that any
   * access to the db be associated with a user. We just supply it with the
   * superuser to bypass Melati's access handling.
   * 
   * @see javax.servlet.http.HttpServlet#service(javax.servlet.http.HttpServletRequest,
   *      javax.servlet.http.HttpServletResponse)
   */
  public final void service(final HttpServletRequest request,
      final HttpServletResponse response) throws ServletException {
    final Melati melati;
    melati = melatiConfig.getMelati(request, response);
    MelatiContext mc = getMelatiContext();
    try {
      melati.setContext(mc);
    } catch (DatabaseInitException e) {
      e.printStackTrace(System.err);
      throw new ServletException(e.toString());
    }

    Database db = melati.getDatabase();
    db.inSession(AccessToken.root, new PoemTask() {
      public void run() {
        // Uncomment to use Melati's access handling
        // melatiConfig.getAccessHandler().establishUser(melati);
        try {
          _jspService(request, response);
        } catch (Exception e) {
          Throwable cause = e.getCause();
          if (cause != null) {
            cause.printStackTrace(System.err);
            throw new RuntimeException(cause.getMessage());
          }
          e.printStackTrace(System.err);
          throw new RuntimeException(e.toString() + " : " + e.getMessage());
        }
      }
    });
  }

  /**
   * Override this to supply a MelatiContext with at least a database field
   * filled. <code>
   *    protected MelatiContext getMelatiContext() {
   *     return new getMelatiContext("mydatabase");
   *    }
   * </code>
   * 
   * @return a new context
   */
  protected MelatiContext getMelatiContext() {
    return new MelatiContext();
  }

  protected MelatiContext getMelatiContext(String logicalDatabase) {
    MelatiContext it = new MelatiContext();
    it.logicalDatabase = logicalDatabase;
    return it;
  }

  protected MelatiContext getMelatiContext(String logicalDatabase,
      String pathInfo) throws PathInfoException {
    MelatiContext it = getMelatiContext(logicalDatabase);
    String[] pathInfoParts = StringUtils.split(pathInfo, '/');
    if (pathInfoParts.length > 0) {
      if (pathInfoParts.length == 1)
        it.method = pathInfoParts[0];
      if (pathInfoParts.length == 2) {
        it.table = pathInfoParts[0];
        it.method = pathInfoParts[1];
      }
      if (pathInfoParts.length >= 3) {
        it.table = pathInfoParts[0];
        try {
          it.troid = new Integer(pathInfoParts[1]);
        } catch (NumberFormatException e) {
          throw new PathInfoException(pathInfo, e);
        }
        it.method = pathInfoParts[2];
      }
    }
    return it;
  }

}

