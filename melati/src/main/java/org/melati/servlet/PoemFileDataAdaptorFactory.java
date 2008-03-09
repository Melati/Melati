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

package org.melati.servlet;

import org.melati.Melati;
import org.melati.poem.AccessToken;
import org.melati.poem.Database;
import org.melati.poem.PoemException;
import org.melati.poem.PoemTask;
import org.melati.poem.PoemThread;

/**
 * Save the uploaded file to disk in a particular directory which has a
 * particular URL.
 * 
 * We get these values from the values of <code>UploadDir</code> and
 * <code>UploadURL</code> in the Setting table of the current Database.
 */
public class PoemFileDataAdaptorFactory extends FormDataAdaptorFactory {

  private String uploadDir;

  private String uploadURL;

  /**
   * Get the defaulted parameters for the adaptor from the database and create
   * it.
   * 
   * @param melati
   *          the {@link Melati}
   * @param field
   *          a {@link MultipartFormField}
   * @return a {@link FormDataAdaptor}
   */
  public synchronized FormDataAdaptor getIt(Melati melati,
      MultipartFormField field) {

    if (uploadDir == null || uploadURL == null) {
      final Database db = melati.getDatabase();
      if (PoemThread.inSession()) {
        uploadDir = (String) db.getSettingTable().getOrDie("UploadDir");
        uploadURL = (String) db.getSettingTable().getOrDie("UploadURL");
      } else {
        db.inSession(AccessToken.root, new PoemTask() {
          public void run() throws PoemException {
            uploadDir = (String) db.getSettingTable().getOrDie("UploadDir");
            uploadURL = (String) db.getSettingTable().getOrDie("UploadURL");
          }

          public String toString() {
            return "Getting UploadDir and UploadURL settings";
          }
        });
      }
    }
    return new DefaultFileDataAdaptor(melati, melati.getConfig().getRealPath()
        + uploadDir, uploadURL);
  }
}
