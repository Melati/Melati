package org.melati.util;

import java.util.*;

public class EmptyEnumeration implements Enumeration {

  public static final EmptyEnumeration it = new EmptyEnumeration();

  public boolean hasMoreElements() {
    return false;
  }

  public Object nextElement() {
    throw new NoSuchElementException();
  }
}
