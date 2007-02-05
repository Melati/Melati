/**
 * 
 */
package org.melati.poem.test;

import org.melati.poem.Capability;

/**
 * @author timp
 * @since 5 Feb 2007
 *
 */
public class CapabilityTest extends PoemTestCase {

  /**
   * Constructor.
   * @param name
   */
  public CapabilityTest(String name) {
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
   * Test method for {@link org.melati.poem.Capability#assertCanRead(org.melati.poem.AccessToken)}.
   */
  public void testAssertCanReadAccessToken() {
    
  }

  /**
   * Test method for {@link org.melati.poem.Capability#toString()}.
   */
  public void testToString() {
    
  }

  /**
   * Test method for {@link org.melati.poem.Capability#Capability()}.
   */
  public void testCapability() {
    
  }

  /**
   * Test method for {@link org.melati.poem.Capability#Capability(java.lang.String)}.
   */
  public void testCapabilityString() {
    Capability c = new Capability("capability");
    assertEquals("capability",c.getName());
    c = null;
  }

}
