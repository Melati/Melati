package org.melati.poem;

import java.sql.*;

public class ExecutingSQLSeriousPoemException extends SQLSeriousPoemException {
  public String sql;
  public ExecutingSQLSeriousPoemException(String sql, SQLException e) {
    super(e);
    this.sql = sql;
  }

  public String getMessage() {
    return
        "Executing SQL ...\n" + sql + "\n" + super.getMessage();
  }
}
