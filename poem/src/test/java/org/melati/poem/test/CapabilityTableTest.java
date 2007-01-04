/**
 * 
 */
package org.melati.poem.test;

import org.melati.poem.Capability;

/**
 * @author timp
 * @since 1 Jan 2007
 *
 */
public class CapabilityTableTest extends PoemTestCase {

  /**
   * @param name
   */
  public CapabilityTableTest(String name) {
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
   * Test method for {@link org.melati.poem.CapabilityTable#ensure(java.lang.String)}.
   */
  public void testEnsure() {
    Capability admin = getDb().administerCapability();
    Capability admin2 = getDb().getCapabilityTable().ensure("_administer_");
    assertEquals(admin, admin2);
    Capability newOne = getDb().getCapabilityTable().ensure("newOne");
    assertEquals("newOne", newOne.getName());
  }

}
