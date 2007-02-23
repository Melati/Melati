/**
 * 
 */
package org.melati.poem.test.throwing;

import org.melati.poem.Database;
import org.melati.poem.ExecutingSQLPoemException;
import org.melati.poem.FieldContentsPoemException;
import org.melati.poem.PoemDatabaseFactory;
import org.melati.poem.SQLSeriousPoemException;
import org.melati.poem.SimpleRetrievalFailedPoemException;
import org.melati.poem.dbms.test.sql.ThrowingConnection;
import org.melati.poem.dbms.test.sql.ThrowingPreparedStatement;
import org.melati.poem.dbms.test.sql.ThrowingResultSet;

/**
 * @author timp
 * @since 22 Feb 2007
 *
 */
public class TableTest extends org.melati.poem.test.TableTest {

  /**
   * @param arg0
   */
  public TableTest(String arg0) {
    super(arg0);
  }

  protected void setUp() throws Exception {
    PoemDatabaseFactory.removeDatabase(databaseName);
    super.setUp();
    assertEquals("org.melati.poem.dbms.test.HsqldbThrower",getDb().getDbms().getClass().getName());
  }

  protected void tearDown() throws Exception {
     super.tearDown();
    PoemDatabaseFactory.removeDatabase(databaseName);
  }

  public Database getDatabase(String name) {
    maxTrans = 4;
    Database db = PoemDatabaseFactory.getDatabase(name, 
        "jdbc:hsqldb:mem:" + name,
        "sa", 
        "",
        "org.melati.poem.PoemDatabase",
        "org.melati.poem.dbms.test.HsqldbThrower", 
        false, 
        false, 
        false, maxTrans);
    return db;
  }

  public void test_getColumn() {
    
    // super.test_getColumn();
  }

  public void test_newPersistent() {
    
    // super.test_newPersistent();
  }

  public void testAddColumnAndCommitBigDecimal() {
    
    // super.testAddColumnAndCommitBigDecimal();
  }

  public void testAddColumnAndCommitBinary() {
    
    // super.testAddColumnAndCommitBinary();
  }

  public void testAddColumnAndCommitBoolean() {
    
    // super.testAddColumnAndCommitBoolean();
  }

  public void testAddColumnAndCommitDate() {
    
    // super.testAddColumnAndCommitDate();
  }

  public void testAddColumnAndCommitDeleted() throws Exception {
    
    // super.testAddColumnAndCommitDeleted();
  }

  public void testAddColumnAndCommitDisplaylevel() {
    
    // super.testAddColumnAndCommitDisplaylevel();
  }

  public void testAddColumnAndCommitDouble() {
    
    // super.testAddColumnAndCommitDouble();
  }

  public void testAddColumnAndCommitInteger() {
    
    // super.testAddColumnAndCommitInteger();
  }

  public void testAddColumnAndCommitIntegrityfix() {
    
    // super.testAddColumnAndCommitIntegrityfix();
  }

  public void testAddColumnAndCommitLong() {
    
    // super.testAddColumnAndCommitLong();
  }

  public void testAddColumnAndCommitNullableInteger() {
    
    // super.testAddColumnAndCommitNullableInteger();
  }

  public void testAddColumnAndCommitPassword() {
    
    // super.testAddColumnAndCommitPassword();
  }

  public void testAddColumnAndCommitSearchability() {
    
    // super.testAddColumnAndCommitSearchability();
  }

  public void testAddColumnAndCommitString() {
    
    // super.testAddColumnAndCommitString();
  }

  public void testAddColumnAndCommitTimestamp() {
    
    // super.testAddColumnAndCommitTimestamp();
  }

  public void testAddColumnAndCommitTroid() {
    
    // super.testAddColumnAndCommitTroid();
  }

  public void testAddColumnAndCommitType() {
    
    // super.testAddColumnAndCommitType();
  }

  public void testAddListener() {
    
    // super.testAddListener();
  }

  public void testAppendWhereClause() {
    
    // super.testAppendWhereClause();
  }

  public void testCachedCountPersistent() {
    
    // super.testCachedCountPersistent();
  }

  public void testCachedCountPersistentBooleanBoolean() {
    
    // super.testCachedCountPersistentBooleanBoolean();
  }

