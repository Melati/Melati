package org.melati.poem;

import java.util.*;

public final class Cache {

  private Hashtable table = new Hashtable();
  private CacheNode theMRU = null, theLRU = null;
  private int maxSize;
  private boolean complete = true;

  public Cache(int maxSize) {
    if (maxSize < 0)
      throw new IllegalArgumentException();
    this.maxSize = maxSize;
  }

  public synchronized void put(CacheNode value) {
    if (value == null)
      throw new NullPointerException();

    if (maxSize > 0) {
      Object previous = table.put(value.getKey(), value);
      if (previous != null) {
        table.put(value.getKey(), previous);
        throw new CacheDuplicationException();
      }
        
      value.putBefore(theMRU);
      theMRU = value;
      if (theLRU == null) theLRU = value;
      value.valid = true;
    }
  }

  public synchronized void trim() {
    for (CacheNode n = theLRU;
         n != null && table.size() > maxSize;
         n = n.nextMRU) {
      if (n.isDroppable()) {
        n.valid = false;
        if (n == theLRU) theLRU = n.prevMRU;
        if (n == theMRU) theMRU = n.nextMRU;
        n.putBefore(null);
        table.remove(n.getKey());
        complete = false;
      }
      else
        n.uncacheContents();
    }
  }

  public synchronized void uncacheContents() {
    for (Enumeration e = table.elements(); e.hasMoreElements();)
      ((CacheNode)e.nextElement()).uncacheContents();
  }

  public boolean isComplete() {
    return complete;
  }

  public synchronized CacheNode get(Object key) {
    if (table.size() > 0) {
      CacheNode value = (CacheNode)table.get(key);
      if (value != null) {
        value.putBefore(theMRU);
        theMRU = value;
        if (theLRU == null) theLRU = value;
        return value;
      }
    }

    return null;
  }
}
