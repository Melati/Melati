/**
 * 
 */
package org.melati.poem.util.test;

import org.melati.poem.util.Base64;

import junit.framework.TestCase;

/**
 * @author timp
 * @since 13 Jun 2007
 *
 */
public class Base64Test extends TestCase {

  /**
   * @param name
   */
  public Base64Test(String name) {
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
   * Test method for {@link org.melati.poem.util.Base64#decode(java.lang.String)}.
   */
  public void testDecodeString() {
    assertEquals("\u0000\u0000", Base64.decode(""));    
    assertEquals("tim\u0000\u0000", Base64.decode("dGlt"));
    //byte[] b = Base64.decode("dGltNjA=").getBytes();
    //for (int i = 0; i< b.length; i++)
    //  System.err.println(b[i]);
    assertEquals("tim60\u0000\u0000\u0000", Base64.decode("dGltNjA="));
    try { 
      Base64.decode((String)null);
    } catch (NullPointerException e) { 
      e = null;
    }
  }

  /**
   * Test method for {@link org.melati.poem.util.Base64#encode(java.lang.String)}.
   */
  public void testEncodeString() {
    assertEquals("", Base64.encode(""));    
    assertEquals("dGlt", Base64.encode("tim"));    
    assertEquals("dGltNjA=", Base64.encode("tim60"));    
  }

  /**
   * Test method for {@link org.melati.poem.util.Base64#decode(byte[])}.
   */
  public void testDecodeByteArray() {
    assertEquals("\u0000\u0000", Base64.decode("".getBytes()));    
    assertEquals("tim\u0000\u0000", Base64.decode("dGlt".getBytes()));
    //byte[] b = Base64.decode("dGltNjA=").getBytes();
    //for (int i = 0; i< b.length; i++)
    //  System.err.println(b[i]);
    assertEquals("tim60\u0000\u0000\u0000", Base64.decode("dGltNjA=".getBytes()));
    try { 
      Base64.decode((byte[])null);
    } catch (NullPointerException e) { 
      e = null;
    }    
  }

  /**
   * Test method for {@link org.melati.poem.util.Base64#encode(byte[])}.
   */
  public void testEncodeByteArray() {
    assertEquals("", Base64.encode("".getBytes()));    
    assertEquals("dGlt", Base64.encode("tim".getBytes()));    
    assertEquals("dGltNjA=", Base64.encode("tim60".getBytes()));    
    
  }
  
  /**
   * Test exceptions thrown by decode.
   */
  public void testBadInputToDecode() {
    try { 
      Base64.decode("$");
      fail("Should have bombed");
    } catch (NumberFormatException e) { 
      e = null;
    }
  }

  /**
   * Test exceptions thrown by encode.
   * FIXME Devise bad input to encode 
   */
  public void testBadInputToEncode() {
    byte[] badBytes = new byte[3];
    badBytes[0] = 0; 
    badBytes[1] = 100; 
    badBytes[2] = 1; 
    try { 
      Base64.encode(badBytes);
      //fail("Should have bombed");
    } catch (NumberFormatException e) { 
      e = null;
    }
  }

}
