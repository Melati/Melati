package org.melati.util;

public class VersionVector extends java.util.Vector implements Version {

  public VersionVector(int initialCapacity) {
    super(initialCapacity);
  }

  public VersionVector() {}

  public Object clone() {
    return super.clone();
  }
}
