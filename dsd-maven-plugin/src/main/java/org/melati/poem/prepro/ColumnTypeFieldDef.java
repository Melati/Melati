package org.melati.poem.prepro;

import java.util.*;
import java.io.*;

public class ColumnTypeFieldDef extends FieldDef {

  public ColumnTypeFieldDef(TableDef table, String name, int displayOrder,
                            Vector qualifiers) throws IllegalityException {
    super(table, name, "PoemTypeFactory", "Integer", displayOrder, qualifiers);
  }

  protected void generateColRawAccessors(Writer w) throws IOException {
    super.generateColRawAccessors(w);

    w.write(
      "\n" +
      "          public Object getRaw(Persistent g)\n" +
      "              throws AccessPoemException {\n" +
      "            return ((" + mainClass + ")g).get" + suffix + "Code();\n" +
      "          }\n" +
      "\n" +
      "          public void setRaw(Persistent g, Object raw)\n" +
      "              throws AccessPoemException {\n" +
      "            ((" + mainClass + ")g).set" + suffix + "Code((" +
                       rawType + ")raw);\n" +
      "          }\n");
  }

  public void generateBaseMethods(Writer w) throws IOException {
    super.generateBaseMethods(w);

    // FIXME the definition of these is duplicated from TableDef
    String targetTableAccessorMethod = "get" + type + "Table";
    String targetSuffix = type;

    w.write("\n" +
	    "  public Integer get" + suffix + "Code()\n" +
            "      throws AccessPoemException {\n" +
	    "    readLock();\n" +
            "    return get" + suffix + "_unsafe();\n" +
            "  }\n" +
            "\n" +
            "  public void set" + suffix + "Code(Integer raw)\n" +
            "      throws AccessPoemException {\n" +
            "    " + tableAccessorMethod + "().get" + suffix + "Column()." +
                     "getType().assertValidRaw(raw);\n" +
	    "    writeLock();\n" +
	    "    set" + suffix + "_unsafe(raw);\n" +
            "  }\n" +
            "\n" +
            "  public " + type + " get" + suffix + "()\n" +
            "      throws AccessPoemException {\n" +
            "    Integer code = get" + suffix + "Code();\n" +
            "    return code == null ? null :\n" +
            "        PoemTypeFactory.forCode(getDatabase(), code.intValue());\n" +
            "  }\n" +
            "\n" +
            "  public void set" + suffix + "(" + type + " cooked)\n" +
            "      throws AccessPoemException {\n" +
            "    set" + suffix + "Code(cooked == null ? null : cooked.code);\n" +
            "  }\n");
  }

  public void generateJavaDeclaration(Writer w) throws IOException {
    w.write("Integer " + name);
  }

  public String poemTypeJava() {
    return "new ColumnTypePoemType(getDatabase())";
  }
}
