package org.melati.util;

import java.util.*;

public class EnumUtils {
  private EnumUtils() {}

  public static int skip(Enumeration e, int n) {
    int c = 0;
    if (e instanceof SkipEnumeration) {
      SkipEnumeration s = (SkipEnumeration)e;
      while (c < n && s.hasMoreElements()) {
        s.skip();
        ++c;
      }
    }
    else
      while (c < n && e.hasMoreElements()) {
        e.nextElement();
        ++c;
      }

    return c;
  }

  public static Vector initial(Enumeration e, int n) {
    Vector v = new Vector(n);

    while (n > 0 && e.hasMoreElements()) {
      v.addElement(e.nextElement());
      --n;
    }

    return v;
  }
}
