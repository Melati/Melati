package org.melati.poem;

public class NoDeletedColumnException extends SeriousPoemException {
  public Table table;

  public NoDeletedColumnException(Table table) {
    this.table = table;
  }

  public String getMessage() {
    return "Table " + table + " has no defined deleted column";
  }
}
