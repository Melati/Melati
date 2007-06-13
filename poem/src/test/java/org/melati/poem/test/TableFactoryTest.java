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
    //getDb().dump();
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
    /**
     * @return the aLong
     */
    public long getALong() {
      return aLong;
    }
    /**
     * @param long1 the aLong to set
     */
    public void setALong(long long1) {
      aLong = long1;
    }
    /**
     * @return the aLongObject
     */
    public Long getALongObject() {
      return aLongObject;
    }
    /**
     * @param longObject the aLongObject to set
     */
    public void setALongObject(Long longObject) {
      aLongObject = longObject;
    }
    /**
     * @return the cat
     */
    public Cat getCat() {
      return cat;
    }
    /**
     * @param cat the cat to set
     */
    public void setCat(Cat cat) {
      this.cat = cat;
    }
    /**
     * @return the d
     */
    public double getD() {
      return d;
    }
    /**
     * @param d the d to set
     */
    public void setD(double d) {
      this.d = d;
    }
    /**
     * @return the dateOfBirth
     */
    public Date getDateOfBirth() {
      return dateOfBirth;
    }
    /**
     * @param dateOfBirth the dateOfBirth to set
     */
    public void setDateOfBirth(Date dateOfBirth) {
      this.dateOfBirth = dateOfBirth;
    }
    /**
     * @return the dd
     */
    public Double getDd() {
      return dd;
    }
    /**
     * @param dd the dd to set
     */
    public void setDd(Double dd) {
      this.dd = dd;
    }
    /**
     * @return the hungry
     */
    public boolean isHungry() {
      return hungry;
    }
    /**
     * @param hungry the hungry to set
     */
    public void setHungry(boolean hungry) {
      this.hungry = hungry;
    }
    /**
     * @return the legs
     */
    public int getLegs() {
      return legs;
    }
    /**
     * @param legs the legs to set
     */
    public void setLegs(int legs) {
      this.legs = legs;
    }
    /**
     * @return the name
     */
    public String getName() {
      return name;
    }
    /**
     * @param name the name to set
     */
    public void setName(String name) {
      this.name = name;
    }
    /**
     * @return the price
     */
    public BigDecimal getPrice() {
      return price;
    }
    /**
     * @param price the price to set
     */
    public void setPrice(BigDecimal price) {
      this.price = price;
    }
    /**
     * @return the reallyHungry
     */
    public Boolean getReallyHungry() {
      return reallyHungry;
    }
    /**
     * @param reallyHungry the reallyHungry to set
     */
    public void setReallyHungry(Boolean reallyHungry) {
      this.reallyHungry = reallyHungry;
    }
    /**
     * @return the tail
     */
    public Integer getTail() {
      return tail;
    }
    /**
     * @param tail the tail to set
     */
    public void setTail(Integer tail) {
      this.tail = tail;
    }
    /**
     * @return the timeOfBirth
     */
    public Timestamp getTimeOfBirth() {
      return timeOfBirth;
    }
    /**
     * @param timeOfBirth the timeOfBirth to set
     */
    public void setTimeOfBirth(Timestamp timeOfBirth) {
      this.timeOfBirth = timeOfBirth;
    }
    /**
     * @param thoughts the thoughts to set
     */
    public void setThoughts(String thoughts) {
      this.thoughts = thoughts;
    }
  }
  class Cat { 
    /**
     * 
     */
    public String name;
    
  }
}
