package org.melati.poem.prepro;

import java.io.*;

public class CriterionHiddenFieldQualifier extends FieldQualifier {

  public CriterionHiddenFieldQualifier(StreamTokenizer tokens) {
  }

  public void apply(FieldDef field) {
    field.searchcriterion = false;
  }
}
