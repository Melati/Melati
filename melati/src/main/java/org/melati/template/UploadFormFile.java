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
import java.net.*;
import org.melati.*;
import org.melati.servlet.*;
import org.melati.util.*;

public class UploadFormFile extends FormFile
{

  FormField field = null;
  Melati melati = null;

  public UploadFormFile(FormField field, Melati melati) {
    this.field = field;
    this.melati = melati;
  }

  /**
   * Returns the name of a file which has
   * been uploaded, null if it is a normal form variable
   */
  public String getUploadedFilename() {
    if (field == null)
      return null;
    return field.getFileName();
  }

  /**
   * Returns the contents of a file which has
   * been uploaded. Note that you could use getForm(s) to get
   * the contents of the file in a String - this function should
   * be a better way to manipulate large files.
   */
  public byte[] getDataArray() {
    if (field == null)
      return new byte[0];
    return field.getData();
  }

  public String getDataString() {
    return new String(getDataArray());
  }

  public File calculateLocalFile() throws Exception {
    String parent = (String)melati.getDatabase().getSettingTable().
                      getOrDie("UploadDir");
    return FileUtils.withUniqueName(
       new File(parent, URLEncoder.encode(getUploadedFilename())));
  }

  public String calculateURL() throws Exception {
    String parent = (String)melati.getDatabase().getSettingTable().
                      getOrDie("UploadURL");
    return (parent != null) ? parent + File.separatorChar + getLocalName() : null;
  }


}






