package org.melati.util;

import java.util.*;

public final class Cache {

  private Hashtable table = new Hashtable();
  private CacheNode theMRU = null, theLRU = null;
  private int maxSize;

  public Cache(int maxSize) {
    setSize(maxSize);
  }

  public void setSize(int maxSize) {
    if (maxSize < 0)
      throw new IllegalArgumentException();
    this.maxSize = maxSize;
  }

  public synchronized void put(CacheNode value) {
    if (value == null)
      throw new NullPointerException();

    trim(maxSize);

    Object previous = table.put(value.getKey(), value);
    if (previous != null) {
      table.put(value.getKey(), previous);
      throw new CacheDuplicationException();
    }

    value.putBefore(theMRU);
    theMRU = value;
    if (theLRU == null) theLRU = value;
  }

  public synchronized void trim(int maxSize) {
    CacheNode n = theLRU;
    while (n != null && table.size() > maxSize) {
      CacheNode nn = n.prevMRU;
      if (n.drop()) {
        if (n == theLRU) theLRU = n.prevMRU;
        if (n == theMRU) theMRU = n.nextMRU;
        n.putBefore(null);
        table.remove(n.getKey());
      }
      else
        n.uncacheContents();
      n = nn;
    }
  }

  public synchronized void uncacheContents() {
    for (Enumeration e = table.elements(); e.hasMoreElements();)
      ((CacheNode)e.nextElement()).uncacheContents();
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

  public synchronized void iterate(Procedure f) {
    for (CacheNode n = theLRU; n != null; n = n.prevMRU)
      f.apply(n);
  }

  public synchronized void dumpAnalysis() {
    Hashtable inLRU = new Hashtable();
    int numFromLRU = 0;

    // System.err.println("-- LRU->MRU");

    for (CacheNode n = theLRU; n != null; n = n.prevMRU) {
      // System.err.println("[" + n + "]");
      ++numFromLRU;
      if (!table.containsKey(n.getKey()))
        System.err.println("*** ERROR " + n + " is not in the table");
      if (inLRU.containsKey(n.getKey()))
        System.err.println("*** ERROR " + n + " is in LRU->MRU twice");
      inLRU.put(n.getKey(), n);
    }

    Hashtable inMRU = new Hashtable();
    int numFromMRU = 0;
    int contentsSize = 0;

    // System.err.println("-- MRU->LRU");

    for (CacheNode n = theMRU; n != null; n = n.nextMRU) {
      // System.err.println("[" + n + "]");
      ++numFromMRU;
      if (!table.containsKey(n.getKey()))
        System.err.println("*** ERROR " + n + " is not in the table");
      if (inMRU.containsKey(n.getKey()))
        System.err.println("*** ERROR " + n + " is in MRU->LRU twice");
      inMRU.put(n.getKey(), n);
      if (!inLRU.containsKey(n.getKey()))
        System.err.println("*** ERROR " + n + " is in LRU->MRU " +
                           "but not MRU->LRU");
      contentsSize += n.analyseContents();
    }

    if (numFromMRU != numFromMRU && numFromMRU != table.size())
      System.err.println("*** ERROR the table has " + table.size() +
                         " elements but LRU->MRU and vv. have " +
                         numFromLRU + " & " + numFromMRU);
    else
      System.err.println("Cache size: " + numFromMRU);

    System.err.println("Contents size: " + contentsSize);
  }
}
