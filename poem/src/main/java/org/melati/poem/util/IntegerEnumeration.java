package org.melati.util;

import java.util.*;

public class IntegerEnumeration implements Enumeration {
  private int start, limit, i;

  public IntegerEnumeration(int start, int limit) {
    this.start = start;
    this.limit = limit;
    this.i = start;
  }

  public boolean hasMoreElements() {
    return i < limit;
  }

  public synchronized Object nextElement() throws NoSuchElementException {
    if (i >= limit)
      throw new NoSuchElementException();
    return new Integer(i++);
  }
}
