package org.melati.poem;

public class UnexpectedNoSuchRowException extends SeriousPoemException {
  public UnexpectedNoSuchRowException(NoSuchRowPoemException exception) {
    super(exception);
  }
}
