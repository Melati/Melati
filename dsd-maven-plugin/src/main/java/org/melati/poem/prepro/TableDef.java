/*
 * $Source$
 * $Revision$
 *
 * Copyright (C) 2000 William Chesters
 *
 * Part of Melati (http://melati.org), a framework for the rapid
 * development of clean, maintainable web applications.
 *
 * Melati is free software; Permission is granted to copy, distribute
 * and/or modify this software under the terms either:
 *
 * a) the GNU General Public License as published by the Free Software
 *    Foundation; either version 2 of the License, or (at your option)
 *    any later version,
 *
 *    or
 *
 * b) any version of the Melati Software License, as published
 *    at http://melati.org
 *
 * You should have received a copy of the GNU General Public License and
 * the Melati Software License along with this program;
 * if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA to obtain the
 * GNU General Public License and visit http://melati.org to obtain the
 * Melati Software License.
 *
 * Feel free to contact the Developers of Melati (http://melati.org),
 * if you would like to work out a different arrangement than the options
 * outlined here.  It is our intention to allow Melati to be used by as
 * wide an audience as possible.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * Contact details for copyright holder:
 *
 *     William Chesters <williamc@paneris.org>
 *     http://paneris.org/~williamc
 *     Obrechtstraat 114, 2517VX Den Haag, The Netherlands
 */

package org.melati.poem.prepro;

import java.util.Enumeration;
import java.util.Vector;
import java.util.Hashtable;
import java.util.Arrays;
import java.io.StreamTokenizer;
import java.io.Writer;
import java.io.IOException;
import org.melati.util.StringUtils;

/**
 * A Table Definition holding information from a DSD.
 *
 */
public class TableDef {

  DSD dsd;
  final String name;
  final String suffix;
  String displayName;
  String description;
  String category;
  int displayOrder;
  boolean seqCached;
  int cacheSize = CacheSizeTableQualifier.DEFAULT;
  private Vector fields = new Vector();
  boolean isAbstract;
  boolean definesColumns;
  TableNamingInfo naming = null;

  int nextFieldDisplayOrder = 0;
  // Note we have to store the imports and process them at 
  // the end to avoid referring to a table that has yet to be processed.
  private final Hashtable imports = new Hashtable();
  private final Vector tableBaseImports = new Vector();
  private final Vector persistentBaseImports = new Vector();

  public TableDef(
    DSD dsd,
    StreamTokenizer tokens,
    int displayOrder,
    boolean isAbstract,
    TableNamingStore nameStore)
    throws ParsingDSDException, IOException, IllegalityException {
    this.dsd = dsd;
    this.displayOrder = displayOrder;
    this.isAbstract = isAbstract;
    if (tokens.ttype != StreamTokenizer.TT_WORD)
      throw new ParsingDSDException("<table name>", tokens);
    suffix = tokens.sval;
    name = suffix.toLowerCase();

    String superclass = null;

    if (tokens.nextToken() == StreamTokenizer.TT_WORD) {
      if (!tokens.sval.equals("extends"))
        throw new ParsingDSDException("{", tokens);
      tokens.wordChars('.', '.');
      try {
        if (tokens.nextToken() != StreamTokenizer.TT_WORD)
          throw new ParsingDSDException("<class name>", tokens);
      } finally {
        tokens.ordinaryChar('.');
      }
      superclass = tokens.sval;
    } else
      tokens.pushBack();

    naming = nameStore.add(dsd.packageName, suffix, superclass);

    while (tokens.nextToken() == '(') {
      tokens.nextToken();
      TableQualifier.from(tokens).apply(this);
      DSD.expect(tokens, ')');
    }

    DSD.expect(tokens, '{');
    while (tokens.nextToken() != '}')
      fields.addElement(FieldDef.from(this, tokens, nextFieldDisplayOrder++));

    tokens.nextToken();

  }

  void addImport(String importName, String destination) {
    if (!destination.equals("table")
      && !destination.equals("persistent")
      && !destination.equals("both"))
      throw new RuntimeException(
        "Destination other than 'table', 'persistent' or 'both' used:"
          + destination);

    String existing = null;
    existing = (String) imports.put(importName, destination);
    if (existing != null && existing != destination)
      imports.put(importName, "both");
  }

  private final TableDef this_ = this;

  /**
   * @param w  DatabaseBase 
   */
  public void generateTableDeclJava(Writer w) throws IOException {
    if (!isAbstract)
      w.write(
        "  private "
          + naming.tableMainClassUnambiguous()
          + " tab_"
          + name
          + " = null;\n");
  }

  /**
   * @param w  DatabaseBase 
   */
  public void generateTableDefnJava(Writer w) throws IOException {
    if (!isAbstract)
      w.write(
        "    redefineTable(tab_"
          + name
          + " = "
          + "new "
          + naming.tableMainClassUnambiguous()
          + "(this, \""
          + name
          + "\", "
          + "DefinitionSource.dsd));\n");
  }

