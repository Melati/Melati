package org.melati.poem;

public class Capability extends CapabilityBase {

  public Capability() {
  }

  public Capability(String name) {
    this.name = name;
  }

  protected void assertCanRead(AccessToken token) {}

  public String toString() {
    return getName();
  }
}
