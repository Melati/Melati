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

import java.io.*;
import java.net.*;

public class IoUtils {

  // not sure this is really optimal ...

  public static byte[] slurp(InputStream i, int estimate, int limit)
      throws IOException {
    try {
      byte[] b = new byte[estimate];
      int p = 0;

      for (;;) {
        int g = i.read(b, p, Math.min(b.length, limit) - p);
        if (g == -1) break;
        p += g;
        if (p >= limit) break;
        if (p >= b.length) {
          byte[] c = new byte[2 * b.length];
          System.arraycopy(b, 0, c, 0, p);
          b = c;
        }
      }

      if (p == b.length)
        return b;
      else {
        byte[] c = new byte[p];
        System.arraycopy(b, 0, c, 0, p);
        return c;
      }
    }
    finally {
      try { i.close(); } catch (Exception e) {}
    }
  }

  public static byte[] slurp(InputStream i, int estimate) throws IOException {
    return slurp(i, estimate, Integer.MAX_VALUE);
  }

  public static byte[] slurp(URL url, int estimate) throws IOException {
    return slurp(url.openStream(), estimate);
  }

  public static byte[] slurp(URL url, int estimate, int max)
      throws IOException {
    return slurp(url.openStream(), estimate, max);
  }

  public static byte[] slurp(File f, int estimate) throws IOException {
    return slurp(new FileInputStream(f), estimate);
  }

  public static char[] slurp(Reader i, int estimate, int limit)
      throws IOException {
    try {
      char[] b = new char[estimate];
      int p = 0;

      for (;;) {
        int g = i.read(b, p, Math.min(b.length, limit) - p);
        if (g == -1) break;
        p += g;
        if (p >= limit) break;
        if (p >= b.length) {
          char[] c = new char[2 * b.length];
          System.arraycopy(b, 0, c, 0, p);
          b = c;
        }
      }

      if (p == b.length)
        return b;
      else {
        char[] c = new char[p];
        System.arraycopy(b, 0, c, 0, p);
        return c;
      }
    }
    finally {
      try { i.close(); } catch (Exception e) {}
    }
  }

  public static char[] slurp(Reader i, int estimate) throws IOException {
    return slurp(i, estimate, Integer.MAX_VALUE);
  }

  /**
   * FIXME warn about potential inefficiency
   */

  public static byte[] slurpOutputOf_bytes(String[] command,
                                           int estimate, int limit)
      throws IOException {
    Process proc = Runtime.getRuntime().exec(command);

    byte[] output = IoUtils.slurp(proc.getInputStream(), estimate, limit);

    byte[] errors = IoUtils.slurp(proc.getErrorStream(), estimate, limit);

    try {
      if (proc.waitFor() != 0)
        throw new ProcessFailedException(command[0] + " failed",
                                         new String(errors));

      return output;
    }
    catch (InterruptedException e) {
      throw new IOException("interrupted while waiting for " +
                            command[0] + " to complete");
    }
  }

  /**
   * FIXME warn about potential inefficiency
   */

  public static String slurpOutputOf(String[] command, int estimate, int limit)
      throws IOException {
    return new String(slurpOutputOf_bytes(command, estimate, limit));
  }

  public static void copy(InputStream i, int buf, OutputStream o)
      throws IOException {
    byte b[] = new byte[buf];
    for (;;) {
      int g = i.read(b);
      if (g == -1) break;
      o.write(b, 0, g);
    }
  }

  public static void copy(Reader i, int buf, Writer o)
      throws IOException {
    char b[] = new char[buf];
    for (;;) {
      int g = i.read(b);
      if (g == -1) break;
      o.write(b, 0, g);
    }
  }
}
