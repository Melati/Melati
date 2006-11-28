/**
 * 
 */
package org.melati.poem.test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.melati.poem.AccessToken;
import org.melati.poem.DoubleCreatePoemException;
import org.melati.poem.InvalidOperationOnFloatingPersistentPoemException;
import org.melati.poem.Persistent;
import org.melati.poem.PoemThread;
import org.melati.poem.User;
import org.melati.poem.WritePersistentAccessPoemException;

/**
 * @author timp
 * @since 27/11/2006
 */
public class PersistentTest extends PoemTestCase {

  /**
   * Constructor for PersistentTest.
   * @param name
   */
  public PersistentTest(String name) {
    super(name);
  }

  /**
   * @see PoemTestCase#setUp()
   */
  protected void setUp()
      throws Exception {
    super.setUp();
  }

  /**
   * @see PoemTestCase#tearDown()
   */
  protected void tearDown()
      throws Exception {
    super.tearDown();
  }

  /**
   * @see org.melati.poem.Persistent#hashCode()
   */
  public void testHashCode() {
    Persistent p = getDb().getUserTable().guestUser();
    assertEquals(3599307, p.hashCode());
  }

  /**
   * 
   * @see org.melati.poem.Persistent#invalidate()
   */
  public void testInvalidate() {
    Persistent p = getDb().getUserTable().newPersistent();
    try {
      p.invalidate();
      fail("Should have blown up");
    } catch (InvalidOperationOnFloatingPersistentPoemException e) {
      e = null;
    }
    
    // guestUser does not hit the database
    int hits = getDb().getQueryCount();
    //System.err.println(hits);
    int hits2 = getDb().getQueryCount();
    getDb().getUserTable().guestUser();
    //System.err.println(hits2);
    getDb().getUserTable().guestUser();
    int hits3 = getDb().getQueryCount();
    //System.err.println(hits3);
    getDb().uncacheContents();
    int hits4 = getDb().getQueryCount();
    //System.err.println(hits4);
    getDb().getUserTable().guestUser();
    int hits5 = getDb().getQueryCount();
    //System.err.println(hits5);
    assertTrue(hits == hits2);
    assertTrue(hits == hits3);
    assertTrue(hits == hits4);
    assertTrue(hits == hits5);
    
    // Count always increments queryCount
    getDb().getUserTable().count();
    int countHits1 = getDb().getQueryCount();
    //System.err.println(countHits1);
    assertTrue(countHits1 == hits5 + 1);
    getDb().getUserTable().count();
    int countHits2 = getDb().getQueryCount();
    //System.err.println(countHits2);
    assertTrue(countHits2 == countHits1 + 1);
    
    // Selection is cached but not invalidated 
    // when table cache is invalidated
    getDb().getUserTable().selection();
    int selectionHits1 = getDb().getQueryCount();
    //System.err.println(selectionHits1);
    getDb().getUserTable().selection();
    int selectionHits2 = getDb().getQueryCount();
    //System.err.println(selectionHits2);
    assertTrue(selectionHits1 == selectionHits2);
    getDb().uncacheContents();
    getDb().getUserTable().selection();
    int selectionHits3 = getDb().getQueryCount();
    //System.err.println(selectionHits3);
    assertTrue(selectionHits2 == selectionHits3);
    
    // Gets should increment query count
    // but don't seem to
    getDb().getUserTable().getUserObject(0);
    int getHits1 = getDb().getQueryCount();
    //System.err.println(getHits1);
    assertTrue(selectionHits3 == getHits1);
    getDb().uncacheContents();
    getDb().getUserTable().getUserObject(0);
    int getHits2 = getDb().getQueryCount();
    //System.err.println(getHits2);
    assertTrue(getHits1  == getHits2);
    
    Persistent p2 = getDb().getUserTable().newPersistent();
    p2.setCooked("name", "test");
    p2.setCooked("login", "test");
    p2.setCooked("password", "test");
    p2.makePersistent();
    getDb().getUserTable().getUserObject(2);
    int getHits3 = getDb().getQueryCount();
    //System.err.println(getHits3);
    assertTrue(getHits3 == getHits2 + 1);
    p2.delete();
    PoemThread.commit();
    // todo - find a scenario where invalidating the cache makes 
    // a difference
  }

  /**
   * @see org.melati.poem.Persistent#Persistent(Table, Integer)
   */
  public void testPersistentTableInteger() {
    Persistent p = new Persistent(getDb().getUserTable(), new Integer(0));
    try {
      p.makePersistent();
      fail("Should have blown up");
    } catch (DoubleCreatePoemException e) {
      e = null;      
    }
    p = new Persistent(getDb().getUserTable(), new Integer(2));
    try {
      p.makePersistent();
      fail("Should have blown up");
    } catch (DoubleCreatePoemException e) {
      e = null;      
    }
  }

  /**
   * @see org.melati.poem.Persistent#Persistent(Table)
   */
  public void testPersistentTable() {

  }

