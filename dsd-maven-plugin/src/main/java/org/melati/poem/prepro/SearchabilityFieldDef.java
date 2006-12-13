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
 * A definition of a <tt>SearchabilityPoemType</tt> from the DSD.
 * A <tt>Searchability</tt> is a metadata field used in the 
 * <tt>ColumnInfo</tt> table.
 * 
 * Its member variables are populated from the DSD or defaults.
 * Its methods are used to generate the java code.
 */ 
public class SearchabilityFieldDef extends FieldDef {

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
  public SearchabilityFieldDef(int lineNo, TableDef table, String name, int displayOrder,
                               Vector qualifiers) throws IllegalityException {
    super(lineNo, table, name, "Searchability", "Integer", displayOrder, qualifiers);
    table.addImport("org.melati.poem.SearchabilityPoemType", 
                    "table");
    table.addImport("org.melati.poem.Searchability", 
                    "table");
    table.addImport("org.melati.poem.Searchability", 
                    "persistent");
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
      "            return ((" + mainClass + ")g).get" + suffix + "Index();\n" +
      "          }\n" +
      "\n");
    w.write(
      "          public void setRaw(Persistent g, Object raw)\n" +
      "              throws AccessPoemException {\n" +
      "            ((" + mainClass + ")g).set" + suffix + "Index((" +
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

//    String targetTableAccessorMethod = "get" + type + "Table";
//    String targetSuffix = type;

    w.write(
      "\n /**\n"
      + "  * Retrieves the " 
      + suffix 
      + " index value \n"
      + "  * of this <code>Persistent</code>.\n" 
      + ((description != null) ?   "  * Field description: \n" 
                                 + DSD.javadocFormat(2, 3, description)
                                 + "  * \n"
                               : "")
      + "  * \n"
      + "  * @generator " 
      + "org.melati.poem.prepro.SearchabiltyFieldDef" 
      + "#generateBaseMethods \n"
      + "  * @throws AccessPoemException \n"
      + "  *         if the current <code>AccessToken</code> \n"
      + "  *         does not confer read access rights\n"
      + "  * @return the " + rawType + " " + name + "\n"
      + "  */\n");
    w.write("\n" +
            "  public Integer get" + suffix + "Index()\n" +
            "      throws AccessPoemException {\n" +
            "    readLock();\n" +
            "    return get" + suffix + "_unsafe();\n" +
            "  }\n" +
            "\n");
    w.write(
      "\n /**\n"
      + "  * Sets the <code>" 
      + suffix 
      + "</code> index value, with checking, for this " 
      + "<code>Persistent</code>.\n"
      + ((description != null) ?   "  * Field description: \n" 
                                 + DSD.javadocFormat(2, 3, description)
                                 + "  * \n"
                               : "")
      + "  * \n"
      + "  * @generator " 
      + "org.melati.poem.prepro.SearchabiltyFieldDef" 
      + "#generateBaseMethods \n"
      + "  * @param raw  the value to set \n"
      + "  * @throws AccessPoemException \n"
      + "  *         if the current <code>AccessToken</code> \n"
      + "  *         does not confer write access rights\n"
      + "  */\n");
    w.write(
            "  public void set" + suffix + "Index(Integer raw)\n" +
            "      throws AccessPoemException {\n" +
            "    " + tableAccessorMethod + "().get" + suffix + "Column()." +
                     "getType().assertValidRaw(raw);\n" +
            "    writeLock();\n" +
            "    set" + suffix + "_unsafe(raw);\n" +
            "  }\n" +
            "\n");
    w.write(
      "\n /**\n"
      + "  * Retrieves the " 
      + suffix 
      + " value \n"
      + "  * of this <code>Persistent</code>.\n" 
      + ((description != null) ?   "  * Field description: \n" 
                                 + DSD.javadocFormat(2, 3, description)
                                 + "  * \n"
                               : "")
      + "  *\n"
      + "  * @generator " 
      + "org.melati.poem.prepro.SearchabiltyFieldDef" 
      + "#generateBaseMethods \n"
      + "  * @throws AccessPoemException \n"
      + "  *         if the current <code>AccessToken</code> \n"
      + "  *         does not confer read access rights\n"
      + "  * @return the " + type + "\n"
      + "  */\n");
    w.write(
            "  public " + type + " get" + suffix + "()\n" +
            "      throws AccessPoemException {\n" +
            "    Integer index = get" + suffix + "Index();\n" +
            "    return index == null ? null :\n" +
            "        Searchability.forIndex(index.intValue());\n" +
            "  }\n" +
            "\n");
    w.write(
      "\n /**\n"
      + "  * Sets the <code>" 
      + suffix 
      + "</code> value, with checking, for the " 
      + "<code>Persistent</code> argument.\n"
      + ((description != null) ?   "  * Field description: \n" 
                                 + DSD.javadocFormat(2, 3, description)
                                 + "  * \n"
                               : "")
      + "  * \n"
      + "  * @generator " 
      + "org.melati.poem.prepro.SearchabiltyFieldDef" 
      + "#generateBaseMethods \n"
      + "  * @param cooked  the value to set \n"
      + "  * @throws AccessPoemException \n"
      + "  *         if the current <code>AccessToken</code> \n"
      + "  *         does not confer write access rights\n"
      + "  */\n");
    w.write(
            "  public void set" + suffix + "(" + type + " cooked)\n" +
            "      throws AccessPoemException {\n" +
            "    set" + suffix + 
            "Index(cooked == null ? null : cooked.index);\n" +
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
    return "new SearchabilityPoemType()";
  }
}
