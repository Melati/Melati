/*
 * $Source$
 * $Revision$
 *
 * Copyright (C) 2007 Tim Pizey
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
import java.sql.RowIdLifetime;
import java.sql.SQLException;


/**
 * @author timp
 * @since 3 May 2007
 *
 */
public class ThrowingDatabaseMetaData extends Thrower implements DatabaseMetaData {
  final static String className = ThrowingDatabaseMetaData.class.getName() + ".";
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

  DatabaseMetaData it = null;

  /**
   * Decorator constructor. 
   * @param d base object to decorate. 
   */
  public ThrowingDatabaseMetaData(DatabaseMetaData d) {
    this.it = d;
  }

  public boolean allProceduresAreCallable() throws SQLException {
    if (shouldThrow("allProceduresAreCallable"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.allProceduresAreCallable();
  }

  public boolean allTablesAreSelectable() throws SQLException {
    if (shouldThrow("allTablesAreSelectable"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.allTablesAreSelectable();
  }

  public boolean dataDefinitionCausesTransactionCommit() throws SQLException {
    if (shouldThrow("dataDefinitionCausesTransactionCommit"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.dataDefinitionCausesTransactionCommit();
  }

  public boolean dataDefinitionIgnoredInTransactions() throws SQLException {
    if (shouldThrow("dataDefinitionIgnoredInTransactions"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.dataDefinitionIgnoredInTransactions();
  }

  public boolean deletesAreDetected(int type) throws SQLException {
    if (shouldThrow("deletesAreDetected"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.deletesAreDetected(type);
  }

  public boolean doesMaxRowSizeIncludeBlobs() throws SQLException {
    if (shouldThrow("doesMaxRowSizeIncludeBlobs"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.doesMaxRowSizeIncludeBlobs();
  }

  public ResultSet getAttributes(String catalog, String schemaPattern, String typeNamePattern, String attributeNamePattern) throws SQLException {
    if (shouldThrow("getAttributes"))
      throw new SQLException("DatabaseMetaData bombed");
    return new ThrowingResultSet<Object>(it.getAttributes(catalog, schemaPattern, typeNamePattern, attributeNamePattern));
  }

  public ResultSet getBestRowIdentifier(String catalog, String schema, String table, int scope, boolean nullable) throws SQLException {
    if (shouldThrow("getBestRowIdentifier"))
      throw new SQLException("DatabaseMetaData bombed");
    return new ThrowingResultSet<Object>(it.getBestRowIdentifier(catalog, schema, table, scope, nullable));
  }

  public String getCatalogSeparator() throws SQLException {
    if (shouldThrow("getCatalogSeparator"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.getCatalogSeparator();
  }

  public String getCatalogTerm() throws SQLException {
    if (shouldThrow("getCatalogTerm"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.getCatalogTerm();
  }

  public ResultSet getCatalogs() throws SQLException {
    if (shouldThrow("getCatalogs"))
      throw new SQLException("DatabaseMetaData bombed");
    return new ThrowingResultSet<Object>(it.getCatalogs());
  }

  public ResultSet getColumnPrivileges(String catalog, String schema, String table, String columnNamePattern) throws SQLException {
    if (shouldThrow("getColumnPrivileges"))
      throw new SQLException("DatabaseMetaData bombed");
    return new ThrowingResultSet<Object>(it.getColumnPrivileges(catalog, schema, table, columnNamePattern));
  }

  public ResultSet getColumns(String catalog, String schemaPattern, String tableNamePattern, String columnNamePattern) throws SQLException {
    if (shouldThrow("getColumns"))
      throw new SQLException("DatabaseMetaData bombed");
    return new ThrowingResultSet<Object>(it.getColumns(catalog, schemaPattern, tableNamePattern, columnNamePattern));
  }

  public Connection getConnection() throws SQLException {
    if (shouldThrow("getConnection"))
      throw new SQLException("DatabaseMetaData bombed");
    return new ThrowingConnection(it.getConnection());
  }

  public ResultSet getCrossReference(String primaryCatalog, String primarySchema, String primaryTable, String foreignCatalog, String foreignSchema, String foreignTable) throws SQLException {
    if (shouldThrow("getCrossReference"))
      throw new SQLException("DatabaseMetaData bombed");
    return new ThrowingResultSet<Object>(it.getCrossReference(primaryCatalog, primarySchema, primaryTable, foreignCatalog, foreignSchema, foreignTable));
  }

  public int getDatabaseMajorVersion() throws SQLException {
    if (shouldThrow("getDatabaseMajorVersion"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.getDatabaseMajorVersion();
  }

  public int getDatabaseMinorVersion() throws SQLException {
    if (shouldThrow("getDatabaseMinorVersion"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.getDatabaseMinorVersion();
  }

  public String getDatabaseProductName() throws SQLException {
    if (shouldThrow("getDatabaseProductName"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.getDatabaseProductName();
  }

  public String getDatabaseProductVersion() throws SQLException {
    if (shouldThrow("getDatabaseProductVersion"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.getDatabaseProductVersion();
  }

  public int getDefaultTransactionIsolation() throws SQLException {
    if (shouldThrow("getDefaultTransactionIsolation"))
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
    if (shouldThrow("getDriverName"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.getDriverName();
  }

  public String getDriverVersion() throws SQLException {
    if (shouldThrow("getDriverVersion"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.getDriverVersion();
  }

  public ResultSet getExportedKeys(String catalog, String schema, String table) throws SQLException {
    if (shouldThrow("getExportedKeys"))
      throw new SQLException("DatabaseMetaData bombed");
    return new ThrowingResultSet<Object>(it.getExportedKeys(catalog, schema, table));
  }

  public String getExtraNameCharacters() throws SQLException {
    if (shouldThrow("getExtraNameCharacters"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.getExtraNameCharacters();
  }

  public String getIdentifierQuoteString() throws SQLException {
    if (shouldThrow("getIdentifierQuoteString"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.getIdentifierQuoteString();
  }

  public ResultSet getImportedKeys(String catalog, String schema, String table) throws SQLException {
    if (shouldThrow("getImportedKeys"))
      throw new SQLException("DatabaseMetaData bombed");
    return new ThrowingResultSet<Object>(it.getImportedKeys(catalog, schema, table));
  }

  public ResultSet getIndexInfo(String catalog, String schema, String table, boolean unique, boolean approximate) throws SQLException {
    if (shouldThrow("getIndexInfo"))
      throw new SQLException("DatabaseMetaData bombed");
    return new ThrowingResultSet<Object>(it.getIndexInfo(catalog, schema, table, unique, approximate));
  }

  public int getJDBCMajorVersion() throws SQLException {
    if (shouldThrow("getJDBCMajorVersion"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.getJDBCMajorVersion();
  }

  public int getJDBCMinorVersion() throws SQLException {
    if (shouldThrow("getJDBCMinorVersion"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.getJDBCMinorVersion();
  }

  public int getMaxBinaryLiteralLength() throws SQLException {
    if (shouldThrow("getMaxBinaryLiteralLength"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.getMaxBinaryLiteralLength();
  }

  public int getMaxCatalogNameLength() throws SQLException {
    if (shouldThrow("getMaxCatalogNameLength"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.getMaxCatalogNameLength();
  }

  public int getMaxCharLiteralLength() throws SQLException {
    if (shouldThrow("getMaxCharLiteralLength"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.getMaxCharLiteralLength();
  }

  public int getMaxColumnNameLength() throws SQLException {
    if (shouldThrow("getMaxColumnNameLength"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.getMaxColumnNameLength();
  }

  public int getMaxColumnsInGroupBy() throws SQLException {
    if (shouldThrow("getMaxColumnsInGroupBy"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.getMaxColumnsInGroupBy();

  }

  public int getMaxColumnsInIndex() throws SQLException {
    if (shouldThrow("getMaxColumnsInIndex"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.getMaxColumnsInIndex();
  }

  public int getMaxColumnsInOrderBy() throws SQLException {
    if (shouldThrow("getMaxColumnsInOrderBy"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.getMaxColumnsInOrderBy();
  }

  public int getMaxColumnsInSelect() throws SQLException {
    if (shouldThrow("getMaxColumnsInSelect"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.getMaxColumnsInSelect();
  }

  public int getMaxColumnsInTable() throws SQLException {
    if (shouldThrow("getMaxColumnsInTable"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.getMaxColumnsInTable();
  }

  public int getMaxConnections() throws SQLException {
    if (shouldThrow("getMaxConnections"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.getMaxConnections();
  }

  public int getMaxCursorNameLength() throws SQLException {
    if (shouldThrow("getMaxCursorNameLength"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.getMaxCursorNameLength();
  }

  public int getMaxIndexLength() throws SQLException {
    if (shouldThrow("getMaxIndexLength"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.getMaxIndexLength();
  }

  public int getMaxProcedureNameLength() throws SQLException {
    if (shouldThrow("getMaxProcedureNameLength"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.getMaxProcedureNameLength();
  }

  public int getMaxRowSize() throws SQLException {
    if (shouldThrow("getMaxRowSize"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.getMaxRowSize();
  }

  public int getMaxSchemaNameLength() throws SQLException {
    if (shouldThrow("getMaxSchemaNameLength"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.getMaxSchemaNameLength();
  }

  public int getMaxStatementLength() throws SQLException {
    if (shouldThrow("getMaxStatementLength"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.getMaxStatementLength();
  }

  public int getMaxStatements() throws SQLException {
    if (shouldThrow("getMaxStatements"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.getMaxStatements();
  }

  public int getMaxTableNameLength() throws SQLException {
    if (shouldThrow("getMaxTableNameLength"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.getMaxTableNameLength();
  }

  public int getMaxTablesInSelect() throws SQLException {
    if (shouldThrow("getMaxTablesInSelect"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.getMaxTablesInSelect();
  }

  public int getMaxUserNameLength() throws SQLException {
    if (shouldThrow("getMaxUserNameLength"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.getMaxUserNameLength();
  }

  public String getNumericFunctions() throws SQLException {
    if (shouldThrow("getNumericFunctions"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.getNumericFunctions();
  }

  public ResultSet getPrimaryKeys(String catalog, String schema, String table) throws SQLException {
    if (shouldThrow("getPrimaryKeys"))
      throw new SQLException("DatabaseMetaData bombed");
    return new ThrowingResultSet<Object>(it.getPrimaryKeys(catalog, schema, table));
  }

  public ResultSet getProcedureColumns(String catalog, String schemaPattern, String procedureNamePattern, String columnNamePattern) throws SQLException {
    if (shouldThrow("getProcedureColumns"))
      throw new SQLException("DatabaseMetaData bombed");
    return new ThrowingResultSet<Object>(it.getProcedureColumns(catalog, schemaPattern, procedureNamePattern, columnNamePattern));
  }

  public String getProcedureTerm() throws SQLException {
    if (shouldThrow("getProcedureTerm"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.getProcedureTerm();
  }

  public ResultSet getProcedures(String catalog, String schemaPattern, String procedureNamePattern) throws SQLException {
    if (shouldThrow("getProcedures"))
      throw new SQLException("DatabaseMetaData bombed");
    return new ThrowingResultSet<Object>(it.getProcedures(catalog, schemaPattern, procedureNamePattern));
  }

  public int getResultSetHoldability() throws SQLException {
    if (shouldThrow("getResultSetHoldability"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.getResultSetHoldability();
  }

  public String getSQLKeywords() throws SQLException {
    if (shouldThrow("getSQLKeywords"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.getSQLKeywords();
  }

  public int getSQLStateType() throws SQLException {
    if (shouldThrow("getSQLStateType"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.getSQLStateType();
  }

  public String getSchemaTerm() throws SQLException {
    if (shouldThrow("getSchemaTerm"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.getSchemaTerm();
  }

  public ResultSet getSchemas() throws SQLException {
    if (shouldThrow("getSchemas"))
      throw new SQLException("DatabaseMetaData bombed");
    return new ThrowingResultSet<Object>(it.getSchemas());
  }

  public String getSearchStringEscape() throws SQLException {
    if (shouldThrow("getSearchStringEscape"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.getSearchStringEscape();
  }

  public String getStringFunctions() throws SQLException {
    if (shouldThrow("getStringFunctions"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.getStringFunctions();
  }

  public ResultSet getSuperTables(String catalog, String schemaPattern, String tableNamePattern) throws SQLException {
    if (shouldThrow("getSuperTables"))
      throw new SQLException("DatabaseMetaData bombed");
    return new ThrowingResultSet<Object>(it.getSuperTables(catalog, schemaPattern, tableNamePattern));
  }

  public ResultSet getSuperTypes(String catalog, String schemaPattern, String typeNamePattern) throws SQLException {
    if (shouldThrow("getSuperTypes"))
      throw new SQLException("DatabaseMetaData bombed");
    return new ThrowingResultSet<Object>(it.getSuperTypes(catalog, schemaPattern, typeNamePattern));
  }

  public String getSystemFunctions() throws SQLException {
    if (shouldThrow("getSystemFunctions"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.getSystemFunctions();
  }

  public ResultSet getTablePrivileges(String catalog, String schemaPattern, String tableNamePattern) throws SQLException {
    if (shouldThrow("getTablePrivileges"))
      throw new SQLException("DatabaseMetaData bombed");
    return new ThrowingResultSet<Object>(it.getTablePrivileges(catalog, schemaPattern, tableNamePattern));
  }

  public ResultSet getTableTypes() throws SQLException {
    if (shouldThrow("getTableTypes"))
      throw new SQLException("DatabaseMetaData bombed");
    return new ThrowingResultSet<Object>(it.getTableTypes());
  }

  public ResultSet getTables(String catalog, String schemaPattern, String tableNamePattern, String[] types) throws SQLException {
    if (shouldThrow("getTables"))
      throw new SQLException("DatabaseMetaData bombed");
    return new ThrowingResultSet<Object>(it.getTables(catalog, schemaPattern, tableNamePattern, types));
  }

  public String getTimeDateFunctions() throws SQLException {
    if (shouldThrow("getTimeDateFunctions"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.getTimeDateFunctions();
  }

  public ResultSet getTypeInfo() throws SQLException {
    if (shouldThrow("getTypeInfo"))
      throw new SQLException("DatabaseMetaData bombed");
    return new ThrowingResultSet<Object>(it.getTypeInfo());
  }

  public ResultSet getUDTs(String catalog, String schemaPattern, String typeNamePattern, int[] types) throws SQLException {
    if (shouldThrow("getUDTs"))
      throw new SQLException("DatabaseMetaData bombed");
    return new ThrowingResultSet<Object>(it.getUDTs(catalog, schemaPattern, typeNamePattern, types));
  }

  public String getURL() throws SQLException {
    if (shouldThrow("getURL"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.getURL();
  }

  public String getUserName() throws SQLException {
    if (shouldThrow("getUserName"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.getUserName();
  }

  public ResultSet getVersionColumns(String catalog, String schema, String table) throws SQLException {
    if (shouldThrow("getVersionColumns"))
      throw new SQLException("DatabaseMetaData bombed");
    return new ThrowingResultSet<Object>(it.getVersionColumns(catalog, schema, table));
  }

  public boolean insertsAreDetected(int type) throws SQLException {
    if (shouldThrow("insertsAreDetected"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.insertsAreDetected(type);
  }

  public boolean isCatalogAtStart() throws SQLException {
    if (shouldThrow("isCatalogAtStart"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.isCatalogAtStart();
  }

  public boolean isReadOnly() throws SQLException {
    if (shouldThrow("isReadOnly"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.isReadOnly();
  }

  public boolean locatorsUpdateCopy() throws SQLException {
    if (shouldThrow("locatorsUpdateCopy"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.locatorsUpdateCopy();
  }

  public boolean nullPlusNonNullIsNull() throws SQLException {
    if (shouldThrow("nullPlusNonNullIsNull"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.nullPlusNonNullIsNull();
  }

  public boolean nullsAreSortedAtEnd() throws SQLException {
    if (shouldThrow("nullsAreSortedAtEnd"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.nullsAreSortedAtEnd();
  }

  public boolean nullsAreSortedAtStart() throws SQLException {
    if (shouldThrow("nullsAreSortedAtStart"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.nullsAreSortedAtStart();
  }

  public boolean nullsAreSortedHigh() throws SQLException {
    if (shouldThrow("nullsAreSortedHigh"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.nullsAreSortedHigh();
  }

  public boolean nullsAreSortedLow() throws SQLException {
    if (shouldThrow("nullsAreSortedLow"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.nullsAreSortedLow();
  }

  public boolean othersDeletesAreVisible(int type) throws SQLException {
    if (shouldThrow("othersDeletesAreVisible"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.othersDeletesAreVisible(type);
  }

  public boolean othersInsertsAreVisible(int type) throws SQLException {
    if (shouldThrow("othersInsertsAreVisible"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.othersInsertsAreVisible(type);
  }

  public boolean othersUpdatesAreVisible(int type) throws SQLException {

    if (shouldThrow("othersUpdatesAreVisible"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.othersUpdatesAreVisible(type);
  }

  public boolean ownDeletesAreVisible(int type) throws SQLException {
    if (shouldThrow("ownDeletesAreVisible"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.ownDeletesAreVisible(type);
  }

  public boolean ownInsertsAreVisible(int type) throws SQLException {
    if (shouldThrow("ownInsertsAreVisible"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.ownInsertsAreVisible(type);
  }

  public boolean ownUpdatesAreVisible(int type) throws SQLException {
    if (shouldThrow("ownUpdatesAreVisible"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.ownUpdatesAreVisible(type);
  }

  public boolean storesLowerCaseIdentifiers() throws SQLException {
    if (shouldThrow("storesLowerCaseIdentifiers"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.storesLowerCaseIdentifiers();
  }

  public boolean storesLowerCaseQuotedIdentifiers() throws SQLException {
    if (shouldThrow("storesLowerCaseQuotedIdentifiers"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.storesLowerCaseQuotedIdentifiers();
  }

  public boolean storesMixedCaseIdentifiers() throws SQLException {
    if (shouldThrow("storesMixedCaseIdentifiers"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.storesMixedCaseIdentifiers();
  }

  public boolean storesMixedCaseQuotedIdentifiers() throws SQLException {
    if (shouldThrow("storesMixedCaseQuotedIdentifiers"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.storesMixedCaseQuotedIdentifiers();
  }

  public boolean storesUpperCaseIdentifiers() throws SQLException {
    if (shouldThrow("storesUpperCaseIdentifiers"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.storesUpperCaseIdentifiers();
  }

  public boolean storesUpperCaseQuotedIdentifiers() throws SQLException {
    if (shouldThrow("storesUpperCaseQuotedIdentifiers"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.storesUpperCaseQuotedIdentifiers();
  }

  public boolean supportsANSI92EntryLevelSQL() throws SQLException {
    if (shouldThrow("supportsANSI92EntryLevelSQL"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.supportsANSI92EntryLevelSQL();
  }

  public boolean supportsANSI92FullSQL() throws SQLException {
    if (shouldThrow("supportsANSI92FullSQL"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.supportsANSI92FullSQL();
  }

  public boolean supportsANSI92IntermediateSQL() throws SQLException {
    if (shouldThrow("supportsANSI92IntermediateSQL"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.supportsANSI92IntermediateSQL();
  }

  public boolean supportsAlterTableWithAddColumn() throws SQLException {
    if (shouldThrow("supportsAlterTableWithAddColumn"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.supportsAlterTableWithAddColumn();
  }

  public boolean supportsAlterTableWithDropColumn() throws SQLException {
    if (shouldThrow("supportsAlterTableWithDropColumn"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.supportsAlterTableWithDropColumn();
  }

  public boolean supportsBatchUpdates() throws SQLException {
    if (shouldThrow("supportsBatchUpdates"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.supportsBatchUpdates();
  }

  public boolean supportsCatalogsInDataManipulation() throws SQLException {
    if (shouldThrow("supportsCatalogsInDataManipulation"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.supportsCatalogsInDataManipulation();
  }

  public boolean supportsCatalogsInIndexDefinitions() throws SQLException {
    if (shouldThrow("supportsCatalogsInIndexDefinitions"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.supportsCatalogsInIndexDefinitions();
  }

  public boolean supportsCatalogsInPrivilegeDefinitions() throws SQLException {
    if (shouldThrow("supportsCatalogsInPrivilegeDefinitions"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.supportsCatalogsInPrivilegeDefinitions();
  }

  public boolean supportsCatalogsInProcedureCalls() throws SQLException {
    if (shouldThrow("supportsCatalogsInProcedureCalls"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.supportsCatalogsInProcedureCalls();
  }

  public boolean supportsCatalogsInTableDefinitions() throws SQLException {
    if (shouldThrow("supportsCatalogsInTableDefinitions"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.supportsCatalogsInTableDefinitions();
  }

  public boolean supportsColumnAliasing() throws SQLException {
    if (shouldThrow("supportsColumnAliasing"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.supportsColumnAliasing();
  }

  public boolean supportsConvert() throws SQLException {
    if (shouldThrow("supportsConvert"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.supportsConvert();
  }

  public boolean supportsConvert(int fromType, int toType) throws SQLException {
    if (shouldThrow("supportsConvert"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.supportsConvert();
  }

  public boolean supportsCoreSQLGrammar() throws SQLException {
    if (shouldThrow("supportsCoreSQLGrammar"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.supportsCoreSQLGrammar();
  }

  public boolean supportsCorrelatedSubqueries() throws SQLException {
    if (shouldThrow("supportsCorrelatedSubqueries"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.supportsCorrelatedSubqueries();
  }

  public boolean supportsDataDefinitionAndDataManipulationTransactions() throws SQLException {
    if (shouldThrow("supportsDataDefinitionAndDataManipulationTransactions"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.supportsDataDefinitionAndDataManipulationTransactions();
  }

  public boolean supportsDataManipulationTransactionsOnly() throws SQLException {
    if (shouldThrow("supportsDataManipulationTransactionsOnly"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.supportsDataManipulationTransactionsOnly();
  }

  public boolean supportsDifferentTableCorrelationNames() throws SQLException {
    if (shouldThrow("supportsDifferentTableCorrelationNames"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.supportsDifferentTableCorrelationNames();
  }

  public boolean supportsExpressionsInOrderBy() throws SQLException {
    if (shouldThrow("supportsExpressionsInOrderBy"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.supportsExpressionsInOrderBy();
  }

  public boolean supportsExtendedSQLGrammar() throws SQLException {
    if (shouldThrow("supportsExtendedSQLGrammar"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.supportsExtendedSQLGrammar();
  }

  public boolean supportsFullOuterJoins() throws SQLException {
    if (shouldThrow("supportsFullOuterJoins"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.supportsFullOuterJoins();
  }

  public boolean supportsGetGeneratedKeys() throws SQLException {
    if (shouldThrow("supportsGetGeneratedKeys"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.supportsGetGeneratedKeys();
  }

  public boolean supportsGroupBy() throws SQLException {
    if (shouldThrow("supportsGroupBy"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.supportsGroupBy();
  }

  public boolean supportsGroupByBeyondSelect() throws SQLException {
    if (shouldThrow("supportsGroupByBeyondSelect"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.supportsGroupByBeyondSelect();
  }

  public boolean supportsGroupByUnrelated() throws SQLException {
    if (shouldThrow("supportsGroupByUnrelated"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.supportsGroupByUnrelated();
  }

  public boolean supportsIntegrityEnhancementFacility() throws SQLException {
    if (shouldThrow("supportsIntegrityEnhancementFacility"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.supportsIntegrityEnhancementFacility();
  }

  public boolean supportsLikeEscapeClause() throws SQLException {
    if (shouldThrow("supportsLikeEscapeClause"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.supportsLikeEscapeClause();
  }

  public boolean supportsLimitedOuterJoins() throws SQLException {
    if (shouldThrow("supportsLimitedOuterJoins"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.supportsLimitedOuterJoins();
  }

  public boolean supportsMinimumSQLGrammar() throws SQLException {
    if (shouldThrow("supportsMinimumSQLGrammar"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.supportsMinimumSQLGrammar();
  }

  public boolean supportsMixedCaseIdentifiers() throws SQLException {
    if (shouldThrow("supportsMixedCaseIdentifiers"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.supportsMixedCaseIdentifiers();
  }

  public boolean supportsMixedCaseQuotedIdentifiers() throws SQLException {
    if (shouldThrow("supportsMixedCaseQuotedIdentifiers"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.supportsMixedCaseQuotedIdentifiers();
  }

  public boolean supportsMultipleOpenResults() throws SQLException {
    if (shouldThrow("supportsMultipleOpenResults"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.supportsMultipleOpenResults();
  }

  public boolean supportsMultipleResultSets() throws SQLException {
    if (shouldThrow("supportsMultipleResultSets"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.supportsMultipleResultSets();
  }

  public boolean supportsMultipleTransactions() throws SQLException {
    if (shouldThrow("supportsMultipleTransactions"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.supportsMultipleTransactions();
  }

  public boolean supportsNamedParameters() throws SQLException {
    if (shouldThrow("supportsNamedParameters"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.supportsNamedParameters();
  }

  public boolean supportsNonNullableColumns() throws SQLException {
    if (shouldThrow("supportsNonNullableColumns"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.supportsNonNullableColumns();
  }

  public boolean supportsOpenCursorsAcrossCommit() throws SQLException {
    if (shouldThrow("supportsOpenCursorsAcrossCommit"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.supportsOpenCursorsAcrossCommit();
  }

  public boolean supportsOpenCursorsAcrossRollback() throws SQLException {
    if (shouldThrow("supportsOpenCursorsAcrossRollback"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.supportsOpenCursorsAcrossRollback();
  }

  public boolean supportsOpenStatementsAcrossCommit() throws SQLException {
    if (shouldThrow("supportsOpenStatementsAcrossCommit"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.supportsOpenStatementsAcrossCommit();
  }

  public boolean supportsOpenStatementsAcrossRollback() throws SQLException {
    if (shouldThrow("supportsOpenStatementsAcrossRollback"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.supportsOpenStatementsAcrossRollback();
  }

  public boolean supportsOrderByUnrelated() throws SQLException {
    if (shouldThrow("supportsOrderByUnrelated"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.supportsOrderByUnrelated();
  }

  public boolean supportsOuterJoins() throws SQLException {
    if (shouldThrow("supportsOuterJoins"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.supportsOuterJoins();
  }

  public boolean supportsPositionedDelete() throws SQLException {
    if (shouldThrow("supportsPositionedDelete"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.supportsPositionedDelete();
  }

  public boolean supportsPositionedUpdate() throws SQLException {
    if (shouldThrow("supportsPositionedUpdate"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.supportsPositionedUpdate();
  }

  public boolean supportsResultSetConcurrency(int type, int concurrency) throws SQLException {
    if (shouldThrow("supportsResultSetConcurrency"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.supportsResultSetConcurrency(type, concurrency);
  }

  public boolean supportsResultSetHoldability(int holdability) throws SQLException {
    if (shouldThrow("supportsResultSetHoldability"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.supportsResultSetHoldability(holdability);
  }

  public boolean supportsResultSetType(int type) throws SQLException {
    if (shouldThrow("supportsResultSetType"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.supportsResultSetType(type);
  }

  public boolean supportsSavepoints() throws SQLException {
    if (shouldThrow("supportsSavepoints"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.supportsSavepoints();
  }

  public boolean supportsSchemasInDataManipulation() throws SQLException {
    if (shouldThrow("supportsSchemasInDataManipulation"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.supportsSchemasInDataManipulation();
  }

  public boolean supportsSchemasInIndexDefinitions() throws SQLException {
    if (shouldThrow("supportsSchemasInIndexDefinitions"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.supportsSchemasInIndexDefinitions();
  }

  public boolean supportsSchemasInPrivilegeDefinitions() throws SQLException {
    if (shouldThrow("supportsSchemasInPrivilegeDefinitions"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.supportsSchemasInPrivilegeDefinitions();
  }

  public boolean supportsSchemasInProcedureCalls() throws SQLException {
    if (shouldThrow("supportsSchemasInProcedureCalls"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.supportsSchemasInProcedureCalls();
  }

  public boolean supportsSchemasInTableDefinitions() throws SQLException {
    if (shouldThrow("supportsSchemasInTableDefinitions"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.supportsSchemasInTableDefinitions();
  }

  public boolean supportsSelectForUpdate() throws SQLException {
    if (shouldThrow("supportsSelectForUpdate"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.supportsSelectForUpdate();
  }

  public boolean supportsStatementPooling() throws SQLException {
    if (shouldThrow("supportsStatementPooling"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.supportsStatementPooling();
  }

  public boolean supportsStoredProcedures() throws SQLException {
    if (shouldThrow("supportsStoredProcedures"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.supportsStoredProcedures();
  }

  public boolean supportsSubqueriesInComparisons() throws SQLException {
    if (shouldThrow("supportsSubqueriesInComparisons"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.supportsSubqueriesInComparisons();
  }

  public boolean supportsSubqueriesInExists() throws SQLException {
    if (shouldThrow("supportsSubqueriesInExists"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.supportsSubqueriesInExists();
  }

  public boolean supportsSubqueriesInIns() throws SQLException {
    if (shouldThrow("supportsSubqueriesInIns"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.supportsSubqueriesInIns();
  }

  public boolean supportsSubqueriesInQuantifieds() throws SQLException {
    if (shouldThrow("supportsSubqueriesInQuantifieds"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.supportsSubqueriesInQuantifieds();
  }

  public boolean supportsTableCorrelationNames() throws SQLException {
    if (shouldThrow("supportsTableCorrelationNames"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.supportsTableCorrelationNames();
  }

  public boolean supportsTransactionIsolationLevel(int level) throws SQLException {
    if (shouldThrow("supportsTransactionIsolationLevel"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.supportsTransactionIsolationLevel(level);
  }

  public boolean supportsTransactions() throws SQLException {
    if (shouldThrow("supportsTransactions"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.supportsTransactions();
  }

  public boolean supportsUnion() throws SQLException {
    if (shouldThrow("supportsUnion"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.supportsUnion();
  }

  public boolean supportsUnionAll() throws SQLException {
    if (shouldThrow("supportsUnionAll"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.supportsUnionAll();
  }

  public boolean updatesAreDetected(int type) throws SQLException {
    if (shouldThrow("updatesAreDetected"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.updatesAreDetected(type);
  }

  public boolean usesLocalFilePerTable() throws SQLException {
    if (shouldThrow("usesLocalFilePerTable"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.usesLocalFilePerTable();
  }

  public boolean usesLocalFiles() throws SQLException {
    if (shouldThrow("usesLocalFiles"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.usesLocalFiles();
  }

  
  /**
   *  JDBC 4.0
   */
  
 
  /** 
   * {@inheritDoc}
   * @see java.sql.DatabaseMetaData#autoCommitFailureClosesAllResultSets()
   */
  public boolean autoCommitFailureClosesAllResultSets() throws SQLException {
    if (shouldThrow("autoCommitFailureClosesAllResultSets"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.autoCommitFailureClosesAllResultSets();
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.DatabaseMetaData#getClientInfoProperties()
   */
  public ResultSet getClientInfoProperties() throws SQLException {
    if (shouldThrow("getClientInfoProperties"))
      throw new SQLException("DatabaseMetaData bombed");
    return new ThrowingResultSet<Object>(it.getClientInfoProperties());
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.DatabaseMetaData#getFunctionColumns(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
   */
  public ResultSet getFunctionColumns(String catalog, String schemaPattern,
          String functionNamePattern, String columnNamePattern)
          throws SQLException {
    if (shouldThrow("getFunctionColumns"))
      throw new SQLException("DatabaseMetaData bombed");
    return  new ThrowingResultSet<Object>(it.getFunctionColumns(catalog, schemaPattern, functionNamePattern, catalog));
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.DatabaseMetaData#getFunctions(java.lang.String, java.lang.String, java.lang.String)
   */
  public ResultSet getFunctions(String catalog, String schemaPattern,
          String functionNamePattern) throws SQLException {
    if (shouldThrow("getFunctions"))
      throw new SQLException("DatabaseMetaData bombed");
    return  new ThrowingResultSet<Object>(it.getFunctions(catalog, schemaPattern, functionNamePattern));
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.DatabaseMetaData#getRowIdLifetime()
   */
  public RowIdLifetime getRowIdLifetime() throws SQLException {
    if (shouldThrow("getRowIdLifetime"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.getRowIdLifetime();
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.DatabaseMetaData#getSchemas(java.lang.String, java.lang.String)
   */
  public ResultSet getSchemas(String catalog, String schemaPattern)
          throws SQLException {
    if (shouldThrow("getSchemas"))
      throw new SQLException("DatabaseMetaData bombed");
    return  new ThrowingResultSet<Object>(it.getSchemas());
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.DatabaseMetaData#supportsStoredFunctionsUsingCallSyntax()
   */
  public boolean supportsStoredFunctionsUsingCallSyntax() throws SQLException {
    if (shouldThrow("supportsStoredFunctionsUsingCallSyntax"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.supportsStoredFunctionsUsingCallSyntax();
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.Wrapper#isWrapperFor(java.lang.Class)
   */
  public boolean isWrapperFor(Class<?> iface) throws SQLException {
    if (shouldThrow("isWrapperFor"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.isWrapperFor(iface);
  }
  /** 
   * {@inheritDoc}
   * @see java.sql.Wrapper#unwrap(java.lang.Class)
   */
  public <T> T unwrap(Class<T> iface) throws SQLException {
    if (shouldThrow("unwrap"))
      throw new SQLException("DatabaseMetaData bombed");
    return it.unwrap(iface);
  }
  


}
