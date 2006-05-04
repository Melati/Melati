/**
 * 
 */
package org.melati.poem.test;

import org.melati.LogicalDatabase;
import org.melati.poem.AccessToken;
import org.melati.poem.PoemDatabase;
import org.melati.poem.PoemTask;
import org.melati.poem.Table;
import org.melati.poem.UnexpectedExceptionPoemException;

import junit.framework.TestCase;

/**
 * @author timp
 *
 */
public class TableTest extends TestCase {
  private PoemDatabase db;
  private static final String dbName = "melatijunit";

  /*
   * @see TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
    db = (PoemDatabase)LogicalDatabase.getDatabase(dbName);
  }
  /*
   * @see TestCase#tearDown()
   */
  protected void tearDown() throws Exception {
    db.inSession(AccessToken.root, // HACK
            new PoemTask() {
              public void run() {
                try {
                  //if (db.getDbms().toString().endsWith("Hsqldb"))
                  //  db.sqlQuery("SHUTDOWN");
                } catch (Exception e) {
                  throw new UnexpectedExceptionPoemException(e);
                }
              }
            });
    // FIXME - Do not understand why disconnecting here 
    // causes NoMoreTransactionsException
    //db.disconnect();
    super.tearDown();
  }
  
  /*
   * Test method for 'org.melati.poem.Table.Table(Database, String, DefinitionSource)'
   */
  public void testTable() {

  }

  /*
   * Test method for 'org.melati.poem.Table.postInitialise()'
   */
  public void testPostInitialise() {

  }

  /*
   * Test method for 'org.melati.poem.Table.getDatabase()'
   */
  public void testGetDatabase() {
     assertTrue(db.getUserTable().getDatabase().equals(db));
  }

  /*
   * Test method for 'org.melati.poem.Table.getName()'
   */
  public void testGetName() {
    Table ut = db.getUserTable();
    assertEquals("user", ut.getName());
  }

  /*
   * Test method for 'org.melati.poem.Table.quotedName()'
   */
  public void testQuotedName() {

  }

  /*
   * Test method for 'org.melati.poem.Table.getDisplayName()'
   */
  public void testGetDisplayName() {

  }

  /*
   * Test method for 'org.melati.poem.Table.getDescription()'
   */
  public void testGetDescription() {

  }

  /*
   * Test method for 'org.melati.poem.Table.getCategory()'
   */
  public void testGetCategory() {

  }

  /*
   * Test method for 'org.melati.poem.Table.getInfo()'
   */
  public void testGetInfo() {

  }

  /*
   * Test method for 'org.melati.poem.Table.tableInfoID()'
   */
  public void testTableInfoID() {

  }

  /*
   * Test method for 'org.melati.poem.Table.getColumn(String)'
   */
  public void testGetColumn() {

  }

  /*
   * Test method for 'org.melati.poem.Table._getColumn(String)'
   */
  public void test_getColumn() {

  }

  /*
   * Test method for 'org.melati.poem.Table.columns()'
   */
  public void testColumns() {

  }

  /*
   * Test method for 'org.melati.poem.Table.getColumnsCount()'
   */
  public void testGetColumnsCount() {

  }

  /*
   * Test method for 'org.melati.poem.Table.columnWithColumnInfoID(int)'
   */
  public void testColumnWithColumnInfoID() {

  }

  /*
   * Test method for 'org.melati.poem.Table.troidColumn()'
   */
  public void testTroidColumn() {

  }

  /*
   * Test method for 'org.melati.poem.Table.deletedColumn()'
   */
  public void testDeletedColumn() {

  }

  /*
   * Test method for 'org.melati.poem.Table.displayColumn()'
   */
  public void testDisplayColumn() {

  }

  /*
   * Test method for 'org.melati.poem.Table.setDisplayColumn(Column)'
   */
  public void testSetDisplayColumn() {

  }

