/**
 * 
 */
package org.melati.poem.test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Enumeration;

import org.melati.poem.Field;
import org.melati.poem.Table;


/**
 * @author timp
 *
 */
public class TableTest extends PoemTestCase {

  /*
   * @see TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
  }
  /*
   * @see TestCase#tearDown()
   */
  protected void tearDown() throws Exception {
    super.tearDown();
  }
  
  /*
   * @see org.melati.poem.Table.Table(Database, String, DefinitionSource)
   */
  public void testTable() {

  }

  /*
   * @see org.melati.poem.Table.postInitialise()
   */
  public void testPostInitialise() {

  }

  /*
   * @see org.melati.poem.Table.getDatabase()
   */
  public void testGetDatabase() {
     assertTrue(getDb().getUserTable().getDatabase().equals(getDb()));
  }

  /*
   * @see org.melati.poem.Table.getName()
   */
  public void testGetName() {
    Table ut = getDb().getUserTable();
    assertEquals("user", ut.getName());
  }

  /*
   * @see org.melati.poem.Table.quotedName()
   */
  public void testQuotedName() {

  }

  /*
   * @see org.melati.poem.Table.getDisplayName()
   */
  public void testGetDisplayName() {

  }

  /*
   * @see org.melati.poem.Table.getDescription()
   */
  public void testGetDescription() {

  }

  /*
   * @see org.melati.poem.Table.getCategory()
   */
  public void testGetCategory() {

  }

  /*
   * @see org.melati.poem.Table.getInfo()
   */
  public void testGetInfo() {

  }

  /*
   * @see org.melati.poem.Table.tableInfoID()
   */
  public void testTableInfoID() {

  }

  /*
   * @see org.melati.poem.Table.getColumn(String)
   */
  public void testGetColumn() {

  }

  /*
   * @see org.melati.poem.Table._getColumn(String)
   */
  public void test_getColumn() {

  }

  /*
   * @see org.melati.poem.Table.columns()
   */
  public void testColumns() {

  }

  /*
   * @see org.melati.poem.Table.getColumnsCount()
   */
  public void testGetColumnsCount() {

  }

  /*
   * @see org.melati.poem.Table.columnWithColumnInfoID(int)
   */
  public void testColumnWithColumnInfoID() {

  }

  /*
   * @see org.melati.poem.Table.troidColumn()
   */
  public void testTroidColumn() {

  }

  /*
   * @see org.melati.poem.Table.deletedColumn()
   */
  public void testDeletedColumn() {

  }

  /*
   * @see org.melati.poem.Table.displayColumn()
   */
  public void testDisplayColumn() {

  }

  /*
   * @see org.melati.poem.Table.setDisplayColumn(Column)
   */
  public void testSetDisplayColumn() {

  }

  /*
   * @see org.melati.poem.Table.primaryCriterionColumn()
   */
  public void testPrimaryCriterionColumn() {

  }

  /*
   * @see org.melati.poem.Table.setSearchColumn(Column)
   */
  public void testSetSearchColumn() {

  }

  /*
   * @see org.melati.poem.Table.defaultOrderByClause()
   */
  public void testDefaultOrderByClause() {

  }

  /*
   * @see org.melati.poem.Table.notifyColumnInfo(ColumnInfo)
   */
  public void testNotifyColumnInfo() {

  }

  /*
   * @see org.melati.poem.Table.displayColumns(DisplayLevel)
   */
  public void testDisplayColumns() {

  }

  /*
   * @see org.melati.poem.Table.displayColumnsCount(DisplayLevel)
   */
  public void testDisplayColumnsCount() {

  }

  /*
   * @see org.melati.poem.Table.getDetailDisplayColumns()
   */
  public void testGetDetailDisplayColumns() {

  }

  /*
   * @see org.melati.poem.Table.getDetailDisplayColumnsCount()
   */
  public void testGetDetailDisplayColumnsCount() {

  }

  /*
   * @see org.melati.poem.Table.getRecordDisplayColumns()
   */
  public void testGetRecordDisplayColumns() {

  }

