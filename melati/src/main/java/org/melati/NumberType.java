package org.melati;

/**
 * The base of all numeric types.
 */

public abstract class NumberType extends AtomType {

  /**
   * The number of digits (before the point).  This is actually computed from
   * the min/max range.
   */

  public int getDigitsBeforePoint() {
    // FIXME cope with minus signs too
    return (int)(Math.log(style.getMax()) / Math.log(10.) + 0.5);
  }

  /**
   * Set the number of digits (before the point).
   */

  public void setDigits(int digits) {
    style.setDigits(digits);
  }
}