  public void testCachedCountString() {
    
    // super.testCachedCountString();
  }

  public void testCachedCountStringBoolean() {
    
    // super.testCachedCountStringBoolean();
  }

  public void testCachedCountStringBooleanBoolean() {
    
    // super.testCachedCountStringBooleanBoolean();
  }

  public void testCachedExists() {
    
    // super.testCachedExists();
  }

  public void testCachedSelection() {
    
    // super.testCachedSelection();
  }

  public void testCachedSelectionField() {
    
    // super.testCachedSelectionField();
  }

  public void testCachedSelectionType() {
    
    // super.testCachedSelectionType();
  }

  public void testCanDeleteColumn() {
    
    // super.testCanDeleteColumn();
  }

  public void testCanReadColumn() {
    
    // super.testCanReadColumn();
  }

  public void testCanSelectColumn() {
    
    // super.testCanSelectColumn();
  }

  public void testCanWriteColumn() {
    
    // super.testCanWriteColumn();
  }

  public void testCnfWhereClauseEnumeration() {
    
    // super.testCnfWhereClauseEnumeration();
  }

  public void testCnfWhereClauseEnumerationBooleanBoolean() {
    
    // super.testCnfWhereClauseEnumerationBooleanBoolean();
  }

  public void testColumns() {
    
    // super.testColumns();
  }

  public void testColumnWithColumnInfoID() {
    
    // super.testColumnWithColumnInfoID();
  }

  public void testCount() {
    ThrowingConnection.startThrowing("createStatement");
    try { 
      super.testCount();
    } catch (ExecutingSQLPoemException e) { 
      assertEquals("Connection bombed", e.innermostException().getMessage());
    } finally {
      ThrowingConnection.stopThrowing("createStatement");
    }
  }

  public void testCountSQLString() {
    
    // super.testCountSQLString();
  }

  public void testCountSQLStringStringBooleanBoolean() {
    
    // super.testCountSQLStringStringBooleanBoolean();
  }

  public void testCountString() {
    
    // super.testCountString();
  }

  public void testCountStringBoolean() {
    
    // super.testCountStringBoolean();
  }

  public void testCountStringBooleanBoolean() {
    
    // super.testCountStringBooleanBoolean();
  }

  public void testCreateInitialiser() {
    
    // super.testCreateInitialiser();
  }

  public void testCreatePersistent() {
    
    // super.testCreatePersistent();
  }

  public void testCreateTableInfo() {
    
    // super.testCreateTableInfo();
  }

  public void testDbModifyStructure() {
    
    // super.testDbModifyStructure();
  }

  public void testDefaultCacheLimit() {
    
    // super.testDefaultCacheLimit();
  }

  public void testDefaultCategory() {
    
    // super.testDefaultCategory();
  }

  public void testDefaultDescription() {
    
    // super.testDefaultDescription();
  }

  public void testDefaultDisplayName() {
    
    // super.testDefaultDisplayName();
  }

  public void testDefaultDisplayOrder() {
    
    // super.testDefaultDisplayOrder();
  }

  public void testDefaultOrderByClause() {
    
    // super.testDefaultOrderByClause();
  }

  public void testDefaultRememberAllTroids() {
    
    // super.testDefaultRememberAllTroids();
  }

  public void testDefineColumnColumn() {
    
    // super.testDefineColumnColumn();
  }

  public void testDefineColumnColumnBoolean() {
    
    // super.testDefineColumnColumnBoolean();
  }

  public void testDelete_unsafe() {
    
    // super.testDelete_unsafe();
  }

  public void testDelete() {
    
    // super.testDelete();
  }

  public void testDeletedColumn() {
    
    // super.testDeletedColumn();
  }

  public void testDisplayColumn() {
    
    // super.testDisplayColumn();
  }

  public void testDisplayColumns() {
    
    // super.testDisplayColumns();
  }

  public void testDisplayColumnsCount() {
    
    // super.testDisplayColumnsCount();
  }

  public void testDump() {
    
    // super.testDump();
  }

  public void testDumpCacheAnalysis() {
    
    // super.testDumpCacheAnalysis();
  }

  public void testEqualsObject() {
    
    // super.testEqualsObject();
  }

