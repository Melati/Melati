/**
 * 
 */
package org.melati.poem.dbms.test;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Savepoint;
import java.sql.Statement;
import java.util.Hashtable;
import java.util.Map;

/**
 * A {@link Connection} decorated to throw an SQLException on command.
 *  
 * @author timp
 * @since 10 Feb 2007
 *
 */
public class ThrowingConnection implements Connection {
  static Hashtable throwers = new Hashtable();
  public static void startThrowing(String methodName) {
    throwers.put(methodName, Boolean.TRUE);
  }
  public static void stopThrowing(String methodName) {
    throwers.put(methodName, Boolean.FALSE);
  }
  public static boolean shouldThrow(String methodName) { 
    if (throwers.get(methodName) == null || throwers.get(methodName) == Boolean.FALSE)
      return false;
    return true;
  }

  Connection c = null;
  
  /**
   * @param c  a connection.
   * 
   */
  public ThrowingConnection(Connection c) {
    this.c = c;
  }

  
  /**
   * {@inheritDoc}
   * @see java.sql.Connection#clearWarnings()
   */
  public void clearWarnings() throws SQLException {
    if (shouldThrow("clearWarnings"))
      throw new SQLException("Connection bombed");
    c.clearWarnings();
  }

  /**
   * {@inheritDoc}
   * @see java.sql.Connection#close()
   */
  public void close() throws SQLException {
    if (shouldThrow("close"))
      throw new SQLException("Connection bombed");
    c.close();
  }

  /**
   * {@inheritDoc}
   * @see java.sql.Connection#commit()
   */
  public void commit() throws SQLException {
    if (shouldThrow("commit"))
      throw new SQLException("Connection bombed");
    c.commit();
  }

  /**
   * {@inheritDoc}
   * @see java.sql.Connection#createStatement()
   */
  public Statement createStatement() throws SQLException {
    if (shouldThrow("createStatement"))
      throw new SQLException("Connection bombed");
    return new ThrowingStatement(c.createStatement());
  }

  /**
   * {@inheritDoc}
   * @see java.sql.Connection#createStatement(int, int)
   */
  public Statement createStatement(int resultSetType, int resultSetConcurrency)
      throws SQLException {
    if (shouldThrow("createStatement"))
      throw new SQLException("Connection bombed");
    return new ThrowingStatement(c.createStatement(resultSetType,resultSetConcurrency));
  }

  /**
   * {@inheritDoc}
   * @see java.sql.Connection#createStatement(int, int, int)
   */
  public Statement createStatement(int resultSetType, int resultSetConcurrency,
      int resultSetHoldability) throws SQLException {
    if (shouldThrow("createStatement"))
      throw new SQLException("Connection bombed");
    return new ThrowingStatement(c.createStatement(resultSetType, resultSetConcurrency));
  }

  /**
   * {@inheritDoc}
   * @see java.sql.Connection#getAutoCommit()
   */
  public boolean getAutoCommit() throws SQLException {
    if (shouldThrow("getAutoCommit"))
      throw new SQLException("Connection bombed");
    return c.getAutoCommit();
  }

  /**
   * {@inheritDoc}
   * @see java.sql.Connection#getCatalog()
   */
  public String getCatalog() throws SQLException {
    if (shouldThrow("getCatalog"))
      throw new SQLException("Connection bombed");
    return c.getCatalog();
  }

  /**
   * {@inheritDoc}
   * @see java.sql.Connection#getHoldability()
   */
  public int getHoldability() throws SQLException {
    if (shouldThrow("getHoldability"))
      throw new SQLException("Connection bombed");
    return c.getHoldability();
  }

  /**
   * @todo
   * {@inheritDoc}
   * @see java.sql.Connection#getMetaData()
   */
  public DatabaseMetaData getMetaData() throws SQLException {
    if (shouldThrow("getMetaData"))
      throw new SQLException("Connection bombed");
    return c.getMetaData();
  }

  /**
   * {@inheritDoc}
   * @see java.sql.Connection#getTransactionIsolation()
   */
  public int getTransactionIsolation() throws SQLException {
    if (shouldThrow("getTransactionIsolation"))
      throw new SQLException("Connection bombed");
    return c.getTransactionIsolation();
  }

