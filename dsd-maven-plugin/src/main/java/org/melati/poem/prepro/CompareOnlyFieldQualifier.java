package org.melati.poem.prepro;

import java.io.*;

public class CompareOnlyFieldQualifier extends FieldQualifier {

  public CompareOnlyFieldQualifier(StreamTokenizer tokens) throws IOException {
  }

  public void apply(FieldDef field) throws IllegalityException {
    field.isCompareOnly = true;
  }
}
