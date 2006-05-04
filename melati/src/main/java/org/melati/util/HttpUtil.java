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
 *     Myles Chippendale <mylesc@paneris.org>
 */
package org.melati.util;

import javax.servlet.http.HttpServletRequest;

/**
 * An assortment of useful things to do with <code>Http</code>.
 */
public final class HttpUtil {

  
  private HttpUtil() {}

  public static void appendZoneURL(StringBuffer url, 
                                   HttpServletRequest request) {
    url.append(request.getScheme());
    url.append("://");
    url.append(request.getServerName());
    if (request.getScheme().equals("http") && 
        request.getServerPort() != 80 
        ||
        request.getScheme().equals("https") && 
        request.getServerPort() != 443) {
      url.append(':');
      url.append(request.getServerPort());
    }
    appendRelativeZoneURL(url,request);
  }

  /*
   * Note that this function should return 
   * http://host/zone from a request of form 
   * http://host/zone/servlet/pathinfo?querystring
   * on all servlet API versions 2.0 through 2.3
   * In 2.0 the zone was returned in the ServletPath 
   * it is now in the ContextPath.
   */
  public static void appendRelativeZoneURL (
      StringBuffer url, HttpServletRequest request) {
    url.append(HttpServletRequestCompat.getContextPath(request));
    String servlet = request.getServletPath();
    if (servlet != null && !servlet.equals(""))
      url.append(servlet.substring(0, servlet.lastIndexOf('/')));
  }


  public static String zoneURL(HttpServletRequest request) {
    StringBuffer url = new StringBuffer();
    appendZoneURL(url, request);
    return url.toString();
  }

  public static String servletURL(HttpServletRequest request) {
    StringBuffer url = new StringBuffer();
    appendZoneURL(url, request);
    String servlet = request.getServletPath();
    if (servlet != null && !servlet.equals(""))
      url.append(servlet.substring(
                          servlet.lastIndexOf('/'), servlet.length()));
    return url.toString();
  }

  public static String getRelativeRequestURL(HttpServletRequest request) {
    StringBuffer url = new StringBuffer();
    url.append(HttpServletRequestCompat.getContextPath(request));
    if (request.getServletPath() != null) url.append(request.getServletPath());
    if (request.getPathInfo() != null) url.append(request.getPathInfo());
    return url.toString();
  }
}
