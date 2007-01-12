/*
 * $Source$
 * $Revision$
 *
 * Copyright (C) 2001 Myles Chippendale
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
 *     Myles Chippendale <mylesc At paneris.org>
 */
package org.melati.util;

import java.io.Writer;
import java.io.FilterWriter;
import java.io.IOException;

/**
 * A <code>FilterWriter</code> which knows how much it has written.
 */
public class FtellWriter extends FilterWriter {
  protected long position = 0;

  /**
   * Constructor.
   * @param writer Underlying writer
   */
  public FtellWriter(Writer writer) {
    super(writer);
  }

  /**
   * {@inheritDoc}
   * @see java.io.FilterWriter#write(int)
   */
  public void write(int c) throws IOException {
    out.write(c);
    ++position;
  }

  /**
   * {@inheritDoc}
   * @see java.io.FilterWriter#write(char[], int, int)
   */
  public void write(char buf[], int off, int len) throws IOException {
    out.write(buf, off, len);
    position += len;
  }

  /**
   * {@inheritDoc}
   * @see java.io.FilterWriter#write(java.lang.String, int, int)
   */
  public void write(String buf, int off, int len) throws IOException {
    out.write(buf, off, len);
    position += len;
  }

  /**
   * @return number of characters written
   */
  public long ftell() {
    return position;
  }
}
