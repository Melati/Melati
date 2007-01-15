/*
 * $Source$
 * $Revision$
 *
 * Copyright (C) 2001 William Chesters
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

package org.melati.util;

import java.util.Map;
import java.util.Enumeration;
import java.util.Locale;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Cookie;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;

/**
 * The <code>HttpServletRequestCompat</code> class enables Melati to compile,
 * without warnings, with the Servlet API versions 2.0, 2.1, 2.2 and 2.3.
 * However, if you use a method which is not in your version of the API then you
 * will get a runtime exception.
 * 
 * @see org.melati.util.DelegatedHttpServletRequest
 */

public final class HttpServletRequestCompat {
  private static final long serialVersionUID = 1L;

  private HttpServletRequestCompat() {
  }

  /** Deprecated in Servlet 2.2 API. */
  private static Method isRequestedSessionIdFromUrl, getRealPath;

  /** New in Servlet 2.2 API. */
  private static Method getUserPrincipal, getContextPath, getHeaders,
      getSession, isRequestedSessionIdFromURL, isUserInRole, getAttributeNames,
      getLocale, getLocales, getRequestDispatcher, isSecure, removeAttribute,
      setAttribute;

  /** New in Servlet 2.3 API. */
  private static Method getRequestURL, setCharacterEncoding, getParameterMap;

  private static Method methodOrNull(Class c, String n, String[] pn) {
    try {
      Class[] p = new Class[pn.length];
      for (int i = 0; i < pn.length; ++i)
        p[i] = Class.forName(pn[i]);
      return c.getMethod(n, p);
    } catch (NoSuchMethodException e) {
      return null;
    } catch (ClassNotFoundException e) {
      return null;
    }
  }

  static {
    try {
      String[] noparams = {};

      Class hsr = Class.forName("javax.servlet.http.HttpServletRequest");
      getUserPrincipal = methodOrNull(hsr, "getUserPrincipal", noparams);
      getContextPath = methodOrNull(hsr, "getContextPath", noparams);
      getHeaders = methodOrNull(hsr, "getHeaders",
          new String[] { "java.lang.String" });
      getSession = methodOrNull(hsr, "getSession", noparams);
      isRequestedSessionIdFromURL = methodOrNull(hsr,
          "isRequestedSessionIdFromURL", noparams);
      isUserInRole = methodOrNull(hsr, "isUserInRole",
          new String[] { "java.lang.String" });
      getAttributeNames = methodOrNull(hsr, "getAttributeNames", noparams);
      getLocale = methodOrNull(hsr, "getLocale", noparams);
      getLocales = methodOrNull(hsr, "getLocales", noparams);
      getRequestDispatcher = methodOrNull(hsr, "getRequestDispatcher",
          new String[] { "java.lang.String" });
      isSecure = methodOrNull(hsr, "isSecure", noparams);
      removeAttribute = methodOrNull(hsr, "removeAttribute",
          new String[] { "java.lang.String" });
      setAttribute = methodOrNull(hsr, "setAttribute", new String[] {
          "java.lang.String", "java.lang.Object" });
    } catch (Exception e) {
      e.printStackTrace();
      throw new Error("org.melati.util.servletcompat.HttpServletRequestCompat"
          + "failed to initialize; contact the Melati developers");
    }
  }

  /**
   * Returns the name of the authentication scheme used to protect the servlet,
   * for example, "BASIC" or "SSL," or <code>null</code> if the servlet was
   * not protected. <p>Same as the value of the CGI variable AUTH_TYPE.
   * 
   * @param it
   *        the HttpServletRequest
   * @return a <code>String</code> specifying the name of the authentication
   *         scheme, or <code>null</code> if the request was not authenticated
   * @see javax.servlet.http.HttpServletRequest#getAuthType()
   */
  public static String getAuthType(HttpServletRequest it) {
    return it.getAuthType();
  }

