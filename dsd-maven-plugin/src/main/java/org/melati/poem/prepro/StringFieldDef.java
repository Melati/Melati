package org.melati.poem.prepro;

import java.util.*;

public class StringFieldDef extends AtomFieldDef {

  int size;

  public StringFieldDef(TableDef table, String name, int displayOrder,
                        Vector qualifiers)
      throws IllegalityException {
    super(table, name, "String", displayOrder, qualifiers);
    if (size == 0) throw new StringSizeZeroException(this);
  }

  public String poemTypeJava() {
    return "new StringPoemType(" + isNullable + ", " + size + ")";
  }
}