  /**
   * @see org.melati.poem.Persistent#Persistent()
   */
  public void testPersistent() {

  }

  /**
   * @see org.melati.poem.Persistent#setStatusNonexistent()
   */
  public void testSetStatusNonexistent() {

  }

  /**
   * @see org.melati.poem.Persistent#setStatusExistent()
   */
  public void testSetStatusExistent() {

  }

  /**
   * @see org.melati.poem.Persistent#statusNonexistent()
   */
  public void testStatusNonexistent() {

  }

  /**
   * @see org.melati.poem.Persistent#statusExistent()
   */
  public void testStatusExistent() {

  }

  /**
   * @see org.melati.poem.Persistent#makePersistent()
   */
  public void testMakePersistent() {

  }

  /**
   * @see org.melati.poem#Persistent.extras()
   */
  public void testExtras() {

  }

  /**
   * @see org.melati.poem.Persistent#getTable()
   */
  public void testGetTable() {

  }

  /**
   * @see org.melati.poem.Persistent#setTable(Table, Integer)
   */
  public void testSetTable() {

  }

  /**
   * @see org.melati.poem.Persistent#getDatabase()
   */
  public void testGetDatabase() {
    Persistent p = new Persistent(getDb().getUserTable(), new Integer(0));
    assertEquals(p.getDatabase(), getDb());
  }

  /**
   * @see org.melati.poem.Persistent#troid()
   */
  public void testTroid() {
    Persistent p = new Persistent(getDb().getUserTable(), new Integer(0));
    assertEquals(p.troid(), new Integer(0));
  }

  /**
   * @see org.melati.poem.Persistent#getTroid()
   */
  public void testGetTroid() {

  }

  /**
   * @see org.melati.poem.Persistent#existenceLock()
   */
  public void testExistenceLock() {

  }

  /**
   * @see org.melati.poem.Persistent#assertCanRead(AccessToken)
   */
  public void testAssertCanReadAccessToken() {

  }

  /**
   * @see org.melati.poem.Persistent#assertCanRead()
   */
  public void testAssertCanRead() {

  }

  /**
   * @see org.melati.poem.Persistent#getReadable()
   */
  public void testGetReadable() {

  }

  /**
   * @see org.melati.poem.Persistent#assertCanWrite(AccessToken)
   */
  public void testAssertCanWriteAccessToken() {

  }

  /**
   * @see org.melati.poem.Persistent#assertCanWrite()
   */
  public void testAssertCanWrite() {
    Persistent p = getDb().getUserTable().administratorUser();
    AccessToken g  = getDb().getUserTable().guestUser();
    try {
      p.assertCanWrite(g);
      fail("Should have bombed");
    } catch (WritePersistentAccessPoemException e) {
      e = null;
    }
    AccessToken a  = getDb().getUserTable().administratorUser();
    p.assertCanWrite(a);
  }

  /**
   * @see org.melati.poem.Persistent#assertCanDelete(AccessToken)
   */
  public void testAssertCanDeleteAccessToken() {

  }

  /**
   * @see org.melati.poem.Persistent#assertCanDelete()
   */
  public void testAssertCanDelete() {

  }

  /**
   * @see org.melati.poem.Persistent#assertCanCreate(AccessToken)
   */
  public void testAssertCanCreateAccessToken() {

  }

  /**
   * @see org.melati.poem.Persistent#assertCanCreate()
   */
  public void testAssertCanCreate() {

  }

  /**
   * @see org.melati.poem.Persistent#getRaw(String)
   */
  public void testGetRaw() {

  }

  /**
   * @see org.melati.poem.Persistent#getRawString(String)
   */
  public void testGetRawString() {

  }

  /**
   * @see org.melati.poem.Persistent#setRaw(String, Object)
   */
  public void testSetRaw() {

  }

  /**
   * @see org.melati.poem.Persistent#setRawString(String, String)
   */
  public void testSetRawString() {

  }

  /**
   * @see org.melati.poem.Persistent#getCooked(String)
   */
  public void testGetCooked() {

  }

  /**
   * @see org.melati.poem.Persistent#getCookedString(String, MelatiLocale, int)
   */
  public void testGetCookedString() {

  }

  /**
   * @see org.melati.poem.Persistent#setCooked(String, Object)
   */
  public void testSetCooked() {

  }

  /**
   * @see org.melati.poem.Persistent#getField(String)
   */
  public void testGetField() {

  }

  /**
   * @see org.melati.poem.Persistent#fieldsOfColumns(Enumeration)
   */
  public void testFieldsOfColumns() {

  }

  /**
   * @see org.melati.poem.Persistent#getFields()
   */
  public void testGetFields() {

  }

  /**
   * @see org.melati.poem.Persistent#getRecordDisplayFields()
   */
  public void testGetRecordDisplayFields() {

  }

  /**
   * @see org.melati.poem.Persistent#getDetailDisplayFields()
   */
  public void testGetDetailDisplayFields() {

  }

  /**
   * @see org.melati.poem.Persistent#getSummaryDisplayFields()
   */
  public void testGetSummaryDisplayFields() {

  }

