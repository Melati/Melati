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
  public void testEncode() {
    String in = "FIXME";
    if (System.getenv().get("OSTYPE").equals("linux"))
      assertEquals("Vu+/vWlc77+9cEQI77+9MduH77+9bu+/ve+/vQ==",
          new String(Base64.encodeBase64(MD5Util.encode(in).getBytes())));
    else
      assertEquals("VtdpXKhwRAitMduH224/7A==",
          new String(Base64.encodeBase64(MD5Util.encode(in).getBytes())));
      
  }

}
