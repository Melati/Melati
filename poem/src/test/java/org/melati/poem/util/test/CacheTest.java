/**
 * 
 */
package org.melati.poem.util.test;

import java.util.Enumeration;

import org.melati.poem.util.Cache;
import org.melati.poem.util.CacheDuplicationException;
import org.melati.poem.util.EnumUtils;

import junit.framework.TestCase;

/**
 * @author timp
 * @since 30 May 2007
 *
 */
public class CacheTest extends TestCase {

  Cache c = null;
  /**
   * @param name
   */
  public CacheTest(String name) {
    super(name);
  }

  /** 
   * {@inheritDoc}
   * @see junit.framework.TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
    c = new Cache(12);
  }

  /** 
   * {@inheritDoc}
   * @see junit.framework.TestCase#tearDown()
   */
  protected void tearDown() throws Exception {
    super.tearDown();
  }

  /**
   * Test method for {@link org.melati.poem.util.Cache#Cache(int)}.
   */
  public void testCache() {
   
  }

  /**
   * Test method for {@link org.melati.poem.util.Cache#setSize(int)}.
   */
  public void testSetSize() {
    assertEquals(12, c.getSize());
    c.setSize(100);
    assertEquals(100, c.getSize());
    c.setSize(12);
    try { 
      c.setSize(-1);
      fail("Should have bombed");
    } catch (IllegalArgumentException e) { 
      e = null;
    }
  }

  /**
   * Test method for {@link org.melati.poem.util.Cache#trim(int)}.
   */
  public void testTrim() {
    c.setSize(4);
    assertEquals(4, c.getSize());
    assertFalse(c.getInfo().getHeldElements().hasMoreElements());
    assertFalse(c.getInfo().getDroppedElements().hasMoreElements());

    c.put("a","a1");
    c.put("b","a1");
    c.put("c","a1");
    c.put("d","a1");
    assertTrue(c.getInfo().getHeldElements().hasMoreElements());
    assertFalse(c.getInfo().getDroppedElements().hasMoreElements());
    c.put("e","a1");
    assertFalse(c.getInfo().getDroppedElements().hasMoreElements());
    c.put("f","a1");
    assertTrue(c.getInfo().getDroppedElements().hasMoreElements());
    c.trim(3);
    assertTrue(c.getInfo().getDroppedElements().hasMoreElements());
    assertEquals(4, c.getSize());   
  }

  /**
   * Test method for {@link org.melati.poem.util.Cache#delete(java.lang.Object)}.
   */
  public void testDelete() {
    c.delete("not in cache");
    c.put("key", "value");
    c.delete("key");
    assertNull(c.get("key"));
  }

  /**
   * Test method for {@link org.melati.poem.util.Cache#put(java.lang.Object, java.lang.Object)}.
   */
  public void testPut() {
    c.put("a","a1");
    try { 
      c.put("a","a1");
      fail("Should have bombed");
    } catch (CacheDuplicationException e) { 
      e = null;
    }
    try { 
      c.put("null", null);
      fail("Should have bombed");
    } catch (NullPointerException e) { 
      e = null;
    }

  }

  /**
   * Test method for {@link org.melati.poem.util.Cache#get(java.lang.Object)}.
   * 
   * It appears that when the cache size is exceeded (size is one bigger than size!!) 
   * then all elements are dropped.
   */
  public void testGet() {
    c.setSize(2);
    c.put("key1", "1");
    assertEquals("1", c.get("key1"));
    c.put("key2", "2");
    assertEquals("1", c.get("key1"));
    assertEquals("2", c.get("key2"));
    c.put("key3", "3");
    assertEquals("1", c.get("key1"));
    assertEquals("2", c.get("key2"));
    assertEquals("3", c.get("key3"));
    c.put("key4", "4");
    assertEquals("1", c.get("key1"));
    assertEquals("2", c.get("key2"));
    assertEquals("3", c.get("key3"));
    assertEquals("4", c.get("key4"));
    c.put("key5", "5");
    assertEquals("1", c.get("key1"));
    assertEquals("2", c.get("key2"));
    assertEquals("3", c.get("key3"));
    assertEquals("4", c.get("key4"));
    assertEquals("5", c.get("key5"));
    c.trim(2);
    assertEquals("1", c.get("key1"));
    assertEquals("2", c.get("key2"));
    assertEquals("3", c.get("key3"));
    assertEquals("4", c.get("key4"));
    assertEquals("5", c.get("key5"));
    assertNull(c.get("not in cache"));
  }

  /**
   * Test method for {@link org.melati.poem.util.Cache#iterate(org.melati.poem.util.Procedure)}.
   */
  public void testIterate() {
   
  }

  /**
   * Test method for {@link org.melati.poem.util.Cache#getReport()}.
   */
  public void testGetReport() {
    c.setSize(1);
    Enumeration report = c.getReport(); 
    assertTrue(report.hasMoreElements());
    assertEquals("0 held, 0 total ", report.nextElement());
    assertFalse(report.hasMoreElements());
    c.put("key1", "value1");
    report = c.getReport(); 
    assertTrue(report.hasMoreElements());
    assertEquals("1 held, 1 total ", report.nextElement()); 
    assertFalse(report.hasMoreElements());
    c.put("key2", "value2");
    report = c.getReport(); 
    assertTrue(report.hasMoreElements());
    assertEquals("2 held, 2 total ", report.nextElement()); 
    assertFalse(report.hasMoreElements());
    c.put("key3", "value3");
    report = c.getReport(); 
    assertTrue(report.hasMoreElements());
    assertEquals("2 held, 3 total ", report.nextElement()); 
    assertFalse(report.hasMoreElements());
  }

  /**
   * Test method for {@link org.melati.poem.util.Cache#getInfo()}.
   */
  public void testGetInfo() {
   Enumeration report = c.getInfo().getReport();
   assertTrue(report.hasMoreElements());
   assertEquals(c.getReport().nextElement(), report.nextElement());
   
   assertFalse(c.getInfo().getDroppedElements().hasMoreElements());
   c.put("key", "value");
   c.delete("key");
   assertFalse(c.getInfo().getDroppedElements().hasMoreElements());   
   c.setSize(1);
   c.put("key1", "value1");
   assertFalse(c.getInfo().getDroppedElements().hasMoreElements());   
   c.put("key2", "value2");
   assertFalse(c.getInfo().getDroppedElements().hasMoreElements());   
   c.put("key3", "value3");
   assertEquals(1, EnumUtils.vectorOf(c.getInfo().getDroppedElements()).size());   
   
  }

  /**
   * Test method for {@link org.melati.poem.util.Cache#dumpAnalysis()}.
   */
  public void testDumpAnalysis() {
   
  }

}
