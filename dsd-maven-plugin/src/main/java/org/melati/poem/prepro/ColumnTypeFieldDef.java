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
 *     William Chesters <williamc At paneris.org>
 *     http://paneris.org/~williamc
 *     Obrechtstraat 114, 2517VX Den Haag, The Netherlands
 */

package org.melati.poem.prepro;

import java.util.Vector;
import java.io.Writer;
import java.io.IOException;

/**
 * A definition of a <tt>ColumnTypePoemType</tt>.
 * 
 * A <tt>ColumnType</tt> is a metadata field, is used in <tt>Poem.dsd</tt> 
 * and is not usually used in normal DSD files.
 */ 
public class ColumnTypeFieldDef extends FieldDef {

 /**
  * Constructor.
  *
  * @param lineNo       the line number in the DSD file
  * @param table        the {@link TableDef} that this <code>Field</code> is 
  *                     part of 
  * @param name         the name of this field
  * @param displayOrder where to place this field in a list
  * @param qualifiers   all the qualifiers of this field
  * 
  * @throws IllegalityException if a semantic inconsistency is detected
  */
  public ColumnTypeFieldDef(int lineNo, TableDef table, String name, int displayOrder,
                            Vector<FieldQualifier> qualifiers) throws IllegalityException {
    super(lineNo, table, name, "PoemTypeFactory", "Integer", displayOrder, qualifiers);
    table.addImport("org.melati.poem.PoemTypeFactory", 
                    "persistent");
    table.addImport("org.melati.poem.PoemTypeFactory", 
                    "table");
    table.addImport("org.melati.poem.ColumnTypePoemType", 
                    "table");
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
      "            return ((" + mainClass + ")g).get" + capitalisedName + "Code();\n" +
      "          }\n" +
      "\n");
    w.write(
      "          public void setRaw(Persistent g, Object raw)\n" +
      "              throws AccessPoemException {\n" +
      "            ((" + mainClass + ")g).set" + capitalisedName + "Code((" +
                       rawType + ")raw);\n" +
      "          }\n");
  }

 /**
  * @param w The base persistent java file.
  * @throws IOException 
  *           if something goes wrong with the file system
  */   
  public void generateBaseMethods(Writer w) throws IOException {
    super.generateBaseMethods(w);

    //String targetTableAccessorMethod = "get" + type + "Table";
    //String targetSuffix = type;

    w.write(
      "\n /**\n"
      + "  * Retrieves the <code>" 
      + capitalisedName 
      + "</code> value as an <code>Integer</code> for this " 
      + "<code>Column</code> of the <code>"
      + table.suffix 
      + "</code> <code>Table</code>.\n"
      + ((description != null) ? "  * Field description: \n" 
                                 + DSD.javadocFormat(2, 3, description)
                                 + "  * \n"
                               : "")
      + "  * \n"
      + "  * @generator " 
      + "org.melati.poem.prepro.ColumnTypeFieldDef" 
      + "#generateBaseMethods \n"
      + "  * @throws AccessPoemException \n"
      + "  *         if the current <code>AccessToken</code> \n"
      + "  *         does not confer read access rights\n"
      + "  * @return the <code>" + capitalisedName 
      + "</code> value of this <code>Column</code>\n"
      + "  */\n");
    w.write("\n" +
            "  public Integer get" + capitalisedName + "Code()\n" +
            "      throws AccessPoemException {\n" +
            "    readLock();\n" +
            "    return get" + capitalisedName + "_unsafe();\n" +
            "  }\n");
    w.write(
      "\n /**\n"
      + "  * Sets the <code>Integer</code> <code>" 
      + capitalisedName 
      + "</code> value  for this <code>" 
      + table.suffix
      + "</code> <code>Column</code> of the <code>"
      + table.suffix 
      + "</code> <code>Table</code>.\n" 
      + ((description != null) ? "  * Field description: \n" 
                                 + DSD.javadocFormat(2, 3, description)
                                 + "  * \n"
                               : "")
      + "  * \n"
      + "  * @generator " 
      + "org.melati.poem.prepro.ColumnTypeFieldDef" 
      + "#generateBaseMethods \n"
      + "  * @param raw the value to set \n"
      + "  * @throws AccessPoemException \n"
      + "  *         if the current <code>AccessToken</code> \n"
      + "  *         does not confer write access rights\n"
      + "  */\n");
    w.write("\n" +
            "  public void set" + capitalisedName + "Code(Integer raw)\n" +
            "      throws AccessPoemException {\n" +
            "    " + tableAccessorMethod + "().get" + capitalisedName + "Column()." +
                     "getType().assertValidRaw(raw);\n" +
            "    writeLock();\n" +
            "    set" + capitalisedName + "_unsafe(raw);\n" +
            "  }\n" +
            "\n");
    w.write(
      "\n /**\n"
      + "  * Retrieves the <code>" 
      + capitalisedName 
      + "</code> value as an <code>" + typeShortName + "</code> for this " 
      + "<code>Column</code> of the <code>"
      + table.suffix 
      + "</code> <code>Table</code>.\n"
      + ((description != null) ? "  * Field description: \n" 
                                 + DSD.javadocFormat(2, 3, description)
                                 + "  * \n"
                               : "")
      + "  * \n"
      + "  * @generator " 
      + "org.melati.poem.prepro.ColumnTypeFieldDef" 
      + "#generateBaseMethods \n"
      + "  * @throws AccessPoemException \n"
      + "  *         if the current <code>AccessToken</code> \n"
      + "  *         does not confer read access rights\n"
      + "  * @return the <code>" + capitalisedName 
      + "</code> value of this <code>Column</code>\n"
      + "  */\n");
    w.write("  public " + typeShortName + " get" + capitalisedName + "()\n" +
            "      throws AccessPoemException {\n" +
            "    Integer code = get" + capitalisedName + "Code();\n" +
            "    return code == null ? null :\n" +
            "        PoemTypeFactory.forCode(getDatabase(), " + 
            "code.intValue());\n" +
            "  }\n" +
            "\n");
    w.write(
      "\n /**\n"
      + "  * Sets the <code>" + typeShortName + "</code> <code>" 
      + capitalisedName 
      + "</code> value  for this <code>" 
      + table.suffix
      + "</code> <code>Column</code> of the <code>"
      + table.suffix 
      + "</code> <code>Table</code>.\n" 
      + ((description != null) ? "  * Field description: \n" 
                                 + DSD.javadocFormat(2, 3, description)
                                 + "  * \n"
                               : "")
      + "  * \n"
      + "  * @generator " 
      + "org.melati.poem.prepro.ColumnTypeFieldDef" 
      + "#generateBaseMethods \n"
      + "  * @param cooked the value to set \n"
      + "  * @throws AccessPoemException \n"
      + "  *         if the current <code>AccessToken</code> \n"
      + "  *         does not confer write access rights\n"
      + "  */\n");
    w.write("  public void set" + capitalisedName + "(" + typeShortName + " cooked)\n" +
            "      throws AccessPoemException {\n" +
            "    set" + capitalisedName + "Code(cooked == null ? null : " +
            "cooked.getCode());\n" +
            "  }\n");
  }

 /**
  * @param w whatever is being written to
  * @throws IOException 
  *           if something goes wrong with the file system
  */   
  public void generateJavaDeclaration(Writer w) throws IOException {
    w.write("Integer " + name);
  }

 /** @return the Java string for this <code>PoemType</code>. */
  public String poemTypeJava() {
      return "new ColumnTypePoemType(getDatabase())";
  }
}
