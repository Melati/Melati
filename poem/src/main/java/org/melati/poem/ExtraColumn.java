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

package org.melati.poem;

import java.sql.*;

public class ExtraColumn extends Column {
  private final int extrasIndex;

  public ExtraColumn(Table table, String name, SQLPoemType type,
                     DefinitionSource definitionSource,
                     int extrasIndex) {
    super(table, name, type, definitionSource);
    this.extrasIndex = extrasIndex;
  }

  public Object getRaw(Persistent g) throws AccessPoemException {
    g.readLock();
    return getRaw_unsafe(g);
  }

  public Object getRaw_unsafe(Persistent g) {
    return g.extras()[extrasIndex];
  }

  public void setRaw(Persistent g, Object raw)
      throws AccessPoemException, ValidationPoemException {
    getType().assertValidRaw(raw);
    g.writeLock();
    setRaw_unsafe(g, raw);
  }

  public void setRaw_unsafe(Persistent g, Object raw) {
    g.extras()[extrasIndex] = raw;
  }

  public Object getCooked(Persistent g)
      throws AccessPoemException, PoemException {
    // FIXME revalidation
    return getType().cookedOfRaw(getRaw(g));
  }

  public void setCooked(Persistent g, Object cooked)
      throws AccessPoemException, ValidationPoemException {
    // FIXME revalidation
    getType().assertValidCooked(cooked);
    setRaw(g, getType().rawOfCooked(cooked));
  }

  public Field asField(Persistent g) {
    try {
      return new Field(getRaw(g), this);
    }
    catch (AccessPoemException accessException) {
      return new Field(accessException, this);
    }
  }

  public static Column from(Table table, ColumnInfo columnInfo,
                            int extrasIndex, DefinitionSource source) {
    return new ExtraColumn(
        table, columnInfo.getName(), columnInfo.getType(), source, extrasIndex);
  }
}
