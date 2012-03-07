/*
 * $Source$
 * $Revision$
 *
 * Copyright (C) 2000 Tim Joyce
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
 *     Tim Joyce <timj AT paneris.org>
 */

package org.melati.template.webmacro;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.webmacro.Broker;

/**
 * Writes to a buffered output stream.
 */

public class MelatiBufferedFastWriter extends MelatiFastWriter {

  private OutputStream underlying;
  private ByteArrayOutputStream buffer;
  
  /**
   * Constructor.
   */
  public MelatiBufferedFastWriter(Broker broker, HttpServletResponse response) 
      throws IOException {
    this(broker, response.getOutputStream(), response.getCharacterEncoding());
  }

  /**
   * Constructor.
   */
  public MelatiBufferedFastWriter(Broker broker, OutputStream output, 
                                  String encoding) {
    super(broker, new ByteArrayOutputStream(), encoding);
    buffer = (ByteArrayOutputStream)outputStream;
    underlying = output;
  }


  /*
  public MelatiBufferedFastWriter(OutputStream output, String encoding)
      throws IOException {
    super(new ByteArrayOutputStream(), encoding);
    buffer = (ByteArrayOutputStream)outputStream;
    underlying = output;
  }

  public MelatiBufferedFastWriter(HttpServletResponse response) 
      throws IOException {
    this(response.getOutputStream(), response.getCharacterEncoding());
  }
  
  public MelatiBufferedFastWriter(String encoding) throws IOException {
    this(new ByteArrayOutputStream(), encoding);
  }
*/
  /**
   * @see org.melati.template.velocity.MelatiVelocityWriter#close()
   */
  public void close() throws IOException {
    super.close();
    buffer.writeTo(underlying);
    buffer.close(); 
    underlying.flush();
    underlying.close();
  }
  
  /**
   * @see org.melati.util.MelatiWriter#flush()
   */
  public void flush() throws IOException {
    out.flush();
    buffer.writeTo(underlying);
    buffer.reset(); 
    underlying.flush();
  }  

  public void reset() throws IOException {
    out.flush();
    buffer.reset();
  }

}
