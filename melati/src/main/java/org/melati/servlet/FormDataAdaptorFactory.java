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

import java.util.Hashtable;

import org.melati.Melati;
import org.melati.poem.AccessToken;
import org.melati.poem.PoemTask;
import org.melati.poem.PoemThread;

/**
 * A way to implement policies about how to save uploaded files.
 */
public abstract class FormDataAdaptorFactory {
  
  /**
   * We need to establish the user and set up any request specific melati stuff
   * so that we can verify the user has permission for this task, and use
   * melati Table / Objects to manipulate what the FormDataAdaptor does.
   *
   * Please note that when uploading a file for a record that has not yet been
   * inserted (ie whilst adding), melati.getObject will return null.
   *
   * @param melati the {@link Melati}
   * @param field  a {@link MultipartFormField} to process
   * @return a new {@link FormDataAdaptor}
   */
  
  public FormDataAdaptor get(final Melati melati, 
                             final MultipartFormField field) {

    final Hashtable holder = new Hashtable();
    if (melati.getDatabase() == null) {
      holder.put("hereiam",getIt(melati,field));
    } else {
      melati.loadTableAndObject();
      holder.put("hereiam",getIt(melati,field));
    }
    return (FormDataAdaptor)holder.get("hereiam");
  }
  
  /**
   * Implements different policies for saving uploaded files depending
   * on the details of the file and the state of the application.
   *
   * @param     melati  the state of (this call to) the application
   * @param     field   details of the uploaded file
   * @return    an adaptor which will save the contents of the file
   */
  public abstract FormDataAdaptor getIt(Melati melati, 
                                        MultipartFormField field);
}

