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

import java.util.Vector;
import java.io.Writer;
import java.io.IOException;

/**
 * A definition of a <tt>ReferencePoemType</tt> from the DSD.
 * 
 * Its member variables are populated from the DSD or defaults.
 * Its methods are used to generate the java code.
 */ 
public class ReferenceFieldDef extends FieldDef {

  String integrityfix;

 /**
  * Constructor.
  *
  * @param lineNo       the line number in the DSD file
  * @param table        the {@link TableDef} that this <code>Field</code> is 
  *                     part of 
  * @param name         the name of this field
  * @param type         the type of this field
  * @param displayOrder where to place this field in a list
  * @param qualifiers   all the qualifiers of this field
  * 
  * @throws IllegalityException if a semantic inconsistency is detected
  */
  public ReferenceFieldDef(int lineNo, TableDef table, String name, int displayOrder,
                           String type, Vector<FieldQualifier> qualifiers)
      throws IllegalityException {
    super(lineNo, table, name, type, "Integer", displayOrder, qualifiers);
    table.addImport("org.melati.poem.ReferencePoemType", 
                      "table");
    table.addImport("org.melati.poem.NoSuchRowPoemException", 
                      "persistent");
    if (integrityfix != null) {
      table.addImport("org.melati.poem.StandardIntegrityFix", 
                        "table");
    }
    // Note these do not have a '.' in and are 
    // looked up once all tables have been processed
    // to enable forward reference within the DSD
    table.addImport(type,"table");
    table.addImport(type,"persistent");
        
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
      "            return ((" + mainClass + ")g).get" + suffix + "Troid();\n" +
      "          }\n" +
      "\n");
    w.write(
      "          public void setRaw(Persistent g, Object raw)\n" +
      "              throws AccessPoemException {\n" +
      "            ((" + mainClass + ")g).set" + suffix + "Troid((" +
                   rawType + ")raw);\n" +
      "          }\n");

    if (integrityfix != null) {
      w.write(
        "\n" +
        "          public StandardIntegrityFix defaultIntegrityFix() {\n" +
        "            return StandardIntegrityFix." + 
        integrityfix + ";\n" +
        "          }\n");
    }
  }

  private String targetCast() {
    TableNamingInfo targetTable =
      (TableNamingInfo)table.dsd.nameStore.tablesByShortName.get(typeShortName);
    return targetTable == null || targetTable.superclass == null ?
             "" : "(" + typeShortName + ")";
  }

 /**
  * @param w The base persistent java file.
  * @throws IOException 
  *           if something goes wrong with the file system
  */   
  public void generateBaseMethods(Writer w) throws IOException {
    super.generateBaseMethods(w);

    String targetTableAccessorMethod = "get" + typeShortName + "Table";
    String targetSuffix = typeShortName;

    String db = "get" + table.dsd.databaseTablesClassName + "()";

    w.write(
      "\n /**\n"
      + "  * Retrieves the Table Row Object ID. \n" 
      + "  *\n"
      + "  * @generator " 
      + "org.melati.poem.prepro.ReferenceFieldDef" 
      + "#generateBaseMethods \n"
      + "  * @throws AccessPoemException  \n" 
      + "  *         if the current <code>AccessToken</code> \n"
      + "  *         does not confer read access rights \n"
      + "  * @return the TROID as an <code>Integer</code> \n"
      + "  */\n");
    w.write("\n" +
      "  public Integer get" + suffix + "Troid()\n" +
      "      throws AccessPoemException {\n" +
      "    readLock();\n" +
      "    return get" + suffix + "_unsafe();\n" +
      "  }\n" +
      "\n");
    w.write(
      "\n /**\n"
      + "  * Sets the Table Row Object ID. \n" 
      + "  * \n" 
      + "  * @generator " 
      + "org.melati.poem.prepro.ReferenceFieldDef" 
      + "#generateBaseMethods \n"
      + "  * @param raw  a Table Row Object Id \n"
      + "  * @throws AccessPoemException  \n" 
      + "  *         if the current <code>AccessToken</code> \n"
      + "  *         does not confer write access rights\n"
      + "  */\n");
    w.write(
      "  public void set" + suffix + "Troid(Integer raw)\n" +
      "      throws AccessPoemException {\n" +
      "    set" + suffix + "(" +
      "raw == null ? null : \n" +
      // This cast is necessary when the target table is
      // an "extends"
      "        " + targetCast() +
      db + "." + targetTableAccessorMethod + "()." +
      "get" + targetSuffix + "Object(raw));\n" +
      "  }\n" +
      "\n");
    w.write(
      "\n /**\n"
      + "  * Retrieves the <code>" + suffix + "</code> object referred to.\n"
      + "  *  \n"
      + "  * @generator " 
      + "org.melati.poem.prepro.ReferenceFieldDef" 
      + "#generateBaseMethods \n"
      + "  * @throws AccessPoemException  \n" 
      + "  *         if the current <code>AccessToken</code> \n"
      + "  *         does not confer read access rights \n"
      + "  * @throws NoSuchRowPoemException  \n" 
      + "  *         if the <code>Persistent</code> has yet "
      + "to be allocated a TROID \n"
      + "  * @return the <code>" 
      + suffix 
      + "</code> as a <code>" 
      + typeShortName 
      + "</code> \n"
      + "  */\n");
    w.write(
      "  public " + typeShortName + " get" + suffix + "()\n" +
      "      throws AccessPoemException, NoSuchRowPoemException {\n" +
      "    Integer troid = get" + suffix + "Troid();\n" +
      "    return troid == null ? null :\n" +
      // This cast is necessary when the target table is
      // an "extends"
      "        " + targetCast() +
      db + "." +
      targetTableAccessorMethod + "()." +
      "get" + targetSuffix + "Object(troid);\n" +
      "  }\n" +
      "\n");
    w.write(
      "\n /**\n"
      + "  * Set the "
      + suffix
      + ".\n" 
      + "  * \n"
      + "  * @generator " 
      + "org.melati.poem.prepro.ReferenceFieldDef" 
      + "#generateBaseMethods \n"
      + "  * @param cooked  a validated <code>" 
      + typeShortName 
      + "</code>\n"
      + "  * @throws AccessPoemException  \n" 
      + "  *         if the current <code>AccessToken</code> \n"
      + "  *         does not confer write access rights \n"
      + "  */\n");
    w.write(
      "  public void set" + suffix + "(" + typeShortName + " cooked)\n" +
      "      throws AccessPoemException {\n" +
      "    _" + tableAccessorMethod + "().\n" + 
      "      get" + suffix + "Column().\n" +
      "        getType().assertValidCooked(cooked);\n" +
      "    writeLock();\n" +
      "    if (cooked == null)\n" +
      "      set" + suffix + "_unsafe(null);\n" +
      "    else {\n" +
      "      cooked.existenceLock();\n" +
      "      set" + suffix + "_unsafe(cooked.troid());\n" +
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
    w.write("Integer " + name);
  }

 /** @return the Java string for this <code>PoemType</code>. */
  public String poemTypeJava() {
    String targetTableAccessorMethod = "get" + typeShortName + "Table";
    String db = "get" + table.dsd.databaseTablesClassName + "()";

    return
        "new ReferencePoemType(" + db + ".\n" + 
        "                                             " +
        targetTableAccessorMethod + "(), " + isNullable() + ")";
  }
}
