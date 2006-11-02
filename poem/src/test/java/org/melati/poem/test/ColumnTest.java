/**
 * 
 */
package org.melati.poem.test;

import org.melati.poem.AppBugPoemException;
import org.melati.poem.Column;
import org.melati.poem.DisplayLevel;
import org.melati.poem.User;
import org.melati.util.EnumUtils;

/**
 * @author timp
 *
 */
public class ColumnTest extends PoemTestCase {

  /**
   * Constructor for ColumnTest.
   * @param name
   */
  public ColumnTest(String name) {
    super(name);
  }

  /*
   * @see PoemTestCase#setUp()
   */
  protected void setUp()
      throws Exception {
    super.setUp();
  }

  /*
   * @see PoemTestCase#tearDown()
   */
  protected void tearDown()
      throws Exception {
    super.tearDown();
  }

  /**
   * @see org.melati.poem.Column#
   *      Column(Table, String, SQLPoemType, DefinitionSource)
   */
  public void testColumn() {

  }

  /**
   * @see org.melati.poem.Column#getDatabase()
   */
  public void testGetDatabase() {
    assertEquals(getDb(), 
        getDb().getUserTable().troidColumn().getDatabase());
  }

  /**
   * @see org.melati.poem.Column#getTable()
   */
  public void testGetTable() {
    assertEquals(getDb().getUserTable(), 
        getDb().getUserTable().troidColumn().getTable());
  }

  /**
   * @see org.melati.poem.Column#getName()
   */
  public void testGetName() {
    assertEquals("id", 
        getDb().getUserTable().troidColumn().getName());
  }

  /**
   * @see org.melati.poem.Column#quotedName()
   */
  public void testQuotedName() {
    assertEquals(getDb().quotedName("id"), 
        getDb().getUserTable().troidColumn().quotedName());
  }

  /**
   * @see org.melati.poem.Column#fullQuotedName()
   */
  public void testFullQuotedName() {
    assertEquals(getDb().quotedName("user")+ "." + getDb().quotedName("id"), 
        getDb().getUserTable().troidColumn().fullQuotedName());

  }

  /**
   * @see org.melati.poem.Column#getDisplayName()
   */
  public void testGetDisplayName() {
    assertEquals("Id", 
        getDb().getUserTable().troidColumn().getDisplayName());

  }

  /**
   * @see org.melati.poem.Column#getDescription()
   */
  public void testGetDescription() {
    assertEquals("The Table Row Object ID", 
        getDb().getUserTable().troidColumn().getDescription());
  }

  /**
   * @see org.melati.poem.Column#getColumnInfo()
   */
  public void testGetColumnInfo() {
    assertEquals("columninfo/0", 
        getDb().getUserTable().troidColumn().getColumnInfo().toString());
  }

  /**
   * @see org.melati.poem.Column#getDisplayLevel()
   */
  public void testGetDisplayLevel() {
    assertEquals(DisplayLevel.detail, 
        getDb().getUserTable().troidColumn().getDisplayLevel());
    assertEquals(DisplayLevel.primary, 
        getDb().getUserTable().getColumn("name").getDisplayLevel());
    assertEquals(DisplayLevel.summary, 
        getDb().getUserTable().getColumn("login").getDisplayLevel());
  }

  /**
   * @see org.melati.poem.Column#setDisplayLevel(DisplayLevel)
   */
  public void testSetDisplayLevel() {

  }

  /**
   * @see org.melati.poem.Column#getSearchability()
   */
  public void testGetSearchability() {

  }

  /**
   * @see org.melati.poem.Column#setSearchability(Searchability)
   */
  public void testSetSearchability() {

  }

  /**
   * @see org.melati.poem.Column#getUserEditable()
   */
  public void testGetUserEditable() {

  }

  /**
   * @see org.melati.poem.Column#getUserCreateable()
   */
  public void testGetUserCreateable() {

  }

  /**
   * @see org.melati.poem.Column#getSQLType()
   */
  public void testGetSQLType() {

  }

  /**
   * @see org.melati.poem.Column#getType()
   */
  public void testGetType() {

  }

  /**
   * @see org.melati.poem.Column#isTroidColumn()
   */
  public void testIsTroidColumn() {

  }

  /**
   * @see org.melati.poem.Column#isDeletedColumn()
   */
  public void testIsDeletedColumn() {

  }

  /**
   * @see org.melati.poem.Column#getIndexed()
   */
  public void testGetIndexed() {

  }

  /**
   * @see org.melati.poem.Column#getUnique()
   */
  public void testGetUnique() {

  }

  /**
   * @see org.melati.poem.Column#getIntegrityFix()
   */
  public void testGetIntegrityFix() {

  }

  /**
   * @see org.melati.poem.Column#setIntegrityFix(StandardIntegrityFix)
   */
  public void testSetIntegrityFix() {

  }

