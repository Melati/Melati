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

package org.melati.template;

import java.io.*;
import org.melati.util.FileUtils;

/**
 * This interface allows us to interact with a file uploaded from a
 * HTML form.
 */
abstract public class FormFile 
{

  /** Some constants */
  public static final long KILOBYTE = 1024;
  public static final long MEGABYTE = 1024 * 1024;
  public static final long GIGABYTE = 1024 * 1024 * 1024;

  /** The path to a local file */
  File localFile = null;

  /** The url to this file */
  String url = null;

  /**
   * The name of the file as uploaded from the HTML form
   */
  abstract public String getUploadedFilename();

  /**
   * The contents of the file
   */
  abstract public byte[] getDataArray();

  /**
   * The contents of the file
   */
  abstract public String getDataString();

  /**
   * Returns the size of the file on the disk (if it exits)
   * otherwise returns the size of the data uploaded. These
   * could be different depending on the local encoding used
   * to convert from byte[] to char[] when we write the file
   */
  public long getSize() {
    if (localFile != null && localFile.exists())
      return localFile.length();
    return getDataArray().length;
  }

  /**
   * The size of the file as a formatted string
   */
  public String getPrettySize() {
    long size = getSize();
    String sizeString = null;
    if ((size / KILOBYTE) >= 1) {
      if ((size / MEGABYTE) >= 1) {
        if ((size / GIGABYTE) >= 1)
          sizeString = (size / GIGABYTE) + " Gb";
        else
          sizeString = (size / MEGABYTE) + " Mb";
      } else {
        sizeString = (size / KILOBYTE) + " Kb";
      }
    } else {
      sizeString = size + " bytes";
    }
    return sizeString;
  }

  /**
   * The name of the file as saved on this machine
   */
  public String getLocalName() throws Exception {
    if (localFile == null)
      localFile = calculateLocalFile();
    return localFile != null ? localFile.getName() : null;
  }

  /**
   * The path of the file as saved on this machine
   */
  public String getLocalPath() throws Exception {
    if (localFile == null)
      localFile = calculateLocalFile();
    return localFile != null ? localFile.getPath() : null;
  }

  /**
   * Calculate where to save this file. For instance, we might
   * extract the directory to save files into from the Setting Table
   */
  abstract public File calculateLocalFile() throws Exception;

  /**
   * Get a URL to this file
   */
  public String getURL() throws Exception {
    if (url == null)
      url = calculateURL();
    return url;
  }

  /**
   * Calculate a URL of this file. For instance, we might
   * extract the base URL from the Setting Table
   */
  abstract public String calculateURL() throws Exception;
  
  /**
   * Writes the data to the file pointed to by getlocalFile()
   */
  public boolean write() throws Exception {
    if (localFile == null)
      localFile = calculateLocalFile();
    if (localFile == null)
      return false;
    try {
      FileUtils.writeFile(localFile, getDataArray());
    } catch (IOException ioe) {
      return false;
    }
    return true;
  }


}






