package org.melati.poem;

class RootAccessToken implements AccessToken {
  public boolean givesCapability(Capability capability) {
    return true;
  }
}

public interface AccessToken {
  boolean givesCapability(Capability capability);

  public static final AccessToken root = new RootAccessToken();
}
