package org.melati.poem;

public class TableRenamePoemException extends AccessPoemException {
  public String tableName;
  public String newName;

  public TableRenamePoemException(String tableName, String newName) {
    this.tableName = tableName;
    this.newName = newName;
  }

  public TableRenamePoemException(String newName) {
    this(null, newName);
  }

  public String getMessage() {
    return
        "You tried to rename a table " +
        "to " + newName + " in the `tableinfo' table, " +
        "but that isn't supported";
  }
}
