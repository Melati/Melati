/**
 * 
 */
package org.melati.poem.test;

import java.util.Properties;

import org.melati.poem.Persistent;
import org.melati.poem.PersistentFactory;
import org.melati.poem.PoemDatabaseFactory;
import org.melati.poem.test.pojo.ClassWithId;
import org.melati.poem.test.pojo.ClassWithNoIdAndPrivateMembers;
import org.melati.poem.test.pojo.ClassWithNoIdAndPublicMembers;
import org.melati.poem.test.pojo.ClassWithStringId;
/**
 * @author timp
 * @since 14 Jun 2007
 *
 */
public class PersistentFactoryTest extends PoemTestCase {

  /**
   * @param name
   */
  public PersistentFactoryTest(String name) {
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
    getDb().disconnect();
    PoemDatabaseFactory.removeDatabase(getDatabaseName());
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
   * Test method for {@link org.melati.poem.PersistentFactory#fromInstance(java.lang.Object)}.
   */
  public void testFromKnownInstance() {
    assertEquals(getDb().getUserTable().administratorUser(), 
            PersistentFactory.fromInstance(getDb(), getDb().getUserTable().administratorUser()));
  }

  /**
   * 
   */
  public void testFromUnknownInstance() { 
    ClassWithNoIdAndPublicMembers d = new ClassWithNoIdAndPublicMembers("Fido");
    d.setThoughts("Food");
    d.setClassWithNoIdAndPrivateMembers(new ClassWithNoIdAndPrivateMembers("Tiddles"));
    Persistent persistedDog = PersistentFactory.fromInstance(getDb(), d);
    assertEquals("Food", persistedDog.getRaw("thoughts"));
    assertEquals("Tiddles", ((Persistent)persistedDog.getCooked("classWithNoIdAndPrivateMembers")).getRaw("name"));
  }
  
  /**
   * @throws Exception 
   */
  public void testFrom() throws Exception {
    ClassWithNoIdAndPublicMembers pojo = new ClassWithNoIdAndPublicMembers("Fido");
    pojo.setThoughts("Food");
    pojo.setClassWithNoIdAndPrivateMembers(new ClassWithNoIdAndPrivateMembers("Tiddles"));
    Persistent persistedDog = PersistentFactory.fromInstance(getDb(), pojo);
    ClassWithNoIdAndPublicMembers pojo2 = 
      (ClassWithNoIdAndPublicMembers)PersistentFactory.from(
              getDb().getTable("classWithNoIdAndPublicMembers").getObject(0), ClassWithNoIdAndPublicMembers.class);
    assertEquals(persistedDog,(Persistent)getDb().getTable("classWithNoIdAndPublicMembers").getObject(0));
    assertEquals(pojo.getThoughts(),pojo2.getThoughts());
    assertEquals(pojo2.getClassWithNoIdAndPrivateMembers().getName(),pojo.getClassWithNoIdAndPrivateMembers().getName());
    assertEquals(pojo2.getClassWithNoIdAndPrivateMembers().getClass(),pojo.getClassWithNoIdAndPrivateMembers().getClass());
  }
  
  /**
   * Test Exception throwing.
   */
  public void testFromBadInput() throws Exception {
    try { 
      PersistentFactory.fromInstance(getDb(), null);
      fail("Should have bombed");
    } catch (NullPointerException e) { 
      e = null;
    }
    ClassWithNoIdAndPublicMembers d = new ClassWithNoIdAndPublicMembers("Fido");
    try { 
      PersistentFactory.fromInstance(null, d);
      fail("Should have bombed");
    } catch (NullPointerException e) { 
      e = null;
    }
  }
  
  /**
   * 
   */
  public void testPersistentFromPojoWithIdSet() throws Exception { 
    ClassWithStringId pojo = new ClassWithStringId();
    pojo.setId("99"); 
    Persistent persisted = PersistentFactory.fromInstance(getDb(), pojo);
    assertEquals("99",persisted.getCooked("id"));
    
    ClassWithId withId = new ClassWithId();
    withId.setId(new Integer(99));
    persisted = PersistentFactory.fromInstance(getDb(), withId);
    assertEquals(new Integer(0), persisted.troid());
    assertEquals(new Integer(0), persisted.getCooked("poemId"));
    assertEquals(new Integer(99), persisted.getCooked("id"));
  }
  
  /**
   * Try to resurrect to wrong class.
   */
  public void testPojoFromWrongPersistent() throws Exception { 
    ClassWithNoIdAndPublicMembers pojo = new ClassWithNoIdAndPublicMembers("Fido");
    pojo.setThoughts("Food");
    Persistent persisted = PersistentFactory.fromInstance(getDb(), pojo);
    try { 
      PersistentFactory.from(persisted, Properties.class);
      fail("Should have bombed");
    } catch (NoSuchMethodException e) { 
      e = null;
    }
  }
}
