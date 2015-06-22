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

package org.melati.poem.prepro;

/**
 * An assortment of useful operations on <code>String</code>s.
 *
 */
public final class StringUtils {

  private StringUtils() {}


  /**
   * Append a String to a StringBuffer, first quoting it with a quote
   * character and escaping any occurances of the quote char in the String.
   *
   * @param b the buffer to append to
   * @param s the String to append
   * @param q the quote character
   */
  public static void appendQuoted(StringBuffer b, String s, char q) {
    b.append(q);
    appendEscaped(b, s, q);
    b.append(q);
  }

  /**
   * Surround a string in quotes.
   *
   * @param i the string to quote
   * @param q The quote character to use
   * @return the quoted string
   */
  public static String quoted(String i, char q) {
    StringBuffer b = new StringBuffer();
    appendQuoted(b, i, q);
    return b.toString();
  }

  /**
   * Append a String to a StringBuffer, and escaping any occurances
   * of the char in the String.
   *
   * @param b the buffer to append to
   * @param s the String to append
   * @param e the character to escape
   */
  public static void appendEscaped(StringBuffer b, String s, char e) {
    int l = s.length();
    for (int i = 0; i < l; ++i) {
      char c = s.charAt(i);
      if (c == '\\' || c == e) {
        // damn, found one; catch up to here ...

        for (int j = 0; j < i; ++j)
          b.append(s.charAt(j));
        b.append('\\');
        b.append(c);

        // ... and continue

        for (++i; i < l; ++i) {
          c = s.charAt(i);
          if (c == '\\' || c == e)
            b.append('\\');
          b.append(c);
        }

        return;
      }
    }

    b.append(s);
  }

  /**
   * Captialise the first character of the input string.
   *
   * @param name the string to be capitalised
   * @return the capitalised string
   */
  public static String capitalised(String name) {
    char suffix[] = name.toCharArray();
    suffix[0] = Character.toUpperCase(suffix[0]);
    return new String(suffix);
  }

}