  /*
   * @see org.melati.poem.Table.getRecordDisplayColumnsCount()
   */
  public void testGetRecordDisplayColumnsCount() {

  }

  /*
   * @see org.melati.poem.Table.getSummaryDisplayColumns()
   */
  public void testGetSummaryDisplayColumns() {

  }

  /*
   * @see org.melati.poem.Table.getSearchCriterionColumns()
   */
  public void testGetSearchCriterionColumns() {

  }

  /*
   * @see org.melati.poem.Table.getSearchCriterionColumnsCount()
   */
  public void testGetSearchCriterionColumnsCount() {

  }

  /*
   * @see org.melati.poem.Table.dbModifyStructure(String)
   */
  public void testDbModifyStructure() {

  }

  /*
   * @see org.melati.poem.Table.load(PoemTransaction, Persistent)
   */
  public void testLoad() {

  }

  /*
   * @see org.melati.poem.Table.delete(Integer, PoemTransaction)
   */
  public void testDelete() {

  }

  /*
   * @see org.melati.poem.Table.writeDown(PoemTransaction, Persistent)
   */
  public void testWriteDown() {

  }

  /*
   * @see org.melati.poem.Table.uncacheContents()
   */
  public void testUncacheContents() {

  }

  /*
   * @see org.melati.poem.Table.trimCache(int)
   */
  public void testTrimCache() {

  }

  /*
   * @see org.melati.poem.Table.getCacheInfo()
   */
  public void testGetCacheInfo() {

  }

  /*
   * @see org.melati.poem.Table.addListener(TableListener)
   */
  public void testAddListener() {

  }

  /*
   * @see org.melati.poem.Table.notifyTouched(PoemTransaction, Persistent)
   */
  public void testNotifyTouched() {

  }

  /*
   * @see org.melati.poem.Table.serial(PoemTransaction)
   */
  public void testSerial() {

  }

  /*
   * @see org.melati.poem.Table.readLock()
   */
  public void testReadLock() {

  }

  /*
   * @see org.melati.poem.Table.getObject(Integer)
   */
  public void testGetObjectInteger() {

  }

  /*
   * @see org.melati.poem.Table.getObject(int)
   */
  public void testGetObjectInt() {

  }

  /*
   * @see org.melati.poem.Table.selectionSQL(String, String, boolean)
   */
  public void testSelectionSQLStringStringBoolean() {

  }

  /*
   * @see org.melati.poem.Table.selectionSQL(String, String, String, boolean, boolean)
   */
  public void testSelectionSQLStringStringStringBooleanBoolean() {

  }

  /*
   * @see org.melati.poem.Table.troidSelection(String, String, boolean, PoemTransaction)
   */
  public void testTroidSelectionStringStringBooleanPoemTransaction() {

  }

  /*
   * @see org.melati.poem.Table.troidSelection(Persistent, String, boolean, boolean, PoemTransaction)
   */
  public void testTroidSelectionPersistentStringBooleanBooleanPoemTransaction() {

  }

  /**
   * @see org.melati.poem.Table.rememberAllTroids(boolean)
   */
  public void testRememberAllTroids() {

  }

  /**
   * @see org.melati.poem.Table.setCacheLimit(Integer)
   */
  public void testSetCacheLimit() {

  }

  /**
   * @see org.melati.poem.Table.troidSelection(String, String, boolean)
   */
  public void testTroidSelectionStringStringBoolean() {

  }

  /**
   * @see org.melati.poem.Table.selection()
   */
  public void testSelection() {

  }

  /**
   * @see org.melati.poem.Table.selection(String)
   */
  public void testSelectionString() {

  }

  /**
   * @see org.melati.poem.Table.firstSelection(String)
   */
  public void testFirstSelection() {

  }

  /**
   * @see org.melati.poem.Table.selection(String, String, boolean)
   */
  public void testSelectionStringStringBoolean() {

  }

  /**
   * @see org.melati.poem.Table.selection(Persistent)
   */
  public void testSelectionPersistent() {

  }

  /**
   * @see org.melati.poem.Table.selection(Persistent, String)
   */
  public void testSelectionPersistentString() {

  }

