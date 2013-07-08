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
import java.sql.PreparedStatement;
import java.sql.Connection;
import org.melati.poem.util.CachedIndexFactory;

/**
 * Maintains a cache of <code>PreparedStatement</code>s for an SQL
 * statement string.
 * <p>
 * The cached contents are discarded if the database structure has
 * changed since the cache was created.
 * <p>
 * Execution of the statement in a transaction reflects uncommitted
 * changes in that transaction.
 * <p>
 * The supertype dictates that connections can be identified
 * by index, but this is slightly complicated and the additional
 * methods rely on transactions instead.
 */

public class PreparedStatementFactory extends CachedIndexFactory {


  private Database database;
  private long structureSerial;
  private String sql;

  /**
   * Constructor.
   * @param database the db we are working with
   * @param sql the SQL statement
   */
  public PreparedStatementFactory(Database database, String sql) {
    this.database = database;
    this.structureSerial = database.structureSerial();
    this.sql = sql;
  }

  /**
   * Obtain a fresh <code>PreparedStatement</code> for a connection
   * identified by an index.
   * <p>
   * The index is zero for the committed connection and the
   * transaction index plus 1 for current transactions.
   */
  protected Object reallyGet(int index) {
    try {
      @SuppressWarnings("resource") // this is wrong, IMHO
      Connection c =
        index == 0 ? database.getCommittedConnection()
                   : database.poemTransaction(index - 1).getConnection();
      return c.prepareStatement(sql);
    } catch (SQLException e) {
      throw new SQLPoemException(e);
    }
  }

  /**
   * {@inheritDoc}
   * @see org.melati.poem.util.CachedIndexFactory#get(int)
   */
  public Object get(int index) {
    if (structureSerial != database.structureSerial()) {
      invalidate();
      structureSerial = database.structureSerial();
    }

    return super.get(index);
  }

  /**
   * Get a new or cached PreparedStatement. 
   * @param transaction the PoemTransaction, can be null
   * @return a new or cached PreparedStatement
   */
  public PreparedStatement preparedStatement(PoemTransaction transaction) {
    return (PreparedStatement)get(transaction == null ? 0 : transaction.index + 1);
  }

  /**
   * @return a new or cached PreparedStatement for the current PoemThread's transaction.
   */
  public final PreparedStatement preparedStatement() {
    return preparedStatement(PoemThread.transaction());
  }

  protected ResultSet resultSet(SessionToken token,
                                PreparedStatement statement) {
    try {
      if (database.logSQL())
        database.log(new SQLLogEvent("PS about to:" + statement.toString() + "(" + sql + ")"));

      token.transaction.writeDown();

      ResultSet rs = null;
      /*
      if (database.getDbms().toString()
              .equals("org.melati.poem.dbms.MSAccess")){
        // This is due to some uncontrollable 
        // lazy write caching.
        // On my machine it is taking 4s to 
        // sync
        boolean notDone = true;
        int failCount = 0;
        while (notDone) {
          try {
            rs = statement.executeQuery();
            notDone = false;
          } catch (SQLException e) {
            try {
              System.err.println("Sleeping");
              Thread.sleep(1000);
              failCount++;
            } catch (InterruptedException e1) {
              throw new PoemBugPoemException("Sleep interrupted");
            }
            if (failCount > 8) {
              throw new PreparedSQLSeriousPoemException(
                      statement, e);              
            }
          }
        }
      } else 
      */
      rs = statement.executeQuery();
      token.toTidy().add(rs);
      database.incrementQueryCount(statement.toString());
      return rs;
    } catch (SQLException e) {
      throw new PreparedSQLSeriousPoemException(statement, e);
    }
  }

  protected final ResultSet resultSet(SessionToken token) {
    return resultSet(token, preparedStatement(token.transaction));
  }

  /**
   * @return the ResultSet from the PreparedStatement of the PoemThread
   */
  public final ResultSet resultSet() {
    return resultSet(PoemThread.sessionToken());
  }

  /**
   * {@inheritDoc}
   * @see java.lang.Object#toString()
   */
  public String toString() {

    return super.toString() + " (SQL: " + sql + ")";
  }

}
