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
 * that to make sense.  When WebMacro's "Simple Public License" is
 * finalised, we'll offer it as an alternative license for Melati.
 * In the meantime, if you want to use Melati on non-GPL terms,
 * contact me!
 */

package org.melati.poem;

import java.sql.*;
import java.util.*;
import org.melati.poem.dbms.*;

class BooleanPossibleRawEnumeration implements Enumeration {
  private int state = 0;

  public boolean hasMoreElements() {
    return state < 2;
  }

  public synchronized Object nextElement() {
    if (state == 2)
      throw new NoSuchElementException();
    else
      return state++ == 0 ? Boolean.FALSE : Boolean.TRUE;
  }
}

public class BooleanPoemType extends AtomPoemType {

  public BooleanPoemType(boolean nullable) {
    super(Types.BIT, "BOOLEAN", nullable);
  }

  protected Enumeration _possibleRaws() {
    return new BooleanPossibleRawEnumeration();
  }

  protected void _assertValidRaw(Object raw) {
    if (raw != null && !(raw instanceof Boolean))
      throw new TypeMismatchPoemException(raw, this);
  }

  protected Object _getRaw(ResultSet rs, int col) throws SQLException {
    synchronized (rs) {
      boolean b = rs.getBoolean(col);
      return rs.wasNull() ? null : b ? Boolean.TRUE : Boolean.FALSE;
    }
  }

  protected void _setRaw(PreparedStatement ps, int col, Object bool)
      throws SQLException {
    ps.setBoolean(col, ((Boolean)bool).booleanValue());
  }

  protected Object _rawOfString(String rawString)
      throws ParsingPoemException {
    rawString = rawString.trim();
    if (rawString.length() == 1)
      switch (rawString.charAt(0)) {
        case 't': case 'T': case 'y': case 'Y': case '1':
          return Boolean.TRUE;
        case 'f': case 'F': case 'n': case 'N': case '0':
          return Boolean.FALSE;
        default:;
      }

    if (rawString.regionMatches(0, "true", 0, 4) ||
             rawString.regionMatches(0, "yes", 0, 3))
      return Boolean.TRUE;
    else if (rawString.regionMatches(0, "false", 0, 5) ||
             rawString.regionMatches(0, "no", 0, 2))
      return Boolean.FALSE;
    else
      throw new ParsingPoemException(this, rawString);
  }

  protected boolean _canBe(PoemType other) {
    return other instanceof BooleanPoemType;
  }

  protected void _saveColumnInfo(ColumnInfo columnInfo)
      throws AccessPoemException {
    columnInfo.setTypefactory(PoemTypeFactory.BOOLEAN);
  }
}
