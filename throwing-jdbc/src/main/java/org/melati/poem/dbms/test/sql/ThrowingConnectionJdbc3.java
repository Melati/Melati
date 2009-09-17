/*
 * $Source$
 * $Revision$
 *
 * Copyright (C) 2008 Tim Pizey
 *
 * Part of Melati (http://melati.org), a framework for the rapid
 * development of clean, maintainable web applications.
 *
 * Melati is free software; Permission is granted to copy, distribute
 * and/or modify this software under the terms either:
 *
 * a) the GNU General Public License as published by the Free Software
 *    Foundation; either version 2 of the License, or (at your option)
 *    any later version,
 *
 *    or
 *
 * b) any version of the Melati Software License, as published
 *    at http://melati.org
 *
 * You should have received a copy of the GNU General Public License and
 * the Melati Software License along with this program;
 * if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA to obtain the
 * GNU General Public License and visit http://melati.org to obtain the
 * Melati Software License.
 *
 * Feel free to contact the Developers of Melati (http://melati.org),
 * if you would like to work out a different arrangement than the options
 * outlined here.  It is our intention to allow Melati to be used by as
 * wide an audience as possible.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * Contact details for copyright holder:
 *
 *     Tim Pizey <timp At paneris.org>
 *     http://paneris.org/~timp
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
 * A JDBC3 {@link Connection}, decorated to throw an SQLException on command.
 * 
 * @author timp
 * @since  5 Feb 2008
 *
 */
