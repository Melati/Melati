package org.melati.poem.prepro;

import java.util.*;
import java.io.*;

public class AtomFieldDef extends FieldDef {

  public AtomFieldDef(TableDef table, String name,
                      String type, int displayOrder, Vector qualifiers)
       throws IllegalityException {
    super(table, name, type, type, displayOrder, qualifiers);
  }

  protected void generateColRawAccessors(Writer w) throws IOException {
    super.generateColRawAccessors(w);

    w.write(
      "\n" +
      "          public Object getRaw(Persistent g)\n" +
      "              throws AccessPoemException {\n" +
      "            return ((" + mainClass + ")g).get" + suffix + "();\n" +
      "          }\n" +
      "\n" +
      "          public void setRaw(Persistent g, Object raw)\n" +
      "              throws AccessPoemException {\n" +
      "            ((" + mainClass + ")g).set" + suffix +
                     "((" + rawType + ")raw);\n" +
      "          }\n");
  }

  public void generateBaseMethods(Writer w) throws IOException {
    super.generateBaseMethods(w);

    w.write("\n" +
	    "  public " + type + " get" + suffix + "()\n" +
            "      throws AccessPoemException {\n" +
	    "    readLock();\n" +
            "    return get" + suffix + "_unsafe();\n" +
            "  }\n" +
            "\n" +
            "  public void set" + suffix + "(" + type + " cooked)\n" +
            "      throws AccessPoemException, ValidationPoemException {\n" +
            "    " + tableAccessorMethod + "().get" + suffix + "Column()." +
                     "getType().assertValidCooked(cooked);\n" +
	    "    writeLock();\n" +
	    "    set" + suffix + "_unsafe(cooked);\n" +
            "  }\n");
  }

  public void generateJavaDeclaration(Writer w) throws IOException {
    w.write(type + " " + name);
  }

  public String poemTypeJava() {
    return "new " + type + "PoemType(" + isNullable + ")"; // FIXME :)
  }
}
