package org.melati.poem;

public class UnexpectedValidationPoemException extends SeriousPoemException {
  public UnexpectedValidationPoemException(ValidationPoemException exception) {
    super(exception);
  }
}
