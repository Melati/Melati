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
 *     William Chesters <williamc@paneris.org>
 *     http://paneris.org/~williamc
 *     Obrechtstraat 114, 2517VX Den Haag, The Netherlands
 */

package org.melati.poem;

import java.util.*;
import java.sql.*;
import org.melati.util.*;

/**
 * Maintains a cache of <code>PreparedStatement</code>s for an SQL
 * statement string and for each connection, allowing it to be
 * properly executed.
 * <p>
 * The cached contents are discarded if the database structure has
 * changed.
 * <p>
 * Execution of the statement in a transaction reflects uncommitted
 * changes in that transaction.
 * <p>
 * The supertype dictates that connections can be indentified
 * by index, but this is slightly complicated and the additional
 * methods rely on transactions instead.
 * <p>
 * (Please review this description and delete this line. JimW.)
 *
 * @author williamc@paneris.org (not javadocs)
 */
public class PreparedStatementFactory extends CachedIndexFactory {

  private Database database;
  private long structureSerial;
  private String sql;

  public PreparedStatementFactory(Database database, String sql) {
    this.database = database;
    this.structureSerial = database.structureSerial();
    this.sql = sql;
  }

  /**
   * Obtain a fresh <code>PreparedStatement</code> for a connection
   * identified by an index.
   * <p>
   * The index is zero for the commited connection and the
   * transaction index plus 1 for current transactions.
   * <p>
   * (Please review this description and delete this line. JimW.)
   */
  protected Object reallyGet(int index) {
    try {
      Connection c =
	index == 0 ? database.getCommittedConnection()
	           : database.poemTransaction(index - 1).getConnection();
      return c.prepareStatement(sql);
    }
    catch (SQLException e) {
      throw new SQLPoemException(e);
    }
  }

  public Object get(int index) {
    if (structureSerial != database.structureSerial()) {
      invalidate();
      structureSerial = database.structureSerial();
    }

    return super.get(index);
  }

  public PreparedStatement preparedStatement(PoemTransaction transaction) {
    return (PreparedStatement)get(transaction == null ?
				    0 : transaction.index + 1);
  }

  public final PreparedStatement preparedStatement() {
    return preparedStatement(PoemThread.transaction());
  }

  protected ResultSet resultSet(SessionToken token,
                                PreparedStatement statement) {
    try {
      if (database.logSQL())
        database.log(new SQLLogEvent(statement.toString()));
      
      token.transaction.writeDown();
      ResultSet rs = statement.executeQuery();
      token.toTidy().add(rs);
      return rs;
    }
    catch (SQLException e) {
      throw new PreparedSQLSeriousPoemException(statement, e);
    }
  }

  protected final ResultSet resultSet(SessionToken token) {
    return resultSet(token, preparedStatement(token.transaction));
  }

  public final ResultSet resultSet() {
    return resultSet(PoemThread.sessionToken());
  }
}