  /**
   * Returns an array containing all of the <code>Cookie</code> objects the
   * client sent with this request. This method returns <code>null</code> if
   * no cookies were sent.
   * 
   * @param it
   *        the HttpServletRequest
   * @return an array of all the <code>Cookies</code> included with this
   *         request, or <code>null</code> if the request has no cookies
   * @see javax.servlet.http.HttpServletRequest#getCookies()
   */
  public static Cookie[] getCookies(HttpServletRequest it) {
    return it.getCookies();
  }

  /**
   * Returns the value of the specified request header as a <code>long</code>
   * value that represents a <code>Date</code> object. Use this method with
   * headers that contain dates, such as <code>If-Modified-Since</code>. <p>The
   * date is returned as the number of milliseconds since January 1, 1970 GMT.
   * The header name is case insensitive. <p>If the request did not have a
   * header of the specified name, this method returns -1. If the header can't
   * be converted to a date, the method throws an
   * <code>IllegalArgumentException</code>.
   * 
   * @param it
   *        the HttpServletRequest
   * @return a <code>long</code> value representing the date specified in the
   *         header expressed as the number of milliseconds since January 1,
   *         1970 GMT, or -1 if the named header was not included with the
   *         reqest
   * @see javax.servlet.http.HttpServletRequest#getDateHeader(String)
   */
  public static long getDateHeader(HttpServletRequest it, String a) {
    return it.getDateHeader(a);
  }

  /**
   * Returns the value of the specified request header as a <code>String</code>.
   * If the request did not include a header of the specified name, this method
   * returns <code>null</code>. The header name is case insensitive. You can
   * use this method with any request header.
   * 
   * @param it
   *        the HttpServletRequest
   * @return a <code>String</code> containing the value of the requested
   *         header, or <code>null</code> if the request does not have a
   *         header of that name
   * @see javax.servlet.http.HttpServletRequest#getHeader(String)
   */
  public static String getHeader(HttpServletRequest it, String a) {
    return it.getHeader(a);
  }

  /**
   * Returns an enumeration of all the header names this request contains. If
   * the request has no headers, this method returns an empty enumeration. <p>Some
   * servlet containers do not allow do not allow servlets to access headers
   * using this method, in which case this method returns <code>null</code>
   * 
   * @param it
   *        the HttpServletRequest
   * @return an enumeration of all the header names sent with this request; if
   *         the request has no headers, an empty enumeration; if the servlet
   *         container does not allow servlets to use this method,
   *         <code>null</code>
   * @see javax.servlet.http.HttpServletRequest#getHeaderNames()
   */
  public static Enumeration getHeaderNames(HttpServletRequest it) {
    return it.getHeaderNames();
  }

  /**
   * Returns the value of the specified request header as an <code>int</code>.
   * If the request does not have a header of the specified name, this method
   * returns -1. If the header cannot be converted to an integer, this method
   * throws a <code>NumberFormatException</code>. <p>The header name is case
   * insensitive.
   * 
   * @param it
   *        the HttpServletRequest
   * @return an integer expressing the value of the request header or -1 if the
   *         request doesn't have a header of this name
   * @see javax.servlet.http.HttpServletRequest#getIntHeader(String)
   */
  public static int getIntHeader(HttpServletRequest it, String a) {
    return it.getIntHeader(a);
  }

  /**
   * @param it the HttpServletRequest
   * @return      a <code>String</code> 
   *        specifying the name
   *        of the method with which
   *        this request was made
   * @see javax.servlet.http.HttpServletRequest#getMethod()
   */
  public static String getMethod(HttpServletRequest it) {
    return it.getMethod();
  }

  /**
   * @param it
   *        the HttpServletRequest
   * @return    a <code>String</code> specifying 
   *      extra path information that comes
   *      after the servlet path but before
   *      the query string in the request URL;
   *      or <code>null</code> if the URL does not have
   *      any extra path information
   * @see javax.servlet.http.HttpServletRequest#getPathInfo()
   */
  public static String getPathInfo(HttpServletRequest it) {
    return it.getPathInfo();
  }

  /**
   * @param it
   *        the HttpServletRequest
   * @return    a <code>String</code> specifying the
   *      real path, or <code>null</code> if
   *      the URL does not have any extra path
   *      information
   * @see javax.servlet.http.HttpServletRequest#getPathTranslated()
   */
  public static String getPathTranslated(HttpServletRequest it) {
    return it.getPathTranslated();
  }

