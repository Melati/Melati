package org.melati.poem;

import java.sql.*;

public class ExecutingSQLPoemException extends SQLPoemException {
  public String sql;
  public ExecutingSQLPoemException(String sql, SQLException e) {
    super(e);
    this.sql = sql;
  }

  public String getMessage() {
    return
        "Executing SQL ...\n" + sql + "\n" + super.getMessage();
  }
}
