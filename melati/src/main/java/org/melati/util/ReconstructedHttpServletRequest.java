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

package org.melati.util;

import java.util.Enumeration;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;

/**
 * An <code>HttpServletRequest</code> created from values stored in 
 * the <code>Session</code>.
 * 
 * Used during login to be able to return to the point where an 
 * <code>AccessPoemException</code> was thrown once the user has 
 * successfully logged in. 
 */
public class ReconstructedHttpServletRequest
    extends DelegatedHttpServletRequest {

  private HttpServletRequestParameters oldParams;

  /**
   * Constructor.
   * 
   * @param oldParams parameters from original request
   * @param newRequest the new request to add parameters to
   * @throws ReconstructedHttpServletRequestMismatchException
   */
  public ReconstructedHttpServletRequest(
      HttpServletRequestParameters oldParams, HttpServletRequest newRequest)
          throws ReconstructedHttpServletRequestMismatchException {

    super(newRequest);
    this.oldParams = oldParams;

    HttpSession session = newRequest.getSession(false);
    if (!oldParams.requestURL.equals(HttpUtil.getRelativeRequestURL(newRequest))
     || !(oldParams.queryString == null ?
          newRequest.getQueryString() == null :
          oldParams.queryString.equals(newRequest.getQueryString())) ||
        !(session != null && session.getId() != null &&
          session.getId().equals(oldParams.sessionID)))
      throw new ReconstructedHttpServletRequestMismatchException(oldParams,
                                                                 newRequest);
  }

  /**
   * Set the peer property.
   * @param newRequest the request to set as peer
   */
  public void setNewRequest(HttpServletRequest newRequest) {
    peer = newRequest;
  }

  /**
   * @return the new request
   */
  public HttpServletRequest getNewRequest() {
    return peer;
  }

  /**
   * From the <B>old</B> request.
   * {@inheritDoc}
   * @see javax.servlet.ServletRequest#getParameter(java.lang.String)
   */
  public String getParameter(String name) {
    String[] vals = (String[])oldParams.parameters.get(name);
    return vals == null ? null : vals[0];
  }

  /**
   * From the <B>old</B> request.
   * {@inheritDoc}
   * @see javax.servlet.ServletRequest#getParameterValues(java.lang.String)
   */
  public String[] getParameterValues(String name) {
    return (String[])oldParams.parameters.get(name);
  }

  /**
   * From the <B>old</B> request.
   * {@inheritDoc}
   * @see javax.servlet.ServletRequest#getParameterNames()
   */
  public Enumeration<String> getParameterNames() {
    return oldParams.parameters.keys();
  }

  /**
   * From the <B>old</B> request.
   * {@inheritDoc}
   * @see javax.servlet.http.HttpServletRequest#getMethod()
   */
  public String getMethod() {
    return oldParams.method;
  }
}
