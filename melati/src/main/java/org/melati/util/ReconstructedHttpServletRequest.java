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

package org.melati.util;

import java.util.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.security.Principal;

public class ReconstructedHttpServletRequest implements HttpServletRequest {

  private HttpServletRequestParameters oldParams;
  private HttpServletRequest newRequest;

  public ReconstructedHttpServletRequest(
      HttpServletRequestParameters oldParams, HttpServletRequest newRequest)
          throws ReconstructedHttpServletRequestMismatchException {
    if (!oldParams.requestURL.equals(
             HttpUtils.getRequestURL(newRequest).toString()) ||
        !(oldParams.queryString == null ?
              newRequest.getQueryString() == null :
              oldParams.queryString.equals(newRequest.getQueryString())) ||
        oldParams.session != newRequest.getSession(false))
      throw new ReconstructedHttpServletRequestMismatchException(oldParams,
                                                                 newRequest);

    this.oldParams = oldParams;
    this.newRequest = newRequest;
  }

  public void setNewRequest(HttpServletRequest newRequest) {
    this.newRequest = newRequest;
  }

  public int getContentLength() {
    return newRequest.getContentLength();
  }

  public String getContentType() {
    return newRequest.getContentType();
  }

  public String getProtocol() {
    return newRequest.getProtocol();
  }

  public String getScheme() {
    return newRequest.getScheme();
  }

  public String getServerName() {
    return newRequest.getServerName();
  }

  public int getServerPort() {
    return newRequest.getServerPort();
  }

  public String getRemoteAddr() {
    return newRequest.getRemoteAddr();
  }

  public String getRemoteHost() {
    return newRequest.getRemoteHost();
  }

  /**
    * @deprecated in Servlet API 2.2
  */
  public String getRealPath(String path) {
    return newRequest.getRealPath(path);
  }

  public ServletInputStream getInputStream() throws IOException {
    return newRequest.getInputStream();
  }

  /**
   * From the <B>old</B> request.
   */

  public String getParameter(String name) {
    String[] vals = (String[])oldParams.parameters.get(name);
    return vals == null ? null : vals[0];
  }

  /**
   * From the <B>old</B> request.
   */

  public String[] getParameterValues(String name) {
    return (String[])oldParams.parameters.get(name);
  }

  /**
   * From the <B>old</B> request.
   */

  public Enumeration getParameterNames() {
    return oldParams.parameters.keys();
  }

  public Object getAttribute(String name) {
    return newRequest.getAttribute(name);
  }

  public BufferedReader getReader() throws IOException {
    return newRequest.getReader();
  }

  public String getCharacterEncoding() {
    return newRequest.getCharacterEncoding();
  }

  public Cookie[] getCookies() {
    return newRequest.getCookies();
  }

  /**
   * From the <B>old</B> request.
   */

  public String getMethod() {
    return oldParams.method;
  }

  public String getRequestURI() {
    return newRequest.getRequestURI();
  }

  public String getServletPath() {
    return newRequest.getServletPath();
  }

  public String getPathInfo() {
    return newRequest.getPathInfo();
  }

  public String getPathTranslated() {
    return newRequest.getPathTranslated();
  }

  public String getQueryString() {
    return newRequest.getQueryString();
  }

  public String getRemoteUser() {
    return newRequest.getRemoteUser();
  }

  public String getAuthType() {
    return newRequest.getAuthType();
  }

  public String getHeader(String name) {
    return newRequest.getHeader(name);
  }

  public int getIntHeader(String name) {
    return newRequest.getIntHeader(name);
  }

  public long getDateHeader(String name) {
    return newRequest.getDateHeader(name);
  }

  public Enumeration getHeaderNames() {
    return newRequest.getHeaderNames();
  }

  public HttpSession getSession(boolean create) {
    return newRequest.getSession(create);
  }

  public String getRequestedSessionId() {
    return newRequest.getRequestedSessionId();
  }

  public boolean isRequestedSessionIdValid() {
    return newRequest.isRequestedSessionIdValid();
  }

  public boolean isRequestedSessionIdFromCookie() {
    return newRequest.isRequestedSessionIdFromCookie();
  }

  /**
    * @deprecated in Servlet API 2.2
  */
  public boolean isRequestedSessionIdFromUrl() {
    return newRequest.isRequestedSessionIdFromUrl();
  }

  // added for Servlet API 2.2
  public String getContextPath() {
      throw new RuntimeException("Unsupported call to Servlet 2.2 API");
  }

  public Enumeration getHeaders(String arg) {
      throw new RuntimeException("Unsupported call to Servlet 2.2 API");
  }

  public HttpSession getSession() {
      throw new RuntimeException("Unsupported call to Servlet 2.2 API");
  }

  public Principal getUserPrincipal() {
      throw new RuntimeException("Unsupported call to Servlet 2.2 API");
  }

  public boolean isRequestedSessionIdFromURL() {
      throw new RuntimeException("Unsupported call to Servlet 2.2 API");
  }

  public boolean isUserInRole(String arg) {
      throw new RuntimeException("Unsupported call to Servlet 2.2 API");
  }

  public Enumeration getAttributeNames() {
      throw new RuntimeException("Unsupported call to Servlet 2.2 API");
  }

  public Locale getLocale()  {
      throw new RuntimeException("Unsupported call to Servlet 2.2 API");
  }

  public Enumeration getLocales() {
      throw new RuntimeException("Unsupported call to Servlet 2.2 API");
  }
/*
  public RequestDispatcher getRequestDispatcher(String arg) {
      throw new RuntimeException("Unsupported call to Servlet 2.2 API");
  }
*/
  public boolean isSecure() {
      throw new RuntimeException("Unsupported call to Servlet 2.2 API");
  }

  public void removeAttribute(String arg) {
      throw new RuntimeException("Unsupported call to Servlet 2.2 API");
  }

  public void setAttribute(String arg1, Object arg2) {
      throw new RuntimeException("Unsupported call to Servlet 2.2 API");
  }

}
