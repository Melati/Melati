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
 * An <code>Enumeration</code> representing the first <code>n</code> 
 * elements of another <code>Enumeration</code>.
 */
public class LimitedEnumeration<T> implements SkipEnumeration<T>{

  private Enumeration<T> base;
  private int limit;
  private int seen = 0;

  /**
   * Constructor.
   * @param base the underlying Enumeration
   * @param limit the excluded limit
   */
  public LimitedEnumeration(Enumeration<T> base, int limit) {
    this.base = base;
    this.limit = limit;
  }

  /**
   * {@inheritDoc}
   * @see java.util.Enumeration#hasMoreElements()
   */
  public synchronized boolean hasMoreElements() {
    return seen < limit && base.hasMoreElements();
  }

  /**
   * {@inheritDoc}
   * @see java.util.Enumeration#nextElement()
   */
  public synchronized T nextElement() throws NoSuchElementException {
    if (seen >= limit)
      throw new NoSuchElementException();
    T it = base.nextElement();
    ++seen;
    return it;
  }

  /**
   * {@inheritDoc}
   * @see org.melati.poem.util.SkipEnumeration#skip()
   */
  public synchronized void skip() throws NoSuchElementException {
    if (seen >= limit)
      throw new NoSuchElementException();
    if (base instanceof SkipEnumeration)
      ((SkipEnumeration<T>)base).skip();
    else
      base.nextElement();

    ++seen;
  }
}