  public void testExistsPersistent() {
    
    // super.testExistsPersistent();
  }

  public void testExistsString() {
    
    // super.testExistsString();
  }

  public void testExtrasCount() {
    
    // super.testExtrasCount();
  }

  public void testFirstSelection() {
    
    // super.testFirstSelection();
  }

  public void testGetCacheInfo() {
    
    // super.testGetCacheInfo();
  }

  public void testGetCanCreate() {
    
    // super.testGetCanCreate();
  }

  public void testGetCategory() {
    
    // super.testGetCategory();
  }

  public void testGetColumn() {
    
    // super.testGetColumn();
  }

  public void testGetColumnsCount() {
    
    // super.testGetColumnsCount();
  }

  public void testGetDatabase() {
    
    // super.testGetDatabase();
  }

  public void testGetDefaultCanDelete() {
    
    // super.testGetDefaultCanDelete();
  }

  public void testGetDefaultCanRead() {
    
    // super.testGetDefaultCanRead();
  }

  public void testGetDefaultCanWrite() {
    
    // super.testGetDefaultCanWrite();
  }

  public void testGetDescription() {
    
    // super.testGetDescription();
  }

  public void testGetDetailDisplayColumns() {
    
    // super.testGetDetailDisplayColumns();
  }

  public void testGetDetailDisplayColumnsCount() {
    
    // super.testGetDetailDisplayColumnsCount();
  }

  public void testGetDisplayName() {
    
    // super.testGetDisplayName();
  }

  public void testGetDsdName() {
    
    // super.testGetDsdName();
  }

  public void testGetInfo() {
    
    // super.testGetInfo();
  }

  public void testGetName() {
    
    // super.testGetName();
  }

  public void testGetObjectInt() {
    getDb().uncacheContents();
    ThrowingResultSet.startThrowing("next");
    ThrowingResultSet.startThrowing("close");
    try { 
      super.testGetObjectInt();
      fail("Should have blown up");
    } catch (SimpleRetrievalFailedPoemException e) { 
      assertEquals("ResultSet bombed", e.innermostException().getMessage());
    } finally { 
      ThrowingResultSet.stopThrowing("next");
      ThrowingResultSet.stopThrowing("close");
    }
  }

  public void testGetObjectInteger() {
    
    // super.testGetObjectInteger();
  }

  public void testGetRecordDisplayColumns() {
    
    // super.testGetRecordDisplayColumns();
  }

  public void testGetRecordDisplayColumnsCount() {
    
    // super.testGetRecordDisplayColumnsCount();
  }

  public void testGetSearchCriterionColumns() {
    
    // super.testGetSearchCriterionColumns();
  }

  public void testGetSearchCriterionColumnsCount() {
    
    // super.testGetSearchCriterionColumnsCount();
  }

  public void testGetSummaryDisplayColumns() {
    
    // super.testGetSummaryDisplayColumns();
  }

  public void testGetSummaryDisplayColumnsCount() {
    
    // super.testGetSummaryDisplayColumnsCount();
  }

  public void testGetTableInfo() {
    
    // super.testGetTableInfo();
  }

  public void testHashCode() {
    
    // super.testHashCode();
  }

  public void testInit() {
    
    // super.testInit();
  }

  public void testLoad() {
    
    // super.testLoad();
  }

  public void testNewPersistent() {
    
    // super.testNewPersistent();
  }

  public void testNotifyColumnInfo() {
    
    // super.testNotifyColumnInfo();
  }

  public void testNotifyTouched() {
    
    // super.testNotifyTouched();
  }

  public void testPostInitialise() {
    
    // super.testPostInitialise();
  }

  public void testPrimaryCriterionColumn() {
    
    // super.testPrimaryCriterionColumn();
  }

  public void testQuotedName() {
    
    // super.testQuotedName();
  }

  public void testReadLock() {
    
    // super.testReadLock();
  }

  public void testReferencesToPersistent() {
    
    // super.testReferencesToPersistent();
  }

  public void testReferencesToTable() {
    
    // super.testReferencesToTable();
  }

  public void testRememberAllTroids() {
    
    // super.testRememberAllTroids();
  }

  public void testSelection() {
    
    // super.testSelection();
  }

