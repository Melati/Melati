package org.melati;

/**
 * The type of plain old integers.
 */

public class IntegerType extends NumberType {

  /**
   * The name of this type.
   */

  public Phrase getName() {
    // how does this work?
    return null;
  }

  public int getMin() {
    return (int)style.getMin();
  }

  public int getMax() {
    return (int)style.getMax();
  }
}
