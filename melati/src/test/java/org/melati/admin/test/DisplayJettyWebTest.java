/**
 * 
 */
package org.melati.admin.test;

import org.melati.JettyWebTestCase;


/**
 * @author timp
 * @since 2008/01/10
 */
public class DisplayJettyWebTest extends JettyWebTestCase {
  /**
   * Constructor for AdminUtilsTest.
   * @param name
   */
  public DisplayJettyWebTest(String name) {
    super(name);
  }

  /**
   * {@inheritDoc}
   * @see org.melati.JettyWebTestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
  }

  /**
   * {@inheritDoc}
   * @see org.melati.JettyWebTestCase#tearDown()
   */
  protected void tearDown() throws Exception {
    super.tearDown();    
  }

  /**
   * Test Display using default template.
   */
  public void testDisplay() {
    beginAt("/Display/melatijunit/User/0/");
    assertTextPresent("Melati guest user");
  }

  /**
   * Test Display using default template.
   */
  public void testDisplayMethod() {
    beginAt("/Display/melatijunit/User/0/org/melati/admin/Display");
    assertTextPresent("Melati guest user");
  }
  /**
   * Test Display using default template.
   */
  public void testDisplayMethodDots() {
    beginAt("/Display/melatijunit/User/0/org.melati.admin.Display");
    assertTextPresent("Melati guest user");
  }

  /**
   * Test Display using default template.
   */
  public void testDisplayParameter() {
    beginAt("/Display/melatijunit/User/0/?template=org/melati/admin/Display");
    assertTextPresent("Melati guest user");
  }
  /**
   * Test Display using default template.
   */
  public void testDisplayParameterDots() {
    beginAt("/Display/melatijunit/User/0/?template=org.melati.admin.Display");
    assertTextPresent("Melati guest user");
  }

  /**
   * Test Display using default template.
   */
  public void testDisplayParameterDotsNoObject() {
    beginAt("/Display/melatijunit/User/?template=org.melati.admin.Display");
    assertTextPresent("null");
  }

}