public abstract class ThrowingConnectionJdbc3 
    extends Thrower 
    implements Connection {

  Connection it = null;
  
  
  /**
   * {@inheritDoc}
   * @see java.sql.Connection#clearWarnings()
   */
  public void clearWarnings() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "clearWarnings"))
      throw new SQLException("Connection bombed");
    it.clearWarnings();
  }

  /**
   * {@inheritDoc}
   * @see java.sql.Connection#close()
   */
  public void close() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "close"))
      throw new SQLException("Connection bombed");
    it.close();
  }

  /**
   * {@inheritDoc}
   * @see java.sql.Connection#commit()
   */
  public void commit() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "commit"))
      throw new SQLException("Connection bombed");
    it.commit();
  }

  /**
   * {@inheritDoc}
   * @see java.sql.Connection#createStatement()
   */
  public Statement createStatement() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "createStatement"))
      throw new SQLException("Connection bombed");
    return new ThrowingStatement(it.createStatement());
  }

  /**
   * {@inheritDoc}
   * @see java.sql.Connection#createStatement(int, int)
   */
  public Statement createStatement(int resultSetType, int resultSetConcurrency)
      throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "createStatement"))
      throw new SQLException("Connection bombed");
    return new ThrowingStatement(it.createStatement(resultSetType,resultSetConcurrency));
  }

  /**
   * {@inheritDoc}
   * @see java.sql.Connection#createStatement(int, int, int)
   */
  public Statement createStatement(int resultSetType, int resultSetConcurrency,
      int resultSetHoldability) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "createStatement"))
      throw new SQLException("Connection bombed");
    return new ThrowingStatement(it.createStatement(resultSetType, resultSetConcurrency));
  }

  /**
   * {@inheritDoc}
   * @see java.sql.Connection#getAutoCommit()
   */
  public boolean getAutoCommit() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getAutoCommit"))
      throw new SQLException("Connection bombed");
    return it.getAutoCommit();
  }

  /**
   * {@inheritDoc}
   * @see java.sql.Connection#getCatalog()
   */
  public String getCatalog() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getCatalog"))
      throw new SQLException("Connection bombed");
    return it.getCatalog();
  }

  /**
   * {@inheritDoc}
   * @see java.sql.Connection#getHoldability()
   */
  public int getHoldability() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getHoldability"))
      throw new SQLException("Connection bombed");
    return it.getHoldability();
  }

  /**
   * {@inheritDoc}
   * @see java.sql.Connection#getMetaData()
   */
  public DatabaseMetaData getMetaData() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getMetaData"))
      throw new SQLException("Connection bombed");
    return new ThrowingDatabaseMetaData(it.getMetaData());
  }

  /**
   * {@inheritDoc}
   * @see java.sql.Connection#getTransactionIsolation()
   */
  public int getTransactionIsolation() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getTransactionIsolation"))
      throw new SQLException("Connection bombed");
    return it.getTransactionIsolation();
  }

  /**
   * {@inheritDoc}
   * @see java.sql.Connection#getTypeMap()
   */
  public Map<String,Class<?>> getTypeMap() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getTypeMap()"))
      throw new SQLException("Connection bombed");
    return it.getTypeMap();
  }

  /**
   * {@inheritDoc}
   * @see java.sql.Connection#getWarnings()
   */
  public SQLWarning getWarnings() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getWarnings"))
      throw new SQLException("Connection bombed");
    return it.getWarnings();
  }

  /**
   * {@inheritDoc}
   * @see java.sql.Connection#isClosed()
   */
  public boolean isClosed() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "isClosed"))
      throw new SQLException("Connection bombed");
    return it.isClosed();
  }

  /**
   * {@inheritDoc}
   * @see java.sql.Connection#isReadOnly()
   */
  public boolean isReadOnly() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "isReadOnly"))
      throw new SQLException("Connection bombed");
    return it.isReadOnly();
  }

  /**
   * {@inheritDoc}
   * @see java.sql.Connection#nativeSQL(java.lang.String)
   */
  public String nativeSQL(String sql) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "nativeSQL"))
      throw new SQLException("Connection bombed");
    return it.nativeSQL(sql);
  }

  /**
   * {@inheritDoc}
   * @see java.sql.Connection#prepareCall(java.lang.String)
   */
  public CallableStatement prepareCall(String sql) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "prepareCall"))
      throw new SQLException("Connection bombed");
    return new ThrowingCallableStatement(it.prepareCall(sql));
  }

  /**
   * {@inheritDoc}
   * @see java.sql.Connection#prepareCall(java.lang.String, int, int)
   */
  public CallableStatement prepareCall(String sql, int resultSetType,
      int resultSetConcurrency) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "prepareCall"))
      throw new SQLException("Connection bombed");
    return new ThrowingCallableStatement(it.prepareCall(sql, resultSetType, resultSetConcurrency));
  }

  /**
   * {@inheritDoc}
   * @see java.sql.Connection#prepareCall(java.lang.String, int, int, int)
   */
  public CallableStatement prepareCall(String sql, int resultSetType,
      int resultSetConcurrency, int resultSetHoldability) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "prepareCall"))
      throw new SQLException("Connection bombed");
    return new ThrowingCallableStatement(it.prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability));
  }

  /**
   * {@inheritDoc}
   * @see java.sql.Connection#prepareStatement(java.lang.String)
   */
  public PreparedStatement prepareStatement(String sql) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "prepareStatement"))
      throw new SQLException("Connection bombed");
    return new ThrowingPreparedStatement(it.prepareStatement(sql));
  }

  /**
   * {@inheritDoc}
   * @see java.sql.Connection#prepareStatement(java.lang.String, int)
   */
  public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys)
      throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "prepareStatement"))
      throw new SQLException("Connection bombed");
    return new ThrowingPreparedStatement(it.prepareStatement(sql, autoGeneratedKeys));
  }

  /**
   * {@inheritDoc}
   * @see java.sql.Connection#prepareStatement(java.lang.String, int[])
   */
  public PreparedStatement prepareStatement(String sql, int[] columnIndexes)
      throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "prepareStatement"))
      throw new SQLException("Connection bombed");
    return new ThrowingPreparedStatement(it.prepareStatement(sql, columnIndexes));
  }

  /**
   * {@inheritDoc}
   * @see java.sql.Connection#prepareStatement(java.lang.String, java.lang.String[])
   */
  public PreparedStatement prepareStatement(String sql, String[] columnNames)
      throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "prepareStatement"))
      throw new SQLException("Connection bombed");
    return new ThrowingPreparedStatement(it.prepareStatement(sql, columnNames));
  }

  /**
   * {@inheritDoc}
   * @see java.sql.Connection#prepareStatement(java.lang.String, int, int)
   */
  public PreparedStatement prepareStatement(String sql, int resultSetType,
      int resultSetConcurrency) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "prepareStatement"))
      throw new SQLException("Connection bombed");
    return new ThrowingPreparedStatement(it.prepareStatement(sql, resultSetType, resultSetConcurrency));
  }

  /**
   * {@inheritDoc}
   * @see java.sql.Connection#prepareStatement(java.lang.String, int, int, int)
   */
  public PreparedStatement prepareStatement(String sql, int resultSetType,
      int resultSetConcurrency, int resultSetHoldability) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "prepareStatement"))
      throw new SQLException("Connection bombed");
    return new ThrowingPreparedStatement(it.prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability));
  }

  /**
   * {@inheritDoc}
   * @see java.sql.Connection#releaseSavepoint(java.sql.Savepoint)
   */
  public void releaseSavepoint(Savepoint savepoint) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "releaseSavepoint"))
      throw new SQLException("Connection bombed");
    it.releaseSavepoint(savepoint);
  }

  /**
   * {@inheritDoc}
   * @see java.sql.Connection#rollback()
   */
  public void rollback() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "rollback"))
      throw new SQLException("Connection bombed");
    it.rollback();
  }

  /**
   * {@inheritDoc}
   * @see java.sql.Connection#rollback(java.sql.Savepoint)
   */
  public void rollback(Savepoint savepoint) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "rollback"))
      throw new SQLException("Connection bombed");
    it.rollback(savepoint);
  }

  /**
   * {@inheritDoc}
   * @see java.sql.Connection#setAutoCommit(boolean)
   */
  public void setAutoCommit(boolean autoCommit) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "setAutoCommit"))
      throw new SQLException("Connection bombed");
    it.setAutoCommit(autoCommit);
  }

  /**
   * {@inheritDoc}
   * @see java.sql.Connection#setCatalog(java.lang.String)
   */
  public void setCatalog(String catalog) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "setCatalog"))
      throw new SQLException("Connection bombed");
    it.setCatalog(catalog);
  }

  /**
   * {@inheritDoc}
   * @see java.sql.Connection#setHoldability(int)
   */
  public void setHoldability(int holdability) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "setHoldability"))
      throw new SQLException("Connection bombed");
    it.setHoldability(holdability);
  }

  /**
   * {@inheritDoc}
   * @see java.sql.Connection#setReadOnly(boolean)
   */
  public void setReadOnly(boolean readOnly) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "setReadOnly"))
      throw new SQLException("Connection bombed");
    it.setReadOnly(readOnly);
  }

  /**
   * {@inheritDoc}
   * @see java.sql.Connection#setSavepoint()
   */
  public Savepoint setSavepoint() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "setSavepoint"))
      throw new SQLException("Connection bombed");
    return new ThrowingSavepoint(it.setSavepoint());
  }

  /**
   * {@inheritDoc}
   * @see java.sql.Connection#setSavepoint(java.lang.String)
   */
  public Savepoint setSavepoint(String name) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "setSavepoint"))
      throw new SQLException("Connection bombed");
    return new ThrowingSavepoint(it.setSavepoint(name));
  }

  /**
   * {@inheritDoc}
   * @see java.sql.Connection#setTransactionIsolation(int)
   */
  public void setTransactionIsolation(int level) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "setTransactionIsolation"))
      throw new SQLException("Connection bombed");
    it.setTransactionIsolation(level);
  }

  /**
   * {@inheritDoc}
   * @see java.sql.Connection#setTypeMap(java.util.Map)
   */
  public void setTypeMap(Map<String,Class<?>> map) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "setTypeMap"))
      throw new SQLException("Connection bombed");
    it.setTypeMap(map);
  }
  
  
}
