package org.melati.poem.prepro;

public class DeletedTypeException extends IllegalityException {
  public FieldDef field;

  public DeletedTypeException(FieldDef field) {
    this.field = field;
  }

  public String toString() {
    return
        "The field " + field + " cannot be a deleted flag: " +
        "it isn't a non-nullable Boolean";
  }
}
