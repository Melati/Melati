package org.melati.poem;

public class TableMismatchPoemException extends PoemException {
  public Persistent value;
  public Table table;

  public TableMismatchPoemException(Persistent value, Table table) {
    this.value = value;
    this.table = table;
  }
}
