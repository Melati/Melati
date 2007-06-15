/**
 * 
 */
package org.melati.poem.test.pojo;

/**
 * A classWithNoIdAndPrivateMembers.
 */
public class ClassWithNoIdAndPrivateMembers { 
  /** Constructor. */
  public ClassWithNoIdAndPrivateMembers() { 
  }
  /** Constructor. */
  public ClassWithNoIdAndPrivateMembers(String name) { 
    this.name = name;
  }
  /**  */
  public String name;
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
  
}