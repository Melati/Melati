package org.melati.poem;

public class CommitFailedPoemException extends SQLSeriousPoemException {
  public CommitFailedPoemException(java.sql.SQLException e) {
    super(e);
  }
}
