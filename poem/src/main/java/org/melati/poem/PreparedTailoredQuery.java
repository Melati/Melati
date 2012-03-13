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

/**
 * A {@link TailoredQuery} which uses a <code>PreparedStatement</code>.
 *
 */ 
public class PreparedTailoredQuery extends TailoredQuery {
  private PreparedStatementFactory statements;

  /**
   * Full constructor.
   * 
   * @param modifier SQL modifier eg DISTINCT
   * @param selectedColumns an array of columns we know we need
   * @param otherTables tables other than the one we belong to
   * @param whereClause raw SQL where clause
   * @param orderByClause raw SQL order by clause 
   */
  public PreparedTailoredQuery(String modifier,
                               Column<?>[] selectedColumns, Table<?>[] otherTables,
                               String whereClause, String orderByClause) {
    super(modifier, selectedColumns, otherTables, whereClause, orderByClause);
    statements = new PreparedStatementFactory(database, sql);
  }

  /**
   * Constructor with null modifier. 
   * 
   * @param selectedColumns an array of columns we know we need
   * @param otherTables tables other than the one we belong to
   * @param whereClause raw SQL where clause
   * @param orderByClause raw SQL order by clause 
   */
  public PreparedTailoredQuery(Column<?>[] selectedColumns, Table<?>[] otherTables,
                               String whereClause, String orderByClause) {
    this(null, selectedColumns, otherTables, whereClause, orderByClause);
  }

  /**
   * The results of the query.
   * @return the results (a TailoredResultSetEnumeration) as an Enumeration.
   * @see org.melati.poem.TailoredQuery#selection()
   */
  public Enumeration<FieldSet> selection() {
    return new TailoredResultSetEnumeration<FieldSet>(this, statements.resultSet());
  }

  /**
   * @return an enumeration of the Columns in the first row of a ResultSet
   * {@inheritDoc}
   * @see org.melati.poem.TailoredQuery#selection_firstRaw()
   */
  public Enumeration<Object> selection_firstRaw() {
    return new FirstRawTailoredResultSetEnumeration<Object>(this,
                                                    statements.resultSet());
  }
}
