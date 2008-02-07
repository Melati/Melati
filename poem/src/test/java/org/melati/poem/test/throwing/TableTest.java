/**
 * 
 */
package org.melati.poem.test.throwing;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import org.melati.poem.Database;
import org.melati.poem.DatabaseInitialisationPoemException;
import org.melati.poem.ExecutingSQLPoemException;
import org.melati.poem.FieldContentsPoemException;
import org.melati.poem.GroupCapability;
import org.melati.poem.PoemDatabaseFactory;
import org.melati.poem.SQLSeriousPoemException;
import org.melati.poem.SimplePrepareFailedPoemException;
import org.melati.poem.SimpleRetrievalFailedPoemException;
import org.melati.poem.Column.LoadException;
import org.melati.poem.dbms.test.sql.ThrowingConnection;
import org.melati.poem.dbms.test.sql.ThrowingPreparedStatement;
import org.melati.poem.dbms.test.sql.ThrowingResultSet;
import org.melati.poem.dbms.test.sql.ThrowingStatement;

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
    PoemDatabaseFactory.removeDatabase(getDatabaseName());
    super.setUp();
    assertEquals("org.melati.poem.dbms.test.HsqldbThrower",getDb().getDbms().getClass().getName());
  }

  protected void tearDown() throws Exception {
    try { 
      super.tearDown();
    } finally { 
      PoemDatabaseFactory.removeDatabase(getDatabaseName());
    }
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
    //super.testCachedCountPersistent();
  }

  public void testCachedCountPersistentBooleanBoolean() {
    
    // super.testCachedCountPersistentBooleanBoolean();
  }

  public void testCachedCountString() {
    
    //super.testCachedCountString();
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
    ThrowingConnection.startThrowing(Connection.class, "createStatement");
    try { 
      super.testCount();
      fail("Should have bombed");
    } catch (ExecutingSQLPoemException e) { 
      assertEquals("Connection bombed", e.innermostException().getMessage());
    } finally {
      ThrowingConnection.stopThrowing(Connection.class, "createStatement");
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
    getDb().uncache();
    ThrowingResultSet.startThrowing(ResultSet.class, "next");
    ThrowingResultSet.startThrowing(ResultSet.class, "close");
    try { 
      super.testGetObjectInt();
      fail("Should have blown up");
    } catch (SimpleRetrievalFailedPoemException e) { 
      assertEquals("ResultSet bombed", e.innermostException().getMessage());
    } finally { 
      ThrowingResultSet.stopThrowing(ResultSet.class, "next");
      ThrowingResultSet.stopThrowing(ResultSet.class, "close");
    }
    getDb().uncache();
    ThrowingResultSet.startThrowing(ResultSet.class, "getInt");
    try { 
      super.testGetObjectInt();
      fail("Should have blown up");
    } catch (LoadException e) { 
      assertEquals("ResultSet bombed", e.innermostException().getMessage());
    } finally { 
      ThrowingResultSet.stopThrowing(ResultSet.class, "getInt");
    }
    // Force bomb of simpleGet
    getDb().getGroupTable().invalidateTransactionStuffs();
    ThrowingConnection.startThrowingAfter(Connection.class,"prepareStatement", 2);
    try { 
      super.testGetObjectInt();
      fail("Should have blown up");
    } catch (SimplePrepareFailedPoemException e) { 
      e.printStackTrace();
      assertEquals("Connection bombed", e.innermostException().getMessage());
    } finally { 
      ThrowingConnection.stopThrowing(Connection.class, "prepareStatement");
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

  /** 
   * {@inheritDoc}
   * @see org.melati.poem.test.TableTest#testSelectionSQLStringStringBoolean()
   */
  public void testSelectionSQLStringStringBoolean() {
    
    // super.testSelectionSQLStringStringBoolean();
  }

  /** 
   * {@inheritDoc}
   * @see org.melati.poem.test.TableTest#testSelectionSQLStringStringStringBooleanBoolean()
   */
  public void testSelectionSQLStringStringStringBooleanBoolean() {
    
    // super.testSelectionSQLStringStringStringBooleanBoolean();
  }

  /** 
   * {@inheritDoc}
   * @see org.melati.poem.test.TableTest#testSelectionString()
   */
  public void testSelectionString() {
    
    // super.testSelectionString();
  }

  /** 
   * {@inheritDoc}
   * @see org.melati.poem.test.TableTest#testSelectionStringStringBoolean()
   */
  public void testSelectionStringStringBoolean() {
    
    // super.testSelectionStringStringBoolean();
  }

  /** 
   * {@inheritDoc}
   * @see org.melati.poem.test.TableTest#testSelectionStringStringBooleanIntInt()
   */
  public void testSelectionStringStringBooleanIntInt() {
    
    // super.testSelectionStringStringBooleanIntInt();
  }

  /** 
   * {@inheritDoc}
   * @see org.melati.poem.test.TableTest#testSerial()
   */
  public void testSerial() {
    
    // super.testSerial();
  }

  /** 
   * {@inheritDoc}
   * @see org.melati.poem.test.TableTest#testSetCacheLimit()
   */
  public void testSetCacheLimit() {
    
    // super.testSetCacheLimit();
  }

  /** 
   * {@inheritDoc}
   * @see org.melati.poem.test.TableTest#testSetDisplayColumn()
   */
  public void testSetDisplayColumn() {
    
    // super.testSetDisplayColumn();
  }

  /** 
   * {@inheritDoc}
   * @see org.melati.poem.test.TableTest#testSetSearchColumn()
   */
  public void testSetSearchColumn() {
    
    // super.testSetSearchColumn();
  }

  /** 
   * {@inheritDoc}
   * @see org.melati.poem.test.TableTest#testSetTableInfo()
   */
  public void testSetTableInfo() {
    
    // super.testSetTableInfo();
  }

  /** 
   * {@inheritDoc}
   * @see org.melati.poem.test.TableTest#testTable()
   */
  public void testTable() {
    
    // super.testTable();
  }

  /** 
   * {@inheritDoc}
   * @see org.melati.poem.test.TableTest#testTableInfoID()
   */
  public void testTableInfoID() {
    
    // super.testTableInfoID();
  }

  /** 
   * {@inheritDoc}
   * @see org.melati.poem.test.TableTest#testToString()
   */
  public void testToString() {
    
    // super.testToString();
  }

  /** 
   * {@inheritDoc}
   * @see org.melati.poem.test.TableTest#testTrimCache()
   */
  public void testTrimCache() {
    
    // super.testTrimCache();
  }

  /** 
   * {@inheritDoc}
   * @see org.melati.poem.test.TableTest#testTroidColumn()
   */
  public void testTroidColumn() {
    
    // super.testTroidColumn();
  }

  /** 
   * {@inheritDoc}
   * @see org.melati.poem.test.TableTest#testTroidFor()
   */
  public void testTroidFor() {
    
    // super.testTroidFor();
  }

  /** 
   * {@inheritDoc}
   * @see org.melati.poem.test.TableTest#testTroidSelectionPersistentStringBooleanBooleanPoemTransaction()
   */
  public void testTroidSelectionPersistentStringBooleanBooleanPoemTransaction() {
    
    //super.testTroidSelectionPersistentStringBooleanBooleanPoemTransaction();
  }

  /** 
   * {@inheritDoc}
   * @see org.melati.poem.test.TableTest#testTroidSelectionStringStringBoolean()
   */
  public void testTroidSelectionStringStringBoolean() {
    ThrowingConnection.startThrowing(Connection.class, "createStatement");
    try { 
      super.testTroidSelectionStringStringBoolean();
      fail("Should have bombed");
    } catch (ExecutingSQLPoemException e) { 
      assertEquals("Connection bombed", e.innermostException().getMessage());
    } finally { 
      ThrowingConnection.stopThrowing(Connection.class, "createStatement");      
    }
  }

  /** 
   * {@inheritDoc}
   * @see org.melati.poem.test.TableTest#testTroidSelectionStringStringBooleanPoemTransaction()
   */
  public void testTroidSelectionStringStringBooleanPoemTransaction() {
    
    // super.testTroidSelectionStringStringBooleanPoemTransaction();
  }

  /** 
   * {@inheritDoc}
   * @see org.melati.poem.test.TableTest#testUncacheContents()
   */
  public void testUncacheContents() {
    
    // super.testUncacheContents();
  }

  /** 
   * {@inheritDoc}
   * @see org.melati.poem.test.TableTest#testUnifyWithColumnInfo()
   */
  public void testUnifyWithColumnInfo() {
    
    // super.testUnifyWithColumnInfo();
  }

  /** 
   * {@inheritDoc}
   * @see org.melati.poem.test.TableTest#testUnifyWithDB()
   */
  public void testUnifyWithDB() {
    
    // super.testUnifyWithDB();
  }

  /** 
   * {@inheritDoc}
   * @see org.melati.poem.test.TableTest#testWhereClausePersistent()
   */
  public void testWhereClausePersistent() {
    
    // super.testWhereClausePersistent();
  }

  /** 
   * {@inheritDoc}
   * @see org.melati.poem.test.TableTest#testWhereClausePersistentBooleanBoolean()
   */
  public void testWhereClausePersistentBooleanBoolean() {
    
    // super.testWhereClausePersistentBooleanBoolean();
  }

  /**
   * Test write down bombs. 
   */
  public void testWriteDown() {
    ThrowingPreparedStatement.startThrowing(PreparedStatement.class, "setInt");
    try { 
      super.testWriteDown();
      fail("Should have blown up");
    } catch (FieldContentsPoemException e) { 
      assertEquals("PreparedStatement bombed", e.innermostException().getMessage());
    } finally { 
      ThrowingPreparedStatement.stopThrowing(PreparedStatement.class, "setInt");
    }
    
  }

  /**
   * Test write down bombs. 
   */
  public void testWriteDown2() {
    ThrowingPreparedStatement.startThrowing(PreparedStatement.class, "executeUpdate");
    try { 
      super.testWriteDown();
      fail("Should have blown up");
    } catch (ExecutingSQLPoemException e) { 
      assertEquals("PreparedStatement bombed", e.innermostException().getMessage());
    } finally { 
      ThrowingPreparedStatement.stopThrowing(PreparedStatement.class, "executeUpdate");
    }
  }

  /**
   * Test write down bombs. 
   */
  public void testWriteDown3() {
    ThrowingPreparedStatement.startThrowingAfter(PreparedStatement.class,"setInt", 1);
    try { 
      super.testWriteDown();
      fail("Should have blown up");
    } catch (SQLSeriousPoemException e) { 
      assertEquals("PreparedStatement bombed", e.innermostException().getMessage());
    } finally { 
      ThrowingPreparedStatement.stopThrowing(PreparedStatement.class, "setInt");
    }
  }

  /**
   * Test write down bombs. 
   */
  public void testWriteDownInsert() {
    ThrowingConnection.startThrowing(Connection.class, "prepareStatement");
    try { 
      getDb().getGroupCapabilityTable().invalidateTransactionStuffs();
      GroupCapability g = (GroupCapability)getDb().getGroupCapabilityTable().newPersistent();
      g.setGroup_unsafe(new Integer(0));
      g.setCapability_unsafe(new Integer(0));
      g.makePersistent();
      fail("Should have blown up");
    } catch (SimplePrepareFailedPoemException e) { 
      assertEquals("Connection bombed", e.innermostException().getMessage());
    } finally { 
      ThrowingConnection.stopThrowing(Connection.class, "prepareStatement");
    }
  }

  /**
   * Test write down bombs. 
   */
  public void testWriteDownModify() {
    ThrowingConnection.startThrowingAfter(Connection.class,"prepareStatement", 1);
    getDb().getUserTable().invalidateTransactionStuffs();
    try { 
      super.testWriteDown();
      fail("Should have blown up");
    } catch (SimplePrepareFailedPoemException e) { 
      assertEquals("Connection bombed", e.innermostException().getMessage());
    } finally { 
      ThrowingConnection.stopThrowing(Connection.class, "prepareStatement");
    }
  }

  /**
   * Test write down bombs. 
   */
  public void testWriteDown5() {
    ThrowingPreparedStatement.startThrowing(PreparedStatement.class, "executeUpdate");
    try { 
      getDb().getGroupCapabilityTable().invalidateTransactionStuffs();
      GroupCapability g = (GroupCapability)getDb().getGroupCapabilityTable().newPersistent();
      g.setGroup_unsafe(new Integer(0));
      g.setCapability_unsafe(new Integer(0));
      g.makePersistent();
      fail("Should have blown up");
    } catch (ExecutingSQLPoemException e) { 
      assertEquals("PreparedStatement bombed", e.innermostException().getMessage());
    } finally { 
      ThrowingPreparedStatement.stopThrowing(PreparedStatement.class, "executeUpdate");
    }
  }

  /**
   * Test write down bombs.
   */
  public void testWriteDownDelete() {
    GroupCapability g = (GroupCapability)getDb().getGroupCapabilityTable().newPersistent();
    g.setGroup_unsafe(new Integer(0));
    g.setCapability_unsafe(new Integer(0));
    g.makePersistent();
    ThrowingStatement.startThrowing(Statement.class, "executeUpdate");
    getDb().getGroupCapabilityTable().invalidateTransactionStuffs();
    try { 
      g.delete();
      fail("Should have blown up");
    } catch (ExecutingSQLPoemException e) { 
      assertEquals("Statement bombed", e.innermostException().getMessage());
    } finally { 
      ThrowingStatement.stopThrowing(Statement.class, "executeUpdate");
      g.delete();
    }
  }

  /**
   * Test that the next troid bombs.
   */
  public void testNextTroidBombs() { 
    ThrowingStatement.startThrowing(Statement.class, "close");
    PoemDatabaseFactory.removeDatabase("melatijunit");
    try { 
      getDb();
      fail("Should have blown up");
    } catch (DatabaseInitialisationPoemException e) { 
      e.printStackTrace();
      assertEquals("Statement bombed", e.innermostException().getMessage());
    } finally { 
      ThrowingStatement.stopThrowing(Statement.class, "close");
    }
  }
}
