/**
 * 
 */
package org.melati.poem.test;

import org.melati.poem.GroupCapability;

/**
 * @author timp
 * @since 09/01/2007
 */
public class GroupCapabilityTest extends PoemTestCase {

  /**
   * @param name
   */
  public GroupCapabilityTest(String name) {
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
   * Test method for {@link org.melati.poem.GroupCapability#GroupCapability()}.
   */
  public void testGroupCapability() {

  }

  /**
   * Test method for {@link org.melati.poem.GroupCapability#
   * GroupCapability(org.melati.poem.Group, org.melati.poem.Capability)}.
   */
  public void testGroupCapabilityGroupCapability() {
    GroupCapability it = new GroupCapability(
        getDb().getGroupTable().ensure("testgroup"),
        getDb().getCapabilityTable().ensure("testing"));
    getDb().getGroupCapabilityTable().create(it);
    assertEquals("testgroup",it.getGroup().getName());
    assertEquals("testing",it.getCapability().getName());
  }

}
