package org.melati.poem.postgresql.jdbc2;

public class Connection extends org.melati.poem.sql.jdbc2.DelegatedConnection {
  public Connection(java.sql.Connection peer) {
    super(peer);
  }

  public java.sql.DatabaseMetaData getMetaData() throws java.sql.SQLException {
    return new IndexAwareDatabaseMetaData(super.getMetaData());
  }
}