  /**
   * @see org.melati.poem.Column#getRenderInfo()
   */
  public void testGetRenderInfo() {

  }

  /**
   * @see org.melati.poem.Column#getWidth()
   */
  public void testGetWidth() {

  }

  /**
   * @see org.melati.poem.Column#getHeight()
   */
  public void testGetHeight() {

  }

  /**
   * @see org.melati.poem.Column#getDisplayOrderPriority()
   */
  public void testGetDisplayOrderPriority() {

  }

  /**
   * @see org.melati.poem.Column#getSortDescending()
   */
  public void testGetSortDescending() {

  }

  /**
   * @see org.melati.poem.Column#toString()
   */
  public void testToString() {

  }

  /**
   * @see org.melati.poem.Column#dump()
   */
  public void testDump() {

  }

  /**
   * @see org.melati.poem.Column#eqClause(Object)
   */
  public void testEqClause() {

  }

  /**
   * @see org.melati.poem.Column#resultSetWhereEq(Object)
   */
  public void testResultSetWhereEq() {

  }

  /**
   * @see org.melati.poem.Column#troidSelectionWhereEq(Object)
   */
  public void testTroidSelectionWhereEq() {

  }

  /**
   * @see org.melati.poem.Column#selectionWhereEq(Object)
   */
  public void testSelectionWhereEq() {

  }

  /**
   * @see org.melati.poem.Column#firstWhereEq(Object)
   */
  public void testFirstWhereEq() {

  }

  /**
   * @see org.melati.poem.Column#cachedSelectionWhereEq(Object)
   */
  public void testCachedSelectionWhereEq() {

  }

  /**
   * @see org.melati.poem.Column#getRaw(Persistent)
   */
  public void testGetRaw() {

  }

  /**
   * @see org.melati.poem.Column#getRaw_unsafe(Persistent)
   */
  public void testGetRaw_unsafe() {

  }

  /**
   * @see org.melati.poem.Column#setRaw(Persistent, Object)
   */
  public void testSetRaw() {

  }

  /**
   * @see org.melati.poem.Column#setRaw_unsafe(Persistent, Object)
   */
  public void testSetRaw_unsafe() {

  }

  /**
   * @see org.melati.poem.Column#getCooked(Persistent)
   */
  public void testGetCooked() {

  }

  /**
   * @see org.melati.poem.Column#setCooked(Persistent, Object)
   */
  public void testSetCooked() {

  }

  /**
   * @see org.melati.poem.Column#load_unsafe(ResultSet, int, Persistent)
   */
  public void testLoad_unsafe() {

  }

  /**
   * @see org.melati.poem.Column#save_unsafe(Persistent, PreparedStatement, int)
   */
  public void testSave_unsafe() {

  }

  /**
   * @see org.melati.poem.Column#asField(Persistent)
   */
  public void testAsField() {

  }

  /**
   * @see org.melati.poem.Column#asEmptyField()
   */
  public void testAsEmptyField() {

  }

  /**
   * @see org.melati.poem.Column#setRawString(Persistent, String)
   */
  public void testSetRawString() {

  }

  /**
   * @see org.melati.poem.Column#referencesTo(Persistent)
   */
  public void testReferencesTo() {
    Column userTroidColumn = getDb().getUserTable().troidColumn();
    User admin = getDb().getUserTable().administratorUser();
    assertEquals("", EnumUtils.concatenated("|", userTroidColumn.referencesTo(admin)));
    Column userColumn = getDb().getGroupMembershipTable().getUserColumn();
    assertEquals("groupmembership/0", 
                 EnumUtils.concatenated("|", userColumn.referencesTo(admin)));    
  }

  /**
   * @see org.melati.poem.Column#ensure(Persistent)
   */
  public void testEnsure() {
    User fred = (User)getDb().getUserTable().newPersistent();
    fred.setName("Fred");
    fred.setLogin("fred");
    fred.setPassword("fred");
    Column userNameColumn = getDb().getUserTable().getColumn("name");
    User ensured = (User)userNameColumn.ensure(fred);
    assertEquals("Fred", ensured.getName());
    fred.delete();
  }

  /**
   * @see org.melati.poem.Column#firstFree(String)
   */
  public void testFirstFree() {
    Column userTroidColumn = getDb().getUserTable().troidColumn();
    assertEquals(2, userTroidColumn.firstFree(null));    

    try {
      Column userNameColumn = getDb().getUserTable().getColumn("name");
      userNameColumn.firstFree(null);
      fail("Should have bombed");
    } catch (AppBugPoemException e) {
      e = null;
    }
    // Think that these two show what could go wrong
    assertEquals(0, userTroidColumn.firstFree("ID > 1"));    
    assertEquals(1, userTroidColumn.firstFree("ID < 1"));    

  }

}
