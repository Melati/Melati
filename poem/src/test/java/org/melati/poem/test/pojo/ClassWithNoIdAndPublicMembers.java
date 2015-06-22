package org.melati.poem.test.pojo;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;



/**
 * A dog.
 */
public class ClassWithNoIdAndPublicMembers { 
  /**
   * 
   */
  public ClassWithNoIdAndPublicMembers() { 
    
  }
  public ClassWithNoIdAndPublicMembers(String name) {
    this.name = name;
  }
  /** The name.  */
  public String name;
  /** The classWithNoIdAndPrivateMembers. */
  public ClassWithNoIdAndPrivateMembers classWithNoIdAndPrivateMembers;
  
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
   * @return the classWithNoIdAndPrivateMembers
   */
  public ClassWithNoIdAndPrivateMembers getClassWithNoIdAndPrivateMembers() {
    return classWithNoIdAndPrivateMembers;
  }
  /**
   * @param classWithNoIdAndPrivateMembers the classWithNoIdAndPrivateMembers to set
   */
  public void setClassWithNoIdAndPrivateMembers(ClassWithNoIdAndPrivateMembers classWithNoIdAndPrivateMembers) {
    this.classWithNoIdAndPrivateMembers = classWithNoIdAndPrivateMembers;
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
   * @param d the d to set
   */
  public void setD(Double d) {
    this.d = d.doubleValue();
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