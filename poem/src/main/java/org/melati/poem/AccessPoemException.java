package org.melati.poem;

public class AccessPoemException extends PoemException {
  public AccessToken token;
  public Capability capability;

  public AccessPoemException(AccessToken token, Capability capability) {
    this.token = token;
    this.capability = capability;
  }

  public AccessPoemException() {
    this(null, null);
  }

  public String getMessage() {
    return
        "You need the capability " + capability + " but your access token " +
        token + " doesn't confer it";
  }
}
