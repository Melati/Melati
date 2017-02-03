/*
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

import org.melati.Melati;
import org.melati.PoemContext;
import org.melati.poem.*;
import org.melati.poem.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Base class to use Poem with Servlets.
 * <p>
 * Simply extend this class and override the doPoemRequest method. If you are
 * going to use a template engine look at TemplateServlet.
 * <UL>
 * <LI> <A NAME=pathinfoscan>By default, the path info of the URL by which the
 * servlet was called up is examined to determine the `logical name' of the
 * Melati POEM database to which the servlet should connect, and possibly a
 * table within that database, an object within that table, and a `method' to
 * apply to that object.</A> The URL is expected to take one of the following
 * forms: <BLOCKQUOTE><TT> http://<I>h</I>/<I>s</I>/<I>db</I>/ <BR>
 * http://<I>h</I>/<I>s</I>/<I>db</I>/<I>meth</I> <BR>
 * http://<I>h</I>/<I>s</I>/<I>db</I>/<I>tbl</I>/<I>meth</I> <BR>
 * http://<I>h</I>/<I>s</I>/<I>db</I>/<I>tbl</I>/<I>troid</I>/<I>meth</I>
 * <BR>
 * http://<I>h</I>/<I>s</I>/<I>db</I>/<I>tbl</I>/<I>troid</I>/<I>meth</I>/<I>other</I>
 * </TT></BLOCKQUOTE> and the following components are broken out of the path
 * info and passed to your application code in the <TT>melati</TT> parameter
 * (which is also copied automatically into <TT>context</TT> so that it is
 * easily available in templates): 
 * <TABLE>
 *   <caption>Path context elements</caption>
 * <TR>
 * <TD><TT><I>h</I></TT></TD>
 * <TD>host name, such as <TT>www.melati.org</TT></TD>
 * </TR>
 * <TR>
 * <TD><TT><I>s</I></TT></TD>
 * <TD> servlet-determining part, such as <TT>melati/org.melati.admin.Admin</TT>
 * </TD>
 * </TR>
 * <TR>
 * <TD><TT><I>db</I></TT></TD>
 * <TD> The first element of the path info is taken to be the `logical name' of
 * the Melati POEM database to which the servlet should connect. It is mapped
 * onto JDBC connection details via the config file <TT>org.melati.LogicalDatabase.properties</TT>,
 * of which there is an example in the source tree. This is automatically made
 * available in templates as <TT>$melati.Database</TT>. </TD>
 * <TR>
 * <TD><TT><I>tbl</I></TT></TD>
 * <TD> The DBMS name of a table with which the servlet is concerned: perhaps it
 * is meant to list its contents. This is automatically made available in
 * templates as <TT>$melati.Table</TT>. </TD>
 * </TR>
 * <TR>
 * <TD><TT><I>troid</I></TT></TD>
 * <TD> The POEM `troid' (table row identifier, or row-unique integer) of a row
 * within <TT><I>tbl</I></TT> with which the servlet is concerned: perhaps it
 * is meant to display it. This is automatically made available in templates as
 * <TT>$melati.Object</TT>. </TD>
 * </TR>
 * <TR>
 * <TD><TT><I>meth</I></TT></TD>
 * <TD> A freeform string telling your servlet what it is meant to do. This is
 * automatically made available in templates as <TT>$melati.Method</TT>.
 * </TD>
 * </TR>
 * <TR>
 * <TD><TT><I>other</I></TT></TD>
 * <TD> Any other information you wish to put in the pathinfo. This is useful,
 * for instance, if you wish to specify the &quot;filename&quot; of your
 * servlet. For instance, if you call <TT>/db/myfiles/0/Download/afile.html</TT>
 * and return a stream with a content-type of <tt>application/octet-stream</tt>
 * most browsers will prompt you to save the &quot;file&quot; as
 * <tt>afile.html</tt> </TD>
 * </TR>
 * </TABLE>
 * <LI> You can change the way these things are determined by overriding <TT>poemContext</TT>.
 * <LI> Any POEM database operations you perform will be done with the access
 * rights of the POEM <TT>User</TT> associated with the servlet session. If
 * there is no established servlet session, the current user will be set to the
 * default `guest' user. If this method terminates with an <TT>AccessPoemException</TT>,
 * indicating that you have attempted something which you aren't entitled to do,
 * the user will be prompted to log in, and the original request will be
 * retried. The precise mechanism used for login is <A
 * HREF=#loginmechanism>configurable</A>.
 * <LI>
 *  No changes made to the database by other concurrently executing threads
 * will be visible to you (in the sense that once you have seen a particular
 * version of a record, you will always subsequently see the same one), and your
 * own changes will not be made permanent until this method completes
 * successfully or you perform an explicit <TT>PoemThread.commit()</TT>. If
 * it terminates with an exception or you issue a <TT>PoemThread.rollback()</TT>,
 * your changes will be lost.
 * <LI> <A NAME=loginmechanism>
 * It's possible to configure how your <TT>PoemServlet</TT>-derived
 * servlets implement user login.</A> If the properties file <TT><A
 * HREF=../org.melati.MelatiConfig.properties>
 * org.melati.MelatiConfig.properties</A></TT> exists and contains a setting
 * <TT>org.melati.MelatiConfig.accessHandler=<I>foo</I></TT>, then <TT><I>foo</I></TT>
 * is taken to be the name of a class implementing the <TT>AccessHandler</TT>
 * interface. The default is <TT>HttpSessionAccessHandler</TT>, which stores
 * the user id in the servlet session, and redirects to the <TT>Login</TT>
 * servlet to throw up templated login screens. If instead you specify 
 * <TT>HttpBasicAuthenticationAccessHandler</TT>, the user id is maintained 
 * using HTTP Basic Authentication (RFC2068 11.1, the
 * mechanism commonly used to password-protect static pages), and the task of
 * popping up login dialogs is delegated to the browser. The advantage of the
 * former method is that the user gets a more informative interface which is
 * more under the designer's control; the advantage of the latter method is that
 * no cookies or URL rewriting are required---for instance it is probably more
 * appropriate for WAP phones. Both methods involve sending the user's password
 * in plain text across the public network.
 * </UL>
 * 
 * @see org.melati.poem.Database#guestAccessToken
 * @see org.melati.poem.PoemThread#commit
 * @see org.melati.poem.PoemThread#rollback
 * @see #poemContext
 * @see org.melati.login.AccessHandler
 * @see org.melati.login.HttpSessionAccessHandler
 * @see org.melati.login.Login
 * @see org.melati.login.HttpBasicAuthenticationAccessHandler
 */

