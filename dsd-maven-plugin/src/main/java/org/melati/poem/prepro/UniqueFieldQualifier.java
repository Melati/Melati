package org.melati.poem.prepro;

import java.io.*;

public class UniqueFieldQualifier extends IndexedFieldQualifier {

  public UniqueFieldQualifier(StreamTokenizer tokens) throws IOException {
    super(tokens);
    unique = true;
  }

  public void apply(FieldDef field) throws IllegalityException {
    // FIXME implement ...
  }
}
