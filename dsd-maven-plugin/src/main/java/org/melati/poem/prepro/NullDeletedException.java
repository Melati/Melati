package org.melati.poem.prepro;

public class NullDeletedException extends IllegalityException {
  public FieldDef field;

  public NullDeletedException(FieldDef field) {
    this.field = field;
  }

  public String getMessage() {
    return "You may not make the deleted-flag column " + field + " nullable";
  }
}
