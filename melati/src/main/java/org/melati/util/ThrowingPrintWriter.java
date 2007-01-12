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

import java.io.PrintWriter;

/**
 * A <code>PrintWriter</code> which can throw an <code>Exception</code>.
 */
public final class ThrowingPrintWriter extends PrintWriter {
  private PrintWriter pw;
  
  /** Description of this PrintWriter. */
  public final String description;

 /**
  * Thrown when a programmer attempts to use <code>super.out</code>.
  */
  public static class SuperUseException extends BugException {
    private static final long serialVersionUID = 1L;
    /**
     * Constructor.
     */
    public SuperUseException() {
      super("ThrowingPrintWriter tried to use super.out");
    }
  }

  /**
   * Constructor.
   * @param pw Preint writer to write to 
   * @param description A description
   */
  public ThrowingPrintWriter(PrintWriter pw, String description) {
    super(pw);
    this.pw = pw;
    this.description = description;
  }

  /**
   * Thrown if there is a problem writing to this 
   * <code>ThowingPrintWriter</code>.
   */
  public class TroubleException extends MelatiRuntimeException {
    private static final long serialVersionUID = 1L;
    /**
     * {@inheritDoc}
     * @see org.melati.util.MelatiRuntimeException#getMessage()
     */
    public String getMessage() {
      return "An exception condition occurred writing to " +
             (description == null ? "a PrintWriter" : description);
    }
  }

  /**
   * Check for problem and throw it if found.
   */
  public void throwOnTrouble() {
    if (pw.checkError())
      throw new TroubleException();
  }

  /**
   * Delegated method.
   * {@inheritDoc}
   * @see java.io.PrintWriter#flush()
   */
  public void flush() {
    pw.flush();
    throwOnTrouble();
  }

  /**
   * Delegated method.
   * {@inheritDoc}
   * @see java.io.PrintWriter#close()
   */
  public void close() {
    pw.close();
    throwOnTrouble();
  }

  /**
   * Delegated method.
   * {@inheritDoc}
   * @see java.io.PrintWriter#checkError()
   */
  public boolean checkError() {
    return pw.checkError();
  }

  /**
   * Delegated method.
   * {@inheritDoc}
   * @see java.io.PrintWriter#write(int)
   */
  public void write(int c) {
    pw.write(c);
    throwOnTrouble();
  }

  /**
   * Delegated method.
   * {@inheritDoc}
   * @see java.io.PrintWriter#write(char[], int, int)
   */
  public void write(char buf[], int off, int len) {
    pw.write(buf, off, len);
    throwOnTrouble();
  }

  /**
   * Delegated method.
   * {@inheritDoc}
   * @see java.io.PrintWriter#write(java.lang.String, int, int)
   */
  public void write(String buf, int off, int len) {
    pw.write(buf, off, len);
    throwOnTrouble();
  }

  /**
   * Delegated method.
   * {@inheritDoc}
   * @see java.io.PrintWriter#println()
   */
  public void println() {
    pw.println();
    throwOnTrouble();
  }
}
