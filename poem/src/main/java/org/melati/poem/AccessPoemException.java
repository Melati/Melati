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

  public String getActionDescription() {
    return null;
  }

  public String getMessage() {
    String ad = getActionDescription();
    return
        "You need the capability " + capability +
        (ad == null ? "" : "to " + ad + " ") + "but your access token " +
        token + " doesn't confer it";
  }
}
