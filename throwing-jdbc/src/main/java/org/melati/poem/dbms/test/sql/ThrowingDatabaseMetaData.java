package org.melati.poem.dbms.test.sql;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;


public class ThrowingDatabaseMetaData extends Thrower implements DatabaseMetaData {
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

  DatabaseMetaData databaseMetaData = null;

  public ThrowingDatabaseMetaData(DatabaseMetaData d) {
    this.databaseMetaData = d;
  }

  public boolean allProceduresAreCallable() throws SQLException {
    if (shouldThrow("allProceduresAreCallable"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.allProceduresAreCallable();
  }

  public boolean allTablesAreSelectable() throws SQLException {
    if (shouldThrow("allTablesAreSelectable"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.allTablesAreSelectable();
  }

  public boolean dataDefinitionCausesTransactionCommit() throws SQLException {
    if (shouldThrow("dataDefinitionCausesTransactionCommit"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.dataDefinitionCausesTransactionCommit();
  }

  public boolean dataDefinitionIgnoredInTransactions() throws SQLException {
    if (shouldThrow("dataDefinitionIgnoredInTransactions"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.dataDefinitionIgnoredInTransactions();
  }

  public boolean deletesAreDetected(int type) throws SQLException {
    if (shouldThrow("deletesAreDetected"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.deletesAreDetected(type);
  }

  public boolean doesMaxRowSizeIncludeBlobs() throws SQLException {
    if (shouldThrow("doesMaxRowSizeIncludeBlobs"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.doesMaxRowSizeIncludeBlobs();
  }

  public ResultSet getAttributes(String catalog, String schemaPattern, String typeNamePattern, String attributeNamePattern) throws SQLException {
    if (shouldThrow("getAttributes"))
      throw new SQLException("DatabaseMetaData bombed");
    return new ThrowingResultSet(databaseMetaData.getAttributes(catalog, schemaPattern, typeNamePattern, attributeNamePattern));
  }

  public ResultSet getBestRowIdentifier(String catalog, String schema, String table, int scope, boolean nullable) throws SQLException {
    if (shouldThrow("getBestRowIdentifier"))
      throw new SQLException("DatabaseMetaData bombed");
    return new ThrowingResultSet(databaseMetaData.getBestRowIdentifier(catalog, schema, table, scope, nullable));
  }

  public String getCatalogSeparator() throws SQLException {
    if (shouldThrow("getCatalogSeparator"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.getCatalogSeparator();
  }

  public String getCatalogTerm() throws SQLException {
    if (shouldThrow("getCatalogTerm"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.getCatalogTerm();
  }

  public ResultSet getCatalogs() throws SQLException {
    if (shouldThrow("getCatalogs"))
      throw new SQLException("DatabaseMetaData bombed");
    return new ThrowingResultSet(databaseMetaData.getCatalogs());
  }

  public ResultSet getColumnPrivileges(String catalog, String schema, String table, String columnNamePattern) throws SQLException {
    if (shouldThrow("getColumnPrivileges"))
      throw new SQLException("DatabaseMetaData bombed");
    return new ThrowingResultSet(databaseMetaData.getColumnPrivileges(catalog, schema, table, columnNamePattern));
  }

  public ResultSet getColumns(String catalog, String schemaPattern, String tableNamePattern, String columnNamePattern) throws SQLException {
    if (shouldThrow("getColumns"))
      throw new SQLException("DatabaseMetaData bombed");
    return new ThrowingResultSet(databaseMetaData.getColumns(catalog, schemaPattern, tableNamePattern, columnNamePattern));
  }

  public Connection getConnection() throws SQLException {
    if (shouldThrow("getConnection"))
      throw new SQLException("DatabaseMetaData bombed");
    return new ThrowingConnection(databaseMetaData.getConnection());
  }

  public ResultSet getCrossReference(String primaryCatalog, String primarySchema, String primaryTable, String foreignCatalog, String foreignSchema, String foreignTable) throws SQLException {
    if (shouldThrow("getCrossReference"))
      throw new SQLException("DatabaseMetaData bombed");
    return new ThrowingResultSet(databaseMetaData.getCrossReference(primaryCatalog, primarySchema, primaryTable, foreignCatalog, foreignSchema, foreignTable));
  }

  public int getDatabaseMajorVersion() throws SQLException {
    if (shouldThrow("getDatabaseMajorVersion"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.getDatabaseMajorVersion();
  }

  public int getDatabaseMinorVersion() throws SQLException {
    if (shouldThrow("getDatabaseMinorVersion"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.getDatabaseMinorVersion();
  }

  public String getDatabaseProductName() throws SQLException {
    if (shouldThrow("getDatabaseProductName"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.getDatabaseProductName();
  }

  public String getDatabaseProductVersion() throws SQLException {
    if (shouldThrow("getDatabaseProductVersion"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.getDatabaseProductVersion();
  }

  public int getDefaultTransactionIsolation() throws SQLException {
    if (shouldThrow("getDefaultTransactionIsolation"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.getDefaultTransactionIsolation();
  }

  public int getDriverMajorVersion() {
    return databaseMetaData.getDriverMajorVersion();
  }

  public int getDriverMinorVersion() {
    return databaseMetaData.getDriverMinorVersion();
  }

  public String getDriverName() throws SQLException {
    if (shouldThrow("getDriverName"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.getDriverName();
  }

  public String getDriverVersion() throws SQLException {
    if (shouldThrow("getDriverVersion"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.getDriverVersion();
  }

  public ResultSet getExportedKeys(String catalog, String schema, String table) throws SQLException {
    if (shouldThrow("getExportedKeys"))
      throw new SQLException("DatabaseMetaData bombed");
    return new ThrowingResultSet(databaseMetaData.getExportedKeys(catalog, schema, table));
  }

  public String getExtraNameCharacters() throws SQLException {
    if (shouldThrow("getExtraNameCharacters"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.getExtraNameCharacters();
  }

  public String getIdentifierQuoteString() throws SQLException {
    if (shouldThrow("getIdentifierQuoteString"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.getIdentifierQuoteString();
  }

  public ResultSet getImportedKeys(String catalog, String schema, String table) throws SQLException {
    if (shouldThrow("getImportedKeys"))
      throw new SQLException("DatabaseMetaData bombed");
    return new ThrowingResultSet(databaseMetaData.getImportedKeys(catalog, schema, table));
  }

  public ResultSet getIndexInfo(String catalog, String schema, String table, boolean unique, boolean approximate) throws SQLException {
    if (shouldThrow("getIndexInfo"))
      throw new SQLException("DatabaseMetaData bombed");
    return new ThrowingResultSet(databaseMetaData.getIndexInfo(catalog, schema, table, unique, approximate));
  }

  public int getJDBCMajorVersion() throws SQLException {
    if (shouldThrow("getJDBCMajorVersion"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.getJDBCMajorVersion();
  }

  public int getJDBCMinorVersion() throws SQLException {
    if (shouldThrow("getJDBCMinorVersion"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.getJDBCMinorVersion();
  }

  public int getMaxBinaryLiteralLength() throws SQLException {
    if (shouldThrow("getMaxBinaryLiteralLength"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.getMaxBinaryLiteralLength();
  }

  public int getMaxCatalogNameLength() throws SQLException {
    if (shouldThrow("getMaxCatalogNameLength"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.getMaxCatalogNameLength();
  }

  public int getMaxCharLiteralLength() throws SQLException {
    if (shouldThrow("getMaxCharLiteralLength"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.getMaxCharLiteralLength();
  }

  public int getMaxColumnNameLength() throws SQLException {
    if (shouldThrow("getMaxColumnNameLength"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.getMaxColumnNameLength();
  }

  public int getMaxColumnsInGroupBy() throws SQLException {
    if (shouldThrow("getMaxColumnsInGroupBy"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.getMaxColumnsInGroupBy();

  }

  public int getMaxColumnsInIndex() throws SQLException {
    if (shouldThrow("getMaxColumnsInIndex"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.getMaxColumnsInIndex();
  }

  public int getMaxColumnsInOrderBy() throws SQLException {
    if (shouldThrow("getMaxColumnsInOrderBy"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.getMaxColumnsInOrderBy();
  }

  public int getMaxColumnsInSelect() throws SQLException {
    if (shouldThrow("getMaxColumnsInSelect"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.getMaxColumnsInSelect();
  }

  public int getMaxColumnsInTable() throws SQLException {
    if (shouldThrow("getMaxColumnsInTable"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.getMaxColumnsInTable();
  }

  public int getMaxConnections() throws SQLException {
    if (shouldThrow("getMaxConnections"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.getMaxConnections();
  }

  public int getMaxCursorNameLength() throws SQLException {
    if (shouldThrow("getMaxCursorNameLength"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.getMaxCursorNameLength();
  }

  public int getMaxIndexLength() throws SQLException {
    if (shouldThrow("getMaxIndexLength"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.getMaxIndexLength();
  }

  public int getMaxProcedureNameLength() throws SQLException {
    if (shouldThrow("getMaxProcedureNameLength"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.getMaxProcedureNameLength();
  }

  public int getMaxRowSize() throws SQLException {
    if (shouldThrow("getMaxRowSize"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.getMaxRowSize();
  }

  public int getMaxSchemaNameLength() throws SQLException {
    if (shouldThrow("getMaxSchemaNameLength"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.getMaxSchemaNameLength();
  }

  public int getMaxStatementLength() throws SQLException {
    if (shouldThrow("getMaxStatementLength"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.getMaxStatementLength();
  }

  public int getMaxStatements() throws SQLException {
    if (shouldThrow("getMaxStatements"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.getMaxStatements();
  }

  public int getMaxTableNameLength() throws SQLException {
    if (shouldThrow("getMaxTableNameLength"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.getMaxTableNameLength();
  }

  public int getMaxTablesInSelect() throws SQLException {
    if (shouldThrow("getMaxTablesInSelect"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.getMaxTablesInSelect();
  }

  public int getMaxUserNameLength() throws SQLException {
    if (shouldThrow("getMaxUserNameLength"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.getMaxUserNameLength();
  }

  public String getNumericFunctions() throws SQLException {
    if (shouldThrow("getNumericFunctions"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.getNumericFunctions();
  }

  public ResultSet getPrimaryKeys(String catalog, String schema, String table) throws SQLException {
    if (shouldThrow("getPrimaryKeys"))
      throw new SQLException("DatabaseMetaData bombed");
    return new ThrowingResultSet(databaseMetaData.getPrimaryKeys(catalog, schema, table));
  }

  public ResultSet getProcedureColumns(String catalog, String schemaPattern, String procedureNamePattern, String columnNamePattern) throws SQLException {
    if (shouldThrow("getProcedureColumns"))
      throw new SQLException("DatabaseMetaData bombed");
    return new ThrowingResultSet(databaseMetaData.getProcedureColumns(catalog, schemaPattern, procedureNamePattern, columnNamePattern));
  }

  public String getProcedureTerm() throws SQLException {
    if (shouldThrow("getProcedureTerm"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.getProcedureTerm();
  }

  public ResultSet getProcedures(String catalog, String schemaPattern, String procedureNamePattern) throws SQLException {
    if (shouldThrow("getProcedures"))
      throw new SQLException("DatabaseMetaData bombed");
    return new ThrowingResultSet(databaseMetaData.getProcedures(catalog, schemaPattern, procedureNamePattern));
  }

  public int getResultSetHoldability() throws SQLException {
    if (shouldThrow("getResultSetHoldability"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.getResultSetHoldability();
  }

  public String getSQLKeywords() throws SQLException {
    if (shouldThrow("getSQLKeywords"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.getSQLKeywords();
  }

  public int getSQLStateType() throws SQLException {
    if (shouldThrow("getSQLStateType"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.getSQLStateType();
  }

  public String getSchemaTerm() throws SQLException {
    if (shouldThrow("getSchemaTerm"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.getSchemaTerm();
  }

  public ResultSet getSchemas() throws SQLException {
    if (shouldThrow("getSchemas"))
      throw new SQLException("DatabaseMetaData bombed");
    return new ThrowingResultSet(databaseMetaData.getSchemas());
  }

  public String getSearchStringEscape() throws SQLException {
    if (shouldThrow("getSearchStringEscape"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.getSearchStringEscape();
  }

  public String getStringFunctions() throws SQLException {
    if (shouldThrow("getStringFunctions"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.getStringFunctions();
  }

  public ResultSet getSuperTables(String catalog, String schemaPattern, String tableNamePattern) throws SQLException {
    if (shouldThrow("getSuperTables"))
      throw new SQLException("DatabaseMetaData bombed");
    return new ThrowingResultSet(databaseMetaData.getSuperTables(catalog, schemaPattern, tableNamePattern));
  }

  public ResultSet getSuperTypes(String catalog, String schemaPattern, String typeNamePattern) throws SQLException {
    if (shouldThrow("getSuperTypes"))
      throw new SQLException("DatabaseMetaData bombed");
    return new ThrowingResultSet(databaseMetaData.getSuperTypes(catalog, schemaPattern, typeNamePattern));
  }

  public String getSystemFunctions() throws SQLException {
    if (shouldThrow("getSystemFunctions"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.getSystemFunctions();
  }

  public ResultSet getTablePrivileges(String catalog, String schemaPattern, String tableNamePattern) throws SQLException {
    if (shouldThrow("getTablePrivileges"))
      throw new SQLException("DatabaseMetaData bombed");
    return new ThrowingResultSet(databaseMetaData.getTablePrivileges(catalog, schemaPattern, tableNamePattern));
  }

  public ResultSet getTableTypes() throws SQLException {
    if (shouldThrow("getTableTypes"))
      throw new SQLException("DatabaseMetaData bombed");
    return new ThrowingResultSet(databaseMetaData.getTableTypes());
  }

  public ResultSet getTables(String catalog, String schemaPattern, String tableNamePattern, String[] types) throws SQLException {
    if (shouldThrow("getTables"))
      throw new SQLException("DatabaseMetaData bombed");
    return new ThrowingResultSet(databaseMetaData.getTables(catalog, schemaPattern, tableNamePattern, types));
  }

  public String getTimeDateFunctions() throws SQLException {
    if (shouldThrow("getTimeDateFunctions"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.getTimeDateFunctions();
  }

  public ResultSet getTypeInfo() throws SQLException {
    if (shouldThrow("getTypeInfo"))
      throw new SQLException("DatabaseMetaData bombed");
    return new ThrowingResultSet(databaseMetaData.getTypeInfo());
  }

  public ResultSet getUDTs(String catalog, String schemaPattern, String typeNamePattern, int[] types) throws SQLException {
    if (shouldThrow("getUDTs"))
      throw new SQLException("DatabaseMetaData bombed");
    return new ThrowingResultSet(databaseMetaData.getUDTs(catalog, schemaPattern, typeNamePattern, types));
  }

  public String getURL() throws SQLException {
    if (shouldThrow("getURL"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.getURL();
  }

  public String getUserName() throws SQLException {
    if (shouldThrow("getUserName"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.getUserName();
  }

  public ResultSet getVersionColumns(String catalog, String schema, String table) throws SQLException {
    if (shouldThrow("getVersionColumns"))
      throw new SQLException("DatabaseMetaData bombed");
    return new ThrowingResultSet(databaseMetaData.getVersionColumns(catalog, schema, table));
  }

  public boolean insertsAreDetected(int type) throws SQLException {
    if (shouldThrow("insertsAreDetected"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.insertsAreDetected(type);
  }

  public boolean isCatalogAtStart() throws SQLException {
    if (shouldThrow("isCatalogAtStart"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.isCatalogAtStart();
  }

  public boolean isReadOnly() throws SQLException {
    if (shouldThrow("isReadOnly"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.isReadOnly();
  }

  public boolean locatorsUpdateCopy() throws SQLException {
    if (shouldThrow("locatorsUpdateCopy"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.locatorsUpdateCopy();
  }

  public boolean nullPlusNonNullIsNull() throws SQLException {
    if (shouldThrow("nullPlusNonNullIsNull"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.nullPlusNonNullIsNull();
  }

  public boolean nullsAreSortedAtEnd() throws SQLException {
    if (shouldThrow("nullsAreSortedAtEnd"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.nullsAreSortedAtEnd();
  }

  public boolean nullsAreSortedAtStart() throws SQLException {
    if (shouldThrow("nullsAreSortedAtStart"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.nullsAreSortedAtStart();
  }

  public boolean nullsAreSortedHigh() throws SQLException {
    if (shouldThrow("nullsAreSortedHigh"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.nullsAreSortedHigh();
  }

  public boolean nullsAreSortedLow() throws SQLException {
    if (shouldThrow("nullsAreSortedLow"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.nullsAreSortedLow();
  }

  public boolean othersDeletesAreVisible(int type) throws SQLException {
    if (shouldThrow("othersDeletesAreVisible"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.othersDeletesAreVisible(type);
  }

  public boolean othersInsertsAreVisible(int type) throws SQLException {
    if (shouldThrow("othersInsertsAreVisible"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.othersInsertsAreVisible(type);
  }

  public boolean othersUpdatesAreVisible(int type) throws SQLException {

    if (shouldThrow("othersUpdatesAreVisible"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.othersUpdatesAreVisible(type);
  }

  public boolean ownDeletesAreVisible(int type) throws SQLException {
    if (shouldThrow("ownDeletesAreVisible"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.ownDeletesAreVisible(type);
  }

  public boolean ownInsertsAreVisible(int type) throws SQLException {
    if (shouldThrow("ownInsertsAreVisible"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.ownInsertsAreVisible(type);
  }

  public boolean ownUpdatesAreVisible(int type) throws SQLException {
    if (shouldThrow("ownUpdatesAreVisible"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.ownUpdatesAreVisible(type);
  }

  public boolean storesLowerCaseIdentifiers() throws SQLException {
    if (shouldThrow("storesLowerCaseIdentifiers"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.storesLowerCaseIdentifiers();
  }

  public boolean storesLowerCaseQuotedIdentifiers() throws SQLException {
    if (shouldThrow("storesLowerCaseQuotedIdentifiers"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.storesLowerCaseQuotedIdentifiers();
  }

  public boolean storesMixedCaseIdentifiers() throws SQLException {
    if (shouldThrow("storesMixedCaseIdentifiers"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.storesMixedCaseIdentifiers();
  }

  public boolean storesMixedCaseQuotedIdentifiers() throws SQLException {
    if (shouldThrow("storesMixedCaseQuotedIdentifiers"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.storesMixedCaseQuotedIdentifiers();
  }

  public boolean storesUpperCaseIdentifiers() throws SQLException {
    if (shouldThrow("storesUpperCaseIdentifiers"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.storesUpperCaseIdentifiers();
  }

  public boolean storesUpperCaseQuotedIdentifiers() throws SQLException {
    if (shouldThrow("storesUpperCaseQuotedIdentifiers"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.storesUpperCaseQuotedIdentifiers();
  }

  public boolean supportsANSI92EntryLevelSQL() throws SQLException {
    if (shouldThrow("supportsANSI92EntryLevelSQL"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.supportsANSI92EntryLevelSQL();
  }

  public boolean supportsANSI92FullSQL() throws SQLException {
    if (shouldThrow("supportsANSI92FullSQL"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.supportsANSI92FullSQL();
  }

  public boolean supportsANSI92IntermediateSQL() throws SQLException {
    if (shouldThrow("supportsANSI92IntermediateSQL"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.supportsANSI92IntermediateSQL();
  }

  public boolean supportsAlterTableWithAddColumn() throws SQLException {
    if (shouldThrow("supportsAlterTableWithAddColumn"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.supportsAlterTableWithAddColumn();
  }

  public boolean supportsAlterTableWithDropColumn() throws SQLException {
    if (shouldThrow("supportsAlterTableWithDropColumn"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.supportsAlterTableWithDropColumn();
  }

  public boolean supportsBatchUpdates() throws SQLException {
    if (shouldThrow("supportsBatchUpdates"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.supportsBatchUpdates();
  }

  public boolean supportsCatalogsInDataManipulation() throws SQLException {
    if (shouldThrow("supportsCatalogsInDataManipulation"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.supportsCatalogsInDataManipulation();
  }

  public boolean supportsCatalogsInIndexDefinitions() throws SQLException {
    if (shouldThrow("supportsCatalogsInIndexDefinitions"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.supportsCatalogsInIndexDefinitions();
  }

  public boolean supportsCatalogsInPrivilegeDefinitions() throws SQLException {
    if (shouldThrow("supportsCatalogsInPrivilegeDefinitions"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.supportsCatalogsInPrivilegeDefinitions();
  }

  public boolean supportsCatalogsInProcedureCalls() throws SQLException {
    if (shouldThrow("supportsCatalogsInProcedureCalls"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.supportsCatalogsInProcedureCalls();
  }

  public boolean supportsCatalogsInTableDefinitions() throws SQLException {
    if (shouldThrow("supportsCatalogsInTableDefinitions"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.supportsCatalogsInTableDefinitions();
  }

  public boolean supportsColumnAliasing() throws SQLException {
    if (shouldThrow("supportsColumnAliasing"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.supportsColumnAliasing();
  }

  public boolean supportsConvert() throws SQLException {
    if (shouldThrow("supportsConvert"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.supportsConvert();
  }

  public boolean supportsConvert(int fromType, int toType) throws SQLException {
    if (shouldThrow("supportsConvert"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.supportsConvert();
  }

  public boolean supportsCoreSQLGrammar() throws SQLException {
    if (shouldThrow("supportsCoreSQLGrammar"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.supportsCoreSQLGrammar();
  }

  public boolean supportsCorrelatedSubqueries() throws SQLException {
    if (shouldThrow("supportsCorrelatedSubqueries"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.supportsCorrelatedSubqueries();
  }

  public boolean supportsDataDefinitionAndDataManipulationTransactions() throws SQLException {
    if (shouldThrow("supportsDataDefinitionAndDataManipulationTransactions"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.supportsDataDefinitionAndDataManipulationTransactions();
  }

  public boolean supportsDataManipulationTransactionsOnly() throws SQLException {
    if (shouldThrow("supportsDataManipulationTransactionsOnly"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.supportsDataManipulationTransactionsOnly();
  }

  public boolean supportsDifferentTableCorrelationNames() throws SQLException {
    if (shouldThrow("supportsDifferentTableCorrelationNames"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.supportsDifferentTableCorrelationNames();
  }

  public boolean supportsExpressionsInOrderBy() throws SQLException {
    if (shouldThrow("supportsExpressionsInOrderBy"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.supportsExpressionsInOrderBy();
  }

  public boolean supportsExtendedSQLGrammar() throws SQLException {
    if (shouldThrow("supportsExtendedSQLGrammar"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.supportsExtendedSQLGrammar();
  }

  public boolean supportsFullOuterJoins() throws SQLException {
    if (shouldThrow("supportsFullOuterJoins"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.supportsFullOuterJoins();
  }

  public boolean supportsGetGeneratedKeys() throws SQLException {
    if (shouldThrow("supportsGetGeneratedKeys"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.supportsGetGeneratedKeys();
  }

  public boolean supportsGroupBy() throws SQLException {
    if (shouldThrow("supportsGroupBy"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.supportsGroupBy();
  }

  public boolean supportsGroupByBeyondSelect() throws SQLException {
    if (shouldThrow("supportsGroupByBeyondSelect"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.supportsGroupByBeyondSelect();
  }

  public boolean supportsGroupByUnrelated() throws SQLException {
    if (shouldThrow("supportsGroupByUnrelated"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.supportsGroupByUnrelated();
  }

  public boolean supportsIntegrityEnhancementFacility() throws SQLException {
    if (shouldThrow("supportsIntegrityEnhancementFacility"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.supportsIntegrityEnhancementFacility();
  }

  public boolean supportsLikeEscapeClause() throws SQLException {
    if (shouldThrow("supportsLikeEscapeClause"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.supportsLikeEscapeClause();
  }

  public boolean supportsLimitedOuterJoins() throws SQLException {
    if (shouldThrow("supportsLimitedOuterJoins"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.supportsLimitedOuterJoins();
  }

  public boolean supportsMinimumSQLGrammar() throws SQLException {
    if (shouldThrow("supportsMinimumSQLGrammar"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.supportsMinimumSQLGrammar();
  }

  public boolean supportsMixedCaseIdentifiers() throws SQLException {
    if (shouldThrow("supportsMixedCaseIdentifiers"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.supportsMixedCaseIdentifiers();
  }

  public boolean supportsMixedCaseQuotedIdentifiers() throws SQLException {
    if (shouldThrow("supportsMixedCaseQuotedIdentifiers"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.supportsMixedCaseQuotedIdentifiers();
  }

  public boolean supportsMultipleOpenResults() throws SQLException {
    if (shouldThrow("supportsMultipleOpenResults"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.supportsMultipleOpenResults();
  }

  public boolean supportsMultipleResultSets() throws SQLException {
    if (shouldThrow("supportsMultipleResultSets"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.supportsMultipleResultSets();
  }

  public boolean supportsMultipleTransactions() throws SQLException {
    if (shouldThrow("supportsMultipleTransactions"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.supportsMultipleTransactions();
  }

  public boolean supportsNamedParameters() throws SQLException {
    if (shouldThrow("supportsNamedParameters"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.supportsNamedParameters();
  }

  public boolean supportsNonNullableColumns() throws SQLException {
    if (shouldThrow("supportsNonNullableColumns"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.supportsNonNullableColumns();
  }

  public boolean supportsOpenCursorsAcrossCommit() throws SQLException {
    if (shouldThrow("supportsOpenCursorsAcrossCommit"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.supportsOpenCursorsAcrossCommit();
  }

  public boolean supportsOpenCursorsAcrossRollback() throws SQLException {
    if (shouldThrow("supportsOpenCursorsAcrossRollback"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.supportsOpenCursorsAcrossRollback();
  }

  public boolean supportsOpenStatementsAcrossCommit() throws SQLException {
    if (shouldThrow("supportsOpenStatementsAcrossCommit"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.supportsOpenStatementsAcrossCommit();
  }

  public boolean supportsOpenStatementsAcrossRollback() throws SQLException {
    if (shouldThrow("supportsOpenStatementsAcrossRollback"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.supportsOpenStatementsAcrossRollback();
  }

  public boolean supportsOrderByUnrelated() throws SQLException {
    if (shouldThrow("supportsOrderByUnrelated"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.supportsOrderByUnrelated();
  }

  public boolean supportsOuterJoins() throws SQLException {
    if (shouldThrow("supportsOuterJoins"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.supportsOuterJoins();
  }

  public boolean supportsPositionedDelete() throws SQLException {
    if (shouldThrow("supportsPositionedDelete"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.supportsPositionedDelete();
  }

  public boolean supportsPositionedUpdate() throws SQLException {
    if (shouldThrow("supportsPositionedUpdate"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.supportsPositionedUpdate();
  }

  public boolean supportsResultSetConcurrency(int type, int concurrency) throws SQLException {
    if (shouldThrow("supportsResultSetConcurrency"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.supportsResultSetConcurrency(type, concurrency);
  }

  public boolean supportsResultSetHoldability(int holdability) throws SQLException {
    if (shouldThrow("supportsResultSetHoldability"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.supportsResultSetHoldability(holdability);
  }

  public boolean supportsResultSetType(int type) throws SQLException {
    if (shouldThrow("supportsResultSetType"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.supportsResultSetType(type);
  }

  public boolean supportsSavepoints() throws SQLException {
    if (shouldThrow("supportsSavepoints"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.supportsSavepoints();
  }

  public boolean supportsSchemasInDataManipulation() throws SQLException {
    if (shouldThrow("supportsSchemasInDataManipulation"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.supportsSchemasInDataManipulation();
  }

  public boolean supportsSchemasInIndexDefinitions() throws SQLException {
    if (shouldThrow("supportsSchemasInIndexDefinitions"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.supportsSchemasInIndexDefinitions();
  }

  public boolean supportsSchemasInPrivilegeDefinitions() throws SQLException {
    if (shouldThrow("supportsSchemasInPrivilegeDefinitions"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.supportsSchemasInPrivilegeDefinitions();
  }

  public boolean supportsSchemasInProcedureCalls() throws SQLException {
    if (shouldThrow("supportsSchemasInProcedureCalls"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.supportsSchemasInProcedureCalls();
  }

  public boolean supportsSchemasInTableDefinitions() throws SQLException {
    if (shouldThrow("supportsSchemasInTableDefinitions"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.supportsSchemasInTableDefinitions();
  }

  public boolean supportsSelectForUpdate() throws SQLException {
    if (shouldThrow("supportsSelectForUpdate"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.supportsSelectForUpdate();
  }

  public boolean supportsStatementPooling() throws SQLException {
    if (shouldThrow("supportsStatementPooling"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.supportsStatementPooling();
  }

  public boolean supportsStoredProcedures() throws SQLException {
    if (shouldThrow("supportsStoredProcedures"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.supportsStoredProcedures();
  }

  public boolean supportsSubqueriesInComparisons() throws SQLException {
    if (shouldThrow("supportsSubqueriesInComparisons"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.supportsSubqueriesInComparisons();
  }

  public boolean supportsSubqueriesInExists() throws SQLException {
    if (shouldThrow("supportsSubqueriesInExists"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.supportsSubqueriesInExists();
  }

  public boolean supportsSubqueriesInIns() throws SQLException {
    if (shouldThrow("supportsSubqueriesInIns"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.supportsSubqueriesInIns();
  }

  public boolean supportsSubqueriesInQuantifieds() throws SQLException {
    if (shouldThrow("supportsSubqueriesInQuantifieds"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.supportsSubqueriesInQuantifieds();
  }

  public boolean supportsTableCorrelationNames() throws SQLException {
    if (shouldThrow("supportsTableCorrelationNames"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.supportsTableCorrelationNames();
  }

  public boolean supportsTransactionIsolationLevel(int level) throws SQLException {
    if (shouldThrow("supportsTransactionIsolationLevel"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.supportsTransactionIsolationLevel(level);
  }

  public boolean supportsTransactions() throws SQLException {
    if (shouldThrow("supportsTransactions"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.supportsTransactions();
  }

  public boolean supportsUnion() throws SQLException {
    if (shouldThrow("supportsUnion"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.supportsUnion();
  }

  public boolean supportsUnionAll() throws SQLException {
    if (shouldThrow("supportsUnionAll"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.supportsUnionAll();
  }

  public boolean updatesAreDetected(int type) throws SQLException {
    if (shouldThrow("updatesAreDetected"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.updatesAreDetected(type);
  }

  public boolean usesLocalFilePerTable() throws SQLException {
    if (shouldThrow("usesLocalFilePerTable"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.usesLocalFilePerTable();
  }

  public boolean usesLocalFiles() throws SQLException {
    if (shouldThrow("usesLocalFiles"))
      throw new SQLException("DatabaseMetaData bombed");
    return databaseMetaData.usesLocalFiles();
  }
}
