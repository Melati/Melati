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
import java.util.Enumeration;

import org.melati.poem.dbms.Dbms;
import org.melati.poem.util.ConsEnumeration;

/**
 * Base class of all fundamental types.
 */
public abstract class BasePoemType<T> implements SQLPoemType<T>, Cloneable {
  private int sqlTypeCode;
  protected boolean nullable;

  private Comparable<T> low = null, limit = null;

  BasePoemType(int sqlTypeCode, boolean nullable) {
    this.sqlTypeCode = sqlTypeCode;
    this.nullable = nullable;
  }

  /**
   * Set the limits, if applicable.
   * @param low included lower limit
   * @param limit excluded upper limit
   */
  public void setRawRange(Comparable<T> low, Comparable<T> limit) {
    this.low = low;
    this.limit = limit;
  }

  protected Comparable<T> getLowRaw() {
    return low;
  }

  protected Comparable<T> getLimitRaw() {
    return limit;
  }

  protected abstract void _assertValidRaw(Object raw)
      throws ValidationPoemException;

  @SuppressWarnings("unchecked")
  private void assertRawInRange(Object raw) {
    // Range check.  Since we can't do this with multiple inheritance, we
    // provide it as a facility even in types for which it is meaningless.

    T asComparable;
    try {
      // Note that in java5 this will not throw until 
      // the cast object is accessed
      asComparable = (T)raw;

      if ((low != null && low.compareTo(asComparable) > 0) ||
          (limit != null && limit.compareTo(asComparable) <= 0))
      throw new ValidationPoemException(
            this, raw, new OutsideRangePoemException(low, limit, raw));
    } catch (ClassCastException e) {
      throw new NotComparablePoemException(raw, this);
    }
  }

  /**
   * {@inheritDoc}
   * @see org.melati.poem.PoemType#assertValidRaw(java.lang.Object)
   */
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

  /**
   * Check if the raw value is valid, as expected.
   * @param raw an Object which should be of correct type
   */
  private void doubleCheckValidRaw(Object raw) {
    try {
      assertValidRaw(raw);
    }
    catch (ValidationPoemException e) {
      throw new UnexpectedValidationPoemException(e);
    }
  }

  protected abstract T _getRaw(ResultSet rs, int col)
      throws SQLException;

