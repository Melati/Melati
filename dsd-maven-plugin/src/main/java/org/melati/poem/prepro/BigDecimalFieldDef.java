/*
 * $Source$
 * $Revision$
 *
 * Copyright (C) 2003 Samuel Goldstein
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
 *     Samuel Goldstein <samuel@1969.ws>
 *     http://www.1969.ws
 *     13101 W. Washington Blvd Suite 248, Los Angeles, CA 90066 USA
 */

package org.melati.poem.prepro;

import java.util.Vector;
import java.io.Writer;
import java.io.IOException;

/**
 * A definition of a <tt>BigDecimalPoemType</tt> from the DSD.
 * 
 * Its member variables are populated from the DSD or defaults.
 * Its methods are used to generate the java code.
 */
public class BigDecimalFieldDef extends AtomFieldDef {

  int scale;
  int precision;

 /**
  * Constructor.
  *
  * @param table        the {@link TableDef} that this <code>Field</code> is 
  *                     part of 
  * @param name         the name of this field
  * @param displayOrder where to place this field in a list
  * @param qualifiers   all the qualifiers of this field
  * 
  * @throws IllegalityException if a semantic inconsistency is detected
  */
  public BigDecimalFieldDef(TableDef table, String name, int displayOrder,
                            Vector qualifiers)
      throws IllegalityException {
    super(table, name, "BigDecimal", displayOrder, qualifiers);
    table.addImport("org.melati.poem.BigDecimalPoemType", "table");
    table.addImport("java.math.BigDecimal", "table");
    table.addImport("java.math.BigDecimal", "persistent");
  }

 /**
  * @param w The base persistent java file.
  * @throws IOException 
  *           if something goes wrong with the file system
  */
  public void generateBaseMethods(Writer w) throws IOException {
    super.generateBaseMethods(w);
    /*    w.write("\n" +
                "  public final void set" + suffix + "(double cooked)\n" +
                "      throws AccessPoemException, ValidationPoemException {\n" +
                "    set" + suffix + "(new Double(cooked));\n" +
                "  }\n");
    */
  }

 /** @return the Java string for this <code>PoemType</code>. */
  public String poemTypeJava() {
    return "new BigDecimalPoemType("
      + isNullable
      + ", "
      + precision
      + ", "
      + scale
      + ")";
  }

}
