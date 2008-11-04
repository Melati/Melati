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

import java.sql.Connection;
import java.sql.SQLException;

import org.melati.poem.transaction.Transaction;

/**
 * A set of dbms actions, which can be rolled back if necessary.
 */
public class PoemTransaction extends Transaction {
  private Database database;
  private Connection connection;

  /**
   * Constructor.
   */
  public PoemTransaction(Database database, Connection connection, int index) {
    super(database, index);
    this.database = database;
    this.connection = connection;
    try {
      connection.setAutoCommit(false);
    }
    catch (SQLException e) {
      throw new SQLSeriousPoemException(e);
    }
  }

  /**
   * @return the Database this PoemTransaction relates to
   */
  public final Database getDatabase() {
    return database;
  }

  final Connection getConnection() {
    return connection;
  }

  protected void backingCommit() {
    try {
      connection.commit();
      if (database.logCommits()) database.log(new CommitLogEvent(this));
    }
    catch (SQLException e) {
      throw new CommitFailedPoemException(e);
    }
  }

  protected void backingRollback() {
    try {
      connection.rollback();
      if (database.logCommits()) database.log(new RollbackLogEvent(this));
    }
    catch (SQLException e) {
      throw new RollbackFailedPoemException(e);
    }
  }

  /**
   * Close the transaction, committing or rolling back, 
   * notifying the database that we are closed.
   * @param commit whether to commit before closing
   */
  public void close(boolean commit) {
    try {
      if (commit)
        commit();
      else
        rollback();
    }
    finally {
      database.notifyClosed(this);
    }
  }

  /**
   * {@inheritDoc}
   * @see org.melati.poem.transaction.Transaction#toString()
   */
  public String toString() {
    return database.getName() + "/" + super.toString();
  }
}


