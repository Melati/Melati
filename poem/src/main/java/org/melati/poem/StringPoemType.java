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

public class StringPoemType extends AtomPoemType {

  protected int size;             // or, < 0 for "unlimited"

  public StringPoemType(boolean nullable, int size, int width, int height) {
    super(Types.VARCHAR, "VARCHAR", nullable, width, height);
    this.size = size;
  }

  public StringPoemType(boolean nullable, int size) {
    this(nullable, size, size < 0 ? 40 : size, 1);
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

  protected String _sqlDefinition() {
    // FIXME Postgres-specific---aargh (have PostgresStringPoemType etc.)
    return size < 0 ? "TEXT" : "VARCHAR(" + size + ")";
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
    columnInfo.setType(PoemTypeFactory.STRING);
    columnInfo.setSize(size);
  }

  protected String _quotedRaw(Object raw) {
    return org.melati.util.StringUtils.quoted((String)raw, '\'');
  }
}
