/*
 * $Source$
 * $Revision$
 *
 * Copyright (C) 2000 David Warnock
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
 *     David Warnock (david@sundayta.co.uk)
 *     Sundayta Ltd
 *     International House, 174 Three Bridges Road, Crawley,
 *     West Sussex RH10 1LE, UK
 *
 */

package org.melati.poem.dbms;

import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import org.melati.poem.Table;
import org.melati.poem.Column;
import org.melati.poem.SQLPoemException;
import org.melati.poem.User;
import org.melati.poem.PoemType;
import org.melati.poem.SQLPoemType;

/**
 * A Database Management System.
 */
public interface Dbms {
  Connection getConnection(String url, String user, String password)
      throws ConnectionFailurePoemException;

  String getQuotedName(String name);

  String preparedStatementPlaceholder(PoemType type);

  String getSqlDefinition(String sqlTypeName) throws SQLException;

  String getStringSqlDefinition(int size) throws SQLException;

  String getBinarySqlDefinition(int size) throws SQLException;
  
  String getFixedPtSqlDefinition(int scale, int precision) throws SQLException;

  PoemType canRepresent(PoemType storage, PoemType type);

  SQLPoemType defaultPoemTypeOfColumnMetaData(ResultSet rs)
      throws SQLException;

  boolean canDropColumns(Connection con) throws SQLException; 

  /**
   * An exception appropriate for expressing what really went wrong
   * during a write to the db.  This gives the opportunity to
   * try to interpret the <TT>getMessage</TT> text returned by
   * the underlying driver, so that a more friendly error page
   * can be put together for the user.
   *
   * Canonically, this is used to separate out "duplicate key"
   * errors from more serious problems.
   *
   * @param table     The table on which the update was affected
   * @param sql       The operation attempted, or possibly <TT>null</TT>
   * @param insert    Whether the operation was an <TT>INSERT</TT> as
   *                  opposed to an <TT>UPDATE</TT>
   * @param e         The raw SQL exception: the routine is meant to
   *                  try to interpret <TT>e.getMessage</TT> if it can
   *
   * @see Postgresql#exceptionForUpdate
   */

  SQLPoemException exceptionForUpdate(Table table, String sql, boolean insert,
                                      SQLException e);

  /**
   * Version of previous method for <TT>PreparedStatement</TT>s.  By default
   * (in the <TT>AnsiStandard</TT> implementation of <TT>Dbms</TT>) this simply
   * invokes <TT>PreparedStatement.toString()</TT> and calls the
   * <TT>String</TT> version.
   *
   * @see AnsiStandard#exceptionForUpdate
   */

  SQLPoemException exceptionForUpdate(Table table, PreparedStatement ps,
                                      boolean insert, SQLException e);

  String unreservedName(String name);
  String melatiName(String name);

  String getIndexLength(Column column);

  String givesCapabilitySQL(User user, String capabilityExpr);

  String caseInsensitiveRegExpSQL(String term1, String term2);

}