  /**
   * {@inheritDoc}
   * @see org.melati.poem.SQLType#getRaw(java.sql.ResultSet, int)
   */
  public final T getRaw(ResultSet rs, int col)
      throws ValidationPoemException {
    T o;
    try {
      o = (T) _getRaw(rs, col);
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

  /**
   * {@inheritDoc}
   * @see org.melati.poem.SQLType#setRaw(java.sql.PreparedStatement, int, java.lang.Object)
   */
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

  protected Enumeration<T> _possibleRaws() {
    return null;
  }
  
  /**
   * {@inheritDoc}
   * @see org.melati.poem.PoemType#possibleRaws()
   */
  public Enumeration<T> possibleRaws() {
    Enumeration<T> them = _possibleRaws();
    return them == null ? null :
                   getNullable() ? new ConsEnumeration<T>(null, them) :
                   them;
  }

  protected abstract String _stringOfRaw(Object raw);

  /**
   * This <B>doesn't</B> do an explicit <TT>assertValidRaw</TT>.
      * {@inheritDoc}
   * @see org.melati.poem.PoemType#stringOfRaw(java.lang.Object)
   */
  public final String stringOfRaw(Object raw)
      throws ValidationPoemException {
    return raw == null ? null : _stringOfRaw(raw);
  }

  /**
   * Converts a non-null string to an appropriate value 
   * for insertion into the underlying DBMS.
   * @param string the String to parse
   * @return a converted type
   */
  protected abstract T _rawOfString(String string)
      throws ParsingPoemException;

  /**
   * Converts a possibly null <code>String</code> to a low level
   * representation of a valid database column value.
   * <p>
   * Null values are not changed.
   * <p>
   * This result is validated with {@link #assertValidRaw(Object)}
   * whereas {@link #stringOfRaw(Object)} assumes this is not
   * required.
   * {@inheritDoc}
   * @see org.melati.poem.PoemType#rawOfString(java.lang.String)
   */
  public final T rawOfString(String string)
      throws ParsingPoemException, ValidationPoemException {
    T raw = string == null ? null : _rawOfString(string);
    assertValidRaw(raw);
    return raw;
  }

  protected abstract void _assertValidCooked(Object cooked)
      throws ValidationPoemException;

  /**
   * {@inheritDoc}
   * @see org.melati.poem.PoemType#assertValidCooked(java.lang.Object)
   */
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

  /**
   * Check that object is valid, as expected.
   * NOTE If it isn't valid then it isn't cooked.
   * @param cooked the cooked object
   */
  final void doubleCheckValidCooked(Object cooked) {
    try {
      assertValidCooked(cooked);
    }
    catch (ValidationPoemException e) {
      throw new UnexpectedValidationPoemException(e);
    }
  }

  /**
   * Converts a non-null low-level representation of a database
   * column value to the appropriate object.
   * <p>
   * For the base object types, (String, Integer etc) this involves no change. 
   * <p>
   * For types with an integer id, such as Poem internal types and user defined types, 
   * then the appropriate instantiated type is returned from its Integer id.
   * @param raw the base object or Integer object id
   * @return the unchanged base object or an instantiated type
   */
  protected abstract Object _cookedOfRaw(Object raw) throws PoemException;

  /**
   * Converts a possibly null low-level representation of a database
   * column value to its canonical form. 
   * Types represented as integers in the database are converted to 
   * corresponding objects .
   * <p>
   * The raw value is checked to ensure it is valid.
   * {@inheritDoc}
   * @see org.melati.poem.PoemType#cookedOfRaw(java.lang.Object)
   */
  public final Object cookedOfRaw(Object raw) throws PoemException {
    doubleCheckValidRaw(raw);
    return raw == null ? null : _cookedOfRaw(raw);
  }

  protected abstract T _rawOfCooked(Object raw) throws PoemException;

  /**
   * {@inheritDoc}
   * @see org.melati.poem.PoemType#rawOfCooked(java.lang.Object)
   */
  public final T rawOfCooked(Object cooked) {
    doubleCheckValidCooked(cooked);
    return cooked == null ? null : _rawOfCooked(cooked);
  }

  protected abstract String _stringOfCooked(Object cooked,
                                           PoemLocale locale, int style)
      throws PoemException;

  /**
   * {@inheritDoc}
   * @see org.melati.poem.PoemType#stringOfCooked(java.lang.Object, 
   *          org.melati.poem.PoemLocale, int)
   */
  public final String stringOfCooked(Object cooked,
                                    PoemLocale locale, int style)
      throws PoemException {
    doubleCheckValidCooked(cooked);
    return cooked == null ? "" : _stringOfCooked(cooked, locale, style);
  }

  /**
   * {@inheritDoc}
   * @see org.melati.poem.PoemType#getNullable()
   */
  public final boolean getNullable() {
    return nullable;
  }

  /**
   * {@inheritDoc}
   * @see org.melati.poem.SQLType#sqlTypeCode()
   */
  public final int sqlTypeCode() {
    return sqlTypeCode;
  }

  protected abstract String _sqlDefinition(Dbms dbms);

  /**
   * See http://dev.mysql.com/doc/refman/5.0/en/timestamp.html
   * The MySQL default for nullability of timestamps is not null, so need to 
   * make all fields explicitly nullable.
   * 
   * {@inheritDoc}
   * @see org.melati.poem.SQLType#sqlDefinition(org.melati.poem.dbms.Dbms)
   */
  public String sqlDefinition(Dbms dbms) {
    return sqlTypeDefinition(dbms) + (nullable ? " NULL" : " NOT NULL");
  }
  /**
   * {@inheritDoc}
   * @see org.melati.poem.SQLType#sqlTypeDefinition(org.melati.poem.dbms.Dbms)
   */
  public String sqlTypeDefinition(Dbms dbms) {
    return _sqlDefinition(dbms);
  }
  protected abstract boolean _canRepresent(SQLPoemType<?> other);

  /**
   * {@inheritDoc}
   * @see org.melati.poem.PoemType#canRepresent(org.melati.poem.PoemType)
   */
  public <O>PoemType<O> canRepresent(PoemType<O> other) {
    // FIXME takes no account of range---need to decide on semantics for this,
    // is it subset (inclusion) or some other notion of storability?
    if (!(other instanceof SQLPoemType)) 
      // NOTE Never happens as currently all PoemTypes are SQLPoemTypes
      return null;
    else {
      SQLPoemType<O> q = (SQLPoemType<O>)other;
      return
          !(!nullable && q.getNullable()) && // Nullable may represent not nullable
          _canRepresent(q) ?
              q : null;
    }
  }

  /**
   * {@inheritDoc}
   * @see org.melati.poem.PoemType#withNullable(boolean)
   */
  @SuppressWarnings("unchecked")
  public final PoemType<T> withNullable(boolean nullableP) {
    if (this.nullable == nullableP)
      return this;
    else {
      BasePoemType<T> it = (BasePoemType<T>)clone();
      it.nullable = nullableP;
      return it;
    }
  }

  protected abstract void _saveColumnInfo(ColumnInfo info)
      throws AccessPoemException;

  /**
   * {@inheritDoc}
   * @see org.melati.poem.PoemType#saveColumnInfo(org.melati.poem.ColumnInfo)
   */
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

  /**
   * {@inheritDoc}
   * @see org.melati.poem.SQLType#quotedRaw(java.lang.Object)
   */
  public String quotedRaw(Object raw) throws ValidationPoemException {
    assertValidRaw(raw);
    return raw == null ? "NULL" : _quotedRaw(raw);
  }

  protected abstract String _toString();

  // 
  // --------
  //  Object
  // --------
  // 

  /**
   * {@inheritDoc}
   * @see java.lang.Object#toString()
   */
  public String toString() {
    return (nullable ? "nullable " : "") + _toString() + 
    " (" + this.getClass().getName() + ")";
  }

  /**
   * {@inheritDoc}
   * @see java.lang.Object#clone()
   */
  protected Object clone() {
    try {
      return super.clone();
    }
    catch (CloneNotSupportedException e) {
      throw new PoemBugPoemException();
    }
  }
}
