package org.melati.poem.prepro;

import java.util.*;
import java.io.*;

public class BooleanFieldDef extends AtomFieldDef {

  public BooleanFieldDef(TableDef table, String name, int displayOrder,
                         Vector qualifiers) throws IllegalityException {
    super(table, name, "Boolean", displayOrder, qualifiers);
  }

  public void generateBaseMethods(Writer w) throws IOException {
    super.generateBaseMethods(w);
    w.write("\n" +
            "  public final void set" + suffix + "(boolean cooked)\n" +
            "      throws AccessPoemException, ValidationPoemException {\n" +
            "    set" + suffix + "(cooked ? Boolean.TRUE : Boolean.FALSE);\n" +
            "  }\n");
  }

  public String poemTypeJava() {
    return isDeletedColumn ? "DeletedPoemType.it" : super.poemTypeJava();
  }
}
