/*
 * $Source$
 * $Revision$
 *
 * Copyright (C) 2012 Tim Pizey
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
 *     Tim Pizey <timp At paneris.org>
 *     http://paneris.org/~timp
 */

package org.melati.poem.prepro;

import java.util.Vector;
import java.io.Writer;
import java.io.IOException;

/**
 * A definition of a <tt>StringKeyReferencePoemType</tt> from the DSD.
 * 
 * Its member variables are populated from the DSD or defaults.
 * Its methods are used to generate the java code.
 */ 
public class StringKeyReferenceFieldDef extends FieldDef {

  private String integrityFix;
  private int size;
  

 /**
  * Constructor.
  *
  * @param lineNo       the line number in the DSD file
  * @param table        the {@link TableDef} that this <code>Field</code> is 
  *                     part of 
  * @param name         the name of this field, must also be the name of the key field in target
  * @param type         the type of this field, ie the table name
  * @param displayOrder where to place this field in a list
  * @param qualifiers   all the qualifiers of this field
  * 
  * @throws IllegalityException if a semantic inconsistency is detected
  */
  public StringKeyReferenceFieldDef(int lineNo, TableDef table, String name, int displayOrder,
                           String type, Vector<FieldQualifier> qualifiers)
      throws IllegalityException {
    super(lineNo, table, name, type, "String", displayOrder, qualifiers);
    if (size == 0)
      throw new StringSizeZeroException(this);
    table.addImport("org.melati.poem.StringKeyReferencePoemType", 
                      "table");
    table.addImport("org.melati.poem.NoSuchRowPoemException", 
                      "persistent");
    if (integrityFix != null) {
      table.addImport("org.melati.poem.StandardIntegrityFix", 
                        "table");
    }
    // Note these do not have a '.' in and are 
    // looked up once all tables have been processed
    // to enable forward reference within the DSD
    table.addImport(type,"table");
    table.addImport(type,"persistent");
    
  }
  
  /** Due to possible forward references this could be null until we finish parse */
  public TableNamingInfo getTargetTableNamingInfo() {
    return table.dsd.tableNamingStore.tableInfoByPersistentShortName.get(typeShortName);
  }

 /**
  * @param w The base table java file.
  * @throws IOException 
  *           if something goes wrong with the file system
  */   
  protected void generateColRawAccessors(Writer w) throws IOException {
    super.generateColRawAccessors(w);

    w.write(
      "\n" +
      "          public Object getRaw(Persistent g)\n" +
      "              throws AccessPoemException {\n" +
      "            return ((" + shortestUnambiguousClassname + ")g).get" + capitalisedName + "();\n" +
      "          }\n" +
      "\n");
    w.write(
      "          public void setRaw(Persistent g, Object raw)\n" +
      "              throws AccessPoemException {\n" +
      "            ((" + shortestUnambiguousClassname + ")g).set" + capitalisedName + "((" +
                   rawType + ")raw);\n" +
      "          }\n");

    if (integrityFix != null) {
      w.write(
        "\n" +
        "          public StandardIntegrityFix defaultIntegrityFix() {\n" +
        "            return StandardIntegrityFix." + 
        integrityFix + ";\n" +
        "          }\n");
    }
  }

  // FIXME Forwards references to extended classes would fail here
  // As it is this will fail with a
  private String targetCast() {
    // If this is irksome comment it out, but then we are relying upon hack below.
    if (getTargetTableNamingInfo() == null)
      throw new ParsingDSDException(lineNumber, 
          "Reference to a type (" + typeShortName + ") which has yet to be defined. \n" + 
          "If there are no reciprocal references then reorder definitions.");

    // FIXME HACK Forwards references to extended classes should fail here until we do two passes
    return getTargetTableNamingInfo() == null || getTargetTableNamingInfo().superclass == null ?
             "" : "(" + typeShortName + ")";
  }

