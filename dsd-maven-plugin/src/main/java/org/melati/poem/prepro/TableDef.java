package org.melati.poem.prepro;

import java.util.*;
import java.io.*;
import org.melati.util.*;

public class TableDef {

  DSD dsd;
  final String name;
  final String suffix;
  String displayName;
  String description;
  boolean seqCached;
  int cacheSize = CacheSizeTableQualifier.DEFAULT;
  final String dataBaseClass;
  final String dataMainClass;
  final String baseClass;
  final String mainClass;
  final String tableBaseClass;
  final String tableMainClass;
  final String tableAccessorMethod;
  final int displayOrder;
  private Vector data = new Vector();

  public TableDef(DSD dsd, StreamTokenizer tokens, int displayOrder)
      throws ParsingDSDException, IOException, IllegalityException {
    this.dsd = dsd;
    this.displayOrder = displayOrder;
    if (tokens.ttype != StreamTokenizer.TT_WORD)
      throw new ParsingDSDException("<table name>", tokens);
    suffix = tokens.sval;
    name = suffix.toLowerCase();
    dataBaseClass = suffix + "DataBase";
    dataMainClass = suffix + "Data";
    baseClass = suffix + "Base";
    mainClass = suffix;
    tableBaseClass = suffix + "TableBase";
    tableMainClass = suffix + "Table";
    tableAccessorMethod = "get" + tableMainClass;

    while (tokens.nextToken() == '(') {
      tokens.nextToken();
      TableQualifier.from(tokens).apply(this);
      DSD.expect(tokens, ')');
    }

    DSD.expect(tokens, '{');
    for (int f = 0; tokens.nextToken() != '}'; ++f)
      data.addElement(FieldDef.from(this, tokens, f));
    tokens.nextToken();
  }

  private final TableDef this_ = this;

  public void generateTableDeclJava(Writer w) throws IOException {
    w.write("  private " + tableMainClass + " tab_" + name + " = null;\n");
  }

  public void generateTableDefnJava(Writer w) throws IOException {
    w.write("      defineTable(tab_" + name + " = " +
                      "new " + tableMainClass + "(this, \"" + name + "\"));\n");
  }

  public void generateTableInitJava(Writer w) throws IOException {
    w.write("      tab_" + name + ".init();\n");
  }

  public void generateTableAccessorJava(Writer w) throws IOException {
    w.write("  public " + tableMainClass + " get" + tableMainClass + "() {\n" +
            "    return tab_" + name + ";\n" +
            "  }\n");
  }

  public void generateDataBaseJava(Writer w) throws IOException {
    w.write("public class " + dataBaseClass + " extends Data {\n");
    for (Enumeration f = data.elements(); f.hasMoreElements();) {
      w.write("  ");
      ((FieldDef)f.nextElement()).generateJavaDeclaration(w);
      w.write(";\n");
    }
    w.write("}\n");
  }

  public void generateDataMainJava(Writer w) throws IOException { 
    w.write("public class " + dataMainClass +
                " extends " + dataBaseClass + " {\n" +
            "  // programmer's domain-specific code here\n" +
            "}\n");
  }

  public void generateBaseJava(Writer w) throws IOException {
    w.write("public class " + baseClass + " extends Persistent {\n" +
            "\n" +
            "  public final " + dataMainClass + " dataSnapshot()\n" +
            "      throws AccessPoemException {\n" +
            "    return (" + dataMainClass + ")_dataSnapshot();\n" +
            "  }\n" +
            "\n" +
            "  protected final " + dataMainClass + " dataForReading()\n" +
            "      throws AccessPoemException {\n" +
            "    return (" + dataMainClass + ")_dataForReading();\n" +
            "  }\n" +
            "\n" +
            "  protected final " + dataMainClass + " dataForWriting()\n" +
            "      throws AccessPoemException {\n" +
            "    return (" + dataMainClass + ")_dataForWriting();\n" +
            "  }\n" +
            "\n" +
            "  public " + tableMainClass + " " + tableAccessorMethod + "() {\n" +
            "    return (" + tableMainClass + ")getTable();\n" +
            "  }\n");

    for (Enumeration f = data.elements(); f.hasMoreElements();) {
      FieldDef field = (FieldDef)f.nextElement();
      w.write('\n');
      field.generateBaseMethods(w);
      w.write('\n');
      field.generateFieldCreator(w);
    }

    w.write("}\n");
  }

