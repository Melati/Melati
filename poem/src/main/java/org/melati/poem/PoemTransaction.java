/*
 * $Source$
 * $Revision$
 *
 * Part of Melati (http://melati.org), a framework for the rapid
 * development of clean, maintainable web applications.
 *
 * -------------------------------------
 *  Copyright (C) 2000 William Chesters
 * -------------------------------------
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 * A copy of the GPL should be in the file org/melati/COPYING in this tree.
 * Or see http://melati.org/License.html.
 *
 * Contact details for copyright holder:
 *
 *     William Chesters <williamc@paneris.org>
 *     http://paneris.org/~williamc
 *     Obrechtstraat 114, 2517VX Den Haag, The Netherlands
 *
 *
 * ------
 *  Note
 * ------
 *
 * I will assign copyright to PanEris (http://paneris.org) as soon as
 * we have sorted out what sort of legal existence we need to have for
 * that to make sense. 
 * In the meantime, if you want to use Melati on non-GPL terms,
 * contact me!
 */

package org.melati.poem;

import java.sql.*;
import java.util.*;
import org.melati.util.*;

public class PoemTransaction extends Transaction {
  private Database database;
  private Connection connection;

  PoemTransaction(Database database, Connection connection, int index) {
    super(index);
    this.database = database;
    this.connection = connection;
    try {
      connection.setAutoCommit(false);
    }
    catch (SQLException e) {
      throw new SQLSeriousPoemException(e);
    }
  }

  final Database getDatabase() {
    return database;
  }

  final Connection getConnection() {
    return connection;
  }

  protected void backingCommit() {
    try {
      connection.commit();
      if (database.logCommits)
	database.log(new CommitLogEvent(this));
    }
    catch (SQLException e) {
      throw new CommitFailedPoemException(e);
    }
  }

  protected void backingRollback() {
    try {
      connection.rollback();
      if (database.logCommits)
	database.log(new RollbackLogEvent(this));
    }
    catch (SQLException e) {
      throw new RollbackFailedPoemException(e);
    }
  }

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
}
