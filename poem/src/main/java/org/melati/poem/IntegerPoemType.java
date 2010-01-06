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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Enumeration;
import org.melati.poem.util.IntegerEnumeration;

/**
 * Plain old integers, the range being contingent on the underlying SQL type. 
 */
public class IntegerPoemType extends AtomPoemType {

  /** Instance of a nullable IntegerPoemType. */
  public static final IntegerPoemType nullableInstance = new IntegerPoemType(true);

  /**
   * Constructor.
   * @param nullable whether nullable or not
   */
  public IntegerPoemType(boolean nullable) {
    super(Types.INTEGER, "INT", nullable);
  }

  protected IntegerPoemType(int sqlTypeCode, String sqlTypeName,
                            boolean nullable) {
    super(sqlTypeCode, sqlTypeName, nullable);
  }

  /**
   * @todo Do down-counting?
   */
  protected Enumeration<Integer> _possibleRaws() {
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
      return i == 0 && rs.wasNull() ? null : new Integer(i); }
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

  /**
   * {@inheritDoc}
   * @see org.melati.poem.BasePoemType#_canRepresent(org.melati.poem.SQLPoemType)
   */
  protected boolean _canRepresent(SQLPoemType other) {
    return other instanceof IntegerPoemType;
  }

  /**
   * {@inheritDoc}
   * @see org.melati.poem.PoemType#toDsdType()
   */
  public String toDsdType() {
    return "Integer";
  }

  protected void _saveColumnInfo(ColumnInfo columnInfo)
      throws AccessPoemException {
    columnInfo.setTypefactory(PoemTypeFactory.INTEGER);
  }

}
