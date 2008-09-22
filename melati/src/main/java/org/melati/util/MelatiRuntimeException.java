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

import org.melati.poem.PoemException;

/**
 * Abstract base class for all <code>RuntimeException</code>s within Melati.
 */
public abstract class MelatiRuntimeException extends PoemException {

  /**
   * Eclipse generated.
   */
  private static final long serialVersionUID = 5539218874152422978L;

  /**
   * Constructor with message and initial cause.
   */
  public MelatiRuntimeException(String message, Exception subException) {
    this(message);
    initCause(subException);
  }

  /**
   * Constructor with detailed message.
   * @param message the detailed message
   */
  public MelatiRuntimeException(String message) {
    super(message);
  }

  /**
   * Constructor with initial cause.
   */
  public MelatiRuntimeException(Exception subException) {
    this(null, subException);
  }

  /**
   * Constructor.
   */
  public MelatiRuntimeException() {
  }

  /**
   * @return the exception this exception is wrapping, recursively or this exception
   * {@inheritDoc}
   * @see org.melati.poem.PoemException#innermostException()
   */
  public Exception innermostException() {
    return subException == null ? this :
           subException instanceof MelatiException ?
               ((MelatiException)subException).innermostException() :
           subException instanceof PoemException ?
               ((PoemException)subException).innermostException() :
           subException;
  }
  
}
