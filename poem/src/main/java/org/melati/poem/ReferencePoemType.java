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

import java.util.Enumeration;
import org.melati.poem.util.StringUtils;

/**
 * A data type that is a reference to a {@link Persistent} object.
 */
public class ReferencePoemType extends IndexPoemType {

  private Table<?> targetTable;

  /**
   * Constructor.
   * 
   * @param targetTable the Table the type refers to 
   * @param nullable whether this type may contain null values
   */
  public ReferencePoemType(Table<?> targetTable, boolean nullable) {
    super(nullable);
    if (targetTable == null)
      throw new NullPointerException();
    this.targetTable = targetTable;
  }

  /**
   * @return Table this type references
   */
  public Table<?> targetTable() {
    return targetTable;
  }

  /**
   * Returns an <code>Enumeration</code> of the possible raw values.
   * <p>
   * In this case the troids of rows in the referenced table.
   */
  protected Enumeration<Integer> _possibleRaws() {
    return targetTable.troidSelection(null, null, false);
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
    return targetTable.getObject((Integer)raw);
  }

  protected Integer _rawOfCooked(Object cooked) {
    return ((Persistent)cooked).troid();
  }

  protected String _stringOfCooked(Object cooked, 
                                   PoemLocale locale, int style)
      throws PoemException {
    return ((Persistent)cooked).displayString(locale, style);
  }

  protected boolean _canRepresent(SQLPoemType<?> other) {
    return
        other instanceof ReferencePoemType &&
        ((ReferencePoemType)other).targetTable == targetTable;
  }

  protected void _saveColumnInfo(ColumnInfo columnInfo)
      throws AccessPoemException {
    columnInfo.setTypefactoryCode(targetTable.tableInfoID());
  }

  /**
   * {@inheritDoc}
   * @see org.melati.poem.BasePoemType#toString()
   */
  public String toString() {
    return
        "reference to " + targetTable.getName() + 
        " (" + super.toString() + ")";
  }

  /**
   * {@inheritDoc}
   * @see org.melati.poem.PoemType#toDsdType()
   */
  public String toDsdType() {
    return StringUtils.capitalised(targetTable.getName());
  }
}
