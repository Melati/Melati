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
import javax.servlet.http.HttpServletRequest;

 /**
  * Enables Melati to compile, without warnings, with the Servlet API 
  * versions 2.0 to 2.5.
  * 
  * However the database listener and admin tests rely upon 2.4 features.
  *
  */

public class DelegatedHttpServletRequest implements HttpServletRequest {
  
  protected HttpServletRequest peer;
  
  /**
   * @param peer
   */
  public DelegatedHttpServletRequest(HttpServletRequest peer) {
    this.peer = peer;
  }

  /**
   *  Accessor to allow one to retrieve the wrapped 
   *  HttpServletRequest.
   *
   *  @return Wrapped HttpServletRequest
   */
 public HttpServletRequest getDelegate() {
   return peer;
 }

  
 /*
  * Core methods common to all APIs; call these directly.
  */

  /**
   * @see javax.servlet.http.HttpServletRequest#getAuthType()
   */
  public String getAuthType() {
    return peer.getAuthType();
  }
  /**
   * @see javax.servlet.http.HttpServletRequest#getCookies()
   */
  public javax.servlet.http.Cookie[] getCookies() {
    return peer.getCookies();
  }
  /**
   * @see javax.servlet.http.HttpServletRequest#getDateHeader(String)
   */
  public long getDateHeader(String a) {
    return peer.getDateHeader(a);
  }
  /**
   * @see javax.servlet.http.HttpServletRequest#getHeader(String)
   */
  public String getHeader(String a) {
    return peer.getHeader(a);
  }
  /**
   * @see javax.servlet.http.HttpServletRequest#getHeaderNames()
   */
  @SuppressWarnings("unchecked")
  public java.util.Enumeration<String> getHeaderNames() {
    return peer.getHeaderNames();
  }
  /**
   * @see javax.servlet.http.HttpServletRequest#getIntHeader(String)
   */
  public int getIntHeader(String a) {
    return peer.getIntHeader(a);
  }
  /**
   * @see javax.servlet.http.HttpServletRequest#getMethod()
   */
  public String getMethod() {
    return peer.getMethod();
  }
  /**
   * @see javax.servlet.http.HttpServletRequest#getPathInfo()
   */
  public String getPathInfo() {
    return peer.getPathInfo();
  }
  /**
   * @see javax.servlet.http.HttpServletRequest#getPathTranslated()
   */
  public String getPathTranslated() {
    return peer.getPathTranslated();
  }
  /**
   * @see javax.servlet.http.HttpServletRequest#getQueryString()
   */
  public String getQueryString() {
    return peer.getQueryString();
  }
  /**
   * @see javax.servlet.http.HttpServletRequest#getRemoteUser()
   */
  public String getRemoteUser() {
    return peer.getRemoteUser();
  }
  /**
   * @see javax.servlet.http.HttpServletRequest#getRequestURI()
   */
  public String getRequestURI() {
    return peer.getRequestURI();
  }
  /**
   * @see javax.servlet.http.HttpServletRequest#getRequestedSessionId()
   */
  public String getRequestedSessionId() {
    return peer.getRequestedSessionId();
  }
  /**
   * @see javax.servlet.http.HttpServletRequest#getServletPath()
   */
  public String getServletPath() {
    return peer.getServletPath();
  }
  /**
   * @see javax.servlet.http.HttpServletRequest#getSession(boolean)
   */
  public javax.servlet.http.HttpSession getSession(boolean a) {
    return peer.getSession(a);
  }

  /**
   * @see javax.servlet.http.HttpServletRequest#isRequestedSessionIdValid()
   */
  public boolean isRequestedSessionIdValid() {
    return peer.isRequestedSessionIdValid();
  }
  /**
   * @see javax.servlet.ServletRequest#getAttribute(String)
   */
  public java.lang.Object getAttribute(String a) {
    return peer.getAttribute(a);
  }
  /**
   * @see javax.servlet.ServletRequest#getCharacterEncoding()
   */
  public String getCharacterEncoding() {
    return peer.getCharacterEncoding();
  }
  /**
   * @see javax.servlet.ServletRequest#getContentLength()
   */
  public int getContentLength() {
    return peer.getContentLength();
  }
  /**
   * @see javax.servlet.ServletRequest#getContentType()
   */
  public String getContentType() {
    return peer.getContentType();
  }
  /**
   * @see javax.servlet.ServletRequest#getInputStream()
   */
  public javax.servlet.ServletInputStream getInputStream() 
        throws java.io.IOException {
    return peer.getInputStream();
  }
  /**
   * @see javax.servlet.ServletRequest#getParameter(String)
   */
  public String getParameter(String a) {
    return peer.getParameter(a);
  }
  /**
   * @see javax.servlet.ServletRequest#getParameterNames()
   */
  @SuppressWarnings("unchecked")
  public java.util.Enumeration<String> getParameterNames() {
    return peer.getParameterNames();
  }
  /**
   * @see javax.servlet.ServletRequest#getParameterValues(String)
   */
  public String[] getParameterValues(String a) {
    return peer.getParameterValues(a);
  }
  /**
   * @see javax.servlet.ServletRequest#getProtocol()
   */
  public String getProtocol() {
    return peer.getProtocol();
  }
  /**
   * @see javax.servlet.ServletRequest#getReader()
   */
  public java.io.BufferedReader getReader() throws java.io.IOException {
    return peer.getReader();
  }

