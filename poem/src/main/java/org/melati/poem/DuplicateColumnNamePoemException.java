package org.melati.poem;

public class DuplicateColumnNamePoemException extends NormalPoemException {
  public Table table;
  public Column column;
  public DuplicateColumnNamePoemException(Table table, Column column) {
    this.table = table;
    this.column = column;
  }

  public String toString() {
    return "Can't add duplicate column " + column + " to " + table;
  }
}