public abstract class PoemServlet extends ConfigServlet {

  /**
   * Eclipse generated.
   */
  private static final long serialVersionUID = 7694978400584943446L;

  /**
   * A place to do things before entering the session 
   * of the user, here is a good place to use root access token.
   * 
   * Overriden in TemplateServlet.
   * 
   * @param melati
   *          org.melati.Melati A source of information about the Melati
   *          database context (database, table, object) and utility objects
   *          such as error handlers.
   */

  protected void prePoemSession(Melati melati) throws Exception {
  }

  /**
   * @see javax.servlet.Servlet#destroy()
   */
  public void destroy() {
    super.destroy();
  }

  /**
   * Process the request.
   */

  protected void doConfiguredRequest(final Melati melati)
      throws ServletException, IOException {

    // Set up a POEM session and call the application code

    // Do something outside of the PoemSession
    try {
      melati.getConfig().getAccessHandler().buildRequest(melati);
      prePoemSession(melati);
    } catch (Exception e) {
        // we have to log this here, otherwise we lose the stacktrace
        error(melati, e);
        throw new TrappedException("Problem in prePoemSession", e);
    }

    final PoemServlet _this = this;

    melati.getDatabase().inSession(AccessToken.root, new PoemTask() {
      @SuppressWarnings("unchecked")
      public void run() {
        String poemAdministratorsName;
        String poemAdministratorsEmail;

        try {
          try {
            poemAdministratorsName = melati.getDatabase().administratorUser().getName();
            Field<String> emailField;
            try {
              emailField = (Field<String>)melati.getDatabase().administratorUser().getField("email");
              poemAdministratorsEmail = emailField.toString();
            } catch (NoSuchColumnPoemException e) {
              poemAdministratorsEmail = "noEmailDefined@nobody.com";
            }
            _this.setSysAdminName(poemAdministratorsName);
            _this.setSysAdminEmail(poemAdministratorsEmail);
          } catch (Exception e) {
            _handleException(melati, e);
          }
        } catch (Exception e) {
          // we have to log this here, otherwise we lose the stacktrace
          error(melati, e);
          throw new TrappedException(e);
        }
        
        
        melati.getConfig().getAccessHandler().establishUser(melati);
        melati.loadTableAndObject();

        try {
          try {
            _this.doPoemRequest(melati);
          } catch (Exception e) {
            _handleException(melati, e);
          }
        } catch (Exception e) {
          // we have to log this here, otherwise we lose the stacktrace
          error(melati, e);
          throw new TrappedException(e);
        }
      }

      public String toString() {
        HttpServletRequest request = melati.getRequest();
        return "PoemServlet: "
            + ((request == null) ? "(no request present)" : request
                .getRequestURI());
      }
    });
  }

