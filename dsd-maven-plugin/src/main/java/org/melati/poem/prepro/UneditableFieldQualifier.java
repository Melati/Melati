package org.melati.poem.prepro;

import java.io.*;

public class UneditableFieldQualifier extends FieldQualifier {

  public UneditableFieldQualifier(StreamTokenizer tokens) {
  }

  public void apply(FieldDef field) {
    field.isEditable = false;
  }
}
