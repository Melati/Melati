package org.melati.poem.odmg;

import java.util.*;

import org.melati.*;
import org.melati.poem.*;

class PoemTableAsDCollection implements org.odmg.DCollection
{
  public static final String cvs = "$Id";

  private Table _wrappedTable = null;

  PoemTableAsDCollection(Table aTable)
  {
    _wrappedTable = aTable;
  }

  public boolean isEmpty() { return size() == 0; }
  public int size() { return _wrappedTable.count(""); }

  public org.odmg.DCollection query(String queryString) { throw new org.odmg.NotImplementedException(); }
  public Object[] toArray(Object[] obj) { throw new org.odmg.NotImplementedException(); }
  public Object[] toArray() { throw new org.odmg.NotImplementedException(); }
  public boolean existsElement(String obj) { throw new org.odmg.NotImplementedException(); }
  public Object selectElement(String queryString) { throw new org.odmg.NotImplementedException(); }
  public boolean remove(Object obj) { throw new org.odmg.NotImplementedException(); }
  public boolean contains(Object obj) { throw new org.odmg.NotImplementedException(); }
  public boolean add(Object obj) { throw new org.odmg.NotImplementedException(); }
  public boolean addAll(Collection coll) { throw new org.odmg.NotImplementedException(); }
  public boolean containsAll(Collection coll) { throw new org.odmg.NotImplementedException(); }
  public boolean retainAll(Collection coll) { throw new org.odmg.NotImplementedException(); }
  public boolean removeAll(Collection coll) { throw new org.odmg.NotImplementedException(); }
  public Iterator iterator() { throw new org.odmg.NotImplementedException(); }
  public Iterator select(String queryString) { throw new org.odmg.NotImplementedException(); }
  public void clear() { throw new org.odmg.NotImplementedException(); }
}
