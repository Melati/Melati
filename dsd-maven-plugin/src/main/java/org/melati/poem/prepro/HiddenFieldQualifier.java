package org.melati.poem.prepro;

import java.io.*;

public class HiddenFieldQualifier extends FieldQualifier {

  public HiddenFieldQualifier(StreamTokenizer tokens) {
  }

  public void apply(FieldDef field) {
    field.recorddisplay = false;
  }
}
