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
 * In the meantime, if you want to use WebMacro on non-GPL terms,
 * contact me!
 */

package org.melati.poem;

import java.util.*;
import java.sql.*;
import org.melati.util.*;

public abstract class BasePoemType implements PoemType, Cloneable {
  private int sqlTypeCode;
  private boolean nullable;
  private int width;
  private int height;

  private Comparable low = null, limit = null;

  BasePoemType(int sqlTypeCode, boolean nullable, int width, int height) {
    this.sqlTypeCode = sqlTypeCode;
    this.nullable = nullable;
    this.width = width;
    this.height = height;
  }

  BasePoemType(int sqlTypeCode, boolean nullable, int width) {
    this(sqlTypeCode, nullable, width, 1);
  }

  BasePoemType(int sqlTypeCode, boolean nullable) {
    this(sqlTypeCode, nullable, 8);
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
    Object raw = _rawOfString(string);
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

  public final int getWidth() {
    return width;
  }

  public final int getHeight() {
    return height;
  }

  protected abstract String _sqlDefinition();

  public final String sqlDefinition() {
    return _sqlDefinition() + (nullable ? "" : " NOT NULL");
  }

  protected abstract boolean _canBe(PoemType other);

  public final boolean canBe(PoemType other) {
    // FIXME takes no account of range---need to decide on semantics for this,
    // is it subset (inclusion) or some other notion of storability?
    return
        other.sqlTypeCode() == sqlTypeCode &&
        other.getNullable() == nullable &&
        _canBe(other);
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
    info.setWidth(width);
    info.setHeight(height);
    info.setRangelow_string(
        getLowRaw() == null ? null : stringOfRaw(getLowRaw()));
    // this _won't_ throw an OutsideRangePoemException since it doesn't check
    info.setRangelimit_string(
        getLimitRaw() == null ? null : stringOfRaw(getLimitRaw()));
    _saveColumnInfo(info);
  }

  protected String _quotedRaw(Object raw) {
    return raw.toString();
  }

  public String quotedRaw(Object raw) throws ValidationPoemException {
    assertValidRaw(raw);
    return raw == null ? "NULL" : _quotedRaw(raw);
  }

  public String toString() {
    return sqlDefinition();
  }

  public static PoemType ofColumnInfo(Database database, ColumnInfo info) {
    return
        PoemTypeFactory.forCode(database,
                                info.type.intValue()).typeOf(database, info);
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
