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

  public static Vector vectorOf(Enumeration e, int roughSize) {
    Vector v = new Vector(roughSize);

    while (e.hasMoreElements())
      v.addElement(e.nextElement());

    return v;
  }

  public static Vector vectorOf(Enumeration e) {
    return vectorOf(e, 20);
  }

  public static String concatenated(String sep, Enumeration e) {
    StringBuffer b = new StringBuffer();

    if (e.hasMoreElements())
      b.append(String.valueOf(e.nextElement()));

    while (e.hasMoreElements()) {
      b.append(sep);
      b.append(String.valueOf(e.nextElement()));
    }

    return b.toString();
  }
}
