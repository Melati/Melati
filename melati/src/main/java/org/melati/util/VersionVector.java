package org.melati.util;

import java.util.Vector;

public class VersionVector extends Vector implements Version {

  public VersionVector(int initialCapacity) {
    super(initialCapacity);
  }

  public VersionVector() {}

  public Object clone() {
    return super.clone();
  }
}
