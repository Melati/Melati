package org.melati.poem;

public class AccessPoemException extends PoemException {
  public AccessToken token;
  public Capability capability;

  public AccessPoemException(AccessToken token, Capability capability) {
    this.token = token;
    this.capability = capability;
  }

  // FIXME toString
}
