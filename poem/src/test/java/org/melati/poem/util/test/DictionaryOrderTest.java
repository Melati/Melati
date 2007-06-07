/**
 * 
 */
package org.melati.poem.util.test;

import org.melati.poem.util.DictionaryOrder;

import junit.framework.TestCase;

/**
 * @author timp
 * @since 7 Jun 2007
 *
 */
public class DictionaryOrderTest extends TestCase {

  /**
   * @param name
   */
  public DictionaryOrderTest(String name) {
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
   * Test method for {@link org.melati.poem.util.DictionaryOrder#lessOrEqual(java.lang.Object, java.lang.Object)}.
   */
  public void testLessOrEqual() {
    DictionaryOrder o = new DictionaryOrder();
    assertTrue(o.lessOrEqual("a", "b"));
    assertTrue(o.lessOrEqual("a", "a"));
    assertFalse(o.lessOrEqual("b", "a"));

    assertTrue(DictionaryOrder.vanilla.lessOrEqual("a", "b"));
    assertTrue(DictionaryOrder.vanilla.lessOrEqual("a", "a"));
    assertFalse(DictionaryOrder.vanilla.lessOrEqual("b", "a"));
  }

}