  /**
   * @see org.melati.poem.Persistent#getSearchCriterionFields()
   */
  public void testGetSearchCriterionFields() {

  }

  /**
   * @see org.melati.poem.Persistent#delete_unsafe()
   */
  public void testDelete_unsafe() {

  }

  /**
   * @see org.melati.poem.Persistent#getPrimaryDisplayField()
   */
  public void testGetPrimaryDisplayField() {

  }

  /**
   * @see org.melati.poem.Persistent#delete(Map)
   */
  public void testDeleteMap() {

  }

  /**
   * @see org.melati.poem.Persistent#delete()
   */
  public void testDelete() {

  }

  /**
   * @see org.melati.poem.Persistent#deleteAndCommit(Map)
   */
  public void testDeleteAndCommitMap() {

  }

  /**
   * @see org.melati.poem.Persistent#deleteAndCommit()
   */
  public void testDeleteAndCommit() {

  }

  /**
   * @see org.melati.poem.Persistent#duplicated()
   */
  public void testDuplicated() {

  }

  /**
   * @see org.melati.poem.Persistent#duplicatedFloating()
   */
  public void testDuplicatedFloating() {

  }

  /**
   * @see org.melati.poem.Persistent#toString()
   */
  public void testToString() {

  }

  /**
   * @see org.melati.poem.Persistent#displayString(MelatiLocale, int)
   */
  public void testDisplayStringMelatiLocaleInt() {

  }

  /** 
   * @see org.melati.poem.Persistent#displayString(MelatiLocale)
   */
  public void testDisplayStringMelatiLocale() {

  }

  /**
   * @see org.melati.poem.Persistent#displayString()
   */
  public void testDisplayString() {

  }

  /**
   * @see org.melati.poem.Persistent#equals(Object)
   */
  public void testEqualsObject() {

  }

  /**
   * @see org.melati.poem.Persistent#dump(PrintStream)
   */
  public void testDump() {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PrintStream ps = new PrintStream(baos);
    try { 
      Persistent p = new Persistent(getDb().getUserTable(), new Integer(0));
      p.dump(ps);
      fail("Should have blown up");
    } catch (ClassCastException e) {
      e = null;
    }
    // Some gets written before blowing up
    assertTrue(baos.toString().startsWith("user/0"));
    
    baos = new ByteArrayOutputStream();
    ps = new PrintStream(baos);
    User u = (User)getDb().getUserTable().newPersistent();
    u.dump(ps);
    assertTrue(baos.toString().startsWith("user/null"));
    u = (User)getDb().getUserTable().guestUser();
    baos = new ByteArrayOutputStream();
    ps = new PrintStream(baos);
    u.dump(ps);
    assertTrue(baos.toString().startsWith("user/0"));
  
  }

  /** 
   * @see org.melati.poem.Persistent#postWrite()
   */
  public void testPostWrite() {

  }

  /**
   * @see org.melati.poem.Persistent#postInsert()
   */
  public void testPostInsert() {

  }

  /**
   * @see org.melati.poem.Persistent#postModify()
   */
  public void testPostModify() {

  }

  /**
   * @see org.melati.poem.Persistent#preEdit()
   */
  public void testPreEdit() {

  }

  /**
   * @see org.melati.poem.Persistent#postEdit(boolean)
   */
  public void testPostEdit() {

  }

  /**
   * @see org.melati.poem.Persistent#countMatchSQL(boolean, boolean)
   */
  public void testCountMatchSQL() {
    try { 
      Persistent p = new Persistent(getDb().getUserTable(), new Integer(0));
      p.countMatchSQL(true, true);
      fail("Should have blown up");
    } catch (ClassCastException e) {
      e = null;
    }
    User u = (User)getDb().getUserTable().newPersistent();
    // All the same as we have no deleted or unselectable columns
    assertEquals("SELECT count(*) FROM \"USER\"", u.countMatchSQL(true, true));
    assertEquals("SELECT count(*) FROM \"USER\"", u.countMatchSQL(true, false));
    assertEquals("SELECT count(*) FROM \"USER\"", u.countMatchSQL(false, true));
    assertEquals("SELECT count(*) FROM \"USER\"", u.countMatchSQL(false, false));

  }

  /**
   * @see org.melati.poem.Persistent#fromClause()
   */
  public void testFromClause() {
    Persistent p = new Persistent(getDb().getUserTable(), new Integer(0));
    assertEquals("\"USER\"", p.fromClause());
  }

  /**
   * @see org.melati.poem.Persistent#otherMatchTables()
   */
  public void testOtherMatchTables() {

  }

  /**
   * @see org.melati.util.Transactioned#Transactioned(TransactionPool)
   */
  public void testTransactionedTransactionPool() {

  }

  /**
   * @see org.melati.util.Transactioned#Transactioned()
   */
  public void testTransactioned() {

  }

  /**
   * @see org.melati.util.Transactioned#markValid()
   */
  public void testMarkValid() {

  }

}