  /**
   * @param it
   *        the HttpServletRequest
   * @return    a <code>String</code> containing the query
   *      string or <code>null</code> if the URL 
   *      contains no query string
   * @see javax.servlet.http.HttpServletRequest#getQueryString()
   */
  public static String getQueryString(HttpServletRequest it) {
    return it.getQueryString();
  }

  /**
   * @param it
   *        the HttpServletRequest
   * @return    a <code>String</code> specifying the login
   *      of the user making this request, or <code>null</code>
   *      if the user login is not known
   * @see javax.servlet.http.HttpServletRequest#getRemoteUser()
   */
  public static String getRemoteUser(HttpServletRequest it) {
    return it.getRemoteUser();
  }

  /**
   * @param it
   *        the HttpServletRequest
   * @return    a <code>String</code> containing
   *      the part of the URL from the 
   *      protocol name up to the query string
   * @see javax.servlet.http.HttpServletRequest#getRequestURI()
   */
  public static String getRequestURI(HttpServletRequest it) {
    return it.getRequestURI();
  }

  /**
   * @param it
   *        the HttpServletRequest
   * @return    a <code>String</code> specifying the session
   *      ID, or <code>null</code> if the request did
   *      not specify a session ID
   * @see javax.servlet.http.HttpServletRequest#getRequestedSessionId()
   */
  public static String getRequestedSessionId(HttpServletRequest it) {
    return it.getRequestedSessionId();
  }

  /**
   * @param it
   *        the HttpServletRequest
   * @return    a <code>String</code> containing
   *      the name or path of the servlet being
   *      called, as specified in the request URL 
   * @see javax.servlet.http.HttpServletRequest#getServletPath()
   */
  public static String getServletPath(HttpServletRequest it) {
    return it.getServletPath();
  }

  /**
   * @param it
   *        the HttpServletRequest
   * @param a
   * @return    the <code>HttpSession</code> associated
   *      with this request
   * @see javax.servlet.http.HttpServletRequest#getSession()
   */
  public static HttpSession getSession(HttpServletRequest it, boolean a) {
    return it.getSession(a);
  }

  /**
   * @param it
   *        the HttpServletRequest
   * @return      <code>true</code> if this
   *        request has an id for a valid session
   *        in the current session context;
   *        <code>false</code> otherwise
   * @see javax.servlet.http.HttpServletRequest#isRequestedSessionIdValid()
   */
  public static boolean isRequestedSessionIdValid(HttpServletRequest it) {
    return it.isRequestedSessionIdValid();
  }

  /**
   * @param it
   *        the HttpServletRequest
   * @return      <code>true</code> if the session ID
   *        came in as a
   *        cookie; otherwise, <code>false</code>
   * @deprecated Servlet API 2.2
   * @see javax.servlet.http.HttpServletRequest#isRequestedSessionIdFromCookie()
   */
  public static boolean isRequestedSessionIdFromCookie(HttpServletRequest it) {
    return it.isRequestedSessionIdFromCookie();
  }

  // 
  // ============================
  // Servlet API 2.2 extensions
  // ============================
  // 

  /**
   * Thrown when a method that is not available is invoked.
   */
  public static class MissingMethodError extends NoSuchMethodError {
    private static final long serialVersionUID = 1L;

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Throwable#getMessage()
     */
    public String getMessage() {
      return "The application tried to use a method from the "
          + "Servlet API 2.2, but was running against the 2.0 " + "API.";
    }
  }

  private static Object invoke(Method method, HttpServletRequest it,
      Object[] args) {
    if (method == null)
      throw new MissingMethodError();
    else {
      try {
        return method.invoke(it, args);
      } catch (InvocationTargetException e) {
        Throwable f = e.getTargetException();
        if (f instanceof RuntimeException) // they all should be
          throw (RuntimeException)f;
        else if (f instanceof Exception)
          throw new RuntimeException("while invoking a Servlet API method",
                                     (Exception)f);
        else if (f instanceof Error)
          throw (Error)f;
        else {
          f.printStackTrace();
          throw new Error("totally unexpected kind of throwable in "
              + "org.melati.util.servletcompat.HttpServletRequestCompat");
        }
      } catch (IllegalAccessException e) {
        e.printStackTrace();
        throw new Error(
                        "org.melati.util.servletcompat.HttpServletRequestCompat"
                            + "got an unexpected IllegalAccessException trying to "
                            + "invoke a method; contact the Melati developers");
      }
    }
  }