  /**
   * {@inheritDoc}
   * @see java.sql.Connection#getTypeMap()
   */
  public Map getTypeMap() throws SQLException {
    if (shouldThrow("getTypeMap()"))
      throw new SQLException("Connection bombed");
    return c.getTypeMap();
  }

  /**
   * {@inheritDoc}
   * @see java.sql.Connection#getWarnings()
   */
  public SQLWarning getWarnings() throws SQLException {
    if (shouldThrow("getWarnings"))
      throw new SQLException("Connection bombed");
    return c.getWarnings();
  }

  /**
   * {@inheritDoc}
   * @see java.sql.Connection#isClosed()
   */
  public boolean isClosed() throws SQLException {
    if (shouldThrow("isClosed"))
      throw new SQLException("Connection bombed");
    return c.isClosed();
  }

  /**
   * {@inheritDoc}
   * @see java.sql.Connection#isReadOnly()
   */
  public boolean isReadOnly() throws SQLException {
    if (shouldThrow("isReadOnly"))
      throw new SQLException("Connection bombed");
    return c.isReadOnly();
  }

  /**
   * {@inheritDoc}
   * @see java.sql.Connection#nativeSQL(java.lang.String)
   */
  public String nativeSQL(String sql) throws SQLException {
    if (shouldThrow("nativeSQL"))
      throw new SQLException("Connection bombed");
    return c.nativeSQL(sql);
  }

  /**
   * @todo
   * {@inheritDoc}
   * @see java.sql.Connection#prepareCall(java.lang.String)
   */
  public CallableStatement prepareCall(String sql) throws SQLException {
    if (shouldThrow("prepareCall"))
      throw new SQLException("Connection bombed");
    return c.prepareCall(sql);
  }

  /**
   * {@inheritDoc}
   * @see java.sql.Connection#prepareCall(java.lang.String, int, int)
   */
  public CallableStatement prepareCall(String sql, int resultSetType,
      int resultSetConcurrency) throws SQLException {
    if (shouldThrow("prepareCall"))
      throw new SQLException("Connection bombed");
    return c.prepareCall(sql, resultSetType, resultSetConcurrency);
  }

  /**
   * {@inheritDoc}
   * @see java.sql.Connection#prepareCall(java.lang.String, int, int, int)
   */
  public CallableStatement prepareCall(String sql, int resultSetType,
      int resultSetConcurrency, int resultSetHoldability) throws SQLException {
    if (shouldThrow("prepareCall"))
      throw new SQLException("Connection bombed");
    return c.prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
  }

  /**
   * {@inheritDoc}
   * @see java.sql.Connection#prepareStatement(java.lang.String)
   */
  public PreparedStatement prepareStatement(String sql) throws SQLException {
    if (shouldThrow("prepareStatement"))
      throw new SQLException("Connection bombed");
    return new ThrowingPreparedStatement(c.prepareStatement(sql));
  }

