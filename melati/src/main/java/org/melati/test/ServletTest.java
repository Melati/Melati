/*
 * $Source$
 * $Revision$
 *
 * Copyright (C) 2004 Tim Pizey
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
 *     Tim Pizey 
 *     http://paneris.org/~timp/
 */

package org.melati.test;

import java.io.IOException;
import java.io.OutputStream;
import java.io.File;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Tim Pizey
 * @version $Id$
 */
public class ServletTest extends HttpServlet {
  private static final long serialVersionUID = 1L;

  public void doGet(HttpServletRequest request, HttpServletResponse response) 
      throws ServletException, IOException {
    OutputStream os = response.getOutputStream();
    os.write("Simple servlet working.\n".getBytes());
    String path = new File("").getAbsolutePath();
    os.write("Default file path (where servlet container was started from):"
             .getBytes());
    os.write(path.getBytes());

  }

}
