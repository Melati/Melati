package org.melati.poem.postgresql.jdbc1;

public class Connection extends org.melati.poem.sql.jdbc1.DelegatedConnection {
  public Connection(postgresql.jdbc1.Connection peer) {
    super(peer);
  }

  public java.sql.DatabaseMetaData getMetaData() throws java.sql.SQLException {
    return new IndexAwareDatabaseMetaData((postgresql.jdbc1.Connection)peer);
  }
}
