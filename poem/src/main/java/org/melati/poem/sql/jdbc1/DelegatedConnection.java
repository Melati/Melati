package org.melati.poem.sql.jdbc1;

public class DelegatedConnection implements java.sql.Connection {
  protected java.sql.Connection peer;
  public DelegatedConnection(java.sql.Connection peer) {
    this.peer = peer;
  }
  public java.sql.Statement createStatement() throws java.sql.SQLException {
    return peer.createStatement();
  }
  public java.sql.PreparedStatement prepareStatement(java.lang.String a) throws java.sql.SQLException {
    return peer.prepareStatement(a);
  }
  public java.sql.CallableStatement prepareCall(java.lang.String a) throws java.sql.SQLException {
    return peer.prepareCall(a);
  }
  public java.lang.String nativeSQL(java.lang.String a) throws java.sql.SQLException {
    return peer.nativeSQL(a);
  }
  public void setAutoCommit(boolean a) throws java.sql.SQLException {
    peer.setAutoCommit(a);
  }
  public boolean getAutoCommit() throws java.sql.SQLException {
    return peer.getAutoCommit();
  }
  public void commit() throws java.sql.SQLException {
    peer.commit();
  }
  public void rollback() throws java.sql.SQLException {
    peer.rollback();
  }
  public void close() throws java.sql.SQLException {
    peer.close();
  }
  public boolean isClosed() throws java.sql.SQLException {
    return peer.isClosed();
  }
  public java.sql.DatabaseMetaData getMetaData() throws java.sql.SQLException {
    return peer.getMetaData();
  }
  public void setReadOnly(boolean a) throws java.sql.SQLException {
    peer.setReadOnly(a);
  }
  public boolean isReadOnly() throws java.sql.SQLException {
    return peer.isReadOnly();
  }
  public void setCatalog(java.lang.String a) throws java.sql.SQLException {
    peer.setCatalog(a);
  }
  public java.lang.String getCatalog() throws java.sql.SQLException {
    return peer.getCatalog();
  }
  public void setTransactionIsolation(int a) throws java.sql.SQLException {
    peer.setTransactionIsolation(a);
  }
  public int getTransactionIsolation() throws java.sql.SQLException {
    return peer.getTransactionIsolation();
  }
  public java.sql.SQLWarning getWarnings() throws java.sql.SQLException {
    return peer.getWarnings();
  }
  public void clearWarnings() throws java.sql.SQLException {
    peer.clearWarnings();
  }
}
