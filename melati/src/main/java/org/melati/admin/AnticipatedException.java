/*
 * $Source$
 * $Revision$
 *
 * Copyright (C) 2006 Tim Pizey
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
 *     Tim Pizey <timp At paneris.org>
 *     http://paneris.org/~timp
 */

package org.melati.admin;

import org.melati.util.MelatiRuntimeException;

/**
 * An error which is expected, so should be rendered prettily.
 */
public class AnticipatedException extends MelatiRuntimeException {
  private static final long serialVersionUID = 1L;

  private String message;

  
  /** Constructor */
  public AnticipatedException(String message) {
    this.message = message;
  }

  /** The detail message. */
  public String getMessage() {
    return message;
  }

  /**
   * Pre-java 1.4 initial cause.
   */
  public AnticipatedException(String message, Exception subException) {
    this(message);
    initCause(subException);
  }

  /**
   * Pre-java 1.4 initial cause.
   */
  public AnticipatedException(Exception subException) {
    this(null, subException);
  }

}
