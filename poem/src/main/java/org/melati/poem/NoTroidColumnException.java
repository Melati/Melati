package org.melati.poem;

public class NoTroidColumnException extends SeriousPoemException {
  public Table table;

  public NoTroidColumnException(Table table) {
    this.table = table;
  }

  public String getMessage() {
    return "Table " + table + " has no defined troid column";
  }
}
