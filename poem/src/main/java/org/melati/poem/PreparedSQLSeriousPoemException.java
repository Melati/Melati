package org.melati.poem;

import java.sql.*;

public class PreparedSQLSeriousPoemException extends SQLSeriousPoemException {
  public PreparedStatement statement;
  public PreparedSQLSeriousPoemException(PreparedStatement statement,
                                         SQLException e) {
    super(e);
    this.statement = statement;
  }

  public String getMessage() {
    return
        "Executing prepared SQL ...\n" + statement + "\n" + super.getMessage();
  }
}
