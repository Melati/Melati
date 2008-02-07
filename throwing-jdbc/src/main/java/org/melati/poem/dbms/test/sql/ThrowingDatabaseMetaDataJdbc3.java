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

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author timp
 * @since  5 Feb 2008
 *
 */
public abstract class ThrowingDatabaseMetaDataJdbc3 
    extends Thrower 
    implements DatabaseMetaData {

  DatabaseMetaData it = null;

  public boolean allProceduresAreCallable() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "allProceduresAreCallable"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.allProceduresAreCallable();
  }

  public boolean allTablesAreSelectable() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "allTablesAreSelectable"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.allTablesAreSelectable();
  }

  public boolean dataDefinitionCausesTransactionCommit() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "dataDefinitionCausesTransactionCommit"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.dataDefinitionCausesTransactionCommit();
  }

  public boolean dataDefinitionIgnoredInTransactions() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "dataDefinitionIgnoredInTransactions"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.dataDefinitionIgnoredInTransactions();
  }

  public boolean deletesAreDetected(int type) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "deletesAreDetected"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.deletesAreDetected(type);
  }

  public boolean doesMaxRowSizeIncludeBlobs() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "doesMaxRowSizeIncludeBlobs"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.doesMaxRowSizeIncludeBlobs();
  }

  public ResultSet getAttributes(String catalog, String schemaPattern, String typeNamePattern, String attributeNamePattern) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getAttributes"))
      throw new SQLException("DatabaseMetaData bombed");
    return new ThrowingResultSet(it.getAttributes(catalog, schemaPattern, typeNamePattern, attributeNamePattern));
  }

  public ResultSet getBestRowIdentifier(String catalog, String schema, String table, int scope, boolean nullable) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getBestRowIdentifier"))
      throw new SQLException("DatabaseMetaData bombed");
    return new ThrowingResultSet(it.getBestRowIdentifier(catalog, schema, table, scope, nullable));
  }

  public String getCatalogSeparator() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getCatalogSeparator"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.getCatalogSeparator();
  }

  public String getCatalogTerm() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getCatalogTerm"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.getCatalogTerm();
  }

  public ResultSet getCatalogs() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getCatalogs"))
      throw new SQLException("DatabaseMetaData bombed");
    return new ThrowingResultSet(it.getCatalogs());
  }

  public ResultSet getColumnPrivileges(String catalog, String schema, String table, String columnNamePattern) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getColumnPrivileges"))
      throw new SQLException("DatabaseMetaData bombed");
    return new ThrowingResultSet(it.getColumnPrivileges(catalog, schema, table, columnNamePattern));
  }

  public ResultSet getColumns(String catalog, String schemaPattern, String tableNamePattern, String columnNamePattern) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getColumns"))
      throw new SQLException("DatabaseMetaData bombed");
    return new ThrowingResultSet(it.getColumns(catalog, schemaPattern, tableNamePattern, columnNamePattern));
  }

  public Connection getConnection() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getConnection"))
      throw new SQLException("DatabaseMetaData bombed");
    return new ThrowingConnection(it.getConnection());
  }

  public ResultSet getCrossReference(String primaryCatalog, String primarySchema, String primaryTable, String foreignCatalog, String foreignSchema, String foreignTable) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getCrossReference"))
      throw new SQLException("DatabaseMetaData bombed");
    return new ThrowingResultSet(it.getCrossReference(primaryCatalog, primarySchema, primaryTable, foreignCatalog, foreignSchema, foreignTable));
  }

  public int getDatabaseMajorVersion() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getDatabaseMajorVersion"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.getDatabaseMajorVersion();
  }

  public int getDatabaseMinorVersion() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getDatabaseMinorVersion"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.getDatabaseMinorVersion();
  }

  public String getDatabaseProductName() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getDatabaseProductName"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.getDatabaseProductName();
  }

  public String getDatabaseProductVersion() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getDatabaseProductVersion"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.getDatabaseProductVersion();
  }

  public int getDefaultTransactionIsolation() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getDefaultTransactionIsolation"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.getDefaultTransactionIsolation();
  }

  public int getDriverMajorVersion() {
    return it.getDriverMajorVersion();
  }

  public int getDriverMinorVersion() {
    return it.getDriverMinorVersion();
  }

  public String getDriverName() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getDriverName"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.getDriverName();
  }

  public String getDriverVersion() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getDriverVersion"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.getDriverVersion();
  }

  public ResultSet getExportedKeys(String catalog, String schema, String table) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getExportedKeys"))
      throw new SQLException("DatabaseMetaData bombed");
    return new ThrowingResultSet(it.getExportedKeys(catalog, schema, table));
  }

  public String getExtraNameCharacters() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getExtraNameCharacters"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.getExtraNameCharacters();
  }

  public String getIdentifierQuoteString() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getIdentifierQuoteString"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.getIdentifierQuoteString();
  }

  public ResultSet getImportedKeys(String catalog, String schema, String table) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getImportedKeys"))
      throw new SQLException("DatabaseMetaData bombed");
    return new ThrowingResultSet(it.getImportedKeys(catalog, schema, table));
  }

  public ResultSet getIndexInfo(String catalog, String schema, String table, boolean unique, boolean approximate) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getIndexInfo"))
      throw new SQLException("DatabaseMetaData bombed");
    return new ThrowingResultSet(it.getIndexInfo(catalog, schema, table, unique, approximate));
  }

  public int getJDBCMajorVersion() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getJDBCMajorVersion"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.getJDBCMajorVersion();
  }

  public int getJDBCMinorVersion() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getJDBCMinorVersion"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.getJDBCMinorVersion();
  }

  public int getMaxBinaryLiteralLength() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getMaxBinaryLiteralLength"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.getMaxBinaryLiteralLength();
  }

  public int getMaxCatalogNameLength() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getMaxCatalogNameLength"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.getMaxCatalogNameLength();
  }

  public int getMaxCharLiteralLength() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getMaxCharLiteralLength"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.getMaxCharLiteralLength();
  }

  public int getMaxColumnNameLength() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getMaxColumnNameLength"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.getMaxColumnNameLength();
  }

  public int getMaxColumnsInGroupBy() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getMaxColumnsInGroupBy"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.getMaxColumnsInGroupBy();

  }

  public int getMaxColumnsInIndex() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getMaxColumnsInIndex"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.getMaxColumnsInIndex();
  }

  public int getMaxColumnsInOrderBy() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getMaxColumnsInOrderBy"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.getMaxColumnsInOrderBy();
  }

  public int getMaxColumnsInSelect() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getMaxColumnsInSelect"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.getMaxColumnsInSelect();
  }

  public int getMaxColumnsInTable() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getMaxColumnsInTable"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.getMaxColumnsInTable();
  }

  public int getMaxConnections() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getMaxConnections"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.getMaxConnections();
  }

  public int getMaxCursorNameLength() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getMaxCursorNameLength"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.getMaxCursorNameLength();
  }

  public int getMaxIndexLength() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getMaxIndexLength"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.getMaxIndexLength();
  }

  public int getMaxProcedureNameLength() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getMaxProcedureNameLength"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.getMaxProcedureNameLength();
  }

  public int getMaxRowSize() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getMaxRowSize"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.getMaxRowSize();
  }

  public int getMaxSchemaNameLength() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getMaxSchemaNameLength"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.getMaxSchemaNameLength();
  }

  public int getMaxStatementLength() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getMaxStatementLength"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.getMaxStatementLength();
  }

  public int getMaxStatements() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getMaxStatements"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.getMaxStatements();
  }

  public int getMaxTableNameLength() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getMaxTableNameLength"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.getMaxTableNameLength();
  }

  public int getMaxTablesInSelect() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getMaxTablesInSelect"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.getMaxTablesInSelect();
  }

  public int getMaxUserNameLength() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getMaxUserNameLength"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.getMaxUserNameLength();
  }

  public String getNumericFunctions() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getNumericFunctions"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.getNumericFunctions();
  }

  public ResultSet getPrimaryKeys(String catalog, String schema, String table) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getPrimaryKeys"))
      throw new SQLException("DatabaseMetaData bombed");
    return new ThrowingResultSet(it.getPrimaryKeys(catalog, schema, table));
  }

  public ResultSet getProcedureColumns(String catalog, String schemaPattern, String procedureNamePattern, String columnNamePattern) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getProcedureColumns"))
      throw new SQLException("DatabaseMetaData bombed");
    return new ThrowingResultSet(it.getProcedureColumns(catalog, schemaPattern, procedureNamePattern, columnNamePattern));
  }

  public String getProcedureTerm() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getProcedureTerm"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.getProcedureTerm();
  }

  public ResultSet getProcedures(String catalog, String schemaPattern, String procedureNamePattern) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getProcedures"))
      throw new SQLException("DatabaseMetaData bombed");
    return new ThrowingResultSet(it.getProcedures(catalog, schemaPattern, procedureNamePattern));
  }

  public int getResultSetHoldability() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getResultSetHoldability"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.getResultSetHoldability();
  }

  public String getSQLKeywords() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getSQLKeywords"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.getSQLKeywords();
  }

  public int getSQLStateType() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getSQLStateType"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.getSQLStateType();
  }

  public String getSchemaTerm() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getSchemaTerm"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.getSchemaTerm();
  }

  public ResultSet getSchemas() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getSchemas"))
      throw new SQLException("DatabaseMetaData bombed");
    return new ThrowingResultSet(it.getSchemas());
  }

  public String getSearchStringEscape() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getSearchStringEscape"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.getSearchStringEscape();
  }

  public String getStringFunctions() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getStringFunctions"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.getStringFunctions();
  }

  public ResultSet getSuperTables(String catalog, String schemaPattern, String tableNamePattern) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getSuperTables"))
      throw new SQLException("DatabaseMetaData bombed");
    return new ThrowingResultSet(it.getSuperTables(catalog, schemaPattern, tableNamePattern));
  }

  public ResultSet getSuperTypes(String catalog, String schemaPattern, String typeNamePattern) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getSuperTypes"))
      throw new SQLException("DatabaseMetaData bombed");
    return new ThrowingResultSet(it.getSuperTypes(catalog, schemaPattern, typeNamePattern));
  }

  public String getSystemFunctions() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getSystemFunctions"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.getSystemFunctions();
  }

  public ResultSet getTablePrivileges(String catalog, String schemaPattern, String tableNamePattern) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getTablePrivileges"))
      throw new SQLException("DatabaseMetaData bombed");
    return new ThrowingResultSet(it.getTablePrivileges(catalog, schemaPattern, tableNamePattern));
  }

  public ResultSet getTableTypes() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getTableTypes"))
      throw new SQLException("DatabaseMetaData bombed");
    return new ThrowingResultSet(it.getTableTypes());
  }

  public ResultSet getTables(String catalog, String schemaPattern, String tableNamePattern, String[] types) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getTables"))
      throw new SQLException("DatabaseMetaData bombed");
    return new ThrowingResultSet(it.getTables(catalog, schemaPattern, tableNamePattern, types));
  }

  public String getTimeDateFunctions() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getTimeDateFunctions"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.getTimeDateFunctions();
  }

  public ResultSet getTypeInfo() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getTypeInfo"))
      throw new SQLException("DatabaseMetaData bombed");
    return new ThrowingResultSet(it.getTypeInfo());
  }

  public ResultSet getUDTs(String catalog, String schemaPattern, String typeNamePattern, int[] types) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getUDTs"))
      throw new SQLException("DatabaseMetaData bombed");
    return new ThrowingResultSet(it.getUDTs(catalog, schemaPattern, typeNamePattern, types));
  }

  public String getURL() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getURL"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.getURL();
  }

  public String getUserName() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getUserName"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.getUserName();
  }

  public ResultSet getVersionColumns(String catalog, String schema, String table) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getVersionColumns"))
      throw new SQLException("DatabaseMetaData bombed");
    return new ThrowingResultSet(it.getVersionColumns(catalog, schema, table));
  }

  public boolean insertsAreDetected(int type) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "insertsAreDetected"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.insertsAreDetected(type);
  }

  public boolean isCatalogAtStart() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "isCatalogAtStart"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.isCatalogAtStart();
  }

  public boolean isReadOnly() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "isReadOnly"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.isReadOnly();
  }

  public boolean locatorsUpdateCopy() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "locatorsUpdateCopy"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.locatorsUpdateCopy();
  }

  public boolean nullPlusNonNullIsNull() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "nullPlusNonNullIsNull"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.nullPlusNonNullIsNull();
  }

  public boolean nullsAreSortedAtEnd() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "nullsAreSortedAtEnd"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.nullsAreSortedAtEnd();
  }

  public boolean nullsAreSortedAtStart() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "nullsAreSortedAtStart"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.nullsAreSortedAtStart();
  }

  public boolean nullsAreSortedHigh() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "nullsAreSortedHigh"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.nullsAreSortedHigh();
  }

  public boolean nullsAreSortedLow() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "nullsAreSortedLow"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.nullsAreSortedLow();
  }

  public boolean othersDeletesAreVisible(int type) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "othersDeletesAreVisible"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.othersDeletesAreVisible(type);
  }

  public boolean othersInsertsAreVisible(int type) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "othersInsertsAreVisible"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.othersInsertsAreVisible(type);
  }

  public boolean othersUpdatesAreVisible(int type) throws SQLException {

    if (shouldThrow(this.getClass().getInterfaces()[0], "othersUpdatesAreVisible"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.othersUpdatesAreVisible(type);
  }

  public boolean ownDeletesAreVisible(int type) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "ownDeletesAreVisible"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.ownDeletesAreVisible(type);
  }

  public boolean ownInsertsAreVisible(int type) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "ownInsertsAreVisible"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.ownInsertsAreVisible(type);
  }

  public boolean ownUpdatesAreVisible(int type) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "ownUpdatesAreVisible"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.ownUpdatesAreVisible(type);
  }

  public boolean storesLowerCaseIdentifiers() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "storesLowerCaseIdentifiers"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.storesLowerCaseIdentifiers();
  }

  public boolean storesLowerCaseQuotedIdentifiers() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "storesLowerCaseQuotedIdentifiers"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.storesLowerCaseQuotedIdentifiers();
  }

  public boolean storesMixedCaseIdentifiers() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "storesMixedCaseIdentifiers"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.storesMixedCaseIdentifiers();
  }

  public boolean storesMixedCaseQuotedIdentifiers() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "storesMixedCaseQuotedIdentifiers"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.storesMixedCaseQuotedIdentifiers();
  }

  public boolean storesUpperCaseIdentifiers() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "storesUpperCaseIdentifiers"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.storesUpperCaseIdentifiers();
  }

  public boolean storesUpperCaseQuotedIdentifiers() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "storesUpperCaseQuotedIdentifiers"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.storesUpperCaseQuotedIdentifiers();
  }

  public boolean supportsANSI92EntryLevelSQL() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "supportsANSI92EntryLevelSQL"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.supportsANSI92EntryLevelSQL();
  }

  public boolean supportsANSI92FullSQL() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "supportsANSI92FullSQL"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.supportsANSI92FullSQL();
  }

  public boolean supportsANSI92IntermediateSQL() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "supportsANSI92IntermediateSQL"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.supportsANSI92IntermediateSQL();
  }

  public boolean supportsAlterTableWithAddColumn() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "supportsAlterTableWithAddColumn"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.supportsAlterTableWithAddColumn();
  }

  public boolean supportsAlterTableWithDropColumn() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "supportsAlterTableWithDropColumn"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.supportsAlterTableWithDropColumn();
  }

  public boolean supportsBatchUpdates() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "supportsBatchUpdates"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.supportsBatchUpdates();
  }

  public boolean supportsCatalogsInDataManipulation() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "supportsCatalogsInDataManipulation"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.supportsCatalogsInDataManipulation();
  }

  public boolean supportsCatalogsInIndexDefinitions() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "supportsCatalogsInIndexDefinitions"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.supportsCatalogsInIndexDefinitions();
  }

  public boolean supportsCatalogsInPrivilegeDefinitions() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "supportsCatalogsInPrivilegeDefinitions"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.supportsCatalogsInPrivilegeDefinitions();
  }

  public boolean supportsCatalogsInProcedureCalls() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "supportsCatalogsInProcedureCalls"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.supportsCatalogsInProcedureCalls();
  }

  public boolean supportsCatalogsInTableDefinitions() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "supportsCatalogsInTableDefinitions"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.supportsCatalogsInTableDefinitions();
  }

  public boolean supportsColumnAliasing() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "supportsColumnAliasing"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.supportsColumnAliasing();
  }

  public boolean supportsConvert() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "supportsConvert"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.supportsConvert();
  }

  public boolean supportsConvert(int fromType, int toType) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "supportsConvert"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.supportsConvert();
  }

  public boolean supportsCoreSQLGrammar() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "supportsCoreSQLGrammar"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.supportsCoreSQLGrammar();
  }

  public boolean supportsCorrelatedSubqueries() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "supportsCorrelatedSubqueries"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.supportsCorrelatedSubqueries();
  }

  public boolean supportsDataDefinitionAndDataManipulationTransactions() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "supportsDataDefinitionAndDataManipulationTransactions"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.supportsDataDefinitionAndDataManipulationTransactions();
  }

  public boolean supportsDataManipulationTransactionsOnly() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "supportsDataManipulationTransactionsOnly"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.supportsDataManipulationTransactionsOnly();
  }

  public boolean supportsDifferentTableCorrelationNames() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "supportsDifferentTableCorrelationNames"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.supportsDifferentTableCorrelationNames();
  }

  public boolean supportsExpressionsInOrderBy() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "supportsExpressionsInOrderBy"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.supportsExpressionsInOrderBy();
  }

  public boolean supportsExtendedSQLGrammar() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "supportsExtendedSQLGrammar"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.supportsExtendedSQLGrammar();
  }

  public boolean supportsFullOuterJoins() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "supportsFullOuterJoins"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.supportsFullOuterJoins();
  }

  public boolean supportsGetGeneratedKeys() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "supportsGetGeneratedKeys"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.supportsGetGeneratedKeys();
  }

  public boolean supportsGroupBy() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "supportsGroupBy"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.supportsGroupBy();
  }

  public boolean supportsGroupByBeyondSelect() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "supportsGroupByBeyondSelect"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.supportsGroupByBeyondSelect();
  }

  public boolean supportsGroupByUnrelated() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "supportsGroupByUnrelated"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.supportsGroupByUnrelated();
  }

  public boolean supportsIntegrityEnhancementFacility() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "supportsIntegrityEnhancementFacility"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.supportsIntegrityEnhancementFacility();
  }

  public boolean supportsLikeEscapeClause() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "supportsLikeEscapeClause"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.supportsLikeEscapeClause();
  }

  public boolean supportsLimitedOuterJoins() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "supportsLimitedOuterJoins"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.supportsLimitedOuterJoins();
  }

  public boolean supportsMinimumSQLGrammar() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "supportsMinimumSQLGrammar"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.supportsMinimumSQLGrammar();
  }

  public boolean supportsMixedCaseIdentifiers() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "supportsMixedCaseIdentifiers"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.supportsMixedCaseIdentifiers();
  }

  public boolean supportsMixedCaseQuotedIdentifiers() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "supportsMixedCaseQuotedIdentifiers"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.supportsMixedCaseQuotedIdentifiers();
  }

  public boolean supportsMultipleOpenResults() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "supportsMultipleOpenResults"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.supportsMultipleOpenResults();
  }

  public boolean supportsMultipleResultSets() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "supportsMultipleResultSets"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.supportsMultipleResultSets();
  }

  public boolean supportsMultipleTransactions() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "supportsMultipleTransactions"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.supportsMultipleTransactions();
  }

  public boolean supportsNamedParameters() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "supportsNamedParameters"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.supportsNamedParameters();
  }

  public boolean supportsNonNullableColumns() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "supportsNonNullableColumns"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.supportsNonNullableColumns();
  }

  public boolean supportsOpenCursorsAcrossCommit() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "supportsOpenCursorsAcrossCommit"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.supportsOpenCursorsAcrossCommit();
  }

  public boolean supportsOpenCursorsAcrossRollback() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "supportsOpenCursorsAcrossRollback"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.supportsOpenCursorsAcrossRollback();
  }

  public boolean supportsOpenStatementsAcrossCommit() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "supportsOpenStatementsAcrossCommit"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.supportsOpenStatementsAcrossCommit();
  }

  public boolean supportsOpenStatementsAcrossRollback() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "supportsOpenStatementsAcrossRollback"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.supportsOpenStatementsAcrossRollback();
  }

  public boolean supportsOrderByUnrelated() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "supportsOrderByUnrelated"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.supportsOrderByUnrelated();
  }

  public boolean supportsOuterJoins() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "supportsOuterJoins"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.supportsOuterJoins();
  }

  public boolean supportsPositionedDelete() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "supportsPositionedDelete"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.supportsPositionedDelete();
  }

  public boolean supportsPositionedUpdate() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "supportsPositionedUpdate"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.supportsPositionedUpdate();
  }

  public boolean supportsResultSetConcurrency(int type, int concurrency) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "supportsResultSetConcurrency"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.supportsResultSetConcurrency(type, concurrency);
  }

  public boolean supportsResultSetHoldability(int holdability) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "supportsResultSetHoldability"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.supportsResultSetHoldability(holdability);
  }

  public boolean supportsResultSetType(int type) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "supportsResultSetType"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.supportsResultSetType(type);
  }

  public boolean supportsSavepoints() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "supportsSavepoints"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.supportsSavepoints();
  }

  public boolean supportsSchemasInDataManipulation() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "supportsSchemasInDataManipulation"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.supportsSchemasInDataManipulation();
  }

  public boolean supportsSchemasInIndexDefinitions() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "supportsSchemasInIndexDefinitions"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.supportsSchemasInIndexDefinitions();
  }

  public boolean supportsSchemasInPrivilegeDefinitions() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "supportsSchemasInPrivilegeDefinitions"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.supportsSchemasInPrivilegeDefinitions();
  }

  public boolean supportsSchemasInProcedureCalls() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "supportsSchemasInProcedureCalls"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.supportsSchemasInProcedureCalls();
  }

  public boolean supportsSchemasInTableDefinitions() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "supportsSchemasInTableDefinitions"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.supportsSchemasInTableDefinitions();
  }

  public boolean supportsSelectForUpdate() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "supportsSelectForUpdate"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.supportsSelectForUpdate();
  }

  public boolean supportsStatementPooling() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "supportsStatementPooling"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.supportsStatementPooling();
  }

  public boolean supportsStoredProcedures() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "supportsStoredProcedures"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.supportsStoredProcedures();
  }

  public boolean supportsSubqueriesInComparisons() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "supportsSubqueriesInComparisons"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.supportsSubqueriesInComparisons();
  }

  public boolean supportsSubqueriesInExists() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "supportsSubqueriesInExists"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.supportsSubqueriesInExists();
  }

  public boolean supportsSubqueriesInIns() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "supportsSubqueriesInIns"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.supportsSubqueriesInIns();
  }

  public boolean supportsSubqueriesInQuantifieds() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "supportsSubqueriesInQuantifieds"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.supportsSubqueriesInQuantifieds();
  }

  public boolean supportsTableCorrelationNames() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "supportsTableCorrelationNames"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.supportsTableCorrelationNames();
  }

  public boolean supportsTransactionIsolationLevel(int level) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "supportsTransactionIsolationLevel"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.supportsTransactionIsolationLevel(level);
  }

  public boolean supportsTransactions() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "supportsTransactions"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.supportsTransactions();
  }

  public boolean supportsUnion() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "supportsUnion"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.supportsUnion();
  }

  public boolean supportsUnionAll() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "supportsUnionAll"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.supportsUnionAll();
  }

  public boolean updatesAreDetected(int type) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "updatesAreDetected"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.updatesAreDetected(type);
  }

  public boolean usesLocalFilePerTable() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "usesLocalFilePerTable"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.usesLocalFilePerTable();
  }

  public boolean usesLocalFiles() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "usesLocalFiles"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.usesLocalFiles();
  }

}