  /**
   * @see org.melati.poem.Table.selection(Persistent, String, boolean, boolean)
   */
  public void testSelectionPersistentStringBooleanBoolean() {

  }

  /**
   * @see org.melati.poem.Table.selection(String, String, boolean, int, int)
   */
  public void testSelectionStringStringBooleanIntInt() {

  }

  /**
   * @see org.melati.poem.Table.selection(Persistent, String, boolean, boolean, int, int)
   */
  public void testSelectionPersistentStringBooleanBooleanIntInt() {

  }

  /**
   * @see org.melati.poem.Table.countSQL(String)
   */
  public void testCountSQLString() {

  }

  /**
   * @see org.melati.poem.Table.countSQL(String, String, boolean, boolean)
   */
  public void testCountSQLStringStringBooleanBoolean() {

  }

  /**
   * @see org.melati.poem.Table.count(String, boolean, boolean)
   */
  public void testCountStringBooleanBoolean() {

  }

  /*
   * @see org.melati.poem.Table.count(String, boolean)
   */
  public void testCountStringBoolean() {

  }

  /*
   * @see org.melati.poem.Table.count(String)
   */
  public void testCountString() {

  }

  /*
   * @see org.melati.poem.Table.count()
   */
  public void testCount() {

  }

  /*
   * @see org.melati.poem.Table.exists(String)
   */
  public void testExistsString() {

  }

  /*
   * @see org.melati.poem.Table.exists(Persistent)
   */
  public void testExistsPersistent() {

  }

  /*
   * @see org.melati.poem.Table.appendWhereClause(StringBuffer, Persistent)
   */
  public void testAppendWhereClause() {

  }

  /*
   * @see org.melati.poem.Table.whereClause(Persistent)
   */
  public void testWhereClausePersistent() {

  }

  /*
   * @see org.melati.poem.Table.whereClause(Persistent, boolean, boolean)
   */
  public void testWhereClausePersistentBooleanBoolean() {

  }

  /*
   * @see org.melati.poem.Table.cnfWhereClause(Enumeration)
   */
  public void testCnfWhereClauseEnumeration() {

  }

  /*
   * @see org.melati.poem.Table.cnfWhereClause(Enumeration, boolean, boolean)
   */
  public void testCnfWhereClauseEnumerationBooleanBoolean() {

  }

  /*
   * @see org.melati.poem.Table.referencesTo(Persistent)
   */
  public void testReferencesToPersistent() {

  }

  /*
   * @see org.melati.poem.Table.referencesTo(Table)
   */
  public void testReferencesToTable() {

  }

  /*
   * @see org.melati.poem.Table.troidFor(Persistent)
   */
  public void testTroidFor() {

  }

  /*
   * @see org.melati.poem.Table.create(Persistent)
   */
  public void testCreatePersistent() {

  }

  /*
   * @see org.melati.poem.Table.create(Initialiser)
   */
  public void testCreateInitialiser() {

  }

  /*
   * @see org.melati.poem.Table.newPersistent()
   */
  public void testNewPersistent() {

  }

  /*
   * @see org.melati.poem.Table._newPersistent()
   */
  public void test_newPersistent() {

  }

  /*
   * @see org.melati.poem.Table.delete_unsafe(String)
   */
  public void testDelete_unsafe() {

  }

  /*
   * @see org.melati.poem.Table.extrasCount()
   */
  public void testExtrasCount() {

  }

  /*
   * @see org.melati.poem.Table.getDefaultCanRead()
   */
  public void testGetDefaultCanRead() {

  }

  /*
   * @see org.melati.poem.Table.getDefaultCanWrite()
   */
  public void testGetDefaultCanWrite() {

  }

  /*
   * @see org.melati.poem.Table.getDefaultCanDelete()
   */
  public void testGetDefaultCanDelete() {

  }

  /*
   * @see org.melati.poem.Table.getCanCreate()
   */
  public void testGetCanCreate() {

  }

  /*
   * @see org.melati.poem.Table.canReadColumn()
   */
  public void testCanReadColumn() {

  }

