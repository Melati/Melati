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

public class ReconstructedHttpServletRequest
    extends DelegatedHttpServletRequest {

  private HttpServletRequestParameters oldParams;

  public ReconstructedHttpServletRequest(
      HttpServletRequestParameters oldParams, HttpServletRequest newRequest)
          throws ReconstructedHttpServletRequestMismatchException {

    super(newRequest);
    this.oldParams = oldParams;

    HttpSession session = newRequest.getSession(false);
    if (!oldParams.requestURL.equals(
            HttpUtils.getRequestURL(newRequest).toString()) ||
        !(oldParams.queryString == null ?
            newRequest.getQueryString() == null :
            oldParams.queryString.equals(newRequest.getQueryString())) ||
        !(session != null && session.getId() != null &&
          session.getId().equals(oldParams.sessionID)))
      throw new ReconstructedHttpServletRequestMismatchException(oldParams,
                                                                 newRequest);
  }

  public void setNewRequest(HttpServletRequest newRequest) {
    peer = newRequest;
  }

  public HttpServletRequest getNewRequest() {
    return peer;
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

  /**
   * From the <B>old</B> request.
   */

  public String getMethod() {
    return oldParams.method;
  }
}
