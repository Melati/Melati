package org.melati.poem;

public interface AccessToken {
  boolean givesCapability(Capability capability);

  public static final AccessToken root =
      new AccessToken() {
        public boolean givesCapability(Capability capability) {
          return true;
        }
      };
}