  public void generateMainJava(Writer w) throws IOException { 
    w.write("public class " + mainClass + " extends " + baseClass + " {\n" +
            "  // programmer's domain-specific code here\n" +
            "}\n");
  }

  public void generateTableBaseJava(Writer w) throws IOException {
    w.write("public class " + tableBaseClass + " extends Table {\n" +
            "\n");

    for (Enumeration f = data.elements(); f.hasMoreElements();) {
      w.write("  private ");
      ((FieldDef)f.nextElement()).generateColDecl(w);
      w.write(" = null;\n");
    }

    w.write("\n" +
            "  public " + tableBaseClass + "(Database database, String name)" +
                   " throws PoemException {\n" +
            "    super(database, name, DefinitionSource.dsd);\n" +
            "  }\n" +
            "\n" +
            "  protected void init() throws PoemException {\n");

    for (Enumeration f = data.elements(); f.hasMoreElements();) {
      ((FieldDef)f.nextElement()).generateColDefinition(w);
      if (f.hasMoreElements()) w.write('\n');
    }

    w.write("  }\n" +
            "\n");

    for (Enumeration f = data.elements(); f.hasMoreElements();) {
      ((FieldDef)f.nextElement()).generateColAccessor(w);
      w.write('\n');
    }

    w.write("  public " + mainClass + " get" + mainClass + "Object(" +
                  "Integer troid) {\n" +
            "    return (" + mainClass + ")getObject(troid);\n" +
            "  }\n" +
            "\n" +
            "  public " + mainClass + " get" + mainClass + "Object(" +
                  "int troid) {\n" +
            "    return (" + mainClass + ")getObject(troid);\n" +
            "  }\n" +
            "\n" +
            "  protected Persistent newPersistent() {\n" +
            "    return new " + mainClass + "();\n" +
            "  }\n" +
            "\n" +
            "  protected Data _newData() {\n" +
            "    return new " + dataMainClass + "();\n" +
            "  }\n");


    if (displayName != null)
      w.write("  protected String defaultDisplayName() {\n" +
              "    return " + StringUtils.quoted(displayName, '"') + ";\n" +
              "  }\n" +
              "\n");

    if (description != null)
      w.write("  protected String defaultDescription() {\n" +
              "    return " + StringUtils.quoted(description, '"') + ";\n" +
              "  }\n" +
              "\n");

    if (seqCached)
      w.write("  protected boolean defaultRememberAllTroids() {\n" +
              "    return true;\n" +
              "  }\n" +
              "\n");

    if (cacheSize != CacheSizeTableQualifier.DEFAULT)
      w.write("  protected Integer defaultCacheLimit() {\n" +
              "    return " +
                       (cacheSize == CacheSizeTableQualifier.UNLIMITED ?
                            "null" :  "new Integer(" + cacheSize + ")") +
                       ";\n" +
              "  }\n" +
              "\n");

    w.write("  protected int defaultDisplayOrder() {\n" +
            "    return " + displayOrder + ";\n" +
            "  }\n");

    w.write("}\n");
  }

  public void generateTableMainJava(Writer w) throws IOException { 
    w.write("public class " + tableMainClass +
               " extends " + tableBaseClass + " {\n" +
            "\n" +
            "  public " + tableMainClass + "(Database database, String name)" +
                   " throws PoemException {\n" +
            "    super(database, name);\n" +
            "  }\n" +
            "\n" +
            "  // programmer's domain-specific code here\n" +
            "}\n");
  }

  public void generateJava() throws IOException {

    dsd.createJava(dataBaseClass,
                   new Generator() {
                     public void process(Writer w) throws IOException {
                       this_.generateDataBaseJava(w);
                     }
                   });

    dsd.createJava(dataMainClass,
                   new Generator() {
                     public void process(Writer w) throws IOException {
                       this_.generateDataMainJava(w);
                     }
                   },
                   false);

    dsd.createJava(baseClass,
                   new Generator() {
                     public void process(Writer w) throws IOException {
                       this_.generateBaseJava(w);
                     }
                   });

    dsd.createJava(mainClass,
                   new Generator() {
                     public void process(Writer w) throws IOException {
                       this_.generateMainJava(w);
                     }
                   },
                   false);

    dsd.createJava(tableBaseClass,
                   new Generator() {
                     public void process(Writer w) throws IOException {
                       this_.generateTableBaseJava(w);
                     }
                   });

    dsd.createJava(tableMainClass,
                   new Generator() {
                     public void process(Writer w) throws IOException {
                       this_.generateTableMainJava(w);
                     }
                   },
                   false);
  }
}
