package org.melati.poem;

public class StringLengthValidationPoemException
    extends ValidationPoemException {
  public StringLengthValidationPoemException(PoemType type, String value) {
    super(type, value);
  }

  public String toString() {
    String value = (String)super.value;
    return
        "The string \"" +  (value.length() > 30 ? "<long>" : value) + "\" " +
        "is too long, at " + value.length() + " characters, for " + type;
  }
}
