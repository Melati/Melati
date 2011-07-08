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
 * A definition of a <tt>DoublePoemType</tt> from the DSD.
 * 
 * Its member variables are populated from the DSD or defaults.
 * Its methods are used to generate the java code.
 */ 
public class DoubleFieldDef extends AtomFieldDef {

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
  public DoubleFieldDef(int lineNo, TableDef table, String name, int displayOrder,
                        Vector<FieldQualifier> qualifiers) throws IllegalityException {
    super(lineNo, table, name, "Double", displayOrder, qualifiers);
    table.addImport("org.melati.poem.DoublePoemType", 
                    "table");
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
      + "  * Sets the <code>" 
      + capitalisedName 
      + "</code> value, with checking, for this <code>" 
      + table.mixedCaseName 
      + "</code> <code>Persistent</code>.\n"
      + ((description != null) ? "  * Field description: \n" 
                                 + DSD.javadocFormat(2, 3, description)
                                 + "  * \n"
                               : "")
      + "  * \n"
      + "  * @generator " 
      + "org.melati.poem.prepro.DoubleFieldDef" 
      + "#generateBaseMethods \n"
      + "  * @param cooked  a validated <code>int</code> \n"
      + "  * @throws AccessPoemException \n"
      + "  *         if the current <code>AccessToken</code> \n"
      + "  *         does not confer write access rights\n"
      + "  * @throws ValidationPoemException \n"
      + "  *         if the value is not valid\n"
      + "  */\n");
    w.write(
      "\n" +
      "  public final void set" + capitalisedName + "(double cooked)\n" +
      "      throws AccessPoemException, ValidationPoemException {\n" +
      "    set" + capitalisedName + "(new Double(cooked));\n" +
      "  }\n");
  }
}
