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
  * 
  * 
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

 /*
  * Core methods common to all APIs; call these directly.
  */

  /**
   * {@inheritDoc}
   * @see javax.servlet.http.HttpServletRequest#getAuthType()
   */
  public java.lang.String getAuthType() {
    return peer.getAuthType();
  }
  /**
   * {@inheritDoc}
   * @see javax.servlet.http.HttpServletRequest#getCookies()
   */
  public javax.servlet.http.Cookie[] getCookies() {
    return peer.getCookies();
  }
  /**
   * {@inheritDoc}
   * @see javax.servlet.http.HttpServletRequest#getDateHeader(java.lang.String)
   */
  public long getDateHeader(java.lang.String a) {
    return peer.getDateHeader(a);
  }
  /**
   * {@inheritDoc}
   * @see javax.servlet.http.HttpServletRequest#getHeader(java.lang.String)
   */
  public java.lang.String getHeader(java.lang.String a) {
    return peer.getHeader(a);
  }
  /**
   * {@inheritDoc}
   * @see javax.servlet.http.HttpServletRequest#getHeaderNames()
   */
  @SuppressWarnings("unchecked")
  public java.util.Enumeration<String> getHeaderNames() {
    return peer.getHeaderNames();
  }
  /**
   * {@inheritDoc}
   * @see javax.servlet.http.HttpServletRequest#getIntHeader(java.lang.String)
   */
  public int getIntHeader(java.lang.String a) {
    return peer.getIntHeader(a);
  }
  /**
   * {@inheritDoc}
   * @see javax.servlet.http.HttpServletRequest#getMethod()
   */
  public java.lang.String getMethod() {
    return peer.getMethod();
  }
  /**
   * {@inheritDoc}
   * @see javax.servlet.http.HttpServletRequest#getPathInfo()
   */
  public java.lang.String getPathInfo() {
    return peer.getPathInfo();
  }
  /**
   * {@inheritDoc}
   * @see javax.servlet.http.HttpServletRequest#getPathTranslated()
   */
  public java.lang.String getPathTranslated() {
    return peer.getPathTranslated();
  }
  /**
   * {@inheritDoc}
   * @see javax.servlet.http.HttpServletRequest#getQueryString()
   */
  public java.lang.String getQueryString() {
    return peer.getQueryString();
  }
  /**
   * {@inheritDoc}
   * @see javax.servlet.http.HttpServletRequest#getRemoteUser()
   */
  public java.lang.String getRemoteUser() {
    return peer.getRemoteUser();
  }
  /**
   * {@inheritDoc}
   * @see javax.servlet.http.HttpServletRequest#getRequestURI()
   */
  public java.lang.String getRequestURI() {
    return peer.getRequestURI();
  }
  /**
   * {@inheritDoc}
   * @see javax.servlet.http.HttpServletRequest#getRequestedSessionId()
   */
  public java.lang.String getRequestedSessionId() {
    return peer.getRequestedSessionId();
  }
  /**
   * {@inheritDoc}
   * @see javax.servlet.http.HttpServletRequest#getServletPath()
   */
  public java.lang.String getServletPath() {
    return peer.getServletPath();
  }
  /**
   * {@inheritDoc}
   * @see javax.servlet.http.HttpServletRequest#getSession(boolean)
   */
  public javax.servlet.http.HttpSession getSession(boolean a) {
    return peer.getSession(a);
  }

  /**
   * {@inheritDoc}
   * @see javax.servlet.http.HttpServletRequest#isRequestedSessionIdValid()
   */
  public boolean isRequestedSessionIdValid() {
    return peer.isRequestedSessionIdValid();
  }
  /**
   * {@inheritDoc}
   * @see javax.servlet.ServletRequest#getAttribute(java.lang.String)
   */
  public java.lang.Object getAttribute(java.lang.String a) {
    return peer.getAttribute(a);
  }
  /**
   * {@inheritDoc}
   * @see javax.servlet.ServletRequest#getCharacterEncoding()
   */
  public java.lang.String getCharacterEncoding() {
    return peer.getCharacterEncoding();
  }
  /**
   * {@inheritDoc}
   * @see javax.servlet.ServletRequest#getContentLength()
   */
  public int getContentLength() {
    return peer.getContentLength();
  }
  /**
   * {@inheritDoc}
   * @see javax.servlet.ServletRequest#getContentType()
   */
  public java.lang.String getContentType() {
    return peer.getContentType();
  }
  /**
   * {@inheritDoc}
   * @see javax.servlet.ServletRequest#getInputStream()
   */
  public javax.servlet.ServletInputStream getInputStream() 
        throws java.io.IOException {
    return peer.getInputStream();
  }
  /**
   * {@inheritDoc}
   * @see javax.servlet.ServletRequest#getParameter(java.lang.String)
   */
  public java.lang.String getParameter(java.lang.String a) {
    return peer.getParameter(a);
  }
  /**
   * {@inheritDoc}
   * @see javax.servlet.ServletRequest#getParameterNames()
   */
  @SuppressWarnings("unchecked")
  public java.util.Enumeration<String> getParameterNames() {
    return peer.getParameterNames();
  }
  /**
   * {@inheritDoc}
   * @see javax.servlet.ServletRequest#getParameterValues(java.lang.String)
   */
  public java.lang.String[] getParameterValues(java.lang.String a) {
    return peer.getParameterValues(a);
  }
  /**
   * {@inheritDoc}
   * @see javax.servlet.ServletRequest#getProtocol()
   */
  public java.lang.String getProtocol() {
    return peer.getProtocol();
  }
  /**
   * {@inheritDoc}
   * @see javax.servlet.ServletRequest#getReader()
   */
  public java.io.BufferedReader getReader() throws java.io.IOException {
    return peer.getReader();
  }

  /**
   * {@inheritDoc}
   * @see javax.servlet.ServletRequest#getRemoteAddr()
   */
  public java.lang.String getRemoteAddr() {
    return peer.getRemoteAddr();
  }
  /**
   * {@inheritDoc}
   * @see javax.servlet.ServletRequest#getRemoteHost()
   */
  public java.lang.String getRemoteHost() {
    return peer.getRemoteHost();
  }
  /**
   * {@inheritDoc}
   * @see javax.servlet.ServletRequest#getScheme()
   */
  public java.lang.String getScheme() {
    return peer.getScheme();
  }
  /**
   * {@inheritDoc}
   * @see javax.servlet.ServletRequest#getServerName()
   */
  public java.lang.String getServerName() {
    return peer.getServerName();
  }
  /**
   * {@inheritDoc}
   * @see javax.servlet.ServletRequest#getServerPort()
   */
  public int getServerPort() {
    return peer.getServerPort();
  }

  /**
   * {@inheritDoc}
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
   * {@inheritDoc}
   * @see javax.servlet.http.HttpServletRequest#isRequestedSessionIdFromUrl()
   */
  public boolean isRequestedSessionIdFromUrl() {
    return peer.isRequestedSessionIdFromUrl();
  }
  /**
   * @deprecated Servlet API 2.1
   *                
   * {@inheritDoc}
   * @see javax.servlet.ServletRequest#getRealPath(java.lang.String)
   */
  public java.lang.String getRealPath(java.lang.String a) {
    return peer.getRealPath(a);
  }

  //
  // Servlet API 2.1 extensions
  //
  
  /**
   * {@inheritDoc}
   * @see javax.servlet.http.HttpServletRequest#getUserPrincipal()
   */
  public java.security.Principal getUserPrincipal() {
    return peer.getUserPrincipal();
  }
  /**
   * {@inheritDoc}
   * @see javax.servlet.http.HttpServletRequest#getContextPath()
   */
  public java.lang.String getContextPath() {
    return peer.getContextPath();
  }
  /**
   * {@inheritDoc}
   * @see javax.servlet.http.HttpServletRequest#getHeaders(java.lang.String)
   */
  @SuppressWarnings("unchecked")
  public java.util.Enumeration<String> getHeaders(java.lang.String name) {
    return peer.getHeaders(name);
  }
  /**
   * {@inheritDoc}
   * @see javax.servlet.http.HttpServletRequest#getSession()
   */
  public javax.servlet.http.HttpSession getSession() {
    return peer.getSession();
  }
  /**
   * {@inheritDoc}
   * @see javax.servlet.http.HttpServletRequest#isRequestedSessionIdFromURL()
   */
  public boolean isRequestedSessionIdFromURL() {
    return peer.isRequestedSessionIdFromURL();
  }
  
  //
  // Servlet API 2.2 extensions
  //
  
  /**
   * {@inheritDoc}
   * @see javax.servlet.http.HttpServletRequest#isUserInRole(java.lang.String)
   */
  public boolean isUserInRole(java.lang.String a) {
    return peer.isUserInRole(a);
  }
  /**
   * {@inheritDoc}
   * @see javax.servlet.ServletRequest#getAttributeNames()
   */
  @SuppressWarnings("unchecked")
  public Enumeration<String> getAttributeNames() {
    return peer.getAttributeNames();
  }
  /**
   * {@inheritDoc}
   * @see javax.servlet.ServletRequest#getLocale()
   */
  public Locale getLocale()  {
    return peer.getLocale();
  }
  /**
   * {@inheritDoc}
   * @see javax.servlet.ServletRequest#getLocales()
   */
  @SuppressWarnings("unchecked")
  public Enumeration<Locale> getLocales() {
    return peer.getLocales();
  }

  /**
   * {@inheritDoc}
   * @see javax.servlet.ServletRequest#getRequestDispatcher(java.lang.String)
   */
  public RequestDispatcher getRequestDispatcher(String arg) {
    return peer.getRequestDispatcher(arg);
  }
  /**
   * {@inheritDoc}
   * @see javax.servlet.ServletRequest#isSecure()
   */
  public boolean isSecure() {
    return peer.isSecure();
  }
  /**
   * {@inheritDoc}
   * @see javax.servlet.ServletRequest#removeAttribute(java.lang.String)
   */
  public void removeAttribute(String arg) {
    peer.removeAttribute(arg);
  }
  /**
   * {@inheritDoc}
   * @see javax.servlet.ServletRequest#setAttribute(java.lang.String, java.lang.Object)
   */
  public void setAttribute(String arg1, Object arg2) {
    peer.setAttribute(arg1, arg2);
  }
  
  //
  //  Servlet 2.3 extensions
  //

  /**
   * {@inheritDoc}
   * @see javax.servlet.http.HttpServletRequest#getRequestURL()
   */
  public StringBuffer getRequestURL() {
    return peer.getRequestURL();
  }

  /**
   * {@inheritDoc}
   * @see javax.servlet.ServletRequest#setCharacterEncoding(java.lang.String)
   */
  public void setCharacterEncoding (String s) throws java.io.UnsupportedEncodingException {
    peer.setCharacterEncoding(s);
  }

  /**
   * {@inheritDoc}
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
   * {@inheritDoc}
   * @see javax.servlet.ServletRequest#getLocalAddr()
   */
  public String getLocalAddr() {
    return peer.getLocalAddr();
  }

  /**
   * {@inheritDoc}
   * @see javax.servlet.ServletRequest#getLocalName()
   */
  public String getLocalName() {
    return peer.getLocalName();    
  }

  /**
   * {@inheritDoc}
   * @see javax.servlet.ServletRequest#getLocalPort()
   */
  public int getLocalPort() {
    return peer.getLocalPort();    
  }

  /**
   * {@inheritDoc}
   * @see javax.servlet.ServletRequest#getRemotePort()
   */
  public int getRemotePort() {
    return peer.getRemotePort();        
  }

  //
  //  There are no Servlet 2.5 extensions
  //
}















