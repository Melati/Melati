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

import java.util.*;

public class EnumUtils {
  private EnumUtils() {}

  public static int skip(Enumeration e, int n) {
    int c = 0;
    if (e instanceof SkipEnumeration) {
      SkipEnumeration s = (SkipEnumeration)e;
      while (c < n && s.hasMoreElements()) {
        s.skip();
        ++c;
      }
    }
    else
      while (c < n && e.hasMoreElements()) {
        e.nextElement();
        ++c;
      }

    return c;
  }

  public static Vector initial(Enumeration e, int n) {
    Vector v = new Vector(n);

    while (n > 0 && e.hasMoreElements()) {
      v.addElement(e.nextElement());
      --n;
    }

    return v;
  }

  public static Enumeration join(Enumeration a, Enumeration b) {
    Vector aVector =  vectorOf(a);
    while (b.hasMoreElements())
      aVector.addElement(b.nextElement());
    return aVector.elements();
  }

  public static Vector vectorOf(Enumeration e, int roughSize) {
    Vector v = new Vector(roughSize);

    while (e.hasMoreElements())
      v.addElement(e.nextElement());

    return v;
  }

  public static Vector vectorOf(Enumeration e) {
    return vectorOf(e, 20);
  }

  public static String concatenated(String sep, Enumeration e) {
    StringBuffer b = new StringBuffer();

    if (e.hasMoreElements())
      b.append(String.valueOf(e.nextElement()));

    while (e.hasMoreElements()) {
      b.append(sep);
      b.append(String.valueOf(e.nextElement()));
    }

    return b.toString();
  }
}