  /**
   * @param w  DatabaseBase 
   */
  public void generateTableAccessorJava(Writer w) throws IOException {

    // if we subclass a table with the same name we need to cast the table to
    // have the same return type as the root superclass
    String requiredReturnClass = naming.tableMainClassRootReturnClass();

    if (!isAbstract) {
      w.write(
        "  public "
          + requiredReturnClass
          + " get"
          + naming.tableMainClassShortName()
          + "() {\n"
          + "    return ");
      if (!requiredReturnClass.equals(naming.tableMainClassUnambiguous()))
        w.write("(" + requiredReturnClass + ")");
      w.write("tab_" + name + ";\n  }\n");
    }
  }

  /**
   * @param w  DatabaseTablesBase 
   */
  public void generateTableAccessorDefnJava(Writer w) throws IOException {
    if (!isAbstract) {
      w.write(
        "  "
          + naming.tableMainClassRootReturnClass()
          + " get"
          + naming.tableMainClassShortName()
          + "();\n");
    }
  }

  /**
   * @param w  PersistentBase
   */
  public void generateBaseJava(Writer w) throws IOException {

    w.write("\n");
    for (Enumeration e = persistentBaseImports.elements();
      e.hasMoreElements();
      ) {
      w.write("import " + e.nextElement() + ";\n");
    }
    w.write("\n");

    // if we subclass a table with the same name we need to cast the table to
    // have the same return type as the root superclass
    String requiredReturnClass = naming.tableMainClassRootReturnClass();

    w.write(
      "\n"
        + "/**\n"
        + " * Melati POEM generated base class for persistent "
        + suffix
        + ".\n"
        + " * Field summary for SQL table "
        + name
        + ":\n");
    for (Enumeration f = fields.elements(); f.hasMoreElements();) {
      w.write(" *   ");
      w.write(((FieldDef) f.nextElement()).name);
      w.write("\n");
    }
    w.write(" *\n");
    w.write(" */\n");
    w.write(
      "public abstract class "
        + naming.baseClassShortName()
        + " extends "
        + naming.superclassMainShortName()
        + " {\n"
        + "\n"
        + "  public "
        + dsd.databaseTablesClass
        + " get"
        + dsd.databaseTablesClass
        + "() {\n"
        + "    return ("
        + dsd.databaseTablesClass
        + ")getDatabase();\n"
        + "  }\n"
        + "\n");

    w.write(
      "  public "
        + requiredReturnClass
        + " "
        + naming.tableAccessorMethod()
        + "() {\n"
        + "    return ("
        + requiredReturnClass
        + ")getTable();\n"
        + "  }\n\n");

    w.write(
      "  private "
        + naming.tableMainClassUnambiguous()
        + " _"
        + naming.tableAccessorMethod()
        + "() {\n"
        + "    return ("
        + naming.tableMainClassUnambiguous()
        + ")getTable();\n"
        + "  }\n\n");

    for (Enumeration f = fields.elements(); f.hasMoreElements();) {
      w.write("  protected ");
      ((FieldDef) f.nextElement()).generateJavaDeclaration(w);
      w.write(";\n");
    }

    for (Enumeration f = fields.elements(); f.hasMoreElements();) {
      FieldDef field = (FieldDef) f.nextElement();
      w.write('\n');
      field.generateBaseMethods(w);
      w.write('\n');
      field.generateFieldCreator(w);
    }

    w.write("}\n");
  }

  /**
   * @param w  Persistent
   */
  public void generateMainJava(Writer w) throws IOException {

    w.write(
      "import "
        + dsd.packageName
        + ".generated."
        + naming.baseClassShortName()
        + ";\n");
    w.write("\n" + "/**\n" + " * Melati POEM generated stub\n" + " */\n");
    w.write(
      "public class "
        + naming.mainClassShortName()
        + " extends "
        + naming.baseClassShortName()
        + " {\n"
        + "  public "
        + naming.mainClassShortName()
        + "() {}\n"
        + "\n"
        + "  // programmer's domain-specific code here\n"
        + "}\n");
  }

