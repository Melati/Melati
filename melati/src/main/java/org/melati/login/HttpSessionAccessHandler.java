/*
 * $Source$
 * $Revision$
 *
 * Copyright (C) 2000 William Chesters
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
 *     William Chesters <williamc@paneris.org>
 *     http://paneris.org/~williamc
 *     Obrechtstraat 114, 2517VX Den Haag, The Netherlands
 */

package org.melati.login;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.melati.poem.AccessPoemException;
import org.melati.poem.PoemException;
import org.melati.poem.PoemThread;
import org.melati.poem.User;
import org.melati.Melati;
import org.melati.util.HttpUtil;
import org.melati.util.HttpServletRequestParameters;
import org.melati.util.ReconstructedHttpServletRequestMismatchException;
import org.melati.util.ReconstructedHttpServletRequest;

public class HttpSessionAccessHandler implements AccessHandler {

  public static final String
  OVERLAY_PARAMETERS =
  "org.melati.HttpSessionAccessHandler.overlayParameters",
  USER = "org.melati.HttpSessionAccessHandler.user";

  /**
   * The class name of the class implementing the login servlet.  Unless
   * overridden, this is <TT>org.melati.Login</TT>.
   *
   * @see org.melati.Login
   */

  protected String loginPageServletClassName() {
    return "org.melati.login.Login";
  }

  /**
   * The URL of the login servlet.  Unless overridden, this is computed by
   * substituting <TT>loginPageServletClassName()</TT> into the URL of the
   * request being serviced.
   *
   * @param request	the request currently being serviced
   *
   * @see #loginPageServletClassName
   */

  public String loginPageURL(Melati melati, HttpServletRequest request) {
    StringBuffer url = new StringBuffer();
    HttpUtil.appendZoneURL(url, request);
    url.append('/');
    url.append(loginPageServletClassName());
    url.append('/');
    url.append(melati.getContext().logicalDatabase);
    url.append('/');

    return url.toString();
  }


  public void handleAccessException(Melati context, 
                         AccessPoemException accessException) throws Exception {
    accessException.printStackTrace();
    HttpServletRequest request = context.getRequest();
    HttpServletResponse response = context.getResponse();
    HttpSession session = request.getSession(true);
    session.putValue(Login.TRIGGERING_REQUEST_PARAMETERS,
                     new HttpServletRequestParameters(request));
    session.putValue(Login.TRIGGERING_EXCEPTION, accessException);
    response.sendRedirect(loginPageURL(context, request));
  }

  /**
   * @return the <TT>WebContext</TT> to use in processing the request; can
   *         just be <TT>context</TT>, or something derived from
   *         <TT>context</TT>, or <TT>null</TT> if the routine has already
   *         handled the request (<I>e.g.</I> by sending back an error)
   */

  public Melati establishUser(Melati melati) throws IOException {
    HttpSession session = melati.getSession();
    synchronized (session) {
      User user = (User)session.getValue(USER);
      PoemThread.setAccessToken(
      user == null ? melati.getDatabase().guestAccessToken() : user);
    }
    return melati;
  }

  public void buildRequest(Melati melati) 
      throws ReconstructedHttpServletRequestMismatchException {
    HttpSession session = melati.getSession();

    // First off, is the user continuing after a login?  If so, we want to
    // recover any POSTed fields from the request that triggered it.

    synchronized (session) {
      HttpServletRequestParameters oldParams =
          (HttpServletRequestParameters)session.getValue(OVERLAY_PARAMETERS);

      if (oldParams != null) {
        session.removeValue(OVERLAY_PARAMETERS);

        // we don't want to create a new object here, rather we are simply 
        // going to set up the old request parameters

        melati.setRequest(
            new ReconstructedHttpServletRequest(oldParams, melati.getRequest()));
      }
    }
  }
}
