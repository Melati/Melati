package org.melati.poem;

public interface AccessToken {
  boolean givesCapability(Capability capability);

  static final AccessToken root =
      new AccessToken() {
        public boolean givesCapability(Capability capability) {
          return true;
        }
      };
}
