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
import org.melati.util.*;
import org.melati.poem.dbms.*;

public class IntegerPoemType extends AtomPoemType {

  public IntegerPoemType(boolean nullable, Dbms dbms) {
    super(Types.INTEGER, "INT", nullable, dbms);
  }

  /**
   * FIXME do down-counting??
   */

  protected Enumeration _possibleRaws() {
    Integer low = (Integer)getLowRaw();
    Integer limit = (Integer)getLimitRaw();
    return low == null ?
        null :
        new IntegerEnumeration(low.intValue(),
                               limit == null ?
                                   Integer.MAX_VALUE : limit.intValue());
  }

  protected void _assertValidRaw(Object raw) {
    if (raw != null && !(raw instanceof Integer))
      throw new TypeMismatchPoemException(raw, this);
  }

  protected Object _getRaw(ResultSet rs, int col) throws SQLException {
    synchronized (rs) {
      int i = rs.getInt(col);
      return i == 0 && rs.wasNull() ? null : new Integer(i);
    }
  }

  protected void _setRaw(PreparedStatement ps, int col, Object integer)
      throws SQLException {
    ps.setInt(col, ((Integer)integer).intValue());
  }

  protected Object _rawOfString(String rawString)
      throws ParsingPoemException {
    try {
      return new Integer(rawString);
    }
    catch (NumberFormatException e) {
      throw new ParsingPoemException(this, rawString, e);
    }
  }

  protected boolean _canBe(PoemType other) {
    return other instanceof IntegerPoemType;
  }

  protected void _saveColumnInfo(ColumnInfo columnInfo)
      throws AccessPoemException {
    columnInfo.setTypefactory(PoemTypeFactory.INTEGER);
  }
}
