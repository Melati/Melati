package org.melati.poem;

/**
 * FIXME make it harder to forge these?  But what would be the point?
 */

class RootAccessToken implements AccessToken {
  public boolean givesCapability(Capability capability) {
    return true;
  }
}

public interface AccessToken {
  boolean givesCapability(Capability capability);

  public static final AccessToken root = new RootAccessToken();
}
