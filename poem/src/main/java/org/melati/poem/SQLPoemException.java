package org.melati.poem;

import java.sql.SQLException;

public class SQLPoemException extends NormalPoemException {
  public SQLPoemException(SQLException sqlException) {
    super(sqlException);
  }

  public String getMessage() {
    return "Error reported by jdbc driver\n" + subException.getMessage();
  }
}
