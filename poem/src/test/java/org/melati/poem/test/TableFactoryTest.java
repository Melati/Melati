/**
 * 
 */
package org.melati.poem.test;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

import org.melati.poem.Database;
import org.melati.poem.PoemDatabaseFactory;
import org.melati.poem.TableFactory;

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
    Database db = getDb();
    db.disconnect();
    PoemDatabaseFactory.removeDatabase(PoemTestCase.databaseName);
  }

  /**
   * Test method for {@link org.melati.poem.TableFactory#fromInstance(org.melati.poem.Database, java.lang.Object)}.
   */
  public void testFromKnownInstance() {
    System.err.println(TableFactory.fromInstance(getDb(), getDb().getUserTable().administratorUser()));
  }
  /**
   * Test method for {@link org.melati.poem.TableFactory#fromInstance(org.melati.poem.Database, java.lang.Object)}.
   */
  public void testFromUnKnownInstance() {
    System.err.println(TableFactory.fromInstance(getDb(), new Dog()));
    getDb().dump();
  }

  /**
   * Test method for {@link org.melati.poem.TableFactory#fromClass(org.melati.poem.Database, java.lang.Class)}.
   */
  public void testFromClassBadInput() {
    try { 
      System.err.println(TableFactory.fromClass(getDb(), int.class));
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
  class Dog { 
    /**
     * 
     */
    public Dog() { 
      
    }
    /**
     * @param name
     */
    public Dog(String name) { 
      this.name = name;
    }
    /** The name.  */
    public String name;
    /** The cat. */
    public Cat cat;
    
    /** Am I hungry: yes.  */
    public boolean hungry = true;
    /** Am I really hungry: yes.  */
    public Boolean reallyHungry = Boolean.TRUE;
    /** Number of legs. */
    public int legs = 4;
    /** Number of tails. */
    public Integer tail = new Integer(1);

    /** long. */
    public long aLong;
    /** A Long. */
    public Long aLongObject;
    /** */
    public double d;
    /** */
    public Double dd;
    /** Value. */
    public BigDecimal price;
    
    /** dob. */
    public Date dateOfBirth;
    /** A Long. */
    public Timestamp timeOfBirth;

    private String thoughts;
    
    
    /**
     * @return the thoughts
     */
    public String getThoughts() { 
      return thoughts;
    }
  }
  class Cat { 
    /**
     * 
     */
    public String name;
    
  }
}
