/*
 * $Source$
 * $Revision$
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

public class PreparedStatementFactory extends CachedIndexFactory {

  private Database database;
  private long structureSerial;
  private String sql;

  public PreparedStatementFactory(Database database, String sql) {
    this.database = database;
    this.structureSerial = database.structureSerial();
    this.sql = sql;
  }

  // HACK we use 0 to mean "committed transaction", i + 1 to mean "noncommitted
  // transaction i"

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

  public PreparedStatement forTransaction(PoemTransaction transaction) {
    return (PreparedStatement)get(transaction == null ?
				    0 : transaction.index + 1);
  }

  public ResultSet resultSet(PoemTransaction transaction) {
    PreparedStatement statement = forTransaction(transaction);
    try {
      if (database.logSQL())
	database.log(new SQLLogEvent(statement.toString()));
      return statement.executeQuery();
    }
    catch (SQLException e) {
      throw new PreparedSQLSeriousPoemException(statement, e);
    }
  }

  public ResultSet resultSet() {
    return resultSet(PoemThread.transaction());
  }
}
