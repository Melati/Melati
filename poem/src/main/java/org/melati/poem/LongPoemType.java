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

import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Types;
import java.sql.SQLException;
import java.util.Enumeration;
import org.melati.poem.util.LongEnumeration;
import org.melati.poem.dbms.Dbms;

/**
 * Normal longs: <code>INT8</code>.
 */
public class LongPoemType extends AtomPoemType {

  /** Instance of a nullable LongPoemType. */
  public static final LongPoemType nullableInstance = new LongPoemType(true);

  /**
   * Constructor.
   * @param nullable whether null is an allowed value
   */
  public LongPoemType(boolean nullable) {
    super(Types.BIGINT, "INT8", nullable); 
  }

  protected LongPoemType(int sqlTypeCode, String sqlTypeName,
                         boolean nullable) {
    super(sqlTypeCode, sqlTypeName, nullable);
  }

  /**
   * @todo Do down-counting??
   */
  protected Enumeration _possibleRaws() {
    Long low = (Long)getLowRaw();
    Long limit = (Long)getLimitRaw();
    return low == null ?
        null :
        new LongEnumeration(low.longValue(),
                            limit == null ?
                                Long.MAX_VALUE : limit.longValue());
  }

  protected void _assertValidRaw(Object raw) {
    if (raw != null && !(raw instanceof Long))
      throw new TypeMismatchPoemException(raw, this);
  }

  protected Object _getRaw(ResultSet rs, int col) throws SQLException {
    synchronized (rs) {
      long i = rs.getLong(col);
      return i == 0 && rs.wasNull() ? null : new Long(i); }
  }

  protected void _setRaw(PreparedStatement ps, int col, Object integer)
      throws SQLException {
    ps.setLong(col, ((Long)integer).longValue());
  }

  protected Object _rawOfString(String rawString)
      throws ParsingPoemException {
    try {
      return new Long(rawString);
    }
    catch (NumberFormatException e) {
      throw new ParsingPoemException(this, rawString, e);
    }
  }

  protected String _sqlDefinition(Dbms dbms) {
      return dbms.getLongSqlDefinition();
  }


  /** 
   * Longs can represent Integers.
   *  
   * {@inheritDoc}
   * @see org.melati.poem.BasePoemType#_canRepresent(org.melati.poem.SQLPoemType)
   */
  protected boolean _canRepresent(SQLPoemType other) {
    // Sql type code is not checked
    if (other instanceof LongPoemType) 
      return true;
    return other instanceof IntegerPoemType;
  }

  /**
   * {@inheritDoc}
   * @see org.melati.poem.PoemType#toDsdType()
   */
  public String toDsdType() {
    return "Long";
  }

  protected void _saveColumnInfo(ColumnInfo columnInfo)
      throws AccessPoemException {
    columnInfo.setTypefactory(PoemTypeFactory.LONG);
  }

}
