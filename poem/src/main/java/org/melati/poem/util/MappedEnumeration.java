package org.melati.util;

import java.util.Enumeration;

public abstract class MappedEnumeration implements Enumeration {
  private Enumeration enumeration;

  public MappedEnumeration(Enumeration enumeration) {
    this.enumeration = enumeration;
  }

  public boolean hasMoreElements() {
    return enumeration.hasMoreElements();
  }

  protected abstract Object mapped(Object element);

  public Object nextElement() {
    return mapped(enumeration.nextElement());
  }
}
