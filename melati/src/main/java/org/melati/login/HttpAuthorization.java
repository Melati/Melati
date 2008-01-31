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

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;


/**
 * The information contained in an HTTP authorization.
 * 
 * See http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html and 
 * http://www.ietf.org/rfc/rfc2617.txt
 */
final class HttpAuthorization {
  String type;
  String username;
  String password;

  private HttpAuthorization() {
    // Utility classes should not have a public or default constructor.
  }

  /**
   * Private constructor.
   *
   * @param type Authorization type - assumed to be "Basic"
   * @param username user name to check
   * @param password user password
   */
  private HttpAuthorization(String type, String username, String password) {
    this.type = type;
    this.username = username;
    this.password = password;
  }

  /**
   * Create an Authorization from an HTTP Authorization header.
   * 
   * @param authHeader
   * @return a new Authorization or null
   */
  static HttpAuthorization from(String authHeader) {
    // Space is only valid separator, 
    // from my reading of http://www.ietf.org/rfc/rfc2617.txt
    // only one.
    // This has worked well for a long time.
    if (authHeader.regionMatches(0, "Basic ", 0, 6)) {

      String logpas = new String(Base64.decodeBase64(
              authHeader.substring(6).getBytes()));

      int colon = logpas.indexOf(':');

      if (colon == -1)
        throw new HttpAuthorizationMelatiException(
            "The browser sent Basic Authorization credentials with no colon " +
            "(that's not legal)");

      return new HttpAuthorization("Basic",
                                   logpas.substring(0, colon).trim(),
                                   logpas.substring(colon + 1).trim());
    }
    else {
      int space = authHeader.indexOf(' ');
      if (space == -1)
        throw new HttpAuthorizationMelatiException(
            "The browser sent an Authorization header without a space, " +
            "so it can't be anything Melati understands: " +
            authHeader);

      String type = authHeader.substring(0, space);
      throw new HttpAuthorizationMelatiException(
            "The browser tried to authenticate using an authorization type " +
            "`" + type + "' which Melati doesn't understand");
    }
  }

  /**
   * Create an Authorization from a request.
   *
   * @param request to extract Authorization header from
   * @return a new Authorization or null
   */
  static HttpAuthorization from(HttpServletRequest request) {
    String header = request.getHeader("Authorization");
    return header == null ? null : from(header);
  }
}

