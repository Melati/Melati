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
import org.melati.*;
import org.melati.poem.*;

/**
 * An Interface to create a FormDataAdaptor from a melati and
 * the field which was upload
 */
public class UploadDirDataAdaptorFactory implements FormDataAdaptorFactory
{

  String uploadDir = null;
  String uploadURL = null;

  /**
   * Returns the FormDataAdaptor appropriate for this field with
   * this call to melati
   */
  public FormDataAdaptor get(final Melati melati, MultipartFormField field) {

    /* If there is no filename it will be a form-data field, i.e. don't
     * bother saving it as a file */
    if (field.getUploadedFileName().equals("")) { // no file uploaded
      return new MemoryDataAdaptor();
    }
    /* Otherwise, save the data to a file in a given default directory -
     * DefaultFileDataAdaptor does this for us.
     * In this case we find the uploadDir (and uploadURL) from the database. */
    else {                             
      // Get the uploadDir and uploadURL from the database
      if (uploadDir == null || uploadURL == null) {
        final UploadDirDataAdaptorFactory _this = this;
        melati.getDatabase().inSession(AccessToken.root,
          new PoemTask() {
            public void run() throws PoemException {
                _this.uploadDir = (String)melati.getDatabase().getSettingTable().
                                    getOrDie("UploadDir");
                _this.uploadURL = (String)melati.getDatabase().getSettingTable().
                                    getOrDie("UploadURL");
            }
            public String toString() {
              return "Getting upload directory and url settings";
            }
          });
        }
      return new DefaultFileDataAdaptor(uploadDir, uploadURL);
    }
  }
}