  public void testSelectionPersistent() {
    
    // super.testSelectionPersistent();
  }

  public void testSelectionPersistentString() {
    
    // super.testSelectionPersistentString();
  }

  public void testSelectionPersistentStringBooleanBoolean() {
    
    // super.testSelectionPersistentStringBooleanBoolean();
  }

  public void testSelectionPersistentStringBooleanBooleanIntInt() {
    
    // super.testSelectionPersistentStringBooleanBooleanIntInt();
  }

  public void testSelectionSQLStringStringBoolean() {
    
    // super.testSelectionSQLStringStringBoolean();
  }

  public void testSelectionSQLStringStringStringBooleanBoolean() {
    
    // super.testSelectionSQLStringStringStringBooleanBoolean();
  }

  public void testSelectionString() {
    
    // super.testSelectionString();
  }

  public void testSelectionStringStringBoolean() {
    
    // super.testSelectionStringStringBoolean();
  }

  public void testSelectionStringStringBooleanIntInt() {
    
    // super.testSelectionStringStringBooleanIntInt();
  }

  public void testSerial() {
    
    // super.testSerial();
  }

  public void testSetCacheLimit() {
    
    // super.testSetCacheLimit();
  }

  public void testSetDisplayColumn() {
    
    // super.testSetDisplayColumn();
  }

  public void testSetSearchColumn() {
    
    // super.testSetSearchColumn();
  }

  public void testSetTableInfo() {
    
    // super.testSetTableInfo();
  }

  public void testTable() {
    
    // super.testTable();
  }

  public void testTableInfoID() {
    
    // super.testTableInfoID();
  }

  public void testToString() {
    
    // super.testToString();
  }

  public void testTrimCache() {
    
    // super.testTrimCache();
  }

  public void testTroidColumn() {
    
    // super.testTroidColumn();
  }

  public void testTroidFor() {
    
    // super.testTroidFor();
  }

  public void testTroidSelectionPersistentStringBooleanBooleanPoemTransaction() {
    
    // super.testTroidSelectionPersistentStringBooleanBooleanPoemTransaction();
  }

  public void testTroidSelectionStringStringBoolean() {
    
    // super.testTroidSelectionStringStringBoolean();
  }

  public void testTroidSelectionStringStringBooleanPoemTransaction() {
    
    // super.testTroidSelectionStringStringBooleanPoemTransaction();
  }

  public void testUncacheContents() {
    
    // super.testUncacheContents();
  }

  public void testUnifyWithColumnInfo() {
    
    // super.testUnifyWithColumnInfo();
  }

  public void testUnifyWithDB() {
    
    // super.testUnifyWithDB();
  }

  public void testWhereClausePersistent() {
    
    // super.testWhereClausePersistent();
  }

  public void testWhereClausePersistentBooleanBoolean() {
    
    // super.testWhereClausePersistentBooleanBoolean();
  }

  public void testWriteDown() {
    // Need to be able to fire on second invocation
    // as this is blowing up on first 
    ThrowingPreparedStatement.startThrowing("setInt");
    try { 
      super.testWriteDown();
      fail("Should have blown up");
    } catch (FieldContentsPoemException e) { 
      assertEquals("PreparedStatement bombed", e.innermostException().getMessage());
    } finally { 
      ThrowingPreparedStatement.stopThrowing("setInt");
    }
  }
  public void testWriteDown2() {
    ThrowingPreparedStatement.startThrowing("executeUpdate");
    try { 
      super.testWriteDown();
      fail("Should have blown up");
    } catch (ExecutingSQLPoemException e) { 
      assertEquals("PreparedStatement bombed", e.innermostException().getMessage());
    } finally { 
      ThrowingPreparedStatement.stopThrowing("executeUpdate");
    }
  }
  public void testWriteDown3() {
    // Need to be able to fire on second invocation
    // as this is blowing up on first 
    ThrowingPreparedStatement.startThrowingAfter("setInt",1);
    try { 
      super.testWriteDown();
      fail("Should have blown up");
    } catch (SQLSeriousPoemException e) { 
      assertEquals("PreparedStatement bombed", e.innermostException().getMessage());
    } finally { 
      ThrowingPreparedStatement.stopThrowing("setInt");
    }
  }

}
