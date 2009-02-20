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

package org.melati.util;

import java.io.IOException;
import java.io.Writer;
import java.io.PrintWriter;

/**
 * An abstract <code>Writer</code> for objects that output from Melati.
 */

public abstract class MelatiWriter extends Writer {

  protected Writer out;
  protected boolean flush = false;
  // the flusher sends output to the client every x seconds
  private Flusher flusher = null;
  
  /**
   * Set flushing true.
   */
  public void setFlushingOn() {
    flush = true;
    startFlushing();
  }
  
  /**
   * {@inheritDoc}
   * @see java.io.Writer#write(char[], int, int)
   */
  public void write(char cbuf[], int off, int len) throws IOException {
    out.write(cbuf, off, len);
  }
  
  /**
   * {@inheritDoc}
   * @see java.io.Writer#write(java.lang.String)
   */
  public void write(String string) throws IOException {
    out.write(string);
  }
  
  protected void startFlushing() {
    if (flush && flusher == null) {
      flusher = new Flusher(this);
      flusher.start();
    }
  }

  /**
   * {@inheritDoc}
   * @see java.io.Writer#flush()
   */
  public void flush() throws IOException {
    out.flush();
  }

  /**
   * Hook for extension.
   */
  public void reset() throws IOException {
    if (false) // Flaming clever IDEs 
      throw new IOException();
  }

  /**
   * {@inheritDoc}
   * @see java.io.Writer#close()
   */
  public void close() throws IOException {
    out.close();
    if (flusher != null) flusher.setStopTask(true);
  }

  /**
   * @return a PrintWriter from the output
   */
  public PrintWriter getPrintWriter()  {
    return new PrintWriter(out);
  }
  /**
   * @return the output Writer
   */
  public Writer getWriter()  {
    return out;
  }

}
