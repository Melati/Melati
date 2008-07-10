/*
 * $Source$
 * $Revision$
 *
 * Copyright (C) 2000 Myles Chippendale
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
 *     Mylesc Chippendale <mylesc At paneris.org>
 *     http://paneris.org/
 *     29 Stanley Road, Oxford, OX4 1QY, UK
 */

package org.melati.servlet;

import java.io.File;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import org.melati.util.DelimitedBufferedInputStream;

/**
 * Store the uploaded data in a byte array in memory.
 */
public class MemoryFormDataAdaptor implements FormDataAdaptor {
  /** Size for byte buffers */
  protected int BUFSIZE = 512;

  private byte[] data = new byte[0];

  /**
   * Return the data as a byte array.
   * @return the data as a byte array
   */
  public byte[] getData() {
    return data;
  }

  /**
   * Return the size of the data.
   * @return the size of the data
   */
  public long getSize() {
    return data.length;
  }

  /**
   * Return a File object pointing to the saved data (if one exists).
   * @return always <code>null</code>
   */
  public File getFile() {
    return null;
  }

  /**
   * Return a url to the object.
   * @return always null
   */
  public String getURL() {
    return null;
  }

  /**
   * Read data from in until the delim and save it
   * in an internal buffer for later use.
   *
   * @param field   a {@link MultipartFormField} to be read
   * @param in      a {@link DelimitedBufferedInputStream} to read from
   * @param delim   the delimitor to differentiate elements
   * @throws IOException if there is a problem reading the input 
   */
  public void readData(MultipartFormField field,
                       DelimitedBufferedInputStream in,
                       byte[] delim) 
      throws IOException {

    ByteArrayOutputStream out = new ByteArrayOutputStream();
    byte[] buff = new byte[BUFSIZE];
    int count;

    try {
      while ((count = in.readToDelimiter(buff, 0, buff.length, delim)) > 0)
        out.write(buff, 0, count);
      if (count == -1)
        throw new IOException(
                      "Didn't find boundary whilst reading field data");
      data = out.toByteArray();
    }
    catch (IOException e) {
      throw e;
    }
    finally {
      try {
        out.close();
        out = null;
      }
      catch (Exception e) {
        // Thrown above
        e = null; // shut PMD up
      }
    }
  }
}






