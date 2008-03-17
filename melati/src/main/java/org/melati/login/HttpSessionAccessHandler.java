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
 *     William Chesters <williamc At paneris.org>
 *     http://paneris.org/~williamc
 *     Obrechtstraat 114, 2517VX Den Haag, The Netherlands
 */

package org.melati.login;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.melati.Melati;
import org.melati.poem.AccessPoemException;
import org.melati.poem.PoemThread;
import org.melati.poem.User;
import org.melati.util.HttpServletRequestParameters;
import org.melati.util.HttpUtil;
import org.melati.util.MD5Util;
import org.melati.util.ReconstructedHttpServletRequest;
import org.melati.util.ReconstructedHttpServletRequestMismatchException;
import org.melati.util.UTF8URLEncoder;

/**
 * An {@link AccessHandler} which uses <code>Session</code> cookies to
 * elicit and maintain the user's login and password.  
 */
public class HttpSessionAccessHandler implements AccessHandler {

  /** Class name. */
  public static final String
     OVERLAY_PARAMETERS = 
       "org.melati.login.HttpSessionAccessHandler.overlayParameters";
  /** Class name. */
  public static final String
     USER = 
       "org.melati.login.HttpSessionAccessHandler.user";

  /**
   * The class name of the class implementing the login servlet.  Unless
   * overridden, this is <TT>org.melati.login.Login</TT>.
   *
   * @return the class name of login servlet
   * @see org.melati.login.Login
   */
  protected String loginPageServletClassName() {
    return "org.melati.login.Login";
  }

  /**
   * The URL of the login servlet.  Unless overridden, this is computed by
   * substituting {@link #loginPageServletClassName()} into the URL of the
   * request being serviced.
   *
   * @param melati   the current Melati
   * @param request  the request currently being serviced
   *
   * @return the login page url
   * @see #loginPageServletClassName
   */
  public String loginPageURL(Melati melati, HttpServletRequest request) {
    StringBuffer url = new StringBuffer();
    HttpUtil.appendRelativeZoneURL(url, request);
    url.append('/');
    url.append(loginPageServletClassName());
    url.append('/');
    url.append(melati.getPoemContext().getLogicalDatabase());
    url.append('/');

    return url.toString();
  }


  /**
   * Store the current request and redirect to the login page. 
   * 
   * {@inheritDoc}
   * @see org.melati.login.AccessHandler#handleAccessException
   */
  public void handleAccessException(Melati melati, 
                                    AccessPoemException accessException) 
      throws Exception {
    HttpServletRequest request = melati.getRequest();
    HttpServletResponse response = melati.getResponse();
    HttpSession session = request.getSession(true);
    session.setAttribute(Login.TRIGGERING_REQUEST_PARAMETERS,
                     new HttpServletRequestParameters(request));
    session.setAttribute(Login.TRIGGERING_EXCEPTION, accessException);
    melati.getWriter().reset();
    response.sendRedirect(loginPageURL(melati, request));
  }

  /**
   * Set the Access token to be used for this request.  
   *
   * The Access Token is eithier picked up from the session, or from a cookie. 
   * The cookie is keyed on the logical database and is used to 
   * retrieve the user's login. 
   * The login is used (with the logical database name) to retrieve an encoded
   * password which is then checked.
   *
   * {@inheritDoc}
   * @see org.melati.login.AccessHandler#establishUser(org.melati.Melati)
   */
  public Melati establishUser(Melati melati) {
    // now when we establish a user, we must also set the cookie
    String ldb = melati.getPoemContext().getLogicalDatabase();
    HttpSession session = melati.getSession();
    synchronized (session) {
      User user = (User)session.getAttribute(USER);
      if (user == null) {
        user = getUserFromCookie(melati,ldb);
        if (user != null) {
          String cookie = getCookieValue(melati,ldb+user.getLogin());
          if (cookie == null || 
              !cookie.equals(MD5Util.encode(user.getPassword())))
            user = null;
        }
      }
      logUsIn(melati,user);
    }
    return melati;
  }
  
  /**
   * Set our AccessToken.
   * NOTE Remember a User isa Token.
   * 
   * @param melati the Melati to get our database from
   * @param user the token to set
   */
  protected void logUsIn(Melati melati, User user) {
    PoemThread.setAccessToken(
        user == null ? melati.getDatabase().guestAccessToken() : user);
  }

  
  /**
   * Extract User via the cookie.
   * @param melati our Melati
   * @param key cookie key
   * @return the found User or null
   */
  User getUserFromCookie(Melati melati,String key) {
    String login = getCookieValue(melati,key);
    if (login == null) return null;
    return (User)melati.getDatabase().getUserTable().getLoginColumn().
              firstWhereEq(login);
  }

  /**
   * Extract a value from the cookies.
   * 
   * @param melati the Melati in which the Request and its cookies are stored
   * @param key the key we need the value of
   * @return the cookie value or null
   */
  String getCookieValue(Melati melati,String key) {
    // try and get from cookie
    // Use default encoding, regardless of user's encoding
    key = UTF8URLEncoder.encode(key);
    Cookie[] cookies = melati.getRequest().getCookies();
    if(cookies == null) return null;
    for (int i=0; i<cookies.length; i++) {
      Cookie c = cookies[i];
        if (c.getName().equals(key)) 
          return UTF8URLEncoder.decode(c.getValue());
    }
    return null;
  }

  /**
   * If we are returning from a login rebuild the original request,
   * otherwise do nothing.
   *  
   * {@inheritDoc}
   * @see org.melati.login.AccessHandler#buildRequest(org.melati.Melati)
   */
  public void buildRequest(Melati melati) 
      throws ReconstructedHttpServletRequestMismatchException {
    HttpSession session = melati.getSession();

    // First off, is the user continuing after a login?  If so, we want to
    // recover any POSTed fields from the request that triggered it.

    synchronized (session) {
      HttpServletRequestParameters oldParams =
          (HttpServletRequestParameters)session.getAttribute(OVERLAY_PARAMETERS);

      if (oldParams != null) {
        session.removeAttribute(OVERLAY_PARAMETERS);

        // we don't want to create a new object here, rather we are simply 
        // going to set up the old request parameters

        melati.setRequest(
            new ReconstructedHttpServletRequest(oldParams, 
                                                melati.getRequest()));
      }
    }
  }
}
