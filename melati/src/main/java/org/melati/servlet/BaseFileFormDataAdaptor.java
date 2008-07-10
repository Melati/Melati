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

import java.io.OutputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.File;
import java.io.IOException;
import org.melati.util.DelimitedBufferedInputStream;

/**
 * Common elements of uploading a file from an HTML form.
 *
 * We store the data uploaded from a multipart form by saving it to
 * a file on disk and, optionally, give it an associated URL.
 */
public abstract class BaseFileFormDataAdaptor implements FormDataAdaptor {
  /** Size for byte buffers. */
  protected int BUFSIZE = 2048;

  /** The file in which to save the data. */
  protected File file = null;

  /** A URL to the data. */
  protected String url = null;

  /** Information about the uploaded file. */
  public MultipartFormField field = null;

  /**
   * @return The file in which to save the data
   */
  protected abstract File calculateLocalFile();

  /**
   * Return a URL to the saved file, null if not appropriate.
   * @return a URL to the saved file, null if not appropriate
   */
  protected abstract String calculateURL();


  /**
   * Return the data in the file as a byte array.
   * @return the data in the file as a byte array
   */
  public byte[] getData() {

    File fileLocal = getFile();
    if (fileLocal == null)
      return new byte[0];

    InputStream in = null;
    ByteArrayOutputStream out = null;
    try {
      in = new BufferedInputStream(new FileInputStream(fileLocal));
      out = new ByteArrayOutputStream();
      byte[] buff = new byte[BUFSIZE];
      int count;
      while ((count = in.read(buff, 0, buff.length)) > 0)
        out.write(buff, 0, count);
      return out.toByteArray();
    }
    catch (IOException e) {
      throw new FormDataAdaptorException(
                  "Couldn't retreive the data from the file", e);
    }
    finally {
      try {
        if (in != null) {
          in.close();
          in = null;
        }
        if (out != null) {
          out.close();
          out = null;
        }
      } catch (Exception e) {
        //Cause already thrown
        e = null; // shut PMD up
      }
    }
  }

  /**
   * Return the size of the data.
   * @return the size of the data as a <code>long</code>
   */
  public long getSize() {
    return (getFile() != null) ? getFile().length() : 0;
  }

  /**
   * Return a File object pointing to the saved data.
   * 
   * @return the {@link #file}
   */
  public File getFile() {
    if (file == null)
      file = calculateLocalFile();
    return file;
  }

  /**
   * @return Url to the data, null if there isn't an appropriate one
   */
  public String getURL() {
    if (url == null)
      url = calculateURL();
    return url;
  }

  /**
   * Read data from in until the delim, work out which file to
   * save it in, and save it.
   * 
   * @param fieldP  a {@link MultipartFormField}
   * @param in     a {@link DelimitedBufferedInputStream}
   * @param delim  the delimiter used to denote elements
   * @throws IOException if there is a problem reading the input 
   */
  public void readData(MultipartFormField fieldP,
                       DelimitedBufferedInputStream in,
                       byte[] delim) throws IOException {

    this.field = fieldP;
    OutputStream out = null;
    byte[] buff = new byte[BUFSIZE];
    int count;

    try {
      // This should be the first call to get file, so hopefully we get any
      // exceptions here
      out = new BufferedOutputStream(new FileOutputStream(getFile()));
      while ((count = in.readToDelimiter(buff, 0, buff.length, delim)) > 0)
        out.write(buff, 0, count);
      if (count == -1)
        throw new IOException(
                            "Didn't find boundary whilst reading field data");
    }
    catch (IOException e) {
      throw e;
    }
    finally {
      try {
        if (out != null) {
          out.close();
          out = null;
        }
      } catch (Exception e) {
        //Cause already thrown
        e = null; // shut PMD up
      }
    }
  }


}






