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

/**
 * Abstract base class for all <code>Exception</code>s within 
 * Melati.
 */
public abstract class MelatiException extends Exception {

  /**
   * Eclipse generated.
   */
  private static final long serialVersionUID = -5635532269280872906L;
  
  /** A sub-exception we may be wrapping, otherwise null. */ 
  public Exception subException;

  /** Constructor with sub-exception. */
  public MelatiException(Exception subException) {
    this.subException = subException;
  }
  /**
   * Constructor with message and pre-java 1.4 initial cause.
   */
  public MelatiException(String message, Exception subException) {
    super(message);
    initCause(subException);
  }

  /**
   * Constructor.
   */
  public MelatiException() {
    this((Exception)null);
  }

  /**
   * Constructor with message.
   * @param message a text 
   */
  public MelatiException(String message) { 
    super(message);
  }
  /**
   * {@inheritDoc}
   * @see java.lang.Throwable#getMessage()
   */
  public String getMessage() {
    return this.getClass().getName() +
    (super.getMessage() == null ? "" : ": " + super.getMessage()) +
    (subException == null ? "" : "\n" + subException);
  }

  /**
   * @return the actual cause
   */
  public Exception innermostException() {
    return subException == null ? this :
           subException instanceof MelatiException ?
               ((MelatiException)subException).innermostException() :
           subException instanceof MelatiRuntimeException ?
               ((MelatiRuntimeException)subException).innermostException() :
           subException;
  }

  /**
   * {@inheritDoc}
   * @see java.lang.Throwable#printStackTrace()
   */
  public void printStackTrace() {
    if (subException == null)
      super.printStackTrace();
    else {
      System.err.println(this);
      System.err.println("---");
      innermostException().printStackTrace();
    }
  }

  /**
   * {@inheritDoc}
   * @see java.lang.Throwable#printStackTrace(java.io.PrintStream)
   */
  public void printStackTrace(java.io.PrintStream s) {
    if (subException == null)
      super.printStackTrace(s);
    else {
      s.println(this);
      s.println("---");
      innermostException().printStackTrace(s);
    }
  }

  /**
   * {@inheritDoc}
   * @see java.lang.Throwable#printStackTrace(java.io.PrintWriter)
   */
  public void printStackTrace(java.io.PrintWriter w) {
    if (subException == null)
      super.printStackTrace(w);
    else {
      w.println(this);
      w.println("---");
      innermostException().printStackTrace(w);
    }
  }
}
