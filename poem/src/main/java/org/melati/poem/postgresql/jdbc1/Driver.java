package org.melati.poem.postgresql.jdbc1;

public class Driver extends org.melati.poem.sql.jdbc1.DelegatedDriver {
  public Driver() throws java.sql.SQLException {
    super(new postgresql.Driver());
  }

  public java.sql.Connection connect(String url, java.util.Properties info)
      throws java.sql.SQLException {
    return new Connection((postgresql.jdbc1.Connection)super.connect(url, info));
  }
}
