package org.melati.util;

import java.util.*;

public class ConsEnumeration implements Enumeration {
  private boolean hadHd = false;
  private Object hd;
  private Enumeration tl;

  public ConsEnumeration(Object hd, Enumeration tl) {
    this.hd = hd;
    this.tl = tl;
  }

  public synchronized boolean hasMoreElements() {
    return !hadHd || tl.hasMoreElements();
  }

  public synchronized Object nextElement() {
    if (!hadHd) 
      try {
        return hd;
      }
      finally {
        hadHd = true;
      }
    else
      return tl.nextElement();
  }
}
