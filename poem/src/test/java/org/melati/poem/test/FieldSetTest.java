/**
 * 
 */
package org.melati.poem.test;

import java.util.Hashtable;

import org.melati.poem.Field;
import org.melati.poem.FieldSet;


/**
 * @author timp
 * @since 14 Jan 2007
 *
 */
public class FieldSetTest extends PoemTestCase {

  /**
   * @param name
   */
  public FieldSetTest(String name) {
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
   * Test method for {@link org.melati.poem.FieldSet#FieldSet(java.util.Hashtable, org.melati.poem.Field[])}.
   */
  public void testFieldSet() {
  }

  /**
   * Test method for {@link org.melati.poem.FieldSet#elements()}.
   */
  public void testElements() {
  }

  /**
   * Test method for {@link org.melati.poem.FieldSet#get(java.lang.String)}.
   */
  public void testGet() {
  }

  /**
   * Test method for {@link org.melati.poem.FieldSet#toString()}.
   */
  public void testToString() {
    Hashtable<String, Integer> h = new Hashtable<String, Integer>();
    FieldSet fs = new FieldSet(h,null);
    assertNull(fs.toString());
    Field<?>[] fields = new Field[1];
    fields[0] = getDb().getUserTable().getNameColumn().asField(getDb().guestUser());
    fs = new FieldSet(h,fields);
    assertEquals("name=\"Melati guest user\"\n",fs.toString());
  }

}
