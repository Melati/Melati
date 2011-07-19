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

package org.melati.poem;

/**
 * An {@link AtomPoemType} with a <code>size</code>.
 */
public abstract class SizedAtomPoemType<T> extends AtomPoemType<T> {

  private int size;

  /**
   * Constructor with size.
   * @param sqlTypeCode SQL TYPE code
   * @param sqlTypeName SQL TYPE name
   * @param nullable whether nullable 
   * @param size how big
   */
  public SizedAtomPoemType(int sqlTypeCode, String sqlTypeName,
                           boolean nullable, int size) {
    super(sqlTypeCode, sqlTypeName, nullable);
    this.size = size;
  }

  /**
   * @return the size of this type
   */
  public int getSize() {
    return size;
  }

  /**
   * @param newSize size of returned field
   * @return this or clone with new size
   */
  @SuppressWarnings("unchecked")
  public SizedAtomPoemType<T> withSize(int newSize) {
    if (newSize == getSize())
      return this;

    SizedAtomPoemType<T> it = (SizedAtomPoemType<T>)clone();
    it.size = newSize;
    return it;
  }

 /**
  * Compare sizes, taking into account magic value -1.
  * 
  * @param sizeA  the size of first field
  * @param sizeB  the size of second field
  * @return whether it should be considered bigger
  */
  public static boolean sizeGreaterEqual(int sizeA, int sizeB) {
    return sizeA < 0 ||           // Text fields have size -1
           (sizeB >= 0 && sizeA >= sizeB);
  }
}
