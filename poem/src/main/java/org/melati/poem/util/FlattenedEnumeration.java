package org.melati.util;

import java.util.*;

public class FlattenedEnumeration implements Enumeration {
  private Enumeration enumerations;
  private Enumeration enumeration = null;

  public FlattenedEnumeration(Enumeration enumerations) {
    this.enumerations = enumerations;
  }

  public FlattenedEnumeration(Enumeration a, Enumeration b) {
    this.enumerations =
        new ConsEnumeration(a, new ConsEnumeration(b, EmptyEnumeration.it));
  }

  private void probe() {
    while ((enumeration == null || !enumeration.hasMoreElements()) &&
           enumerations.hasMoreElements())
      enumeration = (Enumeration)enumerations.nextElement();
  }

  public synchronized boolean hasMoreElements() {
    probe();
    return enumeration != null && enumeration.hasMoreElements();
  }

  public synchronized Object nextElement() {
    probe();
    if (enumeration == null)
      throw new NoSuchElementException();
    return enumeration.nextElement();
  }
}
