package org.melati;

public class TextType extends StringType {

  /**
   * The name of this type.
   */

  public Phrase getName() {
    // how does this work?
    return null;
  }

  /**
   * The number of rows a display or entry field for the string ought to
   * take up.
   */

  public int getRows() {
    return style.getRows();
  }

  /**
   * Define the number of columns a display or entry field for the string ought
   * to take up.
   */

  public void setRows(int rows) {
    style.setRows(rows);
  }
}
