/*
 * $Source$
 * $Revision$
 *
 * Part of Melati (http://melati.org), a framework for the rapid
 * development of clean, maintainable web applications.
 *
 * -------------------------------------
 *  Copyright (C) 2000 William Chesters
 * -------------------------------------
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 * A copy of the GPL should be in the file org/melati/COPYING in this tree.
 * Or see http://melati.org/License.html.
 *
 * Contact details for copyright holder:
 *
 *     William Chesters <williamc@paneris.org>
 *     http://paneris.org/~williamc
 *     Obrechtstraat 114, 2517VX Den Haag, The Netherlands
 *
 *
 * ------
 *  Note
 * ------
 *
 * I will assign copyright to PanEris (http://paneris.org) as soon as
 * we have sorted out what sort of legal existence we need to have for
 * that to make sense. 
 * In the meantime, if you want to use Melati on non-GPL terms,
 * contact me!
 */

package org.melati.poem.prepro;

import java.util.*;
import java.io.*;

public class AtomFieldDef extends FieldDef {

  public AtomFieldDef(TableDef table, String name,
                      String type, int displayOrder, Vector qualifiers)
       throws IllegalityException {
    super(table, name, type, type, displayOrder, qualifiers);
  }

  protected void generateColRawAccessors(Writer w) throws IOException {
    super.generateColRawAccessors(w);

    w.write(
      "\n" +
      "          public Object getRaw(Persistent g)\n" +
      "              throws AccessPoemException {\n" +
      "            return ((" + mainClass + ")g).get" + suffix + "();\n" +
      "          }\n" +
      "\n" +
      "          public void setRaw(Persistent g, Object raw)\n" +
      "              throws AccessPoemException {\n" +
      "            ((" + mainClass + ")g).set" + suffix +
                     "((" + rawType + ")raw);\n" +
      "          }\n");
  }

  public void generateBaseMethods(Writer w) throws IOException {
    super.generateBaseMethods(w);

    w.write("\n" +
	    "  public " + type + " get" + suffix + "()\n" +
            "      throws AccessPoemException {\n" +
	    "    readLock();\n" +
            "    return get" + suffix + "_unsafe();\n" +
            "  }\n" +
            "\n" +
            "  public void set" + suffix + "(" + type + " cooked)\n" +
            "      throws AccessPoemException, ValidationPoemException {\n" +
            "    _" + tableAccessorMethod + "().get" + suffix + "Column()." +
                     "getType().assertValidCooked(cooked);\n" +
	    "    writeLock();\n" +
	    "    set" + suffix + "_unsafe(cooked);\n" +
            "  }\n");
  }

  public void generateJavaDeclaration(Writer w) throws IOException {
    w.write(type + " " + name);
  }

  public String poemTypeJava() {
    return "new " + type + "PoemType(" + isNullable + ")";
  }
}
