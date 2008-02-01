/**
 * 
 */
package org.melati.util.test;

import org.apache.commons.codec.binary.Base64;
import org.melati.util.MD5Util;

import junit.framework.TestCase;

/**
 * @author timp
 * @since 31 Jan 2008
 *
 */
public class MD5UtilTest extends TestCase {

  /**
   * @param name
   */
  public MD5UtilTest(String name) {
    super(name);
  }

  /** 
   * {@inheritDoc}
   * @see junit.framework.TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
  }

  /** 
   * {@inheritDoc}
   * @see junit.framework.TestCase#tearDown()
   */
  protected void tearDown() throws Exception {
    super.tearDown();
  }

  /**
   * Test method for {@link org.melati.util.MD5Util#encode(java.lang.String)}.
   */
  public void testEncode() throws Exception {
    String in = "FIXME";
    assertEquals("VsOXaVzCqHBECMKtMcOb4oChw5tu77+9w6w=",
        new String(Base64.encodeBase64(MD5Util.encode(in).getBytes("UTF-8"))));
  }

}
