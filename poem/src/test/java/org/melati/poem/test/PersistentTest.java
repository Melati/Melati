package org.melati.poem.test;

import org.melati.poem.*;
import org.melati.poem.User;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.text.DateFormat;
import java.util.Enumeration;

/**
 * @author timp
 * @since 27/11/2006
 */
public class PersistentTest extends EverythingTestCase {

  public PersistentTest(String name) {
    super(name);
  }

  protected void setUp()
      throws Exception {
    super.setUp();
  }

  protected void tearDown()
      throws Exception {
    super.tearDown();
  }

  /**
   * 
   * @see org.melati.poem.JdbcPersistent#invalidate()
   */
  public void testInvalidate() {
    getDb().uncache();
    JdbcPersistent p = (JdbcPersistent)getDb().getUserTable().newPersistent();
    try {
      p.invalidate();
      fail("Should have blown up");
    } catch (InvalidOperationOnFloatingPersistentPoemException e) {
      e = null;
    }
    
    // guestUser does not hit the database
    int hits = getDb().getQueryCount();
    int hits2 = getDb().getQueryCount();
    getDb().getUserTable().guestUser();
    getDb().getUserTable().guestUser();
    int hits3 = getDb().getQueryCount();
    getDb().uncache();
    int hits4 = getDb().getQueryCount();
    getDb().getUserTable().guestUser();
    int hits5 = getDb().getQueryCount();
    assertTrue(hits == hits2);
    assertTrue(hits == hits3);
    assertTrue(hits == hits4);
    assertTrue(hits == hits5);
    
    // Count always increments queryCount
    getDb().getUserTable().count();
    int countHits1 = getDb().getQueryCount();
    assertEquals(hits5 + 1, countHits1 );
    getDb().getUserTable().count();
    int countHits2 = getDb().getQueryCount();
    //System.err.println(countHits2);
    assertEquals(countHits1 + 1, countHits2);
    
    // FIXME Selection is cached but not invalidated 
    // when table cache is invalidated
    getDb().getUserTable().selection();
    int selectionHits1 = getDb().getQueryCount();
    getDb().getUserTable().selection();
    int selectionHits2 = getDb().getQueryCount();
    assertEquals(selectionHits1, selectionHits2);
    getDb().uncache();
    getDb().getUserTable().selection();
    int selectionHits3 = getDb().getQueryCount();
    assertEquals(selectionHits2 + 1, selectionHits3);
    
    getDb().getUserTable().getUserObject(0);
    int getHits1 = getDb().getQueryCount();
    //System.err.println(getHits1);
    assertEquals(selectionHits3 + 1, getHits1);
    getDb().uncache();
    getDb().getUserTable().getUserObject(0);
    int getHits2 = getDb().getQueryCount();
    assertEquals(getHits1 + 1, getHits2);
    
    Persistent p2 = getDb().getUserTable().newPersistent();
    p2.setCooked("name", "testuser");
    p2.setCooked("login", "testuser");
    p2.setCooked("password", "testuser");
    p2.makePersistent();
    int getHits3 = getDb().getQueryCount();
    //System.err.println(getHits3);
    assertEquals(getHits2 + 3, getHits3);
    p2.delete();
    PoemThread.commit();
  }


  /**
   * @see org.melati.poem.JdbcPersistent()
   */
  public void testPersistent() {

  }

  /**
   * @see org.melati.poem.JdbcPersistent(String)
   */
  public void testPersistentStringString() {
    Persistent p = new JdbcPersistent("user", "0");
    try {
      p.makePersistent();
      fail("Should have blown up");
    } catch (DoubleCreatePoemException e) {
      e = null;      
    }
    p = new JdbcPersistent(getDb().getUserTable(), new Integer(2));
    try {
      p.makePersistent();
      fail("Should have blown up");
    } catch (DoubleCreatePoemException e) {
      e = null;      
    }
  }

