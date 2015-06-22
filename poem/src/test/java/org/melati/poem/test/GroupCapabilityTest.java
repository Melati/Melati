package org.melati.poem.test;

import org.melati.poem.Capability;
import org.melati.poem.Group;
import org.melati.poem.GroupCapability;

/**
 * @author timp
 * @since 09/01/2007
 */
public class GroupCapabilityTest extends PoemTestCase {

  public GroupCapabilityTest(String name) {
    super(name);
  }

  protected void setUp() throws Exception {
    super.setUp();
  }

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
    Group g = getDb().getGroupTable().ensure("testgroup");
    Capability c = getDb().getCapabilityTable().ensure("testing");
    GroupCapability gc = new GroupCapability(g, c);
    getDb().getGroupCapabilityTable().create(gc);
    assertEquals("testgroup",gc.getGroup().getName());
    assertEquals("testing",gc.getCapability().getName());
    gc.delete();
    c.delete();
    try { 
      c = new Capability();
      gc = new GroupCapability(g,c);
      
      fail("Should have blown up");
    } catch (IllegalArgumentException e) { 
      e = null;
    }
    g.delete();
    try { 
      g = new Group();
      c = new Capability();
      gc = new GroupCapability(g,c);
      
      fail("Should have blown up");
    } catch (IllegalArgumentException e) { 
      e = null;
    }
  }

  /**
   * Test method for {@link org.melati.poem.GroupCapability#
   * ensure(org.melati.poem.Group, org.melati.poem.Capability)}.
   */
  public void testEnsureGroupCapability() {
    Group g = getDb().getGroupTable().ensure("testgroup");
    Capability c = getDb().getCapabilityTable().ensure("testing");
    GroupCapability gc = getDb().getGroupCapabilityTable().ensure(g,c);
    assertEquals("testgroup",gc.getGroup().getName());
    assertEquals("testing",gc.getCapability().getName());
    gc.delete();
    g.delete();
    c.delete();
    
  }

}
