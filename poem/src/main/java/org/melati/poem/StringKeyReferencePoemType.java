/*
 * $Source$
 * $Revision$
 *
 * Copyright (C) 2012 Tim Pizey
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
 *     Tim Pizey <timp At paneris.org>
 *     http://paneris.org/~timp
 */

package org.melati.poem;

import java.util.Enumeration;

import org.melati.poem.util.MappedEnumeration;
import org.melati.poem.util.StringUtils;

/**
 * A data type that is a reference to a {@link Persistent} object via a 
 * String key to a unique, non-nullable index.
 *
 * @author timp
 * @since 2012-05-19
 */
public class StringKeyReferencePoemType extends StringKeyPoemType implements PersistentReferencePoemType {

  private Table<?> targetTable;
  
  private String targetKeyName;

  /**
   * Constructor.
   * 
   * @param targetTable the Table the type refers to 
   * @param targetKeyName name of key both in this and target table
   * @param nullable whether this type may contain null values
   * @param size the size of the key string
   */
  public StringKeyReferencePoemType(Table<?> targetTable, 
      String targetKeyName, boolean nullable, int size) {
    super(nullable, size);
    if (targetTable == null)
      throw new NullPointerException();
    this.targetTable = targetTable;
    if (targetKeyName == null)
      throw new NullPointerException();
    this.targetKeyName = targetKeyName;
  }

  /**
   * @see org.melati.poem.PersistentReferencePoemType#targetTable()
   */
  @Override
  public Table<?> targetTable() {
    return targetTable;
  }

  /** @return the key name in target table, also this table */ 
  public String targetKeyName() { 
    return targetKeyName;
  }
  /**
   * Returns an <code>Enumeration</code> of the possible raw values.
   * <p>
   * In this case the key values of rows in the referenced table.
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  protected Enumeration<String> _possibleRaws() {
    return new MappedEnumeration(targetTable.selection()) {
      public String mapped(Object p) {
        return (String)((Persistent)p).getRaw(targetKeyName);
      }
    };
  }

  protected void _assertValidCooked(Object cooked)
      throws ValidationPoemException {
    if (!(cooked instanceof Persistent))
      throw new TypeMismatchPoemException(cooked, this);

    Persistent persistent = (Persistent)cooked;

    if (persistent.getTable() != targetTable)
      throw new ValidationPoemException(
          this, persistent,
          new TableMismatchPoemException(persistent, targetTable));
  }

  protected Object _cookedOfRaw(Object raw) throws NoSuchRowPoemException {
    return targetTable.getColumn(targetKeyName).firstWhereEq(raw);
  }

  /** 
   * NOTE that it is expected that the targetKeyName is also 
   * the name of the field in this persistent.
   */
  protected String  _rawOfCooked(Object cooked) {
    return (String) ((Persistent)cooked).getField(targetKeyName).getRawString();
  }

  protected String _stringOfCooked(Object cooked, 
                                   PoemLocale locale, int style)
      throws PoemException {
    return ((Persistent)cooked).displayString(locale, style);
  }

  protected boolean _canRepresent(SQLPoemType<?> other) {
    return
        other instanceof StringKeyReferencePoemType &&
        ((StringKeyReferencePoemType)other).targetTable == targetTable;
  }

  protected void _saveColumnInfo(ColumnInfo columnInfo)
      throws AccessPoemException {
    columnInfo.setTypefactoryCode(targetTable.tableInfoID());
    columnInfo.setSize(getSize());
  }

  /**
   * {@inheritDoc}
   * @see org.melati.poem.BasePoemType#toString()
   */
  public String toString() {
    return
        "string key reference to " + targetTable.getName() + 
        " on " + targetKeyName +
        " (" + super.toString() + ")";
  }

  /**
   * {@inheritDoc}
   * @see org.melati.poem.PoemType#toDsdType()
   */
  public String toDsdType() {
    return StringUtils.capitalised(targetTable.getName() + " StringKeyReference on " + targetKeyName);
  }
}
