package org.melati.poem;

public class ColumnInUsePoemException extends SeriousPoemException {
  public Table table;
  public Column column;

  public ColumnInUsePoemException(Table table, Column column) {
    this.table = table;
    this.column = column;
  }
}
