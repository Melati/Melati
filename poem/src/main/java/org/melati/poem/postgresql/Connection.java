package org.melati.poem.postgresql;

public class Connection extends org.melati.poem.sql.DelegatedConnection {
  public Connection(java.sql.Connection peer) {
    super(peer);
  }

  public java.sql.DatabaseMetaData getMetaData() throws java.sql.SQLException {
    return new IndexAwareDatabaseMetaData(super.getMetaData());
  }
}
