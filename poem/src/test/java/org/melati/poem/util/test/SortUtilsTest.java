/**
 * 
 */
package org.melati.poem.util.test;

import org.melati.poem.util.DictionaryOrder;
import org.melati.poem.util.SortUtils;

import junit.framework.TestCase;

/**
 * @author timp
 * @since 13 Jun 2007
 *
 */
public class SortUtilsTest extends TestCase {

  /**
   * @param name
   */
  public SortUtilsTest(String name) {
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
   * Test method for {@link org.melati.poem.util.SortUtils#swap(java.lang.Object[], int, int)}.
   */
  public void testSwap() {

  }

  /**
   * Test method for {@link org.melati.poem.util.SortUtils#insertionSort(org.melati.poem.util.Order, java.lang.Object[])}.
   */
  public void testInsertionSort() {

  }

  /**
   * Test method for {@link org.melati.poem.util.SortUtils#qsort(org.melati.poem.util.Order, java.lang.Object[])}.
   */
  public void testQsort() {
    String[] toSort = new String[] {"a","b","c","d","e","f","g"};
    SortUtils.qsort(DictionaryOrder.vanilla, toSort);
    assertEquals("abcdefg", join(toSort));
    toSort = new String[] {"g","f","e","d","c","b","a"};
    SortUtils.qsort(DictionaryOrder.vanilla, toSort);
    assertEquals("abcdefg", join(toSort));
    toSort = new String[] {"g","b","e","a","c","f","d","z"};
    SortUtils.qsort(DictionaryOrder.vanilla, toSort);
    assertEquals("abcdefgz", join(toSort));
 }
  private String join(String[] in) { 
    String ret = "";
    for (int i = 0; i < in.length; i++)
      ret += in[i];
    return ret;
  }
  
  /**
   * Test method for {@link org.melati.poem.util.SortUtils#sorted(org.melati.poem.util.Order, java.lang.Object[])}.
   */
  public void testSortedOrderObjectArray() {
    String[] toSort = new String[] {"a","b","c","d","e","f","g"};
    String[] sorted = (String[])SortUtils.sorted(DictionaryOrder.vanilla, toSort);
    assertEquals("abcdefg", join(sorted));
    toSort = new String[] {"g","f","e","d","c","b","a"};
    sorted = (String[])SortUtils.sorted(DictionaryOrder.vanilla, toSort);
    assertEquals("abcdefg", join(sorted));

  }

  /**
   * Test method for {@link org.melati.poem.util.SortUtils#sorted(org.melati.poem.util.Order, java.util.Vector)}.
   */
  public void testSortedOrderVector() {

  }

  /**
   * Test method for {@link org.melati.poem.util.SortUtils#sorted(org.melati.poem.util.Order, java.util.Enumeration)}.
   */
  public void testSortedOrderEnumeration() {

  }

}
