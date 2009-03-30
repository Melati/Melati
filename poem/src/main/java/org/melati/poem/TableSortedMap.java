/*
 * $Source$
 * $Revision$
 *
 * Copyright (C) 2007 Tim Pizey
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
 *     Tim Pizey <timp At paneris.org>
 *     http://paneris.org/~timp
 */
package org.melati.poem;

import java.util.Comparator;
import java.util.Enumeration;
import java.util.NoSuchElementException;
import java.util.SortedMap;

/**
 * @author timp
 * @since 8 Jun 2007
 *
 */
public class TableSortedMap extends TableMap implements SortedMap {

  /**
   * Constructor for setter injection.
   */
  public TableSortedMap() {
  }

  /**
   * Constructor for constructor injection.
   * @param t
   */
  public TableSortedMap(Table t) {
    super(t);
  }

  /**
   * We use native ordering. 
   * {@inheritDoc}
   * @see java.util.SortedMap#comparator()
   */
  public Comparator comparator() {
    return null;
  }

  /** 
   * {@inheritDoc}
   * @see java.util.SortedMap#firstKey()
   */
  public Object firstKey() {
    if(table.cachedCount((String)null).count() == 0) 
      throw new NoSuchElementException();
    try { 
      table.getObject(0);
      return new Integer(0);
    } catch (NoSuchRowPoemException e) { 
      Persistent p = table.firstSelection(null);
      return p.troid();
    }
  }

  /** 
   * {@inheritDoc}
   * 
   * NOTE first attempt using table.getMostRecentTroid() does not take 
   * account of deletion as mostRecentTroid is never decremented 
   * @see java.util.SortedMap#lastKey()
   */
  public Object lastKey() {
    if(table.cachedCount((String)null).count() == 0) 
      throw new NoSuchElementException();
    Enumeration them  = table.selection(null, table.getDatabase().getDbms().getQuotedName("id") + " DESC", false);
    return  ((Persistent)them.nextElement()).troid();
  }

  /** 
   * {@inheritDoc}
   * @see java.util.SortedMap#subMap(K fromKey, K toKey)
   */
  public SortedMap subMap(Object fromKey, Object toKey) {
    throw new UnsupportedOperationException();
  }

  /** 
   * {@inheritDoc}
   * @see java.util.SortedMap#headMap(K)
   */
  public SortedMap headMap(Object toKey) {
    throw new UnsupportedOperationException();
  }
  /** 
   * {@inheritDoc}
   * @see java.util.SortedMap#tailMap(K)
   */
  public SortedMap tailMap(Object arg0) {
    throw new UnsupportedOperationException();
  }

}
