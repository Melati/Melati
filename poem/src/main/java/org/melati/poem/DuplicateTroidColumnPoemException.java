package org.melati.poem;

public class DuplicateTroidColumnPoemException extends NormalPoemException {

  public Table table;
  public Column column;

  public DuplicateTroidColumnPoemException(Table table, Column column) {
    this.table = table;
    this.column = column;
  }

  public String getMessage() {
    return
        "Can't add " + column.getName() + " to " + table.getName() +
        " as a troid column, because it already has one, i.e. " +
        table.troidColumn();
  }
}
