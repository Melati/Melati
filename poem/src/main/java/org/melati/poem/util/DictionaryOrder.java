package org.melati.util;

public class DictionaryOrder implements Order {

  public static DictionaryOrder vanilla = new DictionaryOrder();

  public boolean lessOrEqual(Object a, Object b) {
    return !(((String)a).compareTo((String)b) > 0);
  }
}
