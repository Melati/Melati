package org.melati.poem.odmg;

import java.util.*;

import org.melati.*;
import org.melati.poem.*;

class PoemTableAsDCollection implements org.odmg.DCollection
{
  public static final String cvs = "$Id$";

  private Table _wrappedTable = null;

  PoemTableAsDCollection(Table aTable)
  {
    _wrappedTable = aTable;
  }

  public boolean isEmpty() { return size() == 0; }
  public int size() { return _wrappedTable.count(""); }

  public boolean add(Object obj) 
  { 
    _wrappedTable.create((Persistent)obj);
	 return true;
  }

  public boolean removeAll(Collection coll) 
  {  
    Iterator iter = coll.iterator();
    while (iter.hasNext()) 
    {
       if (!remove(iter.next()))
         return false;
    }
    return true;
  }

  /** removes the specified object from the collection 
  * WARNING - this removes and commits it immediately!
  */
  public boolean remove(Object obj) 
  {  
    Persistent p = (Persistent)obj;
    p.deleteAndCommit();
    return true;
  }

  public Iterator iterator() 
  { 
    return new EnumerationIterator(_wrappedTable.selection());
  }


private class EnumerationIterator implements Iterator
{
  private Enumeration _enum = null;
  EnumerationIterator(Enumeration enum)
  {
    _enum = enum;
  }

  public boolean hasNext() { return _enum.hasMoreElements(); }
  public Object next() { return _enum.nextElement(); }
  public void remove() { throw new org.odmg.NotImplementedException(); }
}

// the following have not been implemented - yet...
  public org.odmg.DCollection query(String queryString) { throw new org.odmg.NotImplementedException(); }
  public Object[] toArray(Object[] obj) { throw new org.odmg.NotImplementedException(); }
  public Object[] toArray() { throw new org.odmg.NotImplementedException(); }
  public boolean existsElement(String obj) { throw new org.odmg.NotImplementedException(); }
  public Object selectElement(String queryString) { throw new org.odmg.NotImplementedException(); }
  public boolean contains(Object obj) { throw new org.odmg.NotImplementedException(); }
  public boolean addAll(Collection coll) { throw new org.odmg.NotImplementedException(); }
  public boolean containsAll(Collection coll) { throw new org.odmg.NotImplementedException(); }
  public boolean retainAll(Collection coll) { throw new org.odmg.NotImplementedException(); }
  public Iterator select(String queryString) { throw new org.odmg.NotImplementedException(); }
  public void clear() { throw new org.odmg.NotImplementedException(); }
}
