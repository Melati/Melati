package org.melati.poem.prepro;

import java.util.*;

public class StringFieldDef extends AtomFieldDef {

  int size;

  public StringFieldDef(TableDef table, int index,
                        String name, Vector qualifiers)
      throws IllegalityException {
    super(table, index, name, "String", qualifiers);
    if (size == 0) throw new StringSizeZeroException(this);
  }

  public String poemTypeJava() {
    return "new StringPoemType(" + isNullable + ", " + size + ")";
  }
}
