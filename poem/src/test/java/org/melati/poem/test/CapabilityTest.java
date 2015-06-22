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

  public CapabilityTest(String name) {
    super(name);
  }

  protected void setUp() throws Exception {
    super.setUp();
  }

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
