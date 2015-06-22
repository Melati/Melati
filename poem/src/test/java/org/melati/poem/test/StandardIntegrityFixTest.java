package org.melati.poem.test;

import org.melati.poem.StandardIntegrityFix;
import org.melati.poem.StandardIntegrityFix.NameUnrecognisedException;

/**
 * @author timp
 * @since 29 Jan 2007
 *
 */
public class StandardIntegrityFixTest extends PoemTestCase {

  public StandardIntegrityFixTest(String name) {
    super(name);
  }

  protected void setUp() throws Exception {
    super.setUp();
  }

  protected void tearDown() throws Exception {
    super.tearDown();
  }

  /**
   * Test method for {@link org.melati.poem.StandardIntegrityFix#getName()}.
   */
  public void testGetName() {
    
  }

  /**
   * Test method for {@link org.melati.poem.StandardIntegrityFix#getIndex()}.
   */
  public void testGetIndex() {
    
  }

  /**
   * Test method for {@link org.melati.poem.StandardIntegrityFix#referencesTo(org.melati.poem.Persistent, org.melati.poem.Column, java.util.Enumeration, java.util.Map)}.
   */
  public void testReferencesTo() {
    
  }

  /**
   * Test method for {@link org.melati.poem.StandardIntegrityFix#forIndex(int)}.
   */
  public void testForIndex() {
    
  }

  /**
   * Test method for {@link org.melati.poem.StandardIntegrityFix#count()}.
   */
  public void testCount() {
    
  }

  /**
   * Test method for {@link org.melati.poem.StandardIntegrityFix#named(java.lang.String)}.
   */
  public void testNamed() {
    StandardIntegrityFix fix = StandardIntegrityFix.named("delete");
    assertEquals("delete",fix.getName());
    try { 
      fix = StandardIntegrityFix.named("unknown");
      fail("Should have blown up");
    } catch(NameUnrecognisedException e) { 
      e = null;
    }
  }

  /**
   * Test method for {@link org.melati.poem.StandardIntegrityFix#toString()}.
   */
  public void testToString() {
    StandardIntegrityFix fix = StandardIntegrityFix.named("delete");
    assertEquals("delete/0", fix.toString());
  }

}
