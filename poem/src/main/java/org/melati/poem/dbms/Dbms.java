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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.melati.poem.Column;
import org.melati.poem.PoemType;
import org.melati.poem.SQLPoemException;
import org.melati.poem.SQLPoemType;
import org.melati.poem.SQLType;
import org.melati.poem.Table;

/**
 * A Database Management System.
 */
public interface Dbms {
  
  /**
   * Used in tests to allow multiple dbmsen to be loaded and unloaded.
   */
  void unloadDriver();
  
  /**
   * Return a connection.
   * @param url the jdbc URL
   * @param user the user to connect as, may be null
   * @param password the password for user, may be null
   * @return the connection
   * @throws ConnectionFailurePoemException is we cannot connect
   */
  Connection getConnection(String url, String user, String password)
      throws ConnectionFailurePoemException;

  /**
   * The db schema name to use, if any.
   * This is typically the JDBC connection URL User string.
   * 
   * @return the schema to use or null if not required
   */
  String getSchema();
  
  /**
   * A no-op for all but hsqldb, where the db needs to be shutdown 
   * when the servlet container or jvm is destroyed.   
   */
  void shutdown(Connection connection) throws SQLException;
  
  /**
   * Accomodate different quoting strategies.
   * 
   * @param name the unquoted name
   * @return the name quoted (or not) appropriate for this Dbms
   */
  String getQuotedName(String name);

  /**
   * Accomodate different quoting strategies for values.
   * 
   * @param sqlType the SQLType of the value
   * @param value the value
   * @return a String quoted appropriately
   */
  String getQuotedValue(SQLType sqlType, String value);
  
  /**
   * Some DBMSen (HSQLDB) use canonical uppercased names in the metadata but not 
   * in normal use. 
   * 
   * see org.melati.poem.Table#unifyWithDB
   * @param name entity name such as <tt>tableinfo</tt>
   * @return the (un)quoted name
   */
  String getJdbcMetadataName(String name);

  /**
   * Accomodate casting in placeholders.
   * 
   * @param type
   * @return the place holder
   * @see Postgresql
   */
  String preparedStatementPlaceholder(PoemType type);

  /**
   * Allow hsqldb to have a different create table syntax.
   * 
   * @return "CREATE TABLE " normally 
   */
  String createTableSql();
   
 /**
  * Retrieve a SQL type keyword used by the DBMS 
  * for the given Melati type name.
  *
  * Override this in non-Ansi standard dbms to handle 
  * variants.
  *
  * @param sqlTypeName the Melati internal type name
  * @return this dbms specific type keyword
  */
  String getSqlDefinition(String sqlTypeName);

  /**
   * Accomodate String / Text distinction.
   * 
   * @param size the string length (-1 means no limit)
   * @return the SQL definition for a string of this size 
   * @throws SQLException
   */
  String getStringSqlDefinition(int size) throws SQLException;

  /**
   * Accomodate Long / Bigint deviants.
   * @return the keyword to use.
   */
  String getLongSqlDefinition();

  /**
   * Accomodate different true and false values. 
   * 
   * @return the DBMS specific truth and false values 
   */
  String sqlBooleanValueOfRaw(Object raw);
  
  /**
   * Accomodate different treatment of different sized binary data.
   * 
   * @param size how big the field is
   * @return the keyword to use
   * @throws SQLException 
   */
  String getBinarySqlDefinition(int size) throws SQLException;
  
  /**
   * Accomodate differing Fixed Point notations.
   * 
   * @param scale the number of places to right of decimal point
   * @param precision how many digits in total
   * @return the keywords to use
   * @throws SQLException potentially
   */
  String getFixedPtSqlDefinition(int scale, int precision) throws SQLException;

  /**
   * Enable one PoemType to represent another, 
   * for example a <tt>bit</tt> to represent a <tt>boolean</tt>.
   * 
   * @param storage the POEM native type
   * @param type the current type
   * @return the PoemType to use
   */
  PoemType canRepresent(PoemType storage, PoemType type);

  /**
   * The simplest POEM type corresponding to a JDBC description from the
   * database.
   * 
   * @param rs the JDBC metadata
   * @return the PoemType to use 
   * @throws SQLException potentially
   */
  SQLPoemType defaultPoemTypeOfColumnMetaData(ResultSet rs)
      throws SQLException;

