package org.melati.poem.prepro;

import java.util.*;
import java.io.*;

public class AtomFieldDef extends FieldDef {

  public AtomFieldDef(TableDef table, String name,
                      String type, int displayOrder, Vector qualifiers)
       throws IllegalityException {
    super(table, name, type, type, displayOrder, qualifiers);
  }

  protected void generateColIdentAccessors(Writer w) throws IOException {
    w.write(
      "          public Object getIdent(Persistent g)\n" +
      "              throws AccessPoemException {\n" +
      "            return ((" + mainClass + ")g).get" + suffix + "();\n" +
      "          }\n" +
      "\n" +
      "          public void setIdent(Persistent g, Object ident)\n" +
      "              throws AccessPoemException {\n" +
      "            ((" + mainClass + ")g).set" + suffix +
                     "((" + identType + ")ident);\n" +
      "          }\n");
  }

  public void generateBaseMethods(Writer w) throws IOException {
    w.write("  public " + type + " get" + suffix + "()\n" +
            "      throws AccessPoemException {\n" +
            "    return dataForReading()." + name + ";\n" +
            "  }\n" +
            "\n" +
            "  public void set" + suffix + "(" + type + " value)\n" +
            "      throws AccessPoemException, ValidationPoemException {\n" +
            "    " + tableAccessorMethod + "().get" + suffix + "Column()." +
                     "getType().assertValidValue(value);\n" +
            "    dataForWriting()." + name + " = value;\n" +
            "  }\n");
  }

  public void generateJavaDeclaration(Writer w) throws IOException {
    w.write(type + " " + name);
  }

  public String poemTypeJava() {
    return "new " + type + "PoemType(" + isNullable + ")"; // FIXME :)
  }
}
