package org.melati.admin.test;

import org.melati.admin.Copy;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Generated code for the test suite <b>CopyTest</b> located at
 * <i>/melati/src/test/java/org/melati/admin/test/CopyTest.testsuite</i>.
 *
 * Test Copy
 */
public class CopyTest extends TestCase {
  /**
   * Constructor for CopyTest.
   * @param name
   */
  public CopyTest(String name) {
    super(name);
  }

  /**
   * Returns the JUnit test suite that implements the <b>CopyTest</b>
   * definition.
   */
  public static Test suite() {
    TestSuite copyTest = new TestSuite(CopyTest.class);
    return copyTest;
  }

  /**
   * @see junit.framework.TestCase#setUp()
   */
  protected void setUp() throws Exception {
  }

  /**
   * @see junit.framework.TestCase#tearDown()
   */
  protected void tearDown() throws Exception {
  }

  /**
   * 
   */
  public void testCopy() {
    Copy.copy("champion", "champion2");
  }  
}
