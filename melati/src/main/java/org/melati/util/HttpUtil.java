/*
 * $Source$
 * $Revision$
 *
 * Copyright (C) 2001 Myles Chippendale
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
 *     Myles Chippendale <mylesc At paneris.org>
 */
package org.melati.util;

import javax.servlet.http.HttpServletRequest;

/**
 * An assortment of useful things to do with <code>Http</code>.
 */
public final class HttpUtil {

  
  private HttpUtil() {}

  /**
   * Add a Zone URL to buffer.
   *  
   * @param url an empty StringBuffer to append to 
   * @param request the request to interrogate
   */
  public static void appendZoneURL(StringBuffer url, 
                                   HttpServletRequest request) {
    String scheme = request.getScheme();
    url.append(scheme);
    url.append("://");
    url.append(request.getServerName());
    if ((scheme.equals("http") && 
        request.getServerPort() != 80
        )
        ||
        (scheme.equals("https") && 
        request.getServerPort() != 443)) {
      url.append(':');
      url.append(request.getServerPort());
    }
    appendRelativeZoneURL(url,request);
  }

  /**
   * Return the server URL.
   *  
   * @param request the request to interrogate
   */
  public static String getServerURL(HttpServletRequest request) {
    StringBuffer url = new StringBuffer();
    String scheme = request.getScheme();
    url.append(scheme);
    url.append("://");
    url.append(request.getServerName());
    if ((scheme.equals("http") && 
        request.getServerPort() != 80
        )
        ||
        (scheme.equals("https") && 
        request.getServerPort() != 443)) {
      url.append(':');
      url.append(request.getServerPort());
    }
    return url.toString();
  }

  /**
   * Append relative servlet zone url.
   * 
   * Note that this function should return 
   * http://host/zone from a request of form 
   * http://host/zone/servlet/pathinfo?querystring
   * on all servlet API versions 2.0 through 2.3
   * In 2.0 the zone was returned in the ServletPath 
   * it is now in the ContextPath.
   * @param url StringBuffer to append to 
   * @param request the request to interrogate
   */
  public static void appendRelativeZoneURL (
      StringBuffer url, HttpServletRequest request) {
    url.append(HttpServletRequestCompat.getContextPath(request));
    String servletPath = request.getServletPath();
    if (servletPath != null && !servletPath.equals("")) {
      url.append(servletPath.substring(0, servletPath.lastIndexOf('/')));
      if (servletPath.lastIndexOf('/') == -1) 
        throw new MelatiBugMelatiException(
            "Servlet Path does not contain a forward slash:" + servletPath);
    }
  }

  /**
   * Retrieve a Zone url.
   * @param request the request to interrogate
   * @return an Url up to the zone specification as a String 
   */
  public static String zoneURL(HttpServletRequest request) {
    StringBuffer url = new StringBuffer();
    appendZoneURL(url, request);
    return url.toString();
  }

  /**
   * Retrieve a Servlet url from a request.
   * @param request the request to interrogate
   * @return an Url up to the servlet specification as a String 
   */
  public static String servletURL(HttpServletRequest request) {
    StringBuffer url = new StringBuffer();
    appendZoneURL(url, request);
    String servlet = request.getServletPath();
    if (servlet != null && !servlet.equals(""))
      url.append(servlet.substring(
                          servlet.lastIndexOf('/'), servlet.length()));
    return url.toString();
  }

  /**
   * Retrieve a relative url from a request.
   * @param request the request to interrogate
   * @return a relative Url  
   */
  public static String getRelativeRequestURL(HttpServletRequest request) {
    StringBuffer url = new StringBuffer();
    url.append(HttpServletRequestCompat.getContextPath(request));
    if (request.getServletPath() != null) url.append(request.getServletPath());
    if (request.getPathInfo() != null) url.append(request.getPathInfo());
    return url.toString();
  }
}
