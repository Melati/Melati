package org.melati;

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
      OVERLAY_PARAMETERS = "org.melati.MelatiServlet.overlayParameters",
      USER = "org.melati.MelatiServlet.user";

  /**
   * The class name of the class implementing the login servlet.  Unless
   * overridden, this is <TT>org.melati.Login</TT>.
   *
   * @see org.melati.Login
   */

  protected String loginPageServletClassName() {
    return "org.melati.Login";
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

  protected String loginPageURL(HttpServletRequest request) {
    StringBuffer url = new StringBuffer();
    url.append(request.getScheme());
    url.append("://");
    url.append(request.getServerName());
    if (request.getScheme().equals("http") && request.getServerPort() != 80 ||
        request.getScheme().equals("https") && request.getServerPort() != 443) {
      url.append(':');
      url.append(request.getServerPort());
    }

    String servlet = request.getServletPath();
    if (servlet != null)
      url.append(servlet.substring(0, servlet.lastIndexOf('/') + 1));

    url.append(loginPageServletClassName());
    url.append('/');
    // FIXME cut the front off the pathinfo to retrieve the DB name
    String pathInfo = request.getPathInfo();
    url.append(pathInfo.substring(1, pathInfo.indexOf('/', 1) + 1));

    return url.toString();
  }

  public Template handleAccessException(WebContext context,
					AccessPoemException accessException)
      throws Exception {
    accessException.printStackTrace();

    HttpServletRequest request = context.getRequest();
    HttpServletResponse response = context.getResponse();

    HttpSession session = request.getSession(true);

    session.putValue(Login.TRIGGERING_REQUEST_PARAMETERS,
		     new HttpServletRequestParameters(request));
    session.putValue(Login.TRIGGERING_EXCEPTION, accessException);

    try {
      response.sendRedirect(loginPageURL(request));
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
          context = context.clone(
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
