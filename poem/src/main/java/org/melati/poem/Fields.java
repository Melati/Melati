package org.melati.poem;

public class Fields implements Cloneable {
  Object[] extras;
  boolean exists = true;

  public Object clone() {
    Fields other;
    try {
      other = (Fields)super.clone();
    }
    catch (CloneNotSupportedException e) {
      throw new PoemBugPoemException();
    }
    other.extras = extras == null ? null : (Object[])extras.clone();
    return other;
  }

  public int dsdFieldsCount() {
    return 0;
  }
}
