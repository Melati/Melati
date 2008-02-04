/**
 * 
 */
package org.melati.poem.dbms.test.sql;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Savepoint;
import java.sql.Statement;
import java.util.Map;


/**
 * A {@link Connection} decorated to throw an SQLException on command.
 *  
 * @author timp
 * @since 10 Feb 2007
 *
 */
public class ThrowingConnection extends Thrower implements Connection {
  final static String className = ThrowingConnection.class.getName() + ".";
  public static void startThrowing(String methodName) {
    Thrower.startThrowing(className  +  methodName);
  }
  public static void startThrowingAfter(String methodName, int goes) {
    Thrower.startThrowingAfter(className  +  methodName, goes);
  }
  public static void stopThrowing(String methodName) {
    Thrower.stopThrowing(className  +  methodName);
  }
  public static boolean shouldThrow(String methodName) { 
    return Thrower.shouldThrow(className  +  methodName);
  }

  Connection it = null;
  
  /**
   * @param c  a connection.
   * 
   */
  public ThrowingConnection(Connection c) {
    this.it = c;
  }

  
  /**
   * {@inheritDoc}
   * @see java.sql.Connection#clearWarnings()
   */
  public void clearWarnings() throws SQLException {
    if (shouldThrow("clearWarnings"))
      throw new SQLException("Connection bombed");
    it.clearWarnings();
  }

  /**
   * {@inheritDoc}
   * @see java.sql.Connection#close()
   */
  public void close() throws SQLException {
    if (shouldThrow("close"))
      throw new SQLException("Connection bombed");
    it.close();
  }

  /**
   * {@inheritDoc}
   * @see java.sql.Connection#commit()
   */
  public void commit() throws SQLException {
    if (shouldThrow("commit"))
      throw new SQLException("Connection bombed");
    it.commit();
  }

  /**
   * {@inheritDoc}
   * @see java.sql.Connection#createStatement()
   */
  public Statement createStatement() throws SQLException {
    if (shouldThrow("createStatement"))
      throw new SQLException("Connection bombed");
    return new ThrowingStatement(it.createStatement());
  }

  /**
   * {@inheritDoc}
   * @see java.sql.Connection#createStatement(int, int)
   */
  public Statement createStatement(int resultSetType, int resultSetConcurrency)
      throws SQLException {
    if (shouldThrow("createStatement"))
      throw new SQLException("Connection bombed");
    return new ThrowingStatement(it.createStatement(resultSetType,resultSetConcurrency));
  }

  /**
   * {@inheritDoc}
   * @see java.sql.Connection#createStatement(int, int, int)
   */
  public Statement createStatement(int resultSetType, int resultSetConcurrency,
      int resultSetHoldability) throws SQLException {
    if (shouldThrow("createStatement"))
      throw new SQLException("Connection bombed");
    return new ThrowingStatement(it.createStatement(resultSetType, resultSetConcurrency));
  }

  /**
   * {@inheritDoc}
   * @see java.sql.Connection#getAutoCommit()
   */
  public boolean getAutoCommit() throws SQLException {
    if (shouldThrow("getAutoCommit"))
      throw new SQLException("Connection bombed");
    return it.getAutoCommit();
  }

  /**
   * {@inheritDoc}
   * @see java.sql.Connection#getCatalog()
   */
  public String getCatalog() throws SQLException {
    if (shouldThrow("getCatalog"))
      throw new SQLException("Connection bombed");
    return it.getCatalog();
  }

  /**
   * {@inheritDoc}
   * @see java.sql.Connection#getHoldability()
   */
  public int getHoldability() throws SQLException {
    if (shouldThrow("getHoldability"))
      throw new SQLException("Connection bombed");
    return it.getHoldability();
  }

  /**
   * {@inheritDoc}
   * @see java.sql.Connection#getMetaData()
   */
  public DatabaseMetaData getMetaData() throws SQLException {
    if (shouldThrow("getMetaData"))
      throw new SQLException("Connection bombed");
    return new ThrowingDatabaseMetaData(it.getMetaData());
  }

  /**
   * {@inheritDoc}
   * @see java.sql.Connection#getTransactionIsolation()
   */
  public int getTransactionIsolation() throws SQLException {
    if (shouldThrow("getTransactionIsolation"))
      throw new SQLException("Connection bombed");
    return it.getTransactionIsolation();
  }