  /**
   * @param w  TableBase
   */
  public void generateTableBaseJava(Writer w) throws IOException {

    for (Enumeration e = tableBaseImports.elements(); e.hasMoreElements();)
      w.write("import " + e.nextElement() + ";\n");

    w.write("\n");
    w.write(
      "\n"
        + "/**\n"
        + " * Melati POEM generated base class for table "
        + suffix
        + ".\n"
        + " * Field summary for SQL table "
        + name
        + ":\n");
    for (Enumeration f = fields.elements(); f.hasMoreElements();) {
      w.write(" *   ");
      w.write(((FieldDef) f.nextElement()).name);
      w.write("\n");
    }
    w.write(" *\n" + " */\n\n");
    w.write(
      "public class "
        + naming.tableBaseClassShortName()
        + " extends "
        + naming.superclassTableShortName()
        + " {\n"
        + "\n");

    for (Enumeration f = fields.elements(); f.hasMoreElements();) {
      w.write("  private ");
      ((FieldDef) f.nextElement()).generateColDecl(w);
      w.write(" = null;\n");
    }

    w.write(
      "\n"
        + "  public "
        + naming.tableBaseClassShortName()
        + "(\n"
        + "      Database database, String name,\n"
        + "      DefinitionSource definitionSource)"
        + " throws PoemException {\n"
        + "    super(database, name, definitionSource);\n"
        + "  }\n"
        + "\n"
        + "  public "
        + naming.tableBaseClassShortName()
        + "(\n"
        + "      Database database, String name)"
        + " throws PoemException {\n"
        + "    this(database, name, DefinitionSource.dsd);\n"
        + "  }\n"
        + "\n"
        + "  public "
        + dsd.databaseTablesClass
        + " get"
        + dsd.databaseTablesClass
        + "() {\n"
        + "    return ("
        + dsd.databaseTablesClass
        + ")getDatabase();\n"
        + "  }\n"
        + "\n"
        + "  protected void init() throws PoemException {\n"
        + "    super.init();\n");

    for (Enumeration f = fields.elements(); f.hasMoreElements();) {
      ((FieldDef) f.nextElement()).generateColDefinition(w);
      if (f.hasMoreElements())
        w.write('\n');
    }

    w.write("  }\n" + "\n");

    for (Enumeration f = fields.elements(); f.hasMoreElements();) {
      ((FieldDef) f.nextElement()).generateColAccessor(w);
      w.write('\n');
    }

    // if we subclass a table with the same name we need to cast the table to
    // have the same return type as the root superclass
    String requiredReturnClass = naming.mainClassRootReturnClass();

    w.write(
      "  public "
        + requiredReturnClass
        + " get"
        + naming.mainClassShortName()
        + "Object("
        + "Integer troid) {\n"
        + "    return ("
        + requiredReturnClass
        + ")getObject(troid);\n"
        + "  }\n"
        + "\n"
        + "  public "
        + requiredReturnClass
        + " get"
        + naming.mainClassShortName()
        + "Object("
        + "int troid) {\n"
        + "    return ("
        + requiredReturnClass
        + ")getObject(troid);\n"
        + "  }\n");

    if (!isAbstract)
      w.write(
        "\n"
          + "  protected Persistent _newPersistent() {\n"
          + "    return new "
          + naming.mainClassUnambiguous()
          + "();\n"
          + "  }"
          + "\n");

    if (displayName != null)
      w.write(
        "  protected String defaultDisplayName() {\n"
          + "    return "
          + StringUtils.quoted(displayName, '"')
          + ";\n"
          + "  }\n"
          + "\n");

    if (description != null)
      w.write(
        "  protected String defaultDescription() {\n"
          + "    return "
          + StringUtils.quoted(description, '"')
          + ";\n"
          + "  }\n"
          + "\n");

    if (seqCached)
      w.write(
        "  protected boolean defaultRememberAllTroids() {\n"
          + "    return true;\n"
          + "  }\n"
          + "\n");

    if (cacheSize != CacheSizeTableQualifier.DEFAULT)
      w.write(
        "  protected Integer defaultCacheLimit() {\n"
          + "    return new Integer("
          + (cacheSize == CacheSizeTableQualifier.UNLIMITED
            ? "999999999"
            : "" + cacheSize)
          + ");\n"
          + "  }\n"
          + "\n");

    if (category != null)
      w.write(
        "  protected String defaultCategory() {\n"
          + "    return "
          + StringUtils.quoted(category, '"')
          + ";\n"
          + "  }\n"
          + "\n");

    w.write(
      "  protected int defaultDisplayOrder() {\n"
        + "    return "
        + displayOrder
        + ";\n"
        + "  }\n");

    w.write("}\n");
  }

  /**
   * @param w  Table
   */
  public void generateTableMainJava(Writer w) throws IOException {

    w.write("import " + naming.tableBaseClassFQName() + ";\n");
    w.write("import org.melati.poem.DefinitionSource;\n");
    w.write("import org.melati.poem.Database;\n");
    w.write("import org.melati.poem.PoemException;\n");

    w.write("\n" + "/**\n" + " * Melati POEM generated stub\n" + " */\n");
    w.write(
      "public class "
        + naming.tableMainClassShortName()
        + " extends "
        + naming.tableBaseClassShortName()
        + " {\n"
        + "\n"
        + "  public "
        + naming.tableMainClassShortName()
        + "(\n"
        + "      Database database, String name,\n"
        + "      DefinitionSource definitionSource)"
        + " throws PoemException {\n"
        + "    super(database, name, definitionSource);\n"
        + "  }\n"
        + "\n"
        + "  // programmer's domain-specific code here\n"
        + "}\n");
  }

