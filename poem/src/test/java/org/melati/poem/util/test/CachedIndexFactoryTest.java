/**
 * 
 */
package org.melati.poem.util.test;

import org.melati.poem.util.CachedIndexFactory;

import junit.framework.TestCase;

/**
 * @author timp
 * @since 7 Jun 2007
 *
 */
public class CachedIndexFactoryTest extends TestCase {
  CachedIndexFactory it; 
  
  /**
   * @param name
   */
  public CachedIndexFactoryTest(String name) {
    super(name);
  }

  /** 
   * {@inheritDoc}
   * @see junit.framework.TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
    it = new CachedIndexFactory() { 
      public Object reallyGet(int index) {
        if ((index / 2)*2 == index)
          return new Integer(index);
        else
          return null;
      }   
    };
  }

  /** 
   * {@inheritDoc}
   * @see junit.framework.TestCase#tearDown()
   */
  protected void tearDown() throws Exception {
    super.tearDown();
  }

  /**
   * Test method for {@link org.melati.poem.util.CachedIndexFactory#reallyGet(int)}.
   */
  public void testReallyGet() {
    
  }

  /**
   * Test method for {@link org.melati.poem.util.CachedIndexFactory#get(int)}.
   */
  public void testGet() {
    assertNull(it.get(3)); // Really get it - it is null
    assertNull(it.get(3)); // Get it from cache - it is null
    it.invalidate(3);
    assertNull(it.get(3)); // Really get it - it is null    
  }

  /**
   * Test method for {@link org.melati.poem.util.CachedIndexFactory#invalidate(int)}.
   */
  public void testInvalidateInt() {
    
  }

  /**
   * Test method for {@link org.melati.poem.util.CachedIndexFactory#invalidate()}.
   */
  public void testInvalidate() {
    
  }

}
