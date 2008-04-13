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

import java.util.Vector;

/**
 * Supply and cache objects identified by a numeric index.
 * <p>
 * Subtypes define how the object for a given index is obtained,
 * and hence the mapping of indexes to objects used in the
 * caller.
 * <p>
 * The name is a touch misleading - the objects returned are
 * not (necessarily) indexes.
 * <p>
 * <code>null</code> object references can be cached and returned.
 * <p>
 * Individual elements can be removed from the cache or all elements 
 * may be removed.
 */

public abstract class CachedIndexFactory implements IndexFactory {
  private Vector cache = new Vector();
  private static final Object nullFromFactory = new Object();

  /**
   * @param index of the item, possibly a troid
   * @return the cached Object
   */
  protected abstract Object reallyGet(int index);

  /**
   * Get either from cache or, failing that, really get it.
   * {@inheritDoc}
   * @see org.melati.poem.util.IndexFactory#get(int)
   */
  public Object get(int index) {
    synchronized (cache) {
      if (cache.size() <= index) {
        cache.setSize(index + 1);
        Object it = reallyGet(index);
        cache.setElementAt(it == null ? nullFromFactory : it, index);
        return it;
      }
      else {
        Object it = cache.elementAt(index);
        if (it == null) {
          it = reallyGet(index);
          cache.setElementAt(it == null ? nullFromFactory : it, index);
          return it;
        }
        else if (it == nullFromFactory)
          return null;
        else
          return it;
      }
    }
  }

  /**
   * Invalidate an entry in the cache.
   * @param index the entry's index to invalidate
   */
  public void invalidate(int index) {
    cache.setElementAt(null, index);
  }

  /**
   * Invalidate whole cache.
   */
  public void invalidate() {
    cache.removeAllElements();
  }
}




