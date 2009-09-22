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

package org.melati.servlet;

import java.io.PrintWriter;
import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.melati.Melati;
import org.melati.PoemContext;
import org.melati.MelatiConfig;
import org.melati.poem.AccessPoemException;
import org.melati.poem.NoSuchRowPoemException;
import org.melati.util.ConnectionPendingException;
import org.melati.util.MelatiWriter;

/**
 * Config Servlet is the simplest way to use Melati.
 *
 * All a ConfigServlet does is to configure a melati and combine the
 * doGet and doPost methods.  Importantly it does not establish a poem session
 * leaving you to do this for yourself.
 *
 * If you want a poem session established, please extend PoemServlet.
 *
 * ConfigServlet does set up a basic PoemContext with the Method set,
 * but not the POEM logicaldatabase, table or troid.
 *
 * The URL is expected to take one of the following form:
 *
 * <BLOCKQUOTE><TT>
 * http://<I>h</I>/<I>s</I>/<I>meth</I>
 * </TT></BLOCKQUOTE>
 *
 * the method is broken out of the path info and passed to
 * your application code in the <TT>Melati</TT> and
 * <TT>PoemContext</TT> parameter
 *
 * <TABLE>
 *   <TR>
 *     <TD><TT><I>h</I></TT></TD>
 *     <TD>host name, such as <TT>www.melati.org</TT></TD>
 *   </TR>
 *   <TR>
 *     <TD><TT><I>s</I></TT></TD>
 *     <TD>
 *       servlet-determining part, such as
 *       <TT>melati/org.melati.admin.Admin</TT>
 *     </TD>
 *   </TR>
 *   <TR>
 *     <TD><TT><I>meth</I></TT></TD>
 *     <TD>
 *       A freeform string telling your servlet what it is meant to do.  This
 *       is automatically made available in templates as
 *       <TT>$melati.Method</TT>.
 *     </TD>
 *   </TR>
 * </TABLE>
 *
 * You can change the way these things are determined by overriding
 * <TT>poemContext(Melati)</TT>.
 */

public abstract class ConfigServlet extends HttpServlet {

  /**
   * Eclipse generated.
   */
  private static final long serialVersionUID = 8995954958766276122L;
  
  protected MelatiConfig melatiConfig;
  protected String sysAdminName = "nobody";
  protected String sysAdminEmail = "nobody@nobody.com";;
  
