package org.melati.poem.prepro;

import java.io.*;

public class SummaryHiddenFieldQualifier extends FieldQualifier {

  public SummaryHiddenFieldQualifier(StreamTokenizer tokens) {
  }

  public void apply(FieldDef field) {
    field.summarydisplay = false;
  }
}
