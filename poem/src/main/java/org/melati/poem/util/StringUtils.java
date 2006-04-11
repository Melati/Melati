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
 * An assortment of useful operations on <code>String</code>s.
 * 
 */
public final class StringUtils {

  private StringUtils() {}

  public static String[] split(String s, char c) {
    int n = 0;
    for (int i = s.indexOf(c); i >= 0; i = s.indexOf(c, i + 1))
      ++n;

    String[] them = new String[n + 1];

    for (int i = 0, m = 0;; ++m) {
      int j = s.indexOf(c, i);
      if (j == -1) {
        them[m] = s.substring(i);
        break;
      } else {
        them[m] = s.substring(i, j);
        i = j + 1;
      }
    }

    return them;
  }

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
   * Insert an escape character in front of a character.
   *  
   * @param s the String which may contain characters to escape
   * @param e the character to escape
   * @return the escaped string
   * @todo Delete - not used in Melati.
   * @deprecated not in use in Melati, cannot be used twice.
   */
  public static String escaped(String s, char e) {
    int l = s.length();
    for (int i = 0; i < l; ++i) {
      char c = s.charAt(i);
      if (c == '\\' || c == e) {
        // damn, found one; catch up to here ...

        StringBuffer t = new StringBuffer(l + 2);

        for (int j = 0; j < i; ++j)
          t.append(s.charAt(j));
        t.append('\\');
        t.append(c);

        // ... and continue

        for (++i; i < l; ++i) {
          c = s.charAt(i);
          if (c == '\\' || c == e)
            t.append('\\');
          t.append(c);
        }

        return t.toString();
      }
    }

    return s;
  }

  /**
   * Captialise the first character of the input string.
   * 
   * @param name
   * @return the capitalised string
   */
  public static String capitalised(String name) {
    char suffix[] = name.toCharArray();
    suffix[0] = Character.toUpperCase(suffix[0]);
    return new String(suffix);
  }

 /**
  * As Perl <code>tr</code>; swap any occurances of any characters in the 
  * <code>from</code> string in the input string with the 
  * corresponding character from the <code>to</code> string.
  * 
  * <code>
  *  tr("melati", "ait", "osn").equals("melons")
  * </code>
  */
  public static String tr(String s, String from, String to) {
    StringBuffer sNew = null;

    for (int i = 0; i < s.length(); ++i) {
      int t = from.indexOf(s.charAt(i));
      if (t != -1) {
        if (sNew == null)
          sNew = new StringBuffer(s);
        sNew.setCharAt(i, to.charAt(t));
      }
    }

    return sNew == null ? s : sNew.toString();
  }

 /**
  * As Perl <code>tr</code>; swap any occurances of the 
  * <code>from</code> character in the input string with the 
  * corresponding the <code>to</code> character.
  * 
  * <code>
  *  tr("melati", 'i', 'o').equals("melato")
  * </code>
  */
  public static String tr(String s, char from, char to) {
    StringBuffer sNew = null;

    for (int i = 0; i < s.length(); ++i) {
      if (s.charAt(i) == from) {
        if (sNew == null)
          sNew = new StringBuffer(s);
        sNew.setCharAt(i, to);
      }
    }

    return sNew == null ? s : sNew.toString();
  }

  /**
   * Concatenate an array of Strings with a separator. 
   * 
   * @param sep The separator String to use, may be null.
   * @param xs An array of Strings to concatenate.
   * @return the concatenated String.
   */
  public static String concatenated(String sep, String[] xs) {
    if (sep == null) sep = "";
    if (xs.length == 0)
      return "";
    else {
      int l = sep.length() * (xs.length - 1) + xs[0].length();
      for (int i = 1; i < xs.length; ++i)
        l += xs[i].length();

      StringBuffer c = new StringBuffer(l);

      c.append(xs[0]);
      for (int i = 1; i < xs.length; ++i) {
        c.append(sep);
        c.append(xs[i]);
      }

      return c.toString();
    }
  }

  private static String allowableChars =
    "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvwxyz0123456789";

  public static String randomString(int i) {
    String result = "";
    int j = allowableChars.length();
    for (int a = 0; a < i; a++) {
      int index = new Double(Math.random() * j).intValue();
      result += allowableChars.charAt(index);
    }
    return result;
  }

  /**
   *  Turn an empty String into a null.
   */
  public static String nulled(String s) {
    if (s.equals(""))
      return null;
    return s;
  }

  /**
   *  Turn a null into an empty String.
   */
  public static String unNulled(String in) {
    if (in == null)
      return "";
    return in;
  }

  private static final char[] hexDigits =
    { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 
      'A', 'B', 'C', 'D', 'E', 'F' };

  public static String hexEncoding(byte[] bs) {
    StringBuffer it = new StringBuffer(bs.length * 2);

    for (int i = 0; i < bs.length; ++i) {
      int b = bs[i];
      it.append(hexDigits[b >> 4 & 0xF]);
      it.append(hexDigits[b & 0xF]);
    }

    return it.toString();
  }

  public static byte hexDecoding(char c) {
    if ('0' <= c && c <= '9')
      return (byte) (c - '0');
    else if ('A' <= c && c <= 'F')
      return (byte) (0xA + c - 'A');
    else if ('a' <= c && c <= 'f')
      return (byte) (0xa + c - 'a');
    else
      throw new IllegalArgumentException("Invalid hex digit in string");
  }

  public static byte[] hexDecoding(String digits) {

    int l = digits.length() / 2;
    if (l * 2 != digits.length())
      throw new IllegalArgumentException(
                                    "Hex string has odd number of digits");

    byte[] it = new byte[l];

    for (int i = 0; i < l; ++i)
      it[i] =
        (byte) (hexDecoding(digits.charAt(i * 2)) << 4 | 
                hexDecoding(digits.charAt(i * 2 + 1)));

    return it;
  }

  /**
   * Test hex encoding and decoding.
   * 
   * @param args
   */
  public static void main(String[] args) {
    System.out.println(hexEncoding(hexDecoding(args[0])));
  }

  /**
   * Determine whether a String is quoted, with either quoting character.
   * @param in String to examine
   * @return whether String is quoted
   */
  public static boolean isQuoted(String in) {
    if (in == null)
      return false;
    if (in.length() < 2)
      return false;
    if ((in.startsWith("'") || in.startsWith("\"")) 
        && 
        (in.endsWith("'") || in.endsWith("\""))) {
      return true;
    }
    return false;
  }
}
