/**
 * 
 */
package org.melati.poem.test;


import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.melati.poem.Persistent;
import org.melati.poem.TableMap;
import org.melati.poem.User;

/**
 * @author timp
 * @since 8 Jun 2007
 *
 */
public class TableMapTest extends PoemTestCase {
  
  private TableMap it;
  private TableMap noArg;

  /**
   * @param name
   */
  public TableMapTest(String name) {
    super(name);
  }

  /** 
   * {@inheritDoc}
   * @see org.melati.poem.test.PoemTestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
    createObjectsUnderTest();
  }

  protected void createObjectsUnderTest() {
    it = new TableMap(getDb().getUserTable());
    noArg = new TableMap();
  }

  protected TableMap getObjectUnderTest() { 
    return it;
  }
  protected TableMap getNoArgObjectUnderTest() { 
    return noArg;
  }

  /** 
   * {@inheritDoc}
   * @see org.melati.poem.test.PoemTestCase#tearDown()
   */
  protected void tearDown() throws Exception {
    super.tearDown();
  }

  /**
   * Test method for {@link org.melati.poem.TableMap#TableMap()}.
   */
  public void testTableMap() {
    
  }

  /**
   * Test method for {@link org.melati.poem.TableMap#TableMap(org.melati.poem.Table)}.
   */
  public void testTableMapTable() {
    
  }

  /**
   * Test method for {@link org.melati.poem.TableMap#getTable()}.
   */
  public void testGetTable() {
    System.err.println(getObjectUnderTest().getClass());
    assertEquals(getDb().getUserTable(), getObjectUnderTest().getTable());
    assertNull(getNoArgObjectUnderTest().getTable());
    
  }

  /**
   * Test method for {@link org.melati.poem.TableMap#setTable(org.melati.poem.Table)}.
   */
  public void testSetTable() {
    getNoArgObjectUnderTest().setTable(getDb().getUserTable());
    assertEquals(getDb().getUserTable(), getNoArgObjectUnderTest().getTable());
    
  }

  /**
   * Test method for {@link org.melati.poem.TableMap#clear()}.
   */
  public void testClear() {
    try { 
      getObjectUnderTest().clear();
      fail("Should have bombed");
    } catch (UnsupportedOperationException e) { 
      e = null;
    }
  }

  /**
   * Test method for {@link org.melati.poem.TableMap#containsKey(java.lang.Object)}.
   */
  public void testContainsKey() {
    assertTrue(getObjectUnderTest().containsKey(new Integer(0)));
    try { 
      getNoArgObjectUnderTest().containsKey(new Integer(0));
      fail("Should have bombed");
    } catch (NullPointerException e) { 
      e = null;
    }    
  }

  /**
   * Test method for {@link org.melati.poem.TableMap#containsValue(java.lang.Object)}.
   */
  public void testContainsValue() {
    assertTrue(getObjectUnderTest().containsValue(getDb().getUserTable().administratorUser()));
    try { 
      getNoArgObjectUnderTest().containsValue(getDb().getUserTable().administratorUser());
      fail("Should have bombed");
    } catch (NullPointerException e) { 
      e = null;
    }
  }

  /**
   * Test method for {@link org.melati.poem.TableMap#entrySet()}.
   */
  public void testEntrySet() {
    try { 
      getObjectUnderTest().entrySet();
      fail("Should have bombed");
    } catch (UnsupportedOperationException e) { 
      e = null;
    }    
  }

  /**
   * Test method for {@link org.melati.poem.TableMap#get(java.lang.Object)}.
   */
  public void testGet() {
    assertEquals(getDb().getUserTable().administratorUser(),getObjectUnderTest().get(new Integer(1)));
    try { 
      getNoArgObjectUnderTest().get(new Integer(0));
      fail("Should have bombed");
    } catch (NullPointerException e) { 
      e = null;
    }
  }

  /**
   * Test method for {@link org.melati.poem.TableMap#isEmpty()}.
   */
  public void testIsEmpty() {
    assertFalse(getObjectUnderTest().isEmpty());
    getObjectUnderTest().setTable(getDb().getSettingTable());
    assertTrue(getObjectUnderTest().isEmpty());    
  }

  /**
   * Test method for {@link org.melati.poem.TableMap#keySet()}.
   */
  public void testKeySet() {
    try { 
      getObjectUnderTest().keySet();
      fail("Should have bombed");
    } catch (UnsupportedOperationException e) { 
      e = null;
    }        
  }

  /**
   * Test method for {@link org.melati.poem.TableMap#put(java.lang.Object, java.lang.Object)}.
   */
  public void testPut() {
    /*
     * So generics are good for something 
    try { 
      getObjectUnderTest().put("1","1");
      fail("Should have bombed");
    } catch (UnsupportedOperationException e) { 
      e = null;
    }  
    */          
  }

  /**
   * Test method for {@link org.melati.poem.TableMap#putAll(java.util.Map)}.
   */
  public void testPutAll() {
    try { 
      getObjectUnderTest().putAll(noArg);
      fail("Should have bombed");
    } catch (UnsupportedOperationException e) { 
      e = null;
    }                
  }

  /**
   * Test method for {@link org.melati.poem.TableMap#remove(java.lang.Object)}.
   */
  public void testRemove() {
    User u = (User)getDb().getUserTable().newPersistent();
    u.setLogin("test");
    u.setName("Test");
    u.setPassword("test");
    assertFalse(getObjectUnderTest().containsValue(u));
    u.makePersistent();
    assertTrue(getObjectUnderTest().containsValue(u));
    User old = (User)getObjectUnderTest().remove(u.getTroid());
    assertFalse(getObjectUnderTest().containsValue(u));
    assertFalse(getObjectUnderTest().containsValue(old));
    // Its status is DELETED
    assertFalse(old.statusExistent());
    assertFalse(old.statusNonexistent());

    assertFalse(u.statusExistent());
    assertFalse(u.statusNonexistent());
  }

  /**
   * Test method for {@link org.melati.poem.TableMap#size()}.
   */
  public void testSize() {
    assertEquals(2, getObjectUnderTest().size());
  }

  /**
   * Test method for {@link org.melati.poem.TableMap#values()}.
   */
  public void testValues() {
    Collection<Persistent> c = getObjectUnderTest().values();
    Iterator<Persistent> i = c.iterator(); 
    assertEquals("_guest_",i.next().toString());
    assertEquals("_administrator_",i.next().toString());
    try { 
      i.next();
      fail("Should have bombed");
    } catch (NoSuchElementException e) { 
      e = null;
    }
  }

}
