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

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.apache.commons.codec.binary.Base64;
import org.melati.poem.dbms.Dbms;
import org.melati.poem.util.StringUtils;

/**
 * A <tt>Blob</tt> {@link Field} type.
 */
public class BinaryPoemType extends SizedAtomPoemType<byte[]> {
 
  /**
   * Constructor.
   * @param nullable whether nullable
   * @param size how big
   */
  public BinaryPoemType(boolean nullable, int size) {
    super(Types.VARBINARY, "VARBINARY", nullable, size);
  }

  protected void _assertValidRaw(Object raw)
      throws ValidationPoemException {
    if (raw != null) {
      if (!(raw instanceof byte[]))
        throw new TypeMismatchPoemException(raw, this);
      if (!sizeGreaterEqual(getSize(), ((byte[])raw).length))
        throw new BinaryLengthValidationPoemException(this, (byte[])raw);
    }
  }

  protected String _stringOfRaw(Object raw) {
    return new String(Base64.encodeBase64((byte[])raw));
  }

  protected byte[] _getRaw(ResultSet rs, int col) throws SQLException {
    return rs.getBytes(col);
  }

  protected void _setRaw(PreparedStatement ps, int col, Object string)
      throws SQLException {
    ps.setBytes(col, (byte[])string);
  }

  protected byte[] _rawOfString(String rawString) {
    return Base64.decodeBase64(rawString.getBytes());
  }

  protected String _sqlDefinition(Dbms dbms) {
    try {
      return dbms.getBinarySqlDefinition(getSize());
    } catch (SQLException e) {
      throw new SQLSeriousPoemException(e);
    }
  }

  protected boolean _canRepresent(SQLPoemType<?> other) {
    return 
        sqlTypeCode() == other.sqlTypeCode() && 
        other instanceof BinaryPoemType &&
        sizeGreaterEqual(getSize(), ((BinaryPoemType)other).getSize());
  }

  /**
   * {@inheritDoc}
   * @see org.melati.poem.BasePoemType#toString()
   */
  public String toString() {
    return (getNullable() ? "nullable " : "") + "binary(" + getSize() + ")";
  }

  /**
   * {@inheritDoc}
   * @see org.melati.poem.PoemType#toDsdType()
   */
  public String toDsdType() {
    return "byte[]";
  }

  protected void _saveColumnInfo(ColumnInfo columnInfo)
      throws AccessPoemException {
    columnInfo.setTypefactory(PoemTypeFactory.BINARY);
    columnInfo.setSize(getSize());
  }

  protected String _quotedRaw(Object raw) {
    return StringUtils.quoted(_stringOfRaw(raw), '\'');
  }

}


