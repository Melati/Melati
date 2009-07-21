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
 *     Tim Joyce <timj At paneris.org>
 */

package org.melati.template.velocity;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletResponse;

import org.melati.template.TemplateIOException;
import org.melati.util.MelatiWriter;

import org.apache.velocity.io.VelocityWriter;
import org.apache.velocity.util.SimplePool;

/**
 * This provides an interface for objects that output from melati.
 */
public class MelatiVelocityWriter extends MelatiWriter {

  private static SimplePool writerPool = new SimplePool(40);
  protected OutputStream outputStream;

  /**
   * Constructor.
   * @param response the response to write to
   * @throws IOException if getOutputStream throws one
   */
  public MelatiVelocityWriter(HttpServletResponse response)
      throws IOException {
    this(response.getOutputStream(), response.getCharacterEncoding());
  }

  /**
   * Constructor.
   * @param output OutputStream to write to
   * @param encoding character encoding to use
   * @throws IOException if getOutputStream throws one
   */
  public MelatiVelocityWriter(OutputStream output, String encoding) {
    // need to make this accessible to subclasses
    outputStream = output;
    out = (VelocityWriter)writerPool.get();
    if (out == null) {
      try {
        out = new VelocityWriter(new OutputStreamWriter(output, encoding),
                                 4 * 1024, true);
      } catch (UnsupportedEncodingException e) {
        throw new TemplateIOException(e);
      }
    } else {
      try {
        ((VelocityWriter)out).recycle(new OutputStreamWriter(output, encoding));
      } catch (UnsupportedEncodingException e) {
        throw new TemplateIOException(e);
      }
    }
  }

  /**
   * As we can write to the underlying peer the Flusher may not 
   * get started so we should start it here.
   * @return the underlying output
   */
  public VelocityWriter getVelocityWriter() {
    startFlushing();
    return (VelocityWriter)out;
  }

  /**
   * {@inheritDoc}
   * @see java.io.Closeable#close()
   */
  public void close()
      throws IOException {
    super.close();
    writerPool.put(getVelocityWriter());
  }

}
