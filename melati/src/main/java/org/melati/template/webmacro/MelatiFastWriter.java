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
 *     Tim Joyce <timj@paneris.org>
 */

package org.melati.template.webmacro;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.melati.template.TemplateEngine;
import org.melati.util.MelatiWriter;
import org.webmacro.Broker;

/**
 * Writes to an output object.
 */

public class MelatiFastWriter extends MelatiWriter
    implements MelatiWebmacroWriter {

  protected OutputStream outputStream;
  
  /**
   * Constructor.
   */
  public MelatiFastWriter(Broker broker, HttpServletResponse response) 
      throws IOException {
    this(broker, response.getOutputStream(), response.getCharacterEncoding());
  }
/*  
  public MelatiFastWriter(HttpServletResponse response) 
      throws IOException {
    this(response.getOutputStream(), response.getCharacterEncoding());
  }
*/  
  /**
   * Constructor.
   */
  public MelatiFastWriter(Broker broker, OutputStream output, String encoding)
      throws IOException {
    // need to make this accessible to subclasses
    outputStream = output;
    out = org.webmacro.FastWriter.getInstance(broker, output, encoding);
  }
/*  
  public MelatiFastWriter(OutputStream output, String encoding)
      throws IOException {
    // need to make this accessible to subclasses
    outputStream = output;
    out = FastWriter.getInstance(output, encoding);
  }
*/
  /**
   * @return the peer
   */
  public org.webmacro.FastWriter getPeer() {
    // as we can write to the underlying peer, the Flusher may not get started
    // so we should start it here
    startFlushing();
    return (org.webmacro.FastWriter)out;
  }

  /**
   * Return a <code>FastWriter</code> that can be used for a while instead.
   *
   * @see #stopUsingFastWriter(FastWriter)
   */
  public org.webmacro.FastWriter getFastWriter(TemplateEngine engine) {
    return getPeer();
  }

  /**
   * Returns the underlying <code>OutputStream</code> that can be used.
   *
   * @see #stopUsingFastWriter(FastWriter)
   */
  public OutputStream getOutputStream() {
    return outputStream;
  }

  /**
   * Stop using the given <code>FastWriter</code> obtained from
   * this object.
   *
   * @see #getFastWriter(TemplateEngine)
   */
  public void stopUsingFastWriter(org.webmacro.FastWriter fw) throws IOException {
    // Do nothing
  }


}
