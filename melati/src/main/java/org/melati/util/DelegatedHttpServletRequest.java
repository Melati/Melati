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
 *     William Chesters <williamc@paneris.org>
 *     http://paneris.org/~williamc
 *     Obrechtstraat 114, 2517VX Den Haag, The Netherlands
 */

package org.melati.util;

import java.util.Map;
import java.util.Enumeration;
import java.util.Locale;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import org.melati.util.servletcompat.HttpServletRequestCompat;

 /*
  * The <code>DelegatedHttpServletRequest</code> class enables 
  * Melati to compile, without warnings, with the Servlet API 
  * versions 2.0, 2.1, 2.2 and 2.3.
  * However, unless you use one of the core methods you will 
  * get warnings.
  *
  * @see   servletcompat.HttpServletRequestCompat
  */

public class DelegatedHttpServletRequest implements HttpServletRequest {
  protected HttpServletRequest peer;
  public DelegatedHttpServletRequest(HttpServletRequest peer) {
    this.peer = peer;
  }

 /*
  * Core methods common to all APIs
  */

  public java.lang.String getAuthType() {
    return peer.getAuthType();
  }
  public javax.servlet.http.Cookie[] getCookies() {
    return peer.getCookies();
  }
  public long getDateHeader(java.lang.String a) {
    return peer.getDateHeader(a);
  }
  public java.lang.String getHeader(java.lang.String a) {
    return peer.getHeader(a);
  }
  public java.util.Enumeration getHeaderNames() {
    return peer.getHeaderNames();
  }
  public int getIntHeader(java.lang.String a) {
    return peer.getIntHeader(a);
  }
  public java.lang.String getMethod() {
    return peer.getMethod();
  }
  public java.lang.String getPathInfo() {
    return peer.getPathInfo();
  }
  public java.lang.String getPathTranslated() {
    return peer.getPathTranslated();
  }
  public java.lang.String getQueryString() {
    return peer.getQueryString();
  }
  public java.lang.String getRemoteUser() {
    return peer.getRemoteUser();
  }
  public java.lang.String getRequestURI() {
    return peer.getRequestURI();
  }
  public java.lang.String getRequestedSessionId() {
    return peer.getRequestedSessionId();
  }
  public java.lang.String getServletPath() {
    return peer.getServletPath();
  }
  public javax.servlet.http.HttpSession getSession(boolean a) {
    return peer.getSession(a);
  }

  /**
   *   @deprecated Servlet 2.3
   **/ 
  public boolean isRequestedSessionIdFromUrl() {
    return HttpServletRequestCompat.isRequestedSessionIdFromUrl(peer);
//    return peer.isRequestedSessionIdFromUrl();
  }
  public boolean isRequestedSessionIdValid() {
    return peer.isRequestedSessionIdValid();
  }
  public java.lang.Object getAttribute(java.lang.String a) {
    return peer.getAttribute(a);
  }
  public java.lang.String getCharacterEncoding() {
    return peer.getCharacterEncoding();
  }
  public int getContentLength() {
    return peer.getContentLength();
  }
  public java.lang.String getContentType() {
    return peer.getContentType();
  }
  public javax.servlet.ServletInputStream getInputStream() 
        throws java.io.IOException {
    return peer.getInputStream();
  }
  public java.lang.String getParameter(java.lang.String a) {
    return peer.getParameter(a);
  }
  public java.util.Enumeration getParameterNames() {
    return peer.getParameterNames();
  }
  public java.lang.String[] getParameterValues(java.lang.String a) {
    return peer.getParameterValues(a);
  }
  public java.lang.String getProtocol() {
    return peer.getProtocol();
  }
  public java.io.BufferedReader getReader() throws java.io.IOException {
    return peer.getReader();
  }

  public java.lang.String getRemoteAddr() {
    return peer.getRemoteAddr();
  }
  public java.lang.String getRemoteHost() {
    return peer.getRemoteHost();
  }
  public java.lang.String getScheme() {
    return peer.getScheme();
  }
  public java.lang.String getServerName() {
    return peer.getServerName();
  }
  public int getServerPort() {
    return peer.getServerPort();
  }