 /**
  * @param w The base persistent java file.
  * @throws IOException 
  *           if something goes wrong with the file system
  */   
  public void generateBaseMethods(Writer w) throws IOException {
    super.generateBaseMethods(w);

    String targetTableAccessorMethod = getTargetTableNamingInfo().leafTableAccessorName() ;

    String db = "get" + table.dsd.databaseTablesClassName + "()";

    w.write(
      "\n /**\n"
      + "  * Retrieves the Table Row Object ID. \n" 
      + "  *\n"
      + "  * Generated by " 
      + "org.melati.poem.prepro.StringKeyReferenceFieldDef#generateBaseMethods \n"
      + "  * @throws AccessPoemException  \n" 
      + "  *         if the current <code>AccessToken</code> \n"
      + "  *         does not confer read access rights \n"
      + "  * @return the TROID as an <code>Integer</code> \n"
      + "  */\n");
    w.write("\n" +
      "  public Integer get" + capitalisedName + "Troid()\n" +
      "      throws AccessPoemException {\n" +
      "    String keyValue = get" + capitalisedName + "_unsafe();" +
      "    if (keyValue == null)\n " + 
      "      return null;\n" + 
      "    else\n" +
      "      return " + db + "." + targetTableAccessorMethod + "()." +
      "get"+capitalisedName+"Column().firstWhereEq(keyValue).troid();\n" +
      "  }\n" +
      "\n");
    w.write(
      "\n /**\n"
      + "  * Sets persistent reference with access checking. \n" 
      + "  * \n" 
      + "  * Generated by " 
      + "org.melati.poem.prepro.StringKeyReferenceFieldDef" 
      + "#generateBaseMethods \n"
      + "  * @param raw  a Table Row Object Id \n"
      + "  * @throws AccessPoemException  \n" 
      + "  *         if the current <code>AccessToken</code> \n"
      + "  *         does not confer write access rights\n"
      + "  */\n");
    w.write(
      "  public void set" + capitalisedName + "(String keyValue)\n" +
      "      throws AccessPoemException {\n" +
      "    set" + capitalisedName + "(" +
      "keyValue == null ? null : \n" +
      "        (" + typeShortName + ")"+
      db + "." + targetTableAccessorMethod + "().\n" +
      "            get" +capitalisedName+"Column().firstWhereEq(keyValue));\n" +
      "  }\n" +
      "\n");
    w.write(
      "\n /**\n"
      + "  * Retrieves the <code>" + capitalisedName + "</code> object referred to.\n"
      + "  *  \n"
      + "  * Generated by " 
      + "org.melati.poem.prepro.StringKeyReferenceFieldDef#generateBaseMethods \n"
      + "  * @throws AccessPoemException  \n" 
      + "  *         if the current <code>AccessToken</code> \n"
      + "  *         does not confer read access rights \n"
      + "  * @return the <code>" 
      + capitalisedName 
      + "</code> as a <code>" 
      + typeShortName 
      + "</code> \n"
      + "  */\n");
    w.write(
        "  public String get" + capitalisedName + "()\n" +
        "      throws AccessPoemException, NoSuchRowPoemException {\n" +
        "    String keyValue = get" + capitalisedName + "_unsafe();\n" +
        "    if (keyValue == null)\n " + 
        "      return null;\n" + 
        "    else\n" +
        "      return \n" + 
        "        (String)" + db + "\n" +
        "            ." + targetTableAccessorMethod + "()\n" + 
        "                .get"+capitalisedName+"Column()\n" + 
        "                    .firstWhereEq(keyValue)\n" + 
        "                        .getCooked(\"" + name + "\");\n" +
        "  }\n" +
        "\n");
    w.write(
        "  public " + typeShortName + " get" + capitalisedName + "Referee()\n" +
        "      throws AccessPoemException, NoSuchRowPoemException {\n" +
        "    String keyValue = get" + capitalisedName + "_unsafe();\n" +
        "    if (keyValue == null)\n " + 
        "      return null;\n" + 
        "    else\n" +
        "      return \n" + 
        "        (" + typeShortName + ")" +
        db + "." + targetTableAccessorMethod + "()." +
        "get"+capitalisedName+"Column().firstWhereEq(keyValue);\n" +
        "  }\n" +
        "\n");
    w.write(
      "\n /**\n"
      + "  * Set the " + capitalisedName + ".\n" 
      + "  * \n"
      + "  * Generated by " 
      + "org.melati.poem.prepro.StringKeyReferenceFieldDef" 
      + "#generateBaseMethods \n"
      + "  * @param cooked  a validated <code>" 
      + typeShortName 
      + "</code>\n"
      + "  * @throws AccessPoemException  \n" 
      + "  *         if the current <code>AccessToken</code> \n"
      + "  *         does not confer write access rights \n"
      + "  */\n");
    w.write(
      "  public void set" + capitalisedName + "(" + typeShortName + " cooked)\n" +
      "      throws AccessPoemException {\n" +
      "    _" + rootTableAccessorMethod + "().\n" + 
      "      get" + capitalisedName + "Column().\n" +
      "        getType().assertValidCooked(cooked);\n" +
      "    writeLock();\n" +
      "    if (cooked == null)\n" +
      "      set" + capitalisedName + "_unsafe(null);\n" +
      "    else {\n" +
      "      cooked.existenceLock();\n" +
      "      set" + capitalisedName + "_unsafe(cooked.get" + capitalisedName+ "_unsafe());\n" +
      "    }\n" +
      "  }\n");
  }

 /**
  * Write out this <code>Field</code>'s java declaration string.
  *
  * @param w The base persistent java file.
  * @throws IOException 
  *           if something goes wrong with the file system
  */   
  public void generateJavaDeclaration(Writer w) throws IOException {
    w.write("String " + name);
  }

 /** @return the Java string for this <code>PoemType</code>. */
  public String poemTypeJava() {
    String targetTableAccessorMethod = "get" + typeShortName + "Table";
    String db = "get" + table.dsd.databaseTablesClassName + "()";

    return
        "new StringKeyReferencePoemType(" + db + ".\n" + 
        "                                             " +
        targetTableAccessorMethod + "(), \"" + 
        name + 
        "\", " + 
        isNullable() +
        ", " + size + ")";
  }
  
  /**
   * Set the size property. 
   * 
   * @param sizeP the size to set
   */
  public void setSize(int sizeP) {
    if (this.size > 0)
      throw new IllegalityException(lineNumber,
          "String field size already set to " + this.size
              + " cannot overwrite with " + sizeP);
    if (sizeP == 0) throw new RuntimeException("wtf");
    this.size = sizeP;
  }

  public void setIntegrityFix(String integrityFixIn) {
    this.integrityFix = integrityFixIn;
  }

}
