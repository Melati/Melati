package org.melati.poem;

import java.sql.*;

public class SimplePrepareFailedPoemException
    extends ExecutingSQLSeriousPoemException {

  public SimplePrepareFailedPoemException(String sql, SQLException e) {
    super(sql, e);
  }
}
