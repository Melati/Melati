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
import java.io.IOException;

/**
 * Save uploaded files in a temporary file which is deleted when
 * the JVM exits.
 */
public class TemporaryFileDataAdaptor extends BaseFileDataAdaptor {

  private String temporaryFileName;
  
  /**
   * Constructor.
   */
  public TemporaryFileDataAdaptor() {
    temporaryFileName = "melati";
  }
  /**
   * Constructor specifying temporary file name.
   * @param temporaryFileName the temporary fle name ot use
   */
  public TemporaryFileDataAdaptor(String temporaryFileName) {
    this.temporaryFileName = temporaryFileName;
  }
  
  protected File calculateLocalFile() {
    File fileL = null;
    try {
      fileL = File.createTempFile(temporaryFileName, null);
      fileL.deleteOnExit();
      return fileL;
    }
    catch (IOException ioe) {
      throw new FormDataAdaptorException(
        "Couldn't save the uploaded file in a temporary file", ioe);
    }
  }

  protected String calculateURL() {
    return null;
  }
}






