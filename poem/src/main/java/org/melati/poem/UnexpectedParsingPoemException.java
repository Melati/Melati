package org.melati.poem;

public class UnexpectedParsingPoemException extends SeriousPoemException {
  public UnexpectedParsingPoemException(ParsingPoemException exception) {
    super(exception);
  }
}
