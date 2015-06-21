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

/**
 * A definition of a <tt>BinaryPoemType</tt> from the DSD.
 * 
 * Its member variables are populated from the DSD or defaults. Its methods are
 * used to generate the java code.
 */
public class BinaryFieldDef extends AtomFieldDef {

  int size;

  /**
   * Constructor.
   *
   * @param lineNo       the line number in the DSD file
   * @param table        the {@link TableDef} that this <code>Field</code> is part of
   * @param name         the name of this field
   * @param displayOrder where to place this field in a list
   * @param qualifiers   all the qualifiers of this field
   * @throws IllegalityException if a semantic inconsistency is detected
   */
  public BinaryFieldDef(int lineNo, TableDef table, String name,
      int displayOrder, Vector<FieldQualifier> qualifiers) throws IllegalityException {
    super(lineNo, table, name, "byte[]", displayOrder, qualifiers);
    if (size == 0)
      throw new BinarySizeZeroException(this);
    if (searchability == "yes")
      throw new BinarySearchableException(this);
    table.addImport("org.melati.poem.BinaryPoemType", "table");
  }

  /** @return the Java string for this <code>PoemType</code>. */
  public String poemTypeJava() {
    return "new BinaryPoemType(" + isNullable() + ", " + size + ")";
  }

  /**
   * Get the size.
   * @return the size
   */
  public int getSize() {
    return size;
  }

  /**
   * Set the size property. 
   * @param size the size to set 
   */
  public void setSize(int size) {
    if (this.size != 0)
      throw new IllegalityException(lineNumber,
          "Binary field size already set to " + this.size + 
          " cannot overwrite with " + size);
    this.size = size;
  }
}
