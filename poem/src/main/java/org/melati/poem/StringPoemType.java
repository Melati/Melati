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

import java.sql.*;
import org.melati.poem.dbms.*;

public class StringPoemType extends AtomPoemType {

  public static final StringPoemType nullable = new StringPoemType(true, -1);

  protected int size;             // or, < 0 for "unlimited"

  public StringPoemType(boolean nullable, int size) {
    super(Types.VARCHAR, "VARCHAR", nullable);
    this.size = size;
  }

  public int getSize() {
    return size;
  }

  protected void _assertValidRaw(Object raw)
      throws ValidationPoemException {
    if (raw != null) {
      if (!(raw instanceof String))
	throw new TypeMismatchPoemException(raw, this);
      if (size >= 0 && ((String)raw).length() > size)
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
      return dbms.getStringSqlDefinition(size);
    } catch (SQLException e) {
      throw new SQLSeriousPoemException(e);
    }
  }

  protected boolean _canBe(PoemType other) {
    if (!(other instanceof StringPoemType))
      return false;
    int otherSize = ((StringPoemType)other).size;
    return otherSize < 0 || size >= 0 && otherSize >= size;
  }

  public String toString() {
    return (getNullable() ? "nullable " : "") + "String(" + size + ")";
  }

  protected void _saveColumnInfo(ColumnInfo columnInfo)
      throws AccessPoemException {
    columnInfo.setTypefactory(PoemTypeFactory.STRING);
    columnInfo.setSize(size);
  }

  protected String _quotedRaw(Object raw) {
    return org.melati.util.StringUtils.quoted((String)raw, '\'');
  }
}
