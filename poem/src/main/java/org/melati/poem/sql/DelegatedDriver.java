package org.melati.poem.sql;

import java.sql.*;

public class DelegatedDriver implements Driver {
  protected Driver peer;
  public DelegatedDriver(Driver peer) {
    this.peer = peer;
  }
  public boolean acceptsURL(java.lang.String a) throws java.sql.SQLException {
    return peer.acceptsURL(a);
  }
  public java.sql.Connection connect(java.lang.String a, java.util.Properties b) throws java.sql.SQLException {
    return peer.connect(a, b);
  }
  public int getMajorVersion() {
    return peer.getMajorVersion();
  }
  public int getMinorVersion() {
    return peer.getMinorVersion();
  }
  public java.sql.DriverPropertyInfo[] getPropertyInfo(java.lang.String a, java.util.Properties b) throws java.sql.SQLException {
    return peer.getPropertyInfo(a, b);
  }
  public boolean jdbcCompliant() {
    return peer.jdbcCompliant();
  }
}
