/**
 * 
 */
package org.melati.poem.test;

import org.melati.poem.GroupMembership;

/**
 * @author timp
 * @since 09 Jan 2007
 */
public class GroupMembershipTest extends PoemTestCase {

  /**
   * @param name
   */
  public GroupMembershipTest(String name) {
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
   * Test method for {@link org.melati.poem.GroupMembership#GroupMembership()}.
   */
  public void testGroupMembership() {
    
  }

  /**
   * Test method for {@link org.melati.poem.GroupMembership#
   *     GroupMembership(org.melati.poem.User, org.melati.poem.Group)}.
   */
  public void testGroupMembershipUserGroup() {
    GroupMembership it = new GroupMembership(getDb().guestUser(),
        getDb().getGroupTable().ensure("testgroup"));
    getDb().getGroupMembershipTable().create(it);
    assertEquals("testgroup",it.getGroup().getName());
  }

  /**
   * Test method for {@link org.melati.poem.GroupMembership#
   *     GroupMembership(java.lang.Integer, java.lang.Integer)}.
   */
  public void testGroupMembershipIntegerInteger() {
    GroupMembership it = new GroupMembership(getDb().guestUser().getTroid(),
        getDb().getGroupTable().ensure("testgroup").getTroid());
    getDb().getGroupMembershipTable().create(it);
    assertEquals("testgroup",it.getGroup().getName());
  }

}
