package org.melati.poem.prepro;

import java.util.*;
import java.io.*;

public abstract class FieldDef {
  protected final TableDef table;
  protected final String name;
  protected final String suffix;
  protected final String type;
  protected final String identType;
  protected final int index;
  protected final Vector qualifiers;

  final String dataBaseClass;
  final String dataMainClass;
  final String baseClass;
  final String mainClass;
  final String tableMainClass;
  final String tableAccessorMethod;
  boolean isNullable;
  boolean isTroidColumn;
  boolean isDeletedColumn;

  public FieldDef(TableDef table, int index, String name,
                  String type, String identType, Vector qualifiers)
      throws IllegalityException {
    this.table = table;
    this.index = index;
    this.name = name;
    this.suffix = DSD.capitalise(name);
    this.type = type;
    this.identType = identType;
    this.qualifiers = qualifiers;

    this.dataBaseClass = table.dataBaseClass;
    this.dataMainClass = table.dataMainClass;
    this.baseClass = table.baseClass;
    this.mainClass = table.mainClass;
    this.tableMainClass = table.tableMainClass;
    this.tableAccessorMethod = table.tableAccessorMethod;

    for (int q = 0; q < qualifiers.size(); ++q)
      ((FieldQualifier)qualifiers.elementAt(q)).apply(this);
  }

  public String toString() {
    return
        table.name + "." + name +
        " (" + (isNullable ? "nullable " : "") + type + ")";
  }

  private static void fieldQualifiers(Vector qualifiers, StreamTokenizer tokens)
      throws ParsingDSDException, IOException {
    while (tokens.ttype == '(') {
      tokens.nextToken();
      qualifiers.addElement(FieldQualifier.from(tokens));
      DSD.expect(tokens, ')');
      tokens.nextToken();
    }
  }

  public static FieldDef from(TableDef table, StreamTokenizer tokens,
                              int index)
      throws ParsingDSDException, IOException, IllegalityException {
    Vector qualifiers = new Vector();
    fieldQualifiers(qualifiers, tokens);
    if (tokens.ttype != StreamTokenizer.TT_WORD)
      throw new ParsingDSDException("<field type>", tokens);
    String type = tokens.sval;
    if (tokens.nextToken() != StreamTokenizer.TT_WORD)
      throw new ParsingDSDException("<field name>", tokens);
    String name = tokens.sval;
    tokens.nextToken();
    fieldQualifiers(qualifiers, tokens);
    DSD.expect(tokens, ';');

    if (type.equals("Integer"))
      return new IntegerFieldDef(table, index, name, qualifiers);
    else if (type.equals("Boolean"))
      return new BooleanFieldDef(table, index, name, qualifiers);
    else if (type.equals("String"))
      return new StringFieldDef(table, index, name, qualifiers);
    else
      return new ReferenceFieldDef(table, index, name, type, qualifiers);
  }

  public abstract void generateBaseMethods(Writer w) throws IOException;

  public void generateFieldCreator(Writer w) throws IOException {
    w.write("  public final Field get" + suffix + "Field() " +
                  "throws AccessPoemException {\n" +
            "    return " + tableAccessorMethod + "()." +
                    "get" + suffix + "Column().asField(this);\n" +
            "  }\n");
  }

  public abstract void generateJavaDeclaration(Writer w) throws IOException;

  public void generateColDecl(Writer w) throws IOException {
    w.write("Column col_" + name);
  }

  public void generateColAccessor(Writer w) throws IOException {
    w.write("  public final Column get" + suffix + "Column() {\n" +
            "    return col_" + name + ";\n" +
            "  }\n");
  }

  protected abstract void generateColIdentAccessors(Writer w)
      throws IOException;

  public void generateColDefinition(Writer w) throws IOException {
    w.write(
      "    defineColumn(col_" + name + " =\n" +
      "        new Column(this, \"" + name + "\", " + poemTypeJava() + ", " +
                    "DefinitionSource.dsd) { \n" +
      "          public Object getIdent(Data data) {\n" +
      "            return (" + identType + ")((" + dataMainClass + ")data)." +
                       name + ";\n" +
      "          }\n" +
      "\n" +
      "          public void setIdent(Data data, Object ident) {\n" +
      "            ((" + dataMainClass + ")data)." + name +
                       " = (" + identType + ")ident;\n" +
      "          }\n" +
      "\n" +
      "          public Object getValue(Persistent g)\n" +
      "              throws AccessPoemException, PoemException {\n" +
      "            return ((" + mainClass + ")g).get" + suffix + "();\n" +
      "          }\n" +
      "\n" +
      "          public void setValue(Persistent g, Object value)\n" +
      "              throws AccessPoemException, ValidationPoemException {\n" +
      "            ((" + mainClass + ")g).set" + suffix + "((" +
                       type + ")value);\n" +
      "          }\n" +
      "\n");

    generateColIdentAccessors(w);

    w.write(
      "        });\n");
  }

  public abstract String poemTypeJava();
}
