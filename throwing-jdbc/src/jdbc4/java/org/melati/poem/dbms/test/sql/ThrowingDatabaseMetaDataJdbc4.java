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

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.RowIdLifetime;
import java.sql.SQLException;

/**
 * @author timp
 * @since  5 Feb 2008
 *
 */
public abstract class ThrowingDatabaseMetaDataJdbc4 
    extends ThrowingDatabaseMetaDataJdbc3 
    implements DatabaseMetaData {
  
  /**
   *  JDBC 4.0
   */
  
 
  /** 
   * {@inheritDoc}
   * @see java.sql.DatabaseMetaData#autoCommitFailureClosesAllResultSets()
   */
  public boolean autoCommitFailureClosesAllResultSets() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "autoCommitFailureClosesAllResultSets"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.autoCommitFailureClosesAllResultSets();
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.DatabaseMetaData#getClientInfoProperties()
   */
  public ResultSet getClientInfoProperties() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getClientInfoProperties"))
      throw new SQLException("DatabaseMetaData bombed");
    return new ThrowingResultSet(it.getClientInfoProperties());
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.DatabaseMetaData#getFunctionColumns(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
   */
  public ResultSet getFunctionColumns(String catalog, String schemaPattern,
          String functionNamePattern, String columnNamePattern)
          throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getFunctionColumns"))
      throw new SQLException("DatabaseMetaData bombed");
    return  new ThrowingResultSet(it.getFunctionColumns(catalog, schemaPattern, functionNamePattern, catalog));
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.DatabaseMetaData#getFunctions(java.lang.String, java.lang.String, java.lang.String)
   */
  public ResultSet getFunctions(String catalog, String schemaPattern,
          String functionNamePattern) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getFunctions"))
      throw new SQLException("DatabaseMetaData bombed");
    return  new ThrowingResultSet(it.getFunctions(catalog, schemaPattern, functionNamePattern));
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.DatabaseMetaData#getRowIdLifetime()
   */
  public RowIdLifetime getRowIdLifetime() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getRowIdLifetime"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.getRowIdLifetime();
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.DatabaseMetaData#getSchemas(java.lang.String, java.lang.String)
   */
  public ResultSet getSchemas(String catalog, String schemaPattern)
          throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getSchemas"))
      throw new SQLException("DatabaseMetaData bombed");
    return  new ThrowingResultSet(it.getSchemas());
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.DatabaseMetaData#supportsStoredFunctionsUsingCallSyntax()
   */
  public boolean supportsStoredFunctionsUsingCallSyntax() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "supportsStoredFunctionsUsingCallSyntax"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.supportsStoredFunctionsUsingCallSyntax();
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.Wrapper#isWrapperFor(java.lang.Class)
   */
  public boolean isWrapperFor(Class<?> iface) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "isWrapperFor"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.isWrapperFor(iface);
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.Wrapper#unwrap(java.lang.Class)
   */
  public <T> T unwrap(Class<T> iface) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "unwrap"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.unwrap(iface);
  }
  


}