  /**
   * {@inheritDoc}
   * @see java.sql.Connection#getTypeMap()
   */
  public Map getTypeMap() throws SQLException {
    if (shouldThrow("getTypeMap()"))
      throw new SQLException("Connection bombed");
    return it.getTypeMap();
  }

  /**
   * {@inheritDoc}
   * @see java.sql.Connection#getWarnings()
   */
  public SQLWarning getWarnings() throws SQLException {
    if (shouldThrow("getWarnings"))
      throw new SQLException("Connection bombed");
    return it.getWarnings();
  }

  /**
   * {@inheritDoc}
   * @see java.sql.Connection#isClosed()
   */
  public boolean isClosed() throws SQLException {
    if (shouldThrow("isClosed"))
      throw new SQLException("Connection bombed");
    return it.isClosed();
  }

  /**
   * {@inheritDoc}
   * @see java.sql.Connection#isReadOnly()
   */
  public boolean isReadOnly() throws SQLException {
    if (shouldThrow("isReadOnly"))
      throw new SQLException("Connection bombed");
    return it.isReadOnly();
  }

  /**
   * {@inheritDoc}
   * @see java.sql.Connection#nativeSQL(java.lang.String)
   */
  public String nativeSQL(String sql) throws SQLException {
    if (shouldThrow("nativeSQL"))
      throw new SQLException("Connection bombed");
    return it.nativeSQL(sql);
  }

  /**
   * {@inheritDoc}
   * @see java.sql.Connection#prepareCall(java.lang.String)
   */
  public CallableStatement prepareCall(String sql) throws SQLException {
    if (shouldThrow("prepareCall"))
      throw new SQLException("Connection bombed");
    return new ThrowingCallableStatement(it.prepareCall(sql));
  }

  /**
   * {@inheritDoc}
   * @see java.sql.Connection#prepareCall(java.lang.String, int, int)
   */
  public CallableStatement prepareCall(String sql, int resultSetType,
      int resultSetConcurrency) throws SQLException {
    if (shouldThrow("prepareCall"))
      throw new SQLException("Connection bombed");
    return new ThrowingCallableStatement(it.prepareCall(sql, resultSetType, resultSetConcurrency));
  }

