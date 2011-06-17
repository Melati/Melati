/**
 * 
 */
package org.melati.poem.test;

import java.util.Enumeration;

import org.melati.poem.CachedTailoredQuery;
import org.melati.poem.Column;
import org.melati.poem.Field;
import org.melati.poem.FieldSet;
import org.melati.poem.Table;
import org.melati.poem.util.EnumUtils;

/**
 * @author timp
 */
public class CachedTailoredQueryTest extends PoemTestCase {

  /**
   * Constructor for CachedTailoredQueryTest.
   * 
   * @param name
   */
  public CachedTailoredQueryTest(String name) {
    super(name);
  }

  /**
   * {@inheritDoc}
   * @see org.melati.poem.test.PoemTestCase#setUp()
   */
  protected void setUp()
      throws Exception {
    super.setUp();
  }

  /**
   * {@inheritDoc}
   * @see org.melati.poem.test.PoemTestCase#tearDown()
   */
  protected void tearDown()
      throws Exception {
    super.tearDown();
  }

  /**
   * @see org.melati.poem.CachedTailoredQuery#selection()
   */
  public void testSelection() {

  }

  /** 
   * This needs more thought.
   * 
   * @see org.melati.poem.CachedTailoredQuery#selection_firstRaw()
   */
  public void testSelection_firstRaw() {
    Column[] cols = new Column[1];
    cols[0] = getDb().getTableCategoryTable().troidColumn();
    //cols[1] = getDb().getTableInfoTable().getColumn("category");
    Table[] tables = new Table[1];
    tables[0] = getDb().getTableCategoryTable();
    //tables[1] = getDb().getTableInfoTable();
    int queries = getDb().getQueryCount();
    CachedTailoredQuery ctq = new CachedTailoredQuery(cols,
                                                      tables, 
                                                      null,
                                                      null);
    int queries2 = getDb().getQueryCount();
    assertEquals(queries, queries2);
    //getDb().setLogSQL(true);
    assertEquals(3,EnumUtils.vectorOf(ctq.selection_firstRaw()).size());
    int queries3 = getDb().getQueryCount();
    // Hmm looks like the cache is working
    //assertEquals(queries2 + 2, queries3); 
    assertEquals(3,EnumUtils.vectorOf(ctq.selection_firstRaw()).size());
    int queries4 = getDb().getQueryCount();
    assertEquals(queries3, queries4);

  }

  /**
   * @see org.melati.poem.CachedTailoredQuery# CachedTailoredQuery(String,
   *      Column[], Table[], String, String)
   */
  public void testCachedTailoredQueryStringColumnArrayTableArrayStringString() {
    Column[] colsInQuery = new Column[2];
    colsInQuery[0] = getDb().getGroupTable().getColumn("name");
    colsInQuery[1] = getDb().getGroupTable().troidColumn();
    Table[] tables = new Table[1];
    tables[0] = getDb().getGroupTable();
    //tables[1] = getDb().getTableCategoryTable();
    int queries = getDb().getQueryCount();
    CachedTailoredQuery ctq = new CachedTailoredQuery(colsInQuery,
                                                      tables, 
                                                      null,
                                                      null);
    int queries2 = getDb().getQueryCount();
    assertEquals(queries, queries2);

    getDb().setLogSQL(true);
    
    Enumeration results = ctq.selection();
    System.err.println(ctq.toString());
    while (results.hasMoreElements()) {
      //System.err.println("Found:" + ((FieldSet)results.nextElement()).toString());
      results.nextElement();
    }
    results = ctq.selection();
    assertEquals(1,EnumUtils.vectorOf(results).size());
    while (results.hasMoreElements()) {
      //System.err.println("Found:" + results.nextElement());
      results.nextElement();
    }
    getDb().setLogSQL(false);

    int queries3 = getDb().getQueryCount();
    
    // The query and 2 lookups of tableinfo
    int numberOfCachedTableinfoRecords = 0;
    Enumeration<Object> r = getDb().getTableInfoTable().getCacheInfo().getHeldElements();      
    while(r.hasMoreElements()){
      r.nextElement();
      numberOfCachedTableinfoRecords++;
    }
    //System.err.println("TableInfo records:" + numberOfCachedTableinfoRecords);
    int numberOfCachedGroupRecords = 0;
    Enumeration<Object> g = getDb().getGroupTable().getCacheInfo().getHeldElements();      
    while(g.hasMoreElements()){
      g.nextElement();
      numberOfCachedGroupRecords++;
    }
    assertEquals(1,EnumUtils.vectorOf(ctq.selection()).size());
    int queries4 = getDb().getQueryCount();
    assertEquals(queries3, queries4);
    
    System.err.println(ctq.toString());
    Enumeration en = ctq.selection();
    while (en.hasMoreElements()) {
      FieldSet fs = (FieldSet) en.nextElement();
      Enumeration fields = fs.elements();
      System.err.println("--");
      while (fields.hasMoreElements()) {
        Field f = (Field) fields.nextElement();
        System.err.println(f.getName() + "=" + f.getRawString());
      }

    }
    System.err.println("==");
    CachedTailoredQuery ctqDistinct = new CachedTailoredQuery("DISTINCT", colsInQuery,
        tables, null,
        null);
    assertEquals(1,EnumUtils.vectorOf(ctqDistinct.selection()).size());
    System.err.println(ctqDistinct.toString());
    Enumeration en2 = ctqDistinct.selection();
    while (en2.hasMoreElements()) {
      FieldSet fs = (FieldSet) en2.nextElement();
      Enumeration fields = fs.elements();
      System.err.println("--");
      while (fields.hasMoreElements()) {
        Field f = (Field) fields.nextElement();
        System.err.println(f.getName() + "=" + f.getRawString());
      }
    }
    CachedTailoredQuery ctqOthers = new CachedTailoredQuery("DISTINCT", colsInQuery,
        tables, "id=0",
        null);
    assertEquals(1,EnumUtils.vectorOf(ctqOthers.selection()).size());
  }

  /**
   * @see org.melati.poem.CachedTailoredQuery#CachedTailoredQuery(Column[],
   *      Table[], String, String)
   */
  public void testCachedTailoredQueryColumnArrayTableArrayStringString() {

  }

  /**
   * @see org.melati.poem.CachedTailoredQuery#upToDate()
   */
  public void testUpToDate() {

  }

}
