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

import java.io.Writer;
import java.io.FileWriter;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;

/**
 * IO utilities.
 */ 
public final class IoUtils {

  private IoUtils() {}

 
  /**
   * Read into a byte array. 
   * 
   * Not sure this is really optimal.
   * 
   * @param i the inputStream
   * @param estimate approximately how big
   * @param limit will not be bigger than 
   * @return a byte array no bigger than limit
   * @throws IOException
   */
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
      try { i.close(); } catch (Exception e) {
        // Ignore
        e = null; // shut PMD up
      }
    }
  }

  /**
   * Read into a byte array. 
   * 
   * Used in bibliomania.
   * 
   * @param i the inputStream
   * @param estimate approximately how big
   * @return a byte array no bigger than Integer.MAX_VALUE
   * @throws IOException
   */
  public static byte[] slurp(InputStream i, int estimate) throws IOException {
    return slurp(i, estimate, Integer.MAX_VALUE);
  }

  /**
   * Read an url into a byte array. 
   * 
   * Used in bibliomania.
   * 
   * @param url the url to read
   * @param estimate approximately how big
   * @return a byte array no bigger than Integer.MAX_VALUE
   * @throws IOException
   */
  public static byte[] slurp(URL url, int estimate) throws IOException {
    return slurp(url.openStream(), estimate);
  }

  /**
   * Read an url into a byte array, with a limit.
   * 
   * Used in bibliomania.
   * 
   * @param url the url to read
   * @param estimate approximately how big
   * @param max limit 
   * @return a byte array no bigger than max
   * @throws IOException
   */
  public static byte[] slurp(URL url, int estimate, int max)
      throws IOException {
    return slurp(url.openStream(), estimate, max);
  }

  /**
   * Read a file into a byte array.
   * 
   * Used in bibliomania.
   * 
   * @param f file to read
   * @param estimate approximately how big
   * @return a byte array no bigger than Integer.MAX_VALUE
   * @throws IOException
   */
  public static byte[] slurp(File f, int estimate) throws IOException {
    return slurp(new FileInputStream(f), estimate);
  }

  /**
   * Read from a Reader into a byte array.
   * 
   * Not used elsewhere as of 30/11/2006.
   * 
   * @param i Reader to read
   * @param estimate approximately how big
   * @param limit max size
   * @return a byte array no bigger than limit
   * @throws IOException
   */
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
      try { i.close(); } catch (Exception e) {
        //Ignore
        e = null; // shut PMD up
      }
    }
  }

  /**
   * Read from a Reader into a byte array.
   * 
   * Used in bibliomania.
   * 
   * @param i Reader to read
   * @param estimate approximately how big
   * @return a byte array no bigger than Integer.MAX_VALUE
   * @throws IOException
   */
  public static char[] slurp(Reader i, int estimate) throws IOException {
    return slurp(i, estimate, Integer.MAX_VALUE);
  }

  /**
   * Read the output of a system command into a byte array.
   * 
   * Warning: potentially inefficient.
   * Not used elsewhere as of 30/11/2006.
   * 
   * @param command the command to run
   * @param estimate approximately how big
   * @param limit max size
   * @return a byte array no bigger than limit
   * @throws IOException
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
   * Read the output of a system command into a byte array.
   * 
   * Warning: potentially inefficient.
   * Not used elsewhere as of 30/11/2006.
   * 
   * @param command the command to run
   * @param estimate approximately how big
   * @param limit max size
   * @return a String no bigger than limit
   * @throws IOException
   */
  public static String slurpOutputOf(String[] command, int estimate, int limit)
      throws IOException {
    return new String(slurpOutputOf_bytes(command, estimate, limit));
  }

  /**
   * Copy from an InputStream to an OutputStream.
   * Not used elsewhere as of 30/11/2006.
   * 
   * @param i the InputStream
   * @param buf the size of buffer to use
   * @param o the OutputStream
   * @throws IOException
   */
  public static void copy(InputStream i, int buf, OutputStream o)
      throws IOException {
    byte b[] = new byte[buf];
    for (;;) {
      int g = i.read(b);
      if (g == -1) break;
      o.write(b, 0, g);
    }
  }

  /**
   * Copy from a Reader to a Writer.
   * Not used elsewhere as of 30/11/2006.
   * 
   * @param i the Reader
   * @param buf the size of buffer to use
   * @param o the Writer
   * @throws IOException
   */
  public static void copy(Reader i, int buf, Writer o)
      throws IOException {
    char b[] = new char[buf];
    for (;;) {
      int g = i.read(b);
      if (g == -1) break;
      o.write(b, 0, g);
    }
  }

  /**
   * Copy from one file to another.
   * 
   * Used in bibliomania.
   * @param from input file
   * @param buf the size of buffer to use
   * @param to output file
   * @throws IOException
   */
  public static void copy(File from, int buf, File to) throws IOException {
    FileReader i = new FileReader(from);
    try {
      FileWriter o = new FileWriter(to);
      try {
        copy(i, buf, o);
      }
      finally {
        try { o.close(); } catch (Exception e) {
          //Ignore
          e = null; // shut PMD up
        }
      }
    }
    finally {
      try { i.close(); } catch (Exception e) {
        //Ignore
        e = null; // shut PMD up    
      }
    }
  }
}












