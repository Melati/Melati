/**
 * 
 */
package org.melati.poem.test;

import org.melati.poem.CachedExists;

/**
 * @author timp
 * @since 31/10/2006
 */
public class CachedExistsTest extends PoemTestCase {

  /**
   * Constructor for CachedExistsTest.
   * @param name
   */
  public CachedExistsTest(String name) {
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
   * @see org.melati.poem.CachedExists#CachedExists(Table, String, Table[])
   */
  public void testCachedExistsTableStringTableArray() {
    // FIXME work out good use case
    int queries = getDb().getQueryCount();
    CachedExists ce = new CachedExists(getDb().getUserTable(), "\"USER\".\"ID\" = 0", null);
    int queries2 = getDb().getQueryCount();
    assertEquals(queries, queries2);
    assertTrue(ce.exists());
    int queries3 = getDb().getQueryCount();
    assertEquals(queries2 + 1, queries3);
    assertTrue(ce.exists());
    int queries4 = getDb().getQueryCount();
    assertEquals(queries3, queries4);

  }

  /**
   * @see org.melati.poem.CachedExists#CachedExists(Table, String)
   */
  public void testCachedExistsTableString() {
    int queries = getDb().getQueryCount();
    CachedExists ce = new CachedExists(getDb().getUserTable(), "\"USER\".\"ID\" = 0");
    int queries2 = getDb().getQueryCount();
    assertEquals(queries, queries2);
    assertTrue(ce.exists());
    int queries3 = getDb().getQueryCount();
    assertEquals(queries2 + 1, queries3);
    assertTrue(ce.exists());
    int queries4 = getDb().getQueryCount();
    assertEquals(queries3, queries4);

  }

  /**
   * @see org.melati.poem.CachedExists#exists()
   */
  public void testExists() {
    int queries = getDb().getQueryCount();
    CachedExists ce = new CachedExists(getDb().getUserTable(), "\"USER\".\"ID\" = 0");
    int queries2 = getDb().getQueryCount();
    assertEquals(queries, queries2);
    assertTrue(ce.exists());
    int queries3 = getDb().getQueryCount();
    assertEquals(queries2 + 1, queries3);
    assertTrue(ce.exists());
    int queries4 = getDb().getQueryCount();
    assertEquals(queries3, queries4);

  }

}
