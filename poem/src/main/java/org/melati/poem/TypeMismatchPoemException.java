package org.melati.poem;

public class TypeMismatchPoemException extends AppBugPoemException {
  public Object value;
  public PoemType type;

  public TypeMismatchPoemException(Object value, PoemType type) {
    this.value = value;
    this.type = type;
  }

  public String getMessage() {
    String s;
    try {
      s = String.valueOf(value);
      if (s.length() >= 100) s = "<long>";
    }
    catch (Exception e) {
      s = "<error>";
    }
    return
      "An unexpected type mismatch happened\n" +
      "Expected: " + type + "\n" +
      "Got: " + (value == null ? "null" : value.getClass().getName()) + "\n" +
      "Value: " + s;
  }
}
