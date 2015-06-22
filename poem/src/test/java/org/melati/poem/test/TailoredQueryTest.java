package org.melati.poem.test;

import java.text.DateFormat;
import java.util.Enumeration;

import org.melati.poem.Column;
import org.melati.poem.FieldSet;
import org.melati.poem.Capability;
import org.melati.poem.Table;
import org.melati.poem.TailoredQuery;
import org.melati.poem.PoemLocale;

/**
 * @author timp
 */
public class TailoredQueryTest extends EverythingTestCase {

  public TailoredQueryTest(String name) {
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
   * @see org.melati.poem.TailoredQuery#TailoredQuery(Column[], Table[], String,
   *      String)
   */
  public void testTailoredQueryColumnArrayTableArrayStringString() {
    Column<?>[] columns = { 
            getDb().getUserTable().getNameColumn(),
            getDb().getGroupTable().getNameColumn(), };
    Table<?>[] tables = { getDb().getGroupMembershipTable() };

    String whereClause = 
      getDb().getDbms().getQuotedName("user") + 
      " = " + 
      getDb().getDbms().getQuotedName("user") + 
      "." +
      getDb().getDbms().getQuotedName("id") + 
      " and " +
      getDb().getDbms().getQuotedName("group") + 
      " = " + 
      getDb().getDbms().getQuotedName("group") + 
      "." +
      getDb().getDbms().getQuotedName("id");
    //System.err.println(whereClause);  
    TailoredQuery q = new TailoredQuery(columns,
                                        tables,
                                        whereClause,
                                        getDb().getUserTable().getNameColumn().
                                            fullQuotedName());
    int count = 0;
    for (Enumeration<FieldSet> ms = q.selection(); ms.hasMoreElements();) {
      count++;
      //FieldSet fs = (FieldSet) 
      ms.nextElement();
      /*
      System.out.println(
          fs.get("user_name").getCookedString(PoemLocale.HERE,
                                              DateFormat.MEDIUM)
          + ", "
          + fs.get("group_name").getCookedString(PoemLocale.HERE,
              DateFormat.MEDIUM));
       */
    }
    assertEquals(1,count);
  }

  /**
   * @see org.melati.poem.TailoredQuery#TailoredQuery(String, Column[], Table[],
   *      String, String)
   */
  public void testTailoredQueryStringColumnArrayTableArrayStringString() {
    Column<?>[] columns = { getDb().getUserTable().getNameColumn(),
        getDb().getGroupTable().getNameColumn(), };
    Table<?>[] tables = { getDb().getGroupMembershipTable() };

    String whereClause = 
      getDb().getDbms().getQuotedName("user") + 
      " = " + 
      getDb().getDbms().getQuotedName("user") + 
      "." +
      getDb().getDbms().getQuotedName("id") + 
      " and " +
      getDb().getDbms().getQuotedName("group") + 
      " = " + 
      getDb().getDbms().getQuotedName("group") + 
      "." +
      getDb().getDbms().getQuotedName("id");
    //System.err.println(whereClause);  
    TailoredQuery q = new TailoredQuery("distinct",
                                        columns,
                                        tables,
                                        whereClause,
                                        null);
    int count = 0;
    for (Enumeration<FieldSet> ms = q.selection(); ms.hasMoreElements();) {
      count++;
      //FieldSet fs = (FieldSet) 
      ms.nextElement();
      /*
      System.out.println(
          fs.get("user_name").getCookedString(PoemLocale.HERE,
                                              DateFormat.MEDIUM)
          + ", "
          + fs.get("group_name").getCookedString(PoemLocale.HERE,
              DateFormat.MEDIUM));
       */
    }
    assertEquals(1, count);

  }

  /**
   * @see org.melati.poem.TailoredQuery#selection()
   */
  public void testSelection() {
    Column<?>[] columns = { getDb().getUserTable().getNameColumn(),
        getDb().getGroupTable().getNameColumn(), };
    Table<?>[] tables = { getDb().getGroupMembershipTable() };

    String whereClause = 
      getDb().getDbms().getQuotedName("user") + 
      " = " + 
      getDb().getDbms().getQuotedName("user") + 
      "." +
      getDb().getDbms().getQuotedName("id") + 
      " and " +
      getDb().getDbms().getQuotedName("group") + 
      " = " + 
      getDb().getDbms().getQuotedName("group") + 
      "." +
      getDb().getDbms().getQuotedName("id");
    //System.err.println(whereClause);  
    TailoredQuery q = new TailoredQuery(
                                        columns,
                                        tables,
                                        whereClause,
                                        null);
    int count = 0;
    for (Enumeration<FieldSet> ms = q.selection(); ms.hasMoreElements();) {
      count++;
      //FieldSet fs = (FieldSet) 
      ms.nextElement();
      /*
      System.out.println(
          fs.get("user_name").getCookedString(PoemLocale.HERE,
                                              DateFormat.MEDIUM)
          + ", "
          + fs.get("group_name").getCookedString(PoemLocale.HERE,
              DateFormat.MEDIUM));
        */
    }
    assertEquals(1,count);

  }

