package org.melati.poem;

public class ValidationPoemException extends NormalPoemException {
  public PoemType type;
  public Object value;
  public Exception exception;

  public ValidationPoemException(PoemType type, Object value,
                                 Exception exception) {
    this.type = type;
    this.value = value;
    this.exception = exception;
  }

  public ValidationPoemException(PoemType type, Object value) {
    this(type, value, null);
  }
}
