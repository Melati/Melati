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

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

/**
 * Object to return the results of a query with caching.
 * <p>
 * Cached results will be returned unless the relevant tables
 * have been updated since the query was last executed, in which case 
 * the results will be recomputed. 
 * 
 */
public abstract class CachedQuery {

  protected PreparedStatementFactory statements = null;

  protected Vector rows = null;

  private long tableSerial;

  protected Table table;

  private String query;

  private Table otherTables[];

  private long otherTablesSerial[];

  /**
   * Constructor.
   * 
   * @param table the table to select from 
   * @param query the SQL query string 
   * @param otherTables an array of other tables involved in the query
   */
  public CachedQuery(final Table table,
                     final String query,
          final Table otherTables[]) {
    this.table = table;
    this.query = query;
    this.otherTables = otherTables;
    if (otherTables != null)
      otherTablesSerial = new long[otherTables.length];
  }

  protected PreparedStatementFactory statements() {
    if (statements == null)
      statements = new PreparedStatementFactory(
                       table.getDatabase(),
                       query);

    return statements;
  }

  protected Object extract(ResultSet rs) throws SQLException {
    return new Integer(rs.getInt(1));
  }

  protected void compute() {
    Vector rowsLocal = this.rows;
    SessionToken token = PoemThread.sessionToken();
    if (rowsLocal == null || somethingHasChanged(token.transaction)) {
      rowsLocal = new Vector();
      try {
        ResultSet rs = statements().resultSet(token);
        try {
          while (rs.next())
            rowsLocal.addElement(extract(rs));
        } finally {
          try {
            rs.close();
          } catch (Exception e) {
            // Report the real problem above
            e = null; // shut Checkstyle up!
          }
        }
      } catch (SQLException e) {
        throw new SQLSeriousPoemException(e);
      }
      this.rows = rowsLocal;
      updateSerials(token.transaction);
    }
  }

  private boolean somethingHasChanged(PoemTransaction transaction) {
    if (table.serial(transaction) != tableSerial)
      return true;

    if (otherTables != null) {
      for (int i = 0; i < otherTables.length; i++) {
        if (otherTables[i].serial(transaction) != otherTablesSerial[i])
          return true;
      }
    }

    return false;
  }

  private void updateSerials(PoemTransaction transaction) {
    tableSerial = table.serial(transaction);
    if (otherTables != null) {
      for (int i = 0; i < otherTables.length; i++) {
        otherTablesSerial[i] = otherTables[i].serial(transaction);
      }
    }
  }

  /**
   * @return the table property
   */
  public Table getTable() {
    return table;
  }

  /**
   * Used in constructor of {@link CachedSelection}.
   * @param query the query to set
   */
  protected void setQuery(String query) {
    this.query = query;
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.lang.Object#toString()
   */
  public String toString() {
    return getClass().getName() + " " + query;
  }
}
