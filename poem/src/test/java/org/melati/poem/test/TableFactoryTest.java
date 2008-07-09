/**
 * 
 */
package org.melati.poem.test;


import org.melati.poem.PoemDatabaseFactory;
import org.melati.poem.Table;
import org.melati.poem.TableFactory;
import org.melati.poem.test.pojo.ClassWithNoIdAndPublicMembers;
import org.melati.poem.test.pojo.ClassWithStringId;

/**
 * @author timp
 * @since 12 Jun 2007
 *
 */
public class TableFactoryTest extends PoemTestCase {

  /**
   * @param name
   */
  public TableFactoryTest(String name) {
    super(name);
  }

  /** 
   * {@inheritDoc}
   * @see org.melati.poem.test.PoemTestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
  }
  protected void databaseUnchanged() { 
    assertEquals("Setting changed", 0, getDb().getSettingTable().count());
    assertEquals("Group changed", 1, getDb().getGroupTable().count());
    assertEquals("GroupMembership changed", 1, getDb().getGroupMembershipTable().count());
    assertEquals("Capability changed", 5, getDb().getCapabilityTable().count());
    assertEquals("GroupCapability changed", 1, getDb().getGroupCapabilityTable().count());
    assertEquals("TableCategory changed", 3, getDb().getTableCategoryTable().count());
    assertEquals("User changed", 2, getDb().getUserTable().count());
    //ColumnInfo newOne = null; 
    //try{ 
    //  newOne = (ColumnInfo)getDb().getColumnInfoTable().getObject(69);
    //} catch (Exception e) {}
    //if (newOne != null) { 
    //  System.err.println(newOne.getName() + " " + newOne.getTableinfo().getName());
    //}
    //assertEquals("ColumnInfo changed", 69, getDb().getColumnInfoTable().count());
    //assertEquals("TableInfo changed", 9, getDb().getTableInfoTable().count());
    //checkTablesAndColumns(9,69);
  }

  /** 
   * {@inheritDoc}
   * @see org.melati.poem.test.PoemTestCase#tearDown()
   */
  protected void tearDown() throws Exception {
    super.tearDown();
    getDb().disconnect();
    PoemDatabaseFactory.removeDatabase(getDatabaseName());
  }

  /**
   * Test method for {@link org.melati.poem.TableFactory#fromInstance(org.melati.poem.Database, java.lang.Object)}.
   */
  public void testFromKnownInstance() {
    assertEquals(getDb().getUserTable(), TableFactory.fromInstance(getDb(), getDb().getUserTable().administratorUser()));
  }
  /**
   * Test method for {@link org.melati.poem.TableFactory#fromInstance(org.melati.poem.Database, java.lang.Object)}.
   */
  public void testFromUnKnownInstance() {
    Table t = TableFactory.fromInstance(getDb(), new ClassWithNoIdAndPublicMembers());
    assertEquals("ClassWithNoIdAndPublicMembers", t.getName());
    // FIXME Delete tableinfo and columnInfo
    //getDb().delete(t);
    //getDb().dump();
  }
  /**
   * Test exception thrown.
   */
  public void testFromObjectWithStringIdField() { 
    Table table =  TableFactory.fromInstance(getDb(), new ClassWithStringId());
    assertEquals("ClassWithStringId", table.getName());
  }
  /**
   * Test exception thrown.
   */
  public void testFromNonPublicObject() { 
    try { 
      TableFactory.fromInstance(getDb(), new NonPublicClass());
      fail("Should have blown up.");
    } catch (IllegalArgumentException e) { 
      e = null;
    }
  }
  /**
   * Test method for {@link org.melati.poem.TableFactory#fromClass(org.melati.poem.Database, java.lang.Class)}.
   */
  public void testFromClassBadInput() {
    try { 
      TableFactory.fromClass(getDb(), int.class);
      fail("Should have blown up");
    } catch (IllegalArgumentException e) { 
      e = null;
    }
    try { 
      TableFactory.fromInstance(getDb(), new String[] {});
      fail("Should have blown up");
    } catch (IllegalArgumentException e) { 
      e = null;
    }
  }
  /**
   * @author timp
   * @since 14 Jun 2007
   *
   */
  class NonPublicClass {
    NonPublicClass() {}
  }
}
