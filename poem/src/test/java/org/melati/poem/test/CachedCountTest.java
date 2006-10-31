/**
 * 
 */
package org.melati.poem.test;

import org.melati.poem.CachedCount;

/**
 * @author timp
 *
 */
public class CachedCountTest extends PoemTestCase {

  /**
   * Constructor for CachedCountTest.
   * @param name
   */
  public CachedCountTest(String name) {
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
   * @see org.melati.poem.CachedCount#CachedCount(Table, String, Table[])
   */
  public void testCachedCountTableStringTableArray() {

  }

  /**
   * @see org.melati.poem.CachedCount#CachedCount(Persistent, boolean, boolean)
   */
  public void testCachedCountPersistentBooleanBoolean() {

  }

  /**
   * @see org.melati.poem.CachedCount#CachedCount(Table, String)
   */
  public void testCachedCountTableString() {

  }

  /*
   * @see org.melati.poem.CachedCount.count()'
   */
  public void testCount() {
    int queries = getDb().getQueryCount();
    CachedCount cc = new CachedCount(getDb().getUserTable(), null, null);
    int queries2 = getDb().getQueryCount();
    assertEquals(queries, queries2);
    assertEquals(2, cc.count());
    int queries3 = getDb().getQueryCount();
    assertEquals(queries2 + 1, queries3);
    assertEquals(2, cc.count());
    int queries4 = getDb().getQueryCount();
    assertEquals(queries3, queries4);
  }

  /*
   * @see org.melati.poem.CachedQuery.CachedQuery(Table, String, Table[])'
   */
  public void testCachedQueryTableStringTableArray() {

  }

  /*
   * @see org.melati.poem.CachedQuery.CachedQuery(Table, String)'
   */
  public void testCachedQueryTableString() {

  }

  /*
   * @see org.melati.poem.CachedQuery.statements()'
   */
  public void testStatements() {

  }

  /*
   * @see org.melati.poem.CachedQuery.compute()'
   */
  public void testCompute() {

  }

  /*
   * @see org.melati.poem.CachedQuery.getTable()'
   */
  public void testGetTable() {

  }

  /*
   * @see org.melati.poem.CachedQuery.outOfDate()'
   */
  public void testOutOfDate() {

  }

}
