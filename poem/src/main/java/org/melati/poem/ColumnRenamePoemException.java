package org.melati.poem;

public class ColumnRenamePoemException extends AccessPoemException {
  public String columnName;
  public String newName;

  public ColumnRenamePoemException(String columnName, String newName) {
    this.columnName = columnName;
    this.newName = newName;
  }

  public ColumnRenamePoemException(String newName) {
    this(null, newName);
  }

  public String getMessage() {
    return
        "You tried to rename a column " + // the column " + columnName + " " +
        "to " + newName + " in the `columninfo' table, " +
        "but that isn't supported";
  }
}