  /**
   * 
   * @deprecated    As of Version 2.1 of the Java Servlet API,
   *                use {@link ServletContext#getRealPath} instead.
   **/
  public java.lang.String getRealPath(java.lang.String a) {
    return HttpServletRequestCompat.getRealPath(peer, a);
    //return peer.getRealPath(a);
  }

  /**
   * @deprecated in Servlet API 2.2
   **/
  public boolean isRequestedSessionIdFromCookie() {
    return peer.isRequestedSessionIdFromCookie();
  }
  /**
   * @deprecated Servlet API 2.2 extension
   **/
  public java.security.Principal getUserPrincipal() {
    return HttpServletRequestCompat.getUserPrincipal(peer);
  }
  /**
   * @deprecated Servlet API 2.2 extension
   **/
  public java.lang.String getContextPath() {
    return HttpServletRequestCompat.getContextPath(peer);
  }
  /**
   * @deprecated Servlet API 2.2 extension
   **/
  public java.util.Enumeration getHeaders(java.lang.String a) {
    return HttpServletRequestCompat.getHeaders(peer, a);
  }
  /**
   * @deprecated Servlet API 2.2 extension
   **/
  public javax.servlet.http.HttpSession getSession() {
    return HttpServletRequestCompat.getSession(peer);
  }
  /**
   * @deprecated Servlet API 2.2 extension
   **/
  public boolean isRequestedSessionIdFromURL() {
    return HttpServletRequestCompat.isRequestedSessionIdFromURL(peer);
  }
  /**
   * @deprecated Servlet API 2.2 extension
   **/
  public boolean isUserInRole(java.lang.String a) {
    return HttpServletRequestCompat.isUserInRole(peer, a);
  }
  /**
   * @deprecated Servlet API 2.2 extension
   **/
  public Enumeration getAttributeNames() {
    return HttpServletRequestCompat.getAttributeNames(peer);
  }
  /**
   * @deprecated Servlet API 2.2 extension
   **/
  public Locale getLocale()  {
    return HttpServletRequestCompat.getLocale(peer);
  }
  /**
   * @deprecated Servlet API 2.2 extension
   **/
  public Enumeration getLocales() {
    return HttpServletRequestCompat.getLocales(peer);
  }

  /**
   * If you get compile errors for this method, it's probably because (a) you
   * are compiling against the 2.0 servlet API and (b) you have not obtained
   * our stub <TT>RequestDispatcher.java</TT> along with the Melati
   * distribution, which is provided to help you out here.
   */

  /**
   * @deprecated Servlet API 2.2 extension
   **/
  public RequestDispatcher getRequestDispatcher(String arg) {
    return HttpServletRequestCompat.getRequestDispatcher(peer, arg);
  }
  /**
   * @deprecated Servlet API 2.2 extension
   **/
  public boolean isSecure() {
    return HttpServletRequestCompat.isSecure(peer);
  }
  /**
   * @deprecated Servlet API 2.2 extension
   **/
  public void removeAttribute(String arg) {
    HttpServletRequestCompat.removeAttribute(peer, arg);
  }
  /**
   * @deprecated Servlet API 2.2 extension
   **/
  public void setAttribute(String arg1, Object arg2) {
    HttpServletRequestCompat.setAttribute(peer, arg1, arg2);
  }

  /**
   * @deprecated Servlet 2.3 API extension
   **/  
  public StringBuffer getRequestURL() {
    return HttpServletRequestCompat.getRequestURL(peer);
  }

  /**
   * @deprecated Servlet 2.3 API extension
   **/  
  public void setCharacterEncoding (String s) {
    HttpServletRequestCompat.setCharacterEncoding(peer,s);
  }

  /**
   * @deprecated Servlet 2.3 API extension
   **/  
  public Map getParameterMap () {
    return HttpServletRequestCompat.getParameterMap(peer);
  }

}















