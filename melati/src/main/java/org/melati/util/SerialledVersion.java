package org.melati.util;

public class SerialledVersion implements Version {
  public long serial;

  public SerialledVersion(long serial) {
    this.serial = serial;
  }

  public Object clone() {
    try {
      return super.clone();
    }
    catch (CloneNotSupportedException e) {
      throw new Error("Unexpected CloneNotSupportedException");
    }
  }
}
