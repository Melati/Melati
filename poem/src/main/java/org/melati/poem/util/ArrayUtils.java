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

import java.util.Vector;
import java.util.Enumeration;

/**
 * A collection of useful operations on <code>Array</code>s.
 */
public class ArrayUtils {

  private ArrayUtils() {}

  public static Object[] arrayOf(Vector v) {
    Object[] arr;
    synchronized (v) {
      arr = new Object[v.size()];
      v.copyInto(arr);
    }

    return arr;
  }

  public static Object[] arrayOf(Enumeration e) {
    Vector v = EnumUtils.vectorOf(e);
    return arrayOf(v);
  }

  public static Object[] added(Object[] xs, Object y) {
    Object[] xsx = (Object[])java.lang.reflect.Array.newInstance(
                       xs.getClass().getComponentType(), xs.length + 1);
    System.arraycopy(xs, 0, xsx, 0, xs.length);
    xsx[xs.length] = y;
    return xsx;
  }

  public static Object[] concatenated(Object[] xs, Object[] ys) {
    Object[] xsys =
        (Object[])java.lang.reflect.Array.newInstance(
            xs.getClass().getComponentType(), xs.length + ys.length);
    System.arraycopy(xs, 0, xsys, 0, xs.length);
    System.arraycopy(ys, 0, xsys, xs.length, ys.length);
    return xsys;
  }

  public static Object[] section(Object[] xs, int start, int limit) {
    Object[] xs_ = (Object[])java.lang.reflect.Array.newInstance(
                       xs.getClass().getComponentType(), limit - start);
    System.arraycopy(xs, start, xs_, 0, xs_.length);
    return xs_;
  }

  public static int indexOf(Object[] xs, Object x) {
    for (int i = 0; i < xs.length; ++i)
      if (xs[i].equals(x)) return i;
    return -1;
  }

  public static boolean contains(Object[] xs, Object x) {
    return indexOf(xs, x) != -1;
  }
}
