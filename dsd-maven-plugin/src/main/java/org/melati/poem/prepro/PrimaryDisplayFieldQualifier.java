package org.melati.poem.prepro;

import java.io.*;

public class PrimaryDisplayFieldQualifier extends FieldQualifier {

  public PrimaryDisplayFieldQualifier(StreamTokenizer tokens) {
  }

  public void apply(FieldDef field) {
    field.isPrimaryDisplayColumn = true;
  }
}
