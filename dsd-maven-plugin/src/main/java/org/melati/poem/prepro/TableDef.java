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
  String superclass = null;
  final String suffix;
  String displayName;
  String description;
  String category;
  int displayOrder;
  boolean seqCached;
  int cacheSize = CacheSizeTableQualifier.DEFAULT;
  final String baseClass;
  final String mainClass;
  final String tableBaseClass;
  final String tableMainClass;
  final String tableAccessorMethod;
  private Vector data = new Vector();
  boolean isAbstract;
  /* used to determine the return type of get___Table() and get___Object() methods */
  boolean hidesSuperclass = false;
  /* used to #include this db's version of a Persisent/Table before melati's version */
  boolean overridesPoemTable = false;
  String returnClass;


  int nextFieldDisplayOrder = 0;

  public TableDef(DSD dsd, StreamTokenizer tokens, int displayOrder,
		  boolean isAbstract)
      throws ParsingDSDException, IOException, IllegalityException {
    this.dsd = dsd;
    this.displayOrder = displayOrder;
    this.isAbstract = isAbstract;
    if (tokens.ttype != StreamTokenizer.TT_WORD)
      throw new ParsingDSDException("<table name>", tokens);
    suffix = tokens.sval;
    name = suffix.toLowerCase();
    baseClass = suffix + "Base";
    mainClass = suffix;
    tableBaseClass = suffix + "TableBase";
    tableMainClass = suffix + "Table";
    tableAccessorMethod = "get" + tableMainClass;

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
//      if (superclass.indexOf('.') == -1)
//        superclass = packageName + superclass

      hidesSuperclass =
	  superclass.substring(superclass.lastIndexOf('.') + 1).
              equals(mainClass);

//      overridesPoemTable = superclass.startsWith("org.melati.poem.");

      Class poemClass, superClass;
      try {
        superClass = Class.forName(superclass);
        try {
          poemClass = Class.forName("org.melati.poem."+mainClass);
          overridesPoemTable = poemClass.isAssignableFrom(superClass);
        }
        catch (ClassNotFoundException e) {
          // If we can't find a melati class with this name, we don't override
          // a poem class
        }
      }
      catch (ClassNotFoundException e1) {
        throw new IllegalityException("The superclass of "+mainClass+" ("+
                                      superclass+") cannot be found. Check "+
                                      "your dsd and classpath.");
      }

    }
    else
      tokens.pushBack();

    returnClass = overridesPoemTable
                    ? "org.melati.poem."+mainClass
                    : (hidesSuperclass
                        ? superclass
                        : mainClass);

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

  public boolean isPoemOverride() {
    return overridesPoemTable;
  }

  private final TableDef this_ = this;

  public void generateTableDeclJava(Writer w) throws IOException {
    if (!isAbstract)
      w.write("  private " + tableMainClass + " tab_" + name + " = null;\n");
  }

  public void generateTableDefnJava(Writer w) throws IOException {
    if (!isAbstract)
      w.write("    redefineTable(tab_" + name + " = " +
	               "new " + tableMainClass + "(this, \"" + name + "\", " +
                                                   "DefinitionSource.dsd));\n");
  }

  public void generateTableAccessorJava(Writer w) throws IOException {
    // FIXME hack
    if (!isAbstract)
      w.write("  public " + returnClass + "Table" +
		  " get" + tableMainClass + "() {\n" +
	      "    return tab_" + name + ";\n" +
	      "  }\n");
  }

  public void generateBaseJava(Writer w) throws IOException {
    w.write("public abstract class " + baseClass + " extends " +
                (superclass == null ? "Persistent" : superclass) + " {\n" +
            "\n" +
	    "  public " + dsd.databaseClass + " get" + dsd.databaseClass +
                   "() {\n" +
	    "    return (" + dsd.databaseClass + ")getDatabase();\n" +
            "  }\n" +
	    "\n");

    // FIXME hack

    w.write("  public " + returnClass + "Table " + tableAccessorMethod +
                   "() {\n" +
            "    return (" + returnClass + "Table)getTable();\n" +
            "  }\n\n");

    w.write("  private " + tableMainClass + " _" + tableAccessorMethod +
                   "() {\n" +
            "    return (" + tableMainClass + ")getTable();\n" +
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

  public void generateMainJava(Writer w) throws IOException { 
    w.write("public class " + mainClass + " extends " + baseClass + " {\n" +
	    "  public " + mainClass + "() {}\n" +
	    "\n" +
            "  // programmer's domain-specific code here\n" +
            "}\n");
  }

  public void generateTableBaseJava(Writer w) throws IOException {
    w.write("public class " + tableBaseClass + " extends " +
                (superclass == null ? "" : superclass) + "Table {\n" +
            "\n");

    for (Enumeration f = data.elements(); f.hasMoreElements();) {
      w.write("  private ");
      ((FieldDef)f.nextElement()).generateColDecl(w);
      w.write(" = null;\n");
    }

    w.write("\n" +
            "  public " + tableBaseClass + "(\n" +
	    "      Database database, String name,\n" +
	    "      DefinitionSource definitionSource)" +
                   " throws PoemException {\n" +
            "    super(database, name, definitionSource);\n" +
            "  }\n" +
            "\n" +
            "  public " + tableBaseClass + "(\n" +
	    "      Database database, String name)" +
                   " throws PoemException {\n" +
            "    this(database, name, DefinitionSource.dsd);\n" +
            "  }\n" +
            "\n" +
	    "  public " + dsd.databaseClass + " get" + dsd.databaseClass +
                   "() {\n" +
	    "    return (" + dsd.databaseClass + ")getDatabase();" +
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

    w.write("  public " + returnClass + " get" + mainClass + "Object(" +
                  "Integer troid) {\n" +
            "    return (" + returnClass + ")getObject(troid);\n" +
            "  }\n" +
            "\n" +
            "  public " + returnClass + " get" + mainClass + "Object(" +
                  "int troid) {\n" +
            "    return (" + returnClass + ")getObject(troid);\n" +
            "  }\n");

    if (!isAbstract)
      w.write("\n" +
              "  protected Persistent _newPersistent() {\n" +
              "    return new " + mainClass + "();\n" +
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

  public void generateTableMainJava(Writer w) throws IOException { 
    w.write("public class " + tableMainClass +
               " extends " + tableBaseClass + " {\n" +
            "\n" +
            "  public " + tableMainClass + "(\n" +
	    "      Database database, String name,\n" +
	    "      DefinitionSource definitionSource)" +
                     " throws PoemException {\n" +
            "    super(database, name, definitionSource);\n" +
            "  }\n" +
            "\n" +
            "  // programmer's domain-specific code here\n" +
            "}\n");
  }

  public void generateJava() throws IOException {

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
