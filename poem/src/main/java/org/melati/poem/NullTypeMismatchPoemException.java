package org.melati.poem;

public class NullTypeMismatchPoemException extends TypeMismatchPoemException {
  public NullTypeMismatchPoemException(PoemType type) {
    super(null, type);
  }
}
