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

import java.sql.Types;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * A type for Normal dates.
 */
public class DatePoemType extends AtomPoemType<Date> {

 /**
  * Constructor.
  * 
  * @param nullable whether this type may contain <code>null</code>.
  */
  public DatePoemType(boolean nullable) {
    super(Types.DATE, "DATE", nullable);
  }
 /**
  * Constructor.
  * @see org.melati.poem.dbms.SQLServer.SQLServerDatePoemType
  */
  public DatePoemType(int sqlTypeCode, String sqlTypeName, boolean nullable) {
    super(sqlTypeCode, sqlTypeName, nullable);
  }

  protected void _assertValidRaw(Object raw) {
    if (raw != null && !(raw instanceof Date))
      throw new TypeMismatchPoemException(raw, this);
  }

  protected Object _getRaw(ResultSet rs, int col) throws SQLException {
    return rs.getDate(col);
  }

  protected void _setRaw(PreparedStatement ps, int col, Object raw)
      throws SQLException {
    ps.setDate(col, (Date)raw);
  }

  /** 
   * HACK reformat DDMMYYYY dates to YYYY-MM-DD
   * @see org.melati.poem.BasePoemType#_rawOfString(java.lang.String)
   */
  protected Date _rawOfString(String raw) {
    if (raw.length() == 8)
      return Date.valueOf(raw.substring(4,8)+ "-" + 
                          raw.substring(2,4) + "-" +
                          raw.substring(0,2));
    return Date.valueOf(raw);
  }

  protected String _stringOfCooked(Object cooked,
                                   PoemLocale locale, int style) {
    return locale.dateFormat(style).format((Date)cooked);
  }

  protected boolean _canRepresent(SQLPoemType<?> other) {
    return other instanceof DatePoemType;
  }

  /**
   * The field type used in the Data Structure Definition language.
   * {@inheritDoc}
   * @see org.melati.poem.PoemType#toDsdType()
   */
  public String toDsdType() {
    return "Date";
  }

  protected void _saveColumnInfo(ColumnInfo columnInfo)
      throws AccessPoemException {
    columnInfo.setTypefactory(PoemTypeFactory.DATE);
  }
}
