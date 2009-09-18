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

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * @author timp
 * @since 8 Jun 2007
 *
 */
public class TableMap implements Map<Integer, Persistent> {

  protected Table table;
  /**
   * Constructor.
   */
  public TableMap() {
  }

  /**
   * Constructor given a Table.
   */
  public TableMap(Table t) {
    this.table = t;
  }

  /**
   * @return the table
   */
  public Table getTable() {
    return table;
  }

  /**
   * @param table the table to set
   */
  public void setTable(Table table) {
    this.table = table;
  }
  


  /** 
   * {@inheritDoc}
   * @see java.util.Map#clear()
   */
  public void clear() {
    throw new UnsupportedOperationException();
  }

  /** 
   * {@inheritDoc}
   * @see java.util.Map#containsKey(java.lang.Object)
   */
  public boolean containsKey(Object key) {
    if (key == null)
      return false;
    else {
      try { 
        table.getObject((Integer)key); 
        return true;
      } catch (NoSuchRowPoemException e) { 
        return false;
      }
    }
  }

  /** 
   * {@inheritDoc}
   * @see java.util.Map#containsValue(java.lang.Object)
   */
  public boolean containsValue(Object value) {
    Integer troid  = ((Persistent)value).troid();
    return containsKey(troid);
  }

  /** 
   * {@inheritDoc}
   * @see java.util.Map#entrySet()
   */
  public Set<Map.Entry<Integer, Persistent>> entrySet() {
    throw new UnsupportedOperationException();
  }

  /** 
   * {@inheritDoc}
   * @see java.util.Map#get(java.lang.Object)
   */
  public Persistent get(Object key) {
    return table.getObject((Integer)key);
  }

  /** 
   * {@inheritDoc}
   * @see java.util.Map#isEmpty()
   */
  public boolean isEmpty() {
    return table.cachedCount((String)null).count() == 0;
  }

  /** 
   * {@inheritDoc}
   * @see java.util.Map#keySet()
   */
  public Set<Integer> keySet() {
    throw new UnsupportedOperationException();
  }

  /** 
   * {@inheritDoc}
   * @see java.util.Map#put(K, V)
   */
  public Persistent put(Integer arg0, Persistent arg1) {
    throw new UnsupportedOperationException();
  }

  /** 
   * {@inheritDoc}
   * @see java.util.Map#putAll(java.util.Map)
   */
  public void putAll(Map<? extends Integer, ? extends Persistent> arg0) {
    throw new UnsupportedOperationException();
  }

  /** 
   * {@inheritDoc}
   * @see java.util.Map#remove(java.lang.Object)
   */
  public Persistent remove(Object key) {
    Persistent p = table.getObject((Integer)key);
    if (p != null)
      p.delete();
    return p;
  }

  /** 
   * {@inheritDoc}
   * @see java.util.Map#size()
   */
  public int size() {
    return table.cachedCount((String)null).count();
  }

  /** 
   * {@inheritDoc}
   * @see java.util.Map#values()
   */
  public Collection<Persistent> values() {
    return Collections.list(table.selection());
  }


}
