/*
 * $Source$
 * $Revision$
 *
 * Part of Melati (http://melati.org), a framework for the rapid
 * development of clean, maintainable web applications.
 *
 * -------------------------------------
 *  Copyright (C) 2000 William Chesters
 * -------------------------------------
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 * A copy of the GPL should be in the file org/melati/COPYING in this tree.
 * Or see http://melati.org/License.html.
 *
 * Contact details for copyright holder:
 *
 *     William Chesters <williamc@paneris.org>
 *     http://paneris.org/~williamc
 *     Obrechtstraat 114, 2517VX Den Haag, The Netherlands
 *
 *
 * ------
 *  Note
 * ------
 *
 * I will assign copyright to PanEris (http://paneris.org) as soon as
 * we have sorted out what sort of legal existence we need to have for
 * that to make sense.  When WebMacro's "Simple Public License" is
 * finalised, we'll offer it as an alternative license for Melati.
 * In the meantime, if you want to use WebMacro on non-GPL terms,
 * contact me!
 */

package org.melati.poem.sql.jdbc2;

import java.sql.*;

public class DelegatedDatabaseMetaData implements DatabaseMetaData {
  protected DatabaseMetaData peer;
  public DelegatedDatabaseMetaData(DatabaseMetaData peer) {
    this.peer = peer;
  }
  public boolean allProceduresAreCallable() throws java.sql.SQLException {
    return peer.allProceduresAreCallable();
  }
  public boolean allTablesAreSelectable() throws java.sql.SQLException {
    return peer.allTablesAreSelectable();
  }
  public boolean dataDefinitionCausesTransactionCommit() throws java.sql.SQLException {
    return peer.dataDefinitionCausesTransactionCommit();
  }
  public boolean dataDefinitionIgnoredInTransactions() throws java.sql.SQLException {
    return peer.dataDefinitionIgnoredInTransactions();
  }
  public boolean deletesAreDetected(int a) throws java.sql.SQLException {
    return peer.deletesAreDetected(a);
  }
  public boolean doesMaxRowSizeIncludeBlobs() throws java.sql.SQLException {
    return peer.doesMaxRowSizeIncludeBlobs();
  }
  public java.sql.ResultSet getBestRowIdentifier(java.lang.String a, java.lang.String b, java.lang.String c, int d, boolean e) throws java.sql.SQLException {
    return peer.getBestRowIdentifier(a, b, c, d, e);
  }
  public java.lang.String getCatalogSeparator() throws java.sql.SQLException {
    return peer.getCatalogSeparator();
  }
  public java.lang.String getCatalogTerm() throws java.sql.SQLException {
    return peer.getCatalogTerm();
  }
  public java.sql.ResultSet getCatalogs() throws java.sql.SQLException {
    return peer.getCatalogs();
  }
  public java.sql.ResultSet getColumnPrivileges(java.lang.String a, java.lang.String b, java.lang.String c, java.lang.String d) throws java.sql.SQLException {
    return peer.getColumnPrivileges(a, b, c, d);
  }
  public java.sql.ResultSet getColumns(java.lang.String a, java.lang.String b, java.lang.String c, java.lang.String d) throws java.sql.SQLException {
    return peer.getColumns(a, b, c, d);
  }
  public java.sql.Connection getConnection() throws java.sql.SQLException {
    return peer.getConnection();
  }
  public java.sql.ResultSet getCrossReference(java.lang.String a, java.lang.String b, java.lang.String c, java.lang.String d, java.lang.String e, java.lang.String f) throws java.sql.SQLException {
    return peer.getCrossReference(a, b, c, d, e, f);
  }
  public java.lang.String getDatabaseProductName() throws java.sql.SQLException {
    return peer.getDatabaseProductName();
  }
  public java.lang.String getDatabaseProductVersion() throws java.sql.SQLException {
    return peer.getDatabaseProductVersion();
  }
  public int getDefaultTransactionIsolation() throws java.sql.SQLException {
    return peer.getDefaultTransactionIsolation();
  }
  public int getDriverMajorVersion() {
    return peer.getDriverMajorVersion();
  }
  public int getDriverMinorVersion() {
    return peer.getDriverMinorVersion();
  }
  public java.lang.String getDriverName() throws java.sql.SQLException {
    return peer.getDriverName();
  }
  public java.lang.String getDriverVersion() throws java.sql.SQLException {
    return peer.getDriverVersion();
  }
  public java.sql.ResultSet getExportedKeys(java.lang.String a, java.lang.String b, java.lang.String c) throws java.sql.SQLException {
    return peer.getExportedKeys(a, b, c);
  }
  public java.lang.String getExtraNameCharacters() throws java.sql.SQLException {
    return peer.getExtraNameCharacters();
  }
  public java.lang.String getIdentifierQuoteString() throws java.sql.SQLException {
    return peer.getIdentifierQuoteString();
  }
  public java.sql.ResultSet getImportedKeys(java.lang.String a, java.lang.String b, java.lang.String c) throws java.sql.SQLException {
    return peer.getImportedKeys(a, b, c);
  }
  public java.sql.ResultSet getIndexInfo(java.lang.String a, java.lang.String b, java.lang.String c, boolean d, boolean e) throws java.sql.SQLException {
    return peer.getIndexInfo(a, b, c, d, e);
  }
  public int getMaxBinaryLiteralLength() throws java.sql.SQLException {
    return peer.getMaxBinaryLiteralLength();
  }
  public int getMaxCatalogNameLength() throws java.sql.SQLException {
    return peer.getMaxCatalogNameLength();
  }
  public int getMaxCharLiteralLength() throws java.sql.SQLException {
    return peer.getMaxCharLiteralLength();
  }
  public int getMaxColumnNameLength() throws java.sql.SQLException {
    return peer.getMaxColumnNameLength();
  }
  public int getMaxColumnsInGroupBy() throws java.sql.SQLException {
    return peer.getMaxColumnsInGroupBy();
  }
  public int getMaxColumnsInIndex() throws java.sql.SQLException {
    return peer.getMaxColumnsInIndex();
  }
  public int getMaxColumnsInOrderBy() throws java.sql.SQLException {
    return peer.getMaxColumnsInOrderBy();
  }
  public int getMaxColumnsInSelect() throws java.sql.SQLException {
    return peer.getMaxColumnsInSelect();
  }
  public int getMaxColumnsInTable() throws java.sql.SQLException {
    return peer.getMaxColumnsInTable();
  }
  public int getMaxConnections() throws java.sql.SQLException {
    return peer.getMaxConnections();
  }
  public int getMaxCursorNameLength() throws java.sql.SQLException {
    return peer.getMaxCursorNameLength();
  }
  public int getMaxIndexLength() throws java.sql.SQLException {
    return peer.getMaxIndexLength();
  }
  public int getMaxProcedureNameLength() throws java.sql.SQLException {
    return peer.getMaxProcedureNameLength();
  }
  public int getMaxRowSize() throws java.sql.SQLException {
    return peer.getMaxRowSize();
  }
  public int getMaxSchemaNameLength() throws java.sql.SQLException {
    return peer.getMaxSchemaNameLength();
  }
  public int getMaxStatementLength() throws java.sql.SQLException {
    return peer.getMaxStatementLength();
  }
  public int getMaxStatements() throws java.sql.SQLException {
    return peer.getMaxStatements();
  }
  public int getMaxTableNameLength() throws java.sql.SQLException {
    return peer.getMaxTableNameLength();
  }
  public int getMaxTablesInSelect() throws java.sql.SQLException {
    return peer.getMaxTablesInSelect();
  }
  public int getMaxUserNameLength() throws java.sql.SQLException {
    return peer.getMaxUserNameLength();
  }
  public java.lang.String getNumericFunctions() throws java.sql.SQLException {
    return peer.getNumericFunctions();
  }
  public java.sql.ResultSet getPrimaryKeys(java.lang.String a, java.lang.String b, java.lang.String c) throws java.sql.SQLException {
    return peer.getPrimaryKeys(a, b, c);
  }
  public java.sql.ResultSet getProcedureColumns(java.lang.String a, java.lang.String b, java.lang.String c, java.lang.String d) throws java.sql.SQLException {
    return peer.getProcedureColumns(a, b, c, d);
  }
  public java.lang.String getProcedureTerm() throws java.sql.SQLException {
    return peer.getProcedureTerm();
  }
  public java.sql.ResultSet getProcedures(java.lang.String a, java.lang.String b, java.lang.String c) throws java.sql.SQLException {
    return peer.getProcedures(a, b, c);
  }
  public java.lang.String getSQLKeywords() throws java.sql.SQLException {
    return peer.getSQLKeywords();
  }
  public java.lang.String getSchemaTerm() throws java.sql.SQLException {
    return peer.getSchemaTerm();
  }
  public java.sql.ResultSet getSchemas() throws java.sql.SQLException {
    return peer.getSchemas();
  }
  public java.lang.String getSearchStringEscape() throws java.sql.SQLException {
    return peer.getSearchStringEscape();
  }
  public java.lang.String getStringFunctions() throws java.sql.SQLException {
    return peer.getStringFunctions();
  }
  public java.lang.String getSystemFunctions() throws java.sql.SQLException {
    return peer.getSystemFunctions();
  }
  public java.sql.ResultSet getTablePrivileges(java.lang.String a, java.lang.String b, java.lang.String c) throws java.sql.SQLException {
    return peer.getTablePrivileges(a, b, c);
  }
  public java.sql.ResultSet getTableTypes() throws java.sql.SQLException {
    return peer.getTableTypes();
  }
  public java.sql.ResultSet getTables(java.lang.String a, java.lang.String b, java.lang.String c, java.lang.String[] d) throws java.sql.SQLException {
    return peer.getTables(a, b, c, d);
  }
  public java.lang.String getTimeDateFunctions() throws java.sql.SQLException {
    return peer.getTimeDateFunctions();
  }
  public java.sql.ResultSet getTypeInfo() throws java.sql.SQLException {
    return peer.getTypeInfo();
  }
  public java.sql.ResultSet getUDTs(java.lang.String a, java.lang.String b, java.lang.String c, int[] d) throws java.sql.SQLException {
    return peer.getUDTs(a, b, c, d);
  }
  public java.lang.String getURL() throws java.sql.SQLException {
    return peer.getURL();
  }
  public java.lang.String getUserName() throws java.sql.SQLException {
    return peer.getUserName();
  }
  public java.sql.ResultSet getVersionColumns(java.lang.String a, java.lang.String b, java.lang.String c) throws java.sql.SQLException {
    return peer.getVersionColumns(a, b, c);
  }
  public boolean insertsAreDetected(int a) throws java.sql.SQLException {
    return peer.insertsAreDetected(a);
  }
  public boolean isCatalogAtStart() throws java.sql.SQLException {
    return peer.isCatalogAtStart();
  }
  public boolean isReadOnly() throws java.sql.SQLException {
    return peer.isReadOnly();
  }
  public boolean nullPlusNonNullIsNull() throws java.sql.SQLException {
    return peer.nullPlusNonNullIsNull();
  }
  public boolean nullsAreSortedAtEnd() throws java.sql.SQLException {
    return peer.nullsAreSortedAtEnd();
  }
  public boolean nullsAreSortedAtStart() throws java.sql.SQLException {
    return peer.nullsAreSortedAtStart();
  }
  public boolean nullsAreSortedHigh() throws java.sql.SQLException {
    return peer.nullsAreSortedHigh();
  }
  public boolean nullsAreSortedLow() throws java.sql.SQLException {
    return peer.nullsAreSortedLow();
  }
  public boolean othersDeletesAreVisible(int a) throws java.sql.SQLException {
    return peer.othersDeletesAreVisible(a);
  }
  public boolean othersInsertsAreVisible(int a) throws java.sql.SQLException {
    return peer.othersInsertsAreVisible(a);
  }
  public boolean othersUpdatesAreVisible(int a) throws java.sql.SQLException {
    return peer.othersUpdatesAreVisible(a);
  }
  public boolean ownDeletesAreVisible(int a) throws java.sql.SQLException {
    return peer.ownDeletesAreVisible(a);
  }
  public boolean ownInsertsAreVisible(int a) throws java.sql.SQLException {
    return peer.ownInsertsAreVisible(a);
  }
  public boolean ownUpdatesAreVisible(int a) throws java.sql.SQLException {
    return peer.ownUpdatesAreVisible(a);
  }
  public boolean storesLowerCaseIdentifiers() throws java.sql.SQLException {
    return peer.storesLowerCaseIdentifiers();
  }
  public boolean storesLowerCaseQuotedIdentifiers() throws java.sql.SQLException {
    return peer.storesLowerCaseQuotedIdentifiers();
  }
  public boolean storesMixedCaseIdentifiers() throws java.sql.SQLException {
    return peer.storesMixedCaseIdentifiers();
  }
  public boolean storesMixedCaseQuotedIdentifiers() throws java.sql.SQLException {
    return peer.storesMixedCaseQuotedIdentifiers();
  }
  public boolean storesUpperCaseIdentifiers() throws java.sql.SQLException {
    return peer.storesUpperCaseIdentifiers();
  }
  public boolean storesUpperCaseQuotedIdentifiers() throws java.sql.SQLException {
    return peer.storesUpperCaseQuotedIdentifiers();
  }
  public boolean supportsANSI92EntryLevelSQL() throws java.sql.SQLException {
    return peer.supportsANSI92EntryLevelSQL();
  }
  public boolean supportsANSI92FullSQL() throws java.sql.SQLException {
    return peer.supportsANSI92FullSQL();
  }
  public boolean supportsANSI92IntermediateSQL() throws java.sql.SQLException {
    return peer.supportsANSI92IntermediateSQL();
  }
  public boolean supportsAlterTableWithAddColumn() throws java.sql.SQLException {
    return peer.supportsAlterTableWithAddColumn();
  }
  public boolean supportsAlterTableWithDropColumn() throws java.sql.SQLException {
    return peer.supportsAlterTableWithDropColumn();
  }
  public boolean supportsBatchUpdates() throws java.sql.SQLException {
    return peer.supportsBatchUpdates();
  }
  public boolean supportsCatalogsInDataManipulation() throws java.sql.SQLException {
    return peer.supportsCatalogsInDataManipulation();
  }
  public boolean supportsCatalogsInIndexDefinitions() throws java.sql.SQLException {
    return peer.supportsCatalogsInIndexDefinitions();
  }
  public boolean supportsCatalogsInPrivilegeDefinitions() throws java.sql.SQLException {
    return peer.supportsCatalogsInPrivilegeDefinitions();
  }
  public boolean supportsCatalogsInProcedureCalls() throws java.sql.SQLException {
    return peer.supportsCatalogsInProcedureCalls();
  }
  public boolean supportsCatalogsInTableDefinitions() throws java.sql.SQLException {
    return peer.supportsCatalogsInTableDefinitions();
  }
  public boolean supportsColumnAliasing() throws java.sql.SQLException {
    return peer.supportsColumnAliasing();
  }
  public boolean supportsConvert() throws java.sql.SQLException {
    return peer.supportsConvert();
  }
  public boolean supportsConvert(int a, int b) throws java.sql.SQLException {
    return peer.supportsConvert(a, b);
  }
  public boolean supportsCoreSQLGrammar() throws java.sql.SQLException {
    return peer.supportsCoreSQLGrammar();
  }
  public boolean supportsCorrelatedSubqueries() throws java.sql.SQLException {
    return peer.supportsCorrelatedSubqueries();
  }
  public boolean supportsDataDefinitionAndDataManipulationTransactions() throws java.sql.SQLException {
    return peer.supportsDataDefinitionAndDataManipulationTransactions();
  }
  public boolean supportsDataManipulationTransactionsOnly() throws java.sql.SQLException {
    return peer.supportsDataManipulationTransactionsOnly();
  }
  public boolean supportsDifferentTableCorrelationNames() throws java.sql.SQLException {
    return peer.supportsDifferentTableCorrelationNames();
  }
  public boolean supportsExpressionsInOrderBy() throws java.sql.SQLException {
    return peer.supportsExpressionsInOrderBy();
  }
  public boolean supportsExtendedSQLGrammar() throws java.sql.SQLException {
    return peer.supportsExtendedSQLGrammar();
  }
  public boolean supportsFullOuterJoins() throws java.sql.SQLException {
    return peer.supportsFullOuterJoins();
  }
  public boolean supportsGroupBy() throws java.sql.SQLException {
    return peer.supportsGroupBy();
  }
  public boolean supportsGroupByBeyondSelect() throws java.sql.SQLException {
    return peer.supportsGroupByBeyondSelect();
  }
  public boolean supportsGroupByUnrelated() throws java.sql.SQLException {
    return peer.supportsGroupByUnrelated();
  }
  public boolean supportsIntegrityEnhancementFacility() throws java.sql.SQLException {
    return peer.supportsIntegrityEnhancementFacility();
  }
  public boolean supportsLikeEscapeClause() throws java.sql.SQLException {
    return peer.supportsLikeEscapeClause();
  }
  public boolean supportsLimitedOuterJoins() throws java.sql.SQLException {
    return peer.supportsLimitedOuterJoins();
  }
  public boolean supportsMinimumSQLGrammar() throws java.sql.SQLException {
    return peer.supportsMinimumSQLGrammar();
  }
  public boolean supportsMixedCaseIdentifiers() throws java.sql.SQLException {
    return peer.supportsMixedCaseIdentifiers();
  }
  public boolean supportsMixedCaseQuotedIdentifiers() throws java.sql.SQLException {
    return peer.supportsMixedCaseQuotedIdentifiers();
  }
  public boolean supportsMultipleResultSets() throws java.sql.SQLException {
    return peer.supportsMultipleResultSets();
  }
  public boolean supportsMultipleTransactions() throws java.sql.SQLException {
    return peer.supportsMultipleTransactions();
  }
  public boolean supportsNonNullableColumns() throws java.sql.SQLException {
    return peer.supportsNonNullableColumns();
  }
  public boolean supportsOpenCursorsAcrossCommit() throws java.sql.SQLException {
    return peer.supportsOpenCursorsAcrossCommit();
  }
  public boolean supportsOpenCursorsAcrossRollback() throws java.sql.SQLException {
    return peer.supportsOpenCursorsAcrossRollback();
  }
  public boolean supportsOpenStatementsAcrossCommit() throws java.sql.SQLException {
    return peer.supportsOpenStatementsAcrossCommit();
  }
  public boolean supportsOpenStatementsAcrossRollback() throws java.sql.SQLException {
    return peer.supportsOpenStatementsAcrossRollback();
  }
  public boolean supportsOrderByUnrelated() throws java.sql.SQLException {
    return peer.supportsOrderByUnrelated();
  }
  public boolean supportsOuterJoins() throws java.sql.SQLException {
    return peer.supportsOuterJoins();
  }
  public boolean supportsPositionedDelete() throws java.sql.SQLException {
    return peer.supportsPositionedDelete();
  }
  public boolean supportsPositionedUpdate() throws java.sql.SQLException {
    return peer.supportsPositionedUpdate();
  }
  public boolean supportsResultSetConcurrency(int a, int b) throws java.sql.SQLException {
    return peer.supportsResultSetConcurrency(a, b);
  }
  public boolean supportsResultSetType(int a) throws java.sql.SQLException {
    return peer.supportsResultSetType(a);
  }
  public boolean supportsSchemasInDataManipulation() throws java.sql.SQLException {
    return peer.supportsSchemasInDataManipulation();
  }
  public boolean supportsSchemasInIndexDefinitions() throws java.sql.SQLException {
    return peer.supportsSchemasInIndexDefinitions();
  }
  public boolean supportsSchemasInPrivilegeDefinitions() throws java.sql.SQLException {
    return peer.supportsSchemasInPrivilegeDefinitions();
  }
  public boolean supportsSchemasInProcedureCalls() throws java.sql.SQLException {
    return peer.supportsSchemasInProcedureCalls();
  }
  public boolean supportsSchemasInTableDefinitions() throws java.sql.SQLException {
    return peer.supportsSchemasInTableDefinitions();
  }
  public boolean supportsSelectForUpdate() throws java.sql.SQLException {
    return peer.supportsSelectForUpdate();
  }
  public boolean supportsStoredProcedures() throws java.sql.SQLException {
    return peer.supportsStoredProcedures();
  }
  public boolean supportsSubqueriesInComparisons() throws java.sql.SQLException {
    return peer.supportsSubqueriesInComparisons();
  }
  public boolean supportsSubqueriesInExists() throws java.sql.SQLException {
    return peer.supportsSubqueriesInExists();
  }
  public boolean supportsSubqueriesInIns() throws java.sql.SQLException {
    return peer.supportsSubqueriesInIns();
  }
  public boolean supportsSubqueriesInQuantifieds() throws java.sql.SQLException {
    return peer.supportsSubqueriesInQuantifieds();
  }
  public boolean supportsTableCorrelationNames() throws java.sql.SQLException {
    return peer.supportsTableCorrelationNames();
  }
  public boolean supportsTransactionIsolationLevel(int a) throws java.sql.SQLException {
    return peer.supportsTransactionIsolationLevel(a);
  }
  public boolean supportsTransactions() throws java.sql.SQLException {
    return peer.supportsTransactions();
  }
  public boolean supportsUnion() throws java.sql.SQLException {
    return peer.supportsUnion();
  }
  public boolean supportsUnionAll() throws java.sql.SQLException {
    return peer.supportsUnionAll();
  }
  public boolean updatesAreDetected(int a) throws java.sql.SQLException {
    return peer.updatesAreDetected(a);
  }
  public boolean usesLocalFilePerTable() throws java.sql.SQLException {
    return peer.usesLocalFilePerTable();
  }
  public boolean usesLocalFiles() throws java.sql.SQLException {
    return peer.usesLocalFiles();
  }
}
