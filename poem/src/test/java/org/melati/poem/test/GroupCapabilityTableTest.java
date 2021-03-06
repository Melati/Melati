/**
 * 
 */
package org.melati.poem.test;

/**
 * @author timp
 * @since 29 Jan 2007
 *
 */
public class GroupCapabilityTableTest extends PoemTestCase {

  public GroupCapabilityTableTest(String name) {
    super(name);
  }

  protected void setUp() throws Exception {
    super.setUp();
  }

  protected void tearDown() throws Exception {
    super.tearDown();
  }

  /**
   * Test method for {@link org.melati.poem.GroupCapabilityTable#GroupCapabilityTable(org.melati.poem.Database, java.lang.String, org.melati.poem.DefinitionSource)}.
   */
  public void testGroupCapabilityTable() {

  }

  /**
   * Test method for {@link org.melati.poem.GroupCapabilityTable#ensure(org.melati.poem.Group, org.melati.poem.Capability)}.
   */
  public void testEnsure() {
    assertEquals("groupCapability/0", 
            getDb().getGroupCapabilityTable().
              ensure(getDb().getGroupTable().getGroupObject(new Integer(0)), 
                     getDb().administerCapability()).toString());
  }

}
