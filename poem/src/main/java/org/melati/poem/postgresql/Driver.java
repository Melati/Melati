package org.melati.poem.postgresql;

public class Driver extends org.melati.poem.sql.DelegatedDriver {
  public Driver() throws java.sql.SQLException {
    super(new postgresql.Driver());
  }

  public java.sql.Connection connect(String url, java.util.Properties info)
      throws java.sql.SQLException {
    return new Connection(super.connect(url, info));
  }
}
