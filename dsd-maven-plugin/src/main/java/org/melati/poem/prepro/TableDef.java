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

import java.util.*;
import java.io.*;
import org.melati.util.*;

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
  private Vector data = new Vector();
  boolean isAbstract;
  TableNamingInfo naming = null;

  int nextFieldDisplayOrder = 0;

  public TableDef(DSD dsd, StreamTokenizer tokens, int displayOrder,
		  boolean isAbstract, TableNamingStore nameStore)
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
      }
      finally {
        tokens.ordinaryChar('.');
      }
      superclass = tokens.sval;
    }
    else
      tokens.pushBack();

    naming = nameStore.add(dsd.packageName, suffix, superclass);

    while (tokens.nextToken() == '(') {
      tokens.nextToken();
      TableQualifier.from(tokens).apply(this);
      DSD.expect(tokens, ')');
    }

    DSD.expect(tokens, '{');
    while (tokens.nextToken() != '}')
      data.addElement(FieldDef.from(this, tokens, nextFieldDisplayOrder++));
    tokens.nextToken();
  }

  private final TableDef this_ = this;


  /* For DatabaseBase */
  public void generateTableDeclJava(Writer w) throws IOException {
    if (!isAbstract)
      w.write("  private " + naming.tableMainClassUnambiguous() + " tab_" + name + " = null;\n");
  }
  public void generateTableDefnJava(Writer w) throws IOException {
    if (!isAbstract)
      w.write("    redefineTable(tab_" + name + " = " +
	               "new " + naming.tableMainClassUnambiguous() + "(this, \"" + name + "\", " +
                                                   "DefinitionSource.dsd));\n");
  }
  public void generateTableAccessorJava(Writer w) throws IOException {

    // if we subclass a table with the same name we need to cast the table to
    // have the same return type as the root superclass
    String requiredReturnClass = naming.tableMainClassRootReturnClass();

    // FIXME hack
    if (!isAbstract) {
      w.write("  public " + requiredReturnClass + 
              " get" + naming.tableMainClassShortName() + "() {\n" +
              "    return ");
      if (!requiredReturnClass.equals(naming.tableMainClassUnambiguous()))
        w.write("(" + requiredReturnClass + ")");
      w.write("tab_" + name + ";\n  }\n");
    }
  }
  public void generateTableAccessorDefnJava(Writer w) throws IOException {
    // FIXME hack
    if (!isAbstract) {
      w.write("  public " + naming.tableMainClassRootReturnClass() + 
              " get" + naming.tableMainClassShortName() + "();\n");
    }
  }

  /* For Base */
  public void generateBaseJava(Writer w) throws IOException {
    // if we subclass a table with the same name we need to cast the table to
    // have the same return type as the root superclass
    String requiredReturnClass = naming.tableMainClassRootReturnClass();

    w.write("public abstract class " + naming.baseClassShortName() + " extends " +
                naming.superclassMainUnambiguous() + " {\n" +
            "\n" +
    	    "  public " + dsd.databaseTablesClass + " get" + dsd.databaseTablesClass +
            "() {\n" +
	        "    return (" + dsd.databaseTablesClass + ")getDatabase();\n" +
            "  }\n" +
	        "\n");

    // FIXME hack

    w.write("  public " + requiredReturnClass + " " +
                naming.tableAccessorMethod() +
                   "() {\n" +
            "    return (" + requiredReturnClass + ")getTable();\n" +
            "  }\n\n");

    w.write("  private " + naming.tableMainClassUnambiguous() + " _" +
                naming.tableAccessorMethod() +
                   "() {\n" +
            "    return (" + naming.tableMainClassUnambiguous() + ")getTable();\n" +
            "  }\n\n");

    for (Enumeration f = data.elements(); f.hasMoreElements();) {
      w.write("  protected ");
      ((FieldDef)f.nextElement()).generateJavaDeclaration(w);
      w.write(";\n");
    }

    for (Enumeration f = data.elements(); f.hasMoreElements();) {
      FieldDef field = (FieldDef)f.nextElement();
      w.write('\n');
      field.generateBaseMethods(w);
      w.write('\n');
      field.generateFieldCreator(w);
    }

    w.write("}\n");
  }

  /* For Main */
  public void generateMainJava(Writer w) throws IOException { 
    w.write("public class " + naming.mainClassShortName() + " extends " +
            naming.baseClassShortName() + " {\n" +
	    "  public " + naming.mainClassShortName() + "() {}\n" +
	    "\n" +
            "  // programmer's domain-specific code here\n" +
            "}\n");
  }

  /* For TableBase */
  public void generateTableBaseJava(Writer w) throws IOException {
    w.write("public class " + naming.tableBaseClassShortName() + " extends " +
                naming.superclassTableUnambiguous() + " {\n" +
            "\n");

    for (Enumeration f = data.elements(); f.hasMoreElements();) {
      w.write("  private ");
      ((FieldDef)f.nextElement()).generateColDecl(w);
      w.write(" = null;\n");
    }

    w.write("\n" +
            "  public " + naming.tableBaseClassShortName() + "(\n" +
	    "      Database database, String name,\n" +
	    "      DefinitionSource definitionSource)" +
                   " throws PoemException {\n" +
            "    super(database, name, definitionSource);\n" +
            "  }\n" +
            "\n" +
            "  public " + naming.tableBaseClassShortName() + "(\n" +
	    "      Database database, String name)" +
                   " throws PoemException {\n" +
            "    this(database, name, DefinitionSource.dsd);\n" +
            "  }\n" +
            "\n" +
	    "  public " + dsd.databaseTablesClass + " get" + dsd.databaseTablesClass +
                   "() {\n" +
	    "    return (" + dsd.databaseTablesClass + ")getDatabase();\n" +
	    "  }\n" +
	    "\n" +
            "  protected void init() throws PoemException {\n" +
	    "    super.init();\n");

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

    // if we subclass a table with the same name we need to cast the table to
    // have the same return type as the root superclass
    String requiredReturnClass = naming.mainClassRootReturnClass();

    w.write("  public " + requiredReturnClass +
            " get" + naming.mainClassShortName() + "Object(" +
                  "Integer troid) {\n" +
            "    return (" + requiredReturnClass + ")getObject(troid);\n" +
            "  }\n" +
            "\n" +
            "  public " + requiredReturnClass +
            " get" + naming.mainClassShortName() + "Object(" +
                  "int troid) {\n" +
            "    return (" + requiredReturnClass + ")getObject(troid);\n" +
            "  }\n");

    if (!isAbstract)
      w.write("\n" +
              "  protected Persistent _newPersistent() {\n" +
              "    return new " + naming.mainClassUnambiguous() + "();\n" +
              "  }" +
              "\n");

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
              "    return new Integer(" +
                       (cacheSize == CacheSizeTableQualifier.UNLIMITED ?
                            "999999999" :  "" + cacheSize) +
                       ");\n" +
              "  }\n" +
              "\n");

    if (category != null)
      w.write("  protected String defaultCategory() {\n" +
              "    return " + StringUtils.quoted(category, '"') + ";\n" +
              "  }\n" +
              "\n");

    w.write("  protected int defaultDisplayOrder() {\n" +
            "    return " + displayOrder + ";\n" +
            "  }\n");

    w.write("}\n");
  }

  /* For Table */
  public void generateTableMainJava(Writer w) throws IOException { 
    w.write("public class " + naming.tableMainClassShortName() +
               " extends " + naming.tableBaseClassShortName() + " {\n" +
            "\n" +
            "  public " + naming.tableMainClassShortName() + "(\n" +
	    "      Database database, String name,\n" +
	    "      DefinitionSource definitionSource)" +
                     " throws PoemException {\n" +
            "    super(database, name, definitionSource);\n" +
            "  }\n" +
            "\n" +
            "  // programmer's domain-specific code here\n" +
            "}\n");
  }


  /* Generate the 4 files */
  public void generateJava() throws IOException {

    dsd.createJava(naming.baseClassShortName(),
                   new Generator() {
                     public void process(Writer w) throws IOException {
                       this_.generateBaseJava(w);
                     }
                   });

    dsd.createJava(naming.mainClassShortName(),
                   new Generator() {
                     public void process(Writer w) throws IOException {
                       this_.generateMainJava(w);
                     }
                   },
                   false);

    dsd.createJava(naming.tableBaseClassShortName(),
                   new Generator() {
                     public void process(Writer w) throws IOException {
                       this_.generateTableBaseJava(w);
                     }
                   });

    dsd.createJava(naming.tableMainClassShortName(),
                   new Generator() {
                     public void process(Writer w) throws IOException {
                       this_.generateTableMainJava(w);
                     }
                   },
                   false);
  }
}
