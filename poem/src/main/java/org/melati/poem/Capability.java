package org.melati.poem;

public class Capability extends CapabilityBase {
  protected void assertCanRead(Data data, AccessToken token) {}

  public String toString() {
    return getName();
  }
}