  /*
   * Test method for 'org.melati.poem.Table.primaryCriterionColumn()'
   */
  public void testPrimaryCriterionColumn() {

  }

  /*
   * Test method for 'org.melati.poem.Table.setSearchColumn(Column)'
   */
  public void testSetSearchColumn() {

  }

  /*
   * Test method for 'org.melati.poem.Table.defaultOrderByClause()'
   */
  public void testDefaultOrderByClause() {

  }

  /*
   * Test method for 'org.melati.poem.Table.notifyColumnInfo(ColumnInfo)'
   */
  public void testNotifyColumnInfo() {

  }

  /*
   * Test method for 'org.melati.poem.Table.displayColumns(DisplayLevel)'
   */
  public void testDisplayColumns() {

  }

  /*
   * Test method for 'org.melati.poem.Table.displayColumnsCount(DisplayLevel)'
   */
  public void testDisplayColumnsCount() {

  }

  /*
   * Test method for 'org.melati.poem.Table.getDetailDisplayColumns()'
   */
  public void testGetDetailDisplayColumns() {

  }

  /*
   * Test method for 'org.melati.poem.Table.getDetailDisplayColumnsCount()'
   */
  public void testGetDetailDisplayColumnsCount() {

  }

  /*
   * Test method for 'org.melati.poem.Table.getRecordDisplayColumns()'
   */
  public void testGetRecordDisplayColumns() {

  }

  /*
   * Test method for 'org.melati.poem.Table.getRecordDisplayColumnsCount()'
   */
  public void testGetRecordDisplayColumnsCount() {

  }

  /*
   * Test method for 'org.melati.poem.Table.getSummaryDisplayColumns()'
   */
  public void testGetSummaryDisplayColumns() {

  }

  /*
   * Test method for 'org.melati.poem.Table.getSearchCriterionColumns()'
   */
  public void testGetSearchCriterionColumns() {

  }

  /*
   * Test method for 'org.melati.poem.Table.getSearchCriterionColumnsCount()'
   */
  public void testGetSearchCriterionColumnsCount() {

  }

  /*
   * Test method for 'org.melati.poem.Table.dbModifyStructure(String)'
   */
  public void testDbModifyStructure() {

  }

  /*
   * Test method for 'org.melati.poem.Table.load(PoemTransaction, Persistent)'
   */
  public void testLoad() {

  }

  /*
   * Test method for 'org.melati.poem.Table.delete(Integer, PoemTransaction)'
   */
  public void testDelete() {

  }

  /*
   * Test method for 'org.melati.poem.Table.writeDown(PoemTransaction, Persistent)'
   */
  public void testWriteDown() {

  }

  /*
   * Test method for 'org.melati.poem.Table.uncacheContents()'
   */
  public void testUncacheContents() {

  }

  /*
   * Test method for 'org.melati.poem.Table.trimCache(int)'
   */
  public void testTrimCache() {

  }

  /*
   * Test method for 'org.melati.poem.Table.getCacheInfo()'
   */
  public void testGetCacheInfo() {

  }

  /*
   * Test method for 'org.melati.poem.Table.addListener(TableListener)'
   */
  public void testAddListener() {

  }

  /*
   * Test method for 'org.melati.poem.Table.notifyTouched(PoemTransaction, Persistent)'
   */
  public void testNotifyTouched() {

  }

  /*
   * Test method for 'org.melati.poem.Table.serial(PoemTransaction)'
   */
  public void testSerial() {

  }

  /*
   * Test method for 'org.melati.poem.Table.readLock()'
   */
  public void testReadLock() {

  }

  /*
   * Test method for 'org.melati.poem.Table.getObject(Integer)'
   */
  public void testGetObjectInteger() {

  }

  /*
   * Test method for 'org.melati.poem.Table.getObject(int)'
   */
  public void testGetObjectInt() {

  }

