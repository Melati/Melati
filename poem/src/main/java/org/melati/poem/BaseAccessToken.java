package org.melati.poem;

public class BaseAccessToken implements AccessToken {
  public boolean givesCapability(Capability capability) {
    return false;
  }
}
