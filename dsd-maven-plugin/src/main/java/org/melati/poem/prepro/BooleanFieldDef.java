package org.melati.poem.prepro;

import java.util.*;
import java.io.*;

public class BooleanFieldDef extends AtomFieldDef {

  public BooleanFieldDef(TableDef table, int index, String name,
                         Vector qualifiers) throws IllegalityException {
    super(table, index, name, "Boolean", qualifiers);
  }

  public void generateBaseMethods(Writer w) throws IOException {
    super.generateBaseMethods(w);
    w.write("\n" +
            "  public final void set" + suffix + "(boolean value)\n" +
            "      throws AccessPoemException, ValidationPoemException {\n" +
            "    set" + suffix + "(value ? Boolean.TRUE : Boolean.FALSE);\n" +
            "  }\n");
  }

  public String poemTypeJava() {
    return isDeletedColumn ? "DeletedPoemType.it" : super.poemTypeJava();
  }
}
