package org.melati.poem;

public class NotComparablePoemException extends TypeMismatchPoemException {

  public NotComparablePoemException(Object value, PoemType type) {
    super(value, type);
  }

  public String getMessage() {
    return super.getMessage() + "\n" +
           "--- The application was trying to use a non-Comparable type " +
           "as a raw value for a PoemType with a range set";
  }
}
