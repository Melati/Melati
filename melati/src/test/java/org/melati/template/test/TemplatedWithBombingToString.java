package org.melati.template.test;

public class TemplatedWithBombingToString {

  /**
   * Constructor.
   */
  public TemplatedWithBombingToString() {
    super();
  }
  public String toString() { 
    throw new Error("Who said you could look at this?");
  }
}
