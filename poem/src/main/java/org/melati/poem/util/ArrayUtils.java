package org.melati.util;

import java.util.*;

public class ArrayUtils {

  private ArrayUtils() {}

  public static Object[] arrayOf(Vector v) {
    Object[] arr;
    synchronized (v) {
      arr = new Object[v.size()];
      v.copyInto(arr);
    }

    return arr;
  }

  public static Object[] added(Object[] xs, Object y) {
    Object[] xsx = (Object[])java.lang.reflect.Array.newInstance(
                       xs.getClass().getComponentType(), xs.length + 1);
    System.arraycopy(xs, 0, xsx, 0, xs.length);
    xsx[xs.length] = y;
    return xsx;
  }
}
