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
 * that to make sense. 
 * In the meantime, if you want to use Melati on non-GPL terms,
 * contact me!
 */

package org.melati.util;

public class StringUtils {
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
      }
      else {
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

  public static String quoted(String i, char q) {
    StringBuffer b = new StringBuffer();
    appendQuoted(b, i, q);
    return b.toString();
  }

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

  public static String capitalised(String name) {
    char suffix[] = name.toCharArray();
    suffix[0] = Character.toUpperCase(suffix[0]);
    return new String(suffix);
  }

  public static String tr(String s, String from, String to) {
    StringBuffer sNew = null;

    for (int i = 0; i < s.length(); ++i) {
      int t = from.indexOf(s.charAt(i));
      if (t != -1) {
	if (sNew == null) sNew = new StringBuffer(s);
	sNew.setCharAt(i, to.charAt(t));
      }
    }

    return sNew == null ? s : sNew.toString();
  }

  public static String tr(String s, char from, char to) {
    StringBuffer sNew = null;

    for (int i = 0; i < s.length(); ++i) {
      if (s.charAt(i) == from) {
	if (sNew == null) sNew = new StringBuffer(s);
	sNew.setCharAt(i, to);
      }
    }

    return sNew == null ? s : sNew.toString();
  }

  public static String concatenated(String sep, String[] xs) {
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

  public static String getRandomString(int i) {
    String result = "";
    int j = allowableChars.length();
    for (int a=0;a<i;a++) {
      int index = new Double(Math.random() * j).intValue() ;
      result += allowableChars.charAt(index);
    }
    return result;
  }

  public static void main(String[] args) {
    System.out.println(tr(args[0], "abc", "123"));
    System.out.println(tr(args[0], "a", "1"));
  }
}
