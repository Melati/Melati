package org.melati.poem;

import java.sql.*;

public class StructuralModificationFailedPoemException
    extends ExecutingSQLSeriousPoemException {

  public StructuralModificationFailedPoemException(String sql,
                                                   SQLException e) {
    super(sql, e);
  }

  public String getMessage() {
    return "Structural modification of database failed\n" + super.getMessage();
  }
}
