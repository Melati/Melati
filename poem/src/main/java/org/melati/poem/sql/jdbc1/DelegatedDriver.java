package org.melati.poem.sql.jdbc1;

public class DelegatedDriver implements java.sql.Driver {
  protected java.sql.Driver peer;
  public DelegatedDriver(java.sql.Driver peer) {
    this.peer = peer;
  }
  public java.sql.Connection connect(java.lang.String a, java.util.Properties b) throws java.sql.SQLException {
    return peer.connect(a, b);
  }
  public boolean acceptsURL(java.lang.String a) throws java.sql.SQLException {
    return peer.acceptsURL(a);
  }
  public java.sql.DriverPropertyInfo[] getPropertyInfo(java.lang.String a, java.util.Properties b) throws java.sql.SQLException {
    return peer.getPropertyInfo(a, b);
  }
  public int getMajorVersion() {
    return peer.getMajorVersion();
  }
  public int getMinorVersion() {
    return peer.getMinorVersion();
  }
  public boolean jdbcCompliant() {
    return peer.jdbcCompliant();
  }
}
