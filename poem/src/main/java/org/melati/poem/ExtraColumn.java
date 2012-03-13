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


/**
 * A {@link Column} which exists in the dbms but is not defined in the 
 * DSD.
 */
public class ExtraColumn<T> extends Column<T> {
  private final int extrasIndex;

  /**
   * Constructor.
   */
  public <P extends Persistent> ExtraColumn(Table<P> table, String name, SQLPoemType<T> type,
                     DefinitionSource definitionSource,
                     int extrasIndex) {
    super(table, name, type, definitionSource);
    this.extrasIndex = extrasIndex;
  }

  /**
   * {@inheritDoc}
   * @see org.melati.poem.Column#getRaw(org.melati.poem.Persistent)
   */
  public T getRaw(Persistent g) throws AccessPoemException {
    ((JdbcPersistent)g).readLock();
    return getRaw_unsafe(g);
  }

  /**
   * {@inheritDoc}
   * @see org.melati.poem.Column#getRaw_unsafe(org.melati.poem.Persistent)
   */
  @SuppressWarnings("unchecked")
  public T getRaw_unsafe(Persistent g) {
    return (T)((JdbcPersistent)g).extras()[extrasIndex];
  }

  /**
   * {@inheritDoc}
   * @see org.melati.poem.Column#setRaw(org.melati.poem.Persistent, java.lang.Object)
   */
  public void setRaw(Persistent g, Object raw)
      throws AccessPoemException, ValidationPoemException {
    getType().assertValidRaw(raw);
    ((JdbcPersistent)g).writeLock();
    setRaw_unsafe(g, raw);
  }

  /**
   * {@inheritDoc}
   * @see org.melati.poem.Column#setRaw_unsafe(org.melati.poem.Persistent, java.lang.Object)
   */
  public void setRaw_unsafe(Persistent g, Object raw) {
    ((JdbcPersistent)g).extras()[extrasIndex] = raw;
  }

  /**
   * {@inheritDoc}
   * @see org.melati.poem.Column#getCooked(org.melati.poem.Persistent)
   */
  public Object getCooked(Persistent g)
      throws AccessPoemException, PoemException {
    // FIXME Revalidation  
    return getType().cookedOfRaw(getRaw(g));
  }

  /**
   * {@inheritDoc}
   * @see org.melati.poem.Column#setCooked(org.melati.poem.Persistent, java.lang.Object)
   */
  public void setCooked(Persistent g, Object cooked)
      throws AccessPoemException, ValidationPoemException {
    // FIXME Revalidation
    getType().assertValidCooked(cooked);
    setRaw(g, getType().rawOfCooked(cooked));
  }

  /**
   * {@inheritDoc}
   * @see org.melati.poem.Column#asField(org.melati.poem.Persistent)
   */
  public Field<T> asField(Persistent g) {
    try {
      return new Field<T>((T)getRaw(g), this);
    }
    catch (AccessPoemException accessException) {
      return new Field<T>(accessException, this);
    }
  }

  /**
   * Static factory.
   * @param table
   * @param columnInfo
   * @param extrasIndex
   * @param source
   * @return a new ExtraColumn 
   */
  @SuppressWarnings("unchecked")
  public static <O,P extends Persistent>Column<O> from(Table<P> table, ColumnInfo columnInfo,
                            int extrasIndex, DefinitionSource source) {
    return new ExtraColumn<O>(
        table, columnInfo.getName(), (SQLPoemType<O>)columnInfo.getType(), 
        source, extrasIndex);
  }
}
