package org.melati.poem;

public class DuplicateDeletedColumnPoemException extends NormalPoemException {

  public Table table;
  public Column column;

  public DuplicateDeletedColumnPoemException(Table table, Column column) {
    this.table = table;
    this.column = column;
  }

  public String getMessage() {
    return
        "Can't add " + column.getName() + " to " + table.getName() +
        " as a deleted column, because it already has one, i.e. " +
        table.deletedColumn();
  }
}
