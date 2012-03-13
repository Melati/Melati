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
import java.util.Vector;
import org.melati.poem.util.EnumUtils;

/**
 * A cached instance of a {@link PreparedTailoredQuery}.
 *
 * @author WilliamC At paneris.org
 *
 */
public class CachedTailoredQuery extends PreparedTailoredQuery {

  private Vector<FieldSet> results = null;
  private Vector<Object> firstRawResults = null;
  private long[] tableSerials;

  /**
   * Full Constructor.
   * 
   * @param modifier  SQL modifier eg DISTINCT
   * @param selectedColumns An array of columns we know we need
   * @param otherTables Tables other than ours whose modification 
   *                    state needs to be taken into account
   * @param whereClause Raw SQL
   * @param orderByClause Raw ORDER BY clause
   */
  public CachedTailoredQuery(String modifier, 
                             Column<?>[] selectedColumns, Table<?>[] otherTables,
                             String whereClause, String orderByClause) {
    super(modifier, selectedColumns, otherTables, whereClause, orderByClause);
    tableSerials = new long[tables.length];
  }

  /**
   * Constructor with modifier null. 
   * 
   * @param selectedColumns An array of columns we know we need
   * @param otherTables Tables other than ours whose modification 
   *                    state needs to be taken into account
   * @param whereClause Raw SQL
   * @param orderByClause Raw ORDER BY clause
   */
  public CachedTailoredQuery(Column<?>[] selectedColumns, Table<?>[] otherTables,
                             String whereClause, String orderByClause) {
    this(null, selectedColumns, otherTables, whereClause, orderByClause);
  }

  /**
   * @return whether the underlying tables have changed since last run.
   */
  protected boolean upToDate() {
    boolean is = true;

    PoemTransaction transaction = PoemThread.transaction();
    for (int t = 0; t < tables.length; ++t) {
      long currentSerial = tables[t].serial(transaction);
      if (tableSerials[t] != currentSerial) {
        is = false;
        tableSerials[t] = currentSerial;
      }
    }

    return is;
  }

  /**
   * {@inheritDoc}
   * @see org.melati.poem.TailoredQuery#selection()
   */
  public Enumeration<FieldSet> selection() {
    Vector<FieldSet> resultsLocal = this.results;
    if (!upToDate() || resultsLocal == null) {
      this.results = EnumUtils.vectorOf(super.selection());
      resultsLocal = this.results;
    }
    return resultsLocal.elements();
  }

  /**
   * {@inheritDoc}
   * @see org.melati.poem.TailoredQuery#selection_firstRaw()
   */
  public Enumeration<Object> selection_firstRaw()  {
    Vector<Object> firstRawResultsLocal = this.firstRawResults;
    if (!upToDate() || firstRawResultsLocal == null) {
      this.firstRawResults =
        EnumUtils.vectorOf(super.selection_firstRaw());
      firstRawResultsLocal = this.firstRawResults;
    }
    return firstRawResultsLocal.elements();
  }
}
