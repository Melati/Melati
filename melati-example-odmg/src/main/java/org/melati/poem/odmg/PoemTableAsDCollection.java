/*
 * $Source$
 * $Revision$
 *
 * Copyright (C) 2000 Chris Kimpton
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
 *     Chris Kimpton (kimtoc@techie.com)
 *
 */
package org.melati.poem.odmg;

import java.util.Iterator;
import java.util.Collection;
import java.util.Enumeration;

import org.melati.poem.Persistent;
import org.melati.poem.Table;

/**
 * Wrapper class to present a Poem Table as a Collection.
 */

class PoemTableAsDCollection<P extends Persistent> implements org.odmg.DCollection {

  private Table<P> _wrappedTable = null;

  PoemTableAsDCollection(Table<P> aTable) {
    _wrappedTable = aTable;
  }

  public boolean isEmpty() { return size() == 0; }
  public int size() { return _wrappedTable.count(); }

  @SuppressWarnings("unchecked")
  public boolean add(Object obj) { 
    _wrappedTable.create((P)obj);
    return true;
  }

  public boolean removeAll(@SuppressWarnings("rawtypes") Collection coll) {  
    Iterator<?> iter = coll.iterator();
    while (iter.hasNext()) {
       if (!remove(iter.next()))
         return false;
    }
    return true;
  }

 /** 
  * Removes the specified object from the collection.
  *
  * WARNING - this removes and commits it immediately!
  * WARNING2 - it also deletes entries from tables that reference this object 
  * - this means you CANNOT have circular references
  */
  public boolean remove(Object obj) {  
    @SuppressWarnings("unchecked")
    P p = (P)obj;
    // delete all references first
    Enumeration<Persistent> refs = _wrappedTable.getDatabase().referencesTo(p);
    while (refs.hasMoreElements()) {
      Persistent q = (Persistent)refs.nextElement();
      q.deleteAndCommit();
    }

    p.deleteAndCommit();
    return true;
  }

  public Iterator<P> iterator() { 
    return new EnumerationIterator<P>(_wrappedTable.selection());
  }

 /** 
  * returns all objects that match the query.
  * NOTE: The query string is split into the where-clause and 
  * the order-by-clause and passed to poem.
  */
  public Iterator<P> select(String queryString) {
    String lowerCaseQueryString = queryString.toLowerCase();
    int whereStart = lowerCaseQueryString.indexOf("where ");
    if (whereStart<0) 
      whereStart = 0;
    else
      whereStart += 6;

    int orderByStart = lowerCaseQueryString.indexOf("order by ");
    int whereEnd = 0;
    if (orderByStart<0)
      whereEnd = queryString.length();
    else if (orderByStart==0) {
      whereStart = -1; // no where clause
      orderByStart += 9;
    }
    else {
      whereEnd = orderByStart;
      orderByStart += 9;
    }
    
    String whereClause = "";
    String orderByClause = "";
    if (whereStart>=0)
      whereClause = queryString.substring(whereStart,whereEnd);
    if (orderByStart>=0)
      orderByClause = queryString.substring(orderByStart,queryString.length());

/*
    System.err.println("[poem-odmg]query string="+queryString);
    System.err.println("[poem-odmg]where start="+whereStart);
    System.err.println("[poem-odmg]where end="+whereEnd);
    System.err.println("[poem-odmg]order by start="+orderByStart);
    System.err.println("[poem-odmg]where clause="+whereClause);
    System.err.println("[poem-odmg]order by clause="+orderByClause);
*/
    return new EnumerationIterator<P>(
        _wrappedTable.selection(whereClause,orderByClause,true));
  }

  public Object selectElement(String queryString) {
    Iterator<P> iter = select(queryString);
    if (iter.hasNext())
      return iter.next();
    return null;
  }

// the following have not been implemented - yet...
  public org.odmg.DCollection query(String queryString) { 
    throw new org.odmg.NotImplementedException(); 
  }
  public Object[] toArray(Object[] obj) { 
    throw new org.odmg.NotImplementedException(); 
  }
  public Object[] toArray() { 
    throw new org.odmg.NotImplementedException(); 
  }
  public boolean existsElement(String obj) { 
    throw new org.odmg.NotImplementedException(); 
  }
  public boolean contains(Object obj) { 
    throw new org.odmg.NotImplementedException(); 
  }
  public boolean addAll(@SuppressWarnings("rawtypes") Collection coll) { 
    throw new org.odmg.NotImplementedException(); 
  }
  public boolean containsAll(@SuppressWarnings("rawtypes") Collection coll) { 
    throw new org.odmg.NotImplementedException(); 
  }

  public boolean retainAll(@SuppressWarnings("rawtypes") Collection coll) { 
    throw new org.odmg.NotImplementedException(); 
  }
  public void clear() { 
    throw new org.odmg.NotImplementedException(); 
  }

/** Utility class for converting enumerations into iterators 
 * @param <T>*/
private class EnumerationIterator<T> implements Iterator<T> {
  private Enumeration<T> _enum = null;
  EnumerationIterator(Enumeration<T> en) {
    _enum = en;
  }

  public boolean hasNext() { return _enum.hasMoreElements(); }
  public T next() { return _enum.nextElement(); }
  public void remove() { throw new org.odmg.NotImplementedException(); }
}


}
