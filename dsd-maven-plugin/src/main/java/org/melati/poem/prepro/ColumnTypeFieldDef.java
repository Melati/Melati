package org.melati.poem.prepro;

import java.util.*;
import java.io.*;

public class ColumnTypeFieldDef extends FieldDef {

  public ColumnTypeFieldDef(TableDef table, String name,
                            Vector qualifiers) throws IllegalityException {
    super(table, name, "PoemTypeFactory", "Integer", qualifiers);
  }

  protected void generateColIdentAccessors(Writer w) throws IOException {
    w.write(
      "          public Object getIdent(Persistent g)\n" +
      "              throws AccessPoemException {\n" +
      "            return ((" + mainClass + ")g).get" + suffix + "Code();\n" +
      "          }\n" +
      "\n" +
      "          public void setIdent(Persistent g, Object ident)\n" +
      "              throws AccessPoemException {\n" +
      "            ((" + mainClass + ")g).set" + suffix + "Code((" +
                       identType + ")ident);\n" +
      "          }\n");
  }

  public void generateBaseMethods(Writer w) throws IOException {
    // FIXME the definition of these is duplicated from TableDef
    String targetTableAccessorMethod = "get" + type + "Table";
    String targetSuffix = type;

    w.write("  public Integer get" + suffix + "Code()\n" +
            "      throws AccessPoemException {\n" +
            "    return dataForReading()." + name + ";\n" +
            "  }\n" +
            "\n" +
            "  public void set" + suffix + "Code(Integer ident)\n" +
            "      throws AccessPoemException {\n" +
            "    dataForWriting()." + name + " = ident;\n" +
            "  }\n" +
            "\n" +
            "  public " + type + " get" + suffix + "()\n" +
            "      throws AccessPoemException {\n" +
            "    Integer code = get" + suffix + "Code();\n" +
            "    return code == null ? null :\n" +
            "        PoemTypeFactory.forCode(getDatabase(), code.intValue());\n" +
            "  }\n" +
            "\n" +
            "  public void set" + suffix + "(" + type + " value)\n" +
            "      throws AccessPoemException {\n" +
            "    set" + suffix + "Code(value == null ? null : value.code);\n" +
            "  }\n");
  }

  public void generateJavaDeclaration(Writer w) throws IOException {
    w.write("Integer " + name);
  }

  public String poemTypeJava() {
    return "new ColumnTypePoemType(getDatabase())";
  }
}
