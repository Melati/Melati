/*
 * $Source$
 * $Revision$
 *
 * Part of Melati (http://melati.org), a framework for the rapid
 * development of clean, maintainable web applications.
 *
 * -------------------------------------
 *  Copyright (C) 2000 William Chesters
 * -------------------------------------
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 * A copy of the GPL should be in the file org/melati/COPYING in this tree.
 * Or see http://melati.org/License.html.
 *
 * Contact details for copyright holder:
 *
 *     William Chesters <williamc@paneris.org>
 *     http://paneris.org/~williamc
 *     Obrechtstraat 114, 2517VX Den Haag, The Netherlands
 *
 *
 * ------
 *  Note
 * ------
 *
 * I will assign copyright to PanEris (http://paneris.org) as soon as
 * we have sorted out what sort of legal existence we need to have for
 * that to make sense.  When WebMacro's "Simple Public License" is
 * finalised, we'll offer it as an alternative license for Melati.
 * In the meantime, if you want to use Melati on non-GPL terms,
 * contact me!
 */

package org.melati.util;

public abstract class MelatiException extends Exception {

  public Exception subException;

  public MelatiException(Exception subException) {
    this.subException = subException;
  }

  public MelatiException() {
    this(null);
  }

  public String getMessage() {
    return this.getClass().getName() + "\n" + subException;
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
