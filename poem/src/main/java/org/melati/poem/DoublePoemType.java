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

package org.melati.poem;

import java.sql.Types;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Normal doubles, dependant upon the database/SQL implementation.
 */
public class DoublePoemType extends AtomPoemType<Double> {

  /** The nullable instance. */
  public static final DoublePoemType it = new DoublePoemType(true);
  
  /**
   * Constructor.
   * @param nullable whether null is an allowed value
   */
  public DoublePoemType(boolean nullable) {
    super(Types.DOUBLE, "DOUBLE PRECISION", nullable);
  }

  protected void _assertValidRaw(Object raw) {
    if (raw != null && !(raw instanceof Double))
      throw new TypeMismatchPoemException(raw, this);
  }

  protected Object _getRaw(ResultSet rs, int col) throws SQLException {
    synchronized (rs) {
      double x = rs.getDouble(col);
      return rs.wasNull() ? null : new Double(x); }
  }

  protected void _setRaw(PreparedStatement ps, int col, Object real)
      throws SQLException {
    ps.setDouble(col, ((Double)real).doubleValue());
  }

  protected Double _rawOfString(String rawString)
      throws ParsingPoemException {
    try {
      return new Double(rawString);
    }
    catch (NumberFormatException e) {
      throw new ParsingPoemException(this, rawString, e);
    }
  }

  protected boolean _canRepresent(SQLPoemType<?> other) {
    return sqlTypeCode() == other.sqlTypeCode() && 
           other instanceof DoublePoemType;
  }

  /**
   * {@inheritDoc}
   * @see org.melati.poem.PoemType#toDsdType()
   */
  public String toDsdType() {
    return "Double";
  }

  protected void _saveColumnInfo(ColumnInfo columnInfo)
      throws AccessPoemException {
    columnInfo.setTypefactory(PoemTypeFactory.DOUBLE);
  }

}
