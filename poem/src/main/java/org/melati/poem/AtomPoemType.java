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

import org.melati.poem.dbms.Dbms;

/** 
 * Base class for all atomic types.
 * <p>
 * Currently all types, as generated classes are instances of IntegerPoemType.
 */
public abstract class AtomPoemType<T> extends BasePoemType<T> {

  protected String sqlTypeName;

  /**
   * Constructor.
   * @param sqlTypeCode from Types
   * @param sqlTypeName SQL Type name
   * @param nullable whether null is an allowed value
   */
  public AtomPoemType(int sqlTypeCode, String sqlTypeName, boolean nullable) {
    super(sqlTypeCode, nullable);
    this.sqlTypeName = sqlTypeName;
  }

  protected String _quotedRaw(Object raw) {
    return _stringOfRaw(raw);
  }

  protected String _stringOfRaw(Object raw) {
    return raw.toString();
  }

  protected void _assertValidCooked(Object cooked)
      throws ValidationPoemException {
    _assertValidRaw(cooked);
  }

  /**
   * Returns the given value unchanged.
   */
  protected Object _cookedOfRaw(Object raw) throws PoemException {
    return raw;
  }

  /**
   * Returns the given value unchanged.
   */
  protected Object _rawOfCooked(Object cooked) {
    return cooked;
  }

  protected String _stringOfCooked(Object cooked,
                                   PoemLocale locale, int style) {
    return _stringOfRaw(_rawOfCooked(cooked));
  }
 
  protected String _sqlDefinition(Dbms dbms) {
      return dbms.getSqlDefinition(sqlTypeName);
  }

  /**
   * {@inheritDoc}
   * @see org.melati.poem.SQLType#sqlDefaultValue()
   */
  public String sqlDefaultValue(Dbms dbms) {
    return dbms.getSqlDefaultValue(this);
  }

  
  protected String _toString() {
    return sqlTypeName;
  }
}
