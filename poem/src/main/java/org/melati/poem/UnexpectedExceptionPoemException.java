package org.melati.poem;

public class UnexpectedExceptionPoemException extends SeriousPoemException {
  public UnexpectedExceptionPoemException(Exception exception) {
    super(exception);
  }

  public String getMessage() {
    return
        "An exception occurred in a context where it was very unexpected:\n" +
        subException;
  }
}
