package org.melati.poem;

public class SimpleRetrievalFailedPoemException
    extends SQLSeriousPoemException {
  public SimpleRetrievalFailedPoemException(java.sql.SQLException e) {
    super(e);
  }
}
