package org.melati.poem.prepro;

import java.io.*;
import java.util.*;

public class IndexedFieldQualifier extends FieldQualifier {

  protected boolean unique = false;
  private Vector withs = new Vector();

  public IndexedFieldQualifier(StreamTokenizer tokens) throws IOException {
    if (tokens.ttype == StreamTokenizer.TT_WORD &&
        tokens.sval.equals("with"))
      while (tokens.nextToken() == StreamTokenizer.TT_WORD)
        withs.addElement(tokens.sval);
  }

  public void apply(FieldDef field) throws IllegalityException {
    field.isIndexed = true;
  }
}
