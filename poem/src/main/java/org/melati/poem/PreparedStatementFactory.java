package org.melati.poem;

import java.util.*;
import java.sql.*;
import org.melati.util.*;

public class PreparedStatementFactory extends CachedIndexFactory {

  private Database database;
  private String sql;

  public PreparedStatementFactory(Database database, String sql) {
    this.database = database;
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

  public PreparedStatement forTransaction(PoemTransaction transaction) {
    return (PreparedStatement)get(transaction == null ?
				    0 : transaction.index + 1);
  }

  public ResultSet resultSet(PoemTransaction transaction) {
    PreparedStatement statement = forTransaction(transaction);
    try {
      if (database.logSQL)
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
