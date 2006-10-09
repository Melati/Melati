/**
 * 
 */
package org.melati.poem.test;

import java.text.DateFormat;
import java.util.Enumeration;

import org.melati.poem.Column;
import org.melati.poem.FieldSet;
import org.melati.poem.Table;
import org.melati.poem.TailoredQuery;
import org.melati.util.MelatiLocale;

/**
 * @author timp
 */
public class TailoredQueryTest extends PoemTestCase {

  /**
   * Constructor for TailoredQueryTest.
   * 
   * @param name
   */
  public TailoredQueryTest(String name) {
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
   * @see org.melati.poem.TailoredQuery#TailoredQuery(Column[], Table[], String,
   *      String)
   */
  public void testTailoredQueryColumnArrayTableArrayStringString() {
    Column[] columns = { getDb().getUserTable().getNameColumn(),
        getDb().getGroupTable().getNameColumn(), };
    Table[] tables = { getDb().getGroupMembershipTable() };

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
    System.err.println(whereClause);  
    TailoredQuery q = new TailoredQuery(
                                        columns,
                                        tables,
                                        whereClause,
                                        null);
    int count = 0;
    for (Enumeration ms = q.selection(); ms.hasMoreElements();) {
      count++;
      FieldSet fs = (FieldSet) ms.nextElement();
      System.out.println(
          fs.get("user_name").getCookedString(MelatiLocale.HERE,
                                              DateFormat.MEDIUM)
          + ", "
          + fs.get("group_name").getCookedString(MelatiLocale.HERE,
              DateFormat.MEDIUM));
    }
    assertTrue(count == 1);
  }

  /**
   * @see org.melati.poem.TailoredQuery#TailoredQuery(String, Column[], Table[],
   *      String, String)
   */
  public void testTailoredQueryStringColumnArrayTableArrayStringString() {
    Column[] columns = { getDb().getUserTable().getNameColumn(),
        getDb().getGroupTable().getNameColumn(), };
    Table[] tables = { getDb().getGroupMembershipTable() };

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
    System.err.println(whereClause);  
    TailoredQuery q = new TailoredQuery("distinct",
                                        columns,
                                        tables,
                                        whereClause,
                                        null);
    int count = 0;
    for (Enumeration ms = q.selection(); ms.hasMoreElements();) {
      count++;
      FieldSet fs = (FieldSet) ms.nextElement();
      System.out.println(
          fs.get("user_name").getCookedString(MelatiLocale.HERE,
                                              DateFormat.MEDIUM)
          + ", "
          + fs.get("group_name").getCookedString(MelatiLocale.HERE,
              DateFormat.MEDIUM));
    }
    assertTrue(count == 1);

  }

  /**
   * @see org.melati.poem.TailoredQuery#selection()
   */
  public void testSelection() {
    Column[] columns = { getDb().getUserTable().getNameColumn(),
        getDb().getGroupTable().getNameColumn(), };
    Table[] tables = { getDb().getGroupMembershipTable() };

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
    System.err.println(whereClause);  
    TailoredQuery q = new TailoredQuery(
                                        columns,
                                        tables,
                                        whereClause,
                                        null);
    int count = 0;
    for (Enumeration ms = q.selection(); ms.hasMoreElements();) {
      count++;
      FieldSet fs = (FieldSet) ms.nextElement();
      System.out.println(
          fs.get("user_name").getCookedString(MelatiLocale.HERE,
                                              DateFormat.MEDIUM)
          + ", "
          + fs.get("group_name").getCookedString(MelatiLocale.HERE,
              DateFormat.MEDIUM));
    }
    assertTrue(count == 1);

  }

  /**
   * @see org.melati.poem.TailoredQuery#selection_firstRaw()
   */
  public void testSelection_firstRaw() {
    Column[] columns = { getDb().getUserTable().getNameColumn(),
        getDb().getGroupTable().getNameColumn(), };
    Table[] tables = { getDb().getGroupMembershipTable() };

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
    System.err.println(whereClause);  
    TailoredQuery q = new TailoredQuery(
                                        columns,
                                        tables,
                                        whereClause,
                                        null);
    int count = 0;
    for (Enumeration ms = q.selection_firstRaw(); ms.hasMoreElements();) {
      count++;
      System.out.println(ms.nextElement());
    }
    assertTrue(count == 1);
  }

  /**
   * @see java.lang.Object#toString()
   */
  public void testToString() {
    Column[] columns = { getDb().getUserTable().getNameColumn(),
        getDb().getGroupTable().getNameColumn(), };
    Table[] tables = { getDb().getGroupMembershipTable() };

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
    System.err.println(whereClause);  
    TailoredQuery q = new TailoredQuery(
                                        columns,
                                        tables,
                                        whereClause,
                                        null);
    int count = 0;
    for (Enumeration ms = q.selection(); ms.hasMoreElements();) {
      count++;
      FieldSet fs = (FieldSet) ms.nextElement();
      System.out.println(
          fs.get("user_name").getCookedString(MelatiLocale.HERE,
                                              DateFormat.MEDIUM)
          + ", "
          + fs.get("group_name").getCookedString(MelatiLocale.HERE,
              DateFormat.MEDIUM));
    }
    assertTrue(count == 1);
    System.err.println(q);
    assertTrue(q.toString().indexOf("SELECT") > 0 && 
        q.toString().indexOf("USER") > 0 );
  }

}
