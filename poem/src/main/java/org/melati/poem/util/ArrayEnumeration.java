package org.melati.util;

import java.util.*;

public class ArrayEnumeration implements Enumeration {
  private Object[] a;
  private int i;

  public ArrayEnumeration(Object[] a) {
    this.a = a;
    i = 0;
  }

  public boolean hasMoreElements() {
    return i < a.length;
  }

  public Object nextElement() {
    if (i < a.length)
      return a[i++];
    else
      throw new NoSuchElementException();
  }
}
