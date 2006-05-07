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
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.melati.util.StringUtils;
import org.melati.poem.dbms.Dbms;

/**
 * Base of all character sequence types.
 */
public class StringPoemType extends SizedAtomPoemType {

  /** The nullable instance. */
  public static final StringPoemType nullable = new StringPoemType(true, -1);

  public StringPoemType(boolean nullable, int size) {
    super(Types.VARCHAR, "VARCHAR", nullable, size);
  }

  protected void _assertValidRaw(Object raw)
      throws ValidationPoemException {
    if (raw != null) {
      if (!(raw instanceof String))
        throw new TypeMismatchPoemException(raw, this);
      if (!sizeGreaterEqual(getSize(), ((String)raw).length()))
        throw new StringLengthValidationPoemException(this, (String)raw);
    }
  }

  protected Object _getRaw(ResultSet rs, int col) throws SQLException {
    return rs.getString(col);
  }

  protected void _setRaw(PreparedStatement ps, int col, Object string)
      throws SQLException {
    ps.setString(col, (String)string);
  }

  protected Object _rawOfString(String rawString) {
    return rawString;
  }

  protected String _sqlDefinition(Dbms dbms) {
    try {
      return dbms.getStringSqlDefinition(getSize());
    } catch (SQLException e) {
      throw new SQLSeriousPoemException(e);
    }
  }

  protected boolean _canRepresent(SQLPoemType other) {
    return
        other instanceof StringPoemType &&
        sizeGreaterEqual(getSize(), ((StringPoemType)other).getSize());
  }

  public String toString() {
    return (getNullable() ? "nullable " : "") + "String(" + getSize() + ")";
  }

  /**
   * The field type used in the Data Structure Definition language.
   */
  public String toDsdType() {
    return "String";
  }

  protected void _saveColumnInfo(ColumnInfo columnInfo)
      throws AccessPoemException {
    columnInfo.setTypefactory(PoemTypeFactory.STRING);
    columnInfo.setSize(getSize());
  }

  protected String _quotedRaw(Object raw) {
    return StringUtils.quoted((String)raw, '\'');
  }
}
