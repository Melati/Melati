package org.melati.poem;

public class NoSuchRowPoemException extends NormalPoemException {
  public Table table;
  public Integer troid;
  public NoSuchRowPoemException(Table table, Integer troid) {
    this.table = table;
    this.troid = troid;
  }

  public String getMessage() {
    return "No such row " + table.getName() + "/" + troid;
  }
}
