package org.melati.poem.prepro;

public class TroidTypeException extends IllegalityException {
  public FieldDef field;

  public TroidTypeException(FieldDef field) {
    this.field = field;
  }

  public String toString() {
    return
        "The field " + field + " cannot be a troid: " +
        "it isn't a non-nullable Integer";
  }
}