  /**
   * Whether this DBMS can drop columns.
   * 
   * @return true if we can
   * @throws SQLException
   */
  boolean canDropColumns(); 

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
   * @return an appropriate exception
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
   * @param table     The table on which the update was affected
   * @param ps        The operation attempted, or possibly <TT>null</TT>
   * @param insert    Whether the operation was an <TT>INSERT</TT> as
   *                  opposed to an <TT>UPDATE</TT>
   * @param e         The raw SQL exception: the routine is meant to
   *                  try to interpret <TT>e.getMessage</TT> if it can
   * @return an appropriate exception
   * @see AnsiStandard#exceptionForUpdate(org.melati.poem.Table, java.lang.String, 
   *                             boolean, java.sql.SQLException)
   */

  SQLPoemException exceptionForUpdate(Table table, PreparedStatement ps,
                                      boolean insert, SQLException e);

  /**
   * Translate special names to non special ones.
   * 
   * @param name the field or table name
   * @return the name translated if necessary
   */
  String unreservedName(String name);
  
  /**
   * Reverse the mapping in <tt>unreservedName</tt>.
   * 
   * @param name an SQL name
   * @return the coresponding name to use within Melati
   */
  String melatiName(String name);

  /**
   * Accomodate DBMS which require a length for BLOBS.
   * 
   * @param column the POEM Column we are dealing with
   * @return SQL length string
   */
  String getIndexLength(Column column);

  /**
   * Whether a <tt>Column</tt> can have an SQL index applied to it.
   * 
   * @param column the POEM Column we are dealing with
   * @return true if it can, false otherwise.
   */
  boolean canBeIndexed(Column column);

  /**
   * SQL string to get a <tt>Capability</tt>.
   * 
   * @param userTroid the troid of the User to use in the query
   * @param capabilityExpr the capability troid we need
   * @return the SQL query to use
   */
  String givesCapabilitySQL(Integer userTroid, String capabilityExpr);

  /**
   * Accomodate the variety of ways of ignoring case.
   * 
   * @param term1 the term to find in 
   * @param term2 the quoted term to find 
   * @return the SQL query to use
   */
  String caseInsensitiveRegExpSQL(String term1, String term2);

  /**
   * A string to represent this DBMS.
   * 
   * @return the class name.
   */
  String toString();

  /**
   * If Foreign key definitions are part of field definitions,
   * otherwise blank (silently unsupported).
   *  
   * @param tableName the table that this column is in, unquoted
   * @param fieldName often the name of the foreign table, unquoted
   * @param targetTableName the table that this is a foreign key into, unquoted
   * @param targetTableFieldName name of the primary key field of the foreign 
   * table, often id, unquoted
   * @param fixName name of the IntegrityFix 
   * 
   * @return The definition string
   */
  String getForeignKeyDefinition(String tableName, String fieldName, 
      String targetTableName, String targetTableFieldName, String fixName);

  /**
   * Return the PRIMARY KEY definition string for this dbms. 
   *
   * @param fieldName the table Troid column, often <code>id</code>, unquoted
   * 
   * @return The definition string
   */
  String getPrimaryKeyDefinition(String fieldName);

  /**
   * Return the SQL snippet to alter a column to not nullable.
   * @param tableName
   * @param column
   * @return SQL snippet to set a column not nullable
   */
  String alterColumnNotNullableSQL(String tableName, Column column);

  /**
   * Accomodate different limiting syntax.
   * 
   * @param querySelection main body of query
   * @param limit number to limit to
   * @return limited query
   */
  String selectLimit(String querySelection, int limit);

  /**
   * Accomodate lack of boolean types.
   * @param booleanColumn
   * @return an expresion that evaluates to True ie the column name or column name = 1
   */
  String booleanTrueExpression(Column booleanColumn);

  /**
   * Used to set a not null value when 
   * creating a non nullable column.
   * @param type the type name
   * @return a String suitable for substitution in UPDATE table SET field = ?
   */
  String getSqlDefaultValue(SQLType type);

  /**
   * Accomodate table creation options.
   * @return DMBS specific table creation options
   */
  String createTableOptionsSql();
  
}
