package org.melati.poem;

public class NonRootSetAccessTokenPoemException extends PoemException {
  public AccessToken token;

  public NonRootSetAccessTokenPoemException(AccessToken token) {
    this.token = token;
  }

  public String getMessage() {
    return
        "You need to have the root access token to set a different " +
        "access token, but you only have " + token;
  }
}
