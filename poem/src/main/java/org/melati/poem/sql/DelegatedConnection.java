package org.melati.poem.sql;

import java.sql.*;

public class DelegatedConnection implements Connection {
  protected Connection peer;
  public DelegatedConnection(Connection peer) {
    this.peer = peer;
  }
  public void clearWarnings() throws java.sql.SQLException {
    peer.clearWarnings();
  }
  public void close() throws java.sql.SQLException {
    peer.close();
  }
  public void commit() throws java.sql.SQLException {
    peer.commit();
  }
  public java.sql.Statement createStatement() throws java.sql.SQLException {
    return peer.createStatement();
  }
  public java.sql.Statement createStatement(int a, int b) throws java.sql.SQLException {
    return peer.createStatement(a, b);
  }
  public boolean getAutoCommit() throws java.sql.SQLException {
    return peer.getAutoCommit();
  }
  public java.lang.String getCatalog() throws java.sql.SQLException {
    return peer.getCatalog();
  }
  public java.sql.DatabaseMetaData getMetaData() throws java.sql.SQLException {
    return peer.getMetaData();
  }
  public int getTransactionIsolation() throws java.sql.SQLException {
    return peer.getTransactionIsolation();
  }
  public java.util.Map getTypeMap() throws java.sql.SQLException {
    return peer.getTypeMap();
  }
  public java.sql.SQLWarning getWarnings() throws java.sql.SQLException {
    return peer.getWarnings();
  }
  public boolean isClosed() throws java.sql.SQLException {
    return peer.isClosed();
  }
  public boolean isReadOnly() throws java.sql.SQLException {
    return peer.isReadOnly();
  }
  public java.lang.String nativeSQL(java.lang.String a) throws java.sql.SQLException {
    return peer.nativeSQL(a);
  }
  public java.sql.CallableStatement prepareCall(java.lang.String a) throws java.sql.SQLException {
    return peer.prepareCall(a);
  }
  public java.sql.CallableStatement prepareCall(java.lang.String a, int b, int c) throws java.sql.SQLException {
    return peer.prepareCall(a, b, c);
  }
  public java.sql.PreparedStatement prepareStatement(java.lang.String a) throws java.sql.SQLException {
    return peer.prepareStatement(a);
  }
  public java.sql.PreparedStatement prepareStatement(java.lang.String a, int b, int c) throws java.sql.SQLException {
    return peer.prepareStatement(a, b, c);
  }
  public void rollback() throws java.sql.SQLException {
    peer.rollback();
  }
  public void setAutoCommit(boolean a) throws java.sql.SQLException {
    peer.setAutoCommit(a);
  }
  public void setCatalog(java.lang.String a) throws java.sql.SQLException {
    peer.setCatalog(a);
  }
  public void setReadOnly(boolean a) throws java.sql.SQLException {
    peer.setReadOnly(a);
  }
  public void setTransactionIsolation(int a) throws java.sql.SQLException {
    peer.setTransactionIsolation(a);
  }
  public void setTypeMap(java.util.Map a) throws java.sql.SQLException {
    peer.setTypeMap(a);
  }
}
