/**
 * 
 */
package org.melati.poem.test;

import java.util.Enumeration;
import java.util.NoSuchElementException;

import org.melati.poem.Persistent;
import org.melati.poem.ResultSetEnumeration;
import org.melati.poem.RowDisappearedPoemException;
import org.melati.poem.User;

/**
 * @author timp
 * @since 22 Jan 2007
 *
 */
public class ResultSetEnumerationTest extends PoemTestCase {

  /**
   * Constructor.
   * @param name
   */
  public ResultSetEnumerationTest(String name) {
    super(name);
  }

  /**
   * {@inheritDoc}
   * @see org.melati.poem.test.PoemTestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
  }

  /**
   * {@inheritDoc}
   * @see org.melati.poem.test.PoemTestCase#tearDown()
   */
  protected void tearDown() throws Exception {
    super.tearDown();
  }

  /**
   * Test method for {@link org.melati.poem.ResultSetEnumeration#
   *    ResultSetEnumeration(java.sql.ResultSet)}.
   */
  public void testResultSetEnumeration() {
  }

  /**
   * Test method for {@link org.melati.poem.ResultSetEnumeration#hasMoreElements()}.
   */
  public void testHasMoreElements() {
    Enumeration<Persistent> rse = getDb().getUserTable().getLoginColumn().selectionWhereEq("_guest_");
    while (rse.hasMoreElements()) {
      rse.nextElement();
    }
  }

  /**
   * Test of deviant useage.
   * Test method for {@link org.melati.poem.ResultSetEnumeration#nextElement()}.
   */
  public void testNextElement() {
    Enumeration<Persistent> rse = getDb().getUserTable().getLoginColumn().selectionWhereEq("_guest_");
    rse.nextElement();
    try { 
      rse.nextElement();
      fail("Should have blown up");
    } catch (NoSuchElementException e) { 
      e = null;
    }
    
    // FIXME There should be a way to provoke RowDisappearedPoemException
    User u = new User("tester","tester","tester");
    getDb().getUserTable().create(u); 
    Enumeration<Integer> rse2 = getDb().getUserTable().troidSelection(null,null,false);
    u.delete();
    try { 
      while(rse2.hasMoreElements()) { 
        rse2.nextElement();
      }
      //fail("Should have blown up");
    } catch (RowDisappearedPoemException e) {
      e = null;
    }
  }

  /**
   * Test method for {@link org.melati.poem.ResultSetEnumeration#skip()}.
   */
  public void testSkip() {
    ResultSetEnumeration<Persistent> rse = (ResultSetEnumeration<Persistent>)getDb().getUserTable().getLoginColumn().selectionWhereEq("_guest_");
    rse.skip();
    try { 
      rse.nextElement();
      fail("Should have blown up");
    } catch (NoSuchElementException e) { 
      e = null;
    }    
    rse = (ResultSetEnumeration<Persistent>)getDb().getUserTable().getLoginColumn().selectionWhereEq("_guest_");
    rse.skip();
    try { 
      rse.skip();
      fail("Should have blown up");
    } catch (NoSuchElementException e) { 
      e = null;
    }    
  }

}
