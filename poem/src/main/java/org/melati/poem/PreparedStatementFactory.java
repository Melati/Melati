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

  // HACK we use 0 to mean "committed session", i + 1 to mean "noncommitted
  // session i"

  protected Object reallyGet(int index) {
    try {
      Connection c =
	index == 0 ? database.getCommittedConnection()
	: database.session(index - 1).getConnection();
      return c.prepareStatement(sql);
    }
    catch (SQLException e) {
      throw new SQLPoemException(e);
    }
  }

  public PreparedStatement forSession(Session session) {
    return (PreparedStatement)get(session == null ? 0 : session.index() + 1);
  }

  public ResultSet resultSet() {
    PreparedStatement statement = forSession(PoemThread.session());
    try {
      if (database.logSQL)
	database.log(new SQLLogEvent(statement.toString()));
      return statement.executeQuery();
    }
    catch (SQLException e) {
      throw new PreparedSQLSeriousPoemException(statement, e);
    }
  }
}
