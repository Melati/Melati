package org.melati.poem.prepro;

import java.util.*;
import java.io.*;

public class DoubleFieldDef extends AtomFieldDef {

  public DoubleFieldDef(TableDef table, String name, int displayOrder,
                        Vector qualifiers) throws IllegalityException {
    super(table, name, "Double", displayOrder, qualifiers);
  }

  public void generateBaseMethods(Writer w) throws IOException {
    super.generateBaseMethods(w);
    w.write("\n" +
            "  public final void set" + suffix + "(double cooked)\n" +
            "      throws AccessPoemException, ValidationPoemException {\n" +
            "    set" + suffix + "(new Double(cooked));\n" +
            "  }\n");
  }
}