  /*
   * Test method for 'org.melati.poem.Table.selectionSQL(String, String, boolean)'
   */
  public void testSelectionSQLStringStringBoolean() {

  }

  /*
   * Test method for 'org.melati.poem.Table.selectionSQL(String, String, String, boolean, boolean)'
   */
  public void testSelectionSQLStringStringStringBooleanBoolean() {

  }

  /*
   * Test method for 'org.melati.poem.Table.troidSelection(String, String, boolean, PoemTransaction)'
   */
  public void testTroidSelectionStringStringBooleanPoemTransaction() {

  }

  /*
   * Test method for 'org.melati.poem.Table.troidSelection(Persistent, String, boolean, boolean, PoemTransaction)'
   */
  public void testTroidSelectionPersistentStringBooleanBooleanPoemTransaction() {

  }

  /*
   * Test method for 'org.melati.poem.Table.rememberAllTroids(boolean)'
   */
  public void testRememberAllTroids() {

  }

  /*
   * Test method for 'org.melati.poem.Table.setCacheLimit(Integer)'
   */
  public void testSetCacheLimit() {

  }

  /*
   * Test method for 'org.melati.poem.Table.troidSelection(String, String, boolean)'
   */
  public void testTroidSelectionStringStringBoolean() {

  }

  /*
   * Test method for 'org.melati.poem.Table.selection()'
   */
  public void testSelection() {

  }

  /*
   * Test method for 'org.melati.poem.Table.selection(String)'
   */
  public void testSelectionString() {

  }

  /*
   * Test method for 'org.melati.poem.Table.firstSelection(String)'
   */
  public void testFirstSelection() {

  }

  /*
   * Test method for 'org.melati.poem.Table.selection(String, String, boolean)'
   */
  public void testSelectionStringStringBoolean() {

  }

  /*
   * Test method for 'org.melati.poem.Table.selection(Persistent)'
   */
  public void testSelectionPersistent() {

  }

  /*
   * Test method for 'org.melati.poem.Table.selection(Persistent, String)'
   */
  public void testSelectionPersistentString() {

  }

  /*
   * Test method for 'org.melati.poem.Table.selection(Persistent, String, boolean, boolean)'
   */
  public void testSelectionPersistentStringBooleanBoolean() {

  }

  /*
   * Test method for 'org.melati.poem.Table.selection(String, String, boolean, int, int)'
   */
  public void testSelectionStringStringBooleanIntInt() {

  }

  /*
   * Test method for 'org.melati.poem.Table.selection(Persistent, String, boolean, boolean, int, int)'
   */
  public void testSelectionPersistentStringBooleanBooleanIntInt() {

  }

  /*
   * Test method for 'org.melati.poem.Table.countSQL(String)'
   */
  public void testCountSQLString() {

  }

  /*
   * Test method for 'org.melati.poem.Table.countSQL(String, String, boolean, boolean)'
   */
  public void testCountSQLStringStringBooleanBoolean() {

  }

  /*
   * Test method for 'org.melati.poem.Table.count(String, boolean, boolean)'
   */
  public void testCountStringBooleanBoolean() {

  }

  /*
   * Test method for 'org.melati.poem.Table.count(String, boolean)'
   */
  public void testCountStringBoolean() {

  }

  /*
   * Test method for 'org.melati.poem.Table.count(String)'
   */
  public void testCountString() {

  }

  /*
   * Test method for 'org.melati.poem.Table.count()'
   */
  public void testCount() {

  }

  /*
   * Test method for 'org.melati.poem.Table.exists(String)'
   */
  public void testExistsString() {

  }

  /*
   * Test method for 'org.melati.poem.Table.exists(Persistent)'
   */
  public void testExistsPersistent() {

  }

  /*
   * Test method for 'org.melati.poem.Table.appendWhereClause(StringBuffer, Persistent)'
   */
  public void testAppendWhereClause() {

  }

  /*
   * Test method for 'org.melati.poem.Table.whereClause(Persistent)'
   */
  public void testWhereClausePersistent() {

  }