  /**
   * Generate the 4 files 
   */
  public void generateJava() throws IOException, IllegalityException {

    boolean hasDisplayLevel = false;
    boolean hasSearchability = false;
    int fieldCount = 0;
    for (Enumeration e = fields.elements(); e.hasMoreElements();) {
      fieldCount++;
      FieldDef f = (FieldDef) e.nextElement();
      if (f.displayLevel != null)
        hasDisplayLevel = true;
      if (f.searchability != null)
        hasSearchability = true;
    }
    if (fieldCount == 0 && !isAbstract && naming.superclass == null)
      throw new NonAbstractEmptyTableException(name);

    if (hasDisplayLevel)
      addImport("org.melati.poem.DisplayLevel", "table");
    if (hasSearchability)
      addImport("org.melati.poem.Searchability", "table");
    addImport(naming.tableFQName, "table");
    if (definesColumns) {
      addImport("org.melati.poem.Column", "both");
      addImport("org.melati.poem.Field", "both");
    }
    if (naming.superclassMainUnambiguous().equals("Persistent")) {
      addImport("org.melati.poem.Persistent", "persistent");
    } else {
      addImport(naming.superclassMainFQName(), "persistent");
    }

    addImport(naming.tableMainClassFQName(), "persistent");
    addImport(dsd.packageName + "." + dsd.databaseTablesClass, "persistent");

    addImport("org.melati.poem.Database", "table");
    addImport("org.melati.poem.DefinitionSource", "table");
    addImport("org.melati.poem.PoemException", "table");

    if (!isAbstract)
      addImport("org.melati.poem.Persistent", "table");

    if (naming.superclassTableUnambiguous().equals("Table")) {
      addImport("org.melati.poem.Table", "table");
    } else {
      addImport(naming.superclassTableFQName(), "table");
    }
    addImport(dsd.packageName + "." + dsd.databaseTablesClass, "table");

    // Sort out the imports
    for (Enumeration i = imports.keys(); i.hasMoreElements();) {
      String fqKey;
      String key = (String) i.nextElement();
      if (key.indexOf(".") == -1) {
        TableNamingInfo targetTable =
          (TableNamingInfo) dsd.nameStore.tablesByShortName.get(key);
        if (targetTable == null)
          throw new RuntimeException("No TableNamingInfo for " + key);
        fqKey = targetTable.tableFQName;
        String destination = (String) imports.get(key);
        imports.remove(key);
        addImport(fqKey, destination);
      }
    }
    for (Enumeration i = imports.keys(); i.hasMoreElements();) {
      String fqKey;
      String key = (String) i.nextElement();

      if (key.indexOf(".") == -1) {
        TableNamingInfo targetTable =
          (TableNamingInfo) dsd.nameStore.tablesByShortName.get(key);
        fqKey = targetTable.tableFQName;
      } else {
        fqKey = key;
      }
      String destination = (String) imports.get(key);
      if (destination == "table") {
        tableBaseImports.addElement(fqKey);
      } else if (destination == "persistent") {
        persistentBaseImports.addElement(fqKey);
      } else {
        tableBaseImports.addElement(fqKey);
        persistentBaseImports.addElement(fqKey);
      }
    }
    Object[] t = tableBaseImports.toArray();
    Object[] p = persistentBaseImports.toArray();
    Arrays.sort(t);
    Arrays.sort(p);
    tableBaseImports.removeAllElements();
    persistentBaseImports.removeAllElements();
    for (int i = 0; i < t.length; i++)
      tableBaseImports.addElement((String) t[i]);
    for (int i = 0; i < p.length; i++)
      persistentBaseImports.addElement((String) p[i]);

    dsd.createJava(naming.baseClassShortName(), new Generator() {
      public void process(Writer w) throws IOException {
        this_.generateBaseJava(w);
      }
    }, true);

    dsd.createJava(naming.mainClassShortName(), new Generator() {
      public void process(Writer w) throws IOException {
        this_.generateMainJava(w);
      }
    }, false);

    dsd.createJava(naming.tableBaseClassShortName(), new Generator() {
      public void process(Writer w) throws IOException {
        this_.generateTableBaseJava(w);
      }
    }, true);

    dsd.createJava(naming.tableMainClassShortName(), new Generator() {
      public void process(Writer w) throws IOException {
        this_.generateTableMainJava(w);
      }
    }, false);
  }
}
