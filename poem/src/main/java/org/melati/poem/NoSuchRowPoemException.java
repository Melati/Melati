package org.melati.poem;

public class NoSuchRowPoemException extends NormalPoemException {
  public Table table;
  public Integer troid;
  public NoSuchRowPoemException(Table table, Integer troid) {
    this.troid = troid;
  }
}
