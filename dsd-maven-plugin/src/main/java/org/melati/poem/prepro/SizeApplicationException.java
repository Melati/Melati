package org.melati.poem.prepro;

public class SizeApplicationException extends IllegalityException {
  public FieldDef field;

  public SizeApplicationException(FieldDef field) {
    this.field = field;
  }

  public String getMessage() {
    return
        "The column " + field + " cannot have a `size' " +
        "because that only applies to `String's";
  }
}
