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

import java.io.File;
import java.io.IOException;
import org.melati.util.DelimitedBufferedInputStream;

/**
 * An interface to the data portion of a MultipartFormField.
 * <p>
 * This data is read in by <code>readData</code> and stored
 * somewhere for later access by the other functions.
 * <p>
 * A class implementing FormDataAdaptor needs to implement at least
 * the <code>getData()</code> and <code>getSize()</code> functions
 * but need not provide a URL or a File for its data.
 */

public interface FormDataAdaptor {

  /**
   * Return the data as a byte array.
   */

  byte[] getData();

  /**
   * Return the size of the data.
   */

  long getSize();

  /**
   * Return a File object pointing to the saved data or null
   * if none exists.
   */

  File getFile();

  /**
   * Return a url to the object or null if none exists.
   */

  String getURL();

  /**
   * Read data from <code>in</code> until the delim is found and
   * save the data so that we can access it again.
   */

  void readData(MultipartFormField field,
                DelimitedBufferedInputStream in,
                byte[] delim) throws IOException;
}






