package org.melati.poem.prepro;

import java.io.*;

public class NullableFieldQualifier extends FieldQualifier {

  public NullableFieldQualifier(StreamTokenizer tokens) {
  }

  public void apply(FieldDef field)
      throws NullTroidException, NullDeletedException {
    // oops this isn't terribly OO
    if (field.isTroidColumn)
      throw new NullTroidException(field);
    if (field.isDeletedColumn)
      throw new NullDeletedException(field);
    field.isNullable = true;
  }
}