  /*
   * Test method for 'org.melati.poem.Table.whereClause(Persistent, boolean, boolean)'
   */
  public void testWhereClausePersistentBooleanBoolean() {

  }

  /*
   * Test method for 'org.melati.poem.Table.cnfWhereClause(Enumeration)'
   */
  public void testCnfWhereClauseEnumeration() {

  }

  /*
   * Test method for 'org.melati.poem.Table.cnfWhereClause(Enumeration, boolean, boolean)'
   */
  public void testCnfWhereClauseEnumerationBooleanBoolean() {

  }

  /*
   * Test method for 'org.melati.poem.Table.referencesTo(Persistent)'
   */
  public void testReferencesToPersistent() {

  }

  /*
   * Test method for 'org.melati.poem.Table.referencesTo(Table)'
   */
  public void testReferencesToTable() {

  }

  /*
   * Test method for 'org.melati.poem.Table.troidFor(Persistent)'
   */
  public void testTroidFor() {

  }

  /*
   * Test method for 'org.melati.poem.Table.create(Persistent)'
   */
  public void testCreatePersistent() {

  }

  /*
   * Test method for 'org.melati.poem.Table.create(Initialiser)'
   */
  public void testCreateInitialiser() {

  }

  /*
   * Test method for 'org.melati.poem.Table.newPersistent()'
   */
  public void testNewPersistent() {

  }

  /*
   * Test method for 'org.melati.poem.Table._newPersistent()'
   */
  public void test_newPersistent() {

  }

  /*
   * Test method for 'org.melati.poem.Table.delete_unsafe(String)'
   */
  public void testDelete_unsafe() {

  }

  /*
   * Test method for 'org.melati.poem.Table.extrasCount()'
   */
  public void testExtrasCount() {

  }

  /*
   * Test method for 'org.melati.poem.Table.getDefaultCanRead()'
   */
  public void testGetDefaultCanRead() {

  }

  /*
   * Test method for 'org.melati.poem.Table.getDefaultCanWrite()'
   */
  public void testGetDefaultCanWrite() {

  }

  /*
   * Test method for 'org.melati.poem.Table.getDefaultCanDelete()'
   */
  public void testGetDefaultCanDelete() {

  }

  /*
   * Test method for 'org.melati.poem.Table.getCanCreate()'
   */
  public void testGetCanCreate() {

  }

  /*
   * Test method for 'org.melati.poem.Table.canReadColumn()'
   */
  public void testCanReadColumn() {

  }

  /*
   * Test method for 'org.melati.poem.Table.canSelectColumn()'
   */
  public void testCanSelectColumn() {

  }

  /*
   * Test method for 'org.melati.poem.Table.canWriteColumn()'
   */
  public void testCanWriteColumn() {

  }

  /*
   * Test method for 'org.melati.poem.Table.canDeleteColumn()'
   */
  public void testCanDeleteColumn() {

  }

  /*
   * Test method for 'org.melati.poem.Table.addColumnAndCommit(ColumnInfo)'
   */
  public void testAddColumnAndCommit() {

  }

  /*
   * Test method for 'org.melati.poem.Table.toString()'
   */
  public void testToString() {
    Table ut = db.getUserTable();
    assertEquals("user (from the data structure definition)", ut.toString());

  }

  /*
   * Test method for 'org.melati.poem.Table.dumpCacheAnalysis()'
   */
  public void testDumpCacheAnalysis() {

  }

  /*
   * Test method for 'org.melati.poem.Table.dump()'
   */
  public void testDump() {

  }

  /*
   * Test method for 'org.melati.poem.Table.cachedSelection(String, String)'
   */
  public void testCachedSelection() {

  }

  /*
   * Test method for 'org.melati.poem.Table.cachedCount(String, boolean)'
   */
  public void testCachedCountStringBoolean() {

  }