  /*
   * @see org.melati.poem.Table.canSelectColumn()
   */
  public void testCanSelectColumn() {

  }

  /*
   * @see org.melati.poem.Table.canWriteColumn()
   */
  public void testCanWriteColumn() {

  }

  /*
   * @see org.melati.poem.Table.canDeleteColumn()
   */
  public void testCanDeleteColumn() {

  }

  /*
   * @see org.melati.poem.Table.addColumnAndCommit(ColumnInfo)
   */
  public void testAddColumnAndCommit() {

  }

  /*
   * @see org.melati.poem.Table.toString()
   */
  public void testToString() {
    Table ut = getDb().getUserTable();
    assertEquals("user (from the data structure definition)", ut.toString());

  }

  /*
   * @see org.melati.poem.Table.dumpCacheAnalysis()
   */
  public void testDumpCacheAnalysis() {

  }

  /**
   * @see org.melati.poem.Table#dump()
   */
  public void testDump() {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PrintStream ps = new PrintStream(baos);
    getDb().getUserTable().dump(ps);
    System.err.println(baos.toString());
    assertTrue(baos.toString().startsWith("=== table user (tableinfo id 0"));
  }

  /**
   * @see org.melati.poem.Table.cachedSelection(String, String)
   */
  public void testCachedSelection() {

  }

  /**
   * @see org.melati.poem.Table.cachedCount(String, boolean)
   */
  public void testCachedCountStringBoolean() {

  }

  /**
   * @see org.melati.poem.Table.cachedCount(String, boolean, boolean)
   */
  public void testCachedCountStringBooleanBoolean() {

  }

  /**
   * @see org.melati.poem.Table.cachedCount(Persistent, boolean, boolean)
   */
  public void testCachedCountPersistentBooleanBoolean() {

  }

  /**
   * @see org.melati.poem.Table.cachedCount(String)
   */
  public void testCachedCountString() {

  }

  /**
   * @see org.melati.poem.Table.cachedExists(String)
   */
  public void testCachedExists() {

  }

  /**
   * @see org.melati.poem.Table.cachedSelectionType(String, String, boolean)
   */
  public void testCachedSelectionType() {

  }

  /**
   * @see org.melati.poem.Table.cachedSelectionField(String, String, boolean, Integer, String)
   */
  public void testCachedSelectionField() {
    //getDb().setLogSQL(true);
    Field userTables = getDb().getTableInfoTable().cachedSelectionField(
        "\"TABLEINFO\".\"DISPLAYORDER\" < 3000", null, true, null, "userTables");
    Enumeration them = userTables.getPossibilities();
    assertEquals(them.nextElement().toString(), "userTables: ");
    assertEquals(them.nextElement().toString(), "userTables: User");
    assertEquals(them.nextElement().toString(), "userTables: Group");
    assertEquals(them.nextElement().toString(), "userTables: Capability");
    assertEquals(them.nextElement().toString(), "userTables: Group membership");
    assertEquals(them.nextElement().toString(), "userTables: Group capability");
    assertFalse(them.hasMoreElements());
    assertEquals(null, userTables.getRaw());    
    
   // with order by 
    userTables = getDb().getTableInfoTable().cachedSelectionField(
        "\"TABLEINFO\".\"DISPLAYORDER\" < 3000", 
        "\"TABLEINFO\".\"DISPLAYNAME\"", 
        true, null, "userTables");
    them = userTables.getPossibilities();
    assertEquals(them.nextElement().toString(), "userTables: ");
    assertEquals(them.nextElement().toString(), "userTables: Capability");
    assertEquals(them.nextElement().toString(), "userTables: Group");
    assertEquals(them.nextElement().toString(), "userTables: Group capability");
    assertEquals(them.nextElement().toString(), "userTables: Group membership");
    assertEquals(them.nextElement().toString(), "userTables: User");
    assertFalse(them.hasMoreElements());
    assertEquals(null, userTables.getRaw());    
    
    // without null option
    userTables = getDb().getTableInfoTable().cachedSelectionField(
        "\"TABLEINFO\".\"DISPLAYORDER\" < 3000", 
        "\"TABLEINFO\".\"DISPLAYNAME\"", 
        false, null, "userTables");
    them = userTables.getPossibilities();
    assertEquals(them.nextElement().toString(), "userTables: Capability");
    assertEquals(them.nextElement().toString(), "userTables: Group");
    assertEquals(them.nextElement().toString(), "userTables: Group capability");
    assertEquals(them.nextElement().toString(), "userTables: Group membership");
    assertEquals(them.nextElement().toString(), "userTables: User");
    assertFalse(them.hasMoreElements());
    assertEquals(null, userTables.getRaw());    
    
    // with a troid
    userTables = getDb().getTableInfoTable().cachedSelectionField(
        "\"TABLEINFO\".\"DISPLAYORDER\" < 3000", 
        "\"TABLEINFO\".\"DISPLAYNAME\"", 
        false, new Integer(0), "userTables");
    them = userTables.getPossibilities();
    assertEquals(them.nextElement().toString(), "userTables: Capability");
    assertEquals(them.nextElement().toString(), "userTables: Group");
    assertEquals(them.nextElement().toString(), "userTables: Group capability");
    assertEquals(them.nextElement().toString(), "userTables: Group membership");
    assertEquals(them.nextElement().toString(), "userTables: User");
    assertFalse(them.hasMoreElements());
    assertEquals(new Integer(0), userTables.getRaw());    
  }