  private static final Object[] noargs = {};

  /**
   * Returns the empty string when run against 2.0 API.
   * 
   * @param it
   *        the HttpServletRequest
   * @return the Context path or empty string
   * @see javax.servlet.http.HttpServletRequest#getContextPath()
   */
  public static String getContextPath(HttpServletRequest it) {
    if (getContextPath == null)
      return "";
    else
      return (String)invoke(getContextPath, it, noargs);
    // return it.getContextPath();
  }

  /**
   * Throws <TT>MissingMethodError</TT> when run against 2.0 API.
   * 
   * @param it
   *        the HttpServletRequest
   * @return    a <code>java.security.Principal</code> containing
   *      the name of the user making this request;
   *      <code>null</code> if the user has not been 
   *      authenticated
   * @see javax.servlet.http.HttpServletRequest#getUserPrincipal()
   */
  public static java.security.Principal getUserPrincipal(HttpServletRequest it) {
    return (java.security.Principal)invoke(getUserPrincipal, it, noargs);
    // return it.getUserPrincipal();
  }

  /**
   * Throws <TT>MissingMethodError</TT> when run against 2.0 API.
   * 
   * @param it
   *        the HttpServletRequest
   * @param arg
   * @return      a <code>Enumeration</code> containing the
   *        values of the requested
   *        header, or <code>null</code>
   *        if the request does not
   *        have any headers of that name
   * @see javax.servlet.http.HttpServletRequest#getHeaders(java.lang.String)
   */
  public static Enumeration getHeaders(HttpServletRequest it, String arg) {
    return (Enumeration)invoke(getHeaders, it, new Object[] { arg });
    // return it.getHeaders(arg);
  }

  /**
   * Throws <TT>MissingMethodError</TT> when run against 2.0 API.
   * 
   * @param it
   *        the HttpServletRequest
   * @return    the <code>HttpSession</code> associated
   *      with this request
   * @see javax.servlet.http.HttpServletRequest#getSession()
   */
  public static HttpSession getSession(HttpServletRequest it) {
    return (HttpSession)invoke(getSession, it, noargs);
    // return it.getSession();
  }

  /**
   * Throws <TT>MissingMethodError</TT> when run against 2.0 API.
   * 
   * @param it
   *        the HttpServletRequest
   * @return      <code>true</code> if the session ID
   *        came in as part of a URL; otherwise,
   *        <code>false</code>
   * @see javax.servlet.http.HttpServletRequest#isRequestedSessionIdFromURL()
   */
  public static boolean isRequestedSessionIdFromURL(HttpServletRequest it) {
    return ((Boolean)invoke(isRequestedSessionIdFromURL, it, noargs))
        .booleanValue();
    // return it.isRequestedSessionIdFromURL();
  }

  /**
   * Throws <TT>MissingMethodError</TT> when run against 2.0 API.
   * 
   * @param it
   *        the HttpServletRequest
   * @param arg
   * @return    a <code>boolean</code> indicating whether
   *      the user making this request belongs to a given role;
   *      <code>false</code> if the user has not been 
   *      authenticated
   * @see javax.servlet.http.HttpServletRequest#isUserInRole(java.lang.String)
   */
  public static boolean isUserInRole(HttpServletRequest it, String arg) {
    return ((Boolean)invoke(isUserInRole, it, new Object[] { arg }))
        .booleanValue();
    // return it.isUserInRole(arg);
  }

