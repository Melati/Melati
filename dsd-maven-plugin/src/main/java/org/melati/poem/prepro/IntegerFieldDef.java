package org.melati.poem.prepro;

import java.util.*;
import java.io.*;

public class IntegerFieldDef extends AtomFieldDef {

  public IntegerFieldDef(TableDef table, String name, int displayOrder,
                         Vector qualifiers) throws IllegalityException {
    super(table, name, "Integer", displayOrder, qualifiers);
  }

  public void generateBaseMethods(Writer w) throws IOException {
    super.generateBaseMethods(w);
    w.write("\n" +
            "  public final void set" + suffix + "(int value)\n" +
            "      throws AccessPoemException, ValidationPoemException {\n" +
            "    set" + suffix + "(new Integer(value));\n" +
            "  }\n");
  }

  public String poemTypeJava() {
    return isTroidColumn ? "TroidPoemType.it" : super.poemTypeJava();
  }
}
