package org.melati.poem.prepro;

public class NullTroidException extends IllegalityException {
  public FieldDef field;

  public NullTroidException(FieldDef field) {
    this.field = field;
  }

  public String getMessage() {
    return "You may not make the troid column " + field + " nullable";
  }
}
