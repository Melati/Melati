package org.melati.poem;

import java.sql.*;

public class DatabaseMetaDataFailurePoemException
    extends SQLSeriousPoemException {

  public DatabaseMetaDataFailurePoemException(SQLException e) {
    super(e);
  }
}
