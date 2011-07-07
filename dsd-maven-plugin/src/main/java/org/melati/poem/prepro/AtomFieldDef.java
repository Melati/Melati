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
import java.io.IOException;
import java.io.Writer;

/**
 * The definition of all base fields.
 */
public class AtomFieldDef extends FieldDef {

 /**
  * Constructor.
  *
  * @param table        the {@link TableDef} that this <code>Field</code> is 
  *                     part of 
  * @param name         the name of this field
  * @param type         the type of this field
  * @param displayOrder where to place this field in a list
  * @param qualifiers   all the qualifiers of this field
  * 
  * @throws IllegalityException if a semantic inconsistency is detected
  */
  public AtomFieldDef(int lineNo, TableDef table, String name,
                      String type, int displayOrder, Vector qualifiers)
       throws IllegalityException {
    super(lineNo, table, name, type, type, displayOrder, qualifiers);
    table.addImport("org.melati.poem.ValidationPoemException", 
                    "persistent");
  }

 /**
  * Write out this <code>Column</code>'s field accessors. 
  *
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
      "            return ((" + mainClass + ")g).get" + suffix + "();\n" +
      "          }\n" +
      "\n");
    w.write(
      "          public void setRaw(Persistent g, Object raw)\n" +
      "              throws AccessPoemException {\n" +
      "            ((" + mainClass + ")g).set" + suffix +
                     "((" + rawType + ")raw);\n" +
      "          }\n");
  }

 /**
  * @param w The base persistent java file.
  * @throws IOException 
  *           if something goes wrong with the file system
  */   
  public void generateBaseMethods(Writer w) throws IOException {
    super.generateBaseMethods(w);

    w.write(
      "\n /**\n"
      + "  * Retrieves the " 
      + suffix 
      + " value, with locking, for this \n"
      + "  * <code>" 
      + table.suffix 
      + "</code> <code>Persistent</code>.\n"
      + ((description != null) ? "  * Field description: \n" 
                               + DSD.javadocFormat(2, 3, description)
                             : "")
      + "  * \n"
      + "  * @generator "
      + "org.melati.poem.prepro.AtomFieldDef" 
      + "#generateBaseMethods \n"
      + "  * @throws AccessPoemException \n"
      + "  *         if the current <code>AccessToken</code> \n"
      + "  *         does not confer write access rights \n"
      + "  * @return the value of the field <code>"
      + suffix
      + "</code> for this \n"
      + "  *         <code>" 
      + table.suffix 
      + "</code> <code>Persistent</code>  \n"
      + "  */\n");
    w.write("\n" +
            "  public " + typeShortName + " get" + suffix + "()\n" +
            "      throws AccessPoemException {\n" +
            "    readLock();\n" +
            "    return get" + suffix + "_unsafe();\n" +
            "  }\n" +
            "\n");

    w.write(
      "\n /**\n"
      + "  * Sets the <code>" 
      + suffix 
      + "</code> value, with checking, for this \n"
      + "  * <code>" 
      + table.suffix 
      + "</code> <code>Persistent</code>.\n"
      + (description != null ?   "  * Field description: \n" 
                               + DSD.javadocFormat(2, 3, description)
                             : "")
      + "  * \n"
      + "  * @generator "
      + "org.melati.poem.prepro.AtomFieldDef"
      + "#generateBaseMethods  \n"
      + "  * @param cooked  a validated <code>int</code> \n"
      + "  * @throws AccessPoemException \n"
      + "  *         if the current <code>AccessToken</code> \n"
      + "  *         does not confer write access rights\n"
      + "  * @throws ValidationPoemException \n"
      + "  *         if the value is not valid\n"
      + "  */\n");
    w.write(
            "  public void set" + suffix + "(" + typeShortName + " cooked)\n" +
            "      throws AccessPoemException, ValidationPoemException {\n" +
            "    _" + tableAccessorMethod + "().get" + suffix + "Column().\n" +
            "      getType().assertValidCooked(cooked);\n" +
            "    writeLock();\n" +
            "    set" + suffix + "_unsafe(cooked);\n" +
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
    w.write(typeShortName + " " + name);
  }

 /** @return the Java string for this <code>PoemType</code>. */
  public String poemTypeJava() {
    return "new " + typeShortName + "PoemType(" + isNullable() + ")";
  }
}
