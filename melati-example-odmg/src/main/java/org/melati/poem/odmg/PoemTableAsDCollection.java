package org.melati.poem.odmg;

import java.util.*;

import org.melati.*;
import org.melati.poem.*;

/**
 * Wrapper class to prsent a Poem Table as a Collection.
 */

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

 /** removes the specified object from the collection.
  *
  * WARNING - this removes and commits it immediately!
  * WARNING2 - it also deletes entries from tables that reference this object 
  * - this means you CANNOT have circular references
  */
  public boolean remove(Object obj) 
  {  
    Persistent p = (Persistent)obj;
	 // delete all refs first
	 Enumeration refs = _wrappedTable.getDatabase().referencesTo(p);
	 while (refs.hasMoreElements())
	 {
	   Persistent q = (Persistent)refs.nextElement();
		q.deleteAndCommit();
	 }
	 
    p.deleteAndCommit();
    return true;
  }

  public Iterator iterator() 
  { 
    return new EnumerationIterator(_wrappedTable.selection());
  }

 /** 
  * returns all objects that match the query.
  * NOTE: The query string is split into the where-clause and 
  * the order-by-clause and passed to poem.
  */
  public Iterator select(String queryString) 
  {
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
    else if (orderByStart==0)
    {
      whereStart = -1; // no where clause
      orderByStart += 9;
    }
    else
    {
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
    return new EnumerationIterator(_wrappedTable.selection(whereClause,orderByClause,true));
  }

  public Object selectElement(String queryString) 
  {
    Iterator iter = select(queryString);
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
  public boolean addAll(Collection coll) { 
    throw new org.odmg.NotImplementedException(); 
  }
  public boolean containsAll(Collection coll) { 
    throw new org.odmg.NotImplementedException(); 
  }
  public boolean retainAll(Collection coll) { 
    throw new org.odmg.NotImplementedException(); 
  }
  public void clear() { 
    throw new org.odmg.NotImplementedException(); 
  }

/** utility class for converting enumerations into iterators */
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


}
