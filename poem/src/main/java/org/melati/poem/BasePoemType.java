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

import java.util.*;
import java.sql.*;
import org.melati.util.*;
import org.melati.poem.dbms.*;

/**
 * Base of all fundamental types.
 */
public abstract class BasePoemType implements SQLPoemType, Cloneable {
  private int sqlTypeCode;
  private boolean nullable;

  private Comparable low = null, limit = null;

  BasePoemType(int sqlTypeCode, boolean nullable) {
    this.sqlTypeCode = sqlTypeCode;
    this.nullable = nullable;
  }

  protected void setRawRange(Comparable low, Comparable limit) {
    this.low = low;
    this.limit = limit;
  }

  protected Comparable getLowRaw() {
    return low;
  }

  protected Comparable getLimitRaw() {
    return limit;
  }

  protected abstract void _assertValidRaw(Object raw)
      throws ValidationPoemException;

  private void assertRawInRange(Object raw) {
    // Range check.  Since we can't do this with multiple inheritance, we
    // provide it as a facility even in types for which it is meaningless.

    Comparable asComparable;
    try {
      asComparable = (Comparable)raw;
    }
    catch (ClassCastException e) {
      throw new NotComparablePoemException(raw, this);
    }

    if ((low != null && low.compareTo(asComparable) > 0) ||
        (limit != null && limit.compareTo(asComparable) <= 0))
      throw new ValidationPoemException(
          this, raw, new OutsideRangePoemException(low, limit, raw));
  }

  public final void assertValidRaw(Object raw)
      throws ValidationPoemException {
    if (raw == null) {
      if (!nullable)
        throw new NullTypeMismatchPoemException(this);
    }
    else {
      if (low != null || limit != null)
        assertRawInRange(raw);
      _assertValidRaw(raw);
    }
  }

  public final void doubleCheckValidRaw(Object raw) {
    try {
      assertValidRaw(raw);
    }
    catch (ValidationPoemException e) {
      throw new UnexpectedValidationPoemException(e);
    }
  }

  protected abstract Object _getRaw(ResultSet rs, int col)
      throws SQLException;

  public final Object getRaw(ResultSet rs, int col)
      throws ValidationPoemException {
    Object o;
    try {
      o = _getRaw(rs, col);
    }
    catch (SQLException e) {
      throw new SQLSeriousPoemException(e);
    }

    assertValidRaw(o);
    return o;
  }

  protected abstract void _setRaw(PreparedStatement ps, int col,
                                  Object raw)
      throws SQLException;

  public final void setRaw(PreparedStatement ps, int col, Object raw) {
    doubleCheckValidRaw(raw);
    try {
      if (raw == null)
        ps.setNull(col, sqlTypeCode());
      else
        _setRaw(ps, col, raw);
    }
    catch (SQLException e) {
      throw new SQLSeriousPoemException(e);
    }
  }

  protected Enumeration _possibleRaws() {
    return null;
  }
  
  public Enumeration possibleRaws() {
    Enumeration them = _possibleRaws();
    return them == null ? null :
                   getNullable() ? new ConsEnumeration(null, them) :
                   them;
  }

  protected abstract String _stringOfRaw(Object raw);

  /**
   * This <B>doesn't</B> do an explicit <TT>assertValidRaw</TT>.
   */

  public final String stringOfRaw(Object raw)
      throws ValidationPoemException {
    return raw == null ? null : _stringOfRaw(raw);
  }

  protected abstract Object _rawOfString(String string)
      throws ParsingPoemException;

  /**
   * This <B>does</T> do an explicit <TT>assertValidRaw</TT>.
   */

  public final Object rawOfString(String string)
      throws ParsingPoemException, ValidationPoemException {
    Object raw = string == null ? null : _rawOfString(string);
    assertValidRaw(raw);
    return raw;
  }

  protected abstract void _assertValidCooked(Object cooked)
      throws ValidationPoemException;

  public final void assertValidCooked(Object cooked)
      throws ValidationPoemException {
    if (cooked == null) {
      if (!nullable)
        throw new NullTypeMismatchPoemException(this);
    }
    else {
      _assertValidCooked(cooked);
      if (low != null || limit != null)
        assertRawInRange(_rawOfCooked(cooked));
    }
  }

  public final void doubleCheckValidCooked(Object cooked) {
    try {
      assertValidCooked(cooked);
    }
    catch (ValidationPoemException e) {
      throw new UnexpectedValidationPoemException(e);
    }
  }

  protected abstract Object _cookedOfRaw(Object raw) throws PoemException;

  public final Object cookedOfRaw(Object raw) throws PoemException {
    doubleCheckValidRaw(raw);
    return raw == null ? null : _cookedOfRaw(raw);
  }

  protected abstract Object _rawOfCooked(Object raw) throws PoemException;

  public final Object rawOfCooked(Object cooked) {
    doubleCheckValidCooked(cooked);
    return cooked == null ? null : _rawOfCooked(cooked);
  }

  protected abstract String _stringOfCooked(Object cooked,
                                           MelatiLocale locale, int style)
      throws PoemException;

  public final String stringOfCooked(Object cooked,
                                    MelatiLocale locale, int style)
      throws PoemException {
    doubleCheckValidCooked(cooked);
    return cooked == null ? "" : _stringOfCooked(cooked, locale, style);
  }

  public final boolean getNullable() {
    return nullable;
  }

  public final int sqlTypeCode() {
    return sqlTypeCode;
  }

  protected abstract String _sqlDefinition(Dbms dbms);

  public final String sqlDefinition(Dbms dbms) {
    return _sqlDefinition(dbms) + (nullable ? "" : " NOT NULL");
  }

  protected abstract boolean _canRepresent(SQLPoemType other);

  public PoemType canRepresent(PoemType other) {
    // FIXME takes no account of range---need to decide on semantics for this,
    // is it subset (inclusion) or some other notion of storability?
    if (!(other instanceof SQLPoemType))
      return null;
    else {
      SQLPoemType q = (SQLPoemType)other;
      return
          q.sqlTypeCode() == sqlTypeCode &&
          !(!nullable && q.getNullable()) &&
          _canRepresent(q) ?
              q : null;
    }
  }

  public final PoemType withNullable(boolean nullable) {
    if (this.nullable == nullable)
      return this;
    else {
      BasePoemType it = (BasePoemType)clone();
      it.nullable = nullable;
      return it;
    }
  }

  protected abstract void _saveColumnInfo(ColumnInfo info)
      throws AccessPoemException;

  public void saveColumnInfo(ColumnInfo info) throws AccessPoemException {
    info.setNullable(nullable);
    info.setSize(0);
    info.setRangelow_string(
        getLowRaw() == null ? null : stringOfRaw(getLowRaw()));
    // this _won't_ throw an OutsideRangePoemException since it doesn't check
    info.setRangelimit_string(
        getLimitRaw() == null ? null : stringOfRaw(getLimitRaw()));
    _saveColumnInfo(info);
  }

  protected abstract String _quotedRaw(Object raw);

  public String quotedRaw(Object raw) throws ValidationPoemException {
    assertValidRaw(raw);
    return raw == null ? "NULL" : _quotedRaw(raw);
  }

  protected abstract String _toString();

  public String toString() {
    return (nullable ? "nullable " : "") + _toString();
  }

  // 
  // --------
  //  Object
  // --------
  // 

  protected Object clone() {
    try {
      return super.clone();
    }
    catch (CloneNotSupportedException e) {
      throw new PoemBugPoemException();
    }
  }
}
