/*
 * $Source$
 * $Revision$
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

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import org.webmacro.*;
import org.webmacro.util.*;
import org.webmacro.engine.*;
import org.webmacro.servlet.*;
import org.melati.util.*;
import org.melati.*;
import org.melati.poem.*;

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

  public String loginPageURL(MelatiContext melati, HttpServletRequest request) {
    StringBuffer url = new StringBuffer();
    HttpUtil.appendZoneURL(url, request);
    url.append('/');
    url.append(loginPageServletClassName());
    url.append('/');
    url.append(melati.logicalDatabase);
    url.append('/');

    return url.toString();
  }

  
  public Template handleAccessException(MelatiContext melati,
                                        WebContext context,
					AccessPoemException accessException)
      throws Exception {
    HttpServletRequest request = context.getRequest();
    HttpServletResponse response = context.getResponse();

    HttpSession session = request.getSession(true);

    session.putValue(Login.TRIGGERING_REQUEST_PARAMETERS,
		     new HttpServletRequestParameters(request));
    session.putValue(Login.TRIGGERING_EXCEPTION, accessException);

    try {
      response.sendRedirect(loginPageURL(melati, request));
    }
    catch (IOException e) {
      throw new HandlerException(e.toString());
    }

    return null;
  }

  /**
   * @return the <TT>WebContext</TT> to use in processing the request; can
   *         just be <TT>context</TT>, or something derived from
   *         <TT>context</TT>, or <TT>null</TT> if the routine has already
   *         handled the request (<I>e.g.</I> by sending back an error)
   */

  public WebContext establishUser(WebContext context, Database database)
      throws PoemException, IOException, ServletException {
    HttpSession session = context.getSession();

    // First off, is the user continuing after a login?  If so, we want to
    // recover any POSTed fields from the request that triggered it.

    synchronized (session) {
      HttpServletRequestParameters oldParams =
          (HttpServletRequestParameters)session.getValue(OVERLAY_PARAMETERS);
      if (oldParams != null) {
        session.removeValue(OVERLAY_PARAMETERS);
        try {
          context = context.newInstance(
              new ReconstructedHttpServletRequest(oldParams,
                                                  context.getRequest()),
              context.getResponse());
        }
        catch (ReconstructedHttpServletRequestMismatchException e) {
        }
      }

      User user = (User)session.getValue(USER);
      PoemThread.setAccessToken(
	  user == null ? database.guestAccessToken() : user);
    }

    return context;
  }
}
