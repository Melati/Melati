/**
 * 
 */
package org.melati.poem.test;

import java.util.Properties;

import org.melati.poem.Persistent;
import org.melati.poem.PersistentFactory;
import org.melati.poem.PoemDatabaseFactory;
import org.melati.poem.User;
import org.melati.poem.test.pojo.ClassWithByteArrayMember;
import org.melati.poem.test.pojo.ClassWithId;
import org.melati.poem.test.pojo.ClassWithNoIdAndPrivateMembers;
import org.melati.poem.test.pojo.ClassWithNoIdAndPublicMembers;
import org.melati.poem.test.pojo.ClassWithStringId;
/**
 * @author timp
 * @since 14 June 2007
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
   * Test method for {@link org.melati.poem.PersistentFactory#fromInstance(java.lang.Object)}.
   */
  public void testFromFloatingPersistent() {
    User u = (User)getDb().getUserTable().newPersistent();
    u.setLogin("test");
    u.setName("Test");
    u.setPassword("test");
    User u2 = (User)PersistentFactory.fromInstance(getDb(), u);
    assertNotNull(u2.getTroid());
    u2.delete();
  }

  /**
   * 
   */
  public void testFromUnknownInstance() { 
    if (!getDb().getDbms().canDropColumns()) {
      return;
    }
    // Create one before we start so that it can be compared and rejected
    ClassWithNoIdAndPublicMembers d1 = new ClassWithNoIdAndPublicMembers("Pepper");
    d1.setThoughts("Squirrels");
    Persistent persistedDog = PersistentFactory.fromInstance(getDb(), d1);
    assertEquals(new Integer(0), persistedDog.getTroid());
    
    ClassWithNoIdAndPublicMembers d2 = new ClassWithNoIdAndPublicMembers("Fido");
    d2.setThoughts("Food");
    d2.setClassWithNoIdAndPrivateMembers(new ClassWithNoIdAndPrivateMembers("Tiddles"));
    Persistent persistedDog2 = PersistentFactory.fromInstance(getDb(), d2);
    assertEquals("Food", persistedDog2.getRaw("thoughts"));
    assertEquals("Tiddles", ((Persistent)persistedDog2.getCooked("classWithNoIdAndPrivateMembers")).getRaw("name"));
    assertEquals(new Integer(1), persistedDog2.getTroid());
    
    // Do it again to exercise selection
    Persistent persistedDog3 = PersistentFactory.fromInstance(getDb(), d2);
    assertEquals("Food", persistedDog3.getRaw("thoughts"));
    assertEquals("Tiddles", ((Persistent)persistedDog3.getCooked("classWithNoIdAndPrivateMembers")).getRaw("name"));
    assertEquals(new Integer(1), persistedDog3.getTroid());
    assertTrue(persistedDog2.equals(persistedDog3));
  }
  
  /**
   * @throws Exception 
   */
  public void testFrom() throws Exception {
    if (!getDb().getDbms().canDropColumns()) {
      return;
    }
    ClassWithNoIdAndPublicMembers pojo = new ClassWithNoIdAndPublicMembers("Fido");
    pojo.setThoughts("Food");
    pojo.setClassWithNoIdAndPrivateMembers(new ClassWithNoIdAndPrivateMembers("Tiddles"));
    Persistent persistedDog = PersistentFactory.fromInstance(getDb(), pojo);
    ClassWithNoIdAndPublicMembers pojo2 = 
      (ClassWithNoIdAndPublicMembers)PersistentFactory.from(
              getDb().getTable("classWithNoIdAndPublicMembers").getObject(0), ClassWithNoIdAndPublicMembers.class);
    assertEquals(persistedDog,getDb().getTable("classWithNoIdAndPublicMembers").getObject(0));
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
    if (!getDb().getDbms().canDropColumns()) {
      return;
    }
    ClassWithStringId pojo = new ClassWithStringId();
    pojo.setId("99"); 
    Persistent persisted = PersistentFactory.fromInstance(getDb(), pojo);
    assertEquals("99",persisted.getCooked("id"));
    
    ClassWithId withId = new ClassWithId();
    withId.setId(new Integer(99));
    persisted = PersistentFactory.fromInstance(getDb(), withId);
    assertEquals(new Integer(0), persisted.getTroid());
    assertEquals(new Integer(0), persisted.getCooked("poemId"));
    assertEquals(new Integer(99), persisted.getCooked("id"));
  }
  /**
   * Test persisting an oject with a byte array member.
   */
  public void testPersistentFromPojoWithBinaryField() { 
    if (!getDb().getDbms().canDropColumns()) {
      return;
    }
    ClassWithByteArrayMember pojo = new ClassWithByteArrayMember();
    pojo.setBinaryField(new byte[] {34,35,36});
    Persistent persisted = PersistentFactory.fromInstance(getDb(), pojo);
    assertEquals(35,((byte[])persisted.getCooked("binaryField"))[1]);
  }
  
  /**
   * Try to resurrect to wrong class.
   */
  public void testPojoFromWrongPersistent() throws Exception { 
    if (!getDb().getDbms().canDropColumns()) {
      return;
    }
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
