package org.melati.util;

import java.util.*;

public abstract class FilteredEnumeration implements SkipEnumeration {
  private Enumeration base;
  private int finished = -1;
  private Object next;

  public FilteredEnumeration(Enumeration base) {
    this.base = base;
  }

  protected abstract boolean isIncluded(Object o);

  private void probe() {
    if (finished == -1) {
      for (;;) {
        if (!base.hasMoreElements()) {
          finished = 1;
          next = null;
          break;
        }

        next = base.nextElement();
        if (isIncluded(next)) {
          finished = 0;
          break;
        }
      }
    }
  }

  public synchronized boolean hasMoreElements() {
    probe();
    return finished == 0;
  }

  public synchronized Object nextElement() {
    if (!hasMoreElements())
      throw new NoSuchElementException();

    finished = -1;
    return next;
  }

  public synchronized void skip() {
    nextElement();
  }
}