  /**
   * @see javax.servlet.ServletRequest#getRemoteAddr()
   */
  public String getRemoteAddr() {
    return peer.getRemoteAddr();
  }
  /**
   * @see javax.servlet.ServletRequest#getRemoteHost()
   */
  public String getRemoteHost() {
    return peer.getRemoteHost();
  }
  /**
   * @see javax.servlet.ServletRequest#getScheme()
   */
  public String getScheme() {
    return peer.getScheme();
  }
  /**
   * @see javax.servlet.ServletRequest#getServerName()
   */
  public String getServerName() {
    return peer.getServerName();
  }
  /**
   * @see javax.servlet.ServletRequest#getServerPort()
   */
  public int getServerPort() {
    return peer.getServerPort();
  }

  /**
   * @see javax.servlet.http.HttpServletRequest#isRequestedSessionIdFromCookie()
   */
  public boolean isRequestedSessionIdFromCookie() {
    return peer.isRequestedSessionIdFromCookie();
  }

  //
  //  Deprecated methods, may disappear.
  //
  
  /**
   * @deprecated Servlet API 2.1
   *  
   * @see javax.servlet.http.HttpServletRequest#isRequestedSessionIdFromUrl()
   */
  public boolean isRequestedSessionIdFromUrl() {
    return peer.isRequestedSessionIdFromUrl();
  }
  /**
   * @deprecated Servlet API 2.1
   *                
   * @see javax.servlet.ServletRequest#getRealPath(String)
   */
  public String getRealPath(String a) {
    return peer.getRealPath(a);
  }

  //
  // Servlet API 2.1 extensions
  //
  
  /**
   * @see javax.servlet.http.HttpServletRequest#getUserPrincipal()
   */
  public java.security.Principal getUserPrincipal() {
    return peer.getUserPrincipal();
  }
  /**
   * @see javax.servlet.http.HttpServletRequest#getContextPath()
   */
  public String getContextPath() {
    return peer.getContextPath();
  }
  /**
   * @see javax.servlet.http.HttpServletRequest#getHeaders(String)
   */
  @SuppressWarnings("unchecked")
  public java.util.Enumeration<String> getHeaders(String name) {
    return peer.getHeaders(name);
  }
  /**
   * @see javax.servlet.http.HttpServletRequest#getSession()
   */
  public javax.servlet.http.HttpSession getSession() {
    return peer.getSession();
  }
  /**
   * @see javax.servlet.http.HttpServletRequest#isRequestedSessionIdFromURL()
   */
  public boolean isRequestedSessionIdFromURL() {
    return peer.isRequestedSessionIdFromURL();
  }
  
  //
  // Servlet API 2.2 extensions
  //
  
  /**
   * @see javax.servlet.http.HttpServletRequest#isUserInRole(String)
   */
  public boolean isUserInRole(String a) {
    return peer.isUserInRole(a);
  }
  /**
   * @see javax.servlet.ServletRequest#getAttributeNames()
   */
  @SuppressWarnings("unchecked")
  public Enumeration<String> getAttributeNames() {
    return peer.getAttributeNames();
  }
  /**
   * @see javax.servlet.ServletRequest#getLocale()
   */
  public Locale getLocale()  {
    return peer.getLocale();
  }
  /**
   * @see javax.servlet.ServletRequest#getLocales()
   */
  @SuppressWarnings("unchecked")
  public Enumeration<Locale> getLocales() {
    return peer.getLocales();
  }

  /**
   * @see javax.servlet.ServletRequest#getRequestDispatcher(String)
   */
  public RequestDispatcher getRequestDispatcher(String arg) {
    return peer.getRequestDispatcher(arg);
  }
  /**
   * @see javax.servlet.ServletRequest#isSecure()
   */
  public boolean isSecure() {
    return peer.isSecure();
  }
  /**
   * @see javax.servlet.ServletRequest#removeAttribute(String)
   */
  public void removeAttribute(String arg) {
    peer.removeAttribute(arg);
  }
  /**
   * @see javax.servlet.ServletRequest#setAttribute(String, java.lang.Object)
   */
  public void setAttribute(String arg1, Object arg2) {
    peer.setAttribute(arg1, arg2);
  }
  
  //
  //  Servlet 2.3 extensions
  //

  /**
   * @see javax.servlet.http.HttpServletRequest#getRequestURL()
   */
  public StringBuffer getRequestURL() {
    return peer.getRequestURL();
  }

  /**
   * @see javax.servlet.ServletRequest#setCharacterEncoding(String)
   */
  public void setCharacterEncoding (String s) throws java.io.UnsupportedEncodingException {
    peer.setCharacterEncoding(s);
  }

  /**
   * @see javax.servlet.ServletRequest#getParameterMap()
   */
  @SuppressWarnings("unchecked")
  public Map<String, String[]> getParameterMap () {
    return peer.getParameterMap();
  }

  //
  //  Servlet 2.4 extensions
  //

  /**
   * @see javax.servlet.ServletRequest#getLocalAddr()
   */
  public String getLocalAddr() {
    return peer.getLocalAddr();
  }

  /**
   * @see javax.servlet.ServletRequest#getLocalName()
   */
  public String getLocalName() {
    return peer.getLocalName();    
  }

  /**
   * @see javax.servlet.ServletRequest#getLocalPort()
   */
  public int getLocalPort() {
    return peer.getLocalPort();    
  }

  /**
   * @see javax.servlet.ServletRequest#getRemotePort()
   */
  public int getRemotePort() {
    return peer.getRemotePort();        
  }

  //
  //  There are no Servlet 2.5 extensions
  //
}















