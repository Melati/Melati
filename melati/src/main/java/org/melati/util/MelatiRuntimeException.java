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

/**
 * Abstract base class for all <code>RuntimeException}s within Melati.
 *
 * @author WilliamC@paneris.org
 *
 * @todo Upgrade to Java 1.4 after we stop supporting Java 1.3
 */
public abstract class MelatiRuntimeException extends RuntimeException {

  /**
   * Pre-java 1.4 initial cause.
   */
  public Exception subException;

  /**
   * Pre-java 1.4 initial cause.
   */
  public MelatiRuntimeException(String message, Exception subException) {
    this(message);
    initCause(subException);
  }

  public MelatiRuntimeException(String message) {
    super(message);
  }

  /**
   * Pre-java 1.4 initial cause.
   */
  public MelatiRuntimeException(Exception subException) {
    this(null, subException);
  }

  public MelatiRuntimeException() {
  }

  /**
   * Overrides standard method for backward compatibility.
   */
  public Throwable initCause(Throwable cause) {
    subException = (Exception)cause;
    return this;
    // FIXME Do this after we have abandoned Java 1.3
    // return super.initCause(cause);
  }
  

  public String getCoreMessage() {
    return super.getMessage();
  }

  public String getMessage() {
    return this.getClass().getName() +
           (super.getMessage() == null ? "" : ": " + super.getMessage()) +
           (subException == null ? "" : "\n" + subException);
  }

  public Exception innermostException() {
    return subException == null ? this :
           subException instanceof MelatiException ?
               ((MelatiException)subException).innermostException() :
           subException instanceof MelatiRuntimeException ?
               ((MelatiRuntimeException)subException).innermostException() :
           subException;
  }

  public void printStackTrace() {
    if (subException == null)
      super.printStackTrace();
    else {
      System.err.println(this);
      System.err.println("---");
      innermostException().printStackTrace();
    }
  }

  public void printStackTrace(java.io.PrintStream s) {
    if (subException == null)
      super.printStackTrace(s);
    else {
      s.println(this);
      s.println("---");
      innermostException().printStackTrace(s);
    }
  }

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
