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

package org.melati.poem.util;

import java.util.Vector;
import java.util.Enumeration;

/**
 * An assortment of useful operations on <code>Enumeration</code>s.
 */
public final class EnumUtils {
  private EnumUtils() {}

  /**
   * Skip a specified number of Elements in an Enumeration.
   * @param e the Enumeration to skip Elements of
   * @param n the number of Elements to skip
   * @return the number of Elements actually skipped, may be less than asked 
   */
  public static int skip(Enumeration<?> e, int n) {
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

  /**
   * Create a Vector of the first n Elements of an Enumeration.
   * If the number of elements in the Enumeration is less than 
   * n then the remaining elements of the Vector will be null.
   * @param e the input Enumeration
   * @param n the number of Elements to include
   * @return a new Vector of the initial Elements
   */
  public static Vector<?> initial(Enumeration<?> e, int n) {
    Vector<Object> v = new Vector<Object>(n);

    while (n > 0 && e.hasMoreElements()) {
      v.addElement(e.nextElement());
      --n;
    }

    return v;
  }

  /**
   * Join two Enumerations into a single one.
   * @param a head Enumeration
   * @param b tail Enumeration 
   * @return a new enumeration which is a concatenation of A and B
   */
  public static <T extends Object> Enumeration<T> join(Enumeration<T> a, Enumeration<T> b) {
    Vector<T> aVector =  vectorOf(a);
    while (b.hasMoreElements())
      aVector.addElement(b.nextElement());
    return aVector.elements();
  }

  /**
   * Create a Vector from an Enumeration.
   * @param e the source Enumeration
   * @param roughSize starting size of the Vector
   * @return a Vector of the Elements of the input Enumeration
   */
  public static <T extends Object> Vector<T> vectorOf(Enumeration<T> e, int roughSize) {
    Vector<T> v = new Vector<T>(roughSize);

    while (e.hasMoreElements())
      v.addElement(e.nextElement());

    return v;
  }

  /**
   * Create a Vector from an Enumeration, supplying an 
   * initial size of 20.
   * @param e the source Enumeration
   * @return a Vector with size at least 20 of the Elements of the input Enumeration
   */
  public static <T extends Object> Vector<T> vectorOf(Enumeration<T> e) {
    return vectorOf(e, 20);
  }

  /**
   * Concatenate an Enumeration, specifying the separator.
   * @param sep Separator string 
   * @param e Enumeration to be concatenated
   * @return A String representation of the Enumeration
   */
  public static String concatenated(String sep, Enumeration<Object> e) {
    StringBuffer b = new StringBuffer();

    if (e.hasMoreElements())
      b.append(String.valueOf(e.nextElement()));

    while (e.hasMoreElements()) {
      b.append(sep);
      b.append(String.valueOf(e.nextElement()));
    }

    return b.toString();
  }

  /**
   * Whether the Enumeration contain an Object.
   * @param e an Enumeration to look in
   * @param o the Object to look for
   * @return true if the Object occurs in the Enumeration
   */
  public static boolean contains(Enumeration<Object> e, Object o) {
    while (e.hasMoreElements())
      if (e.nextElement().equals(o))
        return true;

    return false;
  }
}
