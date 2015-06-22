/**
 * 
 */
package org.melati.poem.test;

import org.melati.poem.CachedCount;
import org.melati.poem.Table;
import org.melati.poem.User;

/**
 * @author timp
 * @since 31/10/2006
 */
public class CachedCountTest extends PoemTestCase {

  public CachedCountTest(String name) {
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
   * @see org.melati.poem.CachedCount#CachedCount(Table, String, Table[])
   */
  public void testCachedCountTableStringTableArray() {
    // FIXME work out good use case
    int queries = getDb().getQueryCount();
    CachedCount cc = new CachedCount(getDb().getUserTable(), 
            getDb().getUserTable().troidColumn().fullQuotedName() + " = 0", null);
    int queries2 = getDb().getQueryCount();
    assertEquals(queries, queries2);
    assertEquals(1, cc.count());
    int queries3 = getDb().getQueryCount();
    assertEquals(queries2 + 1, queries3);
    assertEquals(1, cc.count());
    int queries4 = getDb().getQueryCount();
    assertEquals(queries3, queries4);
  }

  /**
   * @see org.melati.poem.CachedCount#CachedCount
   */
  public void testCachedCountPersistentBooleanBoolean() {
    int queries = getDb().getQueryCount();
    User u = (User)getDb().getUserTable().newPersistent();
    u.setName("guest");
    CachedCount cc = new CachedCount(u,false,true);
    int queries2 = getDb().getQueryCount();
    assertEquals(queries, queries2);
    assertEquals(1, cc.count());
    int queries3 = getDb().getQueryCount();
    assertEquals(queries2 + 1, queries3);
    assertEquals(1, cc.count());
    int queries4 = getDb().getQueryCount();
    assertEquals(queries3, queries4);
  }

  /**
   * @see org.melati.poem.CachedCount#CachedCount(Table, String)
   */
  public void testCachedCountTableString() {
    int queries = getDb().getQueryCount();
    CachedCount cc = new CachedCount(getDb().getUserTable(), 
            getDb().getUserTable().troidColumn().fullQuotedName() + " = 0");
    int queries2 = getDb().getQueryCount();
    assertEquals(queries, queries2);
    assertEquals(1, cc.count());
    int queries3 = getDb().getQueryCount();
    assertEquals(queries2 + 1, queries3);
    assertEquals(1, cc.count());
    int queries4 = getDb().getQueryCount();
    assertEquals(queries3, queries4);

  }

  /**
   * @see org.melati.poem.CachedCount#count()
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

}