  /**
   * Override this to provide a different administrator's details to the
   * database admin user.
   * 
   * @return the System Administrators name.
   */
  public String getSysAdminName() {
    return sysAdminName;
  }

  /**
   * Override this to provide a different administrator's details to the
   * database admin user.
   * 
   * @return the System Administrators email address.
   */
  public String getSysAdminEmail() {
    return sysAdminEmail;
  }

  /**
   * Default method to handle an exception without a template engine.
   * 
   * @param melati
   *          the Melati
   * @param exception
   *          the exception to handle
   */
  protected void handleException(Melati melati, Exception exception)
      throws Exception {
    if (exception instanceof AccessPoemException) {
      melati.getConfig().getAccessHandler().handleAccessException(melati,
          (AccessPoemException) exception);
    } else {
      throw exception;
    }
  }

  protected final void _handleException(Melati melati, Exception exception)
      throws Exception {
    try {
      handleException(melati, exception);
    } catch (Exception e) {
      PoemThread.rollback();
      throw e;
    }
  }

  protected PoemContext poemContext(Melati melati) throws PathInfoException {

    PoemContext it = new PoemContext();

    String initParameterPathInfo = getInitParameter("pathInfo");
    String[] parts;
    if (initParameterPathInfo != null)
      parts = StringUtils.split(initParameterPathInfo, '/');
    else
      parts = melati.getPathInfoParts();

    // set it to something in order to provoke meaningful error
    it.setLogicalDatabase("");
    if (parts.length > 0) {
      it.setLogicalDatabase(parts[0]);
      if (parts.length == 2)
        it.setMethod(parts[1]);
      if (parts.length == 3) {
        it.setTable(parts[1]);
        it.setMethod(parts[2]);
      }
      if (parts.length >= 4) {
        it.setTable(parts[1]);
        try {
          it.setTroid(new Integer(parts[2]));
        } catch (NumberFormatException e) {
          throw new PathInfoException(melati.getRequest().getPathInfo(), e);
        }
        if (parts.length == 4) {
          it.setMethod(parts[3]);
        } else {
          String pathInfo = melati.getRequest().getPathInfo();
          pathInfo = pathInfo.substring(1);
          for (int i = 0; i < 3; i++) {
            pathInfo = pathInfo.substring(pathInfo.indexOf("/") + 1);
          }
          it.setMethod(pathInfo);
        }
      }
    }
    return it;
  }

  /*
   * This is provided for convenience, so you don't have to specify the
   * logicaldatabase on the pathinfo. This is a very good idea when writing your
   * applications where you are typically only accessing a single database.
   * Simply override poemContext(Melati melati) thus: 
   * <code> 
   * protected PoemContext poemContext(Melati melati) throws PathInfoException { 
   *   return poemContextWithLDB(melati,"<your logical database name>"); 
   * } 
   * </code>
   */
  protected PoemContext poemContextWithLDB(Melati melati, String logicalDatabase)
      throws PathInfoException {
    PoemContext it = new PoemContext();
    String initParameterPathInfo = getInitParameter("pathInfo");
    String[] parts;
    if (initParameterPathInfo != null)
      parts = StringUtils.split(initParameterPathInfo, '/');
    else
      parts = melati.getPathInfoParts();

    // set it to something in order to provoke meaningful error
    it.setLogicalDatabase(logicalDatabase);
    if (parts.length > 0) {
      if (parts.length == 1)
        it.setMethod(parts[0]);
      if (parts.length == 2) {
        it.setTable(parts[0]);
        it.setMethod(parts[1]);
      }
      if (parts.length >= 3) {
        it.setTable(parts[0]);
        it.setMethod(parts[2]);
        try {
          it.setTroid(new Integer(parts[1]));
        } catch (NumberFormatException e) {
          throw new PathInfoException(melati.getRequest().getPathInfo(), e);
        }
      }
      if (parts.length == 3) {
        it.setMethod(parts[2]);
      } else {
        String pathInfo = melati.getRequest().getPathInfo();
        pathInfo = pathInfo.substring(1);
        for (int i = 0; i < 2; i++) {
          pathInfo = pathInfo.substring(pathInfo.indexOf("/") + 1);
        }
        it.setMethod(pathInfo);
      }

    }
    return it;
  }

  /**
   * Override this method to build up your own output.
   */
  protected abstract void doPoemRequest(Melati melati) throws Exception;

}
