package org.melati.poem.test;

import org.melati.poem.Searchability;

import junit.framework.TestCase;

/**
 * @author timp
 * @since 27 Feb 2007
 *
 */
public class SearchabilityTest extends TestCase {

  public SearchabilityTest(String name) {
    super(name);
  }

  protected void setUp() throws Exception {
    super.setUp();
  }

  protected void tearDown() throws Exception {
    super.tearDown();
  }

  /**
   * Test method for {@link org.melati.poem.Searchability#forIndex(int)}.
   */
  public void testForIndex() {

  }

  /**
   * Test method for {@link org.melati.poem.Searchability#count()}.
   */
  public void testCount() {

  }

  /**
   * Test method for {@link org.melati.poem.Searchability#named(java.lang.String)}.
   */
  public void testNamed() {

  }

  /**
   * Test method for {@link org.melati.poem.Searchability#toString()}.
   */
  public void testToString() {
    assertEquals("primary/0",Searchability.named("primary").toString());
   
  }

  /**
   * Test method for {@link org.melati.poem.Searchability#getIndex()}.
   */
  public void testGetIndex() {

  }

  /**
   * Test method for {@link org.melati.poem.Searchability#getName()}.
   */
  public void testGetName() {

  }

}
