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
 * A definition of a <tt>DisplayLevelPoemType</tt> from the DSD.
 * A <tt>DisplayLevel</tt> is a metadata field used in the 
 * <tt>ColumnInfo</tt> table.
 * 
 * Its member variables are populated from the DSD or defaults.
 * Its methods are used to generate the java code.
 */ 
public class DisplayLevelFieldDef extends FieldDef {

  public DisplayLevelFieldDef(TableDef table, String name, int displayOrder,
                              Vector qualifiers) throws IllegalityException {
    super(table, name, "DisplayLevel", "Integer", displayOrder, qualifiers);
    table.addImport("org.melati.poem.DisplayLevelPoemType", 
                    "table");
    table.addImport("org.melati.poem.DisplayLevel", 
                    "table");
    table.addImport("org.melati.poem.DisplayLevel", 
                    "persistent");
  }

 /**
  * @param w The base table java file.
  */   
  protected void generateColRawAccessors(Writer w) throws IOException {
    super.generateColRawAccessors(w);

    w.write(
      "\n" +
      "          public Object getRaw(Persistent g)\n" +
      "              throws AccessPoemException {\n" +
      "            return ((" + mainClass + ")g).get" + suffix + "Index();\n" +
      "          }\n" +
      "\n" +
      "          public void setRaw(Persistent g, Object raw)\n" +
      "              throws AccessPoemException {\n" +
      "            ((" + mainClass + ")g).set" + suffix + "Index((" +
                       rawType + ")raw);\n" +
      "          }\n");
  }

 /**
  * @param w The base persistent java file.
  */   
  public void generateBaseMethods(Writer w) throws IOException {
    super.generateBaseMethods(w);

    String targetTableAccessorMethod = "get" + type + "Table";
    String targetSuffix = type;

    w.write("\n" +
            "  public Integer get" + suffix + "Index()\n" +
            "      throws AccessPoemException {\n" +
            "    readLock();\n" +
            "    return get" + suffix + "_unsafe();\n" +
            "  }\n" +
            "\n" +
            "  public void set" + suffix + "Index(Integer raw)\n" +
            "      throws AccessPoemException {\n" +
            "    " + tableAccessorMethod + "().get" + suffix + "Column()." +
                     "getType().assertValidRaw(raw);\n" +
            "    writeLock();\n" +
            "    set" + suffix + "_unsafe(raw);\n" +
            "  }\n" +
            "\n" +
            "  public " + type + " get" + suffix + "()\n" +
            "      throws AccessPoemException {\n" +
            "    Integer index = get" + suffix + "Index();\n" +
            "    return index == null ? null :\n" +
            "        DisplayLevel.forIndex(index.intValue());\n" +
            "  }\n" +
            "\n" +
            "  public void set" + suffix + "(" + type + " cooked)\n" +
            "      throws AccessPoemException {\n" +
            "    set" + suffix + 
            "Index(cooked == null ? null : cooked.index);\n" +
            "  }\n");
  }

  public void generateJavaDeclaration(Writer w) throws IOException {
    w.write("Integer " + name);
  }

  public String poemTypeJava() {
    return "new DisplayLevelPoemType()";
  }
}
