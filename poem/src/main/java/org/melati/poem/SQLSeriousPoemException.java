package org.melati.poem;

import java.sql.SQLException;

public class SQLSeriousPoemException extends SeriousPoemException {
  public String sql;

  public SQLSeriousPoemException(SQLException sqlException, String sql) {
    super(sqlException);
  }

  public SQLSeriousPoemException(SQLException sqlException) {
    this(sqlException, null);
  }

  public String getMessage() {
    return
        "An error was reported by the SQL driver:\n" +
        subException.getMessage();
  }
}
