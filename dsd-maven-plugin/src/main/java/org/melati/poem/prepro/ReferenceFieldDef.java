package org.melati.poem.prepro;

import java.util.*;
import java.io.*;

public class ReferenceFieldDef extends FieldDef {

  public ReferenceFieldDef(TableDef table, String name, int displayOrder,
                           String type, Vector qualifiers)
      throws IllegalityException {
    super(table, name, type, "Integer", displayOrder, qualifiers);
  }

  protected void generateColRawAccessors(Writer w) throws IOException {
    super.generateColRawAccessors(w);

    w.write(
      "\n" +
      "          public Object getRaw(Persistent g)\n" +
      "              throws AccessPoemException {\n" +
      "            return ((" + mainClass + ")g).get" + suffix + "Troid();\n" +
      "          }\n" +
      "\n" +
      "          public void setRaw(Persistent g, Object raw)\n" +
      "              throws AccessPoemException {\n" +
      "            ((" + mainClass + ")g).set" + suffix + "Troid((" +
                       rawType + ")raw);\n" +
      "          }\n");
  }

  public void generateBaseMethods(Writer w) throws IOException {
    super.generateBaseMethods(w);

    // FIXME the definition of these is duplicated from TableDef
    String targetTableAccessorMethod = "get" + type + "Table";
    String targetSuffix = type;

    w.write("\n" +
	    "  public Integer get" + suffix + "Troid()\n" +
            "      throws AccessPoemException {\n" +
	    "    readLock();\n" +
            "    return get" + suffix + "_unsafe();\n" +
            "  }\n" +
            "\n" +
            "  public void set" + suffix + "Troid(Integer raw)\n" +
            "      throws AccessPoemException {\n" +
            "    " + tableAccessorMethod + "().get" + suffix + "Column()." +
                     "getType().assertValidRaw(raw);\n" +
	    "    writeLock();\n" +
            "    set" + suffix + "_unsafe(raw);\n" +
            "  }\n" +
            "\n" +
            "  public " + type + " get" + suffix + "()\n" +
            "      throws AccessPoemException, NoSuchRowPoemException {\n" +
            "    Integer troid = get" + suffix + "Troid();\n" +
            "    return troid == null ? null :\n" +
            "        ((" + table.dsd.databaseClass + ")getDatabase())." +
                         targetTableAccessorMethod + "()." +
                         "get" + targetSuffix + "Object(troid);\n" +
            "  }\n" +
            "\n" +
            "  public void set" + suffix + "(" + type + " cooked)\n" +
            "      throws AccessPoemException {\n" +
            "    set" + suffix + "Troid(cooked == null ? null : cooked.troid());\n" +
            "  }\n");
  }

  public void generateJavaDeclaration(Writer w) throws IOException {
    w.write("Integer " + name);
  }

  public String poemTypeJava() {
    // FIXME the definition of these is duplicated from TableDef
    String targetTableAccessorMethod = "get" + type + "Table";
    return
        "new ReferencePoemType(((" + table.dsd.databaseClass + ")getDatabase())." +
        targetTableAccessorMethod + "(), " + isNullable + ")";
  }
}
