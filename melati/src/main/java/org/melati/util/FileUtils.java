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
 *     Myles Chippendale <mylesc@paneris.org>
 *     http://paneris.org/
 *     29 Stanley Road, Oxford, OX4 1QY, UK
 */

package org.melati.util;

import java.io.*;

public class FileUtils 
{
    /**
     * Returns a File in the given <code>directory</code> which does
     * not already exist. This file is found by starting with the given
     * <code>filename</code>. If a file of this name already exists we
     * put 0 then 1,2,... before the extension until we find one that
     * does not exists.
     * <p>
     * For instance, if <code>newfile.txt</code> exists then we
     * check <code>newfile0.txt</code>, <code>newfile1.txt</code>, ...
     * until we find a new filename.
     */
    public static File withUniqueName(File file) {
      String directory = file.getParent();
      String filename = file.getName();
      int dot = filename.lastIndexOf(".");
      String start = (dot != -1) ? filename.substring(0,dot) : filename;
      String extension = (dot != -1) ? filename.substring(dot,filename.length()) : "";

      int count = 0;
      while (file.exists()) {
        filename = start + (count++) + extension;
        file = new File(directory, filename);
      }
      return file;
    }

  /**
   * Write a byte array to a given file
   */
  public static File writeFile(File file, byte[] data) throws IOException {
    FileOutputStream os=new FileOutputStream(file);
    os.write(data);
    os.close();
    return file;
  }

}


