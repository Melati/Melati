package org.melati;

public abstract class StringType extends AtomType {

  /**
   * The maximum length of the string.
   */

  public int getMaxLength() {
    return maxLength;
  }

  /**
   * The number of columns a display or entry field for the string ought to
   * take up.
   */

  public int getCols() {
    return style.getCols();
  }

  /**
   * Define the number of columns a display or entry field for the string ought
   * to take up.
   */

  public void setCols(int cols) {
    style.setCols(cols);
  }

  /**
   * Whether implicit line breaks should be allowed in the field when it is
   * displayed.  Basically this means: should spaces be turned into
   * &amp;nbsp;s?
   */

  public boolean getAllowsExtraLineBreaks() {
    return style.getAllowsExtraLineBreaks();
  }

  /**
   * Define whether line breaks should be allowed in the field when it is
   * displayed.  Basically this means: should spaces be turned into
   * &amp;nbsp;s?
   */

  public void setAllowsExtraLineBreaks(boolean aelb) {
    style.setAllowsExtraLineBreaks(aelb);
  }
}
