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
 *     Mylesc Chippendale <mylesc@paneris.org>
 *     http://paneris.org/
 *     29 Stanley Road, Oxford, OX4 1QY, UK
 */

/**
 * Interface for a file uploaded from a HTML form 
 */

package org.melati.servlet;

import java.io.*;
import org.melati.util.*;

/**
 * We store the data uploaded from a multipart form by saving it to
 * disk.
 */
abstract public class BaseFileDataAdaptor implements FormDataAdaptor
{
  protected File file = null;
  protected String url = null;
  protected int BUFSIZE = 2048;
  MultipartFormField field = null;

  /**
   * return the data as a byte array
   * <p>
   * This could take up a lot of memory if the file is large!
   */
  public byte[] getData() {
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    InputStream in = null;
    try {
      in = new FileInputStream(getFile());
    }
    catch (Exception e) {
      e.printStackTrace();
      return new byte[0];
    }
    byte[] buff = new byte[BUFSIZE];
    int count;

    try {
      while ((count = in.read(buff, 0, buff.length)) > 0)
        out.write(buff, 0, count);
      return out.toByteArray();
    }
    catch (IOException e) {
      e.printStackTrace();
      return new byte[0];
    }
    finally {
      try {
        in.close();
        in = null;
        out.close();
        out = null;
      }
      catch (Exception e) {}
    }
  }

  /**
   * return a File object pointing to the saved data (if one exists)
   */
  public File getFile() throws Exception {
    if (file == null)
      file = calculateLocalFile();
    return file;
  }

  /**
   * return the size of the data
   */
  public long getSize() {
    try {
      return (getFile() != null) ? getFile().length() : -1;
    }
    catch (Exception e) {
      System.err.println("Error getting file length:");
      e.printStackTrace();
      return 0;
    }
  }

  /**
   * return a url to the object (if one exists)
   */
  public String getURL() throws Exception {
    if (url == null)
        url = calculateURL();
    return url;
  }

  /**
   * read data from in until the delim, work out which file to
   * save it in, and save it.
   */
  public void readData(MultipartFormField field,
                       DelimitedBufferedInputStream in,
                       byte[] delim) throws Exception {

    this.field = field;
    OutputStream out = null;
    byte[] buff = new byte[BUFSIZE];
    int count;

    try {
      out = new FileOutputStream(getFile());
      while ((count = in.readToDelimiter(buff, 0, buff.length, delim)) > 0)
        out.write(buff, 0, count);
      if (count == -1)
        throw new IOException("Didn't find boundary whilst reading field data");
    }
    catch (Exception e) {
      throw e;
    }
    finally {
      try {
        out.close();
        out = null;
      }
      catch (Exception e) {}
    }
  }

  /* Provide implementations for these 2 functions to provide your
     own policy for saving uploaded files to disk and associating
     URLs with them */

  abstract protected File calculateLocalFile() throws Exception;

  abstract protected String calculateURL() throws Exception;

}






