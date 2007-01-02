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
 *     William Chesters <williamc AT paneris.org>
 *     http://paneris.org/~williamc
 *     Obrechtstraat 114, 2517VX Den Haag, The Netherlands
 */

package org.melati.poem;

import java.util.Enumeration;
import org.melati.util.FlattenedEnumeration;
import org.melati.util.MappedEnumeration;
import org.melati.util.ArrayEnumeration;
import org.melati.util.MelatiLocale;

/**
 * A metadata type which defines the type of another {@link Column}.
 *
 * Used in the {@link ColumnInfoTable} it can take 
 * positive values to indicate a reference to a type defined 
 * in the {@link TableInfoTable} or a negative value to 
 * point to a fundamental type.
 *
 * @see PoemTypeFactory#forCode
 */
public class ColumnTypePoemType extends IntegerPoemType {

  private Database database;

  /**
   * Constructor for a non nullable column type.
   * @param database the db we are dealing with
   */
  public ColumnTypePoemType(Database database) {
    super(false);
    this.database = database;
  }

  /**
   * {@inheritDoc}
   * @see org.melati.poem.BasePoemType#possibleRaws()
   */
  public Enumeration possibleRaws() {
    return
        new FlattenedEnumeration(
            new MappedEnumeration(
                new ArrayEnumeration(PoemTypeFactory.atomTypeFactories)) {
              public Object mapped(Object factory) {
                return ((PoemTypeFactory)factory).code;
              }
            },
            database.getTableInfoTable().troidSelection(null, null, false));
  }

  protected void _assertValidCooked(Object cooked)
      throws ValidationPoemException {
    if (!(cooked instanceof PoemTypeFactory))
      throw new TypeMismatchPoemException(cooked, this);
  }

  protected Object _cookedOfRaw(Object raw) throws NoSuchRowPoemException {
    return PoemTypeFactory.forCode(database, ((Integer)raw).intValue());
  }

  protected Object _rawOfCooked(Object cooked) {
    return ((PoemTypeFactory)cooked).code;
  }

  protected String _stringOfCooked(Object cooked,
                                   MelatiLocale locale, int style)
      throws PoemException {
    return ((PoemTypeFactory)cooked).getDisplayName();
  }

  protected boolean _canRepresent(SQLPoemType other) {
    return other instanceof ColumnTypePoemType;
  }

  protected void _saveColumnInfo(ColumnInfo columnInfo)
      throws AccessPoemException {
    columnInfo.setTypefactory(PoemTypeFactory.TYPE);
  }

  /**
   * {@inheritDoc}
   * @see org.melati.poem.BasePoemType#toString()
   */
  public String toString() {
    return "type code (" + super.toString() + ")";
  }

  /**
   * {@inheritDoc}
   * @see org.melati.poem.IntegerPoemType#toDsdType()
   */
  public String toDsdType() {
    return "ColumnType";
  }

  /**
   * Defaults to User.
   * {@inheritDoc}
   * @see org.melati.poem.SQLType#sqlDefaultValue()
   */
  public String sqlDefaultValue() {
    return new Integer(0).toString();
  }

}