  /**
   * {@inheritDoc}
   * @see java.sql.Connection#prepareCall(java.lang.String, int, int, int)
   */
  public CallableStatement prepareCall(String sql, int resultSetType,
      int resultSetConcurrency, int resultSetHoldability) throws SQLException {
    if (shouldThrow("prepareCall"))
      throw new SQLException("Connection bombed");
    return new ThrowingCallableStatement(it.prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability));
  }

  /**
   * {@inheritDoc}
   * @see java.sql.Connection#prepareStatement(java.lang.String)
   */
  public PreparedStatement prepareStatement(String sql) throws SQLException {
    if (shouldThrow("prepareStatement"))
      throw new SQLException("Connection bombed");
    return new ThrowingPreparedStatement(it.prepareStatement(sql));
  }

  /**
   * {@inheritDoc}
   * @see java.sql.Connection#prepareStatement(java.lang.String, int)
   */
  public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys)
      throws SQLException {
    if (shouldThrow("prepareStatement"))
      throw new SQLException("Connection bombed");
    return new ThrowingPreparedStatement(it.prepareStatement(sql, autoGeneratedKeys));
  }

  /**
   * {@inheritDoc}
   * @see java.sql.Connection#prepareStatement(java.lang.String, int[])
   */
  public PreparedStatement prepareStatement(String sql, int[] columnIndexes)
      throws SQLException {
    if (shouldThrow("prepareStatement"))
      throw new SQLException("Connection bombed");
    return new ThrowingPreparedStatement(it.prepareStatement(sql, columnIndexes));
  }

  /**
   * {@inheritDoc}
   * @see java.sql.Connection#prepareStatement(java.lang.String, java.lang.String[])
   */
  public PreparedStatement prepareStatement(String sql, String[] columnNames)
      throws SQLException {
    if (shouldThrow("prepareStatement"))
      throw new SQLException("Connection bombed");
    return new ThrowingPreparedStatement(it.prepareStatement(sql, columnNames));
  }

  /**
   * {@inheritDoc}
   * @see java.sql.Connection#prepareStatement(java.lang.String, int, int)
   */
  public PreparedStatement prepareStatement(String sql, int resultSetType,
      int resultSetConcurrency) throws SQLException {
    if (shouldThrow("prepareStatement"))
      throw new SQLException("Connection bombed");
    return new ThrowingPreparedStatement(it.prepareStatement(sql, resultSetType, resultSetConcurrency));
  }

  /**
   * {@inheritDoc}
   * @see java.sql.Connection#prepareStatement(java.lang.String, int, int, int)
   */
  public PreparedStatement prepareStatement(String sql, int resultSetType,
      int resultSetConcurrency, int resultSetHoldability) throws SQLException {
    if (shouldThrow("prepareStatement"))
      throw new SQLException("Connection bombed");
    return new ThrowingPreparedStatement(it.prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability));
  }

  /**
   * {@inheritDoc}
   * @see java.sql.Connection#releaseSavepoint(java.sql.Savepoint)
   */
  public void releaseSavepoint(Savepoint savepoint) throws SQLException {
    if (shouldThrow("releaseSavepoint"))
      throw new SQLException("Connection bombed");
    it.releaseSavepoint(savepoint);
  }

  /**
   * {@inheritDoc}
   * @see java.sql.Connection#rollback()
   */
  public void rollback() throws SQLException {
    if (shouldThrow("rollback"))
      throw new SQLException("Connection bombed");
    it.rollback();
  }

  /**
   * {@inheritDoc}
   * @see java.sql.Connection#rollback(java.sql.Savepoint)
   */
  public void rollback(Savepoint savepoint) throws SQLException {
    if (shouldThrow("rollback"))
      throw new SQLException("Connection bombed");
    it.rollback(savepoint);
  }

  /**
   * {@inheritDoc}
   * @see java.sql.Connection#setAutoCommit(boolean)
   */
  public void setAutoCommit(boolean autoCommit) throws SQLException {
    if (shouldThrow("setAutoCommit"))
      throw new SQLException("Connection bombed");
    it.setAutoCommit(autoCommit);
  }

  /**
   * {@inheritDoc}
   * @see java.sql.Connection#setCatalog(java.lang.String)
   */
  public void setCatalog(String catalog) throws SQLException {
    if (shouldThrow("setCatalog"))
      throw new SQLException("Connection bombed");
    it.setCatalog(catalog);
  }

  /**
   * {@inheritDoc}
   * @see java.sql.Connection#setHoldability(int)
   */
  public void setHoldability(int holdability) throws SQLException {
    if (shouldThrow("setHoldability"))
      throw new SQLException("Connection bombed");
    it.setHoldability(holdability);
  }

  /**
   * {@inheritDoc}
   * @see java.sql.Connection#setReadOnly(boolean)
   */
  public void setReadOnly(boolean readOnly) throws SQLException {
    if (shouldThrow("setReadOnly"))
      throw new SQLException("Connection bombed");
    it.setReadOnly(readOnly);
  }

  /**
   * {@inheritDoc}
   * @see java.sql.Connection#setSavepoint()
   */
  public Savepoint setSavepoint() throws SQLException {
    if (shouldThrow("setSavepoint"))
      throw new SQLException("Connection bombed");
    return new ThrowingSavepoint(it.setSavepoint());
  }

  /**
   * {@inheritDoc}
   * @see java.sql.Connection#setSavepoint(java.lang.String)
   */
  public Savepoint setSavepoint(String name) throws SQLException {
    if (shouldThrow("setSavepoint"))
      throw new SQLException("Connection bombed");
    return new ThrowingSavepoint(it.setSavepoint(name));
  }

  /**
   * {@inheritDoc}
   * @see java.sql.Connection#setTransactionIsolation(int)
   */
  public void setTransactionIsolation(int level) throws SQLException {
    if (shouldThrow("setTransactionIsolation"))
      throw new SQLException("Connection bombed");
    it.setTransactionIsolation(level);
  }

  /**
   * {@inheritDoc}
   * @see java.sql.Connection#setTypeMap(java.util.Map)
   */
  public void setTypeMap(Map map) throws SQLException {
    if (shouldThrow("setTypeMap"))
      throw new SQLException("Connection bombed");
    it.setTypeMap(map);
  }
  
  /**
   * Dummy 1.6
   */
  public java.sql.Struct createStruct(String s, Object[] them) {
    String s2 = s;
    s = s2;
    Object[] them2 = them;
    them = them2;
    return null;
  }

}
