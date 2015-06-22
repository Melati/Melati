package org.melati.poem.test;

import org.melati.poem.Group;
import org.melati.poem.GroupMembership;
import org.melati.poem.User;

/**
 * @author timp
 * @since 09 Jan 2007
 */
public class GroupMembershipTest extends PoemTestCase {

  public GroupMembershipTest(String name) {
    super(name);
  }

  protected void setUp() throws Exception {
    super.setUp();
  }

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
    User u = getDb().guestUser();
    Group g = getDb().getGroupTable().ensure("testgroup");
    GroupMembership it = new GroupMembership(u,
        g);
    getDb().getGroupMembershipTable().create(it);
    assertEquals("testgroup",it.getGroup().getName());
    it.delete();
    g.delete();
  }

  /**
   * Test method for {@link org.melati.poem.GroupMembership#
   *     GroupMembership(java.lang.Integer, java.lang.Integer)}.
   */
  public void testGroupMembershipIntegerInteger() {
    Group g = getDb().getGroupTable().ensure("testgroup");
    GroupMembership it = new GroupMembership(getDb().guestUser().getTroid(),
        g.getTroid());
    getDb().getGroupMembershipTable().create(it);
    assertEquals("testgroup",it.getGroup().getName());
    it.delete();
    g.delete();
  }

}
