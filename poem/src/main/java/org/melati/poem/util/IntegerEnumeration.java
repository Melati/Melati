/*
 * $Source$
 * $Revision$
 *
 * Copyright (C) 2000 William Chesters
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
 *     William Chesters <williamc At paneris.org>
 *     http://paneris.org/~williamc
 *     Obrechtstraat 114, 2517VX Den Haag, The Netherlands
 */

package org.melati.poem.util;

import java.util.Enumeration;
import java.util.NoSuchElementException;

/**
 * An <code>Enumeration</code> of sequential Integers.
 */
public class IntegerEnumeration implements Enumeration {
  private int limit, index;

  /**
   * Constructor with lower, included, bound and upper, excluded, bound.
   * @param start first element.
   * @param limitP upper, excluded, limit
   */
  public IntegerEnumeration(int start, int limitP) {
    this.index = start;
    this.limit = limitP;
  }

  /**
   * {@inheritDoc}
   * @see java.util.Enumeration#hasMoreElements()
   */
  public boolean hasMoreElements() {
    return index < limit;
  }

  /**
   * {@inheritDoc}
   * @see java.util.Enumeration#nextElement()
   */
  public synchronized Object nextElement() throws NoSuchElementException {
    if (index >= limit)
      throw new NoSuchElementException();
    Integer value = new Integer(index);
    index++;
    return value;
  }
}