  /**
   * @see org.melati.poem.JdbcPersistent(Table, Integer)
   */
  public void testPersistentTableInteger() {
    Persistent p = new JdbcPersistent(getDb().getUserTable(), new Integer(0));
    try {
      p.makePersistent();
      fail("Should have blown up");
    } catch (DoubleCreatePoemException e) {
      e = null;      
    }
    p = new JdbcPersistent(getDb().getUserTable(), new Integer(2));
    try {
      p.makePersistent();
      fail("Should have blown up");
    } catch (DoubleCreatePoemException e) {
      e = null;      
    }
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
   * @see org.melati.poem.Persistent#getTable()
   */
  public void testGetTable() {

  }


  /**
   * @see org.melati.poem.Persistent#getDatabase()
   */
  public void testGetDatabase() {
    Persistent p = new JdbcPersistent(getDb().getUserTable(), new Integer(0));
    assertEquals(p.getDatabase(), getDb());
  }

  /**
   * @see org.melati.poem.Persistent#troid()
   */
  public void testTroid() {
    Persistent p = new JdbcPersistent();
    assertNull(p.troid());
    p = new JdbcPersistent(getDb().getUserTable(), new Integer(0));
    assertEquals(p.troid(), new Integer(0));
  }

  /**
   * @see org.melati.poem.Persistent#getTroid()
   */
  public void testGetTroid() {
    Persistent p = new JdbcPersistent();
    assertNull(p.getTroid());
    p = new JdbcPersistent(getDb().getUserTable(), new Integer(0));
    assertEquals(p.getTroid(), new Integer(0));
  }

  /**
   * @see org.melati.poem.Persistent#existenceLock()
   */
  public void testExistenceLock() {
    Persistent p = new JdbcPersistent();
    p.existenceLock();
    try {
      p.delete();
      fail("Should have blown up");
    } catch (InvalidOperationOnFloatingPersistentPoemException e) {
      e = null;
    }
       
  }


  /**
   * @see org.melati.poem.Persistent#assertCanWrite(AccessToken)
   */
  public void testAssertCanWriteAccessToken() {
    Persistent p = getDb().getUserTable().guestUser();
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
   * @see org.melati.poem.Persistent#assertCanWrite()
   */
  public void testAssertCanWrite() {
    Persistent p = getDb().getUserTable().administratorUser();
    AccessToken g  = getDb().getUserTable().guestUser();
    p.assertCanWrite();
    try {
      PoemThread.setAccessToken(g);
      p.assertCanWrite();
      fail("Should have bombed");
    } catch (WritePersistentAccessPoemException e) {
      e = null;
    }
  }

  /**
   * @see org.melati.poem.Persistent#assertCanDelete(AccessToken)
   */
  public void testAssertCanDeleteAccessToken() {
    Persistent p = getDb().getUserTable().guestUser();
    AccessToken g  = getDb().getUserTable().guestUser();
    try {
      p.assertCanDelete(g);
      fail("Should have bombed");
    } catch (DeletePersistentAccessPoemException e) {
      e = null;
    }
    AccessToken a  = getDb().getUserTable().administratorUser();
    p.assertCanDelete(a);
  }

  /**
   * @see org.melati.poem.Persistent#assertCanDelete()
   */
  public void testAssertCanDelete() {
    Persistent p = getDb().getUserTable().administratorUser();
    AccessToken g  = getDb().getUserTable().guestUser();
    p.assertCanDelete();
    try {
      PoemThread.setAccessToken(g);
      p.assertCanDelete();
      fail("Should have bombed");
    } catch (DeletePersistentAccessPoemException e) {
      e = null;
    }
  }

  /**
   * @see org.melati.poem.Persistent#assertCanCreate(AccessToken)
   */
  public void testAssertCanCreateAccessToken() {
    Persistent p = getDb().getUserTable().guestUser();
    AccessToken g  = getDb().getUserTable().guestUser();
    try {
      p.assertCanCreate(g);
      fail("Should have bombed");
    } catch (CreationAccessPoemException e) {
      e = null;
    }
    AccessToken a  = getDb().getUserTable().administratorUser();
    p.assertCanWrite(a);
  }

  /**
   * @see org.melati.poem.Persistent#assertCanCreate()
   */
  public void testAssertCanCreate() {
    Persistent p = getDb().getUserTable().administratorUser();
    AccessToken g  = getDb().getUserTable().guestUser();
    p.assertCanCreate();
    try {
      PoemThread.setAccessToken(g);
      p.assertCanCreate();
      fail("Should have bombed");
    } catch (CreationAccessPoemException e) {
      e = null;
    }
  }

  /**
   * @see org.melati.poem.Persistent#getRaw(String)
   */
  public void testGetRaw() {
    Persistent p = getDb().getUserTable().administratorUser();
    String name = (String)p.getRaw("name");
    assertEquals("Melati database administrator", name);
    Integer id = (Integer)p.getRaw("id");
    assertEquals(new Integer(1), id);
  }

  /**
   * @see org.melati.poem.Persistent#getRawString(String)
   */
  public void testGetRawString() {
    Persistent p = getDb().getUserTable().administratorUser();
    String name = p.getRawString("name");
    assertEquals("Melati database administrator", name);
    String id = p.getRawString("id");
    assertEquals("1", id);
  }

  /**
   * @see org.melati.poem.Persistent#setRaw(String, Object)
   */
  public void testSetRaw() {
    Persistent p = getDb().getUserTable().administratorUser();
    String name = p.getRawString("name");
    assertEquals("Melati database administrator", name);
    p.setRaw("name", "test");
    String name2 = p.getRawString("name");
    assertEquals("test", name2);
    p.setRaw("name", name);
    String name3 = p.getRawString("name");
    assertEquals("Melati database administrator", name3);
  }

  /**
   * @see org.melati.poem.Persistent#setRawString(String, String)
   */
  public void testSetRawString() {
    Persistent p = getDb().getUserTable().administratorUser();
    String name = p.getRawString("name");
    assertEquals("Melati database administrator", name);
    p.setRawString("name", "test");
    String name2 = p.getRawString("name");
    assertEquals("test", name2);
    p.setRawString("name", name);
    String name3 = p.getRawString("name");
    assertEquals("Melati database administrator", name3);
  }

  /**
   * @see org.melati.poem.Persistent#getCooked(String)
   */
  public void testGetCooked() {
    Persistent p = getDb().getUserTable().administratorUser();
    String name = (String)p.getCooked("name");
    assertEquals("Melati database administrator", name);
    p = getDb().getUserTable().getTableInfo();
    TableCategory c = (TableCategory)p.getCooked("category");
    assertEquals("tableCategory/0", c.toString());
  }

  /**
   * @see org.melati.poem.Persistent#getCookedString(String, PoemLocale, int)
   */
  public void testGetCookedString() {
    Persistent p = getDb().getUserTable().administratorUser();
    String name = p.getCookedString("name", PoemLocale.HERE, DateFormat.MEDIUM);
    assertEquals("Melati database administrator", name);
    Persistent p2 = getDb().getUserTable().getTableInfo();
    String c = p2.getCookedString("category", PoemLocale.HERE, DateFormat.MEDIUM);
    assertEquals("User", c);
  }

  /**
   * @see org.melati.poem.Persistent#setCooked(String, Object)
   */
  public void testSetCooked() {
    Persistent p = getDb().getUserTable().administratorUser();
    String name = p.getRawString("name");
    assertEquals("Melati database administrator", name);
    p.setCooked("name", "test");
    String name2 = p.getRawString("name");
    assertEquals("test", name2);
    p.setCooked("name", name);
    String name3 = p.getRawString("name");
    assertEquals("Melati database administrator", name3);

    Persistent p2 = getDb().getUserTable().getTableInfo();
    TableCategory c = (TableCategory)p2.getCooked("category");
    TableCategory c2 = (TableCategory)getDb().getTableCategoryTable().newPersistent();
    c2.setName("Test");
    c2.makePersistent();
    p2.setCooked("category",c2);
    String cString = p2.getCookedString("category",PoemLocale.HERE, DateFormat.MEDIUM);
    assertEquals("Test", cString);
    p2.setCooked("category",c);
    cString = p2.getCookedString("category",PoemLocale.HERE, DateFormat.MEDIUM);
    assertEquals("User", cString);
    c2.delete();
  }

  /**
   * @see org.melati.poem.Persistent#getField(String)
   */
  public void testGetField() {
    Persistent p = getDb().getGroupMembershipTable().getObject(0);
    Field<?> f = p.getField("user");
    assertEquals("user: Melati database administrator", f.toString());
  }

  /**
   * @see org.melati.poem.Persistent#fieldsOfColumns(Enumeration)
   */
  public void testFieldsOfColumns() {
    Persistent p = getDb().getGroupMembershipTable().getObject(0);
    Enumeration<Field<?>> fields = p.fieldsOfColumns(p.getTable().columns());
    Field<?> f = (Field<?>)fields.nextElement();
    assertEquals("id: 0", f.toString());
    f = (Field<?>)fields.nextElement();
    assertEquals("user: Melati database administrator", f.toString());
    f = (Field<?>)fields.nextElement();
    assertEquals("group: Melati database administrators", f.toString());
  }

  /**
   * @see org.melati.poem.Persistent#getFields()
   */
  public void testGetFields() {
    Persistent p = getDb().getGroupMembershipTable().getObject(0);
    Enumeration<Field<?>> fields = p.getFields();
    Field<?> f = (Field<?>)fields.nextElement();
    assertEquals("id: 0", f.toString());
    f = (Field<?>)fields.nextElement();
    assertEquals("user: Melati database administrator", f.toString());
    f = (Field<?>)fields.nextElement();
    assertEquals("group: Melati database administrators", f.toString());
  }

  /**
   * @see org.melati.poem.Persistent#getRecordDisplayFields()
   */
  public void testGetRecordDisplayFields() {
    Persistent p = getDb().getGroupMembershipTable().getObject(0);
    Enumeration<Field<?>> fields = p.getRecordDisplayFields();
    Field<?> f = (Field<?>)fields.nextElement();
    assertEquals("user: Melati database administrator", f.toString());
    f = (Field<?>)fields.nextElement();
    assertEquals("group: Melati database administrators", f.toString());
  }

  /**
   * @see org.melati.poem.Persistent#getDetailDisplayFields()
   */
  public void testGetDetailDisplayFields() {
    Persistent p = getDb().getGroupMembershipTable().getObject(0);
    Enumeration<Field<?>> fields = p.getDetailDisplayFields();
    Field<?> f = (Field<?>)fields.nextElement();
    assertEquals("id: 0", f.toString());
    f = (Field<?>)fields.nextElement();
    assertEquals("user: Melati database administrator", f.toString());
    f = (Field<?>)fields.nextElement();
    assertEquals("group: Melati database administrators", f.toString());
  }

  /**
   * @see org.melati.poem.Persistent#getSummaryDisplayFields()
   */
  public void testGetSummaryDisplayFields() {
    Persistent p = getDb().getGroupMembershipTable().getObject(0);
    Enumeration<Field<?>> fields = p.getSummaryDisplayFields();
    Field<?> f = (Field<?>)fields.nextElement();
    assertEquals("user: Melati database administrator", f.toString());
    f = (Field<?>)fields.nextElement();
    assertEquals("group: Melati database administrators", f.toString());
  }

  /**
   * @see org.melati.poem.Persistent#getSearchCriterionFields()
   */
  public void testGetSearchCriterionFields() {
    Persistent p = getDb().getGroupMembershipTable().getObject(0);
    Enumeration<Field<?>> fields = p.getSearchCriterionFields();
    Field<?> f = (Field<?>)fields.nextElement();
    assertEquals("id: 0", f.toString());
    f = (Field<?>)fields.nextElement();
    assertEquals("user: Melati database administrator", f.toString());
    f = (Field<?>)fields.nextElement();
    assertEquals("group: Melati database administrators", f.toString());
  }

  /**
   * @see org.melati.poem.test.ProtectedPersistentTest#testGetPrimaryDisplayField()
   * @see org.melati.poem.Persistent#getPrimaryDisplayField()
   */
  public void testGetPrimaryDisplayField() {
  }


  /**
   * @see org.melati.poem.Persistent#delete()
   */
  public void testDelete() {
    Persistent p = getDb().getCapabilityTable().newPersistent();
    try {
      p.delete();
      fail("Should have bombed");
    } catch (InvalidOperationOnFloatingPersistentPoemException e) { 
      e = null;
    }
    p.setCooked("name","testCapability");
    p.makePersistent();
    assertEquals("testCapability", p.getCooked("name"));
    p.delete();
    try { 
      p.delete();
      fail("Should have bombed");
    } catch (RowDisappearedPoemException e) { 
      e = null;
    }
    try {
      getDb().getGroupTable().getObject(p.getTroid());
      fail("Should have bombed");
    } catch (NoSuchRowPoemException e) { 
      e = null;
    }
    // To before we started
    PoemThread.rollback();
    try {
      getDb().getGroupTable().getObject(p.getTroid());
      fail("Should have bombed");
    } catch (NoSuchRowPoemException e) { 
      e = null;
    }
    p = getDb().getGroupTable().newPersistent();    
    p.setCooked("name","testGroup");
    p.makePersistent();
    assertEquals("testGroup", p.getCooked("name"));
    // Write to db
    PoemThread.commit();
    p.delete();
    try { 
      p.delete();
      fail("Should have bombed");
    } catch (RowDisappearedPoemException e) { 
      e = null;
    }
    try {
      getDb().getGroupTable().getObject(p.getTroid());
      fail("Should have bombed");
    } catch (NoSuchRowPoemException e) { 
      e = null;
    }
    // Rollback so it should be there again
    getDb().setLogCommits(true);
    PoemThread.rollback(); 
    getDb().setLogCommits(false);
    getDb().getGroupTable().getObject(p.getTroid());
    assertEquals("testGroup", p.getCooked("name"));
    p.delete();
    try { 
      p.delete();
      fail("Should have bombed");
    } catch (RowDisappearedPoemException e) { 
      e = null;
    }
    try {
      getDb().getGroupTable().getObject(1);
      fail("Should have bombed");
    } catch (NoSuchRowPoemException e) { 
      e = null;
    }
    // So the db state should be unchanged
    getDb().setLogCommits(true);
    PoemThread.commit();
    getDb().setLogCommits(false);
  }

  /**
   * @see org.melati.poem.Persistent#delete_unsafe()
   */
  public void testDelete_unsafe() {
    Persistent p = getDb().getGroupTable().newPersistent();
    try {
      p.delete_unsafe();
      fail("Should have bombed");
    } catch (InvalidOperationOnFloatingPersistentPoemException e) { 
      e = null;
    }
    p.setCooked("name","test");
    p.makePersistent();
    // Hmm - not sure I am happy with this ordered dependency
    assertEquals("test", p.getCooked("name"));
    p.delete_unsafe();
    try { 
      p.delete_unsafe();
    } catch (RowDisappearedPoemException e) { 
      e = null;
    }
    try {
      getDb().getGroupTable().getObject(2);
      fail("Should have bombed");
    } catch (NoSuchRowPoemException e) { 
      e = null;
    }

  }
  

  /**
   * @see org.melati.poem.Persistent#deleteAndCommit()
   */
  public void testDeleteAndCommit() {
    Persistent p = getDb().getGroupTable().newPersistent();
    try {
      p.deleteAndCommit();
      fail("Should have bombed");
    } catch (InvalidOperationOnFloatingPersistentPoemException e) { 
      e = null;
    }
    p.setCooked("name","test");
    p.makePersistent();
    assertEquals("test", p.getCooked("name"));
    p.deleteAndCommit();
    try { 
      p.deleteAndCommit();
    } catch (RowDisappearedPoemException e) { 
      e = null;
    }
    try {
      getDb().getGroupTable().getObject(3);
      fail("Should have bombed");
    } catch (NoSuchRowPoemException e) { 
      e = null;
    }
  }

  /**
   * @see org.melati.poem.Persistent#deleteAndCommit(Map)
   */
  public void testDeleteAndCommitMap() {
    Persistent p = getDb().getGroupTable().newPersistent();
    try {
      p.deleteAndCommit(null);
      fail("Should have bombed");
    } catch (InvalidOperationOnFloatingPersistentPoemException e) { 
      e = null;
    }
    p.setCooked("name","test");
    p.makePersistent();
    assertEquals("test", p.getCooked("name"));
    Integer troid = p.getTroid();
    p.deleteAndCommit(null);
    try { 
      p.deleteAndCommit(null);
    } catch (RowDisappearedPoemException e) { 
      e = null;
    }
    try {
      getDb().getGroupTable().getObject(troid);
      fail("Should have bombed");
    } catch (NoSuchRowPoemException e) { 
      e = null;
    }

  }

  /**
   * @see org.melati.poem.Persistent#duplicated()
   */
  public void testDuplicated() {
    Persistent p = getDb().getGroupTable().getObject(0); 
    Persistent p2 = p.duplicated();
    assertFalse(p.equals(p2)); // p2 is floating
    try {
      p2.duplicated();
      fail("Should have bombed");
    } catch (InvalidOperationOnFloatingPersistentPoemException e) { 
      e = null;
    }
  }

  /**
   * @see org.melati.poem.Persistent#duplicatedFloating()
   */
  public void testDuplicatedFloating() {
    Persistent p = getDb().getGroupTable().getObject(0); 
    Persistent p2 = p.duplicatedFloating();
    assertFalse(p.equals(p2)); 
    Persistent p3 = p2.duplicatedFloating();
    assertTrue(p2.equals(p3)); 
  }

  /**
   * @see org.melati.poem.Persistent#toString()
   */
  public void testToString() {
    Persistent p = getDb().getGroupMembershipTable().getObject(0);
    assertEquals("groupMembership/0", p.toString());
  }

  /**
   * @see org.melati.poem.Persistent#displayString(PoemLocale, int)
   */
  public void testDisplayStringPoemLocaleInt() {
    Persistent p = getDb().getGroupTable().getObject(0);
    assertEquals("Melati database administrators", 
        p.displayString(PoemLocale.HERE, DateFormat.MEDIUM));
  }

  /** 
   * @see org.melati.poem.Persistent#displayString(PoemLocale)
   */
  public void testDisplayStringPoemLocale() {
    Persistent p = getDb().getGroupTable().getObject(0);
    assertEquals("Melati database administrators", 
        p.displayString(PoemLocale.HERE));
  }

  /**
   * @see org.melati.poem.Persistent#displayString()
   */
  public void testDisplayString() {
    Persistent p = getDb().getGroupTable().getObject(0);
    assertEquals("Melati database administrators", 
        p.displayString());    
  }
  
  /**
   * @see org.melati.poem.Persistent#hashCode()
   */
  public void testHashCode() {
    Persistent p = getDb().getUserTable().guestUser();
    //assertEquals(3599307, p.hashCode());
    assertEquals(2645995, p.hashCode());
    try { 
      p = getDb().getUserTable().newPersistent();
      p.hashCode();
      fail("Should have bombed");
    } catch (InvalidOperationOnFloatingPersistentPoemException e) { 
      e = null;
    }
  }
  
  /**
   * @see org.melati.poem.Persistent#equals(Object)
   */
  public void testEqualsObject() {
    Persistent p = getDb().getGroupTable().getObject(0);
    assertTrue(p.equals(p));
    Persistent p2 = getDb().getGroupTable().newPersistent();
    assertTrue(p2.equals(p2));
    assertFalse(p.equals(p2));
    assertFalse(p.equals(null));
    assertFalse(p.equals(new Integer(0)));
    // Note that id != troid
    // troid remains null
    p2.setRaw("id", new Integer(0));
    assertFalse(p.equals(p2));
  }

  /**
   * @see org.melati.poem.Persistent#dump()
   */
  public void testDump() {
    Persistent p = new JdbcPersistent(getDb().getUserTable(), new Integer(0));
    String d = null; 
    try { 
      d = p.dump();
      fail("Should have blown up");
    } catch (ClassCastException e) {
      e = null;
    }
    assertNull(d);
    
    User u = (User)getDb().getUserTable().newPersistent();
    d = u.dump();
    
    assertTrue(d, d.startsWith("User/null"));
    u = getDb().getUserTable().guestUser();
    d = u.dump();
    assertTrue(d,d.startsWith("User/0"));


    
    Setting stringSetting = getDb().getSettingTable().ensure("stringSetting","set","String","A set string setting");
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PrintStream ps = new PrintStream(baos);
    stringSetting.dump(ps);
    //System.err.println(baos.toString().trim() + ":");
    assertTrue(baos.toString().startsWith("setting/"));
    stringSetting.delete();
    
  }
  /**
   * @see org.melati.poem.Persistent#dump(PrintStream)
   */
  public void testDumpPrintStream() {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PrintStream ps = new PrintStream(baos);
    try { 
      Persistent p = new JdbcPersistent(getDb().getUserTable(), new Integer(0));
      p.dump(ps);
      fail("Should have blown up");
    } catch (ClassCastException e) {
      e = null;
    }
    // Some gets written before blowing up
    assertEquals(baos.toString(),
        "User/0\n  ",
        baos.toString().replace("\r", ""));
    
    baos = new ByteArrayOutputStream();
    ps = new PrintStream(baos);
    User u = (User)getDb().getUserTable().newPersistent();
    u.dump(ps);
    assertTrue(baos.toString().startsWith("User/null"));
    u = getDb().getUserTable().guestUser();
    baos = new ByteArrayOutputStream();
    ps = new PrintStream(baos);
    u.dump(ps);
    assertTrue(baos.toString().startsWith("User/0"));
  
  }

  /** 
   * @see org.melati.poem.Persistent#postWrite()
   */
  public void testPostWrite() {
    Persistent p = getDb().getUserTable().guestUser();
    p.postWrite();
  }

  /**
   * @see org.melati.poem.Persistent#postInsert()
   */
  public void testPostInsert() {
    Persistent p = getDb().getUserTable().guestUser();
    p.postInsert();
  }

  /**
   * @see org.melati.poem.Persistent#postModify()
   */
  public void testPostModify() {
    Persistent p = getDb().getUserTable().guestUser();
    p.postModify();
  }

  /**
   * @see org.melati.poem.Persistent#preEdit()
   */
  public void testPreEdit() {
    Persistent p = getDb().getUserTable().guestUser();
    p.preEdit();
  }

  /**
   * @see org.melati.poem.Persistent#postEdit(boolean)
   */
  public void testPostEdit() {
    Persistent p = getDb().getUserTable().guestUser();
    p.postEdit(true);
    p.postEdit(false);
  }


  /**
   * Test the representation of an uninitialised Persistent.
   */
  public void testToStringNull() {
    Persistent unit = new JdbcPersistent();
    assertEquals("null/null", unit.toString());
  }

  /**
   * Test that two uninitialised Persistents are equal.
   */
  public void testEqualsNull() {
    Persistent unitOne = new JdbcPersistent();
    Persistent unitTwo = new JdbcPersistent();
    assertTrue(unitOne.equals(unitTwo));
  }

}
