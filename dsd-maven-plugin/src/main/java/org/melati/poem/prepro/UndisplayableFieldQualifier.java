package org.melati.poem.prepro;

import java.io.*;

public class UndisplayableFieldQualifier extends FieldQualifier {

  public UndisplayableFieldQualifier(StreamTokenizer tokens) {
  }

  public void apply(FieldDef field) {
    field.isDisplayable = false;
  }
}