  /**
   * Throws <TT>MissingMethodError</TT> when run against 2.0 API. Returns an
   * <code>Enumeration</code> containing the names of the attributes available
   * to this request. This method returns an empty <code>Enumeration</code> if
   * the request has no attributes available to it.
   * 
   * @param it
   *        the HttpServletRequest
   * @return an <code>Enumeration</code> of strings containing the names of
   *         the request's attributes
   * @see javax.servlet.http.HttpServletRequest#getAttributeNames()
   */
  public static Enumeration getAttributeNames(HttpServletRequest it) {
    return (Enumeration)invoke(getAttributeNames, it, noargs);
    // return it.getAttributeNames();
  }

  /**
   * Throws <TT>MissingMethodError</TT> when run against 2.0 API. Returns the
   * preferred <code>Locale</code> that the client will accept content in,
   * based on the Accept-Language header. If the client request doesn't provide
   * an Accept-Language header, this method returns the default locale for the
   * server.
   * 
   * @param it
   *        the HttpServletRequest
   * @return the preferred <code>Locale</code> for the client
   * @see javax.servlet.http.HttpServletRequest#getLocale()
   */
  public static Locale getLocale(HttpServletRequest it) {
    return (Locale)invoke(getLocale, it, noargs);
    // return it.getLocale();
  }

  /**
   * Throws <TT>MissingMethodError</TT> when run against 2.0 API. Returns an
   * <code>Enumeration</code> of <code>Locale</code> objects indicating, in
   * decreasing order starting with the preferred locale, the locales that are
   * acceptable to the client based on the Accept-Language header. If the client
   * request doesn't provide an Accept-Language header, this method returns an
   * <code>Enumeration</code> containing one <code>Locale</code>, the
   * default locale for the server.
   * 
   * @param it
   *        the HttpServletRequest
   * @return an <code>Enumeration</code> of preferred <code>Locale</code>
   *         objects for the client
   * @see javax.servlet.http.HttpServletRequest#getLocales()
   */
  public static Enumeration getLocales(HttpServletRequest it) {
    return (Enumeration)invoke(getLocales, it, noargs);
    // return it.getLocales();
  }

  /**
   * Throws <TT>MissingMethodError</TT> when run against 2.0 API. If you get
   * compile errors for this method, it's probably because (a) you are compiling
   * against the 2.0 servlet API and (b) you have not obtained our stub <TT>RequestDispatcher.java</TT>
   * along with the Melati distribution, which is provided to help you out here.
   * Returns a {@link RequestDispatcher} object that acts as a wrapper for the
   * resource located at the given path. A <code>RequestDispatcher</code>
   * object can be used to forward a request to the resource or to include the
   * resource in a response. The resource can be dynamic or static. <p>The
   * pathname specified may be relative, although it cannot extend outside the
   * current servlet context. If the path begins with a "/" it is interpreted as
   * relative to the current context root. This method returns <code>null</code>
   * if the servlet container cannot return a <code>RequestDispatcher</code>.
   * <p>The difference between this method and 
   * ServletContext#getRequestDispatcher is that this method can take a
   * relative path.
   * 
   * @param it
   *        the HttpServletRequest
   * @param arg
   *        a <code>String</code> specifying the pathname to the resource
   * @return a <code>RequestDispatcher</code> object that acts as a wrapper
   *         for the resource at the specified path
   * @see RequestDispatcher
   * @see javax.servlet.http.HttpServletRequest#getRequestDispatcher(String)
   */
  public static RequestDispatcher getRequestDispatcher(HttpServletRequest it,
      String arg) {
    return (RequestDispatcher)invoke(getRequestDispatcher, it,
        new Object[] { arg });
    // return it.getRequestDispatcher(arg);
  }

  /**
   * Throws <TT>MissingMethodError</TT> when run against 2.0 API. Returns a
   * boolean indicating whether this request was made using a secure channel,
   * such as HTTPS.
   * 
   * @param it
   *        the HttpServletRequest
   * @return a boolean indicating if the request was made using a secure channel
   * @see javax.servlet.http.HttpServletRequest#isSecure()
   */
  public static boolean isSecure(HttpServletRequest it) {
    return ((Boolean)invoke(isSecure, it, noargs)).booleanValue();
    // return it.isSecure();
  }

