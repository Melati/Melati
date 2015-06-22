/**
 * 
 */
package org.melati.poem.test;

import org.melati.poem.Group;

/**
 * @author timp
 * @since 09/01/2007
 */
public class GroupTest extends PoemTestCase {

  public GroupTest(String name) {
    super(name);
  }

  protected void setUp() throws Exception {
    super.setUp();
  }

  protected void tearDown() throws Exception {
    super.tearDown();
  }

  /**
   * Test method for {@link org.melati.poem.Group#assertCanRead(org.melati.poem.AccessToken)}.
   */
  public void testAssertCanReadAccessToken() {
  }

  /**
   * Test method for {@link org.melati.poem.Group#Group()}.
   */
  public void testGroup() {
  }

  /**
   * Test method for {@link org.melati.poem.Group#Group(java.lang.String)}.
   */
  public void testGroupString() {
    Group it = new Group("testers");
        
    getDb().getGroupTable().create(it);
    assertEquals("testers",it.getName());
    
    it.delete();
  }

}
