package org.melati.poem.prepro;

import java.io.*;

public class DeletedFieldQualifier extends FieldQualifier {

  public DeletedFieldQualifier(StreamTokenizer tokens) {
  }

  public void apply(FieldDef field) throws IllegalityException {
    // FIXME check for duplication
    if (!field.type.equals("Boolean") || field.isNullable)
      throw new DeletedTypeException(field);
    field.isDeletedColumn = true;
  }
}
