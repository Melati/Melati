package org.melati.poem.prepro;

public class StringSizeZeroException extends IllegalityException {
  public FieldDef field;

  public StringSizeZeroException(FieldDef field) {
    this.field = field;
  }

  public String getMessage() {
    return "The column " + field + " is has no defined size";
  }
}
