package org.melati.poem.prepro;

import java.io.*;

public class TroidFieldQualifier extends FieldQualifier {

  public TroidFieldQualifier(StreamTokenizer tokens) {
  }

  public void apply(FieldDef field) throws IllegalityException {
    // FIXME check for duplication
    if (!field.type.equals("Integer") || field.isNullable)
      throw new TroidTypeException(field);
    field.isTroidColumn = true;
  }
}
