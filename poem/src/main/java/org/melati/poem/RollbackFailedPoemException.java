package org.melati.poem;

public class RollbackFailedPoemException extends SQLSeriousPoemException {
  public RollbackFailedPoemException(java.sql.SQLException e) {
    super(e);
  }
}
