package org.melati.poem;

public class ValidationPoemException extends NormalPoemException {
  public PoemType type;
  public Object value;

  public ValidationPoemException(PoemType type, Object value,
                                 Exception exception) {
    super(exception);
    this.type = type;
    this.value = value;
  }

  public ValidationPoemException(PoemType type, Object value) {
    this(type, value, null);
  }
}
