package org.melati.poem;

public class Data implements Cloneable {
  Object[] extras;
  boolean exists = true;
  boolean dirty = false;

  public Object clone() {
    Data other;
    try {
      other = (Data)super.clone();
    }
    catch (CloneNotSupportedException e) {
      throw new PoemBugPoemException();
    }
    other.extras = extras == null ? null : (Object[])extras.clone();
    return other;
  }

  public int dsdDataCount() {
    return 0;
  }
}
