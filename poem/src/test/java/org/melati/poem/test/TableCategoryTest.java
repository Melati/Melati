/**
 * 
 */
package org.melati.poem.test;

import org.melati.poem.TableCategory;

/**
 * @author timp
 *
 */
public class TableCategoryTest extends PoemTestCase {

  /**
   * @param name
   */
  public TableCategoryTest(String name) {
    super(name);
  }

  /**
   * {@inheritDoc}
   * @see org.melati.poem.test.PoemTestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
  }

  /**
   * {@inheritDoc}
   * @see org.melati.poem.test.PoemTestCase#tearDown()
   */
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
  }

}