  /**
   * @see org.melati.poem.Table.defineColumn(Column, boolean)
   */
  public void testDefineColumnColumnBoolean() {

  }

  /**
   * @see org.melati.poem.Table.defineColumn(Column)
   */
  public void testDefineColumnColumn() {

  }

  /**
   * @see org.melati.poem.Table.setTableInfo(TableInfo)
   */
  public void testSetTableInfo() {

  }

  /**
   * @see org.melati.poem.Table.getTableInfo()
   */
  public void testGetTableInfo() {

  }

  /**
   * @see org.melati.poem.Table.defaultDisplayName()
   */
  public void testDefaultDisplayName() {

  }

  /**
   * @see org.melati.poem.Table.getDsdName()
   */
  public void testGetDsdName() {

  }

  /**
   * @see org.melati.poem.Table.defaultDisplayOrder()
   */
  public void testDefaultDisplayOrder() {

  }

  /**
   * @see org.melati.poem.Table.defaultDescription()
   */
  public void testDefaultDescription() {

  }

  /**
   * @see org.melati.poem.Table.defaultCacheLimit()
   */
  public void testDefaultCacheLimit() {

  }

  /**
   * @see org.melati.poem.Table.defaultRememberAllTroids()
   */
  public void testDefaultRememberAllTroids() {

  }

  /**
   * @see org.melati.poem.Table.defaultCategory()
   */
  public void testDefaultCategory() {

  }

  /**
   * @see org.melati.poem.Table.createTableInfo()
   */
  public void testCreateTableInfo() {

  }

  /**
   * @see org.melati.poem.Table.unifyWithColumnInfo()
   */
  public void testUnifyWithColumnInfo() {

  }

  /**
   * @see org.melati.poem.Table.unifyWithDB(ResultSet)
   */
  public void testUnifyWithDB() {

  }

  /**
   * @see org.melati.poem.Table.init()
   */
  public void testInit() {

  }

  /**
   * @see org.melati.poem.Table.hashCode()
   */
  public void testHashCode() {
    Table ut = getDb().getUserTable();
    Table ut2 = getDb().getUserTable();
    assertTrue(ut.equals(ut2));
    assertEquals(ut.hashCode(), ut2.hashCode());
  }

  /**
   * @see org.melati.poem.Table.equals(Object)
   */
  public void testEqualsObject() {
    Table ut = getDb().getUserTable();
    Table ct = getDb().getCapabilityTable();
    assertFalse(ut.equals(ct));
    Table ut2 = getDb().getUserTable();
    assertTrue(ut.equals(ut2));
    assertTrue(ut2.equals(ut));
    assertTrue(ut.equals(ut));
    assertFalse(ut.equals(null));
    assertFalse(ut.equals(new Object()));

  }

}
