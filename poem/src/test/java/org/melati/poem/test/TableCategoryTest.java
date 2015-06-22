package org.melati.poem.test;

import org.melati.poem.TableCategory;

/**
 * @author timp
 *
 */
public class TableCategoryTest extends PoemTestCase {

  public TableCategoryTest(String name) {
    super(name);
  }

  protected void setUp() throws Exception {
    super.setUp();
  }

  protected void tearDown() throws Exception {
    super.tearDown();
  }

  /**
   * Test method for {@link org.melati.poem.TableCategory#assertCanRead(org.melati.poem.AccessToken)}.
   */
  public void testAssertCanReadAccessToken() {
  }

  /**
   * Test method for {@link org.melati.poem.TableCategory#TableCategory()}.
   */
  public void testTableCategory() {
  }

  /**
   * Test method for {@link org.melati.poem.TableCategory#TableCategory(java.lang.String)}.
   */
  public void testTableCategoryString() {
    TableCategory it = new TableCategory("test");
    getDb().getTableCategoryTable().create(it);
    assertEquals("test",it.getName());
    it.delete();
  }

}