  /**
   * Inititialise Melati.
   *
   * @param config a <code>ServletConfig</code>
   * @throws ServletException is anything goes wrong
   */
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
    try {
      melatiConfig = melatiConfig();
    } catch (Exception e) {
      // log it to system.err as ServletExceptions go to the
      // servlet runner log (eg jserv.log), and don't have a stack trace!
      e.printStackTrace(System.err);
      throw new ServletException(e.toString ());
    }
  }

  /**
   * Handles GET.
   *
   * @param request the incoming <code>HttpServletRequest</code>
   * @param response the outgoing <code>HttpServletResponse</code>
   */
  public void doGet(HttpServletRequest request, 
                    HttpServletResponse response) {
    doGetPostRequest(request, response);
  }

  /**
   * Handle a POST.
   *
   * @param request the incoming <code>HttpServletRequest</code>
   * @param response the outgoing <code>HttpServletResponse</code>
   */
  public void doPost(HttpServletRequest request, 
                     HttpServletResponse response) {
    doGetPostRequest(request, response);
  }

  /**
   * Process the request.
   *
   * Exceptions are presented to the user if practicable, or written to the log. 
   * 
   * @param request the incoming <code>HttpServletRequest</code>
   * @param response the outgoing <code>HttpServletResponse</code>
   */
  private void doGetPostRequest(final HttpServletRequest request, 
                                final HttpServletResponse response) {
    Melati melati = new Melati(melatiConfig, request, response);
    try {
      melati.establishCharsets();
      PoemContext poemContext = poemContext(melati);
      melati.setPoemContext(poemContext);
      doConfiguredRequest(melati);
      // send the output to the client
      melati.write();
    }
    catch (Exception e) {
      error(melati,e);
    }
  }

  /**
   * Send an error message.
   *
   * @param melati the {@link Melati}
   * @param e      the {@link Exception} to report
   */
  public void error(Melati melati, Exception e) {
    melati.getResponse().setStatus(httpStatusCode(e));
    
    // has it been trapped already, if so, we don't need to relog it here
    if (! (e instanceof TrappedException)) {
      try { 
        // log it
        e.printStackTrace(System.err);
        // and put it on the page
        melati.setResponseContentType ("text/html");
        MelatiWriter mw =  melati.getWriter();
        // get rid of anything that has been written so far
        mw.reset();
        PrintWriter out = new PrintWriter(mw.getWriter());
        if (e instanceof ConnectionPendingException) {
          writeConnectionPendingException(out,e);
        } else {
          writeError(out,e);
        }
        melati.write();
      } catch (IOException f) {
        e.printStackTrace(System.err);
        throw new TrappedException("Problem logging error", f);
      }
    }
  }
  
  protected int httpStatusCode(Exception e) {
    if (e instanceof AccessPoemException)
      return 401; // Not Authorized
    if (e instanceof InvalidUsageException)
      return 400; // Client error
    if (e instanceof NoSuchRowPoemException)
      return 404; // Not found
    return 500; // Server error
  }

  /**
   * Print an error directly to the client.
   *
   * This is rarely called, eg when the template engine 
   * fails to render the default error template.
   *
   * @param out the <code>PrintWriter</code> to print to 
   * @param e   the {@link Exception} to report
   */
  public void writeError(PrintWriter out, Exception e) {
    out.println("<html><head><title>Melati Error</title></head>");
    out.println("<!-- HTML generated in " + 
                "org.melati.servlet.ConfigServlet.java -->");
    out.println("<body><h2>Melati Error</h2>");
    out.println("<h3>Reported from ConfigServlet</h3>");
    out.println("<p>An error has occured in the application"); 
    out.println("that runs this website, please contact <a href='mailto:");
    out.println(getSysAdminEmail() + "'>" + getSysAdminName() + "</a>");
    out.println(", with the information given below.</p>");
    out.println("<h4><font color='red'><pre>");
    e.printStackTrace(out);
    out.println("</pre></font></h4></body></html>");
  }    
  
  /**
   * Print the <code>ConnectionPendingException</code>  directly to the client.
   *
   * This is called if a request is made whilst the system is 
   * still being initialised.
   *
   * @param out the <code>PrintWriter</code> to print to 
   * @param e   the {@link Exception} to report
   */
  public void writeConnectionPendingException(PrintWriter out, Exception e) {
    out.println("<html><head><title>Database Initialising</title>\n");
    out.println("<META HTTP-EQUIV='Refresh' CONTENT='30'>\n</head>\n");
    out.println("<!-- Generated in org.melati.servlet.ConfigServlet.java -->");
    out.println("<body><center><h2>Database Initialising</h2><p>&nbsp;</p>");
    out.println("<p><b>Sorry</b>, ");
    out.println("the database that runs this website is just starting up.");
    out.println("This takes a few seconds, ");
    out.println("so you should be able to use the site in a moment.");
    out.println("<p>This page will refresh in 30 seconds, ");
    out.println("and you will be able to continue.</p>");
    out.println("<!--");
    e.printStackTrace(out);
    out.println("--></center></body></html>");
  }    

  /** 
   * This method <b>SHOULD</b> be overidden.
   * @return the System Administrators name.
   */
  public String getSysAdminName () {
    return sysAdminName;
  }

  /** 
   * This method <b>SHOULD</b> be overidden.
   * @return the System Administrators email address.
   */
  public String getSysAdminEmail () {
    return sysAdminEmail;
  }

  /**
   * @param sysAdminEmail The sysAdminEmail to set.
   */
  protected void setSysAdminEmail(String sysAdminEmail) {
    this.sysAdminEmail = sysAdminEmail;
  }

  
  /**
   * @param sysAdminName The sysAdminName to set.
   */
  protected void setSysAdminName(String sysAdminName) {
    this.sysAdminName = sysAdminName;
  }

  protected PoemContext poemContext(Melati melati) 
      throws PathInfoException {
    PoemContext it = new PoemContext();
    String[] parts = melati.getPathInfoParts();
    if (parts.length > 0)
     it.setMethod(parts[parts.length - 1]);
   return it;
 }
  
  /** 
   * To override any setting from org.melati.MelatiConfig.properties,
   * simply override this method and return a valid MelatiConfig.
   *
   * eg to use a different AccessHandler from the default:
   *
   * <PRE>
   *   protected MelatiConfig melatiConfig() throws MelatiException {
   *     MelatiConfig config = super.melatiConfig();
   *     config.setAccessHandler(new YourAccessHandler());
   *     return config;
   *   }
   * </PRE>
   * 
   * @return a new {@link MelatiConfig}
   */
  protected MelatiConfig melatiConfig() {
    MelatiConfig m = new MelatiConfig();
    String realPath = getServletConfig().getServletContext().getRealPath("/");
    if (realPath == null)
      throw new NullPointerException();
    m.setRealPath(realPath);
    return m;
  }
  
  /**
   * Instantiate this method to build up your own output.
   * @param melati
   * @throws Exception if anything goes wrong
   */
  protected abstract void doConfiguredRequest(Melati melati)
      throws Exception;

  
}
