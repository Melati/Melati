package org.melati.poem;

import java.sql.*;

public class ConnectionFailurePoemException extends SQLPoemException {
  public ConnectionFailurePoemException(SQLException e) {
    super(e);
  }

  public String getMessage() {
    return "Initialising\n" + super.getMessage();
  }
}