  /*
   * Test method for 'org.melati.poem.Table.cachedCount(String, boolean, boolean)'
   */
  public void testCachedCountStringBooleanBoolean() {

  }

  /*
   * Test method for 'org.melati.poem.Table.cachedCount(Persistent, boolean, boolean)'
   */
  public void testCachedCountPersistentBooleanBoolean() {

  }

  /*
   * Test method for 'org.melati.poem.Table.cachedCount(String)'
   */
  public void testCachedCountString() {

  }

  /*
   * Test method for 'org.melati.poem.Table.cachedExists(String)'
   */
  public void testCachedExists() {

  }

  /*
   * Test method for 'org.melati.poem.Table.cachedSelectionType(String, String, boolean)'
   */
  public void testCachedSelectionType() {

  }

  /*
   * Test method for 'org.melati.poem.Table.cachedSelectionField(String, String, boolean, Integer, String)'
   */
  public void testCachedSelectionField() {

  }

  /*
   * Test method for 'org.melati.poem.Table.defineColumn(Column, boolean)'
   */
  public void testDefineColumnColumnBoolean() {

  }

  /*
   * Test method for 'org.melati.poem.Table.defineColumn(Column)'
   */
  public void testDefineColumnColumn() {

  }

  /*
   * Test method for 'org.melati.poem.Table.setTableInfo(TableInfo)'
   */
  public void testSetTableInfo() {

  }

  /*
   * Test method for 'org.melati.poem.Table.getTableInfo()'
   */
  public void testGetTableInfo() {

  }

  /*
   * Test method for 'org.melati.poem.Table.defaultDisplayName()'
   */
  public void testDefaultDisplayName() {

  }

  /*
   * Test method for 'org.melati.poem.Table.getDsdName()'
   */
  public void testGetDsdName() {

  }

  /*
   * Test method for 'org.melati.poem.Table.defaultDisplayOrder()'
   */
  public void testDefaultDisplayOrder() {

  }

  /*
   * Test method for 'org.melati.poem.Table.defaultDescription()'
   */
  public void testDefaultDescription() {

  }

  /*
   * Test method for 'org.melati.poem.Table.defaultCacheLimit()'
   */
  public void testDefaultCacheLimit() {

  }

  /*
   * Test method for 'org.melati.poem.Table.defaultRememberAllTroids()'
   */
  public void testDefaultRememberAllTroids() {

  }

  /*
   * Test method for 'org.melati.poem.Table.defaultCategory()'
   */
  public void testDefaultCategory() {

  }

  /*
   * Test method for 'org.melati.poem.Table.createTableInfo()'
   */
  public void testCreateTableInfo() {

  }

  /*
   * Test method for 'org.melati.poem.Table.unifyWithColumnInfo()'
   */
  public void testUnifyWithColumnInfo() {

  }

  /*
   * Test method for 'org.melati.poem.Table.unifyWithDB(ResultSet)'
   */
  public void testUnifyWithDB() {

  }

  /*
   * Test method for 'org.melati.poem.Table.init()'
   */
  public void testInit() {

  }

  /*
   * Test method for 'org.melati.poem.Table.hashCode()'
   */
  public void testHashCode() {
    Table ut = db.getUserTable();
    Table ut2 = db.getUserTable();
    assertTrue(ut.equals(ut2));
    assertEquals(ut.hashCode(), ut2.hashCode());
  }

  /*
   * Test method for 'org.melati.poem.Table.equals(Object)'
   */
  public void testEqualsObject() {
    Table ut = db.getUserTable();
    Table ct = db.getCapabilityTable();
    assertFalse(ut.equals(ct));
    Table ut2 = db.getUserTable();
    assertTrue(ut.equals(ut2));
    assertTrue(ut2.equals(ut));
    assertTrue(ut.equals(ut));
    assertFalse(ut.equals(null));
    assertFalse(ut.equals(new Object()));

  }

}