  /**
   * Throws <TT>MissingMethodError</TT> when run against 2.0 API. Removes an
   * attribute from this request. This method is not generally needed as
   * attributes only persist as long as the request is being handled. <p>Attribute
   * names should follow the same conventions as package names. Names beginning
   * with <code>java.*</code>, <code>javax.*</code>, and
   * <code>com.sun.*</code>, are reserved for use by Sun Microsystems.
   * 
   * @param it
   *        the HttpServletRequest
   * @param arg
   *        a <code>String</code> specifying the name of the attribute to
   *        remove
   * @see javax.servlet.http.HttpServletRequest#removeAttribute()
   */
  public static void removeAttribute(HttpServletRequest it, String arg) {
    invoke(removeAttribute, it, new Object[] { arg });
    // it.removeAttribute(arg);
  }

  /**
   * Throws <TT>MissingMethodError</TT> when run against 2.0 API. Stores an
   * attribute in this request. Attributes are reset between requests. This
   * method is most often used in conjunction with {@link RequestDispatcher}.
   * <p>Attribute names should follow the same conventions as package names.
   * Names beginning with <code>java.*</code>, <code>javax.*</code>, and
   * <code>com.sun.*</code>, are reserved for use by Sun Microsystems.
   * 
   * @param it
   *        the HttpServletRequest
   * @param arg1
   *        a <code>String</code> specifying the name of the attribute
   * @param arg2
   *        the <code>Object</code> to be stored
   * @see javax.servlet.http.HttpServletRequest#setAttribute
   */
  public static void setAttribute(HttpServletRequest it, String arg1,
      Object arg2) {
    invoke(setAttribute, it, new Object[] { arg1, arg2 });
    // it.setAttribute(arg1, arg2);
  }

  // 
  // ============================
  // Servlet API 2.3 extensions
  // ============================
  // 

  /**
   * Throws <TT>MissingMethodError</TT> when run against 2.2 API.
   * 
   * @param it
   *        the HttpServletRequest
   * @return request url as a String buffer
   * @see javax.servlet.http.HttpServletRequest#getRequestURL()
   */
  public static StringBuffer getRequestURL(HttpServletRequest it) {
    return (StringBuffer)invoke(getRequestURL, it, noargs);
    // it.getRequestURL();
  }

  /**
   * Throws <TT>MissingMethodError</TT> when run against 2.2 API.
   * 
   * @param it
   *        the HttpServletRequest
   * @param arg
   *        encoding name
   * @see javax.servlet.http.HttpServletRequest#setCharacterEncoding(String)
   */
  public static void setCharacterEncoding(HttpServletRequest it, String arg) {
    invoke(setCharacterEncoding, it, new Object[] { arg });
    // it.setCharacterEncoding(arg);
  }

  /**
   * Throws <TT>MissingMethodError</TT> when run against 2.2 API.
   * 
   * @param it
   *        the HttpServletRequest
   * @return map of parameters
   * @see javax.servlet.http.HttpServletRequest#getParameterMap()
   */
  public static Map getParameterMap(HttpServletRequest it) {
    return (Map)invoke(getParameterMap, it, noargs);
    // return it.getParameterMap();
  }

  /**
   * @param it
   *        the HttpServletRequest
   * @return whether id is from url
   * @deprecated Servlet API 2.3
   * @see javax.servlet.http.HttpServletRequest#isRequestedSessionIdFromUrl()
   */
  public static boolean isRequestedSessionIdFromUrl(HttpServletRequest it) {
    return ((Boolean)invoke(isRequestedSessionIdFromUrl, it, noargs))
        .booleanValue();
    // return it.isRequestedSessionIdFromUrl();
  }

  /**
   * Throws <TT>MissingMethodError</TT> when run against 2.3 API.
   * 
   * @param it
   *        the HttpServletRequest
   * @param arg
   *        url
   * @return the real path
   * @deprecated Servlet API 2.1
   * @see javax.servlet.http.HttpServletRequest#getRealPath(String)
   */
  public static String getRealPath(HttpServletRequest it, String arg) {
    return (String)invoke(getRealPath, it, new Object[] { arg });
    // return it.getRealPath(rg);
  }

}
