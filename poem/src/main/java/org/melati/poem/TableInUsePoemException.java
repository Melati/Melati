package org.melati.poem;

public class TableInUsePoemException extends SeriousPoemException {
  public Database database;
  public Table table;

  public TableInUsePoemException(Database database, Table table) {
    this.database = database;
    this.table = table;
  }
}
