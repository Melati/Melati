package org.melati.util;

import java.util.*;

public class LimitedEnumeration implements SkipEnumeration {

  private Enumeration base;
  private int limit;
  private int seen = 0;

  public LimitedEnumeration(Enumeration base, int limit) {
    this.base = base;
    this.limit = limit;
  }

  public synchronized boolean hasMoreElements() {
    return seen < limit && base.hasMoreElements();
  }

  public synchronized Object nextElement() throws NoSuchElementException {
    if (seen >= limit)
      throw new NoSuchElementException();
    Object it = base.nextElement();
    ++seen;
    return it;
  }

  public synchronized void skip() throws NoSuchElementException {
    if (seen >= limit)
      throw new NoSuchElementException();
    if (base instanceof SkipEnumeration)
      ((SkipEnumeration)base).skip();
    else
      base.nextElement();

    ++seen;
  }
}