  /**
   * @see org.melati.poem.TailoredQuery#selection_firstRaw()
   */
  public void testSelection_firstRaw() {
    Column<?>[] columns = { getDb().getUserTable().getNameColumn(),
        getDb().getGroupTable().getNameColumn(), };
    Table<?>[] tables = { getDb().getGroupMembershipTable() };

    String whereClause = 
      getDb().getDbms().getQuotedName("user") + 
      " = " + 
      getDb().getDbms().getQuotedName("user") + 
      "." +
      getDb().getDbms().getQuotedName("id") + 
      " and " +
      getDb().getDbms().getQuotedName("group") + 
      " = " + 
      getDb().getDbms().getQuotedName("group") + 
      "." +
      getDb().getDbms().getQuotedName("id");
    //System.err.println(whereClause);  
    TailoredQuery q = new TailoredQuery(
                                        columns,
                                        tables,
                                        whereClause,
                                        null);
    int count = 0;
    for (Enumeration<Object> ms = q.selection_firstRaw(); ms.hasMoreElements();) {
      count++;
      ms.nextElement();
    }
    assertEquals(1,count);
  }

  /**
   * @see java.lang.Object#toString()
   */
  public void testToString() {
    Column<?>[] columns = { getDb().getUserTable().getNameColumn(),
        getDb().getGroupTable().getNameColumn(), };
    Table<?>[] tables = { getDb().getGroupMembershipTable() };

    String whereClause = 
      getDb().getDbms().getQuotedName("user") + 
      " = " + 
      getDb().getDbms().getQuotedName("user") + 
      "." +
      getDb().getDbms().getQuotedName("id") + 
      " and " +
      getDb().getDbms().getQuotedName("group") + 
      " = " + 
      getDb().getDbms().getQuotedName("group") + 
      "." +
      getDb().getDbms().getQuotedName("id");
    //System.err.println(whereClause);  
    TailoredQuery q = new TailoredQuery(
                                        columns,
                                        tables,
                                        whereClause,
                                        null);
    int count = 0;
    for (Enumeration<FieldSet> ms = q.selection(); ms.hasMoreElements();) {
      count++;
      //FieldSet fs = (FieldSet) 
      ms.nextElement();
      /*
      System.out.println(
          fs.get("user_name").getCookedString(PoemLocale.HERE,
                                              DateFormat.MEDIUM)
          + ", "
          + fs.get("group_name").getCookedString(PoemLocale.HERE,
              DateFormat.MEDIUM));
       */
    }
    assertEquals(1,count);
    // System.err.println(q);
    assertTrue(q.toString().indexOf("SELECT") > 0 && 
        q.toString().toUpperCase().indexOf("USER") > 0 );
  }

  /**
   * Test table protection
   */
  public void testProtected() {
    EverythingDatabase db = (EverythingDatabase)getDb();
    Capability spyMaster = db.getCapabilityTable().ensure("spyMaster");
    Capability moneyPenny = db.getCapabilityTable().ensure("moneyPenny");
    
    User spy = (User)db.getUserTable().newPersistent();
    spy.setLogin("spy");
    spy.setName("Spy");
    spy.setPassword("spy");
    spy.makePersistent();

    Protected spyMission = (Protected)db.getProtectedTable().newPersistent();
    spyMission.setCanRead(moneyPenny);
    spyMission.setCanSelect(moneyPenny);
    spyMission.setCanWrite(moneyPenny);
    spyMission.setCanDelete(spyMaster);
    spyMission.setSpy(spy);
    spyMission.setMission("impossible");
    spyMission.setDeleted(false);
    spyMission.makePersistent();
    
    Column<?>[] columns = { 
            getDb().getUserTable().getNameColumn(),
            db.getProtectedTable().getMissionColumn() };
    Table<?>[] otherTables = { db.getProtectedTable() };

    String whereClause = 
      db.getUserTable().getNameColumn().fullQuotedName() + 
      " = 'Spy' " + 
      " AND " +
      db.getUserTable().troidColumn().fullQuotedName() + 
      " = " + 
      db.getProtectedTable().getSpyColumn().fullQuotedName();
    TailoredQuery q = new TailoredQuery(
                                        columns,
                                        otherTables,
                                        whereClause,
                                        null);
    int count = 0;
    for (Enumeration<FieldSet> ms = q.selection(); ms.hasMoreElements();) {
      count++;
      FieldSet fs = ms.nextElement();
      System.err.println(fs);
      System.out.println(
          fs.get("User_name").getCookedString(PoemLocale.HERE,
                                              DateFormat.MEDIUM)
          + ", "
          + fs.get("Protected_mission").getCookedString(PoemLocale.HERE,
              DateFormat.MEDIUM));
    }
    assertEquals(1,count);
    // System.err.println(q);
    assertTrue(q.toString().indexOf("SELECT") > 0 && 
        q.toString().toUpperCase().indexOf("USER") > 0 );
    
    spyMission.delete();
    spy.delete();
    spyMaster.delete();
    moneyPenny.delete();
  }

}
