/*
 * $Source$
 * $Revision$
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

  public static boolean contains(Enumeration e, Object o) {
    while (e.hasMoreElements())
      if (e.nextElement().equals(o))
        return true;

    return false;
  }
}