  /**
   * {@inheritDoc}
   * @see java.sql.Connection#prepareStatement(java.lang.String, int)
   */
  public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys)
      throws SQLException {
    if (shouldThrow("prepareStatement"))
      throw new SQLException("Connection bombed");
    return new ThrowingPreparedStatement(c.prepareStatement(sql, autoGeneratedKeys));
  }

  /**
   * {@inheritDoc}
   * @see java.sql.Connection#prepareStatement(java.lang.String, int[])
   */
  public PreparedStatement prepareStatement(String sql, int[] columnIndexes)
      throws SQLException {
    if (shouldThrow("prepareStatement"))
      throw new SQLException("Connection bombed");
    return c.prepareStatement(sql, columnIndexes);
  }

  /**
   * {@inheritDoc}
   * @see java.sql.Connection#prepareStatement(java.lang.String, java.lang.String[])
   */
  public PreparedStatement prepareStatement(String sql, String[] columnNames)
      throws SQLException {
    if (shouldThrow("prepareStatement"))
      throw new SQLException("Connection bombed");
    return c.prepareStatement(sql, columnNames);
  }

  /**
   * {@inheritDoc}
   * @see java.sql.Connection#prepareStatement(java.lang.String, int, int)
   */
  public PreparedStatement prepareStatement(String sql, int resultSetType,
      int resultSetConcurrency) throws SQLException {
    if (shouldThrow("prepareStatement"))
      throw new SQLException("Connection bombed");
    return c.prepareStatement(sql, resultSetType, resultSetConcurrency);
  }

  /**
   * {@inheritDoc}
   * @see java.sql.Connection#prepareStatement(java.lang.String, int, int, int)
   */
  public PreparedStatement prepareStatement(String sql, int resultSetType,
      int resultSetConcurrency, int resultSetHoldability) throws SQLException {
    if (shouldThrow("prepareStatement"))
      throw new SQLException("Connection bombed");
    return c.prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
  }

  /**
   * {@inheritDoc}
   * @see java.sql.Connection#releaseSavepoint(java.sql.Savepoint)
   */
  public void releaseSavepoint(Savepoint savepoint) throws SQLException {
    if (shouldThrow("releaseSavepoint"))
      throw new SQLException("Connection bombed");
    c.releaseSavepoint(savepoint);
  }

  /**
   * {@inheritDoc}
   * @see java.sql.Connection#rollback()
   */
  public void rollback() throws SQLException {
    if (shouldThrow("rollback"))
      throw new SQLException("Connection bombed");
    c.rollback();
  }

  /**
   * {@inheritDoc}
   * @see java.sql.Connection#rollback(java.sql.Savepoint)
   */
  public void rollback(Savepoint savepoint) throws SQLException {
    if (shouldThrow("rollback"))
      throw new SQLException("Connection bombed");
    c.rollback(savepoint);
  }

  /**
   * {@inheritDoc}
   * @see java.sql.Connection#setAutoCommit(boolean)
   */
  public void setAutoCommit(boolean autoCommit) throws SQLException {
    if (shouldThrow("setAutoCommit"))
      throw new SQLException("Connection bombed");
    c.setAutoCommit(autoCommit);
  }

  /**
   * {@inheritDoc}
   * @see java.sql.Connection#setCatalog(java.lang.String)
   */
  public void setCatalog(String catalog) throws SQLException {
    if (shouldThrow("setCatalog"))
      throw new SQLException("Connection bombed");
    c.setCatalog(catalog);
  }

  /**
   * {@inheritDoc}
   * @see java.sql.Connection#setHoldability(int)
   */
  public void setHoldability(int holdability) throws SQLException {
    if (shouldThrow("setHoldability"))
      throw new SQLException("Connection bombed");
    c.setHoldability(holdability);
  }

  /**
   * {@inheritDoc}
   * @see java.sql.Connection#setReadOnly(boolean)
   */
  public void setReadOnly(boolean readOnly) throws SQLException {
    if (shouldThrow("setReadOnly"))
      throw new SQLException("Connection bombed");
    c.setReadOnly(readOnly);
  }

  /**
   * {@inheritDoc}
   * @see java.sql.Connection#setSavepoint()
   */
  public Savepoint setSavepoint() throws SQLException {
    if (shouldThrow("setSavepoint"))
      throw new SQLException("Connection bombed");
    return c.setSavepoint();
  }

  /**
   * {@inheritDoc}
   * @see java.sql.Connection#setSavepoint(java.lang.String)
   */
  public Savepoint setSavepoint(String name) throws SQLException {
    if (shouldThrow("setSavepoint"))
      throw new SQLException("Connection bombed");
    return c.setSavepoint(name);
  }

  /**
   * {@inheritDoc}
   * @see java.sql.Connection#setTransactionIsolation(int)
   */
  public void setTransactionIsolation(int level) throws SQLException {
    if (shouldThrow("setTransactionIsolation"))
      throw new SQLException("Connection bombed");
    c.setTransactionIsolation(level);
  }

  /**
   * {@inheritDoc}
   * @see java.sql.Connection#setTypeMap(java.util.Map)
   */
  public void setTypeMap(Map map) throws SQLException {
    if (shouldThrow("setTypeMap"))
      throw new SQLException("Connection bombed");
    c.setTypeMap(map);
  }

}
