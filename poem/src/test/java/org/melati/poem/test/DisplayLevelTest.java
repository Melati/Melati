/**
 * 
 */
package org.melati.poem.test;

import org.melati.poem.DisplayLevel;
import org.melati.poem.DisplayLevel.NameUnrecognisedException;

import junit.framework.TestCase;

/**
 * @author timp
 * @since 2 Jan 2007
 *
 */
public class DisplayLevelTest extends TestCase {

  /**
   * @param name
   */
  public DisplayLevelTest(String name) {
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
   * Test method for {@link org.melati.poem.DisplayLevel#forIndex(int)}.
   */
  public void testForIndex() {
  }

  /**
   * Test method for {@link org.melati.poem.DisplayLevel#count()}.
   */
  public void testCount() {
  }

  /**
   * Test method for {@link org.melati.poem.DisplayLevel#named(java.lang.String)}.
   */
  public void testNamed() {
    assertEquals(DisplayLevel.primary,DisplayLevel.named("primary"));
    try {
      DisplayLevel.named("unknown");
      fail("Should have blown up");
    } catch (NameUnrecognisedException e) {
      e = null;
    }
  }

  /**
   * Test method for {@link org.melati.poem.DisplayLevel#toString()}.
   */
  public void testToString() {
  }

}
