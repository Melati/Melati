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

import java.sql.SQLException;
import java.sql.ResultSet;

/**
 * A {@link ResultSetEnumeration} relying on a {@link TailoredQuery}
 * for column and access information.
 */
public class TailoredResultSetEnumeration<T> extends ResultSetEnumeration<T> {

  protected TailoredQuery query;

  /**
   * Constructor.
   * @param query the TailoredQuery
   * @param resultSet ResultSet to create Enumeration from
   */
  public TailoredResultSetEnumeration(TailoredQuery query,
                                      ResultSet resultSet) {
    super(resultSet);
    this.query = query;
  }

  /**
   * Check whether defaultCanRead debars us from reading. 
   * Explicit canRead columns are checked in column(ResultSet them, int c).
   */
  void checkTableAccess() {
    AccessToken token = PoemThread.accessToken();

    for (int t = 0; t < query.tablesWithoutCanReadColumn.length; ++t) {
      Capability canRead = query.tablesWithoutCanReadColumn[t].getDefaultCanRead();
      if (canRead != null && !token.givesCapability(canRead))
        throw new AccessPoemException(token, canRead);
    }
  }

  /**
   * Returns the raw value of a numbered column in the current row 
   * of a result set.
   * 
   * @param them the ResultSet
   * @param c index into ResultSet
   * @return the raw value
   */
  Object column(ResultSet them, int c) {
    Column column = query.columns[c];
    Object raw = column.getSQLType().getRaw(them, c + 1);

    if (query.isCanReadColumn[c]) {
      Capability canRead = (Capability)column.getType().cookedOfRaw(raw);
      if (canRead == null)
        canRead = column.getTable().getDefaultCanRead();
      if (canRead != null) {
        AccessToken token = PoemThread.accessToken();
        if (!token.givesCapability(canRead))
          throw new AccessPoemException(token, canRead);
      }
    }

    return raw;
  }

  /**
   * Return the current result set row packaged with column names in a 
   * {@link FieldSet}.
   * {@inheritDoc}
   * @see org.melati.poem.ResultSetEnumeration#mapped(java.sql.ResultSet)
   */
  protected Object mapped(ResultSet them)
      throws SQLException, NoSuchRowPoemException {
    checkTableAccess();

    Field[] fields = new Field[query.columns.length];
    try {
      for (int c = 0; c < fields.length; ++c)
        fields[c] = new Field(column(them, c), query.columns[c]);
    }
    catch (AccessPoemException accessProblem) {
      // Blank out the whole FieldSet.  We have to do this because we don't
      // know how fields have been used in the WHERE clause without
      // interpreting it.  Walls have ears and all that.

      for (int c = 0; c < fields.length; ++c)
        fields[c] = new Field(accessProblem, query.columns[c]);
    }

    return new FieldSet(query.table_columnMap, fields);
  }
}
